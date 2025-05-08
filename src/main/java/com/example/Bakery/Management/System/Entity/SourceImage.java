package com.example.Bakery.Management.System.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "sourceImage")
public class SourceImage extends AbstractEntity<Long> {

    @Column
    private String url;

    @Column
    private String name;

    @OneToOne(mappedBy = "image")
    private MenuItems menuItem;

}
