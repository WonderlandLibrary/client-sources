package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityStructureRenderer extends TileEntitySpecialRenderer<TileEntityStructure> {
	@Override
	public void renderTileEntityAt(TileEntityStructure te, double x, double y, double z, float partialTicks, int destroyStage) {
		if (Minecraft.getMinecraft().thePlayer.func_189808_dh() || Minecraft.getMinecraft().thePlayer.isSpectator()) {
			super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);
			BlockPos blockpos = te.func_189711_e();
			BlockPos blockpos1 = te.func_189717_g();

			if ((blockpos1.getX() >= 1) && (blockpos1.getY() >= 1) && (blockpos1.getZ() >= 1)) {
				if ((te.func_189700_k() == TileEntityStructure.Mode.SAVE) || (te.func_189700_k() == TileEntityStructure.Mode.LOAD)) {
					double d0 = 0.01D;
					double d1 = blockpos.getX();
					double d2 = blockpos.getZ();
					double d6 = (y + blockpos.getY()) - 0.01D;
					double d9 = d6 + blockpos1.getY() + 0.02D;
					double d3;
					double d4;

					switch (te.func_189716_h()) {
					case LEFT_RIGHT:
						d3 = blockpos1.getX() + 0.02D;
						d4 = -(blockpos1.getZ() + 0.02D);
						break;

					case FRONT_BACK:
						d3 = -(blockpos1.getX() + 0.02D);
						d4 = blockpos1.getZ() + 0.02D;
						break;

					default:
						d3 = blockpos1.getX() + 0.02D;
						d4 = blockpos1.getZ() + 0.02D;
					}

					double d5;
					double d7;
					double d8;
					double d10;

					switch (te.func_189726_i()) {
					case CLOCKWISE_90:
						d5 = x + (d4 < 0.0D ? d1 - 0.01D : d1 + 1.0D + 0.01D);
						d7 = z + (d3 < 0.0D ? d2 + 1.0D + 0.01D : d2 - 0.01D);
						d8 = d5 - d4;
						d10 = d7 + d3;
						break;

					case CLOCKWISE_180:
						d5 = x + (d3 < 0.0D ? d1 - 0.01D : d1 + 1.0D + 0.01D);
						d7 = z + (d4 < 0.0D ? d2 - 0.01D : d2 + 1.0D + 0.01D);
						d8 = d5 - d3;
						d10 = d7 - d4;
						break;

					case COUNTERCLOCKWISE_90:
						d5 = x + (d4 < 0.0D ? d1 + 1.0D + 0.01D : d1 - 0.01D);
						d7 = z + (d3 < 0.0D ? d2 - 0.01D : d2 + 1.0D + 0.01D);
						d8 = d5 + d4;
						d10 = d7 - d3;
						break;

					default:
						d5 = x + (d3 < 0.0D ? d1 + 1.0D + 0.01D : d1 - 0.01D);
						d7 = z + (d4 < 0.0D ? d2 + 1.0D + 0.01D : d2 - 0.01D);
						d8 = d5 + d3;
						d10 = d7 + d4;
					}

					int i = 255;
					int j = 223;
					int k = 127;
					Tessellator tessellator = Tessellator.getInstance();
					VertexBuffer vertexbuffer = tessellator.getBuffer();
					GlStateManager.disableFog();
					GlStateManager.disableLighting();
					GlStateManager.disableTexture2D();
					GlStateManager.enableBlend();
					GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
							GlStateManager.DestFactor.ZERO);
					this.func_190053_a(true);

					if ((te.func_189700_k() == TileEntityStructure.Mode.SAVE) || te.func_189721_I()) {
						this.func_190055_a(tessellator, vertexbuffer, d5, d6, d7, d8, d9, d10, 255, 223, 127);
					}

					if ((te.func_189700_k() == TileEntityStructure.Mode.SAVE) && te.func_189707_H()) {
						this.func_190054_a(te, x, y, z, blockpos, tessellator, vertexbuffer, true);
						this.func_190054_a(te, x, y, z, blockpos, tessellator, vertexbuffer, false);
					}

					this.func_190053_a(false);
					GlStateManager.glLineWidth(1.0F);
					GlStateManager.enableLighting();
					GlStateManager.enableTexture2D();
					GlStateManager.enableDepth();
					GlStateManager.depthMask(true);
					GlStateManager.enableFog();
				}
			}
		}
	}

	private void func_190054_a(TileEntityStructure p_190054_1_, double p_190054_2_, double p_190054_4_, double p_190054_6_, BlockPos p_190054_8_, Tessellator p_190054_9_, VertexBuffer p_190054_10_,
			boolean p_190054_11_) {
		GlStateManager.glLineWidth(p_190054_11_ ? 3.0F : 1.0F);
		p_190054_10_.begin(3, DefaultVertexFormats.POSITION_COLOR);
		World world = p_190054_1_.getWorld();
		BlockPos blockpos = p_190054_1_.getPos();
		BlockPos blockpos1 = blockpos.add(p_190054_8_);

		for (BlockPos blockpos2 : BlockPos.getAllInBox(blockpos1, blockpos1.add(p_190054_1_.func_189717_g()).add(-1, -1, -1))) {
			IBlockState iblockstate = world.getBlockState(blockpos2);
			boolean flag = iblockstate == Blocks.AIR.getDefaultState();
			boolean flag1 = iblockstate == Blocks.field_189881_dj.getDefaultState();

			if (flag || flag1) {
				float f = flag ? 0.05F : 0.0F;
				double d0 = (blockpos2.getX() - blockpos.getX() + 0.45F + p_190054_2_) - f;
				double d1 = (blockpos2.getY() - blockpos.getY() + 0.45F + p_190054_4_) - f;
				double d2 = (blockpos2.getZ() - blockpos.getZ() + 0.45F + p_190054_6_) - f;
				double d3 = blockpos2.getX() - blockpos.getX() + 0.55F + p_190054_2_ + f;
				double d4 = blockpos2.getY() - blockpos.getY() + 0.55F + p_190054_4_ + f;
				double d5 = blockpos2.getZ() - blockpos.getZ() + 0.55F + p_190054_6_ + f;

				if (p_190054_11_) {
					RenderGlobal.func_189698_a(p_190054_10_, d0, d1, d2, d3, d4, d5, 0.0F, 0.0F, 0.0F, 1.0F);
				} else if (flag) {
					RenderGlobal.func_189698_a(p_190054_10_, d0, d1, d2, d3, d4, d5, 0.5F, 0.5F, 1.0F, 1.0F);
				} else {
					RenderGlobal.func_189698_a(p_190054_10_, d0, d1, d2, d3, d4, d5, 1.0F, 0.25F, 0.25F, 1.0F);
				}
			}
		}

		p_190054_9_.draw();
	}

	private void func_190055_a(Tessellator p_190055_1_, VertexBuffer p_190055_2_, double p_190055_3_, double p_190055_5_, double p_190055_7_, double p_190055_9_, double p_190055_11_,
			double p_190055_13_, int p_190055_15_, int p_190055_16_, int p_190055_17_) {
		GlStateManager.glLineWidth(2.0F);
		p_190055_2_.begin(3, DefaultVertexFormats.POSITION_COLOR);
		p_190055_2_.pos(p_190055_3_, p_190055_5_, p_190055_7_).color(p_190055_16_, p_190055_16_, p_190055_16_, 0.0F).endVertex();
		p_190055_2_.pos(p_190055_3_, p_190055_5_, p_190055_7_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
		p_190055_2_.pos(p_190055_9_, p_190055_5_, p_190055_7_).color(p_190055_16_, p_190055_17_, p_190055_17_, p_190055_15_).endVertex();
		p_190055_2_.pos(p_190055_9_, p_190055_5_, p_190055_13_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
		p_190055_2_.pos(p_190055_3_, p_190055_5_, p_190055_13_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
		p_190055_2_.pos(p_190055_3_, p_190055_5_, p_190055_7_).color(p_190055_17_, p_190055_17_, p_190055_16_, p_190055_15_).endVertex();
		p_190055_2_.pos(p_190055_3_, p_190055_11_, p_190055_7_).color(p_190055_17_, p_190055_16_, p_190055_17_, p_190055_15_).endVertex();
		p_190055_2_.pos(p_190055_9_, p_190055_11_, p_190055_7_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
		p_190055_2_.pos(p_190055_9_, p_190055_11_, p_190055_13_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
		p_190055_2_.pos(p_190055_3_, p_190055_11_, p_190055_13_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
		p_190055_2_.pos(p_190055_3_, p_190055_11_, p_190055_7_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
		p_190055_2_.pos(p_190055_3_, p_190055_11_, p_190055_13_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
		p_190055_2_.pos(p_190055_3_, p_190055_5_, p_190055_13_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
		p_190055_2_.pos(p_190055_9_, p_190055_5_, p_190055_13_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
		p_190055_2_.pos(p_190055_9_, p_190055_11_, p_190055_13_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
		p_190055_2_.pos(p_190055_9_, p_190055_11_, p_190055_7_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
		p_190055_2_.pos(p_190055_9_, p_190055_5_, p_190055_7_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
		p_190055_2_.pos(p_190055_9_, p_190055_5_, p_190055_7_).color(p_190055_16_, p_190055_16_, p_190055_16_, 0.0F).endVertex();
		p_190055_1_.draw();
		GlStateManager.glLineWidth(1.0F);
	}

	@Override
	public boolean isGlobalRenderer(TileEntityStructure te) {
		return true;
	}
}
