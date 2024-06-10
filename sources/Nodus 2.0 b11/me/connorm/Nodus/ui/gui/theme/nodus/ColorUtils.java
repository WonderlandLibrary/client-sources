/*   1:    */ package me.connorm.Nodus.ui.gui.theme.nodus;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.List;
/*   6:    */ 
/*   7:    */ public class ColorUtils
/*   8:    */ {
/*   9:  9 */   public static int primaryColor = -1;
/*  10: 11 */   public static int secondaryColor = -11141291;
/*  11: 13 */   public static int chestESPColor = -1;
/*  12: 15 */   public static int aggressiveESPColor = -1;
/*  13: 17 */   public static int passiveESPColor = -1;
/*  14: 19 */   public static int playerESPColor = -5635926;
/*  15: 21 */   public static List<Integer> mcColors = Arrays.asList(
/*  16: 22 */     new Integer[] {
/*  17: 23 */     Integer.valueOf(-1), 
/*  18: 24 */     Integer.valueOf(-11141291), 
/*  19: 25 */     Integer.valueOf(-11184641), 
/*  20: 26 */     Integer.valueOf(-16777216), 
/*  21: 27 */     Integer.valueOf(-16777046), 
/*  22: 28 */     Integer.valueOf(-16733696), 
/*  23: 29 */     Integer.valueOf(-16733526), 
/*  24: 30 */     Integer.valueOf(-5636096), 
/*  25: 31 */     Integer.valueOf(-5635926), 
/*  26: 32 */     Integer.valueOf(-22016), 
/*  27: 33 */     Integer.valueOf(-5592406), 
/*  28: 34 */     Integer.valueOf(-11184811), 
/*  29: 35 */     Integer.valueOf(-11141121), 
/*  30: 36 */     Integer.valueOf(-43521), 
/*  31: 37 */     Integer.valueOf(-171), 
/*  32: 38 */     Integer.valueOf(-43691) });
/*  33: 41 */   public static List<Color> mcRGB = Arrays.asList(
/*  34: 42 */     new Color[] {
/*  35: 43 */     new Color(0, 0, 0), 
/*  36: 44 */     new Color(0, 0, 42), 
/*  37: 45 */     new Color(0, 42, 0), 
/*  38: 46 */     new Color(0, 42, 42), 
/*  39: 47 */     new Color(42, 0, 0), 
/*  40: 48 */     new Color(42, 0, 42), 
/*  41: 49 */     new Color(42, 42, 0), 
/*  42: 50 */     new Color(42, 42, 42), 
/*  43: 51 */     new Color(21, 21, 21), 
/*  44: 52 */     new Color(21, 21, 63), 
/*  45: 53 */     new Color(21, 63, 21), 
/*  46: 54 */     new Color(21, 63, 63), 
/*  47: 55 */     new Color(63, 21, 21), 
/*  48: 56 */     new Color(63, 21, 63), 
/*  49: 57 */     new Color(63, 63, 21), 
/*  50: 58 */     new Color(63, 63, 63) });
/*  51:    */   
/*  52:    */   public static void toggleColor(int index)
/*  53:    */   {
/*  54: 64 */     int currentIndex = mcColors.indexOf(Integer.valueOf(getIndex(index)));
/*  55: 65 */     if (currentIndex != mcColors.size()) {
/*  56: 67 */       if (index == 1)
/*  57:    */       {
/*  58: 69 */         if (primaryColor == -43691)
/*  59:    */         {
/*  60: 71 */           primaryColor = -1;
/*  61: 72 */           return;
/*  62:    */         }
/*  63: 74 */         primaryColor = ((Integer)mcColors.get(currentIndex + 1)).intValue();
/*  64:    */       }
/*  65: 75 */       else if (index == 2)
/*  66:    */       {
/*  67: 77 */         if (secondaryColor == -43691)
/*  68:    */         {
/*  69: 79 */           secondaryColor = -1;
/*  70: 80 */           return;
/*  71:    */         }
/*  72: 82 */         secondaryColor = ((Integer)mcColors.get(currentIndex + 1)).intValue();
/*  73:    */       }
/*  74: 83 */       else if (index == 3)
/*  75:    */       {
/*  76: 85 */         if (chestESPColor == -1)
/*  77:    */         {
/*  78: 87 */           chestESPColor = -16777216;
/*  79: 88 */           return;
/*  80:    */         }
/*  81: 91 */         if (chestESPColor == -22016)
/*  82:    */         {
/*  83: 93 */           chestESPColor = -1;
/*  84: 94 */           return;
/*  85:    */         }
/*  86: 96 */         chestESPColor = ((Integer)mcColors.get(currentIndex + 1)).intValue();
/*  87:    */       }
/*  88: 97 */       else if (index == 4)
/*  89:    */       {
/*  90: 99 */         if (aggressiveESPColor == -1)
/*  91:    */         {
/*  92:101 */           aggressiveESPColor = -16777216;
/*  93:102 */           return;
/*  94:    */         }
/*  95:105 */         if (aggressiveESPColor == -16777216)
/*  96:    */         {
/*  97:107 */           aggressiveESPColor = -16733696;
/*  98:108 */           return;
/*  99:    */         }
/* 100:111 */         if (aggressiveESPColor == -16733696)
/* 101:    */         {
/* 102:113 */           aggressiveESPColor = -5635926;
/* 103:114 */           return;
/* 104:    */         }
/* 105:117 */         if (aggressiveESPColor == -5635926)
/* 106:    */         {
/* 107:119 */           aggressiveESPColor = -1;
/* 108:120 */           return;
/* 109:    */         }
/* 110:123 */         aggressiveESPColor = ((Integer)mcColors.get(currentIndex + 1)).intValue();
/* 111:    */       }
/* 112:124 */       else if (index == 5)
/* 113:    */       {
/* 114:126 */         if (passiveESPColor == -1)
/* 115:    */         {
/* 116:128 */           passiveESPColor = -16777216;
/* 117:129 */           return;
/* 118:    */         }
/* 119:132 */         if (passiveESPColor == -16777216)
/* 120:    */         {
/* 121:134 */           passiveESPColor = -16733696;
/* 122:135 */           return;
/* 123:    */         }
/* 124:138 */         if (passiveESPColor == -16733696)
/* 125:    */         {
/* 126:140 */           passiveESPColor = -5635926;
/* 127:141 */           return;
/* 128:    */         }
/* 129:144 */         if (passiveESPColor == -5635926)
/* 130:    */         {
/* 131:146 */           passiveESPColor = -1;
/* 132:147 */           return;
/* 133:    */         }
/* 134:150 */         passiveESPColor = ((Integer)mcColors.get(currentIndex + 1)).intValue();
/* 135:    */       }
/* 136:151 */       else if (index == 6)
/* 137:    */       {
/* 138:153 */         if (playerESPColor == -1)
/* 139:    */         {
/* 140:155 */           playerESPColor = -16777216;
/* 141:156 */           return;
/* 142:    */         }
/* 143:159 */         if (playerESPColor == -16777216)
/* 144:    */         {
/* 145:161 */           playerESPColor = -16733696;
/* 146:162 */           return;
/* 147:    */         }
/* 148:165 */         if (playerESPColor == -16733696)
/* 149:    */         {
/* 150:167 */           playerESPColor = -5635926;
/* 151:168 */           return;
/* 152:    */         }
/* 153:171 */         if (playerESPColor == -5635926)
/* 154:    */         {
/* 155:173 */           playerESPColor = -1;
/* 156:174 */           return;
/* 157:    */         }
/* 158:177 */         playerESPColor = ((Integer)mcColors.get(currentIndex + 1)).intValue();
/* 159:    */       }
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   private static int getIndex(int index)
/* 164:    */   {
/* 165:184 */     if (index == 1) {
/* 166:186 */       return primaryColor;
/* 167:    */     }
/* 168:188 */     if (index == 2) {
/* 169:190 */       return secondaryColor;
/* 170:    */     }
/* 171:192 */     if (index == 3) {
/* 172:194 */       return chestESPColor;
/* 173:    */     }
/* 174:196 */     if (index == 4) {
/* 175:198 */       return aggressiveESPColor;
/* 176:    */     }
/* 177:200 */     if (index == 5) {
/* 178:202 */       return passiveESPColor;
/* 179:    */     }
/* 180:204 */     if (index == 6) {
/* 181:206 */       return playerESPColor;
/* 182:    */     }
/* 183:208 */     return 0;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public static Color hexToRGBA(int hexCode)
/* 187:    */   {
/* 188:213 */     float aValue = (hexCode >> 24 & 0xFF) / 255.0F;
/* 189:214 */     float rValue = (hexCode >> 16 & 0xFF) / 255.0F;
/* 190:215 */     float gValue = (hexCode >> 8 & 0xFF) / 255.0F;
/* 191:216 */     float bValue = (hexCode & 0xFF) / 255.0F;
/* 192:    */     
/* 193:218 */     return new Color(rValue, gValue, bValue, aValue);
/* 194:    */   }
/* 195:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.gui.theme.nodus.ColorUtils
 * JD-Core Version:    0.7.0.1
 */