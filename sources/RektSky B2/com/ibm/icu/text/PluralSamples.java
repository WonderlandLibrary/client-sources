package com.ibm.icu.text;

import java.util.*;
import com.ibm.icu.util.*;

@Deprecated
public class PluralSamples
{
    private PluralRules pluralRules;
    private final Map<String, List<Double>> _keySamplesMap;
    @Deprecated
    public final Map<String, Boolean> _keyLimitedMap;
    private final Map<String, Set<PluralRules.FixedDecimal>> _keyFractionSamplesMap;
    private final Set<PluralRules.FixedDecimal> _fractionSamples;
    private static final int[] TENS;
    private static final int LIMIT_FRACTION_SAMPLES = 3;
    
    @Deprecated
    public PluralSamples(final PluralRules pluralRules) {
        this.pluralRules = pluralRules;
        final Set<String> keywords = pluralRules.getKeywords();
        final int MAX_SAMPLES = 3;
        final Map<String, Boolean> temp = new HashMap<String, Boolean>();
        for (final String k : keywords) {
            temp.put(k, pluralRules.isLimited(k));
        }
        this._keyLimitedMap = temp;
        final Map<String, List<Double>> sampleMap = new HashMap<String, List<Double>>();
        int keywordsRemaining = keywords.size();
        for (int limit = 128, i = 0; keywordsRemaining > 0 && i < limit; keywordsRemaining = this.addSimpleSamples(pluralRules, 3, sampleMap, keywordsRemaining, i / 2.0), ++i) {}
        keywordsRemaining = this.addSimpleSamples(pluralRules, 3, sampleMap, keywordsRemaining, 1000000.0);
        final Map<String, Set<PluralRules.FixedDecimal>> sampleFractionMap = new HashMap<String, Set<PluralRules.FixedDecimal>>();
        final Set<PluralRules.FixedDecimal> mentioned = new TreeSet<PluralRules.FixedDecimal>();
        final Map<String, Set<PluralRules.FixedDecimal>> foundKeywords = new HashMap<String, Set<PluralRules.FixedDecimal>>();
        for (final PluralRules.FixedDecimal s : mentioned) {
            final String keyword = pluralRules.select(s);
            this.addRelation(foundKeywords, keyword, s);
        }
        Label_0378: {
            if (foundKeywords.size() != keywords.size()) {
                for (int j = 1; j < 1000; ++j) {
                    final boolean done = this.addIfNotPresent(j, mentioned, foundKeywords);
                    if (done) {
                        break Label_0378;
                    }
                }
                for (int j = 10; j < 1000; ++j) {
                    final boolean done = this.addIfNotPresent(j / 10.0, mentioned, foundKeywords);
                    if (done) {
                        break Label_0378;
                    }
                }
                System.out.println("Failed to find sample for each keyword: " + foundKeywords + "\n\t" + pluralRules + "\n\t" + mentioned);
            }
        }
        mentioned.add(new PluralRules.FixedDecimal(0L));
        mentioned.add(new PluralRules.FixedDecimal(1L));
        mentioned.add(new PluralRules.FixedDecimal(2L));
        mentioned.add(new PluralRules.FixedDecimal(0.1, 1));
        mentioned.add(new PluralRules.FixedDecimal(1.99, 2));
        mentioned.addAll(this.fractions(mentioned));
        for (final PluralRules.FixedDecimal s : mentioned) {
            final String keyword = pluralRules.select(s);
            Set<PluralRules.FixedDecimal> list = sampleFractionMap.get(keyword);
            if (list == null) {
                list = new LinkedHashSet<PluralRules.FixedDecimal>();
                sampleFractionMap.put(keyword, list);
            }
            list.add(s);
        }
        if (keywordsRemaining > 0) {
            for (final String l : keywords) {
                if (!sampleMap.containsKey(l)) {
                    sampleMap.put(l, Collections.emptyList());
                }
                if (!sampleFractionMap.containsKey(l)) {
                    sampleFractionMap.put(l, Collections.emptySet());
                }
            }
        }
        for (final Map.Entry<String, List<Double>> entry : sampleMap.entrySet()) {
            sampleMap.put(entry.getKey(), Collections.unmodifiableList((List<? extends Double>)entry.getValue()));
        }
        for (final Map.Entry<String, Set<PluralRules.FixedDecimal>> entry2 : sampleFractionMap.entrySet()) {
            sampleFractionMap.put(entry2.getKey(), Collections.unmodifiableSet((Set<? extends PluralRules.FixedDecimal>)entry2.getValue()));
        }
        this._keySamplesMap = sampleMap;
        this._keyFractionSamplesMap = sampleFractionMap;
        this._fractionSamples = Collections.unmodifiableSet((Set<? extends PluralRules.FixedDecimal>)mentioned);
    }
    
    private int addSimpleSamples(final PluralRules pluralRules, final int MAX_SAMPLES, final Map<String, List<Double>> sampleMap, int keywordsRemaining, final double val) {
        final String keyword = pluralRules.select(val);
        final boolean keyIsLimited = this._keyLimitedMap.get(keyword);
        List<Double> list = sampleMap.get(keyword);
        if (list == null) {
            list = new ArrayList<Double>(MAX_SAMPLES);
            sampleMap.put(keyword, list);
        }
        else if (!keyIsLimited && list.size() == MAX_SAMPLES) {
            return keywordsRemaining;
        }
        list.add(val);
        if (!keyIsLimited && list.size() == MAX_SAMPLES) {
            --keywordsRemaining;
        }
        return keywordsRemaining;
    }
    
    private void addRelation(final Map<String, Set<PluralRules.FixedDecimal>> foundKeywords, final String keyword, final PluralRules.FixedDecimal s) {
        Set<PluralRules.FixedDecimal> set = foundKeywords.get(keyword);
        if (set == null) {
            foundKeywords.put(keyword, set = new HashSet<PluralRules.FixedDecimal>());
        }
        set.add(s);
    }
    
    private boolean addIfNotPresent(final double d, final Set<PluralRules.FixedDecimal> mentioned, final Map<String, Set<PluralRules.FixedDecimal>> foundKeywords) {
        final PluralRules.FixedDecimal numberInfo = new PluralRules.FixedDecimal(d);
        final String keyword = this.pluralRules.select(numberInfo);
        if (!foundKeywords.containsKey(keyword) || keyword.equals("other")) {
            this.addRelation(foundKeywords, keyword, numberInfo);
            mentioned.add(numberInfo);
            if (keyword.equals("other") && foundKeywords.get("other").size() > 1) {
                return true;
            }
        }
        return false;
    }
    
    private Set<PluralRules.FixedDecimal> fractions(final Set<PluralRules.FixedDecimal> original) {
        final Set<PluralRules.FixedDecimal> toAddTo = new HashSet<PluralRules.FixedDecimal>();
        final Set<Integer> result = new HashSet<Integer>();
        for (final PluralRules.FixedDecimal base1 : original) {
            result.add((int)base1.integerValue);
        }
        final List<Integer> ints = new ArrayList<Integer>(result);
        final Set<String> keywords = new HashSet<String>();
        for (int j = 0; j < ints.size(); ++j) {
            final Integer base2 = ints.get(j);
            final String keyword = this.pluralRules.select(base2);
            if (!keywords.contains(keyword)) {
                keywords.add(keyword);
                toAddTo.add(new PluralRules.FixedDecimal(base2, 1));
                toAddTo.add(new PluralRules.FixedDecimal(base2, 2));
                final Integer fract = this.getDifferentCategory(ints, keyword);
                if (fract >= PluralSamples.TENS[2]) {
                    toAddTo.add(new PluralRules.FixedDecimal(base2 + "." + fract));
                }
                else {
                    for (int visibleFractions = 1; visibleFractions < 3; ++visibleFractions) {
                        for (int i = 1; i <= visibleFractions; ++i) {
                            if (fract < PluralSamples.TENS[i]) {
                                toAddTo.add(new PluralRules.FixedDecimal(base2 + fract / (double)PluralSamples.TENS[i], visibleFractions));
                            }
                        }
                    }
                }
            }
        }
        return toAddTo;
    }
    
    private Integer getDifferentCategory(final List<Integer> ints, final String keyword) {
        for (int i = ints.size() - 1; i >= 0; --i) {
            final Integer other = ints.get(i);
            final String keywordOther = this.pluralRules.select(other);
            if (!keywordOther.equals(keyword)) {
                return other;
            }
        }
        return 37;
    }
    
    @Deprecated
    public PluralRules.KeywordStatus getStatus(final String keyword, final int offset, Set<Double> explicits, final Output<Double> uniqueValue) {
        if (uniqueValue != null) {
            uniqueValue.value = null;
        }
        if (!this.pluralRules.getKeywords().contains(keyword)) {
            return PluralRules.KeywordStatus.INVALID;
        }
        final Collection<Double> values = this.pluralRules.getAllKeywordValues(keyword);
        if (values == null) {
            return PluralRules.KeywordStatus.UNBOUNDED;
        }
        final int originalSize = values.size();
        if (explicits == null) {
            explicits = Collections.emptySet();
        }
        if (originalSize > explicits.size()) {
            if (originalSize == 1) {
                if (uniqueValue != null) {
                    uniqueValue.value = values.iterator().next();
                }
                return PluralRules.KeywordStatus.UNIQUE;
            }
            return PluralRules.KeywordStatus.BOUNDED;
        }
        else {
            final HashSet<Double> subtractedSet = new HashSet<Double>(values);
            for (final Double explicit : explicits) {
                subtractedSet.remove(explicit - offset);
            }
            if (subtractedSet.size() == 0) {
                return PluralRules.KeywordStatus.SUPPRESSED;
            }
            if (uniqueValue != null && subtractedSet.size() == 1) {
                uniqueValue.value = subtractedSet.iterator().next();
            }
            return (originalSize == 1) ? PluralRules.KeywordStatus.UNIQUE : PluralRules.KeywordStatus.BOUNDED;
        }
    }
    
    Map<String, List<Double>> getKeySamplesMap() {
        return this._keySamplesMap;
    }
    
    Map<String, Set<PluralRules.FixedDecimal>> getKeyFractionSamplesMap() {
        return this._keyFractionSamplesMap;
    }
    
    Set<PluralRules.FixedDecimal> getFractionSamples() {
        return this._fractionSamples;
    }
    
    Collection<Double> getAllKeywordValues(final String keyword) {
        if (!this.pluralRules.getKeywords().contains(keyword)) {
            return (Collection<Double>)Collections.emptyList();
        }
        final Collection<Double> result = this.getKeySamplesMap().get(keyword);
        if (result.size() > 2 && !this._keyLimitedMap.get(keyword)) {
            return null;
        }
        return result;
    }
    
    static {
        TENS = new int[] { 1, 10, 100, 1000, 10000, 100000, 1000000 };
    }
}
