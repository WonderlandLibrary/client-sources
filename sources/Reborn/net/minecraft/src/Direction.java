package net.minecraft.src;

public class Direction
{
    public static final int[] offsetX;
    public static final int[] offsetZ;
    public static final String[] directions;
    public static final int[] directionToFacing;
    public static final int[] facingToDirection;
    public static final int[] rotateOpposite;
    public static final int[] rotateRight;
    public static final int[] rotateLeft;
    public static final int[][] bedDirection;
    
    static {
        offsetX = new int[] { 0, -1, 0, 1 };
        offsetZ = new int[] { 1, 0, -1, 0 };
        directions = new String[] { "SOUTH", "WEST", "NORTH", "EAST" };
        directionToFacing = new int[] { 3, 4, 2, 5 };
        facingToDirection = new int[] { -1, -1, 2, 0, 1, 3 };
        rotateOpposite = new int[] { 2, 3, 0, 1 };
        rotateRight = new int[] { 1, 2, 3, 0 };
        rotateLeft = new int[] { 3, 0, 1, 2 };
        bedDirection = new int[][] { { 1, 0, 3, 2, 5, 4 }, { 1, 0, 5, 4, 2, 3 }, { 1, 0, 2, 3, 4, 5 }, { 1, 0, 4, 5, 3, 2 } };
    }
    
    public static int getMovementDirection(final double par0, final double par2) {
        return (MathHelper.abs((float)par0) > MathHelper.abs((float)par2)) ? ((par0 > 0.0) ? 1 : 3) : ((par2 > 0.0) ? 2 : 0);
    }
}
