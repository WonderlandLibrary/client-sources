/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.crash;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.optifine.CrashReporter;
import net.optifine.reflect.Reflector;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrashReport {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String description;
    private final Throwable cause;
    private final CrashReportCategory systemDetailsCategory = new CrashReportCategory(this, "System Details");
    private final List<CrashReportCategory> crashReportSections = Lists.newArrayList();
    private File crashReportFile;
    private boolean firstCategoryInCrashReport = true;
    private StackTraceElement[] stacktrace = new StackTraceElement[0];
    private boolean reported = false;

    public CrashReport(String string, Throwable throwable) {
        this.description = string;
        this.cause = throwable;
        this.populateEnvironment();
    }

    private void populateEnvironment() {
        this.systemDetailsCategory.addDetail("Minecraft Version", CrashReport::lambda$populateEnvironment$0);
        this.systemDetailsCategory.addDetail("Minecraft Version ID", CrashReport::lambda$populateEnvironment$1);
        this.systemDetailsCategory.addDetail("Operating System", CrashReport::lambda$populateEnvironment$2);
        this.systemDetailsCategory.addDetail("Java Version", CrashReport::lambda$populateEnvironment$3);
        this.systemDetailsCategory.addDetail("Java VM Version", CrashReport::lambda$populateEnvironment$4);
        this.systemDetailsCategory.addDetail("Memory", CrashReport::lambda$populateEnvironment$5);
        this.systemDetailsCategory.addDetail("CPUs", Runtime.getRuntime().availableProcessors());
        this.systemDetailsCategory.addDetail("JVM Flags", CrashReport::lambda$populateEnvironment$6);
        if (Reflector.CrashReportExtender_enhanceCrashReport != null) {
            Reflector.CrashReportExtender_enhanceCrashReport.call(this, this.systemDetailsCategory);
        }
    }

    public String getDescription() {
        return this.description;
    }

    public Throwable getCrashCause() {
        return this.cause;
    }

    public void getSectionsInStringBuilder(StringBuilder stringBuilder) {
        if (!(this.stacktrace != null && this.stacktrace.length > 0 || this.crashReportSections.isEmpty())) {
            this.stacktrace = ArrayUtils.subarray(this.crashReportSections.get(0).getStackTrace(), 0, 1);
        }
        if (this.stacktrace != null && this.stacktrace.length > 0) {
            stringBuilder.append("-- Head --\n");
            stringBuilder.append("Thread: ").append(Thread.currentThread().getName()).append("\n");
            if (Reflector.CrashReportExtender_generateEnhancedStackTraceSTE.exists()) {
                stringBuilder.append("Stacktrace:");
                stringBuilder.append(Reflector.CrashReportExtender_generateEnhancedStackTraceSTE.callString1(this.stacktrace));
            } else {
                stringBuilder.append("Stacktrace:\n");
                for (StackTraceElement stackTraceElement : this.stacktrace) {
                    stringBuilder.append("\t").append("at ").append(stackTraceElement);
                    stringBuilder.append("\n");
                }
                stringBuilder.append("\n");
            }
        }
        for (CrashReportCategory crashReportCategory : this.crashReportSections) {
            crashReportCategory.appendToStringBuilder(stringBuilder);
            stringBuilder.append("\n\n");
        }
        this.systemDetailsCategory.appendToStringBuilder(stringBuilder);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getCauseStackTraceOrString() {
        String string;
        StringWriter stringWriter = null;
        PrintWriter printWriter = null;
        Throwable throwable = this.cause;
        if (throwable.getMessage() == null) {
            if (throwable instanceof NullPointerException) {
                throwable = new NullPointerException(this.description);
            } else if (throwable instanceof StackOverflowError) {
                throwable = new StackOverflowError(this.description);
            } else if (throwable instanceof OutOfMemoryError) {
                throwable = new OutOfMemoryError(this.description);
            }
            throwable.setStackTrace(this.cause.getStackTrace());
        }
        if (Reflector.CrashReportExtender_generateEnhancedStackTraceT.exists()) {
            return Reflector.CrashReportExtender_generateEnhancedStackTraceT.callString(throwable);
        }
        try {
            stringWriter = new StringWriter();
            printWriter = new PrintWriter(stringWriter);
            throwable.printStackTrace(printWriter);
            string = stringWriter.toString();
        } catch (Throwable throwable2) {
            IOUtils.closeQuietly(stringWriter);
            IOUtils.closeQuietly(printWriter);
            throw throwable2;
        }
        IOUtils.closeQuietly(stringWriter);
        IOUtils.closeQuietly(printWriter);
        return string;
    }

    public String getCompleteReport() {
        if (!this.reported) {
            this.reported = true;
            CrashReporter.onCrashReport(this, this.systemDetailsCategory);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---- Minecraft Crash Report ----\n");
        if (Reflector.CrashReportExtender_addCrashReportHeader != null && Reflector.CrashReportExtender_addCrashReportHeader.exists()) {
            Reflector.CrashReportExtender_addCrashReportHeader.call(stringBuilder, this);
        }
        stringBuilder.append("// ");
        stringBuilder.append(CrashReport.getWittyComment());
        stringBuilder.append("\n\n");
        stringBuilder.append("Time: ");
        stringBuilder.append(new SimpleDateFormat().format(new Date()));
        stringBuilder.append("\n");
        stringBuilder.append("Description: ");
        stringBuilder.append(this.description);
        stringBuilder.append("\n\n");
        stringBuilder.append(this.getCauseStackTraceOrString());
        stringBuilder.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
        for (int i = 0; i < 87; ++i) {
            stringBuilder.append("-");
        }
        stringBuilder.append("\n\n");
        this.getSectionsInStringBuilder(stringBuilder);
        return stringBuilder.toString();
    }

    public File getFile() {
        return this.crashReportFile;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean saveToFile(File file) {
        boolean bl2;
        if (this.crashReportFile != null) {
            return true;
        }
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = new OutputStreamWriter((OutputStream)new FileOutputStream(file), StandardCharsets.UTF_8);
            outputStreamWriter.write(this.getCompleteReport());
            this.crashReportFile = file;
            bl2 = true;
        } catch (Throwable throwable) {
            boolean bl;
            try {
                LOGGER.error("Could not save crash report to {}", (Object)file, (Object)throwable);
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

    public CrashReportCategory getCategory() {
        return this.systemDetailsCategory;
    }

    public CrashReportCategory makeCategory(String string) {
        return this.makeCategoryDepth(string, 1);
    }

    public CrashReportCategory makeCategoryDepth(String string, int n) {
        CrashReportCategory crashReportCategory = new CrashReportCategory(this, string);
        try {
            if (this.firstCategoryInCrashReport) {
                int n2 = crashReportCategory.getPrunedStackTrace(n);
                StackTraceElement[] stackTraceElementArray = this.cause.getStackTrace();
                StackTraceElement stackTraceElement = null;
                StackTraceElement stackTraceElement2 = null;
                int n3 = stackTraceElementArray.length - n2;
                if (n3 < 0) {
                    System.out.println("Negative index in crash report handler (" + stackTraceElementArray.length + "/" + n2 + ")");
                }
                if (stackTraceElementArray != null && 0 <= n3 && n3 < stackTraceElementArray.length) {
                    stackTraceElement = stackTraceElementArray[n3];
                    if (stackTraceElementArray.length + 1 - n2 < stackTraceElementArray.length) {
                        stackTraceElement2 = stackTraceElementArray[stackTraceElementArray.length + 1 - n2];
                    }
                }
                this.firstCategoryInCrashReport = crashReportCategory.firstTwoElementsOfStackTraceMatch(stackTraceElement, stackTraceElement2);
                if (n2 > 0 && !this.crashReportSections.isEmpty()) {
                    CrashReportCategory crashReportCategory2 = this.crashReportSections.get(this.crashReportSections.size() - 1);
                    crashReportCategory2.trimStackTraceEntriesFromBottom(n2);
                } else if (stackTraceElementArray != null && stackTraceElementArray.length >= n2 && 0 <= n3 && n3 < stackTraceElementArray.length) {
                    this.stacktrace = new StackTraceElement[n3];
                    System.arraycopy(stackTraceElementArray, 0, this.stacktrace, 0, this.stacktrace.length);
                } else {
                    this.firstCategoryInCrashReport = false;
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        this.crashReportSections.add(crashReportCategory);
        return crashReportCategory;
    }

    private static String getWittyComment() {
        String[] stringArray = new String[]{"Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!", "But it works on my machine."};
        try {
            return stringArray[(int)(Util.nanoTime() % (long)stringArray.length)];
        } catch (Throwable throwable) {
            return "Witty comment unavailable :(";
        }
    }

    public static CrashReport makeCrashReport(Throwable throwable, String string) {
        while (throwable instanceof CompletionException && throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        CrashReport crashReport = throwable instanceof ReportedException ? ((ReportedException)throwable).getCrashReport() : new CrashReport(string, throwable);
        return crashReport;
    }

    public static void crash() {
        new CrashReport("Don't panic!", new Throwable()).getCompleteReport();
    }

    private static String lambda$populateEnvironment$6() throws Exception {
        List list = Util.getJvmFlags().collect(Collectors.toList());
        return String.format("%d total; %s", list.size(), list.stream().collect(Collectors.joining(" ")));
    }

    private static String lambda$populateEnvironment$5() throws Exception {
        Runtime runtime = Runtime.getRuntime();
        long l = runtime.maxMemory();
        long l2 = runtime.totalMemory();
        long l3 = runtime.freeMemory();
        long l4 = l / 1024L / 1024L;
        long l5 = l2 / 1024L / 1024L;
        long l6 = l3 / 1024L / 1024L;
        return l3 + " bytes (" + l6 + " MB) / " + l2 + " bytes (" + l5 + " MB) up to " + l + " bytes (" + l4 + " MB)";
    }

    private static String lambda$populateEnvironment$4() throws Exception {
        return System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
    }

    private static String lambda$populateEnvironment$3() throws Exception {
        return System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
    }

    private static String lambda$populateEnvironment$2() throws Exception {
        return System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
    }

    private static String lambda$populateEnvironment$1() throws Exception {
        return SharedConstants.getVersion().getId();
    }

    private static String lambda$populateEnvironment$0() throws Exception {
        return SharedConstants.getVersion().getName();
    }
}

