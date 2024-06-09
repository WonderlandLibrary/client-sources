package exhibition.module.impl.render;

import exhibition.Client;
import exhibition.event.Event;
import exhibition.event.EventListener;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventRender3D;
import exhibition.event.impl.EventRenderGui;
import exhibition.management.animate.Opacity;
import exhibition.management.waypoints.Waypoint;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.util.RenderingUtil;
import exhibition.util.RotationUtils;
import exhibition.util.render.Colors;
import exhibition.util.render.TTFFontRenderer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Waypoints extends Module {
   private double gradualFOVModifier;
   public static Map waypointMap = new HashMap();
   private Opacity opacity = new Opacity(0);
   private String ARROWS = "ARROWS";
   private String RADIUS = "RADIUS";
   boolean forward = true;

   public Waypoints(ModuleData data) {
      super(data);
      this.settings.put(this.ARROWS, new Setting(this.ARROWS, true, "Shows arrows for out of FOV waypoints."));
      this.settings.put(this.RADIUS, new Setting(this.RADIUS, Integer.valueOf(100), "Radius for arrows.", 10.0D, 30.0D, 500.0D));
   }

   public EventListener.Priority getPriority() {
      return EventListener.Priority.HIGH;
   }

   @RegisterEvent(
      events = {EventRenderGui.class, EventRender3D.class}
   )
   public void onEvent(Event event) {
      if (mc.getCurrentServerData() != null) {
         if (event instanceof EventRender3D) {
            this.updatePositions();
         } else {
            GlStateManager.pushMatrix();
            ScaledResolution scaledRes = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            float radiusHeight = (float)(scaledRes.getScaledHeight() / 4);
            float radiusWidth = (float)(scaledRes.getScaledWidth() / 4);
            float w = (float)(scaledRes.getScaledWidth() / 2);
            float h = (float)(scaledRes.getScaledHeight() / 2);
            Iterator var7 = waypointMap.keySet().iterator();

            while(true) {
               while(var7.hasNext()) {
                  Waypoint waypoint = (Waypoint)var7.next();
                  double[] renderPositions = (double[])waypointMap.get(waypoint);
                  if (Objects.equals(waypoint.getAddress(), mc.getCurrentServerData().serverIP) && this.isInView(renderPositions[0] / (double)scaledRes.getScaleFactor(), renderPositions[1] / (double)scaledRes.getScaleFactor(), scaledRes, waypoint)) {
                     GlStateManager.pushMatrix();
                     String str = "§l" + waypoint.getName() + " §a" + (int)mc.thePlayer.getDistance(waypoint.getVec3().xCoord, waypoint.getVec3().yCoord, waypoint.getVec3().zCoord) + "m";
                     TTFFontRenderer font = Client.nametagsFont;
                     GlStateManager.translate(renderPositions[0] / (double)scaledRes.getScaleFactor(), renderPositions[1] / (double)scaledRes.getScaleFactor(), 0.0D);
                     this.scale();
                     GlStateManager.translate(0.0D, -2.5D, 0.0D);
                     float strWidth = font.getWidth(str);
                     RenderingUtil.rectangleBordered((double)(-strWidth / 2.0F - 3.0F), -12.0D, (double)(strWidth / 2.0F + 3.0F), 1.0D, 0.5D, Colors.getColor(0, 100), waypoint.getColor());
                     GlStateManager.color(1.0F, 1.0F, 1.0F);
                     font.drawStringWithShadow(str, -strWidth / 2.0F, -9.5F, -1);
                     GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                     RenderingUtil.drawCircle(3.0F, 0.0F, 4.0F, 3, waypoint.getColor());
                     RenderingUtil.drawCircle(3.0F, 0.0F, 3.0F, 3, waypoint.getColor());
                     RenderingUtil.drawCircle(3.0F, 0.0F, 2.0F, 3, waypoint.getColor());
                     RenderingUtil.drawCircle(3.0F, 0.0F, 1.0F, 3, waypoint.getColor());
                     GlStateManager.popMatrix();
                  } else if (((Boolean)((Setting)this.settings.get(this.ARROWS)).getValue()).booleanValue() && Objects.equals(waypoint.getAddress(), mc.getCurrentServerData().serverIP)) {
                     float angle = this.findAngle((double)w, renderPositions[0] / (double)scaledRes.getScaleFactor(), (double)h, renderPositions[1] / (double)scaledRes.getScaleFactor()) + (float)(renderPositions[2] > 1.0D ? 180 : 0);
                     int radiusXD = ((Number)((Setting)this.settings.get(this.RADIUS)).getValue()).intValue();
                     double x = (double)radiusXD * Math.cos(Math.toRadians((double)angle));
                     double y = (double)radiusXD * Math.sin(Math.toRadians((double)angle));
                     GlStateManager.pushMatrix();
                     GlStateManager.translate(x + (double)(scaledRes.getScaledWidth() / 2), y + (double)(scaledRes.getScaledHeight() / 2), 0.0D);
                     GlStateManager.rotate(angle, 0.0F, 0.0F, 1.0F);
                     GlStateManager.scale(1.5F, 1.0F, 1.0F);
                     if (this.forward && this.opacity.getOpacity() >= 300.0F) {
                        this.forward = false;
                     } else if (!this.forward && this.opacity.getOpacity() <= 25.0F) {
                        this.forward = true;
                     }

                     this.opacity.interp(this.forward ? 300.0F : 25.0F, 3);
                     int alpha = (int)this.opacity.getOpacity();
                     if (alpha > 255) {
                        alpha = 255;
                     } else if (alpha < 0) {
                        alpha = 0;
                     }

                     int f1 = waypoint.getColor() >> 16 & 255;
                     int f2 = waypoint.getColor() >> 8 & 255;
                     int f3 = waypoint.getColor() & 255;
                     int color = Colors.getColor(f1, f2, f3, alpha);
                     RenderingUtil.drawCircle(0.0F, 0.0F, 6.0F, 3, Colors.getColor(0, alpha));
                     RenderingUtil.drawCircle(0.0F, 0.0F, 5.0F, 3, color);
                     RenderingUtil.drawCircle(0.0F, 0.0F, 4.0F, 3, color);
                     RenderingUtil.drawCircle(0.0F, 0.0F, 3.0F, 3, color);
                     RenderingUtil.drawCircle(0.0F, 0.0F, 2.0F, 3, color);
                     RenderingUtil.drawCircle(0.0F, 0.0F, 1.0F, 3, color);
                     RenderingUtil.drawCircle(0.0F, 0.0F, 0.0F, 3, color);
                     GlStateManager.popMatrix();
                  }
               }

               GlStateManager.popMatrix();
               break;
            }
         }

      }
   }

   public double[] isInFOV(double x, double y) {
      return null;
   }

   private void scale() {
      float scale = 1.0F;
      float target = scale * (mc.gameSettings.fovSetting / (mc.gameSettings.fovSetting * mc.thePlayer.func_175156_o()));
      if (this.gradualFOVModifier == 0.0D || Double.isNaN(this.gradualFOVModifier)) {
         this.gradualFOVModifier = (double)target;
      }

      this.gradualFOVModifier += ((double)target - this.gradualFOVModifier) / ((double)Minecraft.debugFPS * 0.7D);
      scale = (float)((double)scale * this.gradualFOVModifier);
      scale *= (float)(mc.currentScreen == null && GameSettings.isKeyDown(mc.gameSettings.ofKeyBindZoom) ? 3 : 1);
      GlStateManager.scale(scale, scale, scale);
   }

   private void updatePositions() {
      waypointMap.clear();
      Iterator var1 = Client.waypointManager.getWaypoints().iterator();

      while(var1.hasNext()) {
         Waypoint waypoint = (Waypoint)var1.next();
         double x = waypoint.getVec3().xCoord - mc.getRenderManager().viewerPosX;
         double y = waypoint.getVec3().yCoord - mc.getRenderManager().viewerPosY;
         double z = waypoint.getVec3().zCoord - mc.getRenderManager().viewerPosZ;
         y += 0.2D;
         waypointMap.put(waypoint, this.convertTo2D(x, y, z));
      }

   }

   private double[] convertTo2D(double x, double y, double z, Waypoint waypoint) {
      double[] convertedPoints = this.convertTo2D(x, y, z);
      return convertedPoints;
   }

   private double[] convertTo2D(double x, double y, double z) {
      FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
      IntBuffer viewport = BufferUtils.createIntBuffer(16);
      FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
      FloatBuffer projection = BufferUtils.createFloatBuffer(16);
      GL11.glGetFloat(2982, modelView);
      GL11.glGetFloat(2983, projection);
      GL11.glGetInteger(2978, viewport);
      boolean result = GLU.gluProject((float)x, (float)y, (float)z, modelView, projection, viewport, screenCoords);
      return result ? new double[]{(double)screenCoords.get(0), (double)((float)Display.getHeight() - screenCoords.get(1)), (double)screenCoords.get(2)} : null;
   }

   private float findAngle(double x, double x2, double y, double y2) {
      ScaledResolution scaledRes = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
      float a = (float)(scaledRes.getScaledHeight() - 25);
      float b = (float)(scaledRes.getScaledWidth() - 25);
      return (float)Math.toDegrees(Math.atan2(y2 - y, x2 - x));
   }

   private boolean isInView(double x, double y, ScaledResolution resolution, Waypoint waypoint) {
      float angle = RotationUtils.getRotationFromPosition(waypoint.getVec3().xCoord, waypoint.getVec3().zCoord, waypoint.getVec3().yCoord - 1.6D)[0];
      float angle2 = RotationUtils.getRotationFromPosition(waypoint.getVec3().xCoord, waypoint.getVec3().zCoord, waypoint.getVec3().yCoord - 1.6D)[1];
      float cameraYaw = mc.getRenderViewEntity().rotationYaw + (float)(mc.gameSettings.thirdPersonView == 2 ? 180 : 0);
      boolean view = RotationUtils.getDistanceBetweenAngles(angle, RotationUtils.getNewAngle(cameraYaw)) < 90.0F && RotationUtils.getDistanceBetweenAngles(angle2, mc.thePlayer.rotationPitch) < 90.0F;
      return view;
   }
}
