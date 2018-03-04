package com.artemmensk.model;

import lombok.*;

@ToString
@Getter
@Setter
public class Slot {

    /**
     * Quantity of item.
     */
    private Integer quantity = 0;

    /**
     * Price in penny.
     */
    private Integer price = 0;
}
