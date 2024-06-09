package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.gen.*;
import com.google.common.collect.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.transformer.meta.*;
import org.spongepowered.asm.mixin.struct.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.transformer.ext.*;
import java.util.*;
import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.apache.logging.log4j.*;

public class MixinTargetContext extends ClassContext implements IMixinContext
{
    private static final Logger logger;
    private final MixinInfo mixin;
    private final ClassNode classNode;
    private final TargetClassContext targetClass;
    private final String sessionId;
    private final ClassInfo targetClassInfo;
    private final BiMap<String, String> innerClasses;
    private final List<MethodNode> shadowMethods;
    private final Map<FieldNode, ClassInfo.Field> shadowFields;
    private final List<MethodNode> mergedMethods;
    private final InjectorGroupInfo.Map injectorGroups;
    private final List<InjectionInfo> injectors;
    private final List<AccessorInfo> accessors;
    private final boolean inheritsFromMixin;
    private final boolean detachedSuper;
    private final SourceMap.File stratum;
    private int minRequiredClassVersion;
    
    MixinTargetContext(final MixinInfo mixin, final ClassNode classNode, final TargetClassContext targetClass) {
        super();
        this.innerClasses = (BiMap<String, String>)HashBiMap.create();
        this.shadowMethods = new ArrayList<MethodNode>();
        this.shadowFields = new LinkedHashMap<FieldNode, ClassInfo.Field>();
        this.mergedMethods = new ArrayList<MethodNode>();
        this.injectorGroups = new InjectorGroupInfo.Map();
        this.injectors = new ArrayList<InjectionInfo>();
        this.accessors = new ArrayList<AccessorInfo>();
        this.minRequiredClassVersion = MixinEnvironment.CompatibilityLevel.JAVA_6.classVersion();
        this.mixin = mixin;
        this.classNode = classNode;
        this.targetClass = targetClass;
        this.targetClassInfo = ClassInfo.forName(this.getTarget().getClassRef());
        this.stratum = targetClass.getSourceMap().addFile(this.classNode);
        this.inheritsFromMixin = (mixin.getClassInfo().hasMixinInHierarchy() || this.targetClassInfo.hasMixinTargetInHierarchy());
        this.detachedSuper = !this.classNode.superName.equals(this.getTarget().getClassNode().superName);
        this.sessionId = targetClass.getSessionId();
        this.requireVersion(classNode.version);
        final InnerClassGenerator innerClassGenerator = targetClass.getExtensions().getGenerator(InnerClassGenerator.class);
        for (final String s : this.mixin.getInnerClasses()) {
            this.innerClasses.put(s, innerClassGenerator.registerInnerClass(this.mixin, s, this));
        }
    }
    
    void addShadowMethod(final MethodNode methodNode) {
        this.shadowMethods.add(methodNode);
    }
    
    void addShadowField(final FieldNode fieldNode, final ClassInfo.Field field) {
        this.shadowFields.put(fieldNode, field);
    }
    
    void addAccessorMethod(final MethodNode methodNode, final Class<? extends Annotation> clazz) {
        this.accessors.add(AccessorInfo.of(this, methodNode, clazz));
    }
    
    void addMixinMethod(final MethodNode methodNode) {
        Annotations.setVisible(methodNode, MixinMerged.class, "mixin", this.getClassName());
        this.getTarget().addMixinMethod(methodNode);
    }
    
    void methodMerged(final MethodNode methodNode) {
        this.mergedMethods.add(methodNode);
        this.targetClassInfo.addMethod(methodNode);
        this.getTarget().methodMerged(methodNode);
        Annotations.setVisible(methodNode, MixinMerged.class, "mixin", this.getClassName(), "priority", this.getPriority(), "sessionId", this.sessionId);
    }
    
    @Override
    public String toString() {
        return this.mixin.toString();
    }
    
    public MixinEnvironment getEnvironment() {
        return this.mixin.getParent().getEnvironment();
    }
    
    @Override
    public boolean getOption(final MixinEnvironment.Option option) {
        return this.getEnvironment().getOption(option);
    }
    
    public ClassNode getClassNode() {
        return this.classNode;
    }
    
    @Override
    public String getClassName() {
        return this.mixin.getClassName();
    }
    
    @Override
    public String getClassRef() {
        return this.mixin.getClassRef();
    }
    
    public TargetClassContext getTarget() {
        return this.targetClass;
    }
    
    @Override
    public String getTargetClassRef() {
        return this.getTarget().getClassRef();
    }
    
    public ClassNode getTargetClassNode() {
        return this.getTarget().getClassNode();
    }
    
    public ClassInfo getTargetClassInfo() {
        return this.targetClassInfo;
    }
    
    protected ClassInfo getClassInfo() {
        return this.mixin.getClassInfo();
    }
    
    public ClassSignature getSignature() {
        return this.getClassInfo().getSignature();
    }
    
    public SourceMap.File getStratum() {
        return this.stratum;
    }
    
    public int getMinRequiredClassVersion() {
        return this.minRequiredClassVersion;
    }
    
    public int getDefaultRequiredInjections() {
        return this.mixin.getParent().getDefaultRequiredInjections();
    }
    
    public String getDefaultInjectorGroup() {
        return this.mixin.getParent().getDefaultInjectorGroup();
    }
    
    public int getMaxShiftByValue() {
        return this.mixin.getParent().getMaxShiftByValue();
    }
    
    public InjectorGroupInfo.Map getInjectorGroups() {
        return this.injectorGroups;
    }
    
    public boolean requireOverwriteAnnotations() {
        return this.mixin.getParent().requireOverwriteAnnotations();
    }
    
    public ClassInfo findRealType(final ClassInfo classInfo) {
        if (classInfo == this.getClassInfo()) {
            return this.targetClassInfo;
        }
        final ClassInfo correspondingType = this.targetClassInfo.findCorrespondingType(classInfo);
        if (correspondingType == null) {
            throw new InvalidMixinException(this, "Resolution error: unable to find corresponding type for " + classInfo + " in hierarchy of " + this.targetClassInfo);
        }
        return correspondingType;
    }
    
    public void transformMethod(final MethodNode methodNode) {
        this.validateMethod(methodNode);
        this.transformDescriptor(methodNode);
        this.transformLVT(methodNode);
        this.stratum.applyOffset(methodNode);
        AbstractInsnNode abstractInsnNode = null;
        final ListIterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
        while (iterator.hasNext()) {
            final AbstractInsnNode abstractInsnNode2 = iterator.next();
            if (abstractInsnNode2 instanceof MethodInsnNode) {
                this.transformMethodRef(methodNode, iterator, new MemberRef.Method((MethodInsnNode)abstractInsnNode2));
            }
            else if (abstractInsnNode2 instanceof FieldInsnNode) {
                this.transformFieldRef(methodNode, iterator, new MemberRef.Field((FieldInsnNode)abstractInsnNode2));
                this.checkFinal(methodNode, iterator, (FieldInsnNode)abstractInsnNode2);
            }
            else if (abstractInsnNode2 instanceof TypeInsnNode) {
                this.transformTypeNode(methodNode, iterator, (TypeInsnNode)abstractInsnNode2, abstractInsnNode);
            }
            else if (abstractInsnNode2 instanceof LdcInsnNode) {
                this.transformConstantNode(methodNode, iterator, (LdcInsnNode)abstractInsnNode2);
            }
            else if (abstractInsnNode2 instanceof InvokeDynamicInsnNode) {
                this.transformInvokeDynamicNode(methodNode, iterator, (InvokeDynamicInsnNode)abstractInsnNode2);
            }
            abstractInsnNode = abstractInsnNode2;
        }
    }
    
    private void validateMethod(final MethodNode methodNode) {
        if (Annotations.getInvisible(methodNode, SoftOverride.class) != null) {
            final ClassInfo.Method methodInHierarchy = this.targetClassInfo.findMethodInHierarchy(methodNode.name, methodNode.desc, ClassInfo.SearchType.SUPER_CLASSES_ONLY, ClassInfo.Traversal.SUPER);
            if (methodInHierarchy == null || !methodInHierarchy.isInjected()) {
                throw new InvalidMixinException(this, "Mixin method " + methodNode.name + methodNode.desc + " is tagged with @SoftOverride but no valid method was found in superclasses of " + this.getTarget().getClassName());
            }
        }
    }
    
    private void transformLVT(final MethodNode methodNode) {
        if (methodNode.localVariables == null) {
            return;
        }
        for (final LocalVariableNode localVariableNode : methodNode.localVariables) {
            if (localVariableNode != null) {
                if (localVariableNode.desc == null) {
                    continue;
                }
                localVariableNode.desc = this.transformSingleDescriptor(Type.getType(localVariableNode.desc));
            }
        }
    }
    
    private void transformMethodRef(final MethodNode methodNode, final Iterator<AbstractInsnNode> iterator, final MemberRef memberRef) {
        this.transformDescriptor(memberRef);
        if (memberRef.getOwner().equals(this.getClassRef())) {
            memberRef.setOwner(this.getTarget().getClassRef());
            final ClassInfo.Method method = this.getClassInfo().findMethod(memberRef.getName(), memberRef.getDesc(), 10);
            if (method != null && method.isRenamed() && method.getOriginalName().equals(memberRef.getName()) && method.isSynthetic()) {
                memberRef.setName(method.getName());
            }
            this.upgradeMethodRef(methodNode, memberRef, method);
        }
        else if (this.innerClasses.containsKey(memberRef.getOwner())) {
            memberRef.setOwner(this.innerClasses.get(memberRef.getOwner()));
            memberRef.setDesc(this.transformMethodDescriptor(memberRef.getDesc()));
        }
        else if (this.detachedSuper || this.inheritsFromMixin) {
            if (memberRef.getOpcode() == 183) {
                this.updateStaticBinding(methodNode, memberRef);
            }
            else if (memberRef.getOpcode() == 182 && ClassInfo.forName(memberRef.getOwner()).isMixin()) {
                this.updateDynamicBinding(methodNode, memberRef);
            }
        }
    }
    
    private void transformFieldRef(final MethodNode methodNode, final Iterator<AbstractInsnNode> iterator, final MemberRef memberRef) {
        if ("super$".equals(memberRef.getName())) {
            if (!(memberRef instanceof MemberRef.Field)) {
                throw new InvalidMixinException(this.mixin, "Cannot call imaginary super from method handle.");
            }
            this.processImaginarySuper(methodNode, ((MemberRef.Field)memberRef).insn);
            iterator.remove();
        }
        this.transformDescriptor(memberRef);
        if (memberRef.getOwner().equals(this.getClassRef())) {
            memberRef.setOwner(this.getTarget().getClassRef());
            final ClassInfo.Field field = this.getClassInfo().findField(memberRef.getName(), memberRef.getDesc(), 10);
            if (field != null && field.isRenamed() && field.getOriginalName().equals(memberRef.getName()) && field.isStatic()) {
                memberRef.setName(field.getName());
            }
        }
        else {
            final ClassInfo forName = ClassInfo.forName(memberRef.getOwner());
            if (forName.isMixin()) {
                final ClassInfo correspondingType = this.targetClassInfo.findCorrespondingType(forName);
                memberRef.setOwner((correspondingType != null) ? correspondingType.getName() : this.getTarget().getClassRef());
            }
        }
    }
    
