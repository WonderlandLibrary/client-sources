package org.spongepowered.asm.mixin.struct;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.LineNumberNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.util.Bytecode;

public class SourceMap {
   private static final String DEFAULT_STRATUM = "Mixin";
   private static final String NEWLINE = "\n";
   private final String sourceFile;
   private final Map strata = new LinkedHashMap();
   private int nextLineOffset = 1;
   private String defaultStratum = "Mixin";

   public SourceMap(String var1) {
      this.sourceFile = var1;
   }

   public String getSourceFile() {
      return this.sourceFile;
   }

   public String getPseudoGeneratedSourceFile() {
      return this.sourceFile.replace(".java", "$mixin.java");
   }

   public SourceMap.File addFile(ClassNode var1) {
      return this.addFile(this.defaultStratum, var1);
   }

   public SourceMap.File addFile(String var1, ClassNode var2) {
      return this.addFile(var1, var2.sourceFile, var2.name + ".java", Bytecode.getMaxLineNumber(var2, 500, 50));
   }

   public SourceMap.File addFile(String var1, String var2, int var3) {
      return this.addFile(this.defaultStratum, var1, var2, var3);
   }

   public SourceMap.File addFile(String var1, String var2, String var3, int var4) {
      SourceMap.Stratum var5 = (SourceMap.Stratum)this.strata.get(var1);
      if (var5 == null) {
         var5 = new SourceMap.Stratum(var1);
         this.strata.put(var1, var5);
      }

      SourceMap.File var6 = var5.addFile(this.nextLineOffset, var4, var2, var3);
      this.nextLineOffset += var4;
      return var6;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      this.appendTo(var1);
      return var1.toString();
   }

   private void appendTo(StringBuilder var1) {
      var1.append("SMAP").append("\n");
      var1.append(this.getSourceFile()).append("\n");
      var1.append(this.defaultStratum).append("\n");
      Iterator var2 = this.strata.values().iterator();

      while(var2.hasNext()) {
         SourceMap.Stratum var3 = (SourceMap.Stratum)var2.next();
         var3.appendTo(var1);
      }

      var1.append("*E").append("\n");
   }

   static class Stratum {
      private static final String STRATUM_MARK = "*S";
      private static final String FILE_MARK = "*F";
      private static final String LINES_MARK = "*L";
      public final String name;
      private final Map files = new LinkedHashMap();

      public Stratum(String var1) {
         this.name = var1;
      }

      public SourceMap.File addFile(int var1, int var2, String var3, String var4) {
         SourceMap.File var5 = (SourceMap.File)this.files.get(var4);
         if (var5 == null) {
            var5 = new SourceMap.File(this.files.size() + 1, var1, var2, var3, var4);
            this.files.put(var4, var5);
         }

         return var5;
      }

      void appendTo(StringBuilder var1) {
         var1.append("*S").append(" ").append(this.name).append("\n");
         var1.append("*F").append("\n");
         Iterator var2 = this.files.values().iterator();

         SourceMap.File var3;
         while(var2.hasNext()) {
            var3 = (SourceMap.File)var2.next();
            var3.appendFile(var1);
         }

         var1.append("*L").append("\n");
         var2 = this.files.values().iterator();

         while(var2.hasNext()) {
            var3 = (SourceMap.File)var2.next();
            var3.appendLines(var1);
         }

      }
   }

   public static class File {
      public final int id;
      public final int lineOffset;
      public final int size;
      public final String sourceFileName;
      public final String sourceFilePath;

      public File(int var1, int var2, int var3, String var4) {
         this(var1, var2, var3, var4, (String)null);
      }

      public File(int var1, int var2, int var3, String var4, String var5) {
         this.id = var1;
         this.lineOffset = var2;
         this.size = var3;
         this.sourceFileName = var4;
         this.sourceFilePath = var5;
      }

      public void applyOffset(ClassNode var1) {
         Iterator var2 = var1.methods.iterator();

         while(var2.hasNext()) {
            MethodNode var3 = (MethodNode)var2.next();
            this.applyOffset(var3);
         }

      }

      public void applyOffset(MethodNode var1) {
         ListIterator var2 = var1.instructions.iterator();

         while(var2.hasNext()) {
            AbstractInsnNode var3 = (AbstractInsnNode)var2.next();
            if (var3 instanceof LineNumberNode) {
               ((LineNumberNode)var3).line += this.lineOffset - 1;
            }
         }

      }

      void appendFile(StringBuilder var1) {
         if (this.sourceFilePath != null) {
            var1.append("+ ").append(this.id).append(" ").append(this.sourceFileName).append("\n");
            var1.append(this.sourceFilePath).append("\n");
         } else {
            var1.append(this.id).append(" ").append(this.sourceFileName).append("\n");
         }

      }

      public void appendLines(StringBuilder var1) {
         var1.append("1#").append(this.id).append(",").append(this.size).append(":").append(this.lineOffset).append("\n");
      }
   }
}
