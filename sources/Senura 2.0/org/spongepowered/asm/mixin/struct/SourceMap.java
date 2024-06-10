/*     */ package org.spongepowered.asm.mixin.struct;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.ClassNode;
/*     */ import org.spongepowered.asm.lib.tree.LineNumberNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.util.Bytecode;
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
/*     */ public class SourceMap
/*     */ {
/*     */   private static final String DEFAULT_STRATUM = "Mixin";
/*     */   private static final String NEWLINE = "\n";
/*     */   private final String sourceFile;
/*     */   
/*     */   public static class File
/*     */   {
/*     */     public final int id;
/*     */     public final int lineOffset;
/*     */     public final int size;
/*     */     public final String sourceFileName;
/*     */     public final String sourceFilePath;
/*     */     
/*     */     public File(int id, int lineOffset, int size, String sourceFileName) {
/*  84 */       this(id, lineOffset, size, sourceFileName, null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public File(int id, int lineOffset, int size, String sourceFileName, String sourceFilePath) {
/*  96 */       this.id = id;
/*  97 */       this.lineOffset = lineOffset;
/*  98 */       this.size = size;
/*  99 */       this.sourceFileName = sourceFileName;
/* 100 */       this.sourceFilePath = sourceFilePath;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void applyOffset(ClassNode classNode) {
/* 110 */       for (MethodNode method : classNode.methods) {
/* 111 */         applyOffset(method);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void applyOffset(MethodNode method) {
/* 122 */       for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext(); ) {
/* 123 */         AbstractInsnNode node = iter.next();
/* 124 */         if (node instanceof LineNumberNode) {
/* 125 */           ((LineNumberNode)node).line += this.lineOffset - 1;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     void appendFile(StringBuilder sb) {
/* 131 */       if (this.sourceFilePath != null) {
/* 132 */         sb.append("+ ").append(this.id).append(" ").append(this.sourceFileName).append("\n");
/* 133 */         sb.append(this.sourceFilePath).append("\n");
/*     */       } else {
/* 135 */         sb.append(this.id).append(" ").append(this.sourceFileName).append("\n");
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void appendLines(StringBuilder sb) {
/* 145 */       sb.append("1#").append(this.id)
/* 146 */         .append(",").append(this.size)
/* 147 */         .append(":").append(this.lineOffset)
/* 148 */         .append("\n");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class Stratum
/*     */   {
/*     */     private static final String STRATUM_MARK = "*S";
/*     */ 
/*     */     
/*     */     private static final String FILE_MARK = "*F";
/*     */ 
/*     */     
/*     */     private static final String LINES_MARK = "*L";
/*     */ 
/*     */     
/*     */     public final String name;
/*     */     
/* 167 */     private final Map<String, SourceMap.File> files = new LinkedHashMap<String, SourceMap.File>();
/*     */     
/*     */     public Stratum(String name) {
/* 170 */       this.name = name;
/*     */     }
/*     */     
/*     */     public SourceMap.File addFile(int lineOffset, int size, String sourceFileName, String sourceFilePath) {
/* 174 */       SourceMap.File file = this.files.get(sourceFilePath);
/* 175 */       if (file == null) {
/* 176 */         file = new SourceMap.File(this.files.size() + 1, lineOffset, size, sourceFileName, sourceFilePath);
/* 177 */         this.files.put(sourceFilePath, file);
/*     */       } 
/* 179 */       return file;
/*     */     }
/*     */     
/*     */     void appendTo(StringBuilder sb) {
/* 183 */       sb.append("*S").append(" ").append(this.name).append("\n");
/*     */       
/* 185 */       sb.append("*F").append("\n");
/* 186 */       for (SourceMap.File file : this.files.values()) {
/* 187 */         file.appendFile(sb);
/*     */       }
/*     */       
/* 190 */       sb.append("*L").append("\n");
/* 191 */       for (SourceMap.File file : this.files.values()) {
/* 192 */         file.appendLines(sb);
/*     */       }
/*     */     }
/*     */   }
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
/* 206 */   private final Map<String, Stratum> strata = new LinkedHashMap<String, Stratum>();
/*     */   
/* 208 */   private int nextLineOffset = 1;
/*     */   
/* 210 */   private String defaultStratum = "Mixin";
/*     */   
/*     */   public SourceMap(String sourceFile) {
/* 213 */     this.sourceFile = sourceFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSourceFile() {
/* 220 */     return this.sourceFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPseudoGeneratedSourceFile() {
/* 227 */     return this.sourceFile.replace(".java", "$mixin.java");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File addFile(ClassNode classNode) {
/* 237 */     return addFile(this.defaultStratum, classNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File addFile(String stratumName, ClassNode classNode) {
/* 248 */     return addFile(stratumName, classNode.sourceFile, classNode.name + ".java", Bytecode.getMaxLineNumber(classNode, 500, 50));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File addFile(String sourceFileName, String sourceFilePath, int size) {
/* 260 */     return addFile(this.defaultStratum, sourceFileName, sourceFilePath, size);
/*     */   }
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
/*     */   public File addFile(String stratumName, String sourceFileName, String sourceFilePath, int size) {
/* 273 */     Stratum stratum = this.strata.get(stratumName);
/* 274 */     if (stratum == null) {
/* 275 */       stratum = new Stratum(stratumName);
/* 276 */       this.strata.put(stratumName, stratum);
/*     */     } 
/*     */     
/* 279 */     File file = stratum.addFile(this.nextLineOffset, size, sourceFileName, sourceFilePath);
/* 280 */     this.nextLineOffset += size;
/* 281 */     return file;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 286 */     StringBuilder sb = new StringBuilder();
/* 287 */     appendTo(sb);
/* 288 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private void appendTo(StringBuilder sb) {
/* 293 */     sb.append("SMAP").append("\n");
/*     */ 
/*     */     
/* 296 */     sb.append(getSourceFile()).append("\n");
/*     */ 
/*     */     
/* 299 */     sb.append(this.defaultStratum).append("\n");
/* 300 */     for (Stratum stratum : this.strata.values()) {
/* 301 */       stratum.appendTo(sb);
/*     */     }
/*     */ 
/*     */     
/* 305 */     sb.append("*E").append("\n");
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\struct\SourceMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */