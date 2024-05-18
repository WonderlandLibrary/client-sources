package net.minecraft.pathfinding;

import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;

public abstract class PathNavigate
{
    private int ticksAtLastPos;
    private final IAttributeInstance pathSearchRange;
    private final PathFinder pathFinder;
    private float heightRequirement;
    private static final String[] I;
    protected double speed;
    protected EntityLiving theEntity;
    protected PathEntity currentPath;
    private Vec3 lastPosCheck;
    protected World worldObj;
    private int totalTicks;
    
    public PathNavigate(final EntityLiving theEntity, final World worldObj) {
        this.lastPosCheck = new Vec3(0.0, 0.0, 0.0);
        this.heightRequirement = 1.0f;
        this.theEntity = theEntity;
        this.worldObj = worldObj;
        this.pathSearchRange = theEntity.getEntityAttribute(SharedMonsterAttributes.followRange);
        this.pathFinder = this.getPathFinder();
    }
    
    public final PathEntity getPathToXYZ(final double n, final double n2, final double n3) {
        return this.getPathToPos(new BlockPos(MathHelper.floor_double(n), (int)n2, MathHelper.floor_double(n3)));
    }
    
    protected abstract Vec3 getEntityPosition();
    
    public void clearPathEntity() {
        this.currentPath = null;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public PathEntity getPathToEntityLiving(final Entity entity) {
        if (!this.canNavigate()) {
            return null;
        }
        final float pathSearchRange = this.getPathSearchRange();
        this.worldObj.theProfiler.startSection(PathNavigate.I[" ".length()]);
        final BlockPos up = new BlockPos(this.theEntity).up();
        final int n = (int)(pathSearchRange + 16.0f);
        final PathEntity entityPathTo = this.pathFinder.createEntityPathTo(new ChunkCache(this.worldObj, up.add(-n, -n, -n), up.add(n, n, n), "".length()), this.theEntity, entity, pathSearchRange);
        this.worldObj.theProfiler.endSection();
        return entityPathTo;
    }
    
    public void setSpeed(final double speed) {
        this.speed = speed;
    }
    
    protected void removeSunnyPath() {
    }
    
    public void setHeightRequirement(final float heightRequirement) {
        this.heightRequirement = heightRequirement;
    }
    
    protected abstract boolean isDirectPathBetweenPoints(final Vec3 p0, final Vec3 p1, final int p2, final int p3, final int p4);
    
    static {
        I();
    }
    
    protected void checkForStuck(final Vec3 lastPosCheck) {
        if (this.totalTicks - this.ticksAtLastPos > (0x5F ^ 0x3B)) {
            if (lastPosCheck.squareDistanceTo(this.lastPosCheck) < 2.25) {
                this.clearPathEntity();
            }
            this.ticksAtLastPos = this.totalTicks;
            this.lastPosCheck = lastPosCheck;
        }
    }
    
    public boolean tryMoveToEntityLiving(final Entity entity, final double n) {
        final PathEntity pathToEntityLiving = this.getPathToEntityLiving(entity);
        int n2;
        if (pathToEntityLiving != null) {
            n2 = (this.setPath(pathToEntityLiving, n) ? 1 : 0);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return n2 != 0;
    }
    
    protected abstract PathFinder getPathFinder();
    
    public boolean noPath() {
        if (this.currentPath != null && !this.currentPath.isFinished()) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public PathEntity getPath() {
        return this.currentPath;
    }
    
    protected void pathFollow() {
        final Vec3 entityPosition = this.getEntityPosition();
        int currentPathLength = this.currentPath.getCurrentPathLength();
        int i = this.currentPath.getCurrentPathIndex();
        "".length();
        if (4 <= 1) {
            throw null;
        }
        while (i < this.currentPath.getCurrentPathLength()) {
            if (this.currentPath.getPathPointFromIndex(i).yCoord != (int)entityPosition.yCoord) {
                currentPathLength = i;
                "".length();
                if (3 == 1) {
                    throw null;
                }
                break;
            }
            else {
                ++i;
            }
        }
        final float n = this.theEntity.width * this.theEntity.width * this.heightRequirement;
        int j = this.currentPath.getCurrentPathIndex();
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (j < currentPathLength) {
            if (entityPosition.squareDistanceTo(this.currentPath.getVectorFromIndex(this.theEntity, j)) < n) {
                this.currentPath.setCurrentPathIndex(j + " ".length());
            }
            ++j;
        }
        final int ceiling_float_int = MathHelper.ceiling_float_int(this.theEntity.width);
        final int n2 = (int)this.theEntity.height + " ".length();
        final int n3 = ceiling_float_int;
        int k = currentPathLength - " ".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (k >= this.currentPath.getCurrentPathIndex()) {
            if (this.isDirectPathBetweenPoints(entityPosition, this.currentPath.getVectorFromIndex(this.theEntity, k), ceiling_float_int, n2, n3)) {
                this.currentPath.setCurrentPathIndex(k);
                "".length();
                if (false) {
                    throw null;
                }
                break;
            }
            else {
                --k;
            }
        }
        this.checkForStuck(entityPosition);
    }
    
    public PathEntity getPathToPos(final BlockPos blockPos) {
        if (!this.canNavigate()) {
            return null;
        }
        final float pathSearchRange = this.getPathSearchRange();
        this.worldObj.theProfiler.startSection(PathNavigate.I["".length()]);
        final BlockPos blockPos2 = new BlockPos(this.theEntity);
        final int n = (int)(pathSearchRange + 8.0f);
        final PathEntity entityPathTo = this.pathFinder.createEntityPathTo(new ChunkCache(this.worldObj, blockPos2.add(-n, -n, -n), blockPos2.add(n, n, n), "".length()), this.theEntity, blockPos, pathSearchRange);
        this.worldObj.theProfiler.endSection();
        return entityPathTo;
    }
    
    public float getPathSearchRange() {
        return (float)this.pathSearchRange.getAttributeValue();
    }
    
    protected abstract boolean canNavigate();
    
    public boolean tryMoveToXYZ(final double n, final double n2, final double n3, final double n4) {
        return this.setPath(this.getPathToXYZ(MathHelper.floor_double(n), (int)n2, MathHelper.floor_double(n3)), n4);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("=\u0016\u0005\u001c\u0004$\u0019\u0015", "Mwqtb");
        PathNavigate.I[" ".length()] = I("\u001b\u000f:\u0004\t\u0002\u0000*", "knNlo");
    }
    
    public boolean setPath(final PathEntity currentPath, final double speed) {
        if (currentPath == null) {
            this.currentPath = null;
            return "".length() != 0;
        }
        if (!currentPath.isSamePath(this.currentPath)) {
            this.currentPath = currentPath;
        }
        this.removeSunnyPath();
        if (this.currentPath.getCurrentPathLength() == 0) {
            return "".length() != 0;
        }
        this.speed = speed;
        final Vec3 entityPosition = this.getEntityPosition();
        this.ticksAtLastPos = this.totalTicks;
        this.lastPosCheck = entityPosition;
        return " ".length() != 0;
    }
    
    public void onUpdateNavigation() {
        this.totalTicks += " ".length();
        if (!this.noPath()) {
            if (this.canNavigate()) {
                this.pathFollow();
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else if (this.currentPath != null && this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength()) {
                final Vec3 entityPosition = this.getEntityPosition();
                final Vec3 vectorFromIndex = this.currentPath.getVectorFromIndex(this.theEntity, this.currentPath.getCurrentPathIndex());
                if (entityPosition.yCoord > vectorFromIndex.yCoord && !this.theEntity.onGround && MathHelper.floor_double(entityPosition.xCoord) == MathHelper.floor_double(vectorFromIndex.xCoord) && MathHelper.floor_double(entityPosition.zCoord) == MathHelper.floor_double(vectorFromIndex.zCoord)) {
                    this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + " ".length());
                }
            }
            if (!this.noPath()) {
                final Vec3 position = this.currentPath.getPosition(this.theEntity);
                if (position != null) {
                    final AxisAlignedBB expand = new AxisAlignedBB(position.xCoord, position.yCoord, position.zCoord, position.xCoord, position.yCoord, position.zCoord).expand(0.5, 0.5, 0.5);
                    final List<AxisAlignedBB> collidingBoundingBoxes = this.worldObj.getCollidingBoundingBoxes(this.theEntity, expand.addCoord(0.0, -1.0, 0.0));
                    double calculateYOffset = -1.0;
                    final AxisAlignedBB offset = expand.offset(0.0, 1.0, 0.0);
                    final Iterator<AxisAlignedBB> iterator = collidingBoundingBoxes.iterator();
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        calculateYOffset = iterator.next().calculateYOffset(offset, calculateYOffset);
                    }
                    this.theEntity.getMoveHelper().setMoveTo(position.xCoord, position.yCoord + calculateYOffset, position.zCoord, this.speed);
                }
            }
        }
    }
    
    protected boolean isInLiquid() {
        if (!this.theEntity.isInWater() && !this.theEntity.isInLava()) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
}
