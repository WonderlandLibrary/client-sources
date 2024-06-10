/*   1:    */ package net.minecraft.block.material;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.BlockColored;
/*   4:    */ 
/*   5:    */ public class MapColor
/*   6:    */ {
/*   7: 10 */   public static final MapColor[] mapColorArray = new MapColor[64];
/*   8: 11 */   public static final MapColor field_151660_b = new MapColor(0, 0);
/*   9: 12 */   public static final MapColor field_151661_c = new MapColor(1, 8368696);
/*  10: 13 */   public static final MapColor field_151658_d = new MapColor(2, 16247203);
/*  11: 14 */   public static final MapColor field_151659_e = new MapColor(3, 10987431);
/*  12: 15 */   public static final MapColor field_151656_f = new MapColor(4, 16711680);
/*  13: 16 */   public static final MapColor field_151657_g = new MapColor(5, 10526975);
/*  14: 17 */   public static final MapColor field_151668_h = new MapColor(6, 10987431);
/*  15: 18 */   public static final MapColor field_151669_i = new MapColor(7, 31744);
/*  16: 19 */   public static final MapColor field_151666_j = new MapColor(8, 16777215);
/*  17: 20 */   public static final MapColor field_151667_k = new MapColor(9, 10791096);
/*  18: 21 */   public static final MapColor field_151664_l = new MapColor(10, 12020271);
/*  19: 22 */   public static final MapColor field_151665_m = new MapColor(11, 7368816);
/*  20: 23 */   public static final MapColor field_151662_n = new MapColor(12, 4210943);
/*  21: 24 */   public static final MapColor field_151663_o = new MapColor(13, 6837042);
/*  22: 25 */   public static final MapColor field_151677_p = new MapColor(14, 16776437);
/*  23: 26 */   public static final MapColor field_151676_q = new MapColor(15, 14188339);
/*  24: 27 */   public static final MapColor field_151675_r = new MapColor(16, 11685080);
/*  25: 28 */   public static final MapColor field_151674_s = new MapColor(17, 6724056);
/*  26: 29 */   public static final MapColor field_151673_t = new MapColor(18, 15066419);
/*  27: 30 */   public static final MapColor field_151672_u = new MapColor(19, 8375321);
/*  28: 31 */   public static final MapColor field_151671_v = new MapColor(20, 15892389);
/*  29: 32 */   public static final MapColor field_151670_w = new MapColor(21, 5000268);
/*  30: 33 */   public static final MapColor field_151680_x = new MapColor(22, 10066329);
/*  31: 34 */   public static final MapColor field_151679_y = new MapColor(23, 5013401);
/*  32: 35 */   public static final MapColor field_151678_z = new MapColor(24, 8339378);
/*  33: 36 */   public static final MapColor field_151649_A = new MapColor(25, 3361970);
/*  34: 37 */   public static final MapColor field_151650_B = new MapColor(26, 6704179);
/*  35: 38 */   public static final MapColor field_151651_C = new MapColor(27, 6717235);
/*  36: 39 */   public static final MapColor field_151645_D = new MapColor(28, 10040115);
/*  37: 40 */   public static final MapColor field_151646_E = new MapColor(29, 1644825);
/*  38: 41 */   public static final MapColor field_151647_F = new MapColor(30, 16445005);
/*  39: 42 */   public static final MapColor field_151648_G = new MapColor(31, 6085589);
/*  40: 43 */   public static final MapColor field_151652_H = new MapColor(32, 4882687);
/*  41: 44 */   public static final MapColor field_151653_I = new MapColor(33, 55610);
/*  42: 45 */   public static final MapColor field_151654_J = new MapColor(34, 1381407);
/*  43: 46 */   public static final MapColor field_151655_K = new MapColor(35, 7340544);
/*  44:    */   public final int colorValue;
/*  45:    */   public final int colorIndex;
/*  46:    */   private static final String __OBFID = "CL_00000544";
/*  47:    */   
/*  48:    */   private MapColor(int par1, int par2)
/*  49:    */   {
/*  50: 57 */     if ((par1 >= 0) && (par1 <= 63))
/*  51:    */     {
/*  52: 59 */       this.colorIndex = par1;
/*  53: 60 */       this.colorValue = par2;
/*  54: 61 */       mapColorArray[par1] = this;
/*  55:    */     }
/*  56:    */     else
/*  57:    */     {
/*  58: 65 */       throw new IndexOutOfBoundsException("Map colour ID must be between 0 and 63 (inclusive)");
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static MapColor func_151644_a(int p_151644_0_)
/*  63:    */   {
/*  64: 71 */     switch (BlockColored.func_150031_c(p_151644_0_))
/*  65:    */     {
/*  66:    */     case 0: 
/*  67: 74 */       return field_151646_E;
/*  68:    */     case 1: 
/*  69: 77 */       return field_151645_D;
/*  70:    */     case 2: 
/*  71: 80 */       return field_151651_C;
/*  72:    */     case 3: 
/*  73: 83 */       return field_151650_B;
/*  74:    */     case 4: 
/*  75: 86 */       return field_151649_A;
/*  76:    */     case 5: 
/*  77: 89 */       return field_151678_z;
/*  78:    */     case 6: 
/*  79: 92 */       return field_151679_y;
/*  80:    */     case 7: 
/*  81: 95 */       return field_151680_x;
/*  82:    */     case 8: 
/*  83: 98 */       return field_151670_w;
/*  84:    */     case 9: 
/*  85:101 */       return field_151671_v;
/*  86:    */     case 10: 
/*  87:104 */       return field_151672_u;
/*  88:    */     case 11: 
/*  89:107 */       return field_151673_t;
/*  90:    */     case 12: 
/*  91:110 */       return field_151674_s;
/*  92:    */     case 13: 
/*  93:113 */       return field_151675_r;
/*  94:    */     case 14: 
/*  95:116 */       return field_151676_q;
/*  96:    */     case 15: 
/*  97:119 */       return field_151666_j;
/*  98:    */     }
/*  99:122 */     return field_151660_b;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int func_151643_b(int p_151643_1_)
/* 103:    */   {
/* 104:128 */     short var2 = 220;
/* 105:130 */     if (p_151643_1_ == 3) {
/* 106:132 */       var2 = 135;
/* 107:    */     }
/* 108:135 */     if (p_151643_1_ == 2) {
/* 109:137 */       var2 = 255;
/* 110:    */     }
/* 111:140 */     if (p_151643_1_ == 1) {
/* 112:142 */       var2 = 220;
/* 113:    */     }
/* 114:145 */     if (p_151643_1_ == 0) {
/* 115:147 */       var2 = 180;
/* 116:    */     }
/* 117:150 */     int var3 = (this.colorValue >> 16 & 0xFF) * var2 / 255;
/* 118:151 */     int var4 = (this.colorValue >> 8 & 0xFF) * var2 / 255;
/* 119:152 */     int var5 = (this.colorValue & 0xFF) * var2 / 255;
/* 120:153 */     return 0xFF000000 | var3 << 16 | var4 << 8 | var5;
/* 121:    */   }
/* 122:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.material.MapColor
 * JD-Core Version:    0.7.0.1
 */