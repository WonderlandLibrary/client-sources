/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import net.minecraft.client.renderer.model.FaceBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.IVertexProducer;
import net.optifine.Config;
import net.optifine.model.BakedQuadRetextured;
import net.optifine.model.QuadBounds;
import net.optifine.reflect.Reflector;
import net.optifine.render.QuadVertexPositions;
import net.optifine.render.VertexPosition;

public class BakedQuad
implements IVertexProducer {
    protected int[] vertexData;
    protected final int tintIndex;
    protected Direction face;
    protected TextureAtlasSprite sprite;
    private final boolean applyDiffuseLighting;
    private int[] vertexDataSingle = null;
    private QuadBounds quadBounds;
    private boolean quadEmissiveChecked;
    private BakedQuad quadEmissive;
    private QuadVertexPositions quadVertexPositions;

    public BakedQuad(int[] nArray, int n, Direction direction, TextureAtlasSprite textureAtlasSprite, boolean bl) {
        this.vertexData = nArray;
        this.tintIndex = n;
        this.face = direction;
        this.sprite = textureAtlasSprite;
        this.applyDiffuseLighting = bl;
        this.fixVertexData();
    }

    public int[] getVertexData() {
        this.fixVertexData();
        return this.vertexData;
    }

    public boolean hasTintIndex() {
        return this.tintIndex != -1;
    }

    public int getTintIndex() {
        return this.tintIndex;
    }

    public Direction getFace() {
        if (this.face == null) {
            this.face = FaceBakery.getFacingFromVertexData(this.getVertexData());
        }
        return this.face;
    }

    public boolean applyDiffuseLighting() {
        return this.applyDiffuseLighting;
    }

    public TextureAtlasSprite getSprite() {
        if (this.sprite == null) {
            this.sprite = BakedQuad.getSpriteByUv(this.getVertexData());
        }
        return this.sprite;
    }

    public int[] getVertexDataSingle() {
        if (this.vertexDataSingle == null) {
            this.vertexDataSingle = BakedQuad.makeVertexDataSingle(this.getVertexData(), this.getSprite());
        }
        if (this.vertexDataSingle.length != this.getVertexData().length) {
            this.vertexDataSingle = BakedQuad.makeVertexDataSingle(this.getVertexData(), this.getSprite());
        }
        return this.vertexDataSingle;
    }

    private static int[] makeVertexDataSingle(int[] nArray, TextureAtlasSprite textureAtlasSprite) {
        int[] nArray2 = (int[])nArray.clone();
        int n = nArray2.length / 4;
        for (int i = 0; i < 4; ++i) {
            int n2 = i * n;
            float f = Float.intBitsToFloat(nArray2[n2 + 4]);
            float f2 = Float.intBitsToFloat(nArray2[n2 + 4 + 1]);
            float f3 = textureAtlasSprite.toSingleU(f);
            float f4 = textureAtlasSprite.toSingleV(f2);
            nArray2[n2 + 4] = Float.floatToRawIntBits(f3);
            nArray2[n2 + 4 + 1] = Float.floatToRawIntBits(f4);
        }
        return nArray2;
    }

    @Override
    public void pipe(IVertexConsumer iVertexConsumer) {
        Reflector.callVoid(Reflector.LightUtil_putBakedQuad, iVertexConsumer, this);
    }

    private static TextureAtlasSprite getSpriteByUv(int[] nArray) {
        float f = 1.0f;
        float f2 = 1.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        int n = nArray.length / 4;
        for (int i = 0; i < 4; ++i) {
            int n2 = i * n;
            float f5 = Float.intBitsToFloat(nArray[n2 + 4]);
            float f6 = Float.intBitsToFloat(nArray[n2 + 4 + 1]);
            f = Math.min(f, f5);
            f2 = Math.min(f2, f6);
            f3 = Math.max(f3, f5);
            f4 = Math.max(f4, f6);
        }
        float f7 = (f + f3) / 2.0f;
        float f8 = (f2 + f4) / 2.0f;
        return Config.getTextureMap().getIconByUV(f7, f8);
    }

    protected void fixVertexData() {
        if (Config.isShaders()) {
            if (this.vertexData.length == DefaultVertexFormats.BLOCK_VANILLA_SIZE) {
                this.vertexData = BakedQuad.fixVertexDataSize(this.vertexData, DefaultVertexFormats.BLOCK_SHADERS_SIZE);
            }
        } else if (this.vertexData.length == DefaultVertexFormats.BLOCK_SHADERS_SIZE) {
            this.vertexData = BakedQuad.fixVertexDataSize(this.vertexData, DefaultVertexFormats.BLOCK_VANILLA_SIZE);
        }
    }

    private static int[] fixVertexDataSize(int[] nArray, int n) {
        int n2 = nArray.length / 4;
        int n3 = n / 4;
        int[] nArray2 = new int[n3 * 4];
        for (int i = 0; i < 4; ++i) {
            int n4 = Math.min(n2, n3);
            System.arraycopy(nArray, i * n2, nArray2, i * n3, n4);
        }
        return nArray2;
    }

    public QuadBounds getQuadBounds() {
        if (this.quadBounds == null) {
            this.quadBounds = new QuadBounds(this.getVertexData());
        }
        return this.quadBounds;
    }

    public float getMidX() {
        QuadBounds quadBounds = this.getQuadBounds();
        return (quadBounds.getMaxX() + quadBounds.getMinX()) / 2.0f;
    }

    public double getMidY() {
        QuadBounds quadBounds = this.getQuadBounds();
        return (quadBounds.getMaxY() + quadBounds.getMinY()) / 2.0f;
    }

    public double getMidZ() {
        QuadBounds quadBounds = this.getQuadBounds();
        return (quadBounds.getMaxZ() + quadBounds.getMinZ()) / 2.0f;
    }

    public boolean isFaceQuad() {
        QuadBounds quadBounds = this.getQuadBounds();
        return quadBounds.isFaceQuad(this.face);
    }

    public boolean isFullQuad() {
        QuadBounds quadBounds = this.getQuadBounds();
        return quadBounds.isFullQuad(this.face);
    }

    public boolean isFullFaceQuad() {
        return this.isFullQuad() && this.isFaceQuad();
    }

    public BakedQuad getQuadEmissive() {
        if (this.quadEmissiveChecked) {
            return this.quadEmissive;
        }
        if (this.quadEmissive == null && this.sprite != null && this.sprite.spriteEmissive != null) {
            this.quadEmissive = new BakedQuadRetextured(this, this.sprite.spriteEmissive);
        }
        this.quadEmissiveChecked = true;
        return this.quadEmissive;
    }

    public VertexPosition[] getVertexPositions(int n) {
        if (this.quadVertexPositions == null) {
            this.quadVertexPositions = new QuadVertexPositions();
        }
        return (VertexPosition[])this.quadVertexPositions.get(n);
    }

    public String toString() {
        return "vertexData: " + this.vertexData.length + ", tint: " + this.tintIndex + ", facing: " + this.face + ", sprite: " + this.sprite;
    }
}

