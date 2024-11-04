package com.ecommerce.library.model;

import com.ecommerce.library.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class    Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    Long id;
    Date orderDate;
    Date deliveryDate;
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;
    double totalPrice;
    double tax;
    int quantity;
    String paymentMethod;
    boolean isAccept;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "custoner_id")
    Customer customer;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    List<OrderDetail> orderDetailsList;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", totalPrice=" + totalPrice +
                ", tax=" + tax +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", customer=" + customer.getUsername() +
                ", orderDetailsList=" + orderDetailsList.size() +
                '}';
    }
}
