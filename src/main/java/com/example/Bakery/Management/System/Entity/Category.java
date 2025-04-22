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
@Table(name = "category")
public class Category extends AbstractEntity<Long> {
    @Column
    private String name;
}
