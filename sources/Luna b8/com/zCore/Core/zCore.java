package com.zCore.Core;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import lunadevs.luna.events.MoveEvent;
import lunadevs.luna.main.Luna;
import lunadevs.luna.utils.MathUtils;
import lunadevs.luna.utils.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Session;
import net.minecraft.util.StringUtils;
import parallaxadevs.Utils.FontUtils.FontType;
/**
 * 
 * @author Timothy/SLiZ_D_2017/ZiTROX/LunaDevs/Italicz/DanielDev/Nigger Jim/Nuddles
 *
 */
public class zCore {
	
	public static int shit = 0;
    public int shit2 = 0;
    public static boolean back;
    private Graphics2D theGraphics;
    private final Pattern patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-ORU]");
    private final Pattern patternUnsupported = Pattern.compile("(?i)\\u00A7[K-O]");
    private String getzCore = "(i)*zCore.*";
    private int startChar;
    private int endChar;
    private float[] xPos;
    private float[] yPos;
    private float extraSpacing = 0.0F;
    private DynamicTexture dynamicTexture;
    private DynamicTexture dynamicTextureBlurred = null;
    private FontMetrics theMetrics;
	public static String zCore_BUILD = "6";
	
	
	private static final Random rng = new Random();
	  
	  public static Random getRng()
	  {
	    return rng;
	  }
	  
	  
	/**
	 * Same as Minecraft.getMinecraft(), Just with zCore.mc() now.
	 * @return
	 */
	 public static Minecraft mc()
	  {
	    return Minecraft.getMinecraft();
	  }
	  /**
	   * Gets the EntityPlayerSP class, To use this, do zCore.p().***
	   * @return
	   */
	  public static EntityPlayerSP p()
	  {
	    return mc().thePlayer;
	  }
	  public static EntityPlayerSP player()
	  {
	    return mc().thePlayer;
	  }
	  
	  public static PlayerControllerMP playerControll()
	  {
		  return mc().playerController;
	  }
	  
	  public static Luna client()
	  {
		  //YES HELLO NIGGER JIM HERE
		return Luna.client();  
	  }
	  
	  public static void sendChatMessage(String msg) {
	        zCore.sendPacket(new C01PacketChatMessage(msg));
	    }
	  /**
	   * Taken from Winter
	   */
	  public static void rainbowCircle(ScaledResolution sr, int x, int y, int rad)
	  {
	    GL11.glPushMatrix();
	    int scale = zCore.mc().gameSettings.guiScale;
	    zCore.mc().gameSettings.guiScale = 2;
	    GL11.glEnable(3042);
	    GL11.glDisable(3553);
	    GL11.glBlendFunc(770, 771);
	    GL11.glEnable(2848);
	    double tau = 6.283185307179586D;
	    double radius = rad;
	    double fans = 55.0D;
	    GL11.glLineWidth(3.0F);
	    GL11.glColor3f(1.0F, 1.0F, 1.0F);
	    GL11.glBegin(3);
	    for (int i = 0; i <= fans; i++)
	    {
	      Color color = new Color(Color.HSBtoRGB((float)(zCore.mc().thePlayer.ticksExisted / fans + Math.sin(i / fans * 1.5707963267948966D)) % 1.0F, 0.5058824F, 1.0F));
	      GL11.glColor3f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
	      GL11.glVertex2d(
	        x + rad + radius * Math.cos(i * tau / fans), 
	        y + rad + radius * Math.sin(i * tau / fans));
	    }
	    GL11.glEnd();
	    zCore.mc().gameSettings.guiScale = scale;
	    GL11.glDisable(2848);
	    GlStateManager.disableBlend();
	    GL11.glDisable(3042);
	    GL11.glEnable(3553);
	    GL11.glPopMatrix();
	  }
	  
	  public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
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
		
		public static void drawBorderedRect(float x, float y, float x1, float y1, float width, int internalColor, int borderColor) {
	        RenderUtils.enableGL2D();
	        RenderUtils.glColor((int)internalColor);
	        RenderUtils.drawRectZZ((float)(x + width), (float)(y + width), (float)(x1 - width), (float)(y1 - width));
	        RenderUtils.glColor((int)borderColor);
	        RenderUtils.drawRectZZ((float)(x + width), (float)y, (float)(x1 - width), (float)(y + width));
	        RenderUtils.drawRectZZ((float)x, (float)y, (float)(x + width), (float)y1);
	        RenderUtils.drawRectZZ((float)(x1 - width), (float)y, (float)x1, (float)y1);
	        RenderUtils.drawRectZZ((float)(x + width), (float)(y1 - width), (float)(x1 - width), (float)y1);
	        RenderUtils.disableGL2D();
	    }
		
		public static void enableGL2D() {
	        GL11.glDisable((int)2929);
	        GL11.glEnable((int)3042);
	        GL11.glDisable((int)3553);
	        GL11.glBlendFunc((int)770, (int)771);
	        GL11.glDepthMask((boolean)true);
	        GL11.glEnable((int)2848);
	        GL11.glHint((int)3154, (int)4354);
	        GL11.glHint((int)3155, (int)4354);
	    }

	    public static void disableGL2D() {
	        GL11.glEnable((int)3553);
	        GL11.glDisable((int)3042);
	        GL11.glEnable((int)2929);
	        GL11.glDisable((int)2848);
	        GL11.glHint((int)3154, (int)4352);
	        GL11.glHint((int)3155, (int)4352);
	    }
	    
	    public static void glColor2(Color color) {
	        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
	    }

	    public static void glColor(int hex) {
	        float alpha = (float)(hex >> 24 & 255) / 255.0f;
	        float red = (float)(hex >> 16 & 255) / 255.0f;
	        float green = (float)(hex >> 8 & 255) / 255.0f;
	        float blue = (float)(hex & 255) / 255.0f;
	        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
	    }
	    
	    

	    public static void glColor(float alpha, int redRGB, int greenRGB, int blueRGB) {
	        float red = 0.003921569f * (float)redRGB;
	        float green = 0.003921569f * (float)greenRGB;
	        float blue = 0.003921569f * (float)blueRGB;
	        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
	    }
	    
		public static void drawBorderRect(int left, int top, int right, int bottom, int bcolor, int icolor, int bwidth) {
			drawRect(left + bwidth, top + bwidth, right - bwidth, bottom - bwidth, icolor);
			drawRect(left, top + 1, left + bwidth, bottom - 1, bcolor);
			drawRect(left + bwidth, top, right, top + bwidth, bcolor);
			drawRect(left + bwidth, bottom - bwidth , right, bottom, bcolor);
			drawRect(right - bwidth, top + bwidth, right, bottom - bwidth, bcolor);
		}
	    
	    public static void drawRectZZ(int x, int y, int x1, int y1) {
	        GL11.glBegin((int)7);
	        GL11.glVertex2f((float)x, (float)y1);
	        GL11.glVertex2f((float)x1, (float)y1);
	        GL11.glVertex2f((float)x1, (float)y);
	        GL11.glVertex2f((float)x, (float)y);
	        GL11.glEnd();
	    }


		public static int transparency(int color, float alpha) {
			Color c = new Color(color);
			float r = 0.003921569f * c.getRed();
			float g = 0.003921569f * c.getGreen();
			float b = 0.003921569f * c.getBlue();
			return new Color(r, g, b, alpha).getRGB();
		}
		
		public static float getRandom()
		  {
		    return rng.nextFloat();
		  }
		  
		  public static int getRandom(int cap)
		  {
		    return rng.nextInt(cap);
		  }
		  
		  public static int getRandom(int floor, int cap)
		  {
		    return floor + rng.nextInt(cap - floor + 1);
		  }

		public static void drawRect(int x, int y, int x1, int y1, int color) {
			GL11.glDisable(2929);
			GL11.glEnable(3042);
			GL11.glDisable(3553);
			GL11.glBlendFunc(770, 771);
			GL11.glDepthMask(true);
			GL11.glEnable(2848);
			GL11.glHint(3154, 4354);
			GL11.glHint(3155, 4354);
			Gui.drawRect(x, y, x1, y1, color);
			GL11.glEnable(3553);
			GL11.glDisable(3042);
			GL11.glEnable(2929);
			GL11.glDisable(2848);
			GL11.glHint(3154, 4352);
			GL11.glHint(3155, 4352);
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
		
		public static void drawOutlinedEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha) {
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(770, 771);
			// GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(false);
			GL11.glColor4f(red, green, blue, alpha);
			drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width , y + height, z + width));
			GL11.glDisable(GL11.GL_LINE_SMOOTH);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			// GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(true);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
		
		public static void drawSolidEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha) {
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(770, 771);
			// GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(false);
			GL11.glColor4f(red, green, blue, alpha);
			drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width , y + height, z + width));
			GL11.glDisable(GL11.GL_LINE_SMOOTH);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			// GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(true);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}

		public static void drawEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith) {
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(770, 771);
			// GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(false);
			GL11.glColor4f(red, green, blue, alpha);
			drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width , y + height, z + width));
			GL11.glLineWidth(lineWdith);
			GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
			drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width , y + height, z + width));
			GL11.glDisable(GL11.GL_LINE_SMOOTH);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			// GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(true);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
		
		public static void drawTracerLine(double x, double y, double z,Color color, float alpha, float lineWdith) {
			GL11.glPushMatrix();
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glEnable(GL11.GL_LINE_SMOOTH);
	        GL11.glDisable(GL11.GL_DEPTH_TEST);
	        // GL11.glDisable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glBlendFunc(770, 771);
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glLineWidth(lineWdith);
	        glColor(color);
	        GL11.glBegin(2);
	        GL11.glVertex3d(0.0D, 0.0D + Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0D);
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
		public static void glColor(Color color)
		{
			GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
		}
	    public static void drawCircle(int x, int y, double r, int c)
	    {
	        float f = ((c >> 24) & 0xff) / 255F;
	        float f1 = ((c >> 16) & 0xff) / 255F;
	        float f2 = ((c >> 8) & 0xff) / 255F;
	        float f3 = (c & 0xff) / 255F;
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glEnable(GL11.GL_LINE_SMOOTH);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glColor4f(f1, f2, f3, f);
	        GL11.glBegin(GL11.GL_LINE_LOOP);

	        for (int i = 0; i <= 360; i++)
	        {
	            double x2 = Math.sin(((i * Math.PI) / 180)) * r;
	            double y2 = Math.cos(((i * Math.PI) / 180)) * r;
	            GL11.glVertex2d(x + x2, y + y2);
	        }

	        GL11.glEnd();
	        GL11.glDisable(GL11.GL_LINE_SMOOTH);
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	        GL11.glDisable(GL11.GL_BLEND);
	    }

	    public static void drawFilledCircle(int x, int y, double r, int c)
	    {
	        float f = ((c >> 24) & 0xff) / 255F;
	        float f1 = ((c >> 16) & 0xff) / 255F;
	        float f2 = ((c >> 8) & 0xff) / 255F;
	        float f3 = (c & 0xff) / 255F;
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glEnable(GL11.GL_LINE_SMOOTH);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glColor4f(f1, f2, f3, f);
	        GL11.glBegin(GL11.GL_TRIANGLE_FAN);

	        for (int i = 0; i <= 360; i++)
	        {
	            double x2 = Math.sin(((i * Math.PI) / 180)) * r;
	            double y2 = Math.cos(((i * Math.PI) / 180)) * r;
	            GL11.glVertex2d(x + x2, y + y2);
	        }

	        GL11.glEnd();
	        GL11.glDisable(GL11.GL_LINE_SMOOTH);
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	        GL11.glDisable(GL11.GL_BLEND);
	    }
	    
	    public static void dr(double i, double j, double k, double l, int i1)
	    {
	        if (i < k)
	        {
	            double j1 = i;
	            i = k;
	            k = j1;
	        }

	        if (j < l)
	        {
	            double k1 = j;
	            j = l;
	            l = k1;
	        }

	        float f = ((i1 >> 24) & 0xff) / 255F;
	        float f1 = ((i1 >> 16) & 0xff) / 255F;
	        float f2 = ((i1 >> 8) & 0xff) / 255F;
	        float f3 = (i1 & 0xff) / 255F;
	        Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldRenderer = tessellator.getWorldRenderer();
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glColor4f(f1, f2, f3, f);
	        worldRenderer.startDrawingQuads();
	        worldRenderer.addVertex(i, l, 0.0D);
	        worldRenderer.addVertex(k, l, 0.0D);
	        worldRenderer.addVertex(k, j, 0.0D);
	        worldRenderer.addVertex(i, j, 0.0D);
	        tessellator.draw();
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	        GL11.glDisable(GL11.GL_BLEND);
	    }

		public static int getRainbowParallaxa(int speed, int offset) {
			float hue = (System.currentTimeMillis() + offset) % speed;
			hue /= speed;
			return Color.getHSBColor(hue,1f,1f).getRGB();
		}

	    public static void sendPacket(Packet packet) {
	        zCore.getSendQueue().addToSendQueue(packet);
	    }
	    
	    public static NetHandlerPlayClient getSendQueue() {
	        return zCore.p().sendQueue;
	    }


	    public static void sendPacketDirectToServer(Packet packet) {
	        Minecraft.getMinecraft().getNetHandler().netManager.sendPacket(packet);
	    }
	  /**
	   * Gets the World class, To use this, do zCore.w().***
	   * @return
	   */
	  public static WorldClient w()
	  {
	    return mc().theWorld;
	  }
	  
	  public static GameSettings gsettings()
	  {
	    return mc().gameSettings;
	  }
	  
	  public static MovementInput movementInput()
	  {
	    return p().movementInput;
	  }
	  
	  public static double x()
	  {
	    return p().posX;
	  }
	  
	  public static void x(double x)
	  {
	    p().posX = x;
	  }
	  
	  public static double y()
	  {
	    return p().posY;
	  }
	  
	  public static void y(double y)
	  {
	    p().posY = y;
	  }
	  
	  public static double z()
	  {
	    return p().posZ;
	  }
	  
	  public static void z(double z)
	  {
	    p().posZ = z;
	  }
	  
	  public static float yaw()
	  {
	    return p().rotationYaw;
	  }
	  
	  public static void yaw(float yaw)
	  {
	    p().rotationYaw = yaw;
	  }
	  
	  public static float pitch()
	  {
	    return p().rotationPitch;
	  }
	  
	  public static void pitch(float pitch)
	  {
	    p().rotationPitch = pitch;
	  }
	  
	  public static void tpRel(double x, double y, double z)
	  {
	    EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
	    player.setPosition(player.posX + x, player.posY + y, player.posZ + z);
	  }
	  
	  public static void setSpeed(final MoveEvent event, final double speed) {
	        double forward = movementInput().moveForward;
	        double strafe = movementInput().moveStrafe;
	        float yaw = yaw();
	        if (forward == 0.0 && strafe == 0.0) {
	            event.setX(0.0);
	            event.setZ(0.0);
	        }
	        else {
	            if (forward != 0.0) {
	                if (strafe > 0.0) {
	                    yaw += ((forward > 0.0) ? -45 : 45);
	                }
	                else if (strafe < 0.0) {
	                    yaw += ((forward > 0.0) ? 45 : -45);
	                }
	                strafe = 0.0;
	                if (forward > 0.0) {
	                    forward = 1.0;
	                }
	                else if (forward < 0.0) {
	                    forward = -1.0;
	                }
	            }
	            event.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
	            event.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
	        }
	    }
	  /**
		 * Updates The Rotating Shit For The Circle
		 * 
		 * @return
		 */
	    public void updateRotating() {
			if (back) {
				if (this.shit > 0) {
					this.shit -= 2;
				}
			}
			if (this.shit < 360 && !back) {
				this.shit += 2;
			}
			if (this.shit <= 0) {
				++this.shit2;
				if (this.shit2 > 10) {
					this.shit2 = 0;
					back = false;		
				}
			}
			if (this.shit >= 360) {
				++this.shit2;
				if (this.shit2 > 10) {
					this.shit2 = 0;
					back = true;		
				}
			}
	    }
	    public static void drawCircleAnimated(final int x, final int y, final float radius, final float lineWidth, final int color) {
	        final float alpha = (color >> 24 & 0xFF) / 255.0f;
	        final float red = (color >> 16 & 0xFF) / 255.0f;
	        final float green = (color >> 8 & 0xFF) / 255.0f;
	        final float blue = (color & 0xFF) / 255.0f;
	        GlStateManager.pushMatrix();
	        GL11.glColor4f(red, green, blue, alpha);
	        GL11.glLineWidth(lineWidth);
	        GL11.glBegin((int) 3);
	        for (int i = 0; i <= shit; ++i) {
	            GL11.glVertex2d((x) - (back ? Math.sin(i * 3.141526 / 180.0) * radius : 0) + (back ? 0 : Math.sin(i * 3.141526 / 180.0) * radius), y + Math.cos(i * 3.141526 / 180.0) * radius);
	        }
	        GL11.glEnd();
	        GlStateManager.popMatrix();
	    }
	  public static void tpPacket(double x, double y, double z)
	  {
	    EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
	    player.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(player.posX + x, player.posY + y, player.posZ + z, false));
	    player.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY, player.posZ, false));
	    player.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY, player.posZ, true));
	  }
	  
	  public float getDirection()
	  {
	    float yaw = zCore.p().rotationYawHead;float forward = zCore.p().moveForward;float strafe = zCore.p().moveStrafing;
	    yaw += (forward < 0.0F ? 180 : 0);
	    if (strafe < 0.0F) {
	      yaw += (forward < 0.0F ? -45 : forward == 0.0F ? 90 : 45);
	    }
	    if (strafe > 0.0F) {
	      yaw -= (forward < 0.0F ? -45 : forward == 0.0F ? 90 : 45);
	    }
	    return yaw * 0.017453292F;
	  }
    
	  
	  public double getSpeed() {
			return Math.sqrt(MathUtils.square(zCore.p().motionX) + MathUtils.square(zCore.p().motionZ));
		}

		public void setSpeed(double speed) {
			zCore.p().motionX = (-MathHelper.sin(getDirection()) * speed);
			zCore.p().motionZ = (MathHelper.cos(getDirection()) * speed);
		}
		
		public void setY(double speed) {
			zCore.p().motionY = (-MathHelper.sin(getDirection()) * speed);
			zCore.p().chunkCoordY = (int)(-MathHelper.sin(getDirection()) * speed);
		}
		
		
		public static Block getBlock(BlockPos pos)
		  {
		    return Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
		  }
		  
		  public static InventoryPlayer getInventory()
		  {
		    return p().inventory;
		  }
		  
		  public static FontRenderer getFontRenderer()
		  {
		    return mc().fontRendererObj;
		  }
		  
		  public static boolean doesMove()
		  {
		    if ((!zCore.mc().gameSettings.keyBindForward.pressed) && (!zCore.mc().gameSettings.keyBindRight.pressed) && 
		      (!zCore.mc().gameSettings.keyBindBack.pressed) && (!zCore.mc().gameSettings.keyBindLeft.pressed)) {
		      return false;
		    }
		    return true;
		  }
		  
		  /**
		   * Used for custom Client-Sided chat messages.
		   * @return
		   */
		  public static void addChatMessage(String s) {
				p().addChatMessage(new ChatComponentText("§8[§7zCore§8] §7" + s));
			}
		  
		  public static void addErrorChatMessage(String msg)
		  {
			  p().addChatMessage(new ChatComponentText("§8[§7zCore§8] §cERROR: " + msg));
		  }
		  
		  /**
		   * Used for Lunas's addErrorChatMessage and addChatMessage
		   * 
		   */
		  
		  public static void addChatMessageP(String s) {
				p().addChatMessage(new ChatComponentText("§8[§7Luna§8] §7" + s));
			}
		  
		  public static void addNiceMessage(String s) {
				p().addChatMessage(new ChatComponentText("§4>> §7" + s));
			}
		  public static void addPlayerCheckMessageP(String s) {
				p().addChatMessage(new ChatComponentText("" + s));
			}
		  
		  private static Map<Integer, Boolean> glCapMap = new HashMap();
		  
		  public static void setGLCap(int cap, boolean flag)
		  {
		    glCapMap.put(Integer.valueOf(cap), Boolean.valueOf(GL11.glGetBoolean(cap)));
		    if (flag) {
		      GL11.glEnable(cap);
		    } else {
		      GL11.glDisable(cap);
		    }
		  }
		  
		  public static void revertGLCap(int cap)
		  {
		    Boolean origCap = (Boolean)glCapMap.get(Integer.valueOf(cap));
		    if (origCap != null) {
		      if (origCap.booleanValue()) {
		        GL11.glEnable(cap);
		      } else {
		        GL11.glDisable(cap);
		      }
		    }
		  }
		  
		  public static void glEnable(int cap)
		  {
		    setGLCap(cap, true);
		  }
		  
		  public static void glDisable(int cap)
		  {
		    setGLCap(cap, false);
		  }
		  
		  public static void revertAllCaps()
		  {
		    for (Iterator localIterator = glCapMap.keySet().iterator(); localIterator.hasNext();)
		    {
		      int cap = ((Integer)localIterator.next()).intValue();
		      revertGLCap(cap);
		    }
		  }
		  
		  public static void addErrorChatMessageP(String msg)
		  {
			  p().addChatMessage(new ChatComponentText("§8[§7Luna§8] §cERROR: §7" + msg));
		  }
		  
		  public static void faceEntity(Entity entity)
		  {
		    double posX = entity.posX - p().posX;
		    double posZ = entity.posZ - p().posZ;
		    double posY = entity.posY + entity.getEyeHeight() - p().posY + p().getEyeHeight();
		    double helper = MathHelper.sqrt_double(posX * posX + posZ * posZ);
		    float newYaw = (float)Math.toDegrees(-Math.atan(posX / posZ));
		    float newPitch = (float)-Math.toDegrees(Math.atan(posY / helper));
		    if ((posZ < 0.0D) && (posX < 0.0D)) {
		      newYaw = (float)(90.0D + Math.toDegrees(Math.atan(posZ / posX)));
		    } else if ((posZ < 0.0D) && (posX > 0.0D)) {
		      newYaw = (float)(-90.0D + Math.toDegrees(Math.atan(posZ / posX)));
		    }
		    getSendQueue().addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(newYaw, newPitch - 0.1F, p().onGround));
		  }
		  
		  public static Session getSession()
		  {
		    return mc().getSession();
		  }
		  
		  
		  public static void updatePosition(double x, double y, double z)
		  {
		    sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, p().onGround));
		  }
		  
		  
		  public static int drawStringWithShadow(String string, float x, float y, int color)
		  {
		    return getFontRenderer().func_175065_a(string, x, y, color, true);
		  }
		  
		  public static void useCPUUsage(int CPU)
		  {
		    int fpsdrain = 0;
		    System.out.println("[zCore] USING LESS CPU");
		    while (fpsdrain < CPU * 100)
		    {
		      drawStringWithShadow("CPU USAGE", Display.getX() + CPU, Display.getY() + CPU, 16514926);
		      
		      fpsdrain++;
		      if (fpsdrain >= CPU * 100) {
		        break;
		      }
		    }
		  }
		  
		  public static Block getBlockUnderPlayer(EntityPlayer inPlayer)
		  {
		    return getBlock(new BlockPos(inPlayer.posX, inPlayer.posY - 1.0D, inPlayer.posZ));
		  }
		  
	  
	  public static void setSpeedM(float speed)
	  {
	    EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
	    double yaw = player.rotationYaw;
	    boolean isMoving = (player.moveForward != 0.0F) || (player.moveStrafing != 0.0F);
	    boolean isMovingForward = player.moveForward > 0.0F;
	    boolean isMovingBackward = player.moveForward < 0.0F;
	    boolean isMovingRight = player.moveStrafing > 0.0F;
	    boolean isMovingLeft = player.moveStrafing < 0.0F;
	    boolean isMovingSideways = (isMovingLeft) || (isMovingRight);
	    boolean isMovingStraight = (isMovingForward) || (isMovingBackward);
	    if (isMoving)
	    {
	      if ((isMovingForward) && (!isMovingSideways)) {
	        yaw += 0.0D;
	      } else if ((isMovingBackward) && (!isMovingSideways)) {
	        yaw += 180.0D;
	      } else if ((isMovingForward) && (isMovingLeft)) {
	        yaw += 45.0D;
	      } else if (isMovingForward) {
	        yaw -= 45.0D;
	      } else if ((!isMovingStraight) && (isMovingLeft)) {
	        yaw += 90.0D;
	      } else if ((!isMovingStraight) && (isMovingRight)) {
	        yaw -= 90.0D;
	      } else if ((isMovingBackward) && (isMovingLeft)) {
	        yaw += 135.0D;
	      } else if (isMovingBackward) {
	        yaw -= 135.0D;
	      }
	      yaw = Math.toRadians(yaw);
	      player.motionX = (-Math.sin(yaw) * speed);
	      player.motionZ = (Math.cos(yaw) * speed);
	    }
	  }
	  
	  
	  public static void packet(Packet packet)
	  {
	    mc().getNetHandler().addToSendQueue(packet);
	  }
	  
	  
	public static double rn = 1.5D;
	public static int Color() {
		int cxd = 0;
		cxd = (int)(cxd + rn);
            if (cxd > 50) {
              cxd = 0;
            }
		Color color = new Color(Color.HSBtoRGB((float)(Minecraft.getMinecraft().thePlayer.ticksExisted / 60.0D + Math.sin(cxd / 60.0D * 1.5707963267948966D)) % 1.0F, 0.5882353F, 1.0F));
        int col = new Color(color.getRed(), color.getGreen(), color.getBlue(), 200).getRGB();
		return col;
}
	public static void circleOutline(double x, double y, double radius, int color) {
		String volcano = "Thx Volcano for the Circle ESP :)";
	float red = (color >> 24 & 0xFF) / 255.0f;
	float green = (color >> 16 & 0xFF) / 255.0f;
	float blue = (color >> 8 & 0xFF) / 255.0f;
	float alpha = (color & 0xFF) / 255.0f;
	Tessellator tessellator = Tessellator.getInstance();
	GlStateManager.pushMatrix();
	GlStateManager.func_179090_x();
	GlStateManager.enableBlend();
	GlStateManager.color(green, blue, alpha, red);
	GL11.glEnable(2848);
	GL11.glEnable(2881);
	GL11.glHint(3154, 4354);
	GL11.glHint(3155, 4354);
	GL11.glBegin(3);
	for (int i = 0; i <= 360; ++i) {
		final double x2 = Math.sin(i * 3.141592653589793 / 180.0) * radius;
		final double y2 = Math.cos(i * 3.141592653589793 / 180.0) * radius;
		GL11.glVertex2d(x + x2, y + y2);
	}
	GL11.glEnd();
	GL11.glDisable(2848);
	GL11.glDisable(2881);
	GlStateManager.func_179098_w();
	GlStateManager.disableBlend();
	GlStateManager.popMatrix();
}
	
	public static Color rainbow(float offset)
	  {
	    float hue = ((float)System.nanoTime() + offset) / 1.0E10F % 8.0F;
	    long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.001F)).intValue()), 
	      16);
	    Color c = new Color((int)color);
	    return new Color(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, c.getAlpha() / 255.0F);
	  }
	  
	  public static void drawBorderedCircle(int x, int y, float radius, int outsideC, int insideC)
	  {
	    GL11.glEnable(3042);
	    
	    GL11.glDisable(3553);
	    
	    GL11.glBlendFunc(770, 771);
	    
	    GL11.glEnable(2848);
	    
	    GL11.glPushMatrix();
	    
	    float scale = 0.1F;
	    
	    GL11.glScalef(scale, scale, scale);
	    
	    x = (int)(x * (1.0F / scale));
	    
	    y = (int)(y * (1.0F / scale));
	    
	    radius *= 1.0F / scale;
	    
	    drawCircle(x, y, radius, insideC);
	    
	    drawUnfilledCircle(x, y, radius, 1.0F, outsideC);
	    
	    GL11.glScalef(1.0F / scale, 1.0F / scale, 1.0F / scale);
	    
	    GL11.glPopMatrix();
	    
	    GL11.glEnable(3553);
	    
	    GL11.glDisable(3042);
	    
	    GL11.glDisable(2848);
	  }
	  
	  public static void drawUnfilledCircle(int x, int y, float radius, float lineWidth, int color)
	  {
	    float alpha = (color >> 24 & 0xFF) / 255.0F;
	    
	    float red = (color >> 16 & 0xFF) / 255.0F;
	    
	    float green = (color >> 8 & 0xFF) / 255.0F;
	    
	    float blue = (color & 0xFF) / 255.0F;
	    
	    GL11.glColor4f(red, green, blue, alpha);
	    
	    GL11.glLineWidth(lineWidth);
	    
	    GL11.glEnable(2848);
	    
	    GL11.glBegin(2);
	    for (int i = 0; i <= 360; i++) {
	      GL11.glVertex2d(x + Math.sin(i * 3.141526D / 180.0D) * radius, y + Math.cos(i * 3.141526D / 180.0D) * radius);
	    }
	    GL11.glEnd();
	    
	    GL11.glDisable(2848);
	  }
	  
	  public static void drawCircle(int x, int y, float radius, int color)
	  {
	    float alpha = (color >> 24 & 0xFF) / 255.0F;
	    
	    float red = (color >> 16 & 0xFF) / 255.0F;
	    
	    float green = (color >> 8 & 0xFF) / 255.0F;
	    
	    float blue = (color & 0xFF) / 255.0F;
	    
	    GL11.glColor4f(red, green, blue, alpha);
	    
	    GL11.glBegin(9);
	    for (int i = 0; i <= 360; i++) {
	      GL11.glVertex2d(x + Math.sin(i * 3.141526D / 180.0D) * radius, y + Math.cos(i * 3.141526D / 180.0D) * radius);
	    }
	    GL11.glEnd();
	  }
	public static int getRainbow(int speed, int offset) {
	        float hue = (System.currentTimeMillis() + offset) % speed;
	        hue /= speed;
	        return Color.getHSBColor(hue, 1f, 1f).getRGB();
	}
	
	private Rectangle2D getBounds(String text)
	  {
	    return this.theMetrics.getStringBounds(text, this.theGraphics);
	  }


	 public float getStringWidth(String str)
  {
    return (float)(getBounds(StringUtils.stripControlCodes(str)).getWidth() + this.extraSpacing) / 2.0F;
  }
	 
	 public float getStringHeight(String text)
	  {
	    return (float)getBounds(text).getHeight() / 2.0F;
	  }

	 
	 public void drawCenteredStringWithShadow(String text, double x, double y, int color)
	  {
	    drawStringWithShadowTag(text, x - getStringWidth(text) / 2.0F, y - getStringHeight(text) / 2.0F, color);
	  }
	 
	 public String stripControlCodes(String s)
	  {
	    return this.patternControlCode.matcher(s).replaceAll("");
	  }
	  
	  public String stripUnsupported(String s)
	  {
	    return this.patternUnsupported.matcher(s).replaceAll("");
	  }
	  
	  public void drawString(String text, double x, double y, FontType fontType, int color, int color2, boolean shadow)
	  {
	    text = stripUnsupported(text);
	    
	    GlStateManager.enableBlend();
	    GlStateManager.scale(0.5F, 0.5F, 0.5F);
	    
	    String text2 = stripControlCodes(text);
	    boolean skip = false;
	    switch (fontType)
	    {
	    case PLAIN: 
	      drawer(text2, x + 0.5D, y, color2, false);
	      drawer(text2, x - 0.5D, y, color2, false);
	      drawer(text2, x, y + 0.5D, color2, false);
	      drawer(text2, x, y - 0.5D, color2, false);
	      break;
	    case OVERLAY_BLUR: 
	      drawer(text2, x + 0.4000000059604645D, y + 0.4000000059604645D, color2, false);
	      break;
	    case OUTLINE_THIN: 
	      drawer(text2, x + 1.0D, y + 1.0D, color2, false);
	      break;
	    case EMBOSS_TOP: 
	      if (this.dynamicTextureBlurred != null) {
	        drawer(text2, x + 0.5D, y + 0.5D, color2, true);
	      }
	      break;
	    case SHADOW_THIN: 
	      if (this.dynamicTextureBlurred != null)
	      {
	        skip = true;
	        drawer(text, x, y, color, false);
	        drawer(text2, x, y, color2, true);
	      }
	      break;
	    case SHADOW_THICK: 
	      drawer(text2, x, y + 0.5D, color2, false);
	      break;
	    case SHADOW_BLURRED: 
	      drawer(text2, x, y - 0.5D, color2, false);
	      break;
	    case EMBOSS_BOTTOM: 
	      break;
	    }
	    if (shadow) {
	      color2 = 0 >> 2 | color & 0xFF000000;
	    }
	    if (!skip) {
	      drawer(text, x, y, color, false);
	    }
	    GlStateManager.scale(2.0F, 2.0F, 2.0F);
	    GL11.glHint(3155, 4352);
	  }
	  
	  private void drawer(String text, double x, double y, int color, boolean useBlur)
	  {
	    x *= 2.0D;
	    y *= 2.0D;
	    GlStateManager.func_179098_w();
	    if (useBlur) {
	      GlStateManager.func_179144_i(this.dynamicTextureBlurred.getGlTextureId());
	    } else {
	      GlStateManager.func_179144_i(this.dynamicTexture.getGlTextureId());
	    }
	    float alpha = (color >> 24 & 0xFF) / 255.0F;
	    float red = (color >> 16 & 0xFF) / 255.0F;
	    float green = (color >> 8 & 0xFF) / 255.0F;
	    float blue = (color & 0xFF) / 255.0F;
	    GlStateManager.color(red, green, blue, 255.0F);
	    double startX = x;
	    for (int i = 0; i < text.length(); i++) {
	      if ((text.charAt(i) == '§') && (i + 1 < text.length()))
	      {
	        char oneMore = Character.toLowerCase(text.charAt(i + 1));
	        if (oneMore == 'n')
	        {
	          y += this.theMetrics.getAscent() + 2;
	          x = startX;
	        }
	        int colorCode = "0123456789abcdefklmnoru".indexOf(oneMore);
	        if ((colorCode < 16) && (colorCode > -1))
	        {
	          int newColor = net.minecraft.client.Minecraft.getMinecraft().fontRendererObj.colorCode[colorCode];
	          GlStateManager.color((newColor >> 16) / 255.0F, 
	            (newColor >> 8 & 0xFF) / 255.0F, 
	            (newColor & 0xFF) / 255.0F, 
	            255.0F);
	        }
	        else if (oneMore == 'f')
	        {
	          GlStateManager.color(1.0F, 1.0F, 1.0F, 255.0F);
	        }
	        else if (oneMore == 'r')
	        {
	          GlStateManager.color(red, green, blue, 255.0F);
	        }
	        else if (oneMore == 'u')
	        {
	          int newColor = -10859859;
	          GlStateManager.color((newColor >> 16 & 0xFF) / 255.0F, 
	            (newColor >> 8 & 0xFF) / 255.0F, 
	            (newColor & 0xFF) / 255.0F, 
	            255.0F);
	        }
	        i++;
	      }
	      else
	      {
	        try
	        {
	          char c = text.charAt(i);
	          drawChar(c, x, y);
	          x += getStringWidth(Character.toString(c)) * 2.0F;
	        }
	        catch (ArrayIndexOutOfBoundsException indexException)
	        {
	          indexException.printStackTrace();
	        }
	      }
	    }
	  }
	  
	  private void drawChar(char character, double x, double y)
	  {
	    if ((character >= this.startChar) && (character <= this.endChar))
	    {
	      Rectangle2D bounds = this.theMetrics.getStringBounds(Character.toString(character), this.theGraphics);
	      drawTexturedModalRect(x, y - this.theMetrics.getAscent() / 2, this.xPos[(character - this.startChar)], this.yPos[(character - this.startChar)], bounds.getWidth(), getHeight());
	    }
	  }
	  
	  private void drawTexturedModalRect(double x, double y, double u, double v, double width, double height)
	  {
	    double scale = 1.0D / 512.0D;
	    GL11.glBegin(7);
	    GL11.glTexCoord2d(u * scale, v * scale);
	    GL11.glVertex2d(x, y);
	    GL11.glTexCoord2d(u * scale, (v + height) * scale);
	    GL11.glVertex2d(x, y + height);
	    GL11.glTexCoord2d((u + width) * scale, (v + height) * scale);
	    GL11.glVertex2d(x + width, y + height);
	    GL11.glTexCoord2d((u + width) * scale, v * scale);
	    GL11.glVertex2d(x + width, y);
	    GL11.glEnd();
	  }

	  public final float getHeight()
	  {
	    return this.theMetrics.getHeight() + this.theMetrics.getDescent() + 3;
	  }
	  
	public void drawStringWithShadowTag(String text, double d, double e, int color)
  {
    GlStateManager.enableBlend();
    drawString(text, d, e, FontType.SHADOW_THIN, color, 0, true);
    GlStateManager.disableBlend();
  }
	
	
}
