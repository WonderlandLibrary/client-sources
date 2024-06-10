/*     */ package org.spongepowered.tools.agent;
/*     */ 
/*     */ import java.lang.instrument.ClassDefinition;
/*     */ import java.lang.instrument.ClassFileTransformer;
/*     */ import java.lang.instrument.IllegalClassFormatException;
/*     */ import java.lang.instrument.Instrumentation;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.mixin.transformer.MixinTransformer;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IHotSwap;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.MixinReloadException;
/*     */ import org.spongepowered.asm.service.IMixinService;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MixinAgent
/*     */   implements IHotSwap
/*     */ {
/*     */   class Transformer
/*     */     implements ClassFileTransformer
/*     */   {
/*     */     public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain domain, byte[] classfileBuffer) throws IllegalClassFormatException {
/*  58 */       if (classBeingRedefined == null) {
/*  59 */         return null;
/*     */       }
/*     */       
/*  62 */       byte[] mixinBytecode = MixinAgent.classLoader.getFakeMixinBytecode(classBeingRedefined);
/*  63 */       if (mixinBytecode != null) {
/*  64 */         List<String> targets = reloadMixin(className, classfileBuffer);
/*  65 */         if (targets == null || !reApplyMixins(targets)) {
/*  66 */           return MixinAgent.ERROR_BYTECODE;
/*     */         }
/*     */         
/*  69 */         return mixinBytecode;
/*     */       } 
/*     */       
/*     */       try {
/*  73 */         MixinAgent.logger.info("Redefining class " + className);
/*  74 */         return MixinAgent.this.classTransformer.transformClassBytes(null, className, classfileBuffer);
/*  75 */       } catch (Throwable th) {
/*  76 */         MixinAgent.logger.error("Error while re-transforming class " + className, th);
/*  77 */         return MixinAgent.ERROR_BYTECODE;
/*     */       } 
/*     */     }
/*     */     
/*     */     private List<String> reloadMixin(String className, byte[] classfileBuffer) {
/*  82 */       MixinAgent.logger.info("Redefining mixin {}", new Object[] { className });
/*     */       try {
/*  84 */         return MixinAgent.this.classTransformer.reload(className.replace('/', '.'), classfileBuffer);
/*  85 */       } catch (MixinReloadException e) {
/*  86 */         MixinAgent.logger.error("Mixin {} cannot be reloaded, needs a restart to be applied: {} ", new Object[] { e.getMixinInfo(), e.getMessage() });
/*  87 */       } catch (Throwable th) {
/*     */         
/*  89 */         MixinAgent.logger.error("Error while finding targets for mixin " + className, th);
/*     */       } 
/*  91 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean reApplyMixins(List<String> targets) {
/* 102 */       IMixinService service = MixinService.getService();
/*     */       
/* 104 */       for (String target : targets) {
/* 105 */         String targetName = target.replace('/', '.');
/* 106 */         MixinAgent.logger.debug("Re-transforming target class {}", new Object[] { target });
/*     */         try {
/* 108 */           Class<?> targetClass = service.getClassProvider().findClass(targetName);
/* 109 */           byte[] targetBytecode = MixinAgent.classLoader.getOriginalTargetBytecode(targetName);
/* 110 */           if (targetBytecode == null) {
/* 111 */             MixinAgent.logger.error("Target class {} bytecode is not registered", new Object[] { targetName });
/* 112 */             return false;
/*     */           } 
/* 114 */           targetBytecode = MixinAgent.this.classTransformer.transformClassBytes(null, targetName, targetBytecode);
/* 115 */           MixinAgent.instrumentation.redefineClasses(new ClassDefinition[] { new ClassDefinition(targetClass, targetBytecode) });
/* 116 */         } catch (Throwable th) {
/* 117 */           MixinAgent.logger.error("Error while re-transforming target class " + target, th);
/* 118 */           return false;
/*     */         } 
/*     */       } 
/* 121 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   public static final byte[] ERROR_BYTECODE = new byte[] { 1 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   static final MixinAgentClassLoader classLoader = new MixinAgentClassLoader();
/*     */   
/* 137 */   static final Logger logger = LogManager.getLogger("mixin.agent");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   static Instrumentation instrumentation = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   private static List<MixinAgent> agents = new ArrayList<MixinAgent>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final MixinTransformer classTransformer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MixinAgent(MixinTransformer classTransformer) {
/* 162 */     this.classTransformer = classTransformer;
/* 163 */     agents.add(this);
/* 164 */     if (instrumentation != null) {
/* 165 */       initTransformer();
/*     */     }
/*     */   }
/*     */   
/*     */   private void initTransformer() {
/* 170 */     instrumentation.addTransformer(new Transformer(), true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerMixinClass(String name) {
/* 175 */     classLoader.addMixinClass(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerTargetClass(String name, byte[] bytecode) {
/* 180 */     classLoader.addTargetClass(name, bytecode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void init(Instrumentation instrumentation) {
/* 190 */     MixinAgent.instrumentation = instrumentation;
/* 191 */     if (!MixinAgent.instrumentation.isRedefineClassesSupported()) {
/* 192 */       logger.error("The instrumentation doesn't support re-definition of classes");
/*     */     }
/* 194 */     for (MixinAgent agent : agents) {
/* 195 */       agent.initTransformer();
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
/*     */   public static void premain(String arg, Instrumentation instrumentation) {
/* 209 */     System.setProperty("mixin.hotSwap", "true");
/* 210 */     init(instrumentation);
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
/*     */   public static void agentmain(String arg, Instrumentation instrumentation) {
/* 223 */     init(instrumentation);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\agent\MixinAgent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */