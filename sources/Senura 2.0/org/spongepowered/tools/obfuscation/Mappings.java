/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
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
/*     */ class Mappings
/*     */   implements IMappingConsumer
/*     */ {
/*     */   public static class MappingConflictException
/*     */     extends RuntimeException
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private final IMapping<?> oldMapping;
/*     */     private final IMapping<?> newMapping;
/*     */     
/*     */     public MappingConflictException(IMapping<?> oldMapping, IMapping<?> newMapping) {
/*  51 */       this.oldMapping = oldMapping;
/*  52 */       this.newMapping = newMapping;
/*     */     }
/*     */     
/*     */     public IMapping<?> getOld() {
/*  56 */       return this.oldMapping;
/*     */     }
/*     */     
/*     */     public IMapping<?> getNew() {
/*  60 */       return this.newMapping;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class UniqueMappings
/*     */     implements IMappingConsumer
/*     */   {
/*     */     private final IMappingConsumer mappings;
/*     */ 
/*     */     
/*  73 */     private final Map<ObfuscationType, Map<MappingField, MappingField>> fields = new HashMap<ObfuscationType, Map<MappingField, MappingField>>();
/*     */     
/*  75 */     private final Map<ObfuscationType, Map<MappingMethod, MappingMethod>> methods = new HashMap<ObfuscationType, Map<MappingMethod, MappingMethod>>();
/*     */ 
/*     */     
/*     */     public UniqueMappings(IMappingConsumer mappings) {
/*  79 */       this.mappings = mappings;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  84 */       clearMaps();
/*  85 */       this.mappings.clear();
/*     */     }
/*     */     
/*     */     protected void clearMaps() {
/*  89 */       this.fields.clear();
/*  90 */       this.methods.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public void addFieldMapping(ObfuscationType type, MappingField from, MappingField to) {
/*  95 */       if (!checkForExistingMapping(type, from, to, this.fields)) {
/*  96 */         this.mappings.addFieldMapping(type, from, to);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void addMethodMapping(ObfuscationType type, MappingMethod from, MappingMethod to) {
/* 102 */       if (!checkForExistingMapping(type, from, to, this.methods)) {
/* 103 */         this.mappings.addMethodMapping(type, from, to);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private <TMapping extends IMapping<TMapping>> boolean checkForExistingMapping(ObfuscationType type, TMapping from, TMapping to, Map<ObfuscationType, Map<TMapping, TMapping>> mappings) throws Mappings.MappingConflictException {
/* 109 */       Map<TMapping, TMapping> existingMappings = mappings.get(type);
/* 110 */       if (existingMappings == null) {
/* 111 */         existingMappings = new HashMap<TMapping, TMapping>();
/* 112 */         mappings.put(type, existingMappings);
/*     */       } 
/* 114 */       IMapping iMapping = (IMapping)existingMappings.get(from);
/* 115 */       if (iMapping != null) {
/* 116 */         if (iMapping.equals(to)) {
/* 117 */           return true;
/*     */         }
/* 119 */         throw new Mappings.MappingConflictException(iMapping, to);
/*     */       } 
/* 121 */       existingMappings.put(from, to);
/* 122 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public IMappingConsumer.MappingSet<MappingField> getFieldMappings(ObfuscationType type) {
/* 127 */       return this.mappings.getFieldMappings(type);
/*     */     }
/*     */ 
/*     */     
/*     */     public IMappingConsumer.MappingSet<MappingMethod> getMethodMappings(ObfuscationType type) {
/* 132 */       return this.mappings.getMethodMappings(type);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   private final Map<ObfuscationType, IMappingConsumer.MappingSet<MappingField>> fieldMappings = new HashMap<ObfuscationType, IMappingConsumer.MappingSet<MappingField>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 146 */   private final Map<ObfuscationType, IMappingConsumer.MappingSet<MappingMethod>> methodMappings = new HashMap<ObfuscationType, IMappingConsumer.MappingSet<MappingMethod>>();
/*     */   
/*     */   private UniqueMappings unique;
/*     */ 
/*     */   
/*     */   public Mappings() {
/* 152 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/* 156 */     for (ObfuscationType obfType : ObfuscationType.types()) {
/* 157 */       this.fieldMappings.put(obfType, new IMappingConsumer.MappingSet());
/* 158 */       this.methodMappings.put(obfType, new IMappingConsumer.MappingSet());
/*     */     } 
/*     */   }
/*     */   
/*     */   public IMappingConsumer asUnique() {
/* 163 */     if (this.unique == null) {
/* 164 */       this.unique = new UniqueMappings(this);
/*     */     }
/* 166 */     return this.unique;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMappingConsumer.MappingSet<MappingField> getFieldMappings(ObfuscationType type) {
/* 174 */     IMappingConsumer.MappingSet<MappingField> mappings = this.fieldMappings.get(type);
/* 175 */     return (mappings != null) ? mappings : new IMappingConsumer.MappingSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMappingConsumer.MappingSet<MappingMethod> getMethodMappings(ObfuscationType type) {
/* 183 */     IMappingConsumer.MappingSet<MappingMethod> mappings = this.methodMappings.get(type);
/* 184 */     return (mappings != null) ? mappings : new IMappingConsumer.MappingSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 192 */     this.fieldMappings.clear();
/* 193 */     this.methodMappings.clear();
/* 194 */     if (this.unique != null) {
/* 195 */       this.unique.clearMaps();
/*     */     }
/* 197 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFieldMapping(ObfuscationType type, MappingField from, MappingField to) {
/* 202 */     IMappingConsumer.MappingSet<MappingField> mappings = this.fieldMappings.get(type);
/* 203 */     if (mappings == null) {
/* 204 */       mappings = new IMappingConsumer.MappingSet();
/* 205 */       this.fieldMappings.put(type, mappings);
/*     */     } 
/* 207 */     mappings.add(new IMappingConsumer.MappingSet.Pair((IMapping)from, (IMapping)to));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMethodMapping(ObfuscationType type, MappingMethod from, MappingMethod to) {
/* 212 */     IMappingConsumer.MappingSet<MappingMethod> mappings = this.methodMappings.get(type);
/* 213 */     if (mappings == null) {
/* 214 */       mappings = new IMappingConsumer.MappingSet();
/* 215 */       this.methodMappings.put(type, mappings);
/*     */     } 
/* 217 */     mappings.add(new IMappingConsumer.MappingSet.Pair((IMapping)from, (IMapping)to));
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\Mappings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */