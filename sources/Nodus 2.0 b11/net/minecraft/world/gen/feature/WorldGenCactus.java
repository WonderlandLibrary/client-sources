/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ 
/*  8:   */ public class WorldGenCactus
/*  9:   */   extends WorldGenerator
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00000404";
/* 12:   */   
/* 13:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 14:   */   {
/* 15:13 */     for (int var6 = 0; var6 < 10; var6++)
/* 16:   */     {
/* 17:15 */       int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 18:16 */       int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
/* 19:17 */       int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 20:19 */       if (par1World.isAirBlock(var7, var8, var9))
/* 21:   */       {
/* 22:21 */         int var10 = 1 + par2Random.nextInt(par2Random.nextInt(3) + 1);
/* 23:23 */         for (int var11 = 0; var11 < var10; var11++) {
/* 24:25 */           if (Blocks.cactus.canBlockStay(par1World, var7, var8 + var11, var9)) {
/* 25:27 */             par1World.setBlock(var7, var8 + var11, var9, Blocks.cactus, 0, 2);
/* 26:   */           }
/* 27:   */         }
/* 28:   */       }
/* 29:   */     }
/* 30:33 */     return true;
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenCactus
 * JD-Core Version:    0.7.0.1
 */