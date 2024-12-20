/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.client.renderer.block.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.IVertexProducer;
import optifine.Config;
import optifine.Reflector;
import optifine.ReflectorMethod;

public class BakedQuad
implements IVertexProducer {
    protected int[] field_178215_a;
    protected final int field_178213_b;
    protected final EnumFacing face;
    private static final String __OBFID = "CL_00002512";
    private TextureAtlasSprite sprite = null;
    private int[] vertexDataSingle = null;

    public BakedQuad(int[] p_i46232_1_, int p_i46232_2_, EnumFacing p_i46232_3_, TextureAtlasSprite sprite) {
        this.field_178215_a = p_i46232_1_;
        this.field_178213_b = p_i46232_2_;
        this.face = p_i46232_3_;
        this.sprite = sprite;
        this.fixVertexData();
    }

    public TextureAtlasSprite getSprite() {
        if (this.sprite == null) {
            this.sprite = BakedQuad.getSpriteByUv(this.func_178209_a());
        }
        return this.sprite;
    }

    public String toString() {
        return "vertex: " + this.field_178215_a.length / 7 + ", tint: " + this.field_178213_b + ", facing: " + this.face + ", sprite: " + this.sprite;
    }

    public BakedQuad(int[] p_i46232_1_, int p_i46232_2_, EnumFacing p_i46232_3_) {
        this.field_178215_a = p_i46232_1_;
        this.field_178213_b = p_i46232_2_;
        this.face = p_i46232_3_;
        this.fixVertexData();
    }

    public int[] func_178209_a() {
        this.fixVertexData();
        return this.field_178215_a;
    }

    public boolean func_178212_b() {
        return this.field_178213_b != -1;
    }

    public int func_178211_c() {
        return this.field_178213_b;
    }

    public EnumFacing getFace() {
        return this.face;
    }

    public int[] getVertexDataSingle() {
        if (this.vertexDataSingle == null) {
            this.vertexDataSingle = BakedQuad.makeVertexDataSingle(this.func_178209_a(), this.getSprite());
        }
        return this.vertexDataSingle;
    }

    private static int[] makeVertexDataSingle(int[] vd2, TextureAtlasSprite sprite) {
        int[] vdSingle = (int[])vd2.clone();
        int ku = sprite.sheetWidth / sprite.getIconWidth();
        int kv = sprite.sheetHeight / sprite.getIconHeight();
        int step = vdSingle.length / 4;
        for (int i2 = 0; i2 < 4; ++i2) {
            int pos = i2 * step;
            float tu2 = Float.intBitsToFloat(vdSingle[pos + 4]);
            float tv2 = Float.intBitsToFloat(vdSingle[pos + 4 + 1]);
            float u2 = sprite.toSingleU(tu2);
            float v2 = sprite.toSingleV(tv2);
            vdSingle[pos + 4] = Float.floatToRawIntBits(u2);
            vdSingle[pos + 4 + 1] = Float.floatToRawIntBits(v2);
        }
        return vdSingle;
    }

    @Override
    public void pipe(IVertexConsumer consumer) {
        Reflector.callVoid(Reflector.LightUtil_putBakedQuad, consumer, this);
    }

    private static TextureAtlasSprite getSpriteByUv(int[] vertexData) {
        float uMin = 1.0f;
        float vMin = 1.0f;
        float uMax = 0.0f;
        float vMax = 0.0f;
        int step = vertexData.length / 4;
        for (int uMid = 0; uMid < 4; ++uMid) {
            int vMid = uMid * step;
            float spriteUv = Float.intBitsToFloat(vertexData[vMid + 4]);
            float tv2 = Float.intBitsToFloat(vertexData[vMid + 4 + 1]);
            uMin = Math.min(uMin, spriteUv);
            vMin = Math.min(vMin, tv2);
            uMax = Math.max(uMax, spriteUv);
            vMax = Math.max(vMax, tv2);
        }
        float var10 = (uMin + uMax) / 2.0f;
        float var11 = (vMin + vMax) / 2.0f;
        TextureAtlasSprite var12 = Minecraft.getMinecraft().getTextureMapBlocks().getIconByUV(var10, var11);
        return var12;
    }

    private void fixVertexData() {
        if (Config.isShaders()) {
            if (this.field_178215_a.length == 28) {
                this.field_178215_a = BakedQuad.expandVertexData(this.field_178215_a);
            }
        } else if (this.field_178215_a.length == 56) {
            this.field_178215_a = BakedQuad.compactVertexData(this.field_178215_a);
        }
    }

    private static int[] expandVertexData(int[] vd2) {
        int step = vd2.length / 4;
        int stepNew = step * 2;
        int[] vdNew = new int[stepNew * 4];
        for (int i2 = 0; i2 < 4; ++i2) {
            System.arraycopy(vd2, i2 * step, vdNew, i2 * stepNew, step);
        }
        return vdNew;
    }

    private static int[] compactVertexData(int[] vd2) {
        int step = vd2.length / 4;
        int stepNew = step / 2;
        int[] vdNew = new int[stepNew * 4];
        for (int i2 = 0; i2 < 4; ++i2) {
            System.arraycopy(vd2, i2 * step, vdNew, i2 * stepNew, stepNew);
        }
        return vdNew;
    }
}

