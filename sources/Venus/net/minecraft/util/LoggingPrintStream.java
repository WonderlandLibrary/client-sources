/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import java.io.OutputStream;
import java.io.PrintStream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingPrintStream
extends PrintStream {
    protected static final Logger LOGGER = LogManager.getLogger();
    protected final String domain;

    public LoggingPrintStream(String string, OutputStream outputStream) {
        super(outputStream);
        this.domain = string;
    }

    @Override
    public void println(@Nullable String string) {
        this.logString(string);
    }

    @Override
    public void println(Object object) {
        this.logString(String.valueOf(object));
    }

    protected void logString(@Nullable String string) {
        LOGGER.info("[{}]: {}", (Object)this.domain, (Object)string);
    }
}

