package HORIZON-6-0-SKIDPROTECTION;

import java.io.FileWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.apache.commons.io.IOUtils;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.commons.lang3.ArrayUtils;
import java.util.Iterator;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Callable;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.io.File;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class CrashReport
{
    private static final Logger HorizonCode_Horizon_È;
    private final String Â;
    private final Throwable Ý;
    private final CrashReportCategory Ø­áŒŠá;
    private final List Âµá€;
    private File Ó;
    private boolean à;
    private StackTraceElement[] Ø;
    private static final String áŒŠÆ = "CL_00000990";
    private boolean áˆºÑ¢Õ;
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public CrashReport(final String descriptionIn, final Throwable causeThrowable) {
        this.Ø­áŒŠá = new CrashReportCategory(this, "System Details");
        this.Âµá€ = Lists.newArrayList();
        this.à = true;
        this.Ø = new StackTraceElement[0];
        this.áˆºÑ¢Õ = false;
        this.Â = descriptionIn;
        this.Ý = causeThrowable;
        this.à();
    }
    
    private void à() {
        this.Ø­áŒŠá.HorizonCode_Horizon_È("Minecraft Version", new Callable() {
            private static final String Â = "CL_00001197";
            
            public String HorizonCode_Horizon_È() {
                return "1.8";
            }
        });
        this.Ø­áŒŠá.HorizonCode_Horizon_È("Operating System", new Callable() {
            private static final String Â = "CL_00001222";
            
            public String HorizonCode_Horizon_È() {
                return String.valueOf(System.getProperty("os.name")) + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
            }
        });
        this.Ø­áŒŠá.HorizonCode_Horizon_È("Java Version", new Callable() {
            private static final String Â = "CL_00001248";
            
            public String HorizonCode_Horizon_È() {
                return String.valueOf(System.getProperty("java.version")) + ", " + System.getProperty("java.vendor");
            }
        });
        this.Ø­áŒŠá.HorizonCode_Horizon_È("Java VM Version", new Callable() {
            private static final String Â = "CL_00001275";
            
            public String HorizonCode_Horizon_È() {
                return String.valueOf(System.getProperty("java.vm.name")) + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
            }
        });
        this.Ø­áŒŠá.HorizonCode_Horizon_È("Memory", new Callable() {
            private static final String Â = "CL_00001302";
            
            public String HorizonCode_Horizon_È() {
                final Runtime var1 = Runtime.getRuntime();
                final long var2 = var1.maxMemory();
                final long var3 = var1.totalMemory();
                final long var4 = var1.freeMemory();
                final long var5 = var2 / 1024L / 1024L;
                final long var6 = var3 / 1024L / 1024L;
                final long var7 = var4 / 1024L / 1024L;
                return String.valueOf(var4) + " bytes (" + var7 + " MB) / " + var3 + " bytes (" + var6 + " MB) up to " + var2 + " bytes (" + var5 + " MB)";
            }
        });
        this.Ø­áŒŠá.HorizonCode_Horizon_È("JVM Flags", new Callable() {
            private static final String Â = "CL_00001329";
            
            public String HorizonCode_Horizon_È() {
                final RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
                final List var2 = var1.getInputArguments();
                int var3 = 0;
                final StringBuilder var4 = new StringBuilder();
                for (final String var6 : var2) {
                    if (var6.startsWith("-X")) {
                        if (var3++ > 0) {
                            var4.append(" ");
                        }
                        var4.append(var6);
                    }
                }
                return String.format("%d total; %s", var3, var4.toString());
            }
        });
        this.Ø­áŒŠá.HorizonCode_Horizon_È("IntCache", new Callable() {
            private static final String Â = "CL_00001355";
            
            public String HorizonCode_Horizon_È() {
                return IntCache.Â();
            }
        });
        if (Reflector.£ÂµÄ.Â()) {
            final Object instance = Reflector.Ó(Reflector.áŠ, new Object[0]);
            Reflector.Âµá€(instance, Reflector.£ÂµÄ, this, this.Ø­áŒŠá);
        }
    }
    
    public String HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public Throwable Â() {
        return this.Ý;
    }
    
    public void HorizonCode_Horizon_È(final StringBuilder builder) {
        if ((this.Ø == null || this.Ø.length <= 0) && this.Âµá€.size() > 0) {
            this.Ø = (StackTraceElement[])ArrayUtils.subarray((Object[])this.Âµá€.get(0).HorizonCode_Horizon_È(), 0, 1);
        }
        if (this.Ø != null && this.Ø.length > 0) {
            builder.append("-- Head --\n");
            builder.append("Stacktrace:\n");
            for (final StackTraceElement var9 : this.Ø) {
                builder.append("\t").append("at ").append(var9.toString());
                builder.append("\n");
            }
            builder.append("\n");
        }
        for (final CrashReportCategory var11 : this.Âµá€) {
            var11.HorizonCode_Horizon_È(builder);
            builder.append("\n\n");
        }
        this.Ø­áŒŠá.HorizonCode_Horizon_È(builder);
    }
    
    public String Ý() {
        StringWriter var1 = null;
        PrintWriter var2 = null;
        Object var3 = this.Ý;
        if (((Throwable)var3).getMessage() == null) {
            if (var3 instanceof NullPointerException) {
                var3 = new NullPointerException(this.Â);
            }
            else if (var3 instanceof StackOverflowError) {
                var3 = new StackOverflowError(this.Â);
            }
            else if (var3 instanceof OutOfMemoryError) {
                var3 = new OutOfMemoryError(this.Â);
            }
            ((Throwable)var3).setStackTrace(this.Ý.getStackTrace());
        }
        String var4 = ((Throwable)var3).toString();
        try {
            var1 = new StringWriter();
            var2 = new PrintWriter(var1);
            ((Throwable)var3).printStackTrace(var2);
            var4 = var1.toString();
        }
        finally {
            IOUtils.closeQuietly((Writer)var1);
            IOUtils.closeQuietly((Writer)var2);
        }
        IOUtils.closeQuietly((Writer)var1);
        IOUtils.closeQuietly((Writer)var2);
        return var4;
    }
    
    public String Ø­áŒŠá() {
        if (!this.áˆºÑ¢Õ) {
            this.áˆºÑ¢Õ = true;
            CrashReporter.HorizonCode_Horizon_È(this);
        }
        final StringBuilder var1 = new StringBuilder();
        var1.append("---- Minecraft Crash Report ----\n");
        var1.append("// ");
        var1.append(Ø());
        var1.append("\n\n");
        var1.append("Time: ");
        var1.append(new SimpleDateFormat().format(new Date()));
        var1.append("\n");
        var1.append("Description: ");
        var1.append(this.Â);
        var1.append("\n\n");
        var1.append(this.Ý());
        var1.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
        for (int var2 = 0; var2 < 87; ++var2) {
            var1.append("-");
        }
        var1.append("\n\n");
        this.HorizonCode_Horizon_È(var1);
        return var1.toString();
    }
    
    public File Âµá€() {
        return this.Ó;
    }
    
    public boolean HorizonCode_Horizon_È(final File toFile) {
        if (this.Ó != null) {
            return false;
        }
        if (toFile.getParentFile() != null) {
            toFile.getParentFile().mkdirs();
        }
        try {
            final FileWriter var3 = new FileWriter(toFile);
            var3.write(this.Ø­áŒŠá());
            var3.close();
            this.Ó = toFile;
            return true;
        }
        catch (Throwable var4) {
            CrashReport.HorizonCode_Horizon_È.error("Could not save crash report to " + toFile, var4);
            return false;
        }
    }
    
    public CrashReportCategory Ó() {
        return this.Ø­áŒŠá;
    }
    
    public CrashReportCategory HorizonCode_Horizon_È(final String name) {
        return this.HorizonCode_Horizon_È(name, 1);
    }
    
    public CrashReportCategory HorizonCode_Horizon_È(final String categoryName, final int stacktraceLength) {
        final CrashReportCategory var3 = new CrashReportCategory(this, categoryName);
        if (this.à) {
            final int var4 = var3.HorizonCode_Horizon_È(stacktraceLength);
            final StackTraceElement[] var5 = this.Ý.getStackTrace();
            StackTraceElement var6 = null;
            StackTraceElement var7 = null;
            final int var8 = var5.length - var4;
            if (var8 < 0) {
                System.out.println("Negative index in crash report handler (" + var5.length + "/" + var4 + ")");
            }
            if (var5 != null && var8 >= 0 && var8 < var5.length) {
                var6 = var5[var8];
                if (var5.length + 1 - var4 < var5.length) {
                    var7 = var5[var5.length + 1 - var4];
                }
            }
            this.à = var3.HorizonCode_Horizon_È(var6, var7);
            if (var4 > 0 && !this.Âµá€.isEmpty()) {
                final CrashReportCategory var9 = this.Âµá€.get(this.Âµá€.size() - 1);
                var9.Â(var4);
            }
            else if (var5 != null && var5.length >= var4 && var8 >= 0 && var8 < var5.length) {
                System.arraycopy(var5, 0, this.Ø = new StackTraceElement[var8], 0, this.Ø.length);
            }
            else {
                this.à = false;
            }
        }
        this.Âµá€.add(var3);
        return var3;
    }
    
    private static String Ø() {
        return "Not my fault, blame zCode...";
    }
    
    public static CrashReport HorizonCode_Horizon_È(final Throwable causeIn, final String descriptionIn) {
        CrashReport var2;
        if (causeIn instanceof ReportedException) {
            var2 = ((ReportedException)causeIn).HorizonCode_Horizon_È();
        }
        else {
            var2 = new CrashReport(descriptionIn, causeIn);
        }
        return var2;
    }
}
