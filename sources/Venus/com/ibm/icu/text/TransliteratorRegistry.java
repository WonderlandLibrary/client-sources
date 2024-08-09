/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.LocaleUtility;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.lang.UScript;
import com.ibm.icu.text.AnyTransliterator;
import com.ibm.icu.text.CompoundTransliterator;
import com.ibm.icu.text.RuleBasedTransliterator;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.text.TransliteratorIDParser;
import com.ibm.icu.text.TransliteratorParser;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.CaseInsensitiveString;
import com.ibm.icu.util.UResourceBundle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

class TransliteratorRegistry {
    private static final char LOCALE_SEP = '_';
    private static final String NO_VARIANT = "";
    private static final String ANY = "Any";
    private Map<CaseInsensitiveString, Object[]> registry = Collections.synchronizedMap(new HashMap());
    private Map<CaseInsensitiveString, Map<CaseInsensitiveString, List<CaseInsensitiveString>>> specDAG = Collections.synchronizedMap(new HashMap());
    private List<CaseInsensitiveString> availableIDs = new ArrayList<CaseInsensitiveString>();
    private static final boolean DEBUG = false;

    public Transliterator get(String string, StringBuffer stringBuffer) {
        Object[] objectArray = this.find(string);
        return objectArray == null ? null : this.instantiateEntry(string, objectArray, stringBuffer);
    }

    public void put(String string, Class<? extends Transliterator> clazz, boolean bl) {
        this.registerEntry(string, clazz, bl);
    }

    public void put(String string, Transliterator.Factory factory, boolean bl) {
        this.registerEntry(string, factory, bl);
    }

    public void put(String string, String string2, int n, boolean bl) {
        this.registerEntry(string, new ResourceEntry(string2, n), bl);
    }

    public void put(String string, String string2, boolean bl) {
        this.registerEntry(string, new AliasEntry(string2), bl);
    }

    public void put(String string, Transliterator transliterator, boolean bl) {
        this.registerEntry(string, transliterator, bl);
    }

    public void remove(String string) {
        String[] stringArray = TransliteratorIDParser.IDtoSTV(string);
        String string2 = TransliteratorIDParser.STVtoID(stringArray[0], stringArray[5], stringArray[5]);
        this.registry.remove(new CaseInsensitiveString(string2));
        this.removeSTV(stringArray[0], stringArray[5], stringArray[5]);
        this.availableIDs.remove(new CaseInsensitiveString(string2));
    }

    public Enumeration<String> getAvailableIDs() {
        return new IDEnumeration(Collections.enumeration(this.availableIDs));
    }

    public Enumeration<String> getAvailableSources() {
        return new IDEnumeration(Collections.enumeration(this.specDAG.keySet()));
    }

    public Enumeration<String> getAvailableTargets(String string) {
        CaseInsensitiveString caseInsensitiveString = new CaseInsensitiveString(string);
        Map<CaseInsensitiveString, List<CaseInsensitiveString>> map = this.specDAG.get(caseInsensitiveString);
        if (map == null) {
            return new IDEnumeration(null);
        }
        return new IDEnumeration(Collections.enumeration(map.keySet()));
    }

    public Enumeration<String> getAvailableVariants(String string, String string2) {
        CaseInsensitiveString caseInsensitiveString = new CaseInsensitiveString(string);
        CaseInsensitiveString caseInsensitiveString2 = new CaseInsensitiveString(string2);
        Map<CaseInsensitiveString, List<CaseInsensitiveString>> map = this.specDAG.get(caseInsensitiveString);
        if (map == null) {
            return new IDEnumeration(null);
        }
        List<CaseInsensitiveString> list = map.get(caseInsensitiveString2);
        if (list == null) {
            return new IDEnumeration(null);
        }
        return new IDEnumeration(Collections.enumeration(list));
    }

    private void registerEntry(String string, String string2, String string3, Object object, boolean bl) {
        String string4 = string;
        if (string4.length() == 0) {
            string4 = ANY;
        }
        String string5 = TransliteratorIDParser.STVtoID(string, string2, string3);
        this.registerEntry(string5, string4, string2, string3, object, bl);
    }

    private void registerEntry(String string, Object object, boolean bl) {
        String[] stringArray = TransliteratorIDParser.IDtoSTV(string);
        String string2 = TransliteratorIDParser.STVtoID(stringArray[0], stringArray[5], stringArray[5]);
        this.registerEntry(string2, stringArray[0], stringArray[5], stringArray[5], object, bl);
    }

