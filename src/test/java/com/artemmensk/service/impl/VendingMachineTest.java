package com.artemmensk.service.impl;

import com.artemmensk.TestConfiguration;
import com.artemmensk.model.CoinType;
import com.artemmensk.service.IVendingMachineMaintenance;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@Guice(modules = TestConfiguration.class)
public class VendingMachineTest {

    final IVendingMachineMaintenance maintenance;
    final Map<CoinType, Integer> coins;

    @Inject
    public VendingMachineTest(IVendingMachineMaintenance maintenance,
                              @Named("coins") Map<CoinType, Integer> coins) {
        this.maintenance = maintenance;
        this.coins = coins;
    }

    @BeforeMethod
    public void beforeEachTest() {
        Arrays.asList(CoinType.values()).forEach(type -> coins.put(type, 0));
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
}
