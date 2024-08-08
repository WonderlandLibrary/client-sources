package com.example.editme.modules.render;

import com.example.editme.events.RenderEvent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.render.EditmeTessellator;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;

@Module.Info(
   name = "BlockHighlight",
   description = "Happy Halloween <3",
   category = Module.Category.RENDER
)
public class BlockHighlight extends Module {
   private Setting boundingbox = this.register(SettingsManager.b("Bounding Box", true));
   private Setting alpha = this.register(SettingsManager.integerBuilder("Alpha").withMinimum(1).withMaximum(255).withValue((int)28));
   private Setting box = this.register(SettingsManager.b("Highlight Entire Block", true));
   private Setting width = this.register(SettingsManager.d("Width", 1.0D));
   private Setting Red = this.register(SettingsManager.integerBuilder("Red").withMinimum(1).withMaximum(255).withValue((int)255));
   private Setting rainbow = this.register(SettingsManager.b("Rainbow", false));
   private Setting alpha2 = this.register(SettingsManager.integerBuilder("Bounding Box Alpha").withMinimum(1).withMaximum(255).withValue((int)255));
   private Setting Blue = this.register(SettingsManager.integerBuilder("Blue").withMinimum(1).withMaximum(255).withValue((int)255));
   private Setting Green = this.register(SettingsManager.integerBuilder("Green").withMinimum(1).withMaximum(255).withValue((int)255));

   public void onWorldRender(RenderEvent var1) {
      float[] var2 = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0F};
      int var3 = Color.HSBtoRGB(var2[0], 1.0F, 1.0F);
      int var4 = var3 >> 16 & 255;
      int var5 = var3 >> 8 & 255;
      int var6 = var3 & 255;
      Minecraft var7 = Minecraft.func_71410_x();
      RayTraceResult var8 = var7.field_71476_x;
      if (var8.field_72313_a == Type.BLOCK) {
         BlockPos var9 = var8.func_178782_a();
         IBlockState var10 = var7.field_71441_e.func_180495_p(var9);
         if (var10.func_185904_a() != Material.field_151579_a && var7.field_71441_e.func_175723_af().func_177746_a(var9)) {
            if ((Boolean)this.box.getValue()) {
               EditmeTessellator.prepare(7);
               if ((Boolean)this.rainbow.getValue()) {
                  EditmeTessellator.drawBox(var9, var4, var5, var6, (Integer)this.alpha.getValue(), 63);
               } else {
                  EditmeTessellator.drawBox(var9, (Integer)this.Red.getValue(), (Integer)this.Green.getValue(), (Integer)this.Blue.getValue(), (Integer)this.alpha.getValue(), 63);
               }

               EditmeTessellator.release();
            }

            if ((Boolean)this.boundingbox.getValue()) {
               EditmeTessellator.prepare(7);
               if ((Boolean)this.rainbow.getValue()) {
                  EditmeTessellator.drawBoundingBoxBlockPos(var9, ((Double)this.width.getValue()).floatValue(), var4, var5, var6, (Integer)this.alpha2.getValue());
               } else {
                  EditmeTessellator.drawBoundingBoxBlockPos(var9, ((Double)this.width.getValue()).floatValue(), (Integer)this.Red.getValue(), (Integer)this.Green.getValue(), (Integer)this.Blue.getValue(), (Integer)this.alpha2.getValue());
               }

               EditmeTessellator.release();
            }
         }
      }

   }
}
