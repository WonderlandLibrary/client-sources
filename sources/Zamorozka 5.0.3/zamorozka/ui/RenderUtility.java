package zamorozka.ui;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLU;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class RenderUtility {
	
    private static Frustum frustrum;
	public static float delta;
    public static void drawHollowBox(float x, float y, float x1, float y1, float thickness, int color) {
        RenderUtility.drawHorizontalLine(x, y, x1, thickness, color);
        RenderUtility.drawHorizontalLine(x, y1, x1, thickness, color);
        RenderUtility.drawVerticalLine(x, y, y1, thickness, color);
        RenderUtility.drawVerticalLine(x1 - thickness, y, y1, thickness, color);
    }
    public static void drawHorizontalLine(float x, float y, float x1, float thickness, int color) {
    	RenderUtility.drawRect2(x, y, x1, y + thickness, color);
    }

    public static void drawRect2(double x, double y, double x2, double y2, int color) {
    	RenderUtility.drawRect(x, y, x2, y2, color);
    }

    public static void drawVerticalLine(float x, float y, float y1, float thickness, int color) {
    	RenderUtility.drawRect2(x, y, x + thickness, y1, color);
    }
	public static double interpolate(double newPos, double oldPos) {
		return oldPos + (newPos - oldPos) * (double) Minecraft.getMinecraft().timer.renderPartialTicks;
	}
    public static boolean isInViewFrustrum(Entity entity) {
        return RenderUtility.isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }
    public static boolean isInViewFrustrum(AxisAlignedBB bb) {
        Entity current = Minecraft.getMinecraft().getRenderViewEntity();
        frustrum.setPosition(current.posX, current.posY, current.posZ);
        return frustrum.isBoundingBoxInFrustum(bb);
    }
	public static void pre() {
		GL11.glDisable((int) 2929);
		GL11.glDisable((int) 3553);
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
	}

	public static void post() {
		GL11.glDisable((int) 3042);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		GL11.glColor3d((double) 1.0, (double) 1.0, (double) 1.0);
	}
	public static void drawFastRoundedRect(final float x0, final float y0, final float x1, final float y1, final float radius, final int color) {
		final int Semicircle = 18;
		final float f = 90.0f / Semicircle;
		final float f2 = (color >> 24 & 0xFF) / 255.0f;
		final float f3 = (color >> 16 & 0xFF) / 255.0f;
		final float f4 = (color >> 8 & 0xFF) / 255.0f;
		final float f5 = (color & 0xFF) / 255.0f;
		GL11.glDisable(2884);
		GL11.glDisable(3553);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(f3, f4, f5, f2);
		GL11.glBegin(5);
		GL11.glVertex2f(x0 + radius, y0);
		GL11.glVertex2f(x0 + radius, y1);
		GL11.glVertex2f(x1 - radius, y0);
		GL11.glVertex2f(x1 - radius, y1);
		GL11.glEnd();
		GL11.glBegin(5);
		GL11.glVertex2f(x0, y0 + radius);
		GL11.glVertex2f(x0 + radius, y0 + radius);
		GL11.glVertex2f(x0, y1 - radius);
		GL11.glVertex2f(x0 + radius, y1 - radius);
		GL11.glEnd();
		GL11.glBegin(5);
		GL11.glVertex2f(x1, y0 + radius);
		GL11.glVertex2f(x1 - radius, y0 + radius);
		GL11.glVertex2f(x1, y1 - radius);
		GL11.glVertex2f(x1 - radius, y1 - radius);
		GL11.glEnd();
		GL11.glBegin(6);
		float f6 = x1 - radius;
		float f7 = y0 + radius;
		GL11.glVertex2f(f6, f7);
		int j = 0;
		for (j = 0; j <= Semicircle; ++j) {
			final float f8 = j * f;
			GL11.glVertex2f((float)(f6 + radius * Math.cos(Math.toRadians(f8))), (float)(f7 - radius * Math.sin(Math.toRadians(f8))));
		}
		GL11.glEnd();
		GL11.glBegin(6);
		f6 = x0 + radius;
		f7 = y0 + radius;
		GL11.glVertex2f(f6, f7);
		for (j = 0; j <= Semicircle; ++j) {
			final float f9 = j * f;
			GL11.glVertex2f((float)(f6 - radius * Math.cos(Math.toRadians(f9))), (float)(f7 - radius * Math.sin(Math.toRadians(f9))));
		}
		GL11.glEnd();
		GL11.glBegin(6);
		f6 = x0 + radius;
		f7 = y1 - radius;
		GL11.glVertex2f(f6, f7);
		for (j = 0; j <= Semicircle; ++j) {
			final float f10 = j * f;
			GL11.glVertex2f((float)(f6 - radius * Math.cos(Math.toRadians(f10))), (float)(f7 + radius * Math.sin(Math.toRadians(f10))));
		}
		GL11.glEnd();
		GL11.glBegin(6);
		f6 = x1 - radius;
		f7 = y1 - radius;
		GL11.glVertex2f(f6, f7);
		for (j = 0; j <= Semicircle; ++j) {
			final float f11 = j * f;
			GL11.glVertex2f((float)(f6 + radius * Math.cos(Math.toRadians(f11))), (float)(f7 + radius * Math.sin(Math.toRadians(f11))));
		}
		GL11.glEnd();
		GL11.glEnable(3553);
		GL11.glEnable(2884);
		GL11.glDisable(3042);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}
	public static int rainbow(int delay) {
		double rainbowState = Math.ceil((double) (System.currentTimeMillis() + (long) delay) / 20.0D);
		rainbowState %= 360.0D;
		return Color.getHSBColor((float) (rainbowState / 360.0D), 0.8F, 0.7F).brighter().getRGB();
	}

	public static Color rainbowEffect(int delay) {
		float hue = (float) (System.nanoTime() + delay) / 2.0E10F % 1.0F;
		Color color = new Color((int) Long
				.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16));
		return new Color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F,
				color.getAlpha() / 255.0F);
	}

	public static void drawFullscreenImage(ResourceLocation image) {
		ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		GL11.glDisable((int) 2929);
		GL11.glDepthMask((boolean) false);
		OpenGlHelper.glBlendFunc((int) 770, (int) 771, (int) 1, (int) 0);
		GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
		GL11.glDisable((int) 3008);
		Minecraft.getMinecraft().getTextureManager().bindTexture(image);
		Gui.drawModalRectWithCustomSizedTexture((int) 0, (int) 0, (float) 0.0f, (float) 0.0f,
				(int) scaledResolution.getScaledWidth(), (int) scaledResolution.getScaledHeight(),
				(float) scaledResolution.getScaledWidth(), (float) scaledResolution.getScaledHeight());
		GL11.glDepthMask((boolean) true);
		GL11.glEnable((int) 2929);
		GL11.glEnable((int) 3008);
		GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
	}

	public static void drawPlayerHead(String playerName, int x, int y, int width, int height) {
		for (Object player : Minecraft.getMinecraft().world.getLoadedEntityList()) {
			if (player instanceof EntityPlayer) {
				EntityPlayer ply = (EntityPlayer) player;

				if (playerName.equalsIgnoreCase(ply.getName())) {
					GameProfile profile = new GameProfile(ply.getUniqueID(), ply.getName());
					NetworkPlayerInfo networkplayerinfo1 = new NetworkPlayerInfo(profile);
					ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
					GL11.glDisable((int) 2929);
					GL11.glEnable((int) 3042);
					GL11.glDepthMask((boolean) false);
					OpenGlHelper.glBlendFunc(770, 771, 1, 0);
					GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
					Minecraft.getTextureManager().bindTexture(networkplayerinfo1.getLocationSkin());

					Gui.drawScaledCustomSizeModalRect(x, y, 8.0F, 8.0F, 8, 8, width, height, 64.0F, 64.0F);
					if (ply.isWearing(EnumPlayerModelParts.HAT)) {
						Gui.drawScaledCustomSizeModalRect(x, y, 40.0F, 8.0F, 8, 8, width, height, 64.0F, 64.0F);
					}
					// Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height,
					// width, height);
					GL11.glDepthMask((boolean) true);
					GL11.glDisable((int) 3042);
					GL11.glEnable((int) 2929);
				}
			}
		}
	}

	public static double getAnimationState(double animation, final double finalState, final double speed) {
		final float add = (float) (RenderUtility.delta * speed);
		if (animation < finalState) {
			if (animation + add < finalState) {
				animation += add;
			} else {
				animation = finalState;
			}
		} else if (animation - add > finalState) {
			animation -= add;
		} else {
			animation = finalState;
		}
		return animation;
	}

	public static void drawLoadingCircle() {
		float status = (float) ((double) System.currentTimeMillis() * 0.1 % 400.0);
		float size = 0.5f;
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		float radius = (float) res.getScaledWidth() / 16.0f;
		Gui.drawCircle((float) ((float) res.getScaledWidth() / 2.0f), (float) ((float) res.getScaledHeight() / 2.0f),
				(float) radius, (Color) new Color(FlatColors.DARK_GREY.c), (float) 5.0f, (float) 0.0f, (float) 1.0f);
		Gui.drawCircle((float) ((float) res.getScaledWidth() / 2.0f), (float) ((float) res.getScaledHeight() / 2.0f),
				(float) radius, (Color) new Color(FlatColors.BLUE.c), (float) 7.0f, (float) status, (float) size);
	}

	public static String getShaderCode(InputStreamReader file) {
		String shaderSource = "";
		try {
			String line;
			BufferedReader reader = new BufferedReader((Reader) file);
			while ((line = reader.readLine()) != null) {
				shaderSource = String.valueOf((Object) shaderSource) + line + "\n";
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit((int) -1);
		}
		return shaderSource.toString();
	}

	public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
		drawImage(image, x, y, width, height, 1.0f);
	}

	public static void drawImage(ResourceLocation image, int x, int y, int width, int height, float alpha) {
		ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		GL11.glDisable((int) 2929);
		GL11.glEnable((int) 3042);
		GL11.glDepthMask((boolean) false);
		OpenGlHelper.glBlendFunc((int) 770, (int) 771, (int) 1, (int) 0);
		GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, alpha);
		Minecraft.getMinecraft().getTextureManager().bindTexture(image);
		Gui.drawModalRectWithCustomSizedTexture((int) x, (int) y, (float) 0.0f, (float) 0.0f, (int) width, (int) height,
				(float) width, (float) height);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 3042);
		GL11.glEnable((int) 2929);
	}

	public static void drawOutlinedRect(int x, int y, int width, int height, int lineSize, Color lineColor,
			Color backgroundColor) {
		RenderUtility.drawRect(x, y, width, height, backgroundColor.getRGB());
		RenderUtility.drawRect(x, y, width, y + lineSize, lineColor.getRGB());
		RenderUtility.drawRect(x, height - lineSize, width, height, lineColor.getRGB());
		RenderUtility.drawRect(x, y + lineSize, x + lineSize, height - lineSize, lineColor.getRGB());
		RenderUtility.drawRect(width - lineSize, y + lineSize, width, height - lineSize, lineColor.getRGB());
	}

	public static void drawImage(ResourceLocation image, int x, int y, int width, int height, Color color) {
		ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		GL11.glDisable((int) 2929);
		GL11.glEnable((int) 3042);
		GL11.glDepthMask((boolean) false);
		OpenGlHelper.glBlendFunc((int) 770, (int) 771, (int) 1, (int) 0);
		GL11.glColor4f((float) ((float) color.getRed() / 255.0f), (float) ((float) color.getBlue() / 255.0f),
				(float) ((float) color.getRed() / 255.0f), (float) 1.0f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(image);
		Gui.drawModalRectWithCustomSizedTexture((int) x, (int) y, (float) 0.0f, (float) 0.0f, (int) width, (int) height,
				(float) width, (float) height);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 3042);
		GL11.glEnable((int) 2929);
	}

	public static void doGlScissor(int x, int y, int width, int height) {
		Minecraft mc = Minecraft.getMinecraft();
		int scaleFactor = 1;
		int k = mc.gameSettings.guiScale;
		if (k == 0) {
			k = 1000;
		}
		while (scaleFactor < k && mc.displayWidth / (scaleFactor + 1) >= 320
				&& mc.displayHeight / (scaleFactor + 1) >= 240) {
			++scaleFactor;
		}
		GL11.glScissor((int) (x * scaleFactor), (int) (mc.displayHeight - (y + height) * scaleFactor),
				(int) (width * scaleFactor), (int) (height * scaleFactor));
	}

	public static void drawRect(float x1, float y1, float x2, float y2, int color) {
		GL11.glPushMatrix();
		GL11.glEnable((int) 3042);
		GL11.glDisable((int) 3553);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glEnable((int) 2848);
		GL11.glPushMatrix();
		RenderUtility.color(color);
		GL11.glBegin((int) 7);
		GL11.glVertex2d((double) x2, (double) y1);
		GL11.glVertex2d((double) x1, (double) y1);
		GL11.glVertex2d((double) x1, (double) y2);
		GL11.glVertex2d((double) x2, (double) y2);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable((int) 3553);
		GL11.glDisable((int) 3042);
		GL11.glDisable((int) 2848);
		GL11.glPopMatrix();
	}

	public static void color(int color) {
		float f = (float) (color >> 24 & 255) / 255.0f;
		float f1 = (float) (color >> 16 & 255) / 255.0f;
		float f2 = (float) (color >> 8 & 255) / 255.0f;
		float f3 = (float) (color & 255) / 255.0f;
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
	}

	public static int createShader(String shaderCode, int shaderType) throws Exception {
		int shader;
		block4: {
			shader = 0;
			try {
				shader = ARBShaderObjects.glCreateShaderObjectARB((int) shaderType);
				if (shader != 0)
					break block4;
				return 0;
			} catch (Exception exc) {
				ARBShaderObjects.glDeleteObjectARB((int) shader);
				throw exc;
			}
		}
		ARBShaderObjects.glShaderSourceARB((int) shader, (CharSequence) shaderCode);
		ARBShaderObjects.glCompileShaderARB((int) shader);
		if (ARBShaderObjects.glGetObjectParameteriARB((int) shader, (int) 35713) == 0) {
			throw new RuntimeException("Error creating shader:");
		}
		return shader;
	}

	public void drawCircle(int x, int y, float radius, int color) {
		float alpha = (float) (color >> 24 & 255) / 255.0f;
		float red = (float) (color >> 16 & 255) / 255.0f;
		float green = (float) (color >> 8 & 255) / 255.0f;
		float blue = (float) (color & 255) / 255.0f;
		boolean blend = GL11.glIsEnabled((int) 3042);
		boolean line = GL11.glIsEnabled((int) 2848);
		boolean texture = GL11.glIsEnabled((int) 3553);
		if (!blend) {
			GL11.glEnable((int) 3042);
		}
		if (!line) {
			GL11.glEnable((int) 2848);
		}
		if (texture) {
			GL11.glDisable((int) 3553);
		}
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		GL11.glBegin((int) 9);
		int i = 0;
		while (i <= 360) {
			GL11.glVertex2d(
					(double) ((double) x + Math.sin((double) ((double) i * 3.141526 / 180.0)) * (double) radius),
					(double) ((double) y + Math.cos((double) ((double) i * 3.141526 / 180.0)) * (double) radius));
			++i;
		}
		GL11.glEnd();
		if (texture) {
			GL11.glEnable((int) 3553);
		}
		if (!line) {
			GL11.glDisable((int) 2848);
		}
		if (!blend) {
			GL11.glDisable((int) 3042);
		}
	}

	public static void outlineOne() {
		GL11.glPushAttrib((int) 1048575);
		GL11.glDisable((int) 3008);
		GL11.glDisable((int) 3553);
		GL11.glDisable((int) 2896);
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glLineWidth((float) 4.0f);
		GL11.glEnable((int) 2848);
		GL11.glEnable((int) 2960);
		GL11.glClear((int) 1024);
		GL11.glClearStencil((int) 15);
		GL11.glStencilFunc((int) 512, (int) 1, (int) 15);
		GL11.glStencilOp((int) 7681, (int) 7681, (int) 7681);
		GL11.glPolygonMode((int) 1032, (int) 6913);
	}

	public static void outlineTwo() {
		GL11.glStencilFunc((int) 512, (int) 0, (int) 15);
		GL11.glStencilOp((int) 7681, (int) 7681, (int) 7681);
		GL11.glPolygonMode((int) 1032, (int) 6914);
	}

	public static void outlineThree() {
		GL11.glStencilFunc((int) 514, (int) 1, (int) 15);
		GL11.glStencilOp((int) 7680, (int) 7680, (int) 7680);
		GL11.glPolygonMode((int) 1032, (int) 6913);
	}

	public static void outlineFour() {
		GL11.glDepthMask((boolean) false);
		GL11.glDisable((int) 2929);
		GL11.glEnable((int) 10754);
		GL11.glPolygonOffset((float) 1.0f, (float) -2000000.0f);
		GL11.glColor4f((float) 0.9529412f, (float) 0.6117647f, (float) 0.07058824f, (float) 1.0f);
		OpenGlHelper.setLightmapTextureCoords((int) OpenGlHelper.lightmapTexUnit, (float) 240.0f, (float) 240.0f);
	}

	public static void outlineFive() {
		GL11.glPolygonOffset((float) 1.0f, (float) 2000000.0f);
		GL11.glDisable((int) 10754);
		GL11.glEnable((int) 2929);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 2960);
		GL11.glDisable((int) 2848);
		GL11.glHint((int) 3154, (int) 4352);
		GL11.glEnable((int) 3042);
		GL11.glEnable((int) 2896);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 3008);
		GL11.glPopAttrib();
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

	public static void drawBoundingBox(AxisAlignedBB aa) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
		tessellator.draw();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
		tessellator.draw();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
		tessellator.draw();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
		tessellator.draw();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
		tessellator.draw();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
		worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
		tessellator.draw();
	}

	public static void drawOutlinedBlockESP(double x, double y, double z, float red, float green, float blue,
			float alpha, float lineWidth) {
		GL11.glPushMatrix();
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glDisable((int) 2929);
		GL11.glDepthMask((boolean) false);
		GL11.glLineWidth((float) lineWidth);
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		RenderUtility.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 3042);
		GL11.glPopMatrix();
	}

	public static void drawBlockESP(double x, double y, double z, float red, float green, float blue, float alpha,
			float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWidth) {
		GL11.glPushMatrix();
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDisable((int) 2896);
		GL11.glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glDisable((int) 2929);
		GL11.glDepthMask((boolean) false);
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		RenderUtility.drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glLineWidth((float) lineWidth);
		GL11.glColor4f((float) lineRed, (float) lineGreen, (float) lineBlue, (float) lineAlpha);
		RenderUtility.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2896);
		GL11.glEnable((int) 2929);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 3042);
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

	public static void drawOutlinedBox(AxisAlignedBB box) {
		if (box != null) {
			GL11.glBegin(3);
			GL11.glVertex3d(box.minX, box.minY, box.minZ);
			GL11.glVertex3d(box.maxX, box.minY, box.minZ);
			GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
			GL11.glVertex3d(box.minX, box.minY, box.maxZ);
			GL11.glVertex3d(box.minX, box.minY, box.minZ);
			GL11.glEnd();
			GL11.glBegin(3);
			GL11.glVertex3d(box.minX, box.maxY, box.minZ);
			GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
			GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
			GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
			GL11.glVertex3d(box.minX, box.maxY, box.minZ);
			GL11.glEnd();
			GL11.glBegin(1);
			GL11.glVertex3d(box.minX, box.minY, box.minZ);
			GL11.glVertex3d(box.minX, box.maxY, box.minZ);
			GL11.glVertex3d(box.maxX, box.minY, box.minZ);
			GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
			GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
			GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
			GL11.glVertex3d(box.minX, box.minY, box.maxZ);
			GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
			GL11.glEnd();
		}
	}

	
	public static void drawBlockESP(double x, double y, double z, int maincoolor, int borderColor, float lineWidth) {
        float alpha = (float)(maincoolor >> 24 & 255) / 255.0f;
        float red = (float)(maincoolor >> 16 & 255) / 255.0f;
        float green = (float)(maincoolor >> 8 & 255) / 255.0f;
        float blue = (float)(maincoolor & 255) / 255.0f;

        float lineAlpha = (float)(borderColor >> 24 & 255) / 255.0f;
        float lineRed = (float)(borderColor >> 16 & 255) / 255.0f;
        float lineGreen = (float)(borderColor >> 8 & 255) / 255.0f;
        float lineBlue = (float)(borderColor & 255) / 255.0f;

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
    
	public static void drawSolidBlockESP(double x, double y, double z, float red, float green, float blue,
			float alpha) {
		GL11.glPushMatrix();
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glDisable((int) 2929);
		GL11.glDepthMask((boolean) false);
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		RenderUtility.drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 3042);
		GL11.glPopMatrix();
	}

	public static void drawOutlinedEntityESP(double x, double y, double z, double width, double height, float red,
			float green, float blue, float alpha) {
		GL11.glPushMatrix();
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glDisable((int) 2929);
		GL11.glDepthMask((boolean) false);
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		RenderUtility
				.drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
		GL11.glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 3042);
		GL11.glPopMatrix();
	}

	public static void drawSolidEntityESP(double x, double y, double z, double width, double height, float red,
			float green, float blue, float alpha) {
		GL11.glPushMatrix();
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glDisable((int) 2929);
		GL11.glDepthMask((boolean) false);
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		RenderUtility.drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
		GL11.glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 3042);
		GL11.glPopMatrix();
	}

	public static void drawEntityESP(double x, double y, double z, double width, double height, float red, float green,
			float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith) {
		GL11.glPushMatrix();
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glDisable((int) 2929);
		GL11.glDepthMask((boolean) false);
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		RenderUtility.drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
		GL11.glLineWidth((float) lineWdith);
		GL11.glColor4f((float) lineRed, (float) lineGreen, (float) lineBlue, (float) lineAlpha);
		RenderUtility
				.drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
		GL11.glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 3042);
		GL11.glPopMatrix();
	}

	public static void drawTracerLine(double x, double y, double z, float red, float green, float blue, float alpha,
			float lineWdith) {
		GL11.glPushMatrix();
		GL11.glEnable((int) 3042);
		GL11.glEnable((int) 2848);
		GL11.glDisable((int) 2929);
		GL11.glDisable((int) 3553);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glEnable((int) 3042);
		GL11.glLineWidth((float) lineWdith);
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) 0.0, (double) (0.0 + (double) Minecraft.getMinecraft().player.getEyeHeight()),
				(double) 0.0);
		GL11.glVertex3d((double) x, (double) y, (double) z);
		GL11.glEnd();
		GL11.glDisable((int) 3042);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		GL11.glDisable((int) 2848);
		GL11.glDisable((int) 3042);
		GL11.glPopMatrix();
	}

	public static void drawFilledBox(AxisAlignedBB mask) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(mask.minX, mask.minY, mask.minZ).endVertex();
		worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
		tessellator.draw();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
		tessellator.draw();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
		tessellator.draw();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
		tessellator.draw();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
		tessellator.draw();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
		tessellator.draw();
	}

	public static void checkSetupFBO() {
		Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
		if (fbo != null && fbo.depthBuffer > -1) {
			EXTFramebufferObject.glDeleteRenderbuffersEXT((int) fbo.depthBuffer);
			int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
			EXTFramebufferObject.glBindRenderbufferEXT((int) 36161, (int) stencil_depth_buffer_ID);
			EXTFramebufferObject.glRenderbufferStorageEXT((int) 36161, (int) 34041,
					(int) Minecraft.getMinecraft().displayWidth, (int) Minecraft.getMinecraft().displayHeight);
			EXTFramebufferObject.glFramebufferRenderbufferEXT((int) 36160, (int) 36128, (int) 36161,
					(int) stencil_depth_buffer_ID);
			EXTFramebufferObject.glFramebufferRenderbufferEXT((int) 36160, (int) 36096, (int) 36161,
					(int) stencil_depth_buffer_ID);
			fbo.depthBuffer = -1;
		}
	}

	public static void drawRoundedRect(float x, float y, float x2, float y2, float round, int color) {
		x = (float) ((double) x + ((double) (round / 2.0f) + 0.5));
		y = (float) ((double) y + ((double) (round / 2.0f) + 0.5));
		x2 = (float) ((double) x2 - ((double) (round / 2.0f) + 0.5));
		y2 = (float) ((double) y2 - ((double) (round / 2.0f) + 0.5));
		Gui.drawRect((float) x, (float) y, (float) x2, (float) y2, (int) color);
		Gui.circle((float) (x2 - round / 2.0f), (float) (y + round / 2.0f), (float) round, (int) color);
		Gui.circle((float) (x + round / 2.0f), (float) (y2 - round / 2.0f), (float) round, (int) color);
		Gui.circle((float) (x + round / 2.0f), (float) (y + round / 2.0f), (float) round, (int) color);
		Gui.circle((float) (x2 - round / 2.0f), (float) (y2 - round / 2.0f), (float) round, (int) color);
		Gui.drawRect((float) (x - round / 2.0f - 0.5f), (float) (y + round / 2.0f), (float) x2,
				(float) (y2 - round / 2.0f), (int) color);
		Gui.drawRect((float) x, (float) (y + round / 2.0f), (float) (x2 + round / 2.0f + 0.5f),
				(float) (y2 - round / 2.0f), (int) color);
		Gui.drawRect((float) (x + round / 2.0f), (float) (y - round / 2.0f - 0.5f), (float) (x2 - round / 2.0f),
				(float) (y2 - round / 2.0f), (int) color);
		Gui.drawRect((float) (x + round / 2.0f), (float) y, (float) (x2 - round / 2.0f),
				(float) (y2 + round / 2.0f + 0.5f), (int) color);
	}

	FontRenderer font = Minecraft.getMinecraft().fontRendererObj;

	public void drawCenteredString(String text, float x, float y, int color) {
		drawString(text, (float) (x - font.getStringWidth(text) / 2), y, color);
	}

	public int drawString(String text, float x, float y, int color) {
		return font.drawString(text, (float) x, y, color, false);
	}

	public static void startDrawing() {
		GL11.glEnable((int) 3042);
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glEnable((int) 2848);
		GL11.glDisable((int) 3553);
		GL11.glDisable((int) 2929);
		Minecraft.getMinecraft().entityRenderer.setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks,
				0);
	}

	public static void stopDrawing() {
		GL11.glDisable((int) 3042);
		GL11.glEnable((int) 3553);
		GL11.glDisable((int) 2848);
		GL11.glDisable((int) 3042);
		GL11.glEnable((int) 2929);
	}

	public static void drawBorderedRect(final float x, final float y, final float x2, final float y2, final float l1,
			final int col1, final int col2) {
		Gui.drawRect(x, y, x2, y2, col2);
		final float f = (col1 >> 24 & 0xFF) / 255.0f;
		final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
		final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
		final float f4 = (col1 & 0xFF) / 255.0f;
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glPushMatrix();
		GL11.glColor4f(f2, f3, f4, f);
		GL11.glLineWidth(l1);
		GL11.glBegin(1);
		GL11.glVertex2d((double) x, (double) y);
		GL11.glVertex2d((double) x, (double) y2);
		GL11.glVertex2d((double) x2, (double) y2);
		GL11.glVertex2d((double) x2, (double) y);
		GL11.glVertex2d((double) x, (double) y);
		GL11.glVertex2d((double) x2, (double) y);
		GL11.glVertex2d((double) x, (double) y2);
		GL11.glVertex2d((double) x2, (double) y2);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
	}

	public static Color blend(final Color color1, final Color color2, final double ratio) {
		final float r = (float) ratio;
		final float ir = 1.0f - r;
		final float[] rgb1 = new float[3];
		final float[] rgb2 = new float[3];
		color1.getColorComponents(rgb1);
		color2.getColorComponents(rgb2);
		final Color color3 = new Color(rgb1[0] * r + rgb2[0] * ir, rgb1[1] * r + rgb2[1] * ir,
				rgb1[2] * r + rgb2[2] * ir);
		return color3;
	}

	public static void drawArc(float x1, float y1, double r, int color, int startPoint, double arc, int linewidth) {
		r *= 2.0D;
		x1 *= 2;
		y1 *= 2;
		float f = (color >> 24 & 0xFF) / 255.0F;
		float f1 = (color >> 16 & 0xFF) / 255.0F;
		float f2 = (color >> 8 & 0xFF) / 255.0F;
		float f3 = (color & 0xFF) / 255.0F;
		GL11.glDisable(2929);
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glDepthMask(true);
		GL11.glEnable(2848);
		GL11.glHint(3154, 4354);
		GL11.glHint(3155, 4354);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		GL11.glLineWidth(linewidth);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		for (int i = (int) startPoint; i <= arc; i += 1) {
			double x = Math.sin(i * 3.141592653589793D / 180.0D) * r;
			double y = Math.cos(i * 3.141592653589793D / 180.0D) * r;
			GL11.glVertex2d(x1 + x, y1 + y);
		}
		GL11.glEnd();
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glEnable(2929);
		GL11.glDisable(2848);
		GL11.glHint(3154, 4352);
		GL11.glHint(3155, 4352);
	}

	public static int getRainbow(int speed, int offset) {
		float hue = (float) ((System.currentTimeMillis() + (long) offset) % (long) speed);
		hue /= (float) speed;
		return Color.getHSBColor(hue, 0.75F, 1.0F).getRGB();
	}

	public static float[] getRGBAs(int rgb) {
		return new float[] { (float) (rgb >> 16 & 255) / 255.0F, (float) (rgb >> 8 & 255) / 255.0F,
				(float) (rgb & 255) / 255.0F, (float) (rgb >> 24 & 255) / 255.0F };
	}

	public static void drawBox(AxisAlignedBB bb) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		GL11.glEnd();
	}

	public static void renderEntity(Entity e, int color, int type) {
		if (e != null) {
			double x = e.lastTickPosX
					+ (e.posX - e.lastTickPosX) * (double) Minecraft.getMinecraft().timer.renderPartialTicks
					- Minecraft.getMinecraft().getRenderManager().viewerPosX;
			double y = e.lastTickPosY
					+ (e.posY - e.lastTickPosY) * (double) Minecraft.getMinecraft().timer.renderPartialTicks
					- Minecraft.getMinecraft().getRenderManager().viewerPosY;
			double z = e.lastTickPosZ
					+ (e.posZ - e.lastTickPosZ) * (double) Minecraft.getMinecraft().timer.renderPartialTicks
					- Minecraft.getMinecraft().getRenderManager().viewerPosZ;
			if (e instanceof EntityPlayer && ((EntityPlayer) e).hurtTime != 0) {
				color = Color.RED.getRGB();
			}

			float a;
			float r;
			float g;
			float b;
			if (type == 1) {
				GlStateManager.pushMatrix();
				GL11.glBlendFunc(770, 771);
				GL11.glEnable(3042);
				GL11.glDisable(3553);
				GL11.glDisable(2929);
				GL11.glDepthMask(false);
				GL11.glLineWidth(2.0F);
				a = (float) (color >> 24 & 255) / 255.0F;
				r = (float) (color >> 16 & 255) / 255.0F;
				g = (float) (color >> 8 & 255) / 255.0F;
				b = (float) (color & 255) / 255.0F;
				GL11.glColor4f(r, g, b, a);
				RenderGlobal.func_181561_a(new AxisAlignedBB(
						e.getEntityBoundingBox().minX - 0.05D - e.posX
								+ (e.posX - Minecraft.getMinecraft().getRenderManager().viewerPosX),
						e.getEntityBoundingBox().minY - e.posY
								+ (e.posY - Minecraft.getMinecraft().getRenderManager().viewerPosY),
						e.getEntityBoundingBox().minZ - 0.05D - e.posZ
								+ (e.posZ - Minecraft.getMinecraft().getRenderManager().viewerPosZ),
						e.getEntityBoundingBox().maxX + 0.05D - e.posX
								+ (e.posX - Minecraft.getMinecraft().getRenderManager().viewerPosX),
						e.getEntityBoundingBox().maxY + 0.1D - e.posY
								+ (e.posY - Minecraft.getMinecraft().getRenderManager().viewerPosY),
						e.getEntityBoundingBox().maxZ + 0.05D - e.posZ
								+ (e.posZ - Minecraft.getMinecraft().getRenderManager().viewerPosZ)));
				dbb(new AxisAlignedBB(
						e.getEntityBoundingBox().minX - 0.05D - e.posX
								+ (e.posX - Minecraft.getMinecraft().getRenderManager().viewerPosX),
						e.getEntityBoundingBox().minY - e.posY
								+ (e.posY - Minecraft.getMinecraft().getRenderManager().viewerPosY),
						e.getEntityBoundingBox().minZ - 0.05D - e.posZ
								+ (e.posZ - Minecraft.getMinecraft().getRenderManager().viewerPosZ),
						e.getEntityBoundingBox().maxX + 0.05D - e.posX
								+ (e.posX - Minecraft.getMinecraft().getRenderManager().viewerPosX),
						e.getEntityBoundingBox().maxY + 0.1D - e.posY
								+ (e.posY - Minecraft.getMinecraft().getRenderManager().viewerPosY),
						e.getEntityBoundingBox().maxZ + 0.05D - e.posZ
								+ (e.posZ - Minecraft.getMinecraft().getRenderManager().viewerPosZ)),
						r, g, b);
				GL11.glEnable(3553);
				GL11.glEnable(2929);
				GL11.glDepthMask(true);
				GL11.glDisable(3042);
				GlStateManager.popMatrix();
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			} else if (type == 2) {
				GL11.glBlendFunc(770, 771);
				GL11.glEnable(3042);
				GL11.glLineWidth(2.0F);
				GL11.glDisable(3553);
				GL11.glDisable(2929);
				GL11.glDepthMask(false);
				a = (float) (color >> 24 & 255) / 255.0F;
				r = (float) (color >> 16 & 255) / 255.0F;
				g = (float) (color >> 8 & 255) / 255.0F;
				b = (float) (color & 255) / 255.0F;
				GL11.glColor4d((double) r, (double) g, (double) b, (double) a);
				RenderGlobal.func_181561_a(new AxisAlignedBB(
						e.getEntityBoundingBox().minX - 0.05D - e.posX
								+ (e.posX - Minecraft.getMinecraft().getRenderManager().viewerPosX),
						e.getEntityBoundingBox().minY - e.posY
								+ (e.posY - Minecraft.getMinecraft().getRenderManager().viewerPosY),
						e.getEntityBoundingBox().minZ - 0.05D - e.posZ
								+ (e.posZ - Minecraft.getMinecraft().getRenderManager().viewerPosZ),
						e.getEntityBoundingBox().maxX + 0.05D - e.posX
								+ (e.posX - Minecraft.getMinecraft().getRenderManager().viewerPosX),
						e.getEntityBoundingBox().maxY + 0.1D - e.posY
								+ (e.posY - Minecraft.getMinecraft().getRenderManager().viewerPosY),
						e.getEntityBoundingBox().maxZ + 0.05D - e.posZ
								+ (e.posZ - Minecraft.getMinecraft().getRenderManager().viewerPosZ)));
				GL11.glEnable(3553);
				GL11.glEnable(2929);
				GL11.glDepthMask(true);
				GL11.glDisable(3042);
			} else if (type == 3) {
				GL11.glPushMatrix();
				GL11.glTranslated(x, y - 0.2D, z);
				GL11.glScalef(0.03F, 0.03F, 0.03F);
				GL11.glRotated((double) (-Minecraft.getMinecraft().getRenderManager().playerViewY), 0.0D, 1.0D, 0.0D);
				GlStateManager.disableDepth();
				drawRect(-20.0D, -1.0D, -26.0D, 75.0D, Color.black.getRGB());
				drawRect(-21.0D, 0.0D, -25.0D, 74.0D, color);
				drawRect(20.0D, -1.0D, 26.0D, 75.0D, Color.black.getRGB());
				drawRect(21.0D, 0.0D, 25.0D, 74.0D, color);
				drawRect(-20.0D, -1.0D, 21.0D, 5.0D, Color.black.getRGB());
				drawRect(-21.0D, 0.0D, 24.0D, 4.0D, color);
				drawRect(-20.0D, 70.0D, 21.0D, 75.0D, Color.black.getRGB());
				drawRect(-21.0D, 71.0D, 25.0D, 74.0D, color);
				GlStateManager.enableDepth();
				GL11.glPopMatrix();
			} else if (type == 4) {

			}

		}
	}

	public static void dbb(AxisAlignedBB abb, float r, float g, float b) {
		float a = 0.25F;
		Tessellator ts = Tessellator.getInstance();
		BufferBuilder vb = ts.getBuffer();
		vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
		vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
		vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
		vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
		vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
		vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
		vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, 0.25F).endVertex();
		ts.draw();
	}

	public static void drawRect(double left, double top, double right, double bottom, int color) {
		double f3;
		if (left < right) {
			f3 = left;
			left = right;
			right = f3;
		}

		if (top < bottom) {
			f3 = top;
			top = bottom;
			bottom = f3;
		}

		float f31 = (float) (color >> 24 & 255) / 255.0F;
		float f = (float) (color >> 16 & 255) / 255.0F;
		float f1 = (float) (color >> 8 & 255) / 255.0F;
		float f2 = (float) (color & 255) / 255.0F;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldrenderer = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(f, f1, f2, f31);
		worldrenderer.begin(7, DefaultVertexFormats.POSITION);
		worldrenderer.pos(left, bottom, 0.0D).endVertex();
		worldrenderer.pos(right, bottom, 0.0D).endVertex();
		worldrenderer.pos(right, top, 0.0D).endVertex();
		worldrenderer.pos(left, top, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void drawRoundedRect2(int x, int y, int width, int height, int radius, int slices, int color) {
		GL11.glPushMatrix();
		float maxRadius = Math.min(width / 2F, height / 2F);
		radius = (int) MathUtil.clamp(radius, 0, maxRadius);
		drawRect(x + radius, y, width - radius * 2, height, color);
		drawRect(x, y + radius, width, height - radius * 2, color);
		drawCylinder(x, y, 0, radius, slices, color);
		drawCylinder(x, y + height - radius * 2, 0, radius, slices, color);
		drawCylinder(x + width - radius * 2, y, 0, radius, slices, color);
		drawCylinder(x + width - radius * 2, y + height - radius * 2, 0, radius, slices, color);
		GL11.glPopMatrix();
	}

	public static void drawCylinder(float x, float y, float startRad, float endRad, int slices, int color) {
		GL11.glPushMatrix();
		Cylinder cylinder = new Cylinder();
		float r = ((color >> 16) & 255) / 255F;
		float g = ((color >> 8) & 255) / 255F;
		float b = (color & 255) / 255F;
		float a = ((color >> 24) & 255) / 255F;
		GL11.glTranslatef(x + endRad, y + endRad, 0);
		GL11.glColor4f(r, g, b, a);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(1);
		cylinder.setDrawStyle(GLU.GLU_SILHOUETTE);
		cylinder.draw(startRad, endRad, 0, slices, 1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glPopMatrix();
	}

	public static void drawRoundedRect(double x, double y, double width, double height, double radius, int color) {
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

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GL11.glBegin(GL11.GL_POLYGON);

		for (int i = 0; i <= 90; i += 3) {
			GL11.glVertex2d(x + radius + +(Math.sin((i * Math.PI / 180)) * (radius * -1)),
					y + radius + (Math.cos((i * Math.PI / 180)) * (radius * -1)));
		}

		for (int i = 90; i <= 180; i += 3) {
			GL11.glVertex2d(x + radius + (Math.sin((i * Math.PI / 180)) * (radius * -1)),
					y1 - radius + (Math.cos((i * Math.PI / 180)) * (radius * -1)));
		}

		for (int i = 0; i <= 90; i += 3) {
			GL11.glVertex2d(x1 - radius + (Math.sin((i * Math.PI / 180)) * radius),
					y1 - radius + (Math.cos((i * Math.PI / 180)) * radius));
		}

		for (int i = 90; i <= 180; i += 3) {
			GL11.glVertex2d(x1 - radius + (Math.sin((i * Math.PI / 180)) * radius),
					y + radius + (Math.cos((i * Math.PI / 180)) * radius));
		}

		GL11.glEnd();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glScaled(2, 2, 2);

		GL11.glPopAttrib();
		GL11.glColor4f(1, 1, 1, 1);

	}

}