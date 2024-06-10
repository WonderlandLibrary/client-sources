/*   1:    */ package net.minecraft.world.gen.layer;
/*   2:    */ 
/*   3:    */ public class GenLayerEdge
/*   4:    */   extends GenLayer
/*   5:    */ {
/*   6:    */   private final Mode field_151627_c;
/*   7:    */   private static final String __OBFID = "CL_00000547";
/*   8:    */   
/*   9:    */   public GenLayerEdge(long p_i45474_1_, GenLayer p_i45474_3_, Mode p_i45474_4_)
/*  10:    */   {
/*  11: 10 */     super(p_i45474_1_);
/*  12: 11 */     this.parent = p_i45474_3_;
/*  13: 12 */     this.field_151627_c = p_i45474_4_;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public int[] getInts(int par1, int par2, int par3, int par4)
/*  17:    */   {
/*  18: 21 */     switch (SwitchMode.field_151642_a[this.field_151627_c.ordinal()])
/*  19:    */     {
/*  20:    */     case 1: 
/*  21:    */     default: 
/*  22: 25 */       return func_151626_c(par1, par2, par3, par4);
/*  23:    */     case 2: 
/*  24: 28 */       return func_151624_d(par1, par2, par3, par4);
/*  25:    */     }
/*  26: 31 */     return func_151625_e(par1, par2, par3, par4);
/*  27:    */   }
/*  28:    */   
/*  29:    */   private int[] func_151626_c(int p_151626_1_, int p_151626_2_, int p_151626_3_, int p_151626_4_)
/*  30:    */   {
/*  31: 37 */     int var5 = p_151626_1_ - 1;
/*  32: 38 */     int var6 = p_151626_2_ - 1;
/*  33: 39 */     int var7 = 1 + p_151626_3_ + 1;
/*  34: 40 */     int var8 = 1 + p_151626_4_ + 1;
/*  35: 41 */     int[] var9 = this.parent.getInts(var5, var6, var7, var8);
/*  36: 42 */     int[] var10 = IntCache.getIntCache(p_151626_3_ * p_151626_4_);
/*  37: 44 */     for (int var11 = 0; var11 < p_151626_4_; var11++) {
/*  38: 46 */       for (int var12 = 0; var12 < p_151626_3_; var12++)
/*  39:    */       {
/*  40: 48 */         initChunkSeed(var12 + p_151626_1_, var11 + p_151626_2_);
/*  41: 49 */         int var13 = var9[(var12 + 1 + (var11 + 1) * var7)];
/*  42: 51 */         if (var13 == 1)
/*  43:    */         {
/*  44: 53 */           int var14 = var9[(var12 + 1 + (var11 + 1 - 1) * var7)];
/*  45: 54 */           int var15 = var9[(var12 + 1 + 1 + (var11 + 1) * var7)];
/*  46: 55 */           int var16 = var9[(var12 + 1 - 1 + (var11 + 1) * var7)];
/*  47: 56 */           int var17 = var9[(var12 + 1 + (var11 + 1 + 1) * var7)];
/*  48: 57 */           boolean var18 = (var14 == 3) || (var15 == 3) || (var16 == 3) || (var17 == 3);
/*  49: 58 */           boolean var19 = (var14 == 4) || (var15 == 4) || (var16 == 4) || (var17 == 4);
/*  50: 60 */           if ((var18) || (var19)) {
/*  51: 62 */             var13 = 2;
/*  52:    */           }
/*  53:    */         }
/*  54: 66 */         var10[(var12 + var11 * p_151626_3_)] = var13;
/*  55:    */       }
/*  56:    */     }
/*  57: 70 */     return var10;
/*  58:    */   }
/*  59:    */   
/*  60:    */   private int[] func_151624_d(int p_151624_1_, int p_151624_2_, int p_151624_3_, int p_151624_4_)
/*  61:    */   {
/*  62: 75 */     int var5 = p_151624_1_ - 1;
/*  63: 76 */     int var6 = p_151624_2_ - 1;
/*  64: 77 */     int var7 = 1 + p_151624_3_ + 1;
/*  65: 78 */     int var8 = 1 + p_151624_4_ + 1;
/*  66: 79 */     int[] var9 = this.parent.getInts(var5, var6, var7, var8);
/*  67: 80 */     int[] var10 = IntCache.getIntCache(p_151624_3_ * p_151624_4_);
/*  68: 82 */     for (int var11 = 0; var11 < p_151624_4_; var11++) {
/*  69: 84 */       for (int var12 = 0; var12 < p_151624_3_; var12++)
/*  70:    */       {
/*  71: 86 */         int var13 = var9[(var12 + 1 + (var11 + 1) * var7)];
/*  72: 88 */         if (var13 == 4)
/*  73:    */         {
/*  74: 90 */           int var14 = var9[(var12 + 1 + (var11 + 1 - 1) * var7)];
/*  75: 91 */           int var15 = var9[(var12 + 1 + 1 + (var11 + 1) * var7)];
/*  76: 92 */           int var16 = var9[(var12 + 1 - 1 + (var11 + 1) * var7)];
/*  77: 93 */           int var17 = var9[(var12 + 1 + (var11 + 1 + 1) * var7)];
/*  78: 94 */           boolean var18 = (var14 == 2) || (var15 == 2) || (var16 == 2) || (var17 == 2);
/*  79: 95 */           boolean var19 = (var14 == 1) || (var15 == 1) || (var16 == 1) || (var17 == 1);
/*  80: 97 */           if ((var19) || (var18)) {
/*  81: 99 */             var13 = 3;
/*  82:    */           }
/*  83:    */         }
/*  84:103 */         var10[(var12 + var11 * p_151624_3_)] = var13;
/*  85:    */       }
/*  86:    */     }
/*  87:107 */     return var10;
/*  88:    */   }
/*  89:    */   
/*  90:    */   private int[] func_151625_e(int p_151625_1_, int p_151625_2_, int p_151625_3_, int p_151625_4_)
/*  91:    */   {
/*  92:112 */     int[] var5 = this.parent.getInts(p_151625_1_, p_151625_2_, p_151625_3_, p_151625_4_);
/*  93:113 */     int[] var6 = IntCache.getIntCache(p_151625_3_ * p_151625_4_);
/*  94:115 */     for (int var7 = 0; var7 < p_151625_4_; var7++) {
/*  95:117 */       for (int var8 = 0; var8 < p_151625_3_; var8++)
/*  96:    */       {
/*  97:119 */         initChunkSeed(var8 + p_151625_1_, var7 + p_151625_2_);
/*  98:120 */         int var9 = var5[(var8 + var7 * p_151625_3_)];
/*  99:122 */         if ((var9 != 0) && (nextInt(13) == 0)) {
/* 100:124 */           var9 |= 1 + nextInt(15) << 8 & 0xF00;
/* 101:    */         }
/* 102:127 */         var6[(var8 + var7 * p_151625_3_)] = var9;
/* 103:    */       }
/* 104:    */     }
/* 105:131 */     return var6;
/* 106:    */   }
/* 107:    */   
/* 108:    */   static final class SwitchMode
/* 109:    */   {
/* 110:136 */     static final int[] field_151642_a = new int[GenLayerEdge.Mode.values().length];
/* 111:    */     private static final String __OBFID = "CL_00000548";
/* 112:    */     
/* 113:    */     static
/* 114:    */     {
/* 115:    */       try
/* 116:    */       {
/* 117:143 */         field_151642_a[GenLayerEdge.Mode.COOL_WARM.ordinal()] = 1;
/* 118:    */       }
/* 119:    */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/* 120:    */       try
/* 121:    */       {
/* 122:152 */         field_151642_a[GenLayerEdge.Mode.HEAT_ICE.ordinal()] = 2;
/* 123:    */       }
/* 124:    */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/* 125:    */       try
/* 126:    */       {
/* 127:161 */         field_151642_a[GenLayerEdge.Mode.SPECIAL.ordinal()] = 3;
/* 128:    */       }
/* 129:    */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static enum Mode
/* 134:    */   {
/* 135:172 */     COOL_WARM("COOL_WARM", 0),  HEAT_ICE("HEAT_ICE", 1),  SPECIAL("SPECIAL", 2);
/* 136:    */     
/* 137:176 */     private static final Mode[] $VALUES = { COOL_WARM, HEAT_ICE, SPECIAL };
/* 138:    */     private static final String __OBFID = "CL_00000549";
/* 139:    */     
/* 140:    */     private Mode(String p_i45473_1_, int p_i45473_2_) {}
/* 141:    */   }
/* 142:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.layer.GenLayerEdge
 * JD-Core Version:    0.7.0.1
 */