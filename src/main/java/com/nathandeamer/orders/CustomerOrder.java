package com.nathandeamer.orders;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOrder {

    private @Id Integer id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)//
    private List<Item> items;

    @Entity
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Item {

        private @Id @GeneratedValue Integer id;
        private int qty;
        private String sku;
        private String description;
    }

}