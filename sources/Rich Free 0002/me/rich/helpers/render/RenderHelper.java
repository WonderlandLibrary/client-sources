package me.rich.helpers.render;

import java.awt.Color;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

import me.rich.helpers.math.MathHelper;

import static org.lwjgl.opengl.GL11.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class RenderHelper {
    protected static float zLevel;
	public static Minecraft mc = Minecraft.getMinecraft();
	private static final Frustum frustrum = new Frustum();
	private static final FloatBuffer COLOR_BUFFER = GLAllocation.createDirectFloatBuffer(4);
    private static final Vec3d LIGHT0_POS = (new Vec3d(0.20000000298023224D, 1.0D, -0.699999988079071D)).normalize();
    private static final Vec3d LIGHT1_POS = (new Vec3d(-0.20000000298023224D, 1.0D, 0.699999988079071D)).normalize();
	public static void drawRoundedRect(float left, float top, float right, float bottom, int smooth, Color color) {
		Gui.drawRect(((int) left + smooth), (int) top, ((int) right - smooth), (int) bottom, color.getRGB());
		Gui.drawRect((int) left, ((int) top + smooth), (int) right, ((int) bottom - smooth), color.getRGB());
		drawFilledCircle((int) left + smooth, (int) top + smooth, smooth, color);
		drawFilledCircle((int) right - smooth, (int) top + smooth, smooth, color);
		drawFilledCircle((int) right - smooth, (int) bottom - smooth, smooth, color);
		drawFilledCircle((int) left + smooth, (int) bottom - smooth, smooth, color);
	}
	public static void dispose() {
        GL11.glDisable(GL11.GL_STENCIL_TEST);
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
    }
	
	public static void putVertex3d(Vec3d vec) {
		GL11.glVertex3d(vec.xCoord, vec.yCoord, vec.zCoord);
	}
	
	public static Vec3d getRenderPos(double x, double y, double z) {

		x = x - mc.getRenderManager().renderPosX;
		y = y - mc.getRenderManager().renderPosY;
		z = z - mc.getRenderManager().renderPosZ;

		return new Vec3d(x, y, z);
	}
	
	 public static void drawBorderedRect(double x, double y, double width, double height, double lineSize, int borderColor, int color) {
	        Gui.drawRect(x, y, x + width, y + height, color);
	        Gui.drawRect(x, y, x + width, y + lineSize, borderColor);
	        Gui.drawRect(x, y, x + lineSize, y + height, borderColor);
	        Gui.drawRect(x + width, y, x + width - lineSize, y + height, borderColor);
	        Gui.drawRect(x, y + height, x + width, y + height - lineSize, borderColor);
	    }
	 public static void write(boolean renderClipLayer) {
	        checkSetupFBO();
	        GL11.glClearStencil((int)0);
	        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
	        GL11.glEnable(GL11.GL_STENCIL_TEST);
	        GL11.glStencilFunc(GL11.GL_ALWAYS, (int)1, (int)65535);
	        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
	        if(!renderClipLayer){
	            GlStateManager.colorMask(false, false, false, false);
	        }
	    }
	 public static final void color(Color color) {
	        if (color == null)
	            color = Color.white;
	        color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
	    }
	 public static final void color(double red, double green, double blue, double alpha) {
	        GL11.glColor4d(red, green, blue, alpha);
	    }
	    public static void erase(boolean invert) {
	        GL11.glStencilFunc(invert ? GL11.GL_EQUAL : GL11.GL_NOTEQUAL, (int)1, (int)65535);
	        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
	        GlStateManager.colorMask(true, true, true, true);
	        GlStateManager.enableAlpha();
	        GlStateManager.enableBlend();
	        GL11.glAlphaFunc( GL11.GL_GREATER, 0.0f );
	    }

	    public static void checkSetupFBO() {
	        Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
	        if (fbo != null && fbo.depthBuffer > -1) {
	            setupFBO(fbo);
	            fbo.depthBuffer = -1;
	        }
	    }
	    public static Color setAlpha(Color color, int alpha) {
	        alpha = (int) MathHelper.clamp(alpha, 0, 255);
	        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
	    }

	    public static void setupFBO(Framebuffer fbo) {
	        EXTFramebufferObject.glDeleteRenderbuffersEXT((int)fbo.depthBuffer);
	        int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
	        EXTFramebufferObject.glBindRenderbufferEXT((int)36161, (int)stencil_depth_buffer_ID);
	        EXTFramebufferObject.glRenderbufferStorageEXT((int)36161, (int)34041, (int)Minecraft.getMinecraft().displayWidth, (int)Minecraft.getMinecraft().displayHeight);
	        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36128, (int)36161, (int)stencil_depth_buffer_ID);
	        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36096, (int)36161, (int)stencil_depth_buffer_ID);
	    }
	 public static void drawLinesAroundPlayer(Entity entity, double radius, float partialTicks, int points, float width, int color) {
	        GL11.glPushMatrix();
	        RenderHelper.enableGL2D();
	        GL11.glDisable(3553);
	        GL11.glEnable(2848);
	        GL11.glHint(3154, 4354);
	        GL11.glDisable(2929);
	        GL11.glLineWidth(width);
	        GL11.glEnable(3042);
	        GL11.glBlendFunc(770, 771);
	        GL11.glDisable(2929);
	        GL11.glBegin(3);
	        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
	        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks - renderManager.viewerPosX;
	        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks - renderManager.viewerPosY;
	        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks - renderManager.viewerPosZ;
	        RenderHelper.setColor(color);
	        for (int i = 0; i <= points; ++i) {
	            GL11.glVertex3d(x + radius * Math.cos((double)i * (Math.PI * 2) / (double)points), y, z + radius * Math.sin((double)i * (Math.PI * 2) / (double)points));
	        }
	        GL11.glEnd();
	        GL11.glDepthMask(true);
	        GL11.glDisable(3042);
	        GL11.glEnable(2929);
	        GL11.glDisable(2848);
	        GL11.glEnable(2929);
	        GL11.glEnable(3553);
	        RenderHelper.disableGL2D();
	        GL11.glPopMatrix();
	    }
	 
	 public static void drawLinesAroundPlayer2(Entity entity, double radius, float partialTicks, int points, float width, int color) {
	        GL11.glPushMatrix();
	        RenderHelper.enableGL2D();
	        GL11.glDisable(3553);
	        GL11.glEnable(2848);
	        GL11.glHint(3154, 4354);
	        GL11.glDisable(2929);
	        GL11.glLineWidth(width);
	        GL11.glEnable(3042);
	        GL11.glBlendFunc(770, 771);
	        GL11.glDisable(2929);
	        GL11.glBegin(3);
	        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
	        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks - renderManager.viewerPosX;
	        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks - renderManager.viewerPosY;
	        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks - renderManager.viewerPosZ;
	        RenderHelper.setColor(color);
	        for (int i = 0; i <= points; ++i) {
	            GL11.glVertex3d(x + radius * Math.cos((double)i * (Math.PI * 2) / (double)points), y, z + radius * Math.sin((double)i * (Math.PI * 2) / (double)points));
	        }
	        GL11.glEnd();
	        GL11.glDepthMask(true);
	        GL11.glDisable(3042);
	        GL11.glEnable(2929);
	        GL11.glDisable(2848);
	        GL11.glEnable(2929);
	        GL11.glEnable(3553);
	        RenderHelper.disableGL2D();
	        GL11.glPopMatrix();
	    }

	
    public static int getHealthColor(EntityLivingBase player) {
        float f = player.getHealth();
        float f1 = player.getMaxHealth();
        float f2 = Math.max(0.0F, Math.min(f, f1) / f1);
        return Color.HSBtoRGB(f2 / 3.0F, 1.0F, 1.0F) | 0xFF000000;
    }

    public static void drawGradientRect(double d, double e, double e2, double g, int startColor, int endColor)
    {
        float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)e2, (double)e, (double)zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)d, (double)e, (double)zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)d, (double)g, (double)zLevel).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos((double)e2, (double)g, (double)zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
	    
	    public static void drawFullCircle(double xPos, double yPos, double r, int c) {
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			r *= 2;
			xPos *= 2;
			yPos *= 2;
			float f = (float) (c >> 24 & 0xff) / 255F;
			float f1 = (float) (c >> 16 & 0xff) / 255F;
			float f2 = (float) (c >> 8 & 0xff) / 255F;
			float f3 = (float) (c & 0xff) / 255F;
			GL11.glEnable(3042);
			GL11.glDisable(3553);
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			GL11.glBlendFunc(770, 771);
			GL11.glColor4f(f1, f2, f3, f);
			GL11.glBegin(GL11.GL_TRIANGLE_FAN);
			for (int i = 0; i <= 360; i++) {
				double x = Math.sin((i * Math.PI / 180)) * r;
				double y = Math.cos((i * Math.PI / 180)) * r;
				GL11.glVertex2d(xPos + x, yPos + y);
			}
			GL11.glEnd();
			GL11.glDisable(GL11.GL_LINE_SMOOTH);
			GL11.glEnable(3553);
			GL11.glDisable(3042);
			GL11.glScalef(2F, 2F, 2F);
		}
	    
		public static void drawFullCircle(int cx, int cy, double r, int c) {
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			r *= 2;
			cx *= 2;
			cy *= 2;
			float f = (float) (c >> 24 & 0xff) / 255F;
			float f1 = (float) (c >> 16 & 0xff) / 255F;
			float f2 = (float) (c >> 8 & 0xff) / 255F;
			float f3 = (float) (c & 0xff) / 255F;
			GL11.glEnable(3042);
			GL11.glDisable(3553);
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			GL11.glBlendFunc(770, 771);
			GL11.glColor4f(f1, f2, f3, f);
			GL11.glBegin(GL11.GL_TRIANGLE_FAN);
			for (int i = 0; i <= 360; i++) {
				double x = Math.sin((i * Math.PI / 180)) * r;
				double y = Math.cos((i * Math.PI / 180)) * r;
				GL11.glVertex2d(cx + x, cy + y);
			}
			GL11.glEnd();
			GL11.glDisable(GL11.GL_LINE_SMOOTH);
			GL11.glEnable(3553);
			GL11.glDisable(3042);
			GL11.glScalef(2F, 2F, 2F);
		}
	    
	    public static int getColorFromPercentage(float current, float max) {
	        float percentage = (current / max) / 3;
	        return Color.HSBtoRGB(percentage, 1.0F, 1.0F);
	    }

	public static void drawFilledCircle(int xx, int yy, float radius, Color color) {
		int sections = 50;
		double dAngle = 6.283185307179586D / sections;
		GL11.glPushAttrib(8192);
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glBegin(6);
		for (int i = 0; i < sections; i++) {
			float x = (float) (radius * Math.sin(i * dAngle));
			float y = (float) (radius * Math.cos(i * dAngle));
			GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F,
					color.getAlpha() / 255.0F);
			GL11.glVertex2f(xx + x, yy + y);
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnd();
		GL11.glPopAttrib();
	}
	
	public static final void drawSmoothRect(float left, float top, float right, float bottom, int color) {
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        RenderHelper.drawRect(left, top, right, bottom, color);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        RenderHelper.drawRect(left * 2.0f - 1.0f, top * 2.0f, left * 2.0f, bottom * 2.0f - 1.0f, color);
        RenderHelper.drawRect(left * 2.0f, top * 2.0f - 1.0f, right * 2.0f, top * 2.0f, color);
        RenderHelper.drawRect(right * 2.0f, top * 2.0f, right * 2.0f + 1.0f, bottom * 2.0f - 1.0f, color);
        RenderHelper.drawRect(left * 2.0f, bottom * 2.0f - 1.0f, right * 2.0f, bottom * 2.0f, color);
        GL11.glDisable(3042);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }

    public static void drawRectWithEdge(double x, double y, double width, double height, Color color, Color color2) {
        drawRect(x, y, x + width, y + height, color.getRGB());
        int c = color2.getRGB();
        drawRect(x - 1.0D, y, x, y + height, c);
        drawRect(x + width, y, x + width + 1.0D, y + height, c);
        drawRect(x - 1.0D, y - 1.0D, x + width + 1.0D, y, c);
        drawRect(x - 1.0D, y + height, x + width + 1.0D, y + height + 1.0D, c);
    }

	public static void drawRoundedRect2(int x, int y, int width, int height, int cornerRadius, Color color) {
		Gui.drawRect(x, y + cornerRadius, x + cornerRadius, y + height - cornerRadius, color.getRGB());
		Gui.drawRect(x + cornerRadius, y, x + width - cornerRadius, y + height, color.getRGB());
		Gui.drawRect(x + width - cornerRadius, y + cornerRadius, x + width, y + height - cornerRadius, color.getRGB());

		drawArc(x + cornerRadius, y + cornerRadius, cornerRadius, 0, 90, color);
		drawArc(x + width - cornerRadius, y + cornerRadius, cornerRadius, 270, 360, color);
		drawArc(x + width - cornerRadius, y + height - cornerRadius, cornerRadius, 180, 270, color);
		drawArc(x + cornerRadius, y + height - cornerRadius, cornerRadius, 90, 180, color);
	}

	public static void drawHead(ResourceLocation skin, int width, int height) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
		Gui.drawScaledCustomSizeModalRect(width, height, 8.0f, 8.0f, 8, 8, 45, 45, 64.0f, 64.0f);
	}
	
	public static int astofloc(int delay) {
		float speed = 3200.0F;
		float hue = (float) (System.currentTimeMillis() % (int) speed) + (delay / 2);
		while (hue > speed)
			hue -= speed;
		hue /= speed;
		if (hue > 0.5D)
			hue = 0.5F - hue - 0.5F;
		hue += 0.5F;
		return Color.HSBtoRGB(hue, 0.5F, 1.0F);
	}
	
    public static void prepareScissorBox(float x, float y, float x2, float y2) {
        ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
        int factor = scale.getScaleFactor();
        GL11.glScissor((int) (x * factor), (int) ((scale.getScaledHeight() - y2) * factor), (int) ((x2 - x) * factor), (int) ((y2 - y) * factor));
    }

	public static void drawArc(int x, int y, int radius, int startAngle, int endAngle, Color color) {

		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255,
				(float) color.getAlpha() / 255);

		BufferBuilder worldRenderer = Tessellator.getInstance().getBuffer();

		worldRenderer.begin(6, DefaultVertexFormats.POSITION);
		worldRenderer.pos(x, y, 0).endVertex();

		for (int i = (int) (startAngle / 360.0 * 100); i <= (int) (endAngle / 360.0 * 100); i++) {
			double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
			worldRenderer.pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0).endVertex();
		}

		Tessellator.getInstance().draw();

		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}

	public static Method[] getMethods(Class<GuiIngame> clazz, Class<?>... args) {
		ArrayList<Method> methods = new ArrayList<>();

		Method[] declaredMethods = clazz.getDeclaredMethods();
		for (int i = 0; i < declaredMethods.length; i++) {
			Method m = declaredMethods[i];
			if (Arrays.equals(m.getParameterTypes(), args)) {
				methods.add(m);
			}
		}

		return methods.toArray(new Method[0]);
	}

	public static void drawRect(float x, float y, float x1, float y1, int color) {
		enableGL2D();
		setColor(color);
		drawRect(x, y, x1, y1);
		disableGL2D();
	}

	public static void enableGL2D() {
		GL11.glDisable(2929);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glDepthMask(true);
		GL11.glEnable(2848);
		GL11.glHint(3154, 4354);
		GL11.glHint(3155, 4354);
	}

	public static void disableGL2D() {
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDisable(2848);
		GL11.glHint(3154, 4352);
		GL11.glHint(3155, 4352);
	}

	public static void drawRect(double g, double h, double i, double j) {
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glPushMatrix();
		GL11.glBegin(7);
		GL11.glVertex2d(i, h);
		GL11.glVertex2d(g, h);
		GL11.glVertex2d(g, j);
		GL11.glVertex2d(i, j);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
	}

	public static int setColor(int colorHex) {
		float alpha = (colorHex >> 24 & 0xFF) / 255.0F;
		float red = (colorHex >> 16 & 0xFF) / 255.0F;
		float green = (colorHex >> 8 & 0xFF) / 255.0F;
		float blue = (colorHex & 0xFF) / 255.0F;
		GL11.glColor4f(red, green, blue, (alpha == 0.0F) ? 1.0F : alpha);
		return colorHex;
	}

	public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
		float f = (col1 >> 24 & 255) / 255.0f;
		float f1 = (col1 >> 16 & 255) / 255.0f;
		float f2 = (col1 >> 8 & 255) / 255.0f;
		float f3 = (col1 & 255) / 255.0f;
		float f4 = (col2 >> 24 & 255) / 255.0f;
		float f5 = (col2 >> 16 & 255) / 255.0f;
		float f6 = (col2 >> 8 & 255) / 255.0f;
		float f7 = (col2 & 255) / 255.0f;
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glShadeModel(7425);
		GL11.glPushMatrix();
		GL11.glBegin(7);
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glVertex2d(left, top);
		GL11.glVertex2d(left, bottom);
		GL11.glColor4f(f, f6, f7, f4);
		GL11.glVertex2d(right, bottom);
		GL11.glVertex2d(right, top);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
	}

	public static void drawRect(double left, double top, double right, double bottom, int color) {
		if (left < right) {
			double i = left;
			left = right;
			right = i;
		}
		if (top < bottom) {
			double j = top;
			top = bottom;
			bottom = j;
		}
		float f3 = (float) (color >> 24 & 0xFF) / 255.0f;
		float f = (float) (color >> 16 & 0xFF) / 255.0f;
		float f1 = (float) (color >> 8 & 0xFF) / 255.0f;
		float f2 = (float) (color & 0xFF) / 255.0f;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(f, f1, f2, f3);
		bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
		bufferBuilder.pos(left, bottom, 0.0).endVertex();
		bufferBuilder.pos(right, bottom, 0.0).endVertex();
		bufferBuilder.pos(right, top, 0.0).endVertex();
		bufferBuilder.pos(left, top, 0.0).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void drawRect1(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd,
			int paramColor) {
		float alpha = (float) (paramColor >> 24 & 0xFF) / 255F;
		float red = (float) (paramColor >> 16 & 0xFF) / 255F;
		float green = (float) (paramColor >> 8 & 0xFF) / 255F;
		float blue = (float) (paramColor & 0xFF) / 255F;
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GL11.glColor4f(red, green, blue, alpha);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(paramXEnd, paramYStart);
		GL11.glVertex2d(paramXStart, paramYStart);
		GL11.glVertex2d(paramXStart, paramYEnd);
		GL11.glVertex2d(paramXEnd, paramYEnd);
		GL11.glEnd();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glPopMatrix();
	}

	public static void drawRoundedRect1(double x, double y, double x1, double y1, int borderC, int insideC) {
		RenderHelper.drawRect(x + 0.5, y, x1 - 0.5, y + 0.5, insideC);
		RenderHelper.drawRect(x + 0.5, y1 - 0.5, x1 - 0.5, y1, insideC);
		RenderHelper.drawRect(x, y + 0.5, x1, y1 - 0.5, insideC);
	}
	
	public static void drawRoundedRect3(double x, double y, double x1, double y1, int borderC, int insideC) {
		RenderHelper.drawRect(x + 0.5, y, x1, y, insideC);
		RenderHelper.drawRect(x + 0.5, y1 - 0.5, x1 - 0.5, y1, insideC);
		RenderHelper.drawRect(x, y + 0.5, x1, y1 - 0.5, insideC);
	}
	
	public static void drawRoundedRect2(int xCoord, int yCoord, int xSize, int ySize, int colour) {
		int width = xCoord + xSize;
		int height = yCoord + ySize;
		drawRect(xCoord + 1, yCoord, width - 1, height, colour);
		drawRect(xCoord, yCoord + 1, width, height - 1, colour);
	}

	public static boolean isInViewFrustrum(Entity entity) {
		return (isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck);
	}

	private static boolean isInViewFrustrum(AxisAlignedBB bb) {
		Entity current = Minecraft.getMinecraft().getRenderViewEntity();
		frustrum.setPosition(current.posX, current.posY, current.posZ);
		return frustrum.isBoundingBoxInFrustum(bb);
	}

	public static double interpolate(double current, double old, double scale) {
		return old + (current - old) * scale;
	}
	
	public static void drawRoundedRect1(double x, double y, double width, double height, double radius, int color) {
        double x1 = x + width;
        double y1 = y + height;
        float f = (color >> 24 & 0xFF) / 255.0F;
        float f1 = (color >> 16 & 0xFF) / 255.0F;
        float f2 = (color >> 8 & 0xFF) / 255.0F;
        float f3 = (color & 0xFF) / 255.0F;
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);

        x *= 2;
        y *= 2;
        x1 *= 2;
        y1 *= 2;

        glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(f1, f2, f3, f);
        glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glBegin(GL11.GL_POLYGON);

        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x + radius + +(Math.sin((i * Math.PI / 180)) * (radius * -1)), y + radius + (Math.cos((i * Math.PI / 180)) * (radius * -1)));
        }

        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x + radius + (Math.sin((i * Math.PI / 180)) * (radius * -1)), y1 - radius + (Math.cos((i * Math.PI / 180)) * (radius * -1)));
        }

        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x1 - radius + (Math.sin((i * Math.PI / 180)) * radius), y1 - radius + (Math.cos((i * Math.PI / 180)) * radius));
        }

        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x1 - radius + (Math.sin((i * Math.PI / 180)) * radius), y + radius + (Math.cos((i * Math.PI / 180)) * radius));
        }

        GL11.glEnd();

        glEnable(GL11.GL_TEXTURE_2D);
        glDisable(GL11.GL_LINE_SMOOTH);
        glEnable(GL11.GL_TEXTURE_2D);

        GL11.glScaled(2, 2, 2);

        GL11.glPopAttrib();
        GL11.glColor4f(1, 1, 1, 1);

    }

	public static void drawBorderedRect(double left, double top, double right, double bottom, double borderWidth,
			int insideColor, int borderColor, boolean borderIncludedInBounds) {
		RenderHelper.drawRect(left - (!borderIncludedInBounds ? borderWidth : 0.0),
				top - (!borderIncludedInBounds ? borderWidth : 0.0),
				right + (!borderIncludedInBounds ? borderWidth : 0.0),
				bottom + (!borderIncludedInBounds ? borderWidth : 0.0), borderColor);
		RenderHelper.drawRect(left + (borderIncludedInBounds ? borderWidth : 0.0),
				top + (borderIncludedInBounds ? borderWidth : 0.0),
				right - (borderIncludedInBounds ? borderWidth : 0.0),
				bottom - (borderIncludedInBounds ? borderWidth : 0.0), insideColor);
	}

	public static void drawBoundingBox(AxisAlignedBB axisalignedbb) {
		GL11.glBegin(7);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		GL11.glEnd();
	}

	public static void pre3D() {
		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glShadeModel(7425);
		GL11.glDisable(3553);
		GL11.glEnable(2848);
		GL11.glDisable(2929);
		GL11.glDisable(2896);
		GL11.glDepthMask(false);
		GL11.glHint(3154, 4354);
	}

	public static void post3D() {
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GL11.glDisable(2848);
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

	public static Color effect(long offset, float brightness, int speed) {
		float hue = (float) (System.nanoTime() + (offset * speed)) / 1.0E10F % 1.0F;
		long color = Long
				.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, brightness, 1F)).intValue()), 16);
		Color c = new Color((int) color);
		return new Color(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, c.getAlpha() / 255.0F);
	}

	public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();
		worldRenderer.begin(3, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
		tessellator.draw();
		worldRenderer.begin(3, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
		tessellator.draw();
		worldRenderer.begin(1, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
		tessellator.draw();
	}

	public static void drawFilledBox(final AxisAlignedBB axisAlignedBB) {
		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder vertexbufferer = tessellator.getBuffer();
		vertexbufferer.begin(7, DefaultVertexFormats.POSITION);
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
		tessellator.draw();
		vertexbufferer.begin(7, DefaultVertexFormats.POSITION);
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
		tessellator.draw();
		vertexbufferer.begin(7, DefaultVertexFormats.POSITION);
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
		tessellator.draw();
		vertexbufferer.begin(7, DefaultVertexFormats.POSITION);
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
		tessellator.draw();
		vertexbufferer.begin(7, DefaultVertexFormats.POSITION);
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
		tessellator.draw();
		vertexbufferer.begin(7, DefaultVertexFormats.POSITION);
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
		vertexbufferer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
		tessellator.draw();
	}

	public static void drawOutlinedBox(final AxisAlignedBB boundingBox) {
		if (boundingBox == null) {
			return;
		}
		GL11.glBegin(3);
		GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
		GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
		GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
		GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
		GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
		GL11.glEnd();
		GL11.glBegin(3);
		GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
		GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
		GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
		GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
		GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
		GL11.glEnd();
		GL11.glBegin(1);
		GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
		GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
		GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
		GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
		GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
		GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
		GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
		GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
		GL11.glEnd();
	}

	public static void startSmooth() {
		GL11.glEnable(2848);
		GL11.glEnable(2881);
		GL11.glEnable(2832);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glHint(3154, 4354);
		GL11.glHint(3155, 4354);
		GL11.glHint(3153, 4354);
	}

	public static void endSmooth() {
		GL11.glDisable(2848);
		GL11.glDisable(2881);
		GL11.glEnable(2832);
	}

    public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
        RenderHelper.drawRect(x + width, y + width, x1 - width, y1 - width, internalColor);
        RenderHelper.drawRect(x + width, y, x1 - width, y + width, borderColor);
        RenderHelper.drawRect(x, y, x + width, y1, borderColor);
        RenderHelper.drawRect(x1 - width, y, x1, y1, borderColor);
        RenderHelper.drawRect(x + width, y1 - width, x1 - width, y1, borderColor);
    }
    
    public static void renderItem(ItemStack itemStack, int x, int y) {
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.enableDepth();
        net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, x, y);
        mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, itemStack, x, y);
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableDepth();
    }
     
    public static void enableGUIStandardItemLighting()
    {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(-30.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(165.0F, 1.0F, 0.0F, 0.0F);
        enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
    
    public static void drawNewRect(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(left, bottom, 0.0).endVertex();
        vertexbuffer.pos(right, bottom, 0.0).endVertex();
        vertexbuffer.pos(right, top, 0.0).endVertex();
        vertexbuffer.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void disableStandardItemLighting()
    {
        GlStateManager.disableLighting();
        GlStateManager.disableLight(0);
        GlStateManager.disableLight(1);
        GlStateManager.disableColorMaterial();
    }

    /**
     * Sets the OpenGL lighting properties to the values used when rendering blocks as items
     */
    public static void enableStandardItemLighting()
    {
        GlStateManager.enableLighting();
        GlStateManager.enableLight(0);
        GlStateManager.enableLight(1);
        GlStateManager.enableColorMaterial();
        GlStateManager.colorMaterial(1032, 5634);
        GlStateManager.glLight(16384, 4611, setColorBuffer(LIGHT0_POS.xCoord, LIGHT0_POS.yCoord, LIGHT0_POS.zCoord, 0.0D));
        float f = 0.6F;
        GlStateManager.glLight(16384, 4609, setColorBuffer(0.6F, 0.6F, 0.6F, 1.0F));
        GlStateManager.glLight(16384, 4608, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
        GlStateManager.glLight(16384, 4610, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
        GlStateManager.glLight(16385, 4611, setColorBuffer(LIGHT1_POS.xCoord, LIGHT1_POS.yCoord, LIGHT1_POS.zCoord, 0.0D));
        GlStateManager.glLight(16385, 4609, setColorBuffer(0.6F, 0.6F, 0.6F, 1.0F));
        GlStateManager.glLight(16385, 4608, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
        GlStateManager.glLight(16385, 4610, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
        GlStateManager.shadeModel(7424);
        float f1 = 0.4F;
        GlStateManager.glLightModel(2899, setColorBuffer(0.4F, 0.4F, 0.4F, 1.0F));
    }
    
    private static FloatBuffer setColorBuffer(double p_74517_0_, double p_74517_2_, double p_74517_4_, double p_74517_6_)
    {
        return setColorBuffer((float)p_74517_0_, (float)p_74517_2_, (float)p_74517_4_, (float)p_74517_6_);
    }
    
    public static void drawOutline(final AxisAlignedBB axisAlignedBB, final float width, final Color color){
        glPushMatrix();
        glLineWidth(width);
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        tessellator.draw();
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glPopMatrix();
    }

    /**
     * Update and return colorBuffer with the RGBA values passed as arguments
     */
    public static FloatBuffer setColorBuffer(float p_74521_0_, float p_74521_1_, float p_74521_2_, float p_74521_3_)
    {
        COLOR_BUFFER.clear();
        COLOR_BUFFER.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
        COLOR_BUFFER.flip();
        return COLOR_BUFFER;
    }
}
