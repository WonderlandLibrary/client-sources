package net.minecraft.world.pathfinder;

import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.util.*;

public class SwimNodeProcessor extends NodeProcessor
{
    private int func_176186_b(final Entity entity, final int n, final int n2, final int n3) {
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int i = n;
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (i < n + this.entitySizeX) {
            int j = n2;
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (j < n2 + this.entitySizeY) {
                int k = n3;
                "".length();
                if (2 < 1) {
                    throw null;
                }
                while (k < n3 + this.entitySizeZ) {
                    if (this.blockaccess.getBlockState(mutableBlockPos.func_181079_c(i, j, k)).getBlock().getMaterial() != Material.water) {
                        return "".length();
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        return -" ".length();
    }
    
    @Override
    public void postProcess() {
        super.postProcess();
    }
    
    @Override
    public void initProcessor(final IBlockAccess blockAccess, final Entity entity) {
        super.initProcessor(blockAccess, entity);
    }
    
    private PathPoint getSafePoint(final Entity entity, final int n, final int n2, final int n3) {
        PathPoint openPoint;
        if (this.func_176186_b(entity, n, n2, n3) == -" ".length()) {
            openPoint = this.openPoint(n, n2, n3);
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else {
            openPoint = null;
        }
        return openPoint;
    }
    
    @Override
    public int findPathOptions(final PathPoint[] array, final Entity entity, final PathPoint pathPoint, final PathPoint pathPoint2, final float n) {
        int length = "".length();
        final EnumFacing[] values;
        final int length2 = (values = EnumFacing.values()).length;
        int i = "".length();
        "".length();
        if (4 <= 0) {
            throw null;
        }
        while (i < length2) {
            final EnumFacing enumFacing = values[i];
            final PathPoint safePoint = this.getSafePoint(entity, pathPoint.xCoord + enumFacing.getFrontOffsetX(), pathPoint.yCoord + enumFacing.getFrontOffsetY(), pathPoint.zCoord + enumFacing.getFrontOffsetZ());
            if (safePoint != null && !safePoint.visited && safePoint.distanceTo(pathPoint2) < n) {
                array[length++] = safePoint;
            }
            ++i;
        }
        return length;
    }
    
    @Override
    public PathPoint getPathPointTo(final Entity entity) {
        return this.openPoint(MathHelper.floor_double(entity.getEntityBoundingBox().minX), MathHelper.floor_double(entity.getEntityBoundingBox().minY + 0.5), MathHelper.floor_double(entity.getEntityBoundingBox().minZ));
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public PathPoint getPathPointToCoords(final Entity entity, final double n, final double n2, final double n3) {
        return this.openPoint(MathHelper.floor_double(n - entity.width / 2.0f), MathHelper.floor_double(n2 + 0.5), MathHelper.floor_double(n3 - entity.width / 2.0f));
    }
}
