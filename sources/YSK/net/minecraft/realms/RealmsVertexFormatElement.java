package net.minecraft.realms;

import net.minecraft.client.renderer.vertex.*;

public class RealmsVertexFormatElement
{
    private VertexFormatElement v;
    
    public int getCount() {
        return this.v.getElementCount();
    }
    
    @Override
    public String toString() {
        return this.v.toString();
    }
    
    public int getIndex() {
        return this.v.getIndex();
    }
    
    public boolean isPosition() {
        return this.v.isPositionElement();
    }
    
    public int getByteSize() {
        return this.v.getSize();
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
            if (!true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this.v.equals(o);
    }
    
    public RealmsVertexFormatElement(final VertexFormatElement v) {
        this.v = v;
    }
    
    @Override
    public int hashCode() {
        return this.v.hashCode();
    }
    
    public VertexFormatElement getVertexFormatElement() {
        return this.v;
    }
}
