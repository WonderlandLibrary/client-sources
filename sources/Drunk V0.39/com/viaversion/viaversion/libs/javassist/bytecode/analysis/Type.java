/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.javassist.bytecode.analysis;

import com.viaversion.viaversion.libs.javassist.ClassPool;
import com.viaversion.viaversion.libs.javassist.CtClass;
import com.viaversion.viaversion.libs.javassist.NotFoundException;
import com.viaversion.viaversion.libs.javassist.bytecode.analysis.MultiArrayType;
import com.viaversion.viaversion.libs.javassist.bytecode.analysis.MultiType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

public class Type {
    private final CtClass clazz;
    private final boolean special;
    private static final Map<CtClass, Type> prims = new IdentityHashMap<CtClass, Type>();
    public static final Type DOUBLE = new Type(CtClass.doubleType);
    public static final Type BOOLEAN = new Type(CtClass.booleanType);
    public static final Type LONG = new Type(CtClass.longType);
    public static final Type CHAR = new Type(CtClass.charType);
    public static final Type BYTE = new Type(CtClass.byteType);
    public static final Type SHORT = new Type(CtClass.shortType);
    public static final Type INTEGER = new Type(CtClass.intType);
    public static final Type FLOAT = new Type(CtClass.floatType);
    public static final Type VOID = new Type(CtClass.voidType);
    public static final Type UNINIT = new Type(null);
    public static final Type RETURN_ADDRESS = new Type(null, true);
    public static final Type TOP = new Type(null, true);
    public static final Type BOGUS = new Type(null, true);
    public static final Type OBJECT = Type.lookupType("java.lang.Object");
    public static final Type SERIALIZABLE = Type.lookupType("java.io.Serializable");
    public static final Type CLONEABLE = Type.lookupType("java.lang.Cloneable");
    public static final Type THROWABLE = Type.lookupType("java.lang.Throwable");

    public static Type get(CtClass clazz) {
        Type type;
        Type type2 = prims.get(clazz);
        if (type2 != null) {
            type = type2;
            return type;
        }
        type = new Type(clazz);
        return type;
    }