    private void checkFinal(final MethodNode methodNode, final Iterator<AbstractInsnNode> iterator, final FieldInsnNode fieldInsnNode) {
        if (!fieldInsnNode.owner.equals(this.getTarget().getClassRef())) {
            return;
        }
        final int opcode = fieldInsnNode.getOpcode();
        if (opcode == 180 || opcode == 178) {
            return;
        }
        for (final Map.Entry<FieldNode, ClassInfo.Field> entry : this.shadowFields.entrySet()) {
            final FieldNode fieldNode = entry.getKey();
            if (fieldNode.desc.equals(fieldInsnNode.desc)) {
                if (!fieldNode.name.equals(fieldInsnNode.name)) {
                    continue;
                }
                final ClassInfo.Field field = entry.getValue();
                if (field.isDecoratedFinal()) {
                    if (field.isDecoratedMutable()) {
                        if (this.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
                            MixinTargetContext.logger.warn("Write access to @Mutable @Final field {} in {}::{}", new Object[] { field, this.mixin, methodNode.name });
                        }
                    }
                    else if ("<init>".equals(methodNode.name) || "<clinit>".equals(methodNode.name)) {
                        MixinTargetContext.logger.warn("@Final field {} in {} should be final", new Object[] { field, this.mixin });
                    }
                    else {
                        MixinTargetContext.logger.error("Write access detected to @Final field {} in {}::{}", new Object[] { field, this.mixin, methodNode.name });
                        if (this.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
                            throw new InvalidMixinException(this.mixin, "Write access detected to @Final field " + field + " in " + this.mixin + "::" + methodNode.name);
                        }
                    }
                }
            }
        }
    }
    
    private void transformTypeNode(final MethodNode methodNode, final Iterator<AbstractInsnNode> iterator, final TypeInsnNode typeInsnNode, final AbstractInsnNode abstractInsnNode) {
        if (typeInsnNode.getOpcode() == 192 && typeInsnNode.desc.equals(this.getTarget().getClassRef()) && abstractInsnNode.getOpcode() == 25 && ((VarInsnNode)abstractInsnNode).var == 0) {
            iterator.remove();
            return;
        }
        if (typeInsnNode.desc.equals(this.getClassRef())) {
            typeInsnNode.desc = this.getTarget().getClassRef();
        }
        else {
            final String desc = this.innerClasses.get(typeInsnNode.desc);
            if (desc != null) {
                typeInsnNode.desc = desc;
            }
        }
        this.transformDescriptor(typeInsnNode);
    }
    
    private void transformConstantNode(final MethodNode methodNode, final Iterator<AbstractInsnNode> iterator, final LdcInsnNode ldcInsnNode) {
        ldcInsnNode.cst = this.transformConstant(methodNode, iterator, ldcInsnNode.cst);
    }
    
    private void transformInvokeDynamicNode(final MethodNode methodNode, final Iterator<AbstractInsnNode> iterator, final InvokeDynamicInsnNode invokeDynamicInsnNode) {
        this.requireVersion(51);
        invokeDynamicInsnNode.desc = this.transformMethodDescriptor(invokeDynamicInsnNode.desc);
        invokeDynamicInsnNode.bsm = this.transformHandle(methodNode, iterator, invokeDynamicInsnNode.bsm);
        for (int i = 0; i < invokeDynamicInsnNode.bsmArgs.length; ++i) {
            invokeDynamicInsnNode.bsmArgs[i] = this.transformConstant(methodNode, iterator, invokeDynamicInsnNode.bsmArgs[i]);
        }
    }
    
    private Object transformConstant(final MethodNode methodNode, final Iterator<AbstractInsnNode> iterator, final Object o) {
        if (o instanceof Type) {
            final Type type = (Type)o;
            final String transformDescriptor = this.transformDescriptor(type);
            if (!type.toString().equals(transformDescriptor)) {
                return Type.getType(transformDescriptor);
            }
            return o;
        }
        else {
            if (o instanceof Handle) {
                return this.transformHandle(methodNode, iterator, (Handle)o);
            }
            return o;
        }
    }
    
    private Handle transformHandle(final MethodNode methodNode, final Iterator<AbstractInsnNode> iterator, final Handle handle) {
        final MemberRef.Handle handle2 = new MemberRef.Handle(handle);
        if (handle2.isField()) {
            this.transformFieldRef(methodNode, iterator, handle2);
        }
        else {
            this.transformMethodRef(methodNode, iterator, handle2);
        }
        return handle2.getMethodHandle();
    }
    
    private void processImaginarySuper(final MethodNode methodNode, final FieldInsnNode fieldInsnNode) {
        if (fieldInsnNode.getOpcode() != 180) {
            if ("<init>".equals(methodNode.name)) {
                throw new InvalidMixinException(this, "Illegal imaginary super declaration: field " + fieldInsnNode.name + " must not specify an initialiser");
            }
            throw new InvalidMixinException(this, "Illegal imaginary super access: found " + Bytecode.getOpcodeName(fieldInsnNode.getOpcode()) + " opcode in " + methodNode.name + methodNode.desc);
        }
        else {
            if ((methodNode.access & 0x2) != 0x0 || (methodNode.access & 0x8) != 0x0) {
                throw new InvalidMixinException(this, "Illegal imaginary super access: method " + methodNode.name + methodNode.desc + " is private or static");
            }
            if (Annotations.getInvisible(methodNode, SoftOverride.class) == null) {
                throw new InvalidMixinException(this, "Illegal imaginary super access: method " + methodNode.name + methodNode.desc + " is not decorated with @SoftOverride");
            }
            final ListIterator<AbstractInsnNode> iterator = methodNode.instructions.iterator(methodNode.instructions.indexOf(fieldInsnNode));
            while (iterator.hasNext()) {
                final AbstractInsnNode abstractInsnNode = iterator.next();
                if (abstractInsnNode instanceof MethodInsnNode) {
                    final MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;
                    if (methodInsnNode.owner.equals(this.getClassRef()) && methodInsnNode.name.equals(methodNode.name) && methodInsnNode.desc.equals(methodNode.desc)) {
                        methodInsnNode.setOpcode(183);
                        this.updateStaticBinding(methodNode, new MemberRef.Method(methodInsnNode));
                        return;
                    }
                    continue;
                }
            }
            throw new InvalidMixinException(this, "Illegal imaginary super access: could not find INVOKE for " + methodNode.name + methodNode.desc);
        }
    }
    
    private void updateStaticBinding(final MethodNode methodNode, final MemberRef memberRef) {
        this.updateBinding(methodNode, memberRef, ClassInfo.Traversal.SUPER);
    }
    
    private void updateDynamicBinding(final MethodNode methodNode, final MemberRef memberRef) {
        this.updateBinding(methodNode, memberRef, ClassInfo.Traversal.ALL);
    }
    
    private void updateBinding(final MethodNode methodNode, final MemberRef memberRef, final ClassInfo.Traversal traversal) {
        if ("<init>".equals(methodNode.name) || memberRef.getOwner().equals(this.getTarget().getClassRef()) || this.getTarget().getClassRef().startsWith("<")) {
            return;
        }
        final ClassInfo.Method methodInHierarchy = this.targetClassInfo.findMethodInHierarchy(memberRef.getName(), memberRef.getDesc(), traversal.getSearchType(), traversal);
        if (methodInHierarchy != null) {
            if (methodInHierarchy.getOwner().isMixin()) {
                throw new InvalidMixinException(this, "Invalid " + memberRef + " in " + this + " resolved " + methodInHierarchy.getOwner() + " but is mixin.");
            }
            memberRef.setOwner(methodInHierarchy.getImplementor().getName());
        }
        else if (ClassInfo.forName(memberRef.getOwner()).isMixin()) {
            throw new MixinTransformerError("Error resolving " + memberRef + " in " + this);
        }
    }
    
