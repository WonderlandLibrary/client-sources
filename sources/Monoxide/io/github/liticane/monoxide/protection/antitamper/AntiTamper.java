package io.github.liticane.monoxide.protection.antitamper;

import java.io.File;
import java.net.URISyntaxException;

public abstract class AntiTamper {

    private final String name, description;

    public AntiTamper(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static String getDirectory()
    {
        if (isRunningFromJAR()) {
            return getCurrentJARDirectory();
        } else {
            return getCurrentProjectDirectory();
        }
    }

    private static String getJarName()
    {
        return new File(AntiTamper.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath())
                .getName();
    }

    public static boolean isRunningFromJAR()
    {
        String jarName = getJarName();
        return jarName.contains(".jar");
    }

    private static String getCurrentProjectDirectory()
    {
        return new File("").getAbsolutePath();
    }

    public static String getCurrentJARDirectory()
    {
        try {
            return getCurrentJarPath().getParent();
        } catch (URISyntaxException exception) {
            exception.printStackTrace();
        }

        throw new IllegalStateException("Unexpected null JAR path");
    }

    public static File getCurrentJarPath() throws URISyntaxException
    {
        return new File(AntiTamper.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath());
    }

}
