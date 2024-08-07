package net.minecraft.client.particle;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ParticleDigging extends Particle {
	private final IBlockState sourceState;
	private BlockPos sourcePos;

	protected ParticleDigging(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, IBlockState state) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		this.sourceState = state;
		this.setParticleTexture(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state));
		this.particleGravity = state.getBlock().blockParticleGravity;
		this.particleRed = 0.6F;
		this.particleGreen = 0.6F;
		this.particleBlue = 0.6F;
		this.particleScale /= 2.0F;
	}

	/**
	 * Sets the position of the block that this particle came from. Used for calculating texture and color multiplier.
	 */
	public ParticleDigging setBlockPos(BlockPos pos) {
		this.sourcePos = pos;

		if (this.sourceState.getBlock() == Blocks.GRASS) {
			return this;
		} else {
			this.multiplyColor(pos);
			return this;
		}
	}

	public ParticleDigging init() {
		this.sourcePos = new BlockPos(this.posX, this.posY, this.posZ);
		Block block = this.sourceState.getBlock();

		if (block == Blocks.GRASS) {
			return this;
		} else {
			this.multiplyColor((BlockPos) null);
			return this;
		}
	}

	protected void multiplyColor(@Nullable BlockPos p_187154_1_) {
		int i = Minecraft.getMinecraft().getBlockColors().colorMultiplier(this.sourceState, this.worldObj, p_187154_1_, 0);
		this.particleRed *= ((i >> 16) & 255) / 255.0F;
		this.particleGreen *= ((i >> 8) & 255) / 255.0F;
		this.particleBlue *= (i & 255) / 255.0F;
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

	@Override
	public int getBrightnessForRender(float p_189214_1_) {
		int i = super.getBrightnessForRender(p_189214_1_);
		int j = 0;

		if (this.worldObj.isBlockLoaded(this.sourcePos)) {
			j = this.worldObj.getCombinedLight(this.sourcePos, 0);
		}

		return i == 0 ? j : i;
	}

	public static class Factory implements IParticleFactory {
		@Override
		public Particle getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
			return (new ParticleDigging(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, Block.getStateById(p_178902_15_[0]))).init();
		}
	}
}
