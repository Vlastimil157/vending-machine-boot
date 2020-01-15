package cn.moonshotacademy.vendingmachineboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class ProductDTO {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "discount_id")
    private Integer discountId;

    @Column(name = "count")
    private Integer count;

    @Column(name = "img")
    private String img;

    @Column(name = "description")
    private String description;
}