package net.minecraft.util;

import org.apache.logging.log4j.*;
import java.io.*;

public class LoggingPrintStream extends PrintStream
{
    private static final String[] I;
    private static final Logger LOGGER;
    private final String domain;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void println(final String s) {
        this.logString(s);
    }
    
    @Override
    public void println(final Object o) {
        this.logString(String.valueOf(o));
    }
    
    static {
        I();
        LOGGER = LogManager.getLogger();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("?\u0019\u000f\u0004%JJ\t$_\u001f\u001f[cE\u001f\u001f", "dbrYe");
    }
    
    private void logString(final String s) {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        final StackTraceElement stackTraceElement = stackTrace[Math.min("   ".length(), stackTrace.length)];
        final Logger logger = LoggingPrintStream.LOGGER;
        final String s2 = LoggingPrintStream.I["".length()];
        final Object[] array = new Object[0x95 ^ 0x91];
        array["".length()] = this.domain;
        array[" ".length()] = stackTraceElement.getFileName();
        array["  ".length()] = stackTraceElement.getLineNumber();
        array["   ".length()] = s;
        logger.info(s2, array);
    }
    
    public LoggingPrintStream(final String domain, final OutputStream outputStream) {
        super(outputStream);
        this.domain = domain;
    }
}
