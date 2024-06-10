/*    */ package org.spongepowered.tools.obfuscation.mapping.mcp;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import javax.annotation.processing.Filer;
/*    */ import javax.annotation.processing.Messager;
/*    */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*    */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*    */ import org.spongepowered.tools.obfuscation.ObfuscationType;
/*    */ import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
/*    */ import org.spongepowered.tools.obfuscation.mapping.common.MappingWriter;
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
/*    */ public class MappingWriterSrg
/*    */   extends MappingWriter
/*    */ {
/*    */   public MappingWriterSrg(Messager messager, Filer filer) {
/* 46 */     super(messager, filer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(String output, ObfuscationType type, IMappingConsumer.MappingSet<MappingField> fields, IMappingConsumer.MappingSet<MappingMethod> methods) {
/* 51 */     if (output == null) {
/*    */       return;
/*    */     }
/*    */     
/* 55 */     PrintWriter writer = null;
/*    */     
/*    */     try {
/* 58 */       writer = openFileWriter(output, type + " output SRGs");
/* 59 */       writeFieldMappings(writer, fields);
/* 60 */       writeMethodMappings(writer, methods);
/* 61 */     } catch (IOException ex) {
/* 62 */       ex.printStackTrace();
/*    */     } finally {
/* 64 */       if (writer != null) {
/*    */         try {
/* 66 */           writer.close();
/* 67 */         } catch (Exception exception) {}
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeFieldMappings(PrintWriter writer, IMappingConsumer.MappingSet<MappingField> fields) {
/* 75 */     for (IMappingConsumer.MappingSet.Pair<MappingField> field : fields) {
/* 76 */       writer.println(formatFieldMapping(field));
/*    */     }
/*    */   }
/*    */   
/*    */   protected void writeMethodMappings(PrintWriter writer, IMappingConsumer.MappingSet<MappingMethod> methods) {
/* 81 */     for (IMappingConsumer.MappingSet.Pair<MappingMethod> method : methods) {
/* 82 */       writer.println(formatMethodMapping(method));
/*    */     }
/*    */   }
/*    */   
/*    */   protected String formatFieldMapping(IMappingConsumer.MappingSet.Pair<MappingField> mapping) {
/* 87 */     return String.format("FD: %s/%s %s/%s", new Object[] { ((MappingField)mapping.from).getOwner(), ((MappingField)mapping.from).getName(), ((MappingField)mapping.to).getOwner(), ((MappingField)mapping.to).getName() });
/*    */   }
/*    */   
/*    */   protected String formatMethodMapping(IMappingConsumer.MappingSet.Pair<MappingMethod> mapping) {
/* 91 */     return String.format("MD: %s %s %s %s", new Object[] { ((MappingMethod)mapping.from).getName(), ((MappingMethod)mapping.from).getDesc(), ((MappingMethod)mapping.to).getName(), ((MappingMethod)mapping.to).getDesc() });
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\mapping\mcp\MappingWriterSrg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */