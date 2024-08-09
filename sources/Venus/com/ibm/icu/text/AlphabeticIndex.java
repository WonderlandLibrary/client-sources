/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Collator;
import com.ibm.icu.text.Normalizer2;
import com.ibm.icu.text.RuleBasedCollator;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.LocaleData;
import com.ibm.icu.util.ULocale;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public final class AlphabeticIndex<V>
implements Iterable<Bucket<V>> {
    private static final String BASE = "\ufdd0";
    private static final char CGJ = '\u034f';
    private static final Comparator<String> binaryCmp;
    private final RuleBasedCollator collatorOriginal;
    private final RuleBasedCollator collatorPrimaryOnly;
    private RuleBasedCollator collatorExternal;
    private final Comparator<Record<V>> recordComparator = new Comparator<Record<V>>(this){
        final AlphabeticIndex this$0;
        {
            this.this$0 = alphabeticIndex;
        }

        @Override
        public int compare(Record<V> record, Record<V> record2) {
            return AlphabeticIndex.access$100(this.this$0).compare(Record.access$000(record), Record.access$000(record2));
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((Record)object, (Record)object2);
        }
    };
    private final List<String> firstCharsInScripts;
    private final UnicodeSet initialLabels = new UnicodeSet();
    private List<Record<V>> inputList;
    private BucketList<V> buckets;
    private String overflowLabel = "\u2026";
    private String underflowLabel = "\u2026";
    private String inflowLabel = "\u2026";
    private int maxLabelCount = 99;
    private static final int GC_LU_MASK = 2;
    private static final int GC_LL_MASK = 4;
    private static final int GC_LT_MASK = 8;
    private static final int GC_LM_MASK = 16;
    private static final int GC_LO_MASK = 32;
    private static final int GC_L_MASK = 62;
    private static final int GC_CN_MASK = 1;
    static final boolean $assertionsDisabled;

    public AlphabeticIndex(ULocale uLocale) {
        this(uLocale, null);
    }

    public AlphabeticIndex(Locale locale) {
        this(ULocale.forLocale(locale), null);
    }

    public AlphabeticIndex(RuleBasedCollator ruleBasedCollator) {
        this(null, ruleBasedCollator);
    }

    private AlphabeticIndex(ULocale uLocale, RuleBasedCollator ruleBasedCollator) {
        this.collatorOriginal = ruleBasedCollator != null ? ruleBasedCollator : (RuleBasedCollator)Collator.getInstance(uLocale);
        try {
            this.collatorPrimaryOnly = this.collatorOriginal.cloneAsThawed();
        } catch (Exception exception) {
            throw new IllegalStateException("Collator cannot be cloned", exception);
        }
        this.collatorPrimaryOnly.setStrength(0);
        this.collatorPrimaryOnly.freeze();
        this.firstCharsInScripts = this.getFirstCharactersInScripts();
        Collections.sort(this.firstCharsInScripts, this.collatorPrimaryOnly);
        while (true) {
            if (this.firstCharsInScripts.isEmpty()) {
                throw new IllegalArgumentException("AlphabeticIndex requires some non-ignorable script boundary strings");
            }
            if (this.collatorPrimaryOnly.compare(this.firstCharsInScripts.get(0), "") != 0) break;
            this.firstCharsInScripts.remove(0);
        }
        if (!this.addChineseIndexCharacters() && uLocale != null) {
            this.addIndexExemplars(uLocale);
        }
    }

    public AlphabeticIndex<V> addLabels(UnicodeSet unicodeSet) {
        this.initialLabels.addAll(unicodeSet);
        this.buckets = null;
        return this;
    }

    public AlphabeticIndex<V> addLabels(ULocale ... uLocaleArray) {
        for (ULocale uLocale : uLocaleArray) {
            this.addIndexExemplars(uLocale);
        }
        this.buckets = null;
        return this;
    }

    public AlphabeticIndex<V> addLabels(Locale ... localeArray) {
        for (Locale locale : localeArray) {
            this.addIndexExemplars(ULocale.forLocale(locale));
        }
        this.buckets = null;
        return this;
    }

    public AlphabeticIndex<V> setOverflowLabel(String string) {
        this.overflowLabel = string;
        this.buckets = null;
        return this;
    }

    public String getUnderflowLabel() {
        return this.underflowLabel;
    }

    public AlphabeticIndex<V> setUnderflowLabel(String string) {
        this.underflowLabel = string;
        this.buckets = null;
        return this;
    }

    public String getOverflowLabel() {
        return this.overflowLabel;
    }

    public AlphabeticIndex<V> setInflowLabel(String string) {
        this.inflowLabel = string;
        this.buckets = null;
        return this;
    }

    public String getInflowLabel() {
        return this.inflowLabel;
    }

    public int getMaxLabelCount() {
        return this.maxLabelCount;
    }

    public AlphabeticIndex<V> setMaxLabelCount(int n) {
        this.maxLabelCount = n;
        this.buckets = null;
        return this;
    }

    private List<String> initLabels() {
        int n;
        Normalizer2 normalizer2 = Normalizer2.getNFKDInstance();
        ArrayList<String> arrayList = new ArrayList<String>();
        String string = this.firstCharsInScripts.get(0);
        String string2 = this.firstCharsInScripts.get(this.firstCharsInScripts.size() - 1);
        for (String string3 : this.initialLabels) {
            if (!UTF16.hasMoreCodePointsThan(string3, 1)) {
                n = 0;
            } else if (string3.charAt(string3.length() - 1) == '*' && string3.charAt(string3.length() - 2) != '*') {
                string3 = string3.substring(0, string3.length() - 1);
                n = 0;
            } else {
                n = 1;
            }
            if (this.collatorPrimaryOnly.compare(string3, string) < 0 || this.collatorPrimaryOnly.compare(string3, string2) >= 0 || n != 0 && this.collatorPrimaryOnly.compare(string3, this.separated(string3)) == 0) continue;
            int n2 = Collections.binarySearch(arrayList, string3, this.collatorPrimaryOnly);
            if (n2 < 0) {
                arrayList.add(~n2, string3);
                continue;
            }
            String string4 = (String)arrayList.get(n2);
            if (!AlphabeticIndex.isOneLabelBetterThanOther(normalizer2, string3, string4)) continue;
            arrayList.set(n2, string3);
        }
        int n3 = arrayList.size() - 1;
        if (n3 > this.maxLabelCount) {
            int n4 = 0;
            n = -1;
            Iterator iterator2 = arrayList.iterator();
            while (iterator2.hasNext()) {
                iterator2.next();
                int n5 = ++n4 * this.maxLabelCount / n3;
                if (n5 == n) {
                    iterator2.remove();
                    continue;
                }
                n = n5;
            }
        }
        return arrayList;
    }

    private static String fixLabel(String string) {
        if (!string.startsWith(BASE)) {
            return string;
        }
        char c = string.charAt(1);
        if ('\u2800' < c && c <= '\u28ff') {
            return c - 10240 + "\u5283";
        }
        return string.substring(1);
    }

    private void addIndexExemplars(ULocale uLocale) {
        UnicodeSet unicodeSet = LocaleData.getExemplarSet(uLocale, 0, 2);
        if (unicodeSet != null && !unicodeSet.isEmpty()) {
            this.initialLabels.addAll(unicodeSet);
            return;
        }
        unicodeSet = LocaleData.getExemplarSet(uLocale, 0, 0);
        if ((unicodeSet = unicodeSet.cloneAsThawed()).containsSome(97, 1) || unicodeSet.isEmpty()) {
            unicodeSet.addAll(97, 122);
        }
        if (unicodeSet.containsSome(44032, 0)) {
            unicodeSet.remove(44032, 55203).add(44032).add(45208).add(45796).add(46972).add(47560).add(48148).add(49324).add(50500).add(51088).add(52264).add(52852).add(53440).add(54028).add(54616);
        }
        if (unicodeSet.containsSome(4608, 0)) {
            UnicodeSet unicodeSet2 = new UnicodeSet("[\u1200\u1208\u1210\u1218\u1220\u1228\u1230\u1238\u1240\u1248\u1250\u1258\u1260\u1268\u1270\u1278\u1280\u1288\u1290\u1298\u12a0\u12a8\u12b0\u12b8\u12c0\u12c8\u12d0\u12d8\u12e0\u12e8\u12f0\u12f8\u1300\u1308\u1310\u1318\u1320\u1328\u1330\u1338\u1340\u1348\u1350\u1358]");
            unicodeSet2.retainAll(unicodeSet);
            unicodeSet.remove(4608, 4991).addAll(unicodeSet2);
        }
        for (String string : unicodeSet) {
            this.initialLabels.add(UCharacter.toUpperCase(uLocale, string));
        }
    }

    private boolean addChineseIndexCharacters() {
        UnicodeSet unicodeSet = new UnicodeSet();
        try {
            this.collatorPrimaryOnly.internalAddContractions(BASE.charAt(0), unicodeSet);
        } catch (Exception exception) {
            return true;
        }
        if (unicodeSet.isEmpty()) {
            return true;
        }
        this.initialLabels.addAll(unicodeSet);
        for (String string : unicodeSet) {
            if (!$assertionsDisabled && !string.startsWith(BASE)) {
                throw new AssertionError();
            }
            char c = string.charAt(string.length() - 1);
            if ('A' > c || c > 'Z') continue;
            this.initialLabels.add(65, 90);
            break;
        }
        return false;
    }

    private String separated(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        char c = string.charAt(0);
        stringBuilder.append(c);
        for (int i = 1; i < string.length(); ++i) {
            char c2 = string.charAt(i);
            if (!UCharacter.isHighSurrogate(c) || !UCharacter.isLowSurrogate(c2)) {
                stringBuilder.append('\u034f');
            }
            stringBuilder.append(c2);
            c = c2;
        }
        return stringBuilder.toString();
    }

    public ImmutableIndex<V> buildImmutableIndex() {
        BucketList<V> bucketList;
        if (this.inputList != null && !this.inputList.isEmpty()) {
            bucketList = this.createBucketList();
        } else {
            if (this.buckets == null) {
                this.buckets = this.createBucketList();
            }
            bucketList = this.buckets;
        }
        return new ImmutableIndex(bucketList, this.collatorPrimaryOnly, null);
    }

    public List<String> getBucketLabels() {
        this.initBuckets();
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Bucket<V> bucket : this.buckets) {
            arrayList.add(bucket.getLabel());
        }
        return arrayList;
    }

    public RuleBasedCollator getCollator() {
        if (this.collatorExternal == null) {
            try {
                this.collatorExternal = (RuleBasedCollator)this.collatorOriginal.clone();
            } catch (Exception exception) {
                throw new IllegalStateException("Collator cannot be cloned", exception);
            }
        }
        return this.collatorExternal;
    }

    public AlphabeticIndex<V> addRecord(CharSequence charSequence, V v) {
        this.buckets = null;
        if (this.inputList == null) {
            this.inputList = new ArrayList<Record<V>>();
        }
        this.inputList.add(new Record(charSequence, v, null));
        return this;
    }

    public int getBucketIndex(CharSequence charSequence) {
        this.initBuckets();
        return BucketList.access$300(this.buckets, charSequence, this.collatorPrimaryOnly);
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
        return BucketList.access$200(this.buckets);
    }

    public int getRecordCount() {
        return this.inputList != null ? this.inputList.size() : 0;
    }

    @Override
    public Iterator<Bucket<V>> iterator() {
        this.initBuckets();
        return this.buckets.iterator();
    }

    private void initBuckets() {
        String string;
        Bucket bucket;
        if (this.buckets != null) {
            return;
        }
        this.buckets = this.createBucketList();
        if (this.inputList == null || this.inputList.isEmpty()) {
            return;
        }
        Collections.sort(this.inputList, this.recordComparator);
        Iterator iterator2 = BucketList.access$700(this.buckets);
        Bucket bucket2 = (Bucket)iterator2.next();
        if (iterator2.hasNext()) {
            bucket = (Bucket)iterator2.next();
            string = Bucket.access$800(bucket);
        } else {
            bucket = null;
            string = null;
        }
        for (Record<V> record : this.inputList) {
            while (string != null && this.collatorPrimaryOnly.compare(Record.access$000(record), (Object)string) >= 0) {
                bucket2 = bucket;
                if (iterator2.hasNext()) {
                    bucket = (Bucket)iterator2.next();
                    string = Bucket.access$800(bucket);
                    continue;
                }
                string = null;
            }
            Bucket bucket3 = bucket2;
            if (Bucket.access$900(bucket3) != null) {
                bucket3 = Bucket.access$900(bucket3);
            }
            if (Bucket.access$1000(bucket3) == null) {
                Bucket.access$1002(bucket3, new ArrayList());
            }
            Bucket.access$1000(bucket3).add(record);
        }
    }

    private static boolean isOneLabelBetterThanOther(Normalizer2 normalizer2, String string, String string2) {
        String string3 = normalizer2.normalize(string);
        String string4 = normalizer2.normalize(string2);
        int n = string3.codePointCount(0, string3.length()) - string4.codePointCount(0, string4.length());
        if (n != 0) {
            return n < 0;
        }
        n = binaryCmp.compare(string3, string4);
        if (n != 0) {
            return n < 0;
        }
        return binaryCmp.compare(string, string2) < 0;
    }

    private BucketList<V> createBucketList() {
        Iterable<Bucket> iterable;
        List<String> list = this.initLabels();
        long l = this.collatorPrimaryOnly.isAlternateHandlingShifted() ? (long)this.collatorPrimaryOnly.getVariableTop() & 0xFFFFFFFFL : 0L;
        boolean bl = false;
        Bucket[] bucketArray = new Bucket[26];
        Bucket[] bucketArray2 = new Bucket[26];
        boolean bl2 = false;
        ArrayList<Iterable<Record<Object>>> arrayList = new ArrayList<Iterable<Record<Object>>>();
        arrayList.add(new Bucket(this.getUnderflowLabel(), "", Bucket.LabelType.UNDERFLOW, null));
        int n = -1;
        String string = "";
        block0: for (String string2 : list) {
            Bucket bucket;
            char c;
            if (this.collatorPrimaryOnly.compare(string2, string) >= 0) {
                iterable = string;
                c = '\u0000';
                while (this.collatorPrimaryOnly.compare(string2, string = this.firstCharsInScripts.get(++n)) >= 0) {
                    c = '\u0001';
                }
                if (c != '\u0000' && arrayList.size() > 1) {
                    arrayList.add(new Bucket(this.getInflowLabel(), (String)((Object)iterable), Bucket.LabelType.INFLOW, null));
                }
            }
            iterable = new Bucket(AlphabeticIndex.fixLabel(string2), string2, Bucket.LabelType.NORMAL, null);
            arrayList.add(iterable);
            if (string2.length() == 1 && 'A' <= (c = string2.charAt(0)) && c <= 'Z') {
                bucketArray[c - 65] = iterable;
            } else if (string2.length() == 2 && string2.startsWith(BASE) && 'A' <= (c = string2.charAt(1)) && c <= 'Z') {
                bucketArray2[c - 65] = iterable;
                bl2 = true;
            }
            if (string2.startsWith(BASE) || !AlphabeticIndex.hasMultiplePrimaryWeights(this.collatorPrimaryOnly, l, string2) || string2.endsWith("\uffff")) continue;
            int n2 = arrayList.size() - 2;
            while (Bucket.access$1200(bucket = (Bucket)arrayList.get(n2)) == Bucket.LabelType.NORMAL) {
                if (Bucket.access$900(bucket) == null && !AlphabeticIndex.hasMultiplePrimaryWeights(this.collatorPrimaryOnly, l, Bucket.access$800(bucket))) {
                    iterable = new Bucket("", string2 + "\uffff", Bucket.LabelType.NORMAL, null);
                    Bucket.access$902(iterable, bucket);
                    arrayList.add(iterable);
                    bl = true;
                    continue block0;
                }
                --n2;
            }
        }
        if (arrayList.size() == 1) {
            return new BucketList(arrayList, arrayList, null);
        }
        arrayList.add(new Bucket(this.getOverflowLabel(), string, Bucket.LabelType.OVERFLOW, null));
        if (bl2) {
            Object object = null;
            for (int i = 0; i < 26; ++i) {
                if (bucketArray[i] != null) {
                    object = bucketArray[i];
                }
                if (bucketArray2[i] == null || object == null) continue;
                Bucket.access$902(bucketArray2[i], (Bucket)object);
                bl = true;
            }
        }
        if (!bl) {
            return new BucketList(arrayList, arrayList, null);
        }
        int n3 = arrayList.size() - 1;
        Iterable<Record<Object>> iterable2 = (Bucket)arrayList.get(n3);
        while (--n3 > 0) {
            iterable = (Bucket)arrayList.get(n3);
            if (Bucket.access$900(iterable) != null) continue;
            if (Bucket.access$1200(iterable) == Bucket.LabelType.INFLOW && Bucket.access$1200(iterable2) != Bucket.LabelType.NORMAL) {
                Bucket.access$902(iterable, iterable2);
                continue;
            }
            iterable2 = iterable;
        }
        iterable = new ArrayList();
        for (Bucket bucket : arrayList) {
            if (Bucket.access$900(bucket) != null) continue;
            ((ArrayList)iterable).add(bucket);
        }
        return new BucketList(arrayList, (ArrayList)iterable, null);
    }

    private static boolean hasMultiplePrimaryWeights(RuleBasedCollator ruleBasedCollator, long l, String string) {
        long[] lArray = ruleBasedCollator.internalGetCEs(string);
        boolean bl = false;
        for (int i = 0; i < lArray.length; ++i) {
            long l2 = lArray[i];
            long l3 = l2 >>> 32;
            if (l3 <= l) continue;
            if (bl) {
                return false;
            }
            bl = true;
        }
        return true;
    }

    @Deprecated
    public List<String> getFirstCharactersInScripts() {
        ArrayList<String> arrayList = new ArrayList<String>(200);
        UnicodeSet unicodeSet = new UnicodeSet();
        this.collatorPrimaryOnly.internalAddContractions(64977, unicodeSet);
        if (unicodeSet.isEmpty()) {
            throw new UnsupportedOperationException("AlphabeticIndex requires script-first-primary contractions");
        }
        for (String string : unicodeSet) {
            int n = 1 << UCharacter.getType(string.codePointAt(1));
            if ((n & 0x3F) == 0) continue;
            arrayList.add(string);
        }
        return arrayList;
    }

    static RuleBasedCollator access$100(AlphabeticIndex alphabeticIndex) {
        return alphabeticIndex.collatorOriginal;
    }

    static {
        $assertionsDisabled = !AlphabeticIndex.class.desiredAssertionStatus();
        binaryCmp = new UTF16.StringComparator(true, false, 0);
    }

    private static class BucketList<V>
    implements Iterable<Bucket<V>> {
        private final ArrayList<Bucket<V>> bucketList;
        private final List<Bucket<V>> immutableVisibleList;

        private BucketList(ArrayList<Bucket<V>> arrayList, ArrayList<Bucket<V>> arrayList2) {
            this.bucketList = arrayList;
            int n = 0;
            for (Bucket<V> bucket : arrayList2) {
                Bucket.access$1402(bucket, n++);
            }
            this.immutableVisibleList = Collections.unmodifiableList(arrayList2);
        }

        private int getBucketCount() {
            return this.immutableVisibleList.size();
        }

        private int getBucketIndex(CharSequence charSequence, Collator collator) {
            int n = 0;
            int n2 = this.bucketList.size();
            while (n + 1 < n2) {
                int n3 = (n + n2) / 2;
                Bucket<V> bucket = this.bucketList.get(n3);
                int n4 = collator.compare(charSequence, (Object)Bucket.access$800(bucket));
                if (n4 < 0) {
                    n2 = n3;
                    continue;
                }
                n = n3;
            }
            Bucket bucket = this.bucketList.get(n);
            if (Bucket.access$900(bucket) != null) {
                bucket = Bucket.access$900(bucket);
            }
            return Bucket.access$1400(bucket);
        }

        private Iterator<Bucket<V>> fullIterator() {
            return this.bucketList.iterator();
        }

        @Override
        public Iterator<Bucket<V>> iterator() {
            return this.immutableVisibleList.iterator();
        }

        static int access$200(BucketList bucketList) {
            return bucketList.getBucketCount();
        }

        static int access$300(BucketList bucketList, CharSequence charSequence, Collator collator) {
            return bucketList.getBucketIndex(charSequence, collator);
        }

        static List access$400(BucketList bucketList) {
            return bucketList.immutableVisibleList;
        }

        static Iterator access$700(BucketList bucketList) {
            return bucketList.fullIterator();
        }

        BucketList(ArrayList arrayList, ArrayList arrayList2, 1 var3_3) {
            this(arrayList, arrayList2);
        }
    }

    public static class Bucket<V>
    implements Iterable<Record<V>> {
        private final String label;
        private final String lowerBoundary;
        private final LabelType labelType;
        private Bucket<V> displayBucket;
        private int displayIndex;
        private List<Record<V>> records;

        private Bucket(String string, String string2, LabelType labelType) {
            this.label = string;
            this.lowerBoundary = string2;
            this.labelType = labelType;
        }

        public String getLabel() {
            return this.label;
        }

        public LabelType getLabelType() {
            return this.labelType;
        }

        public int size() {
            return this.records == null ? 0 : this.records.size();
        }

        @Override
        public Iterator<Record<V>> iterator() {
            if (this.records == null) {
                return Collections.emptyList().iterator();
            }
            return this.records.iterator();
        }

        public String toString() {
            return "{labelType=" + (Object)((Object)this.labelType) + ", lowerBoundary=" + this.lowerBoundary + ", label=" + this.label + "}";
        }

        static String access$800(Bucket bucket) {
            return bucket.lowerBoundary;
        }

        static Bucket access$900(Bucket bucket) {
            return bucket.displayBucket;
        }

        static List access$1000(Bucket bucket) {
            return bucket.records;
        }

        static List access$1002(Bucket bucket, List list) {
            bucket.records = list;
            return bucket.records;
        }

        Bucket(String string, String string2, LabelType labelType, 1 var4_4) {
            this(string, string2, labelType);
        }

        static LabelType access$1200(Bucket bucket) {
            return bucket.labelType;
        }

        static Bucket access$902(Bucket bucket, Bucket bucket2) {
            bucket.displayBucket = bucket2;
            return bucket.displayBucket;
        }

        static int access$1402(Bucket bucket, int n) {
            bucket.displayIndex = n;
            return bucket.displayIndex;
        }

        static int access$1400(Bucket bucket) {
            return bucket.displayIndex;
        }

        public static enum LabelType {
            NORMAL,
            UNDERFLOW,
            INFLOW,
            OVERFLOW;

        }
    }

    public static class Record<V> {
        private final CharSequence name;
        private final V data;

        private Record(CharSequence charSequence, V v) {
            this.name = charSequence;
            this.data = v;
        }

        public CharSequence getName() {
            return this.name;
        }

        public V getData() {
            return this.data;
        }

        public String toString() {
            return this.name + "=" + this.data;
        }

        static CharSequence access$000(Record record) {
            return record.name;
        }

        Record(CharSequence charSequence, Object object, 1 var3_3) {
            this(charSequence, object);
        }
    }

    public static final class ImmutableIndex<V>
    implements Iterable<Bucket<V>> {
        private final BucketList<V> buckets;
        private final Collator collatorPrimaryOnly;

        private ImmutableIndex(BucketList<V> bucketList, Collator collator) {
            this.buckets = bucketList;
            this.collatorPrimaryOnly = collator;
        }

        public int getBucketCount() {
            return BucketList.access$200(this.buckets);
        }

        public int getBucketIndex(CharSequence charSequence) {
            return BucketList.access$300(this.buckets, charSequence, this.collatorPrimaryOnly);
        }

        public Bucket<V> getBucket(int n) {
            if (0 <= n && n < BucketList.access$200(this.buckets)) {
                return (Bucket)BucketList.access$400(this.buckets).get(n);
            }
            return null;
        }

        @Override
        public Iterator<Bucket<V>> iterator() {
            return this.buckets.iterator();
        }

        ImmutableIndex(BucketList bucketList, Collator collator, 1 var3_3) {
            this(bucketList, collator);
        }
    }
}

