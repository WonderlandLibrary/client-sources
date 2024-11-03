package net.augustus.modules.render;

import java.awt.Color;
import java.util.Arrays;
import net.augustus.events.EventRender3D;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.ColorSetting;
import net.augustus.settings.DoubleValue;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class Line extends Module {
   public final BooleanValue line = new BooleanValue(0, "FeetLine", this, true);
   public final ColorSetting color = new ColorSetting(1, "Color", this, new Color(77, 0, 255, 255));
   public final DoubleValue lineWidth = new DoubleValue(2, "LineWidth", this, 2.0, 0.1, 4.0, 2);
   public final DoubleValue lineTime = new DoubleValue(3, "LineTime", this, 3000.0, 500.0, 20000.0, 0);
   public final BooleanValue killAura = new BooleanValue(4, "AuraLine", this, true);
   public final ColorSetting killAuraColor = new ColorSetting(5, "AuraColor", this, new Color(77, 0, 255, 255));
   public final DoubleValue killAuraLineWidth = new DoubleValue(6, "AuraLineWidth", this, 2.0, 0.1, 4.0, 2);
   public final DoubleValue killAuraLineTime = new DoubleValue(7, "AuraLineTime", this, 3000.0, 500.0, 20000.0, 0);
   private java.util.ArrayList<double[]> positions = new java.util.ArrayList<>();

   public Line() {
      super("Line", new Color(10, 20, 15), Categorys.RENDER);
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.positions.clear();
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.positions = new java.util.ArrayList<>();
   }

   @EventTarget
   public void onEventRender3D(EventRender3D eventRender3D) {
      if (this.line.getBoolean()) {
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(2848);
         GL11.glDisable(3553);
         GlStateManager.disableCull();
         GL11.glDepthMask(false);
         float x = (float)(mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * (double)mc.getTimer().renderPartialTicks);
         float y = (float)(
            mc.thePlayer.lastTickPosY
               + this.lineWidth.getValue() / 100.0
               + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * (double)mc.getTimer().renderPartialTicks
         );
         float z = (float)(mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * (double)mc.getTimer().renderPartialTicks);
         this.positions.add(new double[]{(double)x, (double)y, (double)z, (double)System.currentTimeMillis()});
         this.positions.removeIf(values -> this.shouldRenderPoint(values[3]));
         GL11.glColor4f(
            (float)this.color.getColor().getRed() / 255.0F,
            (float)this.color.getColor().getGreen() / 255.0F,
            (float)this.color.getColor().getBlue() / 255.0F,
            (float)this.color.getColor().getAlpha() / 255.0F
         );
         GL11.glLineWidth((float)this.lineWidth.getValue());
         Tessellator tessellator = Tessellator.getInstance();
         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         worldrenderer.begin(3, DefaultVertexFormats.POSITION);
         double[] lastPosition = new double[]{-2.0, -1.0, -1.0};

         for(double[] position : this.positions) {
            if (!Arrays.equals(lastPosition, new double[]{-2.0, -1.0, -1.0}) && !Arrays.equals(lastPosition, position)) {
               worldrenderer.pos(
                     (double)((float)position[0]) - mc.getRenderManager().getRenderPosX(),
                     (double)((float)position[1]) - mc.getRenderManager().getRenderPosY(),
                     (double)((float)position[2]) - mc.getRenderManager().getRenderPosZ()
                  )
                  .endVertex();
            }

            lastPosition = position;
         }

         tessellator.draw();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glDepthMask(true);
         GlStateManager.enableCull();
         GL11.glEnable(3553);
         GL11.glEnable(2929);
         GL11.glDisable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glDisable(2848);
      }
   }

   private boolean shouldRenderPoint(double time) {
      return Math.abs(time - (double)System.currentTimeMillis()) > this.lineTime.getValue();
   }
}
