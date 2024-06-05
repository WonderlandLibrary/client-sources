package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NewRenderUtil {
	private static final Frustum frustum = new Frustum();
	public static float delta;
	public static Minecraft mc = Minecraft.getMinecraft();

	public NewRenderUtil() {
		super();
	}

	public static void rectangle(double left, double top, double right, double bottom, int color) {
		if (left < right) {
			double var5 = left;
			left = right;
			right = var5;
		}
		if (top < bottom) {
			double var5 = top;
			top = bottom;
			bottom = var5;
		}
		float var11 = (color >> 24 & 0xFF) / 255.0F;
		float var6 = (color >> 16 & 0xFF) / 255.0F;
		float var7 = (color >> 8 & 0xFF) / 255.0F;
		float var8 = (color & 0xFF) / 255.0F;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(var6, var7, var8, var11);
		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(left, bottom, 0.0D).endVertex();
		worldRenderer.pos(right, bottom, 0.0D).endVertex();
		worldRenderer.pos(right, top, 0.0D).endVertex();
		worldRenderer.pos(left, top, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public static void drawScaledRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height,
			float tileWidth, float tileHeight) {
		Gui.drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
	}

	public static void drawIcon(float x, float y, int sizex, int sizey, ResourceLocation resourceLocation) {
		GL11.glPushMatrix();
		Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glEnable((int) 2848);
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableAlpha();
		GlStateManager.alphaFunc(516, 0.1f);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(770, 771);
		GL11.glTranslatef((float) x, (float) y, (float) 0.0f);
		NewRenderUtil.drawScaledRect(0, 0, 0.0f, 0.0f, sizex, sizey, sizex, sizey, sizex, sizey);
		GlStateManager.disableAlpha();
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableLighting();
		GlStateManager.disableRescaleNormal();
		GL11.glDisable((int) 2848);
		GlStateManager.disableBlend();
		GL11.glPopMatrix();
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

	public static void drawFullCircle(int cx, int cy, double r, int segments, float lineWidth, int part, int c) {
		GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
		r *= 2.0;
		cx *= 2;
		cy *= 2;
		float f2 = (float) (c >> 24 & 255) / 255.0f;
		float f22 = (float) (c >> 16 & 255) / 255.0f;
		float f3 = (float) (c >> 8 & 255) / 255.0f;
		float f4 = (float) (c & 255) / 255.0f;
		GL11.glEnable((int) 3042);
		GL11.glLineWidth((float) lineWidth);
		GL11.glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glColor4f((float) f22, (float) f3, (float) f4, (float) f2);
		GL11.glBegin((int) 3);
		int i = segments - part;
		while (i <= segments) {
			double x = Math.sin((double) i * 3.141592653589793 / 180.0) * r;
			double y = Math.cos((double) i * 3.141592653589793 / 180.0) * r;
			GL11.glVertex2d((double) ((double) cx + x), (double) ((double) cy + y));
			++i;
		}
		GL11.glEnd();
		GL11.glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		GL11.glDisable((int) 3042);
		GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
	}

	public static void drawCircle(double x, double y, double radius, int c) {
		float f2 = (float) (c >> 24 & 255) / 255.0f;
		float f22 = (float) (c >> 16 & 255) / 255.0f;
		float f3 = (float) (c >> 8 & 255) / 255.0f;
		float f4 = (float) (c & 255) / 255.0f;
		GlStateManager.alphaFunc(516, 0.001f);
		GlStateManager.color(f22, f3, f4, f2);
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		Tessellator tes = Tessellator.getInstance();
		double i = 0.0;
		while (i < 360.0) {
			double f5 = Math.sin(i * 3.141592653589793 / 180.0) * radius;
			double f6 = Math.cos(i * 3.141592653589793 / 180.0) * radius;
			GL11.glVertex2d((double) ((double) f3 + x), (double) ((double) f4 + y));
			i += 1.0;
		}
		GlStateManager.disableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.enableTexture2D();
		GlStateManager.alphaFunc(516, 0.1f);
	}

	public static void drawCircle(float cx, float cy, float r, int num_segments, int c) {
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

	public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor,
			int borderColor) {
		rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		rectangle(x + width, y, x1 - width, y + width, borderColor);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		rectangle(x, y, x + width, y1, borderColor);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		rectangle(x1 - width, y, x1, y1, borderColor);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		rectangle(x + width, y1 - width, x1 - width, y1, borderColor);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
		float f = (float) (col1 >> 24 & 255) / 255.0f;
		float f1 = (float) (col1 >> 16 & 255) / 255.0f;
		float f2 = (float) (col1 >> 8 & 255) / 255.0f;
		float f3 = (float) (col1 & 255) / 255.0f;
		float f4 = (float) (col2 >> 24 & 255) / 255.0f;
		float f5 = (float) (col2 >> 16 & 255) / 255.0f;
		float f6 = (float) (col2 >> 8 & 255) / 255.0f;
		float f7 = (float) (col2 & 255) / 255.0f;
		GL11.glEnable((int) 3042);
		GL11.glDisable((int) 3553);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glEnable((int) 2848);
		GL11.glShadeModel((int) 7425);
		GL11.glPushMatrix();
		GL11.glBegin((int) 7);
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glVertex2d((double) left, (double) top);
		GL11.glVertex2d((double) left, (double) bottom);
		GL11.glColor4f((float) f5, (float) f6, (float) f7, (float) f4);
		GL11.glVertex2d((double) right, (double) bottom);
		GL11.glVertex2d((double) right, (double) top);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable((int) 3553);
		GL11.glDisable((int) 3042);
		GL11.glDisable((int) 2848);
		GL11.glShadeModel((int) 7424);
	}

	public static void color(int color) {
		float f = (float) (color >> 24 & 255) / 255.0f;
		float f1 = (float) (color >> 16 & 255) / 255.0f;
		float f2 = (float) (color >> 8 & 255) / 255.0f;
		float f3 = (float) (color & 255) / 255.0f;
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
	}

	public static int width() {
		return new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth();
	}

	public static void drawImage(ResourceLocation image, float x, float y, int width, int height) {
		ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		GlStateManager.color(0, 0, 0);
		GL11.glColor4f(0, 0, 0, 0);
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(1, 1, 1, 1);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		Minecraft.getMinecraft().getTextureManager().bindTexture(image);
//			GL11.glBegin(GL11.GL_QUADS);
		Gui.drawModalRectWithCustomSizedTexture((int) x, (int) y, 0.0f, 0.0f, width, height, width, height);
//			GL11.glEnd();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void drawRectShadow(float x, float y, float x2, float y2) {
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
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
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

	public static void entityESPBox(EntityPlayer e, Color color, Render3DEvent event) {
		double posX = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double) event.getTicks()
				- Minecraft.getMinecraft().getRenderManager().getRenderPosX();
		double posY = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double) event.getTicks()
				- Minecraft.getMinecraft().getRenderManager().getRenderPosY();
		double posZ = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double) event.getTicks()
				- Minecraft.getMinecraft().getRenderManager().getRenderPosZ();
		AxisAlignedBB box = AxisAlignedBB.fromBounds(posX - (double) e.width, posY, posZ - (double) e.width,
				posX + (double) e.width, posY + (double) e.height + 0.2, posZ + (double) e.width);
		if (e instanceof EntityLivingBase) {
			box = AxisAlignedBB.fromBounds(posX - (double) e.width + 0.2, posY, posZ - (double) e.width + 0.2,
					posX + (double) e.width - 0.2, posY + (double) e.height + (e.isSneaking() ? 0.02 : 0.2),
					posZ + (double) e.width - 0.2);
		}
		GL11.glLineWidth(1.0f);
		GL11.glColor4f((float) ((float) color.getRed() / 255.0f), (float) ((float) color.getGreen() / 255.0f),
				(float) ((float) color.getBlue() / 255.0f), (float) 1f);
		NewRenderUtil.drawOutlinedBoundingBox(box);
	}

	public static int createShader(String shaderCode, int shaderType) throws Exception {
		int shader;
		block4: {
			shader = 0;
			try {
				shader = ARBShaderObjects.glCreateShaderObjectARB((int) shaderType);
				if (shader != 0) {
					break block4;
				}
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

	public static void glColor(int hex) {
		float alpha = (hex >> 24 & 0xFF) / 255.0F;
		float red = (hex >> 16 & 0xFF) / 255.0F;
		float green = (hex >> 8 & 0xFF) / 255.0F;
		float blue = (hex & 0xFF) / 255.0F;
		GL11.glColor4f(red, green, blue, alpha);
	}

	public static String getShaderCode(InputStreamReader file) {
		String shaderSource = "";
		try {
			String line;
			BufferedReader reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {
				shaderSource = String.valueOf(shaderSource) + line + "\n";
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return shaderSource.toString();
	}

	public static void pre3D() {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDepthMask(false);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
	}

	public static int height() {
		return new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight();
	}

	public static double interpolate(double newPos, double oldPos) {
		return oldPos + (newPos - oldPos) * (double) mc.timer.renderPartialTicks;
	}

	public static void drawRect666(double d, double e, double f2, double f3, double red, double green, double blue,
			double alpha) {
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GL11.glPushMatrix();
		GL11.glLineWidth((float) 4.5f);
		GL11.glBegin((int) 7);
		GL11.glVertex2d((double) f2, (double) e);
		GL11.glVertex2d((double) d, (double) e);
		GL11.glVertex2d((double) d, (double) f3);
		GL11.glVertex2d((double) f2, (double) f3);
		GL11.glEnd();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GL11.glPopMatrix();
	}

	public static boolean isInFrustumView(Entity ent) {
		Entity current = Minecraft.getMinecraft().getRenderViewEntity();
		double x = NewRenderUtil.interpolate(current.posX, current.lastTickPosX);
		double y = NewRenderUtil.interpolate(current.posY, current.lastTickPosY);
		double z = NewRenderUtil.interpolate(current.posZ, current.lastTickPosZ);
		frustum.setPosition(x, y, z);
		if (!frustum.isBoundingBoxInFrustum(ent.getEntityBoundingBox()) && !ent.ignoreFrustumCheck) {
			return false;
		}
		return true;
	}

	public static double getAnimationState(double animation, double finalState, double speed) {
		float add = (float) (0.01 * speed);
		if (animation < finalState) {
			if (animation + add < finalState) {
				animation += add;
			} else {
				animation = finalState;
			}
		} else {
			if (animation - add > finalState) {
				animation -= add;
			} else {
				animation = finalState;
			}
		}
		return animation;
	}

	public static double interpolation(final double newPos, final double oldPos) {
		return oldPos + (newPos - oldPos) * mc.timer.renderPartialTicks;
	}

	public static int getHexRGB(final int hex) {
		return 0xFF000000 | hex;
	}

	public static void drawRect(float g, double d, double x2, double e, int col1) {
		float f2 = (float) (col1 >> 24 & 255) / 255.0f;
		float f22 = (float) (col1 >> 16 & 255) / 255.0f;
		float f3 = (float) (col1 >> 8 & 255) / 255.0f;
		float f4 = (float) (col1 & 255) / 255.0f;
		GL11.glPushMatrix();
		GL11.glEnable((int) 3042);
		GL11.glDisable((int) 3553);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glEnable((int) 2848);
		GL11.glColor4f((float) f22, (float) f3, (float) f4, (float) f2);
		GL11.glBegin((int) 7);
		GL11.glVertex2d((double) x2, (double) d);
		GL11.glVertex2d((double) g, (double) d);
		GL11.glVertex2d((double) g, (double) e);
		GL11.glVertex2d((double) x2, (double) e);
		GL11.glEnd();
		GL11.glEnable((int) 3553);
		GL11.glDisable((int) 3042);
		GL11.glDisable((int) 2848);
		GL11.glPopMatrix();
	}

	public static void drawFilledESP(Entity entity, Color color) {
		double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks
				- Minecraft.getMinecraft().getRenderManager().getRenderPosX();
		double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks
				- Minecraft.getMinecraft().getRenderManager().getRenderPosY();
		double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks
				- Minecraft.getMinecraft().getRenderManager().getRenderPosZ();
		final double width = entity.getEntityBoundingBox().maxX - entity.getEntityBoundingBox().minX;
		final double height = entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY + 0.25;
		drawEntityESP(x, y, z, width, height, color.getRed() / 255.0f, color.getGreen() / 255.0f,
				color.getBlue() / 255.0f, 0.5f, 1.0f, 1.0f, 1.0f, 1.0f, 2.0f);
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
		NewRenderUtil.drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
		GL11.glLineWidth((float) lineWdith);
		GL11.glColor4f((float) lineRed, (float) lineGreen, (float) lineBlue, (float) lineAlpha);
		NewRenderUtil
				.drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
		GL11.glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 3042);
		GL11.glPopMatrix();
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

	public static void drawCustomImage(int x, int y, int width, int height, ResourceLocation image) {
		ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		GL11.glDisable((int) 2929);
		GL11.glEnable((int) 3042);
		GL11.glDepthMask((boolean) false);
		OpenGlHelper.glBlendFunc((int) 770, (int) 771, (int) 1, (int) 0);
		GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(image);
		Gui.drawModalRectWithCustomSizedTexture((int) x, (int) y, (float) 0.0f, (float) 0.0f, (int) width, (int) height,
				(float) width, (float) height);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 3042);
		GL11.glEnable((int) 2929);
	}

	public static void drawBorderedRect(final float x, final float y, final float x2, final float y2, final float l1,
			final int col1, final int col2) {
		Gui.drawRect((int) x, (int) y, (int) x2, (int) y2, col2);
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

	public static void drawFilledCircle(float xx, float yy, float radius, int col) {
		float f = (col >> 24 & 0xFF) / 255.0F;
		float f1 = (col >> 16 & 0xFF) / 255.0F;
		float f2 = (col >> 8 & 0xFF) / 255.0F;
		float f3 = (col & 0xFF) / 255.0F;

		int sections = 50;
		double dAngle = 6.283185307179586D / sections;

		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glBegin(6);
		for (int i = 0; i < sections; i++) {
			float x = (float) (radius * Math.sin(i * dAngle));
			float y = (float) (radius * Math.cos(i * dAngle));

			GL11.glColor4f(f1, f2, f3, f);
			GL11.glVertex2f(xx + x, yy + y);
		}
		GlStateManager.color(0.0F, 0.0F, 0.0F);
		GL11.glEnd();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
		GL11.glPopMatrix();
	}

	public static void pre() {
		GL11.glDisable(2929);
		GL11.glDisable(3553);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
	}

	public static void post() {
		GL11.glDisable(3042);
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glColor3d(1.0, 1.0, 1.0);
	}

	public static void stopDrawing() {
		GL11.glDisable(3042);
		GL11.glEnable(3553);
		GL11.glDisable(2848);
		GL11.glDisable(3042);
		GL11.glEnable(2929);
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

	public static void setupRender(final boolean start) {
		if (start) {
			GlStateManager.enableBlend();
			GL11.glEnable(2848);
			GlStateManager.disableDepth();
			GlStateManager.disableTexture2D();
			GlStateManager.blendFunc(770, 771);
			GL11.glHint(3154, 4354);
		} else {
			GlStateManager.disableBlend();
			GlStateManager.enableTexture2D();
			GL11.glDisable(2848);
			GlStateManager.enableDepth();
		}
		GlStateManager.depthMask(!start);
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
		NewRenderUtil.drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		GL11.glDepthMask((boolean) true);
		GL11.glDisable((int) 3042);
		GL11.glPopMatrix();
	}

	public static class R2DUtils {
		public static void enableGL2D() {
			GL11.glDisable((int) 2929);
			GL11.glEnable((int) 3042);
			GL11.glDisable((int) 3553);
			GL11.glBlendFunc((int) 770, (int) 771);
			GL11.glDepthMask((boolean) true);
			GL11.glEnable((int) 2848);
			GL11.glHint((int) 3154, (int) 4354);
			GL11.glHint((int) 3155, (int) 4354);
		}

		public static void disableGL2D() {
			GL11.glEnable((int) 3553);
			GL11.glDisable((int) 3042);
			GL11.glEnable((int) 2929);
			GL11.glDisable((int) 2848);
			GL11.glHint((int) 3154, (int) 4352);
			GL11.glHint((int) 3155, (int) 4352);
		}

		public static void draw2DCorner(Entity e, double posX, double posY, double posZ, int color) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(posX, posY, posZ);
			GL11.glNormal3f((float) 0.0f, (float) 0.0f, (float) 0.0f);
			GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
			GlStateManager.scale(-0.1, -0.1, 0.1);
			GL11.glDisable((int) 2896);
			GL11.glDisable((int) 2929);
			GL11.glEnable((int) 3042);
			GL11.glBlendFunc((int) 770, (int) 771);
			GlStateManager.depthMask(true);
			R2DUtils.drawRect(7.0, -20.0, 7.300000190734863, -17.5, color);
			R2DUtils.drawRect(-7.300000190734863, -20.0, -7.0, -17.5, color);
			R2DUtils.drawRect(4.0, -20.299999237060547, 7.300000190734863, -20.0, color);
			R2DUtils.drawRect(-7.300000190734863, -20.299999237060547, -4.0, -20.0, color);
			R2DUtils.drawRect(-7.0, 3.0, -4.0, 3.299999952316284, color);
			R2DUtils.drawRect(4.0, 3.0, 7.0, 3.299999952316284, color);
			R2DUtils.drawRect(-7.300000190734863, 0.8, -7.0, 3.299999952316284, color);
			R2DUtils.drawRect(7.0, 0.5, 7.300000190734863, 3.299999952316284, color);
			R2DUtils.drawRect(7.0, -20.0, 7.300000190734863, -17.5, color);
			R2DUtils.drawRect(-7.300000190734863, -20.0, -7.0, -17.5, color);
			R2DUtils.drawRect(4.0, -20.299999237060547, 7.300000190734863, -20.0, color);
			R2DUtils.drawRect(-7.300000190734863, -20.299999237060547, -4.0, -20.0, color);
			R2DUtils.drawRect(-7.0, 3.0, -4.0, 3.299999952316284, color);
			R2DUtils.drawRect(4.0, 3.0, 7.0, 3.299999952316284, color);
			R2DUtils.drawRect(-7.300000190734863, 0.8, -7.0, 3.299999952316284, color);
			R2DUtils.drawRect(7.0, 0.5, 7.300000190734863, 3.299999952316284, color);
			GL11.glDisable((int) 3042);
			GL11.glEnable((int) 2929);
			GlStateManager.popMatrix();
		}

		public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
			R2DUtils.enableGL2D();
			GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
			R2DUtils.drawVLine(x *= 2.0f, (y *= 2.0f) + 1.0f, (y1 *= 2.0f) - 2.0f, borderC);
			R2DUtils.drawVLine((x1 *= 2.0f) - 1.0f, y + 1.0f, y1 - 2.0f, borderC);
			R2DUtils.drawHLine(x + 2.0f, x1 - 3.0f, y, borderC);
			R2DUtils.drawHLine(x + 2.0f, x1 - 3.0f, y1 - 1.0f, borderC);
			R2DUtils.drawHLine(x + 1.0f, x + 1.0f, y + 1.0f, borderC);
			R2DUtils.drawHLine(x1 - 2.0f, x1 - 2.0f, y + 1.0f, borderC);
			R2DUtils.drawHLine(x1 - 2.0f, x1 - 2.0f, y1 - 2.0f, borderC);
			R2DUtils.drawHLine(x + 1.0f, x + 1.0f, y1 - 2.0f, borderC);
			R2DUtils.drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
			GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
			R2DUtils.disableGL2D();
			Gui.drawRect(0, 0, 0, 0, 0);
		}

		public static void drawRoundedRect1(float x, float y, float x2, float y2, float round, int color) {
			x = (float) ((double) x + ((double) (round / 2.0f) + 0.5));
			y = (float) ((double) y + ((double) (round / 2.0f) + 0.5));
			x2 = (float) ((double) x2 - ((double) (round / 2.0f) + 0.5));
			y2 = (float) ((double) y2 - ((double) (round / 2.0f) + 0.5));
			Gui.drawRect((int) x, (int) y, (int) x2, (int) y2, color);
			circle(x2 - round / 2.0f, y + round / 2.0f, round, color);
			circle(x + round / 2.0f, y2 - round / 2.0f, round, color);
			circle(x + round / 2.0f, y + round / 2.0f, round, color);
			circle(x2 - round / 2.0f, y2 - round / 2.0f, round, color);
			Gui.drawRect((int) (x - round / 2.0f - 0.5f), (int) (y + round / 2.0f), (int) (x2),
					(int) (y2 - round / 2.0f), color);
			Gui.drawRect((int) (x), (int) (y + round / 2.0f), (int) (x2 + round / 2.0f + 0.5f),
					(int) (y2 - round / 2.0f), color);
			Gui.drawRect((int) (x + round / 2.0f), (int) (y - round / 2.0f - 0.5f), (int) (x2 - round / 2.0f),
					(int) (y2 - round / 2.0f), color);
			Gui.drawRect((int) (x + round / 2.0f), (int) (y), (int) (x2 - round / 2.0f),
					(int) (y2 + round / 2.0f + 0.5f), color);
		}

		public static void drawRect(double x2, double y2, double x1, double y1, int color) {
			R2DUtils.enableGL2D();
			R2DUtils.glColor(color);
			R2DUtils.drawRect(x2, y2, x1, y1);
			R2DUtils.disableGL2D();
		}

		private static void drawRect(double x2, double y2, double x1, double y1) {
			GL11.glBegin((int) 7);
			GL11.glVertex2d((double) x2, (double) y1);
			GL11.glVertex2d((double) x1, (double) y1);
			GL11.glVertex2d((double) x1, (double) y2);
			GL11.glVertex2d((double) x2, (double) y2);
			GL11.glEnd();
		}

		public static void glColor(int hex) {
			float alpha = (float) (hex >> 24 & 255) / 255.0f;
			float red = (float) (hex >> 16 & 255) / 255.0f;
			float green = (float) (hex >> 8 & 255) / 255.0f;
			float blue = (float) (hex & 255) / 255.0f;
			GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		}

		public static void drawRect(float x, float y, float x1, float y1, int color) {
			R2DUtils.enableGL2D();
			glColor(color);
			R2DUtils.drawRect(x, y, x1, y1);
			R2DUtils.disableGL2D();
		}

		public static void drawBorderedRect(float x, float y, float x1, float y1, float width, int borderColor) {
			R2DUtils.enableGL2D();
			glColor(borderColor);
			R2DUtils.drawRect(x + width, y, x1 - width, y + width);
			R2DUtils.drawRect(x, y, x + width, y1);
			R2DUtils.drawRect(x1 - width, y, x1, y1);
			R2DUtils.drawRect(x + width, y1 - width, x1 - width, y1);
			R2DUtils.disableGL2D();
		}

		public static void drawBorderedRect(float x, float y, float x1, float y1, int insideC, int borderC) {
			R2DUtils.enableGL2D();
			GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
			R2DUtils.drawVLine(x *= 2.0f, y *= 2.0f, y1 *= 2.0f, borderC);
			R2DUtils.drawVLine((x1 *= 2.0f) - 1.0f, y, y1, borderC);
			R2DUtils.drawHLine(x, x1 - 1.0f, y, borderC);
			R2DUtils.drawHLine(x, x1 - 2.0f, y1 - 1.0f, borderC);
			R2DUtils.drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
			GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
			R2DUtils.disableGL2D();
		}

		public static void circle(float x, float y, float radius, int fill) {
			arc(x, y, 0.0F, 360.0F, radius, fill);
		}

		public static void circle(float x, float y, float radius, Color fill) {
			arc(x, y, 0.0F, 360.0F, radius, fill);
		}

		public static void arc(float x, float y, float start, float end, float radius, int color) {
			arcEllipse(x, y, start, end, radius, radius, color);
		}

		public static void arc(float x, float y, float start, float end, float radius, Color color) {
			arcEllipse(x, y, start, end, radius, radius, color);
		}

		public static void arcEllipse(float x, float y, float start, float end, float w, float h, int color) {
			GlStateManager.color(0.0F, 0.0F, 0.0F);
			GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
			float temp = 0.0F;
			if (start > end) {
				temp = end;
				end = start;
				start = temp;
			}

			float var11 = (float) (color >> 24 & 255) / 255.0F;
			float var6 = (float) (color >> 16 & 255) / 255.0F;
			float var7 = (float) (color >> 8 & 255) / 255.0F;
			float var8 = (float) (color & 255) / 255.0F;
			Tessellator var9 = Tessellator.getInstance();
			BufferBuilder var10 = var9.getBuffer();
			GlStateManager.enableBlend();
			GlStateManager.disableTexture2D();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.color(var6, var7, var8, var11);
			float i;
			float ldx;
			float ldy;
			if (var11 > 0.5F) {
				GL11.glEnable(2848);
				GL11.glLineWidth(2.0F);
				GL11.glBegin(3);

				for (i = end; i >= start; i -= 4.0F) {
					ldx = (float) Math.cos((double) i * 3.141592653589793D / 180.0D) * w * 1.001F;
					ldy = (float) Math.sin((double) i * 3.141592653589793D / 180.0D) * h * 1.001F;
					GL11.glVertex2f(x + ldx, y + ldy);
				}

				GL11.glEnd();
				GL11.glDisable(2848);
			}

			GL11.glBegin(6);

			for (i = end; i >= start; i -= 4.0F) {
				ldx = (float) Math.cos((double) i * 3.141592653589793D / 180.0D) * w;
				ldy = (float) Math.sin((double) i * 3.141592653589793D / 180.0D) * h;
				GL11.glVertex2f(x + ldx, y + ldy);
			}

			GL11.glEnd();
			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();
		}

		public static void arcEllipse(float x, float y, float start, float end, float w, float h, Color color) {
			GlStateManager.color(0.0F, 0.0F, 0.0F);
			GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
			float temp = 0.0F;
			if (start > end) {
				temp = end;
				end = start;
				start = temp;
			}

			Tessellator var9 = Tessellator.getInstance();
			BufferBuilder var10 = var9.getBuffer();
			GlStateManager.enableBlend();
			GlStateManager.disableTexture2D();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.color((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F,
					(float) color.getBlue() / 255.0F, (float) color.getAlpha() / 255.0F);
			float i;
			float ldx;
			float ldy;
			if ((float) color.getAlpha() > 0.5F) {
				GL11.glEnable(2848);
				GL11.glLineWidth(2.0F);
				GL11.glBegin(3);

				for (i = end; i >= start; i -= 4.0F) {
					ldx = (float) Math.cos((double) i * 3.141592653589793D / 180.0D) * w * 1.001F;
					ldy = (float) Math.sin((double) i * 3.141592653589793D / 180.0D) * h * 1.001F;
					GL11.glVertex2f(x + ldx, y + ldy);
				}

				GL11.glEnd();
				GL11.glDisable(2848);
			}

			GL11.glBegin(6);

			for (i = end; i >= start; i -= 4.0F) {
				ldx = (float) Math.cos((double) i * 3.141592653589793D / 180.0D) * w;
				ldy = (float) Math.sin((double) i * 3.141592653589793D / 180.0D) * h;
				GL11.glVertex2f(x + ldx, y + ldy);
			}

			GL11.glEnd();
			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();
		}

		public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
			R2DUtils.enableGL2D();
			GL11.glShadeModel((int) 7425);
			GL11.glBegin((int) 7);
			glColor(topColor);
			GL11.glVertex2f((float) x, (float) y1);
			GL11.glVertex2f((float) x1, (float) y1);
			glColor(bottomColor);
			GL11.glVertex2f((float) x1, (float) y);
			GL11.glVertex2f((float) x, (float) y);
			GL11.glEnd();
			GL11.glShadeModel((int) 7424);
			R2DUtils.disableGL2D();
		}

		public static void drawHLine(float x, float y, float x1, int y1) {
			if (y < x) {
				float var5 = x;
				x = y;
				y = var5;
			}
			R2DUtils.drawRect(x, x1, y + 1.0f, x1 + 1.0f, y1);
		}

		public static void drawVLine(float x, float y, float x1, int y1) {
			if (x1 < y) {
				float var5 = y;
				y = x1;
				x1 = var5;
			}
			R2DUtils.drawRect(x, y + 1.0f, x + 1.0f, x1, y1);
		}

		public static void drawHLine(float x, float y, float x1, int y1, int y2) {
			if (y < x) {
				float var5 = x;
				x = y;
				y = var5;
			}
			R2DUtils.drawGradientRect(x, x1, y + 1.0f, x1 + 1.0f, y1, y2);
		}

		public static void drawRect(float x, float y, float x1, float y1) {
			GL11.glBegin((int) 7);
			GL11.glVertex2f((float) x, (float) y1);
			GL11.glVertex2f((float) x1, (float) y1);
			GL11.glVertex2f((float) x1, (float) y);
			GL11.glVertex2f((float) x, (float) y);
			GL11.glEnd();
		}
	}
}