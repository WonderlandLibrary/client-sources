/*   1:    */ package net.minecraft.world.gen.layer;
/*   2:    */ 
/*   3:    */ import net.minecraft.world.WorldType;
/*   4:    */ import net.minecraft.world.biome.BiomeGenBase;
/*   5:    */ 
/*   6:    */ public class GenLayerBiome
/*   7:    */   extends GenLayer
/*   8:    */ {
/*   9:    */   private BiomeGenBase[] field_151623_c;
/*  10:    */   private BiomeGenBase[] field_151621_d;
/*  11:    */   private BiomeGenBase[] field_151622_e;
/*  12:    */   private BiomeGenBase[] field_151620_f;
/*  13:    */   private static final String __OBFID = "CL_00000555";
/*  14:    */   
/*  15:    */   public GenLayerBiome(long par1, GenLayer par3GenLayer, WorldType par4WorldType)
/*  16:    */   {
/*  17: 16 */     super(par1);
/*  18: 17 */     this.field_151623_c = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.field_150588_X, BiomeGenBase.field_150588_X, BiomeGenBase.plains };
/*  19: 18 */     this.field_151621_d = new BiomeGenBase[] { BiomeGenBase.forest, BiomeGenBase.field_150585_R, BiomeGenBase.extremeHills, BiomeGenBase.plains, BiomeGenBase.field_150583_P, BiomeGenBase.swampland };
/*  20: 19 */     this.field_151622_e = new BiomeGenBase[] { BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.taiga, BiomeGenBase.plains };
/*  21: 20 */     this.field_151620_f = new BiomeGenBase[] { BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.field_150584_S };
/*  22: 21 */     this.parent = par3GenLayer;
/*  23: 23 */     if (par4WorldType == WorldType.DEFAULT_1_1) {
/*  24: 25 */       this.field_151623_c = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.taiga };
/*  25:    */     }
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int[] getInts(int par1, int par2, int par3, int par4)
/*  29:    */   {
/*  30: 35 */     int[] var5 = this.parent.getInts(par1, par2, par3, par4);
/*  31: 36 */     int[] var6 = IntCache.getIntCache(par3 * par4);
/*  32: 38 */     for (int var7 = 0; var7 < par4; var7++) {
/*  33: 40 */       for (int var8 = 0; var8 < par3; var8++)
/*  34:    */       {
/*  35: 42 */         initChunkSeed(var8 + par1, var7 + par2);
/*  36: 43 */         int var9 = var5[(var8 + var7 * par3)];
/*  37: 44 */         int var10 = (var9 & 0xF00) >> 8;
/*  38: 45 */         var9 &= 0xFFFFF0FF;
/*  39: 47 */         if (func_151618_b(var9)) {
/*  40: 49 */           var6[(var8 + var7 * par3)] = var9;
/*  41: 51 */         } else if (var9 == BiomeGenBase.mushroomIsland.biomeID) {
/*  42: 53 */           var6[(var8 + var7 * par3)] = var9;
/*  43: 55 */         } else if (var9 == 1)
/*  44:    */         {
/*  45: 57 */           if (var10 > 0)
/*  46:    */           {
/*  47: 59 */             if (nextInt(3) == 0) {
/*  48: 61 */               var6[(var8 + var7 * par3)] = BiomeGenBase.field_150608_ab.biomeID;
/*  49:    */             } else {
/*  50: 65 */               var6[(var8 + var7 * par3)] = BiomeGenBase.field_150607_aa.biomeID;
/*  51:    */             }
/*  52:    */           }
/*  53:    */           else {
/*  54: 70 */             var6[(var8 + var7 * par3)] = this.field_151623_c[nextInt(this.field_151623_c.length)].biomeID;
/*  55:    */           }
/*  56:    */         }
/*  57: 73 */         else if (var9 == 2)
/*  58:    */         {
/*  59: 75 */           if (var10 > 0) {
/*  60: 77 */             var6[(var8 + var7 * par3)] = BiomeGenBase.jungle.biomeID;
/*  61:    */           } else {
/*  62: 81 */             var6[(var8 + var7 * par3)] = this.field_151621_d[nextInt(this.field_151621_d.length)].biomeID;
/*  63:    */           }
/*  64:    */         }
/*  65: 84 */         else if (var9 == 3)
/*  66:    */         {
/*  67: 86 */           if (var10 > 0) {
/*  68: 88 */             var6[(var8 + var7 * par3)] = BiomeGenBase.field_150578_U.biomeID;
/*  69:    */           } else {
/*  70: 92 */             var6[(var8 + var7 * par3)] = this.field_151622_e[nextInt(this.field_151622_e.length)].biomeID;
/*  71:    */           }
/*  72:    */         }
/*  73: 95 */         else if (var9 == 4) {
/*  74: 97 */           var6[(var8 + var7 * par3)] = this.field_151620_f[nextInt(this.field_151620_f.length)].biomeID;
/*  75:    */         } else {
/*  76:101 */           var6[(var8 + var7 * par3)] = BiomeGenBase.mushroomIsland.biomeID;
/*  77:    */         }
/*  78:    */       }
/*  79:    */     }
/*  80:106 */     return var6;
/*  81:    */   }
/*  82:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.layer.GenLayerBiome
 * JD-Core Version:    0.7.0.1
 */