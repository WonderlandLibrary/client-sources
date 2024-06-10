/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class WorldGenHellLava
/* 10:   */   extends WorldGenerator
/* 11:   */ {
/* 12:   */   private Block field_150553_a;
/* 13:   */   private boolean field_94524_b;
/* 14:   */   private static final String __OBFID = "CL_00000414";
/* 15:   */   
/* 16:   */   public WorldGenHellLava(Block p_i45453_1_, boolean p_i45453_2_)
/* 17:   */   {
/* 18:17 */     this.field_150553_a = p_i45453_1_;
/* 19:18 */     this.field_94524_b = p_i45453_2_;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 23:   */   {
/* 24:23 */     if (par1World.getBlock(par3, par4 + 1, par5) != Blocks.netherrack) {
/* 25:25 */       return false;
/* 26:   */     }
/* 27:27 */     if ((par1World.getBlock(par3, par4, par5).getMaterial() != Material.air) && (par1World.getBlock(par3, par4, par5) != Blocks.netherrack)) {
/* 28:29 */       return false;
/* 29:   */     }
/* 30:33 */     int var6 = 0;
/* 31:35 */     if (par1World.getBlock(par3 - 1, par4, par5) == Blocks.netherrack) {
/* 32:37 */       var6++;
/* 33:   */     }
/* 34:40 */     if (par1World.getBlock(par3 + 1, par4, par5) == Blocks.netherrack) {
/* 35:42 */       var6++;
/* 36:   */     }
/* 37:45 */     if (par1World.getBlock(par3, par4, par5 - 1) == Blocks.netherrack) {
/* 38:47 */       var6++;
/* 39:   */     }
/* 40:50 */     if (par1World.getBlock(par3, par4, par5 + 1) == Blocks.netherrack) {
/* 41:52 */       var6++;
/* 42:   */     }
/* 43:55 */     if (par1World.getBlock(par3, par4 - 1, par5) == Blocks.netherrack) {
/* 44:57 */       var6++;
/* 45:   */     }
/* 46:60 */     int var7 = 0;
/* 47:62 */     if (par1World.isAirBlock(par3 - 1, par4, par5)) {
/* 48:64 */       var7++;
/* 49:   */     }
/* 50:67 */     if (par1World.isAirBlock(par3 + 1, par4, par5)) {
/* 51:69 */       var7++;
/* 52:   */     }
/* 53:72 */     if (par1World.isAirBlock(par3, par4, par5 - 1)) {
/* 54:74 */       var7++;
/* 55:   */     }
/* 56:77 */     if (par1World.isAirBlock(par3, par4, par5 + 1)) {
/* 57:79 */       var7++;
/* 58:   */     }
/* 59:82 */     if (par1World.isAirBlock(par3, par4 - 1, par5)) {
/* 60:84 */       var7++;
/* 61:   */     }
/* 62:87 */     if (((!this.field_94524_b) && (var6 == 4) && (var7 == 1)) || (var6 == 5))
/* 63:   */     {
/* 64:89 */       par1World.setBlock(par3, par4, par5, this.field_150553_a, 0, 2);
/* 65:90 */       par1World.scheduledUpdatesAreImmediate = true;
/* 66:91 */       this.field_150553_a.updateTick(par1World, par3, par4, par5, par2Random);
/* 67:92 */       par1World.scheduledUpdatesAreImmediate = false;
/* 68:   */     }
/* 69:95 */     return true;
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenHellLava
 * JD-Core Version:    0.7.0.1
 */