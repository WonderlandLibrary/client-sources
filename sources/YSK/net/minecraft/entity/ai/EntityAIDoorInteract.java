package net.minecraft.entity.ai;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;

public abstract class EntityAIDoorInteract extends EntityAIBase
{
    protected BlockDoor doorBlock;
    boolean hasStoppedDoorInteraction;
    protected BlockPos doorPosition;
    float entityPositionX;
    protected EntityLiving theEntity;
    private static final String[] I;
    float entityPositionZ;
    
    @Override
    public void startExecuting() {
        this.hasStoppedDoorInteraction = ("".length() != 0);
        this.entityPositionX = (float)(this.doorPosition.getX() + 0.5f - this.theEntity.posX);
        this.entityPositionZ = (float)(this.doorPosition.getZ() + 0.5f - this.theEntity.posZ);
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0012\u001b\u0004\u0002<7\u001a\u0005\u0003)#U\u001a\u0018.g\u0001\u000e\u0007)g\u0013\u0018\u0005l\u0003\u001a\u0018\u0005\u0005)\u0001\u0012\u0005-$\u00010\u0018-+", "GuwwL");
    }
    
    @Override
    public boolean continueExecuting() {
        int n;
        if (this.hasStoppedDoorInteraction) {
            n = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theEntity.isCollidedHorizontally) {
            return "".length() != 0;
        }
        final PathNavigateGround pathNavigateGround = (PathNavigateGround)this.theEntity.getNavigator();
        final PathEntity path = pathNavigateGround.getPath();
        if (path == null || path.isFinished() || !pathNavigateGround.getEnterDoors()) {
            return "".length() != 0;
        }
        int i = "".length();
        "".length();
        if (-1 == 1) {
            throw null;
        }
        while (i < Math.min(path.getCurrentPathIndex() + "  ".length(), path.getCurrentPathLength())) {
            final PathPoint pathPointFromIndex = path.getPathPointFromIndex(i);
            this.doorPosition = new BlockPos(pathPointFromIndex.xCoord, pathPointFromIndex.yCoord + " ".length(), pathPointFromIndex.zCoord);
            if (this.theEntity.getDistanceSq(this.doorPosition.getX(), this.theEntity.posY, this.doorPosition.getZ()) <= 2.25) {
                this.doorBlock = this.getBlockDoor(this.doorPosition);
                if (this.doorBlock != null) {
                    return " ".length() != 0;
                }
            }
            ++i;
        }
        this.doorPosition = new BlockPos(this.theEntity).up();
        this.doorBlock = this.getBlockDoor(this.doorPosition);
        if (this.doorBlock != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private BlockDoor getBlockDoor(final BlockPos blockPos) {
        final Block block = this.theEntity.worldObj.getBlockState(blockPos).getBlock();
        BlockDoor blockDoor;
        if (block instanceof BlockDoor && block.getMaterial() == Material.wood) {
            blockDoor = (BlockDoor)block;
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        else {
            blockDoor = null;
        }
        return blockDoor;
    }
    
    public EntityAIDoorInteract(final EntityLiving theEntity) {
        this.doorPosition = BlockPos.ORIGIN;
        this.theEntity = theEntity;
        if (!(theEntity.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException(EntityAIDoorInteract.I["".length()]);
        }
    }
    
    @Override
    public void updateTask() {
        if (this.entityPositionX * (float)(this.doorPosition.getX() + 0.5f - this.theEntity.posX) + this.entityPositionZ * (float)(this.doorPosition.getZ() + 0.5f - this.theEntity.posZ) < 0.0f) {
            this.hasStoppedDoorInteraction = (" ".length() != 0);
        }
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
            if (2 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
