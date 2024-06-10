/*    */ package org.spongepowered.tools.obfuscation.mapping.common;
/*    */ 
/*    */ import com.google.common.collect.BiMap;
/*    */ import com.google.common.collect.HashBiMap;
/*    */ import javax.annotation.processing.Filer;
/*    */ import javax.annotation.processing.Messager;
/*    */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*    */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*    */ import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;
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
/*    */ public abstract class MappingProvider
/*    */   implements IMappingProvider
/*    */ {
/*    */   protected final Messager messager;
/*    */   protected final Filer filer;
/* 45 */   protected final BiMap<String, String> packageMap = (BiMap<String, String>)HashBiMap.create();
/* 46 */   protected final BiMap<String, String> classMap = (BiMap<String, String>)HashBiMap.create();
/* 47 */   protected final BiMap<MappingField, MappingField> fieldMap = (BiMap<MappingField, MappingField>)HashBiMap.create();
/* 48 */   protected final BiMap<MappingMethod, MappingMethod> methodMap = (BiMap<MappingMethod, MappingMethod>)HashBiMap.create();
/*    */   
/*    */   public MappingProvider(Messager messager, Filer filer) {
/* 51 */     this.messager = messager;
/* 52 */     this.filer = filer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 57 */     this.packageMap.clear();
/* 58 */     this.classMap.clear();
/* 59 */     this.fieldMap.clear();
/* 60 */     this.methodMap.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 65 */     return (this.packageMap.isEmpty() && this.classMap.isEmpty() && this.fieldMap.isEmpty() && this.methodMap.isEmpty());
/*    */   }
/*    */ 
/*    */   
/*    */   public MappingMethod getMethodMapping(MappingMethod method) {
/* 70 */     return (MappingMethod)this.methodMap.get(method);
/*    */   }
/*    */ 
/*    */   
/*    */   public MappingField getFieldMapping(MappingField field) {
/* 75 */     return (MappingField)this.fieldMap.get(field);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getClassMapping(String className) {
/* 80 */     return (String)this.classMap.get(className);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPackageMapping(String packageName) {
/* 85 */     return (String)this.packageMap.get(packageName);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\mapping\common\MappingProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */