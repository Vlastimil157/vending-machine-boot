package cn.moonshotacademy.vendingmachineboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
@Table(name = "discounts")
public class DiscountDTO {
    @Id
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "scope")
    private Integer scope;

    @Column(name = "method")
    private Integer method;

    @Column(name = "val_1")
    private Double val1;

    @Column(name = "val_2")
    private Double val2;

    @Column(name = "type")
    private Integer type;
}