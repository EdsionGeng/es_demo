package com.gyc.es.controller;

import com.gyc.es.bean.Book;
import com.gyc.es.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    /**
     * 搜索列表页展示
     *
     * @param keyword
     * @param result
     * @return
     */
    @GetMapping("/bookSearch")
    public String bookSearch(@RequestParam(value = "keyword") String keyword, Map<String, List> result) {
        List<Book> books = bookService.searchBook(keyword);
        result.put("books", books);
        return "index";
    }

    /**
     * 搜索列表页展示
     *
     * @param keyword
     * @param result
     * @return
     */
    @GetMapping("/searchVec")
    public String bookSearchVec(@RequestParam(value = "keyword") String keyword, Map<String, List> result) {
        List<Book> books = bookService.searchBook(keyword);
        result.put("books", books);
        return "index";
    }
    @GetMapping("/addData")
    @ResponseBody
    public String add() {
        int res = bookService.addBooks();
        if (res == 1) {
            return "success";
        }
        return "error";
    }
}
