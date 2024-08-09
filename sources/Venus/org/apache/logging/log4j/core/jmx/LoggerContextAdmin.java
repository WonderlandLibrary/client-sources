/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.jmx;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.jmx.LoggerContextAdminMBean;
import org.apache.logging.log4j.core.jmx.Server;
import org.apache.logging.log4j.core.util.Closer;
import org.apache.logging.log4j.status.StatusLogger;

public class LoggerContextAdmin
extends NotificationBroadcasterSupport
implements LoggerContextAdminMBean,
PropertyChangeListener {
    private static final int PAGE = 4096;
    private static final int TEXT_BUFFER = 65536;
    private static final int BUFFER_SIZE = 2048;
    private static final StatusLogger LOGGER = StatusLogger.getLogger();
    private final AtomicLong sequenceNo = new AtomicLong();
    private final ObjectName objectName;
    private final LoggerContext loggerContext;

    public LoggerContextAdmin(LoggerContext loggerContext, Executor executor) {
        super(executor, LoggerContextAdmin.createNotificationInfo());
        this.loggerContext = Objects.requireNonNull(loggerContext, "loggerContext");
        try {
            String string = Server.escape(loggerContext.getName());
            String string2 = String.format("org.apache.logging.log4j2:type=%s", string);
            this.objectName = new ObjectName(string2);
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
        loggerContext.addPropertyChangeListener(this);
    }

    private static MBeanNotificationInfo createNotificationInfo() {
        String[] stringArray = new String[]{"com.apache.logging.log4j.core.jmx.config.reconfigured"};
        String string = Notification.class.getName();
        String string2 = "Configuration reconfigured";
        return new MBeanNotificationInfo(stringArray, string, "Configuration reconfigured");
    }

    @Override
    public String getStatus() {
        return this.loggerContext.getState().toString();
    }

    @Override
    public String getName() {
        return this.loggerContext.getName();
    }

    private Configuration getConfig() {
        return this.loggerContext.getConfiguration();
    }

    @Override
    public String getConfigLocationUri() {
        if (this.loggerContext.getConfigLocation() != null) {
            return String.valueOf(this.loggerContext.getConfigLocation());
        }
        if (this.getConfigName() != null) {
            return String.valueOf(new File(this.getConfigName()).toURI());
        }
        return "";
    }

    @Override
    public void setConfigLocationUri(String string) throws URISyntaxException, IOException {
        Object object;
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException("Missing configuration location");
        }
        LOGGER.debug("---------");
        LOGGER.debug("Remote request to reconfigure using location " + string);
        File file = new File(string);
        ConfigurationSource configurationSource = null;
        if (file.exists()) {
            LOGGER.debug("Opening config file {}", (Object)file.getAbsolutePath());
            configurationSource = new ConfigurationSource((InputStream)new FileInputStream(file), file);
        } else {
            object = new URL(string);
            LOGGER.debug("Opening config URL {}", object);
            configurationSource = new ConfigurationSource(((URL)object).openStream(), (URL)object);
        }
        object = ConfigurationFactory.getInstance().getConfiguration(this.loggerContext, configurationSource);
        this.loggerContext.start((Configuration)object);
        LOGGER.debug("Completed remote request to reconfigure.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if (!"config".equals(propertyChangeEvent.getPropertyName())) {
            return;
        }
        Notification notification = new Notification("com.apache.logging.log4j.core.jmx.config.reconfigured", this.getObjectName(), this.nextSeqNo(), this.now(), null);
        this.sendNotification(notification);
    }

    @Override
    public String getConfigText() throws IOException {
        return this.getConfigText(StandardCharsets.UTF_8.name());
    }

    @Override
    public String getConfigText(String string) throws IOException {
        try {
            ConfigurationSource configurationSource = this.loggerContext.getConfiguration().getConfigurationSource();
            ConfigurationSource configurationSource2 = configurationSource.resetInputStream();
            Charset charset = Charset.forName(string);
            return this.readContents(configurationSource2.getInputStream(), charset);
        } catch (Exception exception) {
            StringWriter stringWriter = new StringWriter(2048);
            exception.printStackTrace(new PrintWriter(stringWriter));
            return stringWriter.toString();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String readContents(InputStream inputStream, Charset charset) throws IOException {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, charset);
            StringBuilder stringBuilder = new StringBuilder(65536);
            char[] cArray = new char[4096];
            int n = -1;
            while ((n = inputStreamReader.read(cArray)) >= 0) {
                stringBuilder.append(cArray, 0, n);
            }
            String string = stringBuilder.toString();
            return string;
        } finally {
            Closer.closeSilently(inputStream);
            Closer.closeSilently(inputStreamReader);
        }
    }

    @Override
    public void setConfigText(String string, String string2) {
        LOGGER.debug("---------");
        LOGGER.debug("Remote request to reconfigure from config text.");
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(string.getBytes(string2));
            ConfigurationSource configurationSource = new ConfigurationSource(byteArrayInputStream);
            Configuration configuration = ConfigurationFactory.getInstance().getConfiguration(this.loggerContext, configurationSource);
            this.loggerContext.start(configuration);
            LOGGER.debug("Completed remote request to reconfigure from config text.");
        } catch (Exception exception) {
            String string3 = "Could not reconfigure from config text";
            LOGGER.error("Could not reconfigure from config text", (Throwable)exception);
            throw new IllegalArgumentException("Could not reconfigure from config text", exception);
        }
    }

    @Override
    public String getConfigName() {
        return this.getConfig().getName();
    }

    @Override
    public String getConfigClassName() {
        return this.getConfig().getClass().getName();
    }

    @Override
    public String getConfigFilter() {
        return String.valueOf(this.getConfig().getFilter());
    }

    @Override
    public Map<String, String> getConfigProperties() {
        return this.getConfig().getProperties();
    }

    @Override
    public ObjectName getObjectName() {
        return this.objectName;
    }

    private long nextSeqNo() {
        return this.sequenceNo.getAndIncrement();
    }

    private long now() {
        return System.currentTimeMillis();
    }
}

