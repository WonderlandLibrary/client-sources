/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.tileentity.TileEntityChest;
/*  8:   */ import net.minecraft.util.WeightedRandomChestContent;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class WorldGeneratorBonusChest
/* 12:   */   extends WorldGenerator
/* 13:   */ {
/* 14:   */   private final WeightedRandomChestContent[] theBonusChestGenerator;
/* 15:   */   private final int itemsToGenerateInBonusChest;
/* 16:   */   private static final String __OBFID = "CL_00000403";
/* 17:   */   
/* 18:   */   public WorldGeneratorBonusChest(WeightedRandomChestContent[] par1ArrayOfWeightedRandomChestContent, int par2)
/* 19:   */   {
/* 20:26 */     this.theBonusChestGenerator = par1ArrayOfWeightedRandomChestContent;
/* 21:27 */     this.itemsToGenerateInBonusChest = par2;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 25:   */   {
/* 26:   */     Block var6;
/* 27:34 */     while ((((var6 = par1World.getBlock(par3, par4, par5)).getMaterial() == Material.air) || (var6.getMaterial() == Material.leaves)) && (par4 > 1))
/* 28:   */     {
/* 29:   */       Block var6;
/* 30:36 */       par4--;
/* 31:   */     }
/* 32:39 */     if (par4 < 1) {
/* 33:41 */       return false;
/* 34:   */     }
/* 35:45 */     par4++;
/* 36:47 */     for (int var7 = 0; var7 < 4; var7++)
/* 37:   */     {
/* 38:49 */       int var8 = par3 + par2Random.nextInt(4) - par2Random.nextInt(4);
/* 39:50 */       int var9 = par4 + par2Random.nextInt(3) - par2Random.nextInt(3);
/* 40:51 */       int var10 = par5 + par2Random.nextInt(4) - par2Random.nextInt(4);
/* 41:53 */       if ((par1World.isAirBlock(var8, var9, var10)) && (World.doesBlockHaveSolidTopSurface(par1World, var8, var9 - 1, var10)))
/* 42:   */       {
/* 43:55 */         par1World.setBlock(var8, var9, var10, Blocks.chest, 0, 2);
/* 44:56 */         TileEntityChest var11 = (TileEntityChest)par1World.getTileEntity(var8, var9, var10);
/* 45:58 */         if ((var11 != null) && (var11 != null)) {
/* 46:60 */           WeightedRandomChestContent.generateChestContents(par2Random, this.theBonusChestGenerator, var11, this.itemsToGenerateInBonusChest);
/* 47:   */         }
/* 48:63 */         if ((par1World.isAirBlock(var8 - 1, var9, var10)) && (World.doesBlockHaveSolidTopSurface(par1World, var8 - 1, var9 - 1, var10))) {
/* 49:65 */           par1World.setBlock(var8 - 1, var9, var10, Blocks.torch, 0, 2);
/* 50:   */         }
/* 51:68 */         if ((par1World.isAirBlock(var8 + 1, var9, var10)) && (World.doesBlockHaveSolidTopSurface(par1World, var8 - 1, var9 - 1, var10))) {
/* 52:70 */           par1World.setBlock(var8 + 1, var9, var10, Blocks.torch, 0, 2);
/* 53:   */         }
/* 54:73 */         if ((par1World.isAirBlock(var8, var9, var10 - 1)) && (World.doesBlockHaveSolidTopSurface(par1World, var8 - 1, var9 - 1, var10))) {
/* 55:75 */           par1World.setBlock(var8, var9, var10 - 1, Blocks.torch, 0, 2);
/* 56:   */         }
/* 57:78 */         if ((par1World.isAirBlock(var8, var9, var10 + 1)) && (World.doesBlockHaveSolidTopSurface(par1World, var8 - 1, var9 - 1, var10))) {
/* 58:80 */           par1World.setBlock(var8, var9, var10 + 1, Blocks.torch, 0, 2);
/* 59:   */         }
/* 60:83 */         return true;
/* 61:   */       }
/* 62:   */     }
/* 63:87 */     return false;
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGeneratorBonusChest
 * JD-Core Version:    0.7.0.1
 */