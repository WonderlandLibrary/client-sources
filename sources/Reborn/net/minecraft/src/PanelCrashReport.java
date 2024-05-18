package net.minecraft.src;

import java.text.*;
import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import java.io.*;
import java.awt.*;

public class PanelCrashReport extends Panel
{
    public PanelCrashReport(final CrashReport par1CrashReport) {
        this.setBackground(new Color(3028036));
        this.setLayout(new BorderLayout());
        final StringWriter var2 = new StringWriter();
        par1CrashReport.getCrashCause().printStackTrace(new PrintWriter(var2));
        final String var3 = var2.toString();
        String var4 = "";
        String var5 = "";
        try {
            var5 = String.valueOf(var5) + "Generated " + new SimpleDateFormat().format(new Date()) + "\n";
            var5 = String.valueOf(var5) + "\n";
            var5 = String.valueOf(var5) + par1CrashReport.func_90021_c();
            var4 = GL11.glGetString(7936);
        }
        catch (Throwable var6) {
            var5 = String.valueOf(var5) + "[failed to get system properties (" + var6 + ")]\n";
        }
        var5 = String.valueOf(var5) + "\n\n";
        var5 = String.valueOf(var5) + var3;
        String var7 = "";
        var7 = String.valueOf(var7) + "\n";
        var7 = String.valueOf(var7) + "\n";
        if (var3.contains("Pixel format not accelerated")) {
            var7 = String.valueOf(var7) + "      Bad video card drivers!      \n";
            var7 = String.valueOf(var7) + "      -----------------------      \n";
            var7 = String.valueOf(var7) + "\n";
            var7 = String.valueOf(var7) + "Minecraft was unable to start because it failed to find an accelerated OpenGL mode.\n";
            var7 = String.valueOf(var7) + "This can usually be fixed by updating the video card drivers.\n";
            if (var4.toLowerCase().contains("nvidia")) {
                var7 = String.valueOf(var7) + "\n";
                var7 = String.valueOf(var7) + "You might be able to find drivers for your video card here:\n";
                var7 = String.valueOf(var7) + "  http://www.nvidia.com/\n";
            }
            else if (var4.toLowerCase().contains("ati")) {
                var7 = String.valueOf(var7) + "\n";
                var7 = String.valueOf(var7) + "You might be able to find drivers for your video card here:\n";
                var7 = String.valueOf(var7) + "  http://www.amd.com/\n";
            }
        }
        else {
            var7 = String.valueOf(var7) + "      Minecraft has crashed!      \n";
            var7 = String.valueOf(var7) + "      ----------------------      \n";
            var7 = String.valueOf(var7) + "\n";
            var7 = String.valueOf(var7) + "Minecraft has stopped running because it encountered a problem; " + par1CrashReport.getDescription() + "\n\n";
            File var8 = par1CrashReport.getFile();
            if (var8 == null) {
                par1CrashReport.saveToFile(new File(new File(Minecraft.getMinecraftDir(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt"), Minecraft.getMinecraft().getLogAgent());
                var8 = par1CrashReport.getFile();
            }
            if (var8 != null) {
                final String var9 = var8.getAbsolutePath();
                var7 = String.valueOf(var7) + "A full error report has been saved to " + var9 + " - Please include a copy of that file (Not this screen!) if you report this crash to anyone; without it, they will not be able to help fix the crash :(";
                var5 = "Full report at:\n" + var9 + "\nPlease show that file to Mojang, NOT just this screen!\n\n" + var5;
            }
            else {
                var7 = String.valueOf(var7) + "We were unable to save this report to a file.";
            }
            var7 = String.valueOf(var7) + "\n";
        }
        var7 = String.valueOf(var7) + "\n";
        var7 = String.valueOf(var7) + "\n";
        var7 = String.valueOf(var7) + "\n";
        var7 = String.valueOf(var7) + "--- BEGIN ERROR REPORT " + Integer.toHexString(var7.hashCode()) + " --------\n";
        var7 = String.valueOf(var7) + var5;
        var7 = String.valueOf(var7) + "--- END ERROR REPORT " + Integer.toHexString(var7.hashCode()) + " ----------\n";
        var7 = String.valueOf(var7) + "\n";
        var7 = String.valueOf(var7) + "\n";
        final TextArea var10 = new TextArea(var7, 0, 0, 1);
        var10.setFont(new Font("Monospaced", 0, 12));
        this.add(new CanvasMojangLogo(), "North");
        this.add(new CanvasCrashReport(80), "East");
        this.add(new CanvasCrashReport(80), "West");
        this.add(new CanvasCrashReport(100), "South");
        this.add(var10, "Center");
    }
}
