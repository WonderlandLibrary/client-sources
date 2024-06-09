package com.client.glowclient.utils;

import com.google.gson.*;

public class NumberValue extends Value
{
    private double L;
    private double A;
    private double B;
    private double b;
    
    public NumberValue(final String b, final String a, final String b2, final double a2, final double b3, final double b4, final double l) {
        super();
        this.b = b2;
        this.A = a;
        this.A = a2;
        this.b = b3;
        this.B = b4;
        this.L = l;
        this.B = b;
    }
    
    public double k() {
        return this.A;
    }
    
    public double A() {
        return this.L;
    }
    
    @Override
    public String D() {
        return this.b;
    }
    
    @Override
    public String A() {
        return this.A;
    }
    
    public double D() {
        return this.b;
    }
    
    @Override
    public String M() {
        return this.B;
    }
    
    @Override
    public void M(final String s, final JsonObject jsonObject) {
        this.A = ValueFactory.M(jsonObject, "value", (float)this.A);
    }
    
    public int M() {
        return (int)this.A;
    }
    
    @Override
    public void M(final Object o) {
        this.A = (double)o;
    }
    
    @Override
    public void M(final JsonObject jsonObject) {
        jsonObject.addProperty("value", this.A);
    }
    
    public double M() {
        return this.B;
    }
}
