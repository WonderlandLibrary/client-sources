package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.util.perf.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.apache.logging.log4j.*;
import java.io.*;
import org.spongepowered.asm.service.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import java.util.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;

class MixinInfo implements Comparable<MixinInfo>, IMixinInfo
{
    private static final IMixinService classLoaderUtil;
    static int mixinOrder;
    private final transient Logger logger;
    private final transient Profiler profiler;
    private final transient MixinConfig parent;
    private final String name;
    private final String className;
    private final int priority;
    private final boolean virtual;
    private final List<ClassInfo> targetClasses;
    private final List<String> targetClassNames;
    private final transient int order;
    private final transient IMixinService service;
    private final transient IMixinConfigPlugin plugin;
    private final transient MixinEnvironment.Phase phase;
    private final transient ClassInfo info;
    private final transient SubType type;
    private final transient boolean strict;
    private transient State pendingState;
    private transient State state;
    
    MixinInfo(final IMixinService service, final MixinConfig parent, final String name, final boolean b, final IMixinConfigPlugin plugin, final boolean b2) {
        super();
        this.logger = LogManager.getLogger("mixin");
        this.profiler = MixinEnvironment.getProfiler();
        this.order = MixinInfo.mixinOrder++;
        this.service = service;
        this.parent = parent;
        this.name = name;
        this.className = parent.getMixinPackage() + name;
        this.plugin = plugin;
        this.phase = parent.getEnvironment().getPhase();
        this.strict = parent.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_TARGETS);
        try {
            this.pendingState = new State(this.loadMixinClass(this.className, b));
            this.info = this.pendingState.getClassInfo();
            this.type = SubType.getTypeFor(this);
        }
        catch (InvalidMixinException ex) {
            throw ex;
        }
        catch (Exception ex2) {
            throw new InvalidMixinException(this, ex2);
        }
        if (!this.type.isLoadable()) {
            MixinInfo.classLoaderUtil.registerInvalidClass(this.className);
        }
        try {
            this.priority = this.readPriority(this.pendingState.getClassNode());
            this.virtual = this.readPseudo(this.pendingState.getClassNode());
            this.targetClasses = this.readTargetClasses(this.pendingState.getClassNode(), b2);
            this.targetClassNames = Collections.unmodifiableList(Lists.transform(this.targetClasses, (Function<? super ClassInfo, ? extends String>)Functions.toStringFunction()));
        }
        catch (InvalidMixinException ex3) {
            throw ex3;
        }
        catch (Exception ex4) {
            throw new InvalidMixinException(this, ex4);
        }
    }
    
    void validate() {
        if (this.pendingState == null) {
            throw new IllegalStateException("No pending validation state for " + this);
        }
        try {
            this.pendingState.validate(this.type, this.targetClasses);
            this.state = this.pendingState;
        }
        finally {
            this.pendingState = null;
        }
    }
    
    protected List<ClassInfo> readTargetClasses(final MixinClassNode mixinClassNode, final boolean b) {
        if (mixinClassNode == null) {
            return Collections.emptyList();
        }
        final AnnotationNode invisible = Annotations.getInvisible(mixinClassNode, Mixin.class);
        if (invisible == null) {
            throw new InvalidMixinException(this, String.format("The mixin '%s' is missing an @Mixin annotation", this.className));
        }
        final ArrayList<ClassInfo> list = new ArrayList<ClassInfo>();
        final List<Object> fromList = Annotations.getValue(invisible, "value");
        final List<Object> fromList2 = Annotations.getValue(invisible, "targets");
        if (fromList != null) {
            this.readTargets(list, (Collection<String>)Lists.transform(fromList, (Function<? super Object, ?>)new Function<Type, String>(this) {
                final MixinInfo this$0;
                
                MixinInfo$1(final MixinInfo this$0) {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public String apply(final Type type) {
                    return type.getClassName();
                }
                
                @Override
                public Object apply(final Object o) {
                    return this.apply((Type)o);
                }
            }), b, false);
        }
        if (fromList2 != null) {
            this.readTargets(list, (Collection<String>)Lists.transform(fromList2, (Function<? super Object, ?>)new Function<String, String>(this) {
                final MixinInfo this$0;
                
                MixinInfo$2(final MixinInfo this$0) {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public String apply(final String s) {
                    return this.this$0.getParent().remapClassName(this.this$0.getClassRef(), s);
                }
                
                @Override
                public Object apply(final Object o) {
                    return this.apply((String)o);
                }
            }), b, true);
        }
        return list;
    }
    
    private void readTargets(final Collection<ClassInfo> collection, final Collection<String> collection2, final boolean b, final boolean b2) {
        final Iterator<String> iterator = collection2.iterator();
        while (iterator.hasNext()) {
            final String replace = iterator.next().replace('/', '.');
            if (MixinInfo.classLoaderUtil.isClassLoaded(replace) && !this.isReloading()) {
                final String format = String.format("Critical problem: %s target %s was already transformed.", this, replace);
                if (this.parent.isRequired()) {
                    throw new MixinTargetAlreadyLoadedException(this, format, replace);
                }
                this.logger.error(format);
            }
            if (this.shouldApplyMixin(b, replace)) {
                final ClassInfo target = this.getTarget(replace, b2);
                if (target == null || collection.contains(target)) {
                    continue;
                }
                collection.add(target);
                target.addMixin(this);
            }
        }
    }
    
    private boolean shouldApplyMixin(final boolean b, final String s) {
        final Profiler.Section begin = this.profiler.begin("plugin");
        final boolean b2 = this.plugin == null || b || this.plugin.shouldApplyMixin(s, this.className);
        begin.end();
        return b2;
    }
    
    private ClassInfo getTarget(final String s, final boolean b) throws InvalidMixinException {
        final ClassInfo forName = ClassInfo.forName(s);
        if (forName == null) {
            if (this.isVirtual()) {
                this.logger.debug("Skipping virtual target {} for {}", new Object[] { s, this });
            }
            else {
                this.handleTargetError(String.format("@Mixin target %s was not found %s", s, this));
            }
            return null;
        }
        this.type.validateTarget(s, forName);
        if (b && forName.isPublic() && !this.isVirtual()) {
            this.handleTargetError(String.format("@Mixin target %s is public in %s and should be specified in value", s, this));
        }
        return forName;
    }
    
    private void handleTargetError(final String s) {
        if (this.strict) {
            this.logger.error(s);
            throw new InvalidMixinException(this, s);
        }
        this.logger.warn(s);
    }
    
    protected int readPriority(final ClassNode classNode) {
        if (classNode == null) {
            return this.parent.getDefaultMixinPriority();
        }
        final AnnotationNode invisible = Annotations.getInvisible(classNode, Mixin.class);
        if (invisible == null) {
            throw new InvalidMixinException(this, String.format("The mixin '%s' is missing an @Mixin annotation", this.className));
        }
        final Integer n = Annotations.getValue(invisible, "priority");
        return (n == null) ? this.parent.getDefaultMixinPriority() : n;
    }
    
    protected boolean readPseudo(final ClassNode classNode) {
        return Annotations.getInvisible(classNode, Pseudo.class) != null;
    }
    
    private boolean isReloading() {
        return this.pendingState instanceof Reloaded;
    }
    
    private State getState() {
        return (this.state != null) ? this.state : this.pendingState;
    }
    
    ClassInfo getClassInfo() {
        return this.info;
    }
    
    @Override
    public IMixinConfig getConfig() {
        return this.parent;
    }
    
    MixinConfig getParent() {
        return this.parent;
    }
    
    @Override
    public int getPriority() {
        return this.priority;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getClassName() {
        return this.className;
    }
    
    @Override
    public String getClassRef() {
        return this.getClassInfo().getName();
    }
    
    @Override
    public byte[] getClassBytes() {
        return this.getState().getClassBytes();
    }
    
    @Override
    public boolean isDetachedSuper() {
        return this.getState().isDetachedSuper();
    }
    
    public boolean isUnique() {
        return this.getState().isUnique();
    }
    
    public boolean isVirtual() {
        return this.virtual;
    }
    
    public boolean isAccessor() {
        return this.type instanceof SubType.Accessor;
    }
    
    public boolean isLoadable() {
        return this.type.isLoadable();
    }
    
    public Level getLoggingLevel() {
        return this.parent.getLoggingLevel();
    }
    
    @Override
    public MixinEnvironment.Phase getPhase() {
        return this.phase;
    }
    
    @Override
    public MixinClassNode getClassNode(final int n) {
        return this.getState().createClassNode(n);
    }
    
    @Override
    public List<String> getTargetClasses() {
        return this.targetClassNames;
    }
    
    List<InterfaceInfo> getSoftImplements() {
        return Collections.unmodifiableList(this.getState().getSoftImplements());
    }
    
    Set<String> getSyntheticInnerClasses() {
        return Collections.unmodifiableSet((Set<? extends String>)this.getState().getSyntheticInnerClasses());
    }
    
    Set<String> getInnerClasses() {
        return Collections.unmodifiableSet((Set<? extends String>)this.getState().getInnerClasses());
    }
    
    List<ClassInfo> getTargets() {
        return Collections.unmodifiableList((List<? extends ClassInfo>)this.targetClasses);
    }
    
    Set<String> getInterfaces() {
        return this.getState().getInterfaces();
    }
    
    MixinTargetContext createContextFor(final TargetClassContext targetClassContext) {
        final MixinClassNode classNode = this.getClassNode(8);
        final Profiler.Section begin = this.profiler.begin("pre");
        final MixinTargetContext context = this.type.createPreProcessor(classNode).prepare().createContextFor(targetClassContext);
        begin.end();
        return context;
    }
    
    private byte[] loadMixinClass(final String s, final boolean b) throws ClassNotFoundException {
        byte[] classBytes;
        try {
            if (b) {
                final String classRestrictions = this.service.getClassRestrictions(s);
                if (classRestrictions.length() > 0) {
                    this.logger.error("Classloader restrictions [{}] encountered loading {}, name: {}", new Object[] { classRestrictions, this, s });
                }
            }
            classBytes = this.service.getBytecodeProvider().getClassBytes(s, b);
        }
        catch (ClassNotFoundException ex2) {
            throw new ClassNotFoundException(String.format("The specified mixin '%s' was not found", s));
        }
        catch (IOException ex) {
            this.logger.warn("Failed to load mixin {}, the specified mixin will not be applied", new Object[] { s });
            throw new InvalidMixinException(this, "An error was encountered whilst loading the mixin class", ex);
        }
        return classBytes;
    }
    
    void reloadMixin(final byte[] array) {
        if (this.pendingState != null) {
            throw new IllegalStateException("Cannot reload mixin while it is initialising");
        }
        this.pendingState = new Reloaded(this.state, array);
        this.validate();
    }
    
    @Override
    public int compareTo(final MixinInfo mixinInfo) {
        if (mixinInfo == null) {
            return 0;
        }
        if (mixinInfo.priority == this.priority) {
            return this.order - mixinInfo.order;
        }
        return this.priority - mixinInfo.priority;
    }
    
    public void preApply(final String s, final ClassNode classNode) {
        if (this.plugin != null) {
            final Profiler.Section begin = this.profiler.begin("plugin");
            this.plugin.preApply(s, classNode, this.className, this);
            begin.end();
        }
    }
    
    public void postApply(final String s, final ClassNode classNode) {
        if (this.plugin != null) {
            final Profiler.Section begin = this.profiler.begin("plugin");
            this.plugin.postApply(s, classNode, this.className, this);
            begin.end();
        }
        this.parent.postApply(s, classNode);
    }
    
    @Override
    public String toString() {
        return String.format("%s:%s", this.parent.getName(), this.name);
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((MixinInfo)o);
    }
    
    @Override
    public ClassNode getClassNode(final int n) {
        return this.getClassNode(n);
    }
    
    static {
        classLoaderUtil = MixinService.getService();
        MixinInfo.mixinOrder = 0;
    }
    
    class MixinMethodNode extends MethodNode
    {
        private final String originalName;
        final MixinInfo this$0;
        
        public MixinMethodNode(final MixinInfo this$0, final int n, final String originalName, final String s, final String s2, final String[] array) {
            this.this$0 = this$0;
            super(327680, n, originalName, s, s2, array);
            this.originalName = originalName;
        }
        
        @Override
        public String toString() {
            return String.format("%s%s", this.originalName, this.desc);
        }
        
        public String getOriginalName() {
            return this.originalName;
        }
        
        public boolean isInjector() {
            return this.getInjectorAnnotation() != null || this.isSurrogate();
        }
        
        public boolean isSurrogate() {
            return this.getVisibleAnnotation(Surrogate.class) != null;
        }
        
        public boolean isSynthetic() {
            return Bytecode.hasFlag(this, 4096);
        }
        
        public AnnotationNode getVisibleAnnotation(final Class<? extends Annotation> clazz) {
            return Annotations.getVisible(this, clazz);
        }
        
        public AnnotationNode getInjectorAnnotation() {
            return InjectionInfo.getInjectorAnnotation(this.this$0, this);
        }
        
        public IMixinInfo getOwner() {
            return this.this$0;
        }
    }
    
    class MixinClassNode extends ClassNode
    {
        public final List<MixinMethodNode> mixinMethods;
        final MixinInfo this$0;
        
        public MixinClassNode(final MixinInfo mixinInfo, final MixinInfo mixinInfo2) {
            this(mixinInfo, 327680);
        }
        
        public MixinClassNode(final MixinInfo this$0, final int n) {
            this.this$0 = this$0;
            super(n);
            this.mixinMethods = (List<MixinMethodNode>)this.methods;
        }
        
        public MixinInfo getMixin() {
            return this.this$0;
        }
        
        @Override
        public MethodVisitor visitMethod(final int n, final String s, final String s2, final String s3, final String[] array) {
            final MixinMethodNode mixinMethodNode = this.this$0.new MixinMethodNode(n, s, s2, s3, array);
            this.methods.add(mixinMethodNode);
            return mixinMethodNode;
        }
    }
    
    class State
    {
        private byte[] mixinBytes;
        private final ClassInfo classInfo;
        private boolean detachedSuper;
        private boolean unique;
        protected final Set<String> interfaces;
        protected final List<InterfaceInfo> softImplements;
        protected final Set<String> syntheticInnerClasses;
        protected final Set<String> innerClasses;
        protected MixinClassNode classNode;
        final MixinInfo this$0;
        
        State(final MixinInfo mixinInfo, final byte[] array) {
            this(mixinInfo, array, null);
        }
        
        State(final MixinInfo this$0, final byte[] mixinBytes, final ClassInfo classInfo) {
            this.this$0 = this$0;
            super();
            this.interfaces = new HashSet<String>();
            this.softImplements = new ArrayList<InterfaceInfo>();
            this.syntheticInnerClasses = new HashSet<String>();
            this.innerClasses = new HashSet<String>();
            this.mixinBytes = mixinBytes;
            this.connect();
            this.classInfo = ((classInfo != null) ? classInfo : ClassInfo.fromClassNode(this.getClassNode()));
        }
        
        private void connect() {
            this.classNode = this.createClassNode(0);
        }
        
        private void complete() {
            this.classNode = null;
        }
        
        ClassInfo getClassInfo() {
            return this.classInfo;
        }
        
        byte[] getClassBytes() {
            return this.mixinBytes;
        }
        
        MixinClassNode getClassNode() {
            return this.classNode;
        }
        
        boolean isDetachedSuper() {
            return this.detachedSuper;
        }
        
        boolean isUnique() {
            return this.unique;
        }
        
        List<? extends InterfaceInfo> getSoftImplements() {
            return this.softImplements;
        }
        
        Set<String> getSyntheticInnerClasses() {
            return this.syntheticInnerClasses;
        }
        
        Set<String> getInnerClasses() {
            return this.innerClasses;
        }
        
        Set<String> getInterfaces() {
            return this.interfaces;
        }
        
        MixinClassNode createClassNode(final int n) {
            final MixinClassNode mixinClassNode = this.this$0.new MixinClassNode(this.this$0);
            new ClassReader(this.mixinBytes).accept(mixinClassNode, n);
            return mixinClassNode;
        }
        
        void validate(final SubType subType, final List<ClassInfo> list) {
            final MixinPreProcessorStandard prepare = subType.createPreProcessor(this.getClassNode()).prepare();
            final Iterator<ClassInfo> iterator = list.iterator();
            while (iterator.hasNext()) {
                prepare.conform(iterator.next());
            }
            subType.validate(this, list);
            this.detachedSuper = subType.isDetachedSuper();
            this.unique = (Annotations.getVisible(this.getClassNode(), Unique.class) != null);
            this.validateInner();
            this.validateClassVersion();
            this.validateRemappables(list);
            this.readImplementations(subType);
            this.readInnerClasses();
            this.validateChanges(subType, list);
            this.complete();
        }
        
        private void validateInner() {
            if (!this.classInfo.isProbablyStatic()) {
                throw new InvalidMixinException(this.this$0, "Inner class mixin must be declared static");
            }
        }
        
        private void validateClassVersion() {
            if (this.classNode.version > MixinEnvironment.getCompatibilityLevel().classVersion()) {
                String format = ".";
                for (final MixinEnvironment.CompatibilityLevel compatibilityLevel : MixinEnvironment.CompatibilityLevel.values()) {
                    if (compatibilityLevel.classVersion() >= this.classNode.version) {
                        format = String.format(". Mixin requires compatibility level %s or above.", compatibilityLevel.name());
                    }
                }
                throw new InvalidMixinException(this.this$0, "Unsupported mixin class version " + this.classNode.version + format);
            }
        }
        
        private void validateRemappables(final List<ClassInfo> list) {
            if (list.size() > 1) {
                for (final FieldNode fieldNode : this.classNode.fields) {
                    this.validateRemappable(Shadow.class, fieldNode.name, Annotations.getVisible(fieldNode, Shadow.class));
                }
                for (final MethodNode methodNode : this.classNode.methods) {
                    this.validateRemappable(Shadow.class, methodNode.name, Annotations.getVisible(methodNode, Shadow.class));
                    if (Annotations.getVisible(methodNode, Overwrite.class) != null && ((methodNode.access & 0x8) == 0x0 || (methodNode.access & 0x1) == 0x0)) {
                        throw new InvalidMixinException(this.this$0, "Found @Overwrite annotation on " + methodNode.name + " in " + this.this$0);
                    }
                }
            }
        }
        
        private void validateRemappable(final Class<Shadow> clazz, final String s, final AnnotationNode annotationNode) {
            if (annotationNode != null && Annotations.getValue(annotationNode, "remap", Boolean.TRUE)) {
                throw new InvalidMixinException(this.this$0, "Found a remappable @" + clazz.getSimpleName() + " annotation on " + s + " in " + this);
            }
        }
        
        void readImplementations(final SubType subType) {
            this.interfaces.addAll(this.classNode.interfaces);
            this.interfaces.addAll(subType.getInterfaces());
            final AnnotationNode invisible = Annotations.getInvisible(this.classNode, Implements.class);
            if (invisible == null) {
                return;
            }
            final List<AnnotationNode> list = Annotations.getValue(invisible);
            if (list == null) {
                return;
            }
            final Iterator<AnnotationNode> iterator = list.iterator();
            while (iterator.hasNext()) {
                final InterfaceInfo fromAnnotation = InterfaceInfo.fromAnnotation(this.this$0, iterator.next());
                this.softImplements.add(fromAnnotation);
                this.interfaces.add(fromAnnotation.getInternalName());
                if (!(this instanceof Reloaded)) {
                    this.classInfo.addInterface(fromAnnotation.getInternalName());
                }
            }
        }
        
        void readInnerClasses() {
            for (final InnerClassNode innerClassNode : this.classNode.innerClasses) {
                final ClassInfo forName = ClassInfo.forName(innerClassNode.name);
                if ((innerClassNode.outerName != null && innerClassNode.outerName.equals(this.classInfo.getName())) || innerClassNode.name.startsWith(this.classNode.name + "$")) {
                    if (forName.isProbablyStatic() && forName.isSynthetic()) {
                        this.syntheticInnerClasses.add(innerClassNode.name);
                    }
                    else {
                        this.innerClasses.add(innerClassNode.name);
                    }
                }
            }
        }
        
        protected void validateChanges(final SubType subType, final List<ClassInfo> list) {
            subType.createPreProcessor(this.classNode).prepare();
        }
    }
    
    class Reloaded extends State
    {
        private final State previous;
        final MixinInfo this$0;
        
        Reloaded(final MixinInfo this$0, final State previous, final byte[] array) {
            this.this$0 = this$0.super(array, previous.getClassInfo());
            this.previous = previous;
        }
        
        @Override
        protected void validateChanges(final SubType subType, final List<ClassInfo> list) {
            if (!this.syntheticInnerClasses.equals(this.previous.syntheticInnerClasses)) {
                throw new MixinReloadException(this.this$0, "Cannot change inner classes");
            }
            if (!this.interfaces.equals(this.previous.interfaces)) {
                throw new MixinReloadException(this.this$0, "Cannot change interfaces");
            }
            if (!new HashSet(this.softImplements).equals(new HashSet(this.previous.softImplements))) {
                throw new MixinReloadException(this.this$0, "Cannot change soft interfaces");
            }
            if (!new HashSet(this.this$0.readTargetClasses(this.classNode, true)).equals(new HashSet(list))) {
                throw new MixinReloadException(this.this$0, "Cannot change target classes");
            }
            if (this.this$0.readPriority(this.classNode) != this.this$0.getPriority()) {
                throw new MixinReloadException(this.this$0, "Cannot change mixin priority");
            }
        }
    }
    
    abstract static class SubType
    {
        protected final MixinInfo mixin;
        protected final String annotationType;
        protected final boolean targetMustBeInterface;
        protected boolean detached;
        
        SubType(final MixinInfo mixin, final String annotationType, final boolean targetMustBeInterface) {
            super();
            this.mixin = mixin;
            this.annotationType = annotationType;
            this.targetMustBeInterface = targetMustBeInterface;
        }
        
        Collection<String> getInterfaces() {
            return (Collection<String>)Collections.emptyList();
        }
        
        boolean isDetachedSuper() {
            return this.detached;
        }
        
        boolean isLoadable() {
            return false;
        }
        
        void validateTarget(final String s, final ClassInfo classInfo) {
            final boolean interface1 = classInfo.isInterface();
            if (interface1 != this.targetMustBeInterface) {
                throw new InvalidMixinException(this.mixin, this.annotationType + " target type mismatch: " + s + " is " + (interface1 ? "" : "not ") + "an interface in " + this);
            }
        }
        
        abstract void validate(final State p0, final List<ClassInfo> p1);
        
        abstract MixinPreProcessorStandard createPreProcessor(final MixinClassNode p0);
        
        static SubType getTypeFor(final MixinInfo mixinInfo) {
            if (!mixinInfo.getClassInfo().isInterface()) {
                return new Standard(mixinInfo);
            }
            boolean b = false;
            final Iterator<ClassInfo.Method> iterator = mixinInfo.getClassInfo().getMethods().iterator();
            while (iterator.hasNext()) {
                b |= !iterator.next().isAccessor();
            }
            if (b) {
                return new Interface(mixinInfo);
            }
            return new Accessor(mixinInfo);
        }
        
        static class Standard extends SubType
        {
            Standard(final MixinInfo mixinInfo) {
                super(mixinInfo, "@Mixin", false);
            }
            
            @Override
            void validate(final State state, final List<ClassInfo> list) {
                final MixinClassNode classNode = state.getClassNode();
                for (final ClassInfo classInfo : list) {
                    if (classNode.superName.equals(classInfo.getSuperName())) {
                        continue;
                    }
                    if (!classInfo.hasSuperClass(classNode.superName, ClassInfo.Traversal.SUPER)) {
                        final ClassInfo forName = ClassInfo.forName(classNode.superName);
                        if (forName.isMixin()) {
                            for (final ClassInfo classInfo2 : forName.getTargets()) {
                                if (list.contains(classInfo2)) {
                                    throw new InvalidMixinException(this.mixin, "Illegal hierarchy detected. Derived mixin " + this + " targets the same class " + classInfo2.getClassName() + " as its superclass " + forName.getClassName());
                                }
                            }
                        }
                        throw new InvalidMixinException(this.mixin, "Super class '" + classNode.superName.replace('/', '.') + "' of " + this.mixin.getName() + " was not found in the hierarchy of target class '" + classInfo + "'");
                    }
                    this.detached = true;
                }
            }
            
            @Override
            MixinPreProcessorStandard createPreProcessor(final MixinClassNode mixinClassNode) {
                return new MixinPreProcessorStandard(this.mixin, mixinClassNode);
            }
        }
        
        static class Interface extends SubType
        {
            Interface(final MixinInfo mixinInfo) {
                super(mixinInfo, "@Mixin", true);
            }
            
            @Override
            void validate(final State state, final List<ClassInfo> list) {
                if (!MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()) {
                    throw new InvalidMixinException(this.mixin, "Interface mixin not supported in current enviromnment");
                }
                final MixinClassNode classNode = state.getClassNode();
                if (!"java/lang/Object".equals(classNode.superName)) {
                    throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + classNode.superName.replace('/', '.'));
                }
            }
            
            @Override
            MixinPreProcessorStandard createPreProcessor(final MixinClassNode mixinClassNode) {
                return new MixinPreProcessorInterface(this.mixin, mixinClassNode);
            }
        }
        
        static class Accessor extends SubType
        {
            private final Collection<String> interfaces;
            
            Accessor(final MixinInfo mixinInfo) {
                super(mixinInfo, "@Mixin", false);
                (this.interfaces = new ArrayList<String>()).add(mixinInfo.getClassRef());
            }
            
            @Override
            boolean isLoadable() {
                return true;
            }
            
            @Override
            Collection<String> getInterfaces() {
                return this.interfaces;
            }
            
            @Override
            void validateTarget(final String s, final ClassInfo classInfo) {
                if (classInfo.isInterface() && !MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()) {
                    throw new InvalidMixinException(this.mixin, "Accessor mixin targetting an interface is not supported in current enviromnment");
                }
            }
            
            @Override
            void validate(final State state, final List<ClassInfo> list) {
                final MixinClassNode classNode = state.getClassNode();
                if (!"java/lang/Object".equals(classNode.superName)) {
                    throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + classNode.superName.replace('/', '.'));
                }
            }
            
            @Override
            MixinPreProcessorStandard createPreProcessor(final MixinClassNode mixinClassNode) {
                return new MixinPreProcessorAccessor(this.mixin, mixinClassNode);
            }
        }
    }
}
