package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.MotionUpdateEvent$State;
import cc.novoline.events.events.Render3DEvent;
import cc.novoline.events.events.RenderEntityEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.visual.HUD;
import net.Du;
import net.ML;
import net.OE;
import net.a0J;
import net.a0g;
import net.a2R;
import net.aOE;
import net.aSm;
import net.asJ;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.util.vector.Vector3f;

public class MoreBends extends AbstractModule {
   public static float x;

   public MoreBends(@NotNull ModuleManager var1) {
      super(var1, EnumModuleType.VISUALS, "MoreBends", "More Bends");
   }

   public void onEnable() {
      aSm.b();
      ((a0J)a0g.a("swordTrail")).d = true;
      a2R.c();
   }

   public void onDisable() {
      a2R.a();
   }

   @EventTarget
   public void a(Render3DEvent var1) {
      int var2 = HUD.e();
      if(Minecraft.getInstance().world != null) {
         int var3 = 0;
         if(var3 < Du.F.size()) {
            ((Du)Du.F.get(var3)).a(var1.getPartialTicks());
            ++var3;
         }

         x = var1.getPartialTicks();
      }
   }

   @EventTarget
   public void a(RenderEntityEvent var1) {
      int var2 = HUD.e();
      if(var1.f() == MotionUpdateEvent$State.PRE) {
         if(!(var1.a() instanceof EntityPlayer)) {
            return;
         }

         if(var1.b() instanceof aOE) {
            return;
         }

         if(a2R.a(var1.a()).g) {
            asJ var3 = (asJ)var1.a();
            var1.setCancelled(true);
            ML.a(a2R.a(var3), var3, var1.c(), var1.e(), var1.d(), 0.0F, x);
         }
      }

   }

   @EventTarget
   public void a(TickUpdateEvent var1) {
      int var2 = HUD.h();
      if(Minecraft.getInstance().world != null) {
         int var3 = 0;
         if(var3 < Du.F.size()) {
            Du var4 = (Du)Du.F.get(var3);
            Entity var5 = Minecraft.getInstance().world.removeEntityFromWorld(var4.a);
            if(!var4.l.equalsIgnoreCase(var5.getName())) {
               Du.F.remove(var4);
               Du.a(new Du(var5.getEntityID()));
               OE.a("Reset entity", OE.DEBUG);
            }

            var4.q.set(var4.f);
            var4.f.x = (float)var5.posX - var4.i.x;
            var4.f.y = (float)var5.posY - var4.i.y;
            var4.f.z = (float)var5.posZ - var4.i.z;
            var4.i = new Vector3f((float)var5.posX, (float)var5.posY, (float)var5.posZ);
            Du.F.remove(var4);
            OE.a("No entity", OE.DEBUG);
            ++var3;
         }

      }
   }
}
