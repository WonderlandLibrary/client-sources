package HORIZON-6-0-SKIDPROTECTION;

import java.util.Map;
import java.util.HashMap;

public class CrashReporter
{
    public static void HorizonCode_Horizon_È(final CrashReport crashReport) {
        try {
            final GameSettings e = Config.ÇªØ­();
            if (e == null) {
                return;
            }
            if (!e.áŒŠá€) {
                return;
            }
            final String url = "http://optifine.net/crashReport";
            final String reportStr = Â(crashReport);
            final byte[] content = reportStr.getBytes("ASCII");
            final IFileUploadListener listener = new IFileUploadListener() {
                @Override
                public void HorizonCode_Horizon_È(final String url, final byte[] content, final Throwable exception) {
                }
            };
            final HashMap headers = new HashMap();
            headers.put("OF-Version", Config.HorizonCode_Horizon_È());
            headers.put("OF-Summary", Ý(crashReport));
            final FileUploadThread fut = new FileUploadThread(url, headers, content, listener);
            fut.setPriority(10);
            fut.start();
            Thread.sleep(1000L);
        }
        catch (Exception var8) {
            Config.HorizonCode_Horizon_È(String.valueOf(var8.getClass().getName()) + ": " + var8.getMessage());
        }
    }
    
    private static String Â(final CrashReport crashReport) {
        final StringBuffer sb = new StringBuffer();
        sb.append("OptiFineVersion: " + Config.HorizonCode_Horizon_È() + "\n");
        sb.append("Summary: " + Ý(crashReport) + "\n");
        sb.append("\n");
        sb.append(crashReport.Ø­áŒŠá());
        sb.append("\n");
        sb.append("OpenGlVersion: " + Config.Ó + "\n");
        sb.append("OpenGlRenderer: " + Config.à + "\n");
        sb.append("OpenGlVendor: " + Config.Ø + "\n");
        sb.append("CpuCount: " + Config.Šà() + "\n");
        return sb.toString();
    }
    
    private static String Ý(final CrashReport crashReport) {
        final Throwable t = crashReport.Â();
        if (t == null) {
            return "Unknown";
        }
        final StackTraceElement[] traces = t.getStackTrace();
        String firstTrace = "unknown";
        if (traces.length > 0) {
            firstTrace = traces[0].toString().trim();
        }
        final String sum = String.valueOf(t.getClass().getName()) + ": " + t.getMessage() + " (" + crashReport.HorizonCode_Horizon_È() + ")" + " [" + firstTrace + "]";
        return sum;
    }
}
