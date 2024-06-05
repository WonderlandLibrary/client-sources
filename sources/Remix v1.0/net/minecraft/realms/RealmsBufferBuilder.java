package net.minecraft.realms;

import java.nio.*;

public class RealmsBufferBuilder
{
    private bfd b;
    
    public RealmsBufferBuilder(final bfd \u2603) {
        this.b = \u2603;
    }
    
    public RealmsBufferBuilder from(final bfd \u2603) {
        this.b = \u2603;
        return this;
    }
    
    public void sortQuads(final float \u2603, final float \u2603, final float \u2603) {
        this.b.a(\u2603, \u2603, \u2603);
    }
    
    public void fixupQuadColor(final int \u2603) {
        this.b.a(\u2603);
    }
    
    public ByteBuffer getBuffer() {
        return this.b.f();
    }
    
    public void postNormal(final float \u2603, final float \u2603, final float \u2603) {
        this.b.b(\u2603, \u2603, \u2603);
    }
    
    public int getDrawMode() {
        return this.b.i();
    }
    
    public void offset(final double \u2603, final double \u2603, final double \u2603) {
        this.b.c(\u2603, \u2603, \u2603);
    }
    
    public void restoreState(final bfd.a \u2603) {
        this.b.a(\u2603);
    }
    
    public void endVertex() {
        this.b.d();
    }
    
    public RealmsBufferBuilder normal(final float \u2603, final float \u2603, final float \u2603) {
        return this.from(this.b.c(\u2603, \u2603, \u2603));
    }
    
    public void end() {
        this.b.e();
    }
    
    public void begin(final int \u2603, final bmu \u2603) {
        this.b.a(\u2603, \u2603);
    }
    
    public RealmsBufferBuilder color(final int \u2603, final int \u2603, final int \u2603, final int \u2603) {
        return this.from(this.b.b(\u2603, \u2603, \u2603, \u2603));
    }
    
    public void faceTex2(final int \u2603, final int \u2603, final int \u2603, final int \u2603) {
        this.b.a(\u2603, \u2603, \u2603, \u2603);
    }
    
    public void postProcessFacePosition(final double \u2603, final double \u2603, final double \u2603) {
        this.b.a(\u2603, \u2603, \u2603);
    }
    
    public void fixupVertexColor(final float \u2603, final float \u2603, final float \u2603, final int \u2603) {
        this.b.b(\u2603, \u2603, \u2603, \u2603);
    }
    
    public RealmsBufferBuilder color(final float \u2603, final float \u2603, final float \u2603, final float \u2603) {
        return this.from(this.b.a(\u2603, \u2603, \u2603, \u2603));
    }
    
    public RealmsVertexFormat getVertexFormat() {
        return new RealmsVertexFormat(this.b.g());
    }
    
    public void faceTint(final float \u2603, final float \u2603, final float \u2603, final int \u2603) {
        this.b.a(\u2603, \u2603, \u2603, \u2603);
    }
    
    public RealmsBufferBuilder tex2(final int \u2603, final int \u2603) {
        return this.from(this.b.a(\u2603, \u2603));
    }
    
    public void putBulkData(final int[] \u2603) {
        this.b.a(\u2603);
    }
    
    public RealmsBufferBuilder tex(final double \u2603, final double \u2603) {
        return this.from(this.b.a(\u2603, \u2603));
    }
    
    public int getVertexCount() {
        return this.b.h();
    }
    
    public void clear() {
        this.b.b();
    }
    
    public RealmsBufferBuilder vertex(final double \u2603, final double \u2603, final double \u2603) {
        return this.from(this.b.b(\u2603, \u2603, \u2603));
    }
    
    public void fixupQuadColor(final float \u2603, final float \u2603, final float \u2603) {
        this.b.d(\u2603, \u2603, \u2603);
    }
    
    public void noColor() {
        this.b.c();
    }
}
