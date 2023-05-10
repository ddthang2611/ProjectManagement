package com.sapo.edu.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "warehouse")
@ToString
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "warehouse_id cannot be null")
    private String warehouse_id;

    @NotNull(message = "warehouse_name cannot be null")
    private String warehouse_name;

    @NotNull(message = "warehouse_address cannot be null")
    private String warehouse_address;


    @Column(columnDefinition = "DATETIME")
    private Date created_date;

    @Column(columnDefinition = "DATETIME")
    private Date modified_date;
}
