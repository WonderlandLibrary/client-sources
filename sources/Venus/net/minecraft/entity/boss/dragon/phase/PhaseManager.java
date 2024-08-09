/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.IPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PhaseManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private final EnderDragonEntity dragon;
    private final IPhase[] phases = new IPhase[PhaseType.getTotalPhases()];
    private IPhase phase;

    public PhaseManager(EnderDragonEntity enderDragonEntity) {
        this.dragon = enderDragonEntity;
        this.setPhase(PhaseType.HOVER);
    }

    public void setPhase(PhaseType<?> phaseType) {
        if (this.phase == null || phaseType != this.phase.getType()) {
            if (this.phase != null) {
                this.phase.removeAreaEffect();
            }
            this.phase = this.getPhase(phaseType);
            if (!this.dragon.world.isRemote) {
                this.dragon.getDataManager().set(EnderDragonEntity.PHASE, phaseType.getId());
            }
            LOGGER.debug("Dragon is now in phase {} on the {}", (Object)phaseType, (Object)(this.dragon.world.isRemote ? "client" : "server"));
            this.phase.initPhase();
        }
    }

    public IPhase getCurrentPhase() {
        return this.phase;
    }

    public <T extends IPhase> T getPhase(PhaseType<T> phaseType) {
        int n = phaseType.getId();
        if (this.phases[n] == null) {
            this.phases[n] = phaseType.createPhase(this.dragon);
        }
        return (T)this.phases[n];
    }
}

