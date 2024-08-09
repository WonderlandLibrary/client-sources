/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.patchy;

import com.mojang.patchy.BlockedServers;
import com.mojang.patchy.BlockingICF;
import java.util.Hashtable;
import java.util.function.Predicate;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;

public class BlockingICFB
implements InitialContextFactoryBuilder {
    private final Predicate<String> blockList;

    public BlockingICFB(Predicate<String> predicate) {
        this.blockList = predicate;
    }

    public static void install() {
        try {
            System.getProperties().setProperty("sun.net.spi.nameservice.provider.1", "dns,sun");
            NamingManager.setInitialContextFactoryBuilder(new BlockingICFB(BlockedServers::isBlockedServer));
        } catch (Throwable throwable) {
            System.out.println("Block failed :(");
            throwable.printStackTrace();
        }
    }

    @Override
    public InitialContextFactory createInitialContextFactory(Hashtable<?, ?> hashtable) throws NamingException {
        String string = (String)hashtable.get("java.naming.factory.initial");
        try {
            InitialContextFactory initialContextFactory = (InitialContextFactory)Class.forName(string).newInstance();
            if ("com.sun.jndi.dns.DnsContextFactory".equals(string)) {
                return new BlockingICF(this.blockList, initialContextFactory);
            }
            return initialContextFactory;
        } catch (Exception exception) {
            NoInitialContextException noInitialContextException = new NoInitialContextException("Cannot instantiate class: " + string);
            noInitialContextException.setRootCause(exception);
            throw noInitialContextException;
        }
    }
}

