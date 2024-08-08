package org.spongepowered.asm.mixin.transformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.Map.Entry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
import org.spongepowered.asm.mixin.throwables.ClassAlreadyLoadedException;
import org.spongepowered.asm.mixin.throwables.MixinApplyError;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.throwables.MixinPrepareError;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.IHotSwap;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckClass;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckInterfaces;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionClassExporter;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.ITransformer;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.transformers.TreeTransformer;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.ReEntranceLock;
import org.spongepowered.asm.util.perf.Profiler;

public class MixinTransformer extends TreeTransformer {
   private static final String MIXIN_AGENT_CLASS = "org.spongepowered.tools.agent.MixinAgent";
   private static final String METRONOME_AGENT_CLASS = "org.spongepowered.metronome.Agent";
   static final Logger logger = LogManager.getLogger("mixin");
   private final IMixinService service = MixinService.getService();
   private final List configs = new ArrayList();
   private final List pendingConfigs = new ArrayList();
   private final ReEntranceLock lock;
   private final String sessionId = UUID.randomUUID().toString();
   private final Extensions extensions;
   private final IHotSwap hotSwapper;
   private final MixinPostProcessor postProcessor;
   private final Profiler profiler;
   private MixinEnvironment currentEnvironment;
   private Level verboseLoggingLevel;
   private boolean errorState;
   private int transformedCount;

   MixinTransformer() {
      this.verboseLoggingLevel = Level.DEBUG;
      this.errorState = false;
      this.transformedCount = 0;
      MixinEnvironment var1 = MixinEnvironment.getCurrentEnvironment();
      Object var2 = var1.getActiveTransformer();
      if (var2 instanceof ITransformer) {
         throw new MixinException("Terminating MixinTransformer instance " + this);
      } else {
         var1.setActiveTransformer(this);
         this.lock = this.service.getReEntranceLock();
         this.extensions = new Extensions(this);
         this.hotSwapper = this.initHotSwapper(var1);
         this.postProcessor = new MixinPostProcessor();
         this.extensions.add((IClassGenerator)(new ArgsClassGenerator()));
         this.extensions.add((IClassGenerator)(new InnerClassGenerator()));
         this.extensions.add((IExtension)(new ExtensionClassExporter(var1)));
         this.extensions.add((IExtension)(new ExtensionCheckClass()));
         this.extensions.add((IExtension)(new ExtensionCheckInterfaces()));
         this.profiler = MixinEnvironment.getProfiler();
      }
   }

   private IHotSwap initHotSwapper(MixinEnvironment var1) {
      if (!var1.getOption(MixinEnvironment.Option.HOT_SWAP)) {
         return null;
      } else {
         try {
            logger.info("Attempting to load Hot-Swap agent");
            Class var2 = Class.forName("org.spongepowered.tools.agent.MixinAgent");
            Constructor var3 = var2.getDeclaredConstructor(MixinTransformer.class);
            return (IHotSwap)var3.newInstance(this);
         } catch (Throwable var4) {
            logger.info("Hot-swap agent could not be loaded, hot swapping of mixins won't work. {}: {}", new Object[]{var4.getClass().getSimpleName(), var4.getMessage()});
            return null;
         }
      }
   }

   public void audit(MixinEnvironment var1) {
      HashSet var2 = new HashSet();
      Iterator var3 = this.configs.iterator();

      while(var3.hasNext()) {
         MixinConfig var4 = (MixinConfig)var3.next();
         var2.addAll(var4.getUnhandledTargets());
      }

      Logger var10 = LogManager.getLogger("mixin/audit");
      Iterator var11 = var2.iterator();

      while(var11.hasNext()) {
         String var5 = (String)var11.next();

         try {
            var10.info("Force-loading class {}", new Object[]{var5});
            this.service.getClassProvider().findClass(var5, true);
         } catch (ClassNotFoundException var9) {
            var10.error("Could not force-load " + var5, var9);
         }
      }

      var11 = this.configs.iterator();

      while(var11.hasNext()) {
         MixinConfig var12 = (MixinConfig)var11.next();
         Iterator var6 = var12.getUnhandledTargets().iterator();

         while(var6.hasNext()) {
            String var7 = (String)var6.next();
            ClassAlreadyLoadedException var8 = new ClassAlreadyLoadedException(var7 + " was already classloaded");
            var10.error("Could not force-load " + var7, var8);
         }
      }

      if (var1.getOption(MixinEnvironment.Option.DEBUG_PROFILER)) {
         this.printProfilerSummary();
      }

   }

   private void printProfilerSummary() {
      DecimalFormat var1 = new DecimalFormat("(###0.000");
      DecimalFormat var2 = new DecimalFormat("(###0.0");
      PrettyPrinter var3 = this.profiler.printer(false, false);
      long var4 = this.profiler.get("mixin.prepare").getTotalTime();
      long var6 = this.profiler.get("mixin.read").getTotalTime();
      long var8 = this.profiler.get("mixin.apply").getTotalTime();
      long var10 = this.profiler.get("mixin.write").getTotalTime();
      long var12 = this.profiler.get("mixin").getTotalTime();
      long var14 = this.profiler.get("class.load").getTotalTime();
      long var16 = this.profiler.get("class.transform").getTotalTime();
      long var18 = this.profiler.get("mixin.debug.export").getTotalTime();
      long var20 = var12 - var14 - var16 - var18;
      double var22 = (double)var20 / (double)var12 * 100.0D;
      double var24 = (double)var14 / (double)var12 * 100.0D;
      double var26 = (double)var16 / (double)var12 * 100.0D;
      double var28 = (double)var18 / (double)var12 * 100.0D;
      long var30 = 0L;
      Profiler.Section var32 = null;
      Iterator var33 = this.profiler.getSections().iterator();

      while(var33.hasNext()) {
         Profiler.Section var34 = (Profiler.Section)var33.next();
         long var35 = var34.getName().startsWith("class.transform.") ? var34.getTotalTime() : 0L;
         if (var35 > var30) {
            var30 = var35;
            var32 = var34;
         }
      }

      var3.hr().add("Summary").hr().add();
      String var46 = "%9d ms %12s seconds)";
      var3.kv("Total mixin time", var46, var12, var1.format((double)var12 * 0.001D)).add();
      var3.kv("Preparing mixins", var46, var4, var1.format((double)var4 * 0.001D));
      var3.kv("Reading input", var46, var6, var1.format((double)var6 * 0.001D));
      var3.kv("Applying mixins", var46, var8, var1.format((double)var8 * 0.001D));
      var3.kv("Writing output", var46, var10, var1.format((double)var10 * 0.001D)).add();
      var3.kv("of which", "");
      var3.kv("Time spent loading from disk", var46, var14, var1.format((double)var14 * 0.001D));
      var3.kv("Time spent transforming classes", var46, var16, var1.format((double)var16 * 0.001D)).add();
      if (var32 != null) {
         var3.kv("Worst transformer", var32.getName());
         var3.kv("Class", var32.getInfo());
         var3.kv("Time spent", "%s seconds", var32.getTotalSeconds());
         var3.kv("called", "%d times", var32.getTotalCount()).add();
      }

      var3.kv("   Time allocation:     Processing mixins", "%9d ms %10s%% of total)", var20, var2.format(var22));
      var3.kv("Loading classes", "%9d ms %10s%% of total)", var14, var2.format(var24));
      var3.kv("Running transformers", "%9d ms %10s%% of total)", var16, var2.format(var26));
      if (var18 > 0L) {
         var3.kv("Exporting classes (debug)", "%9d ms %10s%% of total)", var18, var2.format(var28));
      }

      var3.add();

      try {
         Class var47 = this.service.getClassProvider().findAgentClass("org.spongepowered.metronome.Agent", false);
         Method var48 = var47.getDeclaredMethod("getTimes");
         Map var36 = (Map)var48.invoke((Object)null);
         var3.hr().add("Transformer Times").hr().add();
         int var37 = 10;

         Iterator var38;
         Entry var39;
         for(var38 = var36.entrySet().iterator(); var38.hasNext(); var37 = Math.max(var37, ((String)var39.getKey()).length())) {
            var39 = (Entry)var38.next();
         }

         var38 = var36.entrySet().iterator();

         while(var38.hasNext()) {
            var39 = (Entry)var38.next();
            String var40 = (String)var39.getKey();
            long var41 = 0L;
            Iterator var43 = this.profiler.getSections().iterator();

            while(var43.hasNext()) {
               Profiler.Section var44 = (Profiler.Section)var43.next();
               if (var40.equals(var44.getInfo())) {
                  var41 = var44.getTotalTime();
                  break;
               }
            }

            if (var41 > 0L) {
               var3.add("%-" + var37 + "s %8s ms %8s ms in mixin)", var40, (Long)var39.getValue() + var41, "(" + var41);
            } else {
               var3.add("%-" + var37 + "s %8s ms", var40, var39.getValue());
            }
         }

         var3.add();
      } catch (Throwable var45) {
      }

      var3.print();
   }

   public String getName() {
      return this.getClass().getName();
   }

   public boolean isDelegationExcluded() {
      return true;
   }

   public synchronized byte[] transformClassBytes(String var1, String var2, byte[] var3) {
      if (var2 != null && !this.errorState) {
         MixinEnvironment var4 = MixinEnvironment.getCurrentEnvironment();
         Profiler.Section var7;
         if (var3 == null) {
            Iterator var21 = this.extensions.getGenerators().iterator();

            do {
               if (!var21.hasNext()) {
                  return var3;
               }

               IClassGenerator var22 = (IClassGenerator)var21.next();
               var7 = this.profiler.begin("generator", var22.getClass().getSimpleName().toLowerCase());
               var3 = var22.generate(var2);
               var7.end();
            } while(var3 == null);

            this.extensions.export(var4, var2.replace('.', '/'), false, var3);
            return var3;
         } else {
            boolean var5 = this.lock.push().check();
            Profiler.Section var6 = this.profiler.begin("mixin");
            if (!var5) {
               try {
                  this.checkSelect(var4);
               } catch (Exception var18) {
                  this.lock.pop();
                  var6.end();
                  throw new MixinException(var18);
               }
            }

            byte[] var9;
            try {
               if (!this.postProcessor.canTransform(var2)) {
                  TreeSet var23 = null;
                  boolean var24 = false;
                  Iterator var25 = this.configs.iterator();

                  while(var25.hasNext()) {
                     MixinConfig var10 = (MixinConfig)var25.next();
                     if (var10.packageMatch(var2)) {
                        var24 = true;
                     } else if (var10.hasMixinsFor(var2)) {
                        if (var23 == null) {
                           var23 = new TreeSet();
                        }

                        var23.addAll(var10.getMixinsFor(var2));
                     }
                  }

                  if (var24) {
                     throw new NoClassDefFoundError(String.format("%s is a mixin class and cannot be referenced directly", var2));
                  }

                  if (var23 != null) {
                     if (var5) {
                        logger.warn("Re-entrance detected, this will cause serious problems.", new MixinException());
                        throw new MixinApplyError("Re-entrance error.");
                     }

                     if (this.hotSwapper != null) {
                        this.hotSwapper.registerTargetClass(var2, var3);
                     }

                     try {
                        Profiler.Section var26 = this.profiler.begin("read");
                        ClassNode var27 = this.readClass(var3, true);
                        TargetClassContext var11 = new TargetClassContext(var4, this.extensions, this.sessionId, var2, var27, var23);
                        var26.end();
                        var3 = this.applyMixins(var4, var11);
                        ++this.transformedCount;
                     } catch (InvalidMixinException var17) {
                        this.dumpClassOnFailure(var2, var3, var4);
                        this.handleMixinApplyError(var2, var17, var4);
                     }
                  }

                  var9 = var3;
                  return var9;
               }

               var7 = this.profiler.begin("postprocessor");
               byte[] var8 = this.postProcessor.transformClassBytes(var1, var2, var3);
               var7.end();
               this.extensions.export(var4, var2, false, var8);
               var9 = var8;
            } catch (Throwable var19) {
               var19.printStackTrace();
               this.dumpClassOnFailure(var2, var3, var4);
               throw new MixinTransformerError("An unexpected critical error was encountered", var19);
            } finally {
               this.lock.pop();
               var6.end();
            }

            return var9;
         }
      } else {
         return var3;
      }
   }

   public List reload(String var1, byte[] var2) {
      if (this.lock.getDepth() > 0) {
         throw new MixinApplyError("Cannot reload mixin if re-entrant lock entered");
      } else {
         ArrayList var3 = new ArrayList();
         Iterator var4 = this.configs.iterator();

         while(var4.hasNext()) {
            MixinConfig var5 = (MixinConfig)var4.next();
            var3.addAll(var5.reloadMixin(var1, var2));
         }

         return var3;
      }
   }

   private void checkSelect(MixinEnvironment var1) {
      if (this.currentEnvironment != var1) {
         this.select(var1);
      } else {
         int var2 = Mixins.getUnvisitedCount();
         if (var2 > 0 && this.transformedCount == 0) {
            this.select(var1);
         }

      }
   }

   private void select(MixinEnvironment var1) {
      this.verboseLoggingLevel = var1.getOption(MixinEnvironment.Option.DEBUG_VERBOSE) ? Level.INFO : Level.DEBUG;
      if (this.transformedCount > 0) {
         logger.log(this.verboseLoggingLevel, "Ending {}, applied {} mixins", new Object[]{this.currentEnvironment, this.transformedCount});
      }

      String var2 = this.currentEnvironment == var1 ? "Checking for additional" : "Preparing";
      logger.log(this.verboseLoggingLevel, "{} mixins for {}", new Object[]{var2, var1});
      this.profiler.setActive(true);
      this.profiler.mark(var1.getPhase().toString() + ":prepare");
      Profiler.Section var3 = this.profiler.begin("prepare");
      this.selectConfigs(var1);
      this.extensions.select(var1);
      int var4 = this.prepareConfigs(var1);
      this.currentEnvironment = var1;
      this.transformedCount = 0;
      var3.end();
      long var5 = var3.getTime();
      double var7 = var3.getSeconds();
      if (var7 > 0.25D) {
         long var9 = this.profiler.get("class.load").getTime();
         long var11 = this.profiler.get("class.transform").getTime();
         long var13 = this.profiler.get("mixin.plugin").getTime();
         String var15 = (new DecimalFormat("###0.000")).format(var7);
         String var16 = (new DecimalFormat("###0.0")).format((double)var5 / (double)var4);
         logger.log(this.verboseLoggingLevel, "Prepared {} mixins in {} sec ({}ms avg) ({}ms load, {}ms transform, {}ms plugin)", new Object[]{var4, var15, var16, var9, var11, var13});
      }

      this.profiler.mark(var1.getPhase().toString() + ":apply");
      this.profiler.setActive(var1.getOption(MixinEnvironment.Option.DEBUG_PROFILER));
   }

   private void selectConfigs(MixinEnvironment var1) {
      Iterator var2 = Mixins.getConfigs().iterator();

      while(var2.hasNext()) {
         Config var3 = (Config)var2.next();

         try {
            MixinConfig var4 = var3.get();
            if (var4.select(var1)) {
               var2.remove();
               logger.log(this.verboseLoggingLevel, "Selecting config {}", new Object[]{var4});
               var4.onSelect();
               this.pendingConfigs.add(var4);
            }
         } catch (Exception var5) {
            logger.warn(String.format("Failed to select mixin config: %s", var3), var5);
         }
      }

      Collections.sort(this.pendingConfigs);
   }

   private int prepareConfigs(MixinEnvironment var1) {
      int var2 = 0;
      IHotSwap var3 = this.hotSwapper;
      Iterator var4 = this.pendingConfigs.iterator();

      MixinConfig var5;
      while(var4.hasNext()) {
         var5 = (MixinConfig)var4.next();
         var5.addListener(this.postProcessor);
         if (var3 != null) {
            var5.addListener(new MixinConfig.IListener(this, var3) {
               final IHotSwap val$hotSwapper;
               final MixinTransformer this$0;

               {
                  this.this$0 = var1;
                  this.val$hotSwapper = var2;
               }

               public void onPrepare(MixinInfo var1) {
                  this.val$hotSwapper.registerMixinClass(var1.getClassName());
               }

               public void onInit(MixinInfo var1) {
               }
            });
         }
      }

      var4 = this.pendingConfigs.iterator();

      String var7;
      while(var4.hasNext()) {
         var5 = (MixinConfig)var4.next();

         try {
            logger.log(this.verboseLoggingLevel, "Preparing {} ({})", new Object[]{var5, var5.getDeclaredMixinCount()});
            var5.prepare();
            var2 += var5.getMixinCount();
         } catch (InvalidMixinException var12) {
            this.handleMixinPrepareError(var5, var12, var1);
         } catch (Exception var13) {
            var7 = var13.getMessage();
            logger.error("Error encountered whilst initialising mixin config '" + var5.getName() + "': " + var7, var13);
         }
      }

      var4 = this.pendingConfigs.iterator();

      while(true) {
         IMixinConfigPlugin var6;
         do {
            if (!var4.hasNext()) {
               var4 = this.pendingConfigs.iterator();

               while(var4.hasNext()) {
                  var5 = (MixinConfig)var4.next();

                  try {
                     var5.postInitialise();
                  } catch (InvalidMixinException var10) {
                     this.handleMixinPrepareError(var5, var10, var1);
                  } catch (Exception var11) {
                     var7 = var11.getMessage();
                     logger.error("Error encountered during mixin config postInit step'" + var5.getName() + "': " + var7, var11);
                  }
               }

               this.configs.addAll(this.pendingConfigs);
               Collections.sort(this.configs);
               this.pendingConfigs.clear();
               return var2;
            }

            var5 = (MixinConfig)var4.next();
            var6 = var5.getPlugin();
         } while(var6 == null);

         HashSet var14 = new HashSet();
         Iterator var8 = this.pendingConfigs.iterator();

         while(var8.hasNext()) {
            MixinConfig var9 = (MixinConfig)var8.next();
            if (!var9.equals(var5)) {
               var14.addAll(var9.getTargets());
            }
         }

         var6.acceptTargets(var5.getTargets(), Collections.unmodifiableSet(var14));
      }
   }

   private byte[] applyMixins(MixinEnvironment var1, TargetClassContext var2) {
      Profiler.Section var3 = this.profiler.begin("preapply");
      this.extensions.preApply(var2);
      var3 = var3.next("apply");
      this.apply(var2);
      var3 = var3.next("postapply");

      try {
         this.extensions.postApply(var2);
      } catch (ExtensionCheckClass.ValidationFailedException var5) {
         logger.info(var5.getMessage());
         if (var2.isExportForced() || var1.getOption(MixinEnvironment.Option.DEBUG_EXPORT)) {
            this.writeClass(var2);
         }
      }

      var3.end();
      return this.writeClass(var2);
   }

   private void apply(TargetClassContext var1) {
      var1.applyMixins();
   }

   private void handleMixinPrepareError(MixinConfig var1, InvalidMixinException var2, MixinEnvironment var3) throws MixinPrepareError {
      this.handleMixinError(var1.getName(), var2, var3, MixinTransformer.ErrorPhase.PREPARE);
   }

   private void handleMixinApplyError(String var1, InvalidMixinException var2, MixinEnvironment var3) throws MixinApplyError {
      this.handleMixinError(var1, var2, var3, MixinTransformer.ErrorPhase.APPLY);
   }

   private void handleMixinError(String var1, InvalidMixinException var2, MixinEnvironment var3, MixinTransformer.ErrorPhase var4) throws Error {
      this.errorState = true;
      IMixinInfo var5 = var2.getMixin();
      if (var5 == null) {
         logger.error("InvalidMixinException has no mixin!", var2);
         throw var2;
      } else {
         IMixinConfig var6 = var5.getConfig();
         MixinEnvironment.Phase var7 = var5.getPhase();
         IMixinErrorHandler.ErrorAction var8 = var6.isRequired() ? IMixinErrorHandler.ErrorAction.ERROR : IMixinErrorHandler.ErrorAction.WARN;
         if (var3.getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
            (new PrettyPrinter()).add("Invalid Mixin").centre().hr('-').kvWidth(10).kv("Action", var4.name()).kv("Mixin", var5.getClassName()).kv("Config", var6.getName()).kv("Phase", var7).hr('-').add("    %s", var2.getClass().getName()).hr('-').addWrapped("    %s", var2.getMessage()).hr('-').add((Throwable)var2, 8).trace(var8.logLevel);
         }

         Iterator var9 = this.getErrorHandlers(var5.getPhase()).iterator();

         while(var9.hasNext()) {
            IMixinErrorHandler var10 = (IMixinErrorHandler)var9.next();
            IMixinErrorHandler.ErrorAction var11 = var4.onError(var10, var1, var2, var5, var8);
            if (var11 != null) {
               var8 = var11;
            }
         }

         logger.log(var8.logLevel, var4.getLogMessage(var1, var2, var5), var2);
         this.errorState = false;
         if (var8 == IMixinErrorHandler.ErrorAction.ERROR) {
            throw new MixinApplyError(var4.getErrorMessage(var5, var6, var7), var2);
         }
      }
   }

   private List getErrorHandlers(MixinEnvironment.Phase var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = Mixins.getErrorHandlerClasses().iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();

         try {
            logger.info("Instancing error handler class {}", new Object[]{var4});
            Class var5 = this.service.getClassProvider().findClass(var4, true);
            IMixinErrorHandler var6 = (IMixinErrorHandler)var5.newInstance();
            if (var6 != null) {
               var2.add(var6);
            }
         } catch (Throwable var7) {
         }
      }

      return var2;
   }

   private byte[] writeClass(TargetClassContext var1) {
      return this.writeClass(var1.getClassName(), var1.getClassNode(), var1.isExportForced());
   }

   private byte[] writeClass(String var1, ClassNode var2, boolean var3) {
      Profiler.Section var4 = this.profiler.begin("write");
      byte[] var5 = this.writeClass(var2);
      var4.end();
      this.extensions.export(this.currentEnvironment, var1, var3, var5);
      return var5;
   }

   private void dumpClassOnFailure(String var1, byte[] var2, MixinEnvironment var3) {
      if (var3.getOption(MixinEnvironment.Option.DUMP_TARGET_ON_FAILURE)) {
         ExtensionClassExporter var4 = (ExtensionClassExporter)this.extensions.getExtension(ExtensionClassExporter.class);
         var4.dumpClass(var1.replace('.', '/') + ".target", var2);
      }

   }

   static enum ErrorPhase {
      PREPARE {
         IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler var1, String var2, InvalidMixinException var3, IMixinInfo var4, IMixinErrorHandler.ErrorAction var5) {
            try {
               return var1.onPrepareError(var4.getConfig(), var3, var4, var5);
            } catch (AbstractMethodError var7) {
               return var5;
            }
         }

         protected String getContext(IMixinInfo var1, String var2) {
            return String.format("preparing %s in %s", var1.getName(), var2);
         }
      },
      APPLY {
         IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler var1, String var2, InvalidMixinException var3, IMixinInfo var4, IMixinErrorHandler.ErrorAction var5) {
            try {
               return var1.onApplyError(var2, var3, var4, var5);
            } catch (AbstractMethodError var7) {
               return var5;
            }
         }

         protected String getContext(IMixinInfo var1, String var2) {
            return String.format("%s -> %s", var1, var2);
         }
      };

      private final String text;
      private static final MixinTransformer.ErrorPhase[] $VALUES = new MixinTransformer.ErrorPhase[]{PREPARE, APPLY};

      private ErrorPhase() {
         this.text = this.name().toLowerCase();
      }

      abstract IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler var1, String var2, InvalidMixinException var3, IMixinInfo var4, IMixinErrorHandler.ErrorAction var5);

      protected abstract String getContext(IMixinInfo var1, String var2);

      public String getLogMessage(String var1, InvalidMixinException var2, IMixinInfo var3) {
         return String.format("Mixin %s failed %s: %s %s", this.text, this.getContext(var3, var1), var2.getClass().getName(), var2.getMessage());
      }

      public String getErrorMessage(IMixinInfo var1, IMixinConfig var2, MixinEnvironment.Phase var3) {
         return String.format("Mixin [%s] from phase [%s] in config [%s] FAILED during %s", var1, var3, var2, this.name());
      }

      ErrorPhase(Object var3) {
         this();
      }
   }
}
