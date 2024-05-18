package net.minecraft.world.pathfinder;

import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;

public class WalkNodeProcessor extends NodeProcessor
{
    private boolean avoidsWater;
    private boolean canEnterDoors;
    private boolean canSwim;
    private boolean canBreakDoors;
    private boolean shouldAvoidWater;
    
    @Override
    public PathPoint getPathPointTo(final Entity entity) {
        int floor_double;
        if (this.canSwim && entity.isInWater()) {
            floor_double = (int)entity.getEntityBoundingBox().minY;
            final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(MathHelper.floor_double(entity.posX), floor_double, MathHelper.floor_double(entity.posZ));
            Block block = this.blockaccess.getBlockState(mutableBlockPos).getBlock();
            "".length();
            if (0 <= -1) {
                throw null;
            }
            while (block == Blocks.flowing_water || block == Blocks.water) {
                ++floor_double;
                mutableBlockPos.func_181079_c(MathHelper.floor_double(entity.posX), floor_double, MathHelper.floor_double(entity.posZ));
                block = this.blockaccess.getBlockState(mutableBlockPos).getBlock();
            }
            this.avoidsWater = ("".length() != 0);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            floor_double = MathHelper.floor_double(entity.getEntityBoundingBox().minY + 0.5);
        }
        return this.openPoint(MathHelper.floor_double(entity.getEntityBoundingBox().minX), floor_double, MathHelper.floor_double(entity.getEntityBoundingBox().minZ));
    }
    
    public void setEnterDoors(final boolean canEnterDoors) {
        this.canEnterDoors = canEnterDoors;
    }
    
    @Override
    public int findPathOptions(final PathPoint[] array, final Entity entity, final PathPoint pathPoint, final PathPoint pathPoint2, final float n) {
        int length = "".length();
        int n2 = "".length();
        if (this.getVerticalOffset(entity, pathPoint.xCoord, pathPoint.yCoord + " ".length(), pathPoint.zCoord) == " ".length()) {
            n2 = " ".length();
        }
        final PathPoint safePoint = this.getSafePoint(entity, pathPoint.xCoord, pathPoint.yCoord, pathPoint.zCoord + " ".length(), n2);
        final PathPoint safePoint2 = this.getSafePoint(entity, pathPoint.xCoord - " ".length(), pathPoint.yCoord, pathPoint.zCoord, n2);
        final PathPoint safePoint3 = this.getSafePoint(entity, pathPoint.xCoord + " ".length(), pathPoint.yCoord, pathPoint.zCoord, n2);
        final PathPoint safePoint4 = this.getSafePoint(entity, pathPoint.xCoord, pathPoint.yCoord, pathPoint.zCoord - " ".length(), n2);
        if (safePoint != null && !safePoint.visited && safePoint.distanceTo(pathPoint2) < n) {
            array[length++] = safePoint;
        }
        if (safePoint2 != null && !safePoint2.visited && safePoint2.distanceTo(pathPoint2) < n) {
            array[length++] = safePoint2;
        }
        if (safePoint3 != null && !safePoint3.visited && safePoint3.distanceTo(pathPoint2) < n) {
            array[length++] = safePoint3;
        }
        if (safePoint4 != null && !safePoint4.visited && safePoint4.distanceTo(pathPoint2) < n) {
            array[length++] = safePoint4;
        }
        return length;
    }
    
