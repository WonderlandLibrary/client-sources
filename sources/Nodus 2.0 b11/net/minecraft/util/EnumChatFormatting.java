/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.regex.Matcher;
/*   8:    */ import java.util.regex.Pattern;
/*   9:    */ 
/*  10:    */ public enum EnumChatFormatting
/*  11:    */ {
/*  12: 11 */   BLACK('0'),  DARK_BLUE('1'),  DARK_GREEN('2'),  DARK_AQUA('3'),  DARK_RED('4'),  DARK_PURPLE('5'),  GOLD('6'),  GRAY('7'),  DARK_GRAY('8'),  BLUE('9'),  GREEN('a'),  AQUA('b'),  RED('c'),  LIGHT_PURPLE('d'),  YELLOW('e'),  WHITE('f'),  OBFUSCATED('k', true),  BOLD('l', true),  STRIKETHROUGH('m', true),  UNDERLINE('n', true),  ITALIC('o', true),  RESET('r');
/*  13:    */   
/*  14:    */   private static final Map formattingCodeMapping;
/*  15:    */   private static final Map nameMapping;
/*  16:    */   private static final Pattern formattingCodePattern;
/*  17:    */   private final char formattingCode;
/*  18:    */   private final boolean fancyStyling;
/*  19:    */   private final String controlString;
/*  20:    */   private static final String __OBFID = "CL_00000342";
/*  21:    */   
/*  22:    */   private EnumChatFormatting(char par3)
/*  23:    */   {
/*  24: 63 */     this(par3, false);
/*  25:    */   }
/*  26:    */   
/*  27:    */   private EnumChatFormatting(char par3, boolean par4)
/*  28:    */   {
/*  29: 68 */     this.formattingCode = par3;
/*  30: 69 */     this.fancyStyling = par4;
/*  31: 70 */     this.controlString = ("§" + par3);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public char getFormattingCode()
/*  35:    */   {
/*  36: 78 */     return this.formattingCode;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean isFancyStyling()
/*  40:    */   {
/*  41: 86 */     return this.fancyStyling;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean isColor()
/*  45:    */   {
/*  46: 94 */     return (!this.fancyStyling) && (this != RESET);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getFriendlyName()
/*  50:    */   {
/*  51:102 */     return name().toLowerCase();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String toString()
/*  55:    */   {
/*  56:107 */     return this.controlString;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static String getTextWithoutFormattingCodes(String par0Str)
/*  60:    */   {
/*  61:115 */     return par0Str == null ? null : formattingCodePattern.matcher(par0Str).replaceAll("");
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static EnumChatFormatting getValueByName(String par0Str)
/*  65:    */   {
/*  66:123 */     return par0Str == null ? null : (EnumChatFormatting)nameMapping.get(par0Str.toLowerCase());
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static Collection getValidValues(boolean par0, boolean par1)
/*  70:    */   {
/*  71:132 */     ArrayList var2 = new ArrayList();
/*  72:133 */     EnumChatFormatting[] var3 = values();
/*  73:134 */     int var4 = var3.length;
/*  74:136 */     for (int var5 = 0; var5 < var4; var5++)
/*  75:    */     {
/*  76:138 */       EnumChatFormatting var6 = var3[var5];
/*  77:140 */       if (((!var6.isColor()) || (par0)) && ((!var6.isFancyStyling()) || (par1))) {
/*  78:142 */         var2.add(var6.getFriendlyName());
/*  79:    */       }
/*  80:    */     }
/*  81:146 */     return var2;
/*  82:    */   }
/*  83:    */   
/*  84:    */   static
/*  85:    */   {
/*  86: 37 */     formattingCodeMapping = new HashMap();
/*  87:    */     
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91: 42 */     nameMapping = new HashMap();
/*  92:    */     
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97: 48 */     formattingCodePattern = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");
/*  98:    */     
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:    */ 
/* 181:    */ 
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:150 */     EnumChatFormatting[] var0 = values();
/* 200:151 */     int var1 = var0.length;
/* 201:153 */     for (int var2 = 0; var2 < var1; var2++)
/* 202:    */     {
/* 203:155 */       EnumChatFormatting var3 = var0[var2];
/* 204:156 */       formattingCodeMapping.put(Character.valueOf(var3.getFormattingCode()), var3);
/* 205:157 */       nameMapping.put(var3.getFriendlyName(), var3);
/* 206:    */     }
/* 207:    */   }
/* 208:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.EnumChatFormatting
 * JD-Core Version:    0.7.0.1
 */