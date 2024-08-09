/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.Callback;
import com.sun.jna.FromNativeContext;
import com.sun.jna.FromNativeConverter;
import com.sun.jna.Function;
import com.sun.jna.IntegerType;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeMapped;
import com.sun.jna.NativeMappedConverter;
import com.sun.jna.NativeString;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.StructureReadContext;
import com.sun.jna.StructureWriteContext;
import com.sun.jna.ToNativeContext;
import com.sun.jna.ToNativeConverter;
import com.sun.jna.TypeMapper;
import com.sun.jna.Union;
import com.sun.jna.WString;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.nio.Buffer;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public abstract class Structure {
    public static final int ALIGN_DEFAULT = 0;
    public static final int ALIGN_NONE = 1;
    public static final int ALIGN_GNUC = 2;
    public static final int ALIGN_MSVC = 3;
    protected static final int CALCULATE_SIZE = -1;
    static final Map<Class<?>, LayoutInfo> layoutInfo = new WeakHashMap();
    static final Map<Class<?>, List<String>> fieldOrder = new WeakHashMap();
    private Pointer memory;
    private int size = -1;
    private int alignType;
    private String encoding;
    private int actualAlignType;
    private int structAlignment;
    private Map<String, StructField> structFields;
    private final Map<String, Object> nativeStrings = new HashMap<String, Object>();
    private TypeMapper typeMapper;
    private long typeInfo;
    private boolean autoRead = true;
    private boolean autoWrite = true;
    private Structure[] array;
    private boolean readCalled;
    private static final ThreadLocal<Map<Pointer, Structure>> reads = new ThreadLocal<Map<Pointer, Structure>>(){

        @Override
        protected synchronized Map<Pointer, Structure> initialValue() {
            return new HashMap<Pointer, Structure>();
        }

        @Override
        protected Object initialValue() {
            return this.initialValue();
        }
    };
    private static final ThreadLocal<Set<Structure>> busy = new ThreadLocal<Set<Structure>>(){

        @Override
        protected synchronized Set<Structure> initialValue() {
            return new StructureSet();
        }

        @Override
        protected Object initialValue() {
            return this.initialValue();
        }
    };
    private static final Pointer PLACEHOLDER_MEMORY = new Pointer(0L){

        @Override
        public Pointer share(long l, long l2) {
            return this;
        }
    };

    protected Structure() {
        this(0);
    }

    protected Structure(TypeMapper typeMapper) {
        this(null, 0, typeMapper);
    }

    protected Structure(int n) {
        this(null, n);
    }

    protected Structure(int n, TypeMapper typeMapper) {
        this(null, n, typeMapper);
    }

    protected Structure(Pointer pointer) {
        this(pointer, 0);
    }

    protected Structure(Pointer pointer, int n) {
        this(pointer, n, null);
    }

    protected Structure(Pointer pointer, int n, TypeMapper typeMapper) {
        this.setAlignType(n);
        this.setStringEncoding(Native.getStringEncoding(this.getClass()));
        this.initializeTypeMapper(typeMapper);
        this.validateFields();
        if (pointer != null) {
            this.useMemory(pointer, 0, false);
        } else {
            this.allocateMemory(-1);
        }
        this.initializeFields();
    }

    Map<String, StructField> fields() {
        return this.structFields;
    }

    TypeMapper getTypeMapper() {
        return this.typeMapper;
    }

    private void initializeTypeMapper(TypeMapper typeMapper) {
        if (typeMapper == null) {
            typeMapper = Native.getTypeMapper(this.getClass());
        }
        this.typeMapper = typeMapper;
        this.layoutChanged();
    }

    private void layoutChanged() {
        if (this.size != -1) {
            this.size = -1;
            if (this.memory instanceof AutoAllocated) {
                this.memory = null;
            }
            this.ensureAllocated();
        }
    }

    protected void setStringEncoding(String string) {
        this.encoding = string;
    }

    protected String getStringEncoding() {
        return this.encoding;
    }

    protected void setAlignType(int n) {
        this.alignType = n;
        if (n == 0 && (n = Native.getStructureAlignment(this.getClass())) == 0) {
            n = Platform.isWindows() ? 3 : 2;
        }
        this.actualAlignType = n;
        this.layoutChanged();
    }

    protected Memory autoAllocate(int n) {
        return new AutoAllocated(n);
    }

    protected void useMemory(Pointer pointer) {
        this.useMemory(pointer, 0);
    }

    protected void useMemory(Pointer pointer, int n) {
        this.useMemory(pointer, n, true);
    }

    void useMemory(Pointer pointer, int n, boolean bl) {
        try {
            this.nativeStrings.clear();
            if (this instanceof ByValue && !bl) {
                byte[] byArray = new byte[this.size()];
                pointer.read(0L, byArray, 0, byArray.length);
                this.memory.write(0L, byArray, 0, byArray.length);
            } else {
                this.memory = pointer.share(n);
                if (this.size == -1) {
                    this.size = this.calculateSize(true);
                }
                if (this.size != -1) {
                    this.memory = pointer.share(n, this.size);
                }
            }
            this.array = null;
            this.readCalled = false;
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new IllegalArgumentException("Structure exceeds provided memory bounds", indexOutOfBoundsException);
        }
    }

    protected void ensureAllocated() {
        this.ensureAllocated(false);
    }

    private void ensureAllocated(boolean bl) {
        if (this.memory == null) {
            this.allocateMemory(bl);
        } else if (this.size == -1) {
            this.size = this.calculateSize(true, bl);
            if (!(this.memory instanceof AutoAllocated)) {
                try {
                    this.memory = this.memory.share(0L, this.size);
                } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    throw new IllegalArgumentException("Structure exceeds provided memory bounds", indexOutOfBoundsException);
                }
            }
        }
    }

    protected void allocateMemory() {
        this.allocateMemory(false);
    }

    private void allocateMemory(boolean bl) {
        this.allocateMemory(this.calculateSize(true, bl));
    }

    protected void allocateMemory(int n) {
        if (n == -1) {
            n = this.calculateSize(true);
        } else if (n <= 0) {
            throw new IllegalArgumentException("Structure size must be greater than zero: " + n);
        }
        if (n != -1) {
            if (this.memory == null || this.memory instanceof AutoAllocated) {
                this.memory = this.autoAllocate(n);
            }
            this.size = n;
        }
    }

    public int size() {
        this.ensureAllocated();
        return this.size;
    }

    public void clear() {
        this.ensureAllocated();
        this.memory.clear(this.size());
    }

    public Pointer getPointer() {
        this.ensureAllocated();
        return this.memory;
    }

    static Set<Structure> busy() {
        return busy.get();
    }

    static Map<Pointer, Structure> reading() {
        return reads.get();
    }

    void conditionalAutoRead() {
        if (!this.readCalled) {
            this.autoRead();
        }
    }

    public void read() {
        if (this.memory == PLACEHOLDER_MEMORY) {
            return;
        }
        this.readCalled = true;
        this.ensureAllocated();
        if (Structure.busy().contains(this)) {
            return;
        }
        Structure.busy().add(this);
        if (this instanceof ByReference) {
            Structure.reading().put(this.getPointer(), this);
        }
        try {
            for (StructField structField : this.fields().values()) {
                this.readField(structField);
            }
        } finally {
            Structure.busy().remove(this);
            if (Structure.reading().get(this.getPointer()) == this) {
                Structure.reading().remove(this.getPointer());
            }
        }
    }

    protected int fieldOffset(String string) {
        this.ensureAllocated();
        StructField structField = this.fields().get(string);
        if (structField == null) {
            throw new IllegalArgumentException("No such field: " + string);
        }
        return structField.offset;
    }

    public Object readField(String string) {
        this.ensureAllocated();
        StructField structField = this.fields().get(string);
        if (structField == null) {
            throw new IllegalArgumentException("No such field: " + string);
        }
        return this.readField(structField);
    }

    Object getFieldValue(Field field) {
        try {
            return field.get(this);
        } catch (Exception exception) {
            throw new Error("Exception reading field '" + field.getName() + "' in " + this.getClass(), exception);
        }
    }

    void setFieldValue(Field field, Object object) {
        this.setFieldValue(field, object, false);
    }

    private void setFieldValue(Field field, Object object, boolean bl) {
        try {
            field.set(this, object);
        } catch (IllegalAccessException illegalAccessException) {
            int n = field.getModifiers();
            if (Modifier.isFinal(n)) {
                if (bl) {
                    throw new UnsupportedOperationException("This VM does not support Structures with final fields (field '" + field.getName() + "' within " + this.getClass() + ")", illegalAccessException);
                }
                throw new UnsupportedOperationException("Attempt to write to read-only field '" + field.getName() + "' within " + this.getClass(), illegalAccessException);
            }
            throw new Error("Unexpectedly unable to write to field '" + field.getName() + "' within " + this.getClass(), illegalAccessException);
        }
    }

    static Structure updateStructureByReference(Class<?> clazz, Structure structure, Pointer pointer) {
        if (pointer == null) {
            structure = null;
        } else if (structure == null || !pointer.equals(structure.getPointer())) {
            Structure structure2 = Structure.reading().get(pointer);
            if (structure2 != null && clazz.equals(structure2.getClass())) {
                structure = structure2;
                structure.autoRead();
            } else {
                structure = Structure.newInstance(clazz, pointer);
                structure.conditionalAutoRead();
            }
        } else {
            structure.autoRead();
        }
        return structure;
    }

    protected Object readField(StructField structField) {
        Pointer pointer;
        Object object;
        int n = structField.offset;
        Class<?> clazz = structField.type;
        FromNativeConverter fromNativeConverter = structField.readConverter;
        if (fromNativeConverter != null) {
            clazz = fromNativeConverter.nativeType();
        }
        Object object2 = object = Structure.class.isAssignableFrom(clazz) || Callback.class.isAssignableFrom(clazz) || Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(clazz) || Pointer.class.isAssignableFrom(clazz) || NativeMapped.class.isAssignableFrom(clazz) || clazz.isArray() ? this.getFieldValue(structField.field) : null;
        Object object3 = clazz == String.class ? ((pointer = this.memory.getPointer(n)) == null ? null : pointer.getString(0L, this.encoding)) : this.memory.getValue(n, clazz, object);
        if (fromNativeConverter != null) {
            object3 = fromNativeConverter.fromNative(object3, structField.context);
            if (object != null && object.equals(object3)) {
                object3 = object;
            }
        }
        if (clazz.equals(String.class) || clazz.equals(WString.class)) {
            this.nativeStrings.put(structField.name + ".ptr", this.memory.getPointer(n));
            this.nativeStrings.put(structField.name + ".val", object3);
        }
        this.setFieldValue(structField.field, object3, true);
        return object3;
    }

    public void write() {
        if (this.memory == PLACEHOLDER_MEMORY) {
            return;
        }
        this.ensureAllocated();
        if (this instanceof ByValue) {
            this.getTypeInfo();
        }
        if (Structure.busy().contains(this)) {
            return;
        }
        Structure.busy().add(this);
        try {
            for (StructField structField : this.fields().values()) {
                if (structField.isVolatile) continue;
                this.writeField(structField);
            }
        } finally {
            Structure.busy().remove(this);
        }
    }

    public void writeField(String string) {
        this.ensureAllocated();
        StructField structField = this.fields().get(string);
        if (structField == null) {
            throw new IllegalArgumentException("No such field: " + string);
        }
        this.writeField(structField);
    }

    public void writeField(String string, Object object) {
        this.ensureAllocated();
        StructField structField = this.fields().get(string);
        if (structField == null) {
            throw new IllegalArgumentException("No such field: " + string);
        }
        this.setFieldValue(structField.field, object);
        this.writeField(structField);
    }

    protected void writeField(StructField structField) {
        CharSequence charSequence;
        if (structField.isReadOnly) {
            return;
        }
        int n = structField.offset;
        Object object = this.getFieldValue(structField.field);
        Class<?> clazz = structField.type;
        ToNativeConverter toNativeConverter = structField.writeConverter;
        if (toNativeConverter != null) {
            object = toNativeConverter.toNative(object, new StructureWriteContext(this, structField.field));
            clazz = toNativeConverter.nativeType();
        }
        if (String.class == clazz || WString.class == clazz) {
            boolean bl;
            boolean bl2 = bl = clazz == WString.class;
            if (object != null) {
                if (this.nativeStrings.containsKey(structField.name + ".ptr") && object.equals(this.nativeStrings.get(structField.name + ".val"))) {
                    return;
                }
                charSequence = bl ? new NativeString(object.toString(), true) : new NativeString(object.toString(), this.encoding);
                this.nativeStrings.put(structField.name, charSequence);
                object = ((NativeString)charSequence).getPointer();
            } else {
                this.nativeStrings.remove(structField.name);
            }
            this.nativeStrings.remove(structField.name + ".ptr");
            this.nativeStrings.remove(structField.name + ".val");
        }
        try {
            this.memory.setValue(n, object, clazz);
        } catch (IllegalArgumentException illegalArgumentException) {
            charSequence = "Structure field \"" + structField.name + "\" was declared as " + structField.type + (structField.type == clazz ? "" : " (native type " + clazz + ")") + ", which is not supported within a Structure";
            throw new IllegalArgumentException((String)charSequence, illegalArgumentException);
        }
    }

    protected abstract List<String> getFieldOrder();

    @Deprecated
    protected final void setFieldOrder(String[] stringArray) {
        throw new Error("This method is obsolete, use getFieldOrder() instead");
    }

    protected void sortFields(List<Field> list, List<String> list2) {
        block0: for (int i = 0; i < list2.size(); ++i) {
            String string = list2.get(i);
            for (int j = 0; j < list.size(); ++j) {
                Field field = list.get(j);
                if (!string.equals(field.getName())) continue;
                Collections.swap(list, i, j);
                continue block0;
            }
        }
    }

    protected List<Field> getFieldList() {
        ArrayList<Field> arrayList = new ArrayList<Field>();
        Class<?> clazz = this.getClass();
        while (!clazz.equals(Structure.class)) {
            ArrayList<Field> arrayList2 = new ArrayList<Field>();
            Field[] fieldArray = clazz.getDeclaredFields();
            for (int i = 0; i < fieldArray.length; ++i) {
                int n = fieldArray[i].getModifiers();
                if (Modifier.isStatic(n) || !Modifier.isPublic(n)) continue;
                arrayList2.add(fieldArray[i]);
            }
            arrayList.addAll(0, arrayList2);
            clazz = clazz.getSuperclass();
        }
        return arrayList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<String> fieldOrder() {
        Class<?> clazz = this.getClass();
        Map<Class<?>, List<String>> map = fieldOrder;
        synchronized (map) {
            List<String> list = fieldOrder.get(clazz);
            if (list == null) {
                list = this.getFieldOrder();
                fieldOrder.put(clazz, list);
            }
            return list;
        }
    }

    public static List<String> createFieldsOrder(List<String> list, String ... stringArray) {
        return Structure.createFieldsOrder(list, Arrays.asList(stringArray));
    }

    public static List<String> createFieldsOrder(List<String> list, List<String> list2) {
        ArrayList<String> arrayList = new ArrayList<String>(list.size() + list2.size());
        arrayList.addAll(list);
        arrayList.addAll(list2);
        return Collections.unmodifiableList(arrayList);
    }

    public static List<String> createFieldsOrder(String string) {
        return Collections.unmodifiableList(Collections.singletonList(string));
    }

    public static List<String> createFieldsOrder(String ... stringArray) {
        return Collections.unmodifiableList(Arrays.asList(stringArray));
    }

    private static <T extends Comparable<T>> List<T> sort(Collection<? extends T> collection) {
        ArrayList<? extends T> arrayList = new ArrayList<T>(collection);
        Collections.sort(arrayList);
        return arrayList;
    }

    protected List<Field> getFields(boolean bl) {
        List<Field> list = this.getFieldList();
        HashSet<String> hashSet2 = new HashSet<String>();
        for (Field hashSet3 : list) {
            hashSet2.add(hashSet3.getName());
        }
        List<String> list2 = this.fieldOrder();
        if (list2.size() != list.size() && list.size() > 1) {
            if (bl) {
                throw new Error("Structure.getFieldOrder() on " + this.getClass() + " does not provide enough names [" + list2.size() + "] (" + Structure.sort(list2) + ") to match declared fields [" + list.size() + "] (" + Structure.sort(hashSet2) + ")");
            }
            return null;
        }
        HashSet hashSet = new HashSet(list2);
        if (!hashSet.equals(hashSet2)) {
            throw new Error("Structure.getFieldOrder() on " + this.getClass() + " returns names (" + Structure.sort(list2) + ") which do not match declared field names (" + Structure.sort(hashSet2) + ")");
        }
        this.sortFields(list, list2);
        return list;
    }

    protected int calculateSize(boolean bl) {
        return this.calculateSize(bl, true);
    }

    static int size(Class<?> clazz) {
        return Structure.size(clazz, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static int size(Class<?> clazz, Structure structure) {
        int n;
        LayoutInfo layoutInfo;
        Map<Class<?>, LayoutInfo> map = Structure.layoutInfo;
        synchronized (map) {
            layoutInfo = Structure.layoutInfo.get(clazz);
        }
        int n2 = n = layoutInfo != null && !LayoutInfo.access$000(layoutInfo) ? LayoutInfo.access$100(layoutInfo) : -1;
        if (n == -1) {
            if (structure == null) {
                structure = Structure.newInstance(clazz, PLACEHOLDER_MEMORY);
            }
            n = structure.size();
        }
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    int calculateSize(boolean bl, boolean bl2) {
        LayoutInfo layoutInfo;
        int n = -1;
        Class<?> clazz = this.getClass();
        Map<Class<?>, LayoutInfo> map = Structure.layoutInfo;
        synchronized (map) {
            layoutInfo = Structure.layoutInfo.get(clazz);
        }
        if (layoutInfo == null || this.alignType != LayoutInfo.access$200(layoutInfo) || this.typeMapper != LayoutInfo.access$300(layoutInfo)) {
            layoutInfo = this.deriveLayout(bl, bl2);
        }
        if (layoutInfo != null) {
            this.structAlignment = LayoutInfo.access$400(layoutInfo);
            this.structFields = LayoutInfo.access$500(layoutInfo);
            if (!LayoutInfo.access$000(layoutInfo)) {
                map = Structure.layoutInfo;
                synchronized (map) {
                    if (!Structure.layoutInfo.containsKey(clazz) || this.alignType != 0 || this.typeMapper != null) {
                        Structure.layoutInfo.put(clazz, layoutInfo);
                    }
                }
            }
            n = LayoutInfo.access$100(layoutInfo);
        }
        return n;
    }

    private void validateField(String string, Class<?> clazz) {
        ToNativeConverter toNativeConverter;
        if (this.typeMapper != null && (toNativeConverter = this.typeMapper.getToNativeConverter(clazz)) != null) {
            this.validateField(string, toNativeConverter.nativeType());
            return;
        }
        if (clazz.isArray()) {
            this.validateField(string, clazz.getComponentType());
        } else {
            try {
                this.getNativeSize(clazz);
            } catch (IllegalArgumentException illegalArgumentException) {
                String string2 = "Invalid Structure field in " + this.getClass() + ", field name '" + string + "' (" + clazz + "): " + illegalArgumentException.getMessage();
                throw new IllegalArgumentException(string2, illegalArgumentException);
            }
        }
    }

    private void validateFields() {
        List<Field> list = this.getFieldList();
        for (Field field : list) {
            this.validateField(field.getName(), field.getType());
        }
    }

    private LayoutInfo deriveLayout(boolean bl, boolean bl2) {
        int n = 0;
        List<Field> list = this.getFields(bl);
        if (list == null) {
            return null;
        }
        LayoutInfo layoutInfo = new LayoutInfo(null);
        LayoutInfo.access$202(layoutInfo, this.alignType);
        LayoutInfo.access$302(layoutInfo, this.typeMapper);
        boolean bl3 = true;
        for (Field field : list) {
            int n2 = field.getModifiers();
            Class<?> clazz = field.getType();
            if (clazz.isArray()) {
                LayoutInfo.access$002(layoutInfo, true);
            }
            StructField structField = new StructField();
            structField.isVolatile = Modifier.isVolatile(n2);
            structField.isReadOnly = Modifier.isFinal(n2);
            if (structField.isReadOnly) {
                if (!Platform.RO_FIELDS) {
                    throw new IllegalArgumentException("This VM does not support read-only fields (field '" + field.getName() + "' within " + this.getClass() + ")");
                }
                field.setAccessible(false);
            }
            structField.field = field;
            structField.name = field.getName();
            structField.type = clazz;
            if (Callback.class.isAssignableFrom(clazz) && !clazz.isInterface()) {
                throw new IllegalArgumentException("Structure Callback field '" + field.getName() + "' must be an interface");
            }
            if (clazz.isArray() && Structure.class.equals(clazz.getComponentType())) {
                String string = "Nested Structure arrays must use a derived Structure type so that the size of the elements can be determined";
                throw new IllegalArgumentException(string);
            }
            int n3 = 1;
            if (Modifier.isPublic(field.getModifiers())) {
                Object object;
                ToNativeConverter toNativeConverter;
                Object object2 = this.getFieldValue(structField.field);
                if (object2 == null && clazz.isArray()) {
                    if (bl) {
                        throw new IllegalStateException("Array fields must be initialized");
                    }
                    return null;
                }
                Class<Object> clazz2 = clazz;
                if (NativeMapped.class.isAssignableFrom(clazz)) {
                    toNativeConverter = NativeMappedConverter.getInstance(clazz);
                    clazz2 = ((NativeMappedConverter)toNativeConverter).nativeType();
                    structField.writeConverter = toNativeConverter;
                    structField.readConverter = toNativeConverter;
                    structField.context = new StructureReadContext(this, field);
                } else if (this.typeMapper != null) {
                    toNativeConverter = this.typeMapper.getToNativeConverter(clazz);
                    object = this.typeMapper.getFromNativeConverter(clazz);
                    if (toNativeConverter != null && object != null) {
                        clazz2 = (object2 = toNativeConverter.toNative(object2, new StructureWriteContext(this, structField.field))) != null ? object2.getClass() : Pointer.class;
                        structField.writeConverter = toNativeConverter;
                        structField.readConverter = object;
                        structField.context = new StructureReadContext(this, field);
                    } else if (toNativeConverter != null || object != null) {
                        String string = "Structures require bidirectional type conversion for " + clazz;
                        throw new IllegalArgumentException(string);
                    }
                }
                if (object2 == null) {
                    object2 = this.initializeField(structField.field, clazz);
                }
                try {
                    structField.size = this.getNativeSize(clazz2, object2);
                    n3 = this.getNativeAlignment(clazz2, object2, bl3);
                } catch (IllegalArgumentException illegalArgumentException) {
                    if (!bl && this.typeMapper == null) {
                        return null;
                    }
                    object = "Invalid Structure field in " + this.getClass() + ", field name '" + structField.name + "' (" + structField.type + "): " + illegalArgumentException.getMessage();
                    throw new IllegalArgumentException((String)object, illegalArgumentException);
                }
                if (n3 == 0) {
                    throw new Error("Field alignment is zero for field '" + structField.name + "' within " + this.getClass());
                }
                LayoutInfo.access$402(layoutInfo, Math.max(LayoutInfo.access$400(layoutInfo), n3));
                if (n % n3 != 0) {
                    n += n3 - n % n3;
                }
                if (this instanceof Union) {
                    structField.offset = 0;
                    n = Math.max(n, structField.size);
                } else {
                    structField.offset = n;
                    n += structField.size;
                }
                LayoutInfo.access$500(layoutInfo).put(structField.name, structField);
                if (LayoutInfo.access$700(layoutInfo) == null || LayoutInfo.access$700((LayoutInfo)layoutInfo).size < structField.size || LayoutInfo.access$700((LayoutInfo)layoutInfo).size == structField.size && Structure.class.isAssignableFrom(structField.type)) {
                    LayoutInfo.access$702(layoutInfo, structField);
                }
            }
            bl3 = false;
        }
        if (n > 0) {
            int n4 = this.addPadding(n, LayoutInfo.access$400(layoutInfo));
            if (this instanceof ByValue && !bl2) {
                this.getTypeInfo();
            }
            LayoutInfo.access$102(layoutInfo, n4);
            return layoutInfo;
        }
        throw new IllegalArgumentException("Structure " + this.getClass() + " has unknown or zero size (ensure all fields are public)");
    }

    private void initializeFields() {
        List<Field> list = this.getFieldList();
        for (Field field : list) {
            try {
                Object object = field.get(this);
                if (object != null) continue;
                this.initializeField(field, field.getType());
            } catch (Exception exception) {
                throw new Error("Exception reading field '" + field.getName() + "' in " + this.getClass(), exception);
            }
        }
    }

    private Object initializeField(Field field, Class<?> clazz) {
        Object object = null;
        if (Structure.class.isAssignableFrom(clazz) && !ByReference.class.isAssignableFrom(clazz)) {
            try {
                object = Structure.newInstance(clazz, PLACEHOLDER_MEMORY);
                this.setFieldValue(field, object);
            } catch (IllegalArgumentException illegalArgumentException) {
                String string = "Can't determine size of nested structure";
                throw new IllegalArgumentException(string, illegalArgumentException);
            }
        } else if (NativeMapped.class.isAssignableFrom(clazz)) {
            NativeMappedConverter nativeMappedConverter = NativeMappedConverter.getInstance(clazz);
            object = nativeMappedConverter.defaultValue();
            this.setFieldValue(field, object);
        }
        return object;
    }

    private int addPadding(int n) {
        return this.addPadding(n, this.structAlignment);
    }

    private int addPadding(int n, int n2) {
        if (this.actualAlignType != 1 && n % n2 != 0) {
            n += n2 - n % n2;
        }
        return n;
    }

    protected int getStructAlignment() {
        if (this.size == -1) {
            this.calculateSize(false);
        }
        return this.structAlignment;
    }

    protected int getNativeAlignment(Class<?> clazz, Object object, boolean bl) {
        int n = 1;
        if (NativeMapped.class.isAssignableFrom(clazz)) {
            NativeMappedConverter nativeMappedConverter = NativeMappedConverter.getInstance(clazz);
            clazz = nativeMappedConverter.nativeType();
            object = nativeMappedConverter.toNative(object, new ToNativeContext());
        }
        int n2 = Native.getNativeSize(clazz, object);
        if (clazz.isPrimitive() || Long.class == clazz || Integer.class == clazz || Short.class == clazz || Character.class == clazz || Byte.class == clazz || Boolean.class == clazz || Float.class == clazz || Double.class == clazz) {
            n = n2;
        } else if (Pointer.class.isAssignableFrom(clazz) && !Function.class.isAssignableFrom(clazz) || Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(clazz) || Callback.class.isAssignableFrom(clazz) || WString.class == clazz || String.class == clazz) {
            n = Pointer.SIZE;
        } else if (Structure.class.isAssignableFrom(clazz)) {
            if (ByReference.class.isAssignableFrom(clazz)) {
                n = Pointer.SIZE;
            } else {
                if (object == null) {
                    object = Structure.newInstance(clazz, PLACEHOLDER_MEMORY);
                }
                n = ((Structure)object).getStructAlignment();
            }
        } else if (clazz.isArray()) {
            n = this.getNativeAlignment(clazz.getComponentType(), null, bl);
        } else {
            throw new IllegalArgumentException("Type " + clazz + " has unknown native alignment");
        }
        if (this.actualAlignType == 1) {
            n = 1;
        } else if (this.actualAlignType == 3) {
            n = Math.min(8, n);
        } else if (this.actualAlignType == 2) {
            if (!(bl && Platform.isMac() && Platform.isPPC())) {
                n = Math.min(Native.MAX_ALIGNMENT, n);
            }
            if (!bl && Platform.isAIX() && (clazz == Double.TYPE || clazz == Double.class)) {
                n = 4;
            }
        }
        return n;
    }

    public String toString() {
        return this.toString(Boolean.getBoolean("jna.dump_memory"));
    }

    public String toString(boolean bl) {
        return this.toString(0, true, bl);
    }

    private String format(Class<?> clazz) {
        String string = clazz.getName();
        int n = string.lastIndexOf(".");
        return string.substring(n + 1);
    }

    private String toString(int n, boolean bl, boolean bl2) {
        Object object;
        this.ensureAllocated();
        String string = System.getProperty("line.separator");
        String string2 = this.format(this.getClass()) + "(" + this.getPointer() + ")";
        if (!(this.getPointer() instanceof Memory)) {
            string2 = string2 + " (" + this.size() + " bytes)";
        }
        String string3 = "";
        for (int i = 0; i < n; ++i) {
            string3 = string3 + "  ";
        }
        String string4 = string;
        if (!bl) {
            string4 = "...}";
        } else {
            Iterator<StructField> iterator2 = this.fields().values().iterator();
            while (iterator2.hasNext()) {
                object = iterator2.next();
                Object object2 = this.getFieldValue(((StructField)object).field);
                String string5 = this.format(((StructField)object).type);
                String string6 = "";
                string4 = string4 + string3;
                if (((StructField)object).type.isArray() && object2 != null) {
                    string5 = this.format(((StructField)object).type.getComponentType());
                    string6 = "[" + Array.getLength(object2) + "]";
                }
                string4 = string4 + "  " + string5 + " " + ((StructField)object).name + string6 + "@" + Integer.toHexString(((StructField)object).offset);
                if (object2 instanceof Structure) {
                    object2 = ((Structure)object2).toString(n + 1, !(object2 instanceof ByReference), bl2);
                }
                string4 = string4 + "=";
                string4 = object2 instanceof Long ? string4 + Long.toHexString((Long)object2) : (object2 instanceof Integer ? string4 + Integer.toHexString((Integer)object2) : (object2 instanceof Short ? string4 + Integer.toHexString(((Short)object2).shortValue()) : (object2 instanceof Byte ? string4 + Integer.toHexString(((Byte)object2).byteValue()) : string4 + String.valueOf(object2).trim())));
                string4 = string4 + string;
                if (iterator2.hasNext()) continue;
                string4 = string4 + string3 + "}";
            }
        }
        if (n == 0 && bl2) {
            int n2 = 4;
            string4 = string4 + string + "memory dump" + string;
            object = this.getPointer().getByteArray(0L, this.size());
            for (int i = 0; i < ((Object)object).length; ++i) {
                if (i % 4 == 0) {
                    string4 = string4 + "[";
                }
                if (object[i] >= 0 && object[i] < 16) {
                    string4 = string4 + "0";
                }
                string4 = string4 + Integer.toHexString(object[i] & 0xFF);
                if (i % 4 != 3 || i >= ((Object)object).length - 1) continue;
                string4 = string4 + "]" + string;
            }
            string4 = string4 + "]";
        }
        return string2 + " {" + string4;
    }

    public Structure[] toArray(Structure[] structureArray) {
        int n;
        this.ensureAllocated();
        if (this.memory instanceof AutoAllocated) {
            Memory memory = (Memory)this.memory;
            n = structureArray.length * this.size();
            if (memory.size() < (long)n) {
                this.useMemory(this.autoAllocate(n));
            }
        }
        structureArray[0] = this;
        int n2 = this.size();
        for (n = 1; n < structureArray.length; ++n) {
            structureArray[n] = Structure.newInstance(this.getClass(), this.memory.share(n * n2, n2));
            structureArray[n].conditionalAutoRead();
        }
        if (!(this instanceof ByValue)) {
            this.array = structureArray;
        }
        return structureArray;
    }

    public Structure[] toArray(int n) {
        return this.toArray((Structure[])Array.newInstance(this.getClass(), n));
    }

    private Class<?> baseClass() {
        if ((this instanceof ByReference || this instanceof ByValue) && Structure.class.isAssignableFrom(this.getClass().getSuperclass())) {
            return this.getClass().getSuperclass();
        }
        return this.getClass();
    }

    public boolean dataEquals(Structure structure) {
        return this.dataEquals(structure, true);
    }

    public boolean dataEquals(Structure structure, boolean bl) {
        byte[] byArray;
        byte[] byArray2;
        if (bl) {
            structure.getPointer().clear(structure.size());
            structure.write();
            this.getPointer().clear(this.size());
            this.write();
        }
        if ((byArray2 = structure.getPointer().getByteArray(0L, structure.size())).length == (byArray = this.getPointer().getByteArray(0L, this.size())).length) {
            for (int i = 0; i < byArray2.length; ++i) {
                if (byArray2[i] == byArray[i]) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean equals(Object object) {
        return object instanceof Structure && object.getClass() == this.getClass() && ((Structure)object).getPointer().equals(this.getPointer());
    }

    public int hashCode() {
        Pointer pointer = this.getPointer();
        if (pointer != null) {
            return this.getPointer().hashCode();
        }
        return this.getClass().hashCode();
    }

    protected void cacheTypeInfo(Pointer pointer) {
        this.typeInfo = pointer.peer;
    }

    Pointer getFieldTypeInfo(StructField structField) {
        ToNativeConverter toNativeConverter;
        Class<?> clazz = structField.type;
        Object object = this.getFieldValue(structField.field);
        if (this.typeMapper != null && (toNativeConverter = this.typeMapper.getToNativeConverter(clazz)) != null) {
            clazz = toNativeConverter.nativeType();
            object = toNativeConverter.toNative(object, new ToNativeContext());
        }
        return FFIType.access$800(object, clazz);
    }

    Pointer getTypeInfo() {
        Pointer pointer = Structure.getTypeInfo(this);
        this.cacheTypeInfo(pointer);
        return pointer;
    }

    public void setAutoSynch(boolean bl) {
        this.setAutoRead(bl);
        this.setAutoWrite(bl);
    }

    public void setAutoRead(boolean bl) {
        this.autoRead = bl;
    }

    public boolean getAutoRead() {
        return this.autoRead;
    }

    public void setAutoWrite(boolean bl) {
        this.autoWrite = bl;
    }

    public boolean getAutoWrite() {
        return this.autoWrite;
    }

    static Pointer getTypeInfo(Object object) {
        return FFIType.get(object);
    }

    private static Structure newInstance(Class<?> clazz, long l) {
        try {
            Structure structure = Structure.newInstance(clazz, l == 0L ? PLACEHOLDER_MEMORY : new Pointer(l));
            if (l != 0L) {
                structure.conditionalAutoRead();
            }
            return structure;
        } catch (Throwable throwable) {
            System.err.println("JNA: Error creating structure: " + throwable);
            return null;
        }
    }

    public static Structure newInstance(Class<?> clazz, Pointer pointer) throws IllegalArgumentException {
        try {
            Constructor<?> constructor = clazz.getConstructor(Pointer.class);
            return (Structure)constructor.newInstance(pointer);
        } catch (NoSuchMethodException noSuchMethodException) {
        } catch (SecurityException securityException) {
        } catch (InstantiationException instantiationException) {
            String string = "Can't instantiate " + clazz;
            throw new IllegalArgumentException(string, instantiationException);
        } catch (IllegalAccessException illegalAccessException) {
            String string = "Instantiation of " + clazz + " (Pointer) not allowed, is it public?";
            throw new IllegalArgumentException(string, illegalAccessException);
        } catch (InvocationTargetException invocationTargetException) {
            String string = "Exception thrown while instantiating an instance of " + clazz;
            invocationTargetException.printStackTrace();
            throw new IllegalArgumentException(string, invocationTargetException);
        }
        Structure structure = Structure.newInstance(clazz);
        if (pointer != PLACEHOLDER_MEMORY) {
            structure.useMemory(pointer);
        }
        return structure;
    }

    public static Structure newInstance(Class<?> clazz) throws IllegalArgumentException {
        try {
            Structure structure = (Structure)clazz.newInstance();
            if (structure instanceof ByValue) {
                structure.allocateMemory();
            }
            return structure;
        } catch (InstantiationException instantiationException) {
            String string = "Can't instantiate " + clazz;
            throw new IllegalArgumentException(string, instantiationException);
        } catch (IllegalAccessException illegalAccessException) {
            String string = "Instantiation of " + clazz + " not allowed, is it public?";
            throw new IllegalArgumentException(string, illegalAccessException);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    StructField typeInfoField() {
        LayoutInfo layoutInfo;
        Map<Class<?>, LayoutInfo> map = Structure.layoutInfo;
        synchronized (map) {
            layoutInfo = Structure.layoutInfo.get(this.getClass());
        }
        if (layoutInfo != null) {
            return LayoutInfo.access$700(layoutInfo);
        }
        return null;
    }

    private static void structureArrayCheck(Structure[] structureArray) {
        if (ByReference[].class.isAssignableFrom(structureArray.getClass())) {
            return;
        }
        Pointer pointer = structureArray[0].getPointer();
        int n = structureArray[0].size();
        for (int i = 1; i < structureArray.length; ++i) {
            if (structureArray[i].getPointer().peer == pointer.peer + (long)(n * i)) continue;
            String string = "Structure array elements must use contiguous memory (bad backing address at Structure array index " + i + ")";
            throw new IllegalArgumentException(string);
        }
    }

    public static void autoRead(Structure[] structureArray) {
        Structure.structureArrayCheck(structureArray);
        if (structureArray[0].array == structureArray) {
            structureArray[0].autoRead();
        } else {
            for (int i = 0; i < structureArray.length; ++i) {
                if (structureArray[i] == null) continue;
                structureArray[i].autoRead();
            }
        }
    }

    public void autoRead() {
        if (this.getAutoRead()) {
            this.read();
            if (this.array != null) {
                for (int i = 1; i < this.array.length; ++i) {
                    this.array[i].autoRead();
                }
            }
        }
    }

    public static void autoWrite(Structure[] structureArray) {
        Structure.structureArrayCheck(structureArray);
        if (structureArray[0].array == structureArray) {
            structureArray[0].autoWrite();
        } else {
            for (int i = 0; i < structureArray.length; ++i) {
                if (structureArray[i] == null) continue;
                structureArray[i].autoWrite();
            }
        }
    }

    public void autoWrite() {
        if (this.getAutoWrite()) {
            this.write();
            if (this.array != null) {
                for (int i = 1; i < this.array.length; ++i) {
                    this.array[i].autoWrite();
                }
            }
        }
    }

    protected int getNativeSize(Class<?> clazz) {
        return this.getNativeSize(clazz, null);
    }

    protected int getNativeSize(Class<?> clazz, Object object) {
        return Native.getNativeSize(clazz, object);
    }

    static void validate(Class<?> clazz) {
        Structure.newInstance(clazz, PLACEHOLDER_MEMORY);
    }

    static void access$1900(Structure structure, boolean bl) {
        structure.ensureAllocated(bl);
    }

    static Pointer access$2000() {
        return PLACEHOLDER_MEMORY;
    }

    private static class AutoAllocated
    extends Memory {
        public AutoAllocated(int n) {
            super(n);
            super.clear();
        }

        @Override
        public String toString() {
            return "auto-" + super.toString();
        }
    }

    static class FFIType
    extends Structure {
        private static final Map<Object, Object> typeInfoMap = new WeakHashMap<Object, Object>();
        private static final int FFI_TYPE_STRUCT = 13;
        public size_t size;
        public short alignment;
        public short type = (short)13;
        public Pointer elements;

        private FFIType(Structure structure) {
            Pointer[] pointerArray;
            Structure.access$1900(structure, true);
            if (structure instanceof Union) {
                StructField structField = ((Union)structure).typeInfoField();
                pointerArray = new Pointer[]{FFIType.get(structure.getFieldValue(structField.field), structField.type), null};
            } else {
                pointerArray = new Pointer[structure.fields().size() + 1];
                int n = 0;
                for (StructField structField : structure.fields().values()) {
                    pointerArray[n++] = structure.getFieldTypeInfo(structField);
                }
            }
            this.init(pointerArray);
        }

        private FFIType(Object object, Class<?> clazz) {
            int n = Array.getLength(object);
            Pointer[] pointerArray = new Pointer[n + 1];
            Pointer pointer = FFIType.get(null, clazz.getComponentType());
            for (int i = 0; i < n; ++i) {
                pointerArray[i] = pointer;
            }
            this.init(pointerArray);
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("size", "alignment", "type", "elements");
        }

        private void init(Pointer[] pointerArray) {
            this.elements = new Memory(Pointer.SIZE * pointerArray.length);
            this.elements.write(0L, pointerArray, 0, pointerArray.length);
            this.write();
        }

        static Pointer get(Object object) {
            if (object == null) {
                return FFITypes.access$1800();
            }
            if (object instanceof Class) {
                return FFIType.get(null, (Class)object);
            }
            return FFIType.get(object, object.getClass());
        }

        private static Pointer get(Object object, Class<?> clazz) {
            Object object2;
            TypeMapper typeMapper = Native.getTypeMapper(clazz);
            if (typeMapper != null && (object2 = typeMapper.getToNativeConverter(clazz)) != null) {
                clazz = object2.nativeType();
            }
            object2 = typeInfoMap;
            synchronized (object2) {
                Object object3 = typeInfoMap.get(clazz);
                if (object3 instanceof Pointer) {
                    return (Pointer)object3;
                }
                if (object3 instanceof FFIType) {
                    return ((FFIType)object3).getPointer();
                }
                if (Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(clazz) || Callback.class.isAssignableFrom(clazz)) {
                    typeInfoMap.put(clazz, FFITypes.access$1800());
                    return FFITypes.access$1800();
                }
                if (Structure.class.isAssignableFrom(clazz)) {
                    if (object == null) {
                        object = FFIType.newInstance(clazz, Structure.access$2000());
                    }
                    if (ByReference.class.isAssignableFrom(clazz)) {
                        typeInfoMap.put(clazz, FFITypes.access$1800());
                        return FFITypes.access$1800();
                    }
                    FFIType fFIType = new FFIType((Structure)object);
                    typeInfoMap.put(clazz, fFIType);
                    return fFIType.getPointer();
                }
                if (NativeMapped.class.isAssignableFrom(clazz)) {
                    NativeMappedConverter nativeMappedConverter = NativeMappedConverter.getInstance(clazz);
                    return FFIType.get(nativeMappedConverter.toNative(object, new ToNativeContext()), nativeMappedConverter.nativeType());
                }
                if (clazz.isArray()) {
                    FFIType fFIType = new FFIType(object, clazz);
                    typeInfoMap.put(object, fFIType);
                    return fFIType.getPointer();
                }
                throw new IllegalArgumentException("Unsupported type " + clazz);
            }
        }

        static Pointer access$800(Object object, Class clazz) {
            return FFIType.get(object, clazz);
        }

        static {
            if (Native.POINTER_SIZE == 0) {
                throw new Error("Native library not initialized");
            }
            if (FFITypes.access$900() == null) {
                throw new Error("FFI types not initialized");
            }
            typeInfoMap.put(Void.TYPE, FFITypes.access$900());
            typeInfoMap.put(Void.class, FFITypes.access$900());
            typeInfoMap.put(Float.TYPE, FFITypes.access$1000());
            typeInfoMap.put(Float.class, FFITypes.access$1000());
            typeInfoMap.put(Double.TYPE, FFITypes.access$1100());
            typeInfoMap.put(Double.class, FFITypes.access$1100());
            typeInfoMap.put(Long.TYPE, FFITypes.access$1200());
            typeInfoMap.put(Long.class, FFITypes.access$1200());
            typeInfoMap.put(Integer.TYPE, FFITypes.access$1300());
            typeInfoMap.put(Integer.class, FFITypes.access$1300());
            typeInfoMap.put(Short.TYPE, FFITypes.access$1400());
            typeInfoMap.put(Short.class, FFITypes.access$1400());
            Pointer pointer = Native.WCHAR_SIZE == 2 ? FFITypes.access$1500() : FFITypes.access$1600();
            typeInfoMap.put(Character.TYPE, pointer);
            typeInfoMap.put(Character.class, pointer);
            typeInfoMap.put(Byte.TYPE, FFITypes.access$1700());
            typeInfoMap.put(Byte.class, FFITypes.access$1700());
            typeInfoMap.put(Pointer.class, FFITypes.access$1800());
            typeInfoMap.put(String.class, FFITypes.access$1800());
            typeInfoMap.put(WString.class, FFITypes.access$1800());
            typeInfoMap.put(Boolean.TYPE, FFITypes.access$1600());
            typeInfoMap.put(Boolean.class, FFITypes.access$1600());
        }

        private static class FFITypes {
            private static Pointer ffi_type_void;
            private static Pointer ffi_type_float;
            private static Pointer ffi_type_double;
            private static Pointer ffi_type_longdouble;
            private static Pointer ffi_type_uint8;
            private static Pointer ffi_type_sint8;
            private static Pointer ffi_type_uint16;
            private static Pointer ffi_type_sint16;
            private static Pointer ffi_type_uint32;
            private static Pointer ffi_type_sint32;
            private static Pointer ffi_type_uint64;
            private static Pointer ffi_type_sint64;
            private static Pointer ffi_type_pointer;

            private FFITypes() {
            }

            static Pointer access$900() {
                return ffi_type_void;
            }

            static Pointer access$1000() {
                return ffi_type_float;
            }

            static Pointer access$1100() {
                return ffi_type_double;
            }

            static Pointer access$1200() {
                return ffi_type_sint64;
            }

            static Pointer access$1300() {
                return ffi_type_sint32;
            }

            static Pointer access$1400() {
                return ffi_type_sint16;
            }

            static Pointer access$1500() {
                return ffi_type_uint16;
            }

            static Pointer access$1600() {
                return ffi_type_uint32;
            }

            static Pointer access$1700() {
                return ffi_type_sint8;
            }

            static Pointer access$1800() {
                return ffi_type_pointer;
            }
        }

        public static class size_t
        extends IntegerType {
            private static final long serialVersionUID = 1L;

            public size_t() {
                this(0L);
            }

            public size_t(long l) {
                super(Native.SIZE_T_SIZE, l);
            }
        }
    }

    protected static class StructField {
        public String name;
        public Class<?> type;
        public Field field;
        public int size = -1;
        public int offset = -1;
        public boolean isVolatile;
        public boolean isReadOnly;
        public FromNativeConverter readConverter;
        public ToNativeConverter writeConverter;
        public FromNativeContext context;

        protected StructField() {
        }

        public String toString() {
            return this.name + "@" + this.offset + "[" + this.size + "] (" + this.type + ")";
        }
    }

    private static class LayoutInfo {
        private int size = -1;
        private int alignment = 1;
        private final Map<String, StructField> fields = Collections.synchronizedMap(new LinkedHashMap());
        private int alignType = 0;
        private TypeMapper typeMapper;
        private boolean variable;
        private StructField typeInfoField;

        private LayoutInfo() {
        }

        static boolean access$000(LayoutInfo layoutInfo) {
            return layoutInfo.variable;
        }

        static int access$100(LayoutInfo layoutInfo) {
            return layoutInfo.size;
        }

        static int access$200(LayoutInfo layoutInfo) {
            return layoutInfo.alignType;
        }

        static TypeMapper access$300(LayoutInfo layoutInfo) {
            return layoutInfo.typeMapper;
        }

        static int access$400(LayoutInfo layoutInfo) {
            return layoutInfo.alignment;
        }

        static Map access$500(LayoutInfo layoutInfo) {
            return layoutInfo.fields;
        }

        LayoutInfo(1 var1_1) {
            this();
        }

        static int access$202(LayoutInfo layoutInfo, int n) {
            layoutInfo.alignType = n;
            return layoutInfo.alignType;
        }

        static TypeMapper access$302(LayoutInfo layoutInfo, TypeMapper typeMapper) {
            layoutInfo.typeMapper = typeMapper;
            return layoutInfo.typeMapper;
        }

        static boolean access$002(LayoutInfo layoutInfo, boolean bl) {
            layoutInfo.variable = bl;
            return layoutInfo.variable;
        }

        static int access$402(LayoutInfo layoutInfo, int n) {
            layoutInfo.alignment = n;
            return layoutInfo.alignment;
        }

        static StructField access$700(LayoutInfo layoutInfo) {
            return layoutInfo.typeInfoField;
        }

        static StructField access$702(LayoutInfo layoutInfo, StructField structField) {
            layoutInfo.typeInfoField = structField;
            return layoutInfo.typeInfoField;
        }

        static int access$102(LayoutInfo layoutInfo, int n) {
            layoutInfo.size = n;
            return layoutInfo.size;
        }
    }

    static class StructureSet
    extends AbstractCollection<Structure>
    implements Set<Structure> {
        Structure[] elements;
        private int count;

        StructureSet() {
        }

        private void ensureCapacity(int n) {
            if (this.elements == null) {
                this.elements = new Structure[n * 3 / 2];
            } else if (this.elements.length < n) {
                Structure[] structureArray = new Structure[n * 3 / 2];
                System.arraycopy(this.elements, 0, structureArray, 0, this.elements.length);
                this.elements = structureArray;
            }
        }

        public Structure[] getElements() {
            return this.elements;
        }

        @Override
        public int size() {
            return this.count;
        }

        @Override
        public boolean contains(Object object) {
            return this.indexOf((Structure)object) != -1;
        }

        @Override
        public boolean add(Structure structure) {
            if (!this.contains(structure)) {
                this.ensureCapacity(this.count + 1);
                this.elements[this.count++] = structure;
            }
            return false;
        }

        private int indexOf(Structure structure) {
            for (int i = 0; i < this.count; ++i) {
                Structure structure2 = this.elements[i];
                if (structure != structure2 && (structure.getClass() != structure2.getClass() || structure.size() != structure2.size() || !structure.getPointer().equals(structure2.getPointer()))) continue;
                return i;
            }
            return 1;
        }

        @Override
        public boolean remove(Object object) {
            int n = this.indexOf((Structure)object);
            if (n != -1) {
                if (--this.count >= 0) {
                    this.elements[n] = this.elements[this.count];
                    this.elements[this.count] = null;
                }
                return false;
            }
            return true;
        }

        @Override
        public Iterator<Structure> iterator() {
            Structure[] structureArray = new Structure[this.count];
            if (this.count > 0) {
                System.arraycopy(this.elements, 0, structureArray, 0, this.count);
            }
            return Arrays.asList(structureArray).iterator();
        }

        @Override
        public boolean add(Object object) {
            return this.add((Structure)object);
        }
    }

    public static interface ByReference {
    }

    public static interface ByValue {
    }
}