    public static int func_176170_a(final IBlockAccess blockAccess, final Entity entity, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b, final boolean b2, final boolean b3) {
        int n7 = "".length();
        final BlockPos blockPos = new BlockPos(entity);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int i = n;
        "".length();
        if (2 <= 0) {
            throw null;
        }
        while (i < n + n4) {
            int j = n2;
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (j < n2 + n5) {
                int k = n3;
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                while (k < n3 + n6) {
                    mutableBlockPos.func_181079_c(i, j, k);
                    final Block block = blockAccess.getBlockState(mutableBlockPos).getBlock();
                    if (block.getMaterial() != Material.air) {
                        if (block != Blocks.trapdoor && block != Blocks.iron_trapdoor) {
                            if (block != Blocks.flowing_water && block != Blocks.water) {
                                if (!b3 && block instanceof BlockDoor && block.getMaterial() == Material.wood) {
                                    return "".length();
                                }
                            }
                            else {
                                if (b) {
                                    return -" ".length();
                                }
                                n7 = " ".length();
                                "".length();
                                if (1 >= 3) {
                                    throw null;
                                }
                            }
                        }
                        else {
                            n7 = " ".length();
                        }
                        if (entity.worldObj.getBlockState(mutableBlockPos).getBlock() instanceof BlockRailBase) {
                            if (!(entity.worldObj.getBlockState(blockPos).getBlock() instanceof BlockRailBase) && !(entity.worldObj.getBlockState(blockPos.down()).getBlock() instanceof BlockRailBase)) {
                                return -"   ".length();
                            }
                        }
                        else if (!block.isPassable(blockAccess, mutableBlockPos) && (!b2 || !(block instanceof BlockDoor) || block.getMaterial() != Material.wood)) {
                            if (block instanceof BlockFence || block instanceof BlockFenceGate || block instanceof BlockWall) {
                                return -"   ".length();
                            }
                            if (block == Blocks.trapdoor || block == Blocks.iron_trapdoor) {
                                return -(0xB6 ^ 0xB2);
                            }
                            if (block.getMaterial() != Material.lava) {
                                return "".length();
                            }
                            if (!entity.isInLava()) {
                                return -"  ".length();
                            }
                        }
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        int n8;
        if (n7 != 0) {
            n8 = "  ".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            n8 = " ".length();
        }
        return n8;
    }
    
    public boolean getEnterDoors() {
        return this.canEnterDoors;
    }
    
    public boolean getCanSwim() {
        return this.canSwim;
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
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private int getVerticalOffset(final Entity entity, final int n, final int n2, final int n3) {
        return func_176170_a(this.blockaccess, entity, n, n2, n3, this.entitySizeX, this.entitySizeY, this.entitySizeZ, this.avoidsWater, this.canBreakDoors, this.canEnterDoors);
    }
    
    public void setAvoidsWater(final boolean avoidsWater) {
        this.avoidsWater = avoidsWater;
    }
    
    public void setCanSwim(final boolean canSwim) {
        this.canSwim = canSwim;
    }
    
    public void setBreakDoors(final boolean canBreakDoors) {
        this.canBreakDoors = canBreakDoors;
    }
    
    @Override
    public void postProcess() {
        super.postProcess();
        this.avoidsWater = this.shouldAvoidWater;
    }
    
    private PathPoint getSafePoint(final Entity entity, final int n, int i, final int n2, final int n3) {
        PathPoint pathPoint = null;
        final int verticalOffset = this.getVerticalOffset(entity, n, i, n2);
        if (verticalOffset == "  ".length()) {
            return this.openPoint(n, i, n2);
        }
        if (verticalOffset == " ".length()) {
            pathPoint = this.openPoint(n, i, n2);
        }
        if (pathPoint == null && n3 > 0 && verticalOffset != -"   ".length() && verticalOffset != -(0x5 ^ 0x1) && this.getVerticalOffset(entity, n, i + n3, n2) == " ".length()) {
            pathPoint = this.openPoint(n, i + n3, n2);
            i += n3;
        }
        if (pathPoint != null) {
            int length = "".length();
            int n4 = "".length();
            "".length();
            if (2 == 1) {
                throw null;
            }
            while (i > 0) {
                n4 = this.getVerticalOffset(entity, n, i - " ".length(), n2);
                if (this.avoidsWater && n4 == -" ".length()) {
                    return null;
                }
                if (n4 != " ".length()) {
                    "".length();
                    if (4 <= 2) {
                        throw null;
                    }
                    break;
                }
                else {
                    if (length++ >= entity.getMaxFallHeight()) {
                        return null;
                    }
                    if (--i <= 0) {
                        return null;
                    }
                    pathPoint = this.openPoint(n, i, n2);
                }
            }
            if (n4 == -"  ".length()) {
                return null;
            }
        }
        return pathPoint;
    }
    
    public boolean getAvoidsWater() {
        return this.avoidsWater;
    }
    
    @Override
    public PathPoint getPathPointToCoords(final Entity entity, final double n, final double n2, final double n3) {
        return this.openPoint(MathHelper.floor_double(n - entity.width / 2.0f), MathHelper.floor_double(n2), MathHelper.floor_double(n3 - entity.width / 2.0f));
    }
    
    @Override
    public void initProcessor(final IBlockAccess blockAccess, final Entity entity) {
        super.initProcessor(blockAccess, entity);
        this.shouldAvoidWater = this.avoidsWater;
    }
}
