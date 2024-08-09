/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.Phase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;

public class HoldingPatternPhase
extends Phase {
    private static final EntityPredicate field_221117_b = new EntityPredicate().setDistance(64.0);
    private Path currentPath;
    private Vector3d targetLocation;
    private boolean clockwise;

    public HoldingPatternPhase(EnderDragonEntity enderDragonEntity) {
        super(enderDragonEntity);
    }

    public PhaseType<HoldingPatternPhase> getType() {
        return PhaseType.HOLDING_PATTERN;
    }

    @Override
    public void serverTick() {
        double d;
        double d2 = d = this.targetLocation == null ? 0.0 : this.targetLocation.squareDistanceTo(this.dragon.getPosX(), this.dragon.getPosY(), this.dragon.getPosZ());
        if (d < 100.0 || d > 22500.0 || this.dragon.collidedHorizontally || this.dragon.collidedVertically) {
            this.findNewTarget();
        }
    }

    @Override
    public void initPhase() {
        this.currentPath = null;
        this.targetLocation = null;
    }

    @Override
    @Nullable
    public Vector3d getTargetLocation() {
        return this.targetLocation;
    }

    private void findNewTarget() {
        int n;
        if (this.currentPath != null && this.currentPath.isFinished()) {
            BlockPos blockPos = this.dragon.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(EndPodiumFeature.END_PODIUM_LOCATION));
            int n2 = n = this.dragon.getFightManager() == null ? 0 : this.dragon.getFightManager().getNumAliveCrystals();
            if (this.dragon.getRNG().nextInt(n + 3) == 0) {
                this.dragon.getPhaseManager().setPhase(PhaseType.LANDING_APPROACH);
                return;
            }
            double d = 64.0;
            PlayerEntity playerEntity = this.dragon.world.getClosestPlayer(field_221117_b, blockPos.getX(), blockPos.getY(), blockPos.getZ());
            if (playerEntity != null) {
                d = blockPos.distanceSq(playerEntity.getPositionVec(), false) / 512.0;
            }
            if (!(playerEntity == null || playerEntity.abilities.disableDamage || this.dragon.getRNG().nextInt(MathHelper.abs((int)d) + 2) != 0 && this.dragon.getRNG().nextInt(n + 2) != 0)) {
                this.strafePlayer(playerEntity);
                return;
            }
        }
        if (this.currentPath == null || this.currentPath.isFinished()) {
            int n3;
            n = n3 = this.dragon.initPathPoints();
            if (this.dragon.getRNG().nextInt(8) == 0) {
                this.clockwise = !this.clockwise;
                n = n3 + 6;
            }
            n = this.clockwise ? ++n : --n;
            if (this.dragon.getFightManager() != null && this.dragon.getFightManager().getNumAliveCrystals() >= 0) {
                if ((n %= 12) < 0) {
                    n += 12;
                }
            } else {
                n -= 12;
                n &= 7;
                n += 12;
            }
            this.currentPath = this.dragon.findPath(n3, n, null);
            if (this.currentPath != null) {
                this.currentPath.incrementPathIndex();
            }
        }
        this.navigateToNextPathNode();
    }

    private void strafePlayer(PlayerEntity playerEntity) {
        this.dragon.getPhaseManager().setPhase(PhaseType.STRAFE_PLAYER);
        this.dragon.getPhaseManager().getPhase(PhaseType.STRAFE_PLAYER).setTarget(playerEntity);
    }

    private void navigateToNextPathNode() {
        if (this.currentPath != null && !this.currentPath.isFinished()) {
            double d;
            BlockPos blockPos = this.currentPath.func_242948_g();
            this.currentPath.incrementPathIndex();
            double d2 = blockPos.getX();
            double d3 = blockPos.getZ();
            while ((d = (double)((float)blockPos.getY() + this.dragon.getRNG().nextFloat() * 20.0f)) < (double)blockPos.getY()) {
            }
            this.targetLocation = new Vector3d(d2, d, d3);
        }
    }

    @Override
    public void onCrystalDestroyed(EnderCrystalEntity enderCrystalEntity, BlockPos blockPos, DamageSource damageSource, @Nullable PlayerEntity playerEntity) {
        if (playerEntity != null && !playerEntity.abilities.disableDamage) {
            this.strafePlayer(playerEntity);
        }
    }
}

