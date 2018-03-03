package com.artemmensk.service.impl;

import com.artemmensk.exception.ErrorMessage;
import com.artemmensk.model.CoinType;
import com.artemmensk.service.IVendingMachineConsumer;
import com.artemmensk.service.IVendingMachineMaintenance;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.Map;
import java.util.Set;

public class VendingMachine implements IVendingMachineMaintenance, IVendingMachineConsumer {

    private final Integer slotNumber;
    private final Map<CoinType, Integer> coins;

    @Inject
    public VendingMachine(@Named("numberOfSlots") Integer numberOfSlots,
                          @Named("coins") Map<CoinType, Integer> coins) {
        this.slotNumber = numberOfSlots;
        this.coins = coins;
    }

    @Override
    public Map<CoinType, Integer> buyProduct(Integer slotId, Map<CoinType, Integer> coins) {
        return null;
    }

    @Override
    public Integer getQuantity(Integer slotId) {
        return null;
    }

    @Override
    public void setQuantity(Integer quantity, Integer slotId) {

    }

    @Override
    public Integer getPrice(Integer slotId) {
        return null;
    }

    @Override
    public void setPrice(Integer price, Integer slotId) {

    }

    @Override
    public Integer getAmount(CoinType type) {
        if (!coins.containsKey(type)) {
            throw new IllegalArgumentException(String.format(ErrorMessage.COIN_TYPE_NOT_USED.getMessage(), type));
        }

        return coins.get(type);
    }

    @Override
    public void setAmount(Integer amount, CoinType type) {

    }
}
