package net;

import cc.novoline.events.events.EventState;
import cc.novoline.events.events.PacketDirection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import net.UW;
import net.VN;
import net.WB;
import net.a2V;
import net.a6d;
import net.aEs;
import net.aG1;
import net.adZ;
import net.ae9;
import net.agu;
import net.ap9;
import net.as0;
import net.as_;
import net.avB;
import net.axu;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition;
import net.minecraft.network.play.client.C0APacketAnimation;

public final class ask extends as0 {
   private double z;
   private double A;
   private List x = new CopyOnWriteArrayList();
   @VN("mode")
   private aEs y = axu.a("Packet").a(new String[]{"Spoof", "Packet", "Verus"});

   public ask(UW var1) {
      super(var1, "NoFall", "No Fall", a2V.EXPLOITS, "Prevents you from taking fall damage");
      ae9.a(new adZ("NF_MODE", "Mode", a6d.COMBOBOX, this, this.y));
   }

   @agu
   public void b(ap9 var1) {
      int var2 = as_.b();
      if(var1.a().equals(PacketDirection.OUTGOING)) {
         if(this.y.a("Verus")) {
            if(this.a((Class)avB.class) || !(var1.d() instanceof C03PacketPlayer)) {
               return;
            }

            C03PacketPlayer var3 = (C03PacketPlayer)var1.d();
            if(var3.isMoving() && this.w.thePlayer.fallDistance > 3.0F && this.w.thePlayer.posY > 0.0D && this.w.thePlayer.motionY != 0.0D) {
               this.x.add(var3);
               var1.setCancelled(true);
               if(this.x.size() > 4) {
                  this.x.forEach(this::a);
                  this.x.clear();
               }
            }
         }

         if(this.y.a("Packet") && (double)this.w.thePlayer.fallDistance > 2.2D && this.w.thePlayer.motionY != 0.0D && this.w.thePlayer.posY > 0.0D) {
            if(var1.d() instanceof C0APacketAnimation || var1.d() instanceof C02PacketUseEntity) {
               var1.setCancelled(true);
            }

            if(var1.d() instanceof C03PacketPlayer) {
               C03PacketPlayer var4 = (C03PacketPlayer)var1.d();
               if(var4.isMoving() && var4.getRotating()) {
                  this.a((Packet)(new C03PacketPlayer$C04PacketPlayerPosition(var4.getPositionX(), var4.getPositionY(), var4.getPositionZ(), var4.isOnGround())));
                  var1.setCancelled(true);
               }
            }
         }
      }

   }

   @agu
   public void a(aG1 var1) {
      int var2 = as_.b();
      if(var1.h().equals(EventState.PRE)) {
         if(this.w.thePlayer.fallDistance > 3.0F && this.w.thePlayer.motionY != 0.0D && this.w.thePlayer.posY > 0.0D) {
            if(this.A + (double)this.w.thePlayer.fallDistance - this.z > 3.0D || this.w.thePlayer.fallDistance > 3.0F && this.A == 0.0D) {
               if(this.y.a("Packet")) {
                  this.a((Packet)(new C03PacketPlayer(true)));
               }

               if(this.y.a("Spoof")) {
                  var1.c(true);
               }

               if(this.y.a("Verus") && !this.a((Class)avB.class)) {
                  this.x.add(new C03PacketPlayer(true));
               }

               this.A = 0.0D;
            }

            this.A += (double)this.w.thePlayer.fallDistance - this.z;
            this.z = (double)this.w.thePlayer.fallDistance;
            if(this.w.thePlayer.fallDistance <= 120.0F) {
               return;
            }

            this.w.thePlayer.motionY += 1.0E-4D * (double)this.w.thePlayer.fallDistance;
         }

         this.A = 0.0D;
         this.z = 0.0D;
         if(this.y.a("Verus") && !this.a((Class)avB.class) && !this.x.isEmpty()) {
            this.x.forEach(this::a);
            this.x.clear();
         }
      }

   }

   @agu
   public void a(WB var1) {
      this.c((String)this.y.a());
   }

   public void n() {
      this.c((String)this.y.a());
   }

   public aEs a() {
      return this.y;
   }
}
