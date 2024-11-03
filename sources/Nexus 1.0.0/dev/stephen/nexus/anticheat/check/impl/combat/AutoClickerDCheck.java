package dev.stephen.nexus.anticheat.check.impl.combat;

import dev.stephen.nexus.anticheat.check.Check;
import dev.stephen.nexus.anticheat.utils.MathUtil;
import dev.stephen.nexus.event.types.TransferOrder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;

import java.util.ArrayDeque;
import java.util.List;

public class AutoClickerDCheck extends Check {
    private final ArrayDeque<Integer> samples = new ArrayDeque<>();
    private int ticks, buffer;

    public AutoClickerDCheck(PlayerEntity player) {
        super(player, "AutoClickerD");
        samples.clear();
        ticks = buffer = 0;
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

        if (samples.size() == 20) {
            final Pair<List<Double>, List<Double>> outlierPair = MathUtil.getOutliers(samples);

            final int outliers = outlierPair.getLeft().size() + outlierPair.getRight().size();
            final int duplicates = (int) (samples.size() - samples.stream().distinct().count());

            if (outliers < 2 && duplicates > 16) {
                if ((buffer += 10) > 50) {
                    flag("consistency");
                }
            } else {
                buffer = Math.max(buffer - 8, 0);
            }
            samples.clear();
        }

        ticks = 0;
    }
}
