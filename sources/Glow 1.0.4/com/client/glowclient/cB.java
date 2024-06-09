package com.client.glowclient;

import com.google.gson.*;
import com.client.glowclient.utils.*;

public class CB extends Value
{
    private int L;
    private int A;
    private int B;
    private int b;
    
    @Override
    public String A() {
        return this.A;
    }
    
    @Override
    public String D() {
        return this.b;
    }
    
    @Override
    public void M(final String s, final JsonObject jsonObject) {
        this.B = ValueFactory.M(jsonObject, "value", this.B);
    }
    
    public CB(final String b, final String a, final String b2, final int b3, final int l, final int a2, final int b4) {
        super();
        this.b = b2;
        this.A = a;
        this.B = b3;
        this.L = l;
        this.A = a2;
        this.b = b4;
        this.B = b;
    }
    
    public double A() {
        return this.b;
    }
    
    public int M() {
        return this.B;
    }
    
    @Override
    public void M(final JsonObject jsonObject) {
        jsonObject.addProperty("value", this.B);
    }
    
    public double D() {
        return this.A;
    }
    
    @Override
    public String M() {
        return this.B;
    }
    
    public double M() {
        return this.L;
    }
    
    @Override
    public void M(final Object o) {
        this.B = (int)o;
    }
}