    private void registerEntry(String string, String string2, String string3, String string4, Object object, boolean bl) {
        CaseInsensitiveString caseInsensitiveString = new CaseInsensitiveString(string);
        Object[] objectArray = object instanceof Object[] ? (Object[])object : new Object[]{object};
        this.registry.put(caseInsensitiveString, objectArray);
        if (bl) {
            this.registerSTV(string2, string3, string4);
            if (!this.availableIDs.contains(caseInsensitiveString)) {
                this.availableIDs.add(caseInsensitiveString);
            }
        } else {
            this.removeSTV(string2, string3, string4);
            this.availableIDs.remove(caseInsensitiveString);
        }
    }

    private void registerSTV(String string, String string2, String string3) {
        List<CaseInsensitiveString> list;
        CaseInsensitiveString caseInsensitiveString = new CaseInsensitiveString(string);
        CaseInsensitiveString caseInsensitiveString2 = new CaseInsensitiveString(string2);
        CaseInsensitiveString caseInsensitiveString3 = new CaseInsensitiveString(string3);
        Map<CaseInsensitiveString, List<CaseInsensitiveString>> map = this.specDAG.get(caseInsensitiveString);
        if (map == null) {
            map = Collections.synchronizedMap(new HashMap());
            this.specDAG.put(caseInsensitiveString, map);
        }
        if ((list = map.get(caseInsensitiveString2)) == null) {
            list = new ArrayList<CaseInsensitiveString>();
            map.put(caseInsensitiveString2, list);
        }
        if (!list.contains(caseInsensitiveString3)) {
            if (string3.length() > 0) {
                list.add(caseInsensitiveString3);
            } else {
                list.add(0, caseInsensitiveString3);
            }
        }
    }

    private void removeSTV(String string, String string2, String string3) {
        CaseInsensitiveString caseInsensitiveString = new CaseInsensitiveString(string);
        CaseInsensitiveString caseInsensitiveString2 = new CaseInsensitiveString(string2);
        CaseInsensitiveString caseInsensitiveString3 = new CaseInsensitiveString(string3);
        Map<CaseInsensitiveString, List<CaseInsensitiveString>> map = this.specDAG.get(caseInsensitiveString);
        if (map == null) {
            return;
        }
        List<CaseInsensitiveString> list = map.get(caseInsensitiveString2);
        if (list == null) {
            return;
        }
        list.remove(caseInsensitiveString3);
        if (list.size() == 0) {
            map.remove(caseInsensitiveString2);
            if (map.size() == 0) {
                this.specDAG.remove(caseInsensitiveString);
            }
        }
    }

    private Object[] findInDynamicStore(Spec spec, Spec spec2, String string) {
        String string2 = TransliteratorIDParser.STVtoID(spec.get(), spec2.get(), string);
        return this.registry.get(new CaseInsensitiveString(string2));
    }

    private Object[] findInStaticStore(Spec spec, Spec spec2, String string) {
        Object[] objectArray = null;
        if (spec.isLocale()) {
            objectArray = this.findInBundle(spec, spec2, string, 0);
        } else if (spec2.isLocale()) {
            objectArray = this.findInBundle(spec2, spec, string, 1);
        }
        if (objectArray != null) {
            this.registerEntry(spec.getTop(), spec2.getTop(), string, objectArray, false);
        }
        return objectArray;
    }

