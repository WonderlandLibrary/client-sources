package com.example.editme.modules.render;

import com.example.editme.events.RenderEvent;
import com.example.editme.modules.Module;
import com.example.editme.util.render.NametagRenderer;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;

@Module.Info(
   name = "Nametags",
   category = Module.Category.RENDER
)
public class Nametags extends Module {
   public void onWorldRender(RenderEvent var1) {
      Iterator var2 = mc.field_71441_e.field_73010_i.iterator();

      while(var2.hasNext()) {
         EntityPlayer var3 = (EntityPlayer)var2.next();
         if (var3 != mc.func_175606_aa() && var3.func_70089_S() && !LogoutSpots.isGhost(var3)) {
            float var4 = (float)(var3.field_70142_S + (var3.field_70165_t - var3.field_70142_S) * (double)mc.field_71428_T.field_194147_b - mc.func_175598_ae().field_78725_b);
            float var5 = (float)(var3.field_70137_T + (var3.field_70163_u - var3.field_70137_T) * (double)mc.field_71428_T.field_194147_b - mc.func_175598_ae().field_78726_c);
            float var6 = (float)(var3.field_70136_U + (var3.field_70161_v - var3.field_70136_U) * (double)mc.field_71428_T.field_194147_b - mc.func_175598_ae().field_78723_d);
            if (!var3.func_70005_c_().startsWith("Body #")) {
               NametagRenderer.drawNameplate(var3, var4, var5, var6);
            }
         }
      }

   }
}
