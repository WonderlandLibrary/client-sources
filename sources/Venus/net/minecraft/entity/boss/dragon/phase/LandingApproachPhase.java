/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.Phase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;

public class LandingApproachPhase
extends Phase {
    private static final EntityPredicate field_221118_b = new EntityPredicate().setDistance(128.0);
    private Path currentPath;
    private Vector3d targetLocation;

    public LandingApproachPhase(EnderDragonEntity enderDragonEntity) {
        super(enderDragonEntity);
    }

    public PhaseType<LandingApproachPhase> getType() {
        return PhaseType.LANDING_APPROACH;
    }

    @Override
    public void initPhase() {
        this.currentPath = null;
        this.targetLocation = null;
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
    @Nullable
    public Vector3d getTargetLocation() {
        return this.targetLocation;
    }

    private void findNewTarget() {
        if (this.currentPath == null || this.currentPath.isFinished()) {
            int n;
            Object object;
            int n2 = this.dragon.initPathPoints();
            BlockPos blockPos = this.dragon.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.END_PODIUM_LOCATION);
            PlayerEntity playerEntity = this.dragon.world.getClosestPlayer(field_221118_b, blockPos.getX(), blockPos.getY(), blockPos.getZ());
            if (playerEntity != null) {
                object = new Vector3d(playerEntity.getPosX(), 0.0, playerEntity.getPosZ()).normalize();
                n = this.dragon.getNearestPpIdx(-((Vector3d)object).x * 40.0, 105.0, -((Vector3d)object).z * 40.0);
            } else {
                n = this.dragon.getNearestPpIdx(40.0, blockPos.getY(), 0.0);
            }
            object = new PathPoint(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            this.currentPath = this.dragon.findPath(n2, n, (PathPoint)object);
            if (this.currentPath != null) {
                this.currentPath.incrementPathIndex();
            }
        }
        this.navigateToNextPathNode();
        if (this.currentPath != null && this.currentPath.isFinished()) {
            this.dragon.getPhaseManager().setPhase(PhaseType.LANDING);
        }
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
}

