package net.minecraft.client.renderer.debug;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

public class DebugRendererPathfinding implements DebugRenderer.IDebugRenderer {
	private final Minecraft minecraft;
	private final Map<Integer, Path> pathMap = Maps.<Integer, Path> newHashMap();
	private final Map<Integer, Float> pathMaxDistance = Maps.<Integer, Float> newHashMap();
	private final Map<Integer, Long> creationMap = Maps.<Integer, Long> newHashMap();
	private EntityPlayer field_190068_e;
	private double field_190069_f;
	private double field_190070_g;
	private double field_190071_h;

	public DebugRendererPathfinding(Minecraft minecraftIn) {
		this.minecraft = minecraftIn;
	}

	public void addPath(int p_188289_1_, Path p_188289_2_, float p_188289_3_) {
		this.pathMap.put(Integer.valueOf(p_188289_1_), p_188289_2_);
		this.creationMap.put(Integer.valueOf(p_188289_1_), Long.valueOf(System.currentTimeMillis()));
		this.pathMaxDistance.put(Integer.valueOf(p_188289_1_), Float.valueOf(p_188289_3_));
	}

	@Override
	public void func_190060_a(float p_190060_1_, long p_190060_2_) {
		if (this.pathMap.size() != 0) {
			long i = System.currentTimeMillis();
			this.field_190068_e = this.minecraft.thePlayer;
			this.field_190069_f = this.field_190068_e.lastTickPosX + ((this.field_190068_e.posX - this.field_190068_e.lastTickPosX) * p_190060_1_);
			this.field_190070_g = this.field_190068_e.lastTickPosY + ((this.field_190068_e.posY - this.field_190068_e.lastTickPosY) * p_190060_1_);
			this.field_190071_h = this.field_190068_e.lastTickPosZ + ((this.field_190068_e.posZ - this.field_190068_e.lastTickPosZ) * p_190060_1_);
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.color(0.0F, 1.0F, 0.0F, 0.75F);
			GlStateManager.disableTexture2D();
			GlStateManager.glLineWidth(6.0F);

			for (Integer integer : this.pathMap.keySet()) {
				Path path = this.pathMap.get(integer);
				float f = this.pathMaxDistance.get(integer).floatValue();
				this.func_190067_a(p_190060_1_, path);
				PathPoint pathpoint = path.func_189964_i();

				if (this.func_190066_a(pathpoint) <= 40.0F) {
					RenderGlobal.func_189696_b((new AxisAlignedBB(pathpoint.xCoord + 0.25F, pathpoint.yCoord + 0.25F, pathpoint.zCoord + 0.25D, pathpoint.xCoord + 0.75F, pathpoint.yCoord + 0.75F,
							pathpoint.zCoord + 0.75F)).offset(-this.field_190069_f, -this.field_190070_g, -this.field_190071_h), 0.0F, 1.0F, 0.0F, 0.5F);

					for (int j = 0; j < path.getCurrentPathLength(); ++j) {
						PathPoint pathpoint1 = path.getPathPointFromIndex(j);

						if (this.func_190066_a(pathpoint1) <= 40.0F) {
							float f1 = j == path.getCurrentPathIndex() ? 1.0F : 0.0F;
							float f2 = j == path.getCurrentPathIndex() ? 0.0F : 1.0F;
							RenderGlobal.func_189696_b(
									(new AxisAlignedBB((pathpoint1.xCoord + 0.5F) - f, pathpoint1.yCoord + (0.01F * j), (pathpoint1.zCoord + 0.5F) - f, pathpoint1.xCoord + 0.5F + f,
											pathpoint1.yCoord + 0.25F + (0.01F * j), pathpoint1.zCoord + 0.5F + f)).offset(-this.field_190069_f, -this.field_190070_g, -this.field_190071_h),
									f1, 0.0F, f2, 0.5F);
						}
					}
				}
			}

			for (Integer integer1 : this.pathMap.keySet()) {
				Path path1 = this.pathMap.get(integer1);

				for (PathPoint pathpoint3 : path1.func_189965_h()) {
					if (this.func_190066_a(pathpoint3) <= 40.0F) {
						DebugRenderer.func_190076_a(String.format("%s", new Object[] { pathpoint3.nodeType }), pathpoint3.xCoord + 0.5D, pathpoint3.yCoord + 0.75D, pathpoint3.zCoord + 0.5D,
								p_190060_1_, -65536);
						DebugRenderer.func_190076_a(String.format("%.2f", new Object[] { Float.valueOf(pathpoint3.costMalus) }), pathpoint3.xCoord + 0.5D, pathpoint3.yCoord + 0.25D,
								pathpoint3.zCoord + 0.5D, p_190060_1_, -65536);
					}
				}

				for (PathPoint pathpoint4 : path1.func_189966_g()) {
					if (this.func_190066_a(pathpoint4) <= 40.0F) {
						DebugRenderer.func_190076_a(String.format("%s", new Object[] { pathpoint4.nodeType }), pathpoint4.xCoord + 0.5D, pathpoint4.yCoord + 0.75D, pathpoint4.zCoord + 0.5D,
								p_190060_1_, -16776961);
						DebugRenderer.func_190076_a(String.format("%.2f", new Object[] { Float.valueOf(pathpoint4.costMalus) }), pathpoint4.xCoord + 0.5D, pathpoint4.yCoord + 0.25D,
								pathpoint4.zCoord + 0.5D, p_190060_1_, -16776961);
					}
				}

				for (int k = 0; k < path1.getCurrentPathLength(); ++k) {
					PathPoint pathpoint2 = path1.getPathPointFromIndex(k);

					if (this.func_190066_a(pathpoint2) <= 40.0F) {
						DebugRenderer.func_190076_a(String.format("%s", new Object[] { pathpoint2.nodeType }), pathpoint2.xCoord + 0.5D, pathpoint2.yCoord + 0.75D, pathpoint2.zCoord + 0.5D,
								p_190060_1_, -1);
						DebugRenderer.func_190076_a(String.format("%.2f", new Object[] { Float.valueOf(pathpoint2.costMalus) }), pathpoint2.xCoord + 0.5D, pathpoint2.yCoord + 0.25D,
								pathpoint2.zCoord + 0.5D, p_190060_1_, -1);
					}
				}
			}

			for (Integer integer2 : this.creationMap.keySet().toArray(new Integer[0])) {
				if ((i - this.creationMap.get(integer2).longValue()) > 20000L) {
					this.pathMap.remove(integer2);
					this.creationMap.remove(integer2);
				}
			}

			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
	}

	public void func_190067_a(float p_190067_1_, Path p_190067_2_) {
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(3, DefaultVertexFormats.POSITION_COLOR);

		for (int i = 0; i < p_190067_2_.getCurrentPathLength(); ++i) {
			PathPoint pathpoint = p_190067_2_.getPathPointFromIndex(i);

			if (this.func_190066_a(pathpoint) <= 40.0F) {
				float f = ((float) i / (float) p_190067_2_.getCurrentPathLength()) * 0.33F;
				int j = i == 0 ? 0 : MathHelper.hsvToRGB(f, 0.9F, 0.9F);
				int k = (j >> 16) & 255;
				int l = (j >> 8) & 255;
				int i1 = j & 255;
				vertexbuffer.pos((pathpoint.xCoord - this.field_190069_f) + 0.5D, (pathpoint.yCoord - this.field_190070_g) + 0.5D, (pathpoint.zCoord - this.field_190071_h) + 0.5D).color(k, l, i1, 255)
						.endVertex();
			}
		}

		tessellator.draw();
	}

	private float func_190066_a(PathPoint p_190066_1_) {
		return (float) (Math.abs(p_190066_1_.xCoord - this.field_190068_e.posX) + Math.abs(p_190066_1_.yCoord - this.field_190068_e.posY) + Math.abs(p_190066_1_.zCoord - this.field_190068_e.posZ));
	}
}
