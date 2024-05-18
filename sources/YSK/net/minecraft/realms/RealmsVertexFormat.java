package net.minecraft.realms;

import net.minecraft.client.renderer.vertex.*;
import java.util.*;

public class RealmsVertexFormat
{
    private VertexFormat v;
    
    public int getElementCount() {
        return this.v.getElementCount();
    }
    
    public void clear() {
        this.v.clear();
    }
    
    public int getColorOffset() {
        return this.v.getColorOffset();
    }
    
    public int getUvOffset(final int n) {
        return this.v.getUvOffsetById(n);
    }
    
    public int getOffset(final int n) {
        return this.v.func_181720_d(n);
    }
    
    public RealmsVertexFormat addElement(final RealmsVertexFormatElement realmsVertexFormatElement) {
        return this.from(this.v.func_181721_a(realmsVertexFormatElement.getVertexFormatElement()));
    }
    
    public List<RealmsVertexFormatElement> getElements() {
        final ArrayList<RealmsVertexFormatElement> list = new ArrayList<RealmsVertexFormatElement>();
        final Iterator<VertexFormatElement> iterator = this.v.getElements().iterator();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            list.add(new RealmsVertexFormatElement(iterator.next()));
        }
        return list;
    }
    
    public RealmsVertexFormatElement getElement(final int n) {
        return new RealmsVertexFormatElement(this.v.getElement(n));
    }
    
    public int getNormalOffset() {
        return this.v.getNormalOffset();
    }
    
    @Override
    public String toString() {
        return this.v.toString();
    }
    
    @Override
    public int hashCode() {
        return this.v.hashCode();
    }
    
    public boolean hasUv(final int n) {
        return this.v.hasUvOffset(n);
    }
    
    public VertexFormat getVertexFormat() {
        return this.v;
    }
    
    public int getIntegerSize() {
        return this.v.func_181719_f();
    }
    
    public int getVertexSize() {
        return this.v.getNextOffset();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this.v.equals(o);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean hasNormal() {
        return this.v.hasNormal();
    }
    
    public boolean hasColor() {
        return this.v.hasColor();
    }
    
    public RealmsVertexFormat from(final VertexFormat v) {
        this.v = v;
        return this;
    }
    
    public RealmsVertexFormat(final VertexFormat v) {
        this.v = v;
    }
}
