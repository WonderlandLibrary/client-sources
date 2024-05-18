package net.minecraft.client.model;

import net.minecraft.client.renderer.*;

public class ModelBox
{
    public final float posX2;
    private TexturedQuad[] quadList;
    public final float posY1;
    private PositionTextureVertex[] vertexPositions;
    public final float posZ1;
    public String boxName;
    public final float posY2;
    public final float posZ2;
    public final float posX1;
    
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
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void render(final WorldRenderer worldRenderer, final float n) {
        int i = "".length();
        "".length();
        if (-1 >= 2) {
            throw null;
        }
        while (i < this.quadList.length) {
            this.quadList[i].draw(worldRenderer, n);
            ++i;
        }
    }
    
    public ModelBox(final ModelRenderer modelRenderer, final int n, final int n2, final float n3, final float n4, final float n5, final int n6, final int n7, final int n8, final float n9) {
        this(modelRenderer, n, n2, n3, n4, n5, n6, n7, n8, n9, modelRenderer.mirror);
    }
    
    public ModelBox(final ModelRenderer modelRenderer, final int n, final int n2, float posX1, float posY1, float posZ1, final int n3, final int n4, final int n5, final float n6, final boolean b) {
        this.posX1 = posX1;
        this.posY1 = posY1;
        this.posZ1 = posZ1;
        this.posX2 = posX1 + n3;
        this.posY2 = posY1 + n4;
        this.posZ2 = posZ1 + n5;
        this.vertexPositions = new PositionTextureVertex[0x5D ^ 0x55];
        this.quadList = new TexturedQuad[0x35 ^ 0x33];
        final float n7 = posX1 + n3;
        final float n8 = posY1 + n4;
        final float n9 = posZ1 + n5;
        posX1 -= n6;
        posY1 -= n6;
        posZ1 -= n6;
        float n10 = n7 + n6;
        final float n11 = n8 + n6;
        final float n12 = n9 + n6;
        if (b) {
            final float n13 = n10;
            n10 = posX1;
            posX1 = n13;
        }
        final PositionTextureVertex positionTextureVertex = new PositionTextureVertex(posX1, posY1, posZ1, 0.0f, 0.0f);
        final PositionTextureVertex positionTextureVertex2 = new PositionTextureVertex(n10, posY1, posZ1, 0.0f, 8.0f);
        final PositionTextureVertex positionTextureVertex3 = new PositionTextureVertex(n10, n11, posZ1, 8.0f, 8.0f);
        final PositionTextureVertex positionTextureVertex4 = new PositionTextureVertex(posX1, n11, posZ1, 8.0f, 0.0f);
        final PositionTextureVertex positionTextureVertex5 = new PositionTextureVertex(posX1, posY1, n12, 0.0f, 0.0f);
        final PositionTextureVertex positionTextureVertex6 = new PositionTextureVertex(n10, posY1, n12, 0.0f, 8.0f);
        final PositionTextureVertex positionTextureVertex7 = new PositionTextureVertex(n10, n11, n12, 8.0f, 8.0f);
        final PositionTextureVertex positionTextureVertex8 = new PositionTextureVertex(posX1, n11, n12, 8.0f, 0.0f);
        this.vertexPositions["".length()] = positionTextureVertex;
        this.vertexPositions[" ".length()] = positionTextureVertex2;
        this.vertexPositions["  ".length()] = positionTextureVertex3;
        this.vertexPositions["   ".length()] = positionTextureVertex4;
        this.vertexPositions[0xC4 ^ 0xC0] = positionTextureVertex5;
        this.vertexPositions[0x30 ^ 0x35] = positionTextureVertex6;
        this.vertexPositions[0x89 ^ 0x8F] = positionTextureVertex7;
        this.vertexPositions[0x3E ^ 0x39] = positionTextureVertex8;
        final TexturedQuad[] quadList = this.quadList;
        final int length = "".length();
        final PositionTextureVertex[] array = new PositionTextureVertex[0x2E ^ 0x2A];
        array["".length()] = positionTextureVertex6;
        array[" ".length()] = positionTextureVertex2;
        array["  ".length()] = positionTextureVertex3;
        array["   ".length()] = positionTextureVertex7;
        quadList[length] = new TexturedQuad(array, n + n5 + n3, n2 + n5, n + n5 + n3 + n5, n2 + n5 + n4, modelRenderer.textureWidth, modelRenderer.textureHeight);
        final TexturedQuad[] quadList2 = this.quadList;
        final int length2 = " ".length();
        final PositionTextureVertex[] array2 = new PositionTextureVertex[0x55 ^ 0x51];
        array2["".length()] = positionTextureVertex;
        array2[" ".length()] = positionTextureVertex5;
        array2["  ".length()] = positionTextureVertex8;
        array2["   ".length()] = positionTextureVertex4;
        quadList2[length2] = new TexturedQuad(array2, n, n2 + n5, n + n5, n2 + n5 + n4, modelRenderer.textureWidth, modelRenderer.textureHeight);
        final TexturedQuad[] quadList3 = this.quadList;
        final int length3 = "  ".length();
        final PositionTextureVertex[] array3 = new PositionTextureVertex[0x63 ^ 0x67];
        array3["".length()] = positionTextureVertex6;
        array3[" ".length()] = positionTextureVertex5;
        array3["  ".length()] = positionTextureVertex;
        array3["   ".length()] = positionTextureVertex2;
        quadList3[length3] = new TexturedQuad(array3, n + n5, n2, n + n5 + n3, n2 + n5, modelRenderer.textureWidth, modelRenderer.textureHeight);
        final TexturedQuad[] quadList4 = this.quadList;
        final int length4 = "   ".length();
        final PositionTextureVertex[] array4 = new PositionTextureVertex[0x8F ^ 0x8B];
        array4["".length()] = positionTextureVertex3;
        array4[" ".length()] = positionTextureVertex4;
        array4["  ".length()] = positionTextureVertex8;
        array4["   ".length()] = positionTextureVertex7;
        quadList4[length4] = new TexturedQuad(array4, n + n5 + n3, n2 + n5, n + n5 + n3 + n3, n2, modelRenderer.textureWidth, modelRenderer.textureHeight);
        final TexturedQuad[] quadList5 = this.quadList;
        final int n14 = 0x2A ^ 0x2E;
        final PositionTextureVertex[] array5 = new PositionTextureVertex[0x31 ^ 0x35];
        array5["".length()] = positionTextureVertex2;
        array5[" ".length()] = positionTextureVertex;
        array5["  ".length()] = positionTextureVertex4;
        array5["   ".length()] = positionTextureVertex3;
        quadList5[n14] = new TexturedQuad(array5, n + n5, n2 + n5, n + n5 + n3, n2 + n5 + n4, modelRenderer.textureWidth, modelRenderer.textureHeight);
        final TexturedQuad[] quadList6 = this.quadList;
        final int n15 = 0xBD ^ 0xB8;
        final PositionTextureVertex[] array6 = new PositionTextureVertex[0x4F ^ 0x4B];
        array6["".length()] = positionTextureVertex5;
        array6[" ".length()] = positionTextureVertex6;
        array6["  ".length()] = positionTextureVertex7;
        array6["   ".length()] = positionTextureVertex8;
        quadList6[n15] = new TexturedQuad(array6, n + n5 + n3 + n5, n2 + n5, n + n5 + n3 + n5 + n3, n2 + n5 + n4, modelRenderer.textureWidth, modelRenderer.textureHeight);
        if (b) {
            int i = "".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
            while (i < this.quadList.length) {
                this.quadList[i].flipFace();
                ++i;
            }
        }
    }
    
    public ModelBox setBoxName(final String boxName) {
        this.boxName = boxName;
        return this;
    }
}
