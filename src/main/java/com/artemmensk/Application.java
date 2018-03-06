package com.artemmensk;

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
        final Injector injector = Guice.createInjector(new ConfigModule());
        final IVendingMachineConsumer consumer = injector.getInstance(IVendingMachineConsumer.class);
        final IVendingMachineMaintenance maintenance = injector.getInstance(IVendingMachineMaintenance.class);
    }
}
