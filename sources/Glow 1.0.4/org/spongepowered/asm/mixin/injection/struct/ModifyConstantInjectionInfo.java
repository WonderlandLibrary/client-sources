package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.lib.tree.*;
import com.google.common.collect.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.points.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.code.*;
import org.spongepowered.asm.mixin.injection.invoke.*;
import com.google.common.base.*;
import org.spongepowered.asm.mixin.injection.*;

public class ModifyConstantInjectionInfo extends InjectionInfo
{
    private static final String CONSTANT_ANNOTATION_CLASS;
    
    public ModifyConstantInjectionInfo(final MixinTargetContext mixinTargetContext, final MethodNode methodNode, final AnnotationNode annotationNode) {
        super(mixinTargetContext, methodNode, annotationNode, "constant");
    }
    
    @Override
    protected List<AnnotationNode> readInjectionPoints(final String s) {
        List<AnnotationNode> list = super.readInjectionPoints(s);
        if (list.isEmpty()) {
            final AnnotationNode element = new AnnotationNode(ModifyConstantInjectionInfo.CONSTANT_ANNOTATION_CLASS);
            element.visit("log", Boolean.TRUE);
            list = ImmutableList.of(element);
        }
        return list;
    }
    
    @Override
    protected void parseInjectionPoints(final List<AnnotationNode> list) {
        final Type returnType = Type.getReturnType(this.method.desc);
        final Iterator<AnnotationNode> iterator = list.iterator();
        while (iterator.hasNext()) {
            this.injectionPoints.add(new BeforeConstant(this.getContext(), iterator.next(), returnType.getDescriptor()));
        }
    }
    
    @Override
    protected Injector parseInjector(final AnnotationNode annotationNode) {
        return new ModifyConstantInjector(this);
    }
    
    @Override
    protected String getDescription() {
        return "Constant modifier method";
    }
    
    @Override
    public String getSliceId(final String s) {
        return Strings.nullToEmpty(s);
    }
    
    static {
        CONSTANT_ANNOTATION_CLASS = Constant.class.getName().replace('.', '/');
    }
}
