package com.qiuwei.swagger.controller;

import com.qiuwei.swagger.bean.Book;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: qiuweib@yonyou.com
 * @Date: 2020-08-28.
 */
@RestController
@Api(tags = "书本类别")
public class BookController {

    /**
     * 1. @Api(): 用于类,表示标识这个类是swagger的资源
     * tags–表示说明
     * value–也是说明，可以使用tags替代
     * 2. @ApiOperation(): 用于方法,表示一个http请求的操作
     * value用于方法描述
     * notes用于提示内容
     * tags可以重新分组
     * 3. @ApiImplicitParam(): 用于方法，表示单独的请求参数
     * 4. @ApiImplicitParams() 用于方法，包含多个 @ApiImplicitParam
     * name–参数名称
     * value–参数说明
     * dataType–数据类型
     * paramType–参数类型 -- query:请求类型为Json , form :请求为表单类型
     * example–举例说明
     * required-是否必须
     * <p>
     * 5. @ApiModelProperty()用于方法，字段； 表示对model属性的说明或者数据操作更改
     * value–字段说明
     * name–重写属性名字
     * dataType–重写属性类型
     * required–是否必填
     * example–举例说明 (2.2.2版本bug造成example不显示,升级到2.4.0以上即可)
     * hidden–隐藏
     * <p>
     * 查询地址  http://localhost:8080/swagger-ui.html
     *
     * @param book
     * @return
     */


    @GetMapping("book")
    @ApiOperation(value = "查询书本分类", notes = "根据查询条件查询书本")
    public List<Book> listBook(@ApiParam(value = "书本查询条件", required = true) Book book) {
        List<Book> bookList = getBook(book);
        return bookList;
    }


    @PostMapping("add")
    @ApiOperation(value = "添加书本", notes = "添加书")
    public void addBook(@ApiParam(value = "添加书本参数", required = true) @RequestBody Book book) {
        System.out.println("添加书本:" + book.toString());

    }


    private List<Book> getBook(Book book) {
        List<Book> books = new ArrayList<>();

        for (int i = 1; i < 13; i++) {
            Book result = new Book();
            int type = i % 4;
            result.setType(type);
            result.setPrice(String.valueOf(i));
            result.setDesc("这是第" + i + "本书");
            result.setName(i + "禁");

            if (book.getType() == type) {
                books.add(result);
            }

        }
        return books;
    }


}