    public void transformDescriptor(final FieldNode fieldNode) {
        if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
            return;
        }
        fieldNode.desc = this.transformSingleDescriptor(fieldNode.desc, false);
    }
    
    public void transformDescriptor(final MethodNode methodNode) {
        if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
            return;
        }
        methodNode.desc = this.transformMethodDescriptor(methodNode.desc);
    }
    
    public void transformDescriptor(final MemberRef memberRef) {
        if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
            return;
        }
        if (memberRef.isField()) {
            memberRef.setDesc(this.transformSingleDescriptor(memberRef.getDesc(), false));
        }
        else {
            memberRef.setDesc(this.transformMethodDescriptor(memberRef.getDesc()));
        }
    }
    
    public void transformDescriptor(final TypeInsnNode typeInsnNode) {
        if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
            return;
        }
        typeInsnNode.desc = this.transformSingleDescriptor(typeInsnNode.desc, true);
    }
    
    private String transformDescriptor(final Type type) {
        if (type.getSort() == 11) {
            return this.transformMethodDescriptor(type.getDescriptor());
        }
        return this.transformSingleDescriptor(type);
    }
    
    private String transformSingleDescriptor(final Type type) {
        if (type.getSort() < 9) {
            return type.toString();
        }
        return this.transformSingleDescriptor(type.toString(), false);
    }
    
    private String transformSingleDescriptor(final String s, boolean b) {
        String s2 = s;
        while (s2.startsWith("[") || s2.startsWith("L")) {
            if (s2.startsWith("[")) {
                s2 = s2.substring(1);
            }
            else {
                s2 = s2.substring(1, s2.indexOf(";"));
                b = true;
            }
        }
        if (!b) {
            return s;
        }
        final String s3 = this.innerClasses.get(s2);
        if (s3 != null) {
            return s.replace(s2, s3);
        }
        if (this.innerClasses.inverse().containsKey(s2)) {
            return s;
        }
        final ClassInfo forName = ClassInfo.forName(s2);
        if (!forName.isMixin()) {
            return s;
        }
        return s.replace(s2, this.findRealType(forName).toString());
    }
    
    private String transformMethodDescriptor(final String s) {
        final StringBuilder sb = new StringBuilder();
        sb.append('(');
        final Type[] argumentTypes = Type.getArgumentTypes(s);
        for (int length = argumentTypes.length, i = 0; i < length; ++i) {
            sb.append(this.transformSingleDescriptor(argumentTypes[i]));
        }
        return sb.append(')').append(this.transformSingleDescriptor(Type.getReturnType(s))).toString();
    }
    
    @Override
    public Target getTargetMethod(final MethodNode methodNode) {
        return this.getTarget().getTargetMethod(methodNode);
    }
    
    MethodNode findMethod(final MethodNode methodNode, final AnnotationNode annotationNode) {
        final LinkedList<String> list = new LinkedList<String>();
        list.add(methodNode.name);
        if (annotationNode != null) {
            final List<? extends String> list2 = Annotations.getValue(annotationNode, "aliases");
            if (list2 != null) {
                list.addAll((Collection<?>)list2);
            }
        }
        return this.getTarget().findMethod(list, methodNode.desc);
    }
    
    MethodNode findRemappedMethod(final MethodNode methodNode) {
        final String mapMethodName = this.getEnvironment().getRemappers().mapMethodName(this.getTarget().getClassRef(), methodNode.name, methodNode.desc);
        if (mapMethodName.equals(methodNode.name)) {
            return null;
        }
        final LinkedList<String> list = new LinkedList<String>();
        list.add(mapMethodName);
        return this.getTarget().findAliasedMethod(list, methodNode.desc);
    }
    
    FieldNode findField(final FieldNode fieldNode, final AnnotationNode annotationNode) {
        final LinkedList<String> list = new LinkedList<String>();
        list.add(fieldNode.name);
        if (annotationNode != null) {
            final List<? extends String> list2 = Annotations.getValue(annotationNode, "aliases");
            if (list2 != null) {
                list.addAll((Collection<?>)list2);
            }
        }
        return this.getTarget().findAliasedField(list, fieldNode.desc);
    }
    
    FieldNode findRemappedField(final FieldNode fieldNode) {
        final String mapFieldName = this.getEnvironment().getRemappers().mapFieldName(this.getTarget().getClassRef(), fieldNode.name, fieldNode.desc);
        if (mapFieldName.equals(fieldNode.name)) {
            return null;
        }
        final LinkedList<String> list = new LinkedList<String>();
        list.add(mapFieldName);
        return this.getTarget().findAliasedField(list, fieldNode.desc);
    }
    
    protected void requireVersion(final int n) {
        this.minRequiredClassVersion = Math.max(this.minRequiredClassVersion, n);
        if (n > MixinEnvironment.getCompatibilityLevel().classVersion()) {
            throw new InvalidMixinException(this, "Unsupported mixin class version " + n);
        }
    }
    
    @Override
    public Extensions getExtensions() {
        return this.targetClass.getExtensions();
    }
    
    @Override
    public IMixinInfo getMixin() {
        return this.mixin;
    }
    
    MixinInfo getInfo() {
        return this.mixin;
    }
    
    @Override
    public int getPriority() {
        return this.mixin.getPriority();
    }
    
    public Set<String> getInterfaces() {
        return this.mixin.getInterfaces();
    }
    
    public Collection<MethodNode> getShadowMethods() {
        return this.shadowMethods;
    }
    
    public List<MethodNode> getMethods() {
        return this.classNode.methods;
    }
    
    public Set<Map.Entry<FieldNode, ClassInfo.Field>> getShadowFields() {
        return this.shadowFields.entrySet();
    }
    
    public List<FieldNode> getFields() {
        return this.classNode.fields;
    }
    
    public Level getLoggingLevel() {
        return this.mixin.getLoggingLevel();
    }
    
    public boolean shouldSetSourceFile() {
        return this.mixin.getParent().shouldSetSourceFile();
    }
    
    public String getSourceFile() {
        return this.classNode.sourceFile;
    }
    
    @Override
    public IReferenceMapper getReferenceMapper() {
        return this.mixin.getParent().getReferenceMapper();
    }
    
    public void preApply(final String s, final ClassNode classNode) {
        this.mixin.preApply(s, classNode);
    }
    
    public void postApply(final String s, final ClassNode classNode) {
        try {
            this.injectorGroups.validateAll();
        }
        catch (InjectionValidationException ex) {
            throw new InjectionError(String.format("Critical injection failure: Callback group %s in %s failed injection check: %s", ex.getGroup(), this.mixin, ex.getMessage()));
        }
        this.mixin.postApply(s, classNode);
    }
    
    public String getUniqueName(final MethodNode methodNode, final boolean b) {
        return this.getTarget().getUniqueName(methodNode, b);
    }
    
    public String getUniqueName(final FieldNode fieldNode) {
        return this.getTarget().getUniqueName(fieldNode);
    }
    
    public void prepareInjections() {
        this.injectors.clear();
        for (final MethodNode methodNode : this.mergedMethods) {
            final InjectionInfo parse = InjectionInfo.parse(this, methodNode);
            if (parse == null) {
                continue;
            }
            if (parse.isValid()) {
                parse.prepare();
                this.injectors.add(parse);
            }
            methodNode.visibleAnnotations.remove(parse.getAnnotation());
        }
    }
    
    public void applyInjections() {
        final Iterator<InjectionInfo> iterator = this.injectors.iterator();
        while (iterator.hasNext()) {
            iterator.next().inject();
        }
        final Iterator<InjectionInfo> iterator2 = this.injectors.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().postInject();
        }
        this.injectors.clear();
    }
    
    public List<MethodNode> generateAccessors() {
        final Iterator<AccessorInfo> iterator = this.accessors.iterator();
        while (iterator.hasNext()) {
            iterator.next().locate();
        }
        final ArrayList<MethodNode> list = new ArrayList<MethodNode>();
        final Iterator<AccessorInfo> iterator2 = this.accessors.iterator();
        while (iterator2.hasNext()) {
            final MethodNode generate = iterator2.next().generate();
            this.getTarget().addMixinMethod(generate);
            list.add(generate);
        }
        return list;
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
}
