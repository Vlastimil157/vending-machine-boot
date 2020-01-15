package cn.moonshotacademy.vendingmachineboot.vo;

import cn.moonshotacademy.vendingmachineboot.model.ProductDTO;

import lombok.Data;

@Data
public class ProductVO {
    public ProductVO() {}

    public ProductVO(ProductDTO productDTO) {
        this.id = productDTO.getId();
        this.name = productDTO.getName();
        this.price = productDTO.getPrice();
        this.discountId = productDTO.getDiscountId();
        this.count = productDTO.getCount();
        this.img = productDTO.getImg();
        this.description = productDTO.getDescription();
        this.select = 0;
        this.discountSelect = false;
        this.style = "color: rgb(155, 155, 155)";
        this.discountText = "";
        this.calc = 0d;
    }

    private Integer id;

    private String name;

    private Double price;

    private Integer discountId;

    private Integer count;
    
    private String img;

    private String description;

    private Integer select;

    private Boolean discountSelect;

    private String style;

    private String discountText;

    private Double calc;
}