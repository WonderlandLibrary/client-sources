package optfine;

import net.minecraft.crash.*;
import java.util.*;
import net.minecraft.client.settings.*;

public class CrashReporter
{
    private static final String[] I;
    
    private static String makeSummary(final CrashReport crashReport) {
        final Throwable crashCause = crashReport.getCrashCause();
        if (crashCause == null) {
            return CrashReporter.I[0x7F ^ 0x6C];
        }
        final StackTraceElement[] stackTrace = crashCause.getStackTrace();
        String trim = CrashReporter.I[0x7A ^ 0x6E];
        if (stackTrace.length > 0) {
            trim = stackTrace["".length()].toString().trim();
        }
        return String.valueOf(crashCause.getClass().getName()) + CrashReporter.I[0x66 ^ 0x73] + crashCause.getMessage() + CrashReporter.I[0x9C ^ 0x8A] + crashReport.getDescription() + CrashReporter.I[0x78 ^ 0x6F] + CrashReporter.I[0x37 ^ 0x2F] + trim + CrashReporter.I[0x19 ^ 0x0];
    }
    
    static {
        I();
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
    
    public static void onCrashReport(final CrashReport crashReport) {
        try {
            final GameSettings gameSettings = Config.getGameSettings();
            if (gameSettings == null) {
                return;
            }
            if (!gameSettings.snooperEnabled) {
                return;
            }
            final String s = CrashReporter.I["".length()];
            final byte[] bytes = makeReport(crashReport).getBytes(CrashReporter.I[" ".length()]);
            final IFileUploadListener fileUploadListener = new IFileUploadListener() {
                @Override
                public void fileUploadFinished(final String s, final byte[] array, final Throwable t) {
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
                        if (1 >= 2) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
            };
            final HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put(CrashReporter.I["  ".length()], Config.getVersion());
            hashMap.put(CrashReporter.I["   ".length()], makeSummary(crashReport));
            final FileUploadThread fileUploadThread = new FileUploadThread(s, hashMap, bytes, fileUploadListener);
            fileUploadThread.setPriority(0x0 ^ 0xA);
            fileUploadThread.start();
            Thread.sleep(1000L);
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        catch (Exception ex) {
            Config.dbg(String.valueOf(ex.getClass().getName()) + CrashReporter.I[0xB9 ^ 0xBD] + ex.getMessage());
        }
    }
    
    private static void I() {
        (I = new String[0xE ^ 0x14])["".length()] = I("+96:Llb-:\u0002*++$\u0013m#'>Y ?#9\u001e\u0011(2%\u00047", "CMBJv");
        CrashReporter.I[" ".length()] = I("0\t59\r", "qZvpD");
        CrashReporter.I["  ".length()] = I("\u0017\fA4'*9\u0005\r,", "XJlbB");
        CrashReporter.I["   ".length()] = I("\u0007\u0015F\u0000\u001e%>\n!\u0012", "HSkSk");
        CrashReporter.I[0xBE ^ 0xBA] = I("vy", "LYexr");
        CrashReporter.I[0x4A ^ 0x4F] = I("\b!\u00069-.?\u0017\u0006\u000e5\"\u001b?\u0005}q", "GQrPk");
        CrashReporter.I[0x8B ^ 0x8D] = I("^", "TiQuI");
        CrashReporter.I[0x70 ^ 0x77] = I(";9*4\u000b\u001a5}y", "hLGYj");
        CrashReporter.I[0x88 ^ 0x80] = I("@", "JfuRb");
        CrashReporter.I[0xB8 ^ 0xB1] = I("A", "KtwWT");
        CrashReporter.I[0x6D ^ 0x67] = I("Z", "POIVx");
        CrashReporter.I[0xCE ^ 0xC5] = I("\u0016*0\f*5\f0\u0010\u001e05;XM", "YZUbm");
        CrashReporter.I[0x5A ^ 0x56] = I("g", "mbQBg");
        CrashReporter.I[0x10 ^ 0x1D] = I("\n:\u001f6\u0002)\u0018\u001f6! 8\u001f*\u007fe", "EJzXE");
        CrashReporter.I[0x21 ^ 0x2F] = I("^", "TulZH");
        CrashReporter.I[0x99 ^ 0x96] = I("\u001e\u001e\u001c\u0016\u0016=8\u001c\u00165>\u001cCX", "QnyxQ");
        CrashReporter.I[0x5F ^ 0x4F] = I("|", "vRcch");
        CrashReporter.I[0x4A ^ 0x5B] = I(" \u001c90?\u0016\u00028Ip", "clLsP");
        CrashReporter.I[0xB7 ^ 0xA5] = I("F", "LtcvU");
        CrashReporter.I[0xA7 ^ 0xB4] = I("\u0005*\t*?'*", "PDbDP");
        CrashReporter.I[0x80 ^ 0x94] = I("\u0001\b$>\u001f\u0003\b", "tfOPp");
        CrashReporter.I[0xD ^ 0x18] = I("xU", "BuWdl");
        CrashReporter.I[0x80 ^ 0x96] = I("TZ", "trtic");
        CrashReporter.I[0x54 ^ 0x43] = I("q", "XNgnn");
        CrashReporter.I[0xD8 ^ 0xC0] = I("k3", "KhzOr");
        CrashReporter.I[0x20 ^ 0x39] = I("0", "mmJdr");
    }
    
    private static String makeReport(final CrashReport crashReport) {
        final StringBuffer sb = new StringBuffer();
        sb.append(CrashReporter.I[0xA9 ^ 0xAC] + Config.getVersion() + CrashReporter.I[0x5E ^ 0x58]);
        sb.append(CrashReporter.I[0xC ^ 0xB] + makeSummary(crashReport) + CrashReporter.I[0x77 ^ 0x7F]);
        sb.append(CrashReporter.I[0x85 ^ 0x8C]);
        sb.append(crashReport.getCompleteReport());
        sb.append(CrashReporter.I[0x43 ^ 0x49]);
        sb.append(CrashReporter.I[0x2C ^ 0x27] + Config.openGlVersion + CrashReporter.I[0x16 ^ 0x1A]);
        sb.append(CrashReporter.I[0x47 ^ 0x4A] + Config.openGlRenderer + CrashReporter.I[0x6B ^ 0x65]);
        sb.append(CrashReporter.I[0xA2 ^ 0xAD] + Config.openGlVendor + CrashReporter.I[0x4E ^ 0x5E]);
        sb.append(CrashReporter.I[0xA3 ^ 0xB2] + Config.getAvailableProcessors() + CrashReporter.I[0x24 ^ 0x36]);
        return sb.toString();
    }
}
