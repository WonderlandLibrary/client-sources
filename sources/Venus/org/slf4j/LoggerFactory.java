/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.event.SubstituteLoggingEvent;
import org.slf4j.helpers.NOP_FallbackServiceProvider;
import org.slf4j.helpers.SubstituteLogger;
import org.slf4j.helpers.SubstituteServiceProvider;
import org.slf4j.helpers.Util;
import org.slf4j.spi.SLF4JServiceProvider;

public final class LoggerFactory {
    static final String CODES_PREFIX = "https://www.slf4j.org/codes.html";
    static final String NO_PROVIDERS_URL = "https://www.slf4j.org/codes.html#noProviders";
    static final String IGNORED_BINDINGS_URL = "https://www.slf4j.org/codes.html#ignoredBindings";
    static final String MULTIPLE_BINDINGS_URL = "https://www.slf4j.org/codes.html#multiple_bindings";
    static final String VERSION_MISMATCH = "https://www.slf4j.org/codes.html#version_mismatch";
    static final String SUBSTITUTE_LOGGER_URL = "https://www.slf4j.org/codes.html#substituteLogger";
    static final String LOGGER_NAME_MISMATCH_URL = "https://www.slf4j.org/codes.html#loggerNameMismatch";
    static final String REPLAY_URL = "https://www.slf4j.org/codes.html#replay";
    static final String UNSUCCESSFUL_INIT_URL = "https://www.slf4j.org/codes.html#unsuccessfulInit";
    static final String UNSUCCESSFUL_INIT_MSG = "org.slf4j.LoggerFactory in failed state. Original exception was thrown EARLIER. See also https://www.slf4j.org/codes.html#unsuccessfulInit";
    public static final String PROVIDER_PROPERTY_KEY = "slf4j.provider";
    static final int UNINITIALIZED = 0;
    static final int ONGOING_INITIALIZATION = 1;
    static final int FAILED_INITIALIZATION = 2;
    static final int SUCCESSFUL_INITIALIZATION = 3;
    static final int NOP_FALLBACK_INITIALIZATION = 4;
    static volatile int INITIALIZATION_STATE = 0;
    static final SubstituteServiceProvider SUBST_PROVIDER = new SubstituteServiceProvider();
    static final NOP_FallbackServiceProvider NOP_FALLBACK_SERVICE_PROVIDER = new NOP_FallbackServiceProvider();
    static final String DETECT_LOGGER_NAME_MISMATCH_PROPERTY = "slf4j.detectLoggerNameMismatch";
    static final String JAVA_VENDOR_PROPERTY = "java.vendor.url";
    static boolean DETECT_LOGGER_NAME_MISMATCH = Util.safeGetBooleanSystemProperty("slf4j.detectLoggerNameMismatch");
    static volatile SLF4JServiceProvider PROVIDER;
    private static final String[] API_COMPATIBILITY_LIST;
    private static final String STATIC_LOGGER_BINDER_PATH = "org/slf4j/impl/StaticLoggerBinder.class";

    static List<SLF4JServiceProvider> findServiceProviders() {
        ArrayList<SLF4JServiceProvider> arrayList = new ArrayList<SLF4JServiceProvider>();
        ClassLoader classLoader = LoggerFactory.class.getClassLoader();
        SLF4JServiceProvider sLF4JServiceProvider = LoggerFactory.loadExplicitlySpecified(classLoader);
        if (sLF4JServiceProvider != null) {
            arrayList.add(sLF4JServiceProvider);
            return arrayList;
        }
        ServiceLoader<SLF4JServiceProvider> serviceLoader = LoggerFactory.getServiceLoader(classLoader);
        Iterator<SLF4JServiceProvider> iterator2 = serviceLoader.iterator();
        while (iterator2.hasNext()) {
            LoggerFactory.safelyInstantiate(arrayList, iterator2);
        }
        return arrayList;
    }

    private static ServiceLoader<SLF4JServiceProvider> getServiceLoader(ClassLoader classLoader) {
        ServiceLoader serviceLoader;
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager == null) {
            serviceLoader = ServiceLoader.load(SLF4JServiceProvider.class, classLoader);
        } else {
            PrivilegedAction<ServiceLoader> privilegedAction = () -> LoggerFactory.lambda$getServiceLoader$0(classLoader);
            serviceLoader = AccessController.doPrivileged(privilegedAction);
        }
        return serviceLoader;
    }

    private static void safelyInstantiate(List<SLF4JServiceProvider> list, Iterator<SLF4JServiceProvider> iterator2) {
        try {
            SLF4JServiceProvider sLF4JServiceProvider = iterator2.next();
            list.add(sLF4JServiceProvider);
        } catch (ServiceConfigurationError serviceConfigurationError) {
            Util.report("A SLF4J service provider failed to instantiate:\n" + serviceConfigurationError.getMessage());
        }
    }

    private LoggerFactory() {
    }

    static void reset() {
        INITIALIZATION_STATE = 0;
    }

    private static final void performInitialization() {
        LoggerFactory.bind();
        if (INITIALIZATION_STATE == 3) {
            LoggerFactory.versionSanityCheck();
        }
    }

    private static final void bind() {
        try {
            List<SLF4JServiceProvider> list = LoggerFactory.findServiceProviders();
            LoggerFactory.reportMultipleBindingAmbiguity(list);
            if (list != null && !list.isEmpty()) {
                PROVIDER = list.get(0);
                PROVIDER.initialize();
                INITIALIZATION_STATE = 3;
                LoggerFactory.reportActualBinding(list);
            } else {
                INITIALIZATION_STATE = 4;
                Util.report("No SLF4J providers were found.");
                Util.report("Defaulting to no-operation (NOP) logger implementation");
                Util.report("See https://www.slf4j.org/codes.html#noProviders for further details.");
                Set<URL> set = LoggerFactory.findPossibleStaticLoggerBinderPathSet();
                LoggerFactory.reportIgnoredStaticLoggerBinders(set);
            }
            LoggerFactory.postBindCleanUp();
        } catch (Exception exception) {
            LoggerFactory.failedBinding(exception);
            throw new IllegalStateException("Unexpected initialization failure", exception);
        }
    }

    static SLF4JServiceProvider loadExplicitlySpecified(ClassLoader classLoader) {
        String string = System.getProperty(PROVIDER_PROPERTY_KEY);
        if (null == string || string.isEmpty()) {
            return null;
        }
        try {
            String string2 = String.format("Attempting to load provider \"%s\" specified via \"%s\" system property", string, PROVIDER_PROPERTY_KEY);
            Util.report(string2);
            Class<?> clazz = classLoader.loadClass(string);
            Constructor<?> constructor = clazz.getConstructor(new Class[0]);
            Object obj = constructor.newInstance(new Object[0]);
            return (SLF4JServiceProvider)obj;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException reflectiveOperationException) {
            String string3 = String.format("Failed to instantiate the specified SLF4JServiceProvider (%s)", string);
            Util.report(string3, reflectiveOperationException);
            return null;
        } catch (ClassCastException classCastException) {
            String string4 = String.format("Specified SLF4JServiceProvider (%s) does not implement SLF4JServiceProvider interface", string);
            Util.report(string4, classCastException);
            return null;
        }
    }

    private static void reportIgnoredStaticLoggerBinders(Set<URL> set) {
        if (set.isEmpty()) {
            return;
        }
        Util.report("Class path contains SLF4J bindings targeting slf4j-api versions 1.7.x or earlier.");
        for (URL uRL : set) {
            Util.report("Ignoring binding found at [" + uRL + "]");
        }
        Util.report("See https://www.slf4j.org/codes.html#ignoredBindings for an explanation.");
    }

    static Set<URL> findPossibleStaticLoggerBinderPathSet() {
        LinkedHashSet<URL> linkedHashSet = new LinkedHashSet<URL>();
        try {
            ClassLoader classLoader = LoggerFactory.class.getClassLoader();
            Enumeration<URL> enumeration = classLoader == null ? ClassLoader.getSystemResources(STATIC_LOGGER_BINDER_PATH) : classLoader.getResources(STATIC_LOGGER_BINDER_PATH);
            while (enumeration.hasMoreElements()) {
                URL uRL = enumeration.nextElement();
                linkedHashSet.add(uRL);
            }
        } catch (IOException iOException) {
            Util.report("Error getting resources from path", iOException);
        }
        return linkedHashSet;
    }

    private static void postBindCleanUp() {
        LoggerFactory.fixSubstituteLoggers();
        LoggerFactory.replayEvents();
        SUBST_PROVIDER.getSubstituteLoggerFactory().clear();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void fixSubstituteLoggers() {
        SubstituteServiceProvider substituteServiceProvider = SUBST_PROVIDER;
        synchronized (substituteServiceProvider) {
            SUBST_PROVIDER.getSubstituteLoggerFactory().postInitialization();
            for (SubstituteLogger substituteLogger : SUBST_PROVIDER.getSubstituteLoggerFactory().getLoggers()) {
                Logger logger = LoggerFactory.getLogger(substituteLogger.getName());
                substituteLogger.setDelegate(logger);
            }
        }
    }

    static void failedBinding(Throwable throwable) {
        INITIALIZATION_STATE = 2;
        Util.report("Failed to instantiate SLF4J LoggerFactory", throwable);
    }

    private static void replayEvents() {
        int n;
        LinkedBlockingQueue<SubstituteLoggingEvent> linkedBlockingQueue = SUBST_PROVIDER.getSubstituteLoggerFactory().getEventQueue();
        int n2 = linkedBlockingQueue.size();
        int n3 = 0;
        int n4 = 128;
        ArrayList arrayList = new ArrayList(128);
        while ((n = linkedBlockingQueue.drainTo(arrayList, 128)) != 0) {
            for (SubstituteLoggingEvent substituteLoggingEvent : arrayList) {
                LoggerFactory.replaySingleEvent(substituteLoggingEvent);
                if (n3++ != 0) continue;
                LoggerFactory.emitReplayOrSubstituionWarning(substituteLoggingEvent, n2);
            }
            arrayList.clear();
        }
    }

    private static void emitReplayOrSubstituionWarning(SubstituteLoggingEvent substituteLoggingEvent, int n) {
        if (substituteLoggingEvent.getLogger().isDelegateEventAware()) {
            LoggerFactory.emitReplayWarning(n);
        } else if (!substituteLoggingEvent.getLogger().isDelegateNOP()) {
            LoggerFactory.emitSubstitutionWarning();
        }
    }

    private static void replaySingleEvent(SubstituteLoggingEvent substituteLoggingEvent) {
        if (substituteLoggingEvent == null) {
            return;
        }
        SubstituteLogger substituteLogger = substituteLoggingEvent.getLogger();
        String string = substituteLogger.getName();
        if (substituteLogger.isDelegateNull()) {
            throw new IllegalStateException("Delegate logger cannot be null at this state.");
        }
        if (!substituteLogger.isDelegateNOP()) {
            if (substituteLogger.isDelegateEventAware()) {
                if (substituteLogger.isEnabledForLevel(substituteLoggingEvent.getLevel())) {
                    substituteLogger.log(substituteLoggingEvent);
                }
            } else {
                Util.report(string);
            }
        }
    }

    private static void emitSubstitutionWarning() {
        Util.report("The following set of substitute loggers may have been accessed");
        Util.report("during the initialization phase. Logging calls during this");
        Util.report("phase were not honored. However, subsequent logging calls to these");
        Util.report("loggers will work as normally expected.");
        Util.report("See also https://www.slf4j.org/codes.html#substituteLogger");
    }

    private static void emitReplayWarning(int n) {
        Util.report("A number (" + n + ") of logging calls during the initialization phase have been intercepted and are");
        Util.report("now being replayed. These are subject to the filtering rules of the underlying logging system.");
        Util.report("See also https://www.slf4j.org/codes.html#replay");
    }

    private static final void versionSanityCheck() {
        try {
            String string = PROVIDER.getRequestedApiVersion();
            boolean bl = false;
            for (String string2 : API_COMPATIBILITY_LIST) {
                if (!string.startsWith(string2)) continue;
                bl = true;
            }
            if (!bl) {
                Util.report("The requested version " + string + " by your slf4j provider is not compatible with " + Arrays.asList(API_COMPATIBILITY_LIST).toString());
                Util.report("See https://www.slf4j.org/codes.html#version_mismatch for further details.");
            }
        } catch (NoSuchFieldError noSuchFieldError) {
        } catch (Throwable throwable) {
            Util.report("Unexpected problem occurred during version sanity check", throwable);
        }
    }

    private static boolean isAmbiguousProviderList(List<SLF4JServiceProvider> list) {
        return list.size() > 1;
    }

    private static void reportMultipleBindingAmbiguity(List<SLF4JServiceProvider> list) {
        if (LoggerFactory.isAmbiguousProviderList(list)) {
            Util.report("Class path contains multiple SLF4J providers.");
            for (SLF4JServiceProvider sLF4JServiceProvider : list) {
                Util.report("Found provider [" + sLF4JServiceProvider + "]");
            }
            Util.report("See https://www.slf4j.org/codes.html#multiple_bindings for an explanation.");
        }
    }

    private static void reportActualBinding(List<SLF4JServiceProvider> list) {
        if (!list.isEmpty() && LoggerFactory.isAmbiguousProviderList(list)) {
            Util.report("Actual provider is of type [" + list.get(0) + "]");
        }
    }

    public static Logger getLogger(String string) {
        ILoggerFactory iLoggerFactory = LoggerFactory.getILoggerFactory();
        return iLoggerFactory.getLogger(string);
    }

    public static Logger getLogger(Class<?> clazz) {
        Class<?> clazz2;
        Logger logger = LoggerFactory.getLogger(clazz.getName());
        if (DETECT_LOGGER_NAME_MISMATCH && (clazz2 = Util.getCallingClass()) != null && LoggerFactory.nonMatchingClasses(clazz, clazz2)) {
            Util.report(String.format("Detected logger name mismatch. Given name: \"%s\"; computed name: \"%s\".", logger.getName(), clazz2.getName()));
            Util.report("See https://www.slf4j.org/codes.html#loggerNameMismatch for an explanation");
        }
        return logger;
    }

    private static boolean nonMatchingClasses(Class<?> clazz, Class<?> clazz2) {
        return !clazz2.isAssignableFrom(clazz);
    }

    public static ILoggerFactory getILoggerFactory() {
        return LoggerFactory.getProvider().getLoggerFactory();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    static SLF4JServiceProvider getProvider() {
        if (INITIALIZATION_STATE == 0) {
            Class<LoggerFactory> clazz = LoggerFactory.class;
            // MONITORENTER : org.slf4j.LoggerFactory.class
            if (INITIALIZATION_STATE == 0) {
                INITIALIZATION_STATE = 1;
                LoggerFactory.performInitialization();
            }
            // MONITOREXIT : clazz
        }
        switch (INITIALIZATION_STATE) {
            case 3: {
                return PROVIDER;
            }
            case 4: {
                return NOP_FALLBACK_SERVICE_PROVIDER;
            }
            case 2: {
                throw new IllegalStateException(UNSUCCESSFUL_INIT_MSG);
            }
            case 1: {
                return SUBST_PROVIDER;
            }
        }
        throw new IllegalStateException("Unreachable code");
    }

    private static ServiceLoader lambda$getServiceLoader$0(ClassLoader classLoader) {
        return ServiceLoader.load(SLF4JServiceProvider.class, classLoader);
    }

    static {
        API_COMPATIBILITY_LIST = new String[]{"2.0"};
    }
}

