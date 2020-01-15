package cn.moonshotacademy.vendingmachineboot.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import cn.moonshotacademy.vendingmachineboot.dao.DiscountDAO;
import cn.moonshotacademy.vendingmachineboot.dao.ProductDAO;
import cn.moonshotacademy.vendingmachineboot.dao.UserDAO;
import cn.moonshotacademy.vendingmachineboot.model.DiscountDTO;
import cn.moonshotacademy.vendingmachineboot.model.ProductDTO;
import cn.moonshotacademy.vendingmachineboot.model.UserDTO;
import cn.moonshotacademy.vendingmachineboot.vo.DiscountVO;
import cn.moonshotacademy.vendingmachineboot.vo.ProductVO;
import cn.moonshotacademy.vendingmachineboot.vo.UserVO;

@RestController
public class APIRestController {
    @Autowired
    UserDAO userDAO;

    @Autowired
    ProductDAO productDAO;

    @Autowired
    DiscountDAO discountDAO;

    @RequestMapping("/api/products")
    public ArrayList<ProductVO> products() {
        List<ProductDTO> list = productDAO.findAll();
        ArrayList<ProductVO> ret = new ArrayList<ProductVO>();
        for (ProductDTO p : list) {
            ret.add(new ProductVO(p));
        }
        return ret;
    }

    @RequestMapping("/api/discounts-single")
    public ArrayList<DiscountVO> discountSingle() {
        List<DiscountDTO> list = discountDAO.findAllSingle();
        ArrayList<DiscountVO> ret = new ArrayList<DiscountVO>();
        for (DiscountDTO d : list) {
            ret.add(new DiscountVO(d));
        }
        return ret;
    }

    @RequestMapping(value = "/api/discounts-global")
    public ArrayList<DiscountVO> discountGlobal() {
        List<DiscountDTO> list = discountDAO.findAllGlobal();
        ArrayList<DiscountVO> ret = new ArrayList<DiscountVO>();
        for (DiscountDTO d : list) {
            ret.add(new DiscountVO(d));
        }
        return ret;
    }

    @RequestMapping(value = "/api/calc/single-product", method = RequestMethod.POST)
    public ProductVO calcSingleProduct(@RequestBody ProductVO p) {
        ProductVO ret = p;
        ret.setCalc(calcSingleProductById(p.getId(), p.getSelect(), p.getDiscountSelect()));
        return ret;
    }


    public Double calcSingleProductById(Integer id, Integer count, Boolean select) {
        ProductDTO p = productDAO.findAllById(id).get(0);
        
        if (select) {
            DiscountDTO d = discountDAO.findAllById(p.getDiscountId()).get(0);
            
            switch (d.getMethod()) {
                case 1: return p.getPrice() * count * d.getVal1();
                case 2: {
                    Double init = p.getPrice() * count;
                    if (init > d.getVal1()) init -= d.getVal2();
                    return init;
                }
                case 3: return p.getPrice() * count - Math.floor(count / d.getVal1()) * (1 - d.getVal2()) * p.getPrice();
                case 4: return p.getPrice() * count - d.getVal1();
                default: return 9999999999d;
            }
        } else {
            return p.getPrice() * count;
        }
    }
}