package com.gyc.es.service;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.gyc.es.bean.*;
import com.gyc.es.repository.BookRepository;
import com.gyc.es.util.ESUtil;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Value("${book.query.alias}")
    private String bookIndexName;

    /**
     * 书籍搜索
     *
     * @param keyword
     * @return
     */

    public List<Book> searchBook(String keyword) {

        //TODO　进行分词处理，获取词性 以及词性ID，进行不同搜索策略选择
        //List<Word> wordList=AnalyzerUtil.analyze(keyword);
        BookSearchRequest searchRequest = new BookSearchRequest();
        searchRequest.setKeyword(keyword);
        BookSearchResponse response = bookRepository.search(searchRequest);

        //TODO 返回数据，进行遍历加权处理排序 然后组装符合前端需要的数据格式
        List<Book> list = new ArrayList<>(10);

        //for (int i = 0; i < response.getDocs().size(); i++)
        for (int i = 1; i <= 10; i++) {
            Book book = new Book();
            //BeanUtils.copyProperties(response.getDocs().get(i), book);
            book.setId(i);
            book.setAuthor("作者" + i);
            book.setPrice(new BigDecimal(i + 1));
            book.setTitle("标题" + i);
            list.add(book);
        }
        return list;
    }

    /**
     * 书籍搜索
     *
     * @param keyword
     * @return
     */

    public List<Book> searchBookVec(String keyword) {

        //TODO　进行分词处理，获取词性 以及词性ID，进行不同搜索策略选择
        List<Term> termList = StandardTokenizer.segment(keyword);
        if (CollectionUtils.isEmpty(termList)) return null;
        String key = StrUtil.join(",", termList.stream().map(p -> p.word).collect(Collectors.toList()));
        BookSearchRequest searchRequest = new BookSearchRequest();
        searchRequest.setKeyword(key);
        BookSearchResponse response = bookRepository.search(searchRequest);

        //TODO 返回数据，进行遍历加权处理排序 然后组装符合前端需要的数据格式
        List<Book> list = new ArrayList<>(10);
        for (int i = 0; i < response.getDocs().size(); i++) {
            Book book = new Book();
            BeanUtils.copyProperties(response.getDocs().get(i), book);
            book.setPrice(new BigDecimal(response.getDocs().get(i).getPrice()/100.0));
            list.add(book);
        }
        return list;
    }

    public int addBooks() {
        final CsvReader reader = CsvUtil.getReader();
        //假设csv文件在classpath目录下
        final List<BookDTO> result = reader.read(
                ResourceUtil.getUtf8Reader("/Users/biebersjustin/IdeaProjects/es_demo/src/main/resources/data.csv"), BookDTO.class);
        if (CollectionUtils.isEmpty(result)) {
            return 0;
        }
        StringBuffer dsl = new StringBuffer();
        result.stream().forEach(x -> {
            ESBook esBook = new ESBook();
            esBook.setId(x.getId());
            esBook.setAuthor(x.getAuthor());
            esBook.setTitle(x.getTitle());
            esBook.setTitleVec(null);
            esBook.setCategory(x.getCategory());
            esBook.setPrice(x.getPriceRef().multiply(new BigDecimal(100)).intValue());
            String str = ESUtil.dealProductIndexBuildData(bookIndexName, esBook.getId(), esBook);
            dsl.append(str);
        });

        return bookRepository.addBooks(dsl.toString());
    }

}
