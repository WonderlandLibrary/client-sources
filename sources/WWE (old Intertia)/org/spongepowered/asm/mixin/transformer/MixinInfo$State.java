package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import java.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.lib.tree.*;

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