    private static Type lookupType(String name) {
        try {
            return new Type(ClassPool.getDefault().get(name));
        }
        catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    Type(CtClass clazz) {
        this(clazz, false);
    }

    private Type(CtClass clazz, boolean special) {
        this.clazz = clazz;
        this.special = special;
    }

    boolean popChanged() {
        return false;
    }

    public int getSize() {
        if (this.clazz == CtClass.doubleType) return 2;
        if (this.clazz == CtClass.longType) return 2;
        if (this == TOP) return 2;
        return 1;
    }

    public CtClass getCtClass() {
        return this.clazz;
    }

    public boolean isReference() {
        if (this.special) return false;
        if (this.clazz == null) return true;
        if (this.clazz.isPrimitive()) return false;
        return true;
    }

    public boolean isSpecial() {
        return this.special;
    }

    public boolean isArray() {
        if (this.clazz == null) return false;
        if (!this.clazz.isArray()) return false;
        return true;
    }

    public int getDimensions() {
        if (!this.isArray()) {
            return 0;
        }
        String name = this.clazz.getName();
        int pos = name.length() - 1;
        int count = 0;
        while (name.charAt(pos) == ']') {
            pos -= 2;
            ++count;
        }
        return count;
    }

    public Type getComponent() {
        Type type;
        CtClass component;
        if (this.clazz == null) return null;
        if (!this.clazz.isArray()) {
            return null;
        }
        try {
            component = this.clazz.getComponentType();
        }
        catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        Type type2 = prims.get(component);
        if (type2 != null) {
            type = type2;
            return type;
        }
        type = new Type(component);
        return type;
    }

    public boolean isAssignableFrom(Type type) {
        if (this == type) {
            return true;
        }
        if (type == UNINIT) {
            if (this.isReference()) return true;
        }
        if (this == UNINIT && type.isReference()) {
            return true;
        }
        if (type instanceof MultiType) {
            return ((MultiType)type).isAssignableTo(this);
        }
        if (type instanceof MultiArrayType) {
            return ((MultiArrayType)type).isAssignableTo(this);
        }
        if (this.clazz == null) return false;
        if (this.clazz.isPrimitive()) {
            return false;
        }
        try {
            return type.clazz.subtypeOf(this.clazz);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Type merge(Type type) {
        if (type == this) {
            return this;
        }
        if (type == null) {
            return this;
        }
        if (type == UNINIT) {
            return this;
        }
        if (this == UNINIT) {
            return type;
        }
        if (!type.isReference()) return BOGUS;
        if (!this.isReference()) {
            return BOGUS;
        }
        if (type instanceof MultiType) {
            return type.merge(this);
        }
        if (type.isArray() && this.isArray()) {
            return this.mergeArray(type);
        }
        try {
            return this.mergeClasses(type);
        }
        catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    Type getRootComponent(Type type) {
        while (type.isArray()) {
            type = type.getComponent();
        }
        return type;
    }

    private Type createArray(Type rootComponent, int dims) {
        if (rootComponent instanceof MultiType) {
            return new MultiArrayType((MultiType)rootComponent, dims);
        }
        String name = this.arrayName(rootComponent.clazz.getName(), dims);
        try {
            return Type.get(this.getClassPool(rootComponent).get(name));
        }
        catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    String arrayName(String component, int dims) {
        int i = component.length();
        int size = i + dims * 2;
        char[] string = new char[size];
        component.getChars(0, i, string, 0);
        while (i < size) {
            string[i++] = 91;
            string[i++] = 93;
        }
        return new String(string);
    }

    private ClassPool getClassPool(Type rootComponent) {
        ClassPool classPool;
        ClassPool pool = rootComponent.clazz.getClassPool();
        if (pool != null) {
            classPool = pool;
            return classPool;
        }
        classPool = ClassPool.getDefault();
        return classPool;
    }

    private Type mergeArray(Type type) {
        int targetDims;
        Type targetRoot;
        int thisDims;
        Type typeRoot = this.getRootComponent(type);
        Type thisRoot = this.getRootComponent(this);
        int typeDims = type.getDimensions();
        if (typeDims == (thisDims = this.getDimensions())) {
            Type mergedComponent = thisRoot.merge(typeRoot);
            if (mergedComponent != BOGUS) return this.createArray(mergedComponent, thisDims);
            return OBJECT;
        }
        if (typeDims < thisDims) {
            targetRoot = typeRoot;
            targetDims = typeDims;
        } else {
            targetRoot = thisRoot;
            targetDims = thisDims;
        }
        if (Type.eq(Type.CLONEABLE.clazz, targetRoot.clazz)) return this.createArray(targetRoot, targetDims);
        if (!Type.eq(Type.SERIALIZABLE.clazz, targetRoot.clazz)) return this.createArray(OBJECT, targetDims);
        return this.createArray(targetRoot, targetDims);
    }

    private static CtClass findCommonSuperClass(CtClass one, CtClass two) throws NotFoundException {
        CtClass shallow;
        CtClass deep = one;
        CtClass backupShallow = shallow = two;
        CtClass backupDeep = deep;
        while (true) {
            if (Type.eq(deep, shallow) && deep.getSuperclass() != null) {
                return deep;
            }
            CtClass deepSuper = deep.getSuperclass();
            CtClass shallowSuper = shallow.getSuperclass();
            if (shallowSuper == null) {
                shallow = backupShallow;
                break;
            }
            if (deepSuper == null) {
                deep = backupDeep;
                backupDeep = backupShallow;
                backupShallow = deep;
                deep = shallow;
                shallow = backupShallow;
                break;
            }
            deep = deepSuper;
            shallow = shallowSuper;
        }
        while (true) {
            if ((deep = deep.getSuperclass()) == null) break;
            backupDeep = backupDeep.getSuperclass();
        }
        deep = backupDeep;
        while (!Type.eq(deep, shallow)) {
            deep = deep.getSuperclass();
            shallow = shallow.getSuperclass();
        }
        return deep;
    }

    private Type mergeClasses(Type type) throws NotFoundException {
        CtClass superClass = Type.findCommonSuperClass(this.clazz, type.clazz);
        if (superClass.getSuperclass() != null) {
            Map<String, CtClass> commonDeclared = this.findExclusiveDeclaredInterfaces(type, superClass);
            if (commonDeclared.size() <= 0) return new Type(superClass);
            return new MultiType(commonDeclared, new Type(superClass));
        }
        Map<String, CtClass> interfaces = this.findCommonInterfaces(type);
        if (interfaces.size() == 1) {
            return new Type(interfaces.values().iterator().next());
        }
        if (interfaces.size() <= 1) return new Type(superClass);
        return new MultiType(interfaces);
    }

    private Map<String, CtClass> findCommonInterfaces(Type type) {
        Map<String, CtClass> typeMap = this.getAllInterfaces(type.clazz, null);
        Map<String, CtClass> thisMap = this.getAllInterfaces(this.clazz, null);
        return this.findCommonInterfaces(typeMap, thisMap);
    }

    private Map<String, CtClass> findExclusiveDeclaredInterfaces(Type type, CtClass exclude) {
        Map<String, CtClass> typeMap = this.getDeclaredInterfaces(type.clazz, null);
        Map<String, CtClass> thisMap = this.getDeclaredInterfaces(this.clazz, null);
        Map<String, CtClass> excludeMap = this.getAllInterfaces(exclude, null);
        Iterator<String> iterator = excludeMap.keySet().iterator();
        while (iterator.hasNext()) {
            String intf = iterator.next();
            typeMap.remove(intf);
            thisMap.remove(intf);
        }
        return this.findCommonInterfaces(typeMap, thisMap);
    }

    Map<String, CtClass> findCommonInterfaces(Map<String, CtClass> typeMap, Map<String, CtClass> alterMap) {
        if (alterMap == null) {
            alterMap = new HashMap<String, CtClass>();
        }
        if (typeMap == null || typeMap.isEmpty()) {
            alterMap.clear();
        }
        Iterator<String> it = alterMap.keySet().iterator();
        while (it.hasNext()) {
            String name = it.next();
            if (typeMap.containsKey(name)) continue;
            it.remove();
        }
        ArrayList<CtClass> interfaces = new ArrayList<CtClass>();
        for (CtClass intf : alterMap.values()) {
            try {
                interfaces.addAll(Arrays.asList(intf.getInterfaces()));
            }
            catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        Iterator<CtClass> iterator = interfaces.iterator();
        while (iterator.hasNext()) {
            CtClass c = iterator.next();
            alterMap.remove(c.getName());
        }
        return alterMap;
    }

    Map<String, CtClass> getAllInterfaces(CtClass clazz, Map<String, CtClass> map) {
        if (map == null) {
            map = new HashMap<String, CtClass>();
        }
        if (clazz.isInterface()) {
            map.put(clazz.getName(), clazz);
        }
        do {
            try {
                CtClass[] interfaces;
                for (CtClass intf : interfaces = clazz.getInterfaces()) {
                    map.put(intf.getName(), intf);
                    this.getAllInterfaces(intf, map);
                }
                clazz = clazz.getSuperclass();
            }
            catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        } while (clazz != null);
        return map;
    }

    Map<String, CtClass> getDeclaredInterfaces(CtClass clazz, Map<String, CtClass> map) {
        CtClass[] interfaces;
        if (map == null) {
            map = new HashMap<String, CtClass>();
        }
        if (clazz.isInterface()) {
            map.put(clazz.getName(), clazz);
        }
        try {
            interfaces = clazz.getInterfaces();
        }
        catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        CtClass[] ctClassArray = interfaces;
        int n = ctClassArray.length;
        int n2 = 0;
        while (n2 < n) {
            CtClass intf = ctClassArray[n2];
            map.put(intf.getName(), intf);
            this.getDeclaredInterfaces(intf, map);
            ++n2;
        }
        return map;
    }

    public int hashCode() {
        return this.getClass().hashCode() + this.clazz.hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof Type)) {
            return false;
        }
        if (o.getClass() != this.getClass()) return false;
        if (!Type.eq(this.clazz, ((Type)o).clazz)) return false;
        return true;
    }

    static boolean eq(CtClass one, CtClass two) {
        if (one == two) return true;
        if (one == null) return false;
        if (two == null) return false;
        if (!one.getName().equals(two.getName())) return false;
        return true;
    }

    public String toString() {
        if (this == BOGUS) {
            return "BOGUS";
        }
        if (this == UNINIT) {
            return "UNINIT";
        }
        if (this == RETURN_ADDRESS) {
            return "RETURN ADDRESS";
        }
        if (this == TOP) {
            return "TOP";
        }
        if (this.clazz == null) {
            return "null";
        }
        String string = this.clazz.getName();
        return string;
    }

    static {
        prims.put(CtClass.doubleType, DOUBLE);
        prims.put(CtClass.longType, LONG);
        prims.put(CtClass.charType, CHAR);
        prims.put(CtClass.shortType, SHORT);
        prims.put(CtClass.intType, INTEGER);
        prims.put(CtClass.floatType, FLOAT);
        prims.put(CtClass.byteType, BYTE);
        prims.put(CtClass.booleanType, BOOLEAN);
        prims.put(CtClass.voidType, VOID);
    }
}

