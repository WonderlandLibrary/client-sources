package cc.slack.features.modules.impl.ghost;

import cc.slack.events.State;
import cc.slack.events.impl.network.DisconnectEvent;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.other.PrintUtil;
import cc.slack.utils.player.PlayerUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.Vec3;

@ModuleInfo(
   name = "PearlAntiVoid",
   category = Category.GHOST
)
public class PearlAntiVoid extends Module {
   private int overVoidTicks;
   private Vec3 position;
   private Vec3 motion;
   private boolean wasVoid;
   private boolean setBack;
   boolean shouldStuck;
   double x;
   double y;
   double z;
   boolean wait;
   private final NumberValue<Integer> fall = new NumberValue("Min fall distance", 5, 0, 10, 1);

   public PearlAntiVoid() {
      this.addSettings(new Value[]{this.fall});
   }

   public void onDisable() {
      mc.getTimer().timerSpeed = 1.0F;
      mc.getPlayer().isDead = false;
   }

   @Listen
   public void onDisconnect(DisconnectEvent event) {
      PrintUtil.print("XD");
   }

   @Listen
   public void onPacket(PacketEvent e) {
      Packet<?> p = e.getPacket();
      if (e.getDirection() == PacketDirection.OUTGOING) {
         if (!mc.getPlayer().onGround && this.shouldStuck && p instanceof C03PacketPlayer && !(p instanceof C03PacketPlayer.C05PacketPlayerLook) && !(p instanceof C03PacketPlayer.C06PacketPlayerPosLook)) {
            e.cancel();
         }

         if (p instanceof C08PacketPlayerBlockPlacement && this.wait) {
            this.shouldStuck = false;
            mc.getTimer().timerSpeed = 0.2F;
            this.wait = false;
         }
      }

      if (e.getDirection() == PacketDirection.INCOMING && p instanceof S08PacketPlayerPosLook) {
         S08PacketPlayerPosLook wrapper = (S08PacketPlayerPosLook)p;
         this.x = wrapper.getX();
         this.y = wrapper.getY();
         this.z = wrapper.getZ();
         mc.getTimer().timerSpeed = 0.2F;
      }

   }

   @Listen
   public void onMotion(MotionEvent e) {
      try {
         if (e.getState() != State.POST) {
            if (mc.getPlayer().getHeldItem() == null) {
               mc.getTimer().timerSpeed = 1.0F;
            }

            if (mc.getPlayer().getHeldItem().getItem() instanceof ItemEnderPearl) {
               this.wait = true;
            }

            if (this.shouldStuck && !mc.getPlayer().onGround) {
               mc.getPlayer().motionX = 0.0D;
               mc.getPlayer().motionY = 0.0D;
               mc.getPlayer().motionZ = 0.0D;
               mc.getPlayer().setPositionAndRotation(this.x, this.y, this.z, mc.getPlayer().rotationYaw, mc.getPlayer().rotationPitch);
            }

            boolean overVoid = !mc.getPlayer().onGround && !PlayerUtil.isBlockUnderP(30);
            if (!overVoid) {
               this.shouldStuck = false;
               this.x = mc.getPlayer().posX;
               this.y = mc.getPlayer().posY;
               this.z = mc.getPlayer().posZ;
               mc.getTimer().timerSpeed = 1.0F;
            }

            if (overVoid) {
               ++this.overVoidTicks;
            } else if (mc.getPlayer().onGround) {
               this.overVoidTicks = 0;
            }

            if (overVoid && this.position != null && this.motion != null && (double)this.overVoidTicks < 30.0D + (double)(Integer)this.fall.getValue() * 20.0D) {
               if (!this.setBack) {
                  this.wasVoid = true;
                  if (mc.getPlayer().fallDistance > (float)(Integer)this.fall.getValue()) {
                     mc.getPlayer().fallDistance = 0.0F;
                     this.setBack = true;
                     this.shouldStuck = true;
                     this.x = mc.getPlayer().posX;
                     this.y = mc.getPlayer().posY;
                     this.z = mc.getPlayer().posZ;
                  }
               }
            } else {
               if (this.shouldStuck) {
                  this.toggle();
               }

               this.shouldStuck = false;
               mc.getTimer().timerSpeed = 1.0F;
               this.setBack = false;
               if (this.wasVoid) {
                  this.wasVoid = false;
               }

               this.motion = new Vec3(mc.getPlayer().motionX, mc.getPlayer().motionY, mc.getPlayer().motionZ);
               this.position = new Vec3(mc.getPlayer().posX, mc.getPlayer().posY, mc.getPlayer().posZ);
            }
         }
      } catch (NullPointerException var3) {
      }

   }
}
