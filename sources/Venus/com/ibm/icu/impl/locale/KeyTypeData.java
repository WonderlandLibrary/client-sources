/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.locale;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.locale.AsciiUtil;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.UResourceBundleIterator;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.regex.Pattern;

public class KeyTypeData {
    static Set<String> DEPRECATED_KEYS;
    static Map<String, ValueType> VALUE_TYPES;
    static Map<String, Set<String>> DEPRECATED_KEY_TYPES;
    private static final Object[][] KEY_DATA;
    private static final Map<String, KeyData> KEYMAP;
    private static Map<String, Set<String>> BCP47_KEYS;
    static final boolean $assertionsDisabled;

    public static String toBcpKey(String string) {
        KeyData keyData = KEYMAP.get(string = AsciiUtil.toLowerString(string));
        if (keyData != null) {
            return keyData.bcpId;
        }
        return null;
    }

    public static String toLegacyKey(String string) {
        KeyData keyData = KEYMAP.get(string = AsciiUtil.toLowerString(string));
        if (keyData != null) {
            return keyData.legacyId;
        }
        return null;
    }

    public static String toBcpType(String string, String string2, Output<Boolean> output, Output<Boolean> output2) {
        if (output != null) {
            output.value = false;
        }
        if (output2 != null) {
            output2.value = false;
        }
        string = AsciiUtil.toLowerString(string);
        string2 = AsciiUtil.toLowerString(string2);
        KeyData keyData = KEYMAP.get(string);
        if (keyData != null) {
            Type type;
            if (output != null) {
                output.value = Boolean.TRUE;
            }
            if ((type = keyData.typeMap.get(string2)) != null) {
                return type.bcpId;
            }
            if (keyData.specialTypes != null) {
                for (SpecialType specialType : keyData.specialTypes) {
                    if (!specialType.handler.isWellFormed(string2)) continue;
                    if (output2 != null) {
                        output2.value = true;
                    }
                    return specialType.handler.canonicalize(string2);
                }
            }
        }
        return null;
    }

    public static String toLegacyType(String string, String string2, Output<Boolean> output, Output<Boolean> output2) {
        if (output != null) {
            output.value = false;
        }
        if (output2 != null) {
            output2.value = false;
        }
        string = AsciiUtil.toLowerString(string);
        string2 = AsciiUtil.toLowerString(string2);
        KeyData keyData = KEYMAP.get(string);
        if (keyData != null) {
            Type type;
            if (output != null) {
                output.value = Boolean.TRUE;
            }
            if ((type = keyData.typeMap.get(string2)) != null) {
                return type.legacyId;
            }
            if (keyData.specialTypes != null) {
                for (SpecialType specialType : keyData.specialTypes) {
                    if (!specialType.handler.isWellFormed(string2)) continue;
                    if (output2 != null) {
                        output2.value = true;
                    }
                    return specialType.handler.canonicalize(string2);
                }
            }
        }
        return null;
    }

    private static void initFromResourceBundle() {
        ICUResourceBundle iCUResourceBundle = ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "keyTypeData", ICUResourceBundle.ICU_DATA_CLASS_LOADER, ICUResourceBundle.OpenType.DIRECT);
        KeyTypeData.getKeyInfo(iCUResourceBundle.get("keyInfo"));
        KeyTypeData.getTypeInfo(iCUResourceBundle.get("typeInfo"));
        UResourceBundle uResourceBundle = iCUResourceBundle.get("keyMap");
        UResourceBundle uResourceBundle2 = iCUResourceBundle.get("typeMap");
        UResourceBundle uResourceBundle3 = null;
        UResourceBundle uResourceBundle4 = null;
        try {
            uResourceBundle3 = iCUResourceBundle.get("typeAlias");
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        try {
            uResourceBundle4 = iCUResourceBundle.get("bcpTypeAlias");
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        UResourceBundleIterator uResourceBundleIterator = uResourceBundle.getIterator();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        while (uResourceBundleIterator.hasNext()) {
            Object object;
            Object object2;
            Object object3;
            Object object4;
            EnumSet<SpecialType> enumSet;
            Object object5;
            Object object6;
            HashMap<Object, HashSet<Object>> hashMap;
            boolean bl;
            LinkedHashSet<Object> linkedHashSet;
            boolean bl2;
            String string;
            String string2;
            block31: {
                UResourceBundle uResourceBundle5 = uResourceBundleIterator.next();
                string2 = uResourceBundle5.getKey();
                string = uResourceBundle5.getString();
                bl2 = false;
                if (string.length() == 0) {
                    string = string2;
                    bl2 = true;
                }
                linkedHashSet = new LinkedHashSet<Object>();
                linkedHashMap.put(string, Collections.unmodifiableSet(linkedHashSet));
                bl = string2.equals("timezone");
                hashMap = null;
                if (uResourceBundle3 != null) {
                    object6 = null;
                    try {
                        object6 = uResourceBundle3.get(string2);
                    } catch (MissingResourceException missingResourceException) {
                        // empty catch block
                    }
                    if (object6 != null) {
                        hashMap = new HashMap<Object, HashSet<Object>>();
                        object5 = ((UResourceBundle)object6).getIterator();
                        while (((UResourceBundleIterator)object5).hasNext()) {
                            enumSet = ((UResourceBundleIterator)object5).next();
                            object4 = ((UResourceBundle)((Object)enumSet)).getKey();
                            object3 = ((UResourceBundle)((Object)enumSet)).getString();
                            if (bl) {
                                object4 = ((String)object4).replace(':', '/');
                            }
                            if ((object2 = (Set)hashMap.get(object3)) == null) {
                                object2 = new HashSet<Object>();
                                hashMap.put(object3, (HashSet<Object>)object2);
                            }
                            object2.add(object4);
                        }
                    }
                }
                object6 = null;
                if (uResourceBundle4 != null) {
                    object5 = null;
                    try {
                        object5 = uResourceBundle4.get(string);
                    } catch (MissingResourceException missingResourceException) {
                        // empty catch block
                    }
                    if (object5 != null) {
                        object6 = new HashMap();
                        enumSet = ((UResourceBundle)object5).getIterator();
                        while (((UResourceBundleIterator)((Object)enumSet)).hasNext()) {
                            object4 = ((UResourceBundleIterator)((Object)enumSet)).next();
                            object3 = ((UResourceBundle)object4).getKey();
                            object2 = ((UResourceBundle)object4).getString();
                            object = (Set)object6.get(object2);
                            if (object == null) {
                                object = new HashSet<Object>();
                                object6.put(object2, object);
                            }
                            object.add(object3);
                        }
                    }
                }
                object5 = new HashMap();
                enumSet = null;
                object4 = null;
                try {
                    object4 = uResourceBundle2.get(string2);
                } catch (MissingResourceException missingResourceException) {
                    if ($assertionsDisabled) break block31;
                    throw new AssertionError();
                }
            }
            if (object4 != null) {
                object3 = ((UResourceBundle)object4).getIterator();
                while (((UResourceBundleIterator)object3).hasNext()) {
                    Set set;
                    boolean bl3;
                    object2 = ((UResourceBundleIterator)object3).next();
                    object = ((UResourceBundle)object2).getKey();
                    Object object7 = ((UResourceBundle)object2).getString();
                    char c = ((String)object).charAt(0);
                    boolean bl4 = bl3 = '9' < c && c < 'a' && ((String)object7).length() == 0;
                    if (bl3) {
                        if (enumSet == null) {
                            enumSet = EnumSet.noneOf(SpecialType.class);
                        }
                        enumSet.add(SpecialType.valueOf(object));
                        linkedHashSet.add(object);
                        continue;
                    }
                    if (bl) {
                        object = ((String)object).replace(':', '/');
                    }
                    boolean bl5 = false;
                    if (((String)object7).length() == 0) {
                        object7 = object;
                        bl5 = true;
                    }
                    linkedHashSet.add(object7);
                    Type type = new Type((String)object, (String)object7);
                    object5.put(AsciiUtil.toLowerString((String)object), type);
                    if (!bl5) {
                        object5.put(AsciiUtil.toLowerString((String)object7), type);
                    }
                    if (hashMap != null && (set = (Set)hashMap.get(object)) != null) {
                        for (String string3 : set) {
                            object5.put(AsciiUtil.toLowerString(string3), type);
                        }
                    }
                    if (object6 == null || (set = (Set)object6.get(object7)) == null) continue;
                    for (String string3 : set) {
                        object5.put(AsciiUtil.toLowerString(string3), type);
                    }
                }
            }
            object3 = new KeyData(string2, string, (Map<String, Type>)object5, enumSet);
            KEYMAP.put(AsciiUtil.toLowerString(string2), (KeyData)object3);
            if (bl2) continue;
            KEYMAP.put(AsciiUtil.toLowerString(string), (KeyData)object3);
        }
        BCP47_KEYS = Collections.unmodifiableMap(linkedHashMap);
    }

    private static void getKeyInfo(UResourceBundle uResourceBundle) {
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
        LinkedHashMap<String, ValueType> linkedHashMap = new LinkedHashMap<String, ValueType>();
        UResourceBundleIterator uResourceBundleIterator = uResourceBundle.getIterator();
        while (uResourceBundleIterator.hasNext()) {
            UResourceBundle uResourceBundle2 = uResourceBundleIterator.next();
            String string = uResourceBundle2.getKey();
            KeyInfoType keyInfoType = KeyInfoType.valueOf(string);
            UResourceBundleIterator uResourceBundleIterator2 = uResourceBundle2.getIterator();
            while (uResourceBundleIterator2.hasNext()) {
                UResourceBundle uResourceBundle3 = uResourceBundleIterator2.next();
                String string2 = uResourceBundle3.getKey();
                String string3 = uResourceBundle3.getString();
                switch (keyInfoType) {
                    case deprecated: {
                        linkedHashSet.add(string2);
                        break;
                    }
                    case valueType: {
                        linkedHashMap.put(string2, ValueType.valueOf(string3));
                    }
                }
            }
        }
        DEPRECATED_KEYS = Collections.unmodifiableSet(linkedHashSet);
        VALUE_TYPES = Collections.unmodifiableMap(linkedHashMap);
    }

    private static void getTypeInfo(UResourceBundle uResourceBundle) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        UResourceBundleIterator uResourceBundleIterator = uResourceBundle.getIterator();
        while (uResourceBundleIterator.hasNext()) {
            UResourceBundle uResourceBundle2 = uResourceBundleIterator.next();
            String string = uResourceBundle2.getKey();
            TypeInfoType typeInfoType = TypeInfoType.valueOf(string);
            UResourceBundleIterator uResourceBundleIterator2 = uResourceBundle2.getIterator();
            while (uResourceBundleIterator2.hasNext()) {
                UResourceBundle uResourceBundle3 = uResourceBundleIterator2.next();
                String string2 = uResourceBundle3.getKey();
                LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
                UResourceBundleIterator uResourceBundleIterator3 = uResourceBundle3.getIterator();
                while (uResourceBundleIterator3.hasNext()) {
                    UResourceBundle uResourceBundle4 = uResourceBundleIterator3.next();
                    String string3 = uResourceBundle4.getKey();
                    switch (typeInfoType) {
                        case deprecated: {
                            linkedHashSet.add(string3);
                        }
                    }
                }
                linkedHashMap.put(string2, Collections.unmodifiableSet(linkedHashSet));
            }
        }
        DEPRECATED_KEY_TYPES = Collections.unmodifiableMap(linkedHashMap);
    }

