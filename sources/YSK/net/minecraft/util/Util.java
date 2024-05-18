package net.minecraft.util;

import org.apache.logging.log4j.*;
import java.util.concurrent.*;

public class Util
{
    private static final String[] I;
    
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
            if (0 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static <V> V func_181617_a(final FutureTask<V> futureTask, final Logger logger) {
        try {
            futureTask.run();
            return futureTask.get();
        }
        catch (ExecutionException ex) {
            logger.fatal(Util.I[0x58 ^ 0x5F], (Throwable)ex);
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        catch (InterruptedException ex2) {
            logger.fatal(Util.I[0x6F ^ 0x67], (Throwable)ex2);
        }
        return null;
    }
    
    public static EnumOS getOSType() {
        final String lowerCase = System.getProperty(Util.I["".length()]).toLowerCase();
        EnumOS enumOS;
        if (lowerCase.contains(Util.I[" ".length()])) {
            enumOS = EnumOS.WINDOWS;
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else if (lowerCase.contains(Util.I["  ".length()])) {
            enumOS = EnumOS.OSX;
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else if (lowerCase.contains(Util.I["   ".length()])) {
            enumOS = EnumOS.SOLARIS;
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else if (lowerCase.contains(Util.I[0xA9 ^ 0xAD])) {
            enumOS = EnumOS.SOLARIS;
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else if (lowerCase.contains(Util.I[0x49 ^ 0x4C])) {
            enumOS = EnumOS.LINUX;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (lowerCase.contains(Util.I[0x2A ^ 0x2C])) {
            enumOS = EnumOS.LINUX;
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            enumOS = EnumOS.UNKNOWN;
        }
        return enumOS;
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[0xAF ^ 0xA6])["".length()] = I("\u0005*|\u000b\"\u0007<", "jYReC");
        Util.I[" ".length()] = I("\u001e9\r", "iPcYT");
        Util.I["  ".length()] = I("\u00002\n", "mSiKp");
        Util.I["   ".length()] = I("\u001b$\u0015\u0012\u0014\u00018", "hKysf");
        Util.I[0xC1 ^ 0xC5] = I("\u0015 ;9\u0003", "fUUVp");
        Util.I[0x29 ^ 0x2C] = I("'\n\u00011\u0017", "KcoDo");
        Util.I[0x99 ^ 0x9F] = I("\u0016&\u001d?", "cHtGh");
        Util.I[0x18 ^ 0x1F] = I("70\u0003\u0001\u001cR'\t\u000b\r\u00076\u0018\u0000\tR6\u0010\u001d\u0005", "rBqnn");
        Util.I[0x97 ^ 0x9F] = I("\u0006\u00051\u001a\u0000c\u0012;\u0010\u00116\u0003*\u001b\u0015c\u0003\"\u0006\u0019", "CwCur");
    }
    
    public enum EnumOS
    {
        OSX(EnumOS.I["   ".length()], "   ".length()), 
        UNKNOWN(EnumOS.I[0x14 ^ 0x10], 0x38 ^ 0x3C), 
        WINDOWS(EnumOS.I["  ".length()], "  ".length());
        
        private static final String[] I;
        
        SOLARIS(EnumOS.I[" ".length()], " ".length()), 
        LINUX(EnumOS.I["".length()], "".length());
        
        private static final EnumOS[] ENUM$VALUES;
        
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
                if (true != true) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[0x64 ^ 0x61])["".length()] = I("\u001c\u000e\u0007-\u000f", "PGIxW");
            EnumOS.I[" ".length()] = I("\u00008>\u0006\n\u001a$", "SwrGX");
            EnumOS.I["  ".length()] = I("\u000e\u0004\t\r8\u000e\u001e", "YMGIw");
            EnumOS.I["   ".length()] = I("9\n\u0001", "vYYpY");
            EnumOS.I[0x31 ^ 0x35] = I(" $;\u001b\r\"$", "ujpUB");
        }
        
        private EnumOS(final String s, final int n) {
        }
        
        static {
            I();
            final EnumOS[] enum$VALUES = new EnumOS[0xD ^ 0x8];
            enum$VALUES["".length()] = EnumOS.LINUX;
            enum$VALUES[" ".length()] = EnumOS.SOLARIS;
            enum$VALUES["  ".length()] = EnumOS.WINDOWS;
            enum$VALUES["   ".length()] = EnumOS.OSX;
            enum$VALUES[0xAE ^ 0xAA] = EnumOS.UNKNOWN;
            ENUM$VALUES = enum$VALUES;
        }
    }
}
