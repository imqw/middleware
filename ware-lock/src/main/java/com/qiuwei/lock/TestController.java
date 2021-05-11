package com.qiuwei.lock;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("test")
public class TestController {


    @PostMapping("post")
    public Map test(@RequestBody Map params){

        System.out.println(params);

        Map<String, Object> map = new HashMap<>();

        map.put("code","0");
        map.put("msg","成功");
        map.put("success",true);
        map.put("date",null);

        return map;
    }



}
