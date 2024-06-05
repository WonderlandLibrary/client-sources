package com.ibm.icu.impl.locale;

import com.ibm.icu.impl.*;
import com.ibm.icu.util.*;
import java.util.*;
import java.util.regex.*;

public class KeyTypeData
{
    static Set<String> DEPRECATED_KEYS;
    static Map<String, ValueType> VALUE_TYPES;
    static Map<String, Set<String>> DEPRECATED_KEY_TYPES;
    private static final Object[][] KEY_DATA;
    private static final Map<String, KeyData> KEYMAP;
    private static Map<String, Set<String>> BCP47_KEYS;
    
    public static String toBcpKey(String key) {
        key = AsciiUtil.toLowerString(key);
        final KeyData keyData = KeyTypeData.KEYMAP.get(key);
        if (keyData != null) {
            return keyData.bcpId;
        }
        return null;
    }
    
    public static String toLegacyKey(String key) {
        key = AsciiUtil.toLowerString(key);
        final KeyData keyData = KeyTypeData.KEYMAP.get(key);
        if (keyData != null) {
            return keyData.legacyId;
        }
        return null;
    }
    
    public static String toBcpType(String key, String type, final Output<Boolean> isKnownKey, final Output<Boolean> isSpecialType) {
        if (isKnownKey != null) {
            isKnownKey.value = false;
        }
        if (isSpecialType != null) {
            isSpecialType.value = false;
        }
        key = AsciiUtil.toLowerString(key);
        type = AsciiUtil.toLowerString(type);
        final KeyData keyData = KeyTypeData.KEYMAP.get(key);
        if (keyData != null) {
            if (isKnownKey != null) {
                isKnownKey.value = Boolean.TRUE;
            }
            final Type t = keyData.typeMap.get(type);
            if (t != null) {
                return t.bcpId;
            }
            if (keyData.specialTypes != null) {
                for (final SpecialType st : keyData.specialTypes) {
                    if (st.handler.isWellFormed(type)) {
                        if (isSpecialType != null) {
                            isSpecialType.value = true;
                        }
                        return st.handler.canonicalize(type);
                    }
                }
            }
        }
        return null;
    }
    
    public static String toLegacyType(String key, String type, final Output<Boolean> isKnownKey, final Output<Boolean> isSpecialType) {
        if (isKnownKey != null) {
            isKnownKey.value = false;
        }
        if (isSpecialType != null) {
            isSpecialType.value = false;
        }
        key = AsciiUtil.toLowerString(key);
        type = AsciiUtil.toLowerString(type);
        final KeyData keyData = KeyTypeData.KEYMAP.get(key);
        if (keyData != null) {
            if (isKnownKey != null) {
                isKnownKey.value = Boolean.TRUE;
            }
            final Type t = keyData.typeMap.get(type);
            if (t != null) {
                return t.legacyId;
            }
            if (keyData.specialTypes != null) {
                for (final SpecialType st : keyData.specialTypes) {
                    if (st.handler.isWellFormed(type)) {
                        if (isSpecialType != null) {
                            isSpecialType.value = true;
                        }
                        return st.handler.canonicalize(type);
                    }
                }
            }
        }
        return null;
    }
    
