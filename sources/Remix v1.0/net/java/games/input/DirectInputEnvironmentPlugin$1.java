package net.java.games.input;

import java.security.*;
import java.io.*;

static final class DirectInputEnvironmentPlugin$1 implements PrivilegedAction {
    private final /* synthetic */ String val$lib_name;
    
    public final Object run() {
        try {
            final String lib_path = System.getProperty("net.java.games.input.librarypath");
            if (lib_path != null) {
                System.load(lib_path + File.separator + System.mapLibraryName(this.val$lib_name));
            }
            else {
                System.loadLibrary(this.val$lib_name);
            }
        }
        catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            DirectInputEnvironmentPlugin.access$002(false);
        }
        return null;
    }
}