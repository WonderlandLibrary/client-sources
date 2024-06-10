/*    */ package org.spongepowered.tools.obfuscation.mcp;
/*    */ 
/*    */ import javax.annotation.processing.Filer;
/*    */ import javax.annotation.processing.Messager;
/*    */ import org.spongepowered.tools.obfuscation.ObfuscationEnvironment;
/*    */ import org.spongepowered.tools.obfuscation.ObfuscationType;
/*    */ import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;
/*    */ import org.spongepowered.tools.obfuscation.mapping.IMappingWriter;
/*    */ import org.spongepowered.tools.obfuscation.mapping.mcp.MappingProviderSrg;
/*    */ import org.spongepowered.tools.obfuscation.mapping.mcp.MappingWriterSrg;
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
/*    */ public class ObfuscationEnvironmentMCP
/*    */   extends ObfuscationEnvironment
/*    */ {
/*    */   protected ObfuscationEnvironmentMCP(ObfuscationType type) {
/* 43 */     super(type);
/*    */   }
/*    */ 
/*    */   
/*    */   protected IMappingProvider getMappingProvider(Messager messager, Filer filer) {
/* 48 */     return (IMappingProvider)new MappingProviderSrg(messager, filer);
/*    */   }
/*    */ 
/*    */   
/*    */   protected IMappingWriter getMappingWriter(Messager messager, Filer filer) {
/* 53 */     return (IMappingWriter)new MappingWriterSrg(messager, filer);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\mcp\ObfuscationEnvironmentMCP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */