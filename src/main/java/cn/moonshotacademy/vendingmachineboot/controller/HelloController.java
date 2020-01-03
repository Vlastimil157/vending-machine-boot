package cn.moonshotacademy.vendingmachineboot.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class HelloController {
    @RequestMapping("/hello") 
    public String index() {
        return "Hello, World!";
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "Thymeleaf");
        return "hello";
    }

    @RequestMapping("/param")
    public String param(Model model, @RequestParam("id") String id) { // localhost:port/param?id=IDNAME
        model.addAttribute("name", id);
        return "param";
    }

    @RequestMapping("/path-param/{id}")
    public String pathParam(Model model, @PathVariable("id") String id) {
        return id + "/index";
    }

    @RequestMapping("/path-param/{id}/{htmlname}")
    public String pathParam(Model model, @PathVariable("id") String id, @PathVariable("htmlname") String htmlname) {
        return id + "/" + htmlname;
    }

    // 不能这么写
    // @RequestMapping("/path-param/{path1}-{path-2}/{htmlname}")
    // public String pathParam(Model model, @PathVariable("path1") String path1, @PathVariable("path2") String path2, @PathVariable("htmlname") String htmlname) {
    //     return "/" + path1 + "/" + path2 + "/" + htmlname;
    // }
}