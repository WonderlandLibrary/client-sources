/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.Phase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;

public class TakeoffPhase
extends Phase {
    private boolean firstTick;
    private Path currentPath;
    private Vector3d targetLocation;

    public TakeoffPhase(EnderDragonEntity enderDragonEntity) {
        super(enderDragonEntity);
    }

    @Override
    public void serverTick() {
        if (!this.firstTick && this.currentPath != null) {
            BlockPos blockPos = this.dragon.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.END_PODIUM_LOCATION);
            if (!blockPos.withinDistance(this.dragon.getPositionVec(), 10.0)) {
                this.dragon.getPhaseManager().setPhase(PhaseType.HOLDING_PATTERN);
            }
        } else {
            this.firstTick = false;
            this.findNewTarget();
        }
    }

    @Override
    public void initPhase() {
        this.firstTick = true;
        this.currentPath = null;
        this.targetLocation = null;
    }

    private void findNewTarget() {
        int n = this.dragon.initPathPoints();
        Vector3d vector3d = this.dragon.getHeadLookVec(1.0f);
        int n2 = this.dragon.getNearestPpIdx(-vector3d.x * 40.0, 105.0, -vector3d.z * 40.0);
        if (this.dragon.getFightManager() != null && this.dragon.getFightManager().getNumAliveCrystals() > 0) {
            if ((n2 %= 12) < 0) {
                n2 += 12;
            }
        } else {
            n2 -= 12;
            n2 &= 7;
            n2 += 12;
        }
        this.currentPath = this.dragon.findPath(n, n2, null);
        this.navigateToNextPathNode();
    }

    private void navigateToNextPathNode() {
        if (this.currentPath != null) {
            this.currentPath.incrementPathIndex();
            if (!this.currentPath.isFinished()) {
                double d;
                BlockPos blockPos = this.currentPath.func_242948_g();
                this.currentPath.incrementPathIndex();
                while ((d = (double)((float)blockPos.getY() + this.dragon.getRNG().nextFloat() * 20.0f)) < (double)blockPos.getY()) {
                }
                this.targetLocation = new Vector3d(blockPos.getX(), d, blockPos.getZ());
            }
        }
    }

    @Override
    @Nullable
    public Vector3d getTargetLocation() {
        return this.targetLocation;
    }

    public PhaseType<TakeoffPhase> getType() {
        return PhaseType.TAKEOFF;
    }
}

