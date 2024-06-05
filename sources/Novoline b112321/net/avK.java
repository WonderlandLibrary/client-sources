package net;

import cc.novoline.events.events.EventState;
import cc.novoline.modules.misc.WindowedFullscreen;
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
import net.au7;
import net.axu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import org.lwjgl.input.Mouse;

public class avK extends as0 {
   @VN("pearl")
   private final aEu x = axu.b();
   @VN("friend")
   private final aEu z = axu.b();
   @VN("down-place")
   private final aEu A = axu.g();
   private boolean y = true;

   public avK(UW var1) {
      super(var1, a2V.MISC, "MiddleClick", "Middle Click");
      ae9.a(new adZ("MC_FRIEND", "Friend", a6d.CHECKBOX, this, this.z));
      ae9.a(new adZ("MC_PEARL", "Pearl", a6d.CHECKBOX, this, this.x));
      ae9.a(new adZ("MC_DOWN_PLACE", "Down Place", a6d.CHECKBOX, this, this.A));
   }

   private int a() {
      return this.w.thePlayer.a(Items.ender_pearl);
   }

   private void a(int var1) {
      String[] var2 = WindowedFullscreen.a();
      if(this.w.thePlayer.bJ.currentItem != this.a()) {
         this.a(new C09PacketHeldItemChange(var1));
      }

   }

   @agu
   public void a(aG1 var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.h().equals(EventState.PRE)) {
         if(Mouse.isButtonDown(2)) {
            if(!this.y) {
               return;
            }

            label37: {
               if(this.w.objectMouseOver.entityHit == null || !(this.w.objectMouseOver.entityHit instanceof EntityPlayer) || !((Boolean)this.z.a()).booleanValue()) {
                  if(this.a() == -1 || !((Boolean)this.x.a()).booleanValue()) {
                     break label37;
                  }

                  this.a(this.a());
                  this.b(new C08PacketPlayerBlockPlacement(this.w.thePlayer.getHeldItem()));
                  this.a(this.w.thePlayer.bJ.currentItem);
               }

               EntityPlayer var3 = (EntityPlayer)this.w.objectMouseOver.entityHit;
               if(this.j.k().b(var3.getName(), au7.FRIEND)) {
                  this.w.thePlayer.sendChatMessage(".f remove " + var3.getName());
               }

               this.w.thePlayer.sendChatMessage(".f add " + var3.getName());
            }

            this.y = false;
         }

         this.y = true;
      }

   }

   public boolean b() {
      String[] var1 = WindowedFullscreen.a();
      return this.y() && ((Boolean)this.A.a()).booleanValue();
   }
}
