/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package via.fixes;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import mpp.venusfr.utils.client.IMinecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;
import via.ViaLoadingBase;

public class AttackOrder
implements IMinecraft {
    public static void sendConditionalSwing(RayTraceResult rayTraceResult, Hand hand) {
        if (rayTraceResult != null && rayTraceResult.getType() != RayTraceResult.Type.ENTITY) {
            AttackOrder.mc.player.swingArm(hand);
        }
    }

    public static void sendFixedAttack(PlayerEntity playerEntity, Entity entity2, Hand hand) {
        if (ViaLoadingBase.getInstance().getTargetVersion().isOlderThanOrEqualTo(ProtocolVersion.v1_8)) {
            AttackOrder.mc.player.swingArm(hand);
            AttackOrder.mc.playerController.attackEntity(playerEntity, entity2);
        } else {
            AttackOrder.mc.playerController.attackEntity(playerEntity, entity2);
            AttackOrder.mc.player.swingArm(hand);
        }
    }
}

