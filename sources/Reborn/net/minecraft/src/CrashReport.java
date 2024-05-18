package net.minecraft.src;

import java.util.concurrent.*;
import java.text.*;
import java.util.*;
import java.io.*;

public class CrashReport
{
    private final String description;
    private final Throwable cause;
    private final CrashReportCategory field_85061_c;
    private final List crashReportSections;
    private File crashReportFile;
    private boolean field_85059_f;
    private StackTraceElement[] field_85060_g;
    
    public CrashReport(final String par1Str, final Throwable par2Throwable) {
        this.field_85061_c = new CrashReportCategory(this, "System Details");
        this.crashReportSections = new ArrayList();
        this.crashReportFile = null;
        this.field_85059_f = true;
        this.field_85060_g = new StackTraceElement[0];
        this.description = par1Str;
        this.cause = par2Throwable;
        this.populateEnvironment();
    }
    
    private void populateEnvironment() {
        this.field_85061_c.addCrashSectionCallable("Minecraft Version", new CallableMinecraftVersion(this));
        this.field_85061_c.addCrashSectionCallable("Operating System", new CallableOSInfo(this));
        this.field_85061_c.addCrashSectionCallable("Java Version", new CallableJavaInfo(this));
        this.field_85061_c.addCrashSectionCallable("Java VM Version", new CallableJavaInfo2(this));
        this.field_85061_c.addCrashSectionCallable("Memory", new CallableMemoryInfo(this));
        this.field_85061_c.addCrashSectionCallable("JVM Flags", new CallableJVMFlags(this));
        this.field_85061_c.addCrashSectionCallable("AABB Pool Size", new CallableCrashMemoryReport(this));
        this.field_85061_c.addCrashSectionCallable("Suspicious classes", new CallableSuspiciousClasses(this));
        this.field_85061_c.addCrashSectionCallable("IntCache", new CallableIntCache(this));
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public Throwable getCrashCause() {
        return this.cause;
    }
    
    public String func_90021_c() {
        final StringBuilder var1 = new StringBuilder();
        this.getSectionsInStringBuilder(var1);
        return var1.toString();
    }
    
    public void getSectionsInStringBuilder(final StringBuilder par1StringBuilder) {
        if (this.field_85060_g != null && this.field_85060_g.length > 0) {
            par1StringBuilder.append("-- Head --\n");
            par1StringBuilder.append("Stacktrace:\n");
            for (final StackTraceElement var5 : this.field_85060_g) {
                par1StringBuilder.append("\t").append("at ").append(var5.toString());
                par1StringBuilder.append("\n");
            }
            par1StringBuilder.append("\n");
        }
        for (final CrashReportCategory var7 : this.crashReportSections) {
            var7.func_85072_a(par1StringBuilder);
            par1StringBuilder.append("\n\n");
        }
        this.field_85061_c.func_85072_a(par1StringBuilder);
    }
    
    public String getCauseStackTraceOrString() {
        StringWriter var1 = null;
        PrintWriter var2 = null;
        String var3 = this.cause.toString();
        try {
            var1 = new StringWriter();
            var2 = new PrintWriter(var1);
            this.cause.printStackTrace(var2);
            var3 = var1.toString();
        }
        finally {
            try {
                if (var1 != null) {
                    var1.close();
                }
                if (var2 != null) {
                    var2.close();
                }
            }
            catch (IOException ex) {}
        }
        try {
            if (var1 != null) {
                var1.close();
            }
            if (var2 != null) {
                var2.close();
            }
        }
        catch (IOException ex2) {}
        return var3;
    }
    
    public String getCompleteReport() {
        final StringBuilder var1 = new StringBuilder();
        var1.append("---- Minecraft Crash Report ----\n");
        var1.append("// ");
        var1.append(getWittyComment());
        var1.append("\n\n");
        var1.append("Time: ");
        var1.append(new SimpleDateFormat().format(new Date()));
        var1.append("\n");
        var1.append("Description: ");
        var1.append(this.description);
        var1.append("\n\n");
        var1.append(this.getCauseStackTraceOrString());
        var1.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
        for (int var2 = 0; var2 < 87; ++var2) {
            var1.append("-");
        }
        var1.append("\n\n");
        this.getSectionsInStringBuilder(var1);
        return var1.toString();
    }
    
    public File getFile() {
        return this.crashReportFile;
    }
    
    public boolean saveToFile(final File par1File, final ILogAgent par2ILogAgent) {
        if (this.crashReportFile != null) {
            return false;
        }
        if (par1File.getParentFile() != null) {
            par1File.getParentFile().mkdirs();
        }
        try {
            final FileWriter var3 = new FileWriter(par1File);
            var3.write(this.getCompleteReport());
            var3.close();
            this.crashReportFile = par1File;
            return true;
        }
        catch (Throwable var4) {
            par2ILogAgent.logSevereException("Could not save crash report to " + par1File, var4);
            return false;
        }
    }
    
    public CrashReportCategory func_85056_g() {
        return this.field_85061_c;
    }
    
    public CrashReportCategory makeCategory(final String par1Str) {
        return this.makeCategoryDepth(par1Str, 1);
    }
    
    public CrashReportCategory makeCategoryDepth(final String par1Str, final int par2) {
        final CrashReportCategory var3 = new CrashReportCategory(this, par1Str);
        if (this.field_85059_f) {
            final int var4 = var3.func_85073_a(par2);
            final StackTraceElement[] var5 = this.cause.getStackTrace();
            StackTraceElement var6 = null;
            StackTraceElement var7 = null;
            if (var5 != null && var5.length - var4 < var5.length) {
                var6 = var5[var5.length - var4];
                if (var5.length + 1 - var4 < var5.length) {
                    var7 = var5[var5.length + 1 - var4];
                }
            }
            this.field_85059_f = var3.func_85069_a(var6, var7);
            if (var4 > 0 && !this.crashReportSections.isEmpty()) {
                final CrashReportCategory var8 = this.crashReportSections.get(this.crashReportSections.size() - 1);
                var8.func_85070_b(var4);
            }
            else if (var5 != null && var5.length >= var4) {
                System.arraycopy(var5, 0, this.field_85060_g = new StackTraceElement[var5.length - var4], 0, this.field_85060_g.length);
            }
            else {
                this.field_85059_f = false;
            }
        }
        this.crashReportSections.add(var3);
        return var3;
    }
    
    private static String getWittyComment() {
        final String[] var0 = { "Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!" };
        try {
            return var0[(int)(System.nanoTime() % var0.length)];
        }
        catch (Throwable var2) {
            return "Witty comment unavailable :(";
        }
    }
    
    public static CrashReport makeCrashReport(final Throwable par0Throwable, final String par1Str) {
        CrashReport var2;
        if (par0Throwable instanceof ReportedException) {
            var2 = ((ReportedException)par0Throwable).getCrashReport();
        }
        else {
            var2 = new CrashReport(par1Str, par0Throwable);
        }
        return var2;
    }
}
