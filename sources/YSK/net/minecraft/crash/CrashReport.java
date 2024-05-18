package net.minecraft.crash;

import org.apache.commons.io.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import java.io.*;
import java.text.*;
import org.apache.commons.lang3.*;
import java.util.*;
import java.util.concurrent.*;
import java.lang.management.*;
import net.minecraft.world.gen.layer.*;
import optfine.*;
import net.minecraft.util.*;

public class CrashReport
{
    private boolean field_85059_f;
    private File crashReportFile;
    private static final String[] I;
    private final String description;
    private StackTraceElement[] stacktrace;
    private final Throwable cause;
    private static final String __OBFID;
    private final List crashReportSections;
    private boolean reported;
    private final CrashReportCategory theReportCategory;
    private static final Logger logger;
    
    public CrashReportCategory getCategory() {
        return this.theReportCategory;
    }
    
    public String getCauseStackTraceOrString() {
        StringWriter stringWriter = null;
        PrintWriter printWriter = null;
        Throwable cause = this.cause;
        if (cause.getMessage() == null) {
            if (cause instanceof NullPointerException) {
                cause = new NullPointerException(this.description);
                "".length();
                if (-1 < -1) {
                    throw null;
                }
            }
            else if (cause instanceof StackOverflowError) {
                cause = new StackOverflowError(this.description);
                "".length();
                if (1 == 2) {
                    throw null;
                }
            }
            else if (cause instanceof OutOfMemoryError) {
                cause = new OutOfMemoryError(this.description);
            }
            cause.setStackTrace(this.cause.getStackTrace());
        }
        String s = cause.toString();
        try {
            stringWriter = new StringWriter();
            printWriter = new PrintWriter(stringWriter);
            cause.printStackTrace(printWriter);
            s = stringWriter.toString();
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        finally {
            IOUtils.closeQuietly((Writer)stringWriter);
            IOUtils.closeQuietly((Writer)printWriter);
        }
        IOUtils.closeQuietly((Writer)stringWriter);
        IOUtils.closeQuietly((Writer)printWriter);
        return s;
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
            if (3 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public CrashReportCategory makeCategoryDepth(final String s, final int n) {
        final CrashReportCategory crashReportCategory = new CrashReportCategory(this, s);
        if (this.field_85059_f) {
            final int prunedStackTrace = crashReportCategory.getPrunedStackTrace(n);
            final StackTraceElement[] stackTrace = this.cause.getStackTrace();
            StackTraceElement stackTraceElement = null;
            StackTraceElement stackTraceElement2 = null;
            final int n2 = stackTrace.length - prunedStackTrace;
            if (n2 < 0) {
                System.out.println(CrashReport.I[0x26 ^ 0x3A] + stackTrace.length + CrashReport.I[0x5B ^ 0x46] + prunedStackTrace + CrashReport.I[0x4B ^ 0x55]);
            }
            if (stackTrace != null && n2 >= 0 && n2 < stackTrace.length) {
                stackTraceElement = stackTrace[n2];
                if (stackTrace.length + " ".length() - prunedStackTrace < stackTrace.length) {
                    stackTraceElement2 = stackTrace[stackTrace.length + " ".length() - prunedStackTrace];
                }
            }
            this.field_85059_f = crashReportCategory.firstTwoElementsOfStackTraceMatch(stackTraceElement, stackTraceElement2);
            if (prunedStackTrace > 0 && !this.crashReportSections.isEmpty()) {
                this.crashReportSections.get(this.crashReportSections.size() - " ".length()).trimStackTraceEntriesFromBottom(prunedStackTrace);
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else if (stackTrace != null && stackTrace.length >= prunedStackTrace && n2 >= 0 && n2 < stackTrace.length) {
                this.stacktrace = new StackTraceElement[n2];
                System.arraycopy(stackTrace, "".length(), this.stacktrace, "".length(), this.stacktrace.length);
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else {
                this.field_85059_f = ("".length() != 0);
            }
        }
        this.crashReportSections.add(crashReportCategory);
        return crashReportCategory;
    }
    
    private static void I() {
        (I = new String[0x1A ^ 0x58])["".length()] = I("2*=DWAVRM^A", "qfbtg");
        CrashReport.I[" ".length()] = I("4))3\b\np\u001e\"\u0019\u0006964", "gPZGm");
        CrashReport.I["  ".length()] = I("'1\u001f\u001d\u0016\u00189\u0017\fU<=\u0003\u000b\u001c\u00056", "jXqxu");
        CrashReport.I["   ".length()] = I("8\u0013\f&\u0004\u0003\n\u00073E$\u001a\u001a \u0000\u001a", "wciTe");
        CrashReport.I[0x4C ^ 0x48] = I("\t>'", "JnrHL");
        CrashReport.I[0x44 ^ 0x41] = I("\"\u00140\u0007P>\u00104\u0015\u0019\u0007\u001b", "huFfp");
        CrashReport.I[0x31 ^ 0x37] = I("\u0000\u0012\u001b\u0017v\u001c>M 38\u0000\u0004\u00198", "JsmvV");
        CrashReport.I[0xC0 ^ 0xC7] = I("\u0007\"\u0015%\u00163", "JGxJd");
        CrashReport.I[0x8C ^ 0x84] = I("9>\u001aD\u0007\u001f\t0\u0017", "shWdA");
        CrashReport.I[0x28 ^ 0x21] = I("\u001d&\u0019727 \b", "THmtS");
        CrashReport.I[0x51 ^ 0x5B] = I("E`N\u000b\u0011\t)NnYb", "hMnCt");
        CrashReport.I[0x7A ^ 0x71] = I("\u0001$\u0018$#&\"\u0018$-hZ", "RPyGH");
        CrashReport.I[0x8C ^ 0x80] = I("O", "FjjaR");
        CrashReport.I[0x42 ^ 0x4F] = I(";\u001fw", "ZkWcy");
        CrashReport.I[0x32 ^ 0x3C] = I("m", "ghjym");
        CrashReport.I[0x74 ^ 0x7B] = I("{", "qSfLp");
        CrashReport.I[0x33 ^ 0x23] = I("hg", "bmGtC");
        CrashReport.I[0x3 ^ 0x12] = I("fWN]y\u0006\u0013\r\u0015:9\u001b\u0005\u0004y\b\b\u0002\u00031k(\u0006\u000069\u000eC]tfWi", "KzcpY");
        CrashReport.I[0xAC ^ 0xBE] = I("G\u007fr", "hPRxE");
        CrashReport.I[0x78 ^ 0x6B] = I("CO", "IEZzx");
        CrashReport.I[0x7A ^ 0x6E] = I("\u0003\u001a\u0005\u0004iw", "WshaS");
        CrashReport.I[0x6F ^ 0x7A] = I("s", "yFNOj");
        CrashReport.I[0xB2 ^ 0xA4] = I("\t*+\u001a0$?,\u0010-#ux", "MOXyB");
        CrashReport.I[0x9E ^ 0x89] = I("kR", "aXFvn");
        CrashReport.I[0x41 ^ 0x59] = I("gR7t=\b,\u0017=5\b<V#8\u00013\u0002<+\u0002-\u0011<y\u0002>V 1\bx\u0013&+\u0002*Zt0\u0019+V76\t=V$8\u00190V57\tx\u001785M3\u0018;.\u0003x\u00121-\f1\u001a'y\u0004+V5*M>\u001985\u0002/\u0005nS", "mXvTY");
        CrashReport.I[0x41 ^ 0x58] = I("o", "BGFCu");
        CrashReport.I[0x7F ^ 0x65] = I("FX", "LRXNu");
        CrashReport.I[0x4B ^ 0x50] = I("\u000f\u0006\u001d\u000f\u0013l\u0007\u0007\u0017W?\b\u001e\u0006W/\u001b\t\u0010\u001fl\u001b\r\u0013\u0018>\u001dH\u0017\u0018l", "Lihcw");
        CrashReport.I[0x54 ^ 0x48] = I("\u001d+3\u0006\u001f:81G\u0002=*1\u001fK: t\u0004\u00192=<G\u00196>;\u0015\u001fs&5\t\u000f?+&GC", "SNTgk");
        CrashReport.I[0x1 ^ 0x1C] = I("d", "KPQuS");
        CrashReport.I[0x5 ^ 0x1B] = I("[", "rntSZ");
        CrashReport.I[0x16 ^ 0x9] = I("/;9u\u0017\u001d'v \u0017X&&u\u0010\u00106v\u0001*,l", "xSVUd");
        CrashReport.I[0x8A ^ 0xAA] = I("\u0001\u0005\n\u0001;0\u001b\u0006\u001d%c\u0000O\u0014--\u001d\bS6+S\u001f\u001f#*]O=-hS\u001d\u0016#(\u001f\u0016_b0\u001b\u000e\u0007b3\u0012\u001cS11\u0003\u001f\u001c1!\u0017O\u0007-d\u001b\u000e\u00032!\u001dA", "DsosB");
        CrashReport.I[0xB3 ^ 0x92] = I("'\fchGR $\"I;D))I\u0006\f,2V", "rdMFi");
        CrashReport.I[0x5F ^ 0x7D] = I("\u0005- \u0000B", "JBPsl");
        CrashReport.I[0xA6 ^ 0x85] = I("<+?Z.\u0002'f\u0003%\u001ec\"\u0015j\u001f+'\u000eu", "kCFzJ");
        CrashReport.I[0xA6 ^ 0x82] = I("\u0011r\u001e\u0017\f4r\u000b\u0013\rx<\u0017\u0005Ibz", "XRxri");
        CrashReport.I[0x76 ^ 0x53] = I("\u00022N2\n+e", "OKnPk");
        CrashReport.I[0x7B ^ 0x5D] = I(",e%L1\n0:\u0015nE\u0006)\u001a'K", "eBHlB");
        CrashReport.I[0x42 ^ 0x65] = I("%U9<\u0019L\f:,M\b\u001a\"7CL&:+\u001f\u0015Uoq", "luUYm");
        CrashReport.I[0x76 ^ 0x5E] = I("+\u0003q\f\u0007\u0001M3\n\u0006\u0003\u0005%X\u001c\r\t4TO-M3\u0017\u001a\u0003\u0005%X\u0016\u000b\u0018q\u0019O\u0010\b5\u001c\u0016D\u000f4\u0019\u001dE", "dmQxo");
        CrashReport.I[0xBA ^ 0x93] = I("\u001c,+$=tm&6-+4lyj", "XMBWD");
        CrashReport.I[0x71 ^ 0x5B] = I("\u001d\u0019o}J\u001bQ$>\u0005%Q88\u000b&Q\u0006p\u000e;\u0015o'\u0018=\u001f(q", "RqOPj");
        CrashReport.I[0x5B ^ 0x70] = I("\u001d\"\u0003VM!/\u001b\u000eM!.\u0019\u0011\u000104[Z%0/\u001f\u0012\bt", "UGzzm");
        CrashReport.I[0x69 ^ 0x45] = I("\u0005k7!\u0007!.u\t\u000f\"%0?\u0004#%0c", "LKUMf");
        CrashReport.I[0x79 ^ 0x54] = I("\r<\u0013Z\u0006<<\u0013\u0016\u0011t'\u0014\u0003U;&\u0014Z\u0006= \u0012\u001f\u0007t4\u0007\u0017\u0010xs+\u0013\u001b76\u0014\u001b\u0013 r", "TSfzu");
        CrashReport.I[0x39 ^ 0x17] = I("\u0002\u0018<m%f\u00157j\"'\u0013|j\u0018a\u001b>j5)W0/%2\u0012 j?#\u000f&j%/\u001a7fq\u000fW\"8>+\u001e!/p", "FwRJQ");
        CrashReport.I[0x1F ^ 0x30] = I("\u001d\t(B$y\u0004#E#8\u0002jE88\u0010#E1y\u000e3\u0002qyZu", "YfFeP");
        CrashReport.I[0xA6 ^ 0x96] = I(";w\b!$\u0006w\u0006;9U#B?9\u001d B#?\u0013#B#2\u001c#B#%\u001d9\u0005tmZ", "rWbTW");
        CrashReport.I[0x86 ^ 0xB7] = I("\u0004\u0006\u00029\u001cw\u0019\u0006u\u0000;\u000f\u001au\u0011w\t\u00028\u0015h", "WncUp");
        CrashReport.I[0x24 ^ 0x16] = I("\u0010\u001c+<\u0000a\u0001-&\u00002\u001d.1Ia b?\n4\u0005&&B5I5'\u00173\u0010b%\u001c2\f..E \u000b-=\u0011a\u001d*)\u0011o", "AiBHe");
        CrashReport.I[0xA2 ^ 0x91] = I("?M\u000309V.\u00189\"\u0018\u001eA\"\"\u0003\u0001\u0005;j\u0002M\t4;\u0013M\u0015=$\u0005M\u0011'\"\u0014\u0001\u00048c", "vmaUM");
        CrashReport.I[0x77 ^ 0x43] = I("\u0003\b*\u00042p]p", "PgXvK");
        CrashReport.I[0x9F ^ 0xAA] = I("2\u00146\u0011\u0004\b\u0012!@V)\u0000,\u0000XA6!\r\u001aMA0\t\u001f\u0012A-\u0012V\u0000\u0016/\u0016\u0017\u0013\u0005j", "aaDav");
        CrashReport.I[0x9A ^ 0xAC] = I("'\r\u001d5\u0012P\u001b\u0007,V\u001c\u000b\u0003<V\u0011B\u000b,\u0006\u0013\u0003\u0003<I", "pbhYv");
        CrashReport.I[0x80 ^ 0xB7] = I("\r\u000e]V\u0002b\nS;\"+\u0002\u0010\u0004*#\u0013_V*+\u0003S?l(G\u0012V(7\u0006\u0000\u001e*-\b\u001f\u001f(k", "EgsvK");
        CrashReport.I[0xFC ^ 0xC4] = I(" ?)LA<8(\f\u0018A", "oPAba");
        CrashReport.I[0x9B ^ 0xA2] = I(".\u001b+\u0018G\u001e\u001c'\u0018\t]\u0007b\u0006\u0006\u0011\u0016b\n\t\u0003S1\u000e\t\t\u0016c", "zsBkg");
        CrashReport.I[0x6A ^ 0x50] = I("5\u001d0h\u0011\u0011U <X\u0000\u0007,)\u0013\u000b\u001b.hBJ", "buIHx");
        CrashReport.I[0x38 ^ 0x3] = I("\u0003\u001a\u001f_\u0017g\u0011\u001eX\u0017/\u0014\u0005V", "Guqxc");
        CrashReport.I[0xAC ^ 0x90] = I("\u001c>\u0011!cs\u001f\u001a(9s#\u0007;9sqZ", "SKrIM");
        CrashReport.I[0x6B ^ 0x56] = I("\u0017\u001b'T;+T?\u0016( Z", "NtRsI");
        CrashReport.I[0x13 ^ 0x2D] = I("!8\f=V\u001c#E/V\u0001?\u000e+\u0018U6\n<VDp\u0003<\u0013\u0010p\r;\u0011[p7+\u0012\u00105\bn\u0017\u0001p\u001c!\u0003\u0007p\u000b+\u0017\u00075\u0016:V8?\u000f/\u0018\u0012#\u0011/LU\u000b\u001b0> \u0017\u001b0+", "uPeNv");
        CrashReport.I[0x5E ^ 0x61] = I("\u0016?\u001f\u0003\fb6\b\u0014I$8\u000f\u0003I.>\u001d\u0019\u001d1v", "BWzqi");
        CrashReport.I[0x26 ^ 0x66] = I("\t!7g,?t4(7 'c(+k9:g(*7+.+.z", "KTCGE");
        CrashReport.I[0x24 ^ 0x65] = I("\u00013\u0018 (v9\u00039<34\u0018t$8;\u001a58:;\u000e84v`D", "VZlTQ");
    }
    
    private static String getWittyComment() {
        final String[] array = new String[0x3E ^ 0x1C];
        array["".length()] = CrashReport.I[0x7E ^ 0x61];
        array[" ".length()] = CrashReport.I[0xB5 ^ 0x95];
        array["  ".length()] = CrashReport.I[0x22 ^ 0x3];
        array["   ".length()] = CrashReport.I[0x51 ^ 0x73];
        array[0x8C ^ 0x88] = CrashReport.I[0xB5 ^ 0x96];
        array[0xE ^ 0xB] = CrashReport.I[0x5C ^ 0x78];
        array[0x27 ^ 0x21] = CrashReport.I[0xE4 ^ 0xC1];
        array[0x38 ^ 0x3F] = CrashReport.I[0x17 ^ 0x31];
        array[0x4D ^ 0x45] = CrashReport.I[0x4E ^ 0x69];
        array[0x35 ^ 0x3C] = CrashReport.I[0x64 ^ 0x4C];
        array[0xBD ^ 0xB7] = CrashReport.I[0x1A ^ 0x33];
        array[0x8A ^ 0x81] = CrashReport.I[0x8C ^ 0xA6];
        array[0xC ^ 0x0] = CrashReport.I[0x87 ^ 0xAC];
        array[0xBE ^ 0xB3] = CrashReport.I[0x42 ^ 0x6E];
        array[0x63 ^ 0x6D] = CrashReport.I[0x6A ^ 0x47];
        array[0xA2 ^ 0xAD] = CrashReport.I[0x55 ^ 0x7B];
        array[0x56 ^ 0x46] = CrashReport.I[0xA8 ^ 0x87];
        array[0xA4 ^ 0xB5] = CrashReport.I[0x7E ^ 0x4E];
        array[0xA2 ^ 0xB0] = CrashReport.I[0x4E ^ 0x7F];
        array[0x91 ^ 0x82] = CrashReport.I[0x84 ^ 0xB6];
        array[0x85 ^ 0x91] = CrashReport.I[0xA7 ^ 0x94];
        array[0x8C ^ 0x99] = CrashReport.I[0x82 ^ 0xB6];
        array[0x95 ^ 0x83] = CrashReport.I[0xE ^ 0x3B];
        array[0xA0 ^ 0xB7] = CrashReport.I[0x3B ^ 0xD];
        array[0x2 ^ 0x1A] = CrashReport.I[0x9B ^ 0xAC];
        array[0x10 ^ 0x9] = CrashReport.I[0xA2 ^ 0x9A];
        array[0x25 ^ 0x3F] = CrashReport.I[0x72 ^ 0x4B];
        array[0xAC ^ 0xB7] = CrashReport.I[0x80 ^ 0xBA];
        array[0x43 ^ 0x5F] = CrashReport.I[0x4C ^ 0x77];
        array[0xA ^ 0x17] = CrashReport.I[0x74 ^ 0x48];
        array[0x90 ^ 0x8E] = CrashReport.I[0xD ^ 0x30];
        array[0x12 ^ 0xD] = CrashReport.I[0x1 ^ 0x3F];
        array[0xAD ^ 0x8D] = CrashReport.I[0x9D ^ 0xA2];
        array[0x19 ^ 0x38] = CrashReport.I[0x1F ^ 0x5F];
        final String[] array2 = array;
        try {
            return array2[(int)(System.nanoTime() % array2.length)];
        }
        catch (Throwable t) {
            return CrashReport.I[0xC3 ^ 0x82];
        }
    }
    
    static {
        I();
        __OBFID = CrashReport.I["".length()];
        logger = LogManager.getLogger();
    }
    
    public Throwable getCrashCause() {
        return this.cause;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public CrashReport(final String description, final Throwable cause) {
        this.theReportCategory = new CrashReportCategory(this, CrashReport.I[" ".length()]);
        this.crashReportSections = Lists.newArrayList();
        this.field_85059_f = (" ".length() != 0);
        this.stacktrace = new StackTraceElement["".length()];
        this.reported = ("".length() != 0);
        this.description = description;
        this.cause = cause;
        this.populateEnvironment();
    }
    
    public CrashReportCategory makeCategory(final String s) {
        return this.makeCategoryDepth(s, " ".length());
    }
    
    public boolean saveToFile(final File crashReportFile) {
        if (this.crashReportFile != null) {
            return "".length() != 0;
        }
        if (crashReportFile.getParentFile() != null) {
            crashReportFile.getParentFile().mkdirs();
        }
        try {
            final FileWriter fileWriter = new FileWriter(crashReportFile);
            fileWriter.write(this.getCompleteReport());
            fileWriter.close();
            this.crashReportFile = crashReportFile;
            return " ".length() != 0;
        }
        catch (Throwable t) {
            CrashReport.logger.error(CrashReport.I[0xA8 ^ 0xB3] + crashReportFile, t);
            return "".length() != 0;
        }
    }
    
    public File getFile() {
        return this.crashReportFile;
    }
    
    public String getCompleteReport() {
        if (!this.reported) {
            this.reported = (" ".length() != 0);
            CrashReporter.onCrashReport(this);
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(CrashReport.I[0x70 ^ 0x61]);
        sb.append(CrashReport.I[0x10 ^ 0x2]);
        sb.append(getWittyComment());
        sb.append(CrashReport.I[0x13 ^ 0x0]);
        sb.append(CrashReport.I[0xAD ^ 0xB9]);
        sb.append(new SimpleDateFormat().format(new Date()));
        sb.append(CrashReport.I[0xA1 ^ 0xB4]);
        sb.append(CrashReport.I[0x98 ^ 0x8E]);
        sb.append(this.description);
        sb.append(CrashReport.I[0x3E ^ 0x29]);
        sb.append(this.getCauseStackTraceOrString());
        sb.append(CrashReport.I[0x3E ^ 0x26]);
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < (0x8 ^ 0x5F)) {
            sb.append(CrashReport.I[0x11 ^ 0x8]);
            ++i;
        }
        sb.append(CrashReport.I[0x56 ^ 0x4C]);
        this.getSectionsInStringBuilder(sb);
        return sb.toString();
    }
    
    public void getSectionsInStringBuilder(final StringBuilder sb) {
        if ((this.stacktrace == null || this.stacktrace.length <= 0) && this.crashReportSections.size() > 0) {
            this.stacktrace = (StackTraceElement[])ArrayUtils.subarray((Object[])this.crashReportSections.get("".length()).getStackTrace(), "".length(), " ".length());
        }
        if (this.stacktrace != null && this.stacktrace.length > 0) {
            sb.append(CrashReport.I[0x9D ^ 0x97]);
            sb.append(CrashReport.I[0x38 ^ 0x33]);
            final StackTraceElement[] stacktrace;
            final int length = (stacktrace = this.stacktrace).length;
            int i = "".length();
            "".length();
            if (0 == 3) {
                throw null;
            }
            while (i < length) {
                sb.append(CrashReport.I[0xA4 ^ 0xA8]).append(CrashReport.I[0x9F ^ 0x92]).append(stacktrace[i].toString());
                sb.append(CrashReport.I[0xA2 ^ 0xAC]);
                ++i;
            }
            sb.append(CrashReport.I[0x7 ^ 0x8]);
        }
        final Iterator<CrashReportCategory> iterator = (Iterator<CrashReportCategory>)this.crashReportSections.iterator();
        "".length();
        if (2 <= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().appendToStringBuilder(sb);
            sb.append(CrashReport.I[0xAA ^ 0xBA]);
        }
        this.theReportCategory.appendToStringBuilder(sb);
    }
    
    private void populateEnvironment() {
        this.theReportCategory.addCrashSectionCallable(CrashReport.I["  ".length()], new Callable(this) {
            private static final String[] I;
            private static final String __OBFID;
            final CrashReport this$0;
            
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
                    if (4 == 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            static {
                I();
                __OBFID = CrashReport$1.I[" ".length()];
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("GGrY]", "viJwe");
                CrashReport$1.I[" ".length()] = I("1\u0002%VTB~KW]E", "rNzfd");
            }
            
            @Override
            public String call() {
                return CrashReport$1.I["".length()];
            }
        });
        this.theReportCategory.addCrashSectionCallable(CrashReport.I["   ".length()], new Callable(this) {
            private static final String __OBFID;
            private static final String[] I;
            final CrashReport this$0;
            
            static {
                I();
                __OBFID = CrashReport$2.I[0x6C ^ 0x69];
            }
            
            private static void I() {
                (I = new String[0x9F ^ 0x99])["".length()] = I("\b+I\r\u0005\n=", "gXgcd");
                CrashReport$2.I[" ".length()] = I("RR", "rzFYx");
                CrashReport$2.I["  ".length()] = I("6>X\u000f6:%", "YMvnD");
                CrashReport$2.I["   ".length()] = I("|b5\u000f\u000b&+,\u0004Y", "UBCjy");
                CrashReport$2.I[0x84 ^ 0x80] = I("\u001d$}\u0007=\u0000$:\u001e6", "rWSqX");
                CrashReport$2.I[0x32 ^ 0x37] = I("\u0002$\u0018S~qXvQ|s", "AhGcN");
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
                    if (-1 >= 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() {
                return String.valueOf(System.getProperty(CrashReport$2.I["".length()])) + CrashReport$2.I[" ".length()] + System.getProperty(CrashReport$2.I["  ".length()]) + CrashReport$2.I["   ".length()] + System.getProperty(CrashReport$2.I[0xA4 ^ 0xA0]);
            }
        });
        this.theReportCategory.addCrashSectionCallable(CrashReport.I[0x6B ^ 0x6F], new CrashReport$31(this));
        this.theReportCategory.addCrashSectionCallable(CrashReport.I[0xD ^ 0x8], new Callable(this) {
            final CrashReport this$0;
            private static final String __OBFID;
            private static final String[] I;
            
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
                    if (true != true) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                I();
                __OBFID = CrashReport$3.I["   ".length()];
            }
            
            private static void I() {
                (I = new String[0x2B ^ 0x2F])["".length()] = I("\u001d\u0013,\u0000M\u0001\u0017(\u0012\n\u0018\u001c", "wrZac");
                CrashReport$3.I[" ".length()] = I("vz", "ZZFkP");
                CrashReport$3.I["  ".length()] = I("\u0005\n%/}\u0019\u000e=*<\u001d", "okSNS");
                CrashReport$3.I["   ".length()] = I("\u0005:([QvFFYU~", "Fvwka");
            }
            
            @Override
            public String call() {
                return String.valueOf(System.getProperty(CrashReport$3.I["".length()])) + CrashReport$3.I[" ".length()] + System.getProperty(CrashReport$3.I["  ".length()]);
            }
        });
        this.theReportCategory.addCrashSectionCallable(CrashReport.I[0x14 ^ 0x12], new Callable(this) {
            private static final String __OBFID;
            private static final String[] I;
            final CrashReport this$0;
            
            private static void I() {
                (I = new String[0x1C ^ 0x1A])["".length()] = I(")\u0012\u001d\u0010D5\u001eE\u001f\u000b.\u0016", "Cskqj");
                CrashReport$4.I[" ".length()] = I("Oz", "oRKWK");
                CrashReport$4.I["  ".length()] = I("'\u00047\u0019@;\bo\u0011\u0000+\n", "MeAxn");
                CrashReport$4.I["   ".length()] = I("|AT", "UmtYw");
                CrashReport$4.I[0x2 ^ 0x6] = I("\u001f\u0018\u0010\u0005I\u0003\u0014H\u0012\u0002\u001b\u001d\t\u0016", "uyfdg");
                CrashReport$4.I[0x11 ^ 0x14] = I(":\u0001<aDI}RcCL", "yMcQt");
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                I();
                __OBFID = CrashReport$4.I[0xB1 ^ 0xB4];
            }
            
            @Override
            public String call() {
                return String.valueOf(System.getProperty(CrashReport$4.I["".length()])) + CrashReport$4.I[" ".length()] + System.getProperty(CrashReport$4.I["  ".length()]) + CrashReport$4.I["   ".length()] + System.getProperty(CrashReport$4.I[0x28 ^ 0x2C]);
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
        });
        this.theReportCategory.addCrashSectionCallable(CrashReport.I[0xA3 ^ 0xA4], new Callable(this) {
            private static final String[] I;
            final CrashReport this$0;
            private static final String __OBFID;
            
            static {
                I();
                __OBFID = CrashReport$5.I[0x3F ^ 0x39];
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
                    if (2 != 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            private static void I() {
                (I = new String[0x84 ^ 0x83])["".length()] = I("d\u0000\t8\f7BX", "DbpLi");
                CrashReport$5.I[" ".length()] = I("L%6knCH", "lhtBN");
                CrashReport$5.I["  ".length()] = I("U\t7!5\u0006Kf", "ukNUP");
                CrashReport$5.I["   ".length()] = I("V\u00155CR\u0003(W\u001e\u001dV", "vXwjr");
                CrashReport$5.I[0x52 ^ 0x56] = I("c\u0005\u0000#\u000e0GQ", "CgyWk");
                CrashReport$5.I[0x57 ^ 0x52] = I("U\u0017\u0015o", "uZWFt");
                CrashReport$5.I[0x98 ^ 0x9E] = I(";\u0019\tBfHegAfJ", "xUVrV");
            }
            
            @Override
            public String call() {
                final Runtime runtime = Runtime.getRuntime();
                final long maxMemory = runtime.maxMemory();
                final long totalMemory = runtime.totalMemory();
                final long freeMemory = runtime.freeMemory();
                return String.valueOf(freeMemory) + CrashReport$5.I["".length()] + freeMemory / 1024L / 1024L + CrashReport$5.I[" ".length()] + totalMemory + CrashReport$5.I["  ".length()] + totalMemory / 1024L / 1024L + CrashReport$5.I["   ".length()] + maxMemory + CrashReport$5.I[0x48 ^ 0x4C] + maxMemory / 1024L / 1024L + CrashReport$5.I[0x32 ^ 0x37];
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        this.theReportCategory.addCrashSectionCallable(CrashReport.I[0x59 ^ 0x51], new Callable(this) {
            private static final String[] I;
            private static final String __OBFID;
            final CrashReport this$0;
            
            private static void I() {
                (I = new String[0x7C ^ 0x78])["".length()] = I("f+", "KsaNB");
                CrashReport$6.I[" ".length()] = I("v", "Vkjzo");
                CrashReport$6.I["  ".length()] = I("]1i\u001c\t\f4%SF]&", "xUIhf");
                CrashReport$6.I["   ".length()] = I("\n\u0001\u0015aZy}{bXp", "IMJQj");
            }
            
            @Override
            public String call() throws Exception {
                final List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
                int length = "".length();
                final StringBuilder sb = new StringBuilder();
                final Iterator<String> iterator = inputArguments.iterator();
                "".length();
                if (3 <= 0) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final String next = iterator.next();
                    if (next.startsWith(CrashReport$6.I["".length()])) {
                        if (length++ > 0) {
                            sb.append(CrashReport$6.I[" ".length()]);
                        }
                        sb.append((Object)next);
                    }
                }
                final String s = CrashReport$6.I["  ".length()];
                final Object[] array = new Object["  ".length()];
                array["".length()] = length;
                array[" ".length()] = sb.toString();
                return String.format(s, array);
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
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
                    if (3 == 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            static {
                I();
                __OBFID = CrashReport$6.I["   ".length()];
            }
        });
        this.theReportCategory.addCrashSectionCallable(CrashReport.I[0xAE ^ 0xA7], new Callable(this) {
            private static final String __OBFID;
            final CrashReport this$0;
            private static final String[] I;
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("9\u0015\fZfJibYcO", "zYSjV");
            }
            
            static {
                I();
                __OBFID = CrashReport$7.I["".length()];
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
                    if (0 < -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Object call() throws Exception {
                return IntCache.getCacheSizes();
            }
        });
        if (Reflector.FMLCommonHandler_enhanceCrashReport.exists()) {
            final Object call = Reflector.call(Reflector.FMLCommonHandler_instance, new Object["".length()]);
            final ReflectorMethod fmlCommonHandler_enhanceCrashReport = Reflector.FMLCommonHandler_enhanceCrashReport;
            final Object[] array = new Object["  ".length()];
            array["".length()] = this;
            array[" ".length()] = this.theReportCategory;
            Reflector.callString(call, fmlCommonHandler_enhanceCrashReport, array);
        }
    }
    
    public static CrashReport makeCrashReport(final Throwable t, final String s) {
        CrashReport crashReport;
        if (t instanceof ReportedException) {
            crashReport = ((ReportedException)t).getCrashReport();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else {
            crashReport = new CrashReport(s, t);
        }
        return crashReport;
    }
}
