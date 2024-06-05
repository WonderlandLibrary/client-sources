package net;

import cc.novoline.events.events.EventState;
import cc.novoline.events.events.PacketDirection;
import net.UW;
import net.VN;
import net.a2V;
import net.a6d;
import net.aEl;
import net.aG1;
import net.aL_;
import net.adZ;
import net.ae9;
import net.agu;
import net.ap7;
import net.ap9;
import net.apo;
import net.as0;
import net.as_;
import net.axu;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

public final class avq extends as0 {
   private EntityOtherPlayerMP x;
   @VN("freecam-speed")
   private final aEl y = (aEl)((aEl)axu.a(Float.valueOf(4.0F)).d(Float.valueOf(1.0F))).c(Float.valueOf(5.0F));
   private static int[] z;

   public avq(UW var1) {
      super(var1, "Freecam", "Freecam", 0, a2V.MOVEMENT, "Ghost Walking");
      ae9.a(new adZ("FC_SPEED", "Speed", a6d.SLIDER, this, this.y, 1.0D));
   }

   public void c() {
      int[] var1 = a();
      if(this.x != null) {
         this.w.thePlayer.setPositionAndRotation(this.x.posX, this.x.posY, this.x.posZ, this.x.rotationYaw, this.x.rotationPitch);
         this.w.theWorld.d(this.x.getEntityId());
      }

      this.w.thePlayer.noClip = false;
   }

   public void n() {
      int[] var1 = a();
      if(this.w.thePlayer != null) {
         this.c(as_.class);
         this.x = new EntityOtherPlayerMP(this.w.theWorld, this.w.thePlayer.getGameProfile());
         this.x.bJ = this.w.thePlayer.bJ;
         this.x.bo = this.w.thePlayer.bo;
         this.x.setPositionAndRotation(this.w.thePlayer.posX, this.w.thePlayer.posY, this.w.thePlayer.posZ, this.w.thePlayer.rotationYaw, this.w.thePlayer.rotationPitch);
         this.x.rotationYawHead = this.w.thePlayer.rotationYawHead;
         this.x.a(this.w.thePlayer.getUniqueID());
         this.w.theWorld.addEntityToWorld(this.x.getEntityId(), this.x);
      }
   }

   @agu
   public void a(aG1 var1) {
      if(var1.h().equals(EventState.PRE)) {
         this.w.thePlayer.noClip = true;
      }

   }

   @agu
   public void b(ap9 var1) {
      int[] var2 = a();
      if(var1.a().equals(PacketDirection.OUTGOING) && var1.d() instanceof C03PacketPlayer) {
         var1.setCancelled(true);
      }

   }

   @agu
   public void a(apo var1) {
      var1.a((AxisAlignedBB)null);
   }

   @agu
   public void a(aL_ var1) {
      a();
      float var3 = ((Float)this.y.a()).floatValue();
      if(this.w.thePlayer.ap().d()) {
         var1.a(this.w.thePlayer.motionY = (double)var3);
      }

      if(this.w.thePlayer.ap().b()) {
         var1.a(this.w.thePlayer.motionY = (double)(-var3));
      }

      var1.a(this.w.thePlayer.motionY = 0.0D);
      var1.c((double)var3);
   }

   @agu
   public void a(ap7 var1) {
      var1.setCancelled(true);
   }

   public EntityOtherPlayerMP b() {
      return this.x;
   }

   public static void a(int[] var0) {
      z = var0;
   }

   public static int[] a() {
      return z;
   }

   static {
      a((int[])null);
   }
}