    private static void initFromResourceBundle() {
        final UResourceBundle keyTypeDataRes = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", "keyTypeData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        getKeyInfo(keyTypeDataRes.get("keyInfo"));
        getTypeInfo(keyTypeDataRes.get("typeInfo"));
        final UResourceBundle keyMapRes = keyTypeDataRes.get("keyMap");
        final UResourceBundle typeMapRes = keyTypeDataRes.get("typeMap");
        UResourceBundle typeAliasRes = null;
        UResourceBundle bcpTypeAliasRes = null;
        try {
            typeAliasRes = keyTypeDataRes.get("typeAlias");
        }
        catch (MissingResourceException ex) {}
        try {
            bcpTypeAliasRes = keyTypeDataRes.get("bcpTypeAlias");
        }
        catch (MissingResourceException ex2) {}
        final UResourceBundleIterator keyMapItr = keyMapRes.getIterator();
        final Map<String, Set<String>> _Bcp47Keys = new LinkedHashMap<String, Set<String>>();
        while (keyMapItr.hasNext()) {
            final UResourceBundle keyMapEntry = keyMapItr.next();
            final String legacyKeyId = keyMapEntry.getKey();
            String bcpKeyId = keyMapEntry.getString();
            boolean hasSameKey = false;
            if (bcpKeyId.length() == 0) {
                bcpKeyId = legacyKeyId;
                hasSameKey = true;
            }
            final LinkedHashSet<String> _bcp47Types = new LinkedHashSet<String>();
            _Bcp47Keys.put(bcpKeyId, Collections.unmodifiableSet((Set<? extends String>)_bcp47Types));
            final boolean isTZ = legacyKeyId.equals("timezone");
            Map<String, Set<String>> typeAliasMap = null;
            if (typeAliasRes != null) {
                UResourceBundle typeAliasResByKey = null;
                try {
                    typeAliasResByKey = typeAliasRes.get(legacyKeyId);
                }
                catch (MissingResourceException ex3) {}
                if (typeAliasResByKey != null) {
                    typeAliasMap = new HashMap<String, Set<String>>();
                    final UResourceBundleIterator typeAliasResItr = typeAliasResByKey.getIterator();
                    while (typeAliasResItr.hasNext()) {
                        final UResourceBundle typeAliasDataEntry = typeAliasResItr.next();
                        String from = typeAliasDataEntry.getKey();
                        final String to = typeAliasDataEntry.getString();
                        if (isTZ) {
                            from = from.replace(':', '/');
                        }
                        Set<String> aliasSet = typeAliasMap.get(to);
                        if (aliasSet == null) {
                            aliasSet = new HashSet<String>();
                            typeAliasMap.put(to, aliasSet);
                        }
                        aliasSet.add(from);
                    }
                }
            }
            Map<String, Set<String>> bcpTypeAliasMap = null;
            if (bcpTypeAliasRes != null) {
                UResourceBundle bcpTypeAliasResByKey = null;
                try {
                    bcpTypeAliasResByKey = bcpTypeAliasRes.get(bcpKeyId);
                }
                catch (MissingResourceException ex4) {}
                if (bcpTypeAliasResByKey != null) {
                    bcpTypeAliasMap = new HashMap<String, Set<String>>();
                    final UResourceBundleIterator bcpTypeAliasResItr = bcpTypeAliasResByKey.getIterator();
                    while (bcpTypeAliasResItr.hasNext()) {
                        final UResourceBundle bcpTypeAliasDataEntry = bcpTypeAliasResItr.next();
                        final String from2 = bcpTypeAliasDataEntry.getKey();
                        final String to2 = bcpTypeAliasDataEntry.getString();
                        Set<String> aliasSet2 = bcpTypeAliasMap.get(to2);
                        if (aliasSet2 == null) {
                            aliasSet2 = new HashSet<String>();
                            bcpTypeAliasMap.put(to2, aliasSet2);
                        }
                        aliasSet2.add(from2);
                    }
                }
            }
            final Map<String, Type> typeDataMap = new HashMap<String, Type>();
            EnumSet<SpecialType> specialTypeSet = null;
            UResourceBundle typeMapResByKey = null;
            try {
                typeMapResByKey = typeMapRes.get(legacyKeyId);
            }
            catch (MissingResourceException e) {
                assert false;
            }
            if (typeMapResByKey != null) {
                final UResourceBundleIterator typeMapResByKeyItr = typeMapResByKey.getIterator();
                while (typeMapResByKeyItr.hasNext()) {
                    final UResourceBundle typeMapEntry = typeMapResByKeyItr.next();
                    String legacyTypeId = typeMapEntry.getKey();
                    String bcpTypeId = typeMapEntry.getString();
                    final char first = legacyTypeId.charAt(0);
                    final boolean isSpecialType = '9' < first && first < 'a' && bcpTypeId.length() == 0;
                    if (isSpecialType) {
                        if (specialTypeSet == null) {
                            specialTypeSet = EnumSet.noneOf(SpecialType.class);
                        }
                        specialTypeSet.add(SpecialType.valueOf(legacyTypeId));
                        _bcp47Types.add(legacyTypeId);
                    }
                    else {
                        if (isTZ) {
                            legacyTypeId = legacyTypeId.replace(':', '/');
                        }
                        boolean hasSameType = false;
                        if (bcpTypeId.length() == 0) {
                            bcpTypeId = legacyTypeId;
                            hasSameType = true;
                        }
                        _bcp47Types.add(bcpTypeId);
                        final Type t = new Type(legacyTypeId, bcpTypeId);
                        typeDataMap.put(AsciiUtil.toLowerString(legacyTypeId), t);
                        if (!hasSameType) {
                            typeDataMap.put(AsciiUtil.toLowerString(bcpTypeId), t);
                        }
                        if (typeAliasMap != null) {
                            final Set<String> typeAliasSet = typeAliasMap.get(legacyTypeId);
                            if (typeAliasSet != null) {
                                for (final String alias : typeAliasSet) {
                                    typeDataMap.put(AsciiUtil.toLowerString(alias), t);
                                }
                            }
                        }
                        if (bcpTypeAliasMap == null) {
                            continue;
                        }
                        final Set<String> bcpTypeAliasSet = bcpTypeAliasMap.get(bcpTypeId);
                        if (bcpTypeAliasSet == null) {
                            continue;
                        }
                        for (final String alias : bcpTypeAliasSet) {
                            typeDataMap.put(AsciiUtil.toLowerString(alias), t);
                        }
                    }
                }
            }
            final KeyData keyData = new KeyData(legacyKeyId, bcpKeyId, typeDataMap, specialTypeSet);
            KeyTypeData.KEYMAP.put(AsciiUtil.toLowerString(legacyKeyId), keyData);
            if (!hasSameKey) {
                KeyTypeData.KEYMAP.put(AsciiUtil.toLowerString(bcpKeyId), keyData);
            }
        }
        KeyTypeData.BCP47_KEYS = Collections.unmodifiableMap((Map<? extends String, ? extends Set<String>>)_Bcp47Keys);
    }
    
