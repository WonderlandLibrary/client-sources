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
        } catch (SecurityException securityException) {
            LOGGER.debug("Cannot access bundle [{}] contents. Ignoring.", (Object)bundle.getSymbolicName(), (Object)securityException);
        } catch (Exception exception) {
            LOGGER.warn("Problem checking bundle {} for Log4j 2 provider.", (Object)bundle.getSymbolicName(), (Object)exception);
        }
    }

    private void loadProvider(BundleWiring bundleWiring) {
        List list = bundleWiring.findEntries("META-INF", "log4j-provider.properties", 0);
        for (URL uRL : list) {
            ProviderUtil.loadProvider(uRL, bundleWiring.getClassLoader());
        }
    }

    public void start(BundleContext bundleContext) throws Exception {
        ProviderUtil.STARTUP_LOCK.lock();
        this.lockingProviderUtil = true;
        BundleWiring bundleWiring = (BundleWiring)bundleContext.getBundle().adapt(BundleWiring.class);
        List list = bundleWiring.getRequiredWires(LoggerContextFactory.class.getName());
        Bundle[] bundleArray = list.iterator();
        while (bundleArray.hasNext()) {
            Bundle[] bundleArray2 = (Bundle[])bundleArray.next();
            this.loadProvider(bundleArray2.getProviderWiring());
        }
        bundleContext.addBundleListener((BundleListener)this);
        for (Bundle bundle : bundleArray = bundleContext.getBundles()) {
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

    public void stop(BundleContext bundleContext) throws Exception {
        bundleContext.removeBundleListener((BundleListener)this);
        this.unlockIfReady();
    }

    public void bundleChanged(BundleEvent bundleEvent) {
        switch (bundleEvent.getType()) {
            case 2: {
                this.loadProvider(bundleEvent.getBundle());
                this.unlockIfReady();
                break;
            }
        }
    }
}

