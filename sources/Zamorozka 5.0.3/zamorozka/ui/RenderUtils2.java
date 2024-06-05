package zamorozka.ui;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import zamorozka.main.Zamorozka;
import zamorozka.modules.COMBAT.KillAura;

import java.awt.*;
import java.text.NumberFormat;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtils2 {
	
	 public static float delta;
	
	 private static final Color outline = new Color(0, 0, 0, 128);
	 private static final Color shadow1 = new Color(32, 32, 32, 192);
	 private static final Color shadow2 = new Color(0, 0, 0, 0);
	
	/**
	 * Renders a box with any size and any color.
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @param x2
	 * @param y2
	 * @param z2
	 * @param color
	 */
	public static void box(double x, double y, double z, double x2, double y2, double z2, float red, float green,
			float blue, float alpha) {
		x = x - Minecraft.getMinecraft().getRenderManager().renderPosX;
		y = y - Minecraft.getMinecraft().getRenderManager().renderPosY;
		z = z - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		x2 = x2 - Minecraft.getMinecraft().getRenderManager().renderPosX;
		y2 = y2 - Minecraft.getMinecraft().getRenderManager().renderPosY;
		z2 = z2 - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		drawColorBox(new AxisAlignedBB(x, y, z, x2, y2, z2), red, green, blue, alpha);
		GL11.glColor4d(0, 0, 0, 0.5F);
		drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x2, y2, z2));
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}
	

    public static void scissorBox2(int x, int y, int xend, int yend) {
        int width = xend - x;
        int height = yend - y;
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int factor = sr.getScaleFactor();
        int bottomY = Minecraft.getMinecraft().currentScreen.height - yend;
        GL11.glScissor((int)(x * factor), (int)(bottomY * factor), (int)(width * factor), (int)(height * factor));
    }

    public static void downShadow(double x1, double y1, double x2, double y2) {
        double yi1 = y1 + 0.1;
        RenderUtils2.setColor(outline);
        GL11.glLineWidth((float)1.0f);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)x1, (double)yi1);
        GL11.glVertex2d((double)x2, (double)yi1);
        GL11.glEnd();
        GL11.glBegin((int)9);
        RenderUtils2.setColor(shadow1);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glVertex2d((double)x2, (double)y1);
        RenderUtils2.setColor(shadow2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x1, (double)y2);
        GL11.glEnd();
    }

    public static void boxShadow(double x1, double y1, double x2, double y2) {
        double xi1 = x1 - 0.1;
        double xi2 = x2 + 0.1;
        double yi1 = y1 - 0.1;
        double yi2 = y2 + 0.1;
        RenderUtils2.setColor(outline);
        GL11.glLineWidth((float)1.0f);
        GL11.glBegin((int)2);
        GL11.glVertex2d((double)xi1, (double)yi1);
        GL11.glVertex2d((double)xi2, (double)yi1);
        GL11.glVertex2d((double)xi2, (double)yi2);
        GL11.glVertex2d((double)xi1, (double)yi2);
        GL11.glEnd();
        GL11.glBegin((int)9);
        RenderUtils2.setColor(shadow1);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glVertex2d((double)x2, (double)y1);
        RenderUtils2.setColor(shadow2);
        GL11.glVertex2d((double)(xi2 += 0.9), (double)(yi1 -= 0.9));
        GL11.glVertex2d((double)(xi1 -= 0.9), (double)yi1);
        GL11.glVertex2d((double)xi1, (double)(yi2 += 0.9));
        RenderUtils2.setColor(shadow1);
        GL11.glVertex2d((double)x1, (double)y2);
        GL11.glEnd();
        GL11.glBegin((int)9);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x2, (double)y1);
        RenderUtils2.setColor(shadow2);
        GL11.glVertex2d((double)xi2, (double)yi1);
        GL11.glVertex2d((double)xi2, (double)yi2);
        GL11.glVertex2d((double)xi1, (double)yi2);
        RenderUtils2.setColor(shadow1);
        GL11.glVertex2d((double)x1, (double)y2);
        GL11.glEnd();
    }

    public static void setupLineSmooth() {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)3553);
        GL11.glHint((int)3154, (int)4354);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)32925);
        GL11.glEnable((int)32926);
        GL11.glShadeModel((int)7425);
    }

    public static void drawLine(double startX, double startY, double startZ, double endX, double endY, double endZ, float thickness) {
        GL11.glPushMatrix();
        RenderUtils2.setupLineSmooth();
        GL11.glLineWidth((float)thickness);
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)startX, (double)startY, (double)startZ);
        GL11.glVertex3d((double)endX, (double)endY, (double)endZ);
        GL11.glEnd();
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)2896);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)32925);
        GL11.glDisable((int)32926);
        GL11.glPopMatrix();
    }

    public static void drawTexturedModalRect(int textureId, int posX, int posY, int width, int height) {
        double halfHeight = height / 2;
        double halfWidth = width / 2;
        GL11.glDisable((int)2884);
        GL11.glBindTexture((int)3553, (int)textureId);
        GL11.glPushMatrix();
        GL11.glTranslated((double)((double)posX + halfWidth), (double)((double)posY + halfHeight), (double)0.0);
        GL11.glScalef((float)width, (float)height, (float)0.0f);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glBegin((int)4);
        GL11.glNormal3f((float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTexCoord2f((float)1.0f, (float)1.0f);
        GL11.glVertex2d((double)1.0, (double)1.0);
        GL11.glTexCoord2f((float)0.0f, (float)1.0f);
        GL11.glVertex2d((double)-1.0, (double)1.0);
        GL11.glTexCoord2f((float)0.0f, (float)0.0f);
        GL11.glVertex2d((double)-1.0, (double)-1.0);
        GL11.glTexCoord2f((float)0.0f, (float)0.0f);
        GL11.glVertex2d((double)-1.0, (double)-1.0);
        GL11.glTexCoord2f((float)1.0f, (float)0.0f);
        GL11.glVertex2d((double)1.0, (double)-1.0);
        GL11.glTexCoord2f((float)1.0f, (float)1.0f);
        GL11.glVertex2d((double)1.0, (double)1.0);
        GL11.glEnd();
        GL11.glDisable((int)3042);
        GL11.glBindTexture((int)3553, (int)0);
        GL11.glPopMatrix();
    }

    public static int interpolateColor(int rgba1, int rgba2, float percent) {
        int r1 = rgba1 & 255;
        int g1 = rgba1 >> 8 & 255;
        int b1 = rgba1 >> 16 & 255;
        int a1 = rgba1 >> 24 & 255;
        int r2 = rgba2 & 255;
        int g2 = rgba2 >> 8 & 255;
        int b2 = rgba2 >> 16 & 255;
        int a2 = rgba2 >> 24 & 255;
        int r = (int)(r1 < r2 ? (float)r1 + (float)(r2 - r1) * percent : (float)r2 + (float)(r1 - r2) * percent);
        int g = (int)(g1 < g2 ? (float)g1 + (float)(g2 - g1) * percent : (float)g2 + (float)(g1 - g2) * percent);
        int b = (int)(b1 < b2 ? (float)b1 + (float)(b2 - b1) * percent : (float)b2 + (float)(b1 - b2) * percent);
        int a = (int)(a1 < a2 ? (float)a1 + (float)(a2 - a1) * percent : (float)a2 + (float)(a1 - a2) * percent);
        return r | g << 8 | b << 16 | a << 24;
    }

    public static void setColor2(Color c) {
        GL11.glColor4f((float)((float)c.getRed() / 255.0f), (float)((float)c.getGreen() / 255.0f), (float)((float)c.getBlue() / 255.0f), (float)((float)c.getAlpha() / 255.0f));
    }

    public static Color toColor(int rgba) {
        int r = rgba & 255;
        int g = rgba >> 8 & 255;
        int b = rgba >> 16 & 255;
        int a = rgba >> 24 & 255;
        return new Color(r, g, b, a);
    }

    public static int toRGBA(Color c) {
        return c.getRed() | c.getGreen() << 8 | c.getBlue() << 16 | c.getAlpha() << 24;
    }

    public static void setColor(int rgba) {
        int r = rgba & 255;
        int g = rgba >> 8 & 255;
        int b = rgba >> 16 & 255;
        int a = rgba >> 24 & 255;
        GL11.glColor4b((byte)((byte)r), (byte)((byte)g), (byte)((byte)b), (byte)((byte)a));
    }

    public static Point calculateMouseLocation() {
        Minecraft minecraft = Minecraft.getMinecraft();
        int scale = minecraft.gameSettings.guiScale;
        if (scale == 0) {
            scale = 1000;
        }
        int scaleFactor = 0;
        while (scaleFactor < scale && minecraft.displayWidth / (scaleFactor + 1) >= 320 && minecraft.displayHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor;
        }
        return new Point(Mouse.getX() / scaleFactor, minecraft.displayHeight / scaleFactor - Mouse.getY() / scaleFactor - 1);
    }
	
    public static Color getHealthColor3(float health, float maxHealth) {
        float[] fractions = { 0.0F, 0.5F, 1.0F };
        Color[] colors = { new Color(108, 0, 0), new Color(255, 51, 0), Color.GREEN };
        float progress = health / maxHealth;
        return blendColors2(fractions, colors, progress).brighter();
    }
    
    public static void drawArrow(float x, float y, final int hexColor) {
        GL11.glPushMatrix();
        GL11.glScaled(1.3, 1.3, 1.3);
        x /= (float)1.3;
        y /= (float)1.3;
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        hexColor(hexColor);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(1);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)(x + 3.0f), (double)(y + 4.0f));
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex2d((double)(x + 3.0f), (double)(y + 4.0f));
        GL11.glVertex2d((double)(x + 6.0f), (double)y);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static Location predictEntityLocation(Entity e, double milliseconds) {
        if (e != null) {
           if (e.posX == e.lastTickPosX && e.posY == e.lastTickPosY && e.posZ == e.lastTickPosZ) {
              return new Location(e.posX, e.posY, e.posZ);
           } else {
              double ticks = milliseconds / 1000.0D;
              ticks *= 20.0D;
              return interp(new Location(e.lastTickPosX, e.lastTickPosY, e.lastTickPosZ), new Location(e.posX + e.motionX, e.posY + e.motionY, e.posZ + e.motionZ), ticks);
           }
        } else {
           return null;
        }
     }
    
    public static Location interp(Location from, Location to, double pct) {
        double x = from.getX() + (to.getX() - from.getX()) * pct;
        double y = from.getY() + (to.getY() - from.getY()) * pct;
        double z = from.getZ() + (to.getZ() - from.getY()) * pct;
        return new Location(x, y, z);
     }
    
    public static void hexColor(final int hexColor) {
        final float red = (hexColor >> 16 & 0xFF) / 255.0f;
        final float green = (hexColor >> 8 & 0xFF) / 255.0f;
        final float blue = (hexColor & 0xFF) / 255.0f;
        final float alpha = (hexColor >> 24 & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
        
    public static int getRainbow(final int speed, final int offset) {
        float hue = (float)((System.currentTimeMillis() + offset) % speed);
        hue /= speed;
        return Color.getHSBColor(hue, 0.75f, 1.0f).getRGB();
    }
    
    public static float[] getRGBAs(final int rgb) {
        return new float[] { (rgb >> 16 & 0xFF) / 255.0f, (rgb >> 8 & 0xFF) / 255.0f, (rgb & 0xFF) / 255.0f, (rgb >> 24 & 0xFF) / 255.0f };
    }
    
    
    public static void drawCircleESP(Entity entity, float partialTicks, double rad) {
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
        int[] counter = {1};
        for (int i = 0; i <= 120; i++) {
            GL11.glColor4f(255, 255, 255, 255);
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
    
    public static Color blendColors2(float[] fractions, Color[] colors, float progress) {
        Color color = null;
        if (fractions != null) {
            if (colors != null) {
                if (fractions.length == colors.length) {
                    int[] indicies = getFractionIndicies2(fractions, progress);

                    if (indicies[0] < 0 || indicies[0] >= fractions.length || indicies[1] < 0 || indicies[1] >= fractions.length) {
                        return colors[0];
                    }
                    float[] range = new float[]{fractions[indicies[0]], fractions[indicies[1]]};
                    Color[] colorRange = new Color[]{colors[indicies[0]], colors[indicies[1]]};

                    float max = range[1] - range[0];
                    float value = progress - range[0];
                    float weight = value / max;

                    color = blend(colorRange[0], colorRange[1], 1f - weight);
                } else {
                    throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
                }
            } else {
                throw new IllegalArgumentException("Colours can't be null");
            }
        } else {
            throw new IllegalArgumentException("Fractions can't be null");
        }
        return color;
    }
    
    public static Color blend(Color color1, Color color2, double ratio) {
        float r = (float) ratio;
        float ir = (float) 1.0 - r;

        float rgb1[] = new float[3];
        float rgb2[] = new float[3];

        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);

        float red = rgb1[0] * r + rgb2[0] * ir;
        float green = rgb1[1] * r + rgb2[1] * ir;
        float blue = rgb1[2] * r + rgb2[2] * ir;

        if (red < 0) {
            red = 0;
        } else if (red > 255) {
            red = 255;
        }
        if (green < 0) {
            green = 0;
        } else if (green > 255) {
            green = 255;
        }
        if (blue < 0) {
            blue = 0;
        } else if (blue > 255) {
            blue = 255;
        }

        Color color = null;
        try {
            color = new Color(red, green, blue);
        } catch (IllegalArgumentException exp) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            System.out.println(nf.format(red) + "; " + nf.format(green) + "; " + nf.format(blue));
            exp.printStackTrace();
        }
        return color;
    }

    public static int[] getFractionIndicies2(float[] fractions, float progress) {
        int[] range = new int[2];

        int startPoint = 0;
        while (startPoint < fractions.length && fractions[startPoint] <= progress) {
            startPoint++;
        }

        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }

        range[0] = startPoint - 1;
        range[1] = startPoint;

        return range;
    }

	/**
	 * Renders a frame with any size and any color.
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @param x2
	 * @param y2
	 * @param z2
	 * @param color
	 */
	public static void frame(double x, double y, double z, double x2, double y2, double z2, Color color) {
		x = x - Minecraft.getMinecraft().getRenderManager().renderPosX;
		y = y - Minecraft.getMinecraft().getRenderManager().renderPosY;
		z = z - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		x2 = x2 - Minecraft.getMinecraft().getRenderManager().renderPosX;
		y2 = y2 - Minecraft.getMinecraft().getRenderManager().renderPosY;
		z2 = z2 - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		setColor(color);
		drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x2, y2, z2));
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	/**
	 * Renders a green ESP box with the size of a normal block at the specified
	 * BlockPos.
	 */

	public static void blockESP(BlockPos b, Color c, double length, double length2) {
		blockEsp(b, c, length, length2);
	}

	/**
	 * Renders an ESP box with the size of a normal block at the specified
	 * BlockPos.
	 */
	public static void blockEsp(BlockPos blockPos, Color c, double length, double length2) {
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glPushMatrix();
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4d(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, 0.15);
		drawColorBox(new AxisAlignedBB(x, y, z, x + length2, y + 1.0, z + length), 0F, 0F, 0F, 0F);
		GL11.glColor4d(0, 0, 0, 0.5);
		drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + length2, y + 1, z + length));
		GL11.glLineWidth(2.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
		GL11.glPopMatrix();
	}
	
	public static final void color(double red, double green, double blue, double alpha) {
        GL11.glColor4d(red, green, blue, alpha);
    }

    public static final void color(double red, double green, double blue) {
        color(red, green, blue, 1);
    }

    public static final void color(Color color) {
        if (color == null)
            color = Color.white;
        color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
    }
    
	public static void color1(int color, float alpha) {
		float red = (color >> 16 & 0xFF) / 255.0F;
		float green = (color >> 8 & 0xFF) / 255.0F;
		float blue = (color & 0xFF) / 255.0F;
		GL11.glColor4f(red, green, blue, alpha);
	}
    
	 public static void drawOutlinedBlockESP(double p_i485_1_, double p_i485_3_, double p_i485_5_, float n, float n2, float n3, float n4, float n5) {
	        GL11.glPushMatrix();
	        GL11.glEnable(3042);
	        GL11.glBlendFunc(770, 771);
	        GL11.glDisable(3553);
	        GL11.glEnable(2848);
	        GL11.glDisable(2929);
	        GL11.glDepthMask(false);
	        GL11.glLineWidth(n5);
	        GL11.glColor4f(n, n2, n3, n4);
	        drawOutlinedBoundingBox(new AxisAlignedBB(p_i485_1_, p_i485_3_, p_i485_5_, p_i485_1_ + 1.0, p_i485_3_ + 1.0, p_i485_5_ + 1.0));
	        GL11.glDisable(2848);
	        GL11.glEnable(3553);
	        GL11.glEnable(2929);
	        GL11.glDepthMask(true);
	        GL11.glDisable(3042);
	        GL11.glPopMatrix();
	    }
	    
	    public static void drawOutlinedBoundingBox1(AxisAlignedBB class1593) {
	        Tessellator instance = Tessellator.getInstance();
	        BufferBuilder worldRenderer = instance.getBuffer();
	        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
	        worldRenderer.pos(class1593.minX, class1593.minY, class1593.minZ).endVertex();
	        worldRenderer.pos(class1593.maxX, class1593.minY, class1593.minZ).endVertex();
	        worldRenderer.pos(class1593.maxX, class1593.minY, class1593.maxZ).endVertex();
	        worldRenderer.pos(class1593.minX, class1593.minY, class1593.maxZ).endVertex();
	        worldRenderer.pos(class1593.minX, class1593.minY, class1593.minZ).endVertex();
	        instance.draw();
	        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
	        worldRenderer.pos(class1593.minX, class1593.maxY, class1593.minZ).endVertex();
	        worldRenderer.pos(class1593.maxX, class1593.maxY, class1593.minZ).endVertex();
	        worldRenderer.pos(class1593.maxX, class1593.maxY, class1593.maxZ).endVertex();
	        worldRenderer.pos(class1593.minX, class1593.maxY, class1593.maxZ).endVertex();
	        worldRenderer.pos(class1593.minX, class1593.maxY, class1593.minZ).endVertex();
	        instance.draw();
	        worldRenderer.begin(1, DefaultVertexFormats.POSITION);
	        worldRenderer.pos(class1593.minX, class1593.minY, class1593.minZ).endVertex();
	        worldRenderer.pos(class1593.minX, class1593.maxY, class1593.minZ).endVertex();
	        worldRenderer.pos(class1593.maxX, class1593.minY, class1593.minZ).endVertex();
	        worldRenderer.pos(class1593.maxX, class1593.maxY, class1593.minZ).endVertex();
	        worldRenderer.pos(class1593.maxX, class1593.minY, class1593.maxZ).endVertex();
	        worldRenderer.pos(class1593.maxX, class1593.maxY, class1593.maxZ).endVertex();
	        worldRenderer.pos(class1593.minX, class1593.minY, class1593.maxZ).endVertex();
	        worldRenderer.pos(class1593.minX, class1593.maxY, class1593.maxZ).endVertex();
	        instance.draw();
	    }

	public static void blockEspFrame(BlockPos blockPos, double red, double green, double blue) {
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(1.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4d(red, green, blue, 0.5);
		drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	public static void blockEspBox(BlockPos blockPos, double red, double green, double blue) {
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glPushMatrix();
			GL11.glBlendFunc(770, 771);
			GL11.glEnable(GL_BLEND);
			GL11.glLineWidth(2.0F);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDepthMask(false);
			GL11.glColor4d(red, green, blue, 0.15F);
			drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0), 0F, 0F, 0F, 0F);
			GL11.glDepthMask(false);
			GL11.glColor4d(red, green, blue, 0.15F);
			drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0), 0F, 0F, 0F, 0F);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDepthMask(true);
			GL11.glDisable(GL_BLEND);
		    GL11.glPopMatrix();
	}

	public static void emptyBlockESPBox(BlockPos blockPos) {
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4d(0, 0, 0, 0.5F);
		drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	public static int enemy = 0;
	public static int friend = 1;
	public static int other = 2;
	public static int target = 3;
	public static int team = 4;

	public static void entityESPBox(Entity entity, Color c) {
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		drawSelectionBoundingBox(new AxisAlignedBB(
				entity.boundingBox.minX - 0.05 - entity.posX + (entity.posX - renderManager.renderPosX),
				entity.boundingBox.minY - entity.posY + (entity.posY - renderManager.renderPosY),
				entity.boundingBox.minZ - 0.05 - entity.posZ + (entity.posZ - renderManager.renderPosZ),
				entity.boundingBox.maxX + 0.05 - entity.posX + (entity.posX - renderManager.renderPosX),
				entity.boundingBox.maxY + 0.1 - entity.posY + (entity.posY - renderManager.renderPosY),
				entity.boundingBox.maxZ + 0.05 - entity.posZ + (entity.posZ - renderManager.renderPosZ)));
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	public static void nukerBox(BlockPos blockPos, float damage) {
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(1.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(damage, 1 - damage, 0, 0.15F);
		drawColorBox(
				new AxisAlignedBB(x + 0.5 - damage / 2, y + 0.5 - damage / 2, z + 0.5 - damage / 2,
						x + 0.5 + damage / 2, y + 0.5 + damage / 2, z + 0.5 + damage / 2),
				damage, 1 - damage, 0, 0.15F);
		GL11.glColor4d(0, 0, 0, 0.5F);
		drawSelectionBoundingBox(new AxisAlignedBB(x + 0.5 - damage / 2, y + 0.5 - damage / 2, z + 0.5 - damage / 2,
				x + 0.5 + damage / 2, y + 0.5 + damage / 2, z + 0.5 + damage / 2));
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	public static void searchBox(BlockPos blockPos) {
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(1.0F);
		float sinus = 1F - MathHelper
				.abs(MathHelper.sin(Minecraft.getSystemTime() % 10000L / 10000.0F * (float) Math.PI * 4.0F) * 1F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(1F - sinus, sinus, 0F, 0.15F);
		drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0), 1F - sinus, sinus, 0F, 0.15F);
		GL11.glColor4d(0, 0, 0, 0.5);
		drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	public static void drawColorBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha) {
		Tessellator ts = Tessellator.getInstance();
		BufferBuilder vb = ts.getBuffer();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);// Starts X.
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		ts.draw();// Ends X.
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);// Starts Y.
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		ts.draw();// Ends Y.
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);// Starts Z.
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
		ts.draw();// Ends Z.
	}

	public static void tracerLine(Entity entity, Color color) {
		Vec3d eyes = new Vec3d(0, 0, 1)
				.rotatePitch(-(float) Math.toRadians(Minecraft.getMinecraft().player.rotationPitch))
				.rotateYaw(-(float) Math.toRadians(Minecraft.getMinecraft().player.rotationYaw));
		double x = entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = entity.posY + entity.height / 2 - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		glBlendFunc(770, 771);
		glEnable(GL_BLEND);
		glLineWidth(2.0F);
		glDisable(GL11.GL_TEXTURE_2D);
		glDisable(GL_DEPTH_TEST);
		glDepthMask(false);
		setColor(color);
		glBegin(GL_LINES);
		{
	//		glVertex3d(Client.gameResolution.getScaledWidth() / 2, Minecraft.getMinecraft().player.getEyeHeight(),
	//				Client.gameResolution.getScaledHeight() / 2);
	//		glVertex3d(x, y, z);
		}
		glEnd();
		glEnable(GL11.GL_TEXTURE_2D);
		glEnable(GL_DEPTH_TEST);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}

	public static void tracerLine(int x, int y, int z, Color color) {
		x += 0.5 - Minecraft.getMinecraft().getRenderManager().renderPosX;
		y += 0.5 - Minecraft.getMinecraft().getRenderManager().renderPosY;
		z += 0.5 - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		glBlendFunc(770, 771);
		glEnable(GL_BLEND);
		glLineWidth(2.0F);
		glDisable(GL11.GL_TEXTURE_2D);
		glDisable(GL_DEPTH_TEST);
		glDepthMask(false);
		setColor(color);
		glBegin(GL_LINES);
		{
			glVertex3d(0, Minecraft.getMinecraft().player.getEyeHeight(), 0);
			glVertex3d(x, y, z);
		}
		glEnd();
		glEnable(GL11.GL_TEXTURE_2D);
		glEnable(GL_DEPTH_TEST);
		glDepthMask(true);
		glDisable(GL_BLEND);
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

	public static void scissorBox(int x, int y, int xend, int yend) {
		int width = xend - x;
		int height = yend - y;
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		int factor = sr.getScaleFactor();
		int bottomY = Minecraft.getMinecraft().currentScreen.height - yend;
		glScissor(x * factor, bottomY * factor, width * factor, height * factor);
	}

	public static void setColor(Color c) {
		glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
	}

	public static float[] getColorValues(Color c) {
		return new float[] { c.getRed() / 255f, c.getGreen() / 255f, c.getGreen() / 255f };
	}
	
	private static Minecraft mc = Wrapper.getMinecraft();

	/*public static void setWindowCursor(int cursor)
	{
		//Adolf.getMinecraft().mcCanvas.setCursor(Cursor.getPredefinedCursor(cursor));
	}*/
	
	public static void drawSphere(double x, double y, double z, float size, int slices, int stacks) {
		final Sphere s = new Sphere();
		GL11.glPushMatrix();
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(1.2F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		s.setDrawStyle(GLU.GLU_SILHOUETTE);
		GL11.glTranslated(x - RenderManager.renderPosX, y - RenderManager.renderPosY, z - RenderManager.renderPosZ);
		s.draw(size, slices, stacks);
		GL11.glLineWidth(2.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
		GL11.glPopMatrix();
	}
	
	public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
    	x *= 2; y *= 2; x1 *= 2; y1 *= 2;
    	GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawVLine(x, y + 1, y1 -2, borderC);
        drawVLine(x1 - 1, y + 1, y1 - 2, borderC);
        drawHLine(x + 2, x1 - 3, y, borderC);
        drawHLine(x + 2, x1 - 3, y1 -1, borderC);
        drawHLine(x + 1, x + 1, y + 1, borderC);
        drawHLine(x1 - 2, x1 - 2, y + 1, borderC);
        drawHLine(x1 - 2, x1 - 2, y1 - 2, borderC);
        drawHLine(x + 1, x + 1, y1 - 2, borderC);
        drawRect(x + 1, y + 1, x1 - 1, y1 - 1, insideC);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

	public static void drawBetterBorderedRect(int x, float y, int x1, int y1, float size, int borderC, int insideC) {
        drawRect(x, y, x1, y1, insideC); //inside
        drawRect(x, y, x1, y + size, borderC); //top
        drawRect(x, y1, x1, y1 + size, borderC); //bottom
        drawRect(x1, y, x1 + size, y1 + size, borderC); //left
        drawRect(x, y, x + size, y1 + size, borderC); //right
    }
	
	public static void drawBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2) {
        drawRect((float)x, (float)y, (float)x2, (float)y2, col2);

        float f = (float)(col1 >> 24 & 0xFF) / 255F;
        float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
        float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
        float f3 = (float)(col1 & 0xFF) / 255F;
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glColor4f(f1, f2, f3, f);
        GL11.glLineWidth(l1);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glPopMatrix();
	}


	public static void drawHLine(float par1, float par2, float par3, int par4)
	{
		if (par2 < par1)
		{
			float var5 = par1;
			par1 = par2;
			par2 = var5;
		}

		drawRect(par1, par3, par2 + 1, par3 + 1, par4);
	}

	public static void drawVLine(float par1, float par2, float par3, int par4)
	{
		if (par3 < par2)
		{
			float var5 = par2;
			par2 = par3;
			par3 = var5;
		}

		drawRect(par1, par2 + 1, par1 + 1, par3, par4);
	}

	public static void drawRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, int paramColor)
	{
		float alpha = (float)(paramColor >> 24 & 0xFF) / 255F;
		float red = (float)(paramColor >> 16 & 0xFF) / 255F;
		float green = (float)(paramColor >> 8 & 0xFF) / 255F;
		float blue = (float)(paramColor & 0xFF) / 255F;
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

	public static void drawGradientRect(double x, double y, double x2, double y2, int col1, int col2) 
	{
		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		float f4 = (float)(col2 >> 24 & 0xFF) / 255F;
		float f5 = (float)(col2 >> 16 & 0xFF) / 255F;
		float f6 = (float)(col2 >> 8 & 0xFF) / 255F;
		float f7 = (float)(col2 & 0xFF) / 255F;

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y);

		GL11.glColor4f(f5, f6, f7, f4);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	public static void drawGradientBorderedRect(double x, double y, double x2, double y2, int col, int col1, int col2, int col3)
	{
		float f = (float)(col >> 24 & 0xFF) / 255F;
		float f1 = (float)(col >> 16 & 0xFF) / 255F;
		float f2 = (float)(col >> 8 & 0xFF) / 255F;
		float f3 = (float)(col & 0xFF) / 255F;
		
		float z = (float)(col1 >> 24 & 0xFF) / 255F;
		float z1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float z2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float z3 = (float)(col1 & 0xFF) / 255F;

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		GL11.glBegin(GL11.GL_QUADS);
		
		double width = 1;
		double hw = width / 2;
		
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glVertex2d(x, y - hw);
		GL11.glVertex2d(x, y + hw);
		GL11.glColor4f(z1, z2, z3, z);
		GL11.glVertex2d(x2, y + hw);
		GL11.glVertex2d(x2, y - hw);
		
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glVertex2d(x, y2 - hw);
		GL11.glVertex2d(x, y2 + hw);
		GL11.glColor4f(z1, z2, z3, z);
		GL11.glVertex2d(x2, y2 + hw);
		GL11.glVertex2d(x2, y2 - hw);
		
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glVertex2d(x + hw, y);
		GL11.glVertex2d(x - hw, y);
		GL11.glVertex2d(x - hw, y2);
		GL11.glVertex2d(x + hw, y2);
		
		GL11.glColor4f(z1, z2, z3, z);
		GL11.glVertex2d(x2 + hw, y);
		GL11.glVertex2d(x2 - hw, y);
		GL11.glVertex2d(x2 - hw, y2);
		GL11.glVertex2d(x2 + hw, y2);
		
		GL11.glEnd();
		GL11.glPopMatrix();

		drawGradientRect(x, y, x2, y2, col2, col3);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	public static void drawStrip(int x, int y, float width, double angle, float points, float radius, int color)
	{
		GL11.glPushMatrix();
		float f1 = (float)(color >> 24 & 255) / 255.0F;
		float f2 = (float)(color >> 16 & 255) / 255.0F;
		float f3 = (float)(color >> 8 & 255) / 255.0F;
		float f4 = (float)(color & 255) / 255.0F;
		GL11.glTranslatef(x, y, 0);
		GL11.glColor4f(f2, f3, f4, f1);
		GL11.glLineWidth(width);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glEnable(GL13.GL_MULTISAMPLE);

		if (angle > 0)
		{
			GL11.glBegin(GL11.GL_LINE_STRIP);

			for (int i = 0; i < angle; i++)
			{
				float a = (float)(i * (angle * Math.PI / points));
				float xc = (float)(Math.cos(a) * radius);
				float yc = (float)(Math.sin(a) * radius);
				GL11.glVertex2f(xc, yc);
			}

			GL11.glEnd();
		}

		if (angle < 0)
		{
			GL11.glBegin(GL11.GL_LINE_STRIP);

			for (int i = 0; i > angle; i--)
			{
				float a = (float)(i * (angle * Math.PI / points));
				float xc = (float)(Math.cos(a) * -radius);
				float yc = (float)(Math.sin(a) * -radius);
				GL11.glVertex2f(xc, yc);
			}

			GL11.glEnd();
		}

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL13.GL_MULTISAMPLE);
		GL11.glDisable(GL11.GL_MAP1_VERTEX_3);
		GL11.glPopMatrix();
	}


	public static void drawCircle(float cx, float cy, float r, int num_segments, int c)
	{
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		r *= 2;
		cx *= 2;
		cy *= 2;
		float f = (float) (c >> 24 & 0xff) / 255F;
		float f1 = (float) (c >> 16 & 0xff) / 255F;
		float f2 = (float) (c >> 8 & 0xff) / 255F;
		float f3 = (float) (c & 0xff) / 255F;
		float theta = (float) (2 * 3.1415926 / (num_segments));
		float p = (float) Math.cos(theta);//calculate the sine and cosine
		float s = (float) Math.sin(theta);
		float t;
		GL11.glColor4f(f1, f2, f3, f);
		float x = r;
		float y = 0;//start at angle = 0
				GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glBlendFunc(770, 771);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		for(int ii = 0; ii < num_segments; ii++)
		{
			GL11.glVertex2f(x + cx, y + cy);//final vertex vertex

			//rotate the stuff
			t = x;
			x = p * x - s * y;
			y = s * t + p * y;
		}
		GL11.glEnd();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
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

	public static void drawOutlinedBoundingBox(net.minecraft.util.math.AxisAlignedBB par1AxisAlignedBB)
	{
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.enableColorLogic();
        GlStateManager.colorLogicOp(GlStateManager.LogicOp.OR_REVERSE);
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ).endVertex();
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ).endVertex();
        tessellator.draw();
        GlStateManager.disableColorLogic();
        GlStateManager.enableTexture2D();
	}

	public static void drawBoundingBox(AxisAlignedBB par1AxisAlignedBB)
	{
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();

        GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.enableColorLogic();
        GlStateManager.colorLogicOp(GlStateManager.LogicOp.OR_REVERSE);
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		tessellator.draw();
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		tessellator.draw();
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		tessellator.draw();
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		tessellator.draw();
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		tessellator.draw();
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		tessellator.draw();
        GlStateManager.disableColorLogic();
        GlStateManager.enableTexture2D();
	}

	public static void drawESP(double d, double d1, double d2, double r, double b, double g)
	{
		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glLineWidth(1.5F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(1.0F);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glColor4d(r, g, b, 0.1825F);
		drawColorBox(new AxisAlignedBB(d, d1, d2, d + 1.0, d1 + 1.0, d2 + 1.0), 0F, 0F, 0F, 0F);
		GL11.glColor4d(0, 0, 0, 0.5);
		drawSelectionBoundingBox(new AxisAlignedBB(d, d1, d2, d + 1.0, d1 + 1.0, d2 + 1.0));
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}
	
	public static void drawChestESP(double d, double d1, double d2, double r, double b, double g, double length, double length2)
	{
		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glLineWidth(1.5F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(1.0F);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glColor4d(r, g, b, 0.15);
		drawColorBox(new AxisAlignedBB(d, d1, d2, d + length2, d1 + 1.0, d2 + length), 0F, 0F, 0F, 0F);
		GL11.glColor4d(0, 0, 0, 0.5);
		drawSelectionBoundingBox(new AxisAlignedBB(d, d1, d2, d + length2, d1 + 1.0, d2 + length));
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}
	
	public static void drawLogoutESP(double d, double d1, double d2, double r, double b, double g)
	{
		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glLineWidth(1.5F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(1.0F);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glColor4d(r, g, b, 0.1825F);
		drawBoundingBox(new AxisAlignedBB(d, d1, d2, d + 1.0, d1 + 2.0, d2 + 1.0));
		GL11.glColor4d(r, g, b, 1.0F);
		drawOutlinedBoundingBox(new AxisAlignedBB(d, d1, d2, d + 1.0, d1 + 2.0, d2 + 1.0));
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}
	
	
	

    public static void drawNameplate(FontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift, float viewerYaw, float viewerPitch, boolean isThirdPersonFrontal)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(isThirdPersonFrontal ? -1 : 1) * viewerPitch, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-0.025F, -0.025F, 0.025F);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        int i = fontRendererIn.getStringWidth(str) / 2;
        GlStateManager.disableTexture2D();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vertexbuffer.pos((double)(-i - 1), (double)(-1 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        vertexbuffer.pos((double)(-i - 1), (double)(8 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        vertexbuffer.pos((double)(i + 1), (double)(8 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        vertexbuffer.pos((double)(i + 1), (double)(-1 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();

        GlStateManager.depthMask(true);
        fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, -1);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }
	
	public static void drawEntityESP(Entity entity, Color c) {
		GL11.glPushMatrix();
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(1.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4d(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, 0.15F);
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		drawColorBox(new AxisAlignedBB(				entity.boundingBox.minX - 0.05 - entity.posX + (entity.posX - renderManager.renderPosX),
				entity.boundingBox.minY - entity.posY + (entity.posY - renderManager.renderPosY),
				entity.boundingBox.minZ - 0.05 - entity.posZ + (entity.posZ - renderManager.renderPosZ),
				entity.boundingBox.maxX + 0.05 - entity.posX + (entity.posX - renderManager.renderPosX),
				entity.boundingBox.maxY + 0.1 - entity.posY + (entity.posY - renderManager.renderPosY),
				entity.boundingBox.maxZ + 0.05 - entity.posZ + (entity.posZ - renderManager.renderPosZ)), 0F, 0F, 0F, 0F);
		GL11.glColor4d(0, 0, 0, 0.5);
		drawSelectionBoundingBox(new AxisAlignedBB(entity.boundingBox.minX - 0.05 - entity.posX + (entity.posX - renderManager.renderPosX),
				entity.boundingBox.minY - entity.posY + (entity.posY - renderManager.renderPosY),
				entity.boundingBox.minZ - 0.05 - entity.posZ + (entity.posZ - renderManager.renderPosZ),
				entity.boundingBox.maxX + 0.05 - entity.posX + (entity.posX - renderManager.renderPosX),
				entity.boundingBox.maxY + 0.1 - entity.posY + (entity.posY - renderManager.renderPosY),
				entity.boundingBox.maxZ + 0.05 - entity.posZ + (entity.posZ - renderManager.renderPosZ)));
		GL11.glLineWidth(2.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
		GL11.glPopMatrix();
	}
	
	public static void drawPlayerESP(double d, double d1, double d2, EntityPlayer ep, double e, double f)
	{
		if(!(ep instanceof EntityPlayerSP))
		{
	        GL11.glPushMatrix();
	        GL11.glEnable(3042);
	       
	        	GL11.glColor4f(0.7F, 0.0F, 0.0F, 0.15F);
	        
	        GL11.glPushMatrix();
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glDisable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_DEPTH_TEST);
	        GL11.glDepthMask(false);
	        GL11.glLineWidth(1.0F);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glEnable(GL11.GL_LINE_SMOOTH);
	        drawBoundingBox(new AxisAlignedBB(d - f, d1 + 0.1, d2 - f, d + f, d1 + e + 0.25, d2 + f));
	   
	        	GL11.glColor4f(0.7F, 0.0F, 0.0F, 1F);
	       
	        drawOutlinedBoundingBox(new AxisAlignedBB(d - f, d1 + 0.1, d2 - f, d + f, d1 + e + 0.25, d2 + f));
	        GL11.glDepthMask(true);
	        GL11.glEnable(GL11.GL_DEPTH_TEST);
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	        GL11.glEnable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_LINE_SMOOTH);
	        GL11.glDisable(3042);
	        GL11.glPopMatrix();
		}
	}

    public static Color effect(long offset, float brightness, float speed) {
        float hue = (float) (System.nanoTime() + (offset * speed)) / 1.0E09F / 4 % speed;
        long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, brightness, speed)).intValue()), 16);
        Color c = new Color((int) color);
        return new Color(c.getRed()/255.0F, c.getGreen()/255.0F, c.getBlue()/255.0F, c.getAlpha()/255.0F);
    }

    public static void drawCircle2(Entity entity, float partialTicks, double rad) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        startSmooth();
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(1.0F);
        GL11.glBegin(3);
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks - mc.getRenderManager().viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks - mc.getRenderManager().viewerPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks - mc.getRenderManager().viewerPosZ;
        float r, g, b;
        if(entity == KillAura.target && mc.player.getDistanceToEntity(KillAura.target) <= Zamorozka.settingsManager.getSettingByName("MaxTargetRange").getValDouble()){
            r = 0.003921569F * (float) RenderUtils2.effect(1, 1F, 1).getRed();
            g = 0.003921569F * (float)RenderUtils2.effect(1, 1F, 1).getGreen();
            b = 0.003921569F * (float)RenderUtils2.effect(1, 1F, 1).getBlue();
        } else {
            r = 0.003921569F * (float) Color.WHITE.getRed();
            g = 0.003921569F * (float)Color.WHITE.getGreen();
            b = 0.003921569F * (float)Color.WHITE.getBlue();
        }
        double pix2 = 6.283185307179586D;

        for(int i = 0; i <= 90; ++i) {
            GL11.glColor3f(r, g, b);
            GL11.glVertex3d(x + rad * Math.cos((double)i * 6.283185307179586D / 45.0D), y, z + rad * Math.sin((double)i * 6.283185307179586D / 45.0D));
        }

        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        endSmooth();
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }

    private static void startSmooth() {
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glHint(3153, 4354);
    }

    private static void endSmooth() {
        GL11.glDisable(2848);
        GL11.glDisable(2881);
        GL11.glEnable(2832);
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
	
	public static double getAnimationState(double animation, double finalState, double speed) {
        float add = (float)((double)delta * speed);
        if (animation < finalState) {
            if (animation + (double)add >= finalState) return finalState;
            return animation += (double)add;
        }
        if (animation - (double)add <= finalState) return finalState;
        return animation -= (double)add;
    }

	public static void drawRectangle(double x, double y, double width, double height, int color) {
		float f = (color >> 24 & 0xFF) / 255.0F;
		float f1 = (color >> 16 & 0xFF) / 255.0F;
		float f2 = (color >> 8 & 0xFF) / 255.0F;
		float f3 = (color & 0xFF) / 255.0F;
		GL11.glColor4f(f1, f2, f3, f);
		Gui.drawRect(x, y, x + width, y + height, color);

	}


	public static void drawGradientLine(int x, double y, int x2, int col, int col1) {
		float f = (float)(col >> 24 & 0xFF) / 255F;
		float f1 = (float)(col >> 16 & 0xFF) / 255F;
		float f2 = (float)(col >> 8 & 0xFF) / 255F;
		float f3 = (float)(col & 0xFF) / 255F;
		
		float z = (float)(col1 >> 24 & 0xFF) / 255F;
		float z1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float z2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float z3 = (float)(col1 & 0xFF) / 255F;

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		GL11.glBegin(GL11.GL_QUADS);
		
		double width = 2;
		double hw = width / 2;
		
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glVertex2d(x, y - hw);
		GL11.glVertex2d(x, y + hw);
		GL11.glColor4f(z1, z2, z3, z);
		GL11.glVertex2d(x2, y + hw);
		GL11.glVertex2d(x2, y - hw);
		
		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}