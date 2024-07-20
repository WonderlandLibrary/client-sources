/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import java.io.OutputStream;
import java.io.PrintStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingPrintStream
extends PrintStream {
    protected static final Logger LOGGER = LogManager.getLogger();
    protected final String domain;

    public LoggingPrintStream(String domainIn, OutputStream outStream) {
        super(outStream);
        this.domain = domainIn;
    }

    @Override
    public void println(String p_println_1_) {
        this.logString(p_println_1_);
    }

    @Override
    public void println(Object p_println_1_) {
        this.logString(String.valueOf(p_println_1_));
    }

    protected void logString(String string) {
        LOGGER.info("[{}]: {}", (Object)this.domain, (Object)string);
    }
}

