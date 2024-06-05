package net.java.games.input;

import java.security.*;

class DirectInputEnvironmentPlugin$4 implements PrivilegedAction {
    public final Object run() {
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
        return null;
    }
}