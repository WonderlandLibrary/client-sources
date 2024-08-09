/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.logging.impl;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Hashtable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.LogFactory;

public class LogFactoryImpl
extends LogFactory {
    private static final String LOGGING_IMPL_LOG4J_LOGGER = "org.apache.commons.logging.impl.Log4JLogger";
    private static final String LOGGING_IMPL_JDK14_LOGGER = "org.apache.commons.logging.impl.Jdk14Logger";
    private static final String LOGGING_IMPL_LUMBERJACK_LOGGER = "org.apache.commons.logging.impl.Jdk13LumberjackLogger";
    private static final String LOGGING_IMPL_SIMPLE_LOGGER = "org.apache.commons.logging.impl.SimpleLog";
    private static final String PKG_IMPL = "org.apache.commons.logging.impl.";
    private static final int PKG_LEN = 32;
    public static final String LOG_PROPERTY = "org.apache.commons.logging.Log";
    protected static final String LOG_PROPERTY_OLD = "org.apache.commons.logging.log";
    public static final String ALLOW_FLAWED_CONTEXT_PROPERTY = "org.apache.commons.logging.Log.allowFlawedContext";
    public static final String ALLOW_FLAWED_DISCOVERY_PROPERTY = "org.apache.commons.logging.Log.allowFlawedDiscovery";
    public static final String ALLOW_FLAWED_HIERARCHY_PROPERTY = "org.apache.commons.logging.Log.allowFlawedHierarchy";
    private static final String[] classesToDiscover = new String[]{"org.apache.commons.logging.impl.Log4JLogger", "org.apache.commons.logging.impl.Jdk14Logger", "org.apache.commons.logging.impl.Jdk13LumberjackLogger", "org.apache.commons.logging.impl.SimpleLog"};
    private boolean useTCCL = true;
    private String diagnosticPrefix;
    protected Hashtable attributes = new Hashtable();
    protected Hashtable instances = new Hashtable();
    private String logClassName;
    protected Constructor logConstructor = null;
    protected Class[] logConstructorSignature = new Class[]{class$java$lang$String == null ? (class$java$lang$String = LogFactoryImpl.class$("java.lang.String")) : class$java$lang$String};
    protected Method logMethod = null;
    protected Class[] logMethodSignature = new Class[]{class$org$apache$commons$logging$LogFactory == null ? (class$org$apache$commons$logging$LogFactory = LogFactoryImpl.class$("org.apache.commons.logging.LogFactory")) : class$org$apache$commons$logging$LogFactory};
    private boolean allowFlawedContext;
    private boolean allowFlawedDiscovery;
    private boolean allowFlawedHierarchy;
    static Class class$java$lang$String;
    static Class class$org$apache$commons$logging$LogFactory;
    static Class class$org$apache$commons$logging$impl$LogFactoryImpl;
    static Class class$org$apache$commons$logging$Log;

    public LogFactoryImpl() {
        this.initDiagnostics();
        if (LogFactoryImpl.isDiagnosticsEnabled()) {
            this.logDiagnostic("Instance created.");
        }
    }

    public Object getAttribute(String string) {
        return this.attributes.get(string);
    }

    public String[] getAttributeNames() {
        return this.attributes.keySet().toArray(new String[this.attributes.size()]);
    }

    public Log getInstance(Class clazz) throws LogConfigurationException {
        return this.getInstance(clazz.getName());
    }

    public Log getInstance(String string) throws LogConfigurationException {
        Log log2 = (Log)this.instances.get(string);
        if (log2 == null) {
            log2 = this.newInstance(string);
            this.instances.put(string, log2);
        }
        return log2;
    }

    public void release() {
        this.logDiagnostic("Releasing all known loggers");
        this.instances.clear();
    }

    public void removeAttribute(String string) {
        this.attributes.remove(string);
    }

    public void setAttribute(String string, Object object) {
        if (this.logConstructor != null) {
            this.logDiagnostic("setAttribute: call too late; configuration already performed.");
        }
        if (object == null) {
            this.attributes.remove(string);
        } else {
            this.attributes.put(string, object);
        }
        if (string.equals("use_tccl")) {
            this.useTCCL = object != null && Boolean.valueOf(object.toString()) != false;
        }
    }

    protected static ClassLoader getContextClassLoader() throws LogConfigurationException {
        return LogFactory.getContextClassLoader();
    }

    protected static boolean isDiagnosticsEnabled() {
        return LogFactory.isDiagnosticsEnabled();
    }

    protected static ClassLoader getClassLoader(Class clazz) {
        return LogFactory.getClassLoader(clazz);
    }

    private void initDiagnostics() {
        String string;
        Class<?> clazz = this.getClass();
        ClassLoader classLoader = LogFactoryImpl.getClassLoader(clazz);
        try {
            string = classLoader == null ? "BOOTLOADER" : LogFactoryImpl.objectId(classLoader);
        } catch (SecurityException securityException) {
            string = "UNKNOWN";
        }
        this.diagnosticPrefix = "[LogFactoryImpl@" + System.identityHashCode(this) + " from " + string + "] ";
    }

    protected void logDiagnostic(String string) {
        if (LogFactoryImpl.isDiagnosticsEnabled()) {
            LogFactoryImpl.logRawDiagnostic(this.diagnosticPrefix + string);
        }
    }

    protected String getLogClassName() {
        if (this.logClassName == null) {
            this.discoverLogImplementation(this.getClass().getName());
        }
        return this.logClassName;
    }

    protected Constructor getLogConstructor() throws LogConfigurationException {
        if (this.logConstructor == null) {
            this.discoverLogImplementation(this.getClass().getName());
        }
        return this.logConstructor;
    }

    protected boolean isJdk13LumberjackAvailable() {
        return this.isLogLibraryAvailable("Jdk13Lumberjack", LOGGING_IMPL_LUMBERJACK_LOGGER);
    }

    protected boolean isJdk14Available() {
        return this.isLogLibraryAvailable("Jdk14", LOGGING_IMPL_JDK14_LOGGER);
    }

    protected boolean isLog4JAvailable() {
        return this.isLogLibraryAvailable("Log4J", LOGGING_IMPL_LOG4J_LOGGER);
    }

    protected Log newInstance(String string) throws LogConfigurationException {
        try {
            Object[] objectArray;
            Log log2;
            if (this.logConstructor == null) {
                log2 = this.discoverLogImplementation(string);
            } else {
                objectArray = new Object[]{string};
                log2 = (Log)this.logConstructor.newInstance(objectArray);
            }
            if (this.logMethod != null) {
                objectArray = new Object[]{this};
                this.logMethod.invoke(log2, objectArray);
            }
            return log2;
        } catch (LogConfigurationException logConfigurationException) {
            throw logConfigurationException;
        } catch (InvocationTargetException invocationTargetException) {
            Throwable throwable = invocationTargetException.getTargetException();
            throw new LogConfigurationException(throwable == null ? invocationTargetException : throwable);
        } catch (Throwable throwable) {
            LogFactoryImpl.handleThrowable(throwable);
            throw new LogConfigurationException(throwable);
        }
    }

    private static ClassLoader getContextClassLoaderInternal() throws LogConfigurationException {
        return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                return LogFactoryImpl.access$000();
            }
        });
    }

    private static String getSystemProperty(String string, String string2) throws SecurityException {
        return (String)AccessController.doPrivileged(new PrivilegedAction(string, string2){
            private final String val$key;
            private final String val$def;
            {
                this.val$key = string;
                this.val$def = string2;
            }

            public Object run() {
                return System.getProperty(this.val$key, this.val$def);
            }
        });
    }

    private ClassLoader getParentClassLoader(ClassLoader classLoader) {
        try {
            return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction(this, classLoader){
                private final ClassLoader val$cl;
                private final LogFactoryImpl this$0;
                {
                    this.this$0 = logFactoryImpl;
                    this.val$cl = classLoader;
                }

                public Object run() {
                    return this.val$cl.getParent();
                }
            });
        } catch (SecurityException securityException) {
            this.logDiagnostic("[SECURITY] Unable to obtain parent classloader");
            return null;
        }
    }

    private boolean isLogLibraryAvailable(String string, String string2) {
        if (LogFactoryImpl.isDiagnosticsEnabled()) {
            this.logDiagnostic("Checking for '" + string + "'.");
        }
        try {
            Log log2 = this.createLogFromClass(string2, this.getClass().getName(), false);
            if (log2 == null) {
                if (LogFactoryImpl.isDiagnosticsEnabled()) {
                    this.logDiagnostic("Did not find '" + string + "'.");
                }
                return false;
            }
            if (LogFactoryImpl.isDiagnosticsEnabled()) {
                this.logDiagnostic("Found '" + string + "'.");
            }
            return true;
        } catch (LogConfigurationException logConfigurationException) {
            if (LogFactoryImpl.isDiagnosticsEnabled()) {
                this.logDiagnostic("Logging system '" + string + "' is available but not useable.");
            }
            return true;
        }
    }

    private String getConfigurationValue(String string) {
        block10: {
            Object object;
            if (LogFactoryImpl.isDiagnosticsEnabled()) {
                this.logDiagnostic("[ENV] Trying to get configuration for item " + string);
            }
            if ((object = this.getAttribute(string)) != null) {
                if (LogFactoryImpl.isDiagnosticsEnabled()) {
                    this.logDiagnostic("[ENV] Found LogFactory attribute [" + object + "] for " + string);
                }
                return object.toString();
            }
            if (LogFactoryImpl.isDiagnosticsEnabled()) {
                this.logDiagnostic("[ENV] No LogFactory attribute found for " + string);
            }
            try {
                String string2 = LogFactoryImpl.getSystemProperty(string, null);
                if (string2 != null) {
                    if (LogFactoryImpl.isDiagnosticsEnabled()) {
                        this.logDiagnostic("[ENV] Found system property [" + string2 + "] for " + string);
                    }
                    return string2;
                }
                if (LogFactoryImpl.isDiagnosticsEnabled()) {
                    this.logDiagnostic("[ENV] No system property found for property " + string);
                }
            } catch (SecurityException securityException) {
                if (!LogFactoryImpl.isDiagnosticsEnabled()) break block10;
                this.logDiagnostic("[ENV] Security prevented reading system property " + string);
            }
        }
        if (LogFactoryImpl.isDiagnosticsEnabled()) {
            this.logDiagnostic("[ENV] No configuration defined for item " + string);
        }
        return null;
    }

    private boolean getBooleanConfiguration(String string, boolean bl) {
        String string2 = this.getConfigurationValue(string);
        if (string2 == null) {
            return bl;
        }
        return Boolean.valueOf(string2);
    }

    private void initConfiguration() {
        this.allowFlawedContext = this.getBooleanConfiguration(ALLOW_FLAWED_CONTEXT_PROPERTY, true);
        this.allowFlawedDiscovery = this.getBooleanConfiguration(ALLOW_FLAWED_DISCOVERY_PROPERTY, true);
        this.allowFlawedHierarchy = this.getBooleanConfiguration(ALLOW_FLAWED_HIERARCHY_PROPERTY, true);
    }

    private Log discoverLogImplementation(String string) throws LogConfigurationException {
        if (LogFactoryImpl.isDiagnosticsEnabled()) {
            this.logDiagnostic("Discovering a Log implementation...");
        }
        this.initConfiguration();
        Log log2 = null;
        String string2 = this.findUserSpecifiedLogClassName();
        if (string2 != null) {
            if (LogFactoryImpl.isDiagnosticsEnabled()) {
                this.logDiagnostic("Attempting to load user-specified log class '" + string2 + "'...");
            }
            if ((log2 = this.createLogFromClass(string2, string, true)) == null) {
                StringBuffer stringBuffer = new StringBuffer("User-specified log class '");
                stringBuffer.append(string2);
                stringBuffer.append("' cannot be found or is not useable.");
                this.informUponSimilarName(stringBuffer, string2, LOGGING_IMPL_LOG4J_LOGGER);
                this.informUponSimilarName(stringBuffer, string2, LOGGING_IMPL_JDK14_LOGGER);
                this.informUponSimilarName(stringBuffer, string2, LOGGING_IMPL_LUMBERJACK_LOGGER);
                this.informUponSimilarName(stringBuffer, string2, LOGGING_IMPL_SIMPLE_LOGGER);
                throw new LogConfigurationException(stringBuffer.toString());
            }
            return log2;
        }
        if (LogFactoryImpl.isDiagnosticsEnabled()) {
            this.logDiagnostic("No user-specified Log implementation; performing discovery using the standard supported logging implementations...");
        }
        for (int i = 0; i < classesToDiscover.length && log2 == null; ++i) {
            log2 = this.createLogFromClass(classesToDiscover[i], string, true);
        }
        if (log2 == null) {
            throw new LogConfigurationException("No suitable Log implementation");
        }
        return log2;
    }

    private void informUponSimilarName(StringBuffer stringBuffer, String string, String string2) {
        if (string.equals(string2)) {
            return;
        }
        if (string.regionMatches(true, 0, string2, 0, PKG_LEN + 5)) {
            stringBuffer.append(" Did you mean '");
            stringBuffer.append(string2);
            stringBuffer.append("'?");
        }
    }

    private String findUserSpecifiedLogClassName() {
        String string;
        block13: {
            block12: {
                if (LogFactoryImpl.isDiagnosticsEnabled()) {
                    this.logDiagnostic("Trying to get log class from attribute 'org.apache.commons.logging.Log'");
                }
                if ((string = (String)this.getAttribute(LOG_PROPERTY)) == null) {
                    if (LogFactoryImpl.isDiagnosticsEnabled()) {
                        this.logDiagnostic("Trying to get log class from attribute 'org.apache.commons.logging.log'");
                    }
                    string = (String)this.getAttribute(LOG_PROPERTY_OLD);
                }
                if (string == null) {
                    if (LogFactoryImpl.isDiagnosticsEnabled()) {
                        this.logDiagnostic("Trying to get log class from system property 'org.apache.commons.logging.Log'");
                    }
                    try {
                        string = LogFactoryImpl.getSystemProperty(LOG_PROPERTY, null);
                    } catch (SecurityException securityException) {
                        if (!LogFactoryImpl.isDiagnosticsEnabled()) break block12;
                        this.logDiagnostic("No access allowed to system property 'org.apache.commons.logging.Log' - " + securityException.getMessage());
                    }
                }
            }
            if (string == null) {
                if (LogFactoryImpl.isDiagnosticsEnabled()) {
                    this.logDiagnostic("Trying to get log class from system property 'org.apache.commons.logging.log'");
                }
                try {
                    string = LogFactoryImpl.getSystemProperty(LOG_PROPERTY_OLD, null);
                } catch (SecurityException securityException) {
                    if (!LogFactoryImpl.isDiagnosticsEnabled()) break block13;
                    this.logDiagnostic("No access allowed to system property 'org.apache.commons.logging.log' - " + securityException.getMessage());
                }
            }
        }
        if (string != null) {
            string = string.trim();
        }
        return string;
    }

    private Log createLogFromClass(String string, String string2, boolean bl) throws LogConfigurationException {
        if (LogFactoryImpl.isDiagnosticsEnabled()) {
            this.logDiagnostic("Attempting to instantiate '" + string + "'");
        }
        Object[] objectArray = new Object[]{string2};
        Log log2 = null;
        Constructor<?> constructor = null;
        Serializable serializable = null;
        ClassLoader classLoader = this.getBaseClassLoader();
        while (true) {
            String string3;
            this.logDiagnostic("Trying to load '" + string + "' from classloader " + LogFactoryImpl.objectId(classLoader));
            try {
                Serializable serializable2;
                if (LogFactoryImpl.isDiagnosticsEnabled()) {
                    string3 = string.replace('.', '/') + ".class";
                    serializable2 = classLoader != null ? classLoader.getResource(string3) : ClassLoader.getSystemResource(string3 + ".class");
                    if (serializable2 == null) {
                        this.logDiagnostic("Class '" + string + "' [" + string3 + "] cannot be found.");
                    } else {
                        this.logDiagnostic("Class '" + string + "' was found at '" + serializable2 + "'");
                    }
                }
                try {
                    serializable2 = Class.forName(string, true, classLoader);
                } catch (ClassNotFoundException classNotFoundException) {
                    String string4 = classNotFoundException.getMessage();
                    this.logDiagnostic("The log adapter '" + string + "' is not available via classloader " + LogFactoryImpl.objectId(classLoader) + ": " + string4.trim());
                    try {
                        serializable2 = Class.forName(string);
                    } catch (ClassNotFoundException classNotFoundException2) {
                        string4 = classNotFoundException2.getMessage();
                        this.logDiagnostic("The log adapter '" + string + "' is not available via the LogFactoryImpl class classloader: " + string4.trim());
                        break;
                    }
                }
                constructor = serializable2.getConstructor(this.logConstructorSignature);
                string3 = constructor.newInstance(objectArray);
                if (string3 instanceof Log) {
                    serializable = serializable2;
                    log2 = (Log)((Object)string3);
                    break;
                }
                this.handleFlawedHierarchy(classLoader, (Class)serializable2);
            } catch (NoClassDefFoundError noClassDefFoundError) {
                string3 = noClassDefFoundError.getMessage();
                this.logDiagnostic("The log adapter '" + string + "' is missing dependencies when loaded via classloader " + LogFactoryImpl.objectId(classLoader) + ": " + string3.trim());
                break;
            } catch (ExceptionInInitializerError exceptionInInitializerError) {
                string3 = exceptionInInitializerError.getMessage();
                this.logDiagnostic("The log adapter '" + string + "' is unable to initialize itself when loaded via classloader " + LogFactoryImpl.objectId(classLoader) + ": " + string3.trim());
                break;
            } catch (LogConfigurationException logConfigurationException) {
                throw logConfigurationException;
            } catch (Throwable throwable) {
                LogFactoryImpl.handleThrowable(throwable);
                this.handleFlawedDiscovery(string, classLoader, throwable);
            }
            if (classLoader == null) break;
            classLoader = this.getParentClassLoader(classLoader);
        }
        if (serializable != null && bl) {
            this.logClassName = string;
            this.logConstructor = constructor;
            try {
                this.logMethod = ((Class)serializable).getMethod("setLogFactory", this.logMethodSignature);
                this.logDiagnostic("Found method setLogFactory(LogFactory) in '" + string + "'");
            } catch (Throwable throwable) {
                LogFactoryImpl.handleThrowable(throwable);
                this.logMethod = null;
                this.logDiagnostic("[INFO] '" + string + "' from classloader " + LogFactoryImpl.objectId(classLoader) + " does not declare optional method " + "setLogFactory(LogFactory)");
            }
            this.logDiagnostic("Log adapter '" + string + "' from classloader " + LogFactoryImpl.objectId(((Class)serializable).getClassLoader()) + " has been selected for use.");
        }
        return log2;
    }

    private ClassLoader getBaseClassLoader() throws LogConfigurationException {
        ClassLoader classLoader = LogFactoryImpl.getClassLoader(class$org$apache$commons$logging$impl$LogFactoryImpl == null ? (class$org$apache$commons$logging$impl$LogFactoryImpl = LogFactoryImpl.class$("org.apache.commons.logging.impl.LogFactoryImpl")) : class$org$apache$commons$logging$impl$LogFactoryImpl);
        if (!this.useTCCL) {
            return classLoader;
        }
        ClassLoader classLoader2 = LogFactoryImpl.getContextClassLoaderInternal();
        ClassLoader classLoader3 = this.getLowestClassLoader(classLoader2, classLoader);
        if (classLoader3 == null) {
            if (this.allowFlawedContext) {
                if (LogFactoryImpl.isDiagnosticsEnabled()) {
                    this.logDiagnostic("[WARNING] the context classloader is not part of a parent-child relationship with the classloader that loaded LogFactoryImpl.");
                }
                return classLoader2;
            }
            throw new LogConfigurationException("Bad classloader hierarchy; LogFactoryImpl was loaded via a classloader that is not related to the current context classloader.");
        }
        if (classLoader3 != classLoader2) {
            if (this.allowFlawedContext) {
                if (LogFactoryImpl.isDiagnosticsEnabled()) {
                    this.logDiagnostic("Warning: the context classloader is an ancestor of the classloader that loaded LogFactoryImpl; it should be the same or a descendant. The application using commons-logging should ensure the context classloader is used correctly.");
                }
            } else {
                throw new LogConfigurationException("Bad classloader hierarchy; LogFactoryImpl was loaded via a classloader that is not related to the current context classloader.");
            }
        }
        return classLoader3;
    }

    private ClassLoader getLowestClassLoader(ClassLoader classLoader, ClassLoader classLoader2) {
        if (classLoader == null) {
            return classLoader2;
        }
        if (classLoader2 == null) {
            return classLoader;
        }
        ClassLoader classLoader3 = classLoader;
        while (classLoader3 != null) {
            if (classLoader3 == classLoader2) {
                return classLoader;
            }
            classLoader3 = this.getParentClassLoader(classLoader3);
        }
        classLoader3 = classLoader2;
        while (classLoader3 != null) {
            if (classLoader3 == classLoader) {
                return classLoader2;
            }
            classLoader3 = this.getParentClassLoader(classLoader3);
        }
        return null;
    }

    private void handleFlawedDiscovery(String string, ClassLoader classLoader, Throwable throwable) {
        if (LogFactoryImpl.isDiagnosticsEnabled()) {
            InvocationTargetException invocationTargetException;
            Throwable throwable2;
            this.logDiagnostic("Could not instantiate Log '" + string + "' -- " + throwable.getClass().getName() + ": " + throwable.getLocalizedMessage());
            if (throwable instanceof InvocationTargetException && (throwable2 = (invocationTargetException = (InvocationTargetException)throwable).getTargetException()) != null) {
                ExceptionInInitializerError exceptionInInitializerError;
                Throwable throwable3;
                this.logDiagnostic("... InvocationTargetException: " + throwable2.getClass().getName() + ": " + throwable2.getLocalizedMessage());
                if (throwable2 instanceof ExceptionInInitializerError && (throwable3 = (exceptionInInitializerError = (ExceptionInInitializerError)throwable2).getException()) != null) {
                    StringWriter stringWriter = new StringWriter();
                    throwable3.printStackTrace(new PrintWriter(stringWriter, true));
                    this.logDiagnostic("... ExceptionInInitializerError: " + stringWriter.toString());
                }
            }
        }
        if (!this.allowFlawedDiscovery) {
            throw new LogConfigurationException(throwable);
        }
    }

    private void handleFlawedHierarchy(ClassLoader classLoader, Class clazz) throws LogConfigurationException {
        boolean bl = false;
        String string = (class$org$apache$commons$logging$Log == null ? (class$org$apache$commons$logging$Log = LogFactoryImpl.class$(LOG_PROPERTY)) : class$org$apache$commons$logging$Log).getName();
        Class<?>[] classArray = clazz.getInterfaces();
        for (int i = 0; i < classArray.length; ++i) {
            if (!string.equals(classArray[i].getName())) continue;
            bl = true;
            break;
        }
        if (bl) {
            if (LogFactoryImpl.isDiagnosticsEnabled()) {
                try {
                    ClassLoader classLoader2 = LogFactoryImpl.getClassLoader(class$org$apache$commons$logging$Log == null ? (class$org$apache$commons$logging$Log = LogFactoryImpl.class$(LOG_PROPERTY)) : class$org$apache$commons$logging$Log);
                    this.logDiagnostic("Class '" + clazz.getName() + "' was found in classloader " + LogFactoryImpl.objectId(classLoader) + ". It is bound to a Log interface which is not" + " the one loaded from classloader " + LogFactoryImpl.objectId(classLoader2));
                } catch (Throwable throwable) {
                    LogFactoryImpl.handleThrowable(throwable);
                    this.logDiagnostic("Error while trying to output diagnostics about bad class '" + clazz + "'");
                }
            }
            if (!this.allowFlawedHierarchy) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Terminating logging for this context ");
                stringBuffer.append("due to bad log hierarchy. ");
                stringBuffer.append("You have more than one version of '");
                stringBuffer.append((class$org$apache$commons$logging$Log == null ? (class$org$apache$commons$logging$Log = LogFactoryImpl.class$(LOG_PROPERTY)) : class$org$apache$commons$logging$Log).getName());
                stringBuffer.append("' visible.");
                if (LogFactoryImpl.isDiagnosticsEnabled()) {
                    this.logDiagnostic(stringBuffer.toString());
                }
                throw new LogConfigurationException(stringBuffer.toString());
            }
            if (LogFactoryImpl.isDiagnosticsEnabled()) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Warning: bad log hierarchy. ");
                stringBuffer.append("You have more than one version of '");
                stringBuffer.append((class$org$apache$commons$logging$Log == null ? (class$org$apache$commons$logging$Log = LogFactoryImpl.class$(LOG_PROPERTY)) : class$org$apache$commons$logging$Log).getName());
                stringBuffer.append("' visible.");
                this.logDiagnostic(stringBuffer.toString());
            }
        } else {
            if (!this.allowFlawedDiscovery) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Terminating logging for this context. ");
                stringBuffer.append("Log class '");
                stringBuffer.append(clazz.getName());
                stringBuffer.append("' does not implement the Log interface.");
                if (LogFactoryImpl.isDiagnosticsEnabled()) {
                    this.logDiagnostic(stringBuffer.toString());
                }
                throw new LogConfigurationException(stringBuffer.toString());
            }
            if (LogFactoryImpl.isDiagnosticsEnabled()) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("[WARNING] Log class '");
                stringBuffer.append(clazz.getName());
                stringBuffer.append("' does not implement the Log interface.");
                this.logDiagnostic(stringBuffer.toString());
            }
        }
    }

    static Class class$(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }

    static ClassLoader access$000() throws LogConfigurationException {
        return LogFactoryImpl.directGetContextClassLoader();
    }
}

