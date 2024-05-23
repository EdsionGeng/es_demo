package com.gyc.es.service;

import com.gyc.es.bean.Book;
import com.gyc.es.bean.BookSearchRequest;
import com.gyc.es.bean.BookSearchResponse;
import com.gyc.es.bean.SearchRequest;
import com.gyc.es.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * 描述:
 * 商品Service层
 *
 * @author gengyc
 * @create 2024-05-23 10:49
 */
@RestController
public class BookService {
    @Autowired
    private BookRepository bookRepository;

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

}
