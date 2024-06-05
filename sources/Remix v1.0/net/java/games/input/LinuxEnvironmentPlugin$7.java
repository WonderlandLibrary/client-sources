package net.java.games.input;

import java.security.*;
import java.io.*;
import java.util.*;

static final class LinuxEnvironmentPlugin$7 implements PrivilegedAction {
    private final /* synthetic */ File val$dir;
    private final /* synthetic */ FilenameFilter val$filter;
    
    public Object run() {
        final File[] files = this.val$dir.listFiles(this.val$filter);
        Arrays.sort(files, new Comparator() {
            public int compare(final Object f1, final Object f2) {
                return ((File)f1).getName().compareTo(((File)f2).getName());
            }
        });
        return files;
    }
}