package net;

import cc.novoline.events.events.EventState;
import java.util.Iterator;
import java.util.function.Supplier;
import net.UW;
import net.VN;
import net.WB;
import net.WL;
import net.a2V;
import net.a6d;
import net.aE3;
import net.aE8;
import net.aEE;
import net.aEs;
import net.aEu;
import net.aG1;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.asx;
import net.avu;
import net.axu;
import net.d3;
import net.lS;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer$C06PacketPlayerPosLook;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.PotionEffect;

public class as2 extends as0 {
   private final d3 y = new d3();
   private boolean x;
   @VN("mode")
   private final aEs B = axu.a("Edit").a(new String[]{"Edit", "Packet"});
   @VN("health")
   private final aEE z = (aEE)((aEE)axu.a(Double.valueOf(14.0D)).d(Double.valueOf(0.5D))).c(Double.valueOf(20.0D));
   @VN("delay")
   private final aE8 D = (aE8)((aE8)axu.b(Integer.valueOf(600)).d(Integer.valueOf(600))).c(Integer.valueOf(1500));
   @VN("potions")
   private final aE3 C = axu.a((Object)"Regen").a((Object[])(new String[]{"Heal", "Regen", "Jump", "Speed", "Fire"}));
   @VN("over-pot")
   private final aEu A = axu.g();

   public as2(UW var1) {
      super(var1, "AutoPotion", "Auto Potion", a2V.COMBAT, "Automatically throws pots");
      ae9.a(new adZ("AP_MODE", "Mode", a6d.COMBOBOX, this, this.B));
      ae9.a(new adZ("AP_HEARTS", "Health", a6d.SLIDER, this, this.z, 0.5D, this::lambda$new$0));
      ae9.a(new adZ("AP_DELAY", "Throw Delay", a6d.SLIDER, this, this.D, 50.0D, this::lambda$new$1));
      ae9.a(new adZ("AP_POTIONS", "Potions", a6d.SELECTBOX, this, this.C));
      ae9.a(new adZ("AP_OVER_POT", "Over Pot", a6d.CHECKBOX, this, this.A));
   }

   private int b() {
      asx.Q();
      int var2 = 5;
      int var3 = 36;
      if(var3 < 45) {
         if(!this.w.thePlayer.bo.getSlot(var3).getHasStack()) {
            var2 = var3 - 36;
         }

         if(this.w.thePlayer.bo.getSlot(var3).getStack().getItem() instanceof ItemPotion) {
            var2 = var3 - 36;
         }

         ++var3;
      }

      return var2;
   }

   private int[] c() {
      asx.Q();
      int[] var2 = new int[]{-1, -1, -1, -1, -1, -1};
      if(this.C.a((Object)"Heal")) {
         var2[0] = 6;
      }

      if(this.C.a((Object)"Regen")) {
         var2[1] = 10;
      }

      if(this.C.a((Object)"Jump")) {
         var2[2] = 8;
      }

      if(this.C.a((Object)"Speed")) {
         var2[3] = 1;
      }

      if(this.C.a((Object)"Fire")) {
         var2[4] = 12;
      }

      return var2;
   }

   private boolean a(int var1) {
      int[] var2 = asx.Q();
      return var1 != 1 && var1 != 8?(var1 != 6 && var1 != 10?(var1 == 12?this.w.thePlayer.isBurning():false):(double)this.w.thePlayer.getHealth() < ((Double)this.z.a()).doubleValue()):this.w.thePlayer.p();
   }

