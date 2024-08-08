package org.spongepowered.asm.mixin.transformer.ext.extensions;

import com.google.common.base.Charsets;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

public class ExtensionCheckInterfaces implements IExtension {
   private static final String AUDIT_DIR = "audit";
   private static final String IMPL_REPORT_FILENAME = "mixin_implementation_report";
   private static final String IMPL_REPORT_CSV_FILENAME = "mixin_implementation_report.csv";
   private static final String IMPL_REPORT_TXT_FILENAME = "mixin_implementation_report.txt";
   private static final Logger logger = LogManager.getLogger("mixin");
   private final File csv;
   private final File report;
   private final Multimap interfaceMethods = HashMultimap.create();
   private boolean strict;

   public ExtensionCheckInterfaces() {
      File var1 = new File(Constants.DEBUG_OUTPUT_DIR, "audit");
      var1.mkdirs();
      this.csv = new File(var1, "mixin_implementation_report.csv");
      this.report = new File(var1, "mixin_implementation_report.txt");

      try {
         Files.write("Class,Method,Signature,Interface\n", this.csv, Charsets.ISO_8859_1);
      } catch (IOException var4) {
      }

      try {
         String var2 = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
         Files.write("Mixin Implementation Report generated on " + var2 + "\n", this.report, Charsets.ISO_8859_1);
      } catch (IOException var3) {
      }

   }

   public boolean checkActive(MixinEnvironment var1) {
      this.strict = var1.getOption(MixinEnvironment.Option.CHECK_IMPLEMENTS_STRICT);
      return var1.getOption(MixinEnvironment.Option.CHECK_IMPLEMENTS);
   }

   public void preApply(ITargetClassContext var1) {
      ClassInfo var2 = var1.getClassInfo();
      Iterator var3 = var2.getInterfaceMethods(false).iterator();

      while(var3.hasNext()) {
         ClassInfo.Method var4 = (ClassInfo.Method)var3.next();
         this.interfaceMethods.put(var2, var4);
      }

   }

   public void postApply(ITargetClassContext var1) {
      ClassInfo var2 = var1.getClassInfo();
      if (var2.isAbstract() && !this.strict) {
         logger.info("{} is skipping abstract target {}", new Object[]{this.getClass().getSimpleName(), var1});
      } else {
         String var3 = var2.getName().replace('/', '.');
         int var4 = 0;
         PrettyPrinter var5 = new PrettyPrinter();
         var5.add("Class: %s", var3).hr();
         var5.add("%-32s %-47s  %s", "Return Type", "Missing Method", "From Interface").hr();
         Set var6 = var2.getInterfaceMethods(true);
         HashSet var7 = new HashSet(var2.getSuperClass().getInterfaceMethods(true));
         var7.addAll(this.interfaceMethods.removeAll(var2));
         Iterator var8 = var6.iterator();

         while(true) {
            ClassInfo.Method var9;
            ClassInfo.Method var10;
            do {
               if (!var8.hasNext()) {
                  if (var4 > 0) {
                     var5.hr().add("%82s%s: %d", "", "Total unimplemented", var4);
                     var5.print(System.err);
                     this.appendToTextReport(var5);
                  }

                  return;
               }

               var9 = (ClassInfo.Method)var8.next();
               var10 = var2.findMethodInHierarchy(var9.getName(), var9.getDesc(), ClassInfo.SearchType.ALL_CLASSES, ClassInfo.Traversal.ALL);
            } while(var10 != null && !var10.isAbstract());

            if (!var7.contains(var9)) {
               if (var4 > 0) {
                  var5.add();
               }

               SignaturePrinter var11 = (new SignaturePrinter(var9.getName(), var9.getDesc())).setModifiers("");
               String var12 = var9.getOwner().getName().replace('/', '.');
               ++var4;
               var5.add("%-32s%s", var11.getReturnType(), var11);
               var5.add("%-80s  %s", "", var12);
               this.appendToCSVReport(var3, var9, var12);
            }
         }
      }
   }

   public void export(MixinEnvironment var1, String var2, boolean var3, byte[] var4) {
   }

   private void appendToCSVReport(String var1, ClassInfo.Method var2, String var3) {
      try {
         Files.append(String.format("%s,%s,%s,%s\n", var1, var2.getName(), var2.getDesc(), var3), this.csv, Charsets.ISO_8859_1);
      } catch (IOException var5) {
      }

   }

   private void appendToTextReport(PrettyPrinter var1) {
      FileOutputStream var2 = null;

      try {
         var2 = new FileOutputStream(this.report, true);
         PrintStream var3 = new PrintStream(var2);
         var3.print("\n");
         var1.print(var3);
      } catch (Exception var7) {
      } finally {
         IOUtils.closeQuietly(var2);
      }

   }
}
