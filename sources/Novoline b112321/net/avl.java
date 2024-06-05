package net;

import cc.novoline.events.events.EventState;
import net.UW;
import net.a2V;
import net.aG1;
import net.agu;
import net.as0;
import net.avu;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction$Action;

public class avl extends as0 {
   public avl(UW var1) {
      super(var1, a2V.MOVEMENT, "FastSneak", "Fast Sneak");
   }

   @agu
   public void a(aG1 var1) {
      String var2 = avu.r();
      if(this.w.thePlayer.ap().b() && this.w.thePlayer.p() && !this.a(avu.class)) {
         if(var1.h().equals(EventState.PRE)) {
            this.a(new C0BPacketEntityAction(this.w.thePlayer, C0BPacketEntityAction$Action.STOP_SNEAKING));
         }

         this.a(new C0BPacketEntityAction(this.w.thePlayer, C0BPacketEntityAction$Action.START_SNEAKING));
      }

   }
}
