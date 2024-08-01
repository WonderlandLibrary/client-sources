package wtf.diablo.client.pathfinding.api;

import net.minecraft.block.*;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class PathNode {

    public static final int[][] NEIGHBOR_OFFSETS = {
            {-1, -1, -1, 3},
            {-1, -1, 0, 2},
            {-1, -1, 1, 3},
            {-1, 0, -1, 2},
            {-1, 0, 0, 1},
            {-1, 0, 1, 2},
            {-1, 1, -1, 3},
            {-1, 1, 0, 2},
            {-1, 1, 1, 3},
            {0, -1, -1, 2},
            {0, -1, 1, 2},
            {0, 0, -1, 1},
            {0, 0, 1, 1},
            {0, 1, -1, 2},
            {0, 1, 1, 2},
            {1, -1, -1, 3},
            {1, -1, 0, 2},
            {1, -1, 1, 3},
            {1, 0, -1, 2},
            {1, 0, 0, 1},
            {1, 0, 1, 2},
            {1, 1, -1, 3},
            {1, 1, 0, 2},
            {1, 1, 1, 3}};

    private float gScore = Float.POSITIVE_INFINITY;
    private float hScore = Float.POSITIVE_INFINITY;

    private final boolean legit;

    private final int hashCode;

    protected final int posX;
    protected final int posY;
    protected final int posZ;

    public boolean isAccessible(final WorldClient theWorld)
    {
        final BlockPos lower = new BlockPos(this.posX, this.posY, this.posZ);
        final BlockPos upper = lower.add(0, 1, 0);
        final BlockPos below = lower.add(0, -1, 0);

        return isBlockPassable(theWorld, lower) && isBlockPassable(theWorld, upper) && (!isBlockPassable(theWorld, below) || !legit);
    }

    public boolean isBlockPassable(final WorldClient theWorld, final BlockPos blockPos)
    {
        boolean passable = theWorld.isAirBlock(blockPos);
        if(!theWorld.isAirBlock(blockPos))
        {
            final Block block = theWorld.getBlockState(blockPos).getBlock();

            if(block instanceof BlockAir || (block instanceof BlockLiquid && theWorld.getBlockState(new BlockPos(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ())).getBlock() instanceof BlockAir) || block instanceof BlockTallGrass || block instanceof BlockVine)
                return true;
        }

        return passable;
    }

    public PathNode(final int x, final int y, final int z, final boolean legit)
    {
        this.hashCode = positionHash(x, y, z);

        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.legit = legit;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj instanceof PathNode) {
            final PathNode other = (PathNode) obj;

            return this.hashCode == other.hashCode;
        }

        return obj.equals(this);
    }

    @Override
    public int hashCode()
    {
        return this.hashCode;
    }

    /**
     * Custom bijective hash function for blocks in a 2^12 by 2^12 block square
     */
    public static int positionHash(final int x, final int y, final int z)
    {
        int xComp = x & 0b111111111111;
        int yComp = (y & 0b11111111) << 12;
        int zComp = (z & 0b111111111111) << 20;

        return xComp | yComp | zComp;
    }

    public float getFScore()
    {
        return this.gScore + this.hScore;
    }

    public Vec3 getCenter()
    {
        return new Vec3((float) this.posX + 0.5F, (float) this.posY + 0.5F, (float) this.posZ + 0.5F);
    }

    public BlockPos getPosition()
    {
        return new BlockPos(this.posX, this.posY, this.posZ);
    }

    public void setGScore(final float gScore)
    {
        this.gScore = gScore;
    }

    public void setHScore(final float hScore)
    {
        this.hScore = hScore;
    }

    public int getPosX()
    {
        return this.posX;
    }

    public int getPosY()
    {
        return this.posY;
    }

    public int getPosZ()
    {
        return this.posZ;
    }

    public float getGScore()
    {
        return this.gScore;
    }

    public float getHScore()
    {
        return this.hScore;
    }

}