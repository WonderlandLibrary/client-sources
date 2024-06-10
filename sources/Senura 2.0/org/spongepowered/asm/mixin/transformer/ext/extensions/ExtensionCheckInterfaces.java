/*     */ package org.spongepowered.asm.mixin.transformer.ext.extensions;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.collect.HashMultimap;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.google.common.io.Files;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.transformer.ClassInfo;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IExtension;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
/*     */ import org.spongepowered.asm.util.Constants;
/*     */ import org.spongepowered.asm.util.PrettyPrinter;
/*     */ import org.spongepowered.asm.util.SignaturePrinter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExtensionCheckInterfaces
/*     */   implements IExtension
/*     */ {
/*     */   private static final String AUDIT_DIR = "audit";
/*     */   private static final String IMPL_REPORT_FILENAME = "mixin_implementation_report";
/*     */   private static final String IMPL_REPORT_CSV_FILENAME = "mixin_implementation_report.csv";
/*     */   private static final String IMPL_REPORT_TXT_FILENAME = "mixin_implementation_report.txt";
/*  67 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final File csv;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final File report;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private final Multimap<ClassInfo, ClassInfo.Method> interfaceMethods = (Multimap<ClassInfo, ClassInfo.Method>)HashMultimap.create();
/*     */ 
/*     */   
/*     */   private boolean strict;
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtensionCheckInterfaces() {
/*  91 */     File debugOutputFolder = new File(Constants.DEBUG_OUTPUT_DIR, "audit");
/*  92 */     debugOutputFolder.mkdirs();
/*  93 */     this.csv = new File(debugOutputFolder, "mixin_implementation_report.csv");
/*  94 */     this.report = new File(debugOutputFolder, "mixin_implementation_report.txt");
/*     */     
/*     */     try {
/*  97 */       Files.write("Class,Method,Signature,Interface\n", this.csv, Charsets.ISO_8859_1);
/*  98 */     } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 103 */       String dateTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/* 104 */       Files.write("Mixin Implementation Report generated on " + dateTime + "\n", this.report, Charsets.ISO_8859_1);
/* 105 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkActive(MixinEnvironment environment) {
/* 116 */     this.strict = environment.getOption(MixinEnvironment.Option.CHECK_IMPLEMENTS_STRICT);
/* 117 */     return environment.getOption(MixinEnvironment.Option.CHECK_IMPLEMENTS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preApply(ITargetClassContext context) {
/* 126 */     ClassInfo targetClassInfo = context.getClassInfo();
/* 127 */     for (ClassInfo.Method m : targetClassInfo.getInterfaceMethods(false)) {
/* 128 */       this.interfaceMethods.put(targetClassInfo, m);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postApply(ITargetClassContext context) {
/* 138 */     ClassInfo targetClassInfo = context.getClassInfo();
/*     */ 
/*     */     
/* 141 */     if (targetClassInfo.isAbstract() && !this.strict) {
/* 142 */       logger.info("{} is skipping abstract target {}", new Object[] { getClass().getSimpleName(), context });
/*     */       
/*     */       return;
/*     */     } 
/* 146 */     String className = targetClassInfo.getName().replace('/', '.');
/* 147 */     int missingMethodCount = 0;
/* 148 */     PrettyPrinter printer = new PrettyPrinter();
/*     */     
/* 150 */     printer.add("Class: %s", new Object[] { className }).hr();
/* 151 */     printer.add("%-32s %-47s  %s", new Object[] { "Return Type", "Missing Method", "From Interface" }).hr();
/*     */     
/* 153 */     Set<ClassInfo.Method> interfaceMethods = targetClassInfo.getInterfaceMethods(true);
/* 154 */     Set<ClassInfo.Method> implementedMethods = new HashSet<ClassInfo.Method>(targetClassInfo.getSuperClass().getInterfaceMethods(true));
/* 155 */     implementedMethods.addAll(this.interfaceMethods.removeAll(targetClassInfo));
/*     */     
/* 157 */     for (ClassInfo.Method method : interfaceMethods) {
/* 158 */       ClassInfo.Method found = targetClassInfo.findMethodInHierarchy(method.getName(), method.getDesc(), ClassInfo.SearchType.ALL_CLASSES, ClassInfo.Traversal.ALL);
/*     */ 
/*     */       
/* 161 */       if (found != null && !found.isAbstract()) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 166 */       if (implementedMethods.contains(method)) {
/*     */         continue;
/*     */       }
/*     */       
/* 170 */       if (missingMethodCount > 0) {
/* 171 */         printer.add();
/*     */       }
/*     */       
/* 174 */       SignaturePrinter signaturePrinter = (new SignaturePrinter(method.getName(), method.getDesc())).setModifiers("");
/* 175 */       String iface = method.getOwner().getName().replace('/', '.');
/* 176 */       missingMethodCount++;
/* 177 */       printer.add("%-32s%s", new Object[] { signaturePrinter.getReturnType(), signaturePrinter });
/* 178 */       printer.add("%-80s  %s", new Object[] { "", iface });
/*     */       
/* 180 */       appendToCSVReport(className, method, iface);
/*     */     } 
/*     */     
/* 183 */     if (missingMethodCount > 0) {
/* 184 */       printer.hr().add("%82s%s: %d", new Object[] { "", "Total unimplemented", Integer.valueOf(missingMethodCount) });
/* 185 */       printer.print(System.err);
/* 186 */       appendToTextReport(printer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void export(MixinEnvironment env, String name, boolean force, byte[] bytes) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void appendToCSVReport(String className, ClassInfo.Method method, String iface) {
/*     */     try {
/* 201 */       Files.append(String.format("%s,%s,%s,%s\n", new Object[] { className, method.getName(), method.getDesc(), iface }), this.csv, Charsets.ISO_8859_1);
/* 202 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void appendToTextReport(PrettyPrinter printer) {
/* 208 */     FileOutputStream fos = null;
/*     */     
/*     */     try {
/* 211 */       fos = new FileOutputStream(this.report, true);
/* 212 */       PrintStream stream = new PrintStream(fos);
/* 213 */       stream.print("\n");
/* 214 */       printer.print(stream);
/* 215 */     } catch (Exception exception) {
/*     */     
/*     */     } finally {
/* 218 */       IOUtils.closeQuietly(fos);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\ext\extensions\ExtensionCheckInterfaces.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */