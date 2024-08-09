/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.beust.jcommander.Parameter
 *  com.beust.jcommander.validators.PositiveInteger
 */
package org.apache.logging.log4j.core.net.server;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.validators.PositiveInteger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEventListener;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.xml.XmlConfiguration;
import org.apache.logging.log4j.core.config.xml.XmlConfigurationFactory;
import org.apache.logging.log4j.core.net.server.LogEventBridge;
import org.apache.logging.log4j.core.util.BasicCommandLineArguments;
import org.apache.logging.log4j.core.util.InetAddressConverter;
import org.apache.logging.log4j.core.util.Log4jThread;
import org.apache.logging.log4j.util.Strings;

public abstract class AbstractSocketServer<T extends InputStream>
extends LogEventListener
implements Runnable {
    protected static final int MAX_PORT = 65534;
    private volatile boolean active = true;
    protected final LogEventBridge<T> logEventInput;
    protected final Logger logger;

    public AbstractSocketServer(int n, LogEventBridge<T> logEventBridge) {
        this.logger = LogManager.getLogger(this.getClass().getName() + '.' + n);
        this.logEventInput = Objects.requireNonNull(logEventBridge, "LogEventInput");
    }

    protected boolean isActive() {
        return this.active;
    }

    protected void setActive(boolean bl) {
        this.active = bl;
    }

    public Thread startNewThread() {
        Log4jThread log4jThread = new Log4jThread(this);
        log4jThread.start();
        return log4jThread;
    }

    public abstract void shutdown() throws Exception;

    public void awaitTermination(Thread thread2) throws Exception {
        String string;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (!((string = bufferedReader.readLine()) == null || string.equalsIgnoreCase("quit") || string.equalsIgnoreCase("stop") || string.equalsIgnoreCase("exit"))) {
        }
        this.shutdown();
        thread2.join();
    }

    protected static class ServerConfigurationFactory
    extends XmlConfigurationFactory {
        private final String path;

        public ServerConfigurationFactory(String string) {
            this.path = string;
        }

        @Override
        public Configuration getConfiguration(LoggerContext loggerContext, String string, URI uRI) {
            if (Strings.isNotEmpty(this.path)) {
                Object object;
                File file = null;
                ConfigurationSource configurationSource = null;
                try {
                    file = new File(this.path);
                    object = new FileInputStream(file);
                    configurationSource = new ConfigurationSource((InputStream)object, file);
                } catch (FileNotFoundException fileNotFoundException) {
                    // empty catch block
                }
                if (configurationSource == null) {
                    try {
                        object = new URL(this.path);
                        configurationSource = new ConfigurationSource(((URL)object).openStream(), (URL)object);
                    } catch (IOException iOException) {
                        // empty catch block
                    }
                }
                try {
                    if (configurationSource != null) {
                        return new XmlConfiguration(loggerContext, configurationSource);
                    }
                } catch (Exception exception) {
                    // empty catch block
                }
                System.err.println("Unable to process configuration at " + this.path + ", using default.");
            }
            return super.getConfiguration(loggerContext, string, uRI);
        }
    }

    protected static class CommandLineArguments
    extends BasicCommandLineArguments {
        @Parameter(names={"--config", "-c"}, description="Log4j configuration file location (path or URL).")
        private String configLocation;
        @Parameter(names={"--interactive", "-i"}, description="Accepts commands on standard input (\"exit\" is the only command).")
        private boolean interactive;
        @Parameter(names={"--port", "-p"}, validateWith=PositiveInteger.class, description="Server socket port.")
        private int port;
        @Parameter(names={"--localbindaddress", "-a"}, converter=InetAddressConverter.class, description="Server socket local bind address.")
        private InetAddress localBindAddress;

        protected CommandLineArguments() {
        }

        String getConfigLocation() {
            return this.configLocation;
        }

        int getPort() {
            return this.port;
        }

        protected boolean isInteractive() {
            return this.interactive;
        }

        void setConfigLocation(String string) {
            this.configLocation = string;
        }

        void setInteractive(boolean bl) {
            this.interactive = bl;
        }

        void setPort(int n) {
            this.port = n;
        }

        InetAddress getLocalBindAddress() {
            return this.localBindAddress;
        }

        void setLocalBindAddress(InetAddress inetAddress) {
            this.localBindAddress = inetAddress;
        }
    }
}

