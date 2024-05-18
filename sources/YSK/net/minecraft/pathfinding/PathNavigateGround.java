package net.minecraft.pathfinding;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.world.pathfinder.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class PathNavigateGround extends PathNavigate
{
    protected WalkNodeProcessor nodeProcessor;
    private boolean shouldAvoidSun;
    
    public PathNavigateGround(final EntityLiving entityLiving, final World world) {
        super(entityLiving, world);
    }
    
    @Override
    protected void removeSunnyPath() {
        super.removeSunnyPath();
        if (this.shouldAvoidSun) {
            if (this.worldObj.canSeeSky(new BlockPos(MathHelper.floor_double(this.theEntity.posX), (int)(this.theEntity.getEntityBoundingBox().minY + 0.5), MathHelper.floor_double(this.theEntity.posZ)))) {
                return;
            }
            int i = "".length();
            "".length();
            if (1 < 0) {
                throw null;
            }
            while (i < this.currentPath.getCurrentPathLength()) {
                final PathPoint pathPointFromIndex = this.currentPath.getPathPointFromIndex(i);
                if (this.worldObj.canSeeSky(new BlockPos(pathPointFromIndex.xCoord, pathPointFromIndex.yCoord, pathPointFromIndex.zCoord))) {
                    this.currentPath.setCurrentPathLength(i - " ".length());
                    return;
                }
                ++i;
            }
        }
    }
    
    public void setBreakDoors(final boolean breakDoors) {
        this.nodeProcessor.setBreakDoors(breakDoors);
    }
    
    @Override
    protected Vec3 getEntityPosition() {
        return new Vec3(this.theEntity.posX, this.getPathablePosY(), this.theEntity.posZ);
    }
    
    private boolean isPositionClear(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final Vec3 vec3, final double n7, final double n8) {
        final Iterator<BlockPos> iterator = BlockPos.getAllInBox(new BlockPos(n, n2, n3), new BlockPos(n + n4 - " ".length(), n2 + n5 - " ".length(), n3 + n6 - " ".length())).iterator();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final BlockPos blockPos = iterator.next();
            if ((blockPos.getX() + 0.5 - vec3.xCoord) * n7 + (blockPos.getZ() + 0.5 - vec3.zCoord) * n8 >= 0.0 && !this.worldObj.getBlockState(blockPos).getBlock().isPassable(this.worldObj, blockPos)) {
                return "".length() != 0;
            }
        }
        return " ".length() != 0;
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setEnterDoors(final boolean enterDoors) {
        this.nodeProcessor.setEnterDoors(enterDoors);
    }
    
    public void setAvoidSun(final boolean shouldAvoidSun) {
        this.shouldAvoidSun = shouldAvoidSun;
    }
    
    public void setCanSwim(final boolean canSwim) {
        this.nodeProcessor.setCanSwim(canSwim);
    }
    
    @Override
    protected PathFinder getPathFinder() {
        (this.nodeProcessor = new WalkNodeProcessor()).setEnterDoors(" ".length() != 0);
        return new PathFinder(this.nodeProcessor);
    }
    
    private boolean isSafeToStandAt(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final Vec3 vec3, final double n7, final double n8) {
        final int n9 = n - n4 / "  ".length();
        final int n10 = n3 - n6 / "  ".length();
        if (!this.isPositionClear(n9, n2, n10, n4, n5, n6, vec3, n7, n8)) {
            return "".length() != 0;
        }
        int i = n9;
        "".length();
        if (1 >= 2) {
            throw null;
        }
        while (i < n9 + n4) {
            int j = n10;
            "".length();
            if (2 == 1) {
                throw null;
            }
            while (j < n10 + n6) {
                if ((i + 0.5 - vec3.xCoord) * n7 + (j + 0.5 - vec3.zCoord) * n8 >= 0.0) {
                    final Material material = this.worldObj.getBlockState(new BlockPos(i, n2 - " ".length(), j)).getBlock().getMaterial();
                    if (material == Material.air) {
                        return "".length() != 0;
                    }
                    if (material == Material.water && !this.theEntity.isInWater()) {
                        return "".length() != 0;
                    }
                    if (material == Material.lava) {
                        return "".length() != 0;
                    }
                }
                ++j;
            }
            ++i;
        }
        return " ".length() != 0;
    }
    
    public boolean getAvoidsWater() {
        return this.nodeProcessor.getAvoidsWater();
    }
    
    @Override
    protected boolean canNavigate() {
        if (!this.theEntity.onGround && (!this.getCanSwim() || !this.isInLiquid()) && (!this.theEntity.isRiding() || !(this.theEntity instanceof EntityZombie) || !(this.theEntity.ridingEntity instanceof EntityChicken))) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public boolean getCanSwim() {
        return this.nodeProcessor.getCanSwim();
    }
    
    public boolean getEnterDoors() {
        return this.nodeProcessor.getEnterDoors();
    }
    
    @Override
    protected boolean isDirectPathBetweenPoints(final Vec3 vec3, final Vec3 vec4, int n, final int n2, int n3) {
        int floor_double = MathHelper.floor_double(vec3.xCoord);
        int floor_double2 = MathHelper.floor_double(vec3.zCoord);
        final double n4 = vec4.xCoord - vec3.xCoord;
        final double n5 = vec4.zCoord - vec3.zCoord;
        final double n6 = n4 * n4 + n5 * n5;
        if (n6 < 1.0E-8) {
            return "".length() != 0;
        }
        final double n7 = 1.0 / Math.sqrt(n6);
        final double n8 = n4 * n7;
        final double n9 = n5 * n7;
        n += 2;
        n3 += 2;
        if (!this.isSafeToStandAt(floor_double, (int)vec3.yCoord, floor_double2, n, n2, n3, vec3, n8, n9)) {
            return "".length() != 0;
        }
        n -= 2;
        n3 -= 2;
        final double n10 = 1.0 / Math.abs(n8);
        final double n11 = 1.0 / Math.abs(n9);
        double n12 = floor_double * " ".length() - vec3.xCoord;
        double n13 = floor_double2 * " ".length() - vec3.zCoord;
        if (n8 >= 0.0) {
            ++n12;
        }
        if (n9 >= 0.0) {
            ++n13;
        }
        double n14 = n12 / n8;
        double n15 = n13 / n9;
        int length;
        if (n8 < 0.0) {
            length = -" ".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            length = " ".length();
        }
        final int n16 = length;
        int length2;
        if (n9 < 0.0) {
            length2 = -" ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            length2 = " ".length();
        }
        final int n17 = length2;
        final int floor_double3 = MathHelper.floor_double(vec4.xCoord);
        final int floor_double4 = MathHelper.floor_double(vec4.zCoord);
        int n18 = floor_double3 - floor_double;
        int n19 = floor_double4 - floor_double2;
        "".length();
        if (1 <= 0) {
            throw null;
        }
        while (n18 * n16 > 0 || n19 * n17 > 0) {
            if (n14 < n15) {
                n14 += n10;
                floor_double += n16;
                n18 = floor_double3 - floor_double;
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                n15 += n11;
                floor_double2 += n17;
                n19 = floor_double4 - floor_double2;
            }
            if (!this.isSafeToStandAt(floor_double, (int)vec3.yCoord, floor_double2, n, n2, n3, vec3, n8, n9)) {
                return "".length() != 0;
            }
        }
        return " ".length() != 0;
    }
    
    public void setAvoidsWater(final boolean avoidsWater) {
        this.nodeProcessor.setAvoidsWater(avoidsWater);
    }
    
    private int getPathablePosY() {
        if (!this.theEntity.isInWater() || !this.getCanSwim()) {
            return (int)(this.theEntity.getEntityBoundingBox().minY + 0.5);
        }
        int n = (int)this.theEntity.getEntityBoundingBox().minY;
        Block block = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.theEntity.posX), n, MathHelper.floor_double(this.theEntity.posZ))).getBlock();
        int length = "".length();
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (block == Blocks.flowing_water || block == Blocks.water) {
            ++n;
            block = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.theEntity.posX), n, MathHelper.floor_double(this.theEntity.posZ))).getBlock();
            if (++length > (0xA7 ^ 0xB7)) {
                return (int)this.theEntity.getEntityBoundingBox().minY;
            }
        }
        return n;
    }
}
