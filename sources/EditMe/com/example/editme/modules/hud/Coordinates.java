package com.example.editme.modules.hud;

import com.example.editme.gui.EditmeHUDEditor;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.render.RenderUtil;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import net.minecraft.util.math.MathHelper;

@Module.Info(
   name = "Coordinates",
   category = Module.Category.HUD
)
public class Coordinates extends Module {
   private Setting y = this.register(SettingsManager.integerBuilder("y").withValue((int)4).withVisibility(Coordinates::lambda$new$1).build());
   private Setting x = this.register(SettingsManager.integerBuilder("x").withValue((int)4).withVisibility(Coordinates::lambda$new$0).build());
   String line2 = "NULLGAMENOTLOADED";
   String line1 = "NULLGAMENOTLOADED";

   private String getTowards() {
      switch(MathHelper.func_76128_c((double)(mc.field_71439_g.field_70177_z * 8.0F / 360.0F) + 0.5D) & 7) {
      case 0:
         return "+Z";
      case 1:
         return "-X +Z";
      case 2:
         return "-X";
      case 3:
         return "-X -Z";
      case 4:
         return "-Z";
      case 5:
         return "+X -Z";
      case 6:
         return "+X";
      case 7:
         return "+X +Z";
      default:
         return "Invalid";
      }
   }

   private static boolean lambda$new$0(Integer var0) {
      return false;
   }

   private String getFacing() {
      switch(MathHelper.func_76128_c((double)(mc.field_71439_g.field_70177_z * 8.0F / 360.0F) + 0.5D) & 7) {
      case 0:
         return "South";
      case 1:
         return "South West";
      case 2:
         return "West";
      case 3:
         return "North West";
      case 4:
         return "North";
      case 5:
         return "North East";
      case 6:
         return "East";
      case 7:
         return "South East";
      default:
         return "NULL";
      }
   }

   public void onRender() {
      if (mc.field_71439_g != null) {
         int var1 = (Integer)this.x.getValue();
         int var2 = (Integer)this.y.getValue();
         if (mc.field_71462_r instanceof EditmeHUDEditor) {
            RenderUtil.drawRect((float)(Integer)this.x.getValue(), (float)(Integer)this.y.getValue(), (float)this.getWidth(), (float)this.getHeight(), (new Color(153, 153, 153, 102)).getRGB());
         }

         this.line1 = String.valueOf((new StringBuilder()).append(this.getFacing()).append("   ").append(this.getTowards()));
         if (mc.field_71439_g.field_71093_bK != -1) {
            this.line2 = String.valueOf((new StringBuilder()).append(Math.round(mc.field_71439_g.field_70165_t)).append(" ").append(Math.round(mc.field_71439_g.field_70163_u)).append(" ").append(Math.round(mc.field_71439_g.field_70161_v)).append("   Nether: ").append(Math.round(mc.field_71439_g.field_70165_t / 8.0D)).append(" ").append(Math.round(mc.field_71439_g.field_70163_u / 2.0D)).append(" ").append(Math.round(mc.field_71439_g.field_70161_v / 8.0D)));
         } else {
            this.line2 = String.valueOf((new StringBuilder()).append(Math.round(mc.field_71439_g.field_70165_t * 8.0D)).append(" ").append(Math.round(mc.field_71439_g.field_70163_u * 2.0D)).append(" ").append(Math.round(mc.field_71439_g.field_70161_v * 8.0D)).append("   Nether: ").append(Math.round(mc.field_71439_g.field_70165_t)).append(" ").append(Math.round(mc.field_71439_g.field_70163_u)).append(" ").append(Math.round(mc.field_71439_g.field_70161_v)));
         }

         this.fontRenderer.drawStringWithShadow(this.line1, (double)var1, (double)var2, -1);
         var2 += this.fontRenderer.getStringHeight(this.line1) + 5;
         this.fontRenderer.drawStringWithShadow(this.line2, (double)var1, (double)var2, -1);
      }
   }

   public int getHeight() {
      return this.fontRenderer.getStringHeight(this.line1) + 5 + this.fontRenderer.getStringHeight(this.line2);
   }

   public int getWidth() {
      return Math.max(this.fontRenderer.getStringWidth(this.line1), this.fontRenderer.getStringWidth(this.line2));
   }

   private static boolean lambda$new$1(Integer var0) {
      return false;
   }
}
