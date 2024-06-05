package net;

import cc.novoline.events.events.EventState;
import net.UW;
import net.VN;
import net.WB;
import net.a2V;
import net.a6d;
import net.aEu;
import net.aG1;
import net.adZ;
import net.ae9;
import net.agu;
import net.apV;
import net.as0;
import net.asx;
import net.avu;
import net.axu;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging$Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class avm extends as0 {
   @VN("vanilla")
   private final aEu x = axu.g();

   public avm(UW var1) {
      super(var1, "NoSlow", "No Slow", a2V.MOVEMENT, "No slow down when using items");
      ae9.a(new adZ("NS_VANILLA", "Vanilla", a6d.CHECKBOX, this, this.x));
   }

   @agu
   public void a(WB var1) {
      this.c(((Boolean)this.x.a()).booleanValue()?"Vanilla":"Packet");
   }

   @agu
   public void a(aG1 var1) {
      String var2 = avu.r();
      if(!((Boolean)this.x.a()).booleanValue() && this.w.thePlayer.p() && this.w.thePlayer.getHeldItem() != null && this.w.thePlayer.getHeldItem().getItem() instanceof ItemSword && this.w.gameSettings.keyBindUseItem.isKeyDown() && !((asx)this.b(asx.class)).E()) {
         if(var1.h().equals(EventState.PRE)) {
            this.b(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
         }

         this.b(new C08PacketPlayerBlockPlacement(this.w.thePlayer.getHeldItem()));
      }

   }

   @agu
   public void a(apV var1) {
      var1.setCancelled(true);
   }

   public void n() {
      this.c(((Boolean)this.x.a()).booleanValue()?"Vanilla":"NCP");
   }

   public aEu a() {
      return this.x;
   }
}
