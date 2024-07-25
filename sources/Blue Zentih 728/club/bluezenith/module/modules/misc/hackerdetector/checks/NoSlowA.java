package club.bluezenith.module.modules.misc.hackerdetector.checks;

import club.bluezenith.module.modules.misc.hackerdetector.Check;
import club.bluezenith.module.modules.misc.hackerdetector.PlayerInfo;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S14PacketEntity.S15PacketEntityRelMove;
import net.minecraft.network.play.server.S14PacketEntity.S17PacketEntityLookMove;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class NoSlowA extends Check {

    public NoSlowA(PlayerInfo playerInfo) {
        super(playerInfo, "NoSlow", 'A');
        maxViolations = 10;
        minViolations = 0;
    }

    @Override
    public void process(Packet<?> packet) {
        if(packet instanceof S15PacketEntityRelMove || packet instanceof S17PacketEntityLookMove) {
            if(playerInfo.getDelta() > 0 && playerInfo.updatePlayerEntity().isUsingItem()) {

                final PotionEffect speedEffect = playerInfo.getPlayerEntity().getActivePotionEffects()
                        .stream().filter(effect -> effect.getPotionID() == Potion.moveSpeed.id)
                        .findFirst().orElse(null);

                double amplifier = 1;

                if(speedEffect != null)
                    amplifier += (1 + speedEffect.getAmplifier()) * 0.2;

                final double deltaCap = 0.19 * amplifier;

                if(playerInfo.getDelta() > deltaCap) {
                    violationBuffer += (0.02);
                    if(violationBuffer > 0.06) {
                        flag(4 * (playerInfo.getDelta() - deltaCap), "cap: %s, diff: %s", deltaCap, playerInfo.getDelta() - deltaCap);
                    }
                } else checkPassed(0.8, 0.04);
            } else {
                checkPassed(0.4, 0.015);
            }
        }
    }
}
