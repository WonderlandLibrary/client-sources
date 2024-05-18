package net.minecraft.client.renderer.block.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import net.minecraft.util.EnumFacing;

public class BakedQuad {
	/**
	 * Joined 4 vertex records, each stores packed data according to the VertexFormat of the quad. Vanilla minecraft uses DefaultVertexFormats.BLOCK, Forge uses (usually) ITEM, use BakedQuad.getFormat() to get the correct format.
	 */
	protected int[] vertexData;
	protected final int tintIndex;
	protected final EnumFacing face;
	protected final TextureAtlasSprite sprite;
	private int[] vertexDataSingle = null;
	protected boolean applyDiffuseLighting = Reflector.ForgeHooksClient_fillNormal.exists();
	protected VertexFormat format = DefaultVertexFormats.ITEM;

	public BakedQuad(int[] p_i8_1_, int p_i8_2_, EnumFacing p_i8_3_, TextureAtlasSprite p_i8_4_, boolean p_i8_5_, VertexFormat p_i8_6_) {
		this.vertexData = p_i8_1_;
		this.tintIndex = p_i8_2_;
		this.face = p_i8_3_;
		this.sprite = p_i8_4_;
		this.applyDiffuseLighting = p_i8_5_;
		this.format = p_i8_6_;
		this.fixVertexData();
	}

	public BakedQuad(int[] vertexDataIn, int tintIndexIn, EnumFacing faceIn, TextureAtlasSprite spriteIn) {
		this.vertexData = vertexDataIn;
		this.tintIndex = tintIndexIn;
		this.face = faceIn;
		this.sprite = spriteIn;
		this.fixVertexData();
	}

	public TextureAtlasSprite getSprite() {
		return this.sprite;
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

	public EnumFacing getFace() {
		return this.face;
	}

	public int[] getVertexDataSingle() {
		if (this.vertexDataSingle == null) {
			this.vertexDataSingle = makeVertexDataSingle(this.getVertexData(), this.getSprite());
		}

		return this.vertexDataSingle;
	}

	private static int[] makeVertexDataSingle(int[] p_makeVertexDataSingle_0_, TextureAtlasSprite p_makeVertexDataSingle_1_) {
		int[] aint = p_makeVertexDataSingle_0_.clone();
		int i = p_makeVertexDataSingle_1_.sheetWidth / p_makeVertexDataSingle_1_.getIconWidth();
		int j = p_makeVertexDataSingle_1_.sheetHeight / p_makeVertexDataSingle_1_.getIconHeight();
		int k = aint.length / 4;

		for (int l = 0; l < 4; ++l) {
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

	public VertexFormat getFormat() {
		return this.format;
	}

	public boolean shouldApplyDiffuseLighting() {
		return this.applyDiffuseLighting;
	}

	private static TextureAtlasSprite getSpriteByUv(int[] p_getSpriteByUv_0_) {
		float f = 1.0F;
		float f1 = 1.0F;
		float f2 = 0.0F;
		float f3 = 0.0F;
		int i = p_getSpriteByUv_0_.length / 4;

		for (int j = 0; j < 4; ++j) {
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
		TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getTextureMapBlocks().getIconByUV(f6, f7);
		return textureatlassprite;
	}

	protected void fixVertexData() {
		if (Config.isShaders()) {
			if (this.vertexData.length == 28) {
				this.vertexData = expandVertexData(this.vertexData);
			}
		} else if (this.vertexData.length == 56) {
			this.vertexData = compactVertexData(this.vertexData);
		}
	}

	private static int[] expandVertexData(int[] p_expandVertexData_0_) {
		int i = p_expandVertexData_0_.length / 4;
		int j = i * 2;
		int[] aint = new int[j * 4];

		for (int k = 0; k < 4; ++k) {
			System.arraycopy(p_expandVertexData_0_, k * i, aint, k * j, i);
		}

		return aint;
	}

	private static int[] compactVertexData(int[] p_compactVertexData_0_) {
		int i = p_compactVertexData_0_.length / 4;
		int j = i / 2;
		int[] aint = new int[j * 4];

		for (int k = 0; k < 4; ++k) {
			System.arraycopy(p_compactVertexData_0_, k * i, aint, k * j, j);
		}

		return aint;
	}

	public double getMidX() {
		int[] aint = this.getVertexData();
		int i = aint.length / 4;
		double d0 = 0.0D;

		for (int j = 0; j < 4; ++j) {
			int k = j * i;
			float f = Float.intBitsToFloat(aint[k + 0]);
			d0 += f;
		}

		return d0 / 4.0D;
	}

	public double getMidZ() {
		int[] aint = this.getVertexData();
		int i = aint.length / 4;
		double d0 = 0.0D;

		for (int j = 0; j < 4; ++j) {
			int k = j * i;
			float f = Float.intBitsToFloat(aint[k + 2]);
			d0 += f;
		}

		return d0 / 4.0D;
	}

	@Override
	public String toString() {
		return "vertex: " + (this.vertexData.length / 7) + ", tint: " + this.tintIndex + ", facing: " + this.face + ", sprite: " + this.sprite;
	}
}
