package dev.stephen.nexus.anticheat.check.impl.combat;

import dev.stephen.nexus.anticheat.check.Check;
import dev.stephen.nexus.event.types.TransferOrder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.util.Hand;

import java.util.LinkedList;
import java.util.List;

public class AutoClickerACheck extends Check {

    private static final int MAX_CLICK_HISTORY = 10;  // Track the last 10 clicks
    private static final long MIN_DELAY_THRESHOLD = 50;  // Minimum delay between clicks to avoid false positives (milliseconds)

    private final List<Long> clickTimestamps = new LinkedList<>();

    public AutoClickerACheck(PlayerEntity player) {
        super(player, "AutoClickerA");
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

    private void handleClick() {
        long currentTime = System.currentTimeMillis();

        if (!clickTimestamps.isEmpty() && (currentTime - clickTimestamps.get(clickTimestamps.size() - 1) < MIN_DELAY_THRESHOLD)) {
            // Ignore clicks that happen too fast to avoid false positives
            return;
        }

        // Add current click timestamp
        clickTimestamps.add(currentTime);

        // Keep only the last 10 timestamps
        if (clickTimestamps.size() > MAX_CLICK_HISTORY) {
            clickTimestamps.remove(0);
        }

        // If we have enough timestamps, check for consistent delays
        if (clickTimestamps.size() == MAX_CLICK_HISTORY) {
            checkForExactDelays();
        }
    }

    private void checkForExactDelays() {
        long firstInterval = clickTimestamps.get(1) - clickTimestamps.get(0);
        boolean exactIntervals = true;

        // Check each interval and ensure all are exactly the same as the first one
        for (int i = 1; i < clickTimestamps.size() - 1; i++) {
            long interval = clickTimestamps.get(i + 1) - clickTimestamps.get(i);
            if (interval != firstInterval) {
                exactIntervals = false;
                break;
            }
        }

        if (exactIntervals) {
            flag("exactly identical intervals");
        }
    }
}
