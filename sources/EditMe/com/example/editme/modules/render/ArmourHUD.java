package com.example.editme.modules.render;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.color.ColourHolder;
import com.example.editme.util.setting.SettingsManager;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

@Module.Info(
   name = "ArmourHUD",
   category = Module.Category.RENDER
)
public class ArmourHUD extends Module {
   private static RenderItem itemRender = Minecraft.func_71410_x().func_175599_af();
   private Setting damage = this.register(SettingsManager.b("Damage", false));

   public void onRender() {
      GlStateManager.func_179098_w();
      ScaledResolution var1 = new ScaledResolution(mc);
      int var2 = var1.func_78326_a() / 2;
      int var3 = 0;
      int var4 = var1.func_78328_b() - 55 - (mc.field_71439_g.func_70090_H() ? 10 : 0);
      Iterator var5 = mc.field_71439_g.field_71071_by.field_70460_b.iterator();

      while(var5.hasNext()) {
         ItemStack var6 = (ItemStack)var5.next();
         ++var3;
         if (!var6.func_190926_b()) {
            int var7 = var2 - 90 + (9 - var3) * 20 + 2;
            GlStateManager.func_179126_j();
            itemRender.field_77023_b = 200.0F;
            itemRender.func_180450_b(var6, var7, var4);
            itemRender.func_180453_a(mc.field_71466_p, var6, var7, var4, "");
            itemRender.field_77023_b = 0.0F;
            GlStateManager.func_179098_w();
            GlStateManager.func_179140_f();
            GlStateManager.func_179097_i();
            String var8 = var6.func_190916_E() > 1 ? String.valueOf((new StringBuilder()).append(var6.func_190916_E()).append("")) : "";
            mc.field_71466_p.func_175063_a(var8, (float)(var7 + 19 - 2 - mc.field_71466_p.func_78256_a(var8)), (float)(var4 + 9), 16777215);
            if ((Boolean)this.damage.getValue()) {
               float var9 = ((float)var6.func_77958_k() - (float)var6.func_77952_i()) / (float)var6.func_77958_k();
               float var10 = 1.0F - var9;
               int var11 = 100 - (int)(var10 * 100.0F);
               mc.field_71466_p.func_175063_a(String.valueOf((new StringBuilder()).append(var11).append("")), (float)(var7 + 8 - mc.field_71466_p.func_78256_a(String.valueOf((new StringBuilder()).append(var11).append(""))) / 2), (float)(var4 - 11), ColourHolder.toHex((int)(var10 * 255.0F), (int)(var9 * 255.0F), 0));
            }
         }
      }

      GlStateManager.func_179126_j();
      GlStateManager.func_179140_f();
   }
}
