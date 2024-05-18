package net.minecraft.client.renderer.block.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.IVertexProducer;

public class BakedQuad implements IVertexProducer
{
    /**
     * Joined 4 vertex records, each has 7 fields (x, y, z, shadeColor, u, v, <unused>), see
     * FaceBakery.storeVertexData()
     */
    protected final int[] vertexData;
    protected final int tintIndex;
    protected final EnumFacing face;
    private static final String __OBFID = "CL_00002512";
    private TextureAtlasSprite sprite = null;
    private int[] vertexDataSingle = null;

    public BakedQuad(int[] p_i17_1_, int p_i17_2_, EnumFacing p_i17_3_, TextureAtlasSprite p_i17_4_)
    {
        p_i17_1_ = fixVertexData(p_i17_1_);
        this.vertexData = p_i17_1_;
        this.tintIndex = p_i17_2_;
        this.face = p_i17_3_;
        this.sprite = p_i17_4_;
    }

    public TextureAtlasSprite getSprite()
    {
        if (this.sprite == null)
        {
            this.sprite = getSpriteByUv(this.getVertexData());
        }

        return this.sprite;
    }

    public String toString()
    {
        return "vertex: " + this.vertexData.length / 7 + ", tint: " + this.tintIndex + ", facing: " + this.face + ", sprite: " + this.sprite;
    }

    public BakedQuad(int[] vertexDataIn, int tintIndexIn, EnumFacing faceIn)
    {
        vertexDataIn = fixVertexData(vertexDataIn);
        this.vertexData = vertexDataIn;
        this.tintIndex = tintIndexIn;
        this.face = faceIn;
    }

    public int[] getVertexData()
    {
        return this.vertexData;
    }

    public boolean hasTintIndex()
    {
        return this.tintIndex != -1;
    }

    public int getTintIndex()
    {
        return this.tintIndex;
    }

    public EnumFacing getFace()
    {
        return this.face;
    }

    public int[] getVertexDataSingle()
    {
        if (this.vertexDataSingle == null)
        {
            this.vertexDataSingle = makeVertexDataSingle(this.getVertexData(), this.getSprite());
        }

        return this.vertexDataSingle;
    }

    private static int[] makeVertexDataSingle(int[] p_makeVertexDataSingle_0_, TextureAtlasSprite p_makeVertexDataSingle_1_)
    {
        int[] aint = (int[])p_makeVertexDataSingle_0_.clone();
        int i = p_makeVertexDataSingle_1_.sheetWidth / p_makeVertexDataSingle_1_.getIconWidth();
        int j = p_makeVertexDataSingle_1_.sheetHeight / p_makeVertexDataSingle_1_.getIconHeight();
        int k = aint.length / 4;

        for (int l = 0; l < 4; ++l)
        {
            int i1 = l * k;
            float f = Float.intBitsToFloat(aint[i1 + 4]);
            float f1 = Float.intBitsToFloat(aint[i1 + 4 + 1]);
            float f2 = p_makeVertexDataSingle_1_.toSingleU(f);
            float f3 = p_makeVertexDataSingle_1_.toSingleV(f1);
            aint[i1 + 4] = Float.floatToRawIntBits(f2);
            aint[i1 + 4 + 1] = Float.floatToRawIntBits(f3);
        }

        return aint;
    }

    public void pipe(IVertexConsumer p_pipe_1_)
    {
        Reflector.callVoid(Reflector.LightUtil_putBakedQuad, new Object[] {p_pipe_1_, this});
    }

    private static TextureAtlasSprite getSpriteByUv(int[] p_getSpriteByUv_0_)
    {
        float f = 1.0F;
        float f1 = 1.0F;
        float f2 = 0.0F;
        float f3 = 0.0F;
        int i = p_getSpriteByUv_0_.length / 4;

        for (int j = 0; j < 4; ++j)
        {
            int k = j * i;
            float f4 = Float.intBitsToFloat(p_getSpriteByUv_0_[k + 4]);
            float f5 = Float.intBitsToFloat(p_getSpriteByUv_0_[k + 4 + 1]);
            f = Math.min(f, f4);
            f1 = Math.min(f1, f5);
            f2 = Math.max(f2, f4);
            f3 = Math.max(f3, f5);
        }

        float f6 = (f + f2) / 2.0F;
        float f7 = (f1 + f3) / 2.0F;
        TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getTextureMapBlocks().getIconByUV((double)f6, (double)f7);
        return textureatlassprite;
    }

    private static int[] fixVertexData(int[] p_fixVertexData_0_)
    {
        if (Config.isShaders())
        {
            if (p_fixVertexData_0_.length == 28)
            {
                p_fixVertexData_0_ = expandVertexData(p_fixVertexData_0_);
            }
        }
        else if (p_fixVertexData_0_.length == 56)
        {
            p_fixVertexData_0_ = compactVertexData(p_fixVertexData_0_);
        }

        return p_fixVertexData_0_;
    }

    private static int[] expandVertexData(int[] p_expandVertexData_0_)
    {
        int i = p_expandVertexData_0_.length / 4;
        int j = i * 2;
        int[] aint = new int[j * 4];

        for (int k = 0; k < 4; ++k)
        {
            System.arraycopy(p_expandVertexData_0_, k * i, aint, k * j, i);
        }

        return aint;
    }

    private static int[] compactVertexData(int[] p_compactVertexData_0_)
    {
        int i = p_compactVertexData_0_.length / 4;
        int j = i / 2;
        int[] aint = new int[j * 4];

        for (int k = 0; k < 4; ++k)
        {
            System.arraycopy(p_compactVertexData_0_, k * i, aint, k * j, j);
        }

        return aint;
    }
}
