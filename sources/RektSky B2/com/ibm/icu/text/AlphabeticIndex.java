package com.ibm.icu.text;

import java.util.*;
import com.ibm.icu.util.*;
import com.ibm.icu.lang.*;

public final class AlphabeticIndex<V> implements Iterable<Bucket<V>>
{
    private static final String BASE = "\ufdd0";
    private static final char CGJ = '\u034f';
    private static final Comparator<String> binaryCmp;
    private final RuleBasedCollator collatorOriginal;
    private final RuleBasedCollator collatorPrimaryOnly;
    private RuleBasedCollator collatorExternal;
    private final Comparator<Record<V>> recordComparator;
    private final List<String> firstCharsInScripts;
    private final UnicodeSet initialLabels;
    private List<Record<V>> inputList;
    private BucketList<V> buckets;
    private String overflowLabel;
    private String underflowLabel;
    private String inflowLabel;
    private int maxLabelCount;
    private static final int GC_LU_MASK = 2;
    private static final int GC_LL_MASK = 4;
    private static final int GC_LT_MASK = 8;
    private static final int GC_LM_MASK = 16;
    private static final int GC_LO_MASK = 32;
    private static final int GC_L_MASK = 62;
    private static final int GC_CN_MASK = 1;
    
    public AlphabeticIndex(final ULocale locale) {
        this(locale, null);
    }
    
    public AlphabeticIndex(final Locale locale) {
        this(ULocale.forLocale(locale), null);
    }
    
    public AlphabeticIndex(final RuleBasedCollator collator) {
        this(null, collator);
    }
    
    private AlphabeticIndex(final ULocale locale, final RuleBasedCollator collator) {
        this.recordComparator = new Comparator<Record<V>>() {
            @Override
            public int compare(final Record<V> o1, final Record<V> o2) {
                return AlphabeticIndex.this.collatorOriginal.compare(((Record<Object>)o1).name, ((Record<Object>)o2).name);
            }
        };
        this.initialLabels = new UnicodeSet();
        this.overflowLabel = "\u2026";
        this.underflowLabel = "\u2026";
        this.inflowLabel = "\u2026";
        this.maxLabelCount = 99;
        this.collatorOriginal = (RuleBasedCollator)((collator != null) ? collator : Collator.getInstance(locale));
        try {
            this.collatorPrimaryOnly = this.collatorOriginal.cloneAsThawed();
        }
        catch (Exception e) {
            throw new IllegalStateException("Collator cannot be cloned", e);
        }
        this.collatorPrimaryOnly.setStrength(0);
        this.collatorPrimaryOnly.freeze();
        Collections.sort(this.firstCharsInScripts = this.getFirstCharactersInScripts(), this.collatorPrimaryOnly);
        while (!this.firstCharsInScripts.isEmpty()) {
            if (this.collatorPrimaryOnly.compare(this.firstCharsInScripts.get(0), "") != 0) {
                if (!this.addChineseIndexCharacters() && locale != null) {
                    this.addIndexExemplars(locale);
                }
                return;
            }
            this.firstCharsInScripts.remove(0);
        }
        throw new IllegalArgumentException("AlphabeticIndex requires some non-ignorable script boundary strings");
    }
    
    public AlphabeticIndex<V> addLabels(final UnicodeSet additions) {
        this.initialLabels.addAll(additions);
        this.buckets = null;
        return this;
    }
    
    public AlphabeticIndex<V> addLabels(final ULocale... additions) {
        for (final ULocale addition : additions) {
            this.addIndexExemplars(addition);
        }
        this.buckets = null;
        return this;
    }
    
    public AlphabeticIndex<V> addLabels(final Locale... additions) {
        for (final Locale addition : additions) {
            this.addIndexExemplars(ULocale.forLocale(addition));
        }
        this.buckets = null;
        return this;
    }
    
    public AlphabeticIndex<V> setOverflowLabel(final String overflowLabel) {
        this.overflowLabel = overflowLabel;
        this.buckets = null;
        return this;
    }
    
    public String getUnderflowLabel() {
        return this.underflowLabel;
    }
    
    public AlphabeticIndex<V> setUnderflowLabel(final String underflowLabel) {
        this.underflowLabel = underflowLabel;
        this.buckets = null;
        return this;
    }
    
    public String getOverflowLabel() {
        return this.overflowLabel;
    }
    
    public AlphabeticIndex<V> setInflowLabel(final String inflowLabel) {
        this.inflowLabel = inflowLabel;
        this.buckets = null;
        return this;
    }
    
    public String getInflowLabel() {
        return this.inflowLabel;
    }
    
    public int getMaxLabelCount() {
        return this.maxLabelCount;
    }
    
    public AlphabeticIndex<V> setMaxLabelCount(final int maxLabelCount) {
        this.maxLabelCount = maxLabelCount;
        this.buckets = null;
        return this;
    }
    
    private List<String> initLabels() {
        final Normalizer2 nfkdNormalizer = Normalizer2.getNFKDInstance();
        final List<String> indexCharacters = new ArrayList<String>();
        final String firstScriptBoundary = this.firstCharsInScripts.get(0);
        final String overflowBoundary = this.firstCharsInScripts.get(this.firstCharsInScripts.size() - 1);
        for (String item : this.initialLabels) {
            boolean checkDistinct;
            if (!UTF16.hasMoreCodePointsThan(item, 1)) {
                checkDistinct = false;
            }
            else if (item.charAt(item.length() - 1) == '*' && item.charAt(item.length() - 2) != '*') {
                item = item.substring(0, item.length() - 1);
                checkDistinct = false;
            }
            else {
                checkDistinct = true;
            }
            if (this.collatorPrimaryOnly.compare(item, firstScriptBoundary) < 0) {
                continue;
            }
            if (this.collatorPrimaryOnly.compare(item, overflowBoundary) >= 0) {
                continue;
            }
            if (checkDistinct && this.collatorPrimaryOnly.compare(item, this.separated(item)) == 0) {
                continue;
            }
            final int insertionPoint = Collections.binarySearch(indexCharacters, item, this.collatorPrimaryOnly);
            if (insertionPoint < 0) {
                indexCharacters.add(~insertionPoint, item);
            }
            else {
                final String itemAlreadyIn = indexCharacters.get(insertionPoint);
                if (!isOneLabelBetterThanOther(nfkdNormalizer, item, itemAlreadyIn)) {
                    continue;
                }
                indexCharacters.set(insertionPoint, item);
            }
        }
        final int size = indexCharacters.size() - 1;
        if (size > this.maxLabelCount) {
            int count = 0;
            int old = -1;
            final Iterator<String> it = indexCharacters.iterator();
            while (it.hasNext()) {
                ++count;
                it.next();
                final int bump = count * this.maxLabelCount / size;
                if (bump == old) {
                    it.remove();
                }
                else {
                    old = bump;
                }
            }
        }
        return indexCharacters;
    }
    
    private static String fixLabel(final String current) {
        if (!current.startsWith("\ufdd0")) {
            return current;
        }
        final int rest = current.charAt("\ufdd0".length());
        if (10240 < rest && rest <= 10495) {
            return rest - 10240 + "\u5283";
        }
        return current.substring("\ufdd0".length());
    }
    
    private void addIndexExemplars(final ULocale locale) {
        UnicodeSet exemplars = LocaleData.getExemplarSet(locale, 0, 2);
        if (exemplars != null && !exemplars.isEmpty()) {
            this.initialLabels.addAll(exemplars);
            return;
        }
        exemplars = LocaleData.getExemplarSet(locale, 0, 0);
        exemplars = exemplars.cloneAsThawed();
        if (exemplars.containsSome(97, 122) || exemplars.isEmpty()) {
            exemplars.addAll(97, 122);
        }
        if (exemplars.containsSome(44032, 55203)) {
            exemplars.remove(44032, 55203).add(44032).add(45208).add(45796).add(46972).add(47560).add(48148).add(49324).add(50500).add(51088).add(52264).add(52852).add(53440).add(54028).add(54616);
        }
        if (exemplars.containsSome(4608, 4991)) {
            final UnicodeSet ethiopic = new UnicodeSet("[\u1200\u1208\u1210\u1218\u1220\u1228\u1230\u1238\u1240\u1248\u1250\u1258\u1260\u1268\u1270\u1278\u1280\u1288\u1290\u1298\u12a0\u12a8\u12b0\u12b8\u12c0\u12c8\u12d0\u12d8\u12e0\u12e8\u12f0\u12f8\u1300\u1308\u1310\u1318\u1320\u1328\u1330\u1338\u1340\u1348\u1350\u1358]");
            ethiopic.retainAll(exemplars);
            exemplars.remove(4608, 4991).addAll(ethiopic);
        }
        for (final String item : exemplars) {
            this.initialLabels.add(UCharacter.toUpperCase(locale, item));
        }
    }
    
    private boolean addChineseIndexCharacters() {
        final UnicodeSet contractions = new UnicodeSet();
        try {
            this.collatorPrimaryOnly.internalAddContractions("\ufdd0".charAt(0), contractions);
        }
        catch (Exception e) {
            return false;
        }
        if (contractions.isEmpty()) {
            return false;
        }
        this.initialLabels.addAll(contractions);
        for (final String s : contractions) {
            assert s.startsWith("\ufdd0");
            final char c = s.charAt(s.length() - 1);
            if ('A' <= c && c <= 'Z') {
                this.initialLabels.add(65, 90);
                break;
            }
        }
        return true;
    }
    
    private String separated(final String item) {
        final StringBuilder result = new StringBuilder();
        char last = item.charAt(0);
        result.append(last);
        for (int i = 1; i < item.length(); ++i) {
            final char ch = item.charAt(i);
            if (!UCharacter.isHighSurrogate(last) || !UCharacter.isLowSurrogate(ch)) {
                result.append('\u034f');
            }
            result.append(ch);
            last = ch;
        }
        return result.toString();
    }
    
    public ImmutableIndex<V> buildImmutableIndex() {
        BucketList<V> immutableBucketList;
        if (this.inputList != null && !this.inputList.isEmpty()) {
            immutableBucketList = this.createBucketList();
        }
        else {
            if (this.buckets == null) {
                this.buckets = this.createBucketList();
            }
            immutableBucketList = this.buckets;
        }
        return new ImmutableIndex<V>((BucketList)immutableBucketList, (Collator)this.collatorPrimaryOnly);
    }
    
    public List<String> getBucketLabels() {
        this.initBuckets();
        final ArrayList<String> result = new ArrayList<String>();
        for (final Bucket<V> bucket : this.buckets) {
            result.add(bucket.getLabel());
        }
        return result;
    }
    
    public RuleBasedCollator getCollator() {
        if (this.collatorExternal == null) {
            try {
                this.collatorExternal = (RuleBasedCollator)this.collatorOriginal.clone();
            }
            catch (Exception e) {
                throw new IllegalStateException("Collator cannot be cloned", e);
            }
        }
        return this.collatorExternal;
    }
    
    public AlphabeticIndex<V> addRecord(final CharSequence name, final V data) {
        this.buckets = null;
        if (this.inputList == null) {
            this.inputList = new ArrayList<Record<V>>();
        }
        this.inputList.add(new Record<V>(name, (Object)data));
        return this;
    }
    
    public int getBucketIndex(final CharSequence name) {
        this.initBuckets();
        return ((BucketList<Object>)this.buckets).getBucketIndex(name, this.collatorPrimaryOnly);
    }
    
    public AlphabeticIndex<V> clearRecords() {
        if (this.inputList != null && !this.inputList.isEmpty()) {
            this.inputList.clear();
            this.buckets = null;
        }
        return this;
    }
    
    public int getBucketCount() {
        this.initBuckets();
        return ((BucketList<Object>)this.buckets).getBucketCount();
    }
    
    public int getRecordCount() {
        return (this.inputList != null) ? this.inputList.size() : 0;
    }
    
    @Override
    public Iterator<Bucket<V>> iterator() {
        this.initBuckets();
        return this.buckets.iterator();
    }
    
