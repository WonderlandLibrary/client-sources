/*     */ package net.minecraft.crash;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.world.gen.layer.IntCache;
/*     */ import optfine.CrashReporter;
/*     */ import optfine.Reflector;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CrashReport
/*     */ {
/*  29 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private final String description;
/*     */ 
/*     */   
/*     */   private final Throwable cause;
/*     */ 
/*     */   
/*  38 */   private final CrashReportCategory theReportCategory = new CrashReportCategory(this, "System Details");
/*     */ 
/*     */   
/*  41 */   private final List crashReportSections = Lists.newArrayList();
/*     */   
/*     */   private File crashReportFile;
/*     */   
/*     */   private boolean field_85059_f = true;
/*  46 */   private StackTraceElement[] stacktrace = new StackTraceElement[0];
/*     */   
/*     */   private static final String __OBFID = "CL_00000990";
/*     */   private boolean reported = false;
/*     */   
/*     */   public CrashReport(String descriptionIn, Throwable causeThrowable) {
/*  52 */     this.description = descriptionIn;
/*  53 */     this.cause = causeThrowable;
/*  54 */     populateEnvironment();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateEnvironment() {
/*  63 */     this.theReportCategory.addCrashSectionCallable("Minecraft Version", new Callable<String>()
/*     */         {
/*     */           private static final String __OBFID = "CL_00001197";
/*     */           
/*     */           public String call() {
/*  68 */             return "1.8.8";
/*     */           }
/*     */         });
/*  71 */     this.theReportCategory.addCrashSectionCallable("Operating System", new Callable<String>()
/*     */         {
/*     */           private static final String __OBFID = "CL_00001222";
/*     */           
/*     */           public String call() {
/*  76 */             return String.valueOf(System.getProperty("os.name")) + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
/*     */           }
/*     */         });
/*  79 */     this.theReportCategory.addCrashSectionCallable("CPU", new CrashReport3(this));
/*  80 */     this.theReportCategory.addCrashSectionCallable("Java Version", new Callable<String>()
/*     */         {
/*     */           private static final String __OBFID = "CL_00001248";
/*     */           
/*     */           public String call() {
/*  85 */             return String.valueOf(System.getProperty("java.version")) + ", " + System.getProperty("java.vendor");
/*     */           }
/*     */         });
/*  88 */     this.theReportCategory.addCrashSectionCallable("Java VM Version", new Callable<String>()
/*     */         {
/*     */           private static final String __OBFID = "CL_00001275";
/*     */           
/*     */           public String call() {
/*  93 */             return String.valueOf(System.getProperty("java.vm.name")) + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
/*     */           }
/*     */         });
/*  96 */     this.theReportCategory.addCrashSectionCallable("Memory", new Callable<String>()
/*     */         {
/*     */           private static final String __OBFID = "CL_00001302";
/*     */           
/*     */           public String call() {
/* 101 */             Runtime runtime = Runtime.getRuntime();
/* 102 */             long i = runtime.maxMemory();
/* 103 */             long j = runtime.totalMemory();
/* 104 */             long k = runtime.freeMemory();
/* 105 */             long l = i / 1024L / 1024L;
/* 106 */             long i1 = j / 1024L / 1024L;
/* 107 */             long j1 = k / 1024L / 1024L;
/* 108 */             return String.valueOf(k) + " bytes (" + j1 + " MB) / " + j + " bytes (" + i1 + " MB) up to " + i + " bytes (" + l + " MB)";
/*     */           }
/*     */         });
/* 111 */     this.theReportCategory.addCrashSectionCallable("JVM Flags", new Callable<String>()
/*     */         {
/*     */           private static final String __OBFID = "CL_00001329";
/*     */           
/*     */           public String call() throws Exception {
/* 116 */             RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
/* 117 */             List<String> list = runtimemxbean.getInputArguments();
/* 118 */             int i = 0;
/* 119 */             StringBuilder stringbuilder = new StringBuilder();
/*     */             
/* 121 */             for (String s : list) {
/*     */               
/* 123 */               if (((String)s).startsWith("-X")) {
/*     */                 
/* 125 */                 if (i++ > 0)
/*     */                 {
/* 127 */                   stringbuilder.append(" ");
/*     */                 }
/*     */                 
/* 130 */                 stringbuilder.append(s);
/*     */               } 
/*     */             } 
/*     */             
/* 134 */             return String.format("%d total; %s", new Object[] { Integer.valueOf(i), stringbuilder.toString() });
/*     */           }
/*     */         });
/* 137 */     this.theReportCategory.addCrashSectionCallable("IntCache", new Callable<String>()
/*     */         {
/*     */           private static final String __OBFID = "CL_00001355";
/*     */ 
/*     */ 
/*     */           
/*     */           public Object call() throws Exception {
/* 144 */             return IntCache.getCacheSizes();
/*     */           }
/*     */         });
/*     */     
/* 148 */     if (Reflector.FMLCommonHandler_enhanceCrashReport.exists()) {
/*     */       
/* 150 */       Object object = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/* 151 */       Reflector.callString(object, Reflector.FMLCommonHandler_enhanceCrashReport, new Object[] { this, this.theReportCategory });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 160 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getCrashCause() {
/* 168 */     return this.cause;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSectionsInStringBuilder(StringBuilder builder) {
/* 176 */     if ((this.stacktrace == null || this.stacktrace.length <= 0) && this.crashReportSections.size() > 0)
/*     */     {
/* 178 */       this.stacktrace = (StackTraceElement[])ArrayUtils.subarray((Object[])((CrashReportCategory)this.crashReportSections.get(0)).getStackTrace(), 0, 1);
/*     */     }
/*     */     
/* 181 */     if (this.stacktrace != null && this.stacktrace.length > 0) {
/*     */       
/* 183 */       builder.append("-- Head --\n");
/* 184 */       builder.append("Stacktrace:\n"); byte b; int i;
/*     */       StackTraceElement[] arrayOfStackTraceElement;
/* 186 */       for (i = (arrayOfStackTraceElement = this.stacktrace).length, b = 0; b < i; ) { StackTraceElement stacktraceelement = arrayOfStackTraceElement[b];
/*     */         
/* 188 */         builder.append("\t").append("at ").append(stacktraceelement.toString());
/* 189 */         builder.append("\n");
/*     */         b++; }
/*     */       
/* 192 */       builder.append("\n");
/*     */     } 
/*     */     
/* 195 */     for (Object crashreportcategory : this.crashReportSections) {
/*     */       
/* 197 */       ((CrashReportCategory)crashreportcategory).appendToStringBuilder(builder);
/* 198 */       builder.append("\n\n");
/*     */     } 
/*     */     
/* 201 */     this.theReportCategory.appendToStringBuilder(builder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCauseStackTraceOrString() {
/* 209 */     StringWriter stringwriter = null;
/* 210 */     PrintWriter printwriter = null;
/* 211 */     Object object = this.cause;
/*     */     
/* 213 */     if (((Throwable)object).getMessage() == null) {
/*     */       
/* 215 */       if (object instanceof NullPointerException) {
/*     */         
/* 217 */         object = new NullPointerException(this.description);
/*     */       }
/* 219 */       else if (object instanceof StackOverflowError) {
/*     */         
/* 221 */         object = new StackOverflowError(this.description);
/*     */       }
/* 223 */       else if (object instanceof OutOfMemoryError) {
/*     */         
/* 225 */         object = new OutOfMemoryError(this.description);
/*     */       } 
/*     */       
/* 228 */       ((Throwable)object).setStackTrace(this.cause.getStackTrace());
/*     */     } 
/*     */     
/* 231 */     String s = ((Throwable)object).toString();
/*     */ 
/*     */     
/*     */     try {
/* 235 */       stringwriter = new StringWriter();
/* 236 */       printwriter = new PrintWriter(stringwriter);
/* 237 */       ((Throwable)object).printStackTrace(printwriter);
/* 238 */       s = stringwriter.toString();
/*     */     }
/*     */     finally {
/*     */       
/* 242 */       IOUtils.closeQuietly(stringwriter);
/* 243 */       IOUtils.closeQuietly(printwriter);
/*     */     } 
/*     */     
/* 246 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCompleteReport() {
/* 254 */     if (!this.reported) {
/*     */       
/* 256 */       this.reported = true;
/* 257 */       CrashReporter.onCrashReport(this);
/*     */     } 
/*     */     
/* 260 */     StringBuilder stringbuilder = new StringBuilder();
/* 261 */     stringbuilder.append("---- Minecraft Crash Report ----\n");
/* 262 */     stringbuilder.append("// ");
/* 263 */     stringbuilder.append(getWittyComment());
/* 264 */     stringbuilder.append("\n\n");
/* 265 */     stringbuilder.append("Time: ");
/* 266 */     stringbuilder.append((new SimpleDateFormat()).format(new Date()));
/* 267 */     stringbuilder.append("\n");
/* 268 */     stringbuilder.append("Description: ");
/* 269 */     stringbuilder.append(this.description);
/* 270 */     stringbuilder.append("\n\n");
/* 271 */     stringbuilder.append(getCauseStackTraceOrString());
/* 272 */     stringbuilder.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
/*     */     
/* 274 */     for (int i = 0; i < 87; i++)
/*     */     {
/* 276 */       stringbuilder.append("-");
/*     */     }
/*     */     
/* 279 */     stringbuilder.append("\n\n");
/* 280 */     getSectionsInStringBuilder(stringbuilder);
/* 281 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getFile() {
/* 289 */     return this.crashReportFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveToFile(File toFile) {
/* 297 */     if (this.crashReportFile != null)
/*     */     {
/* 299 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 303 */     if (toFile.getParentFile() != null)
/*     */     {
/* 305 */       toFile.getParentFile().mkdirs();
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 310 */       FileWriter filewriter = new FileWriter(toFile);
/* 311 */       filewriter.write(getCompleteReport());
/* 312 */       filewriter.close();
/* 313 */       this.crashReportFile = toFile;
/* 314 */       return true;
/*     */     }
/* 316 */     catch (Throwable throwable) {
/*     */       
/* 318 */       logger.error("Could not save crash report to " + toFile, throwable);
/* 319 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReportCategory getCategory() {
/* 326 */     return this.theReportCategory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReportCategory makeCategory(String name) {
/* 334 */     return makeCategoryDepth(name, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReportCategory makeCategoryDepth(String categoryName, int stacktraceLength) {
/* 342 */     CrashReportCategory crashreportcategory = new CrashReportCategory(this, categoryName);
/*     */     
/* 344 */     if (this.field_85059_f) {
/*     */       
/* 346 */       int i = crashreportcategory.getPrunedStackTrace(stacktraceLength);
/* 347 */       StackTraceElement[] astacktraceelement = this.cause.getStackTrace();
/* 348 */       StackTraceElement stacktraceelement = null;
/* 349 */       StackTraceElement stacktraceelement1 = null;
/* 350 */       int j = astacktraceelement.length - i;
/*     */       
/* 352 */       if (j < 0)
/*     */       {
/* 354 */         System.out.println("Negative index in crash report handler (" + astacktraceelement.length + "/" + i + ")");
/*     */       }
/*     */       
/* 357 */       if (astacktraceelement != null && j >= 0 && j < astacktraceelement.length) {
/*     */         
/* 359 */         stacktraceelement = astacktraceelement[j];
/*     */         
/* 361 */         if (astacktraceelement.length + 1 - i < astacktraceelement.length)
/*     */         {
/* 363 */           stacktraceelement1 = astacktraceelement[astacktraceelement.length + 1 - i];
/*     */         }
/*     */       } 
/*     */       
/* 367 */       this.field_85059_f = crashreportcategory.firstTwoElementsOfStackTraceMatch(stacktraceelement, stacktraceelement1);
/*     */       
/* 369 */       if (i > 0 && !this.crashReportSections.isEmpty()) {
/*     */         
/* 371 */         CrashReportCategory crashreportcategory1 = this.crashReportSections.get(this.crashReportSections.size() - 1);
/* 372 */         crashreportcategory1.trimStackTraceEntriesFromBottom(i);
/*     */       }
/* 374 */       else if (astacktraceelement != null && astacktraceelement.length >= i && j >= 0 && j < astacktraceelement.length) {
/*     */         
/* 376 */         this.stacktrace = new StackTraceElement[j];
/* 377 */         System.arraycopy(astacktraceelement, 0, this.stacktrace, 0, this.stacktrace.length);
/*     */       }
/*     */       else {
/*     */         
/* 381 */         this.field_85059_f = false;
/*     */       } 
/*     */     } 
/*     */     
/* 385 */     this.crashReportSections.add(crashreportcategory);
/* 386 */     return crashreportcategory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getWittyComment() {
/* 394 */     String[] astring = { "Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!", "But it works on my machine." };
/*     */ 
/*     */     
/*     */     try {
/* 398 */       return astring[(int)(System.nanoTime() % astring.length)];
/*     */     }
/* 400 */     catch (Throwable var2) {
/*     */       
/* 402 */       return "Witty comment unavailable :(";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CrashReport makeCrashReport(Throwable causeIn, String descriptionIn) {
/*     */     CrashReport crashreport;
/* 413 */     if (causeIn instanceof ReportedException) {
/*     */       
/* 415 */       crashreport = ((ReportedException)causeIn).getCrashReport();
/*     */     }
/*     */     else {
/*     */       
/* 419 */       crashreport = new CrashReport(descriptionIn, causeIn);
/*     */     } 
/*     */     
/* 422 */     return crashreport;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\crash\CrashReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */