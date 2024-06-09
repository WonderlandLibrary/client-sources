package com.kilo.render;

import javax.vecmath.Vector3d;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import com.kilo.mod.ModuleManager;
import com.kilo.mod.all.Nametags;
import com.kilo.util.RenderUtil;
import com.kilo.util.Util;

public class Render {
	private static Minecraft mc = Minecraft.getMinecraft();

	public static void box(Entity e, int color) {
		float var11 = (float) (color >> 24 & 255) / 255.0F;
		float var6 = (float) (color >> 16 & 255) / 255.0F;
		float var7 = (float) (color >> 8 & 255) / 255.0F;
		float var8 = (float) (color & 255) / 255.0F;
		Tessellator var9 = Tessellator.getInstance();
		WorldRenderer t = var9.getWorldRenderer();

		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GlStateManager.color(var6, var7, var8, var11);

		double[] pos = RenderUtil.entityRenderPos(e);

		AxisAlignedBB bb = e.getEntityBoundingBox();
		AxisAlignedBB aa = new AxisAlignedBB(bb.minX - e.posX + pos[0], bb.minY - e.posY + pos[1], bb.minZ - e.posZ + pos[2], bb.maxX - e.posX + pos[0], bb.maxY - e.posY + pos[1], bb.maxZ - e.posZ + pos[2]);

		int draw = 7;
		cubeFill(draw, aa.minX, aa.minY, aa.minZ, aa.maxX, aa.maxY, aa.maxZ, new float[] {0, -e.rotationYaw, 0}, new boolean[] {true, true, true, true, true, true});

		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}
	
	public static void boxOutline(Entity e, int color) {
		float var11 = (float) (color >> 24 & 255) / 255.0F;
		float var6 = (float) (color >> 16 & 255) / 255.0F;
		float var7 = (float) (color >> 8 & 255) / 255.0F;
		float var8 = (float) (color & 255) / 255.0F;
		Tessellator var9 = Tessellator.getInstance();
		WorldRenderer t = var9.getWorldRenderer();

		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(1f);

		GlStateManager.color(var6, var7, var8, var11);

		double[] pos = RenderUtil.entityRenderPos(e);

		AxisAlignedBB bb = e.getEntityBoundingBox();
		AxisAlignedBB aa = new AxisAlignedBB(bb.minX - e.posX + pos[0], bb.minY - e.posY + pos[1], bb.minZ - e.posZ + pos[2], bb.maxX - e.posX + pos[0], bb.maxY - e.posY + pos[1], bb.maxZ - e.posZ + pos[2]);

		int draw = 1;
		cubeOutline(draw, aa.minX, aa.minY, aa.minZ, aa.maxX, aa.maxY, aa.maxZ, new float[] {0, -e.rotationYaw, 0}, new boolean[] {true, true, true, true, true, true});

		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void boxFilled(Entity e, int outline, int fill) {
		box(e, outline);
		boxOutline(e, fill);
	}
	
	public static void block(BlockPos bp, int color, int drawMode, boolean[] faces) {
		float var11 = (float) (color >> 24 & 255) / 255.0F;
		float var6 = (float) (color >> 16 & 255) / 255.0F;
		float var7 = (float) (color >> 8 & 255) / 255.0F;
		float var8 = (float) (color & 255) / 255.0F;

		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(1f);

		GlStateManager.color(var6, var7, var8, var11);

		AxisAlignedBB aa = new AxisAlignedBB(bp, bp.offset(EnumFacing.SOUTH).offset(EnumFacing.EAST).offset(EnumFacing.UP)).offset(-mc.getRenderManager().viewerPosX, -mc.getRenderManager().viewerPosY, -mc.getRenderManager().viewerPosZ);

		if (drawMode == 7) {
			cubeFill(drawMode, aa.minX, aa.minY, aa.minZ, aa.maxX, aa.maxY, aa.maxZ, new float[] {0, 0, 0}, faces);
		} else {
			cubeOutline(drawMode, aa.minX, aa.minY, aa.minZ, aa.maxX, aa.maxY, aa.maxZ, new float[] {0, 0, 0}, faces);
		}

		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}
	
	public static void blockBB(Block b, BlockPos bp, double expand, int color, int drawMode, boolean[] faces) {
		float var11 = (float) (color >> 24 & 255) / 255.0F;
		float var6 = (float) (color >> 16 & 255) / 255.0F;
		float var7 = (float) (color >> 8 & 255) / 255.0F;
		float var8 = (float) (color & 255) / 255.0F;

		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(1f);

		GlStateManager.color(var6, var7, var8, var11);

		AxisAlignedBB aa = b.getSelectedBoundingBox(mc.theWorld, bp).offset(-mc.getRenderManager().viewerPosX, -mc.getRenderManager().viewerPosY, -mc.getRenderManager().viewerPosZ).expand(expand, expand, expand);

		if (drawMode == 7) {
			cubeFill(drawMode, aa.minX, aa.minY, aa.minZ, aa.maxX, aa.maxY, aa.maxZ, new float[] {0, 0, 0}, faces);
		} else {
			cubeOutline(drawMode, aa.minX, aa.minY, aa.minZ, aa.maxX, aa.maxY, aa.maxZ, new float[] {0, 0, 0}, faces);
		}

		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}
	
	public static void blockBox(BlockPos bp, int outline, int fill, boolean[] faces) {
		block(bp, fill, 1, faces);
		block(bp, outline, 7, faces);
	}

	public static void blockBoxBB(Block b, BlockPos bp, double expand, int outline, int fill, boolean[] faces) {
		blockBB(b, bp, expand, fill, 1, faces);
		blockBB(b, bp, expand, outline, 7, faces);
	}
	
	public static void bbox(Vec3 pos1, Vec3 pos2, int outline, int fill) {
		float ovar11 = (float) (outline >> 24 & 255) / 255.0F;
		float ovar6 = (float) (outline >> 16 & 255) / 255.0F;
		float ovar7 = (float) (outline >> 8 & 255) / 255.0F;
		float ovar8 = (float) (outline & 255) / 255.0F;
		
		float fvar11 = (float) (fill >> 24 & 255) / 255.0F;
		float fvar6 = (float) (fill >> 16 & 255) / 255.0F;
		float fvar7 = (float) (fill >> 8 & 255) / 255.0F;
		float fvar8 = (float) (fill & 255) / 255.0F;

		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(1f);

		double[] p1 = RenderUtil.renderPos(pos1);
		double[] p2 = RenderUtil.renderPos(pos2);
		
		GlStateManager.color(fvar6, fvar7, fvar8, fvar11);
		cubeFill(7, p1[0], p1[1], p1[2], p2[0]+1, p2[1]+1, p2[2]+1, new float[] {0, 0, 0}, new boolean[] {true, true, true, true, true, true});
		
		GlStateManager.color(ovar6, ovar7, ovar8, ovar11);
		cubeOutline(1, p1[0], p1[1], p1[2], p2[0]+1, p2[1]+1, p2[2]+1, new float[] {0, 0, 0}, new boolean[] {true, true, true, true, true, true});

		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}
	
	public static void tracer(Entity from, Entity to, int color) {
		GlStateManager.loadIdentity();
		mc.entityRenderer.orientCamera(mc.timer.renderPartialTicks);
		
		float var11 = (float) (color >> 24 & 255) / 255.0F;
		float var6 = (float) (color >> 16 & 255) / 255.0F;
		float var7 = (float) (color >> 8 & 255) / 255.0F;
		float var8 = (float) (color & 255) / 255.0F;
		Tessellator var9 = Tessellator.getInstance();
		WorldRenderer t = var9.getWorldRenderer();

		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(1f);

		GlStateManager.color(var6, var7, var8, var11);

		double[] pos = RenderUtil.entityRenderPos(to);
		
		double x = pos[0];
		double y = pos[1]+to.getEyeHeight();
		double z = pos[2];

		int draw = 1;
		t.startDrawing(draw);
		t.addVertex(0, mc.thePlayer.getEyeHeight(), 0);
		t.addVertex(x, y, z);
		var9.draw();

		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}
	
	public static void line(Vec3 from, Vec3 to, int color, float width) {
		GlStateManager.loadIdentity();
		mc.entityRenderer.orientCamera(mc.timer.renderPartialTicks);
		
		float var11 = (float) (color >> 24 & 255) / 255.0F;
		float var6 = (float) (color >> 16 & 255) / 255.0F;
		float var7 = (float) (color >> 8 & 255) / 255.0F;
		float var8 = (float) (color & 255) / 255.0F;
		Tessellator var9 = Tessellator.getInstance();
		WorldRenderer t = var9.getWorldRenderer();

		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(width);

		GlStateManager.color(var6, var7, var8, var11);

		Minecraft mc = Minecraft.getMinecraft();

		double[] pf = RenderUtil.renderPos(from);
		double[] pt = RenderUtil.renderPos(to);
		
		int draw = 1;
		t.startDrawing(draw);
		t.addVertex(pf[0], pf[1], pf[2]);
		t.addVertex(pt[0], pt[1], pt[2]);
		var9.draw();

		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}
	
	public static void renderTrail(double x, double y, double z, double mx, double my, double mz, float drag, float waterDrag, float grav, int color, boolean renderBlockHit) {
		for(int j = 0; j < 200; j++) {
			double oldX = x;
			double oldY = y;
			double oldZ = z;
			
			GlStateManager.enableDepth();
			GlStateManager.depthMask(true);
			Render.line(new Vec3(x, y, z), new Vec3(x+mx, y+my, z+mz), color, 2f);
			GlStateManager.disableDepth();
			GlStateManager.depthMask(false);
			
			x+= mx;
			y+= my;
			z+= mz;

			Vec3 op = new Vec3(oldX, oldY, oldZ);
			Vec3 np = new Vec3(x, y, z);
			
			MovingObjectPosition mop = mc.theWorld.rayTraceBlocks(op, np, false, true, true);
			if (mop != null) {
				if (mop.typeOfHit.equals(mop.typeOfHit.BLOCK) && renderBlockHit) {
					float a = (float) (color >> 24 & 255) / 255.0F;
					Render.blockBox(mop.getBlockPos(), Util.reAlpha(color, a/2), Util.reAlpha(color, a), new boolean[] {true, true, true, true, true, true});
					break;
				}
			}
			
			float var25 = drag;
			float var13 = grav;
			
			Vector3d pos = new Vector3d(x, y, z);
			float size = 0.25f;
			if (mc.theWorld.isAABBInMaterial(new AxisAlignedBB(pos.x+size, pos.y+size, pos.z+size, pos.x-size, pos.y-size, pos.z-size), Material.water))
            {
				if (waterDrag == 0f) {
					break;
				}
                var25 = waterDrag;
            }
			
			mx *= var25;
			my *= var25;
			mz *= var25;

			my-= var13;
		}
	}

	public static void cubeFill(int draw, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float[] rotation, boolean[] faces) {
		Tessellator var9 = Tessellator.getInstance();
		WorldRenderer t = var9.getWorldRenderer();

		GlStateManager.pushMatrix();
		
		GlStateManager.translate(minX+((maxX-minX)/2), minY, minZ+((maxZ-minZ)/2));
		GlStateManager.rotate(rotation[0], 1, 0, 0);
		GlStateManager.rotate(rotation[1], 0, 1, 0);
		GlStateManager.rotate(rotation[2], 0, 0, 1);
		
		maxX-= minX;
		maxY-= minY;
		maxZ-= minZ;
		
		maxX/= 2;
		maxZ/= 2;
		
		minX = -maxX;
		minY = 0;
		minZ = -maxZ;
		
		double[][][] array = new double[][][] {
				{{minX, minY, minZ}, {minX, maxY, minZ}, {maxX, maxY, minZ}, {maxX, minY, minZ}},//north
				{{minX, minY, maxZ}, {maxX, minY, maxZ}, {maxX, maxY, maxZ}, {minX, maxY, maxZ}},//south
				{{maxX, minY, minZ}, {maxX, maxY, minZ}, {maxX, maxY, maxZ}, {maxX, minY, maxZ}},//east
				{{minX, minY, minZ}, {minX, minY, maxZ}, {minX, maxY, maxZ}, {minX, maxY, minZ}},//west
				{{minX, maxY, minZ}, {minX, maxY, maxZ}, {maxX, maxY, maxZ}, {maxX, maxY, minZ}},//up
				{{minX, minY, minZ}, {maxX, minY, minZ}, {maxX, minY, maxZ}, {minX, minY, maxZ}},//down
			};

		t.startDrawing(draw);
		for(int i = 0; i < array.length; i++) {
			if (faces[i]) {
				for(int j = 0; j < array[i].length; j++) {
					t.addVertex(array[i][j][0], array[i][j][1], array[i][j][2]);
				}
				for(int j = array[i].length-1; j >= 0; j--) {
					t.addVertex(array[i][j][0], array[i][j][1], array[i][j][2]);
				}
			}
		}
		var9.draw();
		
		GlStateManager.popMatrix();
	}
	
	public static void cubeOutline(int draw, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float[] rotation, boolean[] faces) {
		Tessellator var9 = Tessellator.getInstance();
		WorldRenderer t = var9.getWorldRenderer();

		GlStateManager.pushMatrix();
		
		GlStateManager.translate(minX+((maxX-minX)/2), minY, minZ+((maxZ-minZ)/2));
		GlStateManager.rotate(rotation[0], 1, 0, 0);
		GlStateManager.rotate(rotation[1], 0, 1, 0);
		GlStateManager.rotate(rotation[2], 0, 0, 1);
		
		maxX-= minX;
		maxY-= minY;
		maxZ-= minZ;
		
		maxX/= 2;
		maxZ/= 2;
		
		minX = -maxX;
		minY = 0;
		minZ = -maxZ;
		
		//n s e w t b
		boolean[] vertices = new boolean[] {
				(!faces[0] || !faces[2]?false:true),
				(!faces[0] || !faces[3]?false:true),
				(!faces[1] || !faces[2]?false:true),
				(!faces[1] || !faces[3]?false:true),
				(!faces[0] || !faces[5]?false:true),
				(!faces[1] || !faces[5]?false:true),
				(!faces[2] || !faces[5]?false:true),
				(!faces[3] || !faces[5]?false:true),
				(!faces[0] || !faces[4]?false:true),
				(!faces[1] || !faces[4]?false:true),
				(!faces[2] || !faces[4]?false:true),
				(!faces[3] || !faces[4]?false:true),
			};

		double[][][] array = new double[][][] {
				{{maxX, minY, minZ}, {maxX, maxY, minZ}},//northeast bottom->top
				{{minX, minY, minZ}, {minX, maxY, minZ}},//northwest bottom->top
				{{maxX, minY, maxZ}, {maxX, maxY, maxZ}},//southeast bottom->top
				{{minX, minY, maxZ}, {minX, maxY, maxZ}},//southwest bottom->top
				{{minX, minY, minZ}, {maxX, minY, minZ}},//bottom north
				{{minX, minY, maxZ}, {maxX, minY, maxZ}},//bottom south
				{{maxX, minY, minZ}, {maxX, minY, maxZ}},//bottom east
				{{minX, minY, minZ}, {minX, minY, maxZ}},//bottom west
				{{minX, maxY, minZ}, {maxX, maxY, minZ}},//top north
				{{minX, maxY, maxZ}, {maxX, maxY, maxZ}},//top south
				{{maxX, maxY, minZ}, {maxX, maxY, maxZ}},//top east
				{{minX, maxY, minZ}, {minX, maxY, maxZ}},//top west
			};

		t.startDrawing(draw);
		for(int i = 0; i < array.length; i++) {
			if (vertices[i]) {
				for(int j = 0; j < array[i].length; j++) {
					t.addVertex(array[i][j][0], array[i][j][1], array[i][j][2]);
				}
			}
		}
		var9.draw();
		
		GlStateManager.popMatrix();
	}
	
	public static void render3DNameTag(String text, double x, double y, double z, double wx, double wy, double wz) {
    	if (ModuleManager.get("nametags").active) {
    		String p_147906_2_ = text;
            
    		float distance = (float)Minecraft.getMinecraft().thePlayer.getDistance(wx, wy, wz);
            float scaleFactor = distance < 10.0F ? 0.9F : distance / 11.11F;
            int color = 16777215;
            float height = 0.0F;

			if (distance >= 10.0F) {
				height = (float) (height + (distance / 40.0F - 0.25D));
			}
			
            FontRenderer var12 = mc.fontRendererObj;
            if (var12 == null) {
            	return;
            }
            float var13 = 1.6F;
            float var14 = 0.016666668F * var13;
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x + 0.0F, (float)y + 0.5F, (float)z);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(-var14*scaleFactor, -var14*scaleFactor, var14*scaleFactor);
            
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            Tessellator var15 = Tessellator.getInstance();
            WorldRenderer var16 = var15.getWorldRenderer();

            GlStateManager.disableTexture2D();

            int var18 = var12.getStringWidth(p_147906_2_) / 2;
            float w = var18;
            float h = var12.FONT_HEIGHT;
            float offY = 0;
            
            Draw.rectBorder(-w-4, -4+offY, w+4, h+3+offY, 0xFF010101);
            Draw.rect(-w-3, -3+offY, w+3, h+2+offY, 0xAA010101);
            
            GlStateManager.enableTexture2D();
            int co = -1;
            
            var12.drawString(p_147906_2_, -var12.getStringWidth(p_147906_2_) / 2, 0, co);

            GlStateManager.disableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            
            return;
    	}
    	
        FontRenderer var12 = mc.fontRendererObj;
        
        String var11 = text;
        float var120 = 0.02666667F;
        GlStateManager.alphaFunc(516, 0.1F);

		float distance = (float)Minecraft.getMinecraft().thePlayer.getDistance(wx, wy, wz);
        float scaleFactor = distance < 10.0F ? 0.9F : distance / 11.11F;
        
        float var13 = 1.6F;
        float var14 = 0.016666668F * var13;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x + 0.0F, (float)y + 0.5F, (float)z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-var14*scaleFactor, -var14*scaleFactor, var14*scaleFactor);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator var15 = Tessellator.getInstance();
        WorldRenderer var16 = var15.getWorldRenderer();
        byte var17 = 0;

        GlStateManager.disableTexture2D();
        var16.startDrawingQuads();
        int var18 = var12.getStringWidth(text) / 2;
        var16.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
        var16.addVertex((double)(-var18 - 1), (double)(-1 + var17), 0.0D);
        var16.addVertex((double)(-var18 - 1), (double)(8 + var17), 0.0D);
        var16.addVertex((double)(var18 + 1), (double)(8 + var17), 0.0D);
        var16.addVertex((double)(var18 + 1), (double)(-1 + var17), 0.0D);
        var15.draw();
        GlStateManager.enableTexture2D();
        var12.drawString(text, -var12.getStringWidth(text) / 2, var17, 553648127);
        var12.drawString(text, -var12.getStringWidth(text) / 2, var17, -1);
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }
}