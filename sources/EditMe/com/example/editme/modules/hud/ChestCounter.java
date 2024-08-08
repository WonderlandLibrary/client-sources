package com.example.editme.modules.hud;

import com.example.editme.gui.EditmeHUDEditor;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.render.RenderUtil;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;

@Module.Info(
   name = "ChestCounter",
   category = Module.Category.HUD
)
public class ChestCounter extends Module {
   private Setting y = this.register(SettingsManager.integerBuilder("y").withValue((int)4).withVisibility(ChestCounter::lambda$new$1).build());
   private Setting x = this.register(SettingsManager.integerBuilder("x").withValue((int)4).withVisibility(ChestCounter::lambda$new$0).build());

   public int getHeight() {
      return this.fontRenderer.getStringHeight("Chest Count: ");
   }

   public void onRender() {
      int var1 = (Integer)this.x.getValue();
      int var2 = (Integer)this.y.getValue();
      if (mc.field_71462_r instanceof EditmeHUDEditor) {
         RenderUtil.drawRect((float)(Integer)this.x.getValue(), (float)(Integer)this.y.getValue(), (float)this.getWidth(), (float)this.getHeight(), (new Color(153, 153, 153, 102)).getRGB());
      }

      long var3 = mc.field_71441_e.field_147482_g.stream().filter(ChestCounter::lambda$onRender$2).count();
      this.fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append("Chest Count: ").append(var3)), (double)var1, (double)var2, -1);
   }

   public int getWidth() {
      long var1 = 0L;

      try {
         var1 = mc.field_71441_e.field_147482_g.stream().filter(ChestCounter::lambda$getWidth$3).count();
      } catch (Exception var4) {
      }

      return this.fontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append("Chest Count: ").append(var1)));
   }

   private static boolean lambda$new$0(Integer var0) {
      return false;
   }

   private static boolean lambda$new$1(Integer var0) {
      return false;
   }

   private static boolean lambda$getWidth$3(TileEntity var0) {
      return var0 instanceof TileEntityChest;
   }

   private static boolean lambda$onRender$2(TileEntity var0) {
      return var0 instanceof TileEntityChest;
   }
}
