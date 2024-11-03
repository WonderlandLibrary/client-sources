package net.augustus.modules.render;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;

import net.augustus.Augustus;
import net.augustus.events.EventRender2D;
import net.augustus.events.EventShaderRender;
import net.augustus.font.UnicodeFontRenderer;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.ColorSetting;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.ui.GuiIngameHook;
import net.augustus.utils.skid.idek.Esp2DUtil;
import net.augustus.utils.skid.lorious.ColorUtils;
import net.augustus.utils.skid.vestige.ColorUtil;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

public class NameTags extends Module {
   public StringValue mode = new StringValue(1, "Mode", this, "Custom", new String[]{"None", "Vanilla", "Custom", "Interpolation"});
   public StringValue colorMode = new StringValue(235246, "ColorMode", this, "OtherRainbow", new String[] {"Rainbow", "OtherRainbow", "Custom", "Team"});
   public ColorSetting customColor = new ColorSetting(21553, "CustomColor", this, new Color(255, 255, 255));
   public BooleanValue bg = new BooleanValue(2153, "Background", this, false);
   public DoubleValue scale = new DoubleValue(2, "Scale", this, 1.2, 0.0, 3.0, 2);
   public DoubleValue height = new DoubleValue(4, "Height", this, 0.0, -3.0, 3.0, 2);
   public BooleanValue armor = new BooleanValue(3, "Armor", this, true);

   private final IntBuffer viewport;
   private final FloatBuffer modelview;
   private final FloatBuffer projection;
   private final FloatBuffer vector;

   private static UnicodeFontRenderer arial18;

   static {
      try {
         arial18 = new UnicodeFontRenderer(Font.createFont(0, NameTags.class.getResourceAsStream("/ressources/arial.ttf")).deriveFont(18F));
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   public NameTags() {
      super("NameTags", new Color(21, 35, 81), Categorys.RENDER);
      this.viewport = GLAllocation.createDirectIntBuffer(16);
      this.modelview = GLAllocation.createDirectFloatBuffer(16);
      this.projection = GLAllocation.createDirectFloatBuffer(16);
      this.vector = GLAllocation.createDirectFloatBuffer(4);
   }

   @EventTarget
   public void uwe(EventRender2D eventShaderRender) {
      switch (mode.getSelected()) {
         case "Interpolation": {
            name2d();
            break;
         }
      }
   }

   private void name2d() {
      GL11.glPushMatrix();
      float partialTicks = mc.timer.renderPartialTicks;
      ScaledResolution scaledResolution = new ScaledResolution(mc);
      int scaleFactor = scaledResolution.getScaleFactor();
      double scaling = (double)scaleFactor / Math.pow(scaleFactor, 2.0D);
      GL11.glScaled(scaling, scaling, scaling);
      EntityRenderer entityRenderer = mc.entityRenderer;

      for(Entity entity : mc.theWorld.loadedEntityList) {
         if(entity instanceof EntityPlayer) {
            if (entity == mc.thePlayer) continue;
            if (Esp2DUtil.isInViewFrustrum(entity)) {
               double x = Esp2DUtil.interpolate(entity.posX, entity.lastTickPosX, partialTicks);
               double y = Esp2DUtil.interpolate(entity.posY, entity.lastTickPosY, partialTicks);
               double z = Esp2DUtil.interpolate(entity.posZ, entity.lastTickPosZ, partialTicks);
               double width = (double) entity.width / 1.5D;
               double height = (double) entity.height + (entity.isSneaking() ? -0.3D : 0.2D);
               AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
               List<Vector3d> vectors = Arrays.asList(new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ));
               entityRenderer.setupCameraTransform(partialTicks, 0);
               Vector4d position = null;

               for (Vector3d o : vectors) {
                  Vector3d vector = o;
                  vector = this.project2D(scaleFactor, vector.x - RenderManager.viewerPosX, vector.y - RenderManager.viewerPosY + height, vector.z - RenderManager.viewerPosZ);
                  if (vector != null && vector.z >= 0.0D && vector.z < 1.0D) {
                     if (position == null) {
                        position = new Vector4d(vector.x, vector.y, vector.z, 0.0D);
                     }

                     position.x = Math.min(vector.x, position.x);
                     position.y = Math.min(vector.y, position.y);
                     position.z = Math.max(vector.x, position.z);
                     position.w = Math.max(vector.y, position.w);
                  }
               }

               if (position != null) {
                  entityRenderer.setupOverlayRendering();
                  double posX = position.x;
                  double posY = position.y;
                  double endPosX = position.z;
                  double endPosY = position.w;
                  switch (colorMode.getSelected()) {
                     case "OtherRainbow": {
                        Augustus.getInstance().getLoriousFontService().getArial18().drawCenteredString(entity.getName(), (float) ((posX + endPosX) / 2), (float) ((posY + endPosY) / 2), -1, true, ColorUtils.getRainbow(4.0F, 0.5F, 1.0F), new Color(255, 255, 255));
                        break;
                     }
                     case "Custom": {
                        arial18.drawCenteredString(entity.getName(), (float) ((posX + endPosX) / 2), (float) ((posY + endPosY) / 2), customColor.getColor().getRGB());
                        break;
                     }
                     case "Team": {
                        int i = -1;
                        ScorePlayerTeam scoreplayerteam = (ScorePlayerTeam) ((EntityPlayer) entity).getTeam();
                        if (scoreplayerteam != null) {
                           String s = FontRenderer.getFormatFromString(scoreplayerteam.getColorPrefix());
                           if (s.length() >= 2) {
                              i = mc.fontRendererObj.getColorCode(s.charAt(1));
                           }
                        }
                        arial18.drawCenteredString(entity.getName(), (float) ((posX + endPosX) / 2), (float) ((posY + endPosY) / 2), i);
                        break;
                     }
                     case "Rainbow": {
                        arial18.drawCenteredString(entity.getName(), (float) ((posX + endPosX) / 2), (float) ((posY + endPosY) / 2), ColorUtils.getRainbow(1, 1, 1).getRGB());
                        break;
                     }
                  }
               }
            }
         }
      }
      GL11.glPopMatrix();
      GlStateManager.enableBlend();
      GlStateManager.resetColor();
      entityRenderer.setupOverlayRendering();
   }

   private Vector3d project2D(int scaleFactor, double x, double y, double z) {
      GL11.glGetFloat(2982, this.modelview);
      GL11.glGetFloat(2983, this.projection);
      GL11.glGetInteger(2978, this.viewport);
      return GLU.gluProject((float)x, (float)y, (float)z, this.modelview, this.projection, this.viewport, this.vector) ? new Vector3d(this.vector.get(0) / (float)scaleFactor, ((float) Display.getHeight() - this.vector.get(1)) / (float)scaleFactor, this.vector.get(2)) : null;
   }
}
