package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.util.perf.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.transformer.meta.*;
import org.spongepowered.asm.mixin.gen.throwables.*;
import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.util.throwables.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import com.google.common.base.*;
import org.spongepowered.asm.lib.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

class MixinPreProcessorStandard
{
    private static final Logger logger;
    protected final MixinInfo mixin;
    protected final MixinInfo.MixinClassNode classNode;
    protected final MixinEnvironment env;
    protected final Profiler profiler;
    private final boolean verboseLogging;
    private final boolean strictUnique;
    private boolean prepared;
    private boolean attached;
    
    MixinPreProcessorStandard(final MixinInfo mixin, final MixinInfo.MixinClassNode classNode) {
        super();
        this.profiler = MixinEnvironment.getProfiler();
        this.mixin = mixin;
        this.classNode = classNode;
        this.env = mixin.getParent().getEnvironment();
        this.verboseLogging = this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE);
        this.strictUnique = this.env.getOption(MixinEnvironment.Option.DEBUG_UNIQUE);
    }
    
    final MixinPreProcessorStandard prepare() {
        if (this.prepared) {
            return this;
        }
        this.prepared = true;
        final Profiler.Section begin = this.profiler.begin("prepare");
        for (final MixinInfo.MixinMethodNode mixinMethodNode : this.classNode.mixinMethods) {
            this.prepareMethod(mixinMethodNode, this.mixin.getClassInfo().findMethod(mixinMethodNode));
        }
        final Iterator<FieldNode> iterator2 = this.classNode.fields.iterator();
        while (iterator2.hasNext()) {
            this.prepareField(iterator2.next());
        }
        begin.end();
        return this;
    }
    
    protected void prepareMethod(final MixinInfo.MixinMethodNode mixinMethodNode, final ClassInfo.Method method) {
        this.prepareShadow(mixinMethodNode, method);
        this.prepareSoftImplements(mixinMethodNode, method);
    }
    
    protected void prepareShadow(final MixinInfo.MixinMethodNode mixinMethodNode, final ClassInfo.Method method) {
        final AnnotationNode visible = Annotations.getVisible(mixinMethodNode, Shadow.class);
        if (visible == null) {
            return;
        }
        final String s = Annotations.getValue(visible, "prefix", (Class<?>)Shadow.class);
        if (mixinMethodNode.name.startsWith(s)) {
            Annotations.setVisible(mixinMethodNode, MixinRenamed.class, "originalName", mixinMethodNode.name);
            mixinMethodNode.name = method.renameTo(mixinMethodNode.name.substring(s.length()));
        }
    }
    
    protected void prepareSoftImplements(final MixinInfo.MixinMethodNode mixinMethodNode, final ClassInfo.Method method) {
        final Iterator<InterfaceInfo> iterator = this.mixin.getSoftImplements().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().renameMethod(mixinMethodNode)) {
                method.renameTo(mixinMethodNode.name);
            }
        }
    }
    
    protected void prepareField(final FieldNode fieldNode) {
    }
    
    final MixinPreProcessorStandard conform(final TargetClassContext targetClassContext) {
        return this.conform(targetClassContext.getClassInfo());
    }
    
    final MixinPreProcessorStandard conform(final ClassInfo classInfo) {
        final Profiler.Section begin = this.profiler.begin("conform");
        for (final MixinInfo.MixinMethodNode mixinMethodNode : this.classNode.mixinMethods) {
            if (mixinMethodNode.isInjector()) {
                this.conformInjector(classInfo, mixinMethodNode, this.mixin.getClassInfo().findMethod(mixinMethodNode, 10));
            }
        }
        begin.end();
        return this;
    }
    
    private void conformInjector(final ClassInfo classInfo, final MixinInfo.MixinMethodNode mixinMethodNode, final ClassInfo.Method method) {
        classInfo.getMethodMapper().remapHandlerMethod(this.mixin, mixinMethodNode, method);
    }
    
    MixinTargetContext createContextFor(final TargetClassContext targetClassContext) {
        final MixinTargetContext mixinTargetContext = new MixinTargetContext(this.mixin, this.classNode, targetClassContext);
        this.conform(targetClassContext);
        this.attach(mixinTargetContext);
        return mixinTargetContext;
    }
    
    final MixinPreProcessorStandard attach(final MixinTargetContext mixinTargetContext) {
        if (this.attached) {
            throw new IllegalStateException("Preprocessor was already attached");
        }
        this.attached = true;
        final Profiler.Section begin = this.profiler.begin("attach");
        final Profiler.Section begin2 = this.profiler.begin("methods");
        this.attachMethods(mixinTargetContext);
        final Profiler.Section next = begin2.next("fields");
        this.attachFields(mixinTargetContext);
        final Profiler.Section next2 = next.next("transform");
        this.transform(mixinTargetContext);
        next2.end();
        begin.end();
        return this;
    }
    
    protected void attachMethods(final MixinTargetContext mixinTargetContext) {
        final Iterator<MixinInfo.MixinMethodNode> iterator = this.classNode.mixinMethods.iterator();
        while (iterator.hasNext()) {
            final MixinInfo.MixinMethodNode mixinMethodNode = iterator.next();
            if (!this.validateMethod(mixinTargetContext, mixinMethodNode)) {
                iterator.remove();
            }
            else if (this.attachInjectorMethod(mixinTargetContext, mixinMethodNode)) {
                mixinTargetContext.addMixinMethod(mixinMethodNode);
            }
            else if (this.attachAccessorMethod(mixinTargetContext, mixinMethodNode)) {
                iterator.remove();
            }
            else if (this.attachShadowMethod(mixinTargetContext, mixinMethodNode)) {
                mixinTargetContext.addShadowMethod(mixinMethodNode);
                iterator.remove();
            }
            else if (this.attachOverwriteMethod(mixinTargetContext, mixinMethodNode)) {
                mixinTargetContext.addMixinMethod(mixinMethodNode);
            }
            else if (this.attachUniqueMethod(mixinTargetContext, mixinMethodNode)) {
                iterator.remove();
            }
            else {
                this.attachMethod(mixinTargetContext, mixinMethodNode);
                mixinTargetContext.addMixinMethod(mixinMethodNode);
            }
        }
    }
    
    protected boolean validateMethod(final MixinTargetContext mixinTargetContext, final MixinInfo.MixinMethodNode mixinMethodNode) {
        return true;
    }
    
    protected boolean attachInjectorMethod(final MixinTargetContext mixinTargetContext, final MixinInfo.MixinMethodNode mixinMethodNode) {
        return mixinMethodNode.isInjector();
    }
    
    protected boolean attachAccessorMethod(final MixinTargetContext mixinTargetContext, final MixinInfo.MixinMethodNode mixinMethodNode) {
        return this.attachAccessorMethod(mixinTargetContext, mixinMethodNode, SpecialMethod.ACCESSOR) || this.attachAccessorMethod(mixinTargetContext, mixinMethodNode, SpecialMethod.INVOKER);
    }
    
    protected boolean attachAccessorMethod(final MixinTargetContext mixinTargetContext, final MixinInfo.MixinMethodNode mixinMethodNode, final SpecialMethod specialMethod) {
        if (mixinMethodNode.getVisibleAnnotation(specialMethod.annotation) == null) {
            return false;
        }
        final String string = specialMethod + " method " + mixinMethodNode.name;
        final ClassInfo.Method specialMethod2 = this.getSpecialMethod(mixinMethodNode, specialMethod);
        if (MixinEnvironment.getCompatibilityLevel().isAtLeast(MixinEnvironment.CompatibilityLevel.JAVA_8) && specialMethod2.isStatic()) {
            if (this.mixin.getTargets().size() > 1) {
                throw new InvalidAccessorException(mixinTargetContext, string + " in multi-target mixin is invalid. Mixin must have exactly 1 target.");
            }
            final String uniqueName = mixinTargetContext.getUniqueName(mixinMethodNode, true);
            MixinPreProcessorStandard.logger.log(this.mixin.getLoggingLevel(), "Renaming @Unique method {}{} to {} in {}", new Object[] { mixinMethodNode.name, mixinMethodNode.desc, uniqueName, this.mixin });
            mixinMethodNode.name = specialMethod2.renameTo(uniqueName);
        }
        else {
            if (!specialMethod2.isAbstract()) {
                throw new InvalidAccessorException(mixinTargetContext, string + " is not abstract");
            }
            if (specialMethod2.isStatic()) {
                throw new InvalidAccessorException(mixinTargetContext, string + " cannot be static");
            }
        }
        mixinTargetContext.addAccessorMethod(mixinMethodNode, specialMethod.annotation);
        return true;
    }
    
    protected boolean attachShadowMethod(final MixinTargetContext mixinTargetContext, final MixinInfo.MixinMethodNode mixinMethodNode) {
        return this.attachSpecialMethod(mixinTargetContext, mixinMethodNode, SpecialMethod.SHADOW);
    }
    
    protected boolean attachOverwriteMethod(final MixinTargetContext mixinTargetContext, final MixinInfo.MixinMethodNode mixinMethodNode) {
        return this.attachSpecialMethod(mixinTargetContext, mixinMethodNode, SpecialMethod.OVERWRITE);
    }
    
    protected boolean attachSpecialMethod(final MixinTargetContext mixinTargetContext, final MixinInfo.MixinMethodNode mixinMethodNode, final SpecialMethod specialMethod) {
        final AnnotationNode visibleAnnotation = mixinMethodNode.getVisibleAnnotation(specialMethod.annotation);
        if (visibleAnnotation == null) {
            return false;
        }
        if (specialMethod.isOverwrite) {
            this.checkMixinNotUnique(mixinMethodNode, specialMethod);
        }
        final ClassInfo.Method specialMethod2 = this.getSpecialMethod(mixinMethodNode, specialMethod);
        MethodNode methodNode = mixinTargetContext.findMethod(mixinMethodNode, visibleAnnotation);
        if (methodNode == null) {
            if (specialMethod.isOverwrite) {
                return false;
            }
            methodNode = mixinTargetContext.findRemappedMethod(mixinMethodNode);
            if (methodNode == null) {
                throw new InvalidMixinException(this.mixin, String.format("%s method %s in %s was not located in the target class %s. %s%s", specialMethod, mixinMethodNode.name, this.mixin, mixinTargetContext.getTarget(), mixinTargetContext.getReferenceMapper().getStatus(), getDynamicInfo(mixinMethodNode)));
            }
            mixinMethodNode.name = specialMethod2.renameTo(methodNode.name);
        }
        if ("<init>".equals(methodNode.name)) {
            throw new InvalidMixinException(this.mixin, String.format("Nice try! %s in %s cannot alias a constructor", mixinMethodNode.name, this.mixin));
        }
        if (!Bytecode.compareFlags(mixinMethodNode, methodNode, 8)) {
            throw new InvalidMixinException(this.mixin, String.format("STATIC modifier of %s method %s in %s does not match the target", specialMethod, mixinMethodNode.name, this.mixin));
        }
        this.conformVisibility(mixinTargetContext, mixinMethodNode, specialMethod, methodNode);
        if (!methodNode.name.equals(mixinMethodNode.name)) {
            if (specialMethod.isOverwrite && (methodNode.access & 0x2) == 0x0) {
                throw new InvalidMixinException(this.mixin, "Non-private method cannot be aliased. Found " + methodNode.name);
            }
            mixinMethodNode.name = specialMethod2.renameTo(methodNode.name);
        }
        return true;
    }
    
    private void conformVisibility(final MixinTargetContext mixinTargetContext, final MixinInfo.MixinMethodNode mixinMethodNode, final SpecialMethod specialMethod, final MethodNode methodNode) {
        final Bytecode.Visibility visibility = Bytecode.getVisibility(methodNode);
        final Bytecode.Visibility visibility2 = Bytecode.getVisibility(mixinMethodNode);
        if (visibility2.ordinal() >= visibility.ordinal()) {
            if (visibility == Bytecode.Visibility.PRIVATE && visibility2.ordinal() > Bytecode.Visibility.PRIVATE.ordinal()) {
                mixinTargetContext.getTarget().addUpgradedMethod(methodNode);
            }
            return;
        }
        final String format = String.format("%s %s method %s in %s cannot reduce visibiliy of %s target method", visibility2, specialMethod, mixinMethodNode.name, this.mixin, visibility);
        if (specialMethod.isOverwrite && !this.mixin.getParent().conformOverwriteVisibility()) {
            throw new InvalidMixinException(this.mixin, format);
        }
        if (visibility2 == Bytecode.Visibility.PRIVATE) {
            if (specialMethod.isOverwrite) {
                MixinPreProcessorStandard.logger.warn("Static binding violation: {}, visibility will be upgraded.", new Object[] { format });
            }
            mixinTargetContext.addUpgradedMethod(mixinMethodNode);
            Bytecode.setVisibility(mixinMethodNode, visibility);
        }
    }
    
    protected ClassInfo.Method getSpecialMethod(final MixinInfo.MixinMethodNode mixinMethodNode, final SpecialMethod specialMethod) {
        final ClassInfo.Method method = this.mixin.getClassInfo().findMethod(mixinMethodNode, 10);
        this.checkMethodNotUnique(method, specialMethod);
        return method;
    }
    
    protected void checkMethodNotUnique(final ClassInfo.Method method, final SpecialMethod specialMethod) {
        if (method.isUnique()) {
            throw new InvalidMixinException(this.mixin, String.format("%s method %s in %s cannot be @Unique", specialMethod, method.getName(), this.mixin));
        }
    }
    
    protected void checkMixinNotUnique(final MixinInfo.MixinMethodNode mixinMethodNode, final SpecialMethod specialMethod) {
        if (this.mixin.isUnique()) {
            throw new InvalidMixinException(this.mixin, String.format("%s method %s found in a @Unique mixin %s", specialMethod, mixinMethodNode.name, this.mixin));
        }
    }
    
    protected boolean attachUniqueMethod(final MixinTargetContext mixinTargetContext, final MixinInfo.MixinMethodNode mixinMethodNode) {
        final ClassInfo.Method method = this.mixin.getClassInfo().findMethod(mixinMethodNode, 10);
        if (method == null || (!method.isUnique() && !this.mixin.isUnique() && !method.isSynthetic())) {
            return false;
        }
        if (method.isSynthetic()) {
            mixinTargetContext.transformDescriptor(mixinMethodNode);
            method.remapTo(mixinMethodNode.desc);
        }
        final MethodNode method2 = mixinTargetContext.findMethod(mixinMethodNode, null);
        if (method2 == null) {
            return false;
        }
        final String s = method.isSynthetic() ? "synthetic" : "@Unique";
        if (Bytecode.getVisibility(mixinMethodNode).ordinal() < Bytecode.Visibility.PUBLIC.ordinal()) {
            final String uniqueName = mixinTargetContext.getUniqueName(mixinMethodNode, false);
            MixinPreProcessorStandard.logger.log(this.mixin.getLoggingLevel(), "Renaming {} method {}{} to {} in {}", new Object[] { s, mixinMethodNode.name, mixinMethodNode.desc, uniqueName, this.mixin });
            mixinMethodNode.name = method.renameTo(uniqueName);
            return false;
        }
        if (this.strictUnique) {
            throw new InvalidMixinException(this.mixin, String.format("Method conflict, %s method %s in %s cannot overwrite %s%s in %s", s, mixinMethodNode.name, this.mixin, method2.name, method2.desc, mixinTargetContext.getTarget()));
        }
        final AnnotationNode visible = Annotations.getVisible(mixinMethodNode, Unique.class);
        if (visible == null || !Annotations.getValue(visible, "silent", Boolean.FALSE)) {
            if (Bytecode.hasFlag(mixinMethodNode, 64)) {
                try {
                    Bytecode.compareBridgeMethods(method2, mixinMethodNode);
                    MixinPreProcessorStandard.logger.debug("Discarding sythetic bridge method {} in {} because existing method in {} is compatible", new Object[] { s, mixinMethodNode.name, this.mixin, mixinTargetContext.getTarget() });
                    return true;
                }
                catch (SyntheticBridgeException ex) {
                    if (this.verboseLogging || this.env.getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
                        ex.printAnalysis(mixinTargetContext, method2, mixinMethodNode);
                    }
                    throw new InvalidMixinException(this.mixin, ex.getMessage());
                }
            }
            MixinPreProcessorStandard.logger.warn("Discarding {} public method {} in {} because it already exists in {}", new Object[] { s, mixinMethodNode.name, this.mixin, mixinTargetContext.getTarget() });
            return true;
        }
        mixinTargetContext.addMixinMethod(mixinMethodNode);
        return true;
    }
    
    protected void attachMethod(final MixinTargetContext mixinTargetContext, final MixinInfo.MixinMethodNode mixinMethodNode) {
        final ClassInfo.Method method = this.mixin.getClassInfo().findMethod(mixinMethodNode);
        if (method == null) {
            return;
        }
        final ClassInfo.Method methodInHierarchy = this.mixin.getClassInfo().findMethodInHierarchy(mixinMethodNode, ClassInfo.SearchType.SUPER_CLASSES_ONLY);
        if (methodInHierarchy != null && methodInHierarchy.isRenamed()) {
            mixinMethodNode.name = method.renameTo(methodInHierarchy.getName());
        }
        final MethodNode method2 = mixinTargetContext.findMethod(mixinMethodNode, null);
        if (method2 != null) {
            this.conformVisibility(mixinTargetContext, mixinMethodNode, SpecialMethod.MERGE, method2);
        }
    }
    
    protected void attachFields(final MixinTargetContext mixinTargetContext) {
        final Iterator<FieldNode> iterator = this.classNode.fields.iterator();
        while (iterator.hasNext()) {
            final FieldNode fieldNode = iterator.next();
            final AnnotationNode visible = Annotations.getVisible(fieldNode, Shadow.class);
            final boolean b = visible != null;
            if (!this.validateField(mixinTargetContext, fieldNode, visible)) {
                iterator.remove();
            }
            else {
                final ClassInfo.Field field = this.mixin.getClassInfo().findField(fieldNode);
                mixinTargetContext.transformDescriptor(fieldNode);
                field.remapTo(fieldNode.desc);
                if (field.isUnique() && b) {
                    throw new InvalidMixinException(this.mixin, String.format("@Shadow field %s cannot be @Unique", fieldNode.name));
                }
                FieldNode fieldNode2 = mixinTargetContext.findField(fieldNode, visible);
                if (fieldNode2 == null) {
                    if (visible == null) {
                        continue;
                    }
                    fieldNode2 = mixinTargetContext.findRemappedField(fieldNode);
                    if (fieldNode2 == null) {
                        throw new InvalidMixinException(this.mixin, String.format("Shadow field %s was not located in the target class %s. %s%s", fieldNode.name, mixinTargetContext.getTarget(), mixinTargetContext.getReferenceMapper().getStatus(), getDynamicInfo(fieldNode)));
                    }
                    fieldNode.name = field.renameTo(fieldNode2.name);
                }
                if (!Bytecode.compareFlags(fieldNode, fieldNode2, 8)) {
                    throw new InvalidMixinException(this.mixin, String.format("STATIC modifier of @Shadow field %s in %s does not match the target", fieldNode.name, this.mixin));
                }
                if (field.isUnique()) {
                    if ((fieldNode.access & 0x6) != 0x0) {
                        final String uniqueName = mixinTargetContext.getUniqueName(fieldNode);
                        MixinPreProcessorStandard.logger.log(this.mixin.getLoggingLevel(), "Renaming @Unique field {}{} to {} in {}", new Object[] { fieldNode.name, fieldNode.desc, uniqueName, this.mixin });
                        fieldNode.name = field.renameTo(uniqueName);
                    }
                    else {
                        if (this.strictUnique) {
                            throw new InvalidMixinException(this.mixin, String.format("Field conflict, @Unique field %s in %s cannot overwrite %s%s in %s", fieldNode.name, this.mixin, fieldNode2.name, fieldNode2.desc, mixinTargetContext.getTarget()));
                        }
                        MixinPreProcessorStandard.logger.warn("Discarding @Unique public field {} in {} because it already exists in {}. Note that declared FIELD INITIALISERS will NOT be removed!", new Object[] { fieldNode.name, this.mixin, mixinTargetContext.getTarget() });
                        iterator.remove();
                    }
                }
                else {
                    if (!fieldNode2.desc.equals(fieldNode.desc)) {
                        throw new InvalidMixinException(this.mixin, String.format("The field %s in the target class has a conflicting signature", fieldNode.name));
                    }
                    if (!fieldNode2.name.equals(fieldNode.name)) {
                        if ((fieldNode2.access & 0x2) == 0x0 && (fieldNode2.access & 0x1000) == 0x0) {
                            throw new InvalidMixinException(this.mixin, "Non-private field cannot be aliased. Found " + fieldNode2.name);
                        }
                        fieldNode.name = field.renameTo(fieldNode2.name);
                    }
                    iterator.remove();
                    if (!b) {
                        continue;
                    }
                    final boolean decoratedFinal = field.isDecoratedFinal();
                    if (this.verboseLogging && Bytecode.hasFlag(fieldNode2, 16) != decoratedFinal) {
                        MixinPreProcessorStandard.logger.warn(decoratedFinal ? "@Shadow field {}::{} is decorated with @Final but target is not final" : "@Shadow target {}::{} is final but shadow is not decorated with @Final", new Object[] { this.mixin, fieldNode.name });
                    }
                    mixinTargetContext.addShadowField(fieldNode, field);
                }
            }
        }
    }
    
    protected boolean validateField(final MixinTargetContext mixinTargetContext, final FieldNode fieldNode, final AnnotationNode annotationNode) {
        if (Bytecode.hasFlag(fieldNode, 8) && !Bytecode.hasFlag(fieldNode, 2) && !Bytecode.hasFlag(fieldNode, 4096) && annotationNode == null) {
            throw new InvalidMixinException(mixinTargetContext, String.format("Mixin %s contains non-private static field %s:%s", mixinTargetContext, fieldNode.name, fieldNode.desc));
        }
        if (fieldNode.name.startsWith(Annotations.getValue(annotationNode, "prefix", (Class<?>)Shadow.class))) {
            throw new InvalidMixinException(mixinTargetContext, String.format("@Shadow field %s.%s has a shadow prefix. This is not allowed.", mixinTargetContext, fieldNode.name));
        }
        if (!"super$".equals(fieldNode.name)) {
            return true;
        }
        if (fieldNode.access != 2) {
            throw new InvalidMixinException(this.mixin, String.format("Imaginary super field %s.%s must be private and non-final", mixinTargetContext, fieldNode.name));
        }
        if (!fieldNode.desc.equals("L" + this.mixin.getClassRef() + ";")) {
            throw new InvalidMixinException(this.mixin, String.format("Imaginary super field %s.%s must have the same type as the parent mixin (%s)", mixinTargetContext, fieldNode.name, this.mixin.getClassName()));
        }
        return false;
    }
    
    protected void transform(final MixinTargetContext mixinTargetContext) {
        final Iterator<MethodNode> iterator = this.classNode.methods.iterator();
        while (iterator.hasNext()) {
            for (final AbstractInsnNode abstractInsnNode : iterator.next().instructions) {
                if (abstractInsnNode instanceof MethodInsnNode) {
                    this.transformMethod((MethodInsnNode)abstractInsnNode);
                }
                else {
                    if (!(abstractInsnNode instanceof FieldInsnNode)) {
                        continue;
                    }
                    this.transformField((FieldInsnNode)abstractInsnNode);
                }
            }
        }
    }
    
    protected void transformMethod(final MethodInsnNode methodInsnNode) {
        final Profiler.Section begin = this.profiler.begin("meta");
        final ClassInfo forName = ClassInfo.forName(methodInsnNode.owner);
        if (forName == null) {
            throw new RuntimeException(new ClassNotFoundException(methodInsnNode.owner.replace('/', '.')));
        }
        final ClassInfo.Method methodInHierarchy = forName.findMethodInHierarchy(methodInsnNode, ClassInfo.SearchType.ALL_CLASSES, 2);
        begin.end();
        if (methodInHierarchy != null && methodInHierarchy.isRenamed()) {
            methodInsnNode.name = methodInHierarchy.getName();
        }
    }
    
    protected void transformField(final FieldInsnNode fieldInsnNode) {
        final Profiler.Section begin = this.profiler.begin("meta");
        final ClassInfo forName = ClassInfo.forName(fieldInsnNode.owner);
        if (forName == null) {
            throw new RuntimeException(new ClassNotFoundException(fieldInsnNode.owner.replace('/', '.')));
        }
        final ClassInfo.Field field = forName.findField(fieldInsnNode, 2);
        begin.end();
        if (field != null && field.isRenamed()) {
            fieldInsnNode.name = field.getName();
        }
    }
    
    protected static String getDynamicInfo(final MethodNode methodNode) {
        return getDynamicInfo("Method", Annotations.getInvisible(methodNode, Dynamic.class));
    }
    
    protected static String getDynamicInfo(final FieldNode fieldNode) {
        return getDynamicInfo("Field", Annotations.getInvisible(fieldNode, Dynamic.class));
    }
    
    private static String getDynamicInfo(final String s, final AnnotationNode annotationNode) {
        String s2 = Strings.nullToEmpty((String)Annotations.getValue(annotationNode));
        final Type type = Annotations.getValue(annotationNode, "mixin");
        if (type != null) {
            s2 = String.format("{%s} %s", type.getClassName(), s2).trim();
        }
        return (s2.length() > 0) ? String.format(" %s is @Dynamic(%s)", s, s2) : "";
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
    
    enum SpecialMethod
    {
        MERGE(true), 
        OVERWRITE(true, (Class<? extends Annotation>)Overwrite.class), 
        SHADOW(false, (Class<? extends Annotation>)Shadow.class), 
        ACCESSOR(false, (Class<? extends Annotation>)Accessor.class), 
        INVOKER(false, (Class<? extends Annotation>)Invoker.class);
        
        final boolean isOverwrite;
        final Class<? extends Annotation> annotation;
        final String description;
        private static final SpecialMethod[] $VALUES;
        
        public static SpecialMethod[] values() {
            return SpecialMethod.$VALUES.clone();
        }
        
        public static SpecialMethod valueOf(final String s) {
            return Enum.valueOf(SpecialMethod.class, s);
        }
        
        private SpecialMethod(final boolean isOverwrite, final Class<? extends Annotation> annotation) {
            this.isOverwrite = isOverwrite;
            this.annotation = annotation;
            this.description = "@" + Bytecode.getSimpleName(annotation);
        }
        
        private SpecialMethod(final boolean isOverwrite) {
            this.isOverwrite = isOverwrite;
            this.annotation = null;
            this.description = "overwrite";
        }
        
        @Override
        public String toString() {
            return this.description;
        }
        
        static {
            $VALUES = new SpecialMethod[] { SpecialMethod.MERGE, SpecialMethod.OVERWRITE, SpecialMethod.SHADOW, SpecialMethod.ACCESSOR, SpecialMethod.INVOKER };
        }
    }
}
