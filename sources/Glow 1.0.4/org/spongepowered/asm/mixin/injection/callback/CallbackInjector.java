package org.spongepowered.asm.mixin.injection.callback;

import org.spongepowered.asm.mixin.injection.code.*;
import org.spongepowered.asm.mixin.injection.points.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import com.google.common.base.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;

public class CallbackInjector extends Injector
{
    private final boolean cancellable;
    private final LocalCapture localCapture;
    private final String identifier;
    private final Map<Integer, String> ids;
    private int totalInjections;
    private int callbackInfoVar;
    private String lastId;
    private String lastDesc;
    private Target lastTarget;
    private String callbackInfoClass;
    
    public CallbackInjector(final InjectionInfo injectionInfo, final boolean cancellable, final LocalCapture localCapture, final String identifier) {
        super(injectionInfo);
        this.ids = new HashMap<Integer, String>();
        this.totalInjections = 0;
        this.callbackInfoVar = -1;
        this.cancellable = cancellable;
        this.localCapture = localCapture;
        this.identifier = identifier;
    }
    
    @Override
    protected void sanityCheck(final Target target, final List<InjectionPoint> list) {
        super.sanityCheck(target, list);
        if (target.isStatic != this.isStatic) {
            throw new InvalidInjectionException(this.info, "'static' modifier of callback method does not match target in " + this);
        }
        if ("<init>".equals(target.method.name)) {
            for (final InjectionPoint injectionPoint : list) {
                if (!injectionPoint.getClass().equals(BeforeReturn.class)) {
                    throw new InvalidInjectionException(this.info, "Found injection point type " + injectionPoint.getClass().getSimpleName() + " targetting a ctor in " + this + ". Only RETURN allowed for a ctor target");
                }
            }
        }
    }
    
    @Override
    protected void addTargetNode(final Target target, final List<InjectionNodes.InjectionNode> list, final AbstractInsnNode abstractInsnNode, final Set<InjectionPoint> set) {
        final InjectionNodes.InjectionNode addInjectionNode = target.addInjectionNode(abstractInsnNode);
        final Iterator<InjectionPoint> iterator = set.iterator();
        while (iterator.hasNext()) {
            final String id = iterator.next().getId();
            if (Strings.isNullOrEmpty(id)) {
                continue;
            }
            final String s = this.ids.get(addInjectionNode.getId());
            if (s != null && !s.equals(id)) {
                Injector.logger.warn("Conflicting id for {} insn in {}, found id {} on {}, previously defined as {}", new Object[] { Bytecode.getOpcodeName(abstractInsnNode), target.toString(), id, this.info, s });
                break;
            }
            this.ids.put(addInjectionNode.getId(), id);
        }
        list.add(addInjectionNode);
        ++this.totalInjections;
    }
    
    @Override
    protected void inject(final Target target, final InjectionNodes.InjectionNode injectionNode) {
        LocalVariableNode[] locals = null;
        if (this.localCapture.isCaptureLocals() || this.localCapture.isPrintLocals()) {
            locals = Locals.getLocalsAt(this.classNode, target.method, injectionNode.getCurrentTarget());
        }
        this.inject(new Callback(this.methodNode, target, injectionNode, locals, this.localCapture.isCaptureLocals()));
    }
    
