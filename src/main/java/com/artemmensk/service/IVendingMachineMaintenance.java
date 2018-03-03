package com.artemmensk.service;

import com.artemmensk.model.CoinType;

public interface IVendingMachineMaintenance {

    /**
     * Gets the quantity of items for a given product slot.
     *
     * @param slotId given product slot
     * @return quantity of items
     */
    Integer getQuantity(Integer slotId);

    /**
     * Sets the quantity of items for a given product slot.
     *
     * @param quantity quantity of items
     * @param slotId product slot
     */
    void setQuantity(Integer quantity, Integer slotId);

    /**
     * Gets the price per item for a given product slot.
     *
     * @param slotId product slot
     * @return price per item
     */
    Integer getPrice(Integer slotId);

    /**
     * Sets the price per item for a given product slot.
     *
     * @param price price per item
     * @param slotId product slot
     */
    void setPrice(Integer price, Integer slotId);

    /**
     * Gets the amount of coins available for a given type of coin.
     *
     * @param type type of coin
     * @return amount of coins
     */
    Integer getAmount(CoinType type);

    /**
     * Sets the amount of coins available for a given type of coin.
     *
     * @param amount amount of coins
     * @param type type of coin
     */
    void setAmount(Integer amount, CoinType type);
}
