package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.concurrent.Callable;
import com.google.common.collect.Lists;
import java.util.List;

public class CrashReportCategory
{
    private final CrashReport HorizonCode_Horizon_È;
    private final String Â;
    private final List Ý;
    private StackTraceElement[] Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001409";
    
    public CrashReportCategory(final CrashReport p_i1353_1_, final String name) {
        this.Ý = Lists.newArrayList();
        this.Ø­áŒŠá = new StackTraceElement[0];
        this.HorizonCode_Horizon_È = p_i1353_1_;
        this.Â = name;
    }
    
    public static String HorizonCode_Horizon_È(final double x, final double y, final double z) {
        return String.format("%.2f,%.2f,%.2f - %s", x, y, z, HorizonCode_Horizon_È(new BlockPos(x, y, z)));
    }
    
    public static String HorizonCode_Horizon_È(final BlockPos pos) {
        final int var1 = pos.HorizonCode_Horizon_È();
        final int var2 = pos.Â();
        final int var3 = pos.Ý();
        final StringBuilder var4 = new StringBuilder();
        try {
            var4.append(String.format("World: (%d,%d,%d)", var1, var2, var3));
        }
        catch (Throwable var15) {
            var4.append("(Error finding world loc)");
        }
        var4.append(", ");
        try {
            final int var5 = var1 >> 4;
            final int var6 = var3 >> 4;
            final int var7 = var1 & 0xF;
            final int var8 = var2 >> 4;
            final int var9 = var3 & 0xF;
            final int var10 = var5 << 4;
            final int var11 = var6 << 4;
            final int var12 = (var5 + 1 << 4) - 1;
            final int var13 = (var6 + 1 << 4) - 1;
            var4.append(String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", var7, var8, var9, var5, var6, var10, var11, var12, var13));
        }
        catch (Throwable var16) {
            var4.append("(Error finding chunk loc)");
        }
        var4.append(", ");
        try {
            final int var5 = var1 >> 9;
            final int var6 = var3 >> 9;
            final int var7 = var5 << 5;
            final int var8 = var6 << 5;
            final int var9 = (var5 + 1 << 5) - 1;
            final int var10 = (var6 + 1 << 5) - 1;
            final int var11 = var5 << 9;
            final int var12 = var6 << 9;
            final int var13 = (var5 + 1 << 9) - 1;
            final int var14 = (var6 + 1 << 9) - 1;
            var4.append(String.format("Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)", var5, var6, var7, var8, var9, var10, var11, var12, var13, var14));
        }
        catch (Throwable var17) {
            var4.append("(Error finding world loc)");
        }
        return var4.toString();
    }
    
    public void HorizonCode_Horizon_È(final String sectionName, final Callable callable) {
        try {
            this.HorizonCode_Horizon_È(sectionName, callable.call());
        }
        catch (Throwable var4) {
            this.HorizonCode_Horizon_È(sectionName, var4);
        }
    }
    
    public void HorizonCode_Horizon_È(final String sectionName, final Object value) {
        this.Ý.add(new HorizonCode_Horizon_È(sectionName, value));
    }
    
    public void HorizonCode_Horizon_È(final String sectionName, final Throwable throwable) {
        this.HorizonCode_Horizon_È(sectionName, (Object)throwable);
    }
    
    public int HorizonCode_Horizon_È(final int size) {
        final StackTraceElement[] var2 = Thread.currentThread().getStackTrace();
        if (var2.length <= 0) {
            return 0;
        }
        System.arraycopy(var2, 3 + size, this.Ø­áŒŠá = new StackTraceElement[var2.length - 3 - size], 0, this.Ø­áŒŠá.length);
        return this.Ø­áŒŠá.length;
    }
    
    public boolean HorizonCode_Horizon_È(final StackTraceElement s1, final StackTraceElement s2) {
        if (this.Ø­áŒŠá.length == 0 || s1 == null) {
            return false;
        }
        final StackTraceElement var3 = this.Ø­áŒŠá[0];
        if (var3.isNativeMethod() != s1.isNativeMethod() || !var3.getClassName().equals(s1.getClassName()) || !var3.getFileName().equals(s1.getFileName()) || !var3.getMethodName().equals(s1.getMethodName())) {
            return false;
        }
        if (s2 != null != this.Ø­áŒŠá.length > 1) {
            return false;
        }
        if (s2 != null && !this.Ø­áŒŠá[1].equals(s2)) {
            return false;
        }
        this.Ø­áŒŠá[0] = s1;
        return true;
    }
    
    public void Â(final int amount) {
        final StackTraceElement[] var2 = new StackTraceElement[this.Ø­áŒŠá.length - amount];
        System.arraycopy(this.Ø­áŒŠá, 0, var2, 0, var2.length);
        this.Ø­áŒŠá = var2;
    }
    
    public void HorizonCode_Horizon_È(final StringBuilder builder) {
        builder.append("-- ").append(this.Â).append(" --\n");
        builder.append("Details:");
        for (final HorizonCode_Horizon_È var3 : this.Ý) {
            builder.append("\n\t");
            builder.append(var3.HorizonCode_Horizon_È());
            builder.append(": ");
            builder.append(var3.Â());
        }
        if (this.Ø­áŒŠá != null && this.Ø­áŒŠá.length > 0) {
            builder.append("\nStacktrace:");
            for (final StackTraceElement var7 : this.Ø­áŒŠá) {
                builder.append("\n\tat ");
                builder.append(var7.toString());
            }
        }
    }
    
    public StackTraceElement[] HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
    
    public static void HorizonCode_Horizon_È(final CrashReportCategory category, final BlockPos pos, final Block blockIn, final int blockData) {
        final int var4 = Block.HorizonCode_Horizon_È(blockIn);
        category.HorizonCode_Horizon_È("Block type", new Callable() {
            private static final String HorizonCode_Horizon_È = "CL_00001426";
            
            public String HorizonCode_Horizon_È() {
                try {
                    return String.format("ID #%d (%s // %s)", var4, blockIn.Çªà¢(), blockIn.getClass().getCanonicalName());
                }
                catch (Throwable var2) {
                    return "ID #" + var4;
                }
            }
        });
        category.HorizonCode_Horizon_È("Block data value", new Callable() {
            private static final String HorizonCode_Horizon_È = "CL_00001441";
            
            public String HorizonCode_Horizon_È() {
                if (blockData < 0) {
                    return "Unknown? (Got " + blockData + ")";
                }
                final String var1 = String.format("%4s", Integer.toBinaryString(blockData)).replace(" ", "0");
                return String.format("%1$d / 0x%1$X / 0b%2$s", blockData, var1);
            }
        });
        category.HorizonCode_Horizon_È("Block location", new Callable() {
            private static final String HorizonCode_Horizon_È = "CL_00001465";
            
            public String HorizonCode_Horizon_È() {
                return CrashReportCategory.HorizonCode_Horizon_È(pos);
            }
        });
    }
    
    public static void HorizonCode_Horizon_È(final CrashReportCategory category, final BlockPos pos, final IBlockState state) {
        category.HorizonCode_Horizon_È("Block", new Callable() {
            private static final String HorizonCode_Horizon_È = "CL_00002617";
            
            public String HorizonCode_Horizon_È() {
                return state.toString();
            }
            
            @Override
            public Object call() {
                return this.HorizonCode_Horizon_È();
            }
        });
        category.HorizonCode_Horizon_È("Block location", new Callable() {
            private static final String HorizonCode_Horizon_È = "CL_00002616";
            
            public String HorizonCode_Horizon_È() {
                return CrashReportCategory.HorizonCode_Horizon_È(pos);
            }
            
            @Override
            public Object call() {
                return this.HorizonCode_Horizon_È();
            }
        });
    }
    
    static class HorizonCode_Horizon_È
    {
        private final String HorizonCode_Horizon_È;
        private final String Â;
        private static final String Ý = "CL_00001489";
        
        public HorizonCode_Horizon_È(final String key, final Object value) {
            this.HorizonCode_Horizon_È = key;
            if (value == null) {
                this.Â = "~~NULL~~";
            }
            else if (value instanceof Throwable) {
                final Throwable var3 = (Throwable)value;
                this.Â = "~~ERROR~~ " + var3.getClass().getSimpleName() + ": " + var3.getMessage();
            }
            else {
                this.Â = value.toString();
            }
        }
        
        public String HorizonCode_Horizon_È() {
            return this.HorizonCode_Horizon_È;
        }
        
        public String Â() {
            return this.Â;
        }
    }
}
