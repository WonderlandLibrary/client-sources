package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleSweepAttack extends Particle {
	private static final ResourceLocation SWEEP_TEXTURE = new ResourceLocation("textures/entity/sweep.png");
	private static final VertexFormat VERTEX_FORMAT = (new VertexFormat()).addElement(DefaultVertexFormats.POSITION_3F).addElement(DefaultVertexFormats.TEX_2F)
			.addElement(DefaultVertexFormats.COLOR_4UB).addElement(DefaultVertexFormats.TEX_2S).addElement(DefaultVertexFormats.NORMAL_3B).addElement(DefaultVertexFormats.PADDING_1B);
	private int life;
	private final int lifeTime;
	private final TextureManager textureManager;
	private final float size;

	protected ParticleSweepAttack(TextureManager textureManagerIn, World worldIn, double x, double y, double z, double p_i46582_9_, double p_i46582_11_, double p_i46582_13_) {
		super(worldIn, x, y, z, 0.0D, 0.0D, 0.0D);
		this.textureManager = textureManagerIn;
		this.lifeTime = 4;
		float f = (this.rand.nextFloat() * 0.6F) + 0.4F;
		this.particleRed = f;
		this.particleGreen = f;
		this.particleBlue = f;
		this.size = 1.0F - ((float) p_i46582_9_ * 0.5F);
	}

	/**
	 * Renders the particle
	 */
	@Override
	public void renderParticle(VertexBuffer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		int i = (int) (((this.life + partialTicks) * 3.0F) / this.lifeTime);

		if (i <= 7) {
			this.textureManager.bindTexture(SWEEP_TEXTURE);
			float f = i % 4 / 4.0F;
			float f1 = f + 0.24975F;
			float f2 = i / 2 / 2.0F;
			float f3 = f2 + 0.4995F;
			float f4 = 1.0F * this.size;
			float f5 = (float) ((this.prevPosX + ((this.posX - this.prevPosX) * partialTicks)) - interpPosX);
			float f6 = (float) ((this.prevPosY + ((this.posY - this.prevPosY) * partialTicks)) - interpPosY);
			float f7 = (float) ((this.prevPosZ + ((this.posZ - this.prevPosZ) * partialTicks)) - interpPosZ);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.disableLighting();
			RenderHelper.disableStandardItemLighting();
			worldRendererIn.begin(7, VERTEX_FORMAT);
			worldRendererIn.pos(f5 - (rotationX * f4) - (rotationXY * f4), f6 - (rotationZ * f4 * 0.5F), f7 - (rotationYZ * f4) - (rotationXZ * f4)).tex(f1, f3)
					.color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
			worldRendererIn.pos((f5 - (rotationX * f4)) + (rotationXY * f4), f6 + (rotationZ * f4 * 0.5F), (f7 - (rotationYZ * f4)) + (rotationXZ * f4)).tex(f1, f2)
					.color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
			worldRendererIn.pos(f5 + (rotationX * f4) + (rotationXY * f4), f6 + (rotationZ * f4 * 0.5F), f7 + (rotationYZ * f4) + (rotationXZ * f4)).tex(f, f2)
					.color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
			worldRendererIn.pos((f5 + (rotationX * f4)) - (rotationXY * f4), f6 - (rotationZ * f4 * 0.5F), (f7 + (rotationYZ * f4)) - (rotationXZ * f4)).tex(f, f3)
					.color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
			Tessellator.getInstance().draw();
			GlStateManager.enableLighting();
		}
	}

	@Override
	public int getBrightnessForRender(float p_189214_1_) {
		return 61680;
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		++this.life;

		if (this.life == this.lifeTime) {
			this.setExpired();
		}
	}

	@Override
	public int getFXLayer() {
		return 3;
	}

	public static class Factory implements IParticleFactory {
		@Override
		public Particle getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
			return new ParticleSweepAttack(Minecraft.getMinecraft().getTextureManager(), worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		}
	}
}
