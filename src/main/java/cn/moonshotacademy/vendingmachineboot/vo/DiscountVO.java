package cn.moonshotacademy.vendingmachineboot.vo;

import cn.moonshotacademy.vendingmachineboot.model.DiscountDTO;
import lombok.Data;

@Data
public class DiscountVO {
    public DiscountVO(DiscountDTO d) {
        this.id = d.getId();
        this.scope = d.getScope();
        this.method = d.getMethod();
        this.val1 = d.getVal1();
        this.val2 = d.getVal2();
        this.type = d.getType();
    }

    private Integer id;
    
    private Integer scope;

    private Integer method;

    private Double val1;

    private Double val2;

    private Integer type;
}