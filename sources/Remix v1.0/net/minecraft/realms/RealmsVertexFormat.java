package net.minecraft.realms;

import java.util.*;

public class RealmsVertexFormat
{
    private bmu v;
    
    public RealmsVertexFormat(final bmu \u2603) {
        this.v = \u2603;
    }
    
    public RealmsVertexFormat from(final bmu \u2603) {
        this.v = \u2603;
        return this;
    }
    
    public bmu getVertexFormat() {
        return this.v;
    }
    
    public void clear() {
        this.v.a();
    }
    
    public int getUvOffset(final int \u2603) {
        return this.v.b(\u2603);
    }
    
    public int getElementCount() {
        return this.v.i();
    }
    
    public boolean hasColor() {
        return this.v.d();
    }
    
    public boolean hasUv(final int \u2603) {
        return this.v.a(\u2603);
    }
    
    public RealmsVertexFormatElement getElement(final int \u2603) {
        return new RealmsVertexFormatElement(this.v.c(\u2603));
    }
    
    public RealmsVertexFormat addElement(final RealmsVertexFormatElement \u2603) {
        return this.from(this.v.a(\u2603.getVertexFormatElement()));
    }
    
    public int getColorOffset() {
        return this.v.e();
    }
    
    public List<RealmsVertexFormatElement> getElements() {
        final List<RealmsVertexFormatElement> list = new ArrayList<RealmsVertexFormatElement>();
        for (final bmv \u2603 : this.v.h()) {
            list.add(new RealmsVertexFormatElement(\u2603));
        }
        return list;
    }
    
    public boolean hasNormal() {
        return this.v.b();
    }
    
    public int getVertexSize() {
        return this.v.g();
    }
    
    public int getOffset(final int \u2603) {
        return this.v.d(\u2603);
    }
    
    public int getNormalOffset() {
        return this.v.c();
    }
    
    public int getIntegerSize() {
        return this.v.f();
    }
    
    @Override
    public boolean equals(final Object \u2603) {
        return this.v.equals(\u2603);
    }
    
    @Override
    public int hashCode() {
        return this.v.hashCode();
    }
    
    @Override
    public String toString() {
        return this.v.toString();
    }
}
