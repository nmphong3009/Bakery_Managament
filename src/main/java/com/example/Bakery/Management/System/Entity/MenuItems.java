package com.example.Bakery.Management.System.Entity;

import com.example.Bakery.Management.System.Enum.Category;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "menuItems")
public class MenuItems extends AbstractEntity<Long>{
    @Column
    private String name;

    @Column
    private String description;

    @Column
    private BigDecimal price;

    @Column
    private BigDecimal priceCost;

    @Enumerated(EnumType.STRING)
    private Category category;
}