    private Object[] findInBundle(Spec spec, Spec spec2, String string, int n) {
        ResourceBundle resourceBundle = spec.getBundle();
        if (resourceBundle == null) {
            return null;
        }
        for (int i = 0; i < 2; ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            if (i == 0) {
                stringBuilder.append(n == 0 ? "TransliterateTo" : "TransliterateFrom");
            } else {
                stringBuilder.append("Transliterate");
            }
            stringBuilder.append(spec2.get().toUpperCase(Locale.ENGLISH));
            try {
                String[] stringArray = resourceBundle.getStringArray(stringBuilder.toString());
                int n2 = 0;
                if (string.length() != 0) {
                    for (n2 = 0; n2 < stringArray.length && !stringArray[n2].equalsIgnoreCase(string); n2 += 2) {
                    }
                }
                if (n2 >= stringArray.length) continue;
                int n3 = i == 0 ? 0 : n;
                return new Object[]{new LocaleEntry(stringArray[n2 + 1], n3)};
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }
        return null;
    }

    private Object[] find(String string) {
        String[] stringArray = TransliteratorIDParser.IDtoSTV(string);
        return this.find(stringArray[0], stringArray[5], stringArray[5]);
    }

    private Object[] find(String string, String string2, String string3) {
        Spec spec = new Spec(string);
        Spec spec2 = new Spec(string2);
        Object[] objectArray = null;
        if (string3.length() != 0) {
            objectArray = this.findInDynamicStore(spec, spec2, string3);
            if (objectArray != null) {
                return objectArray;
            }
            objectArray = this.findInStaticStore(spec, spec2, string3);
            if (objectArray != null) {
                return objectArray;
            }
        }
        while (true) {
            spec.reset();
            while (true) {
                if ((objectArray = this.findInDynamicStore(spec, spec2, NO_VARIANT)) != null) {
                    return objectArray;
                }
                objectArray = this.findInStaticStore(spec, spec2, NO_VARIANT);
                if (objectArray != null) {
                    return objectArray;
                }
                if (!spec.hasFallback()) break;
                spec.next();
            }
            if (!spec2.hasFallback()) break;
            spec2.next();
        }
        return null;
    }

    private Transliterator instantiateEntry(String string, Object[] objectArray, StringBuffer stringBuffer) {
        while (true) {
            Object object;
            Object object2;
            if ((object2 = objectArray[0]) instanceof RuleBasedTransliterator.Data) {
                object = (RuleBasedTransliterator.Data)object2;
                return new RuleBasedTransliterator(string, (RuleBasedTransliterator.Data)object, null);
            }
            if (object2 instanceof Class) {
                try {
                    return (Transliterator)((Class)object2).newInstance();
                } catch (InstantiationException instantiationException) {
                } catch (IllegalAccessException illegalAccessException) {
                    // empty catch block
                }
                return null;
            }
            if (object2 instanceof AliasEntry) {
                stringBuffer.append(((AliasEntry)object2).alias);
                return null;
            }
            if (object2 instanceof Transliterator.Factory) {
                return ((Transliterator.Factory)object2).getInstance(string);
            }
            if (object2 instanceof CompoundRBTEntry) {
                return ((CompoundRBTEntry)object2).getInstance();
            }
            if (object2 instanceof AnyTransliterator) {
                object = (AnyTransliterator)object2;
                return ((AnyTransliterator)object).safeClone();
            }
            if (object2 instanceof RuleBasedTransliterator) {
                object = (RuleBasedTransliterator)object2;
                return ((RuleBasedTransliterator)object).safeClone();
            }
            if (object2 instanceof CompoundTransliterator) {
                object = (CompoundTransliterator)object2;
                return ((CompoundTransliterator)object).safeClone();
            }
            if (object2 instanceof Transliterator) {
                return (Transliterator)object2;
            }
            object = new TransliteratorParser();
            try {
                ResourceEntry resourceEntry = (ResourceEntry)object2;
                ((TransliteratorParser)object).parse(resourceEntry.resource, resourceEntry.direction);
            } catch (ClassCastException classCastException) {
                LocaleEntry localeEntry = (LocaleEntry)object2;
                ((TransliteratorParser)object).parse(localeEntry.rule, localeEntry.direction);
            }
            if (((TransliteratorParser)object).idBlockVector.size() == 0 && ((TransliteratorParser)object).dataVector.size() == 0) {
                objectArray[0] = new AliasEntry("Any-Null");
                continue;
            }
            if (((TransliteratorParser)object).idBlockVector.size() == 0 && ((TransliteratorParser)object).dataVector.size() == 1) {
                objectArray[0] = ((TransliteratorParser)object).dataVector.get(0);
                continue;
            }
            if (((TransliteratorParser)object).idBlockVector.size() == 1 && ((TransliteratorParser)object).dataVector.size() == 0) {
                if (((TransliteratorParser)object).compoundFilter != null) {
                    objectArray[0] = new AliasEntry(((TransliteratorParser)object).compoundFilter.toPattern(true) + ";" + ((TransliteratorParser)object).idBlockVector.get(0));
                    continue;
                }
                objectArray[0] = new AliasEntry(((TransliteratorParser)object).idBlockVector.get(0));
                continue;
            }
            objectArray[0] = new CompoundRBTEntry(string, ((TransliteratorParser)object).idBlockVector, ((TransliteratorParser)object).dataVector, ((TransliteratorParser)object).compoundFilter);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class IDEnumeration
    implements Enumeration<String> {
        Enumeration<CaseInsensitiveString> en;

        public IDEnumeration(Enumeration<CaseInsensitiveString> enumeration) {
            this.en = enumeration;
        }

        @Override
        public boolean hasMoreElements() {
            return this.en != null && this.en.hasMoreElements();
        }

        @Override
        public String nextElement() {
            return this.en.nextElement().getString();
        }

        @Override
        public Object nextElement() {
            return this.nextElement();
        }
    }

    static class CompoundRBTEntry {
        private String ID;
        private List<String> idBlockVector;
        private List<RuleBasedTransliterator.Data> dataVector;
        private UnicodeSet compoundFilter;

        public CompoundRBTEntry(String string, List<String> list, List<RuleBasedTransliterator.Data> list2, UnicodeSet unicodeSet) {
            this.ID = string;
            this.idBlockVector = list;
            this.dataVector = list2;
            this.compoundFilter = unicodeSet;
        }

        public Transliterator getInstance() {
            ArrayList<Transliterator> arrayList = new ArrayList<Transliterator>();
            int n = 1;
            int n2 = Math.max(this.idBlockVector.size(), this.dataVector.size());
            for (int i = 0; i < n2; ++i) {
                Object object;
                if (i < this.idBlockVector.size() && ((String)(object = this.idBlockVector.get(i))).length() > 0) {
                    arrayList.add(Transliterator.getInstance((String)object));
                }
                if (i >= this.dataVector.size()) continue;
                object = this.dataVector.get(i);
                arrayList.add(new RuleBasedTransliterator("%Pass" + n++, (RuleBasedTransliterator.Data)object, null));
            }
            CompoundTransliterator compoundTransliterator = new CompoundTransliterator(arrayList, n - 1);
            compoundTransliterator.setID(this.ID);
            if (this.compoundFilter != null) {
                compoundTransliterator.setFilter(this.compoundFilter);
            }
            return compoundTransliterator;
        }
    }

    static class AliasEntry {
        public String alias;

        public AliasEntry(String string) {
            this.alias = string;
        }
    }

    static class LocaleEntry {
        public String rule;
        public int direction;

        public LocaleEntry(String string, int n) {
            this.rule = string;
            this.direction = n;
        }
    }

    static class ResourceEntry {
        public String resource;
        public int direction;

        public ResourceEntry(String string, int n) {
            this.resource = string;
            this.direction = n;
        }
    }

    static class Spec {
        private String top;
        private String spec;
        private String nextSpec;
        private String scriptName;
        private boolean isSpecLocale;
        private boolean isNextLocale;
        private ICUResourceBundle res;

        public Spec(String string) {
            this.top = string;
            this.spec = null;
            this.scriptName = null;
            try {
                int n = UScript.getCodeFromName(this.top);
                int[] nArray = UScript.getCode(this.top);
                if (nArray != null) {
                    this.scriptName = UScript.getName(nArray[0]);
                    if (this.scriptName.equalsIgnoreCase(this.top)) {
                        this.scriptName = null;
                    }
                }
                this.isSpecLocale = false;
                this.res = null;
                if (n == -1) {
                    Locale locale = LocaleUtility.getLocaleFromName(this.top);
                    this.res = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/translit", locale);
                    if (this.res != null && LocaleUtility.isFallbackOf(this.res.getULocale().toString(), this.top)) {
                        this.isSpecLocale = true;
                    }
                }
            } catch (MissingResourceException missingResourceException) {
                this.scriptName = null;
            }
            this.reset();
        }

        public boolean hasFallback() {
            return this.nextSpec != null;
        }

        public void reset() {
            if (!Utility.sameObjects(this.spec, this.top)) {
                this.spec = this.top;
                this.isSpecLocale = this.res != null;
                this.setupNext();
            }
        }

        private void setupNext() {
            this.isNextLocale = false;
            if (this.isSpecLocale) {
                this.nextSpec = this.spec;
                int n = this.nextSpec.lastIndexOf(95);
                if (n > 0) {
                    this.nextSpec = this.spec.substring(0, n);
                    this.isNextLocale = true;
                } else {
                    this.nextSpec = this.scriptName;
                }
            } else {
                this.nextSpec = !Utility.sameObjects(this.nextSpec, this.scriptName) ? this.scriptName : null;
            }
        }

        public String next() {
            this.spec = this.nextSpec;
            this.isSpecLocale = this.isNextLocale;
            this.setupNext();
            return this.spec;
        }

        public String get() {
            return this.spec;
        }

        public boolean isLocale() {
            return this.isSpecLocale;
        }

        public ResourceBundle getBundle() {
            if (this.res != null && this.res.getULocale().toString().equals(this.spec)) {
                return this.res;
            }
            return null;
        }

        public String getTop() {
            return this.top;
        }
    }
}