    private void inject(final Callback callback) {
        if (this.localCapture.isPrintLocals()) {
            this.printLocals(callback);
            this.info.addCallbackInvocation(this.methodNode);
            return;
        }
        MethodNode methodNode = this.methodNode;
        if (!callback.checkDescriptor(this.methodNode.desc)) {
            if (this.info.getTargets().size() > 1) {
                return;
            }
            if (callback.canCaptureLocals) {
                final MethodNode method = Bytecode.findMethod(this.classNode, this.methodNode.name, callback.getDescriptor());
                if (method != null && Annotations.getVisible(method, Surrogate.class) != null) {
                    methodNode = method;
                }
                else {
                    final String generateBadLVTMessage = this.generateBadLVTMessage(callback);
                    switch (this.localCapture) {
                        case CAPTURE_FAILEXCEPTION: {
                            Injector.logger.error("Injection error: {}", new Object[] { generateBadLVTMessage });
                            methodNode = this.generateErrorMethod(callback, "org/spongepowered/asm/mixin/injection/throwables/InjectionError", generateBadLVTMessage);
                            break;
                        }
                        case CAPTURE_FAILSOFT: {
                            Injector.logger.warn("Injection warning: {}", new Object[] { generateBadLVTMessage });
                            return;
                        }
                        default: {
                            Injector.logger.error("Critical injection failure: {}", new Object[] { generateBadLVTMessage });
                            throw new InjectionError(generateBadLVTMessage);
                        }
                    }
                }
            }
            else {
                if (callback.checkDescriptor(this.methodNode.desc.replace("Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;", "Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;"))) {
                    throw new InvalidInjectionException(this.info, "Invalid descriptor on " + this.info + "! CallbackInfoReturnable is required!");
                }
                final MethodNode method2 = Bytecode.findMethod(this.classNode, this.methodNode.name, callback.getDescriptor());
                if (method2 == null || Annotations.getVisible(method2, Surrogate.class) == null) {
                    throw new InvalidInjectionException(this.info, "Invalid descriptor on " + this.info + "! Expected " + callback.getDescriptor() + " but found " + this.methodNode.desc);
                }
                methodNode = method2;
            }
        }
        this.dupReturnValue(callback);
        if (this.cancellable || this.totalInjections > 1) {
            this.createCallbackInfo(callback, true);
        }
        this.invokeCallback(callback, methodNode);
        this.injectCancellationCode(callback);
        callback.inject();
        this.info.notifyInjected(callback.target);
    }
    
    private String generateBadLVTMessage(final Callback callback) {
        return String.format("LVT in %s has incompatible changes at opcode %d in callback %s.\nExpected: %s\n   Found: %s", callback.target, callback.target.indexOf(callback.node), this, summariseLocals(this.methodNode.desc, callback.target.arguments.length + 1), summariseLocals(callback.getDescriptorWithAllLocals(), callback.frameSize));
    }
    
    private MethodNode generateErrorMethod(final Callback callback, final String s, final String s2) {
        final MethodNode addMethod = this.info.addMethod(this.methodNode.access, this.methodNode.name + "$missing", callback.getDescriptor());
        addMethod.maxLocals = Bytecode.getFirstNonArgLocalIndex(Type.getArgumentTypes(callback.getDescriptor()), !this.isStatic);
        addMethod.maxStack = 3;
        final InsnList instructions = addMethod.instructions;
        instructions.add(new TypeInsnNode(187, s));
        instructions.add(new InsnNode(89));
        instructions.add(new LdcInsnNode(s2));
        instructions.add(new MethodInsnNode(183, s, "<init>", "(Ljava/lang/String;)V", false));
        instructions.add(new InsnNode(191));
        return addMethod;
    }
    
    private void printLocals(final Callback callback) {
        final Type[] argumentTypes = Type.getArgumentTypes(callback.getDescriptorWithAllLocals());
        final SignaturePrinter signaturePrinter = new SignaturePrinter(callback.target.method, callback.argNames);
        final SignaturePrinter signaturePrinter2 = new SignaturePrinter(this.methodNode.name, callback.target.returnType, argumentTypes, callback.argNames);
        signaturePrinter2.setModifiers(this.methodNode);
        final PrettyPrinter prettyPrinter = new PrettyPrinter();
        prettyPrinter.kv("Target Class", (Object)this.classNode.name.replace('/', '.'));
        prettyPrinter.kv("Target Method", signaturePrinter);
        prettyPrinter.kv("Target Max LOCALS", callback.target.getMaxLocals());
        prettyPrinter.kv("Initial Frame Size", callback.frameSize);
        prettyPrinter.kv("Callback Name", (Object)this.methodNode.name);
        prettyPrinter.kv("Instruction", "%s %s", callback.node.getClass().getSimpleName(), Bytecode.getOpcodeName(callback.node.getCurrentTarget().getOpcode()));
        prettyPrinter.hr();
        if (callback.locals.length > callback.frameSize) {
            prettyPrinter.add("  %s  %20s  %s", "LOCAL", "TYPE", "NAME");
            for (int i = 0; i < callback.locals.length; ++i) {
                final String s = (i == callback.frameSize) ? ">" : " ";
                if (callback.locals[i] != null) {
                    prettyPrinter.add("%s [%3d]  %20s  %-50s %s", s, i, SignaturePrinter.getTypeName(callback.localTypes[i], false), meltSnowman(i, callback.locals[i].name), (i >= callback.frameSize) ? "<capture>" : "");
                }
                else {
                    prettyPrinter.add("%s [%3d]  %20s", s, i, (i > 0 && callback.localTypes[i - 1] != null && callback.localTypes[i - 1].getSize() > 1) ? "<top>" : "-");
                }
            }
            prettyPrinter.hr();
        }
        prettyPrinter.add().add("/**").add(" * Expected callback signature").add(" * /");
        prettyPrinter.add("%s {", signaturePrinter2);
        prettyPrinter.add("    // Method body").add("}").add().print(System.err);
    }
    
    private void createCallbackInfo(final Callback callback, final boolean b) {
        if (callback.target != this.lastTarget) {
            this.lastId = null;
            this.lastDesc = null;
        }
        this.lastTarget = callback.target;
        final String identifier = this.getIdentifier(callback);
        final String callbackInfoConstructorDescriptor = callback.getCallbackInfoConstructorDescriptor();
        if (identifier.equals(this.lastId) && callbackInfoConstructorDescriptor.equals(this.lastDesc) && !callback.isAtReturn && !this.cancellable) {
            return;
        }
        this.instanceCallbackInfo(callback, identifier, callbackInfoConstructorDescriptor, b);
    }
    
    private void loadOrCreateCallbackInfo(final Callback callback) {
        if (this.cancellable || this.totalInjections > 1) {
            callback.add(new VarInsnNode(25, this.callbackInfoVar), false, true);
        }
        else {
            this.createCallbackInfo(callback, false);
        }
    }
    
    private void dupReturnValue(final Callback callback) {
        if (!callback.isAtReturn) {
            return;
        }
        callback.add(new InsnNode(89));
        callback.add(new VarInsnNode(callback.target.returnType.getOpcode(54), callback.marshalVar()));
    }
    
    protected void instanceCallbackInfo(final Callback callback, final String lastId, final String lastDesc, final boolean b) {
        this.lastId = lastId;
        this.lastDesc = lastDesc;
        this.callbackInfoVar = callback.marshalVar();
        this.callbackInfoClass = callback.target.getCallbackInfoClass();
        final boolean b2 = b && this.totalInjections > 1 && !callback.isAtReturn && !this.cancellable;
        callback.add(new TypeInsnNode(187, this.callbackInfoClass), true, !b, b2);
        callback.add(new InsnNode(89), true, true, b2);
        callback.add(new LdcInsnNode(lastId), true, !b, b2);
        callback.add(new InsnNode(this.cancellable ? 4 : 3), true, !b, b2);
        if (callback.isAtReturn) {
            callback.add(new VarInsnNode(callback.target.returnType.getOpcode(21), callback.marshalVar()), true, !b);
            callback.add(new MethodInsnNode(183, this.callbackInfoClass, "<init>", lastDesc, false));
        }
        else {
            callback.add(new MethodInsnNode(183, this.callbackInfoClass, "<init>", lastDesc, false), false, false, b2);
        }
        if (b) {
            callback.target.addLocalVariable(this.callbackInfoVar, "callbackInfo" + this.callbackInfoVar, "L" + this.callbackInfoClass + ";");
            callback.add(new VarInsnNode(58, this.callbackInfoVar), false, false, b2);
        }
    }
    
