package net.java.games.input;

import java.io.*;

private static class JarFileFilter implements FileFilter
{
    public boolean accept(final File file) {
        return file.getName().toUpperCase().endsWith(".JAR");
    }
}
