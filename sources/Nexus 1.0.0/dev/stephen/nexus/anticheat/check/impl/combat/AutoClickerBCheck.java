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

public class AutoClickerBCheck extends Check {
    public AutoClickerBCheck(PlayerEntity player) {
        super(player, "AutoClickerB");
        lastDev = lastKurt = lastSkew = ticks = buffer = 0;
        samples.clear();
    }

    private int ticks;
    private double lastDev, lastSkew, lastKurt;
    private ArrayDeque<Integer> samples = new ArrayDeque<>();
    private int buffer;

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

        if (samples.size() == 120) {
            final double deviation = MathUtil.getStandardDeviation(samples);
            final double skewness = MathUtil.getSkewness(samples);
            final double kurtosis = MathUtil.getKurtosis(samples);

            final double deltaDeviation = Math.abs(deviation - lastDev);
            final double deltaSkewness = Math.abs(skewness - lastSkew);
            final double deltaKurtosis = Math.abs(kurtosis - lastKurt);


            if (deltaDeviation < 0.01 || deltaSkewness < 0.01 || deltaKurtosis < 0.01) {
                if ((buffer += 10) > 50) {
                    flag("statistics");
                }
            } else {
                buffer = Math.max(buffer - 8, 0);
            }

            lastDev = deviation;
            lastSkew = skewness;
            lastKurt = kurtosis;

            samples.clear();
        }

        ticks = 0;
    }

}
