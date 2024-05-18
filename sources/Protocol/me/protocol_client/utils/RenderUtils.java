package me.protocol_client.utils;

import java.awt.Color;

import me.protocol_client.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import org.lwjgl.opengl.GL11;

public class RenderUtils {
	
	  public static void drawSideGradientRect(float x, float y, float x2, float y2, int col1, int col2)
	  {
	    float f = (col1 >> 24 & 0xFF) / 255.0F;
	    float f1 = (col1 >> 16 & 0xFF) / 255.0F;
	    float f2 = (col1 >> 8 & 0xFF) / 255.0F;
	    float f3 = (col1 & 0xFF) / 255.0F;
	    
	    float f4 = (col2 >> 24 & 0xFF) / 255.0F;
	    float f5 = (col2 >> 16 & 0xFF) / 255.0F;
	    float f6 = (col2 >> 8 & 0xFF) / 255.0F;
	    float f7 = (col2 & 0xFF) / 255.0F;
	    
	    GL11.glEnable(3042);
	    GL11.glDisable(3553);
	    GL11.glBlendFunc(770, 771);
	    GL11.glShadeModel(7425);
	    
	    GL11.glPushMatrix();
	    GL11.glBegin(7);
	    GL11.glColor4f(f1, f2, f3, f);
	    GL11.glVertex2d(x2, y);
	    
	    GL11.glColor4f(f5, f6, f7, f4);
	    GL11.glVertex2d(x, y);
	    GL11.glVertex2d(x, y2);
	    
	    GL11.glColor4f(f1, f2, f3, f);
	    GL11.glVertex2d(x2, y2);
	    GL11.glEnd();
	    GL11.glPopMatrix();
	    
	    GL11.glEnable(3553);
	    GL11.glDisable(3042);
	    GL11.glShadeModel(7424);
	  }
	  public static void drawCircle(float cx, float cy, float r, int num_segments, int c)
	  {
	    GL11.glPushMatrix();
	    r *= 2.0F;
	    cx *= 2.0F;
	    cy *= 2.0F;
	    float f = (c >> 24 & 0xFF) / 255.0F;
	    float f1 = (c >> 16 & 0xFF) / 255.0F;
	    float f2 = (c >> 8 & 0xFF) / 255.0F;
	    float f3 = (c & 0xFF) / 255.0F;
	    float theta = (float)(6.2831852D / num_segments);
	    float p = (float)Math.cos(theta);
	    float s = (float)Math.sin(theta);
	    
	    float x = r;
	    float y = 0.0F;
	    enableGL2D();
	    GL11.glScalef(0.5F, 0.5F, 0.5F);
	    GL11.glColor4f(f1, f2, f3, f);
	    GL11.glLineWidth(1.5f);
	    GL11.glBegin(2);
	    for (int ii = 0; ii < num_segments; ii++)
	    {
	      GL11.glVertex2f(x + cx, y + cy);
	      
	      float t = x;
	      x = p * x - s * y;
	      y = s * t + p * y;
	    }
	    GL11.glEnd();
	    GL11.glScalef(2.0F, 2.0F, 2.0F);
	    disableGL2D();
	    GL11.glPopMatrix();
	  }
	  
