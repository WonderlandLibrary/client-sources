/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.pathfinding;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.FlaggedPathPoint;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.Region;

public abstract class NodeProcessor {
    protected Region blockaccess;
    protected MobEntity entity;
    protected final Int2ObjectMap<PathPoint> pointMap = new Int2ObjectOpenHashMap<PathPoint>();
    protected int entitySizeX;
    protected int entitySizeY;
    protected int entitySizeZ;
    protected boolean canEnterDoors;
    protected boolean canOpenDoors;
    protected boolean canSwim;

    public void func_225578_a_(Region region, MobEntity mobEntity) {
        this.blockaccess = region;
        this.entity = mobEntity;
        this.pointMap.clear();
        this.entitySizeX = MathHelper.floor(mobEntity.getWidth() + 1.0f);
        this.entitySizeY = MathHelper.floor(mobEntity.getHeight() + 1.0f);
        this.entitySizeZ = MathHelper.floor(mobEntity.getWidth() + 1.0f);
    }

    public void postProcess() {
        this.blockaccess = null;
        this.entity = null;
    }

    protected PathPoint func_237223_a_(BlockPos blockPos) {
        return this.openPoint(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    protected PathPoint openPoint(int n, int n2, int n3) {
        return this.pointMap.computeIfAbsent(PathPoint.makeHash(n, n2, n3), arg_0 -> NodeProcessor.lambda$openPoint$0(n, n2, n3, arg_0));
    }

    public abstract PathPoint getStart();

    public abstract FlaggedPathPoint func_224768_a(double var1, double var3, double var5);

    public abstract int func_222859_a(PathPoint[] var1, PathPoint var2);

    public abstract PathNodeType getPathNodeType(IBlockReader var1, int var2, int var3, int var4, MobEntity var5, int var6, int var7, int var8, boolean var9, boolean var10);

    public abstract PathNodeType getPathNodeType(IBlockReader var1, int var2, int var3, int var4);

    public void setCanEnterDoors(boolean bl) {
        this.canEnterDoors = bl;
    }

    public void setCanOpenDoors(boolean bl) {
        this.canOpenDoors = bl;
    }

    public void setCanSwim(boolean bl) {
        this.canSwim = bl;
    }

    public boolean getCanEnterDoors() {
        return this.canEnterDoors;
    }

    public boolean getCanOpenDoors() {
        return this.canOpenDoors;
    }

    public boolean getCanSwim() {
        return this.canSwim;
    }

    private static PathPoint lambda$openPoint$0(int n, int n2, int n3, int n4) {
        return new PathPoint(n, n2, n3);
    }
}

