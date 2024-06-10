/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ 
/*  8:   */ public class WorldGenVines
/*  9:   */   extends WorldGenerator
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00000439";
/* 12:   */   
/* 13:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 14:   */   {
/* 15:15 */     int var6 = par3;
/* 16:17 */     for (int var7 = par5; par4 < 128; par4++) {
/* 17:19 */       if (par1World.isAirBlock(par3, par4, par5))
/* 18:   */       {
/* 19:21 */         for (int var8 = 2; var8 <= 5; var8++) {
/* 20:23 */           if (Blocks.vine.canPlaceBlockOnSide(par1World, par3, par4, par5, var8))
/* 21:   */           {
/* 22:25 */             par1World.setBlock(par3, par4, par5, Blocks.vine, 1 << net.minecraft.util.Direction.facingToDirection[net.minecraft.util.Facing.oppositeSide[var8]], 2);
/* 23:26 */             break;
/* 24:   */           }
/* 25:   */         }
/* 26:   */       }
/* 27:   */       else
/* 28:   */       {
/* 29:32 */         par3 = var6 + par2Random.nextInt(4) - par2Random.nextInt(4);
/* 30:33 */         par5 = var7 + par2Random.nextInt(4) - par2Random.nextInt(4);
/* 31:   */       }
/* 32:   */     }
/* 33:37 */     return true;
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenVines
 * JD-Core Version:    0.7.0.1
 */