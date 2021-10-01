package com.example.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class MyOrder {
    @Id
    @GeneratedValue
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateCreated;

    private String status;


    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderProducts;


    @Transient
    public int getNumberOfProducts() {
        return this.orderProducts.size();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderItem> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
