/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.realms;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.realms.RealmsVertexFormatElement;

public class RealmsVertexFormat {
    private bmu v;

    public RealmsVertexFormat(bmu bmu2) {
        this.v = bmu2;
    }

    public RealmsVertexFormat from(bmu bmu2) {
        this.v = bmu2;
        return this;
    }

    public bmu getVertexFormat() {
        return this.v;
    }

    public void clear() {
        this.v.a();
    }

    public int getUvOffset(int n2) {
        return this.v.b(n2);
    }

    public int getElementCount() {
        return this.v.i();
    }

    public boolean hasColor() {
        return this.v.d();
    }

    public boolean hasUv(int n2) {
        return this.v.a(n2);
    }

    public RealmsVertexFormatElement getElement(int n2) {
        return new RealmsVertexFormatElement(this.v.c(n2));
    }

    public RealmsVertexFormat addElement(RealmsVertexFormatElement realmsVertexFormatElement) {
        return this.from(this.v.a(realmsVertexFormatElement.getVertexFormatElement()));
    }

    public int getColorOffset() {
        return this.v.e();
    }

    public List<RealmsVertexFormatElement> getElements() {
        ArrayList<RealmsVertexFormatElement> arrayList = new ArrayList<RealmsVertexFormatElement>();
        for (bmv bmv2 : this.v.h()) {
            arrayList.add(new RealmsVertexFormatElement(bmv2));
        }
        return arrayList;
    }

    public boolean hasNormal() {
        return this.v.b();
    }

    public int getVertexSize() {
        return this.v.g();
    }

    public int getOffset(int n2) {
        return this.v.d(n2);
    }

    public int getNormalOffset() {
        return this.v.c();
    }

    public int getIntegerSize() {
        return this.v.f();
    }

    public boolean equals(Object object) {
        return this.v.equals(object);
    }

    public int hashCode() {
        return this.v.hashCode();
    }

    public String toString() {
        return this.v.toString();
    }
}