    /*
     * WARNING - void declaration
     */
    private static void initFromTables() {
        for (Object[] objectArray : KEY_DATA) {
            void object42;
            Set<String> set;
            String string;
            Object object;
            String string2 = (String)objectArray[0];
            String string22 = (String)objectArray[5];
            String[][] stringArray = (String[][])objectArray[5];
            String[][] stringArray2 = (String[][])objectArray[5];
            String[][] stringArray3 = (String[][])objectArray[5];
            boolean bl = false;
            if (string22 == null) {
                string22 = string2;
                bl = true;
            }
            HashMap hashMap = null;
            if (stringArray2 != null) {
                hashMap = new HashMap();
                for (String[] stringArray4 : stringArray2) {
                    void var17_32;
                    object = stringArray4[0];
                    string = stringArray4[5];
                    Set set2 = (Set)hashMap.get(string);
                    if (set2 == null) {
                        HashSet hashSet = new HashSet();
                        hashMap.put(string, hashSet);
                    }
                    var17_32.add(object);
                }
            }
            Object object5 = null;
            if (stringArray3 != null) {
                void var14_21;
                object5 = new HashMap();
                String[][] stringArray5 = stringArray3;
                int n = stringArray5.length;
                boolean bl2 = false;
                while (var14_21 < n) {
                    object = stringArray5[var14_21];
                    string = object[0];
                    String string3 = object[5];
                    set = (Set)object5.get(string3);
                    if (set == null) {
                        set = new HashSet<String>();
                        object5.put(string3, set);
                    }
                    set.add(string);
                    ++var14_21;
                }
            }
            if (!$assertionsDisabled && stringArray == null) {
                throw new AssertionError();
            }
            HashMap<String, Type> hashMap2 = new HashMap<String, Type>();
            HashSet<SpecialType> hashSet = null;
            for (String[] stringArray6 : stringArray) {
                Object object2;
                Set set3;
                Object object32;
                set = stringArray6[0];
                Object object4 = stringArray6[5];
                boolean bl3 = false;
                for (Object object32 : SpecialType.values()) {
                    if (!((String)((Object)set)).equals(object32.toString())) continue;
                    bl3 = true;
                    if (hashSet == null) {
                        hashSet = new HashSet<SpecialType>();
                    }
                    hashSet.add((SpecialType)((Object)object32));
                    break;
                }
                if (bl3) continue;
                boolean bl4 = false;
                if (object4 == null) {
                    object4 = set;
                    bl4 = true;
                }
                Type type = new Type((String)((Object)set), (String)object4);
                hashMap2.put(AsciiUtil.toLowerString(set), type);
                if (!bl4) {
                    hashMap2.put(AsciiUtil.toLowerString((String)object4), type);
                }
                if ((set3 = (Set)hashMap.get(set)) != null) {
                    object32 = set3.iterator();
                    while (object32.hasNext()) {
                        object2 = (String)object32.next();
                        hashMap2.put(AsciiUtil.toLowerString((String)object2), type);
                    }
                }
                if ((object32 = (Set)object5.get(object4)) == null) continue;
                object2 = object32.iterator();
                while (object2.hasNext()) {
                    String string4 = (String)object2.next();
                    hashMap2.put(AsciiUtil.toLowerString(string4), type);
                }
            }
            Object var14_23 = null;
            if (hashSet != null) {
                EnumSet enumSet = EnumSet.copyOf(hashSet);
            }
            object = new KeyData(string2, string22, hashMap2, (EnumSet<SpecialType>)object42);
            KEYMAP.put(AsciiUtil.toLowerString(string2), (KeyData)object);
            if (bl) continue;
            KEYMAP.put(AsciiUtil.toLowerString(string22), (KeyData)object);
        }
    }

    public static Set<String> getBcp47Keys() {
        return BCP47_KEYS.keySet();
    }

    public static Set<String> getBcp47KeyTypes(String string) {
        return BCP47_KEYS.get(string);
    }

    public static boolean isDeprecated(String string) {
        return DEPRECATED_KEYS.contains(string);
    }

    public static boolean isDeprecated(String string, String string2) {
        Set<String> set = DEPRECATED_KEY_TYPES.get(string);
        if (set == null) {
            return true;
        }
        return set.contains(string2);
    }

    public static ValueType getValueType(String string) {
        ValueType valueType = VALUE_TYPES.get(string);
        return valueType == null ? ValueType.single : valueType;
    }

    static {
        $assertionsDisabled = !KeyTypeData.class.desiredAssertionStatus();
        DEPRECATED_KEYS = Collections.emptySet();
        VALUE_TYPES = Collections.emptyMap();
        DEPRECATED_KEY_TYPES = Collections.emptyMap();
        KEY_DATA = new Object[0][];
        KEYMAP = new HashMap<String, KeyData>();
        KeyTypeData.initFromResourceBundle();
    }

    private static enum TypeInfoType {
        deprecated;

    }

    private static enum KeyInfoType {
        deprecated,
        valueType;

    }

    private static class Type {
        String legacyId;
        String bcpId;

