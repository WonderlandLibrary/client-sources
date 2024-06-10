/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class WorldGenLiquids
/* 10:   */   extends WorldGenerator
/* 11:   */ {
/* 12:   */   private Block field_150521_a;
/* 13:   */   private static final String __OBFID = "CL_00000434";
/* 14:   */   
/* 15:   */   public WorldGenLiquids(Block p_i45465_1_)
/* 16:   */   {
/* 17:16 */     this.field_150521_a = p_i45465_1_;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 21:   */   {
/* 22:21 */     if (par1World.getBlock(par3, par4 + 1, par5) != Blocks.stone) {
/* 23:23 */       return false;
/* 24:   */     }
/* 25:25 */     if (par1World.getBlock(par3, par4 - 1, par5) != Blocks.stone) {
/* 26:27 */       return false;
/* 27:   */     }
/* 28:29 */     if ((par1World.getBlock(par3, par4, par5).getMaterial() != Material.air) && (par1World.getBlock(par3, par4, par5) != Blocks.stone)) {
/* 29:31 */       return false;
/* 30:   */     }
/* 31:35 */     int var6 = 0;
/* 32:37 */     if (par1World.getBlock(par3 - 1, par4, par5) == Blocks.stone) {
/* 33:39 */       var6++;
/* 34:   */     }
/* 35:42 */     if (par1World.getBlock(par3 + 1, par4, par5) == Blocks.stone) {
/* 36:44 */       var6++;
/* 37:   */     }
/* 38:47 */     if (par1World.getBlock(par3, par4, par5 - 1) == Blocks.stone) {
/* 39:49 */       var6++;
/* 40:   */     }
/* 41:52 */     if (par1World.getBlock(par3, par4, par5 + 1) == Blocks.stone) {
/* 42:54 */       var6++;
/* 43:   */     }
/* 44:57 */     int var7 = 0;
/* 45:59 */     if (par1World.isAirBlock(par3 - 1, par4, par5)) {
/* 46:61 */       var7++;
/* 47:   */     }
/* 48:64 */     if (par1World.isAirBlock(par3 + 1, par4, par5)) {
/* 49:66 */       var7++;
/* 50:   */     }
/* 51:69 */     if (par1World.isAirBlock(par3, par4, par5 - 1)) {
/* 52:71 */       var7++;
/* 53:   */     }
/* 54:74 */     if (par1World.isAirBlock(par3, par4, par5 + 1)) {
/* 55:76 */       var7++;
/* 56:   */     }
/* 57:79 */     if ((var6 == 3) && (var7 == 1))
/* 58:   */     {
/* 59:81 */       par1World.setBlock(par3, par4, par5, this.field_150521_a, 0, 2);
/* 60:82 */       par1World.scheduledUpdatesAreImmediate = true;
/* 61:83 */       this.field_150521_a.updateTick(par1World, par3, par4, par5, par2Random);
/* 62:84 */       par1World.scheduledUpdatesAreImmediate = false;
/* 63:   */     }
/* 64:87 */     return true;
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenLiquids
 * JD-Core Version:    0.7.0.1
 */