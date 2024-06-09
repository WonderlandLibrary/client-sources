package com.client.glowclient.utils;

import com.google.gson.*;

public class StringValue extends Value
{
    private String L;
    
    public String e() {
        return this.L;
    }
    
    @Override
    public void M(final Object o) {
        this.L = (String)o;
    }
    
    @Override
    public String A() {
        return this.A;
    }
    
    @Override
    public void M(final JsonObject jsonObject) {
        jsonObject.addProperty("value", this.L);
    }
    
    @Override
    public String M() {
        return this.B;
    }
    
    @Override
    public String D() {
        return this.b;
    }
    
    public StringValue(final String b, final String a, final String b2, final String l) {
        super();
        this.b = b2;
        this.A = a;
        this.L = l;
        this.B = b;
    }
    
    @Override
    public void M(final String s, final JsonObject jsonObject) {
        this.L = ValueFactory.M(jsonObject, "value", this.L);
    }
}
