package dev.stephen.nexus.anticheat.check.impl.combat;

import dev.stephen.nexus.anticheat.check.Check;
import dev.stephen.nexus.event.types.TransferOrder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.util.Hand;

public class AutoBlockCheck extends Check {

    public AutoBlockCheck(PlayerEntity player) {
        super(player, "AutoBlock");
    }

    @Override
    public void onPacket(Packet<?> packet, TransferOrder transferOrder) {
        if (getPlayer() == null) {
            return;
        }

        if (transferOrder == TransferOrder.SEND) {
            if (shouldCheckSelf() && getPlayer() == mc.player) {
                if (packet instanceof HandSwingC2SPacket handSwingC2SPacket) {
                    if (handSwingC2SPacket.getHand() != Hand.MAIN_HAND) {
                        return;
                    }
                    if (canBlock(getPlayer())) {
                        if (getPlayer().getItemUseTime() > 5) {
                            flag("impossible hit");
                        }
                    }
                }
            }
        } else {
            if (packet instanceof EntityAnimationS2CPacket entityAnimationS2CPacket) {
                if (entityAnimationS2CPacket.getId() != getPlayer().getId()) {
                    return;
                }
                if (entityAnimationS2CPacket.getAnimationId() != 0) {
                    return;
                }
                if (canBlock(getPlayer())) {
                    if (getPlayer().getItemUseTime() > 5) {
                        flag("impossible hit");
                    }
                }
            }
        }
        super.onPacket(packet, transferOrder);
    }

    private boolean canBlock(PlayerEntity player) {
        return player.getOffHandStack().getItem() instanceof ShieldItem && player.getMainHandStack().getItem() instanceof SwordItem;
    }
}
