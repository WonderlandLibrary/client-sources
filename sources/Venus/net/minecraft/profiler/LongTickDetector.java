/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.profiler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.LongSupplier;
import javax.annotation.Nullable;
import net.minecraft.profiler.EmptyProfiler;
import net.minecraft.profiler.IProfileResult;
import net.minecraft.profiler.IProfiler;
import net.minecraft.profiler.IResultableProfiler;
import net.minecraft.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LongTickDetector {
    private static final Logger field_233516_a_ = LogManager.getLogger();
    private final LongSupplier field_233517_b_ = null;
    private final long field_233518_c_ = 0L;
    private int field_233519_d_;
    private final File field_233520_e_ = null;
    private IResultableProfiler field_233521_f_;

    public IProfiler func_233522_a_() {
        this.field_233521_f_ = new Profiler(this.field_233517_b_, this::lambda$func_233522_a_$0, false);
        ++this.field_233519_d_;
        return this.field_233521_f_;
    }

    public void func_233525_b_() {
        if (this.field_233521_f_ != EmptyProfiler.INSTANCE) {
            IProfileResult iProfileResult = this.field_233521_f_.getResults();
            this.field_233521_f_ = EmptyProfiler.INSTANCE;
            if (iProfileResult.nanoTime() >= this.field_233518_c_) {
                File file = new File(this.field_233520_e_, "tick-results-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt");
                iProfileResult.writeToFile(file);
                field_233516_a_.info("Recorded long tick -- wrote info to: {}", (Object)file.getAbsolutePath());
            }
        }
    }

    @Nullable
    public static LongTickDetector func_233524_a_(String string) {
        return null;
    }

    public static IProfiler func_233523_a_(IProfiler iProfiler, @Nullable LongTickDetector longTickDetector) {
        return longTickDetector != null ? IProfiler.func_233513_a_(longTickDetector.func_233522_a_(), iProfiler) : iProfiler;
    }

    private LongTickDetector() {
        throw new RuntimeException("Synthetic constructor added by MCP, do not call");
    }

    private int lambda$func_233522_a_$0() {
        return this.field_233519_d_;
    }
}

