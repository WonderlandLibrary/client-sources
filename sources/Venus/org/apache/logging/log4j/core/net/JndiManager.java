/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.util.JndiCloser;

public class JndiManager
extends AbstractManager {
    private static final JndiManagerFactory FACTORY = new JndiManagerFactory(null);
    private final Context context;

    private JndiManager(String string, Context context) {
        super(null, string);
        this.context = context;
    }

    public static JndiManager getDefaultManager() {
        return JndiManager.getManager(JndiManager.class.getName(), FACTORY, null);
    }

    public static JndiManager getDefaultManager(String string) {
        return JndiManager.getManager(string, FACTORY, null);
    }

    public static JndiManager getJndiManager(String string, String string2, String string3, String string4, String string5, Properties properties) {
        String string6 = JndiManager.class.getName() + '@' + JndiManager.class.hashCode();
        if (string == null) {
            return JndiManager.getManager(string6, FACTORY, null);
        }
        Properties properties2 = new Properties();
        properties2.setProperty("java.naming.factory.initial", string);
        if (string2 != null) {
            properties2.setProperty("java.naming.provider.url", string2);
        } else {
            LOGGER.warn("The JNDI InitialContextFactory class name [{}] was provided, but there was no associated provider URL. This is likely to cause problems.", (Object)string);
        }
        if (string3 != null) {
            properties2.setProperty("java.naming.factory.url.pkgs", string3);
        }
        if (string4 != null) {
            properties2.setProperty("java.naming.security.principal", string4);
            if (string5 != null) {
                properties2.setProperty("java.naming.security.credentials", string5);
            } else {
                LOGGER.warn("A security principal [{}] was provided, but with no corresponding security credentials.", (Object)string4);
            }
        }
        if (properties != null) {
            properties2.putAll(properties);
        }
        return JndiManager.getManager(string6, FACTORY, properties2);
    }

    @Override
    protected boolean releaseSub(long l, TimeUnit timeUnit) {
        return JndiCloser.closeSilently(this.context);
    }

    public <T> T lookup(String string) throws NamingException {
        return (T)this.context.lookup(string);
    }

    JndiManager(String string, Context context, 1 var3_3) {
        this(string, context);
    }

    static Logger access$200() {
        return LOGGER;
    }

    static class 1 {
    }

    private static class JndiManagerFactory
    implements ManagerFactory<JndiManager, Properties> {
        private JndiManagerFactory() {
        }

        @Override
        public JndiManager createManager(String string, Properties properties) {
            try {
                return new JndiManager(string, new InitialContext(properties), null);
            } catch (NamingException namingException) {
                JndiManager.access$200().error("Error creating JNDI InitialContext.", (Throwable)namingException);
                return null;
            }
        }

        @Override
        public Object createManager(String string, Object object) {
            return this.createManager(string, (Properties)object);
        }

        JndiManagerFactory(1 var1_1) {
            this();
        }
    }
}