    private void initBuckets() {
        if (this.buckets != null) {
            return;
        }
        this.buckets = this.createBucketList();
        if (this.inputList == null || this.inputList.isEmpty()) {
            return;
        }
        Collections.sort(this.inputList, this.recordComparator);
        final Iterator<Bucket<V>> bucketIterator = (Iterator<Bucket<V>>)((BucketList<Object>)this.buckets).fullIterator();
        Bucket<V> currentBucket = bucketIterator.next();
        Bucket<V> nextBucket;
        String upperBoundary;
        if (bucketIterator.hasNext()) {
            nextBucket = bucketIterator.next();
            upperBoundary = ((Bucket<Object>)nextBucket).lowerBoundary;
        }
        else {
            nextBucket = null;
            upperBoundary = null;
        }
        for (final Record<V> r : this.inputList) {
            while (upperBoundary != null && this.collatorPrimaryOnly.compare(((Record<Object>)r).name, upperBoundary) >= 0) {
                currentBucket = nextBucket;
                if (bucketIterator.hasNext()) {
                    nextBucket = bucketIterator.next();
                    upperBoundary = ((Bucket<Object>)nextBucket).lowerBoundary;
                }
                else {
                    upperBoundary = null;
                }
            }
            Bucket<V> bucket = currentBucket;
            if (((Bucket<Object>)bucket).displayBucket != null) {
                bucket = (Bucket<V>)((Bucket<Object>)bucket).displayBucket;
            }
            if (((Bucket<Object>)bucket).records == null) {
                ((Bucket<Object>)bucket).records = (List<Record<Object>>)new ArrayList();
            }
            ((Bucket<Object>)bucket).records.add(r);
        }
    }
    
    private static boolean isOneLabelBetterThanOther(final Normalizer2 nfkdNormalizer, final String one, final String other) {
        final String n1 = nfkdNormalizer.normalize(one);
        final String n2 = nfkdNormalizer.normalize(other);
        int result = n1.codePointCount(0, n1.length()) - n2.codePointCount(0, n2.length());
        if (result != 0) {
            return result < 0;
        }
        result = AlphabeticIndex.binaryCmp.compare(n1, n2);
        if (result != 0) {
            return result < 0;
        }
        return AlphabeticIndex.binaryCmp.compare(one, other) < 0;
    }
    
