package de.florianmichael.viamcp.fixes;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;

public class AttackOrder {
   private static final Minecraft mc = Minecraft.getMinecraft();

   public static void sendConditionalSwing(MovingObjectPosition mop) {
      if (mop != null && mop.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY) {
         mc.thePlayer.swingItem();
      }
   }

   public static void sendFixedAttack(EntityPlayer entityIn, Entity target) {
      if (ViaLoadingBase.getInstance().getTargetVersion().isOlderThanOrEqualTo(ProtocolVersion.v1_8)) {
         mc.thePlayer.swingItem();
         mc.playerController.attackEntity(entityIn, target);
      } else {
         mc.playerController.attackEntity(entityIn, target);
         mc.thePlayer.swingItem();
      }
   }

   public static void sendFixedAttackEvent(EntityPlayerSP entityIn, Entity target) {
      if (ViaLoadingBase.getInstance().getTargetVersion().isOlderThanOrEqualTo(ProtocolVersion.v1_8)) {
         mc.thePlayer.swingItem();
         mc.playerController.attackEntityEvent(entityIn, target);
      } else {
         mc.playerController.attackEntityEvent(entityIn, target);
         mc.thePlayer.swingItem();
      }
   }

   public static void sendLegitFixedKillAuraAttack(EntityPlayerSP entityIn, Entity target) {
      if (mc.leftClickCounter <= 0) {
         sendConditionalSwing(mc.objectMouseOver);
         sendFixedAttackEvent(entityIn, target);
      }
   }
}
