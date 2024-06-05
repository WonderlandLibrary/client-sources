package com.ibm.icu.text;

import java.util.concurrent.*;
import com.ibm.icu.lang.*;
import java.util.*;

class AnyTransliterator extends Transliterator
{
    static final char TARGET_SEP = '-';
    static final char VARIANT_SEP = '/';
    static final String ANY = "Any";
    static final String NULL_ID = "Null";
    static final String LATIN_PIVOT = "-Latin;Latin-";
    private ConcurrentHashMap<Integer, Transliterator> cache;
    private String target;
    private int targetScript;
    private Transliterator widthFix;
    
    @Override
    protected void handleTransliterate(final Replaceable text, final Position pos, final boolean isIncremental) {
        final int allStart = pos.start;
        int allLimit = pos.limit;
        final ScriptRunIterator it = new ScriptRunIterator(text, pos.contextStart, pos.contextLimit);
        while (it.next()) {
            if (it.limit <= allStart) {
                continue;
            }
            final Transliterator t = this.getTransliterator(it.scriptCode);
            if (t == null) {
                pos.start = it.limit;
            }
            else {
                final boolean incremental = isIncremental && it.limit >= allLimit;
                pos.start = Math.max(allStart, it.start);
                pos.limit = Math.min(allLimit, it.limit);
                final int limit = pos.limit;
                t.filteredTransliterate(text, pos, incremental);
                final int delta = pos.limit - limit;
                allLimit += delta;
                it.adjustLimit(delta);
                if (it.limit >= allLimit) {
                    break;
                }
                continue;
            }
        }
        pos.limit = allLimit;
    }
    
    private AnyTransliterator(final String id, final String theTarget, final String theVariant, final int theTargetScript) {
        super(id, null);
        this.widthFix = Transliterator.getInstance("[[:dt=Nar:][:dt=Wide:]] nfkd");
        this.targetScript = theTargetScript;
        this.cache = new ConcurrentHashMap<Integer, Transliterator>();
        this.target = theTarget;
        if (theVariant.length() > 0) {
            this.target = theTarget + '/' + theVariant;
        }
    }
    
    public AnyTransliterator(final String id, final UnicodeFilter filter, final String target2, final int targetScript2, final Transliterator widthFix2, final ConcurrentHashMap<Integer, Transliterator> cache2) {
        super(id, filter);
        this.widthFix = Transliterator.getInstance("[[:dt=Nar:][:dt=Wide:]] nfkd");
        this.targetScript = targetScript2;
        this.cache = cache2;
        this.target = target2;
    }
    
    private Transliterator getTransliterator(final int source) {
        if (source != this.targetScript && source != -1) {
            final Integer key = source;
            Transliterator t = this.cache.get(key);
            if (t == null) {
                final String sourceName = UScript.getName(source);
                String id = sourceName + '-' + this.target;
                try {
                    t = Transliterator.getInstance(id, 0);
                }
                catch (RuntimeException ex) {}
                if (t == null) {
                    id = sourceName + "-Latin;Latin-" + this.target;
                    try {
                        t = Transliterator.getInstance(id, 0);
                    }
                    catch (RuntimeException ex2) {}
                }
                if (t != null) {
                    if (!this.isWide(this.targetScript)) {
                        final List<Transliterator> v = new ArrayList<Transliterator>();
                        v.add(this.widthFix);
                        v.add(t);
                        t = new CompoundTransliterator(v);
                    }
                    final Transliterator prevCachedT = this.cache.putIfAbsent(key, t);
                    if (prevCachedT != null) {
                        t = prevCachedT;
                    }
                }
                else if (!this.isWide(this.targetScript)) {
                    return this.widthFix;
                }
            }
            return t;
        }
        if (this.isWide(this.targetScript)) {
            return null;
        }
        return this.widthFix;
    }
    
    private boolean isWide(final int script) {
        return script == 5 || script == 17 || script == 18 || script == 20 || script == 22;
    }
    
    static void register() {
        final HashMap<String, Set<String>> seen = new HashMap<String, Set<String>>();
        final Enumeration<String> s = Transliterator.getAvailableSources();
        while (s.hasMoreElements()) {
            final String source = s.nextElement();
            if (source.equalsIgnoreCase("Any")) {
                continue;
            }
            final Enumeration<String> t = Transliterator.getAvailableTargets(source);
            while (t.hasMoreElements()) {
                final String target = t.nextElement();
                final int targetScript = scriptNameToCode(target);
                if (targetScript == -1) {
                    continue;
                }
                Set<String> seenVariants = seen.get(target);
                if (seenVariants == null) {
                    seen.put(target, seenVariants = new HashSet<String>());
                }
                final Enumeration<String> v = Transliterator.getAvailableVariants(source, target);
                while (v.hasMoreElements()) {
                    final String variant = v.nextElement();
                    if (seenVariants.contains(variant)) {
                        continue;
                    }
                    seenVariants.add(variant);
                    final String id = TransliteratorIDParser.STVtoID("Any", target, variant);
                    final AnyTransliterator trans = new AnyTransliterator(id, target, variant, targetScript);
                    Transliterator.registerInstance(trans);
                    Transliterator.registerSpecialInverse(target, "Null", false);
                }
            }
        }
    }
    
    private static int scriptNameToCode(final String name) {
        try {
            final int[] codes = UScript.getCode(name);
            return (codes != null) ? codes[0] : -1;
        }
        catch (MissingResourceException e) {
            return -1;
        }
    }
    
    public Transliterator safeClone() {
        UnicodeFilter filter = this.getFilter();
        if (filter != null && filter instanceof UnicodeSet) {
            filter = new UnicodeSet((UnicodeSet)filter);
        }
        return new AnyTransliterator(this.getID(), filter, this.target, this.targetScript, this.widthFix, this.cache);
    }
    
    @Override
    public void addSourceTargetSet(final UnicodeSet inputFilter, final UnicodeSet sourceSet, final UnicodeSet targetSet) {
        final UnicodeSet myFilter = this.getFilterAsUnicodeSet(inputFilter);
        sourceSet.addAll(myFilter);
        if (myFilter.size() != 0) {
            targetSet.addAll(0, 1114111);
        }
    }
    
    private static class ScriptRunIterator
    {
        private Replaceable text;
        private int textStart;
        private int textLimit;
        public int scriptCode;
        public int start;
        public int limit;
        
        public ScriptRunIterator(final Replaceable text, final int start, final int limit) {
            this.text = text;
            this.textStart = start;
            this.textLimit = limit;
            this.limit = start;
        }
        
        public boolean next() {
            this.scriptCode = -1;
            this.start = this.limit;
            if (this.start == this.textLimit) {
                return false;
            }
            while (this.start > this.textStart) {
                final int ch = this.text.char32At(this.start - 1);
                final int s = UScript.getScript(ch);
                if (s != 0 && s != 1) {
                    break;
                }
                --this.start;
            }
            while (this.limit < this.textLimit) {
                final int ch = this.text.char32At(this.limit);
                final int s = UScript.getScript(ch);
                if (s != 0 && s != 1) {
                    if (this.scriptCode == -1) {
                        this.scriptCode = s;
                    }
                    else if (s != this.scriptCode) {
                        break;
                    }
                }
                ++this.limit;
            }
            return true;
        }
        
        public void adjustLimit(final int delta) {
            this.limit += delta;
            this.textLimit += delta;
        }
    }
}
