package cn.moonshotacademy.vendingmachineboot.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.moonshotacademy.vendingmachineboot.dao.UserDAO;
import cn.moonshotacademy.vendingmachineboot.model.UserDTO;
import cn.moonshotacademy.vendingmachineboot.vo.UserVO;
import cn.moonshotacademy.vendingmachineboot.vo.LoginVO;

@RestController
public class LoginRestController {
    @Autowired
    UserDAO userDAO;

    @RequestMapping(value = "/login-check", method = RequestMethod.POST)
    public UserVO loginCheck(Model m, @RequestBody LoginVO u, HttpSession session, HttpServletResponse response) throws IOException {
        List<UserDTO> list = userDAO.findAllByNameAndPassword(u.getName(), u.getPassword());
        
        if (list.isEmpty()) {
            response.setStatus(401);
            response.getWriter().append("401");
            return null;
        }
        session.removeAttribute("id");
        session.setAttribute("id", list.get(0).getId());
        return new UserVO(list.get(0));
    }
}