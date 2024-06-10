/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IReferenceManager;
/*     */ import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
/*     */ import org.spongepowered.tools.obfuscation.service.ObfuscationServices;
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
/*     */ public class ObfuscationManager
/*     */   implements IObfuscationManager
/*     */ {
/*     */   private final IMixinAnnotationProcessor ap;
/*  50 */   private final List<ObfuscationEnvironment> environments = new ArrayList<ObfuscationEnvironment>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final IObfuscationDataProvider obfs;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final IReferenceManager refs;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private final List<IMappingConsumer> consumers = new ArrayList<IMappingConsumer>();
/*     */   
/*     */   private boolean initDone;
/*     */   
/*     */   public ObfuscationManager(IMixinAnnotationProcessor ap) {
/*  70 */     this.ap = ap;
/*  71 */     this.obfs = new ObfuscationDataProvider(ap, this.environments);
/*  72 */     this.refs = new ReferenceManager(ap, this.environments);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  77 */     if (this.initDone) {
/*     */       return;
/*     */     }
/*  80 */     this.initDone = true;
/*  81 */     ObfuscationServices.getInstance().initProviders(this.ap);
/*  82 */     for (ObfuscationType obfType : ObfuscationType.types()) {
/*  83 */       if (obfType.isSupported()) {
/*  84 */         this.environments.add(obfType.createEnvironment());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IObfuscationDataProvider getDataProvider() {
/*  91 */     return this.obfs;
/*     */   }
/*     */ 
/*     */   
/*     */   public IReferenceManager getReferenceManager() {
/*  96 */     return this.refs;
/*     */   }
/*     */ 
/*     */   
/*     */   public IMappingConsumer createMappingConsumer() {
/* 101 */     Mappings mappings = new Mappings();
/* 102 */     this.consumers.add(mappings);
/* 103 */     return mappings;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ObfuscationEnvironment> getEnvironments() {
/* 108 */     return this.environments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeMappings() {
/* 116 */     for (ObfuscationEnvironment env : this.environments) {
/* 117 */       env.writeMappings(this.consumers);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeReferences() {
/* 126 */     this.refs.write();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\ObfuscationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */