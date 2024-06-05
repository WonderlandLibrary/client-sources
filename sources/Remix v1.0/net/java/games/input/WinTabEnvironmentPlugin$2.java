package net.java.games.input;

import java.security.*;

static final class WinTabEnvironmentPlugin$2 implements PrivilegedAction {
    private final /* synthetic */ String val$property;
    
    public Object run() {
        return System.getProperty(this.val$property);
    }
}