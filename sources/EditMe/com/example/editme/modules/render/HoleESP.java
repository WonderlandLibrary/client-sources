package com.example.editme.modules.render;

import com.example.editme.events.RenderEvent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.render.EditmeTessellator;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

@Module.Info(
   name = "HoleESP",
   category = Module.Category.RENDER
)
public class HoleESP extends Module {
   private Setting renderDistance = this.register(SettingsManager.d("Render Distance", 8.0D));
   private Setting renderModeSetting;
   private Setting r1;
   private Setting g2;
   private ConcurrentHashMap safeHoles;
   private Setting b1;
   private Setting r2;
   private Setting a0;
   private Setting b2;
   private Setting g1;
   private final BlockPos[] surroundOffset = new BlockPos[]{new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)};

   private boolean lambda$new$5(Integer var1) {
      return this.bedrockSettings();
   }

   private boolean obbySettings() {
      return true;
   }

   private boolean lambda$new$0(Integer var1) {
      return this.obbySettings();
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

   public HoleESP() {
      this.renderModeSetting = this.register(SettingsManager.e("Render Mode", HoleESP.RenderMode.FULL));
      this.a0 = this.register(SettingsManager.integerBuilder("Alpha").withMinimum(0).withValue((int)32).withMaximum(255).build());
      this.r1 = this.register(SettingsManager.integerBuilder("Obby Red").withMinimum(0).withValue((int)255).withMaximum(255).withVisibility(this::lambda$new$0).build());
      this.g1 = this.register(SettingsManager.integerBuilder("Obby Green").withMinimum(0).withValue((int)255).withMaximum(255).withVisibility(this::lambda$new$1).build());
      this.b1 = this.register(SettingsManager.integerBuilder("Obby Blue").withMinimum(0).withValue((int)0).withMaximum(255).withVisibility(this::lambda$new$2).build());
      this.r2 = this.register(SettingsManager.integerBuilder("Red").withMinimum(0).withValue((int)0).withMaximum(255).withVisibility(this::lambda$new$3).build());
      this.g2 = this.register(SettingsManager.integerBuilder("Green").withMinimum(0).withValue((int)255).withMaximum(255).withVisibility(this::lambda$new$4).build());
      this.b2 = this.register(SettingsManager.integerBuilder("Blue").withMinimum(0).withValue((int)0).withMaximum(255).withVisibility(this::lambda$new$5).build());
   }

   private void lambda$onWorldRender$6(BlockPos var1, Boolean var2) {
      if (var2) {
         this.drawBox(var1, (Integer)this.r2.getValue(), (Integer)this.g2.getValue(), (Integer)this.b2.getValue());
      } else {
         this.drawBox(var1, (Integer)this.r1.getValue(), (Integer)this.g1.getValue(), (Integer)this.b1.getValue());
      }

   }

   public static BlockPos getPlayerPos() {
      return new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v));
   }

   private boolean lambda$new$1(Integer var1) {
      return this.obbySettings();
   }

   private boolean lambda$new$4(Integer var1) {
      return this.bedrockSettings();
   }

   private boolean bedrockSettings() {
      return true;
   }

   private boolean lambda$new$3(Integer var1) {
      return this.bedrockSettings();
   }

   public void onWorldRender(RenderEvent var1) {
      if (mc.field_71439_g != null && this.safeHoles != null) {
         if (!this.safeHoles.isEmpty()) {
            EditmeTessellator.prepare(7);
            this.safeHoles.forEach(this::lambda$onWorldRender$6);
            EditmeTessellator.release();
         }
      }
   }

   private void drawBox(BlockPos var1, int var2, int var3, int var4) {
      Color var5 = new Color(var2, var3, var4, (Integer)this.a0.getValue());
      if (((HoleESP.RenderMode)this.renderModeSetting.getValue()).equals(HoleESP.RenderMode.BOTTOM)) {
         EditmeTessellator.drawBox(var1, var5.getRGB(), 1);
      } else if (((HoleESP.RenderMode)this.renderModeSetting.getValue()).equals(HoleESP.RenderMode.FULL)) {
         EditmeTessellator.drawBox(var1, var5.getRGB(), 63);
      }

   }

   public void onUpdate() {
      if (this.safeHoles == null) {
         this.safeHoles = new ConcurrentHashMap();
      } else {
         this.safeHoles.clear();
      }

      int var1 = (int)Math.ceil((Double)this.renderDistance.getValue());
      List var2 = this.getSphere(getPlayerPos(), (float)var1, var1, false, true, 0);
      Iterator var3 = var2.iterator();

      while(true) {
         BlockPos var4;
         do {
            do {
               do {
                  if (!var3.hasNext()) {
                     return;
                  }

                  var4 = (BlockPos)var3.next();
               } while(!mc.field_71441_e.func_180495_p(var4).func_177230_c().equals(Blocks.field_150350_a));
            } while(!mc.field_71441_e.func_180495_p(var4.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a));
         } while(!mc.field_71441_e.func_180495_p(var4.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a));

         boolean var5 = true;
         boolean var6 = true;
         BlockPos[] var7 = this.surroundOffset;
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            BlockPos var10 = var7[var9];
            Block var11 = mc.field_71441_e.func_180495_p(var4.func_177971_a(var10)).func_177230_c();
            if (var11 != Blocks.field_150357_h) {
               var6 = false;
            }

            if (var11 != Blocks.field_150357_h && var11 != Blocks.field_150343_Z && var11 != Blocks.field_150477_bB && var11 != Blocks.field_150467_bQ) {
               var5 = false;
               break;
            }
         }

         if (var5) {
            this.safeHoles.put(var4, var6);
         }
      }
   }

   private boolean lambda$new$2(Integer var1) {
      return this.obbySettings();
   }

   private static enum RenderMode {
      FULL,
      BOTTOM;

      private static final HoleESP.RenderMode[] $VALUES = new HoleESP.RenderMode[]{BOTTOM, FULL};
   }
}