    private BucketList<V> createBucketList() {
        final List<String> indexCharacters = this.initLabels();
        long variableTop;
        if (this.collatorPrimaryOnly.isAlternateHandlingShifted()) {
            variableTop = ((long)this.collatorPrimaryOnly.getVariableTop() & 0xFFFFFFFFL);
        }
        else {
            variableTop = 0L;
        }
        boolean hasInvisibleBuckets = false;
        final Bucket<V>[] asciiBuckets = (Bucket<V>[])new Bucket[26];
        final Bucket<V>[] pinyinBuckets = (Bucket<V>[])new Bucket[26];
        boolean hasPinyin = false;
        final ArrayList<Bucket<V>> bucketList = new ArrayList<Bucket<V>>();
        bucketList.add(new Bucket<V>(this.getUnderflowLabel(), "", Bucket.LabelType.UNDERFLOW));
        int scriptIndex = -1;
        String scriptUpperBoundary = "";
        for (final String current : indexCharacters) {
            if (this.collatorPrimaryOnly.compare(current, scriptUpperBoundary) >= 0) {
                final String inflowBoundary = scriptUpperBoundary;
                boolean skippedScript = false;
                while (true) {
                    scriptUpperBoundary = this.firstCharsInScripts.get(++scriptIndex);
                    if (this.collatorPrimaryOnly.compare(current, scriptUpperBoundary) < 0) {
                        break;
                    }
                    skippedScript = true;
                }
                if (skippedScript && bucketList.size() > 1) {
                    bucketList.add(new Bucket<V>(this.getInflowLabel(), inflowBoundary, Bucket.LabelType.INFLOW));
                }
            }
            Bucket<V> bucket = new Bucket<V>(fixLabel(current), current, Bucket.LabelType.NORMAL);
            bucketList.add(bucket);
            char c;
            if (current.length() == 1 && 'A' <= (c = current.charAt(0)) && c <= 'Z') {
                asciiBuckets[c - 'A'] = bucket;
            }
            else if (current.length() == "\ufdd0".length() + 1 && current.startsWith("\ufdd0") && 'A' <= (c = current.charAt("\ufdd0".length())) && c <= 'Z') {
                pinyinBuckets[c - 'A'] = bucket;
                hasPinyin = true;
            }
            if (!current.startsWith("\ufdd0") && hasMultiplePrimaryWeights(this.collatorPrimaryOnly, variableTop, current) && !current.endsWith("\uffff")) {
                int i = bucketList.size() - 2;
                while (true) {
                    final Bucket<V> singleBucket = bucketList.get(i);
                    if (((Bucket<Object>)singleBucket).labelType != Bucket.LabelType.NORMAL) {
                        break;
                    }
                    if (((Bucket<Object>)singleBucket).displayBucket == null && !hasMultiplePrimaryWeights(this.collatorPrimaryOnly, variableTop, ((Bucket<Object>)singleBucket).lowerBoundary)) {
                        bucket = new Bucket<V>("", current + "\uffff", Bucket.LabelType.NORMAL);
                        ((Bucket<Object>)bucket).displayBucket = (Bucket<Object>)singleBucket;
                        bucketList.add(bucket);
                        hasInvisibleBuckets = true;
                        break;
                    }
                    --i;
                }
            }
        }
        if (bucketList.size() == 1) {
            return new BucketList<V>((ArrayList)bucketList, (ArrayList)bucketList);
        }
        bucketList.add(new Bucket<V>(this.getOverflowLabel(), scriptUpperBoundary, Bucket.LabelType.OVERFLOW));
        if (hasPinyin) {
            Bucket<V> asciiBucket = null;
            for (int j = 0; j < 26; ++j) {
                if (asciiBuckets[j] != null) {
                    asciiBucket = asciiBuckets[j];
                }
                if (pinyinBuckets[j] != null && asciiBucket != null) {
                    ((Bucket<Object>)pinyinBuckets[j]).displayBucket = (Bucket<Object>)asciiBucket;
                    hasInvisibleBuckets = true;
                }
            }
        }
        if (!hasInvisibleBuckets) {
            return new BucketList<V>((ArrayList)bucketList, (ArrayList)bucketList);
        }
        int k = bucketList.size() - 1;
        Bucket<V> nextBucket = bucketList.get(k);
        while (--k > 0) {
            final Bucket<V> bucket = bucketList.get(k);
            if (((Bucket<Object>)bucket).displayBucket != null) {
                continue;
            }
            if (((Bucket<Object>)bucket).labelType == Bucket.LabelType.INFLOW && ((Bucket<Object>)nextBucket).labelType != Bucket.LabelType.NORMAL) {
                ((Bucket<Object>)bucket).displayBucket = (Bucket<Object>)nextBucket;
            }
            else {
                nextBucket = bucket;
            }
        }
        final ArrayList<Bucket<V>> publicBucketList = new ArrayList<Bucket<V>>();
        for (final Bucket<V> bucket2 : bucketList) {
            if (((Bucket<Object>)bucket2).displayBucket == null) {
                publicBucketList.add(bucket2);
            }
        }
        return new BucketList<V>((ArrayList)bucketList, (ArrayList)publicBucketList);
    }
    
    private static boolean hasMultiplePrimaryWeights(final RuleBasedCollator coll, final long variableTop, final String s) {
        final long[] ces = coll.internalGetCEs(s);
        boolean seenPrimary = false;
        for (int i = 0; i < ces.length; ++i) {
            final long ce = ces[i];
            final long p = ce >>> 32;
            if (p > variableTop) {
                if (seenPrimary) {
                    return true;
                }
                seenPrimary = true;
            }
        }
        return false;
    }
    
    @Deprecated
    public List<String> getFirstCharactersInScripts() {
        final List<String> dest = new ArrayList<String>(200);
        final UnicodeSet set = new UnicodeSet();
        this.collatorPrimaryOnly.internalAddContractions(64977, set);
        if (set.isEmpty()) {
            throw new UnsupportedOperationException("AlphabeticIndex requires script-first-primary contractions");
        }
        for (final String boundary : set) {
            final int gcMask = 1 << UCharacter.getType(boundary.codePointAt(1));
            if ((gcMask & 0x3F) == 0x0) {
                continue;
            }
            dest.add(boundary);
        }
        return dest;
    }
    
    static {
        binaryCmp = new UTF16.StringComparator(true, false, 0);
    }
    
    public static final class ImmutableIndex<V> implements Iterable<Bucket<V>>
    {
        private final BucketList<V> buckets;
        private final Collator collatorPrimaryOnly;
        
