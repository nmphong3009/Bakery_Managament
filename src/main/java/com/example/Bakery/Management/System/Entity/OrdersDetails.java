package com.example.Bakery.Management.System.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "orderDetails")
public class OrdersDetails extends AbstractEntity<Long>{
    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItems menuItems;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @Column
    private Integer quantity;
}