    private static void getKeyInfo(final UResourceBundle keyInfoRes) {
        final Set<String> _deprecatedKeys = new LinkedHashSet<String>();
        final Map<String, ValueType> _valueTypes = new LinkedHashMap<String, ValueType>();
        final UResourceBundleIterator keyInfoIt = keyInfoRes.getIterator();
        while (keyInfoIt.hasNext()) {
            final UResourceBundle keyInfoEntry = keyInfoIt.next();
            final String key = keyInfoEntry.getKey();
            final KeyInfoType keyInfo = KeyInfoType.valueOf(key);
            final UResourceBundleIterator keyInfoIt2 = keyInfoEntry.getIterator();
            while (keyInfoIt2.hasNext()) {
                final UResourceBundle keyInfoEntry2 = keyInfoIt2.next();
                final String key2 = keyInfoEntry2.getKey();
                final String value2 = keyInfoEntry2.getString();
                switch (keyInfo) {
                    case deprecated: {
                        _deprecatedKeys.add(key2);
                        continue;
                    }
                    case valueType: {
                        _valueTypes.put(key2, ValueType.valueOf(value2));
                        continue;
                    }
                }
            }
        }
        KeyTypeData.DEPRECATED_KEYS = Collections.unmodifiableSet((Set<? extends String>)_deprecatedKeys);
        KeyTypeData.VALUE_TYPES = Collections.unmodifiableMap((Map<? extends String, ? extends ValueType>)_valueTypes);
    }
    
    private static void getTypeInfo(final UResourceBundle typeInfoRes) {
        final Map<String, Set<String>> _deprecatedKeyTypes = new LinkedHashMap<String, Set<String>>();
        final UResourceBundleIterator keyInfoIt = typeInfoRes.getIterator();
        while (keyInfoIt.hasNext()) {
            final UResourceBundle keyInfoEntry = keyInfoIt.next();
            final String key = keyInfoEntry.getKey();
            final TypeInfoType typeInfo = TypeInfoType.valueOf(key);
            final UResourceBundleIterator keyInfoIt2 = keyInfoEntry.getIterator();
            while (keyInfoIt2.hasNext()) {
                final UResourceBundle keyInfoEntry2 = keyInfoIt2.next();
                final String key2 = keyInfoEntry2.getKey();
                final Set<String> _deprecatedTypes = new LinkedHashSet<String>();
                final UResourceBundleIterator keyInfoIt3 = keyInfoEntry2.getIterator();
                while (keyInfoIt3.hasNext()) {
                    final UResourceBundle keyInfoEntry3 = keyInfoIt3.next();
                    final String key3 = keyInfoEntry3.getKey();
                    switch (typeInfo) {
                        case deprecated: {
                            _deprecatedTypes.add(key3);
                            continue;
                        }
                    }
                }
                _deprecatedKeyTypes.put(key2, Collections.unmodifiableSet((Set<? extends String>)_deprecatedTypes));
            }
        }
        KeyTypeData.DEPRECATED_KEY_TYPES = Collections.unmodifiableMap((Map<? extends String, ? extends Set<String>>)_deprecatedKeyTypes);
    }
    
