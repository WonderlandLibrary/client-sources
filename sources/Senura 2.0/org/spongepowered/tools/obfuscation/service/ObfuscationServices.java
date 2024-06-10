/*     */ package org.spongepowered.tools.obfuscation.service;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.ServiceConfigurationError;
/*     */ import java.util.ServiceLoader;
/*     */ import java.util.Set;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.tools.obfuscation.ObfuscationType;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
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
/*     */ public final class ObfuscationServices
/*     */ {
/*     */   private static ObfuscationServices instance;
/*     */   private final ServiceLoader<IObfuscationService> serviceLoader;
/*  56 */   private final Set<IObfuscationService> services = new HashSet<IObfuscationService>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ObfuscationServices() {
/*  62 */     this.serviceLoader = ServiceLoader.load(IObfuscationService.class, getClass().getClassLoader());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ObfuscationServices getInstance() {
/*  69 */     if (instance == null) {
/*  70 */       instance = new ObfuscationServices();
/*     */     }
/*  72 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initProviders(IMixinAnnotationProcessor ap) {
/*     */     try {
/*  82 */       for (IObfuscationService service : this.serviceLoader) {
/*  83 */         if (!this.services.contains(service)) {
/*  84 */           this.services.add(service);
/*     */           
/*  86 */           String serviceName = service.getClass().getSimpleName();
/*     */           
/*  88 */           Collection<ObfuscationTypeDescriptor> obfTypes = service.getObfuscationTypes();
/*  89 */           if (obfTypes != null) {
/*  90 */             for (ObfuscationTypeDescriptor obfType : obfTypes) {
/*     */               try {
/*  92 */                 ObfuscationType type = ObfuscationType.create(obfType, ap);
/*  93 */                 ap.printMessage(Diagnostic.Kind.NOTE, serviceName + " supports type: \"" + type + "\"");
/*  94 */               } catch (Exception ex) {
/*  95 */                 ex.printStackTrace();
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/* 101 */     } catch (ServiceConfigurationError serviceError) {
/* 102 */       ap.printMessage(Diagnostic.Kind.ERROR, serviceError.getClass().getSimpleName() + ": " + serviceError.getMessage());
/* 103 */       serviceError.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getSupportedOptions() {
/* 111 */     Set<String> supportedOptions = new HashSet<String>();
/* 112 */     for (IObfuscationService provider : this.serviceLoader) {
/* 113 */       Set<String> options = provider.getSupportedOptions();
/* 114 */       if (options != null) {
/* 115 */         supportedOptions.addAll(options);
/*     */       }
/*     */     } 
/* 118 */     return supportedOptions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IObfuscationService getService(Class<? extends IObfuscationService> serviceClass) {
/* 128 */     for (IObfuscationService service : this.serviceLoader) {
/* 129 */       if (serviceClass.getName().equals(service.getClass().getName())) {
/* 130 */         return service;
/*     */       }
/*     */     } 
/* 133 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\service\ObfuscationServices.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */