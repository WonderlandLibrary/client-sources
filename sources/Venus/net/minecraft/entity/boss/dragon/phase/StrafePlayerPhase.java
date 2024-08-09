/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.Phase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StrafePlayerPhase
extends Phase {
    private static final Logger LOGGER = LogManager.getLogger();
    private int fireballCharge;
    private Path currentPath;
    private Vector3d targetLocation;
    private LivingEntity attackTarget;
    private boolean holdingPatternClockwise;

    public StrafePlayerPhase(EnderDragonEntity enderDragonEntity) {
        super(enderDragonEntity);
    }

    @Override
    public void serverTick() {
        if (this.attackTarget == null) {
            LOGGER.warn("Skipping player strafe phase because no player was found");
            this.dragon.getPhaseManager().setPhase(PhaseType.HOLDING_PATTERN);
        } else {
            double d;
            double d2;
            double d3;
            if (this.currentPath != null && this.currentPath.isFinished()) {
                d3 = this.attackTarget.getPosX();
                d2 = this.attackTarget.getPosZ();
                double d4 = d3 - this.dragon.getPosX();
                double d5 = d2 - this.dragon.getPosZ();
                d = MathHelper.sqrt(d4 * d4 + d5 * d5);
                double d6 = Math.min((double)0.4f + d / 80.0 - 1.0, 10.0);
                this.targetLocation = new Vector3d(d3, this.attackTarget.getPosY() + d6, d2);
            }
            double d7 = d3 = this.targetLocation == null ? 0.0 : this.targetLocation.squareDistanceTo(this.dragon.getPosX(), this.dragon.getPosY(), this.dragon.getPosZ());
            if (d3 < 100.0 || d3 > 22500.0) {
                this.findNewTarget();
            }
            d2 = 64.0;
            if (this.attackTarget.getDistanceSq(this.dragon) < 4096.0) {
                if (this.dragon.canEntityBeSeen(this.attackTarget)) {
                    ++this.fireballCharge;
                    Vector3d vector3d = new Vector3d(this.attackTarget.getPosX() - this.dragon.getPosX(), 0.0, this.attackTarget.getPosZ() - this.dragon.getPosZ()).normalize();
                    Vector3d vector3d2 = new Vector3d(MathHelper.sin(this.dragon.rotationYaw * ((float)Math.PI / 180)), 0.0, -MathHelper.cos(this.dragon.rotationYaw * ((float)Math.PI / 180))).normalize();
                    float f = (float)vector3d2.dotProduct(vector3d);
                    float f2 = (float)(Math.acos(f) * 57.2957763671875);
                    f2 += 0.5f;
                    if (this.fireballCharge >= 5 && f2 >= 0.0f && f2 < 10.0f) {
                        d = 1.0;
                        Vector3d vector3d3 = this.dragon.getLook(1.0f);
                        double d8 = this.dragon.dragonPartHead.getPosX() - vector3d3.x * 1.0;
                        double d9 = this.dragon.dragonPartHead.getPosYHeight(0.5) + 0.5;
                        double d10 = this.dragon.dragonPartHead.getPosZ() - vector3d3.z * 1.0;
                        double d11 = this.attackTarget.getPosX() - d8;
                        double d12 = this.attackTarget.getPosYHeight(0.5) - d9;
                        double d13 = this.attackTarget.getPosZ() - d10;
                        if (!this.dragon.isSilent()) {
                            this.dragon.world.playEvent(null, 1017, this.dragon.getPosition(), 0);
                        }
                        DragonFireballEntity dragonFireballEntity = new DragonFireballEntity(this.dragon.world, this.dragon, d11, d12, d13);
                        dragonFireballEntity.setLocationAndAngles(d8, d9, d10, 0.0f, 0.0f);
                        this.dragon.world.addEntity(dragonFireballEntity);
                        this.fireballCharge = 0;
                        if (this.currentPath != null) {
                            while (!this.currentPath.isFinished()) {
                                this.currentPath.incrementPathIndex();
                            }
                        }
                        this.dragon.getPhaseManager().setPhase(PhaseType.HOLDING_PATTERN);
                    }
                } else if (this.fireballCharge > 0) {
                    --this.fireballCharge;
                }
            } else if (this.fireballCharge > 0) {
                --this.fireballCharge;
            }
        }
    }

    private void findNewTarget() {
        if (this.currentPath == null || this.currentPath.isFinished()) {
            int n;
            int n2 = n = this.dragon.initPathPoints();
            if (this.dragon.getRNG().nextInt(8) == 0) {
                this.holdingPatternClockwise = !this.holdingPatternClockwise;
                n2 = n + 6;
            }
            n2 = this.holdingPatternClockwise ? ++n2 : --n2;
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
            if (this.currentPath != null) {
                this.currentPath.incrementPathIndex();
            }
        }
        this.navigateToNextPathNode();
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
    public void initPhase() {
        this.fireballCharge = 0;
        this.targetLocation = null;
        this.currentPath = null;
        this.attackTarget = null;
    }

    public void setTarget(LivingEntity livingEntity) {
        this.attackTarget = livingEntity;
        int n = this.dragon.initPathPoints();
        int n2 = this.dragon.getNearestPpIdx(this.attackTarget.getPosX(), this.attackTarget.getPosY(), this.attackTarget.getPosZ());
        int n3 = MathHelper.floor(this.attackTarget.getPosX());
        int n4 = MathHelper.floor(this.attackTarget.getPosZ());
        double d = (double)n3 - this.dragon.getPosX();
        double d2 = (double)n4 - this.dragon.getPosZ();
        double d3 = MathHelper.sqrt(d * d + d2 * d2);
        double d4 = Math.min((double)0.4f + d3 / 80.0 - 1.0, 10.0);
        int n5 = MathHelper.floor(this.attackTarget.getPosY() + d4);
        PathPoint pathPoint = new PathPoint(n3, n5, n4);
        this.currentPath = this.dragon.findPath(n, n2, pathPoint);
        if (this.currentPath != null) {
            this.currentPath.incrementPathIndex();
            this.navigateToNextPathNode();
        }
    }

    @Override
    @Nullable
    public Vector3d getTargetLocation() {
        return this.targetLocation;
    }

    public PhaseType<StrafePlayerPhase> getType() {
        return PhaseType.STRAFE_PLAYER;
    }
}

