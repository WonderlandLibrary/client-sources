package com.client.glowclient;

import net.minecraft.client.resources.*;

public enum Fd
{
    G("Copy", "com.client.glowclient.utils.mod.imports.wdl.saveProgress.backingUp.title.folder");
    
    public final String d;
    private static final Fd[] L;
    
    A("Zip copy", "com.client.glowclient.utils.mod.imports.wdl.saveProgress.backingUp.title.zip");
    
    public final String B;
    
    b("None", "");
    
    public static Fd[] values() {
        return Fd.L.clone();
    }
    
    private Fd(final String d, final String b) {
        this.d = d;
        this.B = b;
    }
    
    public static Fd valueOf(final String s) {
        return Enum.valueOf(Fd.class, s);
    }
    
    public static Fd M(final String s) {
        final Fd[] values;
        final int length = (values = values()).length;
        int n;
        int i = n = 0;
        while (i < length) {
            final Fd fd;
            if ((fd = values[n]).name().equalsIgnoreCase(s)) {
                return fd;
            }
            i = ++n;
        }
        return Fd.b;
    }
    
    public String D() {
        return I18n.format(this.d, new Object[0]);
    }
    
    static {
        L = new Fd[] { Fd.b, Fd.G, Fd.A };
    }
    
    public String M() {
        return I18n.format(this.B, new Object[0]);
    }
}
