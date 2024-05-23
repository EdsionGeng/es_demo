package com.gyc.es.controller;

import com.gyc.es.bean.Book;
import com.gyc.es.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
/**
 * 描述:
 * 商品Controller层
 *
 * @author gengyc
 * @create 2024-05-23 10:49
 */
@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    /**
     * 搜索列表页展示
     * @param keyword
     * @param result
     * @return
     */
    @GetMapping("/index.html")
    public String userList(@RequestParam(value = "keyword") String keyword, Map<String, List> result) {
        List<Book> books = bookService.searchBook(keyword);
        result.put("books", books);
        return "index";
    }
}
