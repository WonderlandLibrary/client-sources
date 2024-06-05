package cc.novoline.modules.visual;

import cc.novoline.Novoline;
import cc.novoline.modules.visual.HUD;
import cc.novoline.modules.visual.Waypoints;
import cc.novoline.utils.RotationUtil;
import cc.novoline.utils.ScaleUtils;
import cc.novoline.utils.java.Checks;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Objects;
import net.Ls;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public final class Waypoints$Waypoint {
   private transient double[] positions = new double[]{0.0D, 0.0D, 0.0D};
   private final String name;
   private final int x;
   private final int y;
   private final int z;

   public Waypoints$Waypoint(String var1, int var2, int var3, int var4) {
      this.name = var1;
      this.x = var2;
      this.y = var3;
      this.z = var4;
   }

   public static Waypoints$Waypoint of(String var0, int var1, int var2, int var3) {
      Checks.notBlank(var0, "name");
      return new Waypoints$Waypoint(var0, var1, var2, var3);
   }

   public void render() {
      HUD.h();
      ScaledResolution var2 = new ScaledResolution(Minecraft.getInstance());
      if(this.isInView()) {
         GL11.glPushMatrix();
         GL11.glTranslated(this.positions[0] / (double)var2.getScaleFactor(), this.positions[1] / (double)var2.getScaleFactor(), this.positions[2] / (double)var2.getScaleFactor());
         ScaleUtils.scale(Minecraft.getInstance());
         float var3 = 1.0F;
         switch(Minecraft.getInstance().gameSettings.guiScale) {
         case 0:
            var3 = 0.5F;
         case 1:
            var3 = 2.0F;
         case 3:
            var3 = 0.6666667F;
         case 2:
         default:
            float var4 = (float)var2.getScaledWidth() / 2.0F;
            float var5 = (float)var2.getScaledHeight() / 2.0F;
            float var6 = (float)Waypoints.access$000().stringWidth(this.name + " " + this.getDistance() + "m") / 2.0F;
            float var7 = 11.25F;
            float var8 = var4 / var3 + var6;
            float var9 = var4 / var3 - var6;
            float var10 = var5 / var3 - var7;
            float var11 = var5 / var3 + var7;
            GlStateManager.translate(0.0D, -2.5D, 0.0D);
            GlStateManager.disableDepth();
            Gui.drawRect(-(Waypoints.access$000().stringWidth(this.name + " " + this.getDistance() + "m") / 2) - 2, -5, Waypoints.access$000().stringWidth(this.name + " " + this.getDistance() + "m") / 2 + 2, 10, (new Color(0, 0, 0, 100)).getRGB());
            Gui.drawRect(-(Waypoints.access$000().stringWidth(this.name + " " + this.getDistance() + "m") / 2) - 2, -6, Waypoints.access$000().stringWidth(this.name + " " + this.getDistance() + "m") / 2 + 2, -5, ((Color)Objects.requireNonNull(((HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class)).getColor())).getRGB());
            Waypoints.access$000().drawString(this.name + " " + EnumChatFormatting.GRAY + this.getDistance() + "m", -((float)Waypoints.access$000().stringWidth(this.name + " " + this.getDistance() + "m") / 2.0F), 0.0F, -1);
            GlStateManager.enableDepth();
            GL11.glPopMatrix();
         }
      }

   }

   public int getDistance() {
      int var1 = (int)Math.abs(Minecraft.getInstance().player.posX - (double)this.getX());
      int var2 = (int)Math.abs(Minecraft.getInstance().player.posY - (double)this.getY());
      int var3 = (int)Math.abs(Minecraft.getInstance().player.posZ - (double)this.getZ());
      return (int)Math.sqrt((double)(var1 * var1 + var2 * var2 + var3 * var3));
   }

   public boolean isInView() {
      HUD.e();
      Minecraft var2 = Minecraft.getInstance();
      float var3 = RotationUtil.a((double)this.getX(), (double)this.getZ(), (double)this.getY())[0];
      float var4 = RotationUtil.a((double)this.getX(), (double)this.getZ(), (double)this.getY())[1];
      float var5 = (var2.gameSettings.thirdPersonView == 0?var2.getRenderViewEntity().rotationYaw:var2.getRenderViewEntity().cameraRotationYaw) + (float)(var2.gameSettings.thirdPersonView == 2?180:0);
      return RotationUtil.getDistanceBetweenAngles(var3, RotationUtil.c(var5)) < 90.0F && RotationUtil.getDistanceBetweenAngles(var4, var2.gameSettings.thirdPersonView == 0?var2.player.rotationPitch:var2.player.cameraRotationPitch) < 90.0F;
   }

   public double[] convertTo2D(double var1, double var3, double var5) {
      FloatBuffer var8 = BufferUtils.createFloatBuffer(3);
      FloatBuffer var9 = BufferUtils.createFloatBuffer(16);
      FloatBuffer var10 = BufferUtils.createFloatBuffer(16);
      HUD.e();
      IntBuffer var11 = BufferUtils.createIntBuffer(16);
      GL11.glGetFloat(2982, var9);
      GL11.glGetFloat(2983, var10);
      GL11.glGetInteger(2978, var11);
      boolean var12 = Ls.a((float)var1, (float)var3, (float)var5, var9, var10, var11, var8);
      return var12?new double[]{(double)var8.get(0), (double)((float)Display.getHeight() - var8.get(1)), (double)var8.get(2)}:null;
   }

   public void setPositions(double[] var1) {
      this.positions = var1;
   }

   public String getName() {
      return this.name;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getZ() {
      return this.z;
   }
}
