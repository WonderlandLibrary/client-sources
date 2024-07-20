/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.util.JndiCloser;

public class JndiManager
extends AbstractManager {
    private static final JndiManagerFactory FACTORY = new JndiManagerFactory();
    private final Context context;

    private JndiManager(String name, Context context) {
        super(null, name);
        this.context = context;
    }

    public static JndiManager getDefaultManager() {
        return JndiManager.getManager(JndiManager.class.getName(), FACTORY, null);
    }

    public static JndiManager getDefaultManager(String name) {
        return JndiManager.getManager(name, FACTORY, null);
    }

    public static JndiManager getJndiManager(String initialContextFactoryName, String providerURL, String urlPkgPrefixes, String securityPrincipal, String securityCredentials, Properties additionalProperties) {
        String name = JndiManager.class.getName() + '@' + JndiManager.class.hashCode();
        if (initialContextFactoryName == null) {
            return JndiManager.getManager(name, FACTORY, null);
        }
        Properties properties = new Properties();
        properties.setProperty("java.naming.factory.initial", initialContextFactoryName);
        if (providerURL != null) {
            properties.setProperty("java.naming.provider.url", providerURL);
        } else {
            LOGGER.warn("The JNDI InitialContextFactory class name [{}] was provided, but there was no associated provider URL. This is likely to cause problems.", (Object)initialContextFactoryName);
        }
        if (urlPkgPrefixes != null) {
            properties.setProperty("java.naming.factory.url.pkgs", urlPkgPrefixes);
        }
        if (securityPrincipal != null) {
            properties.setProperty("java.naming.security.principal", securityPrincipal);
            if (securityCredentials != null) {
                properties.setProperty("java.naming.security.credentials", securityCredentials);
            } else {
                LOGGER.warn("A security principal [{}] was provided, but with no corresponding security credentials.", (Object)securityPrincipal);
            }
        }
        if (additionalProperties != null) {
            properties.putAll(additionalProperties);
        }
        return JndiManager.getManager(name, FACTORY, properties);
    }

    @Override
    protected boolean releaseSub(long timeout, TimeUnit timeUnit) {
        return JndiCloser.closeSilently(this.context);
    }

    public <T> T lookup(String name) throws NamingException {
        return (T)this.context.lookup(name);
    }

    private static class JndiManagerFactory
    implements ManagerFactory<JndiManager, Properties> {
        private JndiManagerFactory() {
        }

        @Override
        public JndiManager createManager(String name, Properties data) {
            try {
                return new JndiManager(name, new InitialContext(data));
            } catch (NamingException e) {
                LOGGER.error("Error creating JNDI InitialContext.", (Throwable)e);
                return null;
            }
        }
    }
}

