/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.player;

import mpp.venusfr.events.EventDamageReceive;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.math.StopWatch;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SEntityStatusPacket;
import net.minecraft.network.play.server.SExplosionPacket;
import net.minecraft.potion.Effects;

public class DamagePlayerUtil
implements IMinecraft {
    private final StopWatch timeTracker = new StopWatch();
    private boolean normalDamage;
    private boolean fallDamage;
    private boolean explosionDamage;
    private boolean arrowDamage;
    private boolean pearlDamage;

    public void onPacketEvent(EventPacket eventPacket) {
        boolean bl;
        boolean bl2 = bl = this.fallDamage || this.arrowDamage || this.explosionDamage || this.pearlDamage;
        if (this.isBadEffects()) {
            return;
        }
        if (eventPacket.getPacket() instanceof SExplosionPacket) {
            this.explosionDamage = true;
        }
        if (!bl) {
            SEntityStatusPacket sEntityStatusPacket;
            IPacket<?> iPacket = eventPacket.getPacket();
            if (iPacket instanceof SEntityStatusPacket && (sEntityStatusPacket = (SEntityStatusPacket)iPacket).getOpCode() == 2 && sEntityStatusPacket.getEntity(DamagePlayerUtil.mc.world) == IMinecraft.mc.player) {
                this.normalDamage = true;
            }
        } else if (DamagePlayerUtil.mc.player.hurtTime > 0) {
            this.normalDamage = false;
            this.reset();
        }
    }

    public boolean time(long l) {
        if (this.normalDamage) {
            if (this.timeTracker.isReached(l)) {
                this.normalDamage = false;
                this.timeTracker.reset();
                return false;
            }
        } else {
            this.timeTracker.reset();
        }
        return true;
    }

    public void processDamage(EventDamageReceive eventDamageReceive) {
        switch (1.$SwitchMap$mpp$venusfr$events$EventDamageReceive$DamageType[eventDamageReceive.getDamageType().ordinal()]) {
            case 1: {
                this.fallDamage = true;
                break;
            }
            case 2: {
                this.arrowDamage = true;
                break;
            }
            case 3: {
                this.pearlDamage = true;
            }
        }
        this.normalDamage = false;
    }

    public void resetDamages() {
        this.normalDamage = false;
        this.reset();
        this.timeTracker.reset();
    }

    private void reset() {
        this.fallDamage = false;
        this.explosionDamage = false;
        this.arrowDamage = false;
        this.pearlDamage = false;
    }

    private boolean isBadEffects() {
        if (DamagePlayerUtil.mc.player == null) {
            return true;
        }
        return DamagePlayerUtil.mc.player.isPotionActive(Effects.POISON) || DamagePlayerUtil.mc.player.isPotionActive(Effects.WITHER) || DamagePlayerUtil.mc.player.isPotionActive(Effects.INSTANT_DAMAGE);
    }

    public boolean isNormalDamage() {
        return this.normalDamage;
    }
}

