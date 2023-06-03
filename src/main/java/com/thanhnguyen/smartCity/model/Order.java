package com.thanhnguyen.smartCity.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.annotation.processing.Generated;

@Entity
@Data
@Table(name = "Orders", //
        uniqueConstraints = { @UniqueConstraint(columnNames = "Order_Num") })
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int id;

    @Column(name = "Order_Num", nullable = false)
    private int orderNum;

    @Column(name = "Price", nullable = false)
    private int price;

    @Column(name = "Amount", nullable = false)
    private int amount;

    @Column(name = "Corrency", nullable = false)
    private String Currency;

    @Column(name = "Method", nullable = false)
    private String method;

    @Column(name = "Intent", nullable = false)
    private String intent;

    @Column(name = "Description", nullable = true)
    private String description;

    @Column(name = "Customer_Name", length = 255, nullable = false)
    private String customerName;

    @Column(name = "Customer_Address", length = 255, nullable = false)
    private String customerAddress;

    @Column(name = "Customer_Email", length = 128, nullable = false)
    private String customerEmail;

    @Column(name = "Customer_Phone", length = 128, nullable = false)
    private String customerPhone;
}
