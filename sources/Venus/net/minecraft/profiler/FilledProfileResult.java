/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.profiler;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.profiler.DataPoint;
import net.minecraft.profiler.IProfileResult;
import net.minecraft.profiler.IProfilerSection;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FilledProfileResult
implements IProfileResult {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final IProfilerSection field_230090_b_ = new IProfilerSection(){

        @Override
        public long func_230037_a_() {
            return 0L;
        }

        @Override
        public long func_230038_b_() {
            return 0L;
        }

        @Override
        public Object2LongMap<String> func_230039_c_() {
            return Object2LongMaps.emptyMap();
        }
    };
    private static final Splitter field_230091_c_ = Splitter.on('\u001e');
    private static final Comparator<Map.Entry<String, Section>> field_230092_d_ = Map.Entry.comparingByValue(Comparator.comparingLong(FilledProfileResult::lambda$static$0)).reversed();
    private final Map<String, ? extends IProfilerSection> field_230093_e_;
    private final long timeStop;
    private final int ticksStop;
    private final long timeStart;
    private final int ticksStart;
    private final int ticksTotal;

    public FilledProfileResult(Map<String, ? extends IProfilerSection> map, long l, int n, long l2, int n2) {
        this.field_230093_e_ = map;
        this.timeStop = l;
        this.ticksStop = n;
        this.timeStart = l2;
        this.ticksStart = n2;
        this.ticksTotal = n2 - n;
    }

    private IProfilerSection func_230104_c_(String string) {
        IProfilerSection iProfilerSection = this.field_230093_e_.get(string);
        return iProfilerSection != null ? iProfilerSection : field_230090_b_;
    }

    @Override
    public List<DataPoint> getDataPoints(String object) {
        Object string = object;
        IProfilerSection iProfilerSection = this.func_230104_c_("root");
        long l = iProfilerSection.func_230037_a_();
        IProfilerSection iProfilerSection2 = this.func_230104_c_((String)object);
        long l2 = iProfilerSection2.func_230037_a_();
        long l3 = iProfilerSection2.func_230038_b_();
        ArrayList<DataPoint> arrayList = Lists.newArrayList();
        if (!((String)object).isEmpty()) {
            object = (String)object + "\u001e";
        }
        long l4 = 0L;
        for (String object2 : this.field_230093_e_.keySet()) {
            if (!FilledProfileResult.func_230097_a_((String)object, object2)) continue;
            l4 += this.func_230104_c_(object2).func_230037_a_();
        }
        float f = l4;
        if (l4 < l2) {
            l4 = l2;
        }
        if (l < l4) {
            l = l4;
        }
        for (String string2 : this.field_230093_e_.keySet()) {
            if (!FilledProfileResult.func_230097_a_((String)object, string2)) continue;
            IProfilerSection iProfilerSection3 = this.func_230104_c_(string2);
            long l5 = iProfilerSection3.func_230037_a_();
            double d = (double)l5 * 100.0 / (double)l4;
            double d2 = (double)l5 * 100.0 / (double)l;
            String string3 = string2.substring(((String)object).length());
            arrayList.add(new DataPoint(string3, d, d2, iProfilerSection3.func_230038_b_()));
        }
        if ((float)l4 > f) {
            arrayList.add(new DataPoint("unspecified", (double)((float)l4 - f) * 100.0 / (double)l4, (double)((float)l4 - f) * 100.0 / (double)l, l3));
        }
        Collections.sort(arrayList);
        arrayList.add(0, new DataPoint((String)string, 100.0, (double)l4 * 100.0 / (double)l, l3));
        return arrayList;
    }

    private static boolean func_230097_a_(String string, String string2) {
        return string2.length() > string.length() && string2.startsWith(string) && string2.indexOf(30, string.length() + 1) < 0;
    }

    private Map<String, Section> func_230106_h_() {
        TreeMap<String, Section> treeMap = Maps.newTreeMap();
        this.field_230093_e_.forEach((arg_0, arg_1) -> FilledProfileResult.lambda$func_230106_h_$3(treeMap, arg_0, arg_1));
        return treeMap;
    }

    @Override
    public long timeStop() {
        return this.timeStop;
    }

    @Override
    public int ticksStop() {
        return this.ticksStop;
    }

    @Override
    public long timeStart() {
        return this.timeStart;
    }

    @Override
    public int ticksStart() {
        return this.ticksStart;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean writeToFile(File file) {
        boolean bl2;
        file.getParentFile().mkdirs();
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = new OutputStreamWriter((OutputStream)new FileOutputStream(file), StandardCharsets.UTF_8);
            outputStreamWriter.write(this.inlineIntoCrashReport(this.nanoTime(), this.ticksSpend()));
            bl2 = true;
        } catch (Throwable throwable) {
            boolean bl;
            try {
                LOGGER.error("Could not save profiler results to {}", (Object)file, (Object)throwable);
                bl = false;
            } catch (Throwable throwable2) {
                IOUtils.closeQuietly(outputStreamWriter);
                throw throwable2;
            }
            IOUtils.closeQuietly(outputStreamWriter);
            return bl;
        }
        IOUtils.closeQuietly(outputStreamWriter);
        return bl2;
    }

    protected String inlineIntoCrashReport(long l, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---- Minecraft Profiler Results ----\n");
        stringBuilder.append("// ");
        stringBuilder.append(FilledProfileResult.getWittyString());
        stringBuilder.append("\n\n");
        stringBuilder.append("Version: ").append(SharedConstants.getVersion().getId()).append('\n');
        stringBuilder.append("Time span: ").append(l / 1000000L).append(" ms\n");
        stringBuilder.append("Tick span: ").append(n).append(" ticks\n");
        stringBuilder.append("// This is approximately ").append(String.format(Locale.ROOT, "%.2f", Float.valueOf((float)n / ((float)l / 1.0E9f)))).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
        stringBuilder.append("--- BEGIN PROFILE DUMP ---\n\n");
        this.format(0, "root", stringBuilder);
        stringBuilder.append("--- END PROFILE DUMP ---\n\n");
        Map<String, Section> map = this.func_230106_h_();
        if (!map.isEmpty()) {
            stringBuilder.append("--- BEGIN COUNTER DUMP ---\n\n");
            this.func_230102_a_(map, stringBuilder, n);
            stringBuilder.append("--- END COUNTER DUMP ---\n\n");
        }
        return stringBuilder.toString();
    }

    private static StringBuilder func_230098_a_(StringBuilder stringBuilder, int n) {
        stringBuilder.append(String.format("[%02d] ", n));
        for (int i = 0; i < n; ++i) {
            stringBuilder.append("|   ");
        }
        return stringBuilder;
    }

    private void format(int n, String string, StringBuilder stringBuilder) {
        List<DataPoint> list = this.getDataPoints(string);
        Object2LongMap<String> object2LongMap = ObjectUtils.firstNonNull(this.field_230093_e_.get(string), field_230090_b_).func_230039_c_();
        object2LongMap.forEach((arg_0, arg_1) -> this.lambda$format$4(stringBuilder, n, arg_0, arg_1));
        if (list.size() >= 3) {
            for (int i = 1; i < list.size(); ++i) {
                DataPoint dataPoint = list.get(i);
                FilledProfileResult.func_230098_a_(stringBuilder, n).append(dataPoint.name).append('(').append(dataPoint.field_223511_c).append('/').append(String.format(Locale.ROOT, "%.0f", Float.valueOf((float)dataPoint.field_223511_c / (float)this.ticksTotal))).append(')').append(" - ").append(String.format(Locale.ROOT, "%.2f", dataPoint.relTime)).append("%/").append(String.format(Locale.ROOT, "%.2f", dataPoint.rootRelTime)).append("%\n");
                if ("unspecified".equals(dataPoint.name)) continue;
                try {
                    this.format(n + 1, string + "\u001e" + dataPoint.name, stringBuilder);
                    continue;
                } catch (Exception exception) {
                    stringBuilder.append("[[ EXCEPTION ").append(exception).append(" ]]");
                }
            }
        }
    }

    private void func_230095_a_(int n, String string, Section section, int n2, StringBuilder stringBuilder) {
        FilledProfileResult.func_230098_a_(stringBuilder, n).append(string).append(" total:").append(section.field_230107_a_).append('/').append(section.field_230108_b_).append(" average: ").append(section.field_230107_a_ / (long)n2).append('/').append(section.field_230108_b_ / (long)n2).append('\n');
        section.field_230109_c_.entrySet().stream().sorted(field_230092_d_).forEach(arg_0 -> this.lambda$func_230095_a_$5(n, n2, stringBuilder, arg_0));
    }

    private void func_230102_a_(Map<String, Section> map, StringBuilder stringBuilder, int n) {
        map.forEach((arg_0, arg_1) -> this.lambda$func_230102_a_$6(stringBuilder, n, arg_0, arg_1));
    }

    private static String getWittyString() {
        String[] stringArray = new String[]{"Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server."};
        try {
            return stringArray[(int)(Util.nanoTime() % (long)stringArray.length)];
        } catch (Throwable throwable) {
            return "Witty comment unavailable :(";
        }
    }

    @Override
    public int ticksSpend() {
        return this.ticksTotal;
    }

    private void lambda$func_230102_a_$6(StringBuilder stringBuilder, int n, String string, Section section) {
        stringBuilder.append("-- Counter: ").append(string).append(" --\n");
        this.func_230095_a_(0, "root", section.field_230109_c_.get("root"), n, stringBuilder);
        stringBuilder.append("\n\n");
    }

    private void lambda$func_230095_a_$5(int n, int n2, StringBuilder stringBuilder, Map.Entry entry) {
        this.func_230095_a_(n + 1, (String)entry.getKey(), (Section)entry.getValue(), n2, stringBuilder);
    }

    private void lambda$format$4(StringBuilder stringBuilder, int n, String string, Long l) {
        FilledProfileResult.func_230098_a_(stringBuilder, n).append('#').append(string).append(' ').append(l).append('/').append(l / (long)this.ticksTotal).append('\n');
    }

    private static void lambda$func_230106_h_$3(Map map, String string, IProfilerSection iProfilerSection) {
        Object2LongMap<String> object2LongMap = iProfilerSection.func_230039_c_();
        if (!object2LongMap.isEmpty()) {
            List<String> list = field_230091_c_.splitToList(string);
            object2LongMap.forEach((arg_0, arg_1) -> FilledProfileResult.lambda$func_230106_h_$2(map, list, arg_0, arg_1));
        }
    }

    private static void lambda$func_230106_h_$2(Map map, List list, String string, Long l) {
        map.computeIfAbsent(string, FilledProfileResult::lambda$func_230106_h_$1).func_230112_a_(list.iterator(), l);
    }

    private static Section lambda$func_230106_h_$1(String string) {
        return new Section();
    }

    private static long lambda$static$0(Section section) {
        return section.field_230108_b_;
    }

    static class Section {
        private long field_230107_a_;
        private long field_230108_b_;
        private final Map<String, Section> field_230109_c_ = Maps.newHashMap();

        private Section() {
        }

        public void func_230112_a_(Iterator<String> iterator2, long l) {
            this.field_230108_b_ += l;
            if (!iterator2.hasNext()) {
                this.field_230107_a_ += l;
            } else {
                this.field_230109_c_.computeIfAbsent(iterator2.next(), Section::lambda$func_230112_a_$0).func_230112_a_(iterator2, l);
            }
        }

        private static Section lambda$func_230112_a_$0(String string) {
            return new Section();
        }
    }
}

