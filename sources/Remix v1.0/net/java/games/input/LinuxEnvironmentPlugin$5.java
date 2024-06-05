package net.java.games.input;

import java.io.*;

static final class LinuxEnvironmentPlugin$5 implements FilenameFilter {
    public final boolean accept(final File dir, final String name) {
        return name.startsWith("js");
    }
}