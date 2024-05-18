package net.minecraft.client.model;

import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class TexturedQuad
{
    private boolean invertNormal;
    public PositionTextureVertex[] vertexPositions;
    public int nVertices;
    
    public TexturedQuad(final PositionTextureVertex[] array, final int n, final int n2, final int n3, final int n4, final float n5, final float n6) {
        this(array);
        final float n7 = 0.0f / n5;
        final float n8 = 0.0f / n6;
        array["".length()] = array["".length()].setTexturePosition(n3 / n5 - n7, n2 / n6 + n8);
        array[" ".length()] = array[" ".length()].setTexturePosition(n / n5 + n7, n2 / n6 + n8);
        array["  ".length()] = array["  ".length()].setTexturePosition(n / n5 + n7, n4 / n6 - n8);
        array["   ".length()] = array["   ".length()].setTexturePosition(n3 / n5 - n7, n4 / n6 - n8);
    }
    
    public void draw(final WorldRenderer worldRenderer, final float n) {
        final Vec3 normalize = this.vertexPositions[" ".length()].vector3D.subtractReverse(this.vertexPositions["  ".length()].vector3D).crossProduct(this.vertexPositions[" ".length()].vector3D.subtractReverse(this.vertexPositions["".length()].vector3D)).normalize();
        float n2 = (float)normalize.xCoord;
        float n3 = (float)normalize.yCoord;
        float n4 = (float)normalize.zCoord;
        if (this.invertNormal) {
            n2 = -n2;
            n3 = -n3;
            n4 = -n4;
        }
        worldRenderer.begin(0x59 ^ 0x5E, DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL);
        int i = "".length();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (i < (0xBF ^ 0xBB)) {
            final PositionTextureVertex positionTextureVertex = this.vertexPositions[i];
            worldRenderer.pos(positionTextureVertex.vector3D.xCoord * n, positionTextureVertex.vector3D.yCoord * n, positionTextureVertex.vector3D.zCoord * n).tex(positionTextureVertex.texturePositionX, positionTextureVertex.texturePositionY).normal(n2, n3, n4).endVertex();
            ++i;
        }
        Tessellator.getInstance().draw();
    }
    
    public void flipFace() {
        final PositionTextureVertex[] vertexPositions = new PositionTextureVertex[this.vertexPositions.length];
        int i = "".length();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (i < this.vertexPositions.length) {
            vertexPositions[i] = this.vertexPositions[this.vertexPositions.length - i - " ".length()];
            ++i;
        }
        this.vertexPositions = vertexPositions;
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
            if (4 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public TexturedQuad(final PositionTextureVertex[] vertexPositions) {
        this.vertexPositions = vertexPositions;
        this.nVertices = vertexPositions.length;
    }
}
