package com.artemmensk.service.impl;

import com.artemmensk.Configuration;
import com.artemmensk.exception.ErrorMessage;
import com.artemmensk.model.CoinType;
import com.artemmensk.model.Slot;
import com.artemmensk.service.IVendingMachineConsumer;
import com.artemmensk.service.IVendingMachineMaintenance;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.Map;

public class VendingMachine implements IVendingMachineMaintenance, IVendingMachineConsumer {

    private final Integer slotNumber;
    private final Map<CoinType, Integer> coins;
    private final Map<Integer, Slot> slots;

    @Inject
    public VendingMachine(@Named("numberOfSlots") Integer numberOfSlots,
                          @Named("coins") Map<CoinType, Integer> coins,
                          @Named("slots") Map<Integer, Slot> slots) {
        this.slotNumber = numberOfSlots;
        this.coins = coins;
        this.slots = slots;
    }

    @Override
    public Map<CoinType, Integer> buyProduct(Integer slotId, Map<CoinType, Integer> coins) {
        return null;
    }

    @Override
    public Integer getQuantity(Integer slotId) {
        if (!slots.containsKey(slotId)) {
            throw new IllegalArgumentException(String.format(ErrorMessage.SLOT_NOT_FOUND.getMessage(), slotId));
        }

        return slots.get(slotId).getQuantity();
    }

    @Override
    public void setQuantity(Integer quantity, Integer slotId) {
        if (quantity < 0) {
            throw new IllegalArgumentException(String.format(ErrorMessage.NEGATIVE_QUANTITY.getMessage(), quantity));
        }

        if (!slots.containsKey(slotId)) {
            throw new IllegalArgumentException(String.format(ErrorMessage.SLOT_NOT_FOUND.getMessage(), slotId));
        }

        slots.get(slotId).setQuantity(quantity);
    }

    @Override
    public Integer getPrice(Integer slotId) {
        if (!slots.containsKey(slotId)) {
            throw new IllegalArgumentException(String.format(ErrorMessage.SLOT_NOT_FOUND.getMessage(), slotId));
        }

        return slots.get(slotId).getPrice();
    }

    @Override
    public void setPrice(Integer price, Integer slotId) {
        if (price < Configuration.MINIMAL_PRICE) {
            throw new IllegalArgumentException(String.format(ErrorMessage.PRICE_TOO_SMALL.getMessage(), price));
        }

        if (price % Configuration.MINIMAL_PRICE != 0) {
            throw new IllegalArgumentException(String.format(ErrorMessage.NOT_MULTIPLICITY_PRICE.getMessage(), price));
        }

        if (!slots.containsKey(slotId)) {
            throw new IllegalArgumentException(String.format(ErrorMessage.SLOT_NOT_FOUND.getMessage(), slotId));
        }

        slots.get(slotId).setPrice(price);
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
        if (amount < 0) {
            throw new IllegalArgumentException(String.format(ErrorMessage.NEGATIVE_AMOUNT.getMessage(), amount));
        }

        if (!coins.containsKey(type)) {
            throw new IllegalArgumentException(String.format(ErrorMessage.COIN_TYPE_NOT_USED.getMessage(), type));
        }

        coins.put(type, amount);
    }
}
