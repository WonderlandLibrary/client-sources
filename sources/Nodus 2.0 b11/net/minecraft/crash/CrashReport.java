/*   1:    */ package net.minecraft.crash;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileWriter;
/*   5:    */ import java.io.PrintWriter;
/*   6:    */ import java.io.StringWriter;
/*   7:    */ import java.lang.management.ManagementFactory;
/*   8:    */ import java.lang.management.RuntimeMXBean;
/*   9:    */ import java.text.SimpleDateFormat;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.concurrent.Callable;
/*  15:    */ import net.minecraft.util.AABBPool;
/*  16:    */ import net.minecraft.util.AxisAlignedBB;
/*  17:    */ import net.minecraft.util.ReportedException;
/*  18:    */ import net.minecraft.world.gen.layer.IntCache;
/*  19:    */ import org.apache.commons.io.IOUtils;
/*  20:    */ import org.apache.commons.lang3.ArrayUtils;
/*  21:    */ import org.apache.logging.log4j.LogManager;
/*  22:    */ import org.apache.logging.log4j.Logger;
/*  23:    */ 
/*  24:    */ public class CrashReport
/*  25:    */ {
/*  26: 25 */   private static final Logger logger = ;
/*  27:    */   private final String description;
/*  28:    */   private final Throwable cause;
/*  29: 34 */   private final CrashReportCategory theReportCategory = new CrashReportCategory(this, "System Details");
/*  30: 37 */   private final List crashReportSections = new ArrayList();
/*  31:    */   private File crashReportFile;
/*  32: 41 */   private boolean field_85059_f = true;
/*  33: 42 */   private StackTraceElement[] stacktrace = new StackTraceElement[0];
/*  34:    */   private static final String __OBFID = "CL_00000990";
/*  35:    */   
/*  36:    */   public CrashReport(String par1Str, Throwable par2Throwable)
/*  37:    */   {
/*  38: 47 */     this.description = par1Str;
/*  39: 48 */     this.cause = par2Throwable;
/*  40: 49 */     populateEnvironment();
/*  41:    */   }
/*  42:    */   
/*  43:    */   private void populateEnvironment()
/*  44:    */   {
/*  45: 58 */     this.theReportCategory.addCrashSectionCallable("Minecraft Version", new Callable()
/*  46:    */     {
/*  47:    */       private static final String __OBFID = "CL_00001197";
/*  48:    */       
/*  49:    */       public String call()
/*  50:    */       {
/*  51: 63 */         return "1.7.2";
/*  52:    */       }
/*  53: 65 */     });
/*  54: 66 */     this.theReportCategory.addCrashSectionCallable("Operating System", new Callable()
/*  55:    */     {
/*  56:    */       private static final String __OBFID = "CL_00001222";
/*  57:    */       
/*  58:    */       public String call()
/*  59:    */       {
/*  60: 71 */         return System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
/*  61:    */       }
/*  62: 73 */     });
/*  63: 74 */     this.theReportCategory.addCrashSectionCallable("Java Version", new Callable()
/*  64:    */     {
/*  65:    */       private static final String __OBFID = "CL_00001248";
/*  66:    */       
/*  67:    */       public String call()
/*  68:    */       {
/*  69: 79 */         return System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
/*  70:    */       }
/*  71: 81 */     });
/*  72: 82 */     this.theReportCategory.addCrashSectionCallable("Java VM Version", new Callable()
/*  73:    */     {
/*  74:    */       private static final String __OBFID = "CL_00001275";
/*  75:    */       
/*  76:    */       public String call()
/*  77:    */       {
/*  78: 87 */         return System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
/*  79:    */       }
/*  80: 89 */     });
/*  81: 90 */     this.theReportCategory.addCrashSectionCallable("Memory", new Callable()
/*  82:    */     {
/*  83:    */       private static final String __OBFID = "CL_00001302";
/*  84:    */       
/*  85:    */       public String call()
/*  86:    */       {
/*  87: 95 */         Runtime var1 = Runtime.getRuntime();
/*  88: 96 */         long var2 = var1.maxMemory();
/*  89: 97 */         long var4 = var1.totalMemory();
/*  90: 98 */         long var6 = var1.freeMemory();
/*  91: 99 */         long var8 = var2 / 1024L / 1024L;
/*  92:100 */         long var10 = var4 / 1024L / 1024L;
/*  93:101 */         long var12 = var6 / 1024L / 1024L;
/*  94:102 */         return var6 + " bytes (" + var12 + " MB) / " + var4 + " bytes (" + var10 + " MB) up to " + var2 + " bytes (" + var8 + " MB)";
/*  95:    */       }
/*  96:104 */     });
/*  97:105 */     this.theReportCategory.addCrashSectionCallable("JVM Flags", new Callable()
/*  98:    */     {
/*  99:    */       private static final String __OBFID = "CL_00001329";
/* 100:    */       
/* 101:    */       public String call()
/* 102:    */       {
/* 103:110 */         RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
/* 104:111 */         List var2 = var1.getInputArguments();
/* 105:112 */         int var3 = 0;
/* 106:113 */         StringBuilder var4 = new StringBuilder();
/* 107:114 */         Iterator var5 = var2.iterator();
/* 108:116 */         while (var5.hasNext())
/* 109:    */         {
/* 110:118 */           String var6 = (String)var5.next();
/* 111:120 */           if (var6.startsWith("-X"))
/* 112:    */           {
/* 113:122 */             if (var3++ > 0) {
/* 114:124 */               var4.append(" ");
/* 115:    */             }
/* 116:127 */             var4.append(var6);
/* 117:    */           }
/* 118:    */         }
/* 119:131 */         return String.format("%d total; %s", new Object[] { Integer.valueOf(var3), var4.toString() });
/* 120:    */       }
/* 121:133 */     });
/* 122:134 */     this.theReportCategory.addCrashSectionCallable("AABB Pool Size", new Callable()
/* 123:    */     {
/* 124:    */       private static final String __OBFID = "CL_00001355";
/* 125:    */       
/* 126:    */       public String call()
/* 127:    */       {
/* 128:139 */         int var1 = AxisAlignedBB.getAABBPool().getlistAABBsize();
/* 129:140 */         int var2 = 56 * var1;
/* 130:141 */         int var3 = var2 / 1024 / 1024;
/* 131:142 */         int var4 = AxisAlignedBB.getAABBPool().getnextPoolIndex();
/* 132:143 */         int var5 = 56 * var4;
/* 133:144 */         int var6 = var5 / 1024 / 1024;
/* 134:145 */         return var1 + " (" + var2 + " bytes; " + var3 + " MB) allocated, " + var4 + " (" + var5 + " bytes; " + var6 + " MB) used";
/* 135:    */       }
/* 136:147 */     });
/* 137:148 */     this.theReportCategory.addCrashSectionCallable("IntCache", new Callable()
/* 138:    */     {
/* 139:    */       private static final String __OBFID = "CL_00001382";
/* 140:    */       
/* 141:    */       public String call()
/* 142:    */         throws SecurityException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException
/* 143:    */       {
/* 144:153 */         return IntCache.getCacheSizes();
/* 145:    */       }
/* 146:    */     });
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String getDescription()
/* 150:    */   {
/* 151:163 */     return this.description;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public Throwable getCrashCause()
/* 155:    */   {
/* 156:171 */     return this.cause;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void getSectionsInStringBuilder(StringBuilder par1StringBuilder)
/* 160:    */   {
/* 161:179 */     if (((this.stacktrace == null) || (this.stacktrace.length <= 0)) && (this.crashReportSections.size() > 0)) {
/* 162:181 */       this.stacktrace = ((StackTraceElement[])ArrayUtils.subarray(((CrashReportCategory)this.crashReportSections.get(0)).func_147152_a(), 0, 1));
/* 163:    */     }
/* 164:184 */     if ((this.stacktrace != null) && (this.stacktrace.length > 0))
/* 165:    */     {
/* 166:186 */       par1StringBuilder.append("-- Head --\n");
/* 167:187 */       par1StringBuilder.append("Stacktrace:\n");
/* 168:188 */       StackTraceElement[] var2 = this.stacktrace;
/* 169:189 */       int var3 = var2.length;
/* 170:191 */       for (int var4 = 0; var4 < var3; var4++)
/* 171:    */       {
/* 172:193 */         StackTraceElement var5 = var2[var4];
/* 173:194 */         par1StringBuilder.append("\t").append("at ").append(var5.toString());
/* 174:195 */         par1StringBuilder.append("\n");
/* 175:    */       }
/* 176:198 */       par1StringBuilder.append("\n");
/* 177:    */     }
/* 178:201 */     Iterator var6 = this.crashReportSections.iterator();
/* 179:203 */     while (var6.hasNext())
/* 180:    */     {
/* 181:205 */       CrashReportCategory var7 = (CrashReportCategory)var6.next();
/* 182:206 */       var7.appendToStringBuilder(par1StringBuilder);
/* 183:207 */       par1StringBuilder.append("\n\n");
/* 184:    */     }
/* 185:210 */     this.theReportCategory.appendToStringBuilder(par1StringBuilder);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String getCauseStackTraceOrString()
/* 189:    */   {
/* 190:218 */     StringWriter var1 = null;
/* 191:219 */     PrintWriter var2 = null;
/* 192:220 */     Object var3 = this.cause;
/* 193:222 */     if (((Throwable)var3).getMessage() == null)
/* 194:    */     {
/* 195:224 */       if ((var3 instanceof NullPointerException)) {
/* 196:226 */         var3 = new NullPointerException(this.description);
/* 197:228 */       } else if ((var3 instanceof StackOverflowError)) {
/* 198:230 */         var3 = new StackOverflowError(this.description);
/* 199:232 */       } else if ((var3 instanceof OutOfMemoryError)) {
/* 200:234 */         var3 = new OutOfMemoryError(this.description);
/* 201:    */       }
/* 202:237 */       ((Throwable)var3).setStackTrace(this.cause.getStackTrace());
/* 203:    */     }
/* 204:240 */     String var4 = ((Throwable)var3).toString();
/* 205:    */     try
/* 206:    */     {
/* 207:244 */       var1 = new StringWriter();
/* 208:245 */       var2 = new PrintWriter(var1);
/* 209:246 */       ((Throwable)var3).printStackTrace(var2);
/* 210:247 */       var4 = var1.toString();
/* 211:    */     }
/* 212:    */     finally
/* 213:    */     {
/* 214:251 */       IOUtils.closeQuietly(var1);
/* 215:252 */       IOUtils.closeQuietly(var2);
/* 216:    */     }
/* 217:255 */     return var4;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public String getCompleteReport()
/* 221:    */   {
/* 222:263 */     StringBuilder var1 = new StringBuilder();
/* 223:264 */     var1.append("---- Minecraft Crash Report ----\n");
/* 224:265 */     var1.append("// ");
/* 225:266 */     var1.append(getWittyComment());
/* 226:267 */     var1.append("\n\n");
/* 227:268 */     var1.append("Time: ");
/* 228:269 */     var1.append(new SimpleDateFormat().format(new Date()));
/* 229:270 */     var1.append("\n");
/* 230:271 */     var1.append("Description: ");
/* 231:272 */     var1.append(this.description);
/* 232:273 */     var1.append("\n\n");
/* 233:274 */     var1.append(getCauseStackTraceOrString());
/* 234:275 */     var1.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
/* 235:277 */     for (int var2 = 0; var2 < 87; var2++) {
/* 236:279 */       var1.append("-");
/* 237:    */     }
/* 238:282 */     var1.append("\n\n");
/* 239:283 */     getSectionsInStringBuilder(var1);
/* 240:284 */     return var1.toString();
/* 241:    */   }
/* 242:    */   
/* 243:    */   public File getFile()
/* 244:    */   {
/* 245:292 */     return this.crashReportFile;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public boolean saveToFile(File p_147149_1_)
/* 249:    */   {
/* 250:300 */     if (this.crashReportFile != null) {
/* 251:302 */       return false;
/* 252:    */     }
/* 253:306 */     if (p_147149_1_.getParentFile() != null) {
/* 254:308 */       p_147149_1_.getParentFile().mkdirs();
/* 255:    */     }
/* 256:    */     try
/* 257:    */     {
/* 258:313 */       FileWriter var2 = new FileWriter(p_147149_1_);
/* 259:314 */       var2.write(getCompleteReport());
/* 260:315 */       var2.close();
/* 261:316 */       this.crashReportFile = p_147149_1_;
/* 262:317 */       return true;
/* 263:    */     }
/* 264:    */     catch (Throwable var3)
/* 265:    */     {
/* 266:321 */       logger.error("Could not save crash report to " + p_147149_1_, var3);
/* 267:    */     }
/* 268:322 */     return false;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public CrashReportCategory getCategory()
/* 272:    */   {
/* 273:329 */     return this.theReportCategory;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public CrashReportCategory makeCategory(String par1Str)
/* 277:    */   {
/* 278:337 */     return makeCategoryDepth(par1Str, 1);
/* 279:    */   }
/* 280:    */   
/* 281:    */   public CrashReportCategory makeCategoryDepth(String par1Str, int par2)
/* 282:    */   {
/* 283:345 */     CrashReportCategory var3 = new CrashReportCategory(this, par1Str);
/* 284:347 */     if (this.field_85059_f)
/* 285:    */     {
/* 286:349 */       int var4 = var3.getPrunedStackTrace(par2);
/* 287:350 */       StackTraceElement[] var5 = this.cause.getStackTrace();
/* 288:351 */       StackTraceElement var6 = null;
/* 289:352 */       StackTraceElement var7 = null;
/* 290:354 */       if ((var5 != null) && (var5.length - var4 < var5.length))
/* 291:    */       {
/* 292:356 */         var6 = var5[(var5.length - var4)];
/* 293:358 */         if (var5.length + 1 - var4 < var5.length) {
/* 294:360 */           var7 = var5[(var5.length + 1 - var4)];
/* 295:    */         }
/* 296:    */       }
/* 297:364 */       this.field_85059_f = var3.firstTwoElementsOfStackTraceMatch(var6, var7);
/* 298:366 */       if ((var4 > 0) && (!this.crashReportSections.isEmpty()))
/* 299:    */       {
/* 300:368 */         CrashReportCategory var8 = (CrashReportCategory)this.crashReportSections.get(this.crashReportSections.size() - 1);
/* 301:369 */         var8.trimStackTraceEntriesFromBottom(var4);
/* 302:    */       }
/* 303:371 */       else if ((var5 != null) && (var5.length >= var4))
/* 304:    */       {
/* 305:373 */         this.stacktrace = new StackTraceElement[var5.length - var4];
/* 306:374 */         System.arraycopy(var5, 0, this.stacktrace, 0, this.stacktrace.length);
/* 307:    */       }
/* 308:    */       else
/* 309:    */       {
/* 310:378 */         this.field_85059_f = false;
/* 311:    */       }
/* 312:    */     }
/* 313:382 */     this.crashReportSections.add(var3);
/* 314:383 */     return var3;
/* 315:    */   }
/* 316:    */   
/* 317:    */   private static String getWittyComment()
/* 318:    */   {
/* 319:391 */     String[] var0 = { "Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!" };
/* 320:    */     try
/* 321:    */     {
/* 322:395 */       return var0[((int)(System.nanoTime() % var0.length))];
/* 323:    */     }
/* 324:    */     catch (Throwable var2) {}
/* 325:399 */     return "Witty comment unavailable :(";
/* 326:    */   }
/* 327:    */   
/* 328:    */   public static CrashReport makeCrashReport(Throwable par0Throwable, String par1Str)
/* 329:    */   {
/* 330:    */     CrashReport var2;
/* 331:    */     CrashReport var2;
/* 332:410 */     if ((par0Throwable instanceof ReportedException)) {
/* 333:412 */       var2 = ((ReportedException)par0Throwable).getCrashReport();
/* 334:    */     } else {
/* 335:416 */       var2 = new CrashReport(par1Str, par0Throwable);
/* 336:    */     }
/* 337:419 */     return var2;
/* 338:    */   }
/* 339:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.crash.CrashReport
 * JD-Core Version:    0.7.0.1
 */