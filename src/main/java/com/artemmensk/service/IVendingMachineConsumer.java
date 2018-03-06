package com.artemmensk.service;

import com.artemmensk.model.Money;

public interface IVendingMachineConsumer {

    /**
     * Gets the price per item for a given product slot
     *
     * @param slotId product slot
     * @return price per item
     */
    Integer getPrice(Integer slotId);

    /**
     * Buys the product for a given product slot and returns computeChange if needed.
     *
     * @param slotId product slot
     * @param money wrapped collection of money
     * @return wrapped collection of money representing the computeChange
     */
    Money buyProduct(Integer slotId, Money money);
}
