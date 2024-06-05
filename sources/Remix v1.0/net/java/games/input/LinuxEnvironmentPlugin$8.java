package net.java.games.input;

import java.io.*;

class LinuxEnvironmentPlugin$8 implements FilenameFilter {
    public final boolean accept(final File dir, final String name) {
        return name.startsWith("event");
    }
}