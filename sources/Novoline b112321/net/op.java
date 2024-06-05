package net;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.Render3DEvent;
import cc.novoline.events.events.TickUpdateEvent;
import net.Du;
import net.OE;
import net.rm;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import org.lwjgl.util.vector.Vector3f;

public class op {
   @EventTarget
   public void a(Render3DEvent var1) {
      String[] var2 = rm.b();
      if(Minecraft.getInstance().world != null) {
         int var3 = 0;
         if(var3 < Du.F.size()) {
            ((Du)Du.F.get(var3)).a(var1.getPartialTicks());
            ++var3;
         }

      }
   }

   @EventTarget
   public void a(TickUpdateEvent var1) {
      String[] var2 = rm.b();
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
