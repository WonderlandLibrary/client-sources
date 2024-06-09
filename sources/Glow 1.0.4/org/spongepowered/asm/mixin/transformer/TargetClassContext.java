package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.transformer.ext.*;
import org.spongepowered.asm.mixin.struct.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import java.util.*;
import org.spongepowered.asm.mixin.*;
import java.lang.annotation.*;
import org.spongepowered.asm.util.*;
import java.io.*;
import org.spongepowered.asm.lib.tree.*;
import org.apache.logging.log4j.*;

class TargetClassContext extends ClassContext implements ITargetClassContext
{
    private static final Logger logger;
    private final MixinEnvironment env;
    private final Extensions extensions;
    private final String sessionId;
    private final String className;
    private final ClassNode classNode;
    private final ClassInfo classInfo;
    private final SourceMap sourceMap;
    private final ClassSignature signature;
    private final SortedSet<MixinInfo> mixins;
    private final Map<String, Target> targetMethods;
    private final Set<MethodNode> mixinMethods;
    private int nextUniqueMethodIndex;
    private int nextUniqueFieldIndex;
    private boolean applied;
    private boolean forceExport;
    
    TargetClassContext(final MixinEnvironment env, final Extensions extensions, final String sessionId, final String className, final ClassNode classNode, final SortedSet<MixinInfo> mixins) {
        super();
        this.targetMethods = new HashMap<String, Target>();
        this.mixinMethods = new HashSet<MethodNode>();
        this.env = env;
        this.extensions = extensions;
        this.sessionId = sessionId;
        this.className = className;
        this.classNode = classNode;
        this.classInfo = ClassInfo.fromClassNode(classNode);
        this.signature = this.classInfo.getSignature();
        this.mixins = mixins;
        (this.sourceMap = new SourceMap(classNode.sourceFile)).addFile(this.classNode);
    }
    
    @Override
    public String toString() {
        return this.className;
    }
    
    boolean isApplied() {
        return this.applied;
    }
    
    boolean isExportForced() {
        return this.forceExport;
    }
    
    Extensions getExtensions() {
        return this.extensions;
    }
    
    String getSessionId() {
        return this.sessionId;
    }
    
    @Override
    String getClassRef() {
        return this.classNode.name;
    }
    
    String getClassName() {
        return this.className;
    }
    
    @Override
    public ClassNode getClassNode() {
        return this.classNode;
    }
    
    List<MethodNode> getMethods() {
        return this.classNode.methods;
    }
    
    List<FieldNode> getFields() {
        return this.classNode.fields;
    }
    
    @Override
    public ClassInfo getClassInfo() {
        return this.classInfo;
    }
    
    SortedSet<MixinInfo> getMixins() {
        return this.mixins;
    }
    
    SourceMap getSourceMap() {
        return this.sourceMap;
    }
    
    void mergeSignature(final ClassSignature classSignature) {
        this.signature.merge(classSignature);
    }
    
    void addMixinMethod(final MethodNode methodNode) {
        this.mixinMethods.add(methodNode);
    }
    
    void methodMerged(final MethodNode methodNode) {
        if (!this.mixinMethods.remove(methodNode)) {
            TargetClassContext.logger.debug("Unexpected: Merged unregistered method {}{} in {}", new Object[] { methodNode.name, methodNode.desc, this });
        }
    }
    
    MethodNode findMethod(final Deque<String> deque, final String s) {
        return this.findAliasedMethod(deque, s, true);
    }
    
    MethodNode findAliasedMethod(final Deque<String> deque, final String s) {
        return this.findAliasedMethod(deque, s, false);
    }
    
    private MethodNode findAliasedMethod(final Deque<String> deque, final String s, final boolean b) {
        final String s2 = deque.poll();
        if (s2 == null) {
            return null;
        }
        for (final MethodNode methodNode : this.classNode.methods) {
            if (methodNode.name.equals(s2) && methodNode.desc.equals(s)) {
                return methodNode;
            }
        }
        if (b) {
            for (final MethodNode methodNode2 : this.mixinMethods) {
                if (methodNode2.name.equals(s2) && methodNode2.desc.equals(s)) {
                    return methodNode2;
                }
            }
        }
        return this.findAliasedMethod(deque, s);
    }
    
    FieldNode findAliasedField(final Deque<String> deque, final String s) {
        final String s2 = deque.poll();
        if (s2 == null) {
            return null;
        }
        for (final FieldNode fieldNode : this.classNode.fields) {
            if (fieldNode.name.equals(s2) && fieldNode.desc.equals(s)) {
                return fieldNode;
            }
        }
        return this.findAliasedField(deque, s);
    }
    
    Target getTargetMethod(final MethodNode methodNode) {
        if (!this.classNode.methods.contains(methodNode)) {
            throw new IllegalArgumentException("Invalid target method supplied to getTargetMethod()");
        }
        final String string = methodNode.name + methodNode.desc;
        Target target = this.targetMethods.get(string);
        if (target == null) {
            target = new Target(this.classNode, methodNode);
            this.targetMethods.put(string, target);
        }
        return target;
    }
    
    String getUniqueName(final MethodNode methodNode, final boolean b) {
        return String.format(b ? "%2$s_$md$%1$s$%3$s" : "md%s$%s$%s", this.sessionId.substring(30), methodNode.name, Integer.toHexString(this.nextUniqueMethodIndex++));
    }
    
    String getUniqueName(final FieldNode fieldNode) {
        return String.format("fd%s$%s$%s", this.sessionId.substring(30), fieldNode.name, Integer.toHexString(this.nextUniqueFieldIndex++));
    }
    
    void applyMixins() {
        if (this.applied) {
            throw new IllegalStateException("Mixins already applied to target class " + this.className);
        }
        this.applied = true;
        this.createApplicator().apply(this.mixins);
        this.applySignature();
        this.upgradeMethods();
        this.checkMerges();
    }
    
    private MixinApplicatorStandard createApplicator() {
        if (this.classInfo.isInterface()) {
            return new MixinApplicatorInterface(this);
        }
        return new MixinApplicatorStandard(this);
    }
    
    private void applySignature() {
        this.getClassNode().signature = this.signature.toString();
    }
    
    private void checkMerges() {
        for (final MethodNode methodNode : this.mixinMethods) {
            if (!methodNode.name.startsWith("<")) {
                TargetClassContext.logger.debug("Unexpected: Registered method {}{} in {} was not merged", new Object[] { methodNode.name, methodNode.desc, this });
            }
        }
    }
    
    void processDebugTasks() {
        if (!this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
            return;
        }
        final AnnotationNode visible = Annotations.getVisible(this.classNode, Debug.class);
        if (visible != null) {
            this.forceExport = Boolean.TRUE.equals(Annotations.getValue(visible, "export"));
            if (Boolean.TRUE.equals(Annotations.getValue(visible, "print"))) {
                Bytecode.textify(this.classNode, System.err);
            }
        }
        for (final MethodNode methodNode : this.classNode.methods) {
            final AnnotationNode visible2 = Annotations.getVisible(methodNode, Debug.class);
            if (visible2 != null && Boolean.TRUE.equals(Annotations.getValue(visible2, "print"))) {
                Bytecode.textify(methodNode, System.err);
            }
        }
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
}
