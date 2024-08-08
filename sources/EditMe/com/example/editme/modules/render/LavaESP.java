package com.example.editme.modules.render;

import com.example.editme.events.RenderEvent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.render.EditmeTessellator;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

@Module.Info(
   name = "LavaESP",
   category = Module.Category.RENDER
)
public class LavaESP extends Module {
   private Setting renderDistance = this.register(SettingsManager.integerBuilder("Render Distance").withMinimum(2).withMaximum(150).withValue((int)8).build());
   private Setting green = this.register(SettingsManager.integerBuilder("Green").withMinimum(1).withMaximum(255).withValue((int)255).build());
   private List blockPosList;
   private Setting red = this.register(SettingsManager.integerBuilder("Red").withMinimum(1).withMaximum(255).withValue((int)255).build());
   private Setting blue = this.register(SettingsManager.integerBuilder("Blue").withMinimum(1).withMaximum(255).withValue((int)255).build());
   private Setting alpha = this.register(SettingsManager.integerBuilder("Alpha").withMinimum(1).withMaximum(255).withValue((int)255).build());

   private void drawBox(BlockPos var1, Color var2) {
      EditmeTessellator.drawBox(var1, var2.getRGB(), 63);
   }

   public void onWorldRender(RenderEvent var1) {
      if (mc.field_71439_g != null && this.blockPosList != null) {
         if (!this.blockPosList.isEmpty()) {
            EditmeTessellator.prepare(7);
            this.blockPosList.forEach(this::lambda$onWorldRender$0);
            EditmeTessellator.release();
         }
      }
   }

   public void onUpdate() {
      this.blockPosList = this.getSphere(mc.field_71439_g.func_180425_c(), (float)(Integer)this.renderDistance.getValue(), (Integer)this.renderDistance.getValue(), false, true, 0);
   }

   private void lambda$onWorldRender$0(BlockPos var1) {
      if (mc.field_71441_e.func_180495_p(var1).func_177230_c().equals(Blocks.field_150353_l)) {
         this.drawBox(var1, new Color((Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue(), (Integer)this.alpha.getValue()));
      }

   }

   public List getSphere(BlockPos var1, float var2, int var3, boolean var4, boolean var5, int var6) {
      ArrayList var7 = new ArrayList();
      int var8 = var1.func_177958_n();
      int var9 = var1.func_177956_o();
      int var10 = var1.func_177952_p();

      for(int var11 = var8 - (int)var2; (float)var11 <= (float)var8 + var2; ++var11) {
         for(int var12 = var10 - (int)var2; (float)var12 <= (float)var10 + var2; ++var12) {
            for(int var13 = var5 ? var9 - (int)var2 : var9; (float)var13 < (var5 ? (float)var9 + var2 : (float)(var9 + var3)); ++var13) {
               double var14 = (double)((var8 - var11) * (var8 - var11) + (var10 - var12) * (var10 - var12) + (var5 ? (var9 - var13) * (var9 - var13) : 0));
               if (var14 < (double)(var2 * var2) && (!var4 || var14 >= (double)((var2 - 1.0F) * (var2 - 1.0F)))) {
                  BlockPos var16 = new BlockPos(var11, var13 + var6, var12);
                  var7.add(var16);
               }
            }
         }
      }

      return var7;
   }
}
