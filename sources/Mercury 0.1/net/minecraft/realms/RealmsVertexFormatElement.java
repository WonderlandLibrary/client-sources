/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.realms;

public class RealmsVertexFormatElement {
    private bmv v;

    public RealmsVertexFormatElement(bmv bmv2) {
        this.v = bmv2;
    }

    public bmv getVertexFormatElement() {
        return this.v;
    }

    public boolean isPosition() {
        return this.v.f();
    }

    public int getIndex() {
        return this.v.d();
    }

    public int getByteSize() {
        return this.v.e();
    }

    public int getCount() {
        return this.v.c();
    }

    public int hashCode() {
        return this.v.hashCode();
    }

    public boolean equals(Object object) {
        return this.v.equals(object);
    }

    public String toString() {
        return this.v.toString();
    }
}

