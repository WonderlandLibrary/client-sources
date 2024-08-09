/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.border;

import com.google.common.collect.Lists;
import com.mojang.serialization.DynamicLike;
import java.util.List;
import java.util.Objects;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.border.BorderStatus;
import net.minecraft.world.border.IBorderListener;

public class WorldBorder {
    private final List<IBorderListener> listeners = Lists.newArrayList();
    private double damagePerBlock = 0.2;
    private double damageBuffer = 5.0;
    private int warningTime = 15;
    private int warningDistance = 5;
    private double centerX;
    private double centerZ;
    private int worldSize = 29999984;
    private IBorderInfo state = new StationaryBorderInfo(this, 6.0E7);
    public static final Serializer DEFAULT_SERIALIZER = new Serializer(0.0, 0.0, 0.2, 5.0, 5, 15, 6.0E7, 0L, 0.0);

    public boolean contains(BlockPos blockPos) {
        return (double)(blockPos.getX() + 1) > this.minX() && (double)blockPos.getX() < this.maxX() && (double)(blockPos.getZ() + 1) > this.minZ() && (double)blockPos.getZ() < this.maxZ();
    }

    public boolean contains(ChunkPos chunkPos) {
        return (double)chunkPos.getXEnd() > this.minX() && (double)chunkPos.getXStart() < this.maxX() && (double)chunkPos.getZEnd() > this.minZ() && (double)chunkPos.getZStart() < this.maxZ();
    }

    public boolean contains(AxisAlignedBB axisAlignedBB) {
        return axisAlignedBB.maxX > this.minX() && axisAlignedBB.minX < this.maxX() && axisAlignedBB.maxZ > this.minZ() && axisAlignedBB.minZ < this.maxZ();
    }

    public double getClosestDistance(Entity entity2) {
        return this.getClosestDistance(entity2.getPosX(), entity2.getPosZ());
    }

    public VoxelShape getShape() {
        return this.state.getShape();
    }

    public double getClosestDistance(double d, double d2) {
        double d3 = d2 - this.minZ();
        double d4 = this.maxZ() - d2;
        double d5 = d - this.minX();
        double d6 = this.maxX() - d;
        double d7 = Math.min(d5, d6);
        d7 = Math.min(d7, d3);
        return Math.min(d7, d4);
    }

    public BorderStatus getStatus() {
        return this.state.getStatus();
    }

    public double minX() {
        return this.state.getMinX();
    }

    public double minZ() {
        return this.state.getMinZ();
    }

    public double maxX() {
        return this.state.getMaxX();
    }

    public double maxZ() {
        return this.state.getMaxZ();
    }

    public double getCenterX() {
        return this.centerX;
    }

    public double getCenterZ() {
        return this.centerZ;
    }

    public void setCenter(double d, double d2) {
        this.centerX = d;
        this.centerZ = d2;
        this.state.onCenterChanged();
        for (IBorderListener iBorderListener : this.getListeners()) {
            iBorderListener.onCenterChanged(this, d, d2);
        }
    }

    public double getDiameter() {
        return this.state.getSize();
    }

    public long getTimeUntilTarget() {
        return this.state.getTimeUntilTarget();
    }

    public double getTargetSize() {
        return this.state.getTargetSize();
    }

    public void setTransition(double d) {
        this.state = new StationaryBorderInfo(this, d);
        for (IBorderListener iBorderListener : this.getListeners()) {
            iBorderListener.onSizeChanged(this, d);
        }
    }

    public void setTransition(double d, double d2, long l) {
        this.state = d == d2 ? new StationaryBorderInfo(this, d2) : new MovingBorderInfo(this, d, d2, l);
        for (IBorderListener iBorderListener : this.getListeners()) {
            iBorderListener.onTransitionStarted(this, d, d2, l);
        }
    }

    protected List<IBorderListener> getListeners() {
        return Lists.newArrayList(this.listeners);
    }

