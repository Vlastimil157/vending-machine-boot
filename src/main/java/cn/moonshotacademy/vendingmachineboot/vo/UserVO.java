package cn.moonshotacademy.vendingmachineboot.vo;

import cn.moonshotacademy.vendingmachineboot.model.UserDTO;

import lombok.Data;

@Data
public class UserVO {
    private Integer id;
    private String name;
    private Double balance;

    public UserVO(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.name = userDTO.getName();
        this.balance = userDTO.getBalance();
    }
}