/*    */ package org.spongepowered.tools.obfuscation.mapping.common;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import javax.annotation.processing.Filer;
/*    */ import javax.annotation.processing.Messager;
/*    */ import javax.tools.Diagnostic;
/*    */ import javax.tools.FileObject;
/*    */ import javax.tools.StandardLocation;
/*    */ import org.spongepowered.tools.obfuscation.mapping.IMappingWriter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MappingWriter
/*    */   implements IMappingWriter
/*    */ {
/*    */   private final Messager messager;
/*    */   private final Filer filer;
/*    */   
/*    */   public MappingWriter(Messager messager, Filer filer) {
/* 49 */     this.messager = messager;
/* 50 */     this.filer = filer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected PrintWriter openFileWriter(String fileName, String description) throws IOException {
/* 62 */     if (fileName.matches("^.*[\\\\/:].*$")) {
/* 63 */       File outFile = new File(fileName);
/* 64 */       outFile.getParentFile().mkdirs();
/* 65 */       this.messager.printMessage(Diagnostic.Kind.NOTE, "Writing " + description + " to " + outFile.getAbsolutePath());
/* 66 */       return new PrintWriter(outFile);
/*    */     } 
/*    */     
/* 69 */     FileObject outResource = this.filer.createResource(StandardLocation.CLASS_OUTPUT, "", fileName, new javax.lang.model.element.Element[0]);
/* 70 */     this.messager.printMessage(Diagnostic.Kind.NOTE, "Writing " + description + " to " + (new File(outResource.toUri())).getAbsolutePath());
/* 71 */     return new PrintWriter(outResource.openWriter());
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\mapping\common\MappingWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */