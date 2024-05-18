package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.mixin.*;
import java.util.*;

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
