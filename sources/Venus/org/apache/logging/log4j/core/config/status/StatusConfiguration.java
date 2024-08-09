/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.status;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.util.FileUtils;
import org.apache.logging.log4j.core.util.NetUtils;
import org.apache.logging.log4j.status.StatusConsoleListener;
import org.apache.logging.log4j.status.StatusListener;
import org.apache.logging.log4j.status.StatusLogger;

public class StatusConfiguration {
    private static final PrintStream DEFAULT_STREAM = System.out;
    private static final Level DEFAULT_STATUS = Level.ERROR;
    private static final Verbosity DEFAULT_VERBOSITY = Verbosity.QUIET;
    private final Collection<String> errorMessages = Collections.synchronizedCollection(new LinkedList());
    private final StatusLogger logger = StatusLogger.getLogger();
    private volatile boolean initialized = false;
    private PrintStream destination = DEFAULT_STREAM;
    private Level status = DEFAULT_STATUS;
    private Verbosity verbosity = DEFAULT_VERBOSITY;
    private String[] verboseClasses;

    public void error(String string) {
        if (!this.initialized) {
            this.errorMessages.add(string);
        } else {
            this.logger.error(string);
        }
    }

    public StatusConfiguration withDestination(String string) {
        try {
            this.destination = this.parseStreamName(string);
        } catch (URISyntaxException uRISyntaxException) {
            this.error("Could not parse URI [" + string + "]. Falling back to default of stdout.");
            this.destination = DEFAULT_STREAM;
        } catch (FileNotFoundException fileNotFoundException) {
            this.error("File could not be found at [" + string + "]. Falling back to default of stdout.");
            this.destination = DEFAULT_STREAM;
        }
        return this;
    }

    private PrintStream parseStreamName(String string) throws URISyntaxException, FileNotFoundException {
        if (string == null || string.equalsIgnoreCase("out")) {
            return DEFAULT_STREAM;
        }
        if (string.equalsIgnoreCase("err")) {
            return System.err;
        }
        URI uRI = NetUtils.toURI(string);
        File file = FileUtils.fileFromUri(uRI);
        if (file == null) {
            return DEFAULT_STREAM;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        return new PrintStream(fileOutputStream, true);
    }

    public StatusConfiguration withStatus(String string) {
        this.status = Level.toLevel(string, null);
        if (this.status == null) {
            this.error("Invalid status level specified: " + string + ". Defaulting to ERROR.");
            this.status = Level.ERROR;
        }
        return this;
    }

    public StatusConfiguration withStatus(Level level) {
        this.status = level;
        return this;
    }

    public StatusConfiguration withVerbosity(String string) {
        this.verbosity = Verbosity.toVerbosity(string);
        return this;
    }

    public StatusConfiguration withVerboseClasses(String ... stringArray) {
        this.verboseClasses = stringArray;
        return this;
    }

    public void initialize() {
        if (!this.initialized) {
            if (this.status == Level.OFF) {
                this.initialized = true;
            } else {
                boolean bl = this.configureExistingStatusConsoleListener();
                if (!bl) {
                    this.registerNewStatusConsoleListener();
                }
                this.migrateSavedLogMessages();
            }
        }
    }

    private boolean configureExistingStatusConsoleListener() {
        boolean bl = false;
        for (StatusListener statusListener : this.logger.getListeners()) {
            if (!(statusListener instanceof StatusConsoleListener)) continue;
            StatusConsoleListener statusConsoleListener = (StatusConsoleListener)statusListener;
            statusConsoleListener.setLevel(this.status);
            this.logger.updateListenerLevel(this.status);
            if (this.verbosity == Verbosity.QUIET) {
                statusConsoleListener.setFilters(this.verboseClasses);
            }
            bl = true;
        }
        return bl;
    }

    private void registerNewStatusConsoleListener() {
        StatusConsoleListener statusConsoleListener = new StatusConsoleListener(this.status, this.destination);
        if (this.verbosity == Verbosity.QUIET) {
            statusConsoleListener.setFilters(this.verboseClasses);
        }
        this.logger.registerListener(statusConsoleListener);
    }

    private void migrateSavedLogMessages() {
        for (String string : this.errorMessages) {
            this.logger.error(string);
        }
        this.initialized = true;
        this.errorMessages.clear();
    }

    public static enum Verbosity {
        QUIET,
        VERBOSE;


        public static Verbosity toVerbosity(String string) {
            return Boolean.parseBoolean(string) ? VERBOSE : QUIET;
        }
    }
}

