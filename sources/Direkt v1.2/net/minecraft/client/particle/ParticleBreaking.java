package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ParticleBreaking extends Particle {
	protected ParticleBreaking(World worldIn, double posXIn, double posYIn, double posZIn, Item itemIn) {
		this(worldIn, posXIn, posYIn, posZIn, itemIn, 0);
	}

	protected ParticleBreaking(World worldIn, double posXIn, double posYIn, double posZIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, Item itemIn, int meta) {
		this(worldIn, posXIn, posYIn, posZIn, itemIn, meta);
		this.motionX *= 0.10000000149011612D;
		this.motionY *= 0.10000000149011612D;
		this.motionZ *= 0.10000000149011612D;
		this.motionX += xSpeedIn;
		this.motionY += ySpeedIn;
		this.motionZ += zSpeedIn;
	}

	protected ParticleBreaking(World worldIn, double posXIn, double posYIn, double posZIn, Item itemIn, int meta) {
		super(worldIn, posXIn, posYIn, posZIn, 0.0D, 0.0D, 0.0D);
		this.setParticleTexture(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(itemIn, meta));
		this.particleRed = 1.0F;
		this.particleGreen = 1.0F;
		this.particleBlue = 1.0F;
		this.particleGravity = Blocks.SNOW.blockParticleGravity;
		this.particleScale /= 2.0F;
	}

	@Override
	public int getFXLayer() {
		return 1;
	}

	/**
	 * Renders the particle
	 */
	@Override
	public void renderParticle(VertexBuffer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		float f = (this.particleTextureIndexX + (this.particleTextureJitterX / 4.0F)) / 16.0F;
		float f1 = f + 0.015609375F;
		float f2 = (this.particleTextureIndexY + (this.particleTextureJitterY / 4.0F)) / 16.0F;
		float f3 = f2 + 0.015609375F;
		float f4 = 0.1F * this.particleScale;

		if (this.particleTexture != null) {
			f = this.particleTexture.getInterpolatedU((this.particleTextureJitterX / 4.0F) * 16.0F);
			f1 = this.particleTexture.getInterpolatedU(((this.particleTextureJitterX + 1.0F) / 4.0F) * 16.0F);
			f2 = this.particleTexture.getInterpolatedV((this.particleTextureJitterY / 4.0F) * 16.0F);
			f3 = this.particleTexture.getInterpolatedV(((this.particleTextureJitterY + 1.0F) / 4.0F) * 16.0F);
		}

		float f5 = (float) ((this.prevPosX + ((this.posX - this.prevPosX) * partialTicks)) - interpPosX);
		float f6 = (float) ((this.prevPosY + ((this.posY - this.prevPosY) * partialTicks)) - interpPosY);
		float f7 = (float) ((this.prevPosZ + ((this.posZ - this.prevPosZ) * partialTicks)) - interpPosZ);
		int i = this.getBrightnessForRender(partialTicks);
		int j = (i >> 16) & 65535;
		int k = i & 65535;
		worldRendererIn.pos(f5 - (rotationX * f4) - (rotationXY * f4), f6 - (rotationZ * f4), f7 - (rotationYZ * f4) - (rotationXZ * f4)).tex(f, f3)
				.color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
		worldRendererIn.pos((f5 - (rotationX * f4)) + (rotationXY * f4), f6 + (rotationZ * f4), (f7 - (rotationYZ * f4)) + (rotationXZ * f4)).tex(f, f2)
				.color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
		worldRendererIn.pos(f5 + (rotationX * f4) + (rotationXY * f4), f6 + (rotationZ * f4), f7 + (rotationYZ * f4) + (rotationXZ * f4)).tex(f1, f2)
				.color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
		worldRendererIn.pos((f5 + (rotationX * f4)) - (rotationXY * f4), f6 - (rotationZ * f4), (f7 + (rotationYZ * f4)) - (rotationXZ * f4)).tex(f1, f3)
				.color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
	}

	public static class Factory implements IParticleFactory {
		@Override
		public Particle getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
			int i = p_178902_15_.length > 1 ? p_178902_15_[1] : 0;
			return new ParticleBreaking(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, Item.getItemById(p_178902_15_[0]), i);
		}
	}

	public static class SlimeFactory implements IParticleFactory {
		@Override
		public Particle getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
			return new ParticleBreaking(worldIn, xCoordIn, yCoordIn, zCoordIn, Items.SLIME_BALL);
		}
	}

	public static class SnowballFactory implements IParticleFactory {
		@Override
		public Particle getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
			return new ParticleBreaking(worldIn, xCoordIn, yCoordIn, zCoordIn, Items.SNOWBALL);
		}
	}
}
