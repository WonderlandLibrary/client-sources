package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.transformers.*;
import org.spongepowered.asm.util.perf.*;
import org.spongepowered.asm.service.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.*;
import org.spongepowered.asm.mixin.transformer.ext.extensions.*;
import org.apache.logging.log4j.*;
import java.text.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.*;
import java.util.*;
import org.spongepowered.asm.mixin.transformer.ext.*;
import org.spongepowered.asm.mixin.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.lib.tree.*;

public class MixinTransformer extends TreeTransformer
{
    private static final String MIXIN_AGENT_CLASS = "org.spongepowered.tools.agent.MixinAgent";
    private static final String METRONOME_AGENT_CLASS = "org.spongepowered.metronome.Agent";
    static final Logger logger;
    private final IMixinService service;
    private final List<MixinConfig> configs;
    private final List<MixinConfig> pendingConfigs;
    private final ReEntranceLock lock;
    private final String sessionId;
    private final Extensions extensions;
    private final IHotSwap hotSwapper;
    private final MixinPostProcessor postProcessor;
    private final Profiler profiler;
    private MixinEnvironment currentEnvironment;
    private Level verboseLoggingLevel;
    private boolean errorState;
    private int transformedCount;
    
    MixinTransformer() {
        super();
        this.service = MixinService.getService();
        this.configs = new ArrayList<MixinConfig>();
        this.pendingConfigs = new ArrayList<MixinConfig>();
        this.sessionId = UUID.randomUUID().toString();
        this.verboseLoggingLevel = Level.DEBUG;
        this.errorState = false;
        this.transformedCount = 0;
        final MixinEnvironment currentEnvironment = MixinEnvironment.getCurrentEnvironment();
        if (currentEnvironment.getActiveTransformer() instanceof ITransformer) {
            throw new MixinException("Terminating MixinTransformer instance " + this);
        }
        currentEnvironment.setActiveTransformer(this);
        this.lock = this.service.getReEntranceLock();
        this.extensions = new Extensions(this);
        this.hotSwapper = this.initHotSwapper(currentEnvironment);
        this.postProcessor = new MixinPostProcessor();
        this.extensions.add(new ArgsClassGenerator());
        this.extensions.add(new InnerClassGenerator());
        this.extensions.add(new ExtensionClassExporter(currentEnvironment));
        this.extensions.add(new ExtensionCheckClass());
        this.extensions.add(new ExtensionCheckInterfaces());
        this.profiler = MixinEnvironment.getProfiler();
    }
    
    private IHotSwap initHotSwapper(final MixinEnvironment mixinEnvironment) {
        if (!mixinEnvironment.getOption(MixinEnvironment.Option.HOT_SWAP)) {
            return null;
        }
        try {
            MixinTransformer.logger.info("Attempting to load Hot-Swap agent");
            return (IHotSwap)Class.forName("org.spongepowered.tools.agent.MixinAgent").getDeclaredConstructor(MixinTransformer.class).newInstance(this);
        }
        catch (Throwable t) {
            MixinTransformer.logger.info("Hot-swap agent could not be loaded, hot swapping of mixins won't work. {}: {}", new Object[] { t.getClass().getSimpleName(), t.getMessage() });
            return null;
        }
    }
    
    public void audit(final MixinEnvironment mixinEnvironment) {
        final HashSet<String> set = new HashSet<String>();
        final Iterator<MixinConfig> iterator = this.configs.iterator();
        while (iterator.hasNext()) {
            set.addAll((Collection<?>)iterator.next().getUnhandledTargets());
        }
        final Logger logger = LogManager.getLogger("mixin/audit");
        for (final String s : set) {
            try {
                logger.info("Force-loading class {}", new Object[] { s });
                this.service.getClassProvider().findClass(s, true);
            }
            catch (ClassNotFoundException ex) {
                logger.error("Could not force-load " + s, (Throwable)ex);
            }
        }
        final Iterator<MixinConfig> iterator3 = this.configs.iterator();
        while (iterator3.hasNext()) {
            for (final String s2 : iterator3.next().getUnhandledTargets()) {
                logger.error("Could not force-load " + s2, (Throwable)new ClassAlreadyLoadedException(s2 + " was already classloaded"));
            }
        }
        if (mixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_PROFILER)) {
            this.printProfilerSummary();
        }
    }
    
    private void printProfilerSummary() {
        final DecimalFormat decimalFormat = new DecimalFormat("(###0.000");
        final DecimalFormat decimalFormat2 = new DecimalFormat("(###0.0");
        final PrettyPrinter printer = this.profiler.printer(false, false);
        final long totalTime = this.profiler.get("mixin.prepare").getTotalTime();
        final long totalTime2 = this.profiler.get("mixin.read").getTotalTime();
        final long totalTime3 = this.profiler.get("mixin.apply").getTotalTime();
        final long totalTime4 = this.profiler.get("mixin.write").getTotalTime();
        final long totalTime5 = this.profiler.get("mixin").getTotalTime();
        final long totalTime6 = this.profiler.get("class.load").getTotalTime();
        final long totalTime7 = this.profiler.get("class.transform").getTotalTime();
        final long totalTime8 = this.profiler.get("mixin.debug.export").getTotalTime();
        final long n = totalTime5 - totalTime6 - totalTime7 - totalTime8;
        final double n2 = n / (double)totalTime5 * 100.0;
        final double n3 = totalTime6 / (double)totalTime5 * 100.0;
        final double n4 = totalTime7 / (double)totalTime5 * 100.0;
        final double n5 = totalTime8 / (double)totalTime5 * 100.0;
        long n6 = 0L;
        Profiler.Section section = null;
        for (final Profiler.Section section2 : this.profiler.getSections()) {
            final long n7 = section2.getName().startsWith("class.transform.") ? section2.getTotalTime() : 0L;
            if (n7 > n6) {
                n6 = n7;
                section = section2;
            }
        }
        printer.hr().add("Summary").hr().add();
        final String s = "%9d ms %12s seconds)";
        printer.kv("Total mixin time", s, totalTime5, decimalFormat.format(totalTime5 * 0.001)).add();
        printer.kv("Preparing mixins", s, totalTime, decimalFormat.format(totalTime * 0.001));
        printer.kv("Reading input", s, totalTime2, decimalFormat.format(totalTime2 * 0.001));
        printer.kv("Applying mixins", s, totalTime3, decimalFormat.format(totalTime3 * 0.001));
        printer.kv("Writing output", s, totalTime4, decimalFormat.format(totalTime4 * 0.001)).add();
        printer.kv("of which", (Object)"");
        printer.kv("Time spent loading from disk", s, totalTime6, decimalFormat.format(totalTime6 * 0.001));
        printer.kv("Time spent transforming classes", s, totalTime7, decimalFormat.format(totalTime7 * 0.001)).add();
        if (section != null) {
            printer.kv("Worst transformer", (Object)section.getName());
            printer.kv("Class", (Object)section.getInfo());
            printer.kv("Time spent", "%s seconds", section.getTotalSeconds());
            printer.kv("called", "%d times", section.getTotalCount()).add();
        }
        printer.kv("   Time allocation:     Processing mixins", "%9d ms %10s%% of total)", n, decimalFormat2.format(n2));
        printer.kv("Loading classes", "%9d ms %10s%% of total)", totalTime6, decimalFormat2.format(n3));
        printer.kv("Running transformers", "%9d ms %10s%% of total)", totalTime7, decimalFormat2.format(n4));
        if (totalTime8 > 0L) {
            printer.kv("Exporting classes (debug)", "%9d ms %10s%% of total)", totalTime8, decimalFormat2.format(n5));
        }
        printer.add();
        try {
            final Map map = (Map)this.service.getClassProvider().findAgentClass("org.spongepowered.metronome.Agent", false).getDeclaredMethod("getTimes", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
            printer.hr().add("Transformer Times").hr().add();
            int max = 10;
            final Iterator<Map.Entry<String, V>> iterator2 = map.entrySet().iterator();
            while (iterator2.hasNext()) {
                max = Math.max(max, iterator2.next().getKey().length());
            }
            for (final Map.Entry<String, V> entry : map.entrySet()) {
                final String s2 = entry.getKey();
                long totalTime9 = 0L;
                for (final Profiler.Section section3 : this.profiler.getSections()) {
                    if (s2.equals(section3.getInfo())) {
                        totalTime9 = section3.getTotalTime();
                        break;
                    }
                }
                if (totalTime9 > 0L) {
                    printer.add("%-" + max + "s %8s ms %8s ms in mixin)", s2, (long)entry.getValue() + totalTime9, "(" + totalTime9);
                }
                else {
                    printer.add("%-" + max + "s %8s ms", s2, entry.getValue());
                }
            }
            printer.add();
        }
        catch (Throwable t) {}
        printer.print();
    }
    
    @Override
    public String getName() {
        return this.getClass().getName();
    }
    
    @Override
    public boolean isDelegationExcluded() {
        return true;
    }
    
    @Override
    public synchronized byte[] transformClassBytes(final String s, final String s2, byte[] array) {
        if (s2 == null || this.errorState) {
            return array;
        }
        final MixinEnvironment currentEnvironment = MixinEnvironment.getCurrentEnvironment();
        if (array == null) {
            for (final IClassGenerator classGenerator : this.extensions.getGenerators()) {
                final Profiler.Section begin = this.profiler.begin("generator", classGenerator.getClass().getSimpleName().toLowerCase());
                array = classGenerator.generate(s2);
                begin.end();
                if (array != null) {
                    this.extensions.export(currentEnvironment, s2.replace('.', '/'), false, array);
                    return array;
                }
            }
            return array;
        }
        final boolean check = this.lock.push().check();
        final Profiler.Section begin2 = this.profiler.begin("mixin");
        if (!check) {
            try {
                this.checkSelect(currentEnvironment);
            }
            catch (Exception ex) {
                this.lock.pop();
                begin2.end();
                throw new MixinException(ex);
            }
        }
        try {
            if (this.postProcessor.canTransform(s2)) {
                final Profiler.Section begin3 = this.profiler.begin("postprocessor");
                final byte[] transformClassBytes = this.postProcessor.transformClassBytes(s, s2, array);
                begin3.end();
                this.extensions.export(currentEnvironment, s2, false, transformClassBytes);
                return transformClassBytes;
            }
            SortedSet<MixinInfo> set = null;
            boolean b = false;
            for (final MixinConfig mixinConfig : this.configs) {
                if (mixinConfig.packageMatch(s2)) {
                    b = true;
                }
                else {
                    if (!mixinConfig.hasMixinsFor(s2)) {
                        continue;
                    }
                    if (set == null) {
                        set = new TreeSet<MixinInfo>();
                    }
                    set.addAll((Collection<?>)mixinConfig.getMixinsFor(s2));
                }
            }
            if (b) {
                throw new NoClassDefFoundError(String.format("%s is a mixin class and cannot be referenced directly", s2));
            }
            if (set != null) {
                if (check) {
                    MixinTransformer.logger.warn("Re-entrance detected, this will cause serious problems.", (Throwable)new MixinException());
                    throw new MixinApplyError("Re-entrance error.");
                }
                if (this.hotSwapper != null) {
                    this.hotSwapper.registerTargetClass(s2, array);
                }
                try {
                    final Profiler.Section begin4 = this.profiler.begin("read");
                    final TargetClassContext targetClassContext = new TargetClassContext(currentEnvironment, this.extensions, this.sessionId, s2, this.readClass(array, true), set);
                    begin4.end();
                    array = this.applyMixins(currentEnvironment, targetClassContext);
                    ++this.transformedCount;
                }
                catch (InvalidMixinException ex2) {
                    this.dumpClassOnFailure(s2, array, currentEnvironment);
                    this.handleMixinApplyError(s2, ex2, currentEnvironment);
                }
            }
            return array;
        }
        catch (Throwable t) {
            t.printStackTrace();
            this.dumpClassOnFailure(s2, array, currentEnvironment);
            throw new MixinTransformerError("An unexpected critical error was encountered", t);
        }
        finally {
            this.lock.pop();
            begin2.end();
        }
    }
    
    public List<String> reload(final String s, final byte[] array) {
        if (this.lock.getDepth() > 0) {
            throw new MixinApplyError("Cannot reload mixin if re-entrant lock entered");
        }
        final ArrayList<Object> list = (ArrayList<Object>)new ArrayList<String>();
        final Iterator<MixinConfig> iterator = this.configs.iterator();
        while (iterator.hasNext()) {
            list.addAll(iterator.next().reloadMixin(s, array));
        }
        return (List<String>)list;
    }
    
    private void checkSelect(final MixinEnvironment mixinEnvironment) {
        if (this.currentEnvironment != mixinEnvironment) {
            this.select(mixinEnvironment);
            return;
        }
        if (Mixins.getUnvisitedCount() > 0 && this.transformedCount == 0) {
            this.select(mixinEnvironment);
        }
    }
    
    private void select(final MixinEnvironment currentEnvironment) {
        this.verboseLoggingLevel = (currentEnvironment.getOption(MixinEnvironment.Option.DEBUG_VERBOSE) ? Level.INFO : Level.DEBUG);
        if (this.transformedCount > 0) {
            MixinTransformer.logger.log(this.verboseLoggingLevel, "Ending {}, applied {} mixins", new Object[] { this.currentEnvironment, this.transformedCount });
        }
        MixinTransformer.logger.log(this.verboseLoggingLevel, "{} mixins for {}", new Object[] { (this.currentEnvironment == currentEnvironment) ? "Checking for additional" : "Preparing", currentEnvironment });
        this.profiler.setActive(true);
        this.profiler.mark(currentEnvironment.getPhase().toString() + ":prepare");
        final Profiler.Section begin = this.profiler.begin("prepare");
        this.selectConfigs(currentEnvironment);
        this.extensions.select(currentEnvironment);
        final int prepareConfigs = this.prepareConfigs(currentEnvironment);
        this.currentEnvironment = currentEnvironment;
        this.transformedCount = 0;
        begin.end();
        final long time = begin.getTime();
        final double seconds = begin.getSeconds();
        if (seconds > 0.25) {
            MixinTransformer.logger.log(this.verboseLoggingLevel, "Prepared {} mixins in {} sec ({}ms avg) ({}ms load, {}ms transform, {}ms plugin)", new Object[] { prepareConfigs, new DecimalFormat("###0.000").format(seconds), new DecimalFormat("###0.0").format(time / (double)prepareConfigs), this.profiler.get("class.load").getTime(), this.profiler.get("class.transform").getTime(), this.profiler.get("mixin.plugin").getTime() });
        }
        this.profiler.mark(currentEnvironment.getPhase().toString() + ":apply");
        this.profiler.setActive(currentEnvironment.getOption(MixinEnvironment.Option.DEBUG_PROFILER));
    }
    
    private void selectConfigs(final MixinEnvironment mixinEnvironment) {
        final Iterator<Config> iterator = Mixins.getConfigs().iterator();
        while (iterator.hasNext()) {
            final Config config = iterator.next();
            try {
                final MixinConfig value = config.get();
                if (!value.select(mixinEnvironment)) {
                    continue;
                }
                iterator.remove();
                MixinTransformer.logger.log(this.verboseLoggingLevel, "Selecting config {}", new Object[] { value });
                value.onSelect();
                this.pendingConfigs.add(value);
            }
            catch (Exception ex) {
                MixinTransformer.logger.warn(String.format("Failed to select mixin config: %s", config), (Throwable)ex);
            }
        }
        Collections.sort(this.pendingConfigs);
    }
    
    private int prepareConfigs(final MixinEnvironment mixinEnvironment) {
        int n = 0;
        final IHotSwap hotSwapper = this.hotSwapper;
        for (final MixinConfig mixinConfig : this.pendingConfigs) {
            mixinConfig.addListener(this.postProcessor);
            if (hotSwapper != null) {
                mixinConfig.addListener(new MixinConfig.IListener(this, hotSwapper) {
                    final IHotSwap val$hotSwapper;
                    final MixinTransformer this$0;
                    
                    MixinTransformer$1(final MixinTransformer this$0, final IHotSwap val$hotSwapper) {
                        this.this$0 = this$0;
                        this.val$hotSwapper = val$hotSwapper;
                        super();
                    }
                    
                    @Override
                    public void onPrepare(final MixinInfo mixinInfo) {
                        this.val$hotSwapper.registerMixinClass(mixinInfo.getClassName());
                    }
                    
                    @Override
                    public void onInit(final MixinInfo mixinInfo) {
                    }
                });
            }
        }
        for (final MixinConfig mixinConfig2 : this.pendingConfigs) {
            try {
                MixinTransformer.logger.log(this.verboseLoggingLevel, "Preparing {} ({})", new Object[] { mixinConfig2, mixinConfig2.getDeclaredMixinCount() });
                mixinConfig2.prepare();
                n += mixinConfig2.getMixinCount();
            }
            catch (InvalidMixinException ex) {
                this.handleMixinPrepareError(mixinConfig2, ex, mixinEnvironment);
            }
            catch (Exception ex2) {
                MixinTransformer.logger.error("Error encountered whilst initialising mixin config '" + mixinConfig2.getName() + "': " + ex2.getMessage(), (Throwable)ex2);
            }
        }
        for (final MixinConfig mixinConfig3 : this.pendingConfigs) {
            final IMixinConfigPlugin plugin = mixinConfig3.getPlugin();
            if (plugin == null) {
                continue;
            }
            final HashSet<String> set = new HashSet<String>();
            for (final MixinConfig mixinConfig4 : this.pendingConfigs) {
                if (!mixinConfig4.equals(mixinConfig3)) {
                    set.addAll((Collection<?>)mixinConfig4.getTargets());
                }
            }
            plugin.acceptTargets(mixinConfig3.getTargets(), (Set<String>)Collections.unmodifiableSet((Set<?>)set));
        }
        for (final MixinConfig mixinConfig5 : this.pendingConfigs) {
            try {
                mixinConfig5.postInitialise();
            }
            catch (InvalidMixinException ex3) {
                this.handleMixinPrepareError(mixinConfig5, ex3, mixinEnvironment);
            }
            catch (Exception ex4) {
                MixinTransformer.logger.error("Error encountered during mixin config postInit step'" + mixinConfig5.getName() + "': " + ex4.getMessage(), (Throwable)ex4);
            }
        }
        this.configs.addAll(this.pendingConfigs);
        Collections.sort(this.configs);
        this.pendingConfigs.clear();
        return n;
    }
    
    private byte[] applyMixins(final MixinEnvironment mixinEnvironment, final TargetClassContext targetClassContext) {
        final Profiler.Section begin = this.profiler.begin("preapply");
        this.extensions.preApply(targetClassContext);
        final Profiler.Section next = begin.next("apply");
        this.apply(targetClassContext);
        final Profiler.Section next2 = next.next("postapply");
        try {
            this.extensions.postApply(targetClassContext);
        }
        catch (ExtensionCheckClass.ValidationFailedException ex) {
            MixinTransformer.logger.info(ex.getMessage());
            if (targetClassContext.isExportForced() || mixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_EXPORT)) {
                this.writeClass(targetClassContext);
            }
        }
        next2.end();
        return this.writeClass(targetClassContext);
    }
    
    private void apply(final TargetClassContext targetClassContext) {
        targetClassContext.applyMixins();
    }
    
    private void handleMixinPrepareError(final MixinConfig mixinConfig, final InvalidMixinException ex, final MixinEnvironment mixinEnvironment) throws MixinPrepareError {
        this.handleMixinError(mixinConfig.getName(), ex, mixinEnvironment, ErrorPhase.PREPARE);
    }
    
    private void handleMixinApplyError(final String s, final InvalidMixinException ex, final MixinEnvironment mixinEnvironment) throws MixinApplyError {
        this.handleMixinError(s, ex, mixinEnvironment, ErrorPhase.APPLY);
    }
    
    private void handleMixinError(final String s, final InvalidMixinException ex, final MixinEnvironment mixinEnvironment, final ErrorPhase errorPhase) throws Error {
        this.errorState = true;
        final IMixinInfo mixin = ex.getMixin();
        if (mixin == null) {
            MixinTransformer.logger.error("InvalidMixinException has no mixin!", (Throwable)ex);
            throw ex;
        }
        final IMixinConfig config = mixin.getConfig();
        final MixinEnvironment.Phase phase = mixin.getPhase();
        IMixinErrorHandler.ErrorAction errorAction = config.isRequired() ? IMixinErrorHandler.ErrorAction.ERROR : IMixinErrorHandler.ErrorAction.WARN;
        if (mixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
            new PrettyPrinter().add("Invalid Mixin").centre().hr('-').kvWidth(10).kv("Action", (Object)errorPhase.name()).kv("Mixin", (Object)mixin.getClassName()).kv("Config", (Object)config.getName()).kv("Phase", phase).hr('-').add("    %s", ex.getClass().getName()).hr('-').addWrapped("    %s", ex.getMessage()).hr('-').add(ex, 8).trace(errorAction.logLevel);
        }
        final Iterator<IMixinErrorHandler> iterator = this.getErrorHandlers(mixin.getPhase()).iterator();
        while (iterator.hasNext()) {
            final IMixinErrorHandler.ErrorAction onError = errorPhase.onError(iterator.next(), s, ex, mixin, errorAction);
            if (onError != null) {
                errorAction = onError;
            }
        }
        MixinTransformer.logger.log(errorAction.logLevel, errorPhase.getLogMessage(s, ex, mixin), (Throwable)ex);
        this.errorState = false;
        if (errorAction == IMixinErrorHandler.ErrorAction.ERROR) {
            throw new MixinApplyError(errorPhase.getErrorMessage(mixin, config, phase), ex);
        }
    }
    
    private List<IMixinErrorHandler> getErrorHandlers(final MixinEnvironment.Phase phase) {
        final ArrayList<IMixinErrorHandler> list = new ArrayList<IMixinErrorHandler>();
        for (final String s : Mixins.getErrorHandlerClasses()) {
            try {
                MixinTransformer.logger.info("Instancing error handler class {}", new Object[] { s });
                final IMixinErrorHandler mixinErrorHandler = (IMixinErrorHandler)this.service.getClassProvider().findClass(s, true).newInstance();
                if (mixinErrorHandler == null) {
                    continue;
                }
                list.add(mixinErrorHandler);
            }
            catch (Throwable t) {}
        }
        return list;
    }
    
    private byte[] writeClass(final TargetClassContext targetClassContext) {
        return this.writeClass(targetClassContext.getClassName(), targetClassContext.getClassNode(), targetClassContext.isExportForced());
    }
    
    private byte[] writeClass(final String s, final ClassNode classNode, final boolean b) {
        final Profiler.Section begin = this.profiler.begin("write");
        final byte[] writeClass = this.writeClass(classNode);
        begin.end();
        this.extensions.export(this.currentEnvironment, s, b, writeClass);
        return writeClass;
    }
    
    private void dumpClassOnFailure(final String s, final byte[] array, final MixinEnvironment mixinEnvironment) {
        if (mixinEnvironment.getOption(MixinEnvironment.Option.DUMP_TARGET_ON_FAILURE)) {
            this.extensions.getExtension(ExtensionClassExporter.class).dumpClass(s.replace('.', '/') + ".target", array);
        }
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
    
    enum ErrorPhase
    {
        PREPARE {
            MixinTransformer$ErrorPhase$1(final String s, final int n) {
            }
            
            @Override
            IMixinErrorHandler.ErrorAction onError(final IMixinErrorHandler mixinErrorHandler, final String s, final InvalidMixinException ex, final IMixinInfo mixinInfo, final IMixinErrorHandler.ErrorAction errorAction) {
                try {
                    return mixinErrorHandler.onPrepareError(mixinInfo.getConfig(), ex, mixinInfo, errorAction);
                }
                catch (AbstractMethodError abstractMethodError) {
                    return errorAction;
                }
            }
            
            @Override
            protected String getContext(final IMixinInfo mixinInfo, final String s) {
                return String.format("preparing %s in %s", mixinInfo.getName(), s);
            }
        }, 
        APPLY {
            MixinTransformer$ErrorPhase$2(final String s, final int n) {
            }
            
            @Override
            IMixinErrorHandler.ErrorAction onError(final IMixinErrorHandler mixinErrorHandler, final String s, final InvalidMixinException ex, final IMixinInfo mixinInfo, final IMixinErrorHandler.ErrorAction errorAction) {
                try {
                    return mixinErrorHandler.onApplyError(s, ex, mixinInfo, errorAction);
                }
                catch (AbstractMethodError abstractMethodError) {
                    return errorAction;
                }
            }
            
            @Override
            protected String getContext(final IMixinInfo mixinInfo, final String s) {
                return String.format("%s -> %s", mixinInfo, s);
            }
        };
        
        private final String text;
        private static final ErrorPhase[] $VALUES;
        
        public static ErrorPhase[] values() {
            return ErrorPhase.$VALUES.clone();
        }
        
        public static ErrorPhase valueOf(final String s) {
            return Enum.valueOf(ErrorPhase.class, s);
        }
        
        private ErrorPhase() {
            this.text = this.name().toLowerCase();
        }
        
        abstract IMixinErrorHandler.ErrorAction onError(final IMixinErrorHandler p0, final String p1, final InvalidMixinException p2, final IMixinInfo p3, final IMixinErrorHandler.ErrorAction p4);
        
        protected abstract String getContext(final IMixinInfo p0, final String p1);
        
        public String getLogMessage(final String s, final InvalidMixinException ex, final IMixinInfo mixinInfo) {
            return String.format("Mixin %s failed %s: %s %s", this.text, this.getContext(mixinInfo, s), ex.getClass().getName(), ex.getMessage());
        }
        
        public String getErrorMessage(final IMixinInfo mixinInfo, final IMixinConfig mixinConfig, final MixinEnvironment.Phase phase) {
            return String.format("Mixin [%s] from phase [%s] in config [%s] FAILED during %s", mixinInfo, phase, mixinConfig, this.name());
        }
        
        ErrorPhase(final String s, final int n, final MixinTransformer$1 listener) {
            this();
        }
        
        static {
            $VALUES = new ErrorPhase[] { ErrorPhase.PREPARE, ErrorPhase.APPLY };
        }
    }
}
