package com.client.glowclient;

import org.apache.logging.log4j.*;

public enum Jc
{
    G("VERSION", 1, "com.client.glowclient.utils.mod.imports.wdl.sanity.version");
    
    private static final Jc[] d;
    public final String L;
    
    A("TRIPWIRE", 0, "com.client.glowclient.utils.mod.imports.wdl.sanity.tripwire");
    
    private static final Logger B;
    
    b("TRANSLATION", 2, "com.client.glowclient.utils.mod.imports.wdl.sanity.translation");
    
    public static Logger M() {
        return Jc.B;
    }
    
    public abstract void M() throws Exception;
    
    public Jc(final String s, final int n, final String s2, final md md) {
        this(s2);
    }
    
    public static Jc[] values() {
        return Jc.d.clone();
    }
    
    static {
        d = new Jc[] { Jc.A, Jc.G, Jc.b };
        B = LogManager.getLogger();
    }
    
    private Jc(final String l) {
        this.L = l;
    }
    
    public static Jc valueOf(final String s) {
        return Enum.valueOf(Jc.class, s);
    }
}
