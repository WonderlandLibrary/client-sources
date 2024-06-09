package org.spongepowered.asm.mixin.injection.invoke;

import java.lang.annotation.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.injection.code.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.mixin.injection.points.*;
import java.util.*;
import org.spongepowered.asm.util.*;
import com.google.common.primitives.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.lib.*;
import com.google.common.base.*;
import org.spongepowered.asm.lib.tree.*;
import com.google.common.collect.*;

public class RedirectInjector extends InvokeInjector
{
    private static final String KEY_NOMINATORS = "nominators";
    private static final String KEY_FUZZ = "fuzz";
    private static final String KEY_OPCODE = "opcode";
    protected Meta meta;
    private Map<BeforeNew, ConstructorRedirectData> ctorRedirectors;
    
    public RedirectInjector(final InjectionInfo injectionInfo) {
        this(injectionInfo, "@Redirect");
    }
    
    protected RedirectInjector(final InjectionInfo injectionInfo, final String s) {
        super(injectionInfo, s);
        this.ctorRedirectors = new HashMap<BeforeNew, ConstructorRedirectData>();
        this.meta = new Meta(injectionInfo.getContext().getPriority(), Annotations.getVisible(this.methodNode, Final.class) != null, this.info.toString(), this.methodNode.desc);
    }
    
    @Override
    protected void checkTarget(final Target target) {
    }
    
    @Override
    protected void addTargetNode(final Target target, final List<InjectionNodes.InjectionNode> list, final AbstractInsnNode abstractInsnNode, final Set<InjectionPoint> set) {
        final InjectionNodes.InjectionNode injectionNode = target.getInjectionNode(abstractInsnNode);
        ConstructorRedirectData ctorRedirect = null;
        int fuzzFactor = 8;
        int arrayOpcode = 0;
        if (injectionNode != null) {
            final Meta meta = injectionNode.getDecoration("redirector");
            if (meta != null && meta.getOwner() != this) {
                if (meta.priority >= this.meta.priority) {
                    Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[] { this.annotationType, this.info, this.meta.priority, meta.name, meta.priority });
                    return;
                }
                if (meta.isFinal) {
                    throw new InvalidInjectionException(this.info, String.format("%s conflict: %s failed because target was already remapped by %s", this.annotationType, this, meta.name));
                }
            }
        }
        for (final InjectionPoint injectionPoint : set) {
            if (injectionPoint instanceof BeforeNew) {
                ctorRedirect = this.getCtorRedirect((BeforeNew)injectionPoint);
                ctorRedirect.wildcard = !((BeforeNew)injectionPoint).hasDescriptor();
            }
            else {
                if (!(injectionPoint instanceof BeforeFieldAccess)) {
                    continue;
                }
                final BeforeFieldAccess beforeFieldAccess = (BeforeFieldAccess)injectionPoint;
                fuzzFactor = beforeFieldAccess.getFuzzFactor();
                arrayOpcode = beforeFieldAccess.getArrayOpcode();
            }
        }
        final InjectionNodes.InjectionNode addInjectionNode = target.addInjectionNode(abstractInsnNode);
        addInjectionNode.decorate("redirector", this.meta);
        addInjectionNode.decorate("nominators", set);
        if (abstractInsnNode instanceof TypeInsnNode && abstractInsnNode.getOpcode() == 187) {
            addInjectionNode.decorate("ctor", ctorRedirect);
        }
        else {
            addInjectionNode.decorate("fuzz", fuzzFactor);
            addInjectionNode.decorate("opcode", arrayOpcode);
        }
        list.add(addInjectionNode);
    }
    
    private ConstructorRedirectData getCtorRedirect(final BeforeNew beforeNew) {
        ConstructorRedirectData constructorRedirectData = this.ctorRedirectors.get(beforeNew);
        if (constructorRedirectData == null) {
            constructorRedirectData = new ConstructorRedirectData();
            this.ctorRedirectors.put(beforeNew, constructorRedirectData);
        }
        return constructorRedirectData;
    }
    
    @Override
    protected void inject(final Target target, final InjectionNodes.InjectionNode injectionNode) {
        if (!this.preInject(injectionNode)) {
            return;
        }
        if (injectionNode.isReplaced()) {
            throw new UnsupportedOperationException("Redirector target failure for " + this.info);
        }
        if (injectionNode.getCurrentTarget() instanceof MethodInsnNode) {
            this.checkTargetForNode(target, injectionNode);
            this.injectAtInvoke(target, injectionNode);
            return;
        }
        if (injectionNode.getCurrentTarget() instanceof FieldInsnNode) {
            this.checkTargetForNode(target, injectionNode);
            this.injectAtFieldAccess(target, injectionNode);
            return;
        }
        if (!(injectionNode.getCurrentTarget() instanceof TypeInsnNode) || injectionNode.getCurrentTarget().getOpcode() != 187) {
            throw new InvalidInjectionException(this.info, String.format("%s annotation on is targetting an invalid insn in %s in %s", this.annotationType, target, this));
        }
        if (!this.isStatic && target.isStatic) {
            throw new InvalidInjectionException(this.info, String.format("non-static callback method %s has a static target which is not supported", this));
        }
        this.injectAtConstructor(target, injectionNode);
    }
    
    protected boolean preInject(final InjectionNodes.InjectionNode injectionNode) {
        final Meta meta = injectionNode.getDecoration("redirector");
        if (meta.getOwner() != this) {
            Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[] { this.annotationType, this.info, this.meta.priority, meta.name, meta.priority });
            return false;
        }
        return true;
    }
    
    @Override
    protected void postInject(final Target target, final InjectionNodes.InjectionNode injectionNode) {
        super.postInject(target, injectionNode);
        if (injectionNode.getOriginalTarget() instanceof TypeInsnNode && injectionNode.getOriginalTarget().getOpcode() == 187) {
            final ConstructorRedirectData constructorRedirectData = injectionNode.getDecoration("ctor");
            if (constructorRedirectData.wildcard && constructorRedirectData.injected == 0) {
                throw new InvalidInjectionException(this.info, String.format("%s ctor invocation was not found in %s", this.annotationType, target));
            }
        }
    }
    
    @Override
    protected void injectAtInvoke(final Target target, final InjectionNodes.InjectionNode injectionNode) {
        final RedirectedInvoke redirectedInvoke = new RedirectedInvoke(target, (MethodInsnNode)injectionNode.getCurrentTarget());
        this.validateParams(redirectedInvoke);
        final InsnList list = new InsnList();
        int n = Bytecode.getArgsSize(redirectedInvoke.locals) + 1;
        int n2 = 1;
        int[] array = this.storeArgs(target, redirectedInvoke.locals, list, 0);
        if (redirectedInvoke.captureTargetArgs) {
            final int argsSize = Bytecode.getArgsSize(target.arguments);
            n += argsSize;
            n2 += argsSize;
            array = Ints.concat(new int[][] { array, target.getArgIndices() });
        }
        target.replaceNode(redirectedInvoke.node, this.invokeHandlerWithArgs(this.methodArgs, list, array), list);
        target.addToLocals(n);
        target.addToStack(n2);
    }
    
    protected void validateParams(final RedirectedInvoke redirectedInvoke) {
        int n = this.methodArgs.length;
        final String format = String.format("%s handler method %s", this.annotationType, this);
        if (!redirectedInvoke.returnType.equals(this.returnType)) {
            throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Expected return type %s found %s", format, this.returnType, redirectedInvoke.returnType));
        }
        for (int i = 0; i < n; ++i) {
            if (i >= this.methodArgs.length) {
                throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Not enough arguments found for capture of target method args, expected %d but found %d", format, n, this.methodArgs.length));
            }
            final Type type = this.methodArgs[i];
            Type type2;
            if (i < redirectedInvoke.locals.length) {
                type2 = redirectedInvoke.locals[i];
            }
            else {
                redirectedInvoke.captureTargetArgs = true;
                n = Math.max(n, redirectedInvoke.locals.length + redirectedInvoke.target.arguments.length);
                final int n2 = i - redirectedInvoke.locals.length;
                if (n2 >= redirectedInvoke.target.arguments.length) {
                    throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Found unexpected additional target argument with type %s at index %d", format, type, i));
                }
                type2 = redirectedInvoke.target.arguments[n2];
            }
            final AnnotationNode invisibleParameter = Annotations.getInvisibleParameter(this.methodNode, Coerce.class, i);
            if (type.equals(type2)) {
                if (invisibleParameter != null && this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
                    Injector.logger.warn("Redundant @Coerce on {} argument {}, {} is identical to {}", new Object[] { format, i, type2, type });
                }
            }
            else {
                final boolean canCoerce = Injector.canCoerce(type, type2);
                if (invisibleParameter == null) {
                    throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Found unexpected argument type %s at index %d, expected %s", format, type, i, type2));
                }
                if (!canCoerce) {
                    throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Cannot @Coerce argument type %s at index %d to %s", format, type2, i, type));
                }
            }
        }
    }
    
    private void injectAtFieldAccess(final Target target, final InjectionNodes.InjectionNode injectionNode) {
        final FieldInsnNode fieldInsnNode = (FieldInsnNode)injectionNode.getCurrentTarget();
        final int opcode = fieldInsnNode.getOpcode();
        final Type type = Type.getType("L" + fieldInsnNode.owner + ";");
        final Type type2 = Type.getType(fieldInsnNode.desc);
        final int n = (type2.getSort() == 9) ? type2.getDimensions() : 0;
        final int n2 = (this.returnType.getSort() == 9) ? this.returnType.getDimensions() : 0;
        if (n2 > n) {
            throw new InvalidInjectionException(this.info, "Dimensionality of handler method is greater than target array on " + this);
        }
        if (n2 == 0 && n > 0) {
            this.injectAtArrayField(target, fieldInsnNode, opcode, type, type2, injectionNode.getDecoration("fuzz"), injectionNode.getDecoration("opcode"));
        }
        else {
            this.injectAtScalarField(target, fieldInsnNode, opcode, type, type2);
        }
    }
    
    private void injectAtArrayField(final Target target, final FieldInsnNode fieldInsnNode, final int n, final Type type, final Type type2, final int n2, int opcode) {
        final Type elementType = type2.getElementType();
        if (n != 178 && n != 180) {
            throw new InvalidInjectionException(this.info, String.format("Unspported opcode %s for array access %s", Bytecode.getOpcodeName(n), this.info));
        }
        if (this.returnType.getSort() != 0) {
            if (opcode != 190) {
                opcode = elementType.getOpcode(46);
            }
            this.injectAtGetArray(target, fieldInsnNode, BeforeFieldAccess.findArrayNode(target.insns, fieldInsnNode, opcode, n2), type, type2);
        }
        else {
            this.injectAtSetArray(target, fieldInsnNode, BeforeFieldAccess.findArrayNode(target.insns, fieldInsnNode, elementType.getOpcode(79), n2), type, type2);
        }
    }
    
    private void injectAtGetArray(final Target target, final FieldInsnNode fieldInsnNode, final AbstractInsnNode abstractInsnNode, final Type type, final Type type2) {
        this.injectArrayRedirect(target, fieldInsnNode, abstractInsnNode, this.checkDescriptor(getGetArrayHandlerDescriptor(abstractInsnNode, this.returnType, type2), target, "array getter"), "array getter");
    }
    
    private void injectAtSetArray(final Target target, final FieldInsnNode fieldInsnNode, final AbstractInsnNode abstractInsnNode, final Type type, final Type type2) {
        this.injectArrayRedirect(target, fieldInsnNode, abstractInsnNode, this.checkDescriptor(Bytecode.generateDescriptor(null, (Object[])getArrayArgs(type2, 1, type2.getElementType())), target, "array setter"), "array setter");
    }
    
    public void injectArrayRedirect(final Target target, final FieldInsnNode fieldInsnNode, final AbstractInsnNode abstractInsnNode, final boolean b, final String s) {
        if (abstractInsnNode == null) {
            throw new InvalidInjectionException(this.info, String.format("Array element %s on %s could not locate a matching %s instruction in %s. %s", this.annotationType, this, s, target, ""));
        }
        if (!this.isStatic) {
            target.insns.insertBefore(fieldInsnNode, new VarInsnNode(25, 0));
            target.addToStack(1);
        }
        final InsnList list = new InsnList();
        if (b) {
            this.pushArgs(target.arguments, list, target.getArgIndices(), 0, target.arguments.length);
            target.addToStack(Bytecode.getArgsSize(target.arguments));
        }
        target.replaceNode(abstractInsnNode, this.invokeHandler(list), list);
    }
    
    public void injectAtScalarField(final Target target, final FieldInsnNode fieldInsnNode, final int n, final Type type, final Type type2) {
        final InsnList list = new InsnList();
        AbstractInsnNode abstractInsnNode;
        if (n == 178 || n == 180) {
            abstractInsnNode = this.injectAtGetField(list, target, fieldInsnNode, n == 178, type, type2);
        }
        else {
            if (n != 179 && n != 181) {
                throw new InvalidInjectionException(this.info, String.format("Unspported opcode %s for %s", Bytecode.getOpcodeName(n), this.info));
            }
            abstractInsnNode = this.injectAtPutField(list, target, fieldInsnNode, n == 179, type, type2);
        }
        target.replaceNode(fieldInsnNode, abstractInsnNode, list);
    }
    
    private AbstractInsnNode injectAtGetField(final InsnList list, final Target target, final FieldInsnNode fieldInsnNode, final boolean b, final Type type, final Type type2) {
        final boolean checkDescriptor = this.checkDescriptor(b ? Bytecode.generateDescriptor(type2, new Object[0]) : Bytecode.generateDescriptor(type2, type), target, "getter");
        if (!this.isStatic) {
            list.add(new VarInsnNode(25, 0));
            if (!b) {
                list.add(new InsnNode(95));
            }
        }
        if (checkDescriptor) {
            this.pushArgs(target.arguments, list, target.getArgIndices(), 0, target.arguments.length);
            target.addToStack(Bytecode.getArgsSize(target.arguments));
        }
        target.addToStack(this.isStatic ? 0 : 1);
        return this.invokeHandler(list);
    }
    
    private AbstractInsnNode injectAtPutField(final InsnList list, final Target target, final FieldInsnNode fieldInsnNode, final boolean b, final Type type, final Type type2) {
        final boolean checkDescriptor = this.checkDescriptor(b ? Bytecode.generateDescriptor(null, type2) : Bytecode.generateDescriptor(null, type, type2), target, "setter");
        if (!this.isStatic) {
            if (b) {
                list.add(new VarInsnNode(25, 0));
                list.add(new InsnNode(95));
            }
            else {
                final int allocateLocals = target.allocateLocals(type2.getSize());
                list.add(new VarInsnNode(type2.getOpcode(54), allocateLocals));
                list.add(new VarInsnNode(25, 0));
                list.add(new InsnNode(95));
                list.add(new VarInsnNode(type2.getOpcode(21), allocateLocals));
            }
        }
        if (checkDescriptor) {
            this.pushArgs(target.arguments, list, target.getArgIndices(), 0, target.arguments.length);
            target.addToStack(Bytecode.getArgsSize(target.arguments));
        }
        target.addToStack((!this.isStatic && !b) ? 1 : 0);
        return this.invokeHandler(list);
    }
    
    protected boolean checkDescriptor(final String s, final Target target, final String s2) {
        if (this.methodNode.desc.equals(s)) {
            return false;
        }
        final int index = s.indexOf(41);
        if (this.methodNode.desc.equals(String.format("%s%s%s", s.substring(0, index), Joiner.on("").join((Object[])target.arguments), s.substring(index)))) {
            return true;
        }
        throw new InvalidInjectionException(this.info, String.format("%s method %s %s has an invalid signature. Expected %s but found %s", this.annotationType, s2, this, s, this.methodNode.desc));
    }
    
    protected void injectAtConstructor(final Target target, final InjectionNodes.InjectionNode injectionNode) {
        final ConstructorRedirectData constructorRedirectData = injectionNode.getDecoration("ctor");
        if (constructorRedirectData == null) {
            throw new InvalidInjectionException(this.info, String.format("%s ctor redirector has no metadata, the injector failed a preprocessing phase", this.annotationType));
        }
        final TypeInsnNode typeInsnNode = (TypeInsnNode)injectionNode.getCurrentTarget();
        final AbstractInsnNode value = target.get(target.indexOf(typeInsnNode) + 1);
        final MethodInsnNode initNode = target.findInitNodeFor(typeInsnNode);
        if (initNode != null) {
            final boolean b = value.getOpcode() == 89;
            final String replace = initNode.desc.replace(")V", ")L" + typeInsnNode.desc + ";");
            boolean checkDescriptor;
            try {
                checkDescriptor = this.checkDescriptor(replace, target, "constructor");
            }
            catch (InvalidInjectionException ex) {
                if (!constructorRedirectData.wildcard) {
                    throw ex;
                }
                return;
            }
            if (b) {
                target.removeNode(value);
            }
            if (this.isStatic) {
                target.removeNode(typeInsnNode);
            }
            else {
                target.replaceNode(typeInsnNode, new VarInsnNode(25, 0));
            }
            final InsnList list = new InsnList();
            if (checkDescriptor) {
                this.pushArgs(target.arguments, list, target.getArgIndices(), 0, target.arguments.length);
                target.addToStack(Bytecode.getArgsSize(target.arguments));
            }
            this.invokeHandler(list);
            if (b) {
                final LabelNode labelNode = new LabelNode();
                list.add(new InsnNode(89));
                list.add(new JumpInsnNode(199, labelNode));
                this.throwException(list, "java/lang/NullPointerException", String.format("%s constructor handler %s returned null for %s", this.annotationType, this, typeInsnNode.desc.replace('/', '.')));
                list.add(labelNode);
                target.addToStack(1);
            }
            else {
                list.add(new InsnNode(87));
            }
            target.replaceNode(initNode, list);
            final ConstructorRedirectData constructorRedirectData2 = constructorRedirectData;
            ++constructorRedirectData2.injected;
            return;
        }
        if (!constructorRedirectData.wildcard) {
            throw new InvalidInjectionException(this.info, String.format("%s ctor invocation was not found in %s", this.annotationType, target));
        }
    }
    
    private static String getGetArrayHandlerDescriptor(final AbstractInsnNode abstractInsnNode, final Type type, final Type type2) {
        if (abstractInsnNode != null && abstractInsnNode.getOpcode() == 190) {
            return Bytecode.generateDescriptor(Type.INT_TYPE, (Object[])getArrayArgs(type2, 0, new Type[0]));
        }
        return Bytecode.generateDescriptor(type, (Object[])getArrayArgs(type2, 1, new Type[0]));
    }
    
    private static Type[] getArrayArgs(final Type type, final int n, final Type... array) {
        final int n2 = type.getDimensions() + n;
        final Type[] array2 = new Type[n2 + array.length];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = ((i == 0) ? type : ((i < n2) ? Type.INT_TYPE : array[n2 - i]));
        }
        return array2;
    }
    
    class Meta
    {
        public static final String KEY = "redirector";
        final int priority;
        final boolean isFinal;
        final String name;
        final String desc;
        final RedirectInjector this$0;
        
        public Meta(final RedirectInjector this$0, final int priority, final boolean isFinal, final String name, final String desc) {
            this.this$0 = this$0;
            super();
            this.priority = priority;
            this.isFinal = isFinal;
            this.name = name;
            this.desc = desc;
        }
        
        RedirectInjector getOwner() {
            return this.this$0;
        }
    }
    
    static class ConstructorRedirectData
    {
        public static final String KEY = "ctor";
        public boolean wildcard;
        public int injected;
        
        ConstructorRedirectData() {
            super();
            this.wildcard = false;
            this.injected = 0;
        }
    }
    
    static class RedirectedInvoke
    {
        final Target target;
        final MethodInsnNode node;
        final Type returnType;
        final Type[] args;
        final Type[] locals;
        boolean captureTargetArgs;
        
        RedirectedInvoke(final Target target, final MethodInsnNode node) {
            super();
            this.captureTargetArgs = false;
            this.target = target;
            this.node = node;
            this.returnType = Type.getReturnType(node.desc);
            this.args = Type.getArgumentTypes(node.desc);
            this.locals = (Type[])((node.getOpcode() == 184) ? this.args : ObjectArrays.concat((Object)Type.getType("L" + node.owner + ";"), (Object[])this.args));
        }
    }
}
