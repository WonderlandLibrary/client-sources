package net.java.games.input;

import java.security.*;
import java.io.*;

static final class LinuxEnvironmentPlugin$6 implements PrivilegedAction {
    private final /* synthetic */ File val$file;
    
    public Object run() {
        return this.val$file.getAbsolutePath();
    }
}