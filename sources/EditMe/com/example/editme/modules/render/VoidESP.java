package com.example.editme.modules.render;

import com.example.editme.events.EventPlayerUpdate;
import com.example.editme.events.RenderEvent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

@Module.Info(
   name = "VoidESP",
   category = Module.Category.RENDER
)
public class VoidESP extends Module {
   private Setting alpha = this.register(SettingsManager.integerBuilder("Alpha").withMinimum(1).withMaximum(255).withValue((int)255).build());
   private Setting red = this.register(SettingsManager.integerBuilder("Red").withMinimum(1).withMaximum(255).withValue((int)255).build());
   private ICamera camera = new Frustum();
   @EventHandler
   private Listener onPlayerUpdate = new Listener(this::lambda$new$0, new Predicate[0]);
   private Setting renderDistance = this.register(SettingsManager.integerBuilder("Render Distance").withValue((int)8).withMinimum(1).withMaximum(20));
   private Setting green = this.register(SettingsManager.integerBuilder("Green").withMinimum(1).withMaximum(255).withValue((int)255).build());
   private Setting blue = this.register(SettingsManager.integerBuilder("Blue").withMinimum(1).withMaximum(255).withValue((int)255).build());
   public final List voidBlocks = new ArrayList();

   public void onWorldRender(RenderEvent var1) {
      if (mc.func_175598_ae() != null && mc.func_175598_ae().field_78733_k != null) {
         (new ArrayList(this.voidBlocks)).forEach(this::lambda$onWorldRender$1);
      }
   }

   private boolean isVoidHole(BlockPos var1, IBlockState var2) {
      if (var1.func_177956_o() <= 4 && var1.func_177956_o() > 0) {
         BlockPos var3 = var1;

         for(int var4 = var1.func_177956_o(); var4 >= 0; --var4) {
            if (mc.field_71441_e.func_180495_p(var3).func_177230_c() != Blocks.field_150350_a) {
               return false;
            }

            var3 = var3.func_177977_b();
         }

         return true;
      } else {
         return false;
      }
   }

   private void lambda$new$0(EventPlayerUpdate var1) {
      if (mc.field_71439_g != null) {
         this.voidBlocks.clear();

         for(int var2 = (int)(mc.field_71439_g.field_70165_t - (double)(Integer)this.renderDistance.getValue()); (double)var2 < mc.field_71439_g.field_70165_t + (double)(Integer)this.renderDistance.getValue(); ++var2) {
            for(int var3 = (int)(mc.field_71439_g.field_70161_v - (double)(Integer)this.renderDistance.getValue()); (double)var3 < mc.field_71439_g.field_70161_v + (double)(Integer)this.renderDistance.getValue(); ++var3) {
               for(int var4 = (int)(mc.field_71439_g.field_70163_u + (double)(Integer)this.renderDistance.getValue()); (double)var4 > mc.field_71439_g.field_70163_u - (double)(Integer)this.renderDistance.getValue(); --var4) {
                  BlockPos var5 = new BlockPos(var2, var4, var3);
                  IBlockState var6 = mc.field_71441_e.func_180495_p(var5);
                  if (this.isVoidHole(var5, var6)) {
                     this.voidBlocks.add(var5);
                  }
               }
            }
         }

      }
   }

   private void lambda$onWorldRender$1(BlockPos var1) {
      AxisAlignedBB var2 = new AxisAlignedBB((double)var1.func_177958_n() - mc.func_175598_ae().field_78730_l, (double)var1.func_177956_o() - mc.func_175598_ae().field_78731_m, (double)var1.func_177952_p() - mc.func_175598_ae().field_78728_n, (double)(var1.func_177958_n() + 1) - mc.func_175598_ae().field_78730_l, (double)(var1.func_177956_o() + 1) - mc.func_175598_ae().field_78731_m, (double)(var1.func_177952_p() + 1) - mc.func_175598_ae().field_78728_n);
      this.camera.func_78547_a(mc.func_175606_aa().field_70165_t, mc.func_175606_aa().field_70163_u, mc.func_175606_aa().field_70161_v);
      if (this.camera.func_78546_a(new AxisAlignedBB(var2.field_72340_a + mc.func_175598_ae().field_78730_l, var2.field_72338_b + mc.func_175598_ae().field_78731_m, var2.field_72339_c + mc.func_175598_ae().field_78728_n, var2.field_72336_d + mc.func_175598_ae().field_78730_l, var2.field_72337_e + mc.func_175598_ae().field_78731_m, var2.field_72334_f + mc.func_175598_ae().field_78728_n))) {
         GlStateManager.func_179094_E();
         GlStateManager.func_179147_l();
         GlStateManager.func_179097_i();
         GlStateManager.func_179120_a(770, 771, 0, 1);
         GlStateManager.func_179090_x();
         GlStateManager.func_179132_a(false);
         GL11.glEnable(2848);
         GL11.glHint(3154, 4354);
         GL11.glLineWidth(1.5F);
         RenderGlobal.func_189695_b(var2.field_72340_a, var2.field_72338_b, var2.field_72339_c, var2.field_72336_d, var2.field_72337_e, var2.field_72334_f, (float)(Integer)this.red.getValue() / 255.0F, (float)(Integer)this.green.getValue() / 255.0F, (float)(Integer)this.blue.getValue() / 255.0F, (float)(Integer)this.alpha.getValue() / 255.0F);
         GL11.glDisable(2848);
         GlStateManager.func_179132_a(true);
         GlStateManager.func_179126_j();
         GlStateManager.func_179098_w();
         GlStateManager.func_179084_k();
         GlStateManager.func_179121_F();
      }

   }
}
