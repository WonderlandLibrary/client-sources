/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.profiler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.profiler.FilledProfileResult;
import net.minecraft.profiler.IProfileResult;
import net.minecraft.profiler.IProfilerSection;
import net.minecraft.profiler.IResultableProfiler;
import net.minecraft.profiler.TimeTracker;
import net.minecraft.util.Util;
import net.optifine.Config;
import net.optifine.Lagometer;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorClass;
import net.optifine.reflect.ReflectorField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Profiler
implements IResultableProfiler {
    private static final long WARN_TIME_THRESHOLD = Duration.ofMillis(100L).toNanos();
    private static final Logger LOGGER = LogManager.getLogger();
    private final List<String> sectionList = Lists.newArrayList();
    private final LongList timeStack = new LongArrayList();
    private final Map<String, Section> field_230078_e_ = Maps.newHashMap();
    private final IntSupplier currentTicks;
    private final LongSupplier field_233501_g_;
    private final long startTime;
    private final int startTicks;
    private String currentSectionName = "";
    private boolean tickStarted;
    @Nullable
    private Section field_230079_k_;
    private final boolean field_226230_l_;
    private boolean clientProfiler = false;
    private boolean lagometerActive = false;
    private static final String SCHEDULED_EXECUTABLES = "scheduledExecutables";
    private static final String TICK = "tick";
    private static final String SOUND = "sound";
    private static final int HASH_SCHEDULED_EXECUTABLES = "scheduledExecutables".hashCode();
    private static final int HASH_TICK = "tick".hashCode();
    private static final int HASH_SOUND = "sound".hashCode();
    private static final ReflectorClass MINECRAFT = new ReflectorClass(Minecraft.class);
    private static final ReflectorField Minecraft_timeTracker = new ReflectorField(MINECRAFT, TimeTracker.class);

    public Profiler(LongSupplier longSupplier, IntSupplier intSupplier, boolean bl) {
        this.startTime = longSupplier.getAsLong();
        this.field_233501_g_ = longSupplier;
        this.startTicks = intSupplier.getAsInt();
        this.currentTicks = intSupplier;
        this.field_226230_l_ = bl;
    }

    @Override
    public void startTick() {
        TimeTracker timeTracker = (TimeTracker)Reflector.getFieldValue(Minecraft.getInstance(), Minecraft_timeTracker);
        this.clientProfiler = timeTracker != null && timeTracker.func_233508_d_() == this;
        boolean bl = this.lagometerActive = this.clientProfiler && Lagometer.isActive();
        if (this.tickStarted) {
            LOGGER.error("Profiler tick already started - missing endTick()?");
        } else {
            this.tickStarted = true;
            this.currentSectionName = "";
            this.sectionList.clear();
            this.startSection("root");
        }
    }

    @Override
    public void endTick() {
        if (!this.tickStarted) {
            LOGGER.error("Profiler tick already ended - missing startTick()?");
        } else {
            this.endSection();
            this.tickStarted = false;
            if (!this.currentSectionName.isEmpty()) {
                LOGGER.error("Profiler tick ended before path was fully popped (remainder: '{}'). Mismatched push/pop?", this::lambda$endTick$0);
            }
        }
    }

    @Override
    public void startSection(String string) {
        if (this.lagometerActive) {
            int n = string.hashCode();
            if (n == HASH_SCHEDULED_EXECUTABLES && string.equals(SCHEDULED_EXECUTABLES)) {
                Lagometer.timerScheduledExecutables.start();
            } else if (n == HASH_TICK && string.equals(TICK) && Config.isMinecraftThread()) {
                Lagometer.timerScheduledExecutables.end();
                Lagometer.timerTick.start();
            }
        }
        if (!this.tickStarted) {
            LOGGER.error("Cannot push '{}' to profiler if profiler tick hasn't started - missing startTick()?", (Object)string);
        } else {
            if (!this.currentSectionName.isEmpty()) {
                this.currentSectionName = this.currentSectionName + "\u001e";
            }
            this.currentSectionName = this.currentSectionName + string;
            this.sectionList.add(this.currentSectionName);
            this.timeStack.add(Util.nanoTime());
            this.field_230079_k_ = null;
        }
    }

    @Override
    public void startSection(Supplier<String> supplier) {
        this.startSection(supplier.get());
    }

    @Override
    public void endSection() {
        if (!this.tickStarted) {
            LOGGER.error("Cannot pop from profiler if profiler tick hasn't started - missing startTick()?");
        } else if (this.timeStack.isEmpty()) {
            LOGGER.error("Tried to pop one too many times! Mismatched push() and pop()?");
        } else {
            long l = Util.nanoTime();
            long l2 = this.timeStack.removeLong(this.timeStack.size() - 1);
            this.sectionList.remove(this.sectionList.size() - 1);
            long l3 = l - l2;
            Section section = this.func_230081_e_();
            section.field_230082_a_ = (section.field_230082_a_ * 49L + l3) / 50L;
            section.field_230083_b_ = 1L;
            if (this.field_226230_l_ && l3 > WARN_TIME_THRESHOLD) {
                LOGGER.warn("Something's taking too long! '{}' took aprox {} ms", this::lambda$endSection$1, () -> Profiler.lambda$endSection$2(l3));
            }
            this.currentSectionName = this.sectionList.isEmpty() ? "" : this.sectionList.get(this.sectionList.size() - 1);
            this.field_230079_k_ = null;
        }
    }

    @Override
    public void endStartSection(String string) {
        int n;
        if (this.lagometerActive && (n = string.hashCode()) == HASH_SOUND && string.equals(SOUND)) {
            Lagometer.timerTick.end();
        }
        this.endSection();
        this.startSection(string);
    }

    @Override
    public void endStartSection(Supplier<String> supplier) {
        this.endSection();
        this.startSection(supplier);
    }

    private Section func_230081_e_() {
        if (this.field_230079_k_ == null) {
            this.field_230079_k_ = this.field_230078_e_.computeIfAbsent(this.currentSectionName, Profiler::lambda$func_230081_e_$3);
        }
        return this.field_230079_k_;
    }

    @Override
    public void func_230035_c_(String string) {
        this.func_230081_e_().field_230084_c_.addTo(string, 1L);
    }

    @Override
    public void func_230036_c_(Supplier<String> supplier) {
        this.func_230081_e_().field_230084_c_.addTo(supplier.get(), 1L);
    }

    @Override
    public IProfileResult getResults() {
        return new FilledProfileResult(this.field_230078_e_, this.startTime, this.startTicks, this.field_233501_g_.getAsLong(), this.currentTicks.getAsInt());
    }

    private static Section lambda$func_230081_e_$3(String string) {
        return new Section();
    }

    private static Object lambda$endSection$2(long l) {
        return (double)l / 1000000.0;
    }

    private Object lambda$endSection$1() {
        return IProfileResult.decodePath(this.currentSectionName);
    }

    private Object lambda$endTick$0() {
        return IProfileResult.decodePath(this.currentSectionName);
    }

    static class Section
    implements IProfilerSection {
        private long field_230082_a_;
        private long field_230083_b_;
        private Object2LongOpenHashMap<String> field_230084_c_ = new Object2LongOpenHashMap();

        private Section() {
        }

        @Override
        public long func_230037_a_() {
            return this.field_230082_a_;
        }

        @Override
        public long func_230038_b_() {
            return this.field_230083_b_;
        }

        @Override
        public Object2LongMap<String> func_230039_c_() {
            return Object2LongMaps.unmodifiable(this.field_230084_c_);
        }
    }
}

