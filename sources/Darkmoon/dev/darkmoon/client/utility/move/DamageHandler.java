package dev.darkmoon.client.utility.move;

import dev.darkmoon.client.event.packet.EventReceivePacket;
import dev.darkmoon.client.event.player.EventDamage;
import dev.darkmoon.client.utility.Utility;
import dev.darkmoon.client.utility.misc.TimerHelper;
import net.minecraft.init.MobEffects;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketExplosion;

public class DamageHandler implements Utility {
    private final TimerHelper timerHelper = new TimerHelper();
    private boolean normalDamage;
    private boolean fallDamage;
    private boolean explosionDamage;
    private boolean arrowDamage;
    private boolean pearlDamage;

    public void processPacket(EventReceivePacket event) {
        if (!this.isBadEffectActive()) {
            if (event.getPacket() instanceof SPacketExplosion) {
                this.explosionDamage = true;
            }

            boolean damage = this.fallDamage || this.arrowDamage || this.explosionDamage || this.pearlDamage;
            if (!damage) {
                Packet<?> packet = event.getPacket();
                if (packet instanceof SPacketEntityStatus) {
                    SPacketEntityStatus packetEntityStatus = (SPacketEntityStatus)packet;
                    if (packetEntityStatus.getOpCode() == 2 && packetEntityStatus.getEntity(mc.world) == mc.player) {
                        this.normalDamage = true;
                    }
                }
            } else if (mc.player.hurtTime > 0) {
                this.normalDamage = false;
                this.reset();
            }

        }
    }

    public boolean isReachedNormal(float time) {
        if (this.normalDamage) {
            if (this.timerHelper.hasReached(time)) {
                this.normalDamage = false;
                this.timerHelper.reset();
                return true;
            }
        } else {
            this.timerHelper.reset();
        }

        return false;
    }

    public void processDamage(EventDamage eventDamage) {
        switch (eventDamage.getDamageType()) {
            case FALL:
                this.fallDamage = true;
                break;
            case ARROW:
                this.arrowDamage = true;
                break;
            case ENDER_PEARL:
                this.pearlDamage = true;
                break;
        }

        this.normalDamage = false;
    }

    public void resetDamages() {
        this.normalDamage = false;
        this.reset();
        this.timerHelper.reset();
    }

    private void reset() {
        this.fallDamage = false;
        this.explosionDamage = false;
        this.arrowDamage = false;
        this.pearlDamage = false;
    }

    private boolean isBadEffectActive() {
        return mc.player.isPotionActive(MobEffects.POISON) || mc.player.isPotionActive(MobEffects.WITHER) || mc.player.isPotionActive(MobEffects.INSTANT_DAMAGE);
    }

    public boolean isNormalDamage() {
        return this.normalDamage;
    }
}
