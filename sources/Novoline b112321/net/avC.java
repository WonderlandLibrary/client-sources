package net;

import cc.novoline.events.events.EventState;
import cc.novoline.modules.misc.WindowedFullscreen;
import net.CD;
import net.UW;
import net.VN;
import net.a2V;
import net.a6d;
import net.aEu;
import net.aG1;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.axu;
import net.minecraft.client.Minecraft;

public final class avC extends as0 {
   @VN("switch-back")
   private aEu x = axu.b();
   private int z;
   private int y;

   public avC(UW var1) {
      super(var1, "AutoTool", "Auto Tool", 0, a2V.MISC, "Switches to the best tool");
      ae9.a(new adZ("AT_SWITCH_BACK", "Switch Back", a6d.CHECKBOX, this, this.x));
   }

   @agu
   public void a(aG1 var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.h().equals(EventState.PRE)) {
         if(this.w.playerController.f()) {
            ++this.y;
            if(this.y == 1) {
               this.z = this.w.thePlayer.bJ.currentItem;
            }

            this.w.thePlayer.b(this.w.objectMouseOver.getBlockPos());
         }

         if(this.y > 0) {
            if(((Boolean)this.x.a()).booleanValue()) {
               this.j.y().a(new CD(this, 100));
            }

            this.y = 0;
         }
      }

   }

   static Minecraft b(avC var0) {
      return var0.w;
   }

   static Minecraft a(avC var0) {
      return var0.w;
   }

   static int c(avC var0) {
      return var0.z;
   }
}