    private static void initFromTables() {
        for (final Object[] keyDataEntry : KeyTypeData.KEY_DATA) {
            final String legacyKeyId = (String)keyDataEntry[0];
            String bcpKeyId = (String)keyDataEntry[1];
            final String[][] typeData = (String[][])keyDataEntry[2];
            final String[][] typeAliasData = (String[][])keyDataEntry[3];
            final String[][] bcpTypeAliasData = (String[][])keyDataEntry[4];
            boolean hasSameKey = false;
            if (bcpKeyId == null) {
                bcpKeyId = legacyKeyId;
                hasSameKey = true;
            }
            Map<String, Set<String>> typeAliasMap = null;
            if (typeAliasData != null) {
                typeAliasMap = new HashMap<String, Set<String>>();
                for (final String[] typeAliasDataEntry : typeAliasData) {
                    final String from = typeAliasDataEntry[0];
                    final String to = typeAliasDataEntry[1];
                    Set<String> aliasSet = typeAliasMap.get(to);
                    if (aliasSet == null) {
                        aliasSet = new HashSet<String>();
                        typeAliasMap.put(to, aliasSet);
                    }
                    aliasSet.add(from);
                }
            }
            Map<String, Set<String>> bcpTypeAliasMap = null;
            if (bcpTypeAliasData != null) {
                bcpTypeAliasMap = new HashMap<String, Set<String>>();
                for (final String[] bcpTypeAliasDataEntry : bcpTypeAliasData) {
                    final String from2 = bcpTypeAliasDataEntry[0];
                    final String to2 = bcpTypeAliasDataEntry[1];
                    Set<String> aliasSet2 = bcpTypeAliasMap.get(to2);
                    if (aliasSet2 == null) {
                        aliasSet2 = new HashSet<String>();
                        bcpTypeAliasMap.put(to2, aliasSet2);
                    }
                    aliasSet2.add(from2);
                }
            }
            assert typeData != null;
            final Map<String, Type> typeDataMap = new HashMap<String, Type>();
            Set<SpecialType> specialTypeSet = null;
            for (final String[] typeDataEntry : typeData) {
                final String legacyTypeId = typeDataEntry[0];
                String bcpTypeId = typeDataEntry[1];
                boolean isSpecialType = false;
                for (final SpecialType st : SpecialType.values()) {
                    if (legacyTypeId.equals(st.toString())) {
                        isSpecialType = true;
                        if (specialTypeSet == null) {
                            specialTypeSet = new HashSet<SpecialType>();
                        }
                        specialTypeSet.add(st);
                        break;
                    }
                }
                if (!isSpecialType) {
                    boolean hasSameType = false;
                    if (bcpTypeId == null) {
                        bcpTypeId = legacyTypeId;
                        hasSameType = true;
                    }
                    final Type t = new Type(legacyTypeId, bcpTypeId);
                    typeDataMap.put(AsciiUtil.toLowerString(legacyTypeId), t);
                    if (!hasSameType) {
                        typeDataMap.put(AsciiUtil.toLowerString(bcpTypeId), t);
                    }
                    final Set<String> typeAliasSet = typeAliasMap.get(legacyTypeId);
                    if (typeAliasSet != null) {
                        for (final String alias : typeAliasSet) {
                            typeDataMap.put(AsciiUtil.toLowerString(alias), t);
                        }
                    }
                    final Set<String> bcpTypeAliasSet = bcpTypeAliasMap.get(bcpTypeId);
                    if (bcpTypeAliasSet != null) {
                        for (final String alias2 : bcpTypeAliasSet) {
                            typeDataMap.put(AsciiUtil.toLowerString(alias2), t);
                        }
                    }
                }
            }
            EnumSet<SpecialType> specialTypes = null;
            if (specialTypeSet != null) {
                specialTypes = EnumSet.copyOf(specialTypeSet);
            }
            final KeyData keyData = new KeyData(legacyKeyId, bcpKeyId, typeDataMap, specialTypes);
            KeyTypeData.KEYMAP.put(AsciiUtil.toLowerString(legacyKeyId), keyData);
            if (!hasSameKey) {
                KeyTypeData.KEYMAP.put(AsciiUtil.toLowerString(bcpKeyId), keyData);
            }
        }
    }
    
    public static Set<String> getBcp47Keys() {
        return KeyTypeData.BCP47_KEYS.keySet();
    }
    
    public static Set<String> getBcp47KeyTypes(final String key) {
        return KeyTypeData.BCP47_KEYS.get(key);
    }
    
    public static boolean isDeprecated(final String key) {
        return KeyTypeData.DEPRECATED_KEYS.contains(key);
    }
    
    public static boolean isDeprecated(final String key, final String type) {
        final Set<String> deprecatedTypes = KeyTypeData.DEPRECATED_KEY_TYPES.get(key);
        return deprecatedTypes != null && deprecatedTypes.contains(type);
    }
    
