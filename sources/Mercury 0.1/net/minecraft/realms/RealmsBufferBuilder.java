/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.realms;

import java.nio.ByteBuffer;
import net.minecraft.realms.RealmsVertexFormat;

public class RealmsBufferBuilder {
    private bfd b;

    public RealmsBufferBuilder(bfd bfd2) {
        this.b = bfd2;
    }

    public RealmsBufferBuilder from(bfd bfd2) {
        this.b = bfd2;
        return this;
    }

    public void sortQuads(float f2, float f3, float f4) {
        this.b.a(f2, f3, f4);
    }

    public void fixupQuadColor(int n2) {
        this.b.a(n2);
    }

    public ByteBuffer getBuffer() {
        return this.b.f();
    }

    public void postNormal(float f2, float f3, float f4) {
        this.b.b(f2, f3, f4);
    }

    public int getDrawMode() {
        return this.b.i();
    }

    public void offset(double d2, double d3, double d4) {
        this.b.c(d2, d3, d4);
    }

    public void restoreState(bfd.a a2) {
        this.b.a(a2);
    }

    public void endVertex() {
        this.b.d();
    }

    public RealmsBufferBuilder normal(float f2, float f3, float f4) {
        return this.from(this.b.c(f2, f3, f4));
    }

    public void end() {
        this.b.e();
    }

    public void begin(int n2, bmu bmu2) {
        this.b.a(n2, bmu2);
    }

    public RealmsBufferBuilder color(int n2, int n3, int n4, int n5) {
        return this.from(this.b.b(n2, n3, n4, n5));
    }

    public void faceTex2(int n2, int n3, int n4, int n5) {
        this.b.a(n2, n3, n4, n5);
    }

    public void postProcessFacePosition(double d2, double d3, double d4) {
        this.b.a(d2, d3, d4);
    }

    public void fixupVertexColor(float f2, float f3, float f4, int n2) {
        this.b.b(f2, f3, f4, n2);
    }

    public RealmsBufferBuilder color(float f2, float f3, float f4, float f5) {
        return this.from(this.b.a(f2, f3, f4, f5));
    }

    public RealmsVertexFormat getVertexFormat() {
        return new RealmsVertexFormat(this.b.g());
    }

    public void faceTint(float f2, float f3, float f4, int n2) {
        this.b.a(f2, f3, f4, n2);
    }

    public RealmsBufferBuilder tex2(int n2, int n3) {
        return this.from(this.b.a(n2, n3));
    }

    public void putBulkData(int[] arrn) {
        this.b.a(arrn);
    }

    public RealmsBufferBuilder tex(double d2, double d3) {
        return this.from(this.b.a(d2, d3));
    }

    public int getVertexCount() {
        return this.b.h();
    }

    public void clear() {
        this.b.b();
    }

    public RealmsBufferBuilder vertex(double d2, double d3, double d4) {
        return this.from(this.b.b(d2, d3, d4));
    }

    public void fixupQuadColor(float f2, float f3, float f4) {
        this.b.d(f2, f3, f4);
    }

    public void noColor() {
        this.b.c();
    }
}