    public void addListener(IBorderListener iBorderListener) {
        this.listeners.add(iBorderListener);
    }

    public void setSize(int n) {
        this.worldSize = n;
        this.state.onSizeChanged();
    }

    public int getSize() {
        return this.worldSize;
    }

    public double getDamageBuffer() {
        return this.damageBuffer;
    }

    public void setDamageBuffer(double d) {
        this.damageBuffer = d;
        for (IBorderListener iBorderListener : this.getListeners()) {
            iBorderListener.onDamageBufferChanged(this, d);
        }
    }

    public double getDamagePerBlock() {
        return this.damagePerBlock;
    }

    public void setDamagePerBlock(double d) {
        this.damagePerBlock = d;
        for (IBorderListener iBorderListener : this.getListeners()) {
            iBorderListener.onDamageAmountChanged(this, d);
        }
    }

    public double getResizeSpeed() {
        return this.state.getResizeSpeed();
    }

    public int getWarningTime() {
        return this.warningTime;
    }

    public void setWarningTime(int n) {
        this.warningTime = n;
        for (IBorderListener iBorderListener : this.getListeners()) {
            iBorderListener.onWarningTimeChanged(this, n);
        }
    }

    public int getWarningDistance() {
        return this.warningDistance;
    }

    public void setWarningDistance(int n) {
        this.warningDistance = n;
        for (IBorderListener iBorderListener : this.getListeners()) {
            iBorderListener.onWarningDistanceChanged(this, n);
        }
    }

    public void tick() {
        this.state = this.state.tick();
    }

    public Serializer getSerializer() {
        return new Serializer(this);
    }

    public void deserialize(Serializer serializer) {
        this.setCenter(serializer.getCenterX(), serializer.getCenterZ());
        this.setDamagePerBlock(serializer.getDamagePerBlock());
        this.setDamageBuffer(serializer.getDamageBuffer());
        this.setWarningDistance(serializer.getWarningDistance());
        this.setWarningTime(serializer.getWarningTime());
        if (serializer.getSizeLerpTime() > 0L) {
            this.setTransition(serializer.getSize(), serializer.getSizeLerpTarget(), serializer.getSizeLerpTime());
        } else {
            this.setTransition(serializer.getSize());
        }
    }

    class StationaryBorderInfo
    implements IBorderInfo {
        private final double size;
        private double minX;
        private double minZ;
        private double maxX;
        private double maxZ;
        private VoxelShape shape;
        final WorldBorder this$0;

        public StationaryBorderInfo(WorldBorder worldBorder, double d) {
            this.this$0 = worldBorder;
            this.size = d;
            this.updateBox();
        }

        @Override
        public double getMinX() {
            return this.minX;
        }

        @Override
        public double getMaxX() {
            return this.maxX;
        }

        @Override
        public double getMinZ() {
            return this.minZ;
        }

        @Override
        public double getMaxZ() {
            return this.maxZ;
        }

        @Override
        public double getSize() {
            return this.size;
        }

        @Override
        public BorderStatus getStatus() {
            return BorderStatus.STATIONARY;
        }

        @Override
        public double getResizeSpeed() {
            return 0.0;
        }

        @Override
        public long getTimeUntilTarget() {
            return 0L;
        }

        @Override
        public double getTargetSize() {
            return this.size;
        }

        private void updateBox() {
            this.minX = Math.max(this.this$0.getCenterX() - this.size / 2.0, (double)(-this.this$0.worldSize));
            this.minZ = Math.max(this.this$0.getCenterZ() - this.size / 2.0, (double)(-this.this$0.worldSize));
            this.maxX = Math.min(this.this$0.getCenterX() + this.size / 2.0, (double)this.this$0.worldSize);
            this.maxZ = Math.min(this.this$0.getCenterZ() + this.size / 2.0, (double)this.this$0.worldSize);
            this.shape = VoxelShapes.combineAndSimplify(VoxelShapes.INFINITY, VoxelShapes.create(Math.floor(this.getMinX()), Double.NEGATIVE_INFINITY, Math.floor(this.getMinZ()), Math.ceil(this.getMaxX()), Double.POSITIVE_INFINITY, Math.ceil(this.getMaxZ())), IBooleanFunction.ONLY_FIRST);
        }

        @Override
        public void onSizeChanged() {
            this.updateBox();
        }

        @Override
        public void onCenterChanged() {
            this.updateBox();
        }

        @Override
        public IBorderInfo tick() {
            return this;
        }

        @Override
        public VoxelShape getShape() {
            return this.shape;
        }
    }

