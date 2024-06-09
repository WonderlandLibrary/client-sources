/*
 * Decompiled with CFR 0.143.
 */
package javassist.bytecode.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.analysis.MultiArrayType;
import javassist.bytecode.analysis.MultiType;

public class Type {
    private final CtClass clazz;
    private final boolean special;
    private static final Map prims = new IdentityHashMap();
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
        Type type = (Type)prims.get(clazz);
        return type != null ? type : new Type(clazz);
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
        return this.clazz == CtClass.doubleType || this.clazz == CtClass.longType || this == TOP ? 2 : 1;
    }

    public CtClass getCtClass() {
        return this.clazz;
    }

    public boolean isReference() {
        return !this.special && (this.clazz == null || !this.clazz.isPrimitive());
    }

    public boolean isSpecial() {
        return this.special;
    }

    public boolean isArray() {
        return this.clazz != null && this.clazz.isArray();
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
        CtClass component;
        if (this.clazz == null || !this.clazz.isArray()) {
            return null;
        }
        try {
            component = this.clazz.getComponentType();
        }
        catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        Type type = (Type)prims.get(component);
        return type != null ? type : new Type(component);
    }

    public boolean isAssignableFrom(Type type) {
        if (this == type) {
            return true;
        }
        if (type == UNINIT && this.isReference() || this == UNINIT && type.isReference()) {
            return true;
        }
        if (type instanceof MultiType) {
            return ((MultiType)type).isAssignableTo(this);
        }
        if (type instanceof MultiArrayType) {
            return ((MultiArrayType)type).isAssignableTo(this);
        }
        if (this.clazz == null || this.clazz.isPrimitive()) {
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
        if (!type.isReference() || !this.isReference()) {
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
        Type type;
        if (rootComponent instanceof MultiType) {
            return new MultiArrayType((MultiType)rootComponent, dims);
        }
        String name = this.arrayName(rootComponent.clazz.getName(), dims);
        try {
            type = Type.get(this.getClassPool(rootComponent).get(name));
        }
        catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        return type;
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
        component = new String(string);
        return component;
    }

    private ClassPool getClassPool(Type rootComponent) {
        ClassPool pool = rootComponent.clazz.getClassPool();
        return pool != null ? pool : ClassPool.getDefault();
    }

    private Type mergeArray(Type type) {
        Type targetRoot;
        int targetDims;
        int thisDims;
        Type typeRoot = this.getRootComponent(type);
        Type thisRoot = this.getRootComponent(this);
        int typeDims = type.getDimensions();
        if (typeDims == (thisDims = this.getDimensions())) {
            Type mergedComponent = thisRoot.merge(typeRoot);
            if (mergedComponent == BOGUS) {
                return OBJECT;
            }
            return this.createArray(mergedComponent, thisDims);
        }
        if (typeDims < thisDims) {
            targetRoot = typeRoot;
            targetDims = typeDims;
        } else {
            targetRoot = thisRoot;
            targetDims = thisDims;
        }
        if (Type.eq(Type.CLONEABLE.clazz, targetRoot.clazz) || Type.eq(Type.SERIALIZABLE.clazz, targetRoot.clazz)) {
            return this.createArray(targetRoot, targetDims);
        }
        return this.createArray(OBJECT, targetDims);
    }

    private static CtClass findCommonSuperClass(CtClass one, CtClass two) throws NotFoundException {
        CtClass shallow;
        CtClass deep = one;
        CtClass backupShallow = shallow = two;
        CtClass backupDeep = deep;
        do {
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
        } while (true);
        while ((deep = deep.getSuperclass()) != null) {
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
        if (superClass.getSuperclass() == null) {
            Map interfaces = this.findCommonInterfaces(type);
            if (interfaces.size() == 1) {
                return new Type((CtClass)interfaces.values().iterator().next());
            }
            if (interfaces.size() > 1) {
                return new MultiType(interfaces);
            }
            return new Type(superClass);
        }
        Map commonDeclared = this.findExclusiveDeclaredInterfaces(type, superClass);
        if (commonDeclared.size() > 0) {
            return new MultiType(commonDeclared, new Type(superClass));
        }
        return new Type(superClass);
    }

    private Map findCommonInterfaces(Type type) {
        Map typeMap = this.getAllInterfaces(type.clazz, null);
        Map thisMap = this.getAllInterfaces(this.clazz, null);
        return this.findCommonInterfaces(typeMap, thisMap);
    }

    private Map findExclusiveDeclaredInterfaces(Type type, CtClass exclude) {
        Map typeMap = this.getDeclaredInterfaces(type.clazz, null);
        Map thisMap = this.getDeclaredInterfaces(this.clazz, null);
        Map excludeMap = this.getAllInterfaces(exclude, null);
        for (Object intf : excludeMap.keySet()) {
            typeMap.remove(intf);
            thisMap.remove(intf);
        }
        return this.findCommonInterfaces(typeMap, thisMap);
    }

    Map findCommonInterfaces(Map typeMap, Map alterMap) {
        Iterator<Object> i = alterMap.keySet().iterator();
        while (i.hasNext()) {
            if (typeMap.containsKey(i.next())) continue;
            i.remove();
        }
        for (CtClass intf : new ArrayList(alterMap.values())) {
            CtClass[] interfaces;
            try {
                interfaces = intf.getInterfaces();
            }
            catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
            for (int c = 0; c < interfaces.length; ++c) {
                alterMap.remove(interfaces[c].getName());
            }
        }
        return alterMap;
    }

    Map getAllInterfaces(CtClass clazz, Map map) {
        if (map == null) {
            map = new HashMap<String, CtClass>();
        }
        if (clazz.isInterface()) {
            map.put(clazz.getName(), clazz);
        }
        do {
            try {
                CtClass[] interfaces = clazz.getInterfaces();
                for (int i = 0; i < interfaces.length; ++i) {
                    CtClass intf = interfaces[i];
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

    Map getDeclaredInterfaces(CtClass clazz, Map map) {
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
        for (int i = 0; i < interfaces.length; ++i) {
            CtClass intf = interfaces[i];
            map.put(intf.getName(), intf);
            this.getDeclaredInterfaces(intf, map);
        }
        return map;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Type)) {
            return false;
        }
        return o.getClass() == this.getClass() && Type.eq(this.clazz, ((Type)o).clazz);
    }

    static boolean eq(CtClass one, CtClass two) {
        return one == two || one != null && two != null && one.getName().equals(two.getName());
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
        return this.clazz == null ? "null" : this.clazz.getName();
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