    public static ValueType getValueType(final String key) {
        final ValueType type = KeyTypeData.VALUE_TYPES.get(key);
        return (type == null) ? ValueType.single : type;
    }
    
    static {
        KeyTypeData.DEPRECATED_KEYS = Collections.emptySet();
        KeyTypeData.VALUE_TYPES = Collections.emptyMap();
        KeyTypeData.DEPRECATED_KEY_TYPES = Collections.emptyMap();
        KEY_DATA = new Object[0][];
        KEYMAP = new HashMap<String, KeyData>();
        initFromResourceBundle();
    }
    
    public enum ValueType
    {
        single, 
        multiple, 
        incremental, 
        any;
    }
    
    private abstract static class SpecialTypeHandler
    {
        abstract boolean isWellFormed(final String p0);
        
        String canonicalize(final String value) {
            return AsciiUtil.toLowerString(value);
        }
    }
    
    private static class CodepointsTypeHandler extends SpecialTypeHandler
    {
        private static final Pattern pat;
        
        @Override
        boolean isWellFormed(final String value) {
            return CodepointsTypeHandler.pat.matcher(value).matches();
        }
        
        static {
            pat = Pattern.compile("[0-9a-fA-F]{4,6}(-[0-9a-fA-F]{4,6})*");
        }
    }
    
    private static class ReorderCodeTypeHandler extends SpecialTypeHandler
    {
        private static final Pattern pat;
        
        @Override
        boolean isWellFormed(final String value) {
            return ReorderCodeTypeHandler.pat.matcher(value).matches();
        }
        
        static {
            pat = Pattern.compile("[a-zA-Z]{3,8}(-[a-zA-Z]{3,8})*");
        }
    }
    
    private static class RgKeyValueTypeHandler extends SpecialTypeHandler
    {
        private static final Pattern pat;
        
        @Override
        boolean isWellFormed(final String value) {
            return RgKeyValueTypeHandler.pat.matcher(value).matches();
        }
        
        static {
            pat = Pattern.compile("([a-zA-Z]{2}|[0-9]{3})[zZ]{4}");
        }
    }
    
    private static class SubdivisionKeyValueTypeHandler extends SpecialTypeHandler
    {
        private static final Pattern pat;
        
        @Override
        boolean isWellFormed(final String value) {
            return SubdivisionKeyValueTypeHandler.pat.matcher(value).matches();
        }
        
        static {
            pat = Pattern.compile("([a-zA-Z]{2}|[0-9]{3})");
        }
    }
    
    private static class PrivateUseKeyValueTypeHandler extends SpecialTypeHandler
    {
        private static final Pattern pat;
        
        @Override
        boolean isWellFormed(final String value) {
            return PrivateUseKeyValueTypeHandler.pat.matcher(value).matches();
        }
        
        static {
            pat = Pattern.compile("[a-zA-Z0-9]{3,8}(-[a-zA-Z0-9]{3,8})*");
        }
    }
    
    private enum SpecialType
    {
        CODEPOINTS((SpecialTypeHandler)new CodepointsTypeHandler()), 
        REORDER_CODE((SpecialTypeHandler)new ReorderCodeTypeHandler()), 
        RG_KEY_VALUE((SpecialTypeHandler)new RgKeyValueTypeHandler()), 
        SUBDIVISION_CODE((SpecialTypeHandler)new SubdivisionKeyValueTypeHandler()), 
        PRIVATE_USE((SpecialTypeHandler)new PrivateUseKeyValueTypeHandler());
        
        SpecialTypeHandler handler;
        
        private SpecialType(final SpecialTypeHandler handler) {
            this.handler = handler;
        }
    }
    
    private static class KeyData
    {
        String legacyId;
        String bcpId;
        Map<String, Type> typeMap;
        EnumSet<SpecialType> specialTypes;
        
        KeyData(final String legacyId, final String bcpId, final Map<String, Type> typeMap, final EnumSet<SpecialType> specialTypes) {
            this.legacyId = legacyId;
            this.bcpId = bcpId;
            this.typeMap = typeMap;
            this.specialTypes = specialTypes;
        }
    }
    
    private static class Type
    {
        String legacyId;
        String bcpId;
        
        Type(final String legacyId, final String bcpId) {
            this.legacyId = legacyId;
            this.bcpId = bcpId;
        }
    }
    
    private enum KeyInfoType
    {
        deprecated, 
        valueType;
    }
    
    private enum TypeInfoType
    {
        deprecated;
    }
}
