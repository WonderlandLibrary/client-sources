/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.osgi.framework.AdaptPermission
 *  org.osgi.framework.AdminPermission
 *  org.osgi.framework.Bundle
 *  org.osgi.framework.BundleActivator
 *  org.osgi.framework.BundleContext
 *  org.osgi.framework.BundleEvent
 *  org.osgi.framework.BundleListener
 *  org.osgi.framework.SynchronousBundleListener
 *  org.osgi.framework.wiring.BundleWire
 *  org.osgi.framework.wiring.BundleWiring
 */
package org.apache.logging.log4j.util;

import java.net.URL;
import java.security.Permission;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.ProviderUtil;
import org.osgi.framework.AdaptPermission;
import org.osgi.framework.AdminPermission;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.SynchronousBundleListener;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

public class Activator
implements BundleActivator,
SynchronousBundleListener {
    private static final SecurityManager SECURITY_MANAGER = System.getSecurityManager();
    private static final Logger LOGGER = StatusLogger.getLogger();
    private boolean lockingProviderUtil;

    private static void checkPermission(Permission permission) {
        if (SECURITY_MANAGER != null) {
            SECURITY_MANAGER.checkPermission(permission);
        }
    }

    private void loadProvider(Bundle bundle) {
        if (bundle.getState() == 1) {
            return;
        }
        try {
            Activator.checkPermission((Permission)new AdminPermission(bundle, "resource"));
            Activator.checkPermission((Permission)new AdaptPermission(BundleWiring.class.getName(), bundle, "adapt"));
            this.loadProvider((BundleWiring)bundle.adapt(BundleWiring.class));
        } catch (SecurityException e) {
            LOGGER.debug("Cannot access bundle [{}] contents. Ignoring.", (Object)bundle.getSymbolicName(), (Object)e);
        } catch (Exception e) {
            LOGGER.warn("Problem checking bundle {} for Log4j 2 provider.", (Object)bundle.getSymbolicName(), (Object)e);
        }
    }

    private void loadProvider(BundleWiring provider) {
        List urls = provider.findEntries("META-INF", "log4j-provider.properties", 0);
        for (URL url : urls) {
            ProviderUtil.loadProvider(url, provider.getClassLoader());
        }
    }

    public void start(BundleContext context) throws Exception {
        Bundle[] bundles;
        ProviderUtil.STARTUP_LOCK.lock();
        this.lockingProviderUtil = true;
        BundleWiring self = (BundleWiring)context.getBundle().adapt(BundleWiring.class);
        List required = self.getRequiredWires(LoggerContextFactory.class.getName());
        for (BundleWire wire : required) {
            this.loadProvider(wire.getProviderWiring());
        }
        context.addBundleListener((BundleListener)this);
        for (Bundle bundle : bundles = context.getBundles()) {
            this.loadProvider(bundle);
        }
        this.unlockIfReady();
    }

    private void unlockIfReady() {
        if (this.lockingProviderUtil && !ProviderUtil.PROVIDERS.isEmpty()) {
            ProviderUtil.STARTUP_LOCK.unlock();
            this.lockingProviderUtil = false;
        }
    }

    public void stop(BundleContext context) throws Exception {
        context.removeBundleListener((BundleListener)this);
        this.unlockIfReady();
    }

    public void bundleChanged(BundleEvent event) {
        switch (event.getType()) {
            case 2: {
                this.loadProvider(event.getBundle());
                this.unlockIfReady();
                break;
            }
        }
    }
}

