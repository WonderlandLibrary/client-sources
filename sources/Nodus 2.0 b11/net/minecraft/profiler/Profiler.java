/*   1:    */ package net.minecraft.profiler;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Set;
/*  11:    */ import net.minecraft.client.settings.GameSettings;
/*  12:    */ import net.minecraft.src.Config;
/*  13:    */ import org.apache.logging.log4j.LogManager;
/*  14:    */ import org.apache.logging.log4j.Logger;
/*  15:    */ 
/*  16:    */ public class Profiler
/*  17:    */ {
/*  18: 16 */   private static final Logger logger = ;
/*  19: 19 */   private final List sectionList = new ArrayList();
/*  20: 22 */   private final List timestampList = new ArrayList();
/*  21:    */   public boolean profilingEnabled;
/*  22: 28 */   private String profilingSection = "";
/*  23: 31 */   private final Map profilingMap = new HashMap();
/*  24: 32 */   public boolean profilerGlobalEnabled = true;
/*  25:    */   private boolean profilerLocalEnabled;
/*  26:    */   private long startTickNano;
/*  27:    */   public long timeTickNano;
/*  28:    */   private long startUpdateChunksNano;
/*  29:    */   public long timeUpdateChunksNano;
/*  30:    */   private static final String __OBFID = "CL_00001497";
/*  31:    */   
/*  32:    */   public Profiler()
/*  33:    */   {
/*  34: 42 */     this.profilerLocalEnabled = this.profilerGlobalEnabled;
/*  35: 43 */     this.startTickNano = 0L;
/*  36: 44 */     this.timeTickNano = 0L;
/*  37: 45 */     this.startUpdateChunksNano = 0L;
/*  38: 46 */     this.timeUpdateChunksNano = 0L;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void clearProfiling()
/*  42:    */   {
/*  43: 54 */     this.profilingMap.clear();
/*  44: 55 */     this.profilingSection = "";
/*  45: 56 */     this.sectionList.clear();
/*  46: 57 */     this.profilerLocalEnabled = this.profilerGlobalEnabled;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void startSection(String par1Str)
/*  50:    */   {
/*  51: 65 */     if (Config.getGameSettings().showDebugInfo)
/*  52:    */     {
/*  53: 67 */       if ((this.startTickNano == 0L) && (par1Str.equals("tick"))) {
/*  54: 69 */         this.startTickNano = System.nanoTime();
/*  55:    */       }
/*  56: 72 */       if ((this.startTickNano != 0L) && (par1Str.equals("preRenderErrors")))
/*  57:    */       {
/*  58: 74 */         this.timeTickNano = (System.nanoTime() - this.startTickNano);
/*  59: 75 */         this.startTickNano = 0L;
/*  60:    */       }
/*  61: 78 */       if ((this.startUpdateChunksNano == 0L) && (par1Str.equals("updatechunks"))) {
/*  62: 80 */         this.startUpdateChunksNano = System.nanoTime();
/*  63:    */       }
/*  64: 83 */       if ((this.startUpdateChunksNano != 0L) && (par1Str.equals("terrain")))
/*  65:    */       {
/*  66: 85 */         this.timeUpdateChunksNano = (System.nanoTime() - this.startUpdateChunksNano);
/*  67: 86 */         this.startUpdateChunksNano = 0L;
/*  68:    */       }
/*  69:    */     }
/*  70: 90 */     if (this.profilerLocalEnabled) {
/*  71: 92 */       if (this.profilingEnabled)
/*  72:    */       {
/*  73: 94 */         if (this.profilingSection.length() > 0) {
/*  74: 96 */           this.profilingSection += ".";
/*  75:    */         }
/*  76: 99 */         this.profilingSection += par1Str;
/*  77:100 */         this.sectionList.add(this.profilingSection);
/*  78:101 */         this.timestampList.add(Long.valueOf(System.nanoTime()));
/*  79:    */       }
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void endSection()
/*  84:    */   {
/*  85:111 */     if (this.profilerLocalEnabled) {
/*  86:113 */       if (this.profilingEnabled)
/*  87:    */       {
/*  88:115 */         long var1 = System.nanoTime();
/*  89:116 */         long var3 = ((Long)this.timestampList.remove(this.timestampList.size() - 1)).longValue();
/*  90:117 */         this.sectionList.remove(this.sectionList.size() - 1);
/*  91:118 */         long var5 = var1 - var3;
/*  92:120 */         if (this.profilingMap.containsKey(this.profilingSection)) {
/*  93:122 */           this.profilingMap.put(this.profilingSection, Long.valueOf(((Long)this.profilingMap.get(this.profilingSection)).longValue() + var5));
/*  94:    */         } else {
/*  95:126 */           this.profilingMap.put(this.profilingSection, Long.valueOf(var5));
/*  96:    */         }
/*  97:129 */         if (var5 > 100000000L) {
/*  98:131 */           logger.warn("Something's taking too long! '" + this.profilingSection + "' took aprox " + var5 / 1000000.0D + " ms");
/*  99:    */         }
/* 100:134 */         this.profilingSection = (!this.sectionList.isEmpty() ? (String)this.sectionList.get(this.sectionList.size() - 1) : "");
/* 101:    */       }
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public List getProfilingData(String par1Str)
/* 106:    */   {
/* 107:144 */     this.profilerLocalEnabled = this.profilerGlobalEnabled;
/* 108:146 */     if (!this.profilerLocalEnabled) {
/* 109:148 */       return new ArrayList(Arrays.asList(new Result[] { new Result("root", 0.0D, 0.0D) }));
/* 110:    */     }
/* 111:150 */     if (!this.profilingEnabled) {
/* 112:152 */       return null;
/* 113:    */     }
/* 114:156 */     long var3 = this.profilingMap.containsKey("root") ? ((Long)this.profilingMap.get("root")).longValue() : 0L;
/* 115:157 */     long var5 = this.profilingMap.containsKey(par1Str) ? ((Long)this.profilingMap.get(par1Str)).longValue() : -1L;
/* 116:158 */     ArrayList var7 = new ArrayList();
/* 117:160 */     if (par1Str.length() > 0) {
/* 118:162 */       par1Str = par1Str + ".";
/* 119:    */     }
/* 120:165 */     long var8 = 0L;
/* 121:166 */     Iterator var10 = this.profilingMap.keySet().iterator();
/* 122:168 */     while (var10.hasNext())
/* 123:    */     {
/* 124:170 */       String var21 = (String)var10.next();
/* 125:172 */       if ((var21.length() > par1Str.length()) && (var21.startsWith(par1Str)) && (var21.indexOf(".", par1Str.length() + 1) < 0)) {
/* 126:174 */         var8 += ((Long)this.profilingMap.get(var21)).longValue();
/* 127:    */       }
/* 128:    */     }
/* 129:178 */     float var211 = (float)var8;
/* 130:180 */     if (var8 < var5) {
/* 131:182 */       var8 = var5;
/* 132:    */     }
/* 133:185 */     if (var3 < var8) {
/* 134:187 */       var3 = var8;
/* 135:    */     }
/* 136:190 */     Iterator var20 = this.profilingMap.keySet().iterator();
/* 137:193 */     while (var20.hasNext())
/* 138:    */     {
/* 139:195 */       String var12 = (String)var20.next();
/* 140:197 */       if ((var12.length() > par1Str.length()) && (var12.startsWith(par1Str)) && (var12.indexOf(".", par1Str.length() + 1) < 0))
/* 141:    */       {
/* 142:199 */         long var13 = ((Long)this.profilingMap.get(var12)).longValue();
/* 143:200 */         double var15 = var13 * 100.0D / var8;
/* 144:201 */         double var17 = var13 * 100.0D / var3;
/* 145:202 */         String var19 = var12.substring(par1Str.length());
/* 146:203 */         var7.add(new Result(var19, var15, var17));
/* 147:    */       }
/* 148:    */     }
/* 149:207 */     var20 = this.profilingMap.keySet().iterator();
/* 150:209 */     while (var20.hasNext())
/* 151:    */     {
/* 152:211 */       String var12 = (String)var20.next();
/* 153:212 */       this.profilingMap.put(var12, Long.valueOf(((Long)this.profilingMap.get(var12)).longValue() * 999L / 1000L));
/* 154:    */     }
/* 155:215 */     if ((float)var8 > var211) {
/* 156:217 */       var7.add(new Result("unspecified", ((float)var8 - var211) * 100.0D / var8, ((float)var8 - var211) * 100.0D / var3));
/* 157:    */     }
/* 158:220 */     Collections.sort(var7);
/* 159:221 */     var7.add(0, new Result(par1Str, 100.0D, var8 * 100.0D / var3));
/* 160:222 */     return var7;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void endStartSection(String par1Str)
/* 164:    */   {
/* 165:231 */     if (this.profilerLocalEnabled)
/* 166:    */     {
/* 167:233 */       endSection();
/* 168:234 */       startSection(par1Str);
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   public String getNameOfLastSection()
/* 173:    */   {
/* 174:240 */     return this.sectionList.size() == 0 ? "[UNKNOWN]" : (String)this.sectionList.get(this.sectionList.size() - 1);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public static final class Result
/* 178:    */     implements Comparable
/* 179:    */   {
/* 180:    */     public double field_76332_a;
/* 181:    */     public double field_76330_b;
/* 182:    */     public String field_76331_c;
/* 183:    */     private static final String __OBFID = "CL_00001498";
/* 184:    */     
/* 185:    */     public Result(String par1Str, double par2, double par4)
/* 186:    */     {
/* 187:252 */       this.field_76331_c = par1Str;
/* 188:253 */       this.field_76332_a = par2;
/* 189:254 */       this.field_76330_b = par4;
/* 190:    */     }
/* 191:    */     
/* 192:    */     public int compareTo(Result par1ProfilerResult)
/* 193:    */     {
/* 194:259 */       return par1ProfilerResult.field_76332_a > this.field_76332_a ? 1 : par1ProfilerResult.field_76332_a < this.field_76332_a ? -1 : par1ProfilerResult.field_76331_c.compareTo(this.field_76331_c);
/* 195:    */     }
/* 196:    */     
/* 197:    */     public int func_76329_a()
/* 198:    */     {
/* 199:264 */       return (this.field_76331_c.hashCode() & 0xAAAAAA) + 4473924;
/* 200:    */     }
/* 201:    */     
/* 202:    */     public int compareTo(Object par1Obj)
/* 203:    */     {
/* 204:269 */       return compareTo((Result)par1Obj);
/* 205:    */     }
/* 206:    */   }
/* 207:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.profiler.Profiler
 * JD-Core Version:    0.7.0.1
 */