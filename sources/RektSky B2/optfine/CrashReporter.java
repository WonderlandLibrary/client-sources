package optfine;

import net.minecraft.crash.*;
import net.minecraft.client.settings.*;
import java.util.*;

public class CrashReporter
{
    public static void onCrashReport(final CrashReport p_onCrashReport_0_) {
        try {
            final GameSettings gamesettings = Config.getGameSettings();
            if (gamesettings == null) {
                return;
            }
            if (!gamesettings.snooperEnabled) {
                return;
            }
            final String s = "http://optifine.net/crashReport";
            final String s2 = makeReport(p_onCrashReport_0_);
            final byte[] abyte = s2.getBytes("ASCII");
            final IFileUploadListener ifileuploadlistener = new IFileUploadListener() {
                @Override
                public void fileUploadFinished(final String p_fileUploadFinished_1_, final byte[] p_fileUploadFinished_2_, final Throwable p_fileUploadFinished_3_) {
                }
            };
            final Map map = new HashMap();
            map.put("OF-Version", Config.getVersion());
            map.put("OF-Summary", makeSummary(p_onCrashReport_0_));
            final FileUploadThread fileuploadthread = new FileUploadThread(s, map, abyte, ifileuploadlistener);
            fileuploadthread.setPriority(10);
            fileuploadthread.start();
            Thread.sleep(1000L);
        }
        catch (Exception exception) {
            Config.dbg(exception.getClass().getName() + ": " + exception.getMessage());
        }
    }
    
    private static String makeReport(final CrashReport p_makeReport_0_) {
        final StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("OptiFineVersion: " + Config.getVersion() + "\n");
        stringbuffer.append("Summary: " + makeSummary(p_makeReport_0_) + "\n");
        stringbuffer.append("\n");
        stringbuffer.append(p_makeReport_0_.getCompleteReport());
        stringbuffer.append("\n");
        stringbuffer.append("OpenGlVersion: " + Config.openGlVersion + "\n");
        stringbuffer.append("OpenGlRenderer: " + Config.openGlRenderer + "\n");
        stringbuffer.append("OpenGlVendor: " + Config.openGlVendor + "\n");
        stringbuffer.append("CpuCount: " + Config.getAvailableProcessors() + "\n");
        return stringbuffer.toString();
    }
    
    private static String makeSummary(final CrashReport p_makeSummary_0_) {
        final Throwable throwable = p_makeSummary_0_.getCrashCause();
        if (throwable == null) {
            return "Unknown";
        }
        final StackTraceElement[] astacktraceelement = throwable.getStackTrace();
        String s = "unknown";
        if (astacktraceelement.length > 0) {
            s = astacktraceelement[0].toString().trim();
        }
        final String s2 = throwable.getClass().getName() + ": " + throwable.getMessage() + " (" + p_makeSummary_0_.getDescription() + ") [" + s + "]";
        return s2;
    }
}
