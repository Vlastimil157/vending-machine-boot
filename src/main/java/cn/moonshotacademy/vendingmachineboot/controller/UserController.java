package cn.moonshotacademy.vendingmachineboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.moonshotacademy.vendingmachineboot.dao.UserDAO;
import cn.moonshotacademy.vendingmachineboot.model.UserDTO;

@Controller
public class UserController {
    @Autowired
    UserDAO userDAO;

    @RequestMapping("/users")
    public String listCategory(Model m) {
        List<UserDTO> cs = userDAO.findAll();

        m.addAttribute("cs", cs);

        return "users";
    }
}