package com.ibm.icu.text;

import java.util.*;
import com.ibm.icu.lang.*;
import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;

class TransliteratorRegistry
{
    private static final char LOCALE_SEP = '_';
    private static final String NO_VARIANT = "";
    private static final String ANY = "Any";
    private Map<CaseInsensitiveString, Object[]> registry;
    private Map<CaseInsensitiveString, Map<CaseInsensitiveString, List<CaseInsensitiveString>>> specDAG;
    private List<CaseInsensitiveString> availableIDs;
    private static final boolean DEBUG = false;
    
    public TransliteratorRegistry() {
        this.registry = Collections.synchronizedMap(new HashMap<CaseInsensitiveString, Object[]>());
        this.specDAG = Collections.synchronizedMap(new HashMap<CaseInsensitiveString, Map<CaseInsensitiveString, List<CaseInsensitiveString>>>());
        this.availableIDs = new ArrayList<CaseInsensitiveString>();
    }
    
    public Transliterator get(final String ID, final StringBuffer aliasReturn) {
        final Object[] entry = this.find(ID);
        return (entry == null) ? null : this.instantiateEntry(ID, entry, aliasReturn);
    }
    
    public void put(final String ID, final Class<? extends Transliterator> transliteratorSubclass, final boolean visible) {
        this.registerEntry(ID, transliteratorSubclass, visible);
    }
    
    public void put(final String ID, final Transliterator.Factory factory, final boolean visible) {
        this.registerEntry(ID, factory, visible);
    }
    
    public void put(final String ID, final String resourceName, final int dir, final boolean visible) {
        this.registerEntry(ID, new ResourceEntry(resourceName, dir), visible);
    }
    
    public void put(final String ID, final String alias, final boolean visible) {
        this.registerEntry(ID, new AliasEntry(alias), visible);
    }
    
    public void put(final String ID, final Transliterator trans, final boolean visible) {
        this.registerEntry(ID, trans, visible);
    }
    
    public void remove(final String ID) {
        final String[] stv = TransliteratorIDParser.IDtoSTV(ID);
        final String id = TransliteratorIDParser.STVtoID(stv[0], stv[1], stv[2]);
        this.registry.remove(new CaseInsensitiveString(id));
        this.removeSTV(stv[0], stv[1], stv[2]);
        this.availableIDs.remove(new CaseInsensitiveString(id));
    }
    
    public Enumeration<String> getAvailableIDs() {
        return new IDEnumeration(Collections.enumeration(this.availableIDs));
    }
    
    public Enumeration<String> getAvailableSources() {
        return new IDEnumeration(Collections.enumeration(this.specDAG.keySet()));
    }
    
    public Enumeration<String> getAvailableTargets(final String source) {
        final CaseInsensitiveString cisrc = new CaseInsensitiveString(source);
        final Map<CaseInsensitiveString, List<CaseInsensitiveString>> targets = this.specDAG.get(cisrc);
        if (targets == null) {
            return new IDEnumeration(null);
        }
        return new IDEnumeration(Collections.enumeration(targets.keySet()));
    }
    
    public Enumeration<String> getAvailableVariants(final String source, final String target) {
        final CaseInsensitiveString cisrc = new CaseInsensitiveString(source);
        final CaseInsensitiveString citrg = new CaseInsensitiveString(target);
        final Map<CaseInsensitiveString, List<CaseInsensitiveString>> targets = this.specDAG.get(cisrc);
        if (targets == null) {
            return new IDEnumeration(null);
        }
        final List<CaseInsensitiveString> variants = targets.get(citrg);
        if (variants == null) {
            return new IDEnumeration(null);
        }
        return new IDEnumeration(Collections.enumeration(variants));
    }
    
    private void registerEntry(final String source, final String target, final String variant, final Object entry, final boolean visible) {
        String s = source;
        if (s.length() == 0) {
            s = "Any";
        }
        final String ID = TransliteratorIDParser.STVtoID(source, target, variant);
        this.registerEntry(ID, s, target, variant, entry, visible);
    }
    
    private void registerEntry(final String ID, final Object entry, final boolean visible) {
        final String[] stv = TransliteratorIDParser.IDtoSTV(ID);
        final String id = TransliteratorIDParser.STVtoID(stv[0], stv[1], stv[2]);
        this.registerEntry(id, stv[0], stv[1], stv[2], entry, visible);
    }
    
    private void registerEntry(final String ID, final String source, final String target, final String variant, final Object entry, final boolean visible) {
        final CaseInsensitiveString ciID = new CaseInsensitiveString(ID);
        Object[] arrayOfObj;
        if (entry instanceof Object[]) {
            arrayOfObj = (Object[])entry;
        }
        else {
            arrayOfObj = new Object[] { entry };
        }
        this.registry.put(ciID, arrayOfObj);
        if (visible) {
            this.registerSTV(source, target, variant);
            if (!this.availableIDs.contains(ciID)) {
                this.availableIDs.add(ciID);
            }
        }
        else {
            this.removeSTV(source, target, variant);
            this.availableIDs.remove(ciID);
        }
    }
    
    private void registerSTV(final String source, final String target, final String variant) {
        final CaseInsensitiveString cisrc = new CaseInsensitiveString(source);
        final CaseInsensitiveString citrg = new CaseInsensitiveString(target);
        final CaseInsensitiveString civar = new CaseInsensitiveString(variant);
        Map<CaseInsensitiveString, List<CaseInsensitiveString>> targets = this.specDAG.get(cisrc);
        if (targets == null) {
            targets = Collections.synchronizedMap(new HashMap<CaseInsensitiveString, List<CaseInsensitiveString>>());
            this.specDAG.put(cisrc, targets);
        }
        List<CaseInsensitiveString> variants = targets.get(citrg);
        if (variants == null) {
            variants = new ArrayList<CaseInsensitiveString>();
            targets.put(citrg, variants);
        }
        if (!variants.contains(civar)) {
            if (variant.length() > 0) {
                variants.add(civar);
            }
            else {
                variants.add(0, civar);
            }
        }
    }
    
    private void removeSTV(final String source, final String target, final String variant) {
        final CaseInsensitiveString cisrc = new CaseInsensitiveString(source);
        final CaseInsensitiveString citrg = new CaseInsensitiveString(target);
        final CaseInsensitiveString civar = new CaseInsensitiveString(variant);
        final Map<CaseInsensitiveString, List<CaseInsensitiveString>> targets = this.specDAG.get(cisrc);
        if (targets == null) {
            return;
        }
        final List<CaseInsensitiveString> variants = targets.get(citrg);
        if (variants == null) {
            return;
        }
        variants.remove(civar);
        if (variants.size() == 0) {
            targets.remove(citrg);
            if (targets.size() == 0) {
                this.specDAG.remove(cisrc);
            }
        }
    }
    
    private Object[] findInDynamicStore(final Spec src, final Spec trg, final String variant) {
        final String ID = TransliteratorIDParser.STVtoID(src.get(), trg.get(), variant);
        return this.registry.get(new CaseInsensitiveString(ID));
    }
    
    private Object[] findInStaticStore(final Spec src, final Spec trg, final String variant) {
        Object[] entry = null;
        if (src.isLocale()) {
            entry = this.findInBundle(src, trg, variant, 0);
        }
        else if (trg.isLocale()) {
            entry = this.findInBundle(trg, src, variant, 1);
        }
        if (entry != null) {
            this.registerEntry(src.getTop(), trg.getTop(), variant, entry, false);
        }
        return entry;
    }
    
    private Object[] findInBundle(final Spec specToOpen, final Spec specToFind, final String variant, final int direction) {
        final ResourceBundle res = specToOpen.getBundle();
        if (res == null) {
            return null;
        }
        for (int pass = 0; pass < 2; ++pass) {
            final StringBuilder tag = new StringBuilder();
            if (pass == 0) {
                tag.append((direction == 0) ? "TransliterateTo" : "TransliterateFrom");
            }
            else {
                tag.append("Transliterate");
            }
            tag.append(specToFind.get().toUpperCase(Locale.ENGLISH));
            try {
                final String[] subres = res.getStringArray(tag.toString());
                int i = 0;
                if (variant.length() != 0) {
                    for (i = 0; i < subres.length; i += 2) {
                        if (subres[i].equalsIgnoreCase(variant)) {
                            break;
                        }
                    }
                }
                if (i < subres.length) {
                    final int dir = (pass == 0) ? 0 : direction;
                    return new Object[] { new LocaleEntry(subres[i + 1], dir) };
                }
            }
            catch (MissingResourceException ex) {}
        }
        return null;
    }
    
    private Object[] find(final String ID) {
        final String[] stv = TransliteratorIDParser.IDtoSTV(ID);
        return this.find(stv[0], stv[1], stv[2]);
    }
    
    private Object[] find(final String source, final String target, final String variant) {
        final Spec src = new Spec(source);
        final Spec trg = new Spec(target);
        Object[] entry = null;
        if (variant.length() != 0) {
            entry = this.findInDynamicStore(src, trg, variant);
            if (entry != null) {
                return entry;
            }
            entry = this.findInStaticStore(src, trg, variant);
            if (entry != null) {
                return entry;
            }
        }
        while (true) {
            src.reset();
            while (true) {
                entry = this.findInDynamicStore(src, trg, "");
                if (entry != null) {
                    return entry;
                }
                entry = this.findInStaticStore(src, trg, "");
                if (entry != null) {
                    return entry;
                }
                if (!src.hasFallback()) {
                    if (!trg.hasFallback()) {
                        return null;
                    }
                    trg.next();
                    break;
                }
                else {
                    src.next();
                }
            }
        }
    }
    
    private Transliterator instantiateEntry(final String ID, final Object[] entryWrapper, final StringBuffer aliasReturn) {
        while (true) {
            final Object entry = entryWrapper[0];
            if (entry instanceof RuleBasedTransliterator.Data) {
                final RuleBasedTransliterator.Data data = (RuleBasedTransliterator.Data)entry;
                return new RuleBasedTransliterator(ID, data, null);
            }
            if (entry instanceof Class) {
                try {
                    return ((Class)entry).newInstance();
                }
                catch (InstantiationException ex) {}
                catch (IllegalAccessException ex2) {}
                return null;
            }
            if (entry instanceof AliasEntry) {
                aliasReturn.append(((AliasEntry)entry).alias);
                return null;
            }
            if (entry instanceof Transliterator.Factory) {
                return ((Transliterator.Factory)entry).getInstance(ID);
            }
            if (entry instanceof CompoundRBTEntry) {
                return ((CompoundRBTEntry)entry).getInstance();
            }
            if (entry instanceof AnyTransliterator) {
                final AnyTransliterator temp = (AnyTransliterator)entry;
                return temp.safeClone();
            }
            if (entry instanceof RuleBasedTransliterator) {
                final RuleBasedTransliterator temp2 = (RuleBasedTransliterator)entry;
                return temp2.safeClone();
            }
            if (entry instanceof CompoundTransliterator) {
                final CompoundTransliterator temp3 = (CompoundTransliterator)entry;
                return temp3.safeClone();
            }
            if (entry instanceof Transliterator) {
                return (Transliterator)entry;
            }
            final TransliteratorParser parser = new TransliteratorParser();
            try {
                final ResourceEntry re = (ResourceEntry)entry;
                parser.parse(re.resource, re.direction);
            }
            catch (ClassCastException e) {
                final LocaleEntry le = (LocaleEntry)entry;
                parser.parse(le.rule, le.direction);
            }
            if (parser.idBlockVector.size() == 0 && parser.dataVector.size() == 0) {
                entryWrapper[0] = new AliasEntry("Any-Null");
            }
            else if (parser.idBlockVector.size() == 0 && parser.dataVector.size() == 1) {
                entryWrapper[0] = parser.dataVector.get(0);
            }
            else if (parser.idBlockVector.size() == 1 && parser.dataVector.size() == 0) {
                if (parser.compoundFilter != null) {
                    entryWrapper[0] = new AliasEntry(parser.compoundFilter.toPattern(false) + ";" + parser.idBlockVector.get(0));
                }
                else {
                    entryWrapper[0] = new AliasEntry(parser.idBlockVector.get(0));
                }
            }
            else {
                entryWrapper[0] = new CompoundRBTEntry(ID, parser.idBlockVector, parser.dataVector, parser.compoundFilter);
            }
        }
    }
    
    static class Spec
    {
        private String top;
        private String spec;
        private String nextSpec;
        private String scriptName;
        private boolean isSpecLocale;
        private boolean isNextLocale;
        private ICUResourceBundle res;
        
        public Spec(final String theSpec) {
            this.top = theSpec;
            this.spec = null;
            this.scriptName = null;
            try {
                final int script = UScript.getCodeFromName(this.top);
                final int[] s = UScript.getCode(this.top);
                if (s != null) {
                    this.scriptName = UScript.getName(s[0]);
                    if (this.scriptName.equalsIgnoreCase(this.top)) {
                        this.scriptName = null;
                    }
                }
                this.isSpecLocale = false;
                this.res = null;
                if (script == -1) {
                    final Locale toploc = LocaleUtility.getLocaleFromName(this.top);
                    this.res = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/translit", toploc);
                    if (this.res != null && LocaleUtility.isFallbackOf(this.res.getULocale().toString(), this.top)) {
                        this.isSpecLocale = true;
                    }
                }
            }
            catch (MissingResourceException e) {
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
                this.isSpecLocale = (this.res != null);
                this.setupNext();
            }
        }
        
        private void setupNext() {
            this.isNextLocale = false;
            if (this.isSpecLocale) {
                this.nextSpec = this.spec;
                final int i = this.nextSpec.lastIndexOf(95);
                if (i > 0) {
                    this.nextSpec = this.spec.substring(0, i);
                    this.isNextLocale = true;
                }
                else {
                    this.nextSpec = this.scriptName;
                }
            }
            else if (!Utility.sameObjects(this.nextSpec, this.scriptName)) {
                this.nextSpec = this.scriptName;
            }
            else {
                this.nextSpec = null;
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
    
    static class ResourceEntry
    {
        public String resource;
        public int direction;
        
        public ResourceEntry(final String n, final int d) {
            this.resource = n;
            this.direction = d;
        }
    }
    
    static class LocaleEntry
    {
        public String rule;
        public int direction;
        
        public LocaleEntry(final String r, final int d) {
            this.rule = r;
            this.direction = d;
        }
    }
    
    static class AliasEntry
    {
        public String alias;
        
        public AliasEntry(final String a) {
            this.alias = a;
        }
    }
    
    static class CompoundRBTEntry
    {
        private String ID;
        private List<String> idBlockVector;
        private List<RuleBasedTransliterator.Data> dataVector;
        private UnicodeSet compoundFilter;
        
        public CompoundRBTEntry(final String theID, final List<String> theIDBlockVector, final List<RuleBasedTransliterator.Data> theDataVector, final UnicodeSet theCompoundFilter) {
            this.ID = theID;
            this.idBlockVector = theIDBlockVector;
            this.dataVector = theDataVector;
            this.compoundFilter = theCompoundFilter;
        }
        
        public Transliterator getInstance() {
            final List<Transliterator> transliterators = new ArrayList<Transliterator>();
            int passNumber = 1;
            for (int limit = Math.max(this.idBlockVector.size(), this.dataVector.size()), i = 0; i < limit; ++i) {
                if (i < this.idBlockVector.size()) {
                    final String idBlock = this.idBlockVector.get(i);
                    if (idBlock.length() > 0) {
                        transliterators.add(Transliterator.getInstance(idBlock));
                    }
                }
                if (i < this.dataVector.size()) {
                    final RuleBasedTransliterator.Data data = this.dataVector.get(i);
                    transliterators.add(new RuleBasedTransliterator("%Pass" + passNumber++, data, null));
                }
            }
            final Transliterator t = new CompoundTransliterator(transliterators, passNumber - 1);
            t.setID(this.ID);
            if (this.compoundFilter != null) {
                t.setFilter(this.compoundFilter);
            }
            return t;
        }
    }
    
    private static class IDEnumeration implements Enumeration<String>
    {
        Enumeration<CaseInsensitiveString> en;
        
        public IDEnumeration(final Enumeration<CaseInsensitiveString> e) {
            this.en = e;
        }
        
        @Override
        public boolean hasMoreElements() {
            return this.en != null && this.en.hasMoreElements();
        }
        
        @Override
        public String nextElement() {
            return this.en.nextElement().getString();
        }
    }
}
