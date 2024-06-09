package com.client.glowclient.utils;

import com.google.gson.*;

public class BooleanValue extends Value
{
    private boolean b;
    
    public boolean M() {
        return this.b;
    }
    
    @Override
    public String A() {
        return this.A;
    }
    
    @Override
    public void M(final Object o) {
        this.b = (boolean)o;
    }
    
    @Override
    public String M() {
        return this.B;
    }
    
    @Override
    public void M(final String s, final JsonObject jsonObject) {
        this.b = ValueFactory.M(jsonObject, "value", this.b);
    }
    
    @Override
    public String D() {
        return this.b;
    }
    
    @Override
    public void M(final JsonObject jsonObject) {
        jsonObject.addProperty("value", this.b);
    }
    
    public BooleanValue(final String b, final String a, final String b2, final boolean b3) {
        super();
        this.b = b2;
        this.A = a;
        this.b = b3;
        this.B = b;
    }
}
