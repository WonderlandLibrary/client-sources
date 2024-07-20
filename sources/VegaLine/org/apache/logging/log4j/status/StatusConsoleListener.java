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

    public StatusConsoleListener(Level level, PrintStream stream) {
        if (stream == null) {
            throw new IllegalArgumentException("You must provide a stream to use for this listener.");
        }
        this.level = level;
        this.stream = stream;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public Level getStatusLevel() {
        return this.level;
    }

    @Override
    public void log(StatusData data) {
        if (!this.filtered(data)) {
            this.stream.println(data.getFormattedStatus());
        }
    }

    public void setFilters(String ... filters) {
        this.filters = filters;
    }

    private boolean filtered(StatusData data) {
        if (this.filters == null) {
            return false;
        }
        String caller = data.getStackTraceElement().getClassName();
        for (String filter : this.filters) {
            if (!caller.startsWith(filter)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void close() throws IOException {
        if (this.stream != System.out && this.stream != System.err) {
            this.stream.close();
        }
    }
}

