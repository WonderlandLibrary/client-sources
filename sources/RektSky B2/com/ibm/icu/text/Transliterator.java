package com.ibm.icu.text;

import com.ibm.icu.util.*;
import java.text.*;
import com.ibm.icu.impl.*;
import java.util.*;

public abstract class Transliterator implements StringTransform
{
    public static final int FORWARD = 0;
    public static final int REVERSE = 1;
    private String ID;
    private UnicodeSet filter;
    private int maximumContextLength;
    private static TransliteratorRegistry registry;
    private static Map<CaseInsensitiveString, String> displayNameCache;
    private static final String RB_DISPLAY_NAME_PREFIX = "%Translit%%";
    private static final String RB_SCRIPT_DISPLAY_NAME_PREFIX = "%Translit%";
    private static final String RB_DISPLAY_NAME_PATTERN = "TransliteratorNamePattern";
    static final char ID_DELIM = ';';
    static final char ID_SEP = '-';
    static final char VARIANT_SEP = '/';
    static final boolean DEBUG = false;
    private static final String ROOT = "root";
    private static final String RB_RULE_BASED_IDS = "RuleBasedTransliteratorIDs";
    
    protected Transliterator(final String ID, final UnicodeFilter filter) {
        this.maximumContextLength = 0;
        if (ID == null) {
            throw new NullPointerException();
        }
        this.ID = ID;
        this.setFilter(filter);
    }
    
    public final int transliterate(final Replaceable text, final int start, final int limit) {
        if (start < 0 || limit < start || text.length() < limit) {
            return -1;
        }
        final Position pos = new Position(start, limit, start);
        this.filteredTransliterate(text, pos, false, true);
        return pos.limit;
    }
    
    public final void transliterate(final Replaceable text) {
        this.transliterate(text, 0, text.length());
    }
    
    public final String transliterate(final String text) {
        final ReplaceableString result = new ReplaceableString(text);
        this.transliterate(result);
        return result.toString();
    }
    
    public final void transliterate(final Replaceable text, final Position index, final String insertion) {
        index.validate(text.length());
        if (insertion != null) {
            text.replace(index.limit, index.limit, insertion);
            index.limit += insertion.length();
            index.contextLimit += insertion.length();
        }
        if (index.limit > 0 && UTF16.isLeadSurrogate(text.charAt(index.limit - 1))) {
            return;
        }
        this.filteredTransliterate(text, index, true, true);
    }
    
    public final void transliterate(final Replaceable text, final Position index, final int insertion) {
        this.transliterate(text, index, UTF16.valueOf(insertion));
    }
    
    public final void transliterate(final Replaceable text, final Position index) {
        this.transliterate(text, index, null);
    }
    
    public final void finishTransliteration(final Replaceable text, final Position index) {
        index.validate(text.length());
        this.filteredTransliterate(text, index, false, true);
    }
    
    protected abstract void handleTransliterate(final Replaceable p0, final Position p1, final boolean p2);
    
    private void filteredTransliterate(final Replaceable text, final Position index, final boolean incremental, final boolean rollback) {
        if (this.filter == null && !rollback) {
            this.handleTransliterate(text, index, incremental);
            return;
        }
        int globalLimit = index.limit;
        final StringBuffer log = null;
        boolean isIncrementalRun;
        do {
            if (this.filter != null) {
                int c;
                while (index.start < globalLimit && !this.filter.contains(c = text.char32At(index.start))) {
                    index.start += UTF16.getCharCount(c);
                }
                index.limit = index.start;
                while (index.limit < globalLimit && this.filter.contains(c = text.char32At(index.limit))) {
                    index.limit += UTF16.getCharCount(c);
                }
            }
            if (index.start == index.limit) {
                break;
            }
            isIncrementalRun = (index.limit >= globalLimit && incremental);
            if (rollback && isIncrementalRun) {
                final int runStart = index.start;
                int runLimit = index.limit;
                final int runLength = runLimit - runStart;
                int rollbackOrigin = text.length();
                text.copy(runStart, runLimit, rollbackOrigin);
                int passStart = runStart;
                int rollbackStart = rollbackOrigin;
                int passLimit = index.start;
                int uncommittedLength = 0;
                int totalDelta = 0;
                while (true) {
                    final int charLength = UTF16.getCharCount(text.char32At(passLimit));
                    passLimit += charLength;
                    if (passLimit > runLimit) {
                        break;
                    }
                    uncommittedLength += charLength;
                    index.limit = passLimit;
                    this.handleTransliterate(text, index, true);
                    final int delta = index.limit - passLimit;
                    if (index.start != index.limit) {
                        final int rs = rollbackStart + delta - (index.limit - passStart);
                        text.replace(passStart, index.limit, "");
                        text.copy(rs, rs + uncommittedLength, passStart);
                        index.start = passStart;
                        index.limit = passLimit;
                        index.contextLimit -= delta;
                    }
                    else {
                        passLimit = (passStart = index.start);
                        rollbackStart += delta + uncommittedLength;
                        uncommittedLength = 0;
                        runLimit += delta;
                        totalDelta += delta;
                    }
                }
                rollbackOrigin += totalDelta;
                globalLimit += totalDelta;
                text.replace(rollbackOrigin, rollbackOrigin + runLength, "");
                index.start = passStart;
            }
            else {
                final int limit = index.limit;
                this.handleTransliterate(text, index, isIncrementalRun);
                final int delta = index.limit - limit;
                if (!isIncrementalRun && index.start != index.limit) {
                    throw new RuntimeException("ERROR: Incomplete non-incremental transliteration by " + this.getID());
                }
                globalLimit += delta;
            }
            if (this.filter == null) {
                break;
            }
        } while (!isIncrementalRun);
        index.limit = globalLimit;
    }
    
    public void filteredTransliterate(final Replaceable text, final Position index, final boolean incremental) {
        this.filteredTransliterate(text, index, incremental, false);
    }
    
    public final int getMaximumContextLength() {
        return this.maximumContextLength;
    }
    
    protected void setMaximumContextLength(final int a) {
        if (a < 0) {
            throw new IllegalArgumentException("Invalid context length " + a);
        }
        this.maximumContextLength = a;
    }
    
    public final String getID() {
        return this.ID;
    }
    
    protected final void setID(final String id) {
        this.ID = id;
    }
    
    public static final String getDisplayName(final String ID) {
        return getDisplayName(ID, ULocale.getDefault(ULocale.Category.DISPLAY));
    }
    
    public static String getDisplayName(final String id, final Locale inLocale) {
        return getDisplayName(id, ULocale.forLocale(inLocale));
    }
    
    public static String getDisplayName(final String id, final ULocale inLocale) {
        final ICUResourceBundle bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/translit", inLocale);
        final String[] stv = TransliteratorIDParser.IDtoSTV(id);
        if (stv == null) {
            return "";
        }
        String ID = stv[0] + '-' + stv[1];
        if (stv[2] != null && stv[2].length() > 0) {
            ID = ID + '/' + stv[2];
        }
        final String n = Transliterator.displayNameCache.get(new CaseInsensitiveString(ID));
        if (n != null) {
            return n;
        }
        try {
            return bundle.getString("%Translit%%" + ID);
        }
        catch (MissingResourceException ex) {
            try {
                final MessageFormat format = new MessageFormat(bundle.getString("TransliteratorNamePattern"));
                final Object[] args = { 2, stv[0], stv[1] };
                for (int j = 1; j <= 2; ++j) {
                    try {
                        args[j] = bundle.getString("%Translit%" + (String)args[j]);
                    }
                    catch (MissingResourceException ex2) {}
                }
                return (stv[2].length() > 0) ? (format.format(args) + '/' + stv[2]) : format.format(args);
            }
            catch (MissingResourceException ex3) {
                throw new RuntimeException();
            }
        }
    }
    
    public final UnicodeFilter getFilter() {
        return this.filter;
    }
    
    public void setFilter(final UnicodeFilter filter) {
        if (filter == null) {
            this.filter = null;
        }
        else {
            try {
                this.filter = new UnicodeSet((UnicodeSet)filter).freeze();
            }
            catch (Exception e) {
                filter.addMatchSetTo(this.filter = new UnicodeSet());
                this.filter.freeze();
            }
        }
    }
    
