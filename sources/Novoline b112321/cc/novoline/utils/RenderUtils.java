package cc.novoline.utils;

import cc.novoline.Novoline;
import cc.novoline.modules.combat.InfiniteAura;
import cc.novoline.modules.combat.KillAura;
import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.visual.HUD;
import cc.novoline.modules.visual.PlayerESP;
import cc.novoline.utils.ScaleUtils;
import cc.novoline.utils.Timer;
import cc.novoline.utils.fonts.api.FontRenderer;
import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_22;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.awt.Color;
import java.util.Iterator;
import net.aIB;
import net.aLM;
import net.aSQ;
import net.acE;
import net.asJ;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor$ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.GL11;

public final class RenderUtils {
   public static double b;
   private static Minecraft mc = Minecraft.getInstance();
   private static double lastP = 0.0D;
   private static double a = 0.0D;
   private static final char[] RDM = new char[]{'a', '1', 'c', '3', 'e', '5', 'g', '7'};
   private static final Frustum frustum = new Frustum();
   private static double e;

   private RenderUtils() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static void setupRender(boolean var0) {
      String[] var1 = Timer.e();
      if(var0) {
         GlStateManager.enableBlend();
         GL11.glEnable(2848);
         GlStateManager.disableDepth();
         GlStateManager.disableTextures();
         GlStateManager.blendFunc(770, 771);
         GL11.glHint(3154, 4354);
      }

      GlStateManager.disableBlend();
      GlStateManager.enableTextures();
      GL11.glDisable(2848);
      GlStateManager.enableDepth();
      GlStateManager.depthMask(!var0);
   }

   public static void drawRainbowBox(double var0, double var2, double var4, double var6, float var8, boolean var9, boolean var10, boolean var11) {
      Timer.e();
      start2D();
      GL11.glPushMatrix();
      double var13 = Math.abs(var4 - var0);
      double var15 = Math.abs(var6 - var2);
      double var17 = var13 / 4.0D;
      double var19 = var15 / 4.0D;
      int var21 = 0;
      if(var13 != 0.0D && var15 != 0.0D) {
         if(var11) {
            GL11.glLineWidth(2.5F);
            setColor(Color.BLACK);
            GL11.glBegin(3);
            GL11.glVertex2d(var0 + var17, var2);
            GL11.glVertex2d(var0, var2);
            GL11.glVertex2d(var0, var2 + var19);
            GL11.glEnd();
            GL11.glBegin(3);
            GL11.glVertex2d(var0, var2 + var15 - var19);
            GL11.glVertex2d(var0, var2 + var15);
            GL11.glVertex2d(var0 + var17, var2 + var15);
            GL11.glEnd();
            GL11.glBegin(3);
            GL11.glVertex2d(var0 + var13 - var17, var2 + var15);
            GL11.glVertex2d(var0 + var13, var2 + var15);
            GL11.glVertex2d(var0 + var13, var2 + var15 - var19);
            GL11.glEnd();
            GL11.glBegin(3);
            GL11.glVertex2d(var0 + var13, var2 + var19);
            GL11.glVertex2d(var0 + var13, var2);
            GL11.glVertex2d(var0 + var13 - var17, var2);
            GL11.glEnd();
         }

         GL11.glLineWidth(1.5F);
         GL11.glBegin(3);
         double var22 = 0.0D;
         if(var22 <= var17) {
            setColor(new Color(getArrayAstolfo(var21, 255)));
            GL11.glVertex2d(var0 + var17 - var22, var2);
            ++var21;
            double var10000 = var22 + var17 / 8.0D;
         }

         var22 = 0.0D;
         if(var22 <= var19) {
            setColor(new Color(getArrayAstolfo(var21, 255)));
            GL11.glVertex2d(var0, var2 + var22);
            ++var21;
            double var39 = var22 + var19 / 8.0D;
         }

         GL11.glEnd();
         GL11.glBegin(3);
         var22 = 0.0D;
         if(var22 <= var19) {
            setColor(new Color(getArrayAstolfo(var21, 255)));
            GL11.glVertex2d(var0, var2 + var15 - var19 + var22);
            ++var21;
            double var40 = var22 + var19 / 8.0D;
         }

         var22 = 0.0D;
         if(var22 <= var17) {
            setColor(new Color(getArrayAstolfo(var21, 255)));
            GL11.glVertex2d(var0 + var22, var2 + var15);
            ++var21;
            double var41 = var22 + var17 / 8.0D;
         }

         GL11.glEnd();
         GL11.glBegin(3);
         var22 = 0.0D;
         if(var22 <= var17) {
            setColor(new Color(getArrayAstolfo(var21, 255)));
            GL11.glVertex2d(var0 + var13 - var17 + var22, var2 + var15);
            ++var21;
            double var42 = var22 + var17 / 8.0D;
         }

         var22 = 0.0D;
         if(var22 <= var19) {
            setColor(new Color(getArrayAstolfo(var21, 255)));
            GL11.glVertex2d(var0 + var13, var2 + var15 - var22);
            ++var21;
            double var43 = var22 + var19 / 8.0D;
         }

         GL11.glEnd();
         GL11.glBegin(3);
         var22 = 0.0D;
         if(var22 <= var19) {
            setColor(new Color(getArrayAstolfo(var21, 255)));
            GL11.glVertex2d(var0 + var13, var2 + var19 - var22);
            ++var21;
            double var44 = var22 + var19 / 8.0D;
         }

         var22 = 0.0D;
         if(var22 <= var17) {
            setColor(new Color(getArrayAstolfo(var21, 255)));
            GL11.glVertex2d(var0 + var13 - var22, var2);
            ++var21;
            double var45 = var22 + var17 / 8.0D;
         }

         GL11.glEnd();
      }

      GL11.glLineWidth(2.5F);
      var13 = Math.abs(var4 - var0);
      var15 = Math.abs(var6 - var2);
      if(var11) {
         setColor(new Color(-16777216));
         GL11.glBegin(3);
         GL11.glVertex2d(var0, var2);
         GL11.glVertex2d(var0 + (var4 - var0), var2);
         GL11.glVertex2d(var0 + (var4 - var0), var2 + (var6 - var2));
         GL11.glVertex2d(var0, var2 + (var6 - var2));
         GL11.glVertex2d(var0, var2);
         GL11.glEnd();
      }

      GL11.glLineWidth(1.5F);
      GL11.glBegin(3);
      int var26 = 0;
      if(var15 != 0.0D && var13 != 0.0D) {
         double var18 = 0.0D;
         if(var18 <= var15) {
            setColor(new Color(getArrayAstolfo(var26, 255)));
            GL11.glVertex2d(var0, var2 + var18);
            ++var26;
            double var46 = var18 + var15 / 10.0D;
         }

         var18 = 0.0D;
         if(var18 <= var13) {
            setColor(new Color(getArrayAstolfo(var26, 255)));
            GL11.glVertex2d(var0 + var18, var2 + var15);
            ++var26;
            double var47 = var18 + var13 / 10.0D;
         }

         var18 = 0.0D;
         if(var18 <= var15) {
            setColor(new Color(getArrayAstolfo(var26, 255)));
            GL11.glVertex2d(var0 + var13, var2 + var15 - var18);
            ++var26;
            double var48 = var18 + var15 / 10.0D;
         }

         var18 = 0.0D;
         if(var18 <= var13) {
            setColor(new Color(getArrayAstolfo(var26, 255)));
            GL11.glVertex2d(var0 + var13 - var18, var2);
            ++var26;
            double var49 = var18 + var13 / 10.0D;
         }
      }

      GL11.glVertex2d(var0, var2);
      GL11.glEnd();
      GL11.glPopMatrix();
      stop2D();
   }

   public static int getArrayRainbow(int var0, int var1) {
      boolean var2 = true;
      PlayerESP var3 = (PlayerESP)Novoline.getInstance().getModuleManager().getModule(PlayerESP.class);
      double var4 = Math.ceil((double)(System.currentTimeMillis() - (long)var0 * 110L)) / 11.0D;
      var4 = var4 % 360.0D;
      float[] var6 = var3.getColor().getHSB();
      Color var7 = Color.getHSBColor((float)(var4 / 360.0D), var6[1], var6[2]);
      return (new Color(var7.getRed(), var7.getGreen(), var7.getBlue(), var1)).getRGB();
   }

   public static int getArrayAstolfo(int var0, int var1) {
      Timer.e();
      boolean var3 = true;
      PlayerESP var4 = (PlayerESP)Novoline.getInstance().getModuleManager().getModule(PlayerESP.class);
      double var5 = Math.ceil((double)(System.currentTimeMillis() - (long)var0 * 110L)) / 11.0D;
      var5 = var5 % 360.0D;
      float var7 = (double)((float)(var5 / 360.0D)) < 0.5D?-((float)(var5 / 360.0D)):(float)(var5 / 360.0D);
      float[] var8 = var4.getColor().getHSB();
      Color var9 = Color.getHSBColor(var7, var8[1], var8[2]);
      return (new Color(var9.getRed(), var9.getGreen(), var9.getBlue(), var1)).getRGB();
   }

   public static int[] a(ColorProperty var0) {
      float var1 = (float)((System.currentTimeMillis() - 15L) % 6000L);
      var1 = var1 / 6000.0F;
      float[] var2 = var0.getHSB();
      Color var3 = Color.getHSBColor(var1, var2[1], var2[2]);
      return new int[]{var3.getRed(), var3.getGreen(), var3.getBlue()};
   }

   public static int a() {
      float var0 = (float)((System.currentTimeMillis() - 15L) % 6000L);
      var0 = var0 / 6000.0F;
      Color var1 = Color.getHSBColor(var0, 1.0F, 1.0F);
      return var1.getRGB();
   }

   public static int e() {
      float var0 = (float)((System.currentTimeMillis() - 15L) % 6000L);
      var0 = var0 / 6000.0F;
      Color var1 = Color.getHSBColor(var0, 1.0F, 1.0F);
      return var1.getRGB();
   }

   public static int c(ColorProperty var0) {
      Timer.e();
      double var2 = Math.ceil((double)(System.currentTimeMillis() - 15L)) / 20.0D;
      var2 = var2 % 360.0D;
      float var4 = (double)((float)(var2 / 360.0D)) < 0.5D?-((float)(var2 / 360.0D)):(float)(var2 / 360.0D);
      float[] var5 = var0.getHSB();
      Color var6 = Color.getHSBColor(var4, var5[1], var5[2]);
      return var6.getRGB();
   }

   public static int[] b(ColorProperty var0) {
      Timer.e();
      double var2 = Math.ceil((double)(System.currentTimeMillis() - 15L)) / 20.0D;
      var2 = var2 % 360.0D;
      float var4 = (double)((float)(var2 / 360.0D)) < 0.5D?-((float)(var2 / 360.0D)):(float)(var2 / 360.0D);
      float[] var5 = var0.getHSB();
      Color var6 = Color.getHSBColor(var4, var5[1], var5[2]);
      return new int[]{var6.getRed(), var6.getGreen(), var6.getBlue()};
   }

   public static void a(double var0, double var2, double var4, int var6) {
      double var7 = var0 - mc.getRenderManager().renderPosX;
      double var9 = var2 - mc.getRenderManager().renderPosY;
      double var11 = var4 - mc.getRenderManager().renderPosZ;
      double var13 = mc.world.getBlockState(new BlockPos(var0, var2, var4)).getBlock().getBlockBoundsMaxY() - mc.world.getBlockState(new BlockPos(var0, var2, var4)).getBlock().getBlockBoundsMinY();
      float var15 = (float)(var6 >> 16 & 255) / 255.0F;
      float var16 = (float)(var6 >> 8 & 255) / 255.0F;
      float var17 = (float)(var6 & 255) / 255.0F;
      float var18 = (float)(var6 >> 24 & 255) / 255.0F;
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glLineWidth(1.0F);
      GL11.glColor4f(var15, var16, var17, var18);
      drawOutlinedBoundingBox(new AxisAlignedBB(var7, var9, var11, var7 + 1.0D, var9 + var13, var11 + 1.0D));
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawSolidBlockESP(BlockPos var0, int var1) {
      double var2 = (double)var0.getX() - mc.getRenderManager().renderPosX;
      double var4 = (double)var0.getY() - mc.getRenderManager().renderPosY;
      double var6 = (double)var0.getZ() - mc.getRenderManager().renderPosZ;
      double var8 = mc.world.getBlockState(var0).getBlock().getBlockBoundsMaxY() - mc.world.getBlockState(var0).getBlock().getBlockBoundsMinY();
      float var10 = (float)(var1 >> 16 & 255) / 255.0F;
      float var11 = (float)(var1 >> 8 & 255) / 255.0F;
      float var12 = (float)(var1 & 255) / 255.0F;
      float var13 = (float)(var1 >> 24 & 255) / 255.0F;
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glLineWidth(1.0F);
      GL11.glColor4f(var10, var11, var12, var13);
      drawOutlinedBoundingBox(new AxisAlignedBB(var2, var4, var6, var2 + 1.0D, var4 + var8, var6 + 1.0D));
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
      GlStateManager.disableBlend();
      GL11.glPopMatrix();
   }

   public static Vec3 interpolateRender(EntityPlayer var0) {
      float var1 = Minecraft.getInstance().timer.renderPartialTicks;
      double var2 = var0.lastTickPosX + (var0.posX - var0.lastTickPosX) * (double)var1;
      double var4 = var0.lastTickPosY + (var0.posY - var0.lastTickPosY) * (double)var1;
      double var6 = var0.lastTickPosZ + (var0.posZ - var0.lastTickPosZ) * (double)var1;
      return new Vec3(var2, var4, var6);
   }

   public static float[] getRGBAs(int var0) {
      return new float[]{(float)(var0 >> 16 & 255) / 255.0F, (float)(var0 >> 8 & 255) / 255.0F, (float)(var0 & 255) / 255.0F, (float)(var0 >> 24 & 255) / 255.0F};
   }

   public static void a(String var0, float var1, float var2) {
      Timer.e();
      HUD var5 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
      int var6 = 0;
      if(var6 < var0.length()) {
         Minecraft var7 = Minecraft.getInstance();
         int var8 = (new Color(255, 255, 255)).getRGB();
         var7.fontRendererObj.drawStringWithShadow(String.valueOf(var0.charAt(var6)), var1, var2, var8);
         float var4 = var1 + (float)var7.fontRendererObj.getCharWidth(var0.charAt(var6));
         ++var6;
      }

   }

   public static void a(FontRenderer var0, String var1, float var2, float var3) {
      Timer.e();
      HUD var6 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
      int var7 = 0;
      if(var7 < var1.length()) {
         Minecraft var8 = Minecraft.getInstance();
         int var9 = (new Color(255, 255, 255)).getRGB();
         var0.drawString(String.valueOf(var1.charAt(var7)), (double)var2, (double)var3, var9, true);
         float var5 = var2 + (float)var0.stringWidth(String.valueOf(var1.charAt(var7))) + 0.5F;
         ++var7;
      }

   }

   public static String obfuscateString(String var0, String var1) {
      Timer.e();
      String var3 = "";
      int var4 = 0;
      if(var4 < var0.length()) {
         char var5 = RDM[MathHelper.randomNumber(RDM.length - 1, 0)];
         var3 = var3.concat(String.valueOf(var5));
         ++var4;
      }

      return var1.contains(var0)?var1.replace(var0, "§k" + var3 + "§r"):null;
   }

   public static void rectangle(double var0, double var2, double var4, double var6, int var8) {
      String[] var9 = Timer.e();
      if(var0 < var4) {
         double var10 = var0;
         var0 = var4;
         var4 = var10;
      }

      if(var2 < var6) {
         double var18 = var2;
         var2 = var6;
         var6 = var18;
      }

      float var12 = (float)(var8 >> 24 & 255) / 255.0F;
      float var13 = (float)(var8 >> 16 & 255) / 255.0F;
      float var14 = (float)(var8 >> 8 & 255) / 255.0F;
      float var15 = (float)(var8 & 255) / 255.0F;
      Tessellator var16 = Tessellator.getInstance();
      WorldRenderer var17 = var16.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTextures();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(var13, var14, var15, var12);
      var17.startDrawingQuads();
      var17.pos(var0, var6, 0.0D).endVertex();
      var17.pos(var4, var6, 0.0D).endVertex();
      var17.pos(var4, var2, 0.0D).endVertex();
      var17.pos(var0, var2, 0.0D).endVertex();
      var16.draw();
      GlStateManager.enableTextures();
      GlStateManager.disableBlend();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static int getRainbow(int var0, int var1) {
      float var2 = (float)((System.currentTimeMillis() + (long)var1) % (long)var0);
      var2 = var2 / (float)var0;
      return Color.getHSBColor(var2, 0.75F, 1.0F).getRGB();
   }

   public static void rectangleBordered(double var0, double var2, double var4, double var6, double var8, int var10, int var11) {
      rectangle(var0 + var8, var2 + var8, var4 - var8, var6 - var8, var10);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      rectangle(var0 + var8, var2, var4 - var8, var2 + var8, var11);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      rectangle(var0, var2, var0 + var8, var6, var11);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      rectangle(var4 - var8, var2, var4, var6, var11);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      rectangle(var0 + var8, var6 - var8, var4 - var8, var6, var11);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawOutlinedBoundingBox(AxisAlignedBB var0) {
      Tessellator var1 = Tessellator.getInstance();
      WorldRenderer var2 = var1.getWorldRenderer();
      var2.begin(3, DefaultVertexFormats.POSITION);
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var1.draw();
      var2.begin(3, DefaultVertexFormats.POSITION);
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var1.draw();
      var2.begin(1, DefaultVertexFormats.POSITION);
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.maxZ).endVertex();
      var1.draw();
   }

   public static void drawFilledBox(@NotNull AxisAlignedBB var0) {
      WorldRenderer var2 = Tessellator.getInstance().getWorldRenderer();
      Tessellator var3 = Tessellator.getInstance();
      var2.startDrawingQuads();
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      Timer.e();
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.maxZ).endVertex();
      var3.draw();
      var2.startDrawingQuads();
      var2.pos(var0.maxX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.maxZ).endVertex();
      var3.draw();
      var2.startDrawingQuads();
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.minZ).endVertex();
      var3.draw();
      var2.startDrawingQuads();
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.minZ).endVertex();
      var3.draw();
      var2.startDrawingQuads();
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.minZ).endVertex();
      var3.draw();
      var2.startDrawingQuads();
      var2.pos(var0.minX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.maxZ).endVertex();
      var3.draw();
      if(acE.b() == null) {
         Timer.b(new String[3]);
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

   public static void glColor(int var0) {
      float var1 = (float)(var0 >> 24 & 255) / 255.0F;
      float var2 = (float)(var0 >> 16 & 255) / 255.0F;
      float var3 = (float)(var0 >> 8 & 255) / 255.0F;
      float var4 = (float)(var0 & 255) / 255.0F;
      GL11.glColor4f(var2, var3, var4, var1);
   }

   public static void drawOutlinedEntityESP(double var0, double var2, double var4, double var6, double var8, float var10, float var11, float var12, float var13) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(var10, var11, var12, var13);
      drawOutlinedBoundingBox(new AxisAlignedBB(var0 - var6, var2, var4 - var6, var0 + var6, var2 + var8, var4 + var6));
      drawFilledBox(new AxisAlignedBB(var0 - var6, var2, var4 - var6, var0 + var6, var2 + var8, var4 + var6));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void a(int var0, int var1, float var2, EntityLivingBase var3) {
      GlStateManager.enableColorMaterial();
      GlStateManager.pushMatrix();
      GlStateManager.color(255.0F, 255.0F, 255.0F);
      GlStateManager.translate((float)var0, (float)var1, 50.0F);
      GlStateManager.scale(-var2, var2, var2);
      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.translate(0.0F, 0.0F, 0.0F);
      RenderManager var4 = Minecraft.getInstance().getRenderManager();
      var4.setPlayerViewY(1.0F);
      var4.setRenderShadow(false);
      aIB.a(var4, var3, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
      var4.setRenderShadow(true);
      GlStateManager.popMatrix();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableRescaleNormal();
      ARBMultitexture.glActiveTextureARB(OpenGlHelper.lightmapTexUnit);
      GlStateManager.disableTexture2D();
      ARBMultitexture.glActiveTextureARB(OpenGlHelper.defaultTexUnit);
   }

   public static int a(Color var0, Color var1, float var2) {
      int var3 = (int)((float)var0.getRed() + (float)(var1.getRed() - var0.getRed()) * var2);
      int var4 = (int)((float)var0.getGreen() + (float)(var1.getGreen() - var0.getGreen()) * var2);
      int var5 = (int)((float)var0.getBlue() + (float)(var1.getBlue() - var0.getBlue()) * var2);
      int var6 = (int)((float)var0.getAlpha() + (float)(var1.getAlpha() - var0.getAlpha()) * var2);

      try {
         return (new Color(var3, var4, var5, var6)).getRGB();
      } catch (Exception var8) {
         return -1;
      }
   }

   public static double interpolate(double var0, double var2, double var4) {
      return var2 + (var0 - var2) * var4;
   }

   public static boolean isInViewFrustrum(Entity var0) {
      String[] var1 = Timer.e();
      return isInViewFrustrum(var0.getEntityBoundingBox()) || var0.ignoreFrustumCheck;
   }

   public static boolean isInViewFrustrum(AxisAlignedBB var0) {
      Entity var1 = Minecraft.getInstance().getRenderViewEntity();
      frustum.setPosition(var1.posX, var1.posY, var1.posZ);
      return frustum.isBoundingBoxInFrustum(var0);
   }

   public static void a(float var0, float var1, float var2, float var3, Color var4, boolean var5) {
      start2D();
      GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
      GL11.glDisable(3553);
      GL11.glLineWidth(1.0F);
      GL11.glBegin(7);
      GL11.glVertex2f(var0, var1);
      GL11.glVertex2f(var0, var3);
      GL11.glVertex2f(var0 + 1.5F, var3);
      GL11.glVertex2f(var0 + 1.5F, var1);
      GL11.glVertex2f(var2 - 1.5F, var1);
      GL11.glVertex2f(var2 - 1.5F, var3);
      GL11.glVertex2f(var2, var3);
      GL11.glVertex2f(var2, var1);
      GL11.glVertex2f(var0 + 1.5F, var1);
      GL11.glVertex2f(var0 + 1.5F, var1 + 1.5F);
      GL11.glVertex2f(var2 - 1.5F, var1 + 1.5F);
      GL11.glVertex2f(var2 - 1.5F, var1);
      GL11.glVertex2f(var0 + 1.5F, var3 - 1.5F);
      GL11.glVertex2f(var0 + 1.5F, var3);
      GL11.glVertex2f(var2 - 1.5F, var3);
      GL11.glVertex2f(var2 - 1.5F, var3 - 1.5F);
      setColor(var4);
      GL11.glVertex2f(var0 + 0.5F, var1 + 0.5F);
      GL11.glVertex2f(var0 + 0.5F, var3 - 0.5F);
      GL11.glVertex2f(var0 + 1.0F, var3 - 0.5F);
      GL11.glVertex2f(var0 + 1.0F, var1 + 0.5F);
      GL11.glVertex2f(var2 - 1.0F, var1 + 0.5F);
      GL11.glVertex2f(var2 - 1.0F, var3 - 0.5F);
      GL11.glVertex2f(var2 - 0.5F, var3 - 0.5F);
      GL11.glVertex2f(var2 - 0.5F, var1 + 0.5F);
      GL11.glVertex2f(var0 + 0.5F, var1 + 0.5F);
      GL11.glVertex2f(var0 + 0.5F, var1 + 1.0F);
      GL11.glVertex2f(var2 - 0.5F, var1 + 1.0F);
      GL11.glVertex2f(var2 - 0.5F, var1 + 0.5F);
      GL11.glVertex2f(var0 + 0.5F, var3 - 1.0F);
      GL11.glVertex2f(var0 + 0.5F, var3 - 0.5F);
      GL11.glVertex2f(var2 - 0.5F, var3 - 0.5F);
      GL11.glVertex2f(var2 - 0.5F, var3 - 1.0F);
      GL11.glEnd();
      GL11.glEnable(3553);
      stop2D();
   }

   public static void a(float var0, float var1, float var2, float var3, float var4, float var5, Color var6, boolean var7) {
      start2D();
      GL11.glPushMatrix();
      GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
      GL11.glDisable(3553);
      GL11.glLineWidth(1.0F);
      GL11.glBegin(7);
      GL11.glVertex2f(var0, var1);
      GL11.glVertex2f(var0, var3);
      GL11.glVertex2f(var0 + 1.5F, var3);
      GL11.glVertex2f(var0 + 1.5F, var1);
      GL11.glVertex2f(var2 - 1.5F, var1);
      GL11.glVertex2f(var2 - 1.5F, var3);
      GL11.glVertex2f(var2, var3);
      GL11.glVertex2f(var2, var1);
      GL11.glVertex2f(var0 + 1.5F, var1);
      GL11.glVertex2f(var0 + 1.5F, var1 + 1.5F);
      GL11.glVertex2f(var2 - 1.5F, var1 + 1.5F);
      GL11.glVertex2f(var2 - 1.5F, var1);
      GL11.glVertex2f(var0 + 1.5F, var3 - 1.5F);
      GL11.glVertex2f(var0 + 1.5F, var3);
      GL11.glVertex2f(var2 - 1.5F, var3);
      GL11.glVertex2f(var2 - 1.5F, var3 - 1.5F);
      setColor(var6.darker().darker().darker());
      GL11.glVertex2f(var0 + 0.5F, var1 + 0.5F);
      GL11.glVertex2f(var0 + 0.5F, var3 - 0.5F);
      GL11.glVertex2f(var0 + 1.0F, var3 - 0.5F);
      GL11.glVertex2f(var0 + 1.0F, var1 + 0.5F);
      GL11.glVertex2f(var2 - 1.0F, var1 + 0.5F);
      GL11.glVertex2f(var2 - 1.0F, var3 - 0.5F);
      GL11.glVertex2f(var2 - 0.5F, var3 - 0.5F);
      GL11.glVertex2f(var2 - 0.5F, var1 + 0.5F);
      GL11.glVertex2f(var0 + 0.5F, var1 + 0.5F);
      GL11.glVertex2f(var0 + 0.5F, var1 + 1.0F);
      GL11.glVertex2f(var2 - 0.5F, var1 + 1.0F);
      GL11.glVertex2f(var2 - 0.5F, var1 + 0.5F);
      GL11.glVertex2f(var0 + 0.5F, var3 - 1.0F);
      GL11.glVertex2f(var0 + 0.5F, var3 - 0.5F);
      GL11.glVertex2f(var2 - 0.5F, var3 - 0.5F);
      GL11.glVertex2f(var2 - 0.5F, var3 - 1.0F);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glPopMatrix();
      stop2D();
   }

   public static void drawBorderedRect(float var0, float var1, float var2, float var3, float var4, int var5, int var6) {
      drawRect(var0, var1, var2, var3, var6);
      float var7 = (float)(var5 >> 24 & 255) / 255.0F;
      float var8 = (float)(var5 >> 16 & 255) / 255.0F;
      float var9 = (float)(var5 >> 8 & 255) / 255.0F;
      float var10 = (float)(var5 & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glPushMatrix();
      GL11.glColor4f(var8, var9, var10, var7);
      GL11.glLineWidth(var4);
      GL11.glBegin(1);
      GL11.glVertex2d((double)var0, (double)var1);
      GL11.glVertex2d((double)var0, (double)var3);
      GL11.glVertex2d((double)var2, (double)var3);
      GL11.glVertex2d((double)var2, (double)var1);
      GL11.glVertex2d((double)var0, (double)var1);
      GL11.glVertex2d((double)var2, (double)var1);
      GL11.glVertex2d((double)var0, (double)var3);
      GL11.glVertex2d((double)var2, (double)var3);
      GL11.glEnd();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 255.0F);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   public static void drawBorderedRect(double var0, double var2, double var4, double var6, float var8, int var9, int var10) {
      drawRect((float)var0, (float)var2, (float)var4, (float)var6, var10);
      float var11 = (float)(var9 >> 24 & 255) / 255.0F;
      float var12 = (float)(var9 >> 16 & 255) / 255.0F;
      float var13 = (float)(var9 >> 8 & 255) / 255.0F;
      float var14 = (float)(var9 & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glPushMatrix();
      GL11.glColor4f(var12, var13, var14, var11);
      GL11.glLineWidth(var8);
      GL11.glBegin(1);
      GL11.glVertex2d(var0, var2);
      GL11.glVertex2d(var0, var6);
      GL11.glVertex2d(var4, var6);
      GL11.glVertex2d(var4, var2);
      GL11.glVertex2d(var0, var2);
      GL11.glVertex2d(var4, var2);
      GL11.glVertex2d(var0, var6);
      GL11.glVertex2d(var4, var6);
      GL11.glEnd();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GL11.glPopMatrix();
      GL11.glColor4f(255.0F, 1.0F, 1.0F, 255.0F);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   public static void drawRoundedRect(float var0, float var1, float var2, float var3, float var4, int var5) {
      float var7 = var0 + var2;
      Timer.e();
      float var8 = var1 + var3;
      float var9 = (float)(var5 >> 24 & 255) / 255.0F;
      float var10 = (float)(var5 >> 16 & 255) / 255.0F;
      float var11 = (float)(var5 >> 8 & 255) / 255.0F;
      float var12 = (float)(var5 & 255) / 255.0F;
      GL11.glPushAttrib(0);
      GL11.glScaled(0.5D, 0.5D, 0.5D);
      var0 = var0 * 2.0F;
      var1 = var1 * 2.0F;
      var7 = var7 * 2.0F;
      var8 = var8 * 2.0F;
      GL11.glDisable(3553);
      GL11.glColor4f(var10, var11, var12, var9);
      GlStateManager.enableBlend();
      GL11.glEnable(2848);
      GL11.glBegin(9);
      double var13 = 0.017453292519943295D;
      int var15 = 0;
      if(var15 <= 90) {
         GL11.glVertex2d((double)(var0 + var4 + MathHelper.sin((double)var15 * 0.017453292519943295D) * var4 * -1.0F), (double)(var1 + var4 + MathHelper.cos((double)var15 * 0.017453292519943295D) * var4 * -1.0F));
         var15 = var15 + 3;
      }

      var15 = 90;
      if(var15 <= 180) {
         GL11.glVertex2d((double)(var0 + var4 + MathHelper.sin((double)var15 * 0.017453292519943295D) * var4 * -1.0F), (double)(var8 - var4 + MathHelper.cos((double)var15 * 0.017453292519943295D) * var4 * -1.0F));
         var15 = var15 + 3;
      }

      var15 = 0;
      if(var15 <= 90) {
         GL11.glVertex2d((double)(var7 - var4 + MathHelper.sin((double)var15 * 0.017453292519943295D) * var4), (double)(var8 - var4 + MathHelper.cos((double)var15 * 0.017453292519943295D) * var4));
         var15 = var15 + 3;
      }

      var15 = 90;
      if(var15 <= 180) {
         GL11.glVertex2d((double)(var7 - var4 + MathHelper.sin((double)var15 * 0.017453292519943295D) * var4), (double)(var1 + var4 + MathHelper.cos((double)var15 * 0.017453292519943295D) * var4));
         var15 = var15 + 3;
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glScaled(2.0D, 2.0D, 2.0D);
      GL11.glPopAttrib();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   @NotNull
   public static Color blend(@NotNull Color var0, @NotNull Color var1, double var2) {
      float var4 = (float)var2;
      float var5 = 1.0F - var4;
      float[] var6 = new float[3];
      float[] var7 = new float[3];
      var0.getColorComponents(var6);
      var1.getColorComponents(var7);
      return new Color(var6[0] * var4 + var7[0] * var5, var6[1] * var4 + var7[1] * var5, var6[2] * var4 + var7[2] * var5);
   }

   public static void drawRect(float var0, float var1, float var2, float var3, int var4) {
      float var5 = (float)(var4 >> 24 & 255) / 255.0F;
      float var6 = (float)(var4 >> 16 & 255) / 255.0F;
      float var7 = (float)(var4 >> 8 & 255) / 255.0F;
      float var8 = (float)(var4 & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glPushMatrix();
      GL11.glColor4f(var6, var7, var8, var5);
      GL11.glBegin(7);
      GL11.glVertex2d((double)var2, (double)var1);
      GL11.glVertex2d((double)var0, (double)var1);
      GL11.glVertex2d((double)var0, (double)var3);
      GL11.glVertex2d((double)var2, (double)var3);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawBoundingBox(@NotNull AxisAlignedBB var0) {
      Tessellator var2 = Tessellator.getInstance();
      WorldRenderer var3 = var2.getWorldRenderer();
      var3.begin(3, DefaultVertexFormats.POSITION_TEX);
      var3.pos(var0.minX, var0.minY, var0.minZ);
      var3.pos(var0.minX, var0.maxY, var0.minZ);
      var3.pos(var0.maxX, var0.minY, var0.minZ);
      var3.pos(var0.maxX, var0.maxY, var0.minZ);
      var3.pos(var0.maxX, var0.minY, var0.maxZ);
      var3.pos(var0.maxX, var0.maxY, var0.maxZ);
      Timer.e();
      var3.pos(var0.minX, var0.minY, var0.maxZ);
      var3.pos(var0.minX, var0.maxY, var0.maxZ);
      var2.draw();
      var3.begin(3, DefaultVertexFormats.POSITION_TEX);
      var3.pos(var0.maxX, var0.maxY, var0.minZ);
      var3.pos(var0.maxX, var0.minY, var0.minZ);
      var3.pos(var0.minX, var0.maxY, var0.minZ);
      var3.pos(var0.minX, var0.minY, var0.minZ);
      var3.pos(var0.minX, var0.maxY, var0.maxZ);
      var3.pos(var0.minX, var0.minY, var0.maxZ);
      var3.pos(var0.maxX, var0.maxY, var0.maxZ);
      var3.pos(var0.maxX, var0.minY, var0.maxZ);
      var2.draw();
      var3.begin(3, DefaultVertexFormats.POSITION_TEX);
      var3.pos(var0.minX, var0.maxY, var0.minZ);
      var3.pos(var0.maxX, var0.maxY, var0.minZ);
      var3.pos(var0.maxX, var0.maxY, var0.maxZ);
      var3.pos(var0.minX, var0.maxY, var0.maxZ);
      var3.pos(var0.minX, var0.maxY, var0.minZ);
      var3.pos(var0.minX, var0.maxY, var0.maxZ);
      var3.pos(var0.maxX, var0.maxY, var0.maxZ);
      var3.pos(var0.maxX, var0.maxY, var0.minZ);
      var2.draw();
      var3.begin(3, DefaultVertexFormats.POSITION_TEX);
      var3.pos(var0.minX, var0.minY, var0.minZ);
      var3.pos(var0.maxX, var0.minY, var0.minZ);
      var3.pos(var0.maxX, var0.minY, var0.maxZ);
      var3.pos(var0.minX, var0.minY, var0.maxZ);
      var3.pos(var0.minX, var0.minY, var0.minZ);
      var3.pos(var0.minX, var0.minY, var0.maxZ);
      var3.pos(var0.maxX, var0.minY, var0.maxZ);
      var3.pos(var0.maxX, var0.minY, var0.minZ);
      var2.draw();
      var3.begin(3, DefaultVertexFormats.POSITION_TEX);
      var3.pos(var0.minX, var0.minY, var0.minZ);
      var3.pos(var0.minX, var0.maxY, var0.minZ);
      var3.pos(var0.minX, var0.minY, var0.maxZ);
      var3.pos(var0.minX, var0.maxY, var0.maxZ);
      var3.pos(var0.maxX, var0.minY, var0.maxZ);
      var3.pos(var0.maxX, var0.maxY, var0.maxZ);
      var3.pos(var0.maxX, var0.minY, var0.minZ);
      var3.pos(var0.maxX, var0.maxY, var0.minZ);
      var2.draw();
      var3.begin(3, DefaultVertexFormats.POSITION_TEX);
      var3.pos(var0.minX, var0.maxY, var0.maxZ);
      var3.pos(var0.minX, var0.minY, var0.maxZ);
      var3.pos(var0.minX, var0.maxY, var0.minZ);
      var3.pos(var0.minX, var0.minY, var0.minZ);
      var3.pos(var0.maxX, var0.maxY, var0.minZ);
      var3.pos(var0.maxX, var0.minY, var0.minZ);
      var3.pos(var0.maxX, var0.maxY, var0.maxZ);
      var3.pos(var0.maxX, var0.minY, var0.maxZ);
      var2.draw();
   }

   public static void drawCircle(float var0, float var1, float var2, float var3, int var4) {
      GL11.glPushMatrix();
      var0 = var0 * 2.0F;
      var1 = var1 * 2.0F;
      Timer.e();
      float var6 = (float)(var4 >> 24 & 255) / 255.0F;
      float var7 = (float)(var4 >> 16 & 255) / 255.0F;
      float var8 = (float)(var4 >> 8 & 255) / 255.0F;
      float var9 = (float)(var4 & 255) / 255.0F;
      float var10 = 6.283185F / var3;
      float var11 = MathHelper.cos(var10);
      float var12 = MathHelper.sin(var10);
      float var13 = var2 * 2.0F;
      float var14 = 0.0F;
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      GL11.glColor4f(var7, var8, var9, var6);
      GL11.glBegin(2);
      int var15 = 0;
      if((float)var15 < var3) {
         GL11.glVertex2f(var13 + var0, var14 + var1);
         var13 = var11 * var13 - var12 * var14;
         var14 = var12 * var13 + var11 * var14;
         ++var15;
      }

      GL11.glEnd();
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      disableGL2D();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   public static int getHealthColor(@NotNull EntityLivingBase var0) {
      float var1 = var0.getHealth();
      float var2 = var0.getMaxHealth();
      float var3 = Math.max(0.0F, Math.min(var1, var2) / var2);
      return Color.HSBtoRGB(var3 / 3.5F, 1.0F, 0.9F) | -16777216;
   }

   public static void drawFilledCircle(float var0, float var1, float var2, int var3) {
      float var5 = (float)(var3 >> 24 & 255) / 255.0F;
      float var6 = (float)(var3 >> 16 & 255) / 255.0F;
      float var7 = (float)(var3 >> 8 & 255) / 255.0F;
      float var8 = (float)(var3 & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glBlendFunc(770, 771);
      Timer.e();
      GL11.glColor4f(var6, var7, var8, var5);
      GL11.glBegin(6);
      float var9 = 3.1415927F;
      int var10 = 0;
      if(var10 <= 360) {
         GL11.glVertex2d((double)(var0 + MathHelper.sin((float)var10 * 3.1415927F / 180.0F) * var2), (double)(var1 + MathHelper.cos((float)var10 * 3.1415927F / 180.0F) * var2));
         ++var10;
      }

      GL11.glEnd();
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
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
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void dr(double var0, double var2, double var4, double var6, int var8) {
      String[] var9 = Timer.e();
      if(var0 < var4) {
         double var10 = var0;
         var0 = var4;
         var4 = var10;
      }

      if(var2 < var6) {
         double var16 = var2;
         var2 = var6;
         var6 = var16;
      }

      float var17 = (float)(var8 >> 24 & 255) / 255.0F;
      float var11 = (float)(var8 >> 16 & 255) / 255.0F;
      float var12 = (float)(var8 >> 8 & 255) / 255.0F;
      float var13 = (float)(var8 & 255) / 255.0F;
      Tessellator var14 = Tessellator.getInstance();
      WorldRenderer var15 = var14.getWorldRenderer();
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(var11, var12, var13, var17);
      var15.startDrawingQuads();
      var15.pos(var0, var6, 0.0D);
      var15.pos(var4, var6, 0.0D);
      var15.pos(var4, var2, 0.0D);
      var15.pos(var0, var2, 0.0D);
      var14.draw();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   public static void a(KillAura var0, EntityLivingBase var1) {
      Timer.e();
      GL11.glPushMatrix();
      ScaleUtils.scale(Minecraft.getInstance());
      Minecraft var3 = Minecraft.getInstance();
      ScaledResolution var4 = new ScaledResolution(var3);
      if(((Integer)var0.getThx().get()).intValue() > var4.getScaledWidthStatic(var3) - 50) {
         var0.getThx().set(Integer.valueOf(var4.getScaledWidthStatic(var3) - 50));
      }

      if(((Integer)var0.getThy().get()).intValue() > var4.getScaledHeightStatic(var3) - 50) {
         var0.getThy().set(Integer.valueOf(var4.getScaledHeightStatic(var3) - 50));
      }

      if(var0.B().equals("Prettier")) {
         float var5 = var1.getHealth() + var1.getAbsorptionAmount();
         float var6 = var1.getMaxHealth() + var1.getAbsorptionAmount() - 0.05F;
         int var7 = 0;
         int var8 = 0;
         if(var8 < ((EntityPlayer)var1).inventory.armorInventory.length) {
            ItemStack var9 = ((EntityPlayer)var1).inventory.armorInventory[var8];
            if(var9 != null) {
               ++var7;
            }

            ++var8;
         }

         if(((EntityPlayer)var1).getCurrentEquippedItem() != null) {
            ++var7;
         }

         float var11;
         float var12;
         int var13;
         int var14;
         label236: {
            var41 = (float)Math.max(var3.fontRendererObj.d(var1.getName()) + 45, 16 * var7 + 45);
            float var45 = (float)((double)Math.round((double)var5 * 100.0D) / 100.0D);
            float var10 = 100.0F / var6;
            var11 = var45 * var10;
            var12 = var41 / 100.0F;
            var13 = ((Integer)var0.getThx().get()).intValue();
            var14 = ((Integer)var0.getThy().get()).intValue();
            if(e > (double)var11) {
               e = (double)var11;
               if(var11 >= 95.0F) {
                  break label236;
               }

               b = (double)(var12 * 3.0F);
            }

            if(e < (double)var11) {
               e = (double)var11;
            }

            if(b > 0.0D) {
               b -= (double)(var12 / 10.0F);
            }
         }

         a(var13 + 16, var14 + 35, 16.0F, var1);
         Gui.drawRect((double)var13, (double)var14, (double)((float)var13 + var41), (double)(var14 + 42), (new Color(0, 0, 0, 120)).getRGB());
         Gui.drawRect((double)var13, (double)(var14 + 40), (double)((float)var13 + var11 * var12) + b, (double)(var14 + 42), getHealthColor(var1));
         var3.fontRendererObj.drawStringWithShadow(var1.getName(), (float)(var13 + 35), (float)(var14 + 7), -1);
         drawArmorHUD((EntityPlayer)var1, var13, var14);
      }

      if(var0.B().equals("Prettiest")) {
         float var28 = var1.getHealth() + var1.getAbsorptionAmount();
         float var32 = var1.getMaxHealth() + var1.getAbsorptionAmount() - 0.05F;
         float var37 = (float)(35 + var3.fontRendererCrack.d(var1.getName()) + 40);
         float var42 = (float)((double)Math.round((double)var28 * 100.0D) / 100.0D);
         if(var42 > var32) {
            var42 *= var32 / var42;
         }

         float var46 = 100.0F / var32;
         float var48 = var42 * var46;
         float var51 = (var37 - 50.0F) / 100.0F;
         int var53 = ((Integer)var0.getThx().get()).intValue();
         int var57 = ((Integer)var0.getThy().get()).intValue();
         if((double)var48 < lastP) {
            a = lastP - (double)var48;
         }

         lastP = (double)var48;
         if(a > 0.0D) {
            a += (0.0D - a) * 0.05000000074505806D;
         }

         a = MathHelper.clamp_double(a, 0.0D, (double)(100.0F - var48));
         var3.getTextureManager().bindTexture(((asJ)var1).getLocationSkin());
         drawBorderedRect((double)var53, (double)((float)var57 - 1.5F), (double)((float)var53 + var37 - 6.0F), (double)var57 + 37.5D, 1.0F, (new Color(0, 0, 0, 50)).getRGB(), (new Color(29, 29, 29, 255)).getRGB());
         Gui.drawRect((double)(var53 + 1), (double)var57, (double)((float)var53 + var37 - 7.0F), (double)(var57 + 36), (new Color(40, 40, 40, 255)).getRGB());
         GL11.glPushMatrix();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         boolean var61 = true;
         boolean var15 = true;
         GL11.glScaled(4.4D, 4.4D, 4.4D);
         aSQ.a((float)(((double)var53 + 1.5D) / 4.4D), (float)(((double)var57 + 0.2D) / 4.4D), 8.0F, 8.0F, 8, 8, 8, 8, 64.0F, 64.0F);
         GL11.glPopMatrix();
         int var16 = ((HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class)).getHUDColor();
         Gui.drawRect((double)(var53 + 40), (double)var57 + 16.5D, (double)((float)(var53 + 40) + 100.0F * var51), (double)var57 + 27.3D, (new Color(0, 0, 0, 50)).getRGB());
         Gui.drawRect((double)(var53 + 40), (double)var57 + 16.5D, (double)((float)(var53 + 40) + var48 * var51), (double)var57 + 27.3D, var16);
         Gui.drawRect((double)((float)(var53 + 40) + var48 * var51), (double)var57 + 16.5D, (double)((float)(var53 + 40) + var48 * var51) + a * (double)var51, (double)var57 + 27.3D, (new Color(var16)).darker().getRGB());
         String var17 = String.format("%.1f", new Object[]{Float.valueOf(var48)}) + "%";
         var3.fontRendererCrack.drawString(var17, (float)(var53 + 40) + 50.0F * var51 - (float)(var3.fontRendererCrack.d(var17) / 2), (float)var57 + 18.0F, -1, true);
         var3.fontRendererCrack.drawString(var1.getName(), (float)(var53 + 40), (float)(var57 + 4), -1, true);
      }

      if(var0.B().equals("Pretty")) {
         double var29 = (double)Math.max(Fonts$SFBOLD$SFBOLD_22.SFBOLD_22.stringWidth(var1.getName()), aLM.a.stringWidth("Health: " + String.format("%.1f", new Object[]{Float.valueOf(var1.getHealth())}).replace(",", ".")));
         double var38 = (double)((Integer)var0.getThx().get()).intValue();
         double var47 = (double)((Integer)var0.getThy().get()).intValue();
         Gui.drawRect(var38, var47, var38 + var29 + 68.0D, var47 + 52.0D, (new Color(0, 0, 0, 100)).getRGB());
         GL11.glPushMatrix();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         boolean var52 = true;
         boolean var54 = true;
         GL11.glScaled(4.0D, 4.0D, 4.0D);
         var3.getTextureManager().bindTexture(((asJ)var1).getLocationSkin());
         aSQ.a((float)((var38 + 3.0D) / 4.0D), (float)((var47 + 3.0D) / 4.0D), 8.0F, 8.0F, 8, 8, 8, 8, 64.0F, 64.0F);
         GL11.glPopMatrix();
         Fonts$SFBOLD$SFBOLD_22.SFBOLD_22.drawString(var1.getName(), (double)((float)var38) + 37.5D, (double)((float)(var47 + 5.0D)), -1, true);
         aLM.a.drawString("Health: " + String.format("%.1f", new Object[]{Float.valueOf(var1.getHealth())}).replace(",", "."), (double)((float)var38 + 38.0F), (double)((float)(var47 + 17.0D)), -1, true);
         aLM.a.drawString("Distance: " + String.format("%.1f", new Object[]{Float.valueOf(var3.player.getDistanceToEntity(var1))}).replace(",", ".") + "m", (double)((float)var38 + 38.0F), (double)((float)(var47 + 28.0D)), -1, true);
         Gui.drawRect(var38 + 3.0D, var47 + 40.0D, var38 + 3.0D + var29 + 62.0D, var47 + 43.0D, (new Color(0, 0, 0, 120)).getRGB());
         float var58 = var1.getMaxHealth() + var1.getAbsorptionAmount() - 0.05F;
         float var62 = var1.getHealth() + var1.getAbsorptionAmount();
         float var65 = (float)(var29 + 62.0D);
         float var68 = (float)((double)Math.round((double)var62 * 100.0D) / 100.0D);
         float var71 = 100.0F / var58;
         float var18 = 0.0F;
         float var19 = var65 / 100.0F;
         if(e > (double)Math.min(100.0F, var68 * var71)) {
            e += ((double)Math.min(100.0F, var68 * var71) - e) * 0.2D;
         }

         e = (double)Math.min(100.0F, var68 * var71);
         Gui.drawRect(var38 + 4.0D, var47 + 40.9D, var38 + 4.0D + MathHelper.clamp_double(e * (double)var19 - 2.0D, 0.0D, (double)(100.0F * var19 - 2.0F)), var47 + 42.0D, (new Color(86, 193, 60)).getRGB());
         double var20 = 0.0D;
         int var22 = 0;
         if(var22 < 3) {
            var20 += (double)ItemArmor$ArmorMaterial.DIAMOND.getDamageReductionAmount(var22);
            ++var22;
         }

         double var82 = var20 / 100.0D;
         double var24 = 0.0D;
         int var26 = 0;
         if(var26 < 3) {
            if(((asJ)var1).inventory.armorInventory[var26] != null) {
               var24 += (double)((ItemArmor)((asJ)var1).inventory.armorInventory[var26].getItem()).damageReduceAmount;
            }

            ++var26;
         }

         double var87 = var24 / var82;
         Gui.drawRect(var38 + 3.0D, var47 + 45.0D, var38 + 3.0D + var29 + 62.0D, var47 + 48.0D, (new Color(0, 0, 0, 120)).getRGB());
         Gui.drawRect(var38 + 4.0D, var47 + 45.9D, var38 + 4.0D + MathHelper.clamp_double(var87 * (double)var19 - 2.0D, 0.0D, (double)(100.0F * var19 - 2.0F)), var47 + 47.0D, (new Color(0, 122, 224)).getRGB());
      }

      if(var0.B().equals("Less Pretty")) {
         ObjectArrayList var30 = new ObjectArrayList();
         int var33 = 3;
         ItemStack var39 = ((EntityPlayer)var1).inventory.armorInventory[var33];
         if(var39 != null) {
            var30.add(var39);
         }

         --var33;
         if(((EntityPlayer)var1).getCurrentEquippedItem() != null) {
            var30.add(((EntityPlayer)var1).getCurrentEquippedItem());
         }

         double var35 = (double)(Math.max(var30.size() * 10, var3.fontRendererCrack.d(var1.getName())) + 20);
         double var43 = (double)((Integer)var0.getThx().get()).intValue();
         double var49 = (double)((Integer)var0.getThy().get()).intValue();
         Gui.drawRect(var43, var49, var43 + var35 + 80.0D, var49 + 59.0D, -16777216);
         Gui.drawRect(var43 + 1.0D, var49 + 1.0D, var43 + var35 + 79.0D, var49 + 58.0D, (new Color(60, 60, 60)).getRGB());
         Gui.drawRect(var43 + 2.0D, var49 + 2.0D, var43 + var35 + 78.0D, var49 + 57.0D, (new Color(40, 40, 40)).getRGB());
         Gui.drawRect(var43 + 4.0D, var49 + 4.0D, var43 + var35 + 76.0D, var49 + 55.0D, (new Color(60, 60, 60)).getRGB());
         Gui.drawRect(var43 + 5.0D, var49 + 5.0D, var43 + var35 + 75.0D, var49 + 54.0D, (new Color(22, 22, 22)).getRGB());
         int var55 = (new Color(110, 255, 255, 255)).getRGB();
         int var59 = var55 >> 16 & 255;
         int var63 = var55 >> 8 & 255;
         int var66 = var55 & 255;
         float[] var69 = Color.RGBtoHSB(var59, var63, var66, new float[3]);
         int var72 = (int)(var35 + 78.0D);
         int var75 = 0;
         if(var75 < var72) {
            Gui.drawRect(var43 + 1.0D + (double)var75, var49 + 1.0D, var43 + 2.0D + (double)var75, var49 + 2.0D, Color.getHSBColor((float)var75 / (float)var72, var69[1], var69[2]).getRGB());
            ++var75;
         }

         a((int)(var43 + 25.0D), (int)var49 + (var1.isSneaking()?47:49), 20.0F, var1);
         var3.fontRendererCrack.drawString(var1.getName(), (float)(var43 + 45.0D), (float)var49 + 10.0F, -1, true);
         Gui.drawRect(var43 + 45.0D, var49 + 22.0D, var43 + var35 + 65.0D, var49 + 27.0D, -16777216);
         float var73 = var1.getMaxHealth() + var1.getAbsorptionAmount() - 0.05F;
         float var77 = var1.getHealth() + var1.getAbsorptionAmount();
         float var79 = (float)(var35 + 20.0D);
         float var80 = (float)((double)Math.round((double)var77 * 100.0D) / 100.0D);
         float var21 = 100.0F / var73;
         float var83 = Math.min(100.0F, var80 * var21);
         float var23 = var79 / 100.0F;
         Gui.drawRect(var43 + 45.0D, var49 + 22.0D, var43 + 45.0D + (double)(var83 * var23), var49 + 27.0D, getHealthColor(var1));
         int var84 = 1;
         if(var84 < 10) {
            double var25 = (double)(var79 / 10.0F);
            Gui.drawRect(var43 + 45.0D + var25 * (double)var84, var49 + 22.0D, var43 + 45.0D + var25 * (double)var84 + 0.5D, var49 + 27.0D, (new Color(22, 22, 22)).getRGB());
            ++var84;
         }

         drawArmorHUD((EntityPlayer)var1, (int)var43 + 12, (int)(var49 + 15.0D));
      }

      if(var0.B().equals("Trash")) {
         HUD var31 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
         double var36 = (double)(var3.fontRendererObj.d(var1.getName()) + 100);
         double var44 = (double)((Integer)var0.getThx().get()).intValue();
         double var50 = (double)((Integer)var0.getThy().get()).intValue();
         Gui.drawRect(var44, var50, var44 + var36, var50 + 60.0D, (new Color(0, 0, 0, 100)).getRGB());
         a((int)(var44 + 17.0D), (int)var50 + (var1.isSneaking()?52:54), 25.0F, var1);
         var3.fontRendererObj.drawString(var1.getName(), (float)(var44 + 35.0D), (float)(var50 + 5.0D), -1, true);
         GL11.glPushMatrix();
         GL11.glScaled(2.0D, 2.0D, 2.0D);
         var3.fontRendererObj.drawString(String.format("%.1f", new Object[]{Float.valueOf(var1.getHealth())}) + " ❤", (float)(var44 + 35.0D) / 2.0F, (float)(var50 + 20.0D) / 2.0F, var31.getColor().getRGB(), true);
         GL11.glPopMatrix();
         Gui.drawRect(var44 + 35.0D, var50 + 45.0D, var44 + var36 - 5.0D, var50 + 54.0D, var31.getColor().darker().darker().darker().getRGB());
         float var56 = var1.getMaxHealth() + var1.getAbsorptionAmount() - 0.05F;
         float var60 = var1.getHealth() + var1.getAbsorptionAmount();
         float var64 = (float)(var36 - 40.0D);
         float var67 = (float)((double)Math.round((double)var60 * 100.0D) / 100.0D);
         float var70 = 100.0F / var56;
         float var74 = Math.min(100.0F, var67 * var70);
         float var78 = var64 / 100.0F;
         Gui.drawRect(var44 + 35.0D, var50 + 45.0D, var44 + 35.0D + (double)(var74 * var78), var50 + 54.0D, var31.getColor().getRGB());
      }

      GL11.glPopMatrix();
   }

   public static void a(InfiniteAura var0, EntityLivingBase var1) {
      Timer.e();
      GL11.glPushMatrix();
      ScaleUtils.scale(Minecraft.getInstance());
      Minecraft var3 = Minecraft.getInstance();
      ScaledResolution var4 = new ScaledResolution(var3);
      if(((Integer)var0.C().get()).intValue() > var4.getScaledWidthStatic(var3) - 50) {
         var0.C().set(Integer.valueOf(var4.getScaledWidthStatic(var3) - 50));
      }

      if(((Integer)var0.G().get()).intValue() > var4.getScaledHeightStatic(var3) - 50) {
         var0.G().set(Integer.valueOf(var4.getScaledHeightStatic(var3) - 50));
      }

      if(var0.a().equals("Prettier")) {
         float var5 = var1.getHealth() + var1.getAbsorptionAmount();
         float var6 = var1.getMaxHealth() + var1.getAbsorptionAmount() - 0.05F;
         int var7 = 0;
         int var8 = 0;
         if(var8 < ((EntityPlayer)var1).inventory.armorInventory.length) {
            ItemStack var9 = ((EntityPlayer)var1).inventory.armorInventory[var8];
            if(var9 != null) {
               ++var7;
            }

            ++var8;
         }

         if(((EntityPlayer)var1).getCurrentEquippedItem() != null) {
            ++var7;
         }

         float var11;
         float var12;
         int var13;
         int var14;
         label236: {
            var41 = (float)Math.max(var3.fontRendererObj.d(var1.getName()) + 45, 16 * var7 + 45);
            float var45 = (float)((double)Math.round((double)var5 * 100.0D) / 100.0D);
            float var10 = 100.0F / var6;
            var11 = var45 * var10;
            var12 = var41 / 100.0F;
            var13 = ((Integer)var0.C().get()).intValue();
            var14 = ((Integer)var0.G().get()).intValue();
            if(e > (double)var11) {
               e = (double)var11;
               if(var11 >= 95.0F) {
                  break label236;
               }

               b = (double)(var12 * 3.0F);
            }

            if(e < (double)var11) {
               e = (double)var11;
            }

            if(b > 0.0D) {
               b -= (double)(var12 / 10.0F);
            }
         }

         a(var13 + 16, var14 + 35, 16.0F, var1);
         Gui.drawRect((double)var13, (double)var14, (double)((float)var13 + var41), (double)(var14 + 42), (new Color(0, 0, 0, 120)).getRGB());
         Gui.drawRect((double)var13, (double)(var14 + 40), (double)((float)var13 + var11 * var12) + b, (double)(var14 + 42), getHealthColor(var1));
         var3.fontRendererObj.drawStringWithShadow(var1.getName(), (float)(var13 + 35), (float)(var14 + 7), -1);
         drawArmorHUD((EntityPlayer)var1, var13, var14);
      }

      if(var0.a().equals("Prettiest")) {
         float var28 = var1.getHealth() + var1.getAbsorptionAmount();
         float var32 = var1.getMaxHealth() + var1.getAbsorptionAmount() - 0.05F;
         float var37 = (float)(35 + var3.fontRendererCrack.d(var1.getName()) + 40);
         float var42 = (float)((double)Math.round((double)var28 * 100.0D) / 100.0D);
         if(var42 > var32) {
            var42 *= var32 / var42;
         }

         float var46 = 100.0F / var32;
         float var48 = var42 * var46;
         float var51 = (var37 - 50.0F) / 100.0F;
         int var53 = ((Integer)var0.C().get()).intValue();
         int var57 = ((Integer)var0.G().get()).intValue();
         if((double)var48 < lastP) {
            a = lastP - (double)var48;
         }

         lastP = (double)var48;
         if(a > 0.0D) {
            a += (0.0D - a) * 0.05000000074505806D;
         }

         a = MathHelper.clamp_double(a, 0.0D, (double)(100.0F - var48));
         var3.getTextureManager().bindTexture(((asJ)var1).getLocationSkin());
         drawBorderedRect((double)var53, (double)((float)var57 - 1.5F), (double)((float)var53 + var37 - 6.0F), (double)var57 + 37.5D, 1.0F, (new Color(0, 0, 0, 50)).getRGB(), (new Color(29, 29, 29, 255)).getRGB());
         Gui.drawRect((double)(var53 + 1), (double)var57, (double)((float)var53 + var37 - 7.0F), (double)(var57 + 36), (new Color(40, 40, 40, 255)).getRGB());
         GL11.glPushMatrix();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         boolean var61 = true;
         boolean var15 = true;
         GL11.glScaled(4.4D, 4.4D, 4.4D);
         aSQ.a((float)(((double)var53 + 1.5D) / 4.4D), (float)(((double)var57 + 0.2D) / 4.4D), 8.0F, 8.0F, 8, 8, 8, 8, 64.0F, 64.0F);
         GL11.glPopMatrix();
         int var16 = ((HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class)).getHUDColor();
         Gui.drawRect((double)(var53 + 40), (double)var57 + 16.5D, (double)((float)(var53 + 40) + 100.0F * var51), (double)var57 + 27.3D, (new Color(0, 0, 0, 50)).getRGB());
         Gui.drawRect((double)(var53 + 40), (double)var57 + 16.5D, (double)((float)(var53 + 40) + var48 * var51), (double)var57 + 27.3D, var16);
         Gui.drawRect((double)((float)(var53 + 40) + var48 * var51), (double)var57 + 16.5D, (double)((float)(var53 + 40) + var48 * var51) + a * (double)var51, (double)var57 + 27.3D, (new Color(var16)).darker().getRGB());
         String var17 = String.format("%.1f", new Object[]{Float.valueOf(var48)}) + "%";
         var3.fontRendererCrack.drawString(var17, (float)(var53 + 40) + 50.0F * var51 - (float)(var3.fontRendererCrack.d(var17) / 2), (float)var57 + 18.0F, -1, true);
         var3.fontRendererCrack.drawString(var1.getName(), (float)(var53 + 40), (float)(var57 + 4), -1, true);
      }

      if(var0.a().equals("Pretty")) {
         double var29 = (double)Math.max(Fonts$SFBOLD$SFBOLD_22.SFBOLD_22.stringWidth(var1.getName()), aLM.a.stringWidth("Health: " + String.format("%.1f", new Object[]{Float.valueOf(var1.getHealth())}).replace(",", ".")));
         double var38 = (double)((Integer)var0.C().get()).intValue();
         double var47 = (double)((Integer)var0.G().get()).intValue();
         Gui.drawRect(var38, var47, var38 + var29 + 68.0D, var47 + 52.0D, (new Color(0, 0, 0, 100)).getRGB());
         GL11.glPushMatrix();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         boolean var52 = true;
         boolean var54 = true;
         GL11.glScaled(4.0D, 4.0D, 4.0D);
         var3.getTextureManager().bindTexture(((asJ)var1).getLocationSkin());
         aSQ.a((float)((var38 + 3.0D) / 4.0D), (float)((var47 + 3.0D) / 4.0D), 8.0F, 8.0F, 8, 8, 8, 8, 64.0F, 64.0F);
         GL11.glPopMatrix();
         Fonts$SFBOLD$SFBOLD_22.SFBOLD_22.drawString(var1.getName(), (double)((float)var38) + 37.5D, (double)((float)(var47 + 5.0D)), -1, true);
         aLM.a.drawString("Health: " + String.format("%.1f", new Object[]{Float.valueOf(var1.getHealth())}).replace(",", "."), (double)((float)var38 + 38.0F), (double)((float)(var47 + 17.0D)), -1, true);
         aLM.a.drawString("Distance: " + String.format("%.1f", new Object[]{Float.valueOf(var3.player.getDistanceToEntity(var1))}).replace(",", ".") + "m", (double)((float)var38 + 38.0F), (double)((float)(var47 + 28.0D)), -1, true);
         Gui.drawRect(var38 + 3.0D, var47 + 40.0D, var38 + 3.0D + var29 + 62.0D, var47 + 43.0D, (new Color(0, 0, 0, 120)).getRGB());
         float var58 = var1.getMaxHealth() + var1.getAbsorptionAmount() - 0.05F;
         float var62 = var1.getHealth() + var1.getAbsorptionAmount();
         float var65 = (float)(var29 + 62.0D);
         float var68 = (float)((double)Math.round((double)var62 * 100.0D) / 100.0D);
         float var71 = 100.0F / var58;
         float var18 = 0.0F;
         float var19 = var65 / 100.0F;
         if(e > (double)Math.min(100.0F, var68 * var71)) {
            e += ((double)Math.min(100.0F, var68 * var71) - e) * 0.2D;
         }

         e = (double)Math.min(100.0F, var68 * var71);
         Gui.drawRect(var38 + 4.0D, var47 + 40.9D, var38 + 4.0D + MathHelper.clamp_double(e * (double)var19 - 2.0D, 0.0D, (double)(100.0F * var19 - 2.0F)), var47 + 42.0D, (new Color(86, 193, 60)).getRGB());
         double var20 = 0.0D;
         int var22 = 0;
         if(var22 < 3) {
            var20 += (double)ItemArmor$ArmorMaterial.DIAMOND.getDamageReductionAmount(var22);
            ++var22;
         }

         double var82 = var20 / 100.0D;
         double var24 = 0.0D;
         int var26 = 0;
         if(var26 < 3) {
            if(((asJ)var1).inventory.armorInventory[var26] != null) {
               var24 += (double)((ItemArmor)((asJ)var1).inventory.armorInventory[var26].getItem()).damageReduceAmount;
            }

            ++var26;
         }

         double var87 = var24 / var82;
         Gui.drawRect(var38 + 3.0D, var47 + 45.0D, var38 + 3.0D + var29 + 62.0D, var47 + 48.0D, (new Color(0, 0, 0, 120)).getRGB());
         Gui.drawRect(var38 + 4.0D, var47 + 45.9D, var38 + 4.0D + MathHelper.clamp_double(var87 * (double)var19 - 2.0D, 0.0D, (double)(100.0F * var19 - 2.0F)), var47 + 47.0D, (new Color(0, 122, 224)).getRGB());
      }

      if(var0.a().equals("Less Pretty")) {
         ObjectArrayList var30 = new ObjectArrayList();
         int var33 = 3;
         ItemStack var39 = ((EntityPlayer)var1).inventory.armorInventory[var33];
         if(var39 != null) {
            var30.add(var39);
         }

         --var33;
         if(((EntityPlayer)var1).getCurrentEquippedItem() != null) {
            var30.add(((EntityPlayer)var1).getCurrentEquippedItem());
         }

         double var35 = (double)(Math.max(var30.size() * 10, var3.fontRendererCrack.d(var1.getName())) + 20);
         double var43 = (double)((Integer)var0.C().get()).intValue();
         double var49 = (double)((Integer)var0.G().get()).intValue();
         Gui.drawRect(var43, var49, var43 + var35 + 80.0D, var49 + 59.0D, -16777216);
         Gui.drawRect(var43 + 1.0D, var49 + 1.0D, var43 + var35 + 79.0D, var49 + 58.0D, (new Color(60, 60, 60)).getRGB());
         Gui.drawRect(var43 + 2.0D, var49 + 2.0D, var43 + var35 + 78.0D, var49 + 57.0D, (new Color(40, 40, 40)).getRGB());
         Gui.drawRect(var43 + 4.0D, var49 + 4.0D, var43 + var35 + 76.0D, var49 + 55.0D, (new Color(60, 60, 60)).getRGB());
         Gui.drawRect(var43 + 5.0D, var49 + 5.0D, var43 + var35 + 75.0D, var49 + 54.0D, (new Color(22, 22, 22)).getRGB());
         int var55 = (new Color(110, 255, 255, 255)).getRGB();
         int var59 = var55 >> 16 & 255;
         int var63 = var55 >> 8 & 255;
         int var66 = var55 & 255;
         float[] var69 = Color.RGBtoHSB(var59, var63, var66, new float[3]);
         int var72 = (int)(var35 + 78.0D);
         int var75 = 0;
         if(var75 < var72) {
            Gui.drawRect(var43 + 1.0D + (double)var75, var49 + 1.0D, var43 + 2.0D + (double)var75, var49 + 2.0D, Color.getHSBColor((float)var75 / (float)var72, var69[1], var69[2]).getRGB());
            ++var75;
         }

         a((int)(var43 + 25.0D), (int)var49 + (var1.isSneaking()?47:49), 20.0F, var1);
         var3.fontRendererCrack.drawString(var1.getName(), (float)(var43 + 45.0D), (float)var49 + 10.0F, -1, true);
         Gui.drawRect(var43 + 45.0D, var49 + 22.0D, var43 + var35 + 65.0D, var49 + 27.0D, -16777216);
         float var73 = var1.getMaxHealth() + var1.getAbsorptionAmount() - 0.05F;
         float var77 = var1.getHealth() + var1.getAbsorptionAmount();
         float var79 = (float)(var35 + 20.0D);
         float var80 = (float)((double)Math.round((double)var77 * 100.0D) / 100.0D);
         float var21 = 100.0F / var73;
         float var83 = Math.min(100.0F, var80 * var21);
         float var23 = var79 / 100.0F;
         Gui.drawRect(var43 + 45.0D, var49 + 22.0D, var43 + 45.0D + (double)(var83 * var23), var49 + 27.0D, getHealthColor(var1));
         int var84 = 1;
         if(var84 < 10) {
            double var25 = (double)(var79 / 10.0F);
            Gui.drawRect(var43 + 45.0D + var25 * (double)var84, var49 + 22.0D, var43 + 45.0D + var25 * (double)var84 + 0.5D, var49 + 27.0D, (new Color(22, 22, 22)).getRGB());
            ++var84;
         }

         drawArmorHUD((EntityPlayer)var1, (int)var43 + 12, (int)(var49 + 15.0D));
      }

      if(var0.a().equals("Trash")) {
         HUD var31 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
         double var36 = (double)(var3.fontRendererObj.d(var1.getName()) + 100);
         double var44 = (double)((Integer)var0.C().get()).intValue();
         double var50 = (double)((Integer)var0.G().get()).intValue();
         Gui.drawRect(var44, var50, var44 + var36, var50 + 60.0D, (new Color(0, 0, 0, 100)).getRGB());
         a((int)(var44 + 17.0D), (int)var50 + (var1.isSneaking()?52:54), 25.0F, var1);
         var3.fontRendererObj.drawString(var1.getName(), (float)(var44 + 35.0D), (float)(var50 + 5.0D), -1, true);
         GL11.glPushMatrix();
         GL11.glScaled(2.0D, 2.0D, 2.0D);
         var3.fontRendererObj.drawString(String.format("%.1f", new Object[]{Float.valueOf(var1.getHealth())}) + " ❤", (float)(var44 + 35.0D) / 2.0F, (float)(var50 + 20.0D) / 2.0F, var31.getColor().getRGB(), true);
         GL11.glPopMatrix();
         Gui.drawRect(var44 + 35.0D, var50 + 45.0D, var44 + var36 - 5.0D, var50 + 54.0D, var31.getColor().darker().darker().darker().getRGB());
         float var56 = var1.getMaxHealth() + var1.getAbsorptionAmount() - 0.05F;
         float var60 = var1.getHealth() + var1.getAbsorptionAmount();
         float var64 = (float)(var36 - 40.0D);
         float var67 = (float)((double)Math.round((double)var60 * 100.0D) / 100.0D);
         float var70 = 100.0F / var56;
         float var74 = Math.min(100.0F, var67 * var70);
         float var78 = var64 / 100.0F;
         Gui.drawRect(var44 + 35.0D, var50 + 45.0D, var44 + 35.0D + (double)(var74 * var78), var50 + 54.0D, var31.getColor().getRGB());
      }

      GL11.glPopMatrix();
   }

   public static void drawArmorHUD(EntityPlayer var0, int var1, int var2) {
      GL11.glPushMatrix();
      Timer.e();
      Minecraft var4 = Minecraft.getInstance();
      ObjectArrayList var5 = new ObjectArrayList();
      if(var4.player.isEntityAlive() && var4.player.isInsideOfMaterial(Material.water)) {
         boolean var15 = true;
      } else {
         boolean var10000 = false;
      }

      int var7 = 3;
      ItemStack var8 = var0.inventory.armorInventory[var7];
      if(var8 != null) {
         var5.add(var8);
      }

      --var7;
      if(var0.getCurrentEquippedItem() != null) {
         var5.add(var0.getCurrentEquippedItem());
      }

      var7 = -3;
      boolean var14 = true;
      Iterator var9 = var5.iterator();
      if(var9.hasNext()) {
         ItemStack var10 = (ItemStack)var9.next();
         if(var4.world != null) {
            RenderHelper.enableGUIStandardItemLighting();
            var7 += 16;
         }

         GlStateManager.pushMatrix();
         GlStateManager.disableAlpha();
         GlStateManager.clear(256);
         GlStateManager.enableBlend();
         var4.getRenderItem().zLevel = -150.0F;
         var4.getRenderItem().renderItemAndEffectIntoGUI(var10, (float)(var7 + var1 + 18), (float)(var2 + 17));
         var4.getRenderItem().zLevel = 0.0F;
         GlStateManager.enableBlend();
         float var11 = 0.5F;
         GlStateManager.scale(0.5F, 0.5F, 0.5F);
         GlStateManager.disableDepth();
         GlStateManager.disableLighting();
         GlStateManager.enableDepth();
         GlStateManager.scale(2.0F, 2.0F, 2.0F);
         GlStateManager.enableAlpha();
         GlStateManager.popMatrix();
      }

      GL11.glPopMatrix();
   }

   public static void drawStack(FontRenderer var0, boolean var1, ItemStack var2, float var3, float var4) {
      Timer.e();
      GL11.glPushMatrix();
      Minecraft var6 = Minecraft.getInstance();
      if(var6.world != null) {
         RenderHelper.enableGUIStandardItemLighting();
      }

      GlStateManager.pushMatrix();
      GlStateManager.disableAlpha();
      GlStateManager.clear(256);
      var6.getRenderItem().zLevel = -150.0F;
      var6.getRenderItem().renderItemAndEffectIntoGUI(var2, var3, var4);
      var6.getRenderItem().renderItemOverlayIntoGUI(var0, var2, var3, var4, String.valueOf(var2.stackSize));
      var6.getRenderItem().zLevel = 0.0F;
      GlStateManager.enableBlend();
      float var7 = 0.5F;
      GlStateManager.scale(0.5F, 0.5F, 0.5F);
      GlStateManager.disableDepth();
      GlStateManager.disableLighting();
      GlStateManager.enableDepth();
      GlStateManager.scale(2.0F, 2.0F, 2.0F);
      GlStateManager.enableAlpha();
      GlStateManager.popMatrix();
      GL11.glPopMatrix();
   }

   public static void drawArrow(double var0, double var2, int var4, int var5, double var6) {
      start2D();
      GL11.glPushMatrix();
      GL11.glLineWidth((float)var4);
      setColor(new Color(var5));
      GL11.glBegin(3);
      GL11.glVertex2d(var0, var2);
      GL11.glVertex2d(var0 + 3.0D, var2 + var6);
      GL11.glVertex2d(var0 + 6.0D, var2);
      GL11.glEnd();
      GL11.glPopMatrix();
      stop2D();
   }

   public static void drawCheck(double var0, double var2, int var4, int var5) {
      start2D();
      GL11.glPushMatrix();
      GL11.glLineWidth((float)var4);
      setColor(new Color(var5));
      GL11.glBegin(3);
      GL11.glVertex2d(var0, var2);
      GL11.glVertex2d(var0 + 2.0D, var2 + 3.0D);
      GL11.glVertex2d(var0 + 6.0D, var2 - 2.0D);
      GL11.glEnd();
      GL11.glPopMatrix();
      stop2D();
   }

   public static void drawCheckbox(double var0, double var2, double var4, double var6, double var8, int var10) {
      start2D();
      GL11.glPushMatrix();
      GL11.glLineWidth((float)var8);
      setColor(new Color(var10));
      GL11.glBegin(3);
      GL11.glVertex2d(var0, var2);
      GL11.glVertex2d(var0, var2 + (var6 - var2));
      GL11.glVertex2d(var0 + (var4 - var0), var2 + (var6 - var2));
      GL11.glVertex2d(var0 + (var4 - var0), var2);
      GL11.glVertex2d(var0, var2);
      GL11.glEnd();
      GL11.glPopMatrix();
      stop2D();
   }

   public static void drawCornerBox(double var0, double var2, double var4, double var6, double var8, Color var10) {
      double var11 = Math.abs(var4 - var0);
      double var13 = Math.abs(var6 - var2);
      double var15 = var11 / 4.0D;
      double var17 = var13 / 4.0D;
      start2D();
      GL11.glPushMatrix();
      GL11.glLineWidth((float)var8);
      setColor(var10);
      GL11.glBegin(3);
      GL11.glVertex2d(var0 + var15, var2);
      GL11.glVertex2d(var0, var2);
      GL11.glVertex2d(var0, var2 + var17);
      GL11.glEnd();
      GL11.glBegin(3);
      GL11.glVertex2d(var0, var2 + var13 - var17);
      GL11.glVertex2d(var0, var2 + var13);
      GL11.glVertex2d(var0 + var15, var2 + var13);
      GL11.glEnd();
      GL11.glBegin(3);
      GL11.glVertex2d(var0 + var11 - var15, var2 + var13);
      GL11.glVertex2d(var0 + var11, var2 + var13);
      GL11.glVertex2d(var0 + var11, var2 + var13 - var17);
      GL11.glEnd();
      GL11.glBegin(3);
      GL11.glVertex2d(var0 + var11, var2 + var17);
      GL11.glVertex2d(var0 + var11, var2);
      GL11.glVertex2d(var0 + var11 - var15, var2);
      GL11.glEnd();
      GL11.glPopMatrix();
      stop2D();
   }

   public static void start2D() {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
   }

   public static void stop2D() {
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void setColor(Color var0) {
      float var1 = (float)(var0.getRGB() >> 24 & 255) / 255.0F;
      float var2 = (float)(var0.getRGB() >> 16 & 255) / 255.0F;
      float var3 = (float)(var0.getRGB() >> 8 & 255) / 255.0F;
      float var4 = (float)(var0.getRGB() & 255) / 255.0F;
      GL11.glColor4f(var2, var3, var4, var1);
   }

   public static void startDrawing() {
      GL11.glEnable(3042);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 0);
   }

   public static void stopDrawing() {
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
      GlStateManager.disableBlend();
   }

   public static void drawLine(Entity var0, double[] var1, double var2, double var4, double var6) {
      Timer.e();
      GL11.glEnable(2848);
      if(var1.length >= 4) {
         if(var1[3] <= 0.1D) {
            return;
         }

         GL11.glColor4d(var1[0], var1[1], var1[2], var1[3]);
      }

      GL11.glColor3d(var1[0], var1[1], var1[2]);
      GL11.glLineWidth(1.5F);
      GL11.glBegin(1);
      GL11.glVertex3d(0.0D, (double)mc.player.getEyeHeight(), 0.0D);
      GL11.glVertex3d(var2, var4, var6);
      GL11.glEnd();
      GL11.glDisable(2848);
   }

   public static void drawLine(BlockPos var0, int var1) {
      Minecraft var2 = Minecraft.getInstance();
      double var3 = (double)var0.getX() - var2.getRenderManager().renderPosX + 0.5D;
      double var5 = (double)var0.getY() - var2.getRenderManager().renderPosY + 0.5D;
      double var7 = (double)var0.getZ() - var2.getRenderManager().renderPosZ + 0.5D;
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glLineWidth(1.0F);
      float var10000 = (float)(var2.player.posX - (double)var0.getX());
      var10000 = (float)(var2.player.posY - (double)var0.getY());
      float var11 = (float)(var1 >> 16 & 255) / 255.0F;
      float var12 = (float)(var1 >> 8 & 255) / 255.0F;
      float var13 = (float)(var1 & 255) / 255.0F;
      float var14 = (float)(var1 >> 24 & 255) / 255.0F;
      GL11.glColor4f(var11, var12, var13, var14);
      GL11.glLoadIdentity();
      boolean var15 = var2.gameSettings.viewBobbing;
      var2.gameSettings.viewBobbing = false;
      var2.entityRenderer.orientCamera(var2.timer.renderPartialTicks);
      GL11.glBegin(3);
      GL11.glVertex3d(0.0D, (double)var2.player.getEyeHeight(), 0.0D);
      GL11.glVertex3d(var3, var5, var7);
      GL11.glVertex3d(var3, var5, var7);
      GL11.glEnd();
      var2.gameSettings.viewBobbing = var15;
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   private static UnsupportedOperationException a(UnsupportedOperationException var0) {
      return var0;
   }
}
