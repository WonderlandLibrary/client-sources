package dev.stephen.nexus.anticheat.check.impl.combat;

import dev.stephen.nexus.anticheat.check.Check;
import dev.stephen.nexus.anticheat.utils.MathUtil;
import dev.stephen.nexus.event.types.TransferOrder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.util.Hand;

import java.util.ArrayDeque;

public class AutoClickerCCheck extends Check {
    private final ArrayDeque<Integer> samples = new ArrayDeque<>();
    private int ticks, buffer;

    public AutoClickerCCheck(PlayerEntity player) {
        super(player, "AutoClickerC");
        ticks = buffer = 0;
        samples.clear();
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
                    handleClick();
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
                handleClick();
            }
        }
        super.onPacket(packet, transferOrder);
    }

    @Override
    public void onTick() {
        if (getPlayer() == null) {
            return;
        }
        ++ticks;
        super.onTick();
    }

    private void handleClick() {
        if (ticks < 4) {
            samples.add(ticks);
        }

        if (samples.size() == 50) {

            final double cps = MathUtil.getCps(samples);
            final double difference = Math.abs(Math.round(cps) - cps);

            if (difference < 0.001) {
                if ((buffer += 10) > 25) {
                    flag("rounded");
                }
            } else {
                buffer = Math.max(buffer - 8, 0);
            }

            samples.clear();
        }

        ticks = 0;
    }
}