        private ImmutableIndex(final BucketList<V> bucketList, final Collator collatorPrimaryOnly) {
            this.buckets = bucketList;
            this.collatorPrimaryOnly = collatorPrimaryOnly;
        }
        
        public int getBucketCount() {
            return ((BucketList<Object>)this.buckets).getBucketCount();
        }
        
        public int getBucketIndex(final CharSequence name) {
            return ((BucketList<Object>)this.buckets).getBucketIndex(name, this.collatorPrimaryOnly);
        }
        
        public Bucket<V> getBucket(final int index) {
            if (0 <= index && index < ((BucketList<Object>)this.buckets).getBucketCount()) {
                return ((BucketList<Object>)this.buckets).immutableVisibleList.get(index);
            }
            return null;
        }
        
        @Override
        public Iterator<Bucket<V>> iterator() {
            return this.buckets.iterator();
        }
    }
    
    public static class Record<V>
    {
        private final CharSequence name;
        private final V data;
        
        private Record(final CharSequence name, final V data) {
            this.name = name;
            this.data = data;
        }
        
        public CharSequence getName() {
            return this.name;
        }
        
        public V getData() {
            return this.data;
        }
        
        @Override
        public String toString() {
            return (Object)this.name + "=" + this.data;
        }
    }
    
    public static class Bucket<V> implements Iterable<Record<V>>
    {
        private final String label;
        private final String lowerBoundary;
        private final LabelType labelType;
        private Bucket<V> displayBucket;
        private int displayIndex;
        private List<Record<V>> records;
        
        private Bucket(final String label, final String lowerBoundary, final LabelType labelType) {
            this.label = label;
            this.lowerBoundary = lowerBoundary;
            this.labelType = labelType;
        }
        
        public String getLabel() {
            return this.label;
        }
        
        public LabelType getLabelType() {
            return this.labelType;
        }
        
        public int size() {
            return (this.records == null) ? 0 : this.records.size();
        }
        
        @Override
        public Iterator<Record<V>> iterator() {
            if (this.records == null) {
                return Collections.emptyList().iterator();
            }
            return this.records.iterator();
        }
        
        @Override
        public String toString() {
            return "{labelType=" + this.labelType + ", lowerBoundary=" + this.lowerBoundary + ", label=" + this.label + "}";
        }
        
        public enum LabelType
        {
            NORMAL, 
            UNDERFLOW, 
            INFLOW, 
            OVERFLOW;
        }
    }
    
    private static class BucketList<V> implements Iterable<Bucket<V>>
    {
        private final ArrayList<Bucket<V>> bucketList;
        private final List<Bucket<V>> immutableVisibleList;
        
        private BucketList(final ArrayList<Bucket<V>> bucketList, final ArrayList<Bucket<V>> publicBucketList) {
            this.bucketList = bucketList;
            int displayIndex = 0;
            for (final Bucket<V> bucket : publicBucketList) {
                ((Bucket<Object>)bucket).displayIndex = displayIndex++;
            }
            this.immutableVisibleList = Collections.unmodifiableList((List<? extends Bucket<V>>)publicBucketList);
        }
        
        private int getBucketCount() {
            return this.immutableVisibleList.size();
        }
        
        private int getBucketIndex(final CharSequence name, final Collator collatorPrimaryOnly) {
            int start = 0;
            int limit = this.bucketList.size();
            while (start + 1 < limit) {
                final int i = (start + limit) / 2;
                final Bucket<V> bucket = this.bucketList.get(i);
                final int nameVsBucket = collatorPrimaryOnly.compare(name, ((Bucket<Object>)bucket).lowerBoundary);
                if (nameVsBucket < 0) {
                    limit = i;
                }
                else {
                    start = i;
                }
            }
            Bucket<V> bucket2 = this.bucketList.get(start);
            if (((Bucket<Object>)bucket2).displayBucket != null) {
                bucket2 = (Bucket<V>)((Bucket<Object>)bucket2).displayBucket;
            }
            return ((Bucket<Object>)bucket2).displayIndex;
        }
        
        private Iterator<Bucket<V>> fullIterator() {
            return this.bucketList.iterator();
        }
        
        @Override
        public Iterator<Bucket<V>> iterator() {
            return this.immutableVisibleList.iterator();
        }
    }
}
