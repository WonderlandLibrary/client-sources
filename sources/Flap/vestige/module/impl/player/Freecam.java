package vestige.module.impl.player;

import java.awt.Color;
import java.awt.event.MouseEvent;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;
import vestige.event.Listener;
import vestige.event.impl.PacketSendEvent;
import vestige.event.impl.Render2DEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.util.player.PlayerUtil;
import vestige.util.render.RenderUtils2;
import vestige.util.util.Utils;

public class Freecam extends Module {
   public static EntityOtherPlayerMP freeEntity = null;
   private int[] lcc = new int[]{Integer.MAX_VALUE, 0};
   private float[] sAng = new float[]{0.0F, 0.0F};
   private final IntegerSetting speed = new IntegerSetting("Speed", 3, 1, 10, 1);
   private final BooleanSetting damage = new BooleanSetting("Disable on Damage", true);
   private final BooleanSetting digging = new BooleanSetting("Allow Digging", false);
   private final BooleanSetting interact = new BooleanSetting("Allow Interact", false);
   private final BooleanSetting place = new BooleanSetting("Allow Placing", false);
   private final BooleanSetting arm = new BooleanSetting("Show Arm", false);

   public Freecam() {
      super("Freecam", Category.ULTILITY);
      this.addSettings(new AbstractSetting[]{this.speed, this.damage, this.digging, this.interact, this.place, this.arm});
   }

   public void onEnable() {
      if (!mc.thePlayer.onGround) {
         this.toggle();
      } else {
         freeEntity = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
         freeEntity.copyLocationAndAnglesFrom(mc.thePlayer);
         this.sAng[0] = freeEntity.rotationYawHead = mc.thePlayer.rotationYawHead;
         this.sAng[1] = mc.thePlayer.rotationPitch;
         freeEntity.setVelocity(0.0D, 0.0D, 0.0D);
         freeEntity.setInvisible(true);
         mc.theWorld.addEntityToWorld(-8008, freeEntity);
         mc.setRenderViewEntity(freeEntity);
      }

   }

   public boolean onDisable() {
      if (freeEntity != null) {
         mc.setRenderViewEntity(mc.thePlayer);
         mc.thePlayer.rotationYaw = mc.thePlayer.rotationYawHead = this.sAng[0];
         mc.thePlayer.rotationPitch = this.sAng[1];
         mc.theWorld.removeEntity(freeEntity);
         freeEntity = null;
      }

      this.lcc = new int[]{Integer.MAX_VALUE, 0};
      int x = mc.thePlayer.chunkCoordX;
      int z = mc.thePlayer.chunkCoordZ;

      for(int x2 = -1; x2 <= 1; ++x2) {
         for(int z2 = -1; z2 <= 1; ++z2) {
            int a = x + x2;
            int b = z + z2;
            mc.theWorld.markBlockRangeForRenderUpdate(a * 16, 0, b * 16, a * 16 + 15, 256, b * 16 + 15);
         }
      }

      return false;
   }

   @Listener
   public void onUpdate() {
      if (this.damage.isEnabled() && mc.thePlayer.hurtTime != 0) {
         this.toggle();
      } else {
         mc.thePlayer.setSprinting(false);
         mc.thePlayer.moveForward = 0.0F;
         mc.thePlayer.moveStrafing = 0.0F;
         freeEntity.rotationYaw = freeEntity.rotationYawHead = mc.thePlayer.rotationYaw;
         freeEntity.rotationPitch = mc.thePlayer.rotationPitch;
         double s = 0.215D * (double)this.speed.getValue();
         EntityOtherPlayerMP var10000;
         double rad;
         double dx;
         double dz;
         if (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
            rad = (double)freeEntity.rotationYawHead * 0.017453292519943295D;
            dx = -1.0D * Math.sin(rad) * s;
            dz = Math.cos(rad) * s;
            var10000 = freeEntity;
            var10000.posX += dx;
            var10000 = freeEntity;
            var10000.posZ += dz;
         }

         if (Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
            rad = (double)freeEntity.rotationYawHead * 0.017453292519943295D;
            dx = -1.0D * Math.sin(rad) * s;
            dz = Math.cos(rad) * s;
            var10000 = freeEntity;
            var10000.posX -= dx;
            var10000 = freeEntity;
            var10000.posZ -= dz;
         }

         if (Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode())) {
            rad = (double)(freeEntity.rotationYawHead - 90.0F) * 0.017453292519943295D;
            dx = -1.0D * Math.sin(rad) * s;
            dz = Math.cos(rad) * s;
            var10000 = freeEntity;
            var10000.posX += dx;
            var10000 = freeEntity;
            var10000.posZ += dz;
         }

         if (Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode())) {
            rad = (double)(freeEntity.rotationYawHead + 90.0F) * 0.017453292519943295D;
            dx = -1.0D * Math.sin(rad) * s;
            dz = Math.cos(rad) * s;
            var10000 = freeEntity;
            var10000.posX += dx;
            var10000 = freeEntity;
            var10000.posZ += dz;
         }

         if (Utils.jumpDown()) {
            var10000 = freeEntity;
            var10000.posY += 0.93D * s;
         }

         if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
            var10000 = freeEntity;
            var10000.posY -= 0.93D * s;
         }

         mc.thePlayer.setSneaking(false);
         if (this.lcc[0] != Integer.MAX_VALUE && (this.lcc[0] != freeEntity.chunkCoordX || this.lcc[1] != freeEntity.chunkCoordZ)) {
            int x = freeEntity.chunkCoordX;
            int z = freeEntity.chunkCoordZ;
            mc.theWorld.markBlockRangeForRenderUpdate(x * 16, 0, z * 16, x * 16 + 15, 256, z * 16 + 15);
         }

         this.lcc[0] = freeEntity.chunkCoordX;
         this.lcc[1] = freeEntity.chunkCoordZ;
      }

   }

   @Listener
   public void re(Render2DEvent e) {
      if (PlayerUtil.nullCheck()) {
         if (!this.arm.isEnabled()) {
            mc.thePlayer.renderArmPitch = mc.thePlayer.prevRenderArmPitch = 700.0F;
         }

         RenderUtils2.renderEntity(mc.thePlayer, 1, 0.0D, 0.0D, Color.green.getRGB(), false);
         RenderUtils2.renderEntity(mc.thePlayer, 2, 0.0D, 0.0D, Color.green.getRGB(), false);
      }

   }

   @Listener
   public void m(MouseEvent e) {
      if (PlayerUtil.nullCheck()) {
         if ((e.getButton() != 0 || this.digging.isEnabled()) && (e.getButton() != 1 || this.place.isEnabled()) || mc.objectMouseOver == null || mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
            if (this.interact.isEnabled() || e.getButton() != 1 && e.getButton() != 0 || mc.objectMouseOver == null || mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY) {
               ;
            }
         }
      }
   }

   @Listener
   public void onSendPacket(PacketSendEvent e) {
      if (PlayerUtil.nullCheck()) {
         if (!this.digging.isEnabled() && e.getPacket() instanceof C07PacketPlayerDigging) {
            e.setCancelled(true);
         }

         if (!this.place.isEnabled() && e.getPacket() instanceof C08PacketPlayerBlockPlacement) {
            e.setCancelled(true);
         }

         if (!this.interact.isEnabled() && e.getPacket() instanceof C02PacketUseEntity) {
            e.setCancelled(true);
         }

      }
   }
}
