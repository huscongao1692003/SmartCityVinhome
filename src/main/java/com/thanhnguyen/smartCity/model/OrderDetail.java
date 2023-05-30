package com.thanhnguyen.smartCity.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Order_Details")
public class OrderDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID", nullable = false, //
            foreignKey = @ForeignKey(name = "ORDER_DETAIL_ORD_FK"))
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false, //
            foreignKey = @ForeignKey(name = "ORDER_DETAIL_PROD_FK"))
    private Product product;

    @Column(name = "Quanity", nullable = false)
    private int quanity;

    @Column(name = "Price", nullable = false)
    private int price;

    @Column(name = "Amount", nullable = false)
    private int amount;

}
