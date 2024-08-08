package com.example.editme.modules.render;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.Pair;
import com.example.editme.util.setting.SettingsManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.BossInfoClient;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import org.lwjgl.opengl.GL11;

@Module.Info(
   name = "BossStack",
   description = "Modify the boss health GUI to take up less space",
   category = Module.Category.MISC
)
public class BossStack extends Module {
   private static Setting scale;
   private static final ResourceLocation GUI_BARS_TEXTURES;
   private static Setting mode;

   static {
      mode = SettingsManager.e("Mode", BossStack.BossStackMode.STACK);
      scale = SettingsManager.d("Scale", 0.5D);
      GUI_BARS_TEXTURES = new ResourceLocation("textures/gui/bars.png");
   }

   public BossStack() {
      this.registerAll(new Setting[]{mode, scale});
   }

   public static void render(Post var0) {
      Map var1;
      int var4;
      String var8;
      if (mode.getValue() == BossStack.BossStackMode.MINIMIZE) {
         var1 = Minecraft.func_71410_x().field_71456_v.func_184046_j().field_184060_g;
         if (var1 == null) {
            return;
         }

         ScaledResolution var2 = new ScaledResolution(Minecraft.func_71410_x());
         int var3 = var2.func_78326_a();
         var4 = 12;

         for(Iterator var5 = var1.entrySet().iterator(); var5.hasNext(); var4 += 10 + Minecraft.func_71410_x().field_71466_p.field_78288_b) {
            Entry var6 = (Entry)var5.next();
            BossInfoClient var7 = (BossInfoClient)var6.getValue();
            var8 = var7.func_186744_e().func_150254_d();
            int var9 = (int)((double)var3 / (Double)scale.getValue() / 2.0D - 91.0D);
            GL11.glScaled((Double)scale.getValue(), (Double)scale.getValue(), 1.0D);
            if (!var0.isCanceled()) {
               GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.func_71410_x().func_110434_K().func_110577_a(GUI_BARS_TEXTURES);
               Minecraft.func_71410_x().field_71456_v.func_184046_j().func_184052_a(var9, var4, var7);
               Minecraft.func_71410_x().field_71466_p.func_175063_a(var8, (float)((double)var3 / (Double)scale.getValue() / 2.0D - (double)(Minecraft.func_71410_x().field_71466_p.func_78256_a(var8) / 2)), (float)(var4 - 9), 16777215);
            }

            GL11.glScaled(1.0D / (Double)scale.getValue(), 1.0D / (Double)scale.getValue(), 1.0D);
         }
      } else if (mode.getValue() == BossStack.BossStackMode.STACK) {
         var1 = Minecraft.func_71410_x().field_71456_v.func_184046_j().field_184060_g;
         HashMap var12 = new HashMap();
         Iterator var13 = var1.entrySet().iterator();

         while(var13.hasNext()) {
            Entry var15 = (Entry)var13.next();
            String var16 = ((BossInfoClient)var15.getValue()).func_186744_e().func_150254_d();
            Pair var18;
            if (var12.containsKey(var16)) {
               var18 = (Pair)var12.get(var16);
               var18 = new Pair(var18.getKey(), (Integer)var18.getValue() + 1);
               var12.put(var16, var18);
            } else {
               var18 = new Pair(var15.getValue(), 1);
               var12.put(var16, var18);
            }
         }

         ScaledResolution var14 = new ScaledResolution(Minecraft.func_71410_x());
         var4 = var14.func_78326_a();
         int var17 = 12;

         for(Iterator var19 = var12.entrySet().iterator(); var19.hasNext(); var17 += 10 + Minecraft.func_71410_x().field_71466_p.field_78288_b) {
            Entry var20 = (Entry)var19.next();
            var8 = (String)var20.getKey();
            BossInfoClient var21 = (BossInfoClient)((Pair)var20.getValue()).getKey();
            int var10 = (Integer)((Pair)var20.getValue()).getValue();
            var8 = String.valueOf((new StringBuilder()).append(var8).append(" x").append(var10));
            int var11 = (int)((double)var4 / (Double)scale.getValue() / 2.0D - 91.0D);
            GL11.glScaled((Double)scale.getValue(), (Double)scale.getValue(), 1.0D);
            if (!var0.isCanceled()) {
               GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.func_71410_x().func_110434_K().func_110577_a(GUI_BARS_TEXTURES);
               Minecraft.func_71410_x().field_71456_v.func_184046_j().func_184052_a(var11, var17, var21);
               Minecraft.func_71410_x().field_71466_p.func_175063_a(var8, (float)((double)var4 / (Double)scale.getValue() / 2.0D - (double)(Minecraft.func_71410_x().field_71466_p.func_78256_a(var8) / 2)), (float)(var17 - 9), 16777215);
            }

            GL11.glScaled(1.0D / (Double)scale.getValue(), 1.0D / (Double)scale.getValue(), 1.0D);
         }
      }

   }

   private static enum BossStackMode {
      private static final BossStack.BossStackMode[] $VALUES = new BossStack.BossStackMode[]{REMOVE, STACK, MINIMIZE};
      REMOVE,
      MINIMIZE,
      STACK;
   }
}
