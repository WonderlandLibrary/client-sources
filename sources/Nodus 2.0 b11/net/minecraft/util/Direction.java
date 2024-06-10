/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ public class Direction
/*  4:   */ {
/*  5: 5 */   public static final int[] offsetX = { 0, -1, 01 };
/*  6: 6 */   public static final int[] offsetZ = { 1, 0-1 };
/*  7: 7 */   public static final String[] directions = { "SOUTH", "WEST", "NORTH", "EAST" };
/*  8:10 */   public static final int[] directionToFacing = { 3, 4, 2, 5 };
/*  9:13 */   public static final int[] facingToDirection = { -1, -1, 2, 0, 1, 3 };
/* 10:16 */   public static final int[] rotateOpposite = { 2, 3, 01 };
/* 11:19 */   public static final int[] rotateRight = { 1, 2, 3 };
/* 12:22 */   public static final int[] rotateLeft = { 3, 0, 1, 2 };
/* 13:23 */   public static final int[][] bedDirection = { { 1, 0, 3, 2, 5, 4 }, { 1, 0, 5, 4, 2, 3 }, { 1, 0, 2, 3, 4, 5 }, { 1, 0, 4, 5, 3, 2 } };
/* 14:   */   private static final String __OBFID = "CL_00001506";
/* 15:   */   
/* 16:   */   public static int getMovementDirection(double par0, double par2)
/* 17:   */   {
/* 18:31 */     return par2 > 0.0D ? 2 : MathHelper.abs((float)par0) > MathHelper.abs((float)par2) ? 3 : par0 > 0.0D ? 1 : 0;
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.Direction
 * JD-Core Version:    0.7.0.1
 */