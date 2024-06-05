package net;

import cc.novoline.events.events.EventState;
import java.util.function.Supplier;
import net.UW;
import net.VN;
import net.WL;
import net.a2V;
import net.a6d;
import net.aEE;
import net.aEu;
import net.aG1;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.avq;
import net.axu;
import net.d3;
import net.lS;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;

public class avb extends as0 {
   private d3 x = new d3();
   @VN("health")
   private final aEE A = (aEE)((aEE)axu.a(Double.valueOf(14.0D)).d(Double.valueOf(1.0D))).c(Double.valueOf(20.0D));
   @VN("force-absorption")
   private final aEu y = axu.g();
   @VN("check-regen")
   private final aEu z = axu.g();

   public avb(UW var1) {
      super(var1, a2V.COMBAT, "AutoHead", "Auto Head");
      ae9.a(new adZ("AH_FORCE_ABS", "Force absorption", a6d.CHECKBOX, this, this.y));
      ae9.a(new adZ("AH_CHECK_REGEN", "Check Regen", a6d.CHECKBOX, this, this.z, this::lambda$new$0));
      ae9.a(new adZ("AH_HEALTH", "Health", a6d.SLIDER, this, this.A, 1.0D, this::lambda$new$1));
   }

   public int a() {
      return this.w.thePlayer.a(Items.skull);
   }

   @agu
   public void a(aG1 var1) {
      int[] var2 = avq.a();
      if(var1.h().equals(EventState.PRE) && !lS.a(WL.LOBBY) && !lS.a(WL.PRE) && this.a(this.a())) {
         ItemStack var3 = this.w.thePlayer.bJ.getStackInSlot(this.a());
         if(var3 != null && var3.getItem() instanceof ItemSkull && this.x.a(500.0D)) {
            this.a(new C09PacketHeldItemChange(this.a()));
            this.a(new C08PacketPlayerBlockPlacement(this.w.thePlayer.getHeldItem()));
            this.a(new C09PacketHeldItemChange(this.w.thePlayer.bJ.currentItem));
            this.x.b();
         }
      }

   }

   private boolean a(int var1) {
      boolean var10000;
      label0: {
         int[] var2 = avq.a();
         if(var1 != -1) {
            if(((Boolean)this.y.a()).booleanValue()) {
               if(this.w.thePlayer.getAbsorptionAmount() <= 0.0F) {
                  break label0;
               }
            } else if((double)this.w.thePlayer.getHealth() < ((Double)this.A.a()).doubleValue() && (!((Boolean)this.z.a()).booleanValue() || !this.w.thePlayer.isPotionActive(Potion.regeneration))) {
               break label0;
            }
         }

         var10000 = false;
         return var10000;
      }

      var10000 = true;
      return var10000;
   }

   private Boolean lambda$new$1() {
      int[] var1 = avq.a();
      return Boolean.valueOf(!((Boolean)this.y.a()).booleanValue());
   }

   private Boolean lambda$new$0() {
      int[] var1 = avq.a();
      return Boolean.valueOf(!((Boolean)this.y.a()).booleanValue());
   }
}
