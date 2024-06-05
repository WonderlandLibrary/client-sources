package net;

import cc.novoline.events.events.PacketDirection;
import java.util.ArrayList;
import java.util.function.Consumer;
import net.UW;
import net.VN;
import net.a2V;
import net.a6d;
import net.aEu;
import net.adZ;
import net.ae9;
import net.agu;
import net.ap9;
import net.as0;
import net.avq;
import net.axu;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public final class as_ extends as0 {
   private EntityOtherPlayerMP y;
   private final ArrayList x = new ArrayList();
   @VN("lagback-check")
   private final aEu A = axu.g();
   private static int z;

   public as_(UW var1) {
      super(var1, "Blink", a2V.EXPLOITS, "there was a cringe description but I removed it -gast");
      ae9.a(new adZ("BLINK_LB", "Lagback check", a6d.CHECKBOX, this, this.A));
   }

   public void n() {
      this.c(avq.class);
      this.y = new EntityOtherPlayerMP(this.w.theWorld, this.w.thePlayer.getGameProfile());
      this.y.copyLocationAndAnglesFrom(this.w.thePlayer);
      this.y.setRotationYawHead(this.w.thePlayer.rotationYawHead);
      this.y.bJ = this.w.thePlayer.bJ;
      this.w.theWorld.addEntityToWorld(this.y.getEntityId(), this.y);
   }

   public void c() {
      a();
      this.w.theWorld.d(this.y.getEntityId());
      if(!this.x.isEmpty()) {
         this.x.forEach(this::b);
         this.x.clear();
      }

   }

   @agu
   private void b(ap9 var1) {
      int var2 = a();
      if(var1.a().equals(PacketDirection.OUTGOING)) {
         if(var1.d() instanceof C03PacketPlayer) {
            C03PacketPlayer var3 = (C03PacketPlayer)var1.d();
            if(var3.isMoving()) {
               this.x.add(var1.d());
               var1.setCancelled(true);
            }
         }

         if(var1.d() instanceof C0APacketAnimation || var1.d() instanceof C02PacketUseEntity || var1.d() instanceof C08PacketPlayerBlockPlacement) {
            var1.setCancelled(true);
         }
      }

   }

   @agu
   public void c(ap9 var1) {
      int var2 = a();
      if(var1.a().equals(PacketDirection.INCOMING) && ((Boolean)this.A.a()).booleanValue() && var1.d() instanceof S08PacketPlayerPosLook) {
         this.c(this.getClass());
      }

   }

   public EntityOtherPlayerMP c() {
      return this.y;
   }

   public static void b(int var0) {
      z = var0;
   }

   public static int a() {
      return z;
   }

   public static int b() {
      int var0 = a();
      return 105;
   }

   static {
      b(69);
   }
}
