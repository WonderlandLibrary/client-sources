package net.minecraft.client.renderer.block.model;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class BakedQuad
{
    protected final int[] vertexData;
    protected final int tintIndex;
    protected final EnumFacing face;
    private static final String __OBFID = "CL_00002512";
    private TextureAtlasSprite sprite;
    private int[] vertexDataSingle;
    
    public BakedQuad(final int[] p_i8_1_, final int p_i8_2_, final EnumFacing p_i8_3_, final TextureAtlasSprite p_i8_4_) {
        this.sprite = null;
        this.vertexDataSingle = null;
        this.vertexData = p_i8_1_;
        this.tintIndex = p_i8_2_;
        this.face = p_i8_3_;
        this.sprite = p_i8_4_;
    }
    
    public TextureAtlasSprite getSprite() {
        return this.sprite;
    }
    
    @Override
    public String toString() {
        return "vertex: " + this.vertexData.length / 7 + ", tint: " + this.tintIndex + ", facing: " + this.face + ", sprite: " + this.sprite;
    }
    
    public BakedQuad(final int[] vertexDataIn, final int tintIndexIn, final EnumFacing faceIn) {
        this.sprite = null;
        this.vertexDataSingle = null;
        this.vertexData = vertexDataIn;
        this.tintIndex = tintIndexIn;
        this.face = faceIn;
    }
    
    public int[] getVertexData() {
        return this.vertexData;
    }
    
    public boolean hasTintIndex() {
        return this.tintIndex != -1;
    }
    
    public int getTintIndex() {
        return this.tintIndex;
    }
    
    public EnumFacing getFace() {
        return this.face;
    }
    
    public int[] getVertexDataSingle() {
        if (this.vertexDataSingle == null) {
            this.vertexDataSingle = makeVertexDataSingle(this.vertexData, this.sprite);
        }
        return this.vertexDataSingle;
    }
    
    private static int[] makeVertexDataSingle(final int[] p_makeVertexDataSingle_0_, final TextureAtlasSprite p_makeVertexDataSingle_1_) {
        final int[] aint = new int[p_makeVertexDataSingle_0_.length];
        for (int i = 0; i < aint.length; ++i) {
            aint[i] = p_makeVertexDataSingle_0_[i];
        }
        final int i2 = p_makeVertexDataSingle_1_.sheetWidth / p_makeVertexDataSingle_1_.getIconWidth();
        final int j = p_makeVertexDataSingle_1_.sheetHeight / p_makeVertexDataSingle_1_.getIconHeight();
        for (int k = 0; k < 4; ++k) {
            final int l = k * 7;
            final float f = Float.intBitsToFloat(aint[l + 4]);
            final float f2 = Float.intBitsToFloat(aint[l + 4 + 1]);
            final float f3 = p_makeVertexDataSingle_1_.toSingleU(f);
            final float f4 = p_makeVertexDataSingle_1_.toSingleV(f2);
            aint[l + 4] = Float.floatToRawIntBits(f3);
            aint[l + 4 + 1] = Float.floatToRawIntBits(f4);
        }
        return aint;
    }
}
