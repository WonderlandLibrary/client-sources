/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.boss.dragon.phase;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AttackingSittingPhase;
import net.minecraft.entity.boss.dragon.phase.ChargingPlayerPhase;
import net.minecraft.entity.boss.dragon.phase.DyingPhase;
import net.minecraft.entity.boss.dragon.phase.FlamingSittingPhase;
import net.minecraft.entity.boss.dragon.phase.HoldingPatternPhase;
import net.minecraft.entity.boss.dragon.phase.HoverPhase;
import net.minecraft.entity.boss.dragon.phase.IPhase;
import net.minecraft.entity.boss.dragon.phase.LandingApproachPhase;
import net.minecraft.entity.boss.dragon.phase.LandingPhase;
import net.minecraft.entity.boss.dragon.phase.ScanningSittingPhase;
import net.minecraft.entity.boss.dragon.phase.StrafePlayerPhase;
import net.minecraft.entity.boss.dragon.phase.TakeoffPhase;

public class PhaseType<T extends IPhase> {
    private static PhaseType<?>[] phases = new PhaseType[0];
    public static final PhaseType<HoldingPatternPhase> HOLDING_PATTERN = PhaseType.create(HoldingPatternPhase.class, "HoldingPattern");
    public static final PhaseType<StrafePlayerPhase> STRAFE_PLAYER = PhaseType.create(StrafePlayerPhase.class, "StrafePlayer");
    public static final PhaseType<LandingApproachPhase> LANDING_APPROACH = PhaseType.create(LandingApproachPhase.class, "LandingApproach");
    public static final PhaseType<LandingPhase> LANDING = PhaseType.create(LandingPhase.class, "Landing");
    public static final PhaseType<TakeoffPhase> TAKEOFF = PhaseType.create(TakeoffPhase.class, "Takeoff");
    public static final PhaseType<FlamingSittingPhase> SITTING_FLAMING = PhaseType.create(FlamingSittingPhase.class, "SittingFlaming");
    public static final PhaseType<ScanningSittingPhase> SITTING_SCANNING = PhaseType.create(ScanningSittingPhase.class, "SittingScanning");
    public static final PhaseType<AttackingSittingPhase> SITTING_ATTACKING = PhaseType.create(AttackingSittingPhase.class, "SittingAttacking");
    public static final PhaseType<ChargingPlayerPhase> CHARGING_PLAYER = PhaseType.create(ChargingPlayerPhase.class, "ChargingPlayer");
    public static final PhaseType<DyingPhase> DYING = PhaseType.create(DyingPhase.class, "Dying");
    public static final PhaseType<HoverPhase> HOVER = PhaseType.create(HoverPhase.class, "Hover");
    private final Class<? extends IPhase> clazz;
    private final int id;
    private final String name;

    private PhaseType(int n, Class<? extends IPhase> clazz, String string) {
        this.id = n;
        this.clazz = clazz;
        this.name = string;
    }

    public IPhase createPhase(EnderDragonEntity enderDragonEntity) {
        try {
            Constructor<IPhase> constructor = this.getConstructor();
            return constructor.newInstance(enderDragonEntity);
        } catch (Exception exception) {
            throw new Error(exception);
        }
    }

    protected Constructor<? extends IPhase> getConstructor() throws NoSuchMethodException {
        return this.clazz.getConstructor(EnderDragonEntity.class);
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return this.name + " (#" + this.id + ")";
    }

    public static PhaseType<?> getById(int n) {
        return n >= 0 && n < phases.length ? phases[n] : HOLDING_PATTERN;
    }

    public static int getTotalPhases() {
        return phases.length;
    }

    private static <T extends IPhase> PhaseType<T> create(Class<T> clazz, String string) {
        PhaseType<T> phaseType = new PhaseType<T>(phases.length, clazz, string);
        phases = Arrays.copyOf(phases, phases.length + 1);
        PhaseType.phases[phaseType.getId()] = phaseType;
        return phaseType;
    }
}

