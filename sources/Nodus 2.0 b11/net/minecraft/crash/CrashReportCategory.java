/*   1:    */ package net.minecraft.crash;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.concurrent.Callable;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.util.MathHelper;
/*   9:    */ 
/*  10:    */ public class CrashReportCategory
/*  11:    */ {
/*  12:    */   private final CrashReport theCrashReport;
/*  13:    */   private final String field_85076_b;
/*  14: 14 */   private final List field_85077_c = new ArrayList();
/*  15: 15 */   private StackTraceElement[] stackTrace = new StackTraceElement[0];
/*  16:    */   private static final String __OBFID = "CL_00001409";
/*  17:    */   
/*  18:    */   public CrashReportCategory(CrashReport par1CrashReport, String par2Str)
/*  19:    */   {
/*  20: 20 */     this.theCrashReport = par1CrashReport;
/*  21: 21 */     this.field_85076_b = par2Str;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static String func_85074_a(double par0, double par2, double par4)
/*  25:    */   {
/*  26: 26 */     return String.format("%.2f,%.2f,%.2f - %s", new Object[] { Double.valueOf(par0), Double.valueOf(par2), Double.valueOf(par4), getLocationInfo(MathHelper.floor_double(par0), MathHelper.floor_double(par2), MathHelper.floor_double(par4)) });
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static String getLocationInfo(int par0, int par1, int par2)
/*  30:    */   {
/*  31: 34 */     StringBuilder var3 = new StringBuilder();
/*  32:    */     try
/*  33:    */     {
/*  34: 38 */       var3.append(String.format("World: (%d,%d,%d)", new Object[] { Integer.valueOf(par0), Integer.valueOf(par1), Integer.valueOf(par2) }));
/*  35:    */     }
/*  36:    */     catch (Throwable var16)
/*  37:    */     {
/*  38: 42 */       var3.append("(Error finding world loc)");
/*  39:    */     }
/*  40: 45 */     var3.append(", ");
/*  41:    */     try
/*  42:    */     {
/*  43: 58 */       int var4 = par0 >> 4;
/*  44: 59 */       int var5 = par2 >> 4;
/*  45: 60 */       int var6 = par0 & 0xF;
/*  46: 61 */       int var7 = par1 >> 4;
/*  47: 62 */       int var8 = par2 & 0xF;
/*  48: 63 */       int var9 = var4 << 4;
/*  49: 64 */       int var10 = var5 << 4;
/*  50: 65 */       int var11 = (var4 + 1 << 4) - 1;
/*  51: 66 */       int var12 = (var5 + 1 << 4) - 1;
/*  52: 67 */       var3.append(String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", new Object[] { Integer.valueOf(var6), Integer.valueOf(var7), Integer.valueOf(var8), Integer.valueOf(var4), Integer.valueOf(var5), Integer.valueOf(var9), Integer.valueOf(var10), Integer.valueOf(var11), Integer.valueOf(var12) }));
/*  53:    */     }
/*  54:    */     catch (Throwable var15)
/*  55:    */     {
/*  56: 71 */       var3.append("(Error finding chunk loc)");
/*  57:    */     }
/*  58: 74 */     var3.append(", ");
/*  59:    */     try
/*  60:    */     {
/*  61: 78 */       int var4 = par0 >> 9;
/*  62: 79 */       int var5 = par2 >> 9;
/*  63: 80 */       int var6 = var4 << 5;
/*  64: 81 */       int var7 = var5 << 5;
/*  65: 82 */       int var8 = (var4 + 1 << 5) - 1;
/*  66: 83 */       int var9 = (var5 + 1 << 5) - 1;
/*  67: 84 */       int var10 = var4 << 9;
/*  68: 85 */       int var11 = var5 << 9;
/*  69: 86 */       int var12 = (var4 + 1 << 9) - 1;
/*  70: 87 */       int var13 = (var5 + 1 << 9) - 1;
/*  71: 88 */       var3.append(String.format("Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)", new Object[] { Integer.valueOf(var4), Integer.valueOf(var5), Integer.valueOf(var6), Integer.valueOf(var7), Integer.valueOf(var8), Integer.valueOf(var9), Integer.valueOf(var10), Integer.valueOf(var11), Integer.valueOf(var12), Integer.valueOf(var13) }));
/*  72:    */     }
/*  73:    */     catch (Throwable var14)
/*  74:    */     {
/*  75: 92 */       var3.append("(Error finding world loc)");
/*  76:    */     }
/*  77: 95 */     return var3.toString();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void addCrashSectionCallable(String par1Str, Callable par2Callable)
/*  81:    */   {
/*  82:    */     try
/*  83:    */     {
/*  84:105 */       addCrashSection(par1Str, par2Callable.call());
/*  85:    */     }
/*  86:    */     catch (Throwable var4)
/*  87:    */     {
/*  88:109 */       addCrashSectionThrowable(par1Str, var4);
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void addCrashSection(String par1Str, Object par2Obj)
/*  93:    */   {
/*  94:118 */     this.field_85077_c.add(new Entry(par1Str, par2Obj));
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void addCrashSectionThrowable(String par1Str, Throwable par2Throwable)
/*  98:    */   {
/*  99:126 */     addCrashSection(par1Str, par2Throwable);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int getPrunedStackTrace(int par1)
/* 103:    */   {
/* 104:135 */     StackTraceElement[] var2 = Thread.currentThread().getStackTrace();
/* 105:137 */     if (var2.length <= 0) {
/* 106:139 */       return 0;
/* 107:    */     }
/* 108:143 */     this.stackTrace = new StackTraceElement[var2.length - 3 - par1];
/* 109:144 */     System.arraycopy(var2, 3 + par1, this.stackTrace, 0, this.stackTrace.length);
/* 110:145 */     return this.stackTrace.length;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean firstTwoElementsOfStackTraceMatch(StackTraceElement par1StackTraceElement, StackTraceElement par2StackTraceElement)
/* 114:    */   {
/* 115:154 */     if ((this.stackTrace.length != 0) && (par1StackTraceElement != null))
/* 116:    */     {
/* 117:156 */       StackTraceElement var3 = this.stackTrace[0];
/* 118:158 */       if ((var3.isNativeMethod() == par1StackTraceElement.isNativeMethod()) && (var3.getClassName().equals(par1StackTraceElement.getClassName())) && (var3.getFileName().equals(par1StackTraceElement.getFileName())) && (var3.getMethodName().equals(par1StackTraceElement.getMethodName())))
/* 119:    */       {
/* 120:160 */         if ((par2StackTraceElement != null ? 1 : 0) != (this.stackTrace.length > 1 ? 1 : 0)) {
/* 121:162 */           return false;
/* 122:    */         }
/* 123:164 */         if ((par2StackTraceElement != null) && (!this.stackTrace[1].equals(par2StackTraceElement))) {
/* 124:166 */           return false;
/* 125:    */         }
/* 126:170 */         this.stackTrace[0] = par1StackTraceElement;
/* 127:171 */         return true;
/* 128:    */       }
/* 129:176 */       return false;
/* 130:    */     }
/* 131:181 */     return false;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void trimStackTraceEntriesFromBottom(int par1)
/* 135:    */   {
/* 136:190 */     StackTraceElement[] var2 = new StackTraceElement[this.stackTrace.length - par1];
/* 137:191 */     System.arraycopy(this.stackTrace, 0, var2, 0, var2.length);
/* 138:192 */     this.stackTrace = var2;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void appendToStringBuilder(StringBuilder par1StringBuilder)
/* 142:    */   {
/* 143:197 */     par1StringBuilder.append("-- ").append(this.field_85076_b).append(" --\n");
/* 144:198 */     par1StringBuilder.append("Details:");
/* 145:199 */     Iterator var2 = this.field_85077_c.iterator();
/* 146:201 */     while (var2.hasNext())
/* 147:    */     {
/* 148:203 */       Entry var3 = (Entry)var2.next();
/* 149:204 */       par1StringBuilder.append("\n\t");
/* 150:205 */       par1StringBuilder.append(var3.func_85089_a());
/* 151:206 */       par1StringBuilder.append(": ");
/* 152:207 */       par1StringBuilder.append(var3.func_85090_b());
/* 153:    */     }
/* 154:210 */     if ((this.stackTrace != null) && (this.stackTrace.length > 0))
/* 155:    */     {
/* 156:212 */       par1StringBuilder.append("\nStacktrace:");
/* 157:213 */       StackTraceElement[] var6 = this.stackTrace;
/* 158:214 */       int var7 = var6.length;
/* 159:216 */       for (int var4 = 0; var4 < var7; var4++)
/* 160:    */       {
/* 161:218 */         StackTraceElement var5 = var6[var4];
/* 162:219 */         par1StringBuilder.append("\n\tat ");
/* 163:220 */         par1StringBuilder.append(var5.toString());
/* 164:    */       }
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   public StackTraceElement[] func_147152_a()
/* 169:    */   {
/* 170:227 */     return this.stackTrace;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public static void func_147153_a(CrashReportCategory p_147153_0_, int p_147153_1_, final int p_147153_2_, final int p_147153_3_, final Block p_147153_4_, int p_147153_5_)
/* 174:    */   {
/* 175:232 */     int var6 = Block.getIdFromBlock(p_147153_4_);
/* 176:233 */     p_147153_0_.addCrashSectionCallable("Block type", new Callable()
/* 177:    */     {
/* 178:    */       private static final String __OBFID = "CL_00001426";
/* 179:    */       
/* 180:    */       public String call()
/* 181:    */       {
/* 182:    */         try
/* 183:    */         {
/* 184:240 */           return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(this.val$var6), p_147153_4_.getUnlocalizedName(), p_147153_4_.getClass().getCanonicalName() });
/* 185:    */         }
/* 186:    */         catch (Throwable var2) {}
/* 187:244 */         return "ID #" + this.val$var6;
/* 188:    */       }
/* 189:247 */     });
/* 190:248 */     p_147153_0_.addCrashSectionCallable("Block data value", new Callable()
/* 191:    */     {
/* 192:    */       private static final String __OBFID = "CL_00001441";
/* 193:    */       
/* 194:    */       public String call()
/* 195:    */       {
/* 196:253 */         if (this.val$p_147153_5_ < 0) {
/* 197:255 */           return "Unknown? (Got " + this.val$p_147153_5_ + ")";
/* 198:    */         }
/* 199:259 */         String var1 = String.format("%4s", new Object[] { Integer.toBinaryString(this.val$p_147153_5_) }).replace(" ", "0");
/* 200:260 */         return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] { Integer.valueOf(this.val$p_147153_5_), var1 });
/* 201:    */       }
/* 202:263 */     });
/* 203:264 */     p_147153_0_.addCrashSectionCallable("Block location", new Callable()
/* 204:    */     {
/* 205:    */       private static final String __OBFID = "CL_00001465";
/* 206:    */       
/* 207:    */       public String call()
/* 208:    */       {
/* 209:269 */         return CrashReportCategory.getLocationInfo(this.val$p_147153_1_, p_147153_2_, p_147153_3_);
/* 210:    */       }
/* 211:    */     });
/* 212:    */   }
/* 213:    */   
/* 214:    */   static class Entry
/* 215:    */   {
/* 216:    */     private final String field_85092_a;
/* 217:    */     private final String field_85091_b;
/* 218:    */     private static final String __OBFID = "CL_00001489";
/* 219:    */     
/* 220:    */     public Entry(String par1Str, Object par2Obj)
/* 221:    */     {
/* 222:282 */       this.field_85092_a = par1Str;
/* 223:284 */       if (par2Obj == null)
/* 224:    */       {
/* 225:286 */         this.field_85091_b = "~~NULL~~";
/* 226:    */       }
/* 227:288 */       else if ((par2Obj instanceof Throwable))
/* 228:    */       {
/* 229:290 */         Throwable var3 = (Throwable)par2Obj;
/* 230:291 */         this.field_85091_b = ("~~ERROR~~ " + var3.getClass().getSimpleName() + ": " + var3.getMessage());
/* 231:    */       }
/* 232:    */       else
/* 233:    */       {
/* 234:295 */         this.field_85091_b = par2Obj.toString();
/* 235:    */       }
/* 236:    */     }
/* 237:    */     
/* 238:    */     public String func_85089_a()
/* 239:    */     {
/* 240:301 */       return this.field_85092_a;
/* 241:    */     }
/* 242:    */     
/* 243:    */     public String func_85090_b()
/* 244:    */     {
/* 245:306 */       return this.field_85091_b;
/* 246:    */     }
/* 247:    */   }
/* 248:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.crash.CrashReportCategory
 * JD-Core Version:    0.7.0.1
 */