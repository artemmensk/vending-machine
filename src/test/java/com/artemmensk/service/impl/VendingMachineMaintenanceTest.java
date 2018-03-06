package com.artemmensk.service.impl;

import com.artemmensk.ConfigModule;
import com.artemmensk.model.CoinType;
import com.artemmensk.model.Slot;
import com.artemmensk.service.IVendingMachineMaintenance;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.IntStream;

import static com.artemmensk.ConfigModule.NUMBER_OF_SLOTS;

@Guice(modules = ConfigModule.class)
public class VendingMachineMaintenanceTest {

    private static final Integer SLOT_ID = 1;
    private static final Integer PRICE = 500;
    private static final Integer QUANTITY = 15;

    private final IVendingMachineMaintenance maintenance;
    private final Map<CoinType, Integer> coins;
    private final Map<Integer, Slot> slots;

    @Inject
    public VendingMachineMaintenanceTest(IVendingMachineMaintenance maintenance,
                                         @Named("coins") Map<CoinType, Integer> coins,
                                         @Named("slots") Map<Integer, Slot> slots) {
        this.maintenance = maintenance;
        this.coins = coins;
        this.slots = slots;
    }

    @BeforeMethod
    public void beforeEachTest() {
        Arrays.asList(CoinType.values()).forEach(type -> coins.put(type, 0));
        IntStream.range(1, NUMBER_OF_SLOTS + 1).forEach(id -> slots.put(id, new Slot()));
    }

    @Test
    public void getQuantity() {
        // given
        slots.get(SLOT_ID).setQuantity(QUANTITY);

        // when
        final Integer quantity = maintenance.getQuantity(SLOT_ID);

        // then
        Assert.assertEquals(quantity, QUANTITY);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getQuantityNonExistingSlot() {
        // given
        slots.clear();

        // when
        maintenance.getQuantity(SLOT_ID);

        // then
        // throws exception
    }

    @Test
    public void setQuantity() {
        // when
        maintenance.setQuantity(QUANTITY, SLOT_ID);

        // then
        Assert.assertEquals(slots.get(SLOT_ID).getQuantity(), QUANTITY);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setQuantityNonExistingSlot() {
        // given
        slots.clear();

        // when
        maintenance.setQuantity(QUANTITY, SLOT_ID);

        // then
        // throws exception
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setNegativeQuantity() {
        // when
        maintenance.setQuantity(-1, SLOT_ID);

        // then
        // throws exception
    }

    @Test
    public void getPrice() {
        // given
        slots.get(SLOT_ID).setPrice(PRICE);

        // when
        final Integer price = maintenance.getPrice(SLOT_ID);

        // then
        Assert.assertEquals(price, PRICE);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getPriceNonExistingSlot() {
        // given
        slots.clear();

        // when
        maintenance.getPrice(SLOT_ID);

        // then
        // throws exception
    }

    @Test
    public void setPrice() {
        // when
        maintenance.setPrice(PRICE, SLOT_ID);

        // then
        Assert.assertEquals(slots.get(SLOT_ID).getPrice(), PRICE);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setPriceNonExistingSlot() {
        // given
        slots.clear();

        // when
        maintenance.setPrice(PRICE, SLOT_ID);

        // then
        // throws exception
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setTooSmallPrice() {
        // when
        maintenance.setPrice(5, SLOT_ID);

        // then
        // throws exception
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setNotMultiplicityPrice() {
        // when
        maintenance.setPrice(PRICE + 2, SLOT_ID);

        // then
        // throws exception
    }

    @Test
    public void getAmount() {
        // given
        coins.put(CoinType.HALF, 2);

        // when
        final Integer amount = maintenance.getAmount(CoinType.HALF);

        // then
        Assert.assertEquals(amount, new Integer(2));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getAmountIllegalCoinType() {
        // given
        coins.clear();

        // when
        maintenance.getAmount(CoinType.HALF);

        // then
        // throws exception
    }

    @Test
    public void setAmount() {
        // when
        maintenance.setAmount(2, CoinType.ONE_TENTH);

        // then
        Assert.assertEquals(coins.get(CoinType.ONE_TENTH), new Integer(2));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setAmountIllegalCoinType() {
        // given
        coins.clear();

        // when
        maintenance.setAmount(2, CoinType.HALF);

        // then
        // throws exception
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setNegativeAmount() {
        // when
        maintenance.setAmount(-2, CoinType.HALF);

        // then
        // throws exception
    }
}
