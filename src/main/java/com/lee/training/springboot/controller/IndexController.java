package com.lee.training.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Lee0609x
 * @Date: 2018/7/23 18:58
 */
@Controller
@RequestMapping("/")
public class IndexController {
    public String Index() {
        return "index";
    }
}
