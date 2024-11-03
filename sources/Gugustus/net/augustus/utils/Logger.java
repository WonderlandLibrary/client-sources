package net.augustus.utils;

import net.minecraft.util.ResourceLocation;

public class Logger {
    public void info(String s) {
        System.out.println(s);
    }
    public void info(String s, Throwable var13) {
        System.out.println(s);
        var13.printStackTrace();
    }

    public void fatal(String s, Throwable var13) {
        System.err.println(s);
        var13.printStackTrace();
    }

    public void error(String s, Throwable var4) {
        System.err.println(s);
        var4.printStackTrace();
    }

    public void error(String s) {
        System.err.println(s);
    }

    public void warn(String s) {
        System.out.println(s);
    }

    public void warn(String s, Throwable ioexception) {
        System.out.println(s);
        ioexception.printStackTrace();
    }

    public void warn(String s, Object... a) {
        System.out.println(String.format(s, a));
    }
    public void error(String s, Object... a) {
        System.err.println(String.format(s, a));
    }
    public void info(String s, Object... a) {
        System.out.println(String.format(s, a));
    }

    public void fatal(Throwable var3) {
        var3.printStackTrace();
    }

    public void fatal(String s) {
        System.err.println(s);
    }

    public void debug(String s, Object... a) {
    }

    public void error(Throwable throwable) {
        throwable.printStackTrace();
    }

    public boolean isDebugEnabled() {
        return false;
    }
}
