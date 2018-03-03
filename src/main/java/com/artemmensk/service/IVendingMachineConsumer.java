package com.artemmensk.service;

import com.artemmensk.model.CoinType;

import java.util.Map;

public interface IVendingMachineConsumer {

    /**
     * Gets the price per item for a given product slot
     *
     * @param slotId product slot
     * @return price per item
     */
    Integer getPrice(Integer slotId);

    /**
     * Buys the product for a given product slot and returns change if needed.
     *
     * @param slotId product slot
     * @param coins collection of coins
     * @return collection of coins representing the change
     */
    Map<CoinType, Integer> buyProduct(Integer slotId, Map<CoinType, Integer> coins);
}
