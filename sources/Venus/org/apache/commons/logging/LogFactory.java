/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.logging;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;

public abstract class LogFactory {
    public static final String PRIORITY_KEY = "priority";
    public static final String TCCL_KEY = "use_tccl";
    public static final String FACTORY_PROPERTY = "org.apache.commons.logging.LogFactory";
    public static final String FACTORY_DEFAULT = "org.apache.commons.logging.impl.LogFactoryImpl";
    public static final String FACTORY_PROPERTIES = "commons-logging.properties";
    protected static final String SERVICE_ID = "META-INF/services/org.apache.commons.logging.LogFactory";
    public static final String DIAGNOSTICS_DEST_PROPERTY = "org.apache.commons.logging.diagnostics.dest";
    private static PrintStream diagnosticsStream;
    private static final String diagnosticPrefix;
    public static final String HASHTABLE_IMPLEMENTATION_PROPERTY = "org.apache.commons.logging.LogFactory.HashtableImpl";
    private static final String WEAK_HASHTABLE_CLASSNAME = "org.apache.commons.logging.impl.WeakHashtable";
    private static final ClassLoader thisClassLoader;
    protected static Hashtable factories;
    protected static volatile LogFactory nullClassLoaderFactory;
    static Class class$org$apache$commons$logging$LogFactory;

    protected LogFactory() {
    }

    public abstract Object getAttribute(String var1);

    public abstract String[] getAttributeNames();

    public abstract Log getInstance(Class var1) throws LogConfigurationException;

    public abstract Log getInstance(String var1) throws LogConfigurationException;

    public abstract void release();

    public abstract void removeAttribute(String var1);

    public abstract void setAttribute(String var1, Object var2);

    private static final Hashtable createFactoryStore() {
        Hashtable hashtable;
        block7: {
            String string;
            hashtable = null;
            try {
                string = LogFactory.getSystemProperty(HASHTABLE_IMPLEMENTATION_PROPERTY, null);
            } catch (SecurityException securityException) {
                string = null;
            }
            if (string == null) {
                string = WEAK_HASHTABLE_CLASSNAME;
            }
            try {
                Class<?> clazz = Class.forName(string);
                hashtable = (Hashtable)clazz.newInstance();
            } catch (Throwable throwable) {
                LogFactory.handleThrowable(throwable);
                if (WEAK_HASHTABLE_CLASSNAME.equals(string)) break block7;
                if (LogFactory.isDiagnosticsEnabled()) {
                    LogFactory.logDiagnostic("[ERROR] LogFactory: Load of custom hashtable failed");
                }
                System.err.println("[ERROR] LogFactory: Load of custom hashtable failed");
            }
        }
        if (hashtable == null) {
            hashtable = new Hashtable();
        }
        return hashtable;
    }

    private static String trim(String string) {
        if (string == null) {
            return null;
        }
        return string.trim();
    }

    protected static void handleThrowable(Throwable throwable) {
        if (throwable instanceof ThreadDeath) {
            throw (ThreadDeath)throwable;
        }
        if (throwable instanceof VirtualMachineError) {
            throw (VirtualMachineError)throwable;
        }
    }

    public static LogFactory getFactory() throws LogConfigurationException {
        String string;
        Object object;
        Enumeration<?> enumeration;
        ClassLoader classLoader;
        Properties properties;
        LogFactory logFactory;
        ClassLoader classLoader2;
        block38: {
            classLoader2 = LogFactory.getContextClassLoaderInternal();
            if (classLoader2 == null && LogFactory.isDiagnosticsEnabled()) {
                LogFactory.logDiagnostic("Context classloader is null.");
            }
            if ((logFactory = LogFactory.getCachedFactory(classLoader2)) != null) {
                return logFactory;
            }
            if (LogFactory.isDiagnosticsEnabled()) {
                LogFactory.logDiagnostic("[LOOKUP] LogFactory implementation requested for the first time for context classloader " + LogFactory.objectId(classLoader2));
                LogFactory.logHierarchy("[LOOKUP] ", classLoader2);
            }
            properties = LogFactory.getConfigurationFile(classLoader2, FACTORY_PROPERTIES);
            classLoader = classLoader2;
            if (properties != null && (enumeration = properties.getProperty(TCCL_KEY)) != null && !Boolean.valueOf((String)((Object)enumeration)).booleanValue()) {
                classLoader = thisClassLoader;
            }
            if (LogFactory.isDiagnosticsEnabled()) {
                LogFactory.logDiagnostic("[LOOKUP] Looking for system property [org.apache.commons.logging.LogFactory] to define the LogFactory subclass to use...");
            }
            try {
                enumeration = LogFactory.getSystemProperty(FACTORY_PROPERTY, null);
                if (enumeration != null) {
                    if (LogFactory.isDiagnosticsEnabled()) {
                        LogFactory.logDiagnostic("[LOOKUP] Creating an instance of LogFactory class '" + (String)((Object)enumeration) + "' as specified by system property " + FACTORY_PROPERTY);
                    }
                    logFactory = LogFactory.newFactory((String)((Object)enumeration), classLoader, classLoader2);
                } else if (LogFactory.isDiagnosticsEnabled()) {
                    LogFactory.logDiagnostic("[LOOKUP] No system property [org.apache.commons.logging.LogFactory] defined.");
                }
            } catch (SecurityException securityException) {
                if (LogFactory.isDiagnosticsEnabled()) {
                    LogFactory.logDiagnostic("[LOOKUP] A security exception occurred while trying to create an instance of the custom factory class: [" + LogFactory.trim(securityException.getMessage()) + "]. Trying alternative implementations...");
                }
            } catch (RuntimeException runtimeException) {
                if (LogFactory.isDiagnosticsEnabled()) {
                    LogFactory.logDiagnostic("[LOOKUP] An exception occurred while trying to create an instance of the custom factory class: [" + LogFactory.trim(runtimeException.getMessage()) + "] as specified by a system property.");
                }
                throw runtimeException;
            }
            if (logFactory == null) {
                if (LogFactory.isDiagnosticsEnabled()) {
                    LogFactory.logDiagnostic("[LOOKUP] Looking for a resource file of name [META-INF/services/org.apache.commons.logging.LogFactory] to define the LogFactory subclass to use...");
                }
                try {
                    enumeration = LogFactory.getResourceAsStream(classLoader2, SERVICE_ID);
                    if (enumeration != null) {
                        try {
                            object = new BufferedReader(new InputStreamReader((InputStream)((Object)enumeration), "UTF-8"));
                        } catch (UnsupportedEncodingException unsupportedEncodingException) {
                            object = new BufferedReader(new InputStreamReader((InputStream)((Object)enumeration)));
                        }
                        string = ((BufferedReader)object).readLine();
                        ((BufferedReader)object).close();
                        if (string != null && !"".equals(string)) {
                            if (LogFactory.isDiagnosticsEnabled()) {
                                LogFactory.logDiagnostic("[LOOKUP]  Creating an instance of LogFactory class " + string + " as specified by file '" + SERVICE_ID + "' which was present in the path of the context classloader.");
                            }
                            logFactory = LogFactory.newFactory(string, classLoader, classLoader2);
                        }
                        break block38;
                    }
                    if (LogFactory.isDiagnosticsEnabled()) {
                        LogFactory.logDiagnostic("[LOOKUP] No resource file with name 'META-INF/services/org.apache.commons.logging.LogFactory' found.");
                    }
                } catch (Exception exception) {
                    if (!LogFactory.isDiagnosticsEnabled()) break block38;
                    LogFactory.logDiagnostic("[LOOKUP] A security exception occurred while trying to create an instance of the custom factory class: [" + LogFactory.trim(exception.getMessage()) + "]. Trying alternative implementations...");
                }
            }
        }
        if (logFactory == null) {
            if (properties != null) {
                if (LogFactory.isDiagnosticsEnabled()) {
                    LogFactory.logDiagnostic("[LOOKUP] Looking in properties file for entry with key 'org.apache.commons.logging.LogFactory' to define the LogFactory subclass to use...");
                }
                if ((enumeration = properties.getProperty(FACTORY_PROPERTY)) != null) {
                    if (LogFactory.isDiagnosticsEnabled()) {
                        LogFactory.logDiagnostic("[LOOKUP] Properties file specifies LogFactory subclass '" + enumeration + "'");
                    }
                    logFactory = LogFactory.newFactory(enumeration, classLoader, classLoader2);
                } else if (LogFactory.isDiagnosticsEnabled()) {
                    LogFactory.logDiagnostic("[LOOKUP] Properties file has no entry specifying LogFactory subclass.");
                }
            } else if (LogFactory.isDiagnosticsEnabled()) {
                LogFactory.logDiagnostic("[LOOKUP] No properties file available to determine LogFactory subclass from..");
            }
        }
        if (logFactory == null) {
            if (LogFactory.isDiagnosticsEnabled()) {
                LogFactory.logDiagnostic("[LOOKUP] Loading the default LogFactory implementation 'org.apache.commons.logging.impl.LogFactoryImpl' via the same classloader that loaded this LogFactory class (ie not looking in the context classloader).");
            }
            logFactory = LogFactory.newFactory(FACTORY_DEFAULT, thisClassLoader, classLoader2);
        }
        if (logFactory != null) {
            LogFactory.cacheFactory(classLoader2, logFactory);
            if (properties != null) {
                enumeration = properties.propertyNames();
                while (enumeration.hasMoreElements()) {
                    object = (String)enumeration.nextElement();
                    string = properties.getProperty((String)object);
                    logFactory.setAttribute((String)object, string);
                }
            }
        }
        return logFactory;
    }

