package com.sid.batch.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int productid;
    private String title;
    private String description;
    private String price;
    private String discount;
    private String discountedPrice;
}
