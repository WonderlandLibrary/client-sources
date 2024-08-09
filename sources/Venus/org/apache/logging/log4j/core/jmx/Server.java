/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.jmx;

import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AsyncAppender;
import org.apache.logging.log4j.core.async.AsyncLoggerConfig;
import org.apache.logging.log4j.core.async.AsyncLoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.impl.Log4jContextFactory;
import org.apache.logging.log4j.core.jmx.AppenderAdmin;
import org.apache.logging.log4j.core.jmx.AsyncAppenderAdmin;
import org.apache.logging.log4j.core.jmx.ContextSelectorAdmin;
import org.apache.logging.log4j.core.jmx.LoggerConfigAdmin;
import org.apache.logging.log4j.core.jmx.LoggerContextAdmin;
import org.apache.logging.log4j.core.jmx.RingBufferAdmin;
import org.apache.logging.log4j.core.jmx.StatusLoggerAdmin;
import org.apache.logging.log4j.core.selector.ContextSelector;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.core.util.Log4jThreadFactory;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.PropertiesUtil;

public final class Server {
    public static final String DOMAIN = "org.apache.logging.log4j2";
    private static final String PROPERTY_DISABLE_JMX = "log4j2.disable.jmx";
    private static final String PROPERTY_ASYNC_NOTIF = "log4j2.jmx.notify.async";
    private static final String THREAD_NAME_PREFIX = "jmx.notif";
    private static final StatusLogger LOGGER = StatusLogger.getLogger();
    static final Executor executor = Server.isJmxDisabled() ? null : Server.createExecutor();

    private Server() {
    }

    private static ExecutorService createExecutor() {
        boolean bl = !Constants.IS_WEB_APP;
        boolean bl2 = PropertiesUtil.getProperties().getBooleanProperty(PROPERTY_ASYNC_NOTIF, bl);
        return bl2 ? Executors.newFixedThreadPool(1, Log4jThreadFactory.createDaemonThreadFactory(THREAD_NAME_PREFIX)) : null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String escape(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length() * 2);
        boolean bl = false;
        block6: for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            switch (c) {
                case '\"': 
                case '*': 
                case '?': 
                case '\\': {
                    stringBuilder.append('\\');
                    bl = true;
                    break;
                }
                case ',': 
                case ':': 
                case '=': {
                    bl = true;
                    break;
                }
                case '\r': {
                    continue block6;
                }
                case '\n': {
                    stringBuilder.append("\\n");
                    bl = true;
                    continue block6;
                }
            }
            stringBuilder.append(c);
        }
        if (bl) {
            stringBuilder.insert(0, '\"');
            stringBuilder.append('\"');
        }
        return stringBuilder.toString();
    }

    private static boolean isJmxDisabled() {
        return PropertiesUtil.getProperties().getBooleanProperty(PROPERTY_DISABLE_JMX);
    }

    public static void reregisterMBeansAfterReconfigure() {
        if (Server.isJmxDisabled()) {
            LOGGER.debug("JMX disabled for Log4j2. Not registering MBeans.");
            return;
        }
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        Server.reregisterMBeansAfterReconfigure(mBeanServer);
    }

    public static void reregisterMBeansAfterReconfigure(MBeanServer mBeanServer) {
        if (Server.isJmxDisabled()) {
            LOGGER.debug("JMX disabled for Log4j2. Not registering MBeans.");
            return;
        }
        try {
            ContextSelector contextSelector = Server.getContextSelector();
            if (contextSelector == null) {
                LOGGER.debug("Could not register MBeans: no ContextSelector found.");
                return;
            }
            LOGGER.trace("Reregistering MBeans after reconfigure. Selector={}", (Object)contextSelector);
            List<LoggerContext> list = contextSelector.getLoggerContexts();
            int n = 0;
            for (LoggerContext loggerContext : list) {
                RingBufferAdmin ringBufferAdmin;
                LOGGER.trace("Reregistering context ({}/{}): '{}' {}", (Object)(++n), (Object)list.size(), (Object)loggerContext.getName(), (Object)loggerContext);
                Server.unregisterLoggerContext(loggerContext.getName(), mBeanServer);
                LoggerContextAdmin loggerContextAdmin = new LoggerContextAdmin(loggerContext, executor);
                Server.register(mBeanServer, loggerContextAdmin, loggerContextAdmin.getObjectName());
                if (loggerContext instanceof AsyncLoggerContext && (ringBufferAdmin = ((AsyncLoggerContext)loggerContext).createRingBufferAdmin()).getBufferSize() > 0L) {
                    Server.register(mBeanServer, ringBufferAdmin, ringBufferAdmin.getObjectName());
                }
                Server.registerStatusLogger(loggerContext.getName(), mBeanServer, executor);
                Server.registerContextSelector(loggerContext.getName(), contextSelector, mBeanServer, executor);
                Server.registerLoggerConfigs(loggerContext, mBeanServer, executor);
                Server.registerAppenders(loggerContext, mBeanServer, executor);
            }
        } catch (Exception exception) {
            LOGGER.error("Could not register mbeans", (Throwable)exception);
        }
    }

    public static void unregisterMBeans() {
        if (Server.isJmxDisabled()) {
            LOGGER.debug("JMX disabled for Log4j2. Not unregistering MBeans.");
            return;
        }
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        Server.unregisterMBeans(mBeanServer);
    }

    public static void unregisterMBeans(MBeanServer mBeanServer) {
        Server.unregisterStatusLogger("*", mBeanServer);
        Server.unregisterContextSelector("*", mBeanServer);
        Server.unregisterContexts(mBeanServer);
        Server.unregisterLoggerConfigs("*", mBeanServer);
        Server.unregisterAsyncLoggerRingBufferAdmins("*", mBeanServer);
        Server.unregisterAsyncLoggerConfigRingBufferAdmins("*", mBeanServer);
        Server.unregisterAppenders("*", mBeanServer);
        Server.unregisterAsyncAppenders("*", mBeanServer);
    }

    private static ContextSelector getContextSelector() {
        LoggerContextFactory loggerContextFactory = LogManager.getFactory();
        if (loggerContextFactory instanceof Log4jContextFactory) {
            ContextSelector contextSelector = ((Log4jContextFactory)loggerContextFactory).getSelector();
            return contextSelector;
        }
        return null;
    }

    public static void unregisterLoggerContext(String string) {
        if (Server.isJmxDisabled()) {
            LOGGER.debug("JMX disabled for Log4j2. Not unregistering MBeans.");
            return;
        }
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        Server.unregisterLoggerContext(string, mBeanServer);
    }

    public static void unregisterLoggerContext(String string, MBeanServer mBeanServer) {
        String string2 = String.format("org.apache.logging.log4j2:type=%s", Server.escape(string), "*");
        Server.unregisterAllMatching(string2, mBeanServer);
        Server.unregisterStatusLogger(string, mBeanServer);
        Server.unregisterContextSelector(string, mBeanServer);
        Server.unregisterLoggerConfigs(string, mBeanServer);
        Server.unregisterAppenders(string, mBeanServer);
        Server.unregisterAsyncAppenders(string, mBeanServer);
        Server.unregisterAsyncLoggerRingBufferAdmins(string, mBeanServer);
        Server.unregisterAsyncLoggerConfigRingBufferAdmins(string, mBeanServer);
    }

    private static void registerStatusLogger(String string, MBeanServer mBeanServer, Executor executor) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        StatusLoggerAdmin statusLoggerAdmin = new StatusLoggerAdmin(string, executor);
        Server.register(mBeanServer, statusLoggerAdmin, statusLoggerAdmin.getObjectName());
    }

    private static void registerContextSelector(String string, ContextSelector contextSelector, MBeanServer mBeanServer, Executor executor) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        ContextSelectorAdmin contextSelectorAdmin = new ContextSelectorAdmin(string, contextSelector);
        Server.register(mBeanServer, contextSelectorAdmin, contextSelectorAdmin.getObjectName());
    }

    private static void unregisterStatusLogger(String string, MBeanServer mBeanServer) {
        String string2 = String.format("org.apache.logging.log4j2:type=%s,component=StatusLogger", Server.escape(string), "*");
        Server.unregisterAllMatching(string2, mBeanServer);
    }

    private static void unregisterContextSelector(String string, MBeanServer mBeanServer) {
        String string2 = String.format("org.apache.logging.log4j2:type=%s,component=ContextSelector", Server.escape(string), "*");
        Server.unregisterAllMatching(string2, mBeanServer);
    }

    private static void unregisterLoggerConfigs(String string, MBeanServer mBeanServer) {
        String string2 = "org.apache.logging.log4j2:type=%s,component=Loggers,name=%s";
        String string3 = String.format("org.apache.logging.log4j2:type=%s,component=Loggers,name=%s", Server.escape(string), "*");
        Server.unregisterAllMatching(string3, mBeanServer);
    }

    private static void unregisterContexts(MBeanServer mBeanServer) {
        String string = "org.apache.logging.log4j2:type=%s";
        String string2 = String.format("org.apache.logging.log4j2:type=%s", "*");
        Server.unregisterAllMatching(string2, mBeanServer);
    }

    private static void unregisterAppenders(String string, MBeanServer mBeanServer) {
        String string2 = "org.apache.logging.log4j2:type=%s,component=Appenders,name=%s";
        String string3 = String.format("org.apache.logging.log4j2:type=%s,component=Appenders,name=%s", Server.escape(string), "*");
        Server.unregisterAllMatching(string3, mBeanServer);
    }

    private static void unregisterAsyncAppenders(String string, MBeanServer mBeanServer) {
        String string2 = "org.apache.logging.log4j2:type=%s,component=AsyncAppenders,name=%s";
        String string3 = String.format("org.apache.logging.log4j2:type=%s,component=AsyncAppenders,name=%s", Server.escape(string), "*");
        Server.unregisterAllMatching(string3, mBeanServer);
    }

    private static void unregisterAsyncLoggerRingBufferAdmins(String string, MBeanServer mBeanServer) {
        String string2 = "org.apache.logging.log4j2:type=%s,component=AsyncLoggerRingBuffer";
        String string3 = String.format("org.apache.logging.log4j2:type=%s,component=AsyncLoggerRingBuffer", Server.escape(string));
        Server.unregisterAllMatching(string3, mBeanServer);
    }

    private static void unregisterAsyncLoggerConfigRingBufferAdmins(String string, MBeanServer mBeanServer) {
        String string2 = "org.apache.logging.log4j2:type=%s,component=Loggers,name=%s,subtype=RingBuffer";
        String string3 = String.format("org.apache.logging.log4j2:type=%s,component=Loggers,name=%s,subtype=RingBuffer", Server.escape(string), "*");
        Server.unregisterAllMatching(string3, mBeanServer);
    }

    private static void unregisterAllMatching(String string, MBeanServer mBeanServer) {
        try {
            ObjectName objectName = new ObjectName(string);
            Set<ObjectName> set = mBeanServer.queryNames(objectName, null);
            if (set.isEmpty()) {
                LOGGER.trace("Unregistering but no MBeans found matching '{}'", (Object)string);
            } else {
                LOGGER.trace("Unregistering {} MBeans: {}", (Object)set.size(), (Object)set);
            }
            for (ObjectName objectName2 : set) {
                mBeanServer.unregisterMBean(objectName2);
            }
        } catch (InstanceNotFoundException instanceNotFoundException) {
            LOGGER.debug("Could not unregister MBeans for " + string + ". Ignoring " + instanceNotFoundException);
        } catch (Exception exception) {
            LOGGER.error("Could not unregister MBeans for " + string, (Throwable)exception);
        }
    }

    private static void registerLoggerConfigs(LoggerContext loggerContext, MBeanServer mBeanServer, Executor executor) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        Map<String, LoggerConfig> map = loggerContext.getConfiguration().getLoggers();
        for (String string : map.keySet()) {
            LoggerConfig loggerConfig = map.get(string);
            LoggerConfigAdmin loggerConfigAdmin = new LoggerConfigAdmin(loggerContext, loggerConfig);
            Server.register(mBeanServer, loggerConfigAdmin, loggerConfigAdmin.getObjectName());
            if (!(loggerConfig instanceof AsyncLoggerConfig)) continue;
            AsyncLoggerConfig asyncLoggerConfig = (AsyncLoggerConfig)loggerConfig;
            RingBufferAdmin ringBufferAdmin = asyncLoggerConfig.createRingBufferAdmin(loggerContext.getName());
            Server.register(mBeanServer, ringBufferAdmin, ringBufferAdmin.getObjectName());
        }
    }

    private static void registerAppenders(LoggerContext loggerContext, MBeanServer mBeanServer, Executor executor) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        Map<String, Appender> map = loggerContext.getConfiguration().getAppenders();
        for (String string : map.keySet()) {
            Object object;
            Appender appender = map.get(string);
            if (appender instanceof AsyncAppender) {
                object = (AsyncAppender)appender;
                AsyncAppenderAdmin asyncAppenderAdmin = new AsyncAppenderAdmin(loggerContext.getName(), (AsyncAppender)object);
                Server.register(mBeanServer, asyncAppenderAdmin, asyncAppenderAdmin.getObjectName());
                continue;
            }
            object = new AppenderAdmin(loggerContext.getName(), appender);
            Server.register(mBeanServer, object, ((AppenderAdmin)object).getObjectName());
        }
    }

    private static void register(MBeanServer mBeanServer, Object object, ObjectName objectName) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        LOGGER.debug("Registering MBean {}", (Object)objectName);
        mBeanServer.registerMBean(object, objectName);
    }
}

