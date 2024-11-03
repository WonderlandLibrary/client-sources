package net.augustus.modules.render;

import java.awt.Color;
import net.augustus.events.EventRender3D;
import net.augustus.events.EventRenderChunk;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.ColorSetting;
import net.augustus.settings.DoubleValue;
import net.augustus.utils.RainbowUtil;
import net.augustus.utils.RenderUtil;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class BlockESP extends Module {
   private static final java.util.ArrayList<BlockPos> POSITIONS = new java.util.ArrayList<>();
   private final RainbowUtil rainbowUtil = new RainbowUtil();
   public ColorSetting color = new ColorSetting(2, "Color", this, new Color(21, 121, 230, 65));
   public BooleanValue rainbow = new BooleanValue(11, "Rainbow", this, false);
   public DoubleValue rainbowSpeed = new DoubleValue(12, "RainbowSpeed", this, 55.0, 0.0, 1000.0, 0);
   public DoubleValue rainbowAlpha = new DoubleValue(13, "RainbowAlpha", this, 80.0, 0.0, 255.0, 0);
   public DoubleValue lineWidth = new DoubleValue(1, "LineWidth", this, 6.0, 0.0, 15.0, 0);
   public DoubleValue distance = new DoubleValue(3, "Distance", this, 50.0, 1.0, 300.0, 0);
   public DoubleValue id = new DoubleValue(4, "ID", this, 26.0, 1.0, 400.0, 0);

   public BlockESP() {
      super("BlockESP", new Color(171, 9, 41), Categorys.RENDER);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      POSITIONS.clear();
      mc.renderGlobal.loadRenderers();
   }

   @EventTarget
   public void onEventTick(EventTick eventTick) {
      for(int i = POSITIONS.size() - 1; i >= 0; --i) {
         BlockPos position = POSITIONS.get(i);
         if (!mc.theWorld.getBlockState(position).getBlock().equals(Block.getBlockById((int)this.id.getValue()))) {
            POSITIONS.remove(i);
         }
      }
   }

   @EventTarget
   public void onEventRenderChunk(EventRenderChunk event) {
      if (event.getiBlockState().getBlock().equals(Block.getBlockById((int)this.id.getValue())) && !POSITIONS.contains(event.getBlockPos())) {
         POSITIONS.add(event.getBlockPos());
      }
   }

   @EventTarget
   public void onEventRender3D(EventRender3D eventRender3D) {
      if (mc.thePlayer != null && mc.theWorld != null) {
         if (this.rainbow.getBoolean()) {
            this.rainbowUtil
               .updateRainbow(
                  this.rainbowSpeed.getValue() == 1000.0 ? (float)(this.rainbowSpeed.getValue() * 1.0E-5F) : (float)(this.rainbowSpeed.getValue() * 1.0E-6F),
                  (int)this.rainbowAlpha.getValue()
               );
         }

         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(2848);
         GL11.glDisable(2929);
         GL11.glDisable(3553);
         GlStateManager.disableCull();
         GL11.glDepthMask(false);
         float red = this.rainbow.getBoolean() ? (float)this.rainbowUtil.getColor().getRed() / 255.0F : (float)this.color.getColor().getRed() / 225.0F;
         float green = this.rainbow.getBoolean() ? (float)this.rainbowUtil.getColor().getGreen() / 255.0F : (float)this.color.getColor().getGreen() / 225.0F;
         float blue = this.rainbow.getBoolean() ? (float)this.rainbowUtil.getColor().getBlue() / 255.0F : (float)this.color.getColor().getBlue() / 225.0F;
         float alpha = this.rainbow.getBoolean() ? (float)this.rainbowUtil.getColor().getAlpha() / 255.0F : (float)this.color.getColor().getAlpha() / 225.0F;

         for(BlockPos blockPos : POSITIONS) {
            this.render(blockPos, red, green, blue, alpha);
         }

         GL11.glDepthMask(true);
         GlStateManager.enableCull();
         GL11.glEnable(3553);
         GL11.glEnable(2929);
         GL11.glDisable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glDisable(2848);
      }
   }

   private void render(BlockPos blockPos, float red, float green, float blue, float alpha) {
      float lineWidth = (float)(this.lineWidth.getValue() / 2.0);
      if (mc.thePlayer.getDistance((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ()) > 1.0) {
         double d0 = 1.0 - mc.thePlayer.getDistance((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ()) / 20.0;
         if (d0 < 0.3) {
            d0 = 0.3;
         }

         lineWidth = (float)((double)lineWidth * d0);
      }

      RenderUtil.drawBlockESP(blockPos, red, green, blue, alpha, 1.0F, lineWidth);
   }
}
