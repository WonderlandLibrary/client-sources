package net.minecraft.src;

import java.util.concurrent.*;
import java.util.*;

public class CrashReportCategory
{
    private final CrashReport theCrashReport;
    private final String field_85076_b;
    private final List field_85077_c;
    private StackTraceElement[] stackTrace;
    
    public CrashReportCategory(final CrashReport par1CrashReport, final String par2Str) {
        this.field_85077_c = new ArrayList();
        this.stackTrace = new StackTraceElement[0];
        this.theCrashReport = par1CrashReport;
        this.field_85076_b = par2Str;
    }
    
    public static String func_85074_a(final double par0, final double par2, final double par4) {
        return String.format("%.2f,%.2f,%.2f - %s", par0, par2, par4, getLocationInfo(MathHelper.floor_double(par0), MathHelper.floor_double(par2), MathHelper.floor_double(par4)));
    }
    
    public static String getLocationInfo(final int par0, final int par1, final int par2) {
        final StringBuilder var3 = new StringBuilder();
        try {
            var3.append(String.format("World: (%d,%d,%d)", par0, par1, par2));
        }
        catch (Throwable var14) {
            var3.append("(Error finding world loc)");
        }
        var3.append(", ");
        try {
            final int var4 = par0 >> 4;
            final int var5 = par2 >> 4;
            final int var6 = par0 & 0xF;
            final int var7 = par1 >> 4;
            final int var8 = par2 & 0xF;
            final int var9 = var4 << 4;
            final int var10 = var5 << 4;
            final int var11 = (var4 + 1 << 4) - 1;
            final int var12 = (var5 + 1 << 4) - 1;
            var3.append(String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", var6, var7, var8, var4, var5, var9, var10, var11, var12));
        }
        catch (Throwable var15) {
            var3.append("(Error finding chunk loc)");
        }
        var3.append(", ");
        try {
            final int var4 = par0 >> 9;
            final int var5 = par2 >> 9;
            final int var6 = var4 << 5;
            final int var7 = var5 << 5;
            final int var8 = (var4 + 1 << 5) - 1;
            final int var9 = (var5 + 1 << 5) - 1;
            final int var10 = var4 << 9;
            final int var11 = var5 << 9;
            final int var12 = (var4 + 1 << 9) - 1;
            final int var13 = (var5 + 1 << 9) - 1;
            var3.append(String.format("Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)", var4, var5, var6, var7, var8, var9, var10, var11, var12, var13));
        }
        catch (Throwable var16) {
            var3.append("(Error finding world loc)");
        }
        return var3.toString();
    }
    
    public void addCrashSectionCallable(final String par1Str, final Callable par2Callable) {
        try {
            this.addCrashSection(par1Str, par2Callable.call());
        }
        catch (Throwable var4) {
            this.addCrashSectionThrowable(par1Str, var4);
        }
    }
    
    public void addCrashSection(final String par1Str, final Object par2Obj) {
        this.field_85077_c.add(new CrashReportCategoryEntry(par1Str, par2Obj));
    }
    
    public void addCrashSectionThrowable(final String par1Str, final Throwable par2Throwable) {
        this.addCrashSection(par1Str, par2Throwable);
    }
    
    public int func_85073_a(final int par1) {
        final StackTraceElement[] var2 = Thread.currentThread().getStackTrace();
        System.arraycopy(var2, 3 + par1, this.stackTrace = new StackTraceElement[var2.length - 3 - par1], 0, this.stackTrace.length);
        return this.stackTrace.length;
    }
    
    public boolean func_85069_a(final StackTraceElement par1StackTraceElement, final StackTraceElement par2StackTraceElement) {
        if (this.stackTrace.length == 0 || par1StackTraceElement == null) {
            return false;
        }
        final StackTraceElement var3 = this.stackTrace[0];
        if (var3.isNativeMethod() != par1StackTraceElement.isNativeMethod() || !var3.getClassName().equals(par1StackTraceElement.getClassName()) || !var3.getFileName().equals(par1StackTraceElement.getFileName()) || !var3.getMethodName().equals(par1StackTraceElement.getMethodName())) {
            return false;
        }
        if (par2StackTraceElement != null != this.stackTrace.length > 1) {
            return false;
        }
        if (par2StackTraceElement != null && !this.stackTrace[1].equals(par2StackTraceElement)) {
            return false;
        }
        this.stackTrace[0] = par1StackTraceElement;
        return true;
    }
    
    public void func_85070_b(final int par1) {
        final StackTraceElement[] var2 = new StackTraceElement[this.stackTrace.length - par1];
        System.arraycopy(this.stackTrace, 0, var2, 0, var2.length);
        this.stackTrace = var2;
    }
    
    public void func_85072_a(final StringBuilder par1StringBuilder) {
        par1StringBuilder.append("-- ").append(this.field_85076_b).append(" --\n");
        par1StringBuilder.append("Details:");
        for (final CrashReportCategoryEntry var3 : this.field_85077_c) {
            par1StringBuilder.append("\n\t");
            par1StringBuilder.append(var3.func_85089_a());
            par1StringBuilder.append(": ");
            par1StringBuilder.append(var3.func_85090_b());
        }
        if (this.stackTrace != null && this.stackTrace.length > 0) {
            par1StringBuilder.append("\nStacktrace:");
            for (final StackTraceElement var7 : this.stackTrace) {
                par1StringBuilder.append("\n\tat ");
                par1StringBuilder.append(var7.toString());
            }
        }
    }
    
    public static void func_85068_a(final CrashReportCategory par0CrashReportCategory, final int par1, final int par2, final int par3, final int par4, final int par5) {
        par0CrashReportCategory.addCrashSectionCallable("Block type", new CallableBlockType(par4));
        par0CrashReportCategory.addCrashSectionCallable("Block data value", new CallableBlockDataValue(par5));
        par0CrashReportCategory.addCrashSectionCallable("Block location", new CallableBlockLocation(par1, par2, par3));
    }
}
