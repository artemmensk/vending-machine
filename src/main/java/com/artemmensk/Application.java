package com.artemmensk;

import com.artemmensk.model.CoinType;
import com.artemmensk.service.IVendingMachineConsumer;
import com.artemmensk.service.IVendingMachineMaintenance;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Main class of application.
 */
public class Application
{
    public static void main( String[] args ) {
        final Injector injector = Guice.createInjector(new Configuration());
        final IVendingMachineConsumer consumer = injector.getInstance(IVendingMachineConsumer.class);
        final IVendingMachineMaintenance maintenance = injector.getInstance(IVendingMachineMaintenance.class);

        consumer.getPrice(1);
        maintenance.setAmount(10, CoinType.HALF);
        maintenance.getAmount(CoinType.HALF);
        System.out.println(CoinType.HALF);
    }
}
