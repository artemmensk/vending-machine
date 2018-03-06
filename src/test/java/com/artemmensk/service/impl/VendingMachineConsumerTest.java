package com.artemmensk.service.impl;

import com.artemmensk.ConfigModule;
import com.artemmensk.model.CoinType;
import com.artemmensk.model.Money;
import com.artemmensk.model.Slot;
import com.artemmensk.service.IVendingMachineConsumer;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static com.artemmensk.ConfigModule.NUMBER_OF_SLOTS;

@Guice(modules = ConfigModule.class)
public class VendingMachineConsumerTest {

    private static final Integer SLOT_ID = 1;
    private static final Integer PRICE = 490;
    private static final Integer QUANTITY = 15;

    private final IVendingMachineConsumer consumer;
    private final Map<CoinType, Integer> coins;
    private final Map<Integer, Slot> slots;

    @Inject
    public VendingMachineConsumerTest(IVendingMachineConsumer consumer,
                                      @Named("coins") Map<CoinType, Integer> coins,
                                      @Named("slots") Map<Integer, Slot> slots) {
        this.consumer = consumer;
        this.coins = coins;
        this.slots = slots;
    }

    @BeforeMethod
    public void beforeEachTest() {
        Arrays.asList(CoinType.values()).forEach(type -> coins.put(type, 0));
        IntStream.range(1, NUMBER_OF_SLOTS + 1).forEach(id -> slots.put(id, new Slot()));
    }

    @Test
    public void buy() {
        // given
        final Money money = new Money(new HashMap<CoinType, Integer>(){{
            put(CoinType.ONE, 5);
        }});
        coins.put(CoinType.ONE_TENTH, 1);
        slots.get(SLOT_ID).setPrice(PRICE);
        slots.get(SLOT_ID).setQuantity(QUANTITY);

        // when
        final Money change = consumer.buyProduct(SLOT_ID, money);

        // then
        Assert.assertTrue(change.equals(new Money(new HashMap<CoinType, Integer>(){{put(CoinType.ONE_TENTH, 1);}})));
        Assert.assertEquals(new Money(coins), money);
        Assert.assertEquals(slots.get(SLOT_ID).getQuantity(), new Integer(QUANTITY - 1));
    }

    @Test
    public void buyNoChange() {
        // given
        final Money money = new Money(new HashMap<CoinType, Integer>(){{
            put(CoinType.ONE, 4);
            put(CoinType.HALF, 1);
            put(CoinType.ONE_FIFTH, 2);
        }});
        slots.get(SLOT_ID).setPrice(PRICE);
        slots.get(SLOT_ID).setQuantity(QUANTITY);

        // when
        final Money change = consumer.buyProduct(SLOT_ID, money);

        // then
        Assert.assertTrue(change.value() == 0);
        Assert.assertEquals(new Money(coins), money);
        Assert.assertEquals(slots.get(SLOT_ID).getQuantity(), new Integer(QUANTITY - 1));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void buyNonExistingSlot() {
        // given
        slots.clear();

        // when
        consumer.buyProduct(SLOT_ID, new Money(new HashMap<>()));

        // then
        // throws exception
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void buyNotEnoughItems() {
        // given
        final Money money = new Money(new HashMap<>());
        slots.get(SLOT_ID).setPrice(PRICE);


        // when
        consumer.buyProduct(SLOT_ID, money);

        // then
        // throws exception
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void buyNonEnoughMoney() {
        // given
        final Money money = new Money(new HashMap<CoinType, Integer>(){{
            put(CoinType.ONE, 3);
            put(CoinType.HALF, 3);
        }});
        slots.get(SLOT_ID).setPrice(PRICE);
        slots.get(SLOT_ID).setQuantity(QUANTITY);

        // when
        consumer.buyProduct(SLOT_ID, money);

        // then
        // throws exception
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void buyNotEnoughCoinsForChange() {
        // given
        final Money money = new Money(new HashMap<CoinType, Integer>() {{ put(CoinType.ONE, 5); }});
        slots.get(SLOT_ID).setQuantity(QUANTITY);
        slots.get(SLOT_ID).setPrice(PRICE);

        // when
        consumer.buyProduct(SLOT_ID, money);

        // then
        // throws exception
    }
}
