/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.util.MathHelper;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class WorldGenMinable
/* 10:   */   extends WorldGenerator
/* 11:   */ {
/* 12:   */   private Block field_150519_a;
/* 13:   */   private int numberOfBlocks;
/* 14:   */   private Block field_150518_c;
/* 15:   */   private static final String __OBFID = "CL_00000426";
/* 16:   */   
/* 17:   */   public WorldGenMinable(Block p_i45459_1_, int p_i45459_2_)
/* 18:   */   {
/* 19:20 */     this(p_i45459_1_, p_i45459_2_, Blocks.stone);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public WorldGenMinable(Block p_i45460_1_, int p_i45460_2_, Block p_i45460_3_)
/* 23:   */   {
/* 24:25 */     this.field_150519_a = p_i45460_1_;
/* 25:26 */     this.numberOfBlocks = p_i45460_2_;
/* 26:27 */     this.field_150518_c = p_i45460_3_;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 30:   */   {
/* 31:32 */     float var6 = par2Random.nextFloat() * 3.141593F;
/* 32:33 */     double var7 = par3 + 8 + MathHelper.sin(var6) * this.numberOfBlocks / 8.0F;
/* 33:34 */     double var9 = par3 + 8 - MathHelper.sin(var6) * this.numberOfBlocks / 8.0F;
/* 34:35 */     double var11 = par5 + 8 + MathHelper.cos(var6) * this.numberOfBlocks / 8.0F;
/* 35:36 */     double var13 = par5 + 8 - MathHelper.cos(var6) * this.numberOfBlocks / 8.0F;
/* 36:37 */     double var15 = par4 + par2Random.nextInt(3) - 2;
/* 37:38 */     double var17 = par4 + par2Random.nextInt(3) - 2;
/* 38:40 */     for (int var19 = 0; var19 <= this.numberOfBlocks; var19++)
/* 39:   */     {
/* 40:42 */       double var20 = var7 + (var9 - var7) * var19 / this.numberOfBlocks;
/* 41:43 */       double var22 = var15 + (var17 - var15) * var19 / this.numberOfBlocks;
/* 42:44 */       double var24 = var11 + (var13 - var11) * var19 / this.numberOfBlocks;
/* 43:45 */       double var26 = par2Random.nextDouble() * this.numberOfBlocks / 16.0D;
/* 44:46 */       double var28 = (MathHelper.sin(var19 * 3.141593F / this.numberOfBlocks) + 1.0F) * var26 + 1.0D;
/* 45:47 */       double var30 = (MathHelper.sin(var19 * 3.141593F / this.numberOfBlocks) + 1.0F) * var26 + 1.0D;
/* 46:48 */       int var32 = MathHelper.floor_double(var20 - var28 / 2.0D);
/* 47:49 */       int var33 = MathHelper.floor_double(var22 - var30 / 2.0D);
/* 48:50 */       int var34 = MathHelper.floor_double(var24 - var28 / 2.0D);
/* 49:51 */       int var35 = MathHelper.floor_double(var20 + var28 / 2.0D);
/* 50:52 */       int var36 = MathHelper.floor_double(var22 + var30 / 2.0D);
/* 51:53 */       int var37 = MathHelper.floor_double(var24 + var28 / 2.0D);
/* 52:55 */       for (int var38 = var32; var38 <= var35; var38++)
/* 53:   */       {
/* 54:57 */         double var39 = (var38 + 0.5D - var20) / (var28 / 2.0D);
/* 55:59 */         if (var39 * var39 < 1.0D) {
/* 56:61 */           for (int var41 = var33; var41 <= var36; var41++)
/* 57:   */           {
/* 58:63 */             double var42 = (var41 + 0.5D - var22) / (var30 / 2.0D);
/* 59:65 */             if (var39 * var39 + var42 * var42 < 1.0D) {
/* 60:67 */               for (int var44 = var34; var44 <= var37; var44++)
/* 61:   */               {
/* 62:69 */                 double var45 = (var44 + 0.5D - var24) / (var28 / 2.0D);
/* 63:71 */                 if ((var39 * var39 + var42 * var42 + var45 * var45 < 1.0D) && (par1World.getBlock(var38, var41, var44) == this.field_150518_c)) {
/* 64:73 */                   par1World.setBlock(var38, var41, var44, this.field_150519_a, 0, 2);
/* 65:   */                 }
/* 66:   */               }
/* 67:   */             }
/* 68:   */           }
/* 69:   */         }
/* 70:   */       }
/* 71:   */     }
/* 72:82 */     return true;
/* 73:   */   }
/* 74:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenMinable
 * JD-Core Version:    0.7.0.1
 */