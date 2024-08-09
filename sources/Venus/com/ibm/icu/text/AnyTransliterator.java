/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.lang.UScript;
import com.ibm.icu.text.CompoundTransliterator;
import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.text.TransliteratorIDParser;
import com.ibm.icu.text.UnicodeFilter;
import com.ibm.icu.text.UnicodeSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.MissingResourceException;
import java.util.concurrent.ConcurrentHashMap;

class AnyTransliterator
extends Transliterator {
    static final char TARGET_SEP = '-';
    static final char VARIANT_SEP = '/';
    static final String ANY = "Any";
    static final String NULL_ID = "Null";
    static final String LATIN_PIVOT = "-Latin;Latin-";
    private ConcurrentHashMap<Integer, Transliterator> cache;
    private String target;
    private int targetScript;
    private Transliterator widthFix = Transliterator.getInstance("[[:dt=Nar:][:dt=Wide:]] nfkd");

    @Override
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        int n = position.start;
        int n2 = position.limit;
        ScriptRunIterator scriptRunIterator = new ScriptRunIterator(replaceable, position.contextStart, position.contextLimit);
        while (scriptRunIterator.next()) {
            if (scriptRunIterator.limit <= n) continue;
            Transliterator transliterator = this.getTransliterator(scriptRunIterator.scriptCode);
            if (transliterator == null) {
                position.start = scriptRunIterator.limit;
                continue;
            }
            boolean bl2 = bl && scriptRunIterator.limit >= n2;
            position.start = Math.max(n, scriptRunIterator.start);
            int n3 = position.limit = Math.min(n2, scriptRunIterator.limit);
            transliterator.filteredTransliterate(replaceable, position, bl2);
            int n4 = position.limit - n3;
            scriptRunIterator.adjustLimit(n4);
            if (scriptRunIterator.limit < (n2 += n4)) continue;
            break;
        }
        position.limit = n2;
    }

    private AnyTransliterator(String string, String string2, String string3, int n) {
        super(string, null);
        this.targetScript = n;
        this.cache = new ConcurrentHashMap();
        this.target = string2;
        if (string3.length() > 0) {
            this.target = string2 + '/' + string3;
        }
    }

    public AnyTransliterator(String string, UnicodeFilter unicodeFilter, String string2, int n, Transliterator transliterator, ConcurrentHashMap<Integer, Transliterator> concurrentHashMap) {
        super(string, unicodeFilter);
        this.targetScript = n;
        this.cache = concurrentHashMap;
        this.target = string2;
    }

    private Transliterator getTransliterator(int n) {
        if (n == this.targetScript || n == -1) {
            if (this.isWide(this.targetScript)) {
                return null;
            }
            return this.widthFix;
        }
        Integer n2 = n;
        Object object = this.cache.get(n2);
        if (object == null) {
            String string = UScript.getName(n);
            String string2 = string + '-' + this.target;
            try {
                object = Transliterator.getInstance(string2, 0);
            } catch (RuntimeException runtimeException) {
                // empty catch block
            }
            if (object == null) {
                string2 = string + LATIN_PIVOT + this.target;
                try {
                    object = Transliterator.getInstance(string2, 0);
                } catch (RuntimeException runtimeException) {
                    // empty catch block
                }
            }
            if (object != null) {
                Object object2;
                if (!this.isWide(this.targetScript)) {
                    object2 = new ArrayList<Transliterator>();
                    object2.add(this.widthFix);
                    object2.add(object);
                    object = new CompoundTransliterator((List<Transliterator>)object2);
                }
                if ((object2 = this.cache.putIfAbsent(n2, (Transliterator)object)) != null) {
                    object = object2;
                }
            } else if (!this.isWide(this.targetScript)) {
                return this.widthFix;
            }
        }
        return object;
    }

    private boolean isWide(int n) {
        return n == 5 || n == 17 || n == 18 || n == 20 || n == 22;
    }

    static void register() {
        HashMap hashMap = new HashMap();
        Enumeration<String> enumeration = Transliterator.getAvailableSources();
        while (enumeration.hasMoreElements()) {
            String string = enumeration.nextElement();
            if (string.equalsIgnoreCase(ANY)) continue;
            Enumeration<String> enumeration2 = Transliterator.getAvailableTargets(string);
            while (enumeration2.hasMoreElements()) {
                String string2 = enumeration2.nextElement();
                int n = AnyTransliterator.scriptNameToCode(string2);
                if (n == -1) continue;
                HashSet<String> hashSet = (HashSet<String>)hashMap.get(string2);
                if (hashSet == null) {
                    hashSet = new HashSet<String>();
                    hashMap.put(string2, hashSet);
                }
                Enumeration<String> enumeration3 = Transliterator.getAvailableVariants(string, string2);
                while (enumeration3.hasMoreElements()) {
                    String string3 = enumeration3.nextElement();
                    if (hashSet.contains(string3)) continue;
                    hashSet.add(string3);
                    String string4 = TransliteratorIDParser.STVtoID(ANY, string2, string3);
                    AnyTransliterator anyTransliterator = new AnyTransliterator(string4, string2, string3, n);
                    Transliterator.registerInstance(anyTransliterator);
                    Transliterator.registerSpecialInverse(string2, NULL_ID, false);
                }
            }
        }
    }

    private static int scriptNameToCode(String string) {
        try {
            int[] nArray = UScript.getCode(string);
            return nArray != null ? nArray[0] : -1;
        } catch (MissingResourceException missingResourceException) {
            return 1;
        }
    }

    public Transliterator safeClone() {
        UnicodeFilter unicodeFilter = this.getFilter();
        if (unicodeFilter != null && unicodeFilter instanceof UnicodeSet) {
            unicodeFilter = new UnicodeSet((UnicodeSet)unicodeFilter);
        }
        return new AnyTransliterator(this.getID(), unicodeFilter, this.target, this.targetScript, this.widthFix, this.cache);
    }

    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        UnicodeSet unicodeSet4 = this.getFilterAsUnicodeSet(unicodeSet);
        unicodeSet2.addAll(unicodeSet4);
        if (unicodeSet4.size() != 0) {
            unicodeSet3.addAll(0, 0x10FFFF);
        }
    }

    private static class ScriptRunIterator {
        private Replaceable text;
        private int textStart;
        private int textLimit;
        public int scriptCode;
        public int start;
        public int limit;

        public ScriptRunIterator(Replaceable replaceable, int n, int n2) {
            this.text = replaceable;
            this.textStart = n;
            this.textLimit = n2;
            this.limit = n;
        }

        public boolean next() {
            int n;
            int n2;
            this.scriptCode = -1;
            this.start = this.limit;
            if (this.start == this.textLimit) {
                return true;
            }
            while (this.start > this.textStart && ((n2 = UScript.getScript(n = this.text.char32At(this.start - 1))) == 0 || n2 == 1)) {
                --this.start;
            }
            while (this.limit < this.textLimit) {
                n = this.text.char32At(this.limit);
                n2 = UScript.getScript(n);
                if (n2 != 0 && n2 != 1) {
                    if (this.scriptCode == -1) {
                        this.scriptCode = n2;
                    } else if (n2 != this.scriptCode) break;
                }
                ++this.limit;
            }
            return false;
        }

        public void adjustLimit(int n) {
            this.limit += n;
            this.textLimit += n;
        }
    }
}

