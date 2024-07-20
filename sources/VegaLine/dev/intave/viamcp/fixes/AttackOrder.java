/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package dev.intave.viamcp.fixes;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import dev.intave.vialoadingbase.ViaLoadingBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;

public class AttackOrder {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void sendConditionalSwing(RayTraceResult ray, EnumHand enumHand) {
        if (ray != null && ray.typeOfHit != RayTraceResult.Type.ENTITY) {
            Minecraft.player.swingArm(enumHand);
        }
    }

    public static void sendFixedAttack(EntityPlayer entityIn, Entity target, EnumHand enumHand) {
        if (ViaLoadingBase.getInstance().getTargetVersion().isOlderThanOrEqualTo(ProtocolVersion.v1_8)) {
            Minecraft.player.swingArm(enumHand);
            AttackOrder.mc.playerController.attackEntity(entityIn, target);
        } else {
            AttackOrder.mc.playerController.attackEntity(entityIn, target);
            Minecraft.player.swingArm(enumHand);
        }
    }
}

