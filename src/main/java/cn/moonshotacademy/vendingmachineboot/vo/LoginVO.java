package cn.moonshotacademy.vendingmachineboot.vo;

import lombok.Data;

@Data
public class LoginVO {
    private String name;
    private String password;
    private Double balance;
}