package com.client.glowclient.utils;

import com.google.gson.*;

public abstract class Value
{
    private static Value L;
    public String A;
    public String B;
    public String b;
    
    public static Value M() {
        return Value.L;
    }
    
    public String k() {
        return new StringBuilder().insert(0, this.B).append(".").append(this.A).toString();
    }
    
    public abstract void M(final String p0, final JsonObject p1);
    
    public abstract void M(final JsonObject p0);
    
    public Value() {
        super();
        Value.L = this;
    }
    
    public String A() {
        return this.A;
    }
    
    public abstract void M(final Object p0);
    
    public String D() {
        return this.b;
    }
    
    public String M() {
        return this.B;
    }
}
