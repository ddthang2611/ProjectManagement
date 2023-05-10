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
@Table(name = "category")
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "category_id cannot be null")
    private String category_id;

    @NotEmpty(message = "category_name cannot be null")
    private String category_name;

    private String category_description;


    @Column(columnDefinition = "DATETIME")
    private Date created_date;

    @Column(columnDefinition = "DATETIME")
    private Date modified_date;
}
