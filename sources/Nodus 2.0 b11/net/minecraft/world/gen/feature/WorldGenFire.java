/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.init.Blocks;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public class WorldGenFire
/*  8:   */   extends WorldGenerator
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00000412";
/* 11:   */   
/* 12:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 13:   */   {
/* 14:13 */     for (int var6 = 0; var6 < 64; var6++)
/* 15:   */     {
/* 16:15 */       int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 17:16 */       int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
/* 18:17 */       int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 19:19 */       if ((par1World.isAirBlock(var7, var8, var9)) && (par1World.getBlock(var7, var8 - 1, var9) == Blocks.netherrack)) {
/* 20:21 */         par1World.setBlock(var7, var8, var9, Blocks.fire, 0, 2);
/* 21:   */       }
/* 22:   */     }
/* 23:25 */     return true;
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenFire
 * JD-Core Version:    0.7.0.1
 */