package com.sapo.edu.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;


@Getter
@Setter
@Entity
@Table(name = "product")
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "product_id cannot be empty")
    private String product_id;
    @NotNull(message = "category_id cannot be empty")
    private Integer category_id;
    @NotNull(message = "warehouse_id cannot be empty")
    private Integer warehouse_id;
    @NotEmpty(message = "product_name cannot be empty")
    private String product_name;
    @NotNull(message = "price cannot be empty")
    private Long price;
    @NotEmpty(message = "product_description cannot be empty")
    private String product_description;
    @NotEmpty(message = "image_url cannot be empty")
    private String image_url;
    @NotNull(message = "quantity_of_product cannot be empty")
    private Integer quantity_of_product;
    @NotNull(message = "quantity_sold cannot be empty")
    private Integer quantity_sold;

    @Column(columnDefinition = "DATETIME")
    private Date created_date;

    @Column(columnDefinition = "DATETIME")
    private Date modified_date;
}
