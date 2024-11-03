package net.augustus.modules.render;

import java.awt.Color;
import net.augustus.events.EventRender3D;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.ColorSetting;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.RainbowUtil;
import net.augustus.utils.RenderUtil;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import org.lwjgl.opengl.GL11;

public class StorageESP extends Module {
   private final RainbowUtil rainbowUtil = new RainbowUtil();
   private final java.util.ArrayList<TileEntity> tileEntities = new java.util.ArrayList<>();
   private final java.util.ArrayList<TileEntity> notRender = new java.util.ArrayList<>();
   public ColorSetting color = new ColorSetting(2, "Color", this, new Color(21, 121, 230, 65));
   public StringValue mode = new StringValue(3, "Mode", this, "Box", new String[]{"Box", "OtherBox", "FakeCorner", "Fake2D", "Real2D"});
   public BooleanValue rainbow = new BooleanValue(11, "Rainbow", this, false);
   public DoubleValue rainbowSpeed = new DoubleValue(12, "RainbowSpeed", this, 55.0, 0.0, 1000.0, 0);
   public DoubleValue rainbowAlpha = new DoubleValue(13, "RainbowAlpha", this, 80.0, 0.0, 255.0, 0);
   public DoubleValue lineWidth = new DoubleValue(1, "LineWidth", this, 6.0, 0.0, 15.0, 0);
   public DoubleValue renderDistance = new DoubleValue(4, "MaxDistance", this, 6.0, 0.0, 32.0, 0);

   public StorageESP() {
      super("StorageESP", new Color(100, 166, 148, 255), Categorys.RENDER);
   }

   @EventTarget
   public void onEventTick(EventTick eventTick) {
      if (mc.theWorld != null) {
    	 if(mm.arrayList.mode.getSelected().equalsIgnoreCase("Default")) {
    	      this.setDisplayName(this.getName() + "  " + this.mode.getSelected());
    	 }else {
    		 this.setDisplayName(this.getName() + this.mode.getSelected());
    	 }
         this.tileEntities.clear();
         this.notRender.clear();

         for(TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {
            if ((tileEntity instanceof TileEntityChest || tileEntity instanceof TileEntityEnderChest)
               && mc.thePlayer.getDistance((double)tileEntity.getPos().getX(), (double)tileEntity.getPos().getY(), (double)tileEntity.getPos().getZ())
                  < this.renderDistance.getValue() * 16.0) {
               if (tileEntity instanceof TileEntityChest) {
                  TileEntityChest tileEntityChest = (TileEntityChest)tileEntity;
                  if (tileEntityChest.adjacentChestXNeg != null && !this.tileEntities.contains(tileEntityChest.adjacentChestXNeg)) {
                     this.notRender.add(tileEntityChest.adjacentChestXNeg);
                  } else if (tileEntityChest.adjacentChestZNeg != null && !this.tileEntities.contains(tileEntityChest.adjacentChestZNeg)) {
                     this.notRender.add(tileEntityChest.adjacentChestZNeg);
                  } else if (tileEntityChest.adjacentChestZPos != null && !this.tileEntities.contains(tileEntityChest.adjacentChestZPos)) {
                     this.notRender.add(tileEntityChest.adjacentChestZPos);
                  } else if (tileEntityChest.adjacentChestXPos != null && !this.tileEntities.contains(tileEntityChest.adjacentChestXPos)) {
                     this.notRender.add(tileEntityChest.adjacentChestXPos);
                  }
               }

               this.tileEntities.add(tileEntity);
            }
         }

         this.tileEntities.removeIf(this.notRender::contains);
      }
   }

   @EventTarget
   public void onEventRender3D(EventRender3D eventRender3D) {
      if (this.rainbow.getBoolean()) {
         this.rainbowUtil
            .updateRainbow(
               this.rainbowSpeed.getValue() == 1000.0 ? (float)(this.rainbowSpeed.getValue() * 1.0E-5F) : (float)(this.rainbowSpeed.getValue() * 1.0E-6F),
               (int)this.rainbowAlpha.getValue()
            );
      }

      float red = this.rainbow.getBoolean() ? (float)this.rainbowUtil.getColor().getRed() / 255.0F : (float)this.color.getColor().getRed() / 225.0F;
      float green = this.rainbow.getBoolean() ? (float)this.rainbowUtil.getColor().getGreen() / 255.0F : (float)this.color.getColor().getGreen() / 225.0F;
      float blue = this.rainbow.getBoolean() ? (float)this.rainbowUtil.getColor().getBlue() / 255.0F : (float)this.color.getColor().getBlue() / 225.0F;
      float alpha = this.rainbow.getBoolean() ? (float)this.rainbowUtil.getColor().getAlpha() / 255.0F : (float)this.color.getColor().getAlpha() / 225.0F;

      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDisable(3553);
      GlStateManager.disableCull();
      GL11.glDepthMask(false);

      for(TileEntity blockEntity : this.tileEntities) {
         this.render(blockEntity, red, green, blue, alpha);
      }

      GL11.glDepthMask(true);
      GlStateManager.enableCull();
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDisable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(2848);
   }

   private void render(TileEntity tileEntity, float red, float green, float blue, float alpha) {
      float lineWidth = (float)(this.lineWidth.getValue() / 2.0);
      if (mc.thePlayer.getDistance((double)tileEntity.getPos().getX(), (double)tileEntity.getPos().getY(), (double)tileEntity.getPos().getZ()) > 1.0) {
         double d0 = 1.0
            - mc.thePlayer.getDistance((double)tileEntity.getPos().getX(), (double)tileEntity.getPos().getY(), (double)tileEntity.getPos().getZ()) / 20.0;
         if (d0 < 0.3) {
            d0 = 0.3;
         }

         lineWidth = (float)((double)lineWidth * d0);
      }

      String var9 = this.mode.getSelected();
      switch(var9) {
         case "Box": {
            RenderUtil.drawBlockESP(tileEntity, red, green, blue, alpha, 1.0F, lineWidth, false);
            break;
         }
         case "OtherBox": {
            RenderUtil.drawBlockESP(tileEntity, red, green, blue, alpha, 1.0F, lineWidth, true);
            break;
         }
         case "FakeCorner": {
            RenderUtil.drawCornerESP(tileEntity, red, green, blue);
            break;
         }
         case "Fake2D": {
            RenderUtil.drawFake2DESP(tileEntity, red, green, blue);
            break;
         }
      }
   }
}
