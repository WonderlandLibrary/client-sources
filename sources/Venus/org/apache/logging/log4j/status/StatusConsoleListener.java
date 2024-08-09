/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.status;

import java.io.IOException;
import java.io.PrintStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.status.StatusData;
import org.apache.logging.log4j.status.StatusListener;

public class StatusConsoleListener
implements StatusListener {
    private Level level = Level.FATAL;
    private String[] filters;
    private final PrintStream stream;

    public StatusConsoleListener(Level level) {
        this(level, System.out);
    }

    public StatusConsoleListener(Level level, PrintStream printStream) {
        if (printStream == null) {
            throw new IllegalArgumentException("You must provide a stream to use for this listener.");
        }
        this.level = level;
        this.stream = printStream;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public Level getStatusLevel() {
        return this.level;
    }

    @Override
    public void log(StatusData statusData) {
        if (!this.filtered(statusData)) {
            this.stream.println(statusData.getFormattedStatus());
        }
    }

    public void setFilters(String ... stringArray) {
        this.filters = stringArray;
    }

    private boolean filtered(StatusData statusData) {
        if (this.filters == null) {
            return true;
        }
        String string = statusData.getStackTraceElement().getClassName();
        for (String string2 : this.filters) {
            if (!string.startsWith(string2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public void close() throws IOException {
        if (this.stream != System.out && this.stream != System.err) {
            this.stream.close();
        }
    }
}