    public static Log getLog(Class clazz) throws LogConfigurationException {
        return LogFactory.getFactory().getInstance(clazz);
    }

    public static Log getLog(String string) throws LogConfigurationException {
        return LogFactory.getFactory().getInstance(string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void release(ClassLoader classLoader) {
        Hashtable hashtable;
        if (LogFactory.isDiagnosticsEnabled()) {
            LogFactory.logDiagnostic("Releasing factory for classloader " + LogFactory.objectId(classLoader));
        }
        Hashtable hashtable2 = hashtable = factories;
        synchronized (hashtable2) {
            if (classLoader == null) {
                if (nullClassLoaderFactory != null) {
                    nullClassLoaderFactory.release();
                    nullClassLoaderFactory = null;
                }
            } else {
                LogFactory logFactory = (LogFactory)hashtable.get(classLoader);
                if (logFactory != null) {
                    logFactory.release();
                    hashtable.remove(classLoader);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void releaseAll() {
        Hashtable hashtable;
        if (LogFactory.isDiagnosticsEnabled()) {
            LogFactory.logDiagnostic("Releasing factory for all classloaders.");
        }
        Hashtable hashtable2 = hashtable = factories;
        synchronized (hashtable2) {
            Enumeration enumeration = hashtable.elements();
            while (enumeration.hasMoreElements()) {
                LogFactory logFactory = (LogFactory)enumeration.nextElement();
                logFactory.release();
            }
            hashtable.clear();
            if (nullClassLoaderFactory != null) {
                nullClassLoaderFactory.release();
                nullClassLoaderFactory = null;
            }
        }
    }

    protected static ClassLoader getClassLoader(Class clazz) {
        try {
            return clazz.getClassLoader();
        } catch (SecurityException securityException) {
            if (LogFactory.isDiagnosticsEnabled()) {
                LogFactory.logDiagnostic("Unable to get classloader for class '" + clazz + "' due to security restrictions - " + securityException.getMessage());
            }
            throw securityException;
        }
    }

    protected static ClassLoader getContextClassLoader() throws LogConfigurationException {
        return LogFactory.directGetContextClassLoader();
    }

    private static ClassLoader getContextClassLoaderInternal() throws LogConfigurationException {
        return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                return LogFactory.directGetContextClassLoader();
            }
        });
    }

    protected static ClassLoader directGetContextClassLoader() throws LogConfigurationException {
        ClassLoader classLoader = null;
        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        } catch (SecurityException securityException) {
            // empty catch block
        }
        return classLoader;
    }

    private static LogFactory getCachedFactory(ClassLoader classLoader) {
        if (classLoader == null) {
            return nullClassLoaderFactory;
        }
        return (LogFactory)factories.get(classLoader);
    }

    private static void cacheFactory(ClassLoader classLoader, LogFactory logFactory) {
        if (logFactory != null) {
            if (classLoader == null) {
                nullClassLoaderFactory = logFactory;
            } else {
                factories.put(classLoader, logFactory);
            }
        }
    }

    protected static LogFactory newFactory(String string, ClassLoader classLoader, ClassLoader classLoader2) throws LogConfigurationException {
        Object t = AccessController.doPrivileged(new PrivilegedAction(string, classLoader){
            private final String val$factoryClass;
            private final ClassLoader val$classLoader;
            {
                this.val$factoryClass = string;
                this.val$classLoader = classLoader;
            }

            public Object run() {
                return LogFactory.createFactory(this.val$factoryClass, this.val$classLoader);
            }
        });
        if (t instanceof LogConfigurationException) {
            LogConfigurationException logConfigurationException = (LogConfigurationException)t;
            if (LogFactory.isDiagnosticsEnabled()) {
                LogFactory.logDiagnostic("An error occurred while loading the factory class:" + logConfigurationException.getMessage());
            }
            throw logConfigurationException;
        }
        if (LogFactory.isDiagnosticsEnabled()) {
            LogFactory.logDiagnostic("Created object " + LogFactory.objectId(t) + " to manage classloader " + LogFactory.objectId(classLoader2));
        }
        return (LogFactory)t;
    }

    protected static LogFactory newFactory(String string, ClassLoader classLoader) {
        return LogFactory.newFactory(string, classLoader, null);
    }

    protected static Object createFactory(String string, ClassLoader classLoader) {
        Class<?> clazz = null;
        try {
            block21: {
                if (classLoader != null) {
                    try {
                        clazz = classLoader.loadClass(string);
                        if ((class$org$apache$commons$logging$LogFactory == null ? (class$org$apache$commons$logging$LogFactory = LogFactory.class$(FACTORY_PROPERTY)) : class$org$apache$commons$logging$LogFactory).isAssignableFrom(clazz)) {
                            if (LogFactory.isDiagnosticsEnabled()) {
                                LogFactory.logDiagnostic("Loaded class " + clazz.getName() + " from classloader " + LogFactory.objectId(classLoader));
                            }
                        } else if (LogFactory.isDiagnosticsEnabled()) {
                            LogFactory.logDiagnostic("Factory class " + clazz.getName() + " loaded from classloader " + LogFactory.objectId(clazz.getClassLoader()) + " does not extend '" + (class$org$apache$commons$logging$LogFactory == null ? (class$org$apache$commons$logging$LogFactory = LogFactory.class$(FACTORY_PROPERTY)) : class$org$apache$commons$logging$LogFactory).getName() + "' as loaded by this classloader.");
                            LogFactory.logHierarchy("[BAD CL TREE] ", classLoader);
                        }
                        return (LogFactory)clazz.newInstance();
                    } catch (ClassNotFoundException classNotFoundException) {
                        if (classLoader == thisClassLoader) {
                            if (LogFactory.isDiagnosticsEnabled()) {
                                LogFactory.logDiagnostic("Unable to locate any class called '" + string + "' via classloader " + LogFactory.objectId(classLoader));
                            }
                            throw classNotFoundException;
                        }
                    } catch (NoClassDefFoundError noClassDefFoundError) {
                        if (classLoader == thisClassLoader) {
                            if (LogFactory.isDiagnosticsEnabled()) {
                                LogFactory.logDiagnostic("Class '" + string + "' cannot be loaded" + " via classloader " + LogFactory.objectId(classLoader) + " - it depends on some other class that cannot be found.");
                            }
                            throw noClassDefFoundError;
                        }
                    } catch (ClassCastException classCastException) {
                        if (classLoader != thisClassLoader) break block21;
                        boolean bl = LogFactory.implementsLogFactory(clazz);
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("The application has specified that a custom LogFactory implementation ");
                        stringBuffer.append("should be used but Class '");
                        stringBuffer.append(string);
                        stringBuffer.append("' cannot be converted to '");
                        stringBuffer.append((class$org$apache$commons$logging$LogFactory == null ? (class$org$apache$commons$logging$LogFactory = LogFactory.class$(FACTORY_PROPERTY)) : class$org$apache$commons$logging$LogFactory).getName());
                        stringBuffer.append("'. ");
                        if (bl) {
                            stringBuffer.append("The conflict is caused by the presence of multiple LogFactory classes ");
                            stringBuffer.append("in incompatible classloaders. ");
                            stringBuffer.append("Background can be found in http://commons.apache.org/logging/tech.html. ");
                            stringBuffer.append("If you have not explicitly specified a custom LogFactory then it is likely ");
                            stringBuffer.append("that the container has set one without your knowledge. ");
                            stringBuffer.append("In this case, consider using the commons-logging-adapters.jar file or ");
                            stringBuffer.append("specifying the standard LogFactory from the command line. ");
                        } else {
                            stringBuffer.append("Please check the custom implementation. ");
                        }
                        stringBuffer.append("Help can be found @http://commons.apache.org/logging/troubleshooting.html.");
                        if (LogFactory.isDiagnosticsEnabled()) {
                            LogFactory.logDiagnostic(stringBuffer.toString());
                        }
                        throw new ClassCastException(stringBuffer.toString());
                    }
                }
            }
            if (LogFactory.isDiagnosticsEnabled()) {
                LogFactory.logDiagnostic("Unable to load factory class via classloader " + LogFactory.objectId(classLoader) + " - trying the classloader associated with this LogFactory.");
            }
            clazz = Class.forName(string);
            return (LogFactory)clazz.newInstance();
        } catch (Exception exception) {
            if (LogFactory.isDiagnosticsEnabled()) {
                LogFactory.logDiagnostic("Unable to create LogFactory instance.");
            }
            if (clazz != null && !(class$org$apache$commons$logging$LogFactory == null ? (class$org$apache$commons$logging$LogFactory = LogFactory.class$(FACTORY_PROPERTY)) : class$org$apache$commons$logging$LogFactory).isAssignableFrom(clazz)) {
                return new LogConfigurationException("The chosen LogFactory implementation does not extend LogFactory. Please check your configuration.", exception);
            }
            return new LogConfigurationException(exception);
        }
    }

    private static boolean implementsLogFactory(Class clazz) {
        boolean bl = false;
        if (clazz != null) {
            try {
                ClassLoader classLoader = clazz.getClassLoader();
                if (classLoader == null) {
                    LogFactory.logDiagnostic("[CUSTOM LOG FACTORY] was loaded by the boot classloader");
                } else {
                    LogFactory.logHierarchy("[CUSTOM LOG FACTORY] ", classLoader);
                    Class<?> clazz2 = Class.forName(FACTORY_PROPERTY, false, classLoader);
                    bl = clazz2.isAssignableFrom(clazz);
                    if (bl) {
                        LogFactory.logDiagnostic("[CUSTOM LOG FACTORY] " + clazz.getName() + " implements LogFactory but was loaded by an incompatible classloader.");
                    } else {
                        LogFactory.logDiagnostic("[CUSTOM LOG FACTORY] " + clazz.getName() + " does not implement LogFactory.");
                    }
                }
            } catch (SecurityException securityException) {
                LogFactory.logDiagnostic("[CUSTOM LOG FACTORY] SecurityException thrown whilst trying to determine whether the compatibility was caused by a classloader conflict: " + securityException.getMessage());
            } catch (LinkageError linkageError) {
                LogFactory.logDiagnostic("[CUSTOM LOG FACTORY] LinkageError thrown whilst trying to determine whether the compatibility was caused by a classloader conflict: " + linkageError.getMessage());
            } catch (ClassNotFoundException classNotFoundException) {
                LogFactory.logDiagnostic("[CUSTOM LOG FACTORY] LogFactory class cannot be loaded by classloader which loaded the custom LogFactory implementation. Is the custom factory in the right classloader?");
            }
        }
        return bl;
    }

    private static InputStream getResourceAsStream(ClassLoader classLoader, String string) {
        return (InputStream)AccessController.doPrivileged(new PrivilegedAction(classLoader, string){
            private final ClassLoader val$loader;
            private final String val$name;
            {
                this.val$loader = classLoader;
                this.val$name = string;
            }

            public Object run() {
                if (this.val$loader != null) {
                    return this.val$loader.getResourceAsStream(this.val$name);
                }
                return ClassLoader.getSystemResourceAsStream(this.val$name);
            }
        });
    }

    private static Enumeration getResources(ClassLoader classLoader, String string) {
        PrivilegedAction privilegedAction = new PrivilegedAction(classLoader, string){
            private final ClassLoader val$loader;
            private final String val$name;
            {
                this.val$loader = classLoader;
                this.val$name = string;
            }

            public Object run() {
                try {
                    if (this.val$loader != null) {
                        return this.val$loader.getResources(this.val$name);
                    }
                    return ClassLoader.getSystemResources(this.val$name);
                } catch (IOException iOException) {
                    if (LogFactory.isDiagnosticsEnabled()) {
                        LogFactory.access$000("Exception while trying to find configuration file " + this.val$name + ":" + iOException.getMessage());
                    }
                    return null;
                } catch (NoSuchMethodError noSuchMethodError) {
                    return null;
                }
            }
        };
        Object t = AccessController.doPrivileged(privilegedAction);
        return (Enumeration)t;
    }

    private static Properties getProperties(URL uRL) {
        PrivilegedAction privilegedAction = new PrivilegedAction(uRL){
            private final URL val$url;
            {
                this.val$url = uRL;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public Object run() {
                InputStream inputStream = null;
                try {
                    URLConnection uRLConnection = this.val$url.openConnection();
                    uRLConnection.setUseCaches(true);
                    inputStream = uRLConnection.getInputStream();
                    if (inputStream != null) {
                        Properties properties = new Properties();
                        properties.load(inputStream);
                        inputStream.close();
                        inputStream = null;
                        Properties properties2 = properties;
                        return properties2;
                    }
                } catch (IOException iOException) {
                    if (LogFactory.isDiagnosticsEnabled()) {
                        LogFactory.access$000("Unable to read URL " + this.val$url);
                    }
                } finally {
                    block17: {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException iOException) {
                                if (!LogFactory.isDiagnosticsEnabled()) break block17;
                                LogFactory.access$000("Unable to close stream for URL " + this.val$url);
                            }
                        }
                    }
                }
                return null;
            }
        };
        return (Properties)AccessController.doPrivileged(privilegedAction);
    }

    private static final Properties getConfigurationFile(ClassLoader classLoader, String string) {
        URL uRL;
        Properties properties;
        block12: {
            properties = null;
            double d = 0.0;
            uRL = null;
            try {
                Enumeration enumeration = LogFactory.getResources(classLoader, string);
                if (enumeration == null) {
                    return null;
                }
                while (enumeration.hasMoreElements()) {
                    String string2;
                    URL uRL2 = (URL)enumeration.nextElement();
                    Properties properties2 = LogFactory.getProperties(uRL2);
                    if (properties2 == null) continue;
                    if (properties == null) {
                        uRL = uRL2;
                        properties = properties2;
                        string2 = properties.getProperty(PRIORITY_KEY);
                        d = 0.0;
                        if (string2 != null) {
                            d = Double.parseDouble(string2);
                        }
                        if (!LogFactory.isDiagnosticsEnabled()) continue;
                        LogFactory.logDiagnostic("[LOOKUP] Properties file found at '" + uRL2 + "'" + " with priority " + d);
                        continue;
                    }
                    string2 = properties2.getProperty(PRIORITY_KEY);
                    double d2 = 0.0;
                    if (string2 != null) {
                        d2 = Double.parseDouble(string2);
                    }
                    if (d2 > d) {
                        if (LogFactory.isDiagnosticsEnabled()) {
                            LogFactory.logDiagnostic("[LOOKUP] Properties file at '" + uRL2 + "'" + " with priority " + d2 + " overrides file at '" + uRL + "'" + " with priority " + d);
                        }
                        uRL = uRL2;
                        properties = properties2;
                        d = d2;
                        continue;
                    }
                    if (!LogFactory.isDiagnosticsEnabled()) continue;
                    LogFactory.logDiagnostic("[LOOKUP] Properties file at '" + uRL2 + "'" + " with priority " + d2 + " does not override file at '" + uRL + "'" + " with priority " + d);
                }
            } catch (SecurityException securityException) {
                if (!LogFactory.isDiagnosticsEnabled()) break block12;
                LogFactory.logDiagnostic("SecurityException thrown while trying to find/read config files.");
            }
        }
        if (LogFactory.isDiagnosticsEnabled()) {
            if (properties == null) {
                LogFactory.logDiagnostic("[LOOKUP] No properties file of name '" + string + "' found.");
            } else {
                LogFactory.logDiagnostic("[LOOKUP] Properties file of name '" + string + "' found at '" + uRL + '\"');
            }
        }
        return properties;
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

    private static PrintStream initDiagnostics() {
        String string;
        try {
            string = LogFactory.getSystemProperty(DIAGNOSTICS_DEST_PROPERTY, null);
            if (string == null) {
                return null;
            }
        } catch (SecurityException securityException) {
            return null;
        }
        if (string.equals("STDOUT")) {
            return System.out;
        }
        if (string.equals("STDERR")) {
            return System.err;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(string, true);
            return new PrintStream(fileOutputStream);
        } catch (IOException iOException) {
            return null;
        }
    }

    protected static boolean isDiagnosticsEnabled() {
        return diagnosticsStream != null;
    }

    private static final void logDiagnostic(String string) {
        if (diagnosticsStream != null) {
            diagnosticsStream.print(diagnosticPrefix);
            diagnosticsStream.println(string);
            diagnosticsStream.flush();
        }
    }

    protected static final void logRawDiagnostic(String string) {
        if (diagnosticsStream != null) {
            diagnosticsStream.println(string);
            diagnosticsStream.flush();
        }
    }

    private static void logClassLoaderEnvironment(Class clazz) {
        ClassLoader classLoader;
        if (!LogFactory.isDiagnosticsEnabled()) {
            return;
        }
        try {
            LogFactory.logDiagnostic("[ENV] Extension directories (java.ext.dir): " + System.getProperty("java.ext.dir"));
            LogFactory.logDiagnostic("[ENV] Application classpath (java.class.path): " + System.getProperty("java.class.path"));
        } catch (SecurityException securityException) {
            LogFactory.logDiagnostic("[ENV] Security setting prevent interrogation of system classpaths.");
        }
        String string = clazz.getName();
        try {
            classLoader = LogFactory.getClassLoader(clazz);
        } catch (SecurityException securityException) {
            LogFactory.logDiagnostic("[ENV] Security forbids determining the classloader for " + string);
            return;
        }
        LogFactory.logDiagnostic("[ENV] Class " + string + " was loaded via classloader " + LogFactory.objectId(classLoader));
        LogFactory.logHierarchy("[ENV] Ancestry of classloader which loaded " + string + " is ", classLoader);
    }

    private static void logHierarchy(String string, ClassLoader classLoader) {
        ClassLoader classLoader2;
        CharSequence charSequence;
        if (!LogFactory.isDiagnosticsEnabled()) {
            return;
        }
        if (classLoader != null) {
            charSequence = classLoader.toString();
            LogFactory.logDiagnostic(string + LogFactory.objectId(classLoader) + " == '" + (String)charSequence + "'");
        }
        try {
            classLoader2 = ClassLoader.getSystemClassLoader();
        } catch (SecurityException securityException) {
            LogFactory.logDiagnostic(string + "Security forbids determining the system classloader.");
            return;
        }
        if (classLoader != null) {
            block9: {
                charSequence = new StringBuffer(string + "ClassLoader tree:");
                do {
                    ((StringBuffer)charSequence).append(LogFactory.objectId(classLoader));
                    if (classLoader == classLoader2) {
                        ((StringBuffer)charSequence).append(" (SYSTEM) ");
                    }
                    try {
                        classLoader = classLoader.getParent();
                    } catch (SecurityException securityException) {
                        ((StringBuffer)charSequence).append(" --> SECRET");
                        break block9;
                    }
                    ((StringBuffer)charSequence).append(" --> ");
                } while (classLoader != null);
                ((StringBuffer)charSequence).append("BOOT");
            }
            LogFactory.logDiagnostic(((StringBuffer)charSequence).toString());
        }
    }

    public static String objectId(Object object) {
        if (object == null) {
            return "null";
        }
        return object.getClass().getName() + "@" + System.identityHashCode(object);
    }

    static Class class$(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }

    static void access$000(String string) {
        LogFactory.logDiagnostic(string);
    }

    static {
        String string;
        diagnosticsStream = null;
        factories = null;
        nullClassLoaderFactory = null;
        thisClassLoader = LogFactory.getClassLoader(class$org$apache$commons$logging$LogFactory == null ? (class$org$apache$commons$logging$LogFactory = LogFactory.class$(FACTORY_PROPERTY)) : class$org$apache$commons$logging$LogFactory);
        try {
            ClassLoader classLoader = thisClassLoader;
            string = thisClassLoader == null ? "BOOTLOADER" : LogFactory.objectId(classLoader);
        } catch (SecurityException securityException) {
            string = "UNKNOWN";
        }
        diagnosticPrefix = "[LogFactory from " + string + "] ";
        diagnosticsStream = LogFactory.initDiagnostics();
        LogFactory.logClassLoaderEnvironment(class$org$apache$commons$logging$LogFactory == null ? (class$org$apache$commons$logging$LogFactory = LogFactory.class$(FACTORY_PROPERTY)) : class$org$apache$commons$logging$LogFactory);
        factories = LogFactory.createFactoryStore();
        if (LogFactory.isDiagnosticsEnabled()) {
            LogFactory.logDiagnostic("BOOTSTRAP COMPLETED");
        }
    }
}

