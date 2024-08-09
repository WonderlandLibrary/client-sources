/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.ResourceLeakDetector;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.security.PrivilegedAction;

public abstract class ResourceLeakDetectorFactory {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ResourceLeakDetectorFactory.class);
    private static volatile ResourceLeakDetectorFactory factoryInstance = new DefaultResourceLeakDetectorFactory();

    public static ResourceLeakDetectorFactory instance() {
        return factoryInstance;
    }

    public static void setResourceLeakDetectorFactory(ResourceLeakDetectorFactory resourceLeakDetectorFactory) {
        factoryInstance = ObjectUtil.checkNotNull(resourceLeakDetectorFactory, "factory");
    }

    public final <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> clazz) {
        return this.newResourceLeakDetector(clazz, 128);
    }

    @Deprecated
    public abstract <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> var1, int var2, long var3);

    public <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> clazz, int n) {
        return this.newResourceLeakDetector(clazz, 128, Long.MAX_VALUE);
    }

    static InternalLogger access$000() {
        return logger;
    }

    private static final class DefaultResourceLeakDetectorFactory
    extends ResourceLeakDetectorFactory {
        private final Constructor<?> obsoleteCustomClassConstructor;
        private final Constructor<?> customClassConstructor;

        DefaultResourceLeakDetectorFactory() {
            String string;
            try {
                string = AccessController.doPrivileged(new PrivilegedAction<String>(this){
                    final DefaultResourceLeakDetectorFactory this$0;
                    {
                        this.this$0 = defaultResourceLeakDetectorFactory;
                    }

                    @Override
                    public String run() {
                        return SystemPropertyUtil.get("io.netty.customResourceLeakDetector");
                    }

                    @Override
                    public Object run() {
                        return this.run();
                    }
                });
            } catch (Throwable throwable) {
                ResourceLeakDetectorFactory.access$000().error("Could not access System property: io.netty.customResourceLeakDetector", throwable);
                string = null;
            }
            if (string == null) {
                this.customClassConstructor = null;
                this.obsoleteCustomClassConstructor = null;
            } else {
                this.obsoleteCustomClassConstructor = DefaultResourceLeakDetectorFactory.obsoleteCustomClassConstructor(string);
                this.customClassConstructor = DefaultResourceLeakDetectorFactory.customClassConstructor(string);
            }
        }

        private static Constructor<?> obsoleteCustomClassConstructor(String string) {
            try {
                Class<?> clazz = Class.forName(string, true, PlatformDependent.getSystemClassLoader());
                if (ResourceLeakDetector.class.isAssignableFrom(clazz)) {
                    return clazz.getConstructor(Class.class, Integer.TYPE, Long.TYPE);
                }
                ResourceLeakDetectorFactory.access$000().error("Class {} does not inherit from ResourceLeakDetector.", (Object)string);
            } catch (Throwable throwable) {
                ResourceLeakDetectorFactory.access$000().error("Could not load custom resource leak detector class provided: {}", (Object)string, (Object)throwable);
            }
            return null;
        }

        private static Constructor<?> customClassConstructor(String string) {
            try {
                Class<?> clazz = Class.forName(string, true, PlatformDependent.getSystemClassLoader());
                if (ResourceLeakDetector.class.isAssignableFrom(clazz)) {
                    return clazz.getConstructor(Class.class, Integer.TYPE);
                }
                ResourceLeakDetectorFactory.access$000().error("Class {} does not inherit from ResourceLeakDetector.", (Object)string);
            } catch (Throwable throwable) {
                ResourceLeakDetectorFactory.access$000().error("Could not load custom resource leak detector class provided: {}", (Object)string, (Object)throwable);
            }
            return null;
        }

        @Override
        public <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> clazz, int n, long l) {
            if (this.obsoleteCustomClassConstructor != null) {
                try {
                    ResourceLeakDetector resourceLeakDetector = (ResourceLeakDetector)this.obsoleteCustomClassConstructor.newInstance(clazz, n, l);
                    ResourceLeakDetectorFactory.access$000().debug("Loaded custom ResourceLeakDetector: {}", (Object)this.obsoleteCustomClassConstructor.getDeclaringClass().getName());
                    return resourceLeakDetector;
                } catch (Throwable throwable) {
                    ResourceLeakDetectorFactory.access$000().error("Could not load custom resource leak detector provided: {} with the given resource: {}", this.obsoleteCustomClassConstructor.getDeclaringClass().getName(), clazz, throwable);
                }
            }
            ResourceLeakDetector resourceLeakDetector = new ResourceLeakDetector(clazz, n, l);
            ResourceLeakDetectorFactory.access$000().debug("Loaded default ResourceLeakDetector: {}", (Object)resourceLeakDetector);
            return resourceLeakDetector;
        }

        @Override
        public <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> clazz, int n) {
            if (this.customClassConstructor != null) {
                try {
                    ResourceLeakDetector resourceLeakDetector = (ResourceLeakDetector)this.customClassConstructor.newInstance(clazz, n);
                    ResourceLeakDetectorFactory.access$000().debug("Loaded custom ResourceLeakDetector: {}", (Object)this.customClassConstructor.getDeclaringClass().getName());
                    return resourceLeakDetector;
                } catch (Throwable throwable) {
                    ResourceLeakDetectorFactory.access$000().error("Could not load custom resource leak detector provided: {} with the given resource: {}", this.customClassConstructor.getDeclaringClass().getName(), clazz, throwable);
                }
            }
            ResourceLeakDetector resourceLeakDetector = new ResourceLeakDetector(clazz, n);
            ResourceLeakDetectorFactory.access$000().debug("Loaded default ResourceLeakDetector: {}", (Object)resourceLeakDetector);
            return resourceLeakDetector;
        }
    }
}

