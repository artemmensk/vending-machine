package com.artemmensk;

import com.artemmensk.model.CoinType;
import com.artemmensk.service.IVendingMachineConsumer;
import com.artemmensk.service.IVendingMachineMaintenance;
import com.artemmensk.service.impl.VendingMachine;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

import java.util.*;

public class ProductionConfiguration extends AbstractModule {

    private static final Integer NUMBER_OF_SLOTS = 10;
    private static final Map<CoinType, Integer> COINS = new HashMap<>();

    static {

        Arrays.asList(CoinType.values()).forEach(type -> COINS.put(type, 0));

        COINS.put(CoinType.ONE, 3);
        COINS.put(CoinType.HALF, 5);
        COINS.put(CoinType.ONE_FIFTH, 10);
        COINS.put(CoinType.ONE_TENTH, 20);
    }

    @Override
    protected void configure() {
        bind(Integer.class).annotatedWith(Names.named("numberOfSlots")).toInstance(NUMBER_OF_SLOTS);
        bind(new TypeLiteral<Map<CoinType, Integer>>(){}).annotatedWith(Names.named("coins")).toInstance(COINS);
        bind(IVendingMachineMaintenance.class).to(VendingMachine.class);
        bind(IVendingMachineConsumer.class).to(VendingMachine.class);
    }
}