    static interface IBorderInfo {
        public double getMinX();

        public double getMaxX();

        public double getMinZ();

        public double getMaxZ();

        public double getSize();

        public double getResizeSpeed();

        public long getTimeUntilTarget();

        public double getTargetSize();

        public BorderStatus getStatus();

        public void onSizeChanged();

        public void onCenterChanged();

        public IBorderInfo tick();

        public VoxelShape getShape();
    }

    class MovingBorderInfo
    implements IBorderInfo {
        private final double oldSize;
        private final double newSize;
        private final long endTime;
        private final long startTime;
        private final double transitionTime;
        final WorldBorder this$0;

        private MovingBorderInfo(WorldBorder worldBorder, double d, double d2, long l) {
            this.this$0 = worldBorder;
            this.oldSize = d;
            this.newSize = d2;
            this.transitionTime = l;
            this.startTime = Util.milliTime();
            this.endTime = this.startTime + l;
        }

        @Override
        public double getMinX() {
            return Math.max(this.this$0.getCenterX() - this.getSize() / 2.0, (double)(-this.this$0.worldSize));
        }

        @Override
        public double getMinZ() {
            return Math.max(this.this$0.getCenterZ() - this.getSize() / 2.0, (double)(-this.this$0.worldSize));
        }

        @Override
        public double getMaxX() {
            return Math.min(this.this$0.getCenterX() + this.getSize() / 2.0, (double)this.this$0.worldSize);
        }

        @Override
        public double getMaxZ() {
            return Math.min(this.this$0.getCenterZ() + this.getSize() / 2.0, (double)this.this$0.worldSize);
        }

        @Override
        public double getSize() {
            double d = (double)(Util.milliTime() - this.startTime) / this.transitionTime;
            return d < 1.0 ? MathHelper.lerp(d, this.oldSize, this.newSize) : this.newSize;
        }

        @Override
        public double getResizeSpeed() {
            return Math.abs(this.oldSize - this.newSize) / (double)(this.endTime - this.startTime);
        }

        @Override
        public long getTimeUntilTarget() {
            return this.endTime - Util.milliTime();
        }

        @Override
        public double getTargetSize() {
            return this.newSize;
        }

        @Override
        public BorderStatus getStatus() {
            return this.newSize < this.oldSize ? BorderStatus.SHRINKING : BorderStatus.GROWING;
        }

        @Override
        public void onCenterChanged() {
        }

        @Override
        public void onSizeChanged() {
        }

        @Override
        public IBorderInfo tick() {
            IBorderInfo iBorderInfo;
            if (this.getTimeUntilTarget() <= 0L) {
                WorldBorder worldBorder = this.this$0;
                Objects.requireNonNull(worldBorder);
                iBorderInfo = new StationaryBorderInfo(worldBorder, this.newSize);
            } else {
                iBorderInfo = this;
            }
            return iBorderInfo;
        }

        @Override
        public VoxelShape getShape() {
            return VoxelShapes.combineAndSimplify(VoxelShapes.INFINITY, VoxelShapes.create(Math.floor(this.getMinX()), Double.NEGATIVE_INFINITY, Math.floor(this.getMinZ()), Math.ceil(this.getMaxX()), Double.POSITIVE_INFINITY, Math.ceil(this.getMaxZ())), IBooleanFunction.ONLY_FIRST);
        }
    }

