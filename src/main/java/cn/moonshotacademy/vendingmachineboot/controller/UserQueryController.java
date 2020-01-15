package cn.moonshotacademy.vendingmachineboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.moonshotacademy.vendingmachineboot.dao.UserDAO;
import cn.moonshotacademy.vendingmachineboot.model.UserDTO;

import java.util.List;

@RestController
public class UserQueryController {
    @Autowired
    UserDAO userDAO;

    @RequestMapping("/balance-query")
    public List<UserDTO> balanceQuery(Model model, @RequestParam("name") String name) {
        UserDTO user = new UserDTO();
        user.setName(name);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.exact());
        List<UserDTO> userList = userDAO.findAll(Example.of(user, exampleMatcher));
        // List<UserDTO> userList = userDAO.findAllByName(name);
        return userList;
    }
}