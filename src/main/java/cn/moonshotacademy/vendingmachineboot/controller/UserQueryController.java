package cn.moonshotacademy.vendingmachineboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.moonshotacademy.vendingmachineboot.dto.UserDAO;
import cn.moonshotacademy.vendingmachineboot.model.UserCategory;

import java.util.List;

@RestController
public class UserQueryController {
    @Autowired
    UserDAO userDAO;

    @RequestMapping("/balance-query")
    public List<UserCategory> balanceQuery(Model model, @RequestParam("name") String name) {
        UserCategory user = new UserCategory();
        user.setName(name);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.exact());
        List<UserCategory> userList = userDAO.findAll(Example.of(user, exampleMatcher));
        // List<UserCategory> userList = userDAO.findAllByName(name);
        return userList;
    }
}