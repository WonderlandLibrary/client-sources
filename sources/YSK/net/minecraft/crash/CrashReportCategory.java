package net.minecraft.crash;

import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.util.concurrent.*;
import net.minecraft.block.*;
import com.google.common.collect.*;
import java.util.*;

public class CrashReportCategory
{
    private final List<Entry> children;
    private static final String[] I;
    private final CrashReport crashReport;
    private final String name;
    private StackTraceElement[] stackTrace;
    
    public boolean firstTwoElementsOfStackTraceMatch(final StackTraceElement stackTraceElement, final StackTraceElement stackTraceElement2) {
        if (this.stackTrace.length == 0 || stackTraceElement == null) {
            return "".length() != 0;
        }
        final StackTraceElement stackTraceElement3 = this.stackTrace["".length()];
        if (stackTraceElement3.isNativeMethod() != stackTraceElement.isNativeMethod() || !stackTraceElement3.getClassName().equals(stackTraceElement.getClassName()) || !stackTraceElement3.getFileName().equals(stackTraceElement.getFileName()) || !stackTraceElement3.getMethodName().equals(stackTraceElement.getMethodName())) {
            return "".length() != 0;
        }
        int n;
        if (stackTraceElement2 != null) {
            n = " ".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        int n2;
        if (this.stackTrace.length > " ".length()) {
            n2 = " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        if (n != n2) {
            return "".length() != 0;
        }
        if (stackTraceElement2 != null && !this.stackTrace[" ".length()].equals(stackTraceElement2)) {
            return "".length() != 0;
        }
        this.stackTrace["".length()] = stackTraceElement;
        return " ".length() != 0;
    }
    
    public int getPrunedStackTrace(final int n) {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length <= 0) {
            return "".length();
        }
        this.stackTrace = new StackTraceElement[stackTrace.length - "   ".length() - n];
        System.arraycopy(stackTrace, "   ".length() + n, this.stackTrace, "".length(), this.stackTrace.length);
        return this.stackTrace.length;
    }
    
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
            if (2 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static void addBlockInfo(final CrashReportCategory crashReportCategory, final BlockPos blockPos, final IBlockState blockState) {
        crashReportCategory.addCrashSectionCallable(CrashReportCategory.I[0x24 ^ 0x37], new Callable<String>(blockState) {
            private final IBlockState val$state;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
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
                    if (3 != 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public String call() throws Exception {
                return this.val$state.toString();
            }
        });
        crashReportCategory.addCrashSectionCallable(CrashReportCategory.I[0x8C ^ 0x98], new Callable<String>(blockPos) {
            private final BlockPos val$pos;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
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
                    if (4 <= 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public String call() throws Exception {
                return CrashReportCategory.getCoordinateInfo(this.val$pos);
            }
        });
    }
    
    public void addCrashSectionThrowable(final String s, final Throwable t) {
        this.addCrashSection(s, t);
    }
    
    public void addCrashSectionCallable(final String s, final Callable<String> callable) {
        try {
            this.addCrashSection(s, callable.call());
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        catch (Throwable t) {
            this.addCrashSectionThrowable(s, t);
        }
    }
    
    public static void addBlockInfo(final CrashReportCategory crashReportCategory, final BlockPos blockPos, final Block block, final int n) {
        crashReportCategory.addCrashSectionCallable(CrashReportCategory.I[0x8C ^ 0x9C], new Callable<String>(Block.getIdFromBlock(block), block) {
            private final int val$i;
            private static final String[] I;
            private final Block val$blockIn;
            
            @Override
            public String call() throws Exception {
                try {
                    final String s = CrashReportCategory$1.I["".length()];
                    final Object[] array = new Object["   ".length()];
                    array["".length()] = this.val$i;
                    array[" ".length()] = this.val$blockIn.getUnlocalizedName();
                    array["  ".length()] = this.val$blockIn.getClass().getCanonicalName();
                    return String.format(s, array);
                }
                catch (Throwable t) {
                    return CrashReportCategory$1.I[" ".length()] + this.val$i;
                }
            }
            
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
                    if (-1 == 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            static {
                I();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I(",\u0010tAr\u0001t|G$E{{Br\u0016}", "eTTbW");
                CrashReportCategory$1.I[" ".length()] = I("\u0019 Jt", "PdjWC");
            }
        });
        crashReportCategory.addCrashSectionCallable(CrashReportCategory.I[0xA1 ^ 0xB0], new Callable<String>(n) {
            private final int val$blockData;
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
                    if (true != true) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public String call() throws Exception {
                if (this.val$blockData < 0) {
                    return CrashReportCategory$2.I["".length()] + this.val$blockData + CrashReportCategory$2.I[" ".length()];
                }
                final String s = CrashReportCategory$2.I["  ".length()];
                final Object[] array = new Object[" ".length()];
                array["".length()] = Integer.toBinaryString(this.val$blockData);
                final String replace = String.format(s, array).replace(CrashReportCategory$2.I["   ".length()], CrashReportCategory$2.I[0x60 ^ 0x64]);
                final String s2 = CrashReportCategory$2.I[0x14 ^ 0x11];
                final Object[] array2 = new Object["  ".length()];
                array2["".length()] = this.val$blockData;
                array2[" ".length()] = replace;
                return String.format(s2, array2);
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            private static void I() {
                (I = new String[0x8C ^ 0x8A])["".length()] = I("9\u001a\u0001-\u0019\u001b\u001aUc^+\u001b\u001ec", "ltjCv");
                CrashReportCategory$2.I[" ".length()] = I("Y", "pqUBl");
                CrashReportCategory$2.I["  ".length()] = I("Fp\u0015", "cDfXF");
                CrashReportCategory$2.I["   ".length()] = I("j", "JwUKh");
                CrashReportCategory$2.I[0x32 ^ 0x36] = I("T", "duZSI");
                CrashReportCategory$2.I[0x7A ^ 0x7F] = I("hdl<Sbux V|q\u0010x\\me*}Ai&", "MUHXs");
            }
            
            static {
                I();
            }
        });
        crashReportCategory.addCrashSectionCallable(CrashReportCategory.I[0x41 ^ 0x53], new Callable<String>(blockPos) {
            private final BlockPos val$pos;
            
            @Override
            public String call() throws Exception {
                return CrashReportCategory.getCoordinateInfo(this.val$pos);
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
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
                    if (2 < 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
    }
    
    public void trimStackTraceEntriesFromBottom(final int n) {
        final StackTraceElement[] stackTrace = new StackTraceElement[this.stackTrace.length - n];
        System.arraycopy(this.stackTrace, "".length(), stackTrace, "".length(), stackTrace.length);
        this.stackTrace = stackTrace;
    }
    
    public StackTraceElement[] getStackTrace() {
        return this.stackTrace;
    }
    
    private static void I() {
        (I = new String[0x88 ^ 0x9D])["".length()] = I("rh`\t]rh`\t]rh`\tQzfw\u001c", "WFRoq");
        CrashReportCategory.I[" ".length()] = I("5\u001b5&\u0007XToo\u0007NQ#fF\u0006]", "btGJc");
        CrashReportCategory.I["  ".length()] = I("a\r\u0000\u001c>;h\u0014\u0007?-!\u001c\tq>'\u0000\u00025i$\u001d\rx", "IHrnQ");
        CrashReportCategory.I["   ".length()] = I("Ui", "yIHzW");
        CrashReportCategory.I[0xA ^ 0xE] = I("0-4\u0004(Iei\u000b7S`%Ff\u0017id\u000ec\u001a+aO'_`%Qc\u0010*/\u001e\"\u001a+2J!\u001f*\"\u00010S`%Fs_`%J7\u001ced\u000eoAptFf\u0017l", "sEAjC");
        CrashReportCategory.I[0x5A ^ 0x5F] = I("B\u000f%\u0015&\u0018j1\u000e'\u000e#9\u0000i\t\"\"\t\"J&8\u0004`", "jJWgI");
        CrashReportCategory.I[0xC2 ^ 0xC4] = I("^A", "raKOz");
        CrashReportCategory.I[0x48 ^ 0x4F] = I("\u001e\u0001 &&\"^ggl(Hb+rl\u0007(!=-\r)<i/\f2!\"?Db+ei\u0000g;&lA#cl(Hg-%#\u0007,<ii\u0000k\u007fei\u0000g;&lA#c{yQkj-e", "LdGOI");
        CrashReportCategory.I[0x24 ^ 0x2C] = I("}<7\u0003+'Y#\u0018*1\u0010+\u0016d\"\u00167\u001d u\u0015*\u0012m", "UyEqD");
        CrashReportCategory.I[0xAD ^ 0xA4] = I("EyJ", "hTjpT");
        CrashReportCategory.I[0xBA ^ 0xB0] = I("FAtR", "flYXm");
        CrashReportCategory.I[0xBA ^ 0xB1] = I("!1\"\u0018\f\t'l", "eTVye");
        CrashReportCategory.I[0x2 ^ 0xE] = I("G|", "MuKlj");
        CrashReportCategory.I[0xD ^ 0x0] = I("@J", "zjjkW");
        CrashReportCategory.I[0x1C ^ 0x12] = I("N\u0004\u00047\u0000/#\u00027\u0000!m", "DWpVc");
        CrashReportCategory.I[0xBE ^ 0xB1] = I("oj\u00150D", "ectDd");
        CrashReportCategory.I[0x2 ^ 0x12] = I("\u0018\t\f\u0006\u0019z\u0011\u001a\u0015\u0017", "Zecer");
        CrashReportCategory.I[0x38 ^ 0x29] = I("\u0003\n\f-\u0006a\u0002\u0002:\fa\u0010\u0002\"\u0018$", "AfcNm");
        CrashReportCategory.I[0x29 ^ 0x3B] = I("\u00048\r\u000f9f8\r\u000f32=\r\u0002", "FTblR");
        CrashReportCategory.I[0x66 ^ 0x75] = I(",8\u0003\u0004\n", "nTlga");
        CrashReportCategory.I[0x17 ^ 0x3] = I("88**=Z8**7\u000e=*'", "zTEIV");
    }
    
    public void addCrashSection(final String s, final Object o) {
        this.children.add(new Entry(s, o));
    }
    
    public static String getCoordinateInfo(final BlockPos blockPos) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        final StringBuilder sb = new StringBuilder();
        try {
            final StringBuilder sb2 = sb;
            final String s = CrashReportCategory.I[" ".length()];
            final Object[] array = new Object["   ".length()];
            array["".length()] = x;
            array[" ".length()] = y;
            array["  ".length()] = z;
            sb2.append(String.format(s, array));
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        catch (Throwable t) {
            sb.append(CrashReportCategory.I["  ".length()]);
        }
        sb.append(CrashReportCategory.I["   ".length()]);
        try {
            final int n = x >> (0x8 ^ 0xC);
            final int n2 = z >> (0x9E ^ 0x9A);
            final int n3 = x & (0x9E ^ 0x91);
            final int n4 = y >> (0x7 ^ 0x3);
            final int n5 = z & (0x55 ^ 0x5A);
            final int n6 = n << (0x14 ^ 0x10);
            final int n7 = n2 << (0x5 ^ 0x1);
            final int n8 = (n + " ".length() << (0x3C ^ 0x38)) - " ".length();
            final int n9 = (n2 + " ".length() << (0x89 ^ 0x8D)) - " ".length();
            final StringBuilder sb3 = sb;
            final String s2 = CrashReportCategory.I[0x71 ^ 0x75];
            final Object[] array2 = new Object[0xC ^ 0x5];
            array2["".length()] = n3;
            array2[" ".length()] = n4;
            array2["  ".length()] = n5;
            array2["   ".length()] = n;
            array2[0x15 ^ 0x11] = n2;
            array2[0x4 ^ 0x1] = n6;
            array2[0x6B ^ 0x6D] = n7;
            array2[0x7D ^ 0x7A] = n8;
            array2[0x5 ^ 0xD] = n9;
            sb3.append(String.format(s2, array2));
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        catch (Throwable t2) {
            sb.append(CrashReportCategory.I[0x60 ^ 0x65]);
        }
        sb.append(CrashReportCategory.I[0x8D ^ 0x8B]);
        try {
            final int n10 = x >> (0x3 ^ 0xA);
            final int n11 = z >> (0x95 ^ 0x9C);
            final int n12 = n10 << (0xAE ^ 0xAB);
            final int n13 = n11 << (0x75 ^ 0x70);
            final int n14 = (n10 + " ".length() << (0x12 ^ 0x17)) - " ".length();
            final int n15 = (n11 + " ".length() << (0xBB ^ 0xBE)) - " ".length();
            final int n16 = n10 << (0x3 ^ 0xA);
            final int n17 = n11 << (0xD ^ 0x4);
            final int n18 = (n10 + " ".length() << (0x8A ^ 0x83)) - " ".length();
            final int n19 = (n11 + " ".length() << (0x68 ^ 0x61)) - " ".length();
            final StringBuilder sb4 = sb;
            final String s3 = CrashReportCategory.I[0x89 ^ 0x8E];
            final Object[] array3 = new Object[0xBC ^ 0xB6];
            array3["".length()] = n10;
            array3[" ".length()] = n11;
            array3["  ".length()] = n12;
            array3["   ".length()] = n13;
            array3[0x2E ^ 0x2A] = n14;
            array3[0xB3 ^ 0xB6] = n15;
            array3[0x8F ^ 0x89] = n16;
            array3[0x2E ^ 0x29] = n17;
            array3[0xBF ^ 0xB7] = n18;
            array3[0x26 ^ 0x2F] = n19;
            sb4.append(String.format(s3, array3));
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        catch (Throwable t3) {
            sb.append(CrashReportCategory.I[0x6B ^ 0x63]);
        }
        return sb.toString();
    }
    
    public CrashReportCategory(final CrashReport crashReport, final String name) {
        this.children = (List<Entry>)Lists.newArrayList();
        this.stackTrace = new StackTraceElement["".length()];
        this.crashReport = crashReport;
        this.name = name;
    }
    
    public void appendToStringBuilder(final StringBuilder sb) {
        sb.append(CrashReportCategory.I[0xCF ^ 0xC6]).append(this.name).append(CrashReportCategory.I[0x51 ^ 0x5B]);
        sb.append(CrashReportCategory.I[0xA4 ^ 0xAF]);
        final Iterator<Entry> iterator = this.children.iterator();
        "".length();
        if (1 < 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Entry entry = iterator.next();
            sb.append(CrashReportCategory.I[0xA1 ^ 0xAD]);
            sb.append(entry.getKey());
            sb.append(CrashReportCategory.I[0xD ^ 0x0]);
            sb.append(entry.getValue());
        }
        if (this.stackTrace != null && this.stackTrace.length > 0) {
            sb.append(CrashReportCategory.I[0x23 ^ 0x2D]);
            final StackTraceElement[] stackTrace;
            final int length = (stackTrace = this.stackTrace).length;
            int i = "".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
            while (i < length) {
                final StackTraceElement stackTraceElement = stackTrace[i];
                sb.append(CrashReportCategory.I[0xBA ^ 0xB5]);
                sb.append(stackTraceElement.toString());
                ++i;
            }
        }
    }
    
    public static String getCoordinateInfo(final double n, final double n2, final double n3) {
        final String s = CrashReportCategory.I["".length()];
        final Object[] array = new Object[0x6D ^ 0x69];
        array["".length()] = n;
        array[" ".length()] = n2;
        array["  ".length()] = n3;
        array["   ".length()] = getCoordinateInfo(new BlockPos(n, n2, n3));
        return String.format(s, array);
    }
    
    static {
        I();
    }
    
    static class Entry
    {
        private final String key;
        private static final String[] I;
        private final String value;
        
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
                if (-1 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("4\u0013\u0014\u0012#\u0006\u0013$", "JmZGo");
            Entry.I[" ".length()] = I("751*=\u0006\u0019\n\u0006O", "IKtxo");
            Entry.I["  ".length()] = I("uR", "OrqUv");
        }
        
        public String getValue() {
            return this.value;
        }
        
        static {
            I();
        }
        
        public String getKey() {
            return this.key;
        }
        
        public Entry(final String key, final Object o) {
            this.key = key;
            if (o == null) {
                this.value = Entry.I["".length()];
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            else if (o instanceof Throwable) {
                final Throwable t = (Throwable)o;
                this.value = Entry.I[" ".length()] + t.getClass().getSimpleName() + Entry.I["  ".length()] + t.getMessage();
                "".length();
                if (2 <= -1) {
                    throw null;
                }
            }
            else {
                this.value = o.toString();
            }
        }
    }
}