    public static final Transliterator getInstance(final String ID) {
        return getInstance(ID, 0);
    }
    
    public static Transliterator getInstance(final String ID, final int dir) {
        final StringBuffer canonID = new StringBuffer();
        final List<TransliteratorIDParser.SingleID> list = new ArrayList<TransliteratorIDParser.SingleID>();
        final UnicodeSet[] globalFilter = { null };
        if (!TransliteratorIDParser.parseCompoundID(ID, dir, canonID, list, globalFilter)) {
            throw new IllegalArgumentException("Invalid ID " + ID);
        }
        final List<Transliterator> translits = TransliteratorIDParser.instantiateList(list);
        Transliterator t = null;
        if (list.size() > 1 || canonID.indexOf(";") >= 0) {
            t = new CompoundTransliterator(translits);
        }
        else {
            t = translits.get(0);
        }
        t.setID(canonID.toString());
        if (globalFilter[0] != null) {
            t.setFilter(globalFilter[0]);
        }
        return t;
    }
    
    static Transliterator getBasicInstance(final String id, final String canonID) {
        final StringBuffer s = new StringBuffer();
        Transliterator t = Transliterator.registry.get(id, s);
        if (s.length() != 0) {
            t = getInstance(s.toString(), 0);
        }
        if (t != null && canonID != null) {
            t.setID(canonID);
        }
        return t;
    }
    
    public static final Transliterator createFromRules(final String ID, final String rules, final int dir) {
        Transliterator t = null;
        final TransliteratorParser parser = new TransliteratorParser();
        parser.parse(rules, dir);
        if (parser.idBlockVector.size() == 0 && parser.dataVector.size() == 0) {
            t = new NullTransliterator();
        }
        else if (parser.idBlockVector.size() == 0 && parser.dataVector.size() == 1) {
            t = new RuleBasedTransliterator(ID, parser.dataVector.get(0), parser.compoundFilter);
        }
        else if (parser.idBlockVector.size() == 1 && parser.dataVector.size() == 0) {
            if (parser.compoundFilter != null) {
                t = getInstance(parser.compoundFilter.toPattern(false) + ";" + parser.idBlockVector.get(0));
            }
            else {
                t = getInstance(parser.idBlockVector.get(0));
            }
            if (t != null) {
                t.setID(ID);
            }
        }
        else {
            final List<Transliterator> transliterators = new ArrayList<Transliterator>();
            int passNumber = 1;
            for (int limit = Math.max(parser.idBlockVector.size(), parser.dataVector.size()), i = 0; i < limit; ++i) {
                if (i < parser.idBlockVector.size()) {
                    final String idBlock = parser.idBlockVector.get(i);
                    if (idBlock.length() > 0) {
                        final Transliterator temp = getInstance(idBlock);
                        if (!(temp instanceof NullTransliterator)) {
                            transliterators.add(getInstance(idBlock));
                        }
                    }
                }
                if (i < parser.dataVector.size()) {
                    final RuleBasedTransliterator.Data data = parser.dataVector.get(i);
                    transliterators.add(new RuleBasedTransliterator("%Pass" + passNumber++, data, null));
                }
            }
            t = new CompoundTransliterator(transliterators, passNumber - 1);
            t.setID(ID);
            if (parser.compoundFilter != null) {
                t.setFilter(parser.compoundFilter);
            }
        }
        return t;
    }
    
    public String toRules(final boolean escapeUnprintable) {
        return this.baseToRules(escapeUnprintable);
    }
    
    protected final String baseToRules(final boolean escapeUnprintable) {
        if (escapeUnprintable) {
            final StringBuffer rulesSource = new StringBuffer();
            final String id = this.getID();
            int c;
            for (int i = 0; i < id.length(); i += UTF16.getCharCount(c)) {
                c = UTF16.charAt(id, i);
                if (!Utility.escapeUnprintable(rulesSource, c)) {
                    UTF16.append(rulesSource, c);
                }
            }
            rulesSource.insert(0, "::");
            rulesSource.append(';');
            return rulesSource.toString();
        }
        return "::" + this.getID() + ';';
    }
    
    public Transliterator[] getElements() {
        Transliterator[] result;
        if (this instanceof CompoundTransliterator) {
            final CompoundTransliterator cpd = (CompoundTransliterator)this;
            result = new Transliterator[cpd.getCount()];
            for (int i = 0; i < result.length; ++i) {
                result[i] = cpd.getTransliterator(i);
            }
        }
        else {
            result = new Transliterator[] { this };
        }
        return result;
    }
    
    public final UnicodeSet getSourceSet() {
        final UnicodeSet result = new UnicodeSet();
        this.addSourceTargetSet(this.getFilterAsUnicodeSet(UnicodeSet.ALL_CODE_POINTS), result, new UnicodeSet());
        return result;
    }
    
    protected UnicodeSet handleGetSourceSet() {
        return new UnicodeSet();
    }
    
    public UnicodeSet getTargetSet() {
        final UnicodeSet result = new UnicodeSet();
        this.addSourceTargetSet(this.getFilterAsUnicodeSet(UnicodeSet.ALL_CODE_POINTS), new UnicodeSet(), result);
        return result;
    }
    
    @Deprecated
    public void addSourceTargetSet(final UnicodeSet inputFilter, final UnicodeSet sourceSet, final UnicodeSet targetSet) {
        final UnicodeSet myFilter = this.getFilterAsUnicodeSet(inputFilter);
        final UnicodeSet temp = new UnicodeSet(this.handleGetSourceSet()).retainAll(myFilter);
        sourceSet.addAll(temp);
        for (final String s : temp) {
            final String t = this.transliterate(s);
            if (!s.equals(t)) {
                targetSet.addAll(t);
            }
        }
    }
    
    @Deprecated
    public UnicodeSet getFilterAsUnicodeSet(final UnicodeSet externalFilter) {
        if (this.filter == null) {
            return externalFilter;
        }
        final UnicodeSet filterSet = new UnicodeSet(externalFilter);
        UnicodeSet temp;
        try {
            temp = this.filter;
        }
        catch (ClassCastException e) {
            this.filter.addMatchSetTo(temp = new UnicodeSet());
        }
        return filterSet.retainAll(temp).freeze();
    }
    
    public final Transliterator getInverse() {
        return getInstance(this.ID, 1);
    }
    
    public static void registerClass(final String ID, final Class<? extends Transliterator> transClass, final String displayName) {
        Transliterator.registry.put(ID, transClass, true);
        if (displayName != null) {
            Transliterator.displayNameCache.put(new CaseInsensitiveString(ID), displayName);
        }
    }
    
    public static void registerFactory(final String ID, final Factory factory) {
        Transliterator.registry.put(ID, factory, true);
    }
    
    public static void registerInstance(final Transliterator trans) {
        Transliterator.registry.put(trans.getID(), trans, true);
    }
    
    static void registerInstance(final Transliterator trans, final boolean visible) {
        Transliterator.registry.put(trans.getID(), trans, visible);
    }
    
    public static void registerAlias(final String aliasID, final String realID) {
        Transliterator.registry.put(aliasID, realID, true);
    }
    
    static void registerSpecialInverse(final String target, final String inverseTarget, final boolean bidirectional) {
        TransliteratorIDParser.registerSpecialInverse(target, inverseTarget, bidirectional);
    }
    
    public static void unregister(final String ID) {
        Transliterator.displayNameCache.remove(new CaseInsensitiveString(ID));
        Transliterator.registry.remove(ID);
    }
    
    public static final Enumeration<String> getAvailableIDs() {
        return Transliterator.registry.getAvailableIDs();
    }
    
    public static final Enumeration<String> getAvailableSources() {
        return Transliterator.registry.getAvailableSources();
    }
    
    public static final Enumeration<String> getAvailableTargets(final String source) {
        return Transliterator.registry.getAvailableTargets(source);
    }
    
