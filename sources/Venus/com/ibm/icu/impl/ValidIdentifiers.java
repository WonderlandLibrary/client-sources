/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.StringRange;
import com.ibm.icu.impl.locale.AsciiUtil;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.UResourceBundleIterator;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ValidIdentifiers {
    public static Map<Datatype, Map<Datasubtype, ValiditySet>> getData() {
        return ValidityData.data;
    }

    public static Datasubtype isValid(Datatype datatype, Set<Datasubtype> set, String string) {
        Map<Datasubtype, ValiditySet> map = ValidityData.data.get((Object)datatype);
        if (map != null) {
            for (Datasubtype datasubtype : set) {
                ValiditySet validitySet = map.get((Object)datasubtype);
                if (validitySet == null || !validitySet.contains(AsciiUtil.toLowerString(string))) continue;
                return datasubtype;
            }
        }
        return null;
    }

    public static Datasubtype isValid(Datatype datatype, Set<Datasubtype> set, String string, String string2) {
        Map<Datasubtype, ValiditySet> map = ValidityData.data.get((Object)datatype);
        if (map != null) {
            string = AsciiUtil.toLowerString(string);
            string2 = AsciiUtil.toLowerString(string2);
            for (Datasubtype datasubtype : set) {
                ValiditySet validitySet = map.get((Object)datasubtype);
                if (validitySet == null || !validitySet.contains(string, string2)) continue;
                return datasubtype;
            }
        }
        return null;
    }

    private static class ValidityData {
        static final Map<Datatype, Map<Datasubtype, ValiditySet>> data;

        private ValidityData() {
        }

        private static void addRange(String string, Set<String> set) {
            int n = (string = AsciiUtil.toLowerString(string)).indexOf(126);
            if (n < 0) {
                set.add(string);
            } else {
                StringRange.expand(string.substring(0, n), string.substring(n + 1), false, set);
            }
        }

        static {
            EnumMap enumMap = new EnumMap(Datatype.class);
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
            UResourceBundle uResourceBundle2 = uResourceBundle.get("idValidity");
            UResourceBundleIterator uResourceBundleIterator = uResourceBundle2.getIterator();
            while (uResourceBundleIterator.hasNext()) {
                UResourceBundle uResourceBundle3 = uResourceBundleIterator.next();
                String string = uResourceBundle3.getKey();
                Datatype datatype = Datatype.valueOf(string);
                EnumMap<Datasubtype, ValiditySet> enumMap2 = new EnumMap<Datasubtype, ValiditySet>(Datasubtype.class);
                UResourceBundleIterator uResourceBundleIterator2 = uResourceBundle3.getIterator();
                while (uResourceBundleIterator2.hasNext()) {
                    UResourceBundle uResourceBundle4 = uResourceBundleIterator2.next();
                    String string2 = uResourceBundle4.getKey();
                    Datasubtype datasubtype = Datasubtype.valueOf(string2);
                    HashSet<String> hashSet = new HashSet<String>();
                    if (uResourceBundle4.getType() == 0) {
                        ValidityData.addRange(uResourceBundle4.getString(), hashSet);
                    } else {
                        for (String string3 : uResourceBundle4.getStringArray()) {
                            ValidityData.addRange(string3, hashSet);
                        }
                    }
                    enumMap2.put(datasubtype, new ValiditySet(hashSet, datatype == Datatype.subdivision));
                }
                enumMap.put(datatype, Collections.unmodifiableMap(enumMap2));
            }
            data = Collections.unmodifiableMap(enumMap);
        }
    }

    public static class ValiditySet {
        public final Set<String> regularData;
        public final Map<String, Set<String>> subdivisionData;

        public ValiditySet(Set<String> set, boolean bl) {
            if (bl) {
                Object object;
                HashMap<Object, HashSet<String>> hashMap = new HashMap<Object, HashSet<String>>();
                for (String object2 : set) {
                    int n = object2.indexOf(45);
                    int n2 = n + 1;
                    if (n < 0) {
                        n = object2.charAt(0) < 'A' ? 3 : 2;
                        n2 = n;
                    }
                    object = object2.substring(0, n);
                    String string = object2.substring(n2);
                    HashSet<String> hashSet = (HashSet<String>)hashMap.get(object);
                    if (hashSet == null) {
                        hashSet = new HashSet<String>();
                        hashMap.put(object, hashSet);
                    }
                    hashSet.add(string);
                }
                this.regularData = null;
                HashMap hashMap2 = new HashMap();
                for (Map.Entry entry : hashMap.entrySet()) {
                    Set set2 = (Set)entry.getValue();
                    object = set2.size() == 1 ? Collections.singleton(set2.iterator().next()) : Collections.unmodifiableSet(set2);
                    hashMap2.put(entry.getKey(), object);
                }
                this.subdivisionData = Collections.unmodifiableMap(hashMap2);
            } else {
                this.regularData = Collections.unmodifiableSet(set);
                this.subdivisionData = null;
            }
        }

        public boolean contains(String string) {
            if (this.regularData != null) {
                return this.regularData.contains(string);
            }
            int n = string.indexOf(45);
            String string2 = string.substring(0, n);
            String string3 = string.substring(n + 1);
            return this.contains(string2, string3);
        }

        public boolean contains(String string, String string2) {
            Set<String> set = this.subdivisionData.get(string);
            return set != null && set.contains(string2);
        }

        public String toString() {
            if (this.regularData != null) {
                return this.regularData.toString();
            }
            return this.subdivisionData.toString();
        }
    }

    public static enum Datasubtype {
        deprecated,
        private_use,
        regular,
        special,
        unknown,
        macroregion,
        reserved;

    }

    public static enum Datatype {
        currency,
        language,
        region,
        script,
        subdivision,
        unit,
        variant,
        u,
        t,
        x,
        illegal;

    }
}

