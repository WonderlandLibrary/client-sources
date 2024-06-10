/*     */ package org.spongepowered.asm.launch.platform;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.net.URI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.launch.GlobalProperties;
/*     */ import org.spongepowered.asm.service.MixinService;
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
/*     */ public class MixinContainer
/*     */ {
/*  43 */   private static final List<String> agentClasses = new ArrayList<String>();
/*     */   
/*     */   static {
/*  46 */     GlobalProperties.put("mixin.agents", agentClasses);
/*  47 */     for (String agent : MixinService.getService().getPlatformAgents()) {
/*  48 */       agentClasses.add(agent);
/*     */     }
/*  50 */     agentClasses.add("org.spongepowered.asm.launch.platform.MixinPlatformAgentDefault");
/*     */   }
/*     */   
/*  53 */   private final Logger logger = LogManager.getLogger("mixin");
/*     */   
/*     */   private final URI uri;
/*     */   
/*  57 */   private final List<IMixinPlatformAgent> agents = new ArrayList<IMixinPlatformAgent>();
/*     */   
/*     */   public MixinContainer(MixinPlatformManager manager, URI uri) {
/*  60 */     this.uri = uri;
/*     */     
/*  62 */     for (String agentClass : agentClasses) {
/*     */       
/*     */       try {
/*  65 */         Class<IMixinPlatformAgent> clazz = (Class)Class.forName(agentClass);
/*  66 */         Constructor<IMixinPlatformAgent> ctor = clazz.getDeclaredConstructor(new Class[] { MixinPlatformManager.class, URI.class });
/*  67 */         this.logger.debug("Instancing new {} for {}", new Object[] { clazz.getSimpleName(), this.uri });
/*  68 */         IMixinPlatformAgent agent = ctor.newInstance(new Object[] { manager, uri });
/*  69 */         this.agents.add(agent);
/*  70 */       } catch (Exception ex) {
/*  71 */         this.logger.catching(ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URI getURI() {
/*  80 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> getPhaseProviders() {
/*  87 */     List<String> phaseProviders = new ArrayList<String>();
/*  88 */     for (IMixinPlatformAgent agent : this.agents) {
/*  89 */       String phaseProvider = agent.getPhaseProvider();
/*  90 */       if (phaseProvider != null) {
/*  91 */         phaseProviders.add(phaseProvider);
/*     */       }
/*     */     } 
/*  94 */     return phaseProviders;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepare() {
/* 101 */     for (IMixinPlatformAgent agent : this.agents) {
/* 102 */       this.logger.debug("Processing prepare() for {}", new Object[] { agent });
/* 103 */       agent.prepare();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initPrimaryContainer() {
/* 112 */     for (IMixinPlatformAgent agent : this.agents) {
/* 113 */       this.logger.debug("Processing launch tasks for {}", new Object[] { agent });
/* 114 */       agent.initPrimaryContainer();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void inject() {
/* 122 */     for (IMixinPlatformAgent agent : this.agents) {
/* 123 */       this.logger.debug("Processing inject() for {}", new Object[] { agent });
/* 124 */       agent.inject();
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
/*     */   public String getLaunchTarget() {
/* 136 */     for (IMixinPlatformAgent agent : this.agents) {
/* 137 */       String launchTarget = agent.getLaunchTarget();
/* 138 */       if (launchTarget != null) {
/* 139 */         return launchTarget;
/*     */       }
/*     */     } 
/* 142 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\launch\platform\MixinContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */