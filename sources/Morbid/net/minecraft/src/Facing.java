package net.minecraft.src;

public class Facing
{
    public static final int[] oppositeSide;
    public static final int[] offsetsXForSide;
    public static final int[] offsetsYForSide;
    public static final int[] offsetsZForSide;
    public static final String[] facings;
    
    static {
        oppositeSide = new int[] { 1, 0, 3, 2, 5, 4 };
        offsetsXForSide = new int[] { 0, 0, 0, 0, -1, 1 };
        final int[] offsetsYForSide2 = new int[6];
        offsetsYForSide2[0] = -1;
        offsetsYForSide2[1] = 1;
        offsetsYForSide = offsetsYForSide2;
        offsetsZForSide = new int[] { 0, 0, -1, 1, 0, 0 };
        facings = new String[] { "DOWN", "UP", "NORTH", "SOUTH", "WEST", "EAST" };
    }
}
