package net.java.games.input;

import java.security.*;

static final class WinTabEnvironmentPlugin$3 implements PrivilegedAction {
    private final /* synthetic */ String val$property;
    private final /* synthetic */ String val$default_value;
    
    public Object run() {
        return System.getProperty(this.val$property, this.val$default_value);
    }
}