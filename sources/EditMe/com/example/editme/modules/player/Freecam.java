package com.example.editme.modules.player;

import com.example.editme.events.PacketEvent;
import com.example.editme.events.PlayerMoveEvent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;

@Module.Info(
   name = "Freecam",
   category = Module.Category.PLAYER,
   description = "Not Black Camera"
)
public class Freecam extends Module {
   @EventHandler
   private Listener sendListener;
   private double posZ;
   private float pitch;
   private Entity ridingEntity;
   private double posY;
   @EventHandler
   private Listener moveListener;
   private EntityOtherPlayerMP clonedPlayer;
   private boolean isRidingEntity;
   private float yaw;
   private double posX;
   private Setting mode;
   @EventHandler
   private Listener pushListener;
   private Setting speed = this.register(SettingsManager.i("Speed", 5));

   protected void onDisable() {
      EntityPlayerSP var1 = mc.field_71439_g;
      if (var1 != null) {
         mc.field_71439_g.func_70080_a(this.posX, this.posY, this.posZ, this.yaw, this.pitch);
         mc.field_71441_e.func_73028_b(-100);
         this.clonedPlayer = null;
         this.posX = this.posY = this.posZ = 0.0D;
         this.pitch = this.yaw = 0.0F;
         mc.field_71439_g.field_71075_bZ.field_75100_b = false;
         mc.field_71439_g.field_71075_bZ.func_75092_a(0.05F);
         mc.field_71439_g.field_70145_X = false;
         mc.field_71439_g.field_70159_w = mc.field_71439_g.field_70181_x = mc.field_71439_g.field_70179_y = 0.0D;
         if (this.isRidingEntity) {
            mc.field_71439_g.func_184205_a(this.ridingEntity, true);
         }
      }

   }

   private void lambda$new$2(PacketEvent.Send var1) {
      if (this.mode.getValue() == Freecam.FreecamMode.PACKETLESS && (var1.getPacket() instanceof CPacketPlayer || var1.getPacket() instanceof CPacketInput)) {
         var1.cancel();
      }

   }

   public Freecam() {
      this.mode = this.register(SettingsManager.e("Mode", Freecam.FreecamMode.PACKETLESS));
      this.moveListener = new Listener(Freecam::lambda$new$0, new Predicate[0]);
      this.pushListener = new Listener(Freecam::lambda$new$1, new Predicate[0]);
      this.sendListener = new Listener(this::lambda$new$2, new Predicate[0]);
   }

   protected void onEnable() {
      if (mc.field_71439_g != null) {
         this.isRidingEntity = mc.field_71439_g.func_184187_bx() != null;
         if (mc.field_71439_g.func_184187_bx() == null) {
            this.posX = mc.field_71439_g.field_70165_t;
            this.posY = mc.field_71439_g.field_70163_u;
            this.posZ = mc.field_71439_g.field_70161_v;
         } else {
            this.ridingEntity = mc.field_71439_g.func_184187_bx();
            mc.field_71439_g.func_184210_p();
         }

         this.pitch = mc.field_71439_g.field_70125_A;
         this.yaw = mc.field_71439_g.field_70177_z;
         this.clonedPlayer = new EntityOtherPlayerMP(mc.field_71441_e, mc.func_110432_I().func_148256_e());
         this.clonedPlayer.func_82149_j(mc.field_71439_g);
         this.clonedPlayer.field_70759_as = mc.field_71439_g.field_70759_as;
         mc.field_71441_e.func_73027_a(-100, this.clonedPlayer);
         mc.field_71439_g.field_71075_bZ.field_75100_b = true;
         mc.field_71439_g.field_71075_bZ.func_75092_a((float)(Integer)this.speed.getValue() / 100.0F);
         mc.field_71439_g.field_70145_X = true;
      }

   }

   private static void lambda$new$1(PlayerSPPushOutOfBlocksEvent var0) {
      var0.setCanceled(true);
   }

   private static void lambda$new$0(PlayerMoveEvent var0) {
      mc.field_71439_g.field_70145_X = true;
   }

   public void onUpdate() {
      mc.field_71439_g.field_71075_bZ.field_75100_b = true;
      mc.field_71439_g.field_71075_bZ.func_75092_a((float)(Integer)this.speed.getValue() / 100.0F);
      mc.field_71439_g.field_70145_X = true;
      mc.field_71439_g.field_70122_E = false;
      mc.field_71439_g.field_70143_R = 0.0F;
   }

   public static enum FreecamMode {
      PACKETLESS;

      private static final Freecam.FreecamMode[] $VALUES = new Freecam.FreecamMode[]{PACKETLESS, PACKET};
      PACKET;
   }
}