        Type(String string, String string2) {
            this.legacyId = string;
            this.bcpId = string2;
        }
    }

    private static class KeyData {
        String legacyId;
        String bcpId;
        Map<String, Type> typeMap;
        EnumSet<SpecialType> specialTypes;

        KeyData(String string, String string2, Map<String, Type> map, EnumSet<SpecialType> enumSet) {
            this.legacyId = string;
            this.bcpId = string2;
            this.typeMap = map;
            this.specialTypes = enumSet;
        }
    }

    private static enum SpecialType {
        CODEPOINTS(new CodepointsTypeHandler(null)),
        REORDER_CODE(new ReorderCodeTypeHandler(null)),
        RG_KEY_VALUE(new RgKeyValueTypeHandler(null)),
        SUBDIVISION_CODE(new SubdivisionKeyValueTypeHandler(null)),
        PRIVATE_USE(new PrivateUseKeyValueTypeHandler(null));

        SpecialTypeHandler handler;

        private SpecialType(SpecialTypeHandler specialTypeHandler) {
            this.handler = specialTypeHandler;
        }
    }

    private static class PrivateUseKeyValueTypeHandler
    extends SpecialTypeHandler {
        private static final Pattern pat = Pattern.compile("[a-zA-Z0-9]{3,8}(-[a-zA-Z0-9]{3,8})*");

        private PrivateUseKeyValueTypeHandler() {
            super(null);
        }

        @Override
        boolean isWellFormed(String string) {
            return pat.matcher(string).matches();
        }

        PrivateUseKeyValueTypeHandler(1 var1_1) {
            this();
        }
    }

    private static class SubdivisionKeyValueTypeHandler
    extends SpecialTypeHandler {
        private static final Pattern pat = Pattern.compile("([a-zA-Z]{2}|[0-9]{3})");

        private SubdivisionKeyValueTypeHandler() {
            super(null);
        }

        @Override
        boolean isWellFormed(String string) {
            return pat.matcher(string).matches();
        }

        SubdivisionKeyValueTypeHandler(1 var1_1) {
            this();
        }
    }

    private static class RgKeyValueTypeHandler
    extends SpecialTypeHandler {
        private static final Pattern pat = Pattern.compile("([a-zA-Z]{2}|[0-9]{3})[zZ]{4}");

        private RgKeyValueTypeHandler() {
            super(null);
        }

        @Override
        boolean isWellFormed(String string) {
            return pat.matcher(string).matches();
        }

        RgKeyValueTypeHandler(1 var1_1) {
            this();
        }
    }

    private static class ReorderCodeTypeHandler
    extends SpecialTypeHandler {
        private static final Pattern pat = Pattern.compile("[a-zA-Z]{3,8}(-[a-zA-Z]{3,8})*");

        private ReorderCodeTypeHandler() {
            super(null);
        }

        @Override
        boolean isWellFormed(String string) {
            return pat.matcher(string).matches();
        }

        ReorderCodeTypeHandler(1 var1_1) {
            this();
        }
    }

    private static class CodepointsTypeHandler
    extends SpecialTypeHandler {
        private static final Pattern pat = Pattern.compile("[0-9a-fA-F]{4,6}(-[0-9a-fA-F]{4,6})*");

        private CodepointsTypeHandler() {
            super(null);
        }

        @Override
        boolean isWellFormed(String string) {
            return pat.matcher(string).matches();
        }

        CodepointsTypeHandler(1 var1_1) {
            this();
        }
    }

    private static abstract class SpecialTypeHandler {
        private SpecialTypeHandler() {
        }

        abstract boolean isWellFormed(String var1);

        String canonicalize(String string) {
            return AsciiUtil.toLowerString(string);
        }

        SpecialTypeHandler(1 var1_1) {
            this();
        }
    }

    public static enum ValueType {
        single,
        multiple,
        incremental,
        any;

    }
}

