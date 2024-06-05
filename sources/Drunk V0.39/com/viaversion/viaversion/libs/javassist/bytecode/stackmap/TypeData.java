/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.javassist.bytecode.stackmap;

import com.viaversion.viaversion.libs.javassist.ClassPool;
import com.viaversion.viaversion.libs.javassist.CtClass;
import com.viaversion.viaversion.libs.javassist.NotFoundException;
import com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import com.viaversion.viaversion.libs.javassist.bytecode.Descriptor;
import com.viaversion.viaversion.libs.javassist.bytecode.stackmap.TypeTag;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class TypeData {
    public static TypeData[] make(int size) {
        TypeData[] array = new TypeData[size];
        int i = 0;
        while (i < size) {
            array[i] = TypeTag.TOP;
            ++i;
        }
        return array;
    }

    protected TypeData() {
    }

    private static void setType(TypeData td, String className, ClassPool cp) throws BadBytecode {
        td.setType(className, cp);
    }

    public abstract int getTypeTag();

    public abstract int getTypeData(ConstPool var1);

    public TypeData join() {
        return new TypeVar(this);
    }

    public abstract BasicType isBasicType();

    public abstract boolean is2WordType();

    public boolean isNullType() {
        return false;
    }

    public boolean isUninit() {
        return false;
    }

    public abstract boolean eq(TypeData var1);

    public abstract String getName();

    public abstract void setType(String var1, ClassPool var2) throws BadBytecode;

    public abstract TypeData getArrayType(int var1) throws NotFoundException;

    public int dfs(List<TypeData> order, int index, ClassPool cp) throws NotFoundException {
        return index;
    }

    protected TypeVar toTypeVar(int dim) {
        return null;
    }

    public void constructorCalled(int offset) {
    }

    public String toString() {
        return super.toString() + "(" + this.toString2(new HashSet<TypeData>()) + ")";
    }

    abstract String toString2(Set<TypeData> var1);

    public static CtClass commonSuperClassEx(CtClass one, CtClass two) throws NotFoundException {
        if (one == two) {
            return one;
        }
        if (one.isArray() && two.isArray()) {
            String string;
            CtClass ele2;
            CtClass ele1 = one.getComponentType();
            CtClass element = TypeData.commonSuperClassEx(ele1, ele2 = two.getComponentType());
            if (element == ele1) {
                return one;
            }
            if (element == ele2) {
                return two;
            }
            ClassPool classPool = one.getClassPool();
            if (element == null) {
                string = "java.lang.Object";
                return classPool.get(string);
            }
            string = element.getName() + "[]";
            return classPool.get(string);
        }
        if (one.isPrimitive()) return null;
        if (two.isPrimitive()) {
            return null;
        }
        if (one.isArray()) return one.getClassPool().get("java.lang.Object");
        if (!two.isArray()) return TypeData.commonSuperClass(one, two);
        return one.getClassPool().get("java.lang.Object");
    }

    public static CtClass commonSuperClass(CtClass one, CtClass two) throws NotFoundException {
        CtClass shallow;
        CtClass deep = one;
        CtClass backupShallow = shallow = two;
        CtClass backupDeep = deep;
        while (true) {
            if (TypeData.eq(deep, shallow) && deep.getSuperclass() != null) {
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
        while (!TypeData.eq(deep, shallow)) {
            deep = deep.getSuperclass();
            shallow = shallow.getSuperclass();
        }
        return deep;
    }

    static boolean eq(CtClass one, CtClass two) {
        if (one == two) return true;
        if (one == null) return false;
        if (two == null) return false;
        if (!one.getName().equals(two.getName())) return false;
        return true;
    }

    public static void aastore(TypeData array, TypeData value, ClassPool cp) throws BadBytecode {
        if (array instanceof AbsTypeVar && !value.isNullType()) {
            ((AbsTypeVar)array).merge(ArrayType.make(value));
        }
        if (!(value instanceof AbsTypeVar)) return;
        if (array instanceof AbsTypeVar) {
            ArrayElement.make(array);
            return;
        }
        if (!(array instanceof ClassName)) throw new BadBytecode("bad AASTORE: " + array);
        if (array.isNullType()) return;
        String type = ArrayElement.typeName(array.getName());
        value.setType(type, cp);
    }

    public static class UninitThis
    extends UninitData {
        UninitThis(String className) {
            super(-1, className);
        }

        @Override
        public UninitData copy() {
            return new UninitThis(this.getName());
        }

        @Override
        public int getTypeTag() {
            return 6;
        }

        @Override
        public int getTypeData(ConstPool cp) {
            return 0;
        }

        @Override
        String toString2(Set<TypeData> set) {
            return "uninit:this";
        }
    }

    public static class UninitData
    extends ClassName {
        int offset;
        boolean initialized;

        UninitData(int offset, String className) {
            super(className);
            this.offset = offset;
            this.initialized = false;
        }

        public UninitData copy() {
            return new UninitData(this.offset, this.getName());
        }

        @Override
        public int getTypeTag() {
            return 8;
        }

        @Override
        public int getTypeData(ConstPool cp) {
            return this.offset;
        }

        @Override
        public TypeData join() {
            if (!this.initialized) return new UninitTypeVar(this.copy());
            return new TypeVar(new ClassName(this.getName()));
        }

        @Override
        public boolean isUninit() {
            return true;
        }

        @Override
        public boolean eq(TypeData d) {
            if (!(d instanceof UninitData)) return false;
            UninitData ud = (UninitData)d;
            if (this.offset != ud.offset) return false;
            if (!this.getName().equals(ud.getName())) return false;
            return true;
        }

        public int offset() {
            return this.offset;
        }

        @Override
        public void constructorCalled(int offset) {
            if (offset != this.offset) return;
            this.initialized = true;
        }

        @Override
        String toString2(Set<TypeData> set) {
            return this.getName() + "," + this.offset;
        }
    }

    public static class NullType
    extends ClassName {
        public NullType() {
            super("null-type");
        }

        @Override
        public int getTypeTag() {
            return 5;
        }

        @Override
        public boolean isNullType() {
            return true;
        }

        @Override
        public int getTypeData(ConstPool cp) {
            return 0;
        }

        @Override
        public TypeData getArrayType(int dim) {
            return this;
        }
    }

    public static class ClassName
    extends TypeData {
        private String name;

        public ClassName(String n) {
            this.name = n;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public BasicType isBasicType() {
            return null;
        }

        @Override
        public boolean is2WordType() {
            return false;
        }

        @Override
        public int getTypeTag() {
            return 7;
        }

        @Override
        public int getTypeData(ConstPool cp) {
            return cp.addClassInfo(this.getName());
        }

        @Override
        public boolean eq(TypeData d) {
            if (!d.isUninit()) return this.name.equals(d.getName());
            return d.eq(this);
        }

        @Override
        public void setType(String typeName, ClassPool cp) throws BadBytecode {
        }

        @Override
        public TypeData getArrayType(int dim) throws NotFoundException {
            if (dim == 0) {
                return this;
            }
            if (dim > 0) {
                char[] dimType = new char[dim];
                int i = 0;
                while (true) {
                    if (i >= dim) {
                        String elementType = this.getName();
                        if (elementType.charAt(0) == '[') return new ClassName(new String(dimType) + elementType);
                        elementType = "L" + elementType.replace('.', '/') + ";";
                        return new ClassName(new String(dimType) + elementType);
                    }
                    dimType[i] = 91;
                    ++i;
                }
            }
            for (int i = 0; i < -dim; ++i) {
                if (this.name.charAt(i) == '[') continue;
                throw new NotFoundException("no " + dim + " dimensional array type: " + this.getName());
            }
            char type = this.name.charAt(-dim);
            if (type == '[') {
                return new ClassName(this.name.substring(-dim));
            }
            if (type == 'L') {
                return new ClassName(this.name.substring(-dim + 1, this.name.length() - 1).replace('/', '.'));
            }
            if (type == TypeTag.DOUBLE.decodedName) {
                return TypeTag.DOUBLE;
            }
            if (type == TypeTag.FLOAT.decodedName) {
                return TypeTag.FLOAT;
            }
            if (type != TypeTag.LONG.decodedName) return TypeTag.INTEGER;
            return TypeTag.LONG;
        }

        @Override
        String toString2(Set<TypeData> set) {
            return this.name;
        }
    }

    public static class UninitTypeVar
    extends AbsTypeVar {
        protected TypeData type;

        public UninitTypeVar(UninitData t) {
            this.type = t;
        }

        @Override
        public int getTypeTag() {
            return this.type.getTypeTag();
        }

        @Override
        public int getTypeData(ConstPool cp) {
            return this.type.getTypeData(cp);
        }

        @Override
        public BasicType isBasicType() {
            return this.type.isBasicType();
        }

        @Override
        public boolean is2WordType() {
            return this.type.is2WordType();
        }

        @Override
        public boolean isUninit() {
            return this.type.isUninit();
        }

        @Override
        public boolean eq(TypeData d) {
            return this.type.eq(d);
        }

        @Override
        public String getName() {
            return this.type.getName();
        }

        @Override
        protected TypeVar toTypeVar(int dim) {
            return null;
        }

        @Override
        public TypeData join() {
            return this.type.join();
        }

        @Override
        public void setType(String s, ClassPool cp) throws BadBytecode {
            this.type.setType(s, cp);
        }

        @Override
        public void merge(TypeData t) {
            if (t.eq(this.type)) return;
            this.type = TypeTag.TOP;
        }

        @Override
        public void constructorCalled(int offset) {
            this.type.constructorCalled(offset);
        }

        public int offset() {
            if (!(this.type instanceof UninitData)) throw new RuntimeException("not available");
            return ((UninitData)this.type).offset;
        }

        @Override
        public TypeData getArrayType(int dim) throws NotFoundException {
            return this.type.getArrayType(dim);
        }

        @Override
        String toString2(Set<TypeData> set) {
            return "";
        }
    }

    public static class ArrayElement
    extends AbsTypeVar {
        private AbsTypeVar array;

        private ArrayElement(AbsTypeVar a) {
            this.array = a;
        }

        public static TypeData make(TypeData array) throws BadBytecode {
            if (array instanceof ArrayType) {
                return ((ArrayType)array).elementType();
            }
            if (array instanceof AbsTypeVar) {
                return new ArrayElement((AbsTypeVar)array);
            }
            if (!(array instanceof ClassName)) throw new BadBytecode("bad AASTORE: " + array);
            if (array.isNullType()) throw new BadBytecode("bad AASTORE: " + array);
            return new ClassName(ArrayElement.typeName(array.getName()));
        }

        @Override
        public void merge(TypeData t) {
            try {
                if (t.isNullType()) return;
                this.array.merge(ArrayType.make(t));
                return;
            }
            catch (BadBytecode e) {
                throw new RuntimeException("fatal: " + e);
            }
        }

        @Override
        public String getName() {
            return ArrayElement.typeName(this.array.getName());
        }

        public AbsTypeVar arrayType() {
            return this.array;
        }

        @Override
        public BasicType isBasicType() {
            return null;
        }

        @Override
        public boolean is2WordType() {
            return false;
        }

        private static String typeName(String arrayType) {
            if (arrayType.length() <= 1) return "java.lang.Object";
            if (arrayType.charAt(0) != '[') return "java.lang.Object";
            char c = arrayType.charAt(1);
            if (c == 'L') {
                return arrayType.substring(2, arrayType.length() - 1).replace('/', '.');
            }
            if (c != '[') return "java.lang.Object";
            return arrayType.substring(1);
        }

        @Override
        public void setType(String s, ClassPool cp) throws BadBytecode {
            this.array.setType(ArrayType.typeName(s), cp);
        }

        @Override
        protected TypeVar toTypeVar(int dim) {
            return this.array.toTypeVar(dim - 1);
        }

        @Override
        public TypeData getArrayType(int dim) throws NotFoundException {
            return this.array.getArrayType(dim - 1);
        }

        @Override
        public int dfs(List<TypeData> order, int index, ClassPool cp) throws NotFoundException {
            return this.array.dfs(order, index, cp);
        }

        @Override
        String toString2(Set<TypeData> set) {
            return "*" + this.array.toString2(set);
        }
    }

    public static class ArrayType
    extends AbsTypeVar {
        private AbsTypeVar element;

        private ArrayType(AbsTypeVar elementType) {
            this.element = elementType;
        }

        static TypeData make(TypeData element) throws BadBytecode {
            if (element instanceof ArrayElement) {
                return ((ArrayElement)element).arrayType();
            }
            if (element instanceof AbsTypeVar) {
                return new ArrayType((AbsTypeVar)element);
            }
            if (!(element instanceof ClassName)) throw new BadBytecode("bad AASTORE: " + element);
            if (element.isNullType()) throw new BadBytecode("bad AASTORE: " + element);
            return new ClassName(ArrayType.typeName(element.getName()));
        }

        @Override
        public void merge(TypeData t) {
            try {
                if (t.isNullType()) return;
                this.element.merge(ArrayElement.make(t));
                return;
            }
            catch (BadBytecode e) {
                throw new RuntimeException("fatal: " + e);
            }
        }

        @Override
        public String getName() {
            return ArrayType.typeName(this.element.getName());
        }

        public AbsTypeVar elementType() {
            return this.element;
        }

        @Override
        public BasicType isBasicType() {
            return null;
        }

        @Override
        public boolean is2WordType() {
            return false;
        }

        public static String typeName(String elementType) {
            if (elementType.charAt(0) != '[') return "[L" + elementType.replace('.', '/') + ";";
            return "[" + elementType;
        }

        @Override
        public void setType(String s, ClassPool cp) throws BadBytecode {
            this.element.setType(ArrayElement.typeName(s), cp);
        }

        @Override
        protected TypeVar toTypeVar(int dim) {
            return this.element.toTypeVar(dim + 1);
        }

        @Override
        public TypeData getArrayType(int dim) throws NotFoundException {
            return this.element.getArrayType(dim + 1);
        }

        @Override
        public int dfs(List<TypeData> order, int index, ClassPool cp) throws NotFoundException {
            return this.element.dfs(order, index, cp);
        }

        @Override
        String toString2(Set<TypeData> set) {
            return "[" + this.element.toString2(set);
        }
    }

    public static class TypeVar
    extends AbsTypeVar {
        protected List<TypeData> lowers = new ArrayList<TypeData>(2);
        protected List<TypeData> usedBy = new ArrayList<TypeData>(2);
        protected List<String> uppers = null;
        protected String fixedType;
        private boolean is2WordType;
        private int visited = 0;
        private int smallest = 0;
        private boolean inList = false;
        private int dimension = 0;

        public TypeVar(TypeData t) {
            this.merge(t);
            this.fixedType = null;
            this.is2WordType = t.is2WordType();
        }

        @Override
        public String getName() {
            if (this.fixedType != null) return this.fixedType;
            return this.lowers.get(0).getName();
        }

        @Override
        public BasicType isBasicType() {
            if (this.fixedType != null) return null;
            return this.lowers.get(0).isBasicType();
        }

        @Override
        public boolean is2WordType() {
            if (this.fixedType != null) return false;
            return this.is2WordType;
        }

        @Override
        public boolean isNullType() {
            if (this.fixedType != null) return false;
            return this.lowers.get(0).isNullType();
        }

        @Override
        public boolean isUninit() {
            if (this.fixedType != null) return false;
            return this.lowers.get(0).isUninit();
        }

        @Override
        public void merge(TypeData t) {
            this.lowers.add(t);
            if (!(t instanceof TypeVar)) return;
            ((TypeVar)t).usedBy.add(this);
        }

        @Override
        public int getTypeTag() {
            if (this.fixedType != null) return super.getTypeTag();
            return this.lowers.get(0).getTypeTag();
        }

        @Override
        public int getTypeData(ConstPool cp) {
            if (this.fixedType != null) return super.getTypeData(cp);
            return this.lowers.get(0).getTypeData(cp);
        }

        @Override
        public void setType(String typeName, ClassPool cp) throws BadBytecode {
            if (this.uppers == null) {
                this.uppers = new ArrayList<String>();
            }
            this.uppers.add(typeName);
        }

        @Override
        protected TypeVar toTypeVar(int dim) {
            this.dimension = dim;
            return this;
        }

        @Override
        public TypeData getArrayType(int dim) throws NotFoundException {
            if (dim == 0) {
                return this;
            }
            BasicType bt = this.isBasicType();
            if (bt != null) return bt.getArrayType(dim);
            if (!this.isNullType()) return new ClassName(this.getName()).getArrayType(dim);
            return new NullType();
        }

        @Override
        public int dfs(List<TypeData> preOrder, int index, ClassPool cp) throws NotFoundException {
            TypeVar cv;
            if (this.visited > 0) {
                return index;
            }
            this.visited = this.smallest = ++index;
            preOrder.add(this);
            this.inList = true;
            int n = this.lowers.size();
            for (int i = 0; i < n; ++i) {
                TypeVar child = this.lowers.get(i).toTypeVar(this.dimension);
                if (child == null) continue;
                if (child.visited == 0) {
                    index = child.dfs(preOrder, index, cp);
                    if (child.smallest >= this.smallest) continue;
                    this.smallest = child.smallest;
                    continue;
                }
                if (!child.inList || child.visited >= this.smallest) continue;
                this.smallest = child.visited;
            }
            if (this.visited != this.smallest) return index;
            ArrayList<TypeData> scc = new ArrayList<TypeData>();
            do {
                cv = (TypeVar)preOrder.remove(preOrder.size() - 1);
                cv.inList = false;
                scc.add(cv);
            } while (cv != this);
            this.fixTypes(scc, cp);
            return index;
        }

        private void fixTypes(List<TypeData> scc, ClassPool cp) throws NotFoundException {
            HashSet<String> lowersSet = new HashSet<String>();
            boolean isBasicType = false;
            TypeData kind = null;
            int size = scc.size();
            int i = 0;
            while (true) {
                int size2;
                List<TypeData> tds;
                TypeVar tvar;
                if (i < size) {
                    tvar = (TypeVar)scc.get(i);
                    tds = tvar.lowers;
                    size2 = tds.size();
                } else {
                    if (isBasicType) {
                        this.is2WordType = kind.is2WordType();
                        this.fixTypes1(scc, kind);
                        return;
                    }
                    String typeName = this.fixTypes2(scc, lowersSet, cp);
                    this.fixTypes1(scc, new ClassName(typeName));
                    return;
                }
                for (int j = 0; j < size2; ++j) {
                    TypeData td = tds.get(j);
                    TypeData d = td.getArrayType(tvar.dimension);
                    BasicType bt = d.isBasicType();
                    if (kind == null) {
                        if (bt == null) {
                            isBasicType = false;
                            kind = d;
                            if (d.isUninit()) {
                                break;
                            }
                        } else {
                            isBasicType = true;
                            kind = bt;
                        }
                    } else if (bt == null && isBasicType || bt != null && kind != bt) {
                        isBasicType = true;
                        kind = TypeTag.TOP;
                        break;
                    }
                    if (bt != null || d.isNullType()) continue;
                    lowersSet.add(d.getName());
                }
                ++i;
            }
        }

        private void fixTypes1(List<TypeData> scc, TypeData kind) throws NotFoundException {
            int size = scc.size();
            int i = 0;
            while (i < size) {
                TypeVar cv = (TypeVar)scc.get(i);
                TypeData kind2 = kind.getArrayType(-cv.dimension);
                if (kind2.isBasicType() == null) {
                    cv.fixedType = kind2.getName();
                } else {
                    cv.lowers.clear();
                    cv.lowers.add(kind2);
                    cv.is2WordType = kind2.is2WordType();
                }
                ++i;
            }
        }

        private String fixTypes2(List<TypeData> scc, Set<String> lowersSet, ClassPool cp) throws NotFoundException {
            Iterator<String> it = lowersSet.iterator();
            if (lowersSet.size() == 0) {
                return null;
            }
            if (lowersSet.size() == 1) {
                return it.next();
            }
            CtClass cc = cp.get(it.next());
            while (it.hasNext()) {
                cc = TypeVar.commonSuperClassEx(cc, cp.get(it.next()));
            }
            if (cc.getSuperclass() == null || TypeVar.isObjectArray(cc)) {
                cc = this.fixByUppers(scc, cp, new HashSet<TypeData>(), cc);
            }
            if (!cc.isArray()) return cc.getName();
            return Descriptor.toJvmName(cc);
        }

        private static boolean isObjectArray(CtClass cc) throws NotFoundException {
            if (!cc.isArray()) return false;
            if (cc.getComponentType().getSuperclass() != null) return false;
            return true;
        }

        private CtClass fixByUppers(List<TypeData> users, ClassPool cp, Set<TypeData> visited, CtClass type) throws NotFoundException {
            if (users == null) {
                return type;
            }
            int size = users.size();
            int i = 0;
            while (i < size) {
                TypeVar t = (TypeVar)users.get(i);
                if (!visited.add(t)) {
                    return type;
                }
                if (t.uppers != null) {
                    int s = t.uppers.size();
                    for (int k = 0; k < s; ++k) {
                        CtClass cc = cp.get(t.uppers.get(k));
                        if (!cc.subtypeOf(type)) continue;
                        type = cc;
                    }
                }
                type = this.fixByUppers(t.usedBy, cp, visited, type);
                ++i;
            }
            return type;
        }

        @Override
        String toString2(Set<TypeData> hash) {
            hash.add(this);
            if (this.lowers.size() <= 0) return "?";
            TypeData e = this.lowers.get(0);
            if (e == null) return "?";
            if (hash.contains(e)) return "?";
            return e.toString2(hash);
        }
    }

    public static abstract class AbsTypeVar
    extends TypeData {
        public abstract void merge(TypeData var1);

        @Override
        public int getTypeTag() {
            return 7;
        }

        @Override
        public int getTypeData(ConstPool cp) {
            return cp.addClassInfo(this.getName());
        }

        @Override
        public boolean eq(TypeData d) {
            if (!d.isUninit()) return this.getName().equals(d.getName());
            return d.eq(this);
        }
    }

    protected static class BasicType
    extends TypeData {
        private String name;
        private int typeTag;
        private char decodedName;

        public BasicType(String type, int tag, char decoded) {
            this.name = type;
            this.typeTag = tag;
            this.decodedName = decoded;
        }

        @Override
        public int getTypeTag() {
            return this.typeTag;
        }

        @Override
        public int getTypeData(ConstPool cp) {
            return 0;
        }

        @Override
        public TypeData join() {
            if (this != TypeTag.TOP) return super.join();
            return this;
        }

        @Override
        public BasicType isBasicType() {
            return this;
        }

        @Override
        public boolean is2WordType() {
            if (this.typeTag == 4) return true;
            if (this.typeTag == 3) return true;
            return false;
        }

        @Override
        public boolean eq(TypeData d) {
            if (this != d) return false;
            return true;
        }

        @Override
        public String getName() {
            return this.name;
        }

        public char getDecodedName() {
            return this.decodedName;
        }

        @Override
        public void setType(String s, ClassPool cp) throws BadBytecode {
            throw new BadBytecode("conflict: " + this.name + " and " + s);
        }

        @Override
        public TypeData getArrayType(int dim) throws NotFoundException {
            if (this == TypeTag.TOP) {
                return this;
            }
            if (dim < 0) {
                throw new NotFoundException("no element type: " + this.name);
            }
            if (dim == 0) {
                return this;
            }
            char[] name = new char[dim + 1];
            int i = 0;
            while (true) {
                if (i >= dim) {
                    name[dim] = this.decodedName;
                    return new ClassName(new String(name));
                }
                name[i] = 91;
                ++i;
            }
        }

        @Override
        String toString2(Set<TypeData> set) {
            return this.name;
        }
    }
}

