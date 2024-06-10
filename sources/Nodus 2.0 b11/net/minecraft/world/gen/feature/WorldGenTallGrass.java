/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ 
/*  8:   */ public class WorldGenTallGrass
/*  9:   */   extends WorldGenerator
/* 10:   */ {
/* 11:   */   private Block field_150522_a;
/* 12:   */   private int tallGrassMetadata;
/* 13:   */   private static final String __OBFID = "CL_00000437";
/* 14:   */   
/* 15:   */   public WorldGenTallGrass(Block p_i45466_1_, int p_i45466_2_)
/* 16:   */   {
/* 17:16 */     this.field_150522_a = p_i45466_1_;
/* 18:17 */     this.tallGrassMetadata = p_i45466_2_;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 22:   */   {
/* 23:   */     Block var6;
/* 24:24 */     while ((((var6 = par1World.getBlock(par3, par4, par5)).getMaterial() == Material.air) || (var6.getMaterial() == Material.leaves)) && (par4 > 0))
/* 25:   */     {
/* 26:   */       Block var6;
/* 27:26 */       par4--;
/* 28:   */     }
/* 29:29 */     for (int var7 = 0; var7 < 128; var7++)
/* 30:   */     {
/* 31:31 */       int var8 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 32:32 */       int var9 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
/* 33:33 */       int var10 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 34:35 */       if ((par1World.isAirBlock(var8, var9, var10)) && (this.field_150522_a.canBlockStay(par1World, var8, var9, var10))) {
/* 35:37 */         par1World.setBlock(var8, var9, var10, this.field_150522_a, this.tallGrassMetadata, 2);
/* 36:   */       }
/* 37:   */     }
/* 38:41 */     return true;
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenTallGrass
 * JD-Core Version:    0.7.0.1
 */