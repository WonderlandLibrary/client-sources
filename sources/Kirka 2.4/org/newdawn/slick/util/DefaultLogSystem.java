/*
 * Decompiled with CFR 0.143.
 */
package org.newdawn.slick.util;

import java.io.PrintStream;
import java.util.Date;
import org.newdawn.slick.util.LogSystem;

public class DefaultLogSystem
implements LogSystem {
    public static PrintStream out = System.out;

    public void error(String message, Throwable e) {
        this.error(message);
        this.error(e);
    }

    public void error(Throwable e) {
        out.println(new Date() + " ERROR:" + e.getMessage());
        e.printStackTrace(out);
    }

    public void error(String message) {
        out.println(new Date() + " ERROR:" + message);
    }

    public void warn(String message) {
        out.println(new Date() + " WARN:" + message);
    }

    public void info(String message) {
        out.println(new Date() + " INFO:" + message);
    }

    public void debug(String message) {
        out.println(new Date() + " DEBUG:" + message);
    }

    public void warn(String message, Throwable e) {
        this.warn(message);
        e.printStackTrace(out);
    }
}

