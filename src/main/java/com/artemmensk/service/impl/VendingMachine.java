package com.artemmensk.service.impl;

import com.artemmensk.ConfigModule;
import com.artemmensk.exception.ErrorMessage;
import com.artemmensk.model.CoinType;
import com.artemmensk.model.Money;
import com.artemmensk.model.Slot;
import com.artemmensk.service.IVendingMachineConsumer;
import com.artemmensk.service.IVendingMachineMaintenance;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.Map;

public class VendingMachine implements IVendingMachineMaintenance, IVendingMachineConsumer {

    private final Map<CoinType, Integer> coins;
    private final Map<Integer, Slot> slots;

    @Inject
    public VendingMachine(@Named("coins") Map<CoinType, Integer> coins,
                          @Named("slots") Map<Integer, Slot> slots) {
        this.coins = coins;
        this.slots = slots;
    }

    @Override
    public Money buyProduct(Integer slotId, Money customerMoney) {
        if (!slots.containsKey(slotId)) {
            throw new IllegalArgumentException(String.format(ErrorMessage.SLOT_NOT_FOUND.getMessage(), slotId));
        }

        final Slot slot = slots.get(slotId);

        synchronized (slot) {

            if (slot.getQuantity() < 1) {
                throw new IllegalStateException(String.format(ErrorMessage.NOT_ENOUGH_ITEMS.getMessage(), slotId));
            }

            final Integer price = slot.getPrice();
            final Integer sum = customerMoney.value();

            if (price > sum) {
                throw new IllegalArgumentException(String.format(ErrorMessage.NOT_ENOUGH_MONEY.getMessage(), sum));
            }

            final Integer rest = sum - price;

            synchronized (this.coins) {

                final Money beforeTransaction = new Money(this.coins);
                final Money beforeTransactionPlusCustomerMoney = beforeTransaction.add(customerMoney);
                final Money change = beforeTransactionPlusCustomerMoney.computeChange(rest);
                final Money afterTransaction = beforeTransactionPlusCustomerMoney.subtract(change);

                coins.putAll(afterTransaction.getCoins());
                slot.setQuantity(slot.getQuantity() - 1);

                return change;
            }
        }
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
        if (price < ConfigModule.MINIMAL_PRICE) {
            throw new IllegalArgumentException(String.format(ErrorMessage.PRICE_TOO_SMALL.getMessage(), price));
        }

        if (price % ConfigModule.MINIMAL_PRICE != 0) {
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
