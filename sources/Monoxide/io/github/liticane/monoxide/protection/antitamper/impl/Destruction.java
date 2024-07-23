package io.github.liticane.monoxide.protection.antitamper.impl;

import net.minecraft.util.Util;
import io.github.liticane.monoxide.protection.antitamper.AntiTamper;

import java.io.File;
import java.nio.file.Files;

public class Destruction extends AntiTamper {

    public Destruction() {
        super("Self Destruct", "Self destructs the client");
    }

    private static void selfDestructWindowsJARFile() throws Exception
    {
        String currentJARFilePath = getCurrentJarPath().toString();
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("cmd /c ping localhost -n 2 > nul && del \"" + currentJARFilePath + "\"");
    }

    public static void selfDestructJARFile() throws Exception
    {
        if (Util.getOSType() == Util.EnumOS.WINDOWS) {
            selfDestructWindowsJARFile();
        } else {
            File directoryFilePath = getCurrentJarPath();
            Files.delete(directoryFilePath.toPath());
        }
    }

}
