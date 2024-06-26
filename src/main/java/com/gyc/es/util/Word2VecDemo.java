package com.gyc.es.util;

import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.Word2VecTrainer;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;

import java.io.IOException;
import java.util.Map;

public class Word2VecDemo {

    private static final String TRAIN_FILE_NAME = "/Users/biebersjustin/Downloads/hanlp-wiki-vec-zh/hanlp-wiki-vec-zh.txt";
    private static final String MODEL_FILE_NAME = "/Users/biebersjustin/Downloads/word2vec.model";

    public static void main(String[] args) throws IOException {
        WordVectorModel wordVectorModel = trainOrLoadModel();
        printNearest("上海", wordVectorModel);
        printNearest("美丽", wordVectorModel);
        printNearest("购买", wordVectorModel);
        System.out.println(wordVectorModel.similarity("上海", "广州"));
        System.out.println(wordVectorModel.analogy("日本", "自民党", "共和党"));

        // 文档向量
        DocVectorModel docVectorModel = new DocVectorModel(wordVectorModel);
        String[] documents = new String[]{
                "山东苹果丰收",
                "农民在江苏种水稻",
                "奥运会女排夺冠",
                "世界锦标赛胜出",
                "中国足球失败",
        };

        System.out.println(docVectorModel.similarity(documents[0], documents[1]));
        System.out.println(docVectorModel.similarity(documents[0], documents[4]));

        for (int i = 0; i < documents.length; i++) {
            docVectorModel.addDocument(i, documents[i]);
        }

        printNearestDocument("体育", documents, docVectorModel);
        printNearestDocument("农业", documents, docVectorModel);
        printNearestDocument("我要看比赛", documents, docVectorModel);
        printNearestDocument("要不做饭吧", documents, docVectorModel);
    }

    static void printNearest(String word, WordVectorModel model) {
        System.out.print("\n                                                Word     Cosine\n------------------------------------------------------------------------\n");
        for (Map.Entry<String, Float> entry : model.nearest(word)) {
            System.out.printf("%50s\t\t%f\n", entry.getKey(), entry.getValue());
        }
    }

    static void printNearestDocument(String document, String[] documents, DocVectorModel model) {
        printHeader(document);
        for (Map.Entry<Integer, Float> entry : model.nearest(document)) {
            System.out.printf("%50s\t\t%f\n", documents[entry.getKey()], entry.getValue());
        }
    }

    private static void printHeader(String query) {
        System.out.printf("\n%50s          Cosine\n------------------------------------------------------------------------\n", query);
    }

    static WordVectorModel trainOrLoadModel() throws IOException {
        if (!IOUtil.isFileExisted(MODEL_FILE_NAME)) {
            if (!IOUtil.isFileExisted(TRAIN_FILE_NAME)) {
                System.err.println("语料不存在，请阅读文档了解语料获取与格式：https://github.com/hankcs/HanLP/wiki/word2vec");
                System.exit(1);
            }
            Word2VecTrainer trainerBuilder = new Word2VecTrainer();
            return trainerBuilder.train(TRAIN_FILE_NAME, MODEL_FILE_NAME);
        }

        return loadModel();}

    static WordVectorModel loadModel() throws IOException {
        return new WordVectorModel(MODEL_FILE_NAME);
    }

}