    public static class Serializer {
        private final double centerX;
        private final double centerZ;
        private final double damagePerBlock;
        private final double damageBuffer;
        private final int warningDistance;
        private final int warningTime;
        private final double size;
        private final long sizeLerpTime;
        private final double sizeLerpTarget;

        private Serializer(double d, double d2, double d3, double d4, int n, int n2, double d5, long l, double d6) {
            this.centerX = d;
            this.centerZ = d2;
            this.damagePerBlock = d3;
            this.damageBuffer = d4;
            this.warningDistance = n;
            this.warningTime = n2;
            this.size = d5;
            this.sizeLerpTime = l;
            this.sizeLerpTarget = d6;
        }

        private Serializer(WorldBorder worldBorder) {
            this.centerX = worldBorder.getCenterX();
            this.centerZ = worldBorder.getCenterZ();
            this.damagePerBlock = worldBorder.getDamagePerBlock();
            this.damageBuffer = worldBorder.getDamageBuffer();
            this.warningDistance = worldBorder.getWarningDistance();
            this.warningTime = worldBorder.getWarningTime();
            this.size = worldBorder.getDiameter();
            this.sizeLerpTime = worldBorder.getTimeUntilTarget();
            this.sizeLerpTarget = worldBorder.getTargetSize();
        }

        public double getCenterX() {
            return this.centerX;
        }

        public double getCenterZ() {
            return this.centerZ;
        }

        public double getDamagePerBlock() {
            return this.damagePerBlock;
        }

        public double getDamageBuffer() {
            return this.damageBuffer;
        }

        public int getWarningDistance() {
            return this.warningDistance;
        }

        public int getWarningTime() {
            return this.warningTime;
        }

        public double getSize() {
            return this.size;
        }

        public long getSizeLerpTime() {
            return this.sizeLerpTime;
        }

        public double getSizeLerpTarget() {
            return this.sizeLerpTarget;
        }

        public static Serializer deserialize(DynamicLike<?> dynamicLike, Serializer serializer) {
            double d = dynamicLike.get("BorderCenterX").asDouble(serializer.centerX);
            double d2 = dynamicLike.get("BorderCenterZ").asDouble(serializer.centerZ);
            double d3 = dynamicLike.get("BorderSize").asDouble(serializer.size);
            long l = dynamicLike.get("BorderSizeLerpTime").asLong(serializer.sizeLerpTime);
            double d4 = dynamicLike.get("BorderSizeLerpTarget").asDouble(serializer.sizeLerpTarget);
            double d5 = dynamicLike.get("BorderSafeZone").asDouble(serializer.damageBuffer);
            double d6 = dynamicLike.get("BorderDamagePerBlock").asDouble(serializer.damagePerBlock);
            int n = dynamicLike.get("BorderWarningBlocks").asInt(serializer.warningDistance);
            int n2 = dynamicLike.get("BorderWarningTime").asInt(serializer.warningTime);
            return new Serializer(d, d2, d6, d5, n, n2, d3, l, d4);
        }

        public void serialize(CompoundNBT compoundNBT) {
            compoundNBT.putDouble("BorderCenterX", this.centerX);
            compoundNBT.putDouble("BorderCenterZ", this.centerZ);
            compoundNBT.putDouble("BorderSize", this.size);
            compoundNBT.putLong("BorderSizeLerpTime", this.sizeLerpTime);
            compoundNBT.putDouble("BorderSafeZone", this.damageBuffer);
            compoundNBT.putDouble("BorderDamagePerBlock", this.damagePerBlock);
            compoundNBT.putDouble("BorderSizeLerpTarget", this.sizeLerpTarget);
            compoundNBT.putDouble("BorderWarningBlocks", this.warningDistance);
            compoundNBT.putDouble("BorderWarningTime", this.warningTime);
        }
    }
}