   @agu
   public void a(aG1 var1) {
      int[] var2 = asx.Q();
      if(this.w.thePlayer.onGround && !(this.w.currentScreen instanceof GuiContainerCreative) && (!lS.c() || !lS.a(WL.PRE) && !lS.a(WL.LOBBY) && lS.d() >= 1)) {
         int var3 = 9;
         if(var3 < 45) {
            ItemStack var4 = this.w.thePlayer.bo.getSlot(var3).getStack();
            Item var5 = var4.getItem();
            if(var5 instanceof ItemPotion) {
               ItemPotion var6 = (ItemPotion)var5;
               PotionEffect var7 = (PotionEffect)var6.getEffects(var4).get(0);
               int[] var8 = this.c();
               int var9 = var8.length;
               int var10 = 0;
               if(var10 < var9) {
                  int var11 = var8[var10];
                  if(var7.getPotionID() == var11 && ItemPotion.isSplash(var4.getItemDamage()) && this.a(var11, var7) && this.a(var11) && this.a(var6, var4) && this.y.a((double)((Integer)this.D.a()).intValue())) {
                     if(this.B.a("Edit")) {
                        if(var1.h().equals(EventState.PRE)) {
                           this.x = true;
                           var1.a(90.0F);
                           this.w.thePlayer.a(var3, this.b());
                           this.a((Packet)(new C09PacketHeldItemChange(this.b())));
                        }

                        this.a((Packet)(new C08PacketPlayerBlockPlacement(this.w.thePlayer.bJ.getStackInSlot(this.b()))));
                        this.a((Packet)(new C09PacketHeldItemChange(this.a((Class)avu.class)?((avu)this.b(avu.class)).t():this.w.thePlayer.bJ.currentItem)));
                        this.x = false;
                        this.y.b();
                     }

                     if(this.B.a("Packet") && var1.h().equals(EventState.PRE)) {
                        this.w.thePlayer.a(var3, this.b());
                        this.a((Packet)(new C09PacketHeldItemChange(this.b())));
                        this.a((Packet)(new C03PacketPlayer$C06PacketPlayerPosLook(var1.d(), var1.f(), var1.i(), var1.g(), 90.0F, var1.e())));
                        this.a((Packet)(new C08PacketPlayerBlockPlacement(this.w.thePlayer.bJ.getStackInSlot(this.b()))));
                        this.a((Packet)(new C03PacketPlayer$C06PacketPlayerPosLook(var1.d(), var1.f(), var1.i(), var1.g(), var1.a(), var1.e())));
                        this.a((Packet)(new C09PacketHeldItemChange(this.a((Class)avu.class)?((avu)this.b(avu.class)).t():this.w.thePlayer.bJ.currentItem)));
                        this.y.b();
                     }
                  }

                  ++var10;
               }
            }

            ++var3;
         }

      }
   }

   private boolean a(int var1, PotionEffect var2) {
      int[] var3 = asx.Q();
      return !((Boolean)this.A.a()).booleanValue()?!this.w.thePlayer.isPotionActive(var1):!this.w.thePlayer.isPotionActive(var1) || this.w.thePlayer.t().containsKey(Integer.valueOf(var1)) && ((PotionEffect)this.w.thePlayer.t().get(Integer.valueOf(var1))).getAmplifier() < var2.getAmplifier();
   }

   private boolean a(ItemPotion var1, ItemStack var2) {
      int[] var3 = asx.Q();
      if(var1.getEffects(var2) != null && var1.getEffects(var2).size() == 1) {
         PotionEffect var4 = (PotionEffect)var1.getEffects(var2).get(0);
         int var5 = var4.getPotionID();
         int var6 = var4.getAmplifier();
         int var7 = var4.getDuration();
         int var8 = 9;
         if(var8 < 45) {
            if(this.w.thePlayer.bo.getSlot(var8).getHasStack()) {
               ItemStack var9 = this.w.thePlayer.bo.getSlot(var8).getStack();
               if(var9.getItem() instanceof ItemPotion) {
                  ItemPotion var10 = (ItemPotion)var9.getItem();
                  if(var10.getEffects(var9) != null) {
                     Iterator var11 = var10.getEffects(var9).iterator();
                     if(var11.hasNext()) {
                        PotionEffect var12 = (PotionEffect)var11.next();
                        int var13 = var12.getPotionID();
                        int var14 = var12.getAmplifier();
                        int var15 = var12.getDuration();
                        if(var13 == var5 && ItemPotion.isSplash(var9.getItemDamage())) {
                           if(var14 > var6) {
                              return false;
                           }

                           if(var14 == var6 && var15 > var7) {
                              return false;
                           }
                        }
                     }
                  }
               }
            }

            ++var8;
         }

         return true;
      } else {
         return false;
      }
   }

   @agu
   public void a(WB var1) {
      this.c((String)this.B.a());
   }

   public void n() {
      this.c((String)this.B.a());
   }

   public void c() {
      this.x = false;
   }

   public aEs a() {
      return this.B;
   }

   public boolean d() {
      int[] var1 = asx.Q();
      return this.y() && this.x;
   }

   private Boolean lambda$new$1() {
      int[] var1 = asx.Q();
      return Boolean.valueOf(!this.C.e());
   }

   private Boolean lambda$new$0() {
      int[] var1 = asx.Q();
      return Boolean.valueOf(this.C.a((Object)"Regen") || this.C.a((Object)"Heal"));
   }
}
