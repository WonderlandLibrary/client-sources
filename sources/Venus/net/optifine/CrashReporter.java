/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.util.HashMap;
import net.minecraft.client.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.optifine.Config;
import net.optifine.http.FileUploadThread;
import net.optifine.http.IFileUploadListener;
import net.optifine.shaders.Shaders;

public class CrashReporter {
    public static void onCrashReport(CrashReport crashReport, CrashReportCategory crashReportCategory) {
        try {
            Throwable throwable = crashReport.getCrashCause();
            if (throwable == null) {
                return;
            }
            if (throwable.getClass().getName().contains(".fml.client.SplashProgress")) {
                return;
            }
            if (throwable.getClass() == Throwable.class) {
                return;
            }
            CrashReporter.extendCrashReport(crashReportCategory);
            GameSettings gameSettings = Config.getGameSettings();
            if (gameSettings == null) {
                return;
            }
            if (!gameSettings.snooper) {
                return;
            }
            String string = "http://optifine.net/crashReport";
            String string2 = CrashReporter.makeReport(crashReport);
            byte[] byArray = string2.getBytes("ASCII");
            IFileUploadListener iFileUploadListener = new IFileUploadListener(){

                @Override
                public void fileUploadFinished(String string, byte[] byArray, Throwable throwable) {
                }
            };
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("OF-Version", Config.getVersion());
            hashMap.put("OF-Summary", CrashReporter.makeSummary(crashReport));
            FileUploadThread fileUploadThread = new FileUploadThread(string, hashMap, byArray, iFileUploadListener);
            fileUploadThread.setPriority(10);
            fileUploadThread.start();
            Thread.sleep(1000L);
        } catch (Exception exception) {
            Config.dbg(exception.getClass().getName() + ": " + exception.getMessage());
        }
    }

    private static String makeReport(CrashReport crashReport) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("OptiFineVersion: " + Config.getVersion() + "\n");
        stringBuffer.append("Summary: " + CrashReporter.makeSummary(crashReport) + "\n");
        stringBuffer.append("\n");
        stringBuffer.append(crashReport.getCompleteReport());
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }

    private static String makeSummary(CrashReport crashReport) {
        Throwable throwable = crashReport.getCrashCause();
        if (throwable == null) {
            return "Unknown";
        }
        StackTraceElement[] stackTraceElementArray = throwable.getStackTrace();
        String string = "unknown";
        if (stackTraceElementArray.length > 0) {
            string = stackTraceElementArray[0].toString().trim();
        }
        return throwable.getClass().getName() + ": " + throwable.getMessage() + " (" + crashReport.getDescription() + ") [" + string + "]";
    }

    public static void extendCrashReport(CrashReportCategory crashReportCategory) {
        crashReportCategory.addDetail("OptiFine Version", Config.getVersion());
        crashReportCategory.addDetail("OptiFine Build", Config.getBuild());
        if (Config.getGameSettings() != null) {
            crashReportCategory.addDetail("Render Distance Chunks", "" + Config.getChunkViewDistance());
            crashReportCategory.addDetail("Mipmaps", "" + Config.getMipmapLevels());
            crashReportCategory.addDetail("Anisotropic Filtering", "" + Config.getAnisotropicFilterLevel());
            crashReportCategory.addDetail("Antialiasing", "" + Config.getAntialiasingLevel());
            crashReportCategory.addDetail("Multitexture", "" + Config.isMultiTexture());
        }
        crashReportCategory.addDetail("Shaders", Shaders.getShaderPackName());
        crashReportCategory.addDetail("OpenGlVersion", Config.openGlVersion);
        crashReportCategory.addDetail("OpenGlRenderer", Config.openGlRenderer);
        crashReportCategory.addDetail("OpenGlVendor", Config.openGlVendor);
        crashReportCategory.addDetail("CpuCount", "" + Config.getAvailableProcessors());
    }
}

