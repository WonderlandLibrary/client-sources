package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.util.perf.*;
import com.google.common.collect.*;
import org.spongepowered.asm.service.*;
import org.spongepowered.asm.lib.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.gen.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import org.spongepowered.asm.mixin.*;

public final class ClassInfo
{
    public static final int INCLUDE_PRIVATE = 2;
    public static final int INCLUDE_STATIC = 8;
    public static final int INCLUDE_ALL = 10;
    private static final Logger logger;
    private static final Profiler profiler;
    private static final String JAVA_LANG_OBJECT = "java/lang/Object";
    private static final Map<String, ClassInfo> cache;
    private static final ClassInfo OBJECT;
    private final String name;
    private final String superName;
    private final String outerName;
    private final boolean isProbablyStatic;
    private final Set<String> interfaces;
    private final Set<Method> methods;
    private final Set<Field> fields;
    private final Set<MixinInfo> mixins;
    private final Map<ClassInfo, ClassInfo> correspondingTypes;
    private final MixinInfo mixin;
    private final MethodMapper methodMapper;
    private final boolean isMixin;
    private final boolean isInterface;
    private final int access;
    private ClassInfo superClass;
    private ClassInfo outerClass;
    private ClassSignature signature;
    
    private ClassInfo() {
        super();
        this.mixins = new HashSet<MixinInfo>();
        this.correspondingTypes = new HashMap<ClassInfo, ClassInfo>();
        this.name = "java/lang/Object";
        this.superName = null;
        this.outerName = null;
        this.isProbablyStatic = true;
        this.methods = (Set<Method>)ImmutableSet.of((Object)new Method("getClass", "()Ljava/lang/Class;"), (Object)new Method("hashCode", "()I"), (Object)new Method("equals", "(Ljava/lang/Object;)Z"), (Object)new Method("clone", "()Ljava/lang/Object;"), (Object)new Method("toString", "()Ljava/lang/String;"), (Object)new Method("notify", "()V"), (Object[])new Method[] { new Method("notifyAll", "()V"), new Method("wait", "(J)V"), new Method("wait", "(JI)V"), new Method("wait", "()V"), new Method("finalize", "()V") });
        this.fields = Collections.emptySet();
        this.isInterface = false;
        this.interfaces = Collections.emptySet();
        this.access = 1;
        this.isMixin = false;
        this.mixin = null;
        this.methodMapper = null;
    }
    
    private ClassInfo(final ClassNode classNode) {
        super();
        this.mixins = new HashSet<MixinInfo>();
        this.correspondingTypes = new HashMap<ClassInfo, ClassInfo>();
        final Profiler.Section begin = ClassInfo.profiler.begin(1, "class.meta");
        try {
            this.name = classNode.name;
            this.superName = ((classNode.superName != null) ? classNode.superName : "java/lang/Object");
            this.methods = new HashSet<Method>();
            this.fields = new HashSet<Field>();
            this.isInterface = ((classNode.access & 0x200) != 0x0);
            this.interfaces = new HashSet<String>();
            this.access = classNode.access;
            this.isMixin = (classNode instanceof MixinInfo.MixinClassNode);
            this.mixin = (this.isMixin ? ((MixinInfo.MixinClassNode)classNode).getMixin() : null);
            this.interfaces.addAll(classNode.interfaces);
            final Iterator<MethodNode> iterator = classNode.methods.iterator();
            while (iterator.hasNext()) {
                this.addMethod(iterator.next(), this.isMixin);
            }
            boolean isProbablyStatic = true;
            String outerName = classNode.outerClass;
            for (final FieldNode fieldNode : classNode.fields) {
                if ((fieldNode.access & 0x1000) != 0x0 && fieldNode.name.startsWith("this$")) {
                    isProbablyStatic = false;
                    if (outerName == null) {
                        outerName = fieldNode.desc;
                        if (outerName != null && outerName.startsWith("L")) {
                            outerName = outerName.substring(1, outerName.length() - 1);
                        }
                    }
                }
                this.fields.add(new Field(fieldNode, this.isMixin));
            }
            this.isProbablyStatic = isProbablyStatic;
            this.outerName = outerName;
            this.methodMapper = new MethodMapper(MixinEnvironment.getCurrentEnvironment(), this);
            this.signature = ClassSignature.ofLazy(classNode);
        }
        finally {
            begin.end();
        }
    }
    
    void addInterface(final String s) {
        this.interfaces.add(s);
        this.getSignature().addInterface(s);
    }
    
    void addMethod(final MethodNode methodNode) {
        this.addMethod(methodNode, true);
    }
    
    private void addMethod(final MethodNode methodNode, final boolean b) {
        if (!methodNode.name.startsWith("<")) {
            this.methods.add(new Method(methodNode, b));
        }
    }
    
    void addMixin(final MixinInfo mixinInfo) {
        if (this.isMixin) {
            throw new IllegalArgumentException("Cannot add target " + this.name + " for " + mixinInfo.getClassName() + " because the target is a mixin");
        }
        this.mixins.add(mixinInfo);
    }
    
    public Set<MixinInfo> getMixins() {
        return Collections.unmodifiableSet((Set<? extends MixinInfo>)this.mixins);
    }
    
    public boolean isMixin() {
        return this.isMixin;
    }
    
    public boolean isPublic() {
        return (this.access & 0x1) != 0x0;
    }
    
    public boolean isAbstract() {
        return (this.access & 0x400) != 0x0;
    }
    
    public boolean isSynthetic() {
        return (this.access & 0x1000) != 0x0;
    }
    
    public boolean isProbablyStatic() {
        return this.isProbablyStatic;
    }
    
    public boolean isInner() {
        return this.outerName != null;
    }
    
    public boolean isInterface() {
        return this.isInterface;
    }
    
    public Set<String> getInterfaces() {
        return Collections.unmodifiableSet((Set<? extends String>)this.interfaces);
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    public MethodMapper getMethodMapper() {
        return this.methodMapper;
    }
    
    public int getAccess() {
        return this.access;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getClassName() {
        return this.name.replace('/', '.');
    }
    
    public String getSuperName() {
        return this.superName;
    }
    
    public ClassInfo getSuperClass() {
        if (this.superClass == null && this.superName != null) {
            this.superClass = forName(this.superName);
        }
        return this.superClass;
    }
    
    public String getOuterName() {
        return this.outerName;
    }
    
    public ClassInfo getOuterClass() {
        if (this.outerClass == null && this.outerName != null) {
            this.outerClass = forName(this.outerName);
        }
        return this.outerClass;
    }
    
    public ClassSignature getSignature() {
        return this.signature.wake();
    }
    
    List<ClassInfo> getTargets() {
        if (this.mixin != null) {
            final ArrayList<Object> list = new ArrayList<Object>();
            list.add(this);
            list.addAll(this.mixin.getTargets());
            return (List<ClassInfo>)list;
        }
        return ImmutableList.of(this);
    }
    
    public Set<Method> getMethods() {
        return Collections.unmodifiableSet((Set<? extends Method>)this.methods);
    }
    
    public Set<Method> getInterfaceMethods(final boolean b) {
        final HashSet<Method> set = new HashSet<Method>();
        ClassInfo classInfo = this.addMethodsRecursive(set, b);
        if (!this.isInterface) {
            while (classInfo != null && classInfo != ClassInfo.OBJECT) {
                classInfo = classInfo.addMethodsRecursive(set, b);
            }
        }
        final Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().isAbstract()) {
                iterator.remove();
            }
        }
        return (Set<Method>)Collections.unmodifiableSet((Set<?>)set);
    }
    
    private ClassInfo addMethodsRecursive(final Set<Method> set, final boolean b) {
        if (this.isInterface) {
            for (final Method method : this.methods) {
                if (!method.isAbstract()) {
                    set.remove(method);
                }
                set.add(method);
            }
        }
        else if (!this.isMixin && b) {
            final Iterator<MixinInfo> iterator2 = this.mixins.iterator();
            while (iterator2.hasNext()) {
                iterator2.next().getClassInfo().addMethodsRecursive(set, b);
            }
        }
        final Iterator<String> iterator3 = this.interfaces.iterator();
        while (iterator3.hasNext()) {
            forName(iterator3.next()).addMethodsRecursive(set, b);
        }
        return this.getSuperClass();
    }
    
    public boolean hasSuperClass(final String s) {
        return this.hasSuperClass(s, Traversal.NONE);
    }
    
    public boolean hasSuperClass(final String s, final Traversal traversal) {
        return "java/lang/Object".equals(s) || this.findSuperClass(s, traversal) != null;
    }
    
    public boolean hasSuperClass(final ClassInfo classInfo) {
        return this.hasSuperClass(classInfo, Traversal.NONE, false);
    }
    
    public boolean hasSuperClass(final ClassInfo classInfo, final Traversal traversal) {
        return this.hasSuperClass(classInfo, traversal, false);
    }
    
    public boolean hasSuperClass(final ClassInfo classInfo, final Traversal traversal, final boolean b) {
        return ClassInfo.OBJECT == classInfo || this.findSuperClass(classInfo.name, traversal, b) != null;
    }
    
    public ClassInfo findSuperClass(final String s) {
        return this.findSuperClass(s, Traversal.NONE);
    }
    
    public ClassInfo findSuperClass(final String s, final Traversal traversal) {
        return this.findSuperClass(s, traversal, false, new HashSet<String>());
    }
    
    public ClassInfo findSuperClass(final String s, final Traversal traversal, final boolean b) {
        if (ClassInfo.OBJECT.name.equals(s)) {
            return null;
        }
        return this.findSuperClass(s, traversal, b, new HashSet<String>());
    }
    
    private ClassInfo findSuperClass(final String s, final Traversal traversal, final boolean b, final Set<String> set) {
        final ClassInfo superClass = this.getSuperClass();
        if (superClass != null) {
            for (final ClassInfo classInfo : superClass.getTargets()) {
                if (s.equals(classInfo.getName())) {
                    return superClass;
                }
                final ClassInfo superClass2 = classInfo.findSuperClass(s, traversal.next(), b, set);
                if (superClass2 != null) {
                    return superClass2;
                }
            }
        }
        if (b) {
            final ClassInfo interface1 = this.findInterface(s);
            if (interface1 != null) {
                return interface1;
            }
        }
        if (traversal.canTraverse()) {
            for (final MixinInfo mixinInfo : this.mixins) {
                final String className = mixinInfo.getClassName();
                if (set.contains(className)) {
                    continue;
                }
                set.add(className);
                final ClassInfo classInfo2 = mixinInfo.getClassInfo();
                if (s.equals(classInfo2.getName())) {
                    return classInfo2;
                }
                final ClassInfo superClass3 = classInfo2.findSuperClass(s, Traversal.ALL, b, set);
                if (superClass3 != null) {
                    return superClass3;
                }
            }
        }
        return null;
    }
    
    private ClassInfo findInterface(final String s) {
        for (final String s2 : this.getInterfaces()) {
            final ClassInfo forName = forName(s2);
            if (s.equals(s2)) {
                return forName;
            }
            final ClassInfo interface1 = forName.findInterface(s);
            if (interface1 != null) {
                return interface1;
            }
        }
        return null;
    }
    
    ClassInfo findCorrespondingType(final ClassInfo classInfo) {
        if (classInfo == null || !classInfo.isMixin || this.isMixin) {
            return null;
        }
        ClassInfo superTypeForMixin = this.correspondingTypes.get(classInfo);
        if (superTypeForMixin == null) {
            superTypeForMixin = this.findSuperTypeForMixin(classInfo);
            this.correspondingTypes.put(classInfo, superTypeForMixin);
        }
        return superTypeForMixin;
    }
    
    private ClassInfo findSuperTypeForMixin(final ClassInfo classInfo) {
        for (ClassInfo superClass = this; superClass != null && superClass != ClassInfo.OBJECT; superClass = superClass.getSuperClass()) {
            final Iterator<MixinInfo> iterator = superClass.mixins.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getClassInfo().equals(classInfo)) {
                    return superClass;
                }
            }
        }
        return null;
    }
    
    public boolean hasMixinInHierarchy() {
        if (!this.isMixin) {
            return false;
        }
        for (ClassInfo classInfo = this.getSuperClass(); classInfo != null && classInfo != ClassInfo.OBJECT; classInfo = classInfo.getSuperClass()) {
            if (classInfo.isMixin) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasMixinTargetInHierarchy() {
        if (this.isMixin) {
            return false;
        }
        for (ClassInfo classInfo = this.getSuperClass(); classInfo != null && classInfo != ClassInfo.OBJECT; classInfo = classInfo.getSuperClass()) {
            if (classInfo.mixins.size() > 0) {
                return true;
            }
        }
        return false;
    }
    
    public Method findMethodInHierarchy(final MethodNode methodNode, final SearchType searchType) {
        return this.findMethodInHierarchy(methodNode.name, methodNode.desc, searchType, Traversal.NONE);
    }
    
    public Method findMethodInHierarchy(final MethodNode methodNode, final SearchType searchType, final int n) {
        return this.findMethodInHierarchy(methodNode.name, methodNode.desc, searchType, Traversal.NONE, n);
    }
    
    public Method findMethodInHierarchy(final MethodInsnNode methodInsnNode, final SearchType searchType) {
        return this.findMethodInHierarchy(methodInsnNode.name, methodInsnNode.desc, searchType, Traversal.NONE);
    }
    
    public Method findMethodInHierarchy(final MethodInsnNode methodInsnNode, final SearchType searchType, final int n) {
        return this.findMethodInHierarchy(methodInsnNode.name, methodInsnNode.desc, searchType, Traversal.NONE, n);
    }
    
    public Method findMethodInHierarchy(final String s, final String s2, final SearchType searchType) {
        return this.findMethodInHierarchy(s, s2, searchType, Traversal.NONE);
    }
    
    public Method findMethodInHierarchy(final String s, final String s2, final SearchType searchType, final Traversal traversal) {
        return this.findMethodInHierarchy(s, s2, searchType, traversal, 0);
    }
    
    public Method findMethodInHierarchy(final String s, final String s2, final SearchType searchType, final Traversal traversal, final int n) {
        return this.findInHierarchy(s, s2, searchType, traversal, n, Member.Type.METHOD);
    }
    
    public Field findFieldInHierarchy(final FieldNode fieldNode, final SearchType searchType) {
        return this.findFieldInHierarchy(fieldNode.name, fieldNode.desc, searchType, Traversal.NONE);
    }
    
    public Field findFieldInHierarchy(final FieldNode fieldNode, final SearchType searchType, final int n) {
        return this.findFieldInHierarchy(fieldNode.name, fieldNode.desc, searchType, Traversal.NONE, n);
    }
    
    public Field findFieldInHierarchy(final FieldInsnNode fieldInsnNode, final SearchType searchType) {
        return this.findFieldInHierarchy(fieldInsnNode.name, fieldInsnNode.desc, searchType, Traversal.NONE);
    }
    
    public Field findFieldInHierarchy(final FieldInsnNode fieldInsnNode, final SearchType searchType, final int n) {
        return this.findFieldInHierarchy(fieldInsnNode.name, fieldInsnNode.desc, searchType, Traversal.NONE, n);
    }
    
    public Field findFieldInHierarchy(final String s, final String s2, final SearchType searchType) {
        return this.findFieldInHierarchy(s, s2, searchType, Traversal.NONE);
    }
    
    public Field findFieldInHierarchy(final String s, final String s2, final SearchType searchType, final Traversal traversal) {
        return this.findFieldInHierarchy(s, s2, searchType, traversal, 0);
    }
    
    public Field findFieldInHierarchy(final String s, final String s2, final SearchType searchType, final Traversal traversal, final int n) {
        return this.findInHierarchy(s, s2, searchType, traversal, n, Member.Type.FIELD);
    }
    
    private <M extends Member> M findInHierarchy(final String s, final String s2, final SearchType searchType, final Traversal traversal, final int n, final Member.Type type) {
        if (searchType == SearchType.ALL_CLASSES) {
            final Member member = this.findMember(s, s2, n, type);
            if (member != null) {
                return (M)member;
            }
            if (traversal.canTraverse()) {
                final Iterator<MixinInfo> iterator = this.mixins.iterator();
                while (iterator.hasNext()) {
                    final Member member2 = iterator.next().getClassInfo().findMember(s, s2, n, type);
                    if (member2 != null) {
                        return this.cloneMember(member2);
                    }
                }
            }
        }
        final ClassInfo superClass = this.getSuperClass();
        if (superClass != null) {
            final Iterator<ClassInfo> iterator2 = superClass.getTargets().iterator();
            while (iterator2.hasNext()) {
                final Member inHierarchy = iterator2.next().findInHierarchy(s, s2, SearchType.ALL_CLASSES, traversal.next(), n & 0xFFFFFFFD, type);
                if (inHierarchy != null) {
                    return (M)inHierarchy;
                }
            }
        }
        if (type == Member.Type.METHOD && (this.isInterface || MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces())) {
            for (final String s3 : this.interfaces) {
                final ClassInfo forName = forName(s3);
                if (forName == null) {
                    ClassInfo.logger.debug("Failed to resolve declared interface {} on {}", new Object[] { s3, this.name });
                }
                else {
                    final Member inHierarchy2 = forName.findInHierarchy(s, s2, SearchType.ALL_CLASSES, traversal.next(), n & 0xFFFFFFFD, type);
                    if (inHierarchy2 != null) {
                        return (M)(this.isInterface ? inHierarchy2 : new InterfaceMethod(inHierarchy2));
                    }
                    continue;
                }
            }
        }
        return null;
    }
    
    private <M extends Member> M cloneMember(final M m) {
        if (m instanceof Method) {
            return (M)new Method(m);
        }
        return (M)new Field(m);
    }
    
    public Method findMethod(final MethodNode methodNode) {
        return this.findMethod(methodNode.name, methodNode.desc, methodNode.access);
    }
    
    public Method findMethod(final MethodNode methodNode, final int n) {
        return this.findMethod(methodNode.name, methodNode.desc, n);
    }
    
    public Method findMethod(final MethodInsnNode methodInsnNode) {
        return this.findMethod(methodInsnNode.name, methodInsnNode.desc, 0);
    }
    
    public Method findMethod(final MethodInsnNode methodInsnNode, final int n) {
        return this.findMethod(methodInsnNode.name, methodInsnNode.desc, n);
    }
    
    public Method findMethod(final String s, final String s2, final int n) {
        return this.findMember(s, s2, n, Member.Type.METHOD);
    }
    
    public Field findField(final FieldNode fieldNode) {
        return this.findField(fieldNode.name, fieldNode.desc, fieldNode.access);
    }
    
    public Field findField(final FieldInsnNode fieldInsnNode, final int n) {
        return this.findField(fieldInsnNode.name, fieldInsnNode.desc, n);
    }
    
    public Field findField(final String s, final String s2, final int n) {
        return this.findMember(s, s2, n, Member.Type.FIELD);
    }
    
    private <M extends Member> M findMember(final String s, final String s2, final int n, final Member.Type type) {
        for (final Member member : (type == Member.Type.METHOD) ? this.methods : this.fields) {
            if (member.equals(s, s2) && member.matchesFlags(n)) {
                return (M)member;
            }
        }
        return null;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof ClassInfo && ((ClassInfo)o).name.equals(this.name);
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    
    static ClassInfo fromClassNode(final ClassNode classNode) {
        ClassInfo classInfo = ClassInfo.cache.get(classNode.name);
        if (classInfo == null) {
            classInfo = new ClassInfo(classNode);
            ClassInfo.cache.put(classNode.name, classInfo);
        }
        return classInfo;
    }
    
    public static ClassInfo forName(String replace) {
        replace = replace.replace('.', '/');
        ClassInfo classInfo = ClassInfo.cache.get(replace);
        if (classInfo == null) {
            try {
                classInfo = new ClassInfo(MixinService.getService().getBytecodeProvider().getClassNode(replace));
            }
            catch (Exception ex) {
                ClassInfo.logger.catching(Level.TRACE, (Throwable)ex);
                ClassInfo.logger.warn("Error loading class: {} ({}: {})", new Object[] { replace, ex.getClass().getName(), ex.getMessage() });
            }
            ClassInfo.cache.put(replace, classInfo);
            ClassInfo.logger.trace("Added class metadata for {} to metadata cache", new Object[] { replace });
        }
        return classInfo;
    }
    
    public static ClassInfo forType(final Type type) {
        if (type.getSort() == 9) {
            return forType(type.getElementType());
        }
        if (type.getSort() < 9) {
            return null;
        }
        return forName(type.getClassName().replace('.', '/'));
    }
    
    public static ClassInfo getCommonSuperClass(final String s, final String s2) {
        if (s == null || s2 == null) {
            return ClassInfo.OBJECT;
        }
        return getCommonSuperClass(forName(s), forName(s2));
    }
    
    public static ClassInfo getCommonSuperClass(final Type type, final Type type2) {
        if (type == null || type2 == null || type.getSort() != 10 || type2.getSort() != 10) {
            return ClassInfo.OBJECT;
        }
        return getCommonSuperClass(forType(type), forType(type2));
    }
    
    private static ClassInfo getCommonSuperClass(final ClassInfo classInfo, final ClassInfo classInfo2) {
        return getCommonSuperClass(classInfo, classInfo2, false);
    }
    
    public static ClassInfo getCommonSuperClassOrInterface(final String s, final String s2) {
        if (s == null || s2 == null) {
            return ClassInfo.OBJECT;
        }
        return getCommonSuperClassOrInterface(forName(s), forName(s2));
    }
    
    public static ClassInfo getCommonSuperClassOrInterface(final Type type, final Type type2) {
        if (type == null || type2 == null || type.getSort() != 10 || type2.getSort() != 10) {
            return ClassInfo.OBJECT;
        }
        return getCommonSuperClassOrInterface(forType(type), forType(type2));
    }
    
    public static ClassInfo getCommonSuperClassOrInterface(final ClassInfo classInfo, final ClassInfo classInfo2) {
        return getCommonSuperClass(classInfo, classInfo2, true);
    }
    
    private static ClassInfo getCommonSuperClass(ClassInfo superClass, final ClassInfo classInfo, final boolean b) {
        if (superClass.hasSuperClass(classInfo, Traversal.NONE, b)) {
            return classInfo;
        }
        if (classInfo.hasSuperClass(superClass, Traversal.NONE, b)) {
            return superClass;
        }
        if (superClass.isInterface() || classInfo.isInterface()) {
            return ClassInfo.OBJECT;
        }
        do {
            superClass = superClass.getSuperClass();
            if (superClass == null) {
                return ClassInfo.OBJECT;
            }
        } while (!classInfo.hasSuperClass(superClass, Traversal.NONE, b));
        return superClass;
    }
    
    static {
        logger = LogManager.getLogger("mixin");
        profiler = MixinEnvironment.getProfiler();
        cache = new HashMap<String, ClassInfo>();
        OBJECT = new ClassInfo();
        ClassInfo.cache.put("java/lang/Object", ClassInfo.OBJECT);
    }
    
    public enum SearchType
    {
        ALL_CLASSES, 
        SUPER_CLASSES_ONLY;
        
        private static final SearchType[] $VALUES;
        
        public static SearchType[] values() {
            return SearchType.$VALUES.clone();
        }
        
        public static SearchType valueOf(final String s) {
            return Enum.valueOf(SearchType.class, s);
        }
        
        static {
            $VALUES = new SearchType[] { SearchType.ALL_CLASSES, SearchType.SUPER_CLASSES_ONLY };
        }
    }
    
    public enum Traversal
    {
        NONE((Traversal)null, false, SearchType.SUPER_CLASSES_ONLY), 
        ALL((Traversal)null, true, SearchType.ALL_CLASSES), 
        IMMEDIATE(Traversal.NONE, true, SearchType.SUPER_CLASSES_ONLY), 
        SUPER(Traversal.ALL, false, SearchType.SUPER_CLASSES_ONLY);
        
        private final Traversal next;
        private final boolean traverse;
        private final SearchType searchType;
        private static final Traversal[] $VALUES;
        
        public static Traversal[] values() {
            return Traversal.$VALUES.clone();
        }
        
        public static Traversal valueOf(final String s) {
            return Enum.valueOf(Traversal.class, s);
        }
        
        private Traversal(final Traversal traversal, final boolean traverse, final SearchType searchType) {
            this.next = ((traversal != null) ? traversal : this);
            this.traverse = traverse;
            this.searchType = searchType;
        }
        
        public Traversal next() {
            return this.next;
        }
        
        public boolean canTraverse() {
            return this.traverse;
        }
        
        public SearchType getSearchType() {
            return this.searchType;
        }
        
        static {
            $VALUES = new Traversal[] { Traversal.NONE, Traversal.ALL, Traversal.IMMEDIATE, Traversal.SUPER };
        }
    }
    
    public static class FrameData
    {
        private static final String[] FRAMETYPES;
        public final int index;
        public final int type;
        public final int locals;
        
        FrameData(final int index, final int type, final int locals) {
            super();
            this.index = index;
            this.type = type;
            this.locals = locals;
        }
        
        FrameData(final int index, final FrameNode frameNode) {
            super();
            this.index = index;
            this.type = frameNode.type;
            this.locals = ((frameNode.local != null) ? frameNode.local.size() : 0);
        }
        
        @Override
        public String toString() {
            return String.format("FrameData[index=%d, type=%s, locals=%d]", this.index, FrameData.FRAMETYPES[this.type + 1], this.locals);
        }
        
        static {
            FRAMETYPES = new String[] { "NEW", "FULL", "APPEND", "CHOP", "SAME", "SAME1" };
        }
    }
    
    abstract static class Member
    {
        private final Type type;
        private final String memberName;
        private final String memberDesc;
        private final boolean isInjected;
        private final int modifiers;
        private String currentName;
        private String currentDesc;
        private boolean decoratedFinal;
        private boolean decoratedMutable;
        private boolean unique;
        
        protected Member(final Member member) {
            this(member.type, member.memberName, member.memberDesc, member.modifiers, member.isInjected);
            this.currentName = member.currentName;
            this.currentDesc = member.currentDesc;
            this.unique = member.unique;
        }
        
        protected Member(final Type type, final String s, final String s2, final int n) {
            this(type, s, s2, n, false);
        }
        
        protected Member(final Type type, final String s, final String s2, final int modifiers, final boolean isInjected) {
            super();
            this.type = type;
            this.memberName = s;
            this.memberDesc = s2;
            this.isInjected = isInjected;
            this.currentName = s;
            this.currentDesc = s2;
            this.modifiers = modifiers;
        }
        
        public String getOriginalName() {
            return this.memberName;
        }
        
        public String getName() {
            return this.currentName;
        }
        
        public String getOriginalDesc() {
            return this.memberDesc;
        }
        
        public String getDesc() {
            return this.currentDesc;
        }
        
        public boolean isInjected() {
            return this.isInjected;
        }
        
        public boolean isRenamed() {
            return !this.currentName.equals(this.memberName);
        }
        
        public boolean isRemapped() {
            return !this.currentDesc.equals(this.memberDesc);
        }
        
        public boolean isPrivate() {
            return (this.modifiers & 0x2) != 0x0;
        }
        
        public boolean isStatic() {
            return (this.modifiers & 0x8) != 0x0;
        }
        
        public boolean isAbstract() {
            return (this.modifiers & 0x400) != 0x0;
        }
        
        public boolean isFinal() {
            return (this.modifiers & 0x10) != 0x0;
        }
        
        public boolean isSynthetic() {
            return (this.modifiers & 0x1000) != 0x0;
        }
        
        public boolean isUnique() {
            return this.unique;
        }
        
        public void setUnique(final boolean unique) {
            this.unique = unique;
        }
        
        public boolean isDecoratedFinal() {
            return this.decoratedFinal;
        }
        
        public boolean isDecoratedMutable() {
            return this.decoratedMutable;
        }
        
        public void setDecoratedFinal(final boolean decoratedFinal, final boolean decoratedMutable) {
            this.decoratedFinal = decoratedFinal;
            this.decoratedMutable = decoratedMutable;
        }
        
        public boolean matchesFlags(final int n) {
            return ((~this.modifiers | (n & 0x2)) & 0x2) != 0x0 && ((~this.modifiers | (n & 0x8)) & 0x8) != 0x0;
        }
        
        public abstract ClassInfo getOwner();
        
        public ClassInfo getImplementor() {
            return this.getOwner();
        }
        
        public int getAccess() {
            return this.modifiers;
        }
        
        public String renameTo(final String currentName) {
            return this.currentName = currentName;
        }
        
        public String remapTo(final String currentDesc) {
            return this.currentDesc = currentDesc;
        }
        
        public boolean equals(final String s, final String s2) {
            return (this.memberName.equals(s) || this.currentName.equals(s)) && (this.memberDesc.equals(s2) || this.currentDesc.equals(s2));
        }
        
        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof Member)) {
                return false;
            }
            final Member member = (Member)o;
            return (member.memberName.equals(this.memberName) || member.currentName.equals(this.currentName)) && (member.memberDesc.equals(this.memberDesc) || member.currentDesc.equals(this.currentDesc));
        }
        
        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
        
        @Override
        public String toString() {
            return String.format(this.getDisplayFormat(), this.memberName, this.memberDesc);
        }
        
        protected String getDisplayFormat() {
            return "%s%s";
        }
        
        enum Type
        {
            METHOD, 
            FIELD;
            
            private static final Type[] $VALUES;
            
            public static Type[] values() {
                return Type.$VALUES.clone();
            }
            
            public static Type valueOf(final String s) {
                return Enum.valueOf(Type.class, s);
            }
            
            static {
                $VALUES = new Type[] { Type.METHOD, Type.FIELD };
            }
        }
    }
    
    public class Method extends Member
    {
        private final List<FrameData> frames;
        private boolean isAccessor;
        final ClassInfo this$0;
        
        public Method(final ClassInfo this$0, final Member member) {
            this.this$0 = this$0;
            super(member);
            this.frames = ((member instanceof Method) ? ((Method)member).frames : null);
        }
        
        public Method(final ClassInfo classInfo, final MethodNode methodNode) {
            this(classInfo, methodNode, false);
            this.setUnique(Annotations.getVisible(methodNode, Unique.class) != null);
            this.isAccessor = (Annotations.getSingleVisible(methodNode, Accessor.class, Invoker.class) != null);
        }
        
        public Method(final ClassInfo this$0, final MethodNode methodNode, final boolean b) {
            this.this$0 = this$0;
            super(Type.METHOD, methodNode.name, methodNode.desc, methodNode.access, b);
            this.frames = this.gatherFrames(methodNode);
            this.setUnique(Annotations.getVisible(methodNode, Unique.class) != null);
            this.isAccessor = (Annotations.getSingleVisible(methodNode, Accessor.class, Invoker.class) != null);
        }
        
        public Method(final ClassInfo this$0, final String s, final String s2) {
            this.this$0 = this$0;
            super(Type.METHOD, s, s2, 1, false);
            this.frames = null;
        }
        
        public Method(final ClassInfo this$0, final String s, final String s2, final int n) {
            this.this$0 = this$0;
            super(Type.METHOD, s, s2, n, false);
            this.frames = null;
        }
        
        public Method(final ClassInfo this$0, final String s, final String s2, final int n, final boolean b) {
            this.this$0 = this$0;
            super(Type.METHOD, s, s2, n, b);
            this.frames = null;
        }
        
        private List<FrameData> gatherFrames(final MethodNode methodNode) {
            final ArrayList<FrameData> list = new ArrayList<FrameData>();
            for (final AbstractInsnNode abstractInsnNode : methodNode.instructions) {
                if (abstractInsnNode instanceof FrameNode) {
                    list.add(new FrameData(methodNode.instructions.indexOf(abstractInsnNode), (FrameNode)abstractInsnNode));
                }
            }
            return list;
        }
        
        public List<FrameData> getFrames() {
            return this.frames;
        }
        
        @Override
        public ClassInfo getOwner() {
            return this.this$0;
        }
        
        public boolean isAccessor() {
            return this.isAccessor;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof Method && super.equals(o);
        }
        
        @Override
        public String toString() {
            return super.toString();
        }
        
        @Override
        public int hashCode() {
            return super.hashCode();
        }
        
        @Override
        public boolean equals(final String s, final String s2) {
            return super.equals(s, s2);
        }
        
        @Override
        public String remapTo(final String s) {
            return super.remapTo(s);
        }
        
        @Override
        public String renameTo(final String s) {
            return super.renameTo(s);
        }
        
        @Override
        public int getAccess() {
            return super.getAccess();
        }
        
        @Override
        public ClassInfo getImplementor() {
            return super.getImplementor();
        }
        
        @Override
        public boolean matchesFlags(final int n) {
            return super.matchesFlags(n);
        }
        
        @Override
        public void setDecoratedFinal(final boolean b, final boolean b2) {
            super.setDecoratedFinal(b, b2);
        }
        
        @Override
        public boolean isDecoratedMutable() {
            return super.isDecoratedMutable();
        }
        
        @Override
        public boolean isDecoratedFinal() {
            return super.isDecoratedFinal();
        }
        
        @Override
        public void setUnique(final boolean unique) {
            super.setUnique(unique);
        }
        
        @Override
        public boolean isUnique() {
            return super.isUnique();
        }
        
        @Override
        public boolean isSynthetic() {
            return super.isSynthetic();
        }
        
        @Override
        public boolean isFinal() {
            return super.isFinal();
        }
        
        @Override
        public boolean isAbstract() {
            return super.isAbstract();
        }
        
        @Override
        public boolean isStatic() {
            return super.isStatic();
        }
        
        @Override
        public boolean isPrivate() {
            return super.isPrivate();
        }
        
        @Override
        public boolean isRemapped() {
            return super.isRemapped();
        }
        
        @Override
        public boolean isRenamed() {
            return super.isRenamed();
        }
        
        @Override
        public boolean isInjected() {
            return super.isInjected();
        }
        
        @Override
        public String getDesc() {
            return super.getDesc();
        }
        
        @Override
        public String getOriginalDesc() {
            return super.getOriginalDesc();
        }
        
        @Override
        public String getName() {
            return super.getName();
        }
        
        @Override
        public String getOriginalName() {
            return super.getOriginalName();
        }
    }
    
    public class InterfaceMethod extends Method
    {
        private final ClassInfo owner;
        final ClassInfo this$0;
        
        public InterfaceMethod(final ClassInfo this$0, final Member member) {
            this.this$0 = this$0.super(member);
            this.owner = member.getOwner();
        }
        
        @Override
        public ClassInfo getOwner() {
            return this.owner;
        }
        
        @Override
        public ClassInfo getImplementor() {
            return this.this$0;
        }
    }
    
    class Field extends Member
    {
        final ClassInfo this$0;
        
        public Field(final ClassInfo this$0, final Member member) {
            this.this$0 = this$0;
            super(member);
        }
        
        public Field(final ClassInfo classInfo, final FieldNode fieldNode) {
            this(classInfo, fieldNode, false);
        }
        
        public Field(final ClassInfo this$0, final FieldNode fieldNode, final boolean b) {
            this.this$0 = this$0;
            super(Type.FIELD, fieldNode.name, fieldNode.desc, fieldNode.access, b);
            this.setUnique(Annotations.getVisible(fieldNode, Unique.class) != null);
            if (Annotations.getVisible(fieldNode, Shadow.class) != null) {
                this.setDecoratedFinal(Annotations.getVisible(fieldNode, Final.class) != null, Annotations.getVisible(fieldNode, Mutable.class) != null);
            }
        }
        
        public Field(final ClassInfo this$0, final String s, final String s2, final int n) {
            this.this$0 = this$0;
            super(Type.FIELD, s, s2, n, false);
        }
        
        public Field(final ClassInfo this$0, final String s, final String s2, final int n, final boolean b) {
            this.this$0 = this$0;
            super(Type.FIELD, s, s2, n, b);
        }
        
        @Override
        public ClassInfo getOwner() {
            return this.this$0;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof Field && super.equals(o);
        }
        
        @Override
        protected String getDisplayFormat() {
            return "%s:%s";
        }
    }
}