	  public static void drawFilledCircle(float cx, float cy, float r, int num_segments, int c)
	  {
	    GL11.glPushMatrix();
	    r *= 2.0F;
	    cx *= 2.0F;
	    cy *= 2.0F;
	    float f = (c >> 24 & 0xFF) / 255.0F;
	    float f1 = (c >> 16 & 0xFF) / 255.0F;
	    float f2 = (c >> 8 & 0xFF) / 255.0F;
	    float f3 = (c & 0xFF) / 255.0F;
	    float theta = (float)(6.2831852D / num_segments);
	    float p = (float)Math.cos(theta);
	    float s = (float)Math.sin(theta);
	    
	    float x = r;
	    float y = 0.0F;
	    enableGL2D();
	    GL11.glScalef(0.5F, 0.5F, 0.5F);
	    GL11.glColor4f(f1, f2, f3, f);
	    GL11.glLineWidth(1.5f);
	    GL11.glBegin(6);
	    for (int ii = 0; ii < num_segments; ii++)
	    {
	      GL11.glVertex2f(x + cx, y + cy);
	      
	      float t = x;
	      x = p * x - s * y;
	      y = s * t + p * y;
	    }
	    GL11.glEnd();
	    GL11.glScalef(2.0F, 2.0F, 2.0F);
	    disableGL2D();
	    GL11.glPopMatrix();
	  }
	  public static void ProtocolEntityBox(AxisAlignedBB aa)
		{
			Tessellator ts = Tessellator.getInstance();
			WorldRenderer wr = ts.getWorldRenderer();
			wr.startDrawing(3);
			wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
			wr.addVertex(aa.minX, aa.maxY, aa.minZ);
			wr.addVertex(aa.minX, aa.minY, aa.minZ);
			wr.addVertex(aa.maxX, aa.minY, aa.minZ);
			wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
			ts.draw();
			wr.startDrawing(3);
			wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
			wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
			wr.addVertex(aa.minX, aa.minY, aa.maxZ);
			wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
			wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
			ts.draw();
			wr.startDrawing(3);
			wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
			wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
			ts.draw();
			wr.startDrawing(3);
			wr.addVertex(aa.maxX, aa.minY, aa.minZ);
			wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
			ts.draw();
			wr.startDrawing(3);
			wr.addVertex(aa.minX, aa.minY, aa.minZ);
			wr.addVertex(aa.minX, aa.minY, aa.maxZ);
			ts.draw();
			wr.startDrawing(3);
			wr.addVertex(aa.minX, aa.maxY, aa.minZ);
			wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
			ts.draw();
//			wr.startDrawing(3);
//			wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
//			wr.addVertex(aa.minX, aa.minY, aa.minZ);
//			ts.draw();
//			wr.startDrawing(3);
//			wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
//			wr.addVertex(aa.maxX, aa.minY, aa.minZ);
//			ts.draw();
//			wr.startDrawing(3);
//			wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
//			wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
//			ts.draw();
//			wr.startDrawing(3);
//			wr.addVertex(aa.maxX, aa.minY, aa.minZ);
//			wr.addVertex(aa.minX, aa.minY, aa.maxZ);
//			ts.draw();
		}
		public static void ProtocolChestBox(AxisAlignedBB aa)
		{
			Tessellator ts = Tessellator.getInstance();
			WorldRenderer wr = ts.getWorldRenderer();
			wr.startDrawing(3);
			wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
			wr.addVertex(aa.minX, aa.maxY, aa.minZ);
			wr.addVertex(aa.minX, aa.minY, aa.minZ);
			wr.addVertex(aa.maxX, aa.minY, aa.minZ);
			wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
			ts.draw();
			wr.startDrawing(3);
			wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
			wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
			wr.addVertex(aa.minX, aa.minY, aa.maxZ);
			wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
			wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
			ts.draw();
			wr.startDrawing(3);
			wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
			wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
			ts.draw();
			wr.startDrawing(3);
			wr.addVertex(aa.maxX, aa.minY, aa.minZ);
			wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
			ts.draw();
			wr.startDrawing(3);
			wr.addVertex(aa.minX, aa.minY, aa.minZ);
			wr.addVertex(aa.minX, aa.minY, aa.maxZ);
			ts.draw();
			wr.startDrawing(3);
			wr.addVertex(aa.minX, aa.maxY, aa.minZ);
			wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
			ts.draw();
		}
		public static void ProtocolBoundingBox(AxisAlignedBB aa) {
			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldRenderer = tessellator.getWorldRenderer();
			worldRenderer.startDrawing(3);
			worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
			worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
			worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
			worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
			worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
			tessellator.draw();
			worldRenderer.startDrawing(3);
			worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
			worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
			worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
			worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
			worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
			tessellator.draw();
			worldRenderer.startDrawing(1);
			worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
			worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
			worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
			worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
			worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
			worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
			worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
			worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
			tessellator.draw();
		}
		
		public static void drawBoundingBox(AxisAlignedBB aa)  {
			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldRenderer = tessellator.getWorldRenderer();
			worldRenderer.startDrawingQuads();
			worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
			worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
			worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
			worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
			worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
			worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
			worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
			worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
			tessellator.draw();
			worldRenderer.startDrawingQuads();
			worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
			worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
			worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
			worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
			worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
			worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
			worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
			worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
			tessellator.draw();
			worldRenderer.startDrawingQuads();
			worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
			worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
			worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
			worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
			worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
			worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
			worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
			worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
			tessellator.draw();
			worldRenderer.startDrawingQuads();
			worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
			worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
			worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
			worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
			worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
			worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
			worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
			worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
			tessellator.draw();
			worldRenderer.startDrawingQuads();
			worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
			worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
			worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
			worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
			worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
			worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
			worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
			worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
			tessellator.draw();
			worldRenderer.startDrawingQuads();
			worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
			worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
			worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
			worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
			worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
			worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
			worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
			worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
			tessellator.draw();
		}
	
	public static void drawBlockESP(double x, double y, double z, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWidth) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
		GL11.glLineWidth(lineWidth);
		GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
		ProtocolBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	public static RenderPlayer field_177208_a;
	public static void drawEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith, EntityLivingBase entity) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GlStateManager.translate(x, y, z);
        GlStateManager.rotate(-entity.rotationYaw, 0.0F, entity.height, 0.0F);
        GlStateManager.translate(-x, -y, -z);
		GL11.glColor4f(red, green, blue, 0.3001f);
		double eheight = (entity.getHealth() / 10);
		double mheight = (entity.getMaxHealth() / 10);
		if(entity instanceof EntityPlayer){
		FilledESP(new AxisAlignedBB(x - width + 0.20, y, z - width + 0.20, x + width - 0.20, y + eheight, z + width - 0.20));
		}else{
			FilledESP(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
		}
		GL11.glLineWidth(0.8F);
		GL11.glColor4f(red, green, blue, 1F);
		if(entity instanceof EntityPlayer){
		ProtocolEntityBox(new AxisAlignedBB(x - width + 0.20, y, z - width + 0.20, x + width - 0.20, y + mheight, z + width - 0.20));
		}else{
			ProtocolEntityBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
		}
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	public static void setColor(Color color) {
		GL11.glColor4d(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
	}
	  public static void enableGL2D()
	  {
	    GL11.glDisable(2929);
	    GL11.glEnable(3042);
	    GL11.glDisable(3553);
	    GL11.glBlendFunc(770, 771);
	    GL11.glDepthMask(true);
	    GL11.glEnable(2848);
	    GL11.glHint(3154, 4354);
	    GL11.glHint(3155, 4354);
	  }
	  
	  public static void disableGL2D()
	  {
	    GL11.glEnable(3553);
	    GL11.glDisable(3042);
	    GL11.glEnable(2929);
	    GL11.glDisable(2848);
	    GL11.glHint(3154, 4352);
	    GL11.glHint(3155, 4352);
	  }
	  
	public static void drawTracerLine(double x, double y, double z, float red, float green, float blue, float alpha, float lineWidth) {
		boolean temp = Wrapper.mc().gameSettings.viewBobbing;
	    Wrapper.mc().gameSettings.viewBobbing = false;
	    Wrapper.mc().entityRenderer.setupCameraTransform(Wrapper.mc().timer.renderPartialTicks, 2);
	    Wrapper.mc().gameSettings.viewBobbing = temp;
		GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(1);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(2);
        GL11.glVertex3d(0.0D, 0.0D + Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0D);
        GL11.glVertex3d(x, y, z);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
	}
	public static void drawChestESP(BlockPos blockPos, boolean echest)
	{
		double d = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double d1 = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double d2 = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
	if(echest){
		GL11.glColor4d(0.5f, 0f, 1f, 0.1001f);
	}else{
	GL11.glColor4d(1, 0.5f, 0, 0.1001F);
	}
	FilledESP(new AxisAlignedBB(d + 0.06, d1, d2  + 0.06, d + 0.94, d1 + 0.88, d2 + 0.94));
	if(echest){
		GL11.glColor4d(0.5f, 0f, 1f, 1f);
	}else{
	GL11.glColor4d(1f, 0.5f, 0f, 1f);
	}
	GL11.glLineWidth(1f);
	ProtocolEntityBox(new AxisAlignedBB(d + 0.06, d1, d2  + 0.06, d + 0.94, d1 + 0.88, d2 + 0.94));
	GL11.glDisable(GL11.GL_LINE_SMOOTH);
	GL11.glEnable(GL11.GL_TEXTURE_2D);
	GL11.glEnable(GL11.GL_DEPTH_TEST);
	GL11.glDepthMask(true);
	GL11.glDisable(GL11.GL_BLEND);
	GL11.glPopMatrix();
	}
	public static void FilledESP(AxisAlignedBB aa)
	{
		Tessellator ts = Tessellator.getInstance();
		WorldRenderer wr = ts.getWorldRenderer();
		
		wr.startDrawingQuads();
		wr.addVertex(aa.minX, aa.minY, aa.minZ);
		wr.addVertex(aa.minX, aa.maxY, aa.minZ);
		wr.addVertex(aa.maxX, aa.minY, aa.minZ);
		wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
		wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
		wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		wr.addVertex(aa.minX, aa.minY, aa.maxZ);
		wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
		ts.draw();
		wr.startDrawingQuads();
		wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
		wr.addVertex(aa.maxX, aa.minY, aa.minZ);
		wr.addVertex(aa.minX, aa.maxY, aa.minZ);
		wr.addVertex(aa.minX, aa.minY, aa.minZ);
		wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
		wr.addVertex(aa.minX, aa.minY, aa.maxZ);
		wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
		ts.draw();
		wr.startDrawingQuads();
		wr.addVertex(aa.minX, aa.maxY, aa.minZ);
		wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
		wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
		wr.addVertex(aa.minX, aa.maxY, aa.minZ);
		wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
		wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
		ts.draw();
		wr.startDrawingQuads();
		wr.addVertex(aa.minX, aa.minY, aa.minZ);
		wr.addVertex(aa.maxX, aa.minY, aa.minZ);
		wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
		wr.addVertex(aa.minX, aa.minY, aa.maxZ);
		wr.addVertex(aa.minX, aa.minY, aa.minZ);
		wr.addVertex(aa.minX, aa.minY, aa.maxZ);
		wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
		wr.addVertex(aa.maxX, aa.minY, aa.minZ);
		ts.draw();
		wr.startDrawingQuads();
		wr.addVertex(aa.minX, aa.minY, aa.minZ);
		wr.addVertex(aa.minX, aa.maxY, aa.minZ);
		wr.addVertex(aa.minX, aa.minY, aa.maxZ);
		wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
		wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
		wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		wr.addVertex(aa.maxX, aa.minY, aa.minZ);
		wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
		ts.draw();
		wr.startDrawingQuads();
		wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
		wr.addVertex(aa.minX, aa.minY, aa.maxZ);
		wr.addVertex(aa.minX, aa.maxY, aa.minZ);
		wr.addVertex(aa.minX, aa.minY, aa.minZ);
		wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
		wr.addVertex(aa.maxX, aa.minY, aa.minZ);
		wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
		ts.draw();
	}
}
