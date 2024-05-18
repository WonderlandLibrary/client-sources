// 
// Decompiled by Procyon v0.5.30
// 

package javassist.bytecode.stackmap;

import java.util.Iterator;
import javassist.bytecode.Descriptor;
import java.util.HashSet;
import javassist.CtClass;
import javassist.NotFoundException;
import java.util.ArrayList;
import javassist.bytecode.ConstPool;
import javassist.bytecode.BadBytecode;
import javassist.ClassPool;

public abstract class TypeData
{
    public static TypeData[] make(final int size) {
        final TypeData[] array = new TypeData[size];
        for (int i = 0; i < size; ++i) {
            array[i] = TypeTag.TOP;
        }
        return array;
    }
    
    private static void setType(final TypeData td, final String className, final ClassPool cp) throws BadBytecode {
        td.setType(className, cp);
    }
    
    public abstract int getTypeTag();
    
    public abstract int getTypeData(final ConstPool p0);
    
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
    
    public abstract boolean eq(final TypeData p0);
    
    public abstract String getName();
    
    public abstract void setType(final String p0, final ClassPool p1) throws BadBytecode;
    
    public int dfs(final ArrayList order, final int index, final ClassPool cp) throws NotFoundException {
        return index;
    }
    
    protected TypeVar toTypeVar() {
        return null;
    }
    
    public void constructorCalled(final int offset) {
    }
    
    public static CtClass commonSuperClassEx(final CtClass one, final CtClass two) throws NotFoundException {
        if (one == two) {
            return one;
        }
        if (one.isArray() && two.isArray()) {
            final CtClass ele1 = one.getComponentType();
            final CtClass ele2 = two.getComponentType();
            final CtClass element = commonSuperClassEx(ele1, ele2);
            if (element == ele1) {
                return one;
            }
            if (element == ele2) {
                return two;
            }
            return one.getClassPool().get((element == null) ? "java.lang.Object" : (element.getName() + "[]"));
        }
        else {
            if (one.isPrimitive() || two.isPrimitive()) {
                return null;
            }
            if (one.isArray() || two.isArray()) {
                return one.getClassPool().get("java.lang.Object");
            }
            return commonSuperClass(one, two);
        }
    }
    
    public static CtClass commonSuperClass(final CtClass one, final CtClass two) throws NotFoundException {
        CtClass deep = one;
        CtClass backupShallow;
        CtClass shallow = backupShallow = two;
        CtClass backupDeep = deep;
        while (!eq(deep, shallow) || deep.getSuperclass() == null) {
            final CtClass deepSuper = deep.getSuperclass();
            final CtClass shallowSuper = shallow.getSuperclass();
            if (shallowSuper == null) {
                shallow = backupShallow;
            }
            else {
                if (deepSuper != null) {
                    deep = deepSuper;
                    shallow = shallowSuper;
                    continue;
                }
                deep = backupDeep;
                backupDeep = backupShallow;
                backupShallow = deep;
                deep = shallow;
                shallow = backupShallow;
            }
            while (true) {
                deep = deep.getSuperclass();
                if (deep == null) {
                    break;
                }
                backupDeep = backupDeep.getSuperclass();
            }
            for (deep = backupDeep; !eq(deep, shallow); deep = deep.getSuperclass(), shallow = shallow.getSuperclass()) {}
            return deep;
        }
        return deep;
    }
    
    static boolean eq(final CtClass one, final CtClass two) {
        return one == two || (one != null && two != null && one.getName().equals(two.getName()));
    }
    
    public static void aastore(final TypeData array, final TypeData value, final ClassPool cp) throws BadBytecode {
        if (array instanceof AbsTypeVar && !value.isNullType()) {
            ((AbsTypeVar)array).merge(ArrayType.make(value));
        }
        if (value instanceof AbsTypeVar) {
            if (array instanceof AbsTypeVar) {
                ArrayElement.make(array);
            }
            else {
                if (!(array instanceof ClassName)) {
                    throw new BadBytecode("bad AASTORE: " + array);
                }
                if (!array.isNullType()) {
                    final String type = typeName(array.getName());
                    value.setType(type, cp);
                }
            }
        }
    }
    
    protected static class BasicType extends TypeData
    {
        private String name;
        private int typeTag;
        
        public BasicType(final String type, final int tag) {
            this.name = type;
            this.typeTag = tag;
        }
        
        @Override
        public int getTypeTag() {
            return this.typeTag;
        }
        
        @Override
        public int getTypeData(final ConstPool cp) {
            return 0;
        }
        
        @Override
        public TypeData join() {
            if (this == TypeTag.TOP) {
                return this;
            }
            return super.join();
        }
        
        @Override
        public BasicType isBasicType() {
            return this;
        }
        
        @Override
        public boolean is2WordType() {
            return this.typeTag == 4 || this.typeTag == 3;
        }
        
        @Override
        public boolean eq(final TypeData d) {
            return this == d;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public void setType(final String s, final ClassPool cp) throws BadBytecode {
            throw new BadBytecode("conflict: " + this.name + " and " + s);
        }
        
        @Override
        public String toString() {
            return this.name;
        }
    }
    
    public abstract static class AbsTypeVar extends TypeData
    {
        public abstract void merge(final TypeData p0);
        
        @Override
        public int getTypeTag() {
            return 7;
        }
        
        @Override
        public int getTypeData(final ConstPool cp) {
            return cp.addClassInfo(this.getName());
        }
        
        @Override
        public boolean eq(final TypeData d) {
            return this.getName().equals(d.getName());
        }
    }
    
    public static class TypeVar extends AbsTypeVar
    {
        protected ArrayList lowers;
        protected ArrayList usedBy;
        protected ArrayList uppers;
        protected String fixedType;
        private boolean is2WordType;
        private int visited;
        private int smallest;
        private boolean inList;
        
        public TypeVar(final TypeData t) {
            this.visited = 0;
            this.smallest = 0;
            this.inList = false;
            this.uppers = null;
            this.lowers = new ArrayList(2);
            this.usedBy = new ArrayList(2);
            this.merge(t);
            this.fixedType = null;
            this.is2WordType = t.is2WordType();
        }
        
        @Override
        public String getName() {
            if (this.fixedType == null) {
                return this.lowers.get(0).getName();
            }
            return this.fixedType;
        }
        
        @Override
        public BasicType isBasicType() {
            if (this.fixedType == null) {
                return this.lowers.get(0).isBasicType();
            }
            return null;
        }
        
        @Override
        public boolean is2WordType() {
            return this.fixedType == null && this.is2WordType;
        }
        
        @Override
        public boolean isNullType() {
            return this.fixedType == null && this.lowers.get(0).isNullType();
        }
        
        @Override
        public boolean isUninit() {
            return this.fixedType == null && this.lowers.get(0).isUninit();
        }
        
        @Override
        public void merge(final TypeData t) {
            this.lowers.add(t);
            if (t instanceof TypeVar) {
                ((TypeVar)t).usedBy.add(this);
            }
        }
        
        @Override
        public int getTypeTag() {
            if (this.fixedType == null) {
                return this.lowers.get(0).getTypeTag();
            }
            return super.getTypeTag();
        }
        
        @Override
        public int getTypeData(final ConstPool cp) {
            if (this.fixedType == null) {
                return this.lowers.get(0).getTypeData(cp);
            }
            return super.getTypeData(cp);
        }
        
        @Override
        public void setType(final String typeName, final ClassPool cp) throws BadBytecode {
            if (this.uppers == null) {
                this.uppers = new ArrayList();
            }
            this.uppers.add(typeName);
        }
        
        @Override
        protected TypeVar toTypeVar() {
            return this;
        }
        
        @Override
        public int dfs(final ArrayList preOrder, int index, final ClassPool cp) throws NotFoundException {
            if (this.visited > 0) {
                return index;
            }
            final int n2 = ++index;
            this.smallest = n2;
            this.visited = n2;
            preOrder.add(this);
            this.inList = true;
            for (int n = this.lowers.size(), i = 0; i < n; ++i) {
                final TypeVar child = this.lowers.get(i).toTypeVar();
                if (child != null) {
                    if (child.visited == 0) {
                        index = child.dfs(preOrder, index, cp);
                        if (child.smallest < this.smallest) {
                            this.smallest = child.smallest;
                        }
                    }
                    else if (child.inList && child.visited < this.smallest) {
                        this.smallest = child.visited;
                    }
                }
            }
            if (this.visited == this.smallest) {
                final ArrayList scc = new ArrayList();
                TypeVar cv;
                do {
                    cv = preOrder.remove(preOrder.size() - 1);
                    cv.inList = false;
                    scc.add(cv);
                } while (cv != this);
                this.fixTypes(scc, cp);
            }
            return index;
        }
        
        private void fixTypes(final ArrayList scc, final ClassPool cp) throws NotFoundException {
            final HashSet lowersSet = new HashSet();
            boolean isBasicType = false;
            TypeData kind = null;
            final int size = scc.size();
            for (int i = 0; i < size; ++i) {
                final ArrayList tds = scc.get(i).lowers;
                for (int size2 = tds.size(), j = 0; j < size2; ++j) {
                    final TypeData d = tds.get(j);
                    final BasicType bt = d.isBasicType();
                    if (kind == null) {
                        if (bt == null) {
                            isBasicType = false;
                            kind = d;
                            if (d.isUninit()) {
                                break;
                            }
                        }
                        else {
                            isBasicType = true;
                            kind = bt;
                        }
                    }
                    else if ((bt == null && isBasicType) || (bt != null && kind != bt)) {
                        isBasicType = true;
                        kind = TypeTag.TOP;
                        break;
                    }
                    if (bt == null && !d.isNullType()) {
                        lowersSet.add(d.getName());
                    }
                }
            }
            if (isBasicType) {
                for (int i = 0; i < size; ++i) {
                    final TypeVar cv = scc.get(i);
                    cv.lowers.clear();
                    cv.lowers.add(kind);
                    this.is2WordType = kind.is2WordType();
                }
            }
            else {
                final String typeName = this.fixTypes2(scc, lowersSet, cp);
                for (int k = 0; k < size; ++k) {
                    final TypeVar cv2 = scc.get(k);
                    cv2.fixedType = typeName;
                }
            }
        }
        
        private String fixTypes2(final ArrayList scc, final HashSet lowersSet, final ClassPool cp) throws NotFoundException {
            final Iterator it = lowersSet.iterator();
            if (lowersSet.size() == 0) {
                return null;
            }
            if (lowersSet.size() == 1) {
                return it.next();
            }
            CtClass cc = cp.get(it.next());
            while (it.hasNext()) {
                cc = TypeData.commonSuperClassEx(cc, cp.get(it.next()));
            }
            if (cc.getSuperclass() == null || isObjectArray(cc)) {
                cc = this.fixByUppers(scc, cp, new HashSet(), cc);
            }
            if (cc.isArray()) {
                return Descriptor.toJvmName(cc);
            }
            return cc.getName();
        }
        
        private static boolean isObjectArray(final CtClass cc) throws NotFoundException {
            return cc.isArray() && cc.getComponentType().getSuperclass() == null;
        }
        
        private CtClass fixByUppers(final ArrayList users, final ClassPool cp, final HashSet visited, CtClass type) throws NotFoundException {
            if (users == null) {
                return type;
            }
            for (int size = users.size(), i = 0; i < size; ++i) {
                final TypeVar t = users.get(i);
                if (!visited.add(t)) {
                    return type;
                }
                if (t.uppers != null) {
                    for (int s = t.uppers.size(), k = 0; k < s; ++k) {
                        final CtClass cc = cp.get(t.uppers.get(k));
                        if (cc.subtypeOf(type)) {
                            type = cc;
                        }
                    }
                }
                type = this.fixByUppers(t.usedBy, cp, visited, type);
            }
            return type;
        }
    }
    
    public static class ArrayType extends AbsTypeVar
    {
        private AbsTypeVar element;
        
        private ArrayType(final AbsTypeVar elementType) {
            this.element = elementType;
        }
        
        static TypeData make(final TypeData element) throws BadBytecode {
            if (element instanceof ArrayElement) {
                return ((ArrayElement)element).arrayType();
            }
            if (element instanceof AbsTypeVar) {
                return new ArrayType((AbsTypeVar)element);
            }
            if (element instanceof ClassName && !element.isNullType()) {
                return new ClassName(typeName(element.getName()));
            }
            throw new BadBytecode("bad AASTORE: " + element);
        }
        
        @Override
        public void merge(final TypeData t) {
            try {
                if (!t.isNullType()) {
                    this.element.merge(ArrayElement.make(t));
                }
            }
            catch (BadBytecode e) {
                throw new RuntimeException("fatal: " + e);
            }
        }
        
        @Override
        public String getName() {
            return typeName(this.element.getName());
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
        
        public static String typeName(final String elementType) {
            if (elementType.charAt(0) == '[') {
                return "[" + elementType;
            }
            return "[L" + elementType.replace('.', '/') + ";";
        }
        
        @Override
        public void setType(final String s, final ClassPool cp) throws BadBytecode {
            this.element.setType(typeName(s), cp);
        }
        
        @Override
        protected TypeVar toTypeVar() {
            return this.element.toTypeVar();
        }
        
        @Override
        public int dfs(final ArrayList order, final int index, final ClassPool cp) throws NotFoundException {
            return this.element.dfs(order, index, cp);
        }
    }
    
    public static class ArrayElement extends AbsTypeVar
    {
        private AbsTypeVar array;
        
        private ArrayElement(final AbsTypeVar a) {
            this.array = a;
        }
        
        public static TypeData make(final TypeData array) throws BadBytecode {
            if (array instanceof ArrayType) {
                return ((ArrayType)array).elementType();
            }
            if (array instanceof AbsTypeVar) {
                return new ArrayElement((AbsTypeVar)array);
            }
            if (array instanceof ClassName && !array.isNullType()) {
                return new ClassName(typeName(array.getName()));
            }
            throw new BadBytecode("bad AASTORE: " + array);
        }
        
        @Override
        public void merge(final TypeData t) {
            try {
                if (!t.isNullType()) {
                    this.array.merge(ArrayType.make(t));
                }
            }
            catch (BadBytecode e) {
                throw new RuntimeException("fatal: " + e);
            }
        }
        
        @Override
        public String getName() {
            return typeName(this.array.getName());
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
        
        private static String typeName(final String arrayType) {
            if (arrayType.length() > 1 && arrayType.charAt(0) == '[') {
                final char c = arrayType.charAt(1);
                if (c == 'L') {
                    return arrayType.substring(2, arrayType.length() - 1).replace('/', '.');
                }
                if (c == '[') {
                    return arrayType.substring(1);
                }
            }
            return "java.lang.Object";
        }
        
        @Override
        public void setType(final String s, final ClassPool cp) throws BadBytecode {
            this.array.setType(ArrayType.typeName(s), cp);
        }
        
        @Override
        protected TypeVar toTypeVar() {
            return this.array.toTypeVar();
        }
        
        @Override
        public int dfs(final ArrayList order, final int index, final ClassPool cp) throws NotFoundException {
            return this.array.dfs(order, index, cp);
        }
    }
    
    public static class UninitTypeVar extends AbsTypeVar
    {
        protected TypeData type;
        
        public UninitTypeVar(final UninitData t) {
            this.type = t;
        }
        
        @Override
        public int getTypeTag() {
            return this.type.getTypeTag();
        }
        
        @Override
        public int getTypeData(final ConstPool cp) {
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
        public boolean eq(final TypeData d) {
            return this.type.eq(d);
        }
        
        @Override
        public String getName() {
            return this.type.getName();
        }
        
        @Override
        protected TypeVar toTypeVar() {
            return null;
        }
        
        @Override
        public TypeData join() {
            return this.type.join();
        }
        
        @Override
        public void setType(final String s, final ClassPool cp) throws BadBytecode {
            this.type.setType(s, cp);
        }
        
        @Override
        public void merge(final TypeData t) {
            if (!t.eq(this.type)) {
                this.type = TypeTag.TOP;
            }
        }
        
        @Override
        public void constructorCalled(final int offset) {
            this.type.constructorCalled(offset);
        }
        
        public int offset() {
            if (this.type instanceof UninitData) {
                return ((UninitData)this.type).offset;
            }
            throw new RuntimeException("not available");
        }
    }
    
    public static class ClassName extends TypeData
    {
        private String name;
        
        public ClassName(final String n) {
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
        public int getTypeData(final ConstPool cp) {
            return cp.addClassInfo(this.getName());
        }
        
        @Override
        public boolean eq(final TypeData d) {
            return this.name.equals(d.getName());
        }
        
        @Override
        public void setType(final String typeName, final ClassPool cp) throws BadBytecode {
        }
    }
    
    public static class NullType extends ClassName
    {
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
        public int getTypeData(final ConstPool cp) {
            return 0;
        }
    }
    
    public static class UninitData extends ClassName
    {
        int offset;
        boolean initialized;
        
        UninitData(final int offset, final String className) {
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
        public int getTypeData(final ConstPool cp) {
            return this.offset;
        }
        
        @Override
        public TypeData join() {
            if (this.initialized) {
                return new TypeVar(new ClassName(this.getName()));
            }
            return new UninitTypeVar(this.copy());
        }
        
        @Override
        public boolean isUninit() {
            return true;
        }
        
        @Override
        public boolean eq(final TypeData d) {
            if (d instanceof UninitData) {
                final UninitData ud = (UninitData)d;
                return this.offset == ud.offset && this.getName().equals(ud.getName());
            }
            return false;
        }
        
        @Override
        public String toString() {
            return "uninit:" + this.getName() + "@" + this.offset;
        }
        
        public int offset() {
            return this.offset;
        }
        
        @Override
        public void constructorCalled(final int offset) {
            if (offset == this.offset) {
                this.initialized = true;
            }
        }
    }
    
    public static class UninitThis extends UninitData
    {
        UninitThis(final String className) {
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
        public int getTypeData(final ConstPool cp) {
            return 0;
        }
        
        @Override
        public String toString() {
            return "uninit:this";
        }
    }
}
