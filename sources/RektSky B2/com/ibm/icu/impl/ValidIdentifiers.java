package com.ibm.icu.impl;

import com.ibm.icu.impl.locale.*;
import java.util.*;
import com.ibm.icu.util.*;

public class ValidIdentifiers
{
    public static Map<Datatype, Map<Datasubtype, ValiditySet>> getData() {
        return ValidityData.data;
    }
    
    public static Datasubtype isValid(final Datatype datatype, final Set<Datasubtype> datasubtypes, final String code) {
        final Map<Datasubtype, ValiditySet> subtable = ValidityData.data.get(datatype);
        if (subtable != null) {
            for (final Datasubtype datasubtype : datasubtypes) {
                final ValiditySet validitySet = subtable.get(datasubtype);
                if (validitySet != null && validitySet.contains(AsciiUtil.toLowerString(code))) {
                    return datasubtype;
                }
            }
        }
        return null;
    }
    
    public static Datasubtype isValid(final Datatype datatype, final Set<Datasubtype> datasubtypes, String code, String value) {
        final Map<Datasubtype, ValiditySet> subtable = ValidityData.data.get(datatype);
        if (subtable != null) {
            code = AsciiUtil.toLowerString(code);
            value = AsciiUtil.toLowerString(value);
            for (final Datasubtype datasubtype : datasubtypes) {
                final ValiditySet validitySet = subtable.get(datasubtype);
                if (validitySet != null && validitySet.contains(code, value)) {
                    return datasubtype;
                }
            }
        }
        return null;
    }
    
    public enum Datatype
    {
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
    
    public enum Datasubtype
    {
        deprecated, 
        private_use, 
        regular, 
        special, 
        unknown, 
        macroregion;
    }
    
    public static class ValiditySet
    {
        public final Set<String> regularData;
        public final Map<String, Set<String>> subdivisionData;
        
        public ValiditySet(final Set<String> plainData, final boolean makeMap) {
            if (makeMap) {
                final HashMap<String, Set<String>> _subdivisionData = new HashMap<String, Set<String>>();
                for (final String s : plainData) {
                    int pos = s.indexOf(45);
                    int pos2 = pos + 1;
                    if (pos < 0) {
                        pos = (pos2 = ((s.charAt(0) < 'A') ? 3 : 2));
                    }
                    final String key = s.substring(0, pos);
                    final String subdivision = s.substring(pos2);
                    Set<String> oldSet = _subdivisionData.get(key);
                    if (oldSet == null) {
                        _subdivisionData.put(key, oldSet = new HashSet<String>());
                    }
                    oldSet.add(subdivision);
                }
                this.regularData = null;
                final HashMap<String, Set<String>> _subdivisionData2 = new HashMap<String, Set<String>>();
                for (final Map.Entry<String, Set<String>> e : _subdivisionData.entrySet()) {
                    final Set<String> value = e.getValue();
                    final Set<String> set = (value.size() == 1) ? Collections.singleton(value.iterator().next()) : Collections.unmodifiableSet((Set<? extends String>)value);
                    _subdivisionData2.put(e.getKey(), set);
                }
                this.subdivisionData = Collections.unmodifiableMap((Map<? extends String, ? extends Set<String>>)_subdivisionData2);
            }
            else {
                this.regularData = Collections.unmodifiableSet((Set<? extends String>)plainData);
                this.subdivisionData = null;
            }
        }
        
        public boolean contains(final String code) {
            if (this.regularData != null) {
                return this.regularData.contains(code);
            }
            final int pos = code.indexOf(45);
            final String key = code.substring(0, pos);
            final String value = code.substring(pos + 1);
            return this.contains(key, value);
        }
        
        public boolean contains(final String key, final String value) {
            final Set<String> oldSet = this.subdivisionData.get(key);
            return oldSet != null && oldSet.contains(value);
        }
        
        @Override
        public String toString() {
            if (this.regularData != null) {
                return this.regularData.toString();
            }
            return this.subdivisionData.toString();
        }
    }
    
    private static class ValidityData
    {
        static final Map<Datatype, Map<Datasubtype, ValiditySet>> data;
        
        private static void addRange(String string, final Set<String> subvalues) {
            string = AsciiUtil.toLowerString(string);
            final int pos = string.indexOf(126);
            if (pos < 0) {
                subvalues.add(string);
            }
            else {
                StringRange.expand(string.substring(0, pos), string.substring(pos + 1), false, subvalues);
            }
        }
        
        static {
            final Map<Datatype, Map<Datasubtype, ValiditySet>> _data = new EnumMap<Datatype, Map<Datasubtype, ValiditySet>>(Datatype.class);
            final UResourceBundle suppData = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
            final UResourceBundle validityInfo = suppData.get("idValidity");
            final UResourceBundleIterator datatypeIterator = validityInfo.getIterator();
            while (datatypeIterator.hasNext()) {
                final UResourceBundle datatype = datatypeIterator.next();
                final String rawKey = datatype.getKey();
                final Datatype key = Datatype.valueOf(rawKey);
                final Map<Datasubtype, ValiditySet> values = new EnumMap<Datasubtype, ValiditySet>(Datasubtype.class);
                final UResourceBundleIterator datasubtypeIterator = datatype.getIterator();
                while (datasubtypeIterator.hasNext()) {
                    final UResourceBundle datasubtype = datasubtypeIterator.next();
                    final String rawsubkey = datasubtype.getKey();
                    final Datasubtype subkey = Datasubtype.valueOf(rawsubkey);
                    final Set<String> subvalues = new HashSet<String>();
                    if (datasubtype.getType() == 0) {
                        addRange(datasubtype.getString(), subvalues);
                    }
                    else {
                        for (final String string : datasubtype.getStringArray()) {
                            addRange(string, subvalues);
                        }
                    }
                    values.put(subkey, new ValiditySet(subvalues, key == Datatype.subdivision));
                }
                _data.put(key, Collections.unmodifiableMap((Map<? extends Datasubtype, ? extends ValiditySet>)values));
            }
            data = Collections.unmodifiableMap((Map<? extends Datatype, ? extends Map<Datasubtype, ValiditySet>>)_data);
        }
    }
}
