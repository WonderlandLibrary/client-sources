/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ 
/*  8:   */ public class WorldGenDeadBush
/*  9:   */   extends WorldGenerator
/* 10:   */ {
/* 11:   */   private Block field_150547_a;
/* 12:   */   private static final String __OBFID = "CL_00000406";
/* 13:   */   
/* 14:   */   public WorldGenDeadBush(Block p_i45451_1_)
/* 15:   */   {
/* 16:15 */     this.field_150547_a = p_i45451_1_;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 20:   */   {
/* 21:   */     Block var6;
/* 22:22 */     while ((((var6 = par1World.getBlock(par3, par4, par5)).getMaterial() == Material.air) || (var6.getMaterial() == Material.leaves)) && (par4 > 0))
/* 23:   */     {
/* 24:   */       Block var6;
/* 25:24 */       par4--;
/* 26:   */     }
/* 27:27 */     for (int var7 = 0; var7 < 4; var7++)
/* 28:   */     {
/* 29:29 */       int var8 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 30:30 */       int var9 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
/* 31:31 */       int var10 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 32:33 */       if ((par1World.isAirBlock(var8, var9, var10)) && (this.field_150547_a.canBlockStay(par1World, var8, var9, var10))) {
/* 33:35 */         par1World.setBlock(var8, var9, var10, this.field_150547_a, 0, 2);
/* 34:   */       }
/* 35:   */     }
/* 36:39 */     return true;
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenDeadBush
 * JD-Core Version:    0.7.0.1
 */