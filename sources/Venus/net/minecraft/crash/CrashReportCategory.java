/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.crash;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.util.math.BlockPos;

public class CrashReportCategory {
    private final CrashReport crashReport;
    private final String name;
    private final List<Entry> children = Lists.newArrayList();
    private StackTraceElement[] stackTrace = new StackTraceElement[0];

    public CrashReportCategory(CrashReport crashReport, String string) {
        this.crashReport = crashReport;
        this.name = string;
    }

    public static String getCoordinateInfo(double d, double d2, double d3) {
        return String.format(Locale.ROOT, "%.2f,%.2f,%.2f - %s", d, d2, d3, CrashReportCategory.getCoordinateInfo(new BlockPos(d, d2, d3)));
    }

    public static String getCoordinateInfo(BlockPos blockPos) {
        return CrashReportCategory.getCoordinateInfo(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static String getCoordinateInfo(int n, int n2, int n3) {
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        int n11;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append(String.format("World: (%d,%d,%d)", n, n2, n3));
        } catch (Throwable throwable) {
            stringBuilder.append("(Error finding world loc)");
        }
        stringBuilder.append(", ");
        try {
            int n12 = n >> 4;
            n11 = n3 >> 4;
            n10 = n & 0xF;
            n9 = n2 >> 4;
            n8 = n3 & 0xF;
            n7 = n12 << 4;
            n6 = n11 << 4;
            n5 = (n12 + 1 << 4) - 1;
            n4 = (n11 + 1 << 4) - 1;
            stringBuilder.append(String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", n10, n9, n8, n12, n11, n7, n6, n5, n4));
        } catch (Throwable throwable) {
            stringBuilder.append("(Error finding chunk loc)");
        }
        stringBuilder.append(", ");
        try {
            int n13 = n >> 9;
            n11 = n3 >> 9;
            n10 = n13 << 5;
            n9 = n11 << 5;
            n8 = (n13 + 1 << 5) - 1;
            n7 = (n11 + 1 << 5) - 1;
            n6 = n13 << 9;
            n5 = n11 << 9;
            n4 = (n13 + 1 << 9) - 1;
            int n14 = (n11 + 1 << 9) - 1;
            stringBuilder.append(String.format("Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)", n13, n11, n10, n9, n8, n7, n6, n5, n4, n14));
        } catch (Throwable throwable) {
            stringBuilder.append("(Error finding world loc)");
        }
        return stringBuilder.toString();
    }

    public CrashReportCategory addDetail(String string, ICrashReportDetail<String> iCrashReportDetail) {
        try {
            this.addDetail(string, iCrashReportDetail.call());
        } catch (Throwable throwable) {
            this.addCrashSectionThrowable(string, throwable);
        }
        return this;
    }

    public CrashReportCategory addDetail(String string, Object object) {
        this.children.add(new Entry(string, object));
        return this;
    }

    public void addCrashSectionThrowable(String string, Throwable throwable) {
        this.addDetail(string, throwable);
    }

    public int getPrunedStackTrace(int n) {
        StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
        if (stackTraceElementArray.length <= 0) {
            return 1;
        }
        this.stackTrace = new StackTraceElement[stackTraceElementArray.length - 3 - n];
        System.arraycopy(stackTraceElementArray, 3 + n, this.stackTrace, 0, this.stackTrace.length);
        return this.stackTrace.length;
    }

    public boolean firstTwoElementsOfStackTraceMatch(StackTraceElement stackTraceElement, StackTraceElement stackTraceElement2) {
        if (this.stackTrace.length != 0 && stackTraceElement != null) {
            StackTraceElement stackTraceElement3 = this.stackTrace[0];
            if (stackTraceElement3.isNativeMethod() == stackTraceElement.isNativeMethod() && stackTraceElement3.getClassName().equals(stackTraceElement.getClassName()) && stackTraceElement3.getFileName().equals(stackTraceElement.getFileName()) && stackTraceElement3.getMethodName().equals(stackTraceElement.getMethodName())) {
                if (stackTraceElement2 != null != this.stackTrace.length > 1) {
                    return true;
                }
                if (stackTraceElement2 != null && !this.stackTrace[1].equals(stackTraceElement2)) {
                    return true;
                }
                this.stackTrace[0] = stackTraceElement;
                return false;
            }
            return true;
        }
        return true;
    }

    public void trimStackTraceEntriesFromBottom(int n) {
        StackTraceElement[] stackTraceElementArray = new StackTraceElement[this.stackTrace.length - n];
        System.arraycopy(this.stackTrace, 0, stackTraceElementArray, 0, stackTraceElementArray.length);
        this.stackTrace = stackTraceElementArray;
    }

    public void appendToStringBuilder(StringBuilder stringBuilder) {
        stringBuilder.append("-- ").append(this.name).append(" --\n");
        stringBuilder.append("Details:");
        for (Entry entry : this.children) {
            stringBuilder.append("\n\t");
            stringBuilder.append(entry.getKey());
            stringBuilder.append(": ");
            stringBuilder.append(entry.getValue());
        }
        if (this.stackTrace != null && this.stackTrace.length > 0) {
            stringBuilder.append("\nStacktrace:");
            for (StackTraceElement stackTraceElement : this.stackTrace) {
                stringBuilder.append("\n\tat ");
                stringBuilder.append(stackTraceElement);
            }
        }
    }

    public StackTraceElement[] getStackTrace() {
        return this.stackTrace;
    }

    public static void addBlockInfo(CrashReportCategory crashReportCategory, BlockPos blockPos, @Nullable BlockState blockState) {
        if (blockState != null) {
            crashReportCategory.addDetail("Block", blockState::toString);
        }
        crashReportCategory.addDetail("Block location", () -> CrashReportCategory.lambda$addBlockInfo$0(blockPos));
    }

    private static String lambda$addBlockInfo$0(BlockPos blockPos) throws Exception {
        return CrashReportCategory.getCoordinateInfo(blockPos);
    }

    static class Entry {
        private final String key;
        private final String value;

        public Entry(String string, @Nullable Object object) {
            this.key = string;
            if (object == null) {
                this.value = "~~NULL~~";
            } else if (object instanceof Throwable) {
                Throwable throwable = (Throwable)object;
                this.value = "~~ERROR~~ " + throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
            } else {
                this.value = object.toString();
            }
        }

        public String getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }
    }
}

