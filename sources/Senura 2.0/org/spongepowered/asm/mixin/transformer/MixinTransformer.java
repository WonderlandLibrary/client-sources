/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
/*     */ import java.util.UUID;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.lib.tree.ClassNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.Mixins;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*     */ import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
/*     */ import org.spongepowered.asm.mixin.throwables.ClassAlreadyLoadedException;
/*     */ import org.spongepowered.asm.mixin.throwables.MixinApplyError;
/*     */ import org.spongepowered.asm.mixin.throwables.MixinException;
/*     */ import org.spongepowered.asm.mixin.throwables.MixinPrepareError;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.Extensions;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IExtension;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IHotSwap;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckClass;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckInterfaces;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionClassExporter;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
/*     */ import org.spongepowered.asm.service.IMixinService;
/*     */ import org.spongepowered.asm.service.ITransformer;
/*     */ import org.spongepowered.asm.service.MixinService;
/*     */ import org.spongepowered.asm.transformers.TreeTransformer;
/*     */ import org.spongepowered.asm.util.PrettyPrinter;
/*     */ import org.spongepowered.asm.util.ReEntranceLock;
/*     */ import org.spongepowered.asm.util.perf.Profiler;
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
/*     */ public class MixinTransformer
/*     */   extends TreeTransformer
/*     */ {
/*     */   private static final String MIXIN_AGENT_CLASS = "org.spongepowered.tools.agent.MixinAgent";
/*     */   private static final String METRONOME_AGENT_CLASS = "org.spongepowered.metronome.Agent";
/*     */   
/*     */   enum ErrorPhase
/*     */   {
/*  84 */     PREPARE
/*     */     {
/*     */       IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler handler, String context, InvalidMixinException ex, IMixinInfo mixin, IMixinErrorHandler.ErrorAction action) {
/*     */         try {
/*  88 */           return handler.onPrepareError(mixin.getConfig(), (Throwable)ex, mixin, action);
/*  89 */         } catch (AbstractMethodError ame) {
/*     */           
/*  91 */           return action;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       protected String getContext(IMixinInfo mixin, String context) {
/*  97 */         return String.format("preparing %s in %s", new Object[] { mixin.getName(), context
/*     */ 
/*     */             
/*     */             });
/*     */       }
/*     */     },
/* 103 */     APPLY
/*     */     {
/*     */       IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler handler, String context, InvalidMixinException ex, IMixinInfo mixin, IMixinErrorHandler.ErrorAction action) {
/*     */         try {
/* 107 */           return handler.onApplyError(context, (Throwable)ex, mixin, action);
/* 108 */         } catch (AbstractMethodError ame) {
/*     */           
/* 110 */           return action;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       protected String getContext(IMixinInfo mixin, String context) {
/* 116 */         return String.format("%s -> %s", new Object[] { mixin, context });
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */     
/*     */     private final String text;
/*     */ 
/*     */     
/*     */     ErrorPhase() {
/* 126 */       this.text = name().toLowerCase();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getLogMessage(String context, InvalidMixinException ex, IMixinInfo mixin) {
/* 134 */       return String.format("Mixin %s failed %s: %s %s", new Object[] { this.text, getContext(mixin, context), ex.getClass().getName(), ex.getMessage() });
/*     */     }
/*     */     
/*     */     public String getErrorMessage(IMixinInfo mixin, IMixinConfig config, MixinEnvironment.Phase phase) {
/* 138 */       return String.format("Mixin [%s] from phase [%s] in config [%s] FAILED during %s", new Object[] { mixin, phase, config, name() });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     abstract IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler param1IMixinErrorHandler, String param1String, InvalidMixinException param1InvalidMixinException, IMixinInfo param1IMixinInfo, IMixinErrorHandler.ErrorAction param1ErrorAction);
/*     */ 
/*     */     
/*     */     protected abstract String getContext(IMixinInfo param1IMixinInfo, String param1String);
/*     */   }
/*     */   
/* 149 */   static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   private final IMixinService service = MixinService.getService();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 159 */   private final List<MixinConfig> configs = new ArrayList<MixinConfig>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   private final List<MixinConfig> pendingConfigs = new ArrayList<MixinConfig>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ReEntranceLock lock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   private final String sessionId = UUID.randomUUID().toString();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Extensions extensions;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final IHotSwap hotSwapper;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final MixinPostProcessor postProcessor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Profiler profiler;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MixinEnvironment currentEnvironment;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 206 */   private Level verboseLoggingLevel = Level.DEBUG;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean errorState = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 216 */   private int transformedCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MixinTransformer() {
/* 222 */     MixinEnvironment environment = MixinEnvironment.getCurrentEnvironment();
/*     */     
/* 224 */     Object globalMixinTransformer = environment.getActiveTransformer();
/* 225 */     if (globalMixinTransformer instanceof ITransformer) {
/* 226 */       throw new MixinException("Terminating MixinTransformer instance " + this);
/*     */     }
/*     */ 
/*     */     
/* 230 */     environment.setActiveTransformer((ITransformer)this);
/*     */     
/* 232 */     this.lock = this.service.getReEntranceLock();
/*     */     
/* 234 */     this.extensions = new Extensions(this);
/* 235 */     this.hotSwapper = initHotSwapper(environment);
/* 236 */     this.postProcessor = new MixinPostProcessor();
/*     */     
/* 238 */     this.extensions.add((IClassGenerator)new ArgsClassGenerator());
/* 239 */     this.extensions.add(new InnerClassGenerator());
/*     */     
/* 241 */     this.extensions.add((IExtension)new ExtensionClassExporter(environment));
/* 242 */     this.extensions.add((IExtension)new ExtensionCheckClass());
/* 243 */     this.extensions.add((IExtension)new ExtensionCheckInterfaces());
/*     */     
/* 245 */     this.profiler = MixinEnvironment.getProfiler();
/*     */   }
/*     */   
/*     */   private IHotSwap initHotSwapper(MixinEnvironment environment) {
/* 249 */     if (!environment.getOption(MixinEnvironment.Option.HOT_SWAP)) {
/* 250 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 254 */       logger.info("Attempting to load Hot-Swap agent");
/*     */ 
/*     */       
/* 257 */       Class<? extends IHotSwap> clazz = (Class)Class.forName("org.spongepowered.tools.agent.MixinAgent");
/* 258 */       Constructor<? extends IHotSwap> ctor = clazz.getDeclaredConstructor(new Class[] { MixinTransformer.class });
/* 259 */       return ctor.newInstance(new Object[] { this });
/* 260 */     } catch (Throwable th) {
/* 261 */       logger.info("Hot-swap agent could not be loaded, hot swapping of mixins won't work. {}: {}", new Object[] { th
/* 262 */             .getClass().getSimpleName(), th.getMessage() });
/*     */ 
/*     */       
/* 265 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void audit(MixinEnvironment environment) {
/* 274 */     Set<String> unhandled = new HashSet<String>();
/*     */     
/* 276 */     for (MixinConfig config : this.configs) {
/* 277 */       unhandled.addAll(config.getUnhandledTargets());
/*     */     }
/*     */     
/* 280 */     Logger auditLogger = LogManager.getLogger("mixin/audit");
/*     */     
/* 282 */     for (String target : unhandled) {
/*     */       try {
/* 284 */         auditLogger.info("Force-loading class {}", new Object[] { target });
/* 285 */         this.service.getClassProvider().findClass(target, true);
/* 286 */       } catch (ClassNotFoundException ex) {
/* 287 */         auditLogger.error("Could not force-load " + target, ex);
/*     */       } 
/*     */     } 
/*     */     
/* 291 */     for (MixinConfig config : this.configs) {
/* 292 */       for (String target : config.getUnhandledTargets()) {
/* 293 */         ClassAlreadyLoadedException ex = new ClassAlreadyLoadedException(target + " was already classloaded");
/* 294 */         auditLogger.error("Could not force-load " + target, (Throwable)ex);
/*     */       } 
/*     */     } 
/*     */     
/* 298 */     if (environment.getOption(MixinEnvironment.Option.DEBUG_PROFILER)) {
/* 299 */       printProfilerSummary();
/*     */     }
/*     */   }
/*     */   
/*     */   private void printProfilerSummary() {
/* 304 */     DecimalFormat threedp = new DecimalFormat("(###0.000");
/* 305 */     DecimalFormat onedp = new DecimalFormat("(###0.0");
/* 306 */     PrettyPrinter printer = this.profiler.printer(false, false);
/*     */     
/* 308 */     long prepareTime = this.profiler.get("mixin.prepare").getTotalTime();
/* 309 */     long readTime = this.profiler.get("mixin.read").getTotalTime();
/* 310 */     long applyTime = this.profiler.get("mixin.apply").getTotalTime();
/* 311 */     long writeTime = this.profiler.get("mixin.write").getTotalTime();
/* 312 */     long totalMixinTime = this.profiler.get("mixin").getTotalTime();
/*     */     
/* 314 */     long loadTime = this.profiler.get("class.load").getTotalTime();
/* 315 */     long transformTime = this.profiler.get("class.transform").getTotalTime();
/* 316 */     long exportTime = this.profiler.get("mixin.debug.export").getTotalTime();
/* 317 */     long actualTime = totalMixinTime - loadTime - transformTime - exportTime;
/* 318 */     double timeSliceMixin = actualTime / totalMixinTime * 100.0D;
/* 319 */     double timeSliceLoad = loadTime / totalMixinTime * 100.0D;
/* 320 */     double timeSliceTransform = transformTime / totalMixinTime * 100.0D;
/* 321 */     double timeSliceExport = exportTime / totalMixinTime * 100.0D;
/*     */     
/* 323 */     long worstTransformerTime = 0L;
/* 324 */     Profiler.Section worstTransformer = null;
/*     */     
/* 326 */     for (Profiler.Section section : this.profiler.getSections()) {
/* 327 */       long transformerTime = section.getName().startsWith("class.transform.") ? section.getTotalTime() : 0L;
/* 328 */       if (transformerTime > worstTransformerTime) {
/* 329 */         worstTransformerTime = transformerTime;
/* 330 */         worstTransformer = section;
/*     */       } 
/*     */     } 
/*     */     
/* 334 */     printer.hr().add("Summary").hr().add();
/*     */     
/* 336 */     String format = "%9d ms %12s seconds)";
/* 337 */     printer.kv("Total mixin time", format, new Object[] { Long.valueOf(totalMixinTime), threedp.format(totalMixinTime * 0.001D) }).add();
/* 338 */     printer.kv("Preparing mixins", format, new Object[] { Long.valueOf(prepareTime), threedp.format(prepareTime * 0.001D) });
/* 339 */     printer.kv("Reading input", format, new Object[] { Long.valueOf(readTime), threedp.format(readTime * 0.001D) });
/* 340 */     printer.kv("Applying mixins", format, new Object[] { Long.valueOf(applyTime), threedp.format(applyTime * 0.001D) });
/* 341 */     printer.kv("Writing output", format, new Object[] { Long.valueOf(writeTime), threedp.format(writeTime * 0.001D) }).add();
/*     */     
/* 343 */     printer.kv("of which", "");
/* 344 */     printer.kv("Time spent loading from disk", format, new Object[] { Long.valueOf(loadTime), threedp.format(loadTime * 0.001D) });
/* 345 */     printer.kv("Time spent transforming classes", format, new Object[] { Long.valueOf(transformTime), threedp.format(transformTime * 0.001D) }).add();
/*     */     
/* 347 */     if (worstTransformer != null) {
/* 348 */       printer.kv("Worst transformer", worstTransformer.getName());
/* 349 */       printer.kv("Class", worstTransformer.getInfo());
/* 350 */       printer.kv("Time spent", "%s seconds", new Object[] { Double.valueOf(worstTransformer.getTotalSeconds()) });
/* 351 */       printer.kv("called", "%d times", new Object[] { Integer.valueOf(worstTransformer.getTotalCount()) }).add();
/*     */     } 
/*     */     
/* 354 */     printer.kv("   Time allocation:     Processing mixins", "%9d ms %10s%% of total)", new Object[] { Long.valueOf(actualTime), onedp.format(timeSliceMixin) });
/* 355 */     printer.kv("Loading classes", "%9d ms %10s%% of total)", new Object[] { Long.valueOf(loadTime), onedp.format(timeSliceLoad) });
/* 356 */     printer.kv("Running transformers", "%9d ms %10s%% of total)", new Object[] { Long.valueOf(transformTime), onedp.format(timeSliceTransform) });
/* 357 */     if (exportTime > 0L) {
/* 358 */       printer.kv("Exporting classes (debug)", "%9d ms %10s%% of total)", new Object[] { Long.valueOf(exportTime), onedp.format(timeSliceExport) });
/*     */     }
/* 360 */     printer.add();
/*     */     
/*     */     try {
/* 363 */       Class<?> agent = this.service.getClassProvider().findAgentClass("org.spongepowered.metronome.Agent", false);
/* 364 */       Method mdGetTimes = agent.getDeclaredMethod("getTimes", new Class[0]);
/*     */ 
/*     */       
/* 367 */       Map<String, Long> times = (Map<String, Long>)mdGetTimes.invoke(null, new Object[0]);
/*     */       
/* 369 */       printer.hr().add("Transformer Times").hr().add();
/*     */       
/* 371 */       int longest = 10;
/* 372 */       for (Map.Entry<String, Long> entry : times.entrySet()) {
/* 373 */         longest = Math.max(longest, ((String)entry.getKey()).length());
/*     */       }
/*     */       
/* 376 */       for (Map.Entry<String, Long> entry : times.entrySet()) {
/* 377 */         String name = entry.getKey();
/* 378 */         long mixinTime = 0L;
/* 379 */         for (Profiler.Section section : this.profiler.getSections()) {
/* 380 */           if (name.equals(section.getInfo())) {
/* 381 */             mixinTime = section.getTotalTime();
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 386 */         if (mixinTime > 0L) {
/* 387 */           printer.add("%-" + longest + "s %8s ms %8s ms in mixin)", new Object[] { name, Long.valueOf(((Long)entry.getValue()).longValue() + mixinTime), "(" + mixinTime }); continue;
/*     */         } 
/* 389 */         printer.add("%-" + longest + "s %8s ms", new Object[] { name, entry.getValue() });
/*     */       } 
/*     */ 
/*     */       
/* 393 */       printer.add();
/*     */     }
/* 395 */     catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 400 */     printer.print();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 408 */     return getClass().getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDelegationExcluded() {
/* 417 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized byte[] transformClassBytes(String name, String transformedName, byte[] basicClass) {
/* 426 */     if (transformedName == null || this.errorState) {
/* 427 */       return basicClass;
/*     */     }
/*     */     
/* 430 */     MixinEnvironment environment = MixinEnvironment.getCurrentEnvironment();
/*     */     
/* 432 */     if (basicClass == null) {
/* 433 */       for (IClassGenerator generator : this.extensions.getGenerators()) {
/* 434 */         Profiler.Section genTimer = this.profiler.begin(new String[] { "generator", generator.getClass().getSimpleName().toLowerCase() });
/* 435 */         basicClass = generator.generate(transformedName);
/* 436 */         genTimer.end();
/* 437 */         if (basicClass != null) {
/* 438 */           this.extensions.export(environment, transformedName.replace('.', '/'), false, basicClass);
/* 439 */           return basicClass;
/*     */         } 
/*     */       } 
/* 442 */       return basicClass;
/*     */     } 
/*     */     
/* 445 */     boolean locked = this.lock.push().check();
/*     */     
/* 447 */     Profiler.Section mixinTimer = this.profiler.begin("mixin");
/*     */     
/* 449 */     if (!locked) {
/*     */       try {
/* 451 */         checkSelect(environment);
/* 452 */       } catch (Exception ex) {
/* 453 */         this.lock.pop();
/* 454 */         mixinTimer.end();
/* 455 */         throw new MixinException(ex);
/*     */       } 
/*     */     }
/*     */     
/*     */     try {
/* 460 */       if (this.postProcessor.canTransform(transformedName)) {
/* 461 */         Profiler.Section postTimer = this.profiler.begin("postprocessor");
/* 462 */         byte[] bytes = this.postProcessor.transformClassBytes(name, transformedName, basicClass);
/* 463 */         postTimer.end();
/* 464 */         this.extensions.export(environment, transformedName, false, bytes);
/* 465 */         return bytes;
/*     */       } 
/*     */       
/* 468 */       SortedSet<MixinInfo> mixins = null;
/* 469 */       boolean invalidRef = false;
/*     */       
/* 471 */       for (MixinConfig config : this.configs) {
/* 472 */         if (config.packageMatch(transformedName)) {
/* 473 */           invalidRef = true;
/*     */           
/*     */           continue;
/*     */         } 
/* 477 */         if (config.hasMixinsFor(transformedName)) {
/* 478 */           if (mixins == null) {
/* 479 */             mixins = new TreeSet<MixinInfo>();
/*     */           }
/*     */ 
/*     */           
/* 483 */           mixins.addAll(config.getMixinsFor(transformedName));
/*     */         } 
/*     */       } 
/*     */       
/* 487 */       if (invalidRef) {
/* 488 */         throw new NoClassDefFoundError(String.format("%s is a mixin class and cannot be referenced directly", new Object[] { transformedName }));
/*     */       }
/*     */       
/* 491 */       if (mixins != null) {
/*     */         
/* 493 */         if (locked) {
/* 494 */           logger.warn("Re-entrance detected, this will cause serious problems.", (Throwable)new MixinException());
/* 495 */           throw new MixinApplyError("Re-entrance error.");
/*     */         } 
/*     */         
/* 498 */         if (this.hotSwapper != null) {
/* 499 */           this.hotSwapper.registerTargetClass(transformedName, basicClass);
/*     */         }
/*     */ 
/*     */         
/*     */         try {
/* 504 */           Profiler.Section timer = this.profiler.begin("read");
/* 505 */           ClassNode targetClassNode = readClass(basicClass, true);
/* 506 */           TargetClassContext context = new TargetClassContext(environment, this.extensions, this.sessionId, transformedName, targetClassNode, mixins);
/*     */           
/* 508 */           timer.end();
/* 509 */           basicClass = applyMixins(environment, context);
/* 510 */           this.transformedCount++;
/* 511 */         } catch (InvalidMixinException th) {
/* 512 */           dumpClassOnFailure(transformedName, basicClass, environment);
/* 513 */           handleMixinApplyError(transformedName, th, environment);
/*     */         } 
/*     */       } 
/*     */       
/* 517 */       return basicClass;
/* 518 */     } catch (Throwable th) {
/* 519 */       th.printStackTrace();
/* 520 */       dumpClassOnFailure(transformedName, basicClass, environment);
/* 521 */       throw new MixinTransformerError("An unexpected critical error was encountered", th);
/*     */     } finally {
/* 523 */       this.lock.pop();
/* 524 */       mixinTimer.end();
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
/*     */   public List<String> reload(String mixinClass, byte[] bytes) {
/* 536 */     if (this.lock.getDepth() > 0) {
/* 537 */       throw new MixinApplyError("Cannot reload mixin if re-entrant lock entered");
/*     */     }
/* 539 */     List<String> targets = new ArrayList<String>();
/* 540 */     for (MixinConfig config : this.configs) {
/* 541 */       targets.addAll(config.reloadMixin(mixinClass, bytes));
/*     */     }
/* 543 */     return targets;
/*     */   }
/*     */   
/*     */   private void checkSelect(MixinEnvironment environment) {
/* 547 */     if (this.currentEnvironment != environment) {
/* 548 */       select(environment);
/*     */       
/*     */       return;
/*     */     } 
/* 552 */     int unvisitedCount = Mixins.getUnvisitedCount();
/* 553 */     if (unvisitedCount > 0 && this.transformedCount == 0) {
/* 554 */       select(environment);
/*     */     }
/*     */   }
/*     */   
/*     */   private void select(MixinEnvironment environment) {
/* 559 */     this.verboseLoggingLevel = environment.getOption(MixinEnvironment.Option.DEBUG_VERBOSE) ? Level.INFO : Level.DEBUG;
/* 560 */     if (this.transformedCount > 0) {
/* 561 */       logger.log(this.verboseLoggingLevel, "Ending {}, applied {} mixins", new Object[] { this.currentEnvironment, Integer.valueOf(this.transformedCount) });
/*     */     }
/* 563 */     String action = (this.currentEnvironment == environment) ? "Checking for additional" : "Preparing";
/* 564 */     logger.log(this.verboseLoggingLevel, "{} mixins for {}", new Object[] { action, environment });
/*     */     
/* 566 */     this.profiler.setActive(true);
/* 567 */     this.profiler.mark(environment.getPhase().toString() + ":prepare");
/* 568 */     Profiler.Section prepareTimer = this.profiler.begin("prepare");
/*     */     
/* 570 */     selectConfigs(environment);
/* 571 */     this.extensions.select(environment);
/* 572 */     int totalMixins = prepareConfigs(environment);
/* 573 */     this.currentEnvironment = environment;
/* 574 */     this.transformedCount = 0;
/*     */     
/* 576 */     prepareTimer.end();
/*     */     
/* 578 */     long elapsedMs = prepareTimer.getTime();
/* 579 */     double elapsedTime = prepareTimer.getSeconds();
/* 580 */     if (elapsedTime > 0.25D) {
/* 581 */       long loadTime = this.profiler.get("class.load").getTime();
/* 582 */       long transformTime = this.profiler.get("class.transform").getTime();
/* 583 */       long pluginTime = this.profiler.get("mixin.plugin").getTime();
/* 584 */       String elapsed = (new DecimalFormat("###0.000")).format(elapsedTime);
/* 585 */       String perMixinTime = (new DecimalFormat("###0.0")).format(elapsedMs / totalMixins);
/*     */       
/* 587 */       logger.log(this.verboseLoggingLevel, "Prepared {} mixins in {} sec ({}ms avg) ({}ms load, {}ms transform, {}ms plugin)", new Object[] {
/* 588 */             Integer.valueOf(totalMixins), elapsed, perMixinTime, Long.valueOf(loadTime), Long.valueOf(transformTime), Long.valueOf(pluginTime)
/*     */           });
/*     */     } 
/* 591 */     this.profiler.mark(environment.getPhase().toString() + ":apply");
/* 592 */     this.profiler.setActive(environment.getOption(MixinEnvironment.Option.DEBUG_PROFILER));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void selectConfigs(MixinEnvironment environment) {
/* 601 */     for (Iterator<Config> iter = Mixins.getConfigs().iterator(); iter.hasNext(); ) {
/* 602 */       Config handle = iter.next();
/*     */       try {
/* 604 */         MixinConfig config = handle.get();
/* 605 */         if (config.select(environment)) {
/* 606 */           iter.remove();
/* 607 */           logger.log(this.verboseLoggingLevel, "Selecting config {}", new Object[] { config });
/* 608 */           config.onSelect();
/* 609 */           this.pendingConfigs.add(config);
/*     */         } 
/* 611 */       } catch (Exception ex) {
/* 612 */         logger.warn(String.format("Failed to select mixin config: %s", new Object[] { handle }), ex);
/*     */       } 
/*     */     } 
/*     */     
/* 616 */     Collections.sort(this.pendingConfigs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int prepareConfigs(MixinEnvironment environment) {
/* 626 */     int totalMixins = 0;
/*     */     
/* 628 */     final IHotSwap hotSwapper = this.hotSwapper;
/* 629 */     for (MixinConfig config : this.pendingConfigs) {
/* 630 */       config.addListener(this.postProcessor);
/* 631 */       if (hotSwapper != null) {
/* 632 */         config.addListener(new MixinConfig.IListener()
/*     */             {
/*     */               public void onPrepare(MixinInfo mixin) {
/* 635 */                 hotSwapper.registerMixinClass(mixin.getClassName());
/*     */               }
/*     */ 
/*     */ 
/*     */               
/*     */               public void onInit(MixinInfo mixin) {}
/*     */             });
/*     */       }
/*     */     } 
/* 644 */     for (MixinConfig config : this.pendingConfigs) {
/*     */       try {
/* 646 */         logger.log(this.verboseLoggingLevel, "Preparing {} ({})", new Object[] { config, Integer.valueOf(config.getDeclaredMixinCount()) });
/* 647 */         config.prepare();
/* 648 */         totalMixins += config.getMixinCount();
/* 649 */       } catch (InvalidMixinException ex) {
/* 650 */         handleMixinPrepareError(config, ex, environment);
/* 651 */       } catch (Exception ex) {
/* 652 */         String message = ex.getMessage();
/* 653 */         logger.error("Error encountered whilst initialising mixin config '" + config.getName() + "': " + message, ex);
/*     */       } 
/*     */     } 
/*     */     
/* 657 */     for (MixinConfig config : this.pendingConfigs) {
/* 658 */       IMixinConfigPlugin plugin = config.getPlugin();
/* 659 */       if (plugin == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 663 */       Set<String> otherTargets = new HashSet<String>();
/* 664 */       for (MixinConfig otherConfig : this.pendingConfigs) {
/* 665 */         if (!otherConfig.equals(config)) {
/* 666 */           otherTargets.addAll(otherConfig.getTargets());
/*     */         }
/*     */       } 
/*     */       
/* 670 */       plugin.acceptTargets(config.getTargets(), Collections.unmodifiableSet(otherTargets));
/*     */     } 
/*     */     
/* 673 */     for (MixinConfig config : this.pendingConfigs) {
/*     */       try {
/* 675 */         config.postInitialise();
/* 676 */       } catch (InvalidMixinException ex) {
/* 677 */         handleMixinPrepareError(config, ex, environment);
/* 678 */       } catch (Exception ex) {
/* 679 */         String message = ex.getMessage();
/* 680 */         logger.error("Error encountered during mixin config postInit step'" + config.getName() + "': " + message, ex);
/*     */       } 
/*     */     } 
/*     */     
/* 684 */     this.configs.addAll(this.pendingConfigs);
/* 685 */     Collections.sort(this.configs);
/* 686 */     this.pendingConfigs.clear();
/*     */     
/* 688 */     return totalMixins;
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
/*     */   private byte[] applyMixins(MixinEnvironment environment, TargetClassContext context) {
/* 700 */     Profiler.Section timer = this.profiler.begin("preapply");
/* 701 */     this.extensions.preApply(context);
/* 702 */     timer = timer.next("apply");
/* 703 */     apply(context);
/* 704 */     timer = timer.next("postapply");
/*     */     try {
/* 706 */       this.extensions.postApply(context);
/* 707 */     } catch (org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckClass.ValidationFailedException ex) {
/* 708 */       logger.info(ex.getMessage());
/*     */       
/* 710 */       if (context.isExportForced() || environment.getOption(MixinEnvironment.Option.DEBUG_EXPORT)) {
/* 711 */         writeClass(context);
/*     */       }
/*     */     } 
/* 714 */     timer.end();
/* 715 */     return writeClass(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void apply(TargetClassContext context) {
/* 724 */     context.applyMixins();
/*     */   }
/*     */   
/*     */   private void handleMixinPrepareError(MixinConfig config, InvalidMixinException ex, MixinEnvironment environment) throws MixinPrepareError {
/* 728 */     handleMixinError(config.getName(), ex, environment, ErrorPhase.PREPARE);
/*     */   }
/*     */   
/*     */   private void handleMixinApplyError(String targetClass, InvalidMixinException ex, MixinEnvironment environment) throws MixinApplyError {
/* 732 */     handleMixinError(targetClass, ex, environment, ErrorPhase.APPLY);
/*     */   }
/*     */   
/*     */   private void handleMixinError(String context, InvalidMixinException ex, MixinEnvironment environment, ErrorPhase errorPhase) throws Error {
/* 736 */     this.errorState = true;
/*     */     
/* 738 */     IMixinInfo mixin = ex.getMixin();
/*     */     
/* 740 */     if (mixin == null) {
/* 741 */       logger.error("InvalidMixinException has no mixin!", (Throwable)ex);
/* 742 */       throw ex;
/*     */     } 
/*     */     
/* 745 */     IMixinConfig config = mixin.getConfig();
/* 746 */     MixinEnvironment.Phase phase = mixin.getPhase();
/* 747 */     IMixinErrorHandler.ErrorAction action = config.isRequired() ? IMixinErrorHandler.ErrorAction.ERROR : IMixinErrorHandler.ErrorAction.WARN;
/*     */     
/* 749 */     if (environment.getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
/* 750 */       (new PrettyPrinter())
/* 751 */         .add("Invalid Mixin").centre()
/* 752 */         .hr('-')
/* 753 */         .kvWidth(10)
/* 754 */         .kv("Action", errorPhase.name())
/* 755 */         .kv("Mixin", mixin.getClassName())
/* 756 */         .kv("Config", config.getName())
/* 757 */         .kv("Phase", phase)
/* 758 */         .hr('-')
/* 759 */         .add("    %s", new Object[] { ex.getClass().getName()
/* 760 */           }).hr('-')
/* 761 */         .addWrapped("    %s", new Object[] { ex.getMessage()
/* 762 */           }).hr('-')
/* 763 */         .add((Throwable)ex, 8)
/* 764 */         .trace(action.logLevel);
/*     */     }
/*     */     
/* 767 */     for (IMixinErrorHandler handler : getErrorHandlers(mixin.getPhase())) {
/* 768 */       IMixinErrorHandler.ErrorAction newAction = errorPhase.onError(handler, context, ex, mixin, action);
/* 769 */       if (newAction != null) {
/* 770 */         action = newAction;
/*     */       }
/*     */     } 
/*     */     
/* 774 */     logger.log(action.logLevel, errorPhase.getLogMessage(context, ex, mixin), (Throwable)ex);
/*     */     
/* 776 */     this.errorState = false;
/*     */     
/* 778 */     if (action == IMixinErrorHandler.ErrorAction.ERROR) {
/* 779 */       throw new MixinApplyError(errorPhase.getErrorMessage(mixin, config, phase), ex);
/*     */     }
/*     */   }
/*     */   
/*     */   private List<IMixinErrorHandler> getErrorHandlers(MixinEnvironment.Phase phase) {
/* 784 */     List<IMixinErrorHandler> handlers = new ArrayList<IMixinErrorHandler>();
/*     */     
/* 786 */     for (String handlerClassName : Mixins.getErrorHandlerClasses()) {
/*     */       try {
/* 788 */         logger.info("Instancing error handler class {}", new Object[] { handlerClassName });
/* 789 */         Class<?> handlerClass = this.service.getClassProvider().findClass(handlerClassName, true);
/* 790 */         IMixinErrorHandler handler = (IMixinErrorHandler)handlerClass.newInstance();
/* 791 */         if (handler != null) {
/* 792 */           handlers.add(handler);
/*     */         }
/* 794 */       } catch (Throwable throwable) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 799 */     return handlers;
/*     */   }
/*     */   
/*     */   private byte[] writeClass(TargetClassContext context) {
/* 803 */     return writeClass(context.getClassName(), context.getClassNode(), context.isExportForced());
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] writeClass(String transformedName, ClassNode targetClass, boolean forceExport) {
/* 808 */     Profiler.Section writeTimer = this.profiler.begin("write");
/* 809 */     byte[] bytes = writeClass(targetClass);
/* 810 */     writeTimer.end();
/* 811 */     this.extensions.export(this.currentEnvironment, transformedName, forceExport, bytes);
/* 812 */     return bytes;
/*     */   }
/*     */   
/*     */   private void dumpClassOnFailure(String className, byte[] bytes, MixinEnvironment env) {
/* 816 */     if (env.getOption(MixinEnvironment.Option.DUMP_TARGET_ON_FAILURE)) {
/* 817 */       ExtensionClassExporter exporter = (ExtensionClassExporter)this.extensions.getExtension(ExtensionClassExporter.class);
/* 818 */       exporter.dumpClass(className.replace('.', '/') + ".target", bytes);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\MixinTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */