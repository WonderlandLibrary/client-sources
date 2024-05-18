package mods.betterhat.main;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.Tessellator;

public class ModelBoxUV extends ModelBox
{
    public final float textureWidth;
    public final float textureHeight;

    /** X vertex coordinate of lower box corner */
    public final float posX1;

    /** Y vertex coordinate of lower box corner */
    public final float posY1;

    /** Z vertex coordinate of lower box corner */
    public final float posZ1;

    /** X vertex coordinate of upper box corner */
    public final float posX2;

    /** Y vertex coordinate of upper box corner */
    public final float posY2;

    /** Z vertex coordinate of upper box corner */
    public final float posZ2;
    public String boxName;
    private boolean mirror;
    private PositionTextureVertex[] vertexPositions;
    private ModelBoxUV.QuadData[] quadDataList;
    private TexturedQuad[] quadList;
    private static final int[][] vectorIndex = new int[][] {{5, 1, 2, 6}, {0, 4, 7, 3}, {5, 4, 0, 1}, {2, 3, 7, 6}, {1, 0, 3, 2}, {4, 5, 6, 7}};

    public static ModelBoxUV addBox(ModelRendererUV renderer, float x, float y, float z, int w, int h, int d, float add)
    {
        int i = ((Integer)ReflectionHelper.getPrivateValue(ModelRenderer.class, renderer, BetterHat.fTextureOffsetX)).intValue();
        int j = ((Integer)ReflectionHelper.getPrivateValue(ModelRenderer.class, renderer, BetterHat.fTextureOffsetY)).intValue();
        ModelBoxUV modelboxuv = new ModelBoxUV(renderer, i, j, x, y, z, w, h, d, add);
        renderer.cubeList.add(modelboxuv);
        return modelboxuv;
    }

    public ModelBoxUV(ModelRendererUV modelRenderer, int textureX, int textureY, float x1, float y1, float z1, int w, int h, int d, float add)
    {
        super(modelRenderer, textureX, textureY, x1, y1, z1, w, h, d, add);
        this.textureWidth = modelRenderer.textureWidth;
        this.textureHeight = modelRenderer.textureHeight;
        this.posX1 = x1;
        this.posY1 = y1;
        this.posZ1 = z1;
        this.posX2 = x1 + (float)w;
        this.posY2 = y1 + (float)h;
        this.posZ2 = z1 + (float)d;
        this.mirror = modelRenderer.mirror;
        this.vertexPositions = new PositionTextureVertex[8];
        this.quadDataList = new ModelBoxUV.QuadData[6];
        this.quadList = null;
        float f = x1 + (float)w;
        float f1 = y1 + (float)h;
        float f2 = z1 + (float)d;
        x1 = x1 - add;
        y1 = y1 - add;
        z1 = z1 - add;
        f = f + add;
        f1 = f1 + add;
        f2 = f2 + add;

        if (this.mirror)
        {
            float f3 = f;
            f = x1;
            x1 = f3;
        }

        PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(x1, y1, z1, 0.0F, 0.0F);
        PositionTextureVertex positiontexturevertex = new PositionTextureVertex(f, y1, z1, 0.0F, 8.0F);
        PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(f, f1, z1, 8.0F, 8.0F);
        PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(x1, f1, z1, 8.0F, 0.0F);
        PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(x1, y1, f2, 0.0F, 0.0F);
        PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(f, y1, f2, 0.0F, 8.0F);
        PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(f, f1, f2, 8.0F, 8.0F);
        PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(x1, f1, f2, 8.0F, 0.0F);
        this.vertexPositions[0] = positiontexturevertex7;
        this.vertexPositions[1] = positiontexturevertex;
        this.vertexPositions[2] = positiontexturevertex1;
        this.vertexPositions[3] = positiontexturevertex2;
        this.vertexPositions[4] = positiontexturevertex3;
        this.vertexPositions[5] = positiontexturevertex4;
        this.vertexPositions[6] = positiontexturevertex5;
        this.vertexPositions[7] = positiontexturevertex6;

        for (int i = 0; i < this.quadDataList.length; ++i)
        {
            this.quadDataList[i] = new ModelBoxUV.QuadData(textureX, textureY, w, h, d, i);
        }
    }

    public ModelBoxUV setSideUV(int side, int textureX, int textureY)
    {
        ModelBoxUV.QuadData modelboxuv$quaddata = this.quadDataList[side];
        int i = Math.abs(modelboxuv$quaddata.uvPos[2] - modelboxuv$quaddata.uvPos[0]);
        int j = Math.abs(modelboxuv$quaddata.uvPos[3] - modelboxuv$quaddata.uvPos[1]);
        modelboxuv$quaddata.uvPos[0] = textureX;
        modelboxuv$quaddata.uvPos[1] = textureY;
        modelboxuv$quaddata.uvPos[2] = textureX + i;
        modelboxuv$quaddata.uvPos[3] = textureY + j;

        if (side == 3)
        {
            modelboxuv$quaddata.uvPos[1] = textureY + j;
            modelboxuv$quaddata.uvPos[3] = textureY;
        }

        return this;
    }

    public ModelBoxUV setAllUV(int textureX, int textureY)
    {
        for (int i = 0; i < this.quadDataList.length; ++i)
        {
            this.setSideUV(i, textureX, textureY);
        }

        return this;
    }

    public ModelBoxUV initQuads()
    {
        this.quadList = new TexturedQuad[6];

        for (int i = 0; i < this.quadList.length; ++i)
        {
            ModelBoxUV.QuadData modelboxuv$quaddata = this.quadDataList[i];
            this.quadList[i] = new TexturedQuad(this.getVertexes(i), modelboxuv$quaddata.uvPos[0], modelboxuv$quaddata.uvPos[1], modelboxuv$quaddata.uvPos[2], modelboxuv$quaddata.uvPos[3], this.textureWidth, this.textureHeight);
        }

        if (this.mirror)
        {
            for (int j = 0; j < this.quadList.length; ++j)
            {
                this.quadList[j].flipFace();
            }
        }

        this.quadDataList = null;
        return this;
    }

    private PositionTextureVertex[] getVertexes(int side)
    {
        int i = vectorIndex[side][0];
        int j = vectorIndex[side][1];
        int k = vectorIndex[side][2];
        int l = vectorIndex[side][3];
        return new PositionTextureVertex[] {this.vertexPositions[i], this.vertexPositions[j], this.vertexPositions[k], this.vertexPositions[l]};
    }

    public void render(Tessellator par1Tessellator, float par2)
    {
        if (this.quadList == null)
        {
            this.initQuads();
        }

        for (int i = 0; i < this.quadList.length; ++i)
        {
            this.quadList[i].draw(par1Tessellator.getWorldRenderer(), par2);
        }
    }

    public ModelBox setBoxName(String name)
    {
        this.boxName = name;
        return this;
    }

    private static class QuadData
    {
        public int[] uvPos = new int[4];

        public QuadData(int textureX, int textureY, int w, int h, int d, int side)
        {
            if (side == 0)
            {
                this.uvPos[0] = textureX + d + w;
                this.uvPos[1] = textureY + d;
                this.uvPos[2] = textureX + d + w + d;
                this.uvPos[3] = textureY + d + h;
            }
            else if (side == 1)
            {
                this.uvPos[0] = textureX;
                this.uvPos[1] = textureY + d;
                this.uvPos[2] = textureX + d;
                this.uvPos[3] = textureY + d + h;
            }
            else if (side == 2)
            {
                this.uvPos[0] = textureX + d;
                this.uvPos[1] = textureY;
                this.uvPos[2] = textureX + d + w;
                this.uvPos[3] = textureY + d;
            }
            else if (side == 3)
            {
                this.uvPos[0] = textureX + d + w;
                this.uvPos[1] = textureY + d;
                this.uvPos[2] = textureX + d + w + w;
                this.uvPos[3] = textureY;
            }
            else if (side == 4)
            {
                this.uvPos[0] = textureX + d;
                this.uvPos[1] = textureY + d;
                this.uvPos[2] = textureX + d + w;
                this.uvPos[3] = textureY + d + h;
            }
            else if (side == 5)
            {
                this.uvPos[0] = textureX + d + w + d;
                this.uvPos[1] = textureY + d;
                this.uvPos[2] = textureX + d + w + d + w;
                this.uvPos[3] = textureY + d + h;
            }
        }
    }
}