    private void invokeCallback(final Callback callback, final MethodNode methodNode) {
        if (!this.isStatic) {
            callback.add(new VarInsnNode(25, 0), false, true);
        }
        if (callback.captureArgs()) {
            Bytecode.loadArgs(callback.target.arguments, callback, this.isStatic ? 0 : 1, -1);
        }
        this.loadOrCreateCallbackInfo(callback);
        if (callback.canCaptureLocals) {
            Locals.loadLocals(callback.localTypes, callback, callback.frameSize, callback.extraArgs);
        }
        this.invokeHandler(callback, methodNode);
    }
    
    private String getIdentifier(final Callback callback) {
        final String s = Strings.isNullOrEmpty(this.identifier) ? callback.target.method.name : this.identifier;
        final String string = this.ids.get(callback.node.getId());
        return s + (Strings.isNullOrEmpty(string) ? "" : (":" + string));
    }
    
    protected void injectCancellationCode(final Callback callback) {
        if (!this.cancellable) {
            return;
        }
        callback.add(new VarInsnNode(25, this.callbackInfoVar));
        callback.add(new MethodInsnNode(182, this.callbackInfoClass, CallbackInfo.getIsCancelledMethodName(), CallbackInfo.getIsCancelledMethodSig(), false));
        final LabelNode labelNode = new LabelNode();
        callback.add(new JumpInsnNode(153, labelNode));
        this.injectReturnCode(callback);
        callback.add(labelNode);
    }
    
    protected void injectReturnCode(final Callback callback) {
        if (callback.target.returnType.equals(Type.VOID_TYPE)) {
            callback.add(new InsnNode(177));
        }
        else {
            callback.add(new VarInsnNode(25, callback.marshalVar()));
            callback.add(new MethodInsnNode(182, this.callbackInfoClass, CallbackInfoReturnable.getReturnAccessor(callback.target.returnType), CallbackInfoReturnable.getReturnDescriptor(callback.target.returnType), false));
            if (callback.target.returnType.getSort() == 10) {
                callback.add(new TypeInsnNode(192, callback.target.returnType.getInternalName()));
            }
            callback.add(new InsnNode(callback.target.returnType.getOpcode(172)));
        }
    }
    
    protected boolean isStatic() {
        return this.isStatic;
    }
    
    private static List<String> summariseLocals(final String s, final int n) {
        return summariseLocals(Type.getArgumentTypes(s), n);
    }
    
    private static List<String> summariseLocals(final Type[] array, int i) {
        final ArrayList<String> list = new ArrayList<String>();
        if (array != null) {
            while (i < array.length) {
                if (array[i] != null) {
                    list.add(array[i].toString());
                }
                ++i;
            }
        }
        return list;
    }
    
    static String meltSnowman(final int n, final String s) {
        return (s != null && '\u2603' == s.charAt(0)) ? ("var" + n) : s;
    }
    
    private class Callback extends InsnList
    {
        private final MethodNode handler;
        private final AbstractInsnNode head;
        final Target target;
        final InjectionNodes.InjectionNode node;
        final LocalVariableNode[] locals;
        final Type[] localTypes;
        final int frameSize;
        final int extraArgs;
        final boolean canCaptureLocals;
        final boolean isAtReturn;
        final String desc;
        final String descl;
        final String[] argNames;
        int ctor;
        int invoke;
        private int marshalVar;
        private boolean captureArgs;
        final CallbackInjector this$0;
        