    public static final Enumeration<String> getAvailableVariants(final String source, final String target) {
        return Transliterator.registry.getAvailableVariants(source, target);
    }
    
    @Deprecated
    public static void registerAny() {
        AnyTransliterator.register();
    }
    
    @Override
    public String transform(final String source) {
        return this.transliterate(source);
    }
    
    static {
        Transliterator.registry = new TransliteratorRegistry();
        Transliterator.displayNameCache = Collections.synchronizedMap(new HashMap<CaseInsensitiveString, String>());
        final UResourceBundle bundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/translit", "root");
        final UResourceBundle transIDs = bundle.get("RuleBasedTransliteratorIDs");
        for (int maxRows = transIDs.getSize(), row = 0; row < maxRows; ++row) {
            final UResourceBundle colBund = transIDs.get(row);
            final String ID = colBund.getKey();
            if (ID.indexOf("-t-") < 0) {
                final UResourceBundle res = colBund.get(0);
                final String type = res.getKey();
                if (type.equals("file") || type.equals("internal")) {
                    final String resString = res.getString("resource");
                    final String direction = res.getString("direction");
                    int dir = 0;
                    switch (direction.charAt(0)) {
                        case 'F': {
                            dir = 0;
                            break;
                        }
                        case 'R': {
                            dir = 1;
                            break;
                        }
                        default: {
                            throw new RuntimeException("Can't parse direction: " + direction);
                        }
                    }
                    Transliterator.registry.put(ID, resString, dir, !type.equals("internal"));
                }
                else {
                    if (!type.equals("alias")) {
                        throw new RuntimeException("Unknow type: " + type);
                    }
                    final String resString = res.getString();
                    Transliterator.registry.put(ID, resString, true);
                }
            }
        }
        registerSpecialInverse("Null", "Null", false);
        registerClass("Any-Null", NullTransliterator.class, null);
        RemoveTransliterator.register();
        EscapeTransliterator.register();
        UnescapeTransliterator.register();
        LowercaseTransliterator.register();
        UppercaseTransliterator.register();
        TitlecaseTransliterator.register();
        CaseFoldTransliterator.register();
        UnicodeNameTransliterator.register();
        NameUnicodeTransliterator.register();
        NormalizationTransliterator.register();
        BreakTransliterator.register();
        AnyTransliterator.register();
    }
    
    public static class Position
    {
        public int contextStart;
        public int contextLimit;
        public int start;
        public int limit;
        
        public Position() {
            this(0, 0, 0, 0);
        }
        
        public Position(final int contextStart, final int contextLimit, final int start) {
            this(contextStart, contextLimit, start, contextLimit);
        }
        
        public Position(final int contextStart, final int contextLimit, final int start, final int limit) {
            this.contextStart = contextStart;
            this.contextLimit = contextLimit;
            this.start = start;
            this.limit = limit;
        }
        
        public Position(final Position pos) {
            this.set(pos);
        }
        
        public void set(final Position pos) {
            this.contextStart = pos.contextStart;
            this.contextLimit = pos.contextLimit;
            this.start = pos.start;
            this.limit = pos.limit;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof Position) {
                final Position pos = (Position)obj;
                return this.contextStart == pos.contextStart && this.contextLimit == pos.contextLimit && this.start == pos.start && this.limit == pos.limit;
            }
            return false;
        }
        
        @Deprecated
        @Override
        public int hashCode() {
            assert false : "hashCode not designed";
            return 42;
        }
        
        @Override
        public String toString() {
            return "[cs=" + this.contextStart + ", s=" + this.start + ", l=" + this.limit + ", cl=" + this.contextLimit + "]";
        }
        
        public final void validate(final int length) {
            if (this.contextStart < 0 || this.start < this.contextStart || this.limit < this.start || this.contextLimit < this.limit || length < this.contextLimit) {
                throw new IllegalArgumentException("Invalid Position {cs=" + this.contextStart + ", s=" + this.start + ", l=" + this.limit + ", cl=" + this.contextLimit + "}, len=" + length);
            }
        }
    }
    
    public interface Factory
    {
        Transliterator getInstance(final String p0);
    }
}
