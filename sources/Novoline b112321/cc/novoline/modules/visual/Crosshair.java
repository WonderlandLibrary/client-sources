package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.Render2DEvent;
import cc.novoline.gui.screen.dropdown.DropdownGUI;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.DoubleProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.ColorUtils;
import cc.novoline.utils.ScaleUtils;
import net.J3;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public final class Crosshair extends AbstractModule {
   @Property("dynamic")
   private final BooleanProperty dynamic = PropertyFactory.booleanTrue();
   @Property("gap")
   private final DoubleProperty gap = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(0.95D)).minimum(Double.valueOf(0.25D))).maximum(Double.valueOf(15.0D));
   @Property("width")
   private final DoubleProperty width = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(0.25D)).minimum(Double.valueOf(0.0D))).maximum(Double.valueOf(10.0D));
   @Property("size")
   private final DoubleProperty size = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(4.0D)).minimum(Double.valueOf(0.25D))).maximum(Double.valueOf(15.0D));

   public Crosshair(ModuleManager var1) {
      super(var1, "Crosshair", EnumModuleType.VISUALS, "Crosshair like in cs:go!");
      Manager.put(new Setting("Dynamic", "Dynamic", SettingType.CHECKBOX, this, this.dynamic));
      Manager.put(new Setting("Gap", "Gap", SettingType.SLIDER, this, this.gap, 0.05D));
      Manager.put(new Setting("Width", "Width", SettingType.SLIDER, this, this.width, 0.01D));
      Manager.put(new Setting("CH_size", "Size", SettingType.SLIDER, this, this.size, 0.05D));
   }

   @EventTarget
   public void onEvent(Render2DEvent var1) {
      int var2 = HUD.h();
      if(!(this.mc.currentScreen instanceof DropdownGUI)) {
         GL11.glPushMatrix();
         ScaleUtils.scale(this.mc);
         double var3 = ((Double)this.gap.get()).doubleValue();
         double var5 = ((Double)this.width.get()).doubleValue();
         double var7 = ((Double)this.size.get()).doubleValue();
         boolean var9 = ((Boolean)this.dynamic.get()).booleanValue();
         int var10 = ColorUtils.getColor(0, 0, 0, 255);
         int var11 = ((HUD)this.getModule(HUD.class)).getHUDColor();
         ScaledResolution var12 = var1.getResolution();
         int var13 = var12.getScaledWidthStatic(this.mc);
         int var14 = var12.getScaledHeightStatic(this.mc);
         float var15 = (float)var13 / 2.0F;
         float var16 = (float)var14 / 2.0F;
         if(var9) {
            if(this.mc.player.isMoving()) {
               J3.a((double)var15 - var5, (double)var16 - var3 - var7 - 2.0D, (double)(var15 + 1.0F) + var5, (double)var16 - var3 - 2.0D, 0.5F, var10, var11);
               J3.a((double)var15 - var5, (double)var16 + var3 + 1.0D + (double)(this.mc.player.isMoving()?2:0) - 0.15000000596046448D, (double)(var15 + 1.0F) + var5, (double)(var16 + 1.0F) + var3 + var7 + (double)(this.mc.player.isMoving()?2:0) - 0.15000000596046448D, 0.5F, var10, var11);
               J3.a((double)var15 - var3 - var7 - (double)(this.mc.player.isMoving()?2:0) + 0.5D, (double)var16 - var5, (double)var15 - var3 - (double)(this.mc.player.isMoving()?2:0) + 0.5D, (double)(var16 + 1.0F) + var5, 0.5F, var10, var11);
               J3.a((double)(var15 + 0.5F) + var3 + (double)(this.mc.player.isMoving()?2:0), (double)var16 - var5, (double)var15 + var7 + var3 + 0.5D + (double)(this.mc.player.isMoving()?2:0), (double)(var16 + 1.0F) + var5, 0.5F, var10, var11);
            }

            J3.a((double)var15 - var5, (double)var16 - var3 - var7 - 0.0D, (double)(var15 + 1.0F) + var5, (double)var16 - var3 - 0.0D, 0.5F, var10, var11);
            J3.a((double)var15 - var5, (double)var16 + var3 + 1.0D + (double)(this.mc.player.isMoving()?2:0) - 0.15000000596046448D, (double)(var15 + 1.0F) + var5, (double)(var16 + 1.0F) + var3 + var7 + (double)(this.mc.player.isMoving()?2:0) - 0.15000000596046448D, 0.5F, var10, var11);
            J3.a((double)var15 - var3 - var7 - (double)(this.mc.player.isMoving()?2:0) + 0.5D, (double)var16 - var5, (double)var15 - var3 - (double)(this.mc.player.isMoving()?2:0) + 0.5D, (double)(var16 + 1.0F) + var5, 0.5F, var10, var11);
            J3.a((double)(var15 + 0.5F) + var3 + (double)(this.mc.player.isMoving()?2:0), (double)var16 - var5, (double)var15 + var7 + var3 + 0.5D + (double)(this.mc.player.isMoving()?2:0), (double)(var16 + 1.0F) + var5, 0.5F, var10, var11);
         }

         if(this.mc.player.isMoving()) {
            J3.a((double)var15 - var5, (double)var16 - var3 - var7 - 0.0D, (double)(var15 + 1.0F) + var5, (double)var16 - var3 - 0.0D, 0.5F, var10, var11);
            J3.a((double)var15 - var5, (double)var16 + var3 + 1.0D + 0.0D - 0.15000000596046448D, (double)(var15 + 1.0F) + var5, (double)(var16 + 1.0F) + var3 + var7 + 0.0D - 0.15000000596046448D, 0.5F, var10, var11);
            J3.a((double)var15 - var3 - var7 - 0.0D + 0.5D, (double)var16 - var5, (double)var15 - var3 - 0.0D + 0.5D, (double)(var16 + 1.0F) + var5, 0.5F, var10, var11);
            J3.a((double)(var15 + 0.5F) + var3 + 0.0D, (double)var16 - var5, (double)var15 + var7 + var3 + 0.5D + 0.0D, (double)(var16 + 1.0F) + var5, 0.5F, var10, var11);
         }

         J3.a((double)var15 - var5, (double)var16 - var3 - var7 - 0.0D, (double)(var15 + 1.0F) + var5, (double)var16 - var3 - 0.0D, 0.5F, var10, var11);
         J3.a((double)var15 - var5, (double)var16 + var3 + 1.0D + 0.0D - 0.15000000596046448D, (double)(var15 + 1.0F) + var5, (double)(var16 + 1.0F) + var3 + var7 + 0.0D - 0.15000000596046448D, 0.5F, var10, var11);
         J3.a((double)var15 - var3 - var7 - 0.0D + 0.5D, (double)var16 - var5, (double)var15 - var3 - 0.0D + 0.5D, (double)(var16 + 1.0F) + var5, 0.5F, var10, var11);
         J3.a((double)(var15 + 0.5F) + var3 + 0.0D, (double)var16 - var5, (double)var15 + var7 + var3 + 0.5D + 0.0D, (double)(var16 + 1.0F) + var5, 0.5F, var10, var11);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
      }
   }
}