        Callback(final CallbackInjector this$0, final MethodNode handler, final Target target, final InjectionNodes.InjectionNode node, final LocalVariableNode[] locals, final boolean b) {
            this.this$0 = this$0;
            super();
            this.marshalVar = -1;
            this.captureArgs = true;
            this.handler = handler;
            this.target = target;
            this.head = target.insns.getFirst();
            this.node = node;
            this.locals = locals;
            this.localTypes = (Type[])((locals != null) ? new Type[locals.length] : null);
            this.frameSize = Bytecode.getFirstNonArgLocalIndex(target.arguments, !this$0.isStatic());
            List<String> list = null;
            if (locals != null) {
                final int n = this$0.isStatic() ? 0 : 1;
                list = new ArrayList<String>();
                for (int i = 0; i <= locals.length; ++i) {
                    if (i == this.frameSize) {
                        list.add((target.returnType == Type.VOID_TYPE) ? "ci" : "cir");
                    }
                    if (i < locals.length && locals[i] != null) {
                        this.localTypes[i] = Type.getType(locals[i].desc);
                        if (i >= n) {
                            list.add(CallbackInjector.meltSnowman(i, locals[i].name));
                        }
                    }
                }
            }
            this.extraArgs = Math.max(0, Bytecode.getFirstNonArgLocalIndex(this.handler) - (this.frameSize + 1));
            this.argNames = (String[])((list != null) ? ((String[])list.toArray(new String[list.size()])) : null);
            this.canCaptureLocals = (b && locals != null && locals.length > this.frameSize);
            this.isAtReturn = (this.node.getCurrentTarget() instanceof InsnNode && this.isValueReturnOpcode(this.node.getCurrentTarget().getOpcode()));
            this.desc = target.getCallbackDescriptor(this.localTypes, target.arguments);
            this.descl = target.getCallbackDescriptor(true, this.localTypes, target.arguments, this.frameSize, this.extraArgs);
            this.invoke = target.arguments.length + (this.canCaptureLocals ? (this.localTypes.length - this.frameSize) : 0);
        }
        
        private boolean isValueReturnOpcode(final int n) {
            return n >= 172 && n < 177;
        }
        
        String getDescriptor() {
            return this.canCaptureLocals ? this.descl : this.desc;
        }
        
        String getDescriptorWithAllLocals() {
            return this.target.getCallbackDescriptor(true, this.localTypes, this.target.arguments, this.frameSize, 32767);
        }
        
        String getCallbackInfoConstructorDescriptor() {
            return this.isAtReturn ? CallbackInfo.getConstructorDescriptor(this.target.returnType) : CallbackInfo.getConstructorDescriptor();
        }
        
        void add(final AbstractInsnNode abstractInsnNode, final boolean b, final boolean b2) {
            this.add(abstractInsnNode, b, b2, false);
        }
        
        void add(final AbstractInsnNode abstractInsnNode, final boolean b, final boolean b2, final boolean b3) {
            if (b3) {
                this.target.insns.insertBefore(this.head, abstractInsnNode);
            }
            else {
                this.add(abstractInsnNode);
            }
            this.ctor += (b ? 1 : 0);
            this.invoke += (b2 ? 1 : 0);
        }
        
        void inject() {
            this.target.insertBefore(this.node, this);
            this.target.addToStack(Math.max(this.invoke, this.ctor));
        }
        
        boolean checkDescriptor(final String s) {
            if (this.getDescriptor().equals(s)) {
                return true;
            }
            if (this.target.getSimpleCallbackDescriptor().equals(s) && !this.canCaptureLocals) {
                this.captureArgs = false;
                return true;
            }
            final Type[] argumentTypes = Type.getArgumentTypes(s);
            final Type[] argumentTypes2 = Type.getArgumentTypes(this.descl);
            if (argumentTypes.length != argumentTypes2.length) {
                return false;
            }
            for (int i = 0; i < argumentTypes2.length; ++i) {
                final Type type = argumentTypes[i];
                if (!type.equals(argumentTypes2[i])) {
                    if (type.getSort() == 9) {
                        return false;
                    }
                    if (Annotations.getInvisibleParameter(this.handler, Coerce.class, i) == null) {
                        return false;
                    }
                    if (!Injector.canCoerce(argumentTypes[i], argumentTypes2[i])) {
                        return false;
                    }
                }
            }
            return true;
        }
        
        boolean captureArgs() {
            return this.captureArgs;
        }
        
        int marshalVar() {
            if (this.marshalVar < 0) {
                this.marshalVar = this.target.allocateLocal();
            }
            return this.marshalVar;
        }
    }
}
