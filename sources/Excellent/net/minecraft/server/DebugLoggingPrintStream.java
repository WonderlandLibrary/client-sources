package net.minecraft.server;

import net.minecraft.util.LoggingPrintStream;

import java.io.OutputStream;

public class DebugLoggingPrintStream extends LoggingPrintStream
{
    public DebugLoggingPrintStream(String domainIn, OutputStream outStream)
    {
        super(domainIn, outStream);
    }

    protected void logString(String string)
    {
        StackTraceElement[] astacktraceelement = Thread.currentThread().getStackTrace();
        StackTraceElement stacktraceelement = astacktraceelement[Math.min(3, astacktraceelement.length)];
        LOGGER.info("[{}]@.({}:{}): {}", this.domain, stacktraceelement.getFileName(), stacktraceelement.getLineNumber(), string);
    }
}
