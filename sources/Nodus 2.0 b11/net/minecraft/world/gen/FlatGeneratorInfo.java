/*   1:    */ package net.minecraft.world.gen;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import java.util.Set;
/*  10:    */ import net.minecraft.block.Block;
/*  11:    */ import net.minecraft.init.Blocks;
/*  12:    */ import net.minecraft.util.MathHelper;
/*  13:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  14:    */ 
/*  15:    */ public class FlatGeneratorInfo
/*  16:    */ {
/*  17: 17 */   private final List flatLayers = new ArrayList();
/*  18: 20 */   private final Map worldFeatures = new HashMap();
/*  19:    */   private int biomeToUse;
/*  20:    */   private static final String __OBFID = "CL_00000440";
/*  21:    */   
/*  22:    */   public int getBiome()
/*  23:    */   {
/*  24: 29 */     return this.biomeToUse;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void setBiome(int par1)
/*  28:    */   {
/*  29: 37 */     this.biomeToUse = par1;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Map getWorldFeatures()
/*  33:    */   {
/*  34: 45 */     return this.worldFeatures;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public List getFlatLayers()
/*  38:    */   {
/*  39: 53 */     return this.flatLayers;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void func_82645_d()
/*  43:    */   {
/*  44: 58 */     int var1 = 0;
/*  45:    */     FlatLayerInfo var3;
/*  46: 61 */     for (Iterator var2 = this.flatLayers.iterator(); var2.hasNext(); var1 += var3.getLayerCount())
/*  47:    */     {
/*  48: 63 */       var3 = (FlatLayerInfo)var2.next();
/*  49: 64 */       var3.setMinY(var1);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String toString()
/*  54:    */   {
/*  55: 70 */     StringBuilder var1 = new StringBuilder();
/*  56: 71 */     var1.append(2);
/*  57: 72 */     var1.append(";");
/*  58: 75 */     for (int var2 = 0; var2 < this.flatLayers.size(); var2++)
/*  59:    */     {
/*  60: 77 */       if (var2 > 0) {
/*  61: 79 */         var1.append(",");
/*  62:    */       }
/*  63: 82 */       var1.append(((FlatLayerInfo)this.flatLayers.get(var2)).toString());
/*  64:    */     }
/*  65: 85 */     var1.append(";");
/*  66: 86 */     var1.append(this.biomeToUse);
/*  67: 88 */     if (!this.worldFeatures.isEmpty())
/*  68:    */     {
/*  69: 90 */       var1.append(";");
/*  70: 91 */       var2 = 0;
/*  71: 92 */       Iterator var3 = this.worldFeatures.entrySet().iterator();
/*  72: 94 */       while (var3.hasNext())
/*  73:    */       {
/*  74: 96 */         Map.Entry var4 = (Map.Entry)var3.next();
/*  75: 98 */         if (var2++ > 0) {
/*  76:100 */           var1.append(",");
/*  77:    */         }
/*  78:103 */         var1.append(((String)var4.getKey()).toLowerCase());
/*  79:104 */         Map var5 = (Map)var4.getValue();
/*  80:106 */         if (!var5.isEmpty())
/*  81:    */         {
/*  82:108 */           var1.append("(");
/*  83:109 */           int var6 = 0;
/*  84:110 */           Iterator var7 = var5.entrySet().iterator();
/*  85:112 */           while (var7.hasNext())
/*  86:    */           {
/*  87:114 */             Map.Entry var8 = (Map.Entry)var7.next();
/*  88:116 */             if (var6++ > 0) {
/*  89:118 */               var1.append(" ");
/*  90:    */             }
/*  91:121 */             var1.append((String)var8.getKey());
/*  92:122 */             var1.append("=");
/*  93:123 */             var1.append((String)var8.getValue());
/*  94:    */           }
/*  95:126 */           var1.append(")");
/*  96:    */         }
/*  97:    */       }
/*  98:    */     }
/*  99:    */     else
/* 100:    */     {
/* 101:132 */       var1.append(";");
/* 102:    */     }
/* 103:135 */     return var1.toString();
/* 104:    */   }
/* 105:    */   
/* 106:    */   private static FlatLayerInfo func_82646_a(String par0Str, int par1)
/* 107:    */   {
/* 108:140 */     String[] var2 = par0Str.split("x", 2);
/* 109:141 */     int var3 = 1;
/* 110:142 */     int var5 = 0;
/* 111:144 */     if (var2.length == 2) {
/* 112:    */       try
/* 113:    */       {
/* 114:148 */         var3 = Integer.parseInt(var2[0]);
/* 115:150 */         if (par1 + var3 >= 256) {
/* 116:152 */           var3 = 256 - par1;
/* 117:    */         }
/* 118:155 */         if (var3 < 0) {
/* 119:157 */           var3 = 0;
/* 120:    */         }
/* 121:    */       }
/* 122:    */       catch (Throwable var7)
/* 123:    */       {
/* 124:162 */         return null;
/* 125:    */       }
/* 126:    */     }
/* 127:    */     try
/* 128:    */     {
/* 129:170 */       String var6 = var2[(var2.length - 1)];
/* 130:171 */       var2 = var6.split(":", 2);
/* 131:172 */       int var4 = Integer.parseInt(var2[0]);
/* 132:174 */       if (var2.length > 1) {
/* 133:176 */         var5 = Integer.parseInt(var2[1]);
/* 134:    */       }
/* 135:179 */       if (Block.getBlockById(var4) == Blocks.air)
/* 136:    */       {
/* 137:181 */         var4 = 0;
/* 138:182 */         var5 = 0;
/* 139:    */       }
/* 140:185 */       if ((var5 < 0) || (var5 > 15)) {
/* 141:187 */         var5 = 0;
/* 142:    */       }
/* 143:    */     }
/* 144:    */     catch (Throwable var8)
/* 145:    */     {
/* 146:192 */       return null;
/* 147:    */     }
/* 148:    */     int var4;
/* 149:195 */     FlatLayerInfo var9 = new FlatLayerInfo(var3, Block.getBlockById(var4), var5);
/* 150:196 */     var9.setMinY(par1);
/* 151:197 */     return var9;
/* 152:    */   }
/* 153:    */   
/* 154:    */   private static List func_82652_b(String par0Str)
/* 155:    */   {
/* 156:202 */     if ((par0Str != null) && (par0Str.length() >= 1))
/* 157:    */     {
/* 158:204 */       ArrayList var1 = new ArrayList();
/* 159:205 */       String[] var2 = par0Str.split(",");
/* 160:206 */       int var3 = 0;
/* 161:207 */       String[] var4 = var2;
/* 162:208 */       int var5 = var2.length;
/* 163:210 */       for (int var6 = 0; var6 < var5; var6++)
/* 164:    */       {
/* 165:212 */         String var7 = var4[var6];
/* 166:213 */         FlatLayerInfo var8 = func_82646_a(var7, var3);
/* 167:215 */         if (var8 == null) {
/* 168:217 */           return null;
/* 169:    */         }
/* 170:220 */         var1.add(var8);
/* 171:221 */         var3 += var8.getLayerCount();
/* 172:    */       }
/* 173:224 */       return var1;
/* 174:    */     }
/* 175:228 */     return null;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public static FlatGeneratorInfo createFlatGeneratorFromString(String par0Str)
/* 179:    */   {
/* 180:234 */     if (par0Str == null) {
/* 181:236 */       return getDefaultFlatGenerator();
/* 182:    */     }
/* 183:240 */     String[] var1 = par0Str.split(";", -1);
/* 184:241 */     int var2 = var1.length == 1 ? 0 : MathHelper.parseIntWithDefault(var1[0], 0);
/* 185:243 */     if ((var2 >= 0) && (var2 <= 2))
/* 186:    */     {
/* 187:245 */       FlatGeneratorInfo var3 = new FlatGeneratorInfo();
/* 188:246 */       int var4 = var1.length == 1 ? 0 : 1;
/* 189:247 */       List var5 = func_82652_b(var1[(var4++)]);
/* 190:249 */       if ((var5 != null) && (!var5.isEmpty()))
/* 191:    */       {
/* 192:251 */         var3.getFlatLayers().addAll(var5);
/* 193:252 */         var3.func_82645_d();
/* 194:253 */         int var6 = BiomeGenBase.plains.biomeID;
/* 195:255 */         if ((var2 > 0) && (var1.length > var4)) {
/* 196:257 */           var6 = MathHelper.parseIntWithDefault(var1[(var4++)], var6);
/* 197:    */         }
/* 198:260 */         var3.setBiome(var6);
/* 199:262 */         if ((var2 > 0) && (var1.length > var4))
/* 200:    */         {
/* 201:264 */           String[] var7 = var1[(var4++)].toLowerCase().split(",");
/* 202:265 */           String[] var8 = var7;
/* 203:266 */           int var9 = var7.length;
/* 204:268 */           for (int var10 = 0; var10 < var9; var10++)
/* 205:    */           {
/* 206:270 */             String var11 = var8[var10];
/* 207:271 */             String[] var12 = var11.split("\\(", 2);
/* 208:272 */             HashMap var13 = new HashMap();
/* 209:274 */             if (var12[0].length() > 0)
/* 210:    */             {
/* 211:276 */               var3.getWorldFeatures().put(var12[0], var13);
/* 212:278 */               if ((var12.length > 1) && (var12[1].endsWith(")")) && (var12[1].length() > 1))
/* 213:    */               {
/* 214:280 */                 String[] var14 = var12[1].substring(0, var12[1].length() - 1).split(" ");
/* 215:282 */                 for (int var15 = 0; var15 < var14.length; var15++)
/* 216:    */                 {
/* 217:284 */                   String[] var16 = var14[var15].split("=", 2);
/* 218:286 */                   if (var16.length == 2) {
/* 219:288 */                     var13.put(var16[0], var16[1]);
/* 220:    */                   }
/* 221:    */                 }
/* 222:    */               }
/* 223:    */             }
/* 224:    */           }
/* 225:    */         }
/* 226:    */         else
/* 227:    */         {
/* 228:297 */           var3.getWorldFeatures().put("village", new HashMap());
/* 229:    */         }
/* 230:300 */         return var3;
/* 231:    */       }
/* 232:304 */       return getDefaultFlatGenerator();
/* 233:    */     }
/* 234:309 */     return getDefaultFlatGenerator();
/* 235:    */   }
/* 236:    */   
/* 237:    */   public static FlatGeneratorInfo getDefaultFlatGenerator()
/* 238:    */   {
/* 239:316 */     FlatGeneratorInfo var0 = new FlatGeneratorInfo();
/* 240:317 */     var0.setBiome(BiomeGenBase.plains.biomeID);
/* 241:318 */     var0.getFlatLayers().add(new FlatLayerInfo(1, Blocks.bedrock));
/* 242:319 */     var0.getFlatLayers().add(new FlatLayerInfo(2, Blocks.dirt));
/* 243:320 */     var0.getFlatLayers().add(new FlatLayerInfo(1, Blocks.grass));
/* 244:321 */     var0.func_82645_d();
/* 245:322 */     var0.getWorldFeatures().put("village", new HashMap());
/* 246:323 */     return var0;
/* 247:    */   }
/* 248:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.FlatGeneratorInfo
 * JD-Core Version:    0.7.0.1
 */