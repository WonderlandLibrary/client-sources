package zamorozka.ui;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import zamorozka.main.Zamorozka;
import zamorozka.modules.COMBAT.KillAura;
import zamorozka.ui.font.CFontRenderer;

import java.awt.*;
import java.text.NumberFormat;

import static org.lwjgl.opengl.GL11.*;

public class RenderingUtils implements MCUtil {
	private static final RenderItem itemRender1 = Minecraft.getMinecraft().getRenderItem();
	private static final Frustum frustrum = new Frustum();
	protected static RenderManager renderManager;
	protected static float zLevel;
	private static int test;
	private static float animtest;
	private static boolean anim;
	public static double yLevel;
	public static boolean decreasing;
	private static final double DOUBLE_PI = Math.PI * 2;

	public static double interpolate(double current, double old, double scale) {
		return old + (current - old) * scale;
	}
	
    public static void drawGuiBackground(int width, int height) {
        Gui.drawRect(0, 0, width, height, 0xFF282C34);
    }
	
	public static void drawEntityOnScreen(double posX, double posY, double scale, float mouseX, float mouseY, EntityLivingBase ent) {
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) posX, (float) posY, 50.0F);
		GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
		float f = ent.renderYawOffset;
		float f1 = ent.rotationYaw;
		float f2 = ent.rotationPitch;
		float f3 = ent.prevRotationYawHead;
		float f4 = ent.rotationYawHead;
		GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
		ent.renderYawOffset = (float) Math.atan((double) (mouseX / 40.0F)) * 20.0F;
		ent.rotationYaw = (float) Math.atan((double) (mouseX / 40.0F)) * 40.0F;
		ent.rotationPitch = -((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F;
		ent.rotationYawHead = ent.rotationYaw;
		ent.prevRotationYawHead = ent.rotationYaw;
		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);

		rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		rendermanager.setRenderShadow(true);
		ent.renderYawOffset = f;
		ent.rotationYaw = f1;
		ent.rotationPitch = f2;
		ent.prevRotationYawHead = f3;
		ent.rotationYawHead = f4;
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	public static void drawOutlinedString(String str, CFontRenderer font, float x, float y, int color) {
		Minecraft mc = Minecraft.getMinecraft();
		font.drawString(str, x - 0.3F, y, Colors.getColor(0));
		font.drawString(str, x + 0.3F, y, Colors.getColor(0));
		font.drawString(str, x, y + 0.3F, Colors.getColor(0));
		font.drawString(str, x, y - 0.3F, Colors.getColor(0));
		font.drawString(str, x, y, color);
	}

	public static boolean isHovered(int mouseX, int mouseY, int x, int y, int width, int height) {
		if (mouseX >= x && mouseX <= (x + width)) {
			if (mouseY >= y && mouseY <= (y + height)) {
				return true;
			}
		}
		return false;
	}

	public static void drawNewRect(double paramXStart, double paramYStart, double paramXEnd, double paramYEnd, int color) {
		float alpha = (color >> 24 & 0xFF) / 255;
		float red = (color >> 16 & 0xFF) / 255;
		float green = (color >> 8 & 0xFF) / 255;
		float blue = (color & 0xFF) / 255;

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GL11.glPushMatrix();
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glVertex2d(paramXEnd, paramYStart);
		GL11.glVertex2d(paramXStart, paramYStart);
		GL11.glVertex2d(paramXStart, paramYEnd);
		GL11.glVertex2d(paramXEnd, paramYEnd);

		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);

		GL11.glColor4f(1, 1, 1, 1);
	}

	public static void drawHead(ResourceLocation skin, float width, float height) {
		GL11.glColor4f(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(skin);
		Gui.drawScaledCustomSizeModalRect(width, height, 8, 8, 8, 8, 16, 16, 64, 64);
	}

	public static void blurStart() {
		/* 18 */ Minecraft mc = Minecraft.getMinecraft();
		/* 19 */ if (OpenGlHelper.shadersSupported && !mc.gameSettings.ofFastRender) {
			/* 20 */ if (mc.entityRenderer.theShaderGroup != null) {
				/* 21 */ mc.entityRenderer.theShaderGroup.deleteShaderGroup();
				/*     */ }
			/* 23 */ mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
			/*     */ }
		/*     */ }

	/*     */ public static void blurStop() {
		/* 27 */ Minecraft mc = Minecraft.getMinecraft();
		/* 28 */ if (mc.entityRenderer.theShaderGroup != null) {
			/* 29 */ mc.entityRenderer.theShaderGroup.deleteShaderGroup();
			/* 30 */ mc.entityRenderer.theShaderGroup = null;
			/*     */ }
		/*     */ }

	/*     */ public static void drawOutlinedBox3d(AxisAlignedBB bb) {
		/* 34 */ Minecraft mc = Minecraft.getMinecraft();
		/* 35 */ bb = bb.offset(-(mc.getRenderManager()).renderPosX, -(mc.getRenderManager()).renderPosY, -(mc.getRenderManager()).renderPosZ);
		/* 36 */ GL11.glPushMatrix();
		/* 37 */ GL11.glEnable(3042);
		/* 38 */ GL11.glBlendFunc(770, 771);
		/* 39 */ GL11.glEnable(2848);
		/* 40 */ GL11.glLineWidth(2.0F);
		/* 41 */ GL11.glDisable(3553);
		/* 42 */ GL11.glEnable(2884);
		/* 43 */ GL11.glDisable(2929);
		/* 44 */ GL11.glBegin(1);
		/* 45 */ GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		/* 46 */ GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		/* 47 */ GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		/* 48 */ GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		/* 49 */ GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		/* 50 */ GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		/* 51 */ GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		/* 52 */ GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		/* 53 */ GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		/* 54 */ GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		/* 55 */ GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		/* 56 */ GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		/* 57 */ GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		/* 58 */ GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		/* 59 */ GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		/* 60 */ GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
		/* 61 */ GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		/* 62 */ GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		/* 63 */ GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		/* 64 */ GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		/* 65 */ GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		/* 66 */ GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
		/* 67 */ GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
		/* 68 */ GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		/* 69 */ GL11.glEnd();
		/* 70 */ GL11.glEnable(2929);
		/* 71 */ GL11.glEnable(3553);
		/* 72 */ GL11.glDisable(3042);
		/* 73 */ GL11.glDisable(2848);
		/* 74 */ GlStateManager.popMatrix();
		/*     */ }

	/*     */ public static void drawSolidBox3d(AxisAlignedBB bb) {
		/* 77 */ Minecraft mc = Minecraft.getMinecraft();
		/* 78 */ bb = bb.offset(-(mc.getRenderManager()).renderPosX, -(mc.getRenderManager()).renderPosY, -(mc.getRenderManager()).renderPosZ);
		/* 79 */ GL11.glPushMatrix();
		/* 80 */ GL11.glEnable(3042);
		/* 81 */ GL11.glBlendFunc(770, 771);
		/* 82 */ GL11.glEnable(2848);
		/* 83 */ GL11.glLineWidth(2.0F);
		/* 84 */ GL11.glDisable(3553);
		/* 85 */ GL11.glEnable(2884);
		/* 86 */ GL11.glDisable(2929);
		/* 87 */ GL11.glBegin(7);
		/* 88 */ GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		/* 89 */ GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		/* 90 */ GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		/* 91 */ GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		/* 92 */ GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		/* 93 */ GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
		/* 94 */ GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		/* 95 */ GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		/* 96 */ GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		/* 97 */ GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		/* 98 */ GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		/* 99 */ GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		/* 100 */ GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		/* 101 */ GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		/* 102 */ GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		/* 103 */ GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		/* 104 */ GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		/* 105 */ GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		/* 106 */ GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		/* 107 */ GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
		/* 108 */ GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		/* 109 */ GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		/* 110 */ GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
		/* 111 */ GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		/* 112 */ GL11.glEnd();
		/* 113 */ GL11.glEnable(2929);
		/* 114 */ GL11.glEnable(3553);
		/* 115 */ GL11.glDisable(3042);
		/* 116 */ GL11.glDisable(2848);
		/* 117 */ GlStateManager.popMatrix();
		/*     */ }

	/*     */ public static void drawOutlinedBox(int x1, int y1, int x2, int y2, int linewidth, int color, int outlinecolor) {
		/* 120 */ Gui.drawRect(x1 + linewidth, y1 + linewidth, x2 - linewidth, y2 - linewidth, color);
		/*     */
		/* 122 */ Gui.drawRect(x1, y1, x1 + linewidth, y2, outlinecolor);
		/* 123 */ Gui.drawRect(x2 - linewidth, y1, x2, y2, outlinecolor);
		/* 124 */ Gui.drawRect(x1, y1, x2, y1 + linewidth, outlinecolor);
		/* 125 */ Gui.drawRect(x1, y2 - linewidth, x2, y2, outlinecolor);
	}

	/*     */ public static void drawCircle1(float cx, float cy, float r, int num_segments, int c) {
		/* 146 */ r *= 2.0F;
		/* 147 */ cx *= 2.0F;
		/* 148 */ cy *= 2.0F;
		/* 149 */ float f = (c >> 24 & 0xFF) / 255.0F;
		/* 150 */ float f2 = (c >> 16 & 0xFF) / 255.0F;
		/* 151 */ float f3 = (c >> 8 & 0xFF) / 255.0F;
		/* 152 */ float f4 = (c & 0xFF) / 255.0F;
		/* 153 */ float theta = (float) (6.2831852D / num_segments);
		/* 154 */ float p = (float) Math.cos(theta);
		/* 155 */ float s = (float) Math.sin(theta);
		/* 156 */ float x = r;
		/* 157 */ float y = 0.0F;
		/* 158 */ enableGL2D();
		/* 159 */ GL11.glScalef(0.5F, 0.5F, 0.5F);
		/* 160 */ GL11.glColor4f(f2, f3, f4, f);
		/* 161 */ GL11.glBegin(2);
		/* 162 */ for (int ii = 0; ii < num_segments; ii++) {
			/* 163 */ GL11.glVertex2f(x + cx, y + cy);
			/* 164 */ float t = x;
			/* 165 */ x = p * x - s * y;
			/* 166 */ y = s * t + p * y;
			/*     */ }
		/* 168 */ GL11.glEnd();
		/* 169 */ GL11.glScalef(2.0F, 2.0F, 2.0F);
		/* 170 */ disableGL2D();
		/*     */ }

	/*     */ public static void drawFullCircle(int cx, int cy, double r, int c) {
		/* 173 */ r *= 2.0D;
		/* 174 */ cx *= 2;
		/* 175 */ cy *= 2;
		/* 176 */ float f = (c >> 24 & 0xFF) / 255.0F;
		/* 177 */ float f2 = (c >> 16 & 0xFF) / 255.0F;
		/* 178 */ float f3 = (c >> 8 & 0xFF) / 255.0F;
		/* 179 */ float f4 = (c & 0xFF) / 255.0F;
		/* 180 */ enableGL2D();
		/* 181 */ GL11.glScalef(0.5F, 0.5F, 0.5F);
		/* 182 */ GL11.glColor4f(f2, f3, f4, f);
		/* 183 */ GL11.glBegin(6);
		/* 184 */ for (int i = 0; i <= 360; i++) {
			/* 185 */ double x = Math.sin(i * Math.PI / 180.0D) * r;
			/* 186 */ double y = Math.cos(i * Math.PI / 180.0D) * r;
			/* 187 */ GL11.glVertex2d(cx + x, cy + y);
			/*     */ }
		/* 189 */ GL11.glEnd();
		/* 190 */ GL11.glScalef(2.0F, 2.0F, 2.0F);
		/* 191 */ disableGL2D();
		/*     */ }

	/*     */ public static void drawSineWave(double x, double y, double height, double lenght, double frequency, double speed, float linewidth, int ticker) {
		/* 194 */ GlStateManager.pushMatrix();
		/* 195 */ Minecraft mc = Minecraft.getMinecraft();
		/* 196 */ ScaledResolution sr = new ScaledResolution(mc);
		/* 197 */ enableGL2D();
		/* 198 */ GL11.glLineWidth(linewidth);
		/* 199 */ GL11.glTranslated(x, y, 0.0D);
		/* 200 */ GL11.glBegin(1);
		/* 201 */ for (int h = 0; h < lenght; h++) {
			/* 202 */ GL11.glVertex2d(h, Math.sin((h - (ticker + mc.timer.renderPartialTicks) * speed) * frequency) * height);
			/* 203 */ GL11.glVertex2d((h + 1), Math.sin((h - (ticker + mc.timer.renderPartialTicks) * speed + 1.0D) * frequency) * height);
			/*     */ }
		/* 205 */ GL11.glEnd();
		/* 206 */ disableGL2D();
		/* 207 */ GlStateManager.popMatrix();
		/*     */ }

	/*     */
	/*     */ public static double getDistance2d(double x1, double y1, double x2, double y2) {
		/* 211 */ return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		/*     */ }

	public static void drawItemTextures(EntityLivingBase target, int px, int py) {
		GlStateManager.enableTexture2D();

		int iteration = 0;
		for (ItemStack is : target.getArmorInventoryList()) {
			iteration++;
			if (is.func_190926_b())
				continue;
			int x = px - 90 + (9 - iteration) * 20 + 2;
			itemRender1.zLevel = 200F;
			itemRender1.renderItemAndEffectIntoGUI(is, x, py);
			itemRender1.renderItemOverlayIntoGUI(mc.fontRendererObj, is, x, py, "");
			itemRender1.zLevel = 0F;

			String s = is.getMaxItemUseDuration() > 1 ? is.getMaxItemUseDuration() + "" : "";
			mc.fontRendererObj.drawStringWithShadow(s, x + 19 - 2 - mc.fontRendererObj.getStringWidth(s), py + 9, new Color(255, 255, 255).getRGB());
			// mc.fontRendererObj.drawStringWithShadow(dmg + "", x + 8 -
			// mc.fontRendererObj.getStringWidth(dmg + "") / 2, py - 11, new Color((int)
			// (red * 255), (int) (green * 255), 0).getRGB());
		}
		GlStateManager.enableDepth();
		GlStateManager.disableLighting();
	}

	public static void drawCircle(float cx, float cy, float r, int c) {
		GL11.glPushMatrix();
		cx *= 2.0f;
		cy *= 2.0f;
		int num_segments = (int) (r * 3);
		float f = (float) (c >> 24 & 255) / 255.0f;
		float f1 = (float) (c >> 16 & 255) / 255.0f;
		float f2 = (float) (c >> 8 & 255) / 255.0f;
		float f3 = (float) (c & 255) / 255.0f;
		float theta = (float) (6.2831852 / (double) num_segments);
		float p = (float) Math.cos(theta);
		float s = (float) Math.sin(theta);
		float x = r *= 2.0f;
		float y = 0.0f;
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glBegin((int) 2);
		int ii = 0;
		while (ii < num_segments) {
			GL11.glVertex2f((float) (x + cx), (float) (y + cy));
			float t = x;
			x = p * x - s * y;
			y = s * t + p * y;
			++ii;
		}
		GL11.glEnd();
		GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GL11.glPopMatrix();
	}

	public static void drawFullCircle(float cx, float cy, float r, int c) {
		r *= 2.0;
		cx *= 2;
		cy *= 2;
		float f = (float) (c >> 24 & 255) / 255.0f;
		float f1 = (float) (c >> 16 & 255) / 255.0f;
		float f2 = (float) (c >> 8 & 255) / 255.0f;
		float f3 = (float) (c & 255) / 255.0f;
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glBegin((int) 6);
		int i = 0;
		while (i <= 360) {
			double x = Math.sin((double) i * 3.141592653589793 / 180.0) * r;
			double y = Math.cos((double) i * 3.141592653589793 / 180.0) * r;
			GL11.glVertex2d((double) ((double) cx + x), (double) ((double) cy + y));
			++i;
		}
		GL11.glEnd();
		GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void drawESP1(Entity entity, int color) {
		double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks;
		double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks + entity.getEyeHeight() * 1.2;
		double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks;
		double width = Math.abs(entity.boundingBox.maxX - entity.boundingBox.minX) + 0.2;
		double height = entity.height + 0.1;

		Vec3d vec = new Vec3d(x - width / 2, y, z - width / 2);
		Vec3d vec2 = new Vec3d(x + width / 2, y - height, z + width / 2);
		RenderingUtils.pre3D();
		mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
		RenderingUtils.glColor(color);
		RenderingUtils.drawBoundingBox(new AxisAlignedBB(vec.xCoord - RenderManager.renderPosX, vec.yCoord - RenderManager.renderPosY, vec.zCoord - RenderManager.renderPosZ, vec2.xCoord - RenderManager.renderPosX,
				vec2.yCoord - RenderManager.renderPosY, vec2.zCoord - RenderManager.renderPosZ));
		GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
		RenderingUtils.post3D();
	}

	public static void drawLinesAroundPlayer(Entity entity, double radius, float partialTicks, int points, float width, int color) {
		// 63 points
		glPushMatrix();
		RenderingUtils.enableGL2D3();
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_LINE_SMOOTH);
		glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
		glDisable(GL_DEPTH_TEST);
		glLineWidth(width);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_DEPTH_TEST);
		glBegin(GL_LINE_STRIP);
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - renderManager.viewerPosX;
		double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - renderManager.viewerPosY;
		double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - renderManager.viewerPosZ;
		RenderingUtils.color228(color);
		for (int i = 0; i <= points; i++)
			glVertex3d(x + radius * Math.cos(i * DOUBLE_PI / points), y, z + radius * Math.sin(i * DOUBLE_PI / points));
		glEnd();
		glDepthMask(true);
		glDisable(GL_BLEND);
		glEnable(GL_DEPTH_TEST);
		glDisable(GL_LINE_SMOOTH);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
		RenderingUtils.disableGL2D3();
		glPopMatrix();
	}

	public static void color228(int color) {
		glColor4ub((byte) (color >> 16 & 0xFF), (byte) (color >> 8 & 0xFF), (byte) (color & 0xFF), (byte) (color >> 24 & 0xFF));
	}

	public static void drawSmoothRect(float left, float top, float right, float bottom, int color) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		drawRect(left, top, right, bottom, color);
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		drawRect(left * 2 - 1, top * 2, left * 2, bottom * 2 - 1, color);
		drawRect(left * 2, top * 2 - 1, right * 2, top * 2, color);
		drawRect(right * 2, top * 2, right * 2 + 1, bottom * 2 - 1, color);
		drawRect(left * 2, bottom * 2 - 1, right * 2, bottom * 2, color);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glScalef(2F, 2F, 2F);
	}

	public static void drawRadius(Entity entity, float partialTicks, double rad) {
		Color c = Color.getHSBColor(0.8F, 1.0F, 1.0F);
		float red = c.getRed() / 255.0F;
		float green = c.getGreen() / 255.0F;
		float blue = c.getBlue() / 255.0F;
		float points = 7f;
		GlStateManager.enableDepth();
		int count = 0;
		for (double il = 0.0D; il < ((0.1 <= 0.005D) ? 0.01 : 0.05); il += 0.01D) {
			count++;
			GL11.glPushMatrix();
			GL11.glDisable(3553);
			GL11.glEnable(2848);
			GL11.glEnable(2881);
			GL11.glEnable(2832);
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			GL11.glHint(3154, 4354);
			GL11.glHint(3155, 4354);
			GL11.glHint(3153, 4354);
			GL11.glDisable(2929);
			GL11.glLineWidth(1.3F);
			GL11.glBegin(3);
			RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
			double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - renderManager.viewerPosX;
			double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - renderManager.viewerPosY;
			double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - renderManager.viewerPosZ;
			double pix2 = 6.283185307179586D;
			float speed = 5000.0F;
			float baseHue = (float) (System.currentTimeMillis() % (int) speed);
			while (baseHue > speed)
				baseHue -= speed;
			baseHue /= speed;
			for (int i = 0; i <= 90; i++) {
				float max = (i + (float) (il * 8.0D)) / points;
				float hue = max + baseHue;
				while (hue > 1.0F)
					hue--;
				GL11.glColor3f(red, green, blue);
				GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586D / points), y + il, z + rad * Math.sin(i * 6.283185307179586D / points));
			}
			GL11.glEnd();
			GL11.glDepthMask(true);
			GL11.glEnable(2929);
			GL11.glDisable(2848);
			GL11.glDisable(2881);
			GL11.glEnable(2832);
			GL11.glEnable(3553);
			GL11.glPopMatrix();
			GlStateManager.color(255.0F, 255.0F, 255.0F);
		}
	}

	public void renderLine(double x, double y, double z, double x2, double y2, double z2, float lineWidth, Color c) {
		GlStateManager.pushMatrix();
		GlStateManager.depthMask((boolean) false);
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.disableBlend();
		GlStateManager.disableDepth();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();
		worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
		GL11.glLineWidth((float) lineWidth);
		worldRenderer.pos(x, y, z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x2, y2, z2).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		tessellator.draw();
		GlStateManager.enableDepth();
		GlStateManager.depthMask((boolean) true);
		GlStateManager.enableTexture2D();
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.enableBlend();
		GlStateManager.popMatrix();
	}

	public static void renderBox(double x, double y, double z, float width, float height, Color c) {
		float halfwidth = width / 2.0f;
		float halfheight = height / 2.0f;
		GlStateManager.pushMatrix();
		GlStateManager.depthMask((boolean) false);
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.disableBlend();
		GlStateManager.disableDepth();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate((int) 770, (int) 771, (int) 1, (int) 0);
		worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		worldRenderer.pos(x - (double) halfwidth, (y += 1.0) - (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y + (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y + (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y - (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y - (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y + (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y + (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y - (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y - (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y + (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y + (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y - (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y - (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y + (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y + (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y - (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y + (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y + (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y + (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y + (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y - (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y - (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y - (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y - (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		tessellator.draw();
		GlStateManager.enableDepth();
		GlStateManager.depthMask((boolean) true);
		GlStateManager.enableTexture2D();
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.enableBlend();
		GlStateManager.popMatrix();
	}

	public static void renderOutlines(double x, double y, double z, float width, float height, Color c) {
		float halfwidth = width / 2.0f;
		float halfheight = height / 2.0f;
		GlStateManager.pushMatrix();
		GlStateManager.depthMask((boolean) false);
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.disableBlend();
		GlStateManager.disableDepth();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();
		worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
		GL11.glLineWidth((float) 1.2f);
		worldRenderer.pos(x - (double) halfwidth, (y += 1.0) - (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y + (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y - (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y + (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y - (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y + (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y - (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y + (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y - (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y - (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y - (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y - (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y - (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y - (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y - (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y - (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y + (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y + (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y + (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y + (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y + (double) halfheight, z - (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y + (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x - (double) halfwidth, y + (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		worldRenderer.pos(x + (double) halfwidth, y + (double) halfheight, z + (double) halfwidth).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
		tessellator.draw();
		GlStateManager.enableDepth();
		GlStateManager.depthMask((boolean) true);
		GlStateManager.enableTexture2D();
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.enableBlend();
		GlStateManager.popMatrix();
	}

	public static void renderBoxWithOutline(double x, double y, double z, float width, float height, Color c) {
		renderBox(x, y, z, width, height, c);
		renderOutlines(x, y, z, width, height, c);
	}

	public static void drawGradientBorderedRectReliant(float x2, float y2, float x1, float y1, float lineWidth, int border, int bottom, int top) {
		RenderingUtils.enableGL2D();
		RenderingUtils.drawGradientRect(x2, y2, x1, y1, top, bottom);
		RenderingUtils.glColor(border);
		GL11.glEnable((int) 3042);
		GL11.glDisable((int) 3553);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glLineWidth((float) lineWidth);
		GL11.glBegin((int) 3);
		GL11.glVertex2f((float) x2, (float) y2);
		GL11.glVertex2f((float) x2, (float) y1);
		GL11.glVertex2f((float) x1, (float) y1);
		GL11.glVertex2f((float) x1, (float) y2);
		GL11.glVertex2f((float) x2, (float) y2);
		GL11.glEnd();
		GL11.glEnable((int) 3553);
		GL11.glDisable((int) 3042);
		RenderingUtils.disableGL2D();
	}

	public static void blockESPBox(BlockPos blockPos) {
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glColor4d(0, 1, 0, 0.15F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		// drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glColor4d(0, 0, 1, 0.5F);
		RenderGlobal.func_181561_a(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void drawAltFace(String name, int x, int y, int w, int h, boolean selected) {
		try {
			AbstractClientPlayer.getDownloadImageSkin(AbstractClientPlayer.getLocationSkin(name), name).loadTexture(Minecraft.getMinecraft().getResourceManager());
			Minecraft.getMinecraft().getTextureManager().bindTexture(AbstractClientPlayer.getLocationSkin(name));
			Tessellator var3 = Tessellator.getInstance();
			BufferBuilder var4 = var3.getBuffer();
			GL11.glEnable((int) 3042);
			if (selected) {
				GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
			} else {
				GL11.glColor4f((float) 0.9f, (float) 0.9f, (float) 0.9f, (float) 1.0f);
			}
			double fw = 32.0;
			double fh = 32.0;
			double u = 32.0;
			double v = 32.0;
			var4.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
			var4.pos((double) x + 0.0, (double) y + (double) h, 0.0);
			var4.pos((double) x + (double) w, (double) y + (double) h, 0.0);
			var4.pos((double) x + (double) w, (double) y + 0.0, 0.0);
			var4.pos((double) x + 0.0, (double) y + 0.0, 0.0);
			var3.draw();
			fw = 32.0;
			fh = 32.0;
			u = 160.0;
			v = 32.0;
			var4.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
			var4.pos((double) x + 0.0, (double) y + (double) h, 0.0);
			var4.pos((double) x + (double) w, (double) y + (double) h, 0.0);
			var4.pos((double) x + (double) w, (double) y + 0.0, 0.0);
			var4.pos((double) x + 0.0, (double) y + 0.0, 0.0);
			var3.draw();
			GL11.glDisable((int) 3042);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void enableGL2D() {
		GL11.glDisable(2929);
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glDepthMask(true);
		GL11.glEnable(2848);
		GL11.glHint(3154, 4354);
		GL11.glHint(3155, 4354);
	}

	public static void disableGL2D() {
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glEnable(2929);
		GL11.glDisable(2848);
		GL11.glHint(3154, 4352);
		GL11.glHint(3155, 4352);
	}

	public static void drawOutlinedBlockESP(double x, double y, double z, float red, float green, float blue, float alpha, float lineWidth) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glLineWidth(lineWidth);
		GL11.glColor4f(red, green, blue, alpha);
		drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
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
		drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public static void drawBlockESP3(double x, double y, double z, int maincoolor, int borderColor, float lineWidth) {
		float alpha = (float) (maincoolor >> 24 & 255) / 255.0f;
		float red = (float) (maincoolor >> 16 & 255) / 255.0f;
		float green = (float) (maincoolor >> 8 & 255) / 255.0f;
		float blue = (float) (maincoolor & 255) / 255.0f;

		float lineAlpha = (float) (borderColor >> 24 & 255) / 255.0f;
		float lineRed = (float) (borderColor >> 16 & 255) / 255.0f;
		float lineGreen = (float) (borderColor >> 8 & 255) / 255.0f;
		float lineBlue = (float) (borderColor & 255) / 255.0f;

		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(3553);
		GL11.glEnable(2848);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);

		GL11.glColor4f(red, green, blue, alpha);
		drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glLineWidth(lineWidth);
		GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
		drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));

		GL11.glDisable(2848);
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}

	public static void drawSolidBlockESP3(double x, double y, double z, float red, float green, float blue, float alpha) {
		GL11.glPushMatrix();
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glDisable((int) 2929);
		GL11.glDepthMask((boolean) false);
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 3042);
		GL11.glPopMatrix();
	}

	public static void drawTracerLine(double x, double y, double z, float red, float green, float blue, float alpha, float lineWdith) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(lineWdith);
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glBegin(2);
		GL11.glVertex3d(0.0D, 0.0D + Minecraft.getMinecraft().player.getEyeHeight(), 0.0D);
		GL11.glVertex3d(x, y, z);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_BLEND);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	public static void drawSolidBlockESP(double x, double y, double z, float red, float green, float blue, float alpha) {
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
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public static void blockEsp(BlockPos blockPos, double red, double green, double blue, float thickness) {
		double x = (double) blockPos.getX() - mc.getRenderManager().renderPosX;
		double y = (double) blockPos.getY() - mc.getRenderManager().renderPosY;
		double z = (double) blockPos.getZ() - mc.getRenderManager().renderPosZ;
		glBlendFunc(770, 771);
		glEnable(3042);
		glLineWidth(thickness);
		glEnable(GL_LINE_SMOOTH);
		glDisable(3553);
		glDisable(2929);
		glDepthMask(false);
		glColor4d(red, green, blue, 13);
		glColor4d(red, green, blue, 1);
		drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		glEnable(3553);
		glEnable(2929);
		glDepthMask(true);
	}

	public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
		tessellator.draw();
		vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
		tessellator.draw();
		vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		tessellator.draw();
	}

	public static Color blendColors(final float[] fractions, final Color[] colors, final float progress) {
		Color color = null;
		if (fractions == null) {
			throw new IllegalArgumentException("Fractions can't be null");
		}
		if (colors == null) {
			throw new IllegalArgumentException("Colours can't be null");
		}
		if (fractions.length == colors.length) {
			final int[] indicies = getFractionIndicies(fractions, progress);
			final float[] range = new float[] { fractions[indicies[0]], fractions[indicies[1]] };
			final Color[] colorRange = new Color[] { colors[indicies[0]], colors[indicies[1]] };
			final float max = range[1] - range[0];
			final float value = progress - range[0];
			final float weight = value / max;
			color = blend(colorRange[0], colorRange[1], 1.0f - weight);
			return color;
		}
		throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
	}

	public static int[] getFractionIndicies(final float[] fractions, final float progress) {
		int startPoint;
		final int[] range = new int[2];
		for (startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {
		}
		if (startPoint >= fractions.length) {
			startPoint = fractions.length - 1;
		}
		range[0] = startPoint - 1;
		range[1] = startPoint;
		return range;
	}

	public static Color blend(final Color color1, final Color color2, final double ratio) {
		final float r = (float) ratio;
		final float ir = 1.0f - r;
		final float[] rgb1 = new float[3];
		final float[] rgb2 = new float[3];
		color1.getColorComponents(rgb1);
		color2.getColorComponents(rgb2);
		float red = rgb1[0] * r + rgb2[0] * ir;
		float green = rgb1[1] * r + rgb2[1] * ir;
		float blue = rgb1[2] * r + rgb2[2] * ir;
		if (red < 0.0f) {
			red = 0.0f;
		} else if (red > 255.0f) {
			red = 255.0f;
		}
		if (green < 0.0f) {
			green = 0.0f;
		} else if (green > 255.0f) {
			green = 255.0f;
		}
		if (blue < 0.0f) {
			blue = 0.0f;
		} else if (blue > 255.0f) {
			blue = 255.0f;
		}
		Color color3 = null;
		try {
			color3 = new Color(red, green, blue);
		} catch (final IllegalArgumentException exp) {
			final NumberFormat nf = NumberFormat.getNumberInstance();
			System.out.println(String.valueOf(String.valueOf(nf.format(red))) + "; " + nf.format(green) + "; " + nf.format(blue));
			exp.printStackTrace();
		}
		return color3;
	}

	/*
	 * public static void renderArmor(final EntityPlayer player) { int xOffset = 0;
	 * for (final ItemStack armourStack : player.inventory.armorInventory) { if
	 * (armourStack == null) { continue; } xOffset -= 8; } if
	 * (player.getHeldItemMainhand() != null) { xOffset -= 8; final ItemStack stock
	 * = player.getHeldItemMainhand().copy(); if (stock.hasEffect() &&
	 * (stock.getItem() instanceof ItemTool || stock.getItem() instanceof
	 * ItemArmor)) { stock.stackSize = 1; } renderItemStack(stock, xOffset, -20);
	 * xOffset += 16; } final ItemStack[] renderStack =
	 * player.inventory.armorInventory; for (int index = 3; index >= 0; --index) {
	 * final ItemStack armourStack2 = renderStack[index]; if (armourStack2 == null)
	 * { continue; } final ItemStack renderStack2 = armourStack2;
	 * renderItemStack(renderStack2, xOffset, -20); xOffset += 16; } }
	 */

	private static int XD(final EntityPlayer e) {
		final int health = Math.round(20.0f * (e.getHealth() / e.getMaxHealth()));
		int color = 0;
		switch (health) {
		case 18:
		case 19: {
			color = 9108247;
			break;
		}
		case 16:
		case 17: {
			color = 10026904;
			break;
		}
		case 14:
		case 15: {
			color = 12844472;
			break;
		}
		case 12:
		case 13: {
			color = 16633879;
			break;
		}
		case 10:
		case 11: {
			color = 15313687;
			break;
		}
		case 8:
		case 9: {
			color = 16285719;
			break;
		}
		case 6:
		case 7: {
			color = 16286040;
			break;
		}
		case 4:
		case 5: {
			color = 15031100;
			break;
		}
		case 2:
		case 3: {
			color = 16711680;
			break;
		}
		case -1:
		case 0:
		case 1: {
			color = 16190746;
			break;
		}
		default: {
			color = -11746281;
		}
		}
		return color;
	}

	private static String getHealth(final EntityPlayer e) {
		String hp = "";
		final double health = 10.0 * (e.getHealth() / e.getMaxHealth());
		hp = Math.floor(health) == health ? String.valueOf((int) health) : String.valueOf(health);
		return hp;
	}

	private static float getSize(final EntityPlayer player) {
		final EntityPlayerSP ent = mc.player;
		final double dist = ent.getDistanceToEntity(player) / 5.0f;
		final float size = dist <= 2.0 ? 1.3f : (float) dist;
		return size;
	}

	private static void renderItemStack(final ItemStack stack, final int x, final int y2) {
		GlStateManager.pushMatrix();
		GlStateManager.disableAlpha();
		GlStateManager.clear(256);
		mc.getRenderItem().zLevel = -150.0f;
		mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y2);
		mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, stack, x, y2);
		mc.getRenderItem().zLevel = 0.0f;
		GlStateManager.disableBlend();
		GlStateManager.scale(0.5, 0.5, 0.5);
		GlStateManager.disableDepth();
		GlStateManager.disableLighting();
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		GlStateManager.scale(2.0f, 2.0f, 2.0f);
		GlStateManager.enableAlpha();
		GlStateManager.popMatrix();
	}

	public void drawOutlineRect(final float drawX, final float drawY, final float drawWidth, final float drawHeight, final int color) {
		drawRect(drawX, drawY, drawWidth, drawY + 0.5f, color);
		drawRect(drawX, drawY + 0.5f, drawX + 0.5f, drawHeight, color);
		drawRect(drawWidth - 0.5f, drawY + 0.5f, drawWidth, drawHeight - 0.5f, color);
		drawRect(drawX + 0.5f, drawHeight - 0.5f, drawWidth, drawHeight, color);
	}

	public void draw2DPlayerESP(final EntityPlayer ep, final double d, final double d1, final double d2) {
		final float distance = Minecraft.getMinecraft().player.getDistanceToEntity(ep);
		final float scale = (float) (0.09 + Minecraft.getMinecraft().player.getDistance(ep.posX, ep.posY, ep.posZ) / 10000.0);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glNormal3f(0.0f, 1.0f, 0.0f);
		GL11.glRotatef(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
		GL11.glScalef(-scale, -scale, scale);
		GL11.glDisable(2896);
		GL11.glDisable(2929);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glScaled(0.5, 0.5, 0.5);
		drawOutlineRect(-13.0f, -45.0f, 13.0f, 5.0f, -65536);
		GL11.glScaled(2.0, 2.0, 2.0);
		GL11.glDisable(3042);
		GL11.glEnable(2929);
		GL11.glEnable(2896);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glPopMatrix();
	}

	public static void renderCircle(float x, float y, float radius, int circleDetail, Color color, float thickness) {
		if (circleDetail > 360 || circleDetail < 8) {
			try {
				throw new IllegalArgumentException("Detail out of bounds.");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}

		glLineWidth(thickness);
		glBegin(GL_LINE_LOOP);
		glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

		for (int i = 0; i < circleDetail; i++) {
			float theta = 2.0f * 3.1415926f * i / circleDetail;

			float x2 = (float) (radius * Math.cos(theta));
			float y2 = (float) (radius * Math.sin(theta));

			glVertex2f(x + x2, y + y2);
		}

		glColor4f(1f, 1f, 1f, 1f);
		glEnd();
	}

	public static void renderPartialCircle(float cx, float cy, float r, float thickness, float amt, Color c, ResourceLocation lookup) {/*
																																		 * if(circleDetail > 360 || circleDetail < 8) { try { throw new
																																		 * IllegalArgumentException("Detail out of bounds."); } catch
																																		 * (IllegalArgumentException e) { e.printStackTrace(); } }
																																		 * 
																																		 * glLineWidth(thickness); glBegin(GL_LINE_LOOP); glColor4f(color.getR(),
																																		 * color.getG(), color.getB(), color.getA());
																																		 * 
																																		 * //glRotatef(rotation, 0, 1, 0);
																																		 * 
																																		 * for(int i = 0; i < circleDetail; i++) { float theta = 2.0f * 3.1415926f *
																																		 * (float)i / (float)circleDetail; float x2 = (float)(radius * Math.cos(theta));
																																		 * float y2 = (float)(radius * Math.sin(theta)); glVertex2f(x + x2, y + y2);
																																		 * //System.out.println(i);
																																		 * 
																																		 * if(i >= degrees)//>= 360f/(float)circleDetail*degrees) { //break; } }
																																		 * 
																																		 * glColor4f(1f, 1f, 1f, 1f); //glRotatef(0f, 0, 1, 0); glEnd();
																																		 */

		// start and end angles
		float start = 0f;
		float end = amt * 360f;

		Minecraft.getMinecraft().getTextureManager().bindTexture(lookup);
		glBegin(GL_TRIANGLE_STRIP);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		int segs = (int) (12 * Math.cbrt(r));
		end += 90f;
		start += 90f;
		float halfThick = thickness / 2f;
		float step = 360f / segs;
		for (float angle = start; angle < (end + step); angle += step) {
			float tc = 0.5f;
			if (angle == start)
				tc = 0f;
			else if (angle >= end)
				tc = 1f;

			float fx = (float) Math.cos(angle);
			float fy = (float) Math.sin(angle);

			float z = 0f;
			glColor4f(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
			glTexCoord2f(tc, 1f);
			glVertex3f(cx + fx * (r + halfThick), cy + fy * (r + halfThick), z);

			glColor4f(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
			glTexCoord2f(tc, 0f);
			glVertex3f(cx + fx * (r + -halfThick), cy + fy * (r + -halfThick), z);
		}
		glEnd();
	}

	public static void drawTracer(final Entity entity, final Color color) {
		final RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		final double x = entity.posX - renderManager.renderPosX;
		final double y = entity.posY - renderManager.renderPosY;
		final double z = entity.posZ - renderManager.renderPosZ;
		final Vec3d eyeVector = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float) Math.toRadians(Minecraft.getMinecraft().player.rotationPitch)).rotateYaw(-(float) Math.toRadians(Minecraft.getMinecraft().player.rotationYaw));
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glLineWidth(2.0f);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		glColor2(color);
		GL11.glBegin(1);
		GL11.glVertex3d(eyeVector.xCoord, Minecraft.getMinecraft().player.getEyeHeight() + eyeVector.yCoord, eyeVector.zCoord);
		GL11.glVertex3d(x, y, z);
		GL11.glVertex3d(x, y, z);
		GL11.glVertex3d(x, y + entity.height, z);
		GL11.glEnd();
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GlStateManager.resetColor();
	}

	public static void glColor2(final Color color) {
		GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
	}

	private static void glColor2(final int hex) {
		final float alpha = (hex >> 24 & 0xFF) / 255.0f;
		final float red = (hex >> 16 & 0xFF) / 255.0f;
		final float green = (hex >> 8 & 0xFF) / 255.0f;
		final float blue = (hex & 0xFF) / 255.0f;
		GL11.glColor4f(red, green, blue, alpha);
	}

	public static void drawCircle8(Entity entity, float partialTicks, double rad) {
		if (KillAura.target != null) {
			for (double il = 0; il < 0.05; il += 0.0006) {
				GL11.glPushMatrix();
				GlStateManager.disableTexture2D();
				GlStateManager.disableDepth();
				GL11.glDepthMask(false);
				GL11.glLineWidth(.1f);
				GL11.glBegin(1);
				yLevel += decreasing ? -0.0001 : 0.0001;
				if (yLevel > 1.9) {
					decreasing = true;
				}
				if (yLevel <= 0) {
					decreasing = false;
				}
				final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
				double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
				final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;

				final double xD = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
				final double yD = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
				final double zD = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;

				final float rD = Color.black.getRGB();
				final float gD = Color.black.getRGB();
				final float bD = Color.black.getRGB();

				final double pix3 = Math.PI * 2.0D;
				y += yLevel;
				final double pix2 = Math.PI * 2D;
				for (int i = 0; i <= 90; ++i) {
					GL11.glColor3f(0, 0, 0);

					GL11.glVertex3d(x + rad * Math.cos(i * pix2 / 45.0), y + il, z + rad * Math.sin(i * pix2 / 45.0));
					GL11.glVertex3d(x + rad * Math.cos(i * pix2 / 45.0), y + il - yLevel, z + rad * Math.sin(i * pix2 / 45.0));
				}

				GL11.glEnd();
				GL11.glDepthMask(true);
				GlStateManager.enableDepth();
				GlStateManager.enableTexture2D();
				GL11.glPopMatrix();
			}

			for (double il = 0; il < 0.05; il += 0.0006) {
				GL11.glPushMatrix();
				GlStateManager.disableTexture2D();
				GlStateManager.disableDepth();
				GL11.glDepthMask(false);
				GL11.glLineWidth(0.25f);
				GL11.glBegin(1);
				final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
				double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
				final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;

				y += yLevel;

				final float r = ((float) -1 / 255) * 1;
				final float g = ((float) -1 / 255) * 1;
				final float b = ((float) -1 / 255) * 1;

				final double pix2 = Math.PI * 2D;

				float speed = 1200f;
				float baseHue = System.currentTimeMillis() % (int) speed;
				while (baseHue > speed) {
					baseHue -= speed;
				}
				baseHue /= speed;

				for (int i = 0; i <= 90; ++i) {
					float max = ((float) i) / 45F;
					float hue = max + baseHue;
					while (hue > 1) {
						hue -= 1;
					}
					float f3 = (float) (-1 >> 24 & 255) / 255.0F;
					float f = (float) (-1 >> 16 & 255) / 255.0F;
					float f1 = (float) (-1 >> 8 & 255) / 255.0F;
					float f2 = (float) (-1 & 255) / 255.0F;
					final float red = 0.003921569f * new Color(Color.HSBtoRGB(hue, 0.75F, 1F)).getRed();
					final float green = 0.003921569f * new Color(Color.HSBtoRGB(hue, 0.75F, 1F)).getGreen();
					final float blue = 0.003921569f * new Color(Color.HSBtoRGB(hue, 0.75F, 1F)).getBlue();
					GL11.glColor3f(red, green, blue);
					GL11.glVertex3d(x + rad * Math.cos(i * pix2 / 45.0), y + il, z + rad * Math.sin(i * pix2 / 45.0));
				}

				GL11.glEnd();
				GL11.glDepthMask(true);
				GlStateManager.enableDepth();
				GlStateManager.enableTexture2D();
				GL11.glPopMatrix();
			}

			for (double il = 0; il < 0.02; il += 0.0006) {
				GL11.glPushMatrix();
				GlStateManager.disableTexture2D();
				GlStateManager.enableDepth();
				GL11.glDepthMask(false);
				GL11.glLineWidth(0.1f);
				GL11.glBegin(1);
				final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
				double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
				final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;

				final float r = ((float) -1 / 255) * 1;
				final float g = ((float) -1 / 255) * 1;
				final float b = ((float) -1 / 255) * 1;

				final double pix2 = Math.PI * 2D;

				float speed = 1200f;
				float baseHue = System.currentTimeMillis() % (int) speed;
				while (baseHue > speed) {
					baseHue -= speed;
				}
				baseHue /= speed;

				for (int i = 0; i <= 90; ++i) {
					float max = ((float) i) / 45F;
					float hue = max + baseHue;
					while (hue > 1) {
						hue -= 1;
					}
					float f3 = (float) (-1 >> 24 & 255) / 255.0F;
					float f = (float) (-1 >> 16 & 255) / 255.0F;
					float f1 = (float) (-1 >> 8 & 255) / 255.0F;
					float f2 = (float) (-1 & 255) / 255.0F;
					final float red = 0.003921569f * new Color(Color.HSBtoRGB(hue, 0.75F, 0.7F)).getRed();
					final float green = 0.003921569f * new Color(Color.HSBtoRGB(hue, 0.75F, 0.7F)).getGreen();
					final float blue = 0.003921569f * new Color(Color.HSBtoRGB(hue, 0.75F, 0.7F)).getBlue();
					GL11.glColor3f(red, green, blue);
					GL11.glVertex3d(x + rad * Math.cos(i * pix2 / 45.0), y + il, z + rad * Math.sin(i * pix2 / 45.0));
				}

				GL11.glEnd();
				GL11.glDepthMask(true);
				GlStateManager.enableDepth();
				GlStateManager.enableTexture2D();
				GL11.glPopMatrix();
			}
		}
		// NO CAGE
		GL11.glPushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.disableDepth();
		GL11.glDepthMask(false);
		GL11.glLineWidth(6f);
		GL11.glBegin(1);

		final double xD = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
		final double yD = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
		final double zD = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;

		final float rD = Color.black.getRGB();
		final float gD = Color.black.getRGB();
		final float bD = Color.black.getRGB();

		final double pix3 = Math.PI * 2.0D;

		for (int i = 0; i <= 90; ++i) {
			GL11.glColor3f(rD, gD, bD);
			GL11.glVertex3d(xD + rad * Math.cos(i * pix3 / 45.0), yD, zD + rad * Math.sin(i * pix3 / 45.0));
		}

		GL11.glEnd();
		GL11.glDepthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableTexture2D();
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.disableDepth();
		GL11.glDepthMask(false);
		GL11.glLineWidth(2f);
		GL11.glBegin(1);
		final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
		double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
		final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;

		final float r = ((float) -1 / 255) * 1;
		final float g = ((float) -1 / 255) * 1;
		final float b = ((float) -1 / 255) * 1;

		final double pix2 = Math.PI * 2D;

		float speed = 1200f;
		float baseHue = System.currentTimeMillis() % (int) speed;
		while (baseHue > speed) {
			baseHue -= speed;
		}
		baseHue /= speed;

		for (int i = 0; i <= 90; ++i) {
			float max = ((float) i) / 45F;
			float hue = max + baseHue;
			while (hue > 1) {
				hue -= 1;
			}
			float f3 = (float) (-1 >> 24 & 255) / 255.0F;
			float f = (float) (-1 >> 16 & 255) / 255.0F;
			float f1 = (float) (-1 >> 8 & 255) / 255.0F;
			float f2 = (float) (-1 & 255) / 255.0F;
			final float red = 0.003921569f * new Color(Color.HSBtoRGB(hue, 0.75F, 0.7F)).getRed();
			final float green = 0.003921569f * new Color(Color.HSBtoRGB(hue, 0.75F, 0.7F)).getGreen();
			final float blue = 0.003921569f * new Color(Color.HSBtoRGB(hue, 0.75F, 0.7F)).getBlue();
			GL11.glColor3f(red, green, blue);
			GL11.glVertex3d(x + rad * Math.cos(i * pix2 / 45.0), y, z + rad * Math.sin(i * pix2 / 45.0));
		}

		GL11.glEnd();
		GL11.glDepthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableTexture2D();
		GL11.glPopMatrix();

	}

	public static int getHealthColor1(final EntityLivingBase player) {
		final float f = player.getHealth();
		final float f2 = player.getMaxHealth();
		final float f3 = Math.max(0.0f, Math.min(f, f2) / f2);
		return Color.HSBtoRGB(f3 / 3.0f, 1.0f, 0.75f) | 0xFF000000;
	}

	public static void drawESP(Entity entity, int color) {
		double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks;
		double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks + entity.getEyeHeight() * 1.2;
		double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks;
		double width = Math.abs(entity.boundingBox.maxX - entity.boundingBox.minX) + 0.2;
		double height = entity.height + 0.1;

		Vec3d vec = new Vec3d(x - width / 2, y, z - width / 2);
		Vec3d vec2 = new Vec3d(x + width / 2, y - height, z + width / 2);
		RenderingUtils.pre3D();
		mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
		RenderingUtils.glColor(color);
		RenderingUtils.drawBoundingBox(new AxisAlignedBB(vec.xCoord - RenderManager.renderPosX, vec.yCoord - RenderManager.renderPosY, vec.zCoord - RenderManager.renderPosZ, vec2.xCoord - RenderManager.renderPosX,
				vec2.yCoord - RenderManager.renderPosY, vec2.zCoord - RenderManager.renderPosZ));
		GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
		RenderingUtils.post3D();
	}

	public static void drawCircle(float cx, float cy, float r, int num_segments, int c) {
		GL11.glPushMatrix();
		cx *= 2.0f;
		cy *= 2.0f;
		float f = (float) (c >> 24 & 255) / 255.0f;
		float f1 = (float) (c >> 16 & 255) / 255.0f;
		float f2 = (float) (c >> 8 & 255) / 255.0f;
		float f3 = (float) (c & 255) / 255.0f;
		float theta = (float) (6.2831852 / (double) num_segments);
		float p = (float) Math.cos(theta);
		float s = (float) Math.sin(theta);
		float x = r *= 2.0f;
		float y = 0.0f;
		RenderingUtils.enableGL2D3();
		GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glBegin((int) 2);
		for (int ii = 0; ii < num_segments; ++ii) {
			GL11.glVertex2f((float) (x + cx), (float) (y + cy));
			float t = x;
			x = p * x - s * y;
			y = s * t + p * y;
		}
		GL11.glEnd();
		GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
		RenderingUtils.disableGL2D3();
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glPopMatrix();
	}

	public static void drawCircle223(float x, float y, float radius, int start, int end) {
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);
		glColor4(Color.GREEN);

		glEnable(GL_LINE_SMOOTH);
		glLineWidth(6F);
		glBegin(GL_LINE_STRIP);
		for (float i = end; i >= start; i -= (360 / 90.0f)) {
			glVertex2f((float) (x + (Math.cos(i * Math.PI / 180) * (radius * 1.001F))), (float) (y + (Math.sin(i * Math.PI / 180) * (radius * 1.001F))));
		}
		glEnd();
		glDisable(GL_LINE_SMOOTH);

		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void glColor4(final int red, final int green, final int blue, final int alpha) {
		GL11.glColor4f(red / 255F, green / 255F, blue / 255F, alpha / 255F);
	}

	public static void glColor4(final Color color) {
		glColor4(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	private static void glColor4(final int hex) {
		glColor4(hex >> 16 & 0xFF, hex >> 8 & 0xFF, hex & 0xFF, hex >> 24 & 0xFF);
	}

	public static void enableGL2D3() {
		GL11.glDisable((int) 2929);
		GL11.glEnable((int) 3042);
		GL11.glDisable((int) 3553);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDepthMask((boolean) true);
		GL11.glEnable((int) 2848);
		GL11.glHint((int) 3154, (int) 4354);
		GL11.glHint((int) 3155, (int) 4354);
	}

	public static void disableGL2D3() {
		GL11.glEnable((int) 3553);
		GL11.glDisable((int) 3042);
		GL11.glEnable((int) 2929);
		GL11.glDisable((int) 2848);
		GL11.glHint((int) 3154, (int) 4352);
		GL11.glHint((int) 3155, (int) 4352);
	}

	public static void drawCircle5(Entity entity, float partialTicks, double rad, final int rgb) {
		GL11.glPushMatrix();
		GL11.glDisable(3553);
		GL11.glEnable(2848);
		GL11.glEnable(2881);
		GL11.glEnable(2832);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glHint(3154, 4354);
		GL11.glHint(3155, 4354);
		GL11.glHint(3153, 4354);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glLineWidth(1.0F);
		GL11.glBegin(3);
		double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosX;
		double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosY;
		double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosZ;
		setColor(rgb);
		double pix2 = 6.283185307179586D;
		int[] counter = { 1 };
		for (int i = 0; i <= 100; i++) {
			GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586D / 45.0D), y, z + rad * Math.sin(i * 6.283185307179586D / 45.0D));
			counter[0] -= 1;
		}
		GL11.glEnd();
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GL11.glEnable(2848);
		GL11.glEnable(2881);
		GL11.glEnable(2832);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glHint(3154, 4354);
		GL11.glHint(3155, 4354);
		GL11.glHint(3153, 4354);
		GL11.glEnable(3553);
		GL11.glPopMatrix();
	}

	public static void setColor(final int colorHex) {
		final float alpha = (colorHex >> 24 & 0xFF) / 255.0f;
		final float red = (colorHex >> 16 & 0xFF) / 255.0f;
		final float green = (colorHex >> 8 & 0xFF) / 255.0f;
		final float blue = (colorHex & 0xFF) / 255.0f;
		GL11.glColor4f(red, green, blue, (alpha == 0.0f) ? 1.0f : alpha);
	}

	public static void drawAura(Entity entity, float partialTicks, double rad) {
		GL11.glPushMatrix();
		GL11.glDisable(3553);
		GL11.glEnable(2848);
		GL11.glEnable(2881);
		GL11.glEnable(2832);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glHint(3154, 4354);
		GL11.glHint(3155, 4354);
		GL11.glHint(3153, 4354);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glLineWidth(1.0F);
		GL11.glBegin(3);
		double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosX;
		double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosY;
		double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosZ;
		float r = 0.003921569F * Color.WHITE.getRed();
		float g = 0.003921569F * Color.WHITE.getGreen();
		float b = 0.003921569F * Color.WHITE.getBlue();
		double pix2 = 6.283185307179586D;
		int[] counter = { 1 };
		for (int i = 0; i <= 120; i++) {
			GL11.glColor4f(255, 255, 255, 255);
			GL11.glVertex3d(x + rad * Math.cos(i * 6.483185307179586D / 45.0D), y, z + rad * Math.sin(i * 6.483185307179586D / 45.0D));
			GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586D / 45.0D), y, z + rad * Math.sin(i * 6.283185307179586D / 45.0D));
			counter[0] -= 1;
		}
		GL11.glEnd();
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GL11.glEnable(2848);
		GL11.glEnable(2881);
		GL11.glEnable(2832);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glHint(3154, 4354);
		GL11.glHint(3155, 4354);
		GL11.glHint(3153, 4354);
		GL11.glEnable(3553);
		GL11.glPopMatrix();
	}

	public static void drawRainbowRectVertical(int x, int y, int x1, int y1, int segments, float alpha) {
		if (segments < 1)
			segments = 1;
		if (segments > y1 - y)
			segments = y1 - y;
		int segmentLength = (y1 - y) / segments;
		long time = System.nanoTime();
		for (int i = 0; i < segments; i++)
			mc.ingameGUI.drawGradientRect(x, y + segmentLength * i - 1, x1, y + (segmentLength + 1) * i, Colors.rainbow(time, i * 0.1F, alpha).getRGB(), Colors.rainbow(time, (i + 0.1F) * 0.1F, alpha).getRGB());
	}

	public static int getRainbow(int speed, int offset) {
		float hue = (float) ((System.currentTimeMillis() + (long) offset) % (long) speed);
		hue /= (float) speed;
		return Color.getHSBColor(hue, 1.0F, 1.0F).getRGB();
	}

	public static void drawCircle(final Entity entity, final float partialTicks, final double rad) {
		GL11.glPushMatrix();
		GL11.glDisable(3553);
		GLUtils.startSmooth();
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glLineWidth(0.01f);
		GL11.glBegin(3);
		final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
		final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
		final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;
		final float r = 0.003921569f * Color.WHITE.getRed();
		final float g = 0.003921569f * Color.WHITE.getGreen();
		final float b = 0.003921569f * Color.WHITE.getBlue();
		if (test <= 10) {
			if (anim) {
				animtest += 0.01F;
			} else {
				animtest -= 0.01F;
			}
			test = 10;
		}
		test--;
		if (animtest <= y) {
			anim = true;
		} else if (animtest >= y + entity.getEyeHeight() + 0.25) {
			anim = false;
		}
		for (int i = 0; i <= 90; ++i) {
			GL11.glColor4f(r, g, b, i);
			GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586 / 45.0), animtest, z + rad * Math.sin(i * 6.283185307179586 / 45.0));
		}
		GL11.glEnd();
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GLUtils.endSmooth();
		GL11.glEnable(3553);
		GL11.glPopMatrix();
	}

	public static void startDrawing() {
		GL11.glEnable(3042);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		Minecraft.getMinecraft().entityRenderer.setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks, 0);
	}

	public static void stopDrawing() {
		GL11.glDisable(3042);
		GL11.glEnable(3553);
		GL11.glDisable(2848);
		GL11.glDisable(3042);
		GL11.glEnable(2929);
	}

	public static int rainbow2(int delay) {
		double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
		rainbowState %= 360;
		return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
	}

	public static Color setblue(long offset, float fade) {
		float hue = (float) (System.nanoTime() * -5L + offset) / 1.0E10F % 1.0F;
		long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
		Color c = new Color((int) color);
		return new Color(c.getRed() / 128.0F * fade, c.getGreen() / 0.0F * fade, c.getBlue() / 128.0F * fade);
	}

	public static Color effect(long offset, float brightness, int speed) {
		float hue = (float) (System.nanoTime() + (offset * speed)) / 1.0E10F % 1.0F;
		long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, brightness, 1F)).intValue()), 16);
		Color c = new Color((int) color);
		return new Color(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, c.getAlpha() / 255.0F);
	}

	public static Color setRainbow(long offset, float fade) {
		float hue = (float) (System.nanoTime() * -5L + offset) / 1.0E10F % 1.0F;
		long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
		Color c = new Color((int) color);
		return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade);

	}

	public static Color setBlueBow(long offset, float fade) {
		float hue = (float) (System.nanoTime() * -2 + offset) / 1.0E10F % 1.0F;
		long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 0.6F, 1.0F)).intValue()), 16);
		Color c = new Color((int) color);
		return new Color(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F);

	}

	public static int rainbow(int delay) {
		double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
		rainbowState %= 360;
		return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
	}

	public static void drawRainbowRectHorizontal(int x, int y, int x1, int y1, int segments, float alpha) {
		if (segments < 1)
			segments = 1;
		if (segments > x1 - x)
			segments = x1 - x;
		int segmentLength = (x1 - x) / segments;
		long time = System.nanoTime();
		for (int i = 0; i < segments; i++)
			drawRect(x + segmentLength * i, y, x + (segmentLength + 1) * i, y1, Colors.rainbow(time, i, alpha).getRGB());
	}

	public static void drawCustomString(String text, float x, float y, int color, boolean shadow, float scale) {
		GlStateManager.pushMatrix();
		GlStateManager.scale(scale, scale, scale);
		mc.fontRendererObj.drawString(text, x, y, color, shadow);
		GlStateManager.popMatrix();
	}

	public static void drawRectWithEdge(double x, double y, double width, double height, Color color, Color color2) {
		drawRect(x, y, x + width, y + height, color.getRGB());
		int c = color2.getRGB();
		drawRect(x - 1.0D, y, x, y + height, c);
		drawRect(x + width, y, x + width + 1.0D, y + height, c);
		drawRect(x - 1.0D, y - 1.0D, x + width + 1.0D, y, c);
		drawRect(x - 1.0D, y + height, x + width + 1.0D, y + height + 1.0D, c);
	}

	public static void drawLine3D(float x, float y, float z, float x1, float y1, float z1, int color) {
		pre3D();
		GL11.glLoadIdentity();
		mc.entityRenderer.orientCamera(mc.timer.renderPartialTicks);
		float var11 = (color >> 24 & 0xFF) / 255.0F;
		float var6 = (color >> 16 & 0xFF) / 255.0F;
		float var7 = (color >> 8 & 0xFF) / 255.0F;
		float var8 = (color & 0xFF) / 255.0F;
		GL11.glColor4f(var6, var7, var8, var11);
		GL11.glLineWidth(0.5f);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glVertex3d(x, y, z);
		GL11.glVertex3d(x1, y1, z1);
		GL11.glEnd();
		post3D();
	}

	public static void drawLine(float x, float y, float x2, float y2, Color color) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldrenderer = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GL11.glEnable(2848);
		GL11.glLineWidth(1.0F);
		GlStateManager.color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
		worldrenderer.begin(1, DefaultVertexFormats.POSITION);
		worldrenderer.pos(x, y, 0.0D).endVertex();
		worldrenderer.pos(x2, y2, 0.0D).endVertex();
		tessellator.draw();
		GL11.glDisable(2848);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void prepareScissorBox(float x, float y, float x2, float y2) {
		ScaledResolution scale = new ScaledResolution(mc);
		int factor = scale.getScaleFactor();
		GL11.glScissor((int) (x * factor), (int) ((scale.getScaledHeight() - y2) * factor), (int) ((x2 - x) * factor), (int) ((y2 - y) * factor));
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

		float f3 = (float) (color >> 24 & 255) / 255.0F;
		float f = (float) (color >> 16 & 255) / 255.0F;
		float f1 = (float) (color >> 8 & 255) / 255.0F;
		float f2 = (float) (color & 255) / 255.0F;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.color(f, f1, f2, f3);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(left, bottom, 0.0D).endVertex();
		vertexbuffer.pos(right, bottom, 0.0D).endVertex();
		vertexbuffer.pos(right, top, 0.0D).endVertex();
		vertexbuffer.pos(left, top, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
		drawRect(x + width, y + width, x1 - width, y1 - width, internalColor);
		drawRect(x + width, y, x1 - width, y + width, borderColor);
		drawRect(x, y, x + width, y1, borderColor);
		drawRect(x1 - width, y, x1, y1, borderColor);
		drawRect(x + width, y1 - width, x1 - width, y1, borderColor);
	}

	public static void drawBorderRect(double x, double y, double x1, double y1, int color, double lwidth) {
		drawHLine(x, y, x1, y, (float) lwidth, color);
		drawHLine(x1, y, x1, y1, (float) lwidth, color);
		drawHLine(x, y1, x1, y1, (float) lwidth, color);
		drawHLine(x, y1, x, y, (float) lwidth, color);
	}

	public static void drawBorderedRect(final double x, final double y, final double x2, final double y2, final double width, final int color1, final int color2) {
		drawRect(x, y, x2, y2, color2);
		drawBorderRect(x, y, x2, y2, color1, width);
//        glEnable(GL_BLEND);
//        glDisable(GL_TEXTURE_2D);
//        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//        glEnable(GL_LINE_SMOOTH);
//        glPushMatrix();
//        glColor(color1);
//        glLineWidth((float) width);
//        glBegin(1);
//        glVertex2d(x, y);
//        glVertex2d(x, y2);
//        glVertex2d(x2, y2);
//        glVertex2d(x2, y);
//        glVertex2d(x, y);
//        glVertex2d(x2, y);
//        glVertex2d(x, y2);
//        glVertex2d(x2, y2);
//        glEnd();
//        glPopMatrix();
//        glEnable(GL_TEXTURE_2D);
//        glDisable(GL_BLEND);
//        glDisable(GL_LINE_SMOOTH);
	}

	public static void drawCustomRoundedRect(float x, float y, float x2, float y2, final float round, final int color) {
		GlStateManager.disableBlend();
		x += (float) (round / 2.0f + 0.0);
		y += (float) (round / 2.0f + 0.0);
		x2 -= (float) (round / 2.0f + 0.0);
		y2 -= (float) (round / 2.0f + 0.0);
		drawCircle(x2 - round / 2.0f, y + round / 2.0f, round, 0, 90, color);
		drawCircle(x + round / 2.0f, y + round / 2.0f, round, 90, 180, color);
		drawCircle(x + round / 2.0f, y2 - round / 2.0f, round, 180, 270, color);
		drawCircle(x2 - round / 2.0f, y2 - round / 2.0f, round, 270, 360, color);
		drawRect(x - round / 2.0f, y + round / 2.0f, x2, y2 - round / 2.0f, color);
		drawRect(x2 + round / 2.0f - round / 2.0f, y + round / 2.0f, x2 + round / 2.0f, y2 - round / 2.0f, color);
		drawRect(x + round / 2.0f, y - round / 2.0f, x2 - round / 2.0f, y + round / 2.0f, color);
		drawRect(x + round / 2.0f, y2 - round / 2.0f + 0.0f, x2 - round / 2.0f, y2 + round / 2.0f + 0.0f, color);
		GlStateManager.disableBlend();
	}

	public static void drawRoundedRect(double x, double y, double x1, double y1, int borderC, int insideC) {
		drawRect(x + 0.5F, y, x1 - 0.5F, y + 0.5F, insideC);
		drawRect(x + 0.5F, y1 - 0.5F, x1 - 0.5F, y1, insideC);
		drawRect(x, y + 0.5F, x1, y1 - 0.5F, insideC);
	}

	public static void drawRoundedRect(int xCoord, int yCoord, int xSize, int ySize, int colour) {
		int width = xCoord + xSize;
		int height = yCoord + ySize;
		drawRect(xCoord + 1, yCoord, width - 1, height, colour);
		drawRect(xCoord, yCoord + 1, width, height - 1, colour);
	}

	public static void drawFace(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight, AbstractClientPlayer target) {
		try {
			ResourceLocation skin = target.getLocationSkin();
			Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1, 1, 1, 1);
			Gui.drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
			GL11.glDisable(GL11.GL_BLEND);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void drawTriangle(double[] points, float r, float g, float b, float a) {
		GL11.glPushAttrib(GL_ALL_ATTRIB_BITS);
		GL11.glColor4f(r, g, b, a);
		GL11.glBegin(GL_TRIANGLES);
		GL11.glVertex2d(points[0], points[1]);
		GL11.glVertex2d(points[2], points[3]);
		GL11.glVertex2d(points[4], points[5]);
		GL11.glEnd();
		GL11.glPopAttrib();
	}

	public static void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor) {
		float f = (float) (startColor >> 24 & 255) / 255.0F;
		float f1 = (float) (startColor >> 16 & 255) / 255.0F;
		float f2 = (float) (startColor >> 8 & 255) / 255.0F;
		float f3 = (float) (startColor & 255) / 255.0F;
		float f4 = (float) (endColor >> 24 & 255) / 255.0F;
		float f5 = (float) (endColor >> 16 & 255) / 255.0F;
		float f6 = (float) (endColor >> 8 & 255) / 255.0F;
		float f7 = (float) (endColor & 255) / 255.0F;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos(right, top, zLevel).color(f1, f2, f3, f).endVertex();
		bufferbuilder.pos(left, top, zLevel).color(f1, f2, f3, f).endVertex();
		bufferbuilder.pos(left, bottom, zLevel).color(f5, f6, f7, f4).endVertex();
		bufferbuilder.pos(right, bottom, zLevel).color(f5, f6, f7, f4).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	public static void drawGradient(double x, double y, double x2, double y2, int col1, int col2) {
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
		GL11.glEnable(2848);
		GL11.glShadeModel(7425);

		GL11.glPushMatrix();
		GL11.glBegin(7);
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y);

		GL11.glColor4f(f5, f6, f7, f4);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
		GL11.glShadeModel(7424);
	}

	public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
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
		GL11.glEnable(2848);
		GL11.glShadeModel(7425);

		GL11.glPushMatrix();
		GL11.glBegin(7);
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glVertex2d(left, top);
		GL11.glVertex2d(left, bottom);

		GL11.glColor4f(f5, f6, f7, f4);
		GL11.glVertex2d(right, bottom);
		GL11.glVertex2d(right, top);
		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
		GL11.glShadeModel(7424);
		GL11.glColor4d(255, 255, 255, 255);
	}

	public static void drawCircle2(float cx, float cy, float r, int num_segments, int c) {
		GL11.glPushMatrix();
		cx *= 2.0F;
		cy *= 2.0F;
		float f = (c >> 24 & 0xFF) / 255.0F;
		float f1 = (c >> 16 & 0xFF) / 255.0F;
		float f2 = (c >> 8 & 0xFF) / 255.0F;
		float f3 = (c & 0xFF) / 255.0F;
		float theta = (float) (6.2831852D / num_segments);
		float p = (float) Math.cos(theta);
		float s = (float) Math.sin(theta);
		float x = r *= 2.0F;
		float y = 0.0F;
		enableGL2D();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glBegin(2);
		int ii = 0;
		while (ii < num_segments) {
			GL11.glVertex2f(x + cx, y + cy);
			float t = x;
			x = p * x - s * y;
			y = s * t + p * y;
			ii++;
		}
		GL11.glEnd();
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		disableGL2D();
		GlStateManager.color(1, 1, 1, 1);
		GL11.glPopMatrix();
	}

	public static void drawCircle(final double x, final double y, final float radius, final int startPi, final int endPi, final int c) {
		final float f = (c >> 24 & 0xFF) / 255.0f;
		final float f2 = (c >> 16 & 0xFF) / 255.0f;
		final float f3 = (c >> 8 & 0xFF) / 255.0f;
		final float f4 = (c & 0xFF) / 255.0f;
		GL11.glColor4f(f2, f3, f4, f);
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GL11.glDisable(3553);
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.alphaFunc(516, 0.001f);
		final Tessellator tess = Tessellator.getInstance();
		final BufferBuilder render = tess.getBuffer();
		for (double i = startPi; i < endPi; ++i) {
			final double cs = i * 3.141592653589793 / 180.0;
			final double ps = (i - 1.0) * 3.141592653589793 / 180.0;
			final double[] outer = { Math.cos(cs) * radius, -Math.sin(cs) * radius, Math.cos(ps) * radius, -Math.sin(ps) * radius };
			render.begin(6, DefaultVertexFormats.POSITION);
			render.pos(x + outer[2], y + outer[3], 0.0).endVertex();
			render.pos(x + outer[0], y + outer[1], 0.0).endVertex();
			render.pos(x, y, 0.0).endVertex();
			tess.draw();
		}
		GlStateManager.color(0.0f, 0.0f, 0.0f);
		GlStateManager.disableBlend();
		GlStateManager.alphaFunc(516, 0.1f);
		GlStateManager.disableAlpha();
		GL11.glEnable(3553);
	}

	public static void drawHLine(double x, double y, double x1, double y1, float width, int color) {
		float var11 = (color >> 24 & 0xFF) / 255.0F;
		float var6 = (color >> 16 & 0xFF) / 255.0F;
		float var7 = (color >> 8 & 0xFF) / 255.0F;
		float var8 = (color & 0xFF) / 255.0F;
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(var6, var7, var8, var11);
		glPushMatrix();
		glLineWidth(width);
		glBegin(GL_LINE_STRIP);
		glVertex2d(x, y);
		glVertex2d(x1, y1);
		glEnd();
		glLineWidth(1);
		glPopMatrix();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GlStateManager.color(1, 1, 1, 1);
	}

	public static void drawFilledTriangle(float x, float y, float r, int c, int borderC) {
		enableGL2D();
		glColor(c);
		glEnable(GL_POLYGON_SMOOTH);
		glBegin(GL_TRIANGLES);
		glVertex2f(x + r / 2, y + r / 2);
		glVertex2f(x + r / 2, y - r / 2);
		glVertex2f(x - r / 2, y);
		glEnd();
		glLineWidth(1.3f);
		glColor(borderC);
		glBegin(GL_LINE_STRIP);
		glVertex2f(x + r / 2, y + r / 2);
		glVertex2f(x + r / 2, y - r / 2);
		glEnd();
		glBegin(GL_LINE_STRIP);
		glVertex2f(x - r / 2, y);
		glVertex2f(x + r / 2, y - r / 2);
		glEnd();
		glBegin(GL_LINE_STRIP);
		glVertex2f(x + r / 2, y + r / 2);
		glVertex2f(x - r / 2, y);
		glEnd();
		glDisable(GL_POLYGON_SMOOTH);
		disableGL2D();
	}

	public static void drawCornerRect(double left, double top, double right, double bottom, int color, int otherColor, int corner) {
		float alpha = (color >> 24 & 0xFF) / 255.0F;
		float red = (color >> 16 & 0xFF) / 255.0F;
		float green = (color >> 8 & 0xFF) / 255.0F;
		float blue = (color & 0xFF) / 255.0F;
		float alpha2 = (otherColor >> 24 & 0xFF) / 255.0F;
		float red2 = (otherColor >> 16 & 0xFF) / 255.0F;
		float green2 = (otherColor >> 8 & 0xFF) / 255.0F;
		float blue2 = (otherColor & 0xFF) / 255.0F;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldrenderer = tessellator.getBuffer();
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		if (corner == 0) {
			worldrenderer.pos(right, top, zLevel).color(red, green, blue, alpha).endVertex();
			worldrenderer.pos(left, top, zLevel).color(red2, green2, blue2, alpha2).endVertex();
			worldrenderer.pos(left, bottom, zLevel).color(red, green, blue, alpha).endVertex();
			worldrenderer.pos(right, bottom, zLevel).color(red, green, blue, alpha).endVertex();
		} else if (corner == 1) {
			worldrenderer.pos(right, top, zLevel).color(red2, green2, blue2, alpha2).endVertex();
			worldrenderer.pos(left, top, zLevel).color(red, green, blue, alpha).endVertex();
			worldrenderer.pos(left, bottom, zLevel).color(red, green, blue, alpha).endVertex();
			worldrenderer.pos(right, bottom, zLevel).color(red, green, blue, alpha).endVertex();
		} else if (corner == 2) {
			worldrenderer.pos(right, top, zLevel).color(red, green, blue, alpha).endVertex();
			worldrenderer.pos(left, top, zLevel).color(red, green, blue, alpha).endVertex();
			worldrenderer.pos(left, bottom, zLevel).color(red2, green2, blue2, alpha2).endVertex();
			worldrenderer.pos(right, bottom, zLevel).color(red, green, blue, alpha).endVertex();
		} else if (corner == 3) {
			worldrenderer.pos(right, top, zLevel).color(red, green, blue, alpha).endVertex();
			worldrenderer.pos(left, top, zLevel).color(red, green, blue, alpha).endVertex();
			worldrenderer.pos(left, bottom, zLevel).color(red, green, blue, alpha).endVertex();
			worldrenderer.pos(right, bottom, zLevel).color(red2, green2, blue2, alpha2).endVertex();
		} else {
			throw new IndexOutOfBoundsException("corner value must be between 0 and 3");
		}
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	public static void drawImage(final ResourceLocation image, final int x, final int y, final int width, final int height) {
		final ScaledResolution scaledResolution = new ScaledResolution(mc);
		GL11.glDisable(2929);
		GL11.glEnable(3042);
		GL11.glDepthMask(false);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(image);
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (float) width, (float) height);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GL11.glEnable(2929);
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

	public static void drawBoundingBox(final AxisAlignedBB axisalignedbb) {
		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder worldrender = Tessellator.getInstance().getBuffer();
		worldrender.begin(7, DefaultVertexFormats.POSITION);
		worldrender.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
		worldrender.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
		tessellator.draw();
	}

	public static void drawImg(ResourceLocation loc, double posX, double posY, double width, double height) {
		mc.getTextureManager().bindTexture(loc);
		float f = 1.0F / (float) width;
		float f1 = 1.0F / (float) height;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldrenderer = tessellator.getBuffer();
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
		worldrenderer.pos(posX, posY + height, 0.0D).tex((0.0F * f), ((0.0F + (float) height) * f1)).endVertex();
		worldrenderer.pos(posX + width, posY + height, 0.0D).tex(((0.0F + (float) width) * f), ((0.0F + (float) height) * f1)).endVertex();
		worldrenderer.pos(posX + width, posY, 0.0D).tex(((0.0F + (float) width) * f), (0.0F * f1)).endVertex();
		worldrenderer.pos(posX, posY, 0.0D).tex((0.0F * f), (0.0F * f1)).endVertex();
		tessellator.draw();
	}

	public static void glColor(final int hex) {
		float alpha = (hex >> 24 & 0xFF) / 255F;
		float red = (hex >> 16 & 0xFF) / 255F;
		float green = (hex >> 8 & 0xFF) / 255F;
		float blue = (hex & 0xFF) / 255F;
		glColor4f(red, green, blue, alpha);
	}

	public static void glColor(final int hex, final float alpha) {
		float red = (hex >> 16 & 0xFF) / 255F;
		float green = (hex >> 8 & 0xFF) / 255F;
		float blue = (hex & 0xFF) / 255F;
		glColor4f(red, green, blue, alpha);
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

	public static boolean isInViewFrustrum(Entity entity) {
		return (isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck);
	}

	private static boolean isInViewFrustrum(AxisAlignedBB bb) {
		Entity current = mc.getRenderViewEntity();
		frustrum.setPosition(current.posX, current.posY, current.posZ);
		return frustrum.isBoundingBoxInFrustum(bb);
	}

	public static double getDiff(double lastI, double i, float ticks, double ownI) {
		return lastI + (i - lastI) * ticks - ownI;
	}

	public static void drawCircle5(Entity entity, float partialTicks, double rad) {
		float wid = (float) Zamorozka.settingsManager.getSettingByName("CircleWidth").getValDouble();
		GL11.glPushMatrix();
		GL11.glDisable(3553);
		GLUtils.startSmooth();
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glLineWidth(wid);
		GL11.glBegin(10);
		double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
		double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
		double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;
		int[] counter = { 1 };
		for (int i = 0; i <= 90; i++) {
			GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586D / 45.0D), y, z + rad * Math.sin(i * 6.283185307179586D / 45D));
			counter[0] -= 1;
		}
		GL11.glEnd();
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GLUtils.endSmooth();
		GL11.glEnable(3553);
		GL11.glPopMatrix();
	}

	public static void drawRomb(Entity entity, float partialTicks, double rad) {
		float wid = (float) Zamorozka.settingsManager.getSettingByName("CircleWidth").getValDouble();
		GL11.glPushMatrix();
		GL11.glDisable(3553);
		GLUtils.startSmooth();
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glLineWidth(wid);
		GL11.glBegin(2);
		double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
		double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
		double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;
		int[] counter = { 1 };
		for (int i = 0; i <= 90; i++) {
			GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586D / 7.0D), y, z + rad * Math.sin(i * 6.283185307179586D / 7.0D));
			counter[0] -= 1;
		}
		GL11.glEnd();
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GLUtils.endSmooth();
		GL11.glEnable(3553);
		GL11.glPopMatrix();
	}

	public static void drawTriangle(Entity entity, float partialTicks, double rad) {
		float wid = (float) Zamorozka.settingsManager.getSettingByName("CircleWidth").getValDouble();
		GL11.glPushMatrix();
		GL11.glDisable(3553);
		GLUtils.startSmooth();
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glLineWidth(wid);
		GL11.glBegin(2);
		double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
		double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
		double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;
		int[] counter = { 1 };
		for (int i = 0; i <= 90; i++) {
			GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586D / 3.0D), y, z + rad * Math.sin(i * 6.283185307179586D / 3.0D));
			counter[0] -= 1;
		}
		GL11.glEnd();
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GLUtils.endSmooth();
		GL11.glEnable(3553);
		GL11.glPopMatrix();
	}

	public static int getHealthColor(final EntityLivingBase player) {
		final float f = player.getHealth();
		final float f2 = player.getMaxHealth();
		final float f3 = Math.max(0.0f, Math.min(f, f2) / f2);
		return Color.HSBtoRGB(f3 / 3.0f, 1.0f, 0.75f) | 0xFF000000;
	}

	public static void drawFilledBBESP(final AxisAlignedBB axisalignedbb, final int color) {
		GL11.glPushMatrix();
		final float red = (color >> 24 & 0xFF) / 255.0f;
		final float green = (color >> 16 & 0xFF) / 255.0f;
		final float blue = (color >> 8 & 0xFF) / 255.0f;
		final float alpha = (color & 0xFF) / 255.0f;
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(2896);
		GL11.glDisable(3553);
		GL11.glEnable(2848);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		drawFilledBox(axisalignedbb);
		GL11.glDisable(2848);
		GL11.glEnable(3553);
		GL11.glEnable(2896);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}

	public static void drawBoundingBoxESP(final AxisAlignedBB axisalignedbb, final float width, final int color) {
		GL11.glPushMatrix();
		final float red = (color >> 24 & 0xFF) / 255.0f;
		final float green = (color >> 16 & 0xFF) / 255.0f;
		final float blue = (color >> 8 & 0xFF) / 255.0f;
		final float alpha = (color & 0xFF) / 255.0f;
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(2896);
		GL11.glDisable(3553);
		GL11.glEnable(2848);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glLineWidth(width);
		GL11.glColor4f(red, green, blue, alpha);
		drawOutlinedBox(axisalignedbb);
		GL11.glLineWidth(1.0f);
		GL11.glDisable(2848);
		GL11.glEnable(3553);
		GL11.glEnable(2896);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
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

	public static ScaledResolution newScaledResolution() {
		return new ScaledResolution(Minecraft.getMinecraft());
	}

	public static void color(int color, float alpha) {
		float red = (color >> 16 & 0xFF) / 255.0F;
		float green = (color >> 8 & 0xFF) / 255.0F;
		float blue = (color & 0xFF) / 255.0F;
		GL11.glColor4f(red, green, blue, alpha);
	}

	public static void renderTwo() {
		GL11.glStencilFunc(GL11.GL_NEVER, 0, 0xF);
		GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
	}

	public static void renderThree() {
		GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xF);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
	}

	public static void renderFour(EntityLivingBase base) {
		setColor(getTeamColor(base));
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_POLYGON_OFFSET_LINE);
		GL11.glPolygonOffset(1.0F, -2000000F);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
	}

	public static void renderFour(int color) {
		setColor(color);
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_POLYGON_OFFSET_LINE);
		GL11.glPolygonOffset(1.0F, -2000000F);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
	}

	public static int getTeamColor(Entity player) {
		int var2 = 16777215;

		if (player instanceof EntityPlayer) {
			ScorePlayerTeam var6 = (ScorePlayerTeam) ((EntityPlayer) player).getTeam();

			if (var6 != null) {
				String var7 = FontRenderer.getFormatFromString(var6.getColorPrefix());

				if (var7.length() >= 2) {
					var2 = mc.fontRendererObj.getColorCode(var7.charAt(1));
				}
			}
		}

		return var2;
	}

	public static void renderFive() {
		GL11.glPolygonOffset(1.0F, 2000000F);
		GL11.glDisable(GL11.GL_POLYGON_OFFSET_LINE);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_STENCIL_TEST);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glPopAttrib();
	}

	public static void checkSetupFBO() {
		final Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
		if (fbo != null && fbo.depthBuffer > -1) {
			setupFBO(fbo);
			fbo.depthBuffer = -1;
		}
	}

	public static void setupFBO(final Framebuffer fbo) {
		EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
		final int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
		EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
		EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
		EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
	}

	public static void renderOne() {
		RenderingUtils.checkSetupFBO();
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glLineWidth(3);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
		GL11.glClearStencil(0xF);
		GL11.glStencilFunc(GL11.GL_NEVER, 1, 0xF);
		GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
	}

    public static void drawIcon(float x, float y, int sizex, int sizey, ResourceLocation resourceLocation) {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GL11.glTranslatef((float)x, (float)y, (float)0.0f);
        RenderingUtils.drawScaledRect(0, 0, 0.0f, 0.0f, sizex, sizey, sizex, sizey, sizex, sizey);
        GlStateManager.disableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.disableRescaleNormal();
        GL11.glDisable((int)2848);
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }


    public static void drawScaledRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        Gui.drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
    }
    
}
