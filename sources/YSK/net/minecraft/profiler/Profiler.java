package net.minecraft.profiler;

import optfine.*;
import net.minecraft.client.renderer.*;
import com.google.common.collect.*;
import org.apache.logging.log4j.*;
import java.util.*;

public class Profiler
{
    private final List timestampList;
    private final Map profilingMap;
    private static final int HASH_DISPLAY;
    public boolean profilerGlobalEnabled;
    private static final String __OBFID;
    private static final String SCHEDULED_EXECUTABLES;
    private static final int HASH_SCHEDULED_EXECUTABLES;
    private static final String TICK;
    private static final String DISPLAY;
    private static final String PRE_RENDER_ERRORS;
    private static final int HASH_PRE_RENDER_ERRORS;
    private boolean profilerLocalEnabled;
    private static final String[] I;
    private final List sectionList;
    private String profilingSection;
    private static final int HASH_TICK;
    private static final String RENDER;
    private static final Logger logger;
    private static final int HASH_RENDER;
    public boolean profilingEnabled;
    
    public void startSection(final String s) {
        if (Lagometer.isActive()) {
            final int hashCode = s.hashCode();
            if (hashCode == Profiler.HASH_SCHEDULED_EXECUTABLES && s.equals(Profiler.I[0x38 ^ 0x35])) {
                Lagometer.timerScheduledExecutables.start();
                "".length();
                if (1 == 2) {
                    throw null;
                }
            }
            else if (hashCode == Profiler.HASH_TICK && s.equals(Profiler.I[0x30 ^ 0x3E]) && Config.isMinecraftThread()) {
                Lagometer.timerScheduledExecutables.end();
                Lagometer.timerTick.start();
                "".length();
                if (4 == 3) {
                    throw null;
                }
            }
            else if (hashCode == Profiler.HASH_PRE_RENDER_ERRORS && s.equals(Profiler.I[0x82 ^ 0x8D])) {
                Lagometer.timerTick.end();
            }
        }
        if (Config.isFastRender()) {
            final int hashCode2 = s.hashCode();
            if (hashCode2 == Profiler.HASH_RENDER && s.equals(Profiler.I[0xE ^ 0x1E])) {
                GlStateManager.clearEnabled = ("".length() != 0);
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            else if (hashCode2 == Profiler.HASH_DISPLAY && s.equals(Profiler.I[0x4A ^ 0x5B])) {
                GlStateManager.clearEnabled = (" ".length() != 0);
            }
        }
        if (this.profilerLocalEnabled && this.profilingEnabled) {
            if (this.profilingSection.length() > 0) {
                this.profilingSection = String.valueOf(this.profilingSection) + Profiler.I[0xA1 ^ 0xB3];
            }
            this.profilingSection = String.valueOf(this.profilingSection) + s;
            this.sectionList.add(this.profilingSection);
            this.timestampList.add(System.nanoTime());
        }
    }
    
    public Profiler() {
        this.sectionList = Lists.newArrayList();
        this.timestampList = Lists.newArrayList();
        this.profilingSection = Profiler.I[0xA4 ^ 0xAF];
        this.profilingMap = Maps.newHashMap();
        this.profilerGlobalEnabled = (" ".length() != 0);
        this.profilerLocalEnabled = this.profilerGlobalEnabled;
    }
    
    public String getNameOfLastSection() {
        String s;
        if (this.sectionList.size() == 0) {
            s = Profiler.I[0x8E ^ 0x90];
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            s = this.sectionList.get(this.sectionList.size() - " ".length());
        }
        return s;
    }
    
    public void clearProfiling() {
        this.profilingMap.clear();
        this.profilingSection = Profiler.I[0x4B ^ 0x47];
        this.sectionList.clear();
        this.profilerLocalEnabled = this.profilerGlobalEnabled;
    }
    
    public void endStartSection(final String s) {
        if (this.profilerLocalEnabled) {
            this.endSection();
            this.startSection(s);
        }
    }
    
    static {
        I();
        DISPLAY = Profiler.I["".length()];
        SCHEDULED_EXECUTABLES = Profiler.I[" ".length()];
        TICK = Profiler.I["  ".length()];
        RENDER = Profiler.I["   ".length()];
        __OBFID = Profiler.I[0xBD ^ 0xB9];
        PRE_RENDER_ERRORS = Profiler.I[0xAB ^ 0xAE];
        logger = LogManager.getLogger();
        HASH_SCHEDULED_EXECUTABLES = Profiler.I[0x45 ^ 0x43].hashCode();
        HASH_TICK = Profiler.I[0x90 ^ 0x97].hashCode();
        HASH_PRE_RENDER_ERRORS = Profiler.I[0xB7 ^ 0xBF].hashCode();
        HASH_RENDER = Profiler.I[0x4 ^ 0xD].hashCode();
        HASH_DISPLAY = Profiler.I[0xF ^ 0x5].hashCode();
    }
    
    public void endSection() {
        if (this.profilerLocalEnabled && this.profilingEnabled) {
            final long nanoTime = System.nanoTime();
            final long longValue = this.timestampList.remove(this.timestampList.size() - " ".length());
            this.sectionList.remove(this.sectionList.size() - " ".length());
            final long n = nanoTime - longValue;
            if (this.profilingMap.containsKey(this.profilingSection)) {
                this.profilingMap.put(this.profilingSection, this.profilingMap.get(this.profilingSection) + n);
                "".length();
                if (0 < -1) {
                    throw null;
                }
            }
            else {
                this.profilingMap.put(this.profilingSection, n);
            }
            if (n > 100000000L) {
                Profiler.logger.warn(Profiler.I[0x57 ^ 0x44] + this.profilingSection + Profiler.I[0x7C ^ 0x68] + n / 1000000.0 + Profiler.I[0xAB ^ 0xBE]);
            }
            String profilingSection;
            if (!this.sectionList.isEmpty()) {
                profilingSection = this.sectionList.get(this.sectionList.size() - " ".length());
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            else {
                profilingSection = Profiler.I[0xD ^ 0x1B];
            }
            this.profilingSection = profilingSection;
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
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public List getProfilingData(String string) {
        if (!(this.profilerLocalEnabled = this.profilerGlobalEnabled)) {
            final Result[] array = new Result[" ".length()];
            array["".length()] = new Result(Profiler.I[0x1C ^ 0xB], 0.0, 0.0);
            return new ArrayList(Arrays.asList(array));
        }
        if (!this.profilingEnabled) {
            return null;
        }
        long longValue;
        if (this.profilingMap.containsKey(Profiler.I[0x1E ^ 0x6])) {
            longValue = this.profilingMap.get(Profiler.I[0x49 ^ 0x50]);
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            longValue = 0L;
        }
        long n = longValue;
        long longValue2;
        if (this.profilingMap.containsKey(string)) {
            longValue2 = this.profilingMap.get(string);
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            longValue2 = -1L;
        }
        final long n2 = longValue2;
        final ArrayList arrayList = Lists.newArrayList();
        if (string.length() > 0) {
            string = String.valueOf(string) + Profiler.I[0x98 ^ 0x82];
        }
        long n3 = 0L;
        final Iterator<String> iterator = this.profilingMap.keySet().iterator();
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String next = iterator.next();
            if (next.length() > string.length() && next.startsWith(string) && next.indexOf(Profiler.I[0x61 ^ 0x7A], string.length() + " ".length()) < 0) {
                n3 += (long)this.profilingMap.get(next);
            }
        }
        final float n4 = n3;
        if (n3 < n2) {
            n3 = n2;
        }
        if (n < n3) {
            n = n3;
        }
        final Iterator<String> iterator2 = this.profilingMap.keySet().iterator();
        "".length();
        if (3 == 1) {
            throw null;
        }
        while (iterator2.hasNext()) {
            final String s = iterator2.next();
            if (s.length() > string.length() && s.startsWith(string) && s.indexOf(Profiler.I[0x38 ^ 0x24], string.length() + " ".length()) < 0) {
                final long longValue3 = this.profilingMap.get(s);
                arrayList.add(new Result(s.substring(string.length()), longValue3 * 100.0 / n3, longValue3 * 100.0 / n));
            }
        }
        final Iterator<Object> iterator3 = this.profilingMap.keySet().iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator3.hasNext()) {
            final Object next2 = iterator3.next();
            this.profilingMap.put(next2, (long)this.profilingMap.get(next2) * 950L / 1000L);
        }
        if (n3 > n4) {
            arrayList.add(new Result(Profiler.I[0x1E ^ 0x3], (n3 - n4) * 100.0 / n3, (n3 - n4) * 100.0 / n));
        }
        Collections.sort((List<Comparable>)arrayList);
        arrayList.add("".length(), new Result(string, 100.0, n3 * 100.0 / n));
        return arrayList;
    }
    
    private static void I() {
        (I = new String[0x11 ^ 0xE])["".length()] = I("\u001d%\u0001#*\u00185", "yLrSF");
        Profiler.I[" ".length()] = I("%:$\f'#5)\r\u0006.</\u001c77; \f0", "VYLiC");
        Profiler.I["  ".length()] = I("::72", "NSTYe");
        Profiler.I["   ".length()] = I("!#\u00051\u0012!", "SFkUw");
        Profiler.I[0xC2 ^ 0xC6] = I("\u0015\u001d:igfaTmna", "VQeYW");
        Profiler.I[0x90 ^ 0x95] = I("3?\u0007>+-)\u0007\u001e\u000b1?\r\u001e=", "CMblN");
        Profiler.I[0x38 ^ 0x3E] = I("\u0004:2\u0004\u0002\u00025?\u0005#\u000f<9\u0014\u0012\u0016;6\u0004\u0015", "wYZaf");
        Profiler.I[0xAB ^ 0xAC] = I("\u0010\u000b\u0014\u001a", "dbwqB");
        Profiler.I[0xE ^ 0x6] = I("\u0016\u001e\u0014\u001a#\b\b\u0014:\u0003\u0014\u001e\u001e:5", "flqHF");
        Profiler.I[0x1F ^ 0x16] = I("(\u0015=\u0001\"(", "ZpSeG");
        Profiler.I[0x46 ^ 0x4C] = I("\u000b\u001a\u0002\u0014-\u000e\n", "osqdA");
        Profiler.I[0x89 ^ 0x82] = I("", "GTztq");
        Profiler.I[0x69 ^ 0x65] = I("", "tYxwe");
        Profiler.I[0xBB ^ 0xB6] = I("8&\u0006$1>)\u000b%\u00103 \r4!*'\u0002$&", "KEnAU");
        Profiler.I[0x64 ^ 0x6A] = I("\u0004%\u001a\n", "pLyan");
        Profiler.I[0x56 ^ 0x59] = I("\u0003+(;\u000b\u001d=(\u001b+\u0001+\"\u001b\u001d", "sYMin");
        Profiler.I[0x14 ^ 0x4] = I("7+\u00066\u00157", "ENhRp");
        Profiler.I[0x55 ^ 0x44] = I("\u001c$ \u0018-\u00194", "xMShA");
        Profiler.I[0x8B ^ 0x99] = I("e", "KDowm");
        Profiler.I[0x2C ^ 0x3F] = I("5\u00035\u000e\u001f\u000e\u00056\fL\u0015L,\n\u0000\u000f\u0002?K\u001f\t\u0003x\u0007\u0004\b\u000byKL", "flXkk");
        Profiler.I[0x59 ^ 0x4D] = I("^j=\u001a\"\u0012j(\u0005?\u00162i", "yJIuM");
        Profiler.I[0x94 ^ 0x81] = I("O&0", "oKCkR");
        Profiler.I[0x4A ^ 0x5C] = I("", "YpMQx");
        Profiler.I[0x16 ^ 0x1] = I("#'\u001c,", "QHsXc");
        Profiler.I[0xA3 ^ 0xBB] = I("+\u0003-8", "YlBLq");
        Profiler.I[0x53 ^ 0x4A] = I("6;\u000b&", "DTdRU");
        Profiler.I[0x2A ^ 0x30] = I("i", "GGUDX");
        Profiler.I[0x7E ^ 0x65] = I("t", "ZRIUy");
        Profiler.I[0x6E ^ 0x72] = I("Z", "tkgwp");
        Profiler.I[0x99 ^ 0x84] = I("3<9\u0001\u0010%;,\u0018\u0010\"", "FRJqu");
        Profiler.I[0x80 ^ 0x9E] = I("0\u0014\u0003\u0011\u0007$\u0016\u0003\u0007", "kAMZI");
    }
    
    public static final class Result implements Comparable
    {
        private static final String[] I;
        public double field_76330_b;
        private static final String __OBFID;
        public String field_76331_c;
        public double field_76332_a;
        
        public int func_76329_a() {
            return (this.field_76331_c.hashCode() & 6905653 + 10875696 - 16984882 + 10388343) + (1608171 + 3200973 - 2679769 + 2344549);
        }
        
        public Result(final String field_76331_c, final double field_76332_a, final double field_76330_b) {
            this.field_76331_c = field_76331_c;
            this.field_76332_a = field_76332_a;
            this.field_76330_b = field_76330_b;
        }
        
        public int compareTo(final Result result) {
            int n;
            if (result.field_76332_a < this.field_76332_a) {
                n = -" ".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else if (result.field_76332_a > this.field_76332_a) {
                n = " ".length();
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            else {
                n = result.field_76331_c.compareTo(this.field_76331_c);
            }
            return n;
        }
        
        static {
            I();
            __OBFID = Result.I["".length()];
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\u001b \rIeh\\cMl`", "XlRyU");
        }
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((Result)o);
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
                if (4 != 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
