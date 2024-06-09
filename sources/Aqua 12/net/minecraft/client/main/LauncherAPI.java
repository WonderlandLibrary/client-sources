// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.client.main;

import java.util.Locale;
import java.io.File;

public class LauncherAPI
{
    public static void main(final String[] args) {
        final String userHome = System.getProperty("user.home", ".");
        File workingDirectory = null;
        switch (OS.getOperatingSystem()) {
            case LINUX: {
                workingDirectory = new File(userHome, ".minecraft/");
                break;
            }
            case WINDOWS: {
                final String applicationData = System.getenv("APPDATA");
                final String folder = (applicationData != null) ? applicationData : userHome;
                workingDirectory = new File(folder, ".minecraft/");
                break;
            }
            case MACOS: {
                workingDirectory = new File(userHome, "Library/Application Support/minecraft");
                break;
            }
            default: {
                workingDirectory = new File(userHome, "minecraft/");
                break;
            }
        }
        try {
            Main.main(new String[] { "--version", "FantaX", "--accessToken", "0", "--gameDir", new File(workingDirectory, ".").getAbsolutePath(), "--assetsDir", new File(workingDirectory, "assets").getAbsolutePath(), "--assetIndex", "1.8", "--userProperties", "{}" });
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
    
    public enum OS
    {
        WINDOWS, 
        MACOS, 
        LINUX, 
        OTHER;
        
        public static OS getOperatingSystem() {
            final String operatingSystem = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if (operatingSystem.contains("mac") || operatingSystem.contains("darwin")) {
                return OS.MACOS;
            }
            if (operatingSystem.contains("win")) {
                return OS.WINDOWS;
            }
            if (operatingSystem.contains("nux")) {
                return OS.LINUX;
            }
            return OS.OTHER;
        }
    }
}
