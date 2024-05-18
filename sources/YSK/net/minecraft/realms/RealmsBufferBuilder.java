package net.minecraft.realms;

import net.minecraft.client.renderer.*;
import java.nio.*;
import net.minecraft.client.renderer.vertex.*;

public class RealmsBufferBuilder
{
    private WorldRenderer b;
    
    public void postProcessFacePosition(final double n, final double n2, final double n3) {
        this.b.putPosition(n, n2, n3);
    }
    
    public RealmsBufferBuilder color(final int n, final int n2, final int n3, final int n4) {
        return this.from(this.b.color(n, n2, n3, n4));
    }
    
    public void offset(final double n, final double n2, final double n3) {
        this.b.setTranslation(n, n2, n3);
    }
    
    public void putBulkData(final int[] array) {
        this.b.addVertexData(array);
    }
    
    public RealmsBufferBuilder tex(final double n, final double n2) {
        return this.from(this.b.tex(n, n2));
    }
    
    public void endVertex() {
        this.b.endVertex();
    }
    
    public RealmsBufferBuilder color(final float n, final float n2, final float n3, final float n4) {
        return this.from(this.b.color(n, n2, n3, n4));
    }
    
    public RealmsBufferBuilder normal(final float n, final float n2, final float n3) {
        return this.from(this.b.normal(n, n2, n3));
    }
    
    public void fixupQuadColor(final int n) {
        this.b.putColor4(n);
    }
    
    public void restoreState(final WorldRenderer.State vertexState) {
        this.b.setVertexState(vertexState);
    }
    
    public RealmsVertexFormat getVertexFormat() {
        return new RealmsVertexFormat(this.b.getVertexFormat());
    }
    
    public void noColor() {
        this.b.markDirty();
    }
    
    public void faceTex2(final int n, final int n2, final int n3, final int n4) {
        this.b.putBrightness4(n, n2, n3, n4);
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
            if (0 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void fixupQuadColor(final float n, final float n2, final float n3) {
        this.b.putColorRGB_F4(n, n2, n3);
    }
    
    public RealmsBufferBuilder tex2(final int n, final int n2) {
        return this.from(this.b.lightmap(n, n2));
    }
    
    public ByteBuffer getBuffer() {
        return this.b.getByteBuffer();
    }
    
    public void clear() {
        this.b.reset();
    }
    
    public RealmsBufferBuilder from(final WorldRenderer b) {
        this.b = b;
        return this;
    }
    
    public void fixupVertexColor(final float n, final float n2, final float n3, final int n4) {
        this.b.putColorRGB_F(n, n2, n3, n4);
    }
    
    public void faceTint(final float n, final float n2, final float n3, final int n4) {
        this.b.putColorMultiplier(n, n2, n3, n4);
    }
    
    public void postNormal(final float n, final float n2, final float n3) {
        this.b.putNormal(n, n2, n3);
    }
    
    public void begin(final int n, final VertexFormat vertexFormat) {
        this.b.begin(n, vertexFormat);
    }
    
    public int getDrawMode() {
        return this.b.getDrawMode();
    }
    
    public void sortQuads(final float n, final float n2, final float n3) {
        this.b.func_181674_a(n, n2, n3);
    }
    
    public int getVertexCount() {
        return this.b.getVertexCount();
    }
    
    public RealmsBufferBuilder(final WorldRenderer b) {
        this.b = b;
    }
    
    public void end() {
        this.b.finishDrawing();
    }
    
    public RealmsBufferBuilder vertex(final double n, final double n2, final double n3) {
        return this.from(this.b.pos(n, n2, n3));
    }
}
