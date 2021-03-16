package com.nathandeamer.orders;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
class OrderEvent {
    private int id;
    private String type;
}
