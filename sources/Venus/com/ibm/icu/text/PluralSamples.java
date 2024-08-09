/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.Output;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Deprecated
public class PluralSamples {
    private PluralRules pluralRules;
    private final Map<String, List<Double>> _keySamplesMap;
    @Deprecated
    public final Map<String, Boolean> _keyLimitedMap;
    private final Map<String, Set<PluralRules.FixedDecimal>> _keyFractionSamplesMap;
    private final Set<PluralRules.FixedDecimal> _fractionSamples;
    private static final int[] TENS = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000};
    private static final int LIMIT_FRACTION_SAMPLES = 3;

    @Deprecated
    public PluralSamples(PluralRules pluralRules) {
        String string;
        TreeSet<PluralRules.FixedDecimal> treeSet;
        HashMap<String, Set<PluralRules.FixedDecimal>> hashMap;
        int n;
        HashMap hashMap2;
        Set<String> set;
        block15: {
            this.pluralRules = pluralRules;
            set = pluralRules.getKeywords();
            int n2 = 3;
            HashMap<String, Boolean> hashMap3 = new HashMap<String, Boolean>();
            for (String string2 : set) {
                hashMap3.put(string2, pluralRules.isLimited(string2));
            }
            this._keyLimitedMap = hashMap3;
            hashMap2 = new HashMap();
            n = set.size();
            int n3 = 128;
            for (int i = 0; n > 0 && i < n3; ++i) {
                n = this.addSimpleSamples(pluralRules, 3, hashMap2, n, (double)i / 2.0);
            }
            n = this.addSimpleSamples(pluralRules, 3, hashMap2, n, 1000000.0);
            hashMap = new HashMap<String, Set<PluralRules.FixedDecimal>>();
            treeSet = new TreeSet<PluralRules.FixedDecimal>();
            HashMap<String, Set<PluralRules.FixedDecimal>> hashMap4 = new HashMap<String, Set<PluralRules.FixedDecimal>>();
            for (PluralRules.FixedDecimal fixedDecimal : treeSet) {
                string = pluralRules.select(fixedDecimal);
                this.addRelation(hashMap4, string, fixedDecimal);
            }
            if (hashMap4.size() != set.size()) {
                int n4;
                for (n4 = 1; n4 < 1000; ++n4) {
                    boolean bl = this.addIfNotPresent(n4, treeSet, hashMap4);
                    if (!bl) {
                        continue;
                    }
                    break block15;
                }
                for (n4 = 10; n4 < 1000; ++n4) {
                    boolean bl = this.addIfNotPresent((double)n4 / 10.0, treeSet, hashMap4);
                    if (!bl) {
                        continue;
                    }
                    break block15;
                }
                System.out.println("Failed to find sample for each keyword: " + hashMap4 + "\n\t" + pluralRules + "\n\t" + treeSet);
            }
        }
        treeSet.add(new PluralRules.FixedDecimal(0L));
        treeSet.add(new PluralRules.FixedDecimal(1L));
        treeSet.add(new PluralRules.FixedDecimal(2L));
        treeSet.add(new PluralRules.FixedDecimal(0.1, 1));
        treeSet.add(new PluralRules.FixedDecimal(1.99, 2));
        treeSet.addAll(this.fractions(treeSet));
        for (PluralRules.FixedDecimal fixedDecimal : treeSet) {
            string = pluralRules.select(fixedDecimal);
            LinkedHashSet<PluralRules.FixedDecimal> linkedHashSet = (LinkedHashSet<PluralRules.FixedDecimal>)hashMap.get(string);
            if (linkedHashSet == null) {
                linkedHashSet = new LinkedHashSet<PluralRules.FixedDecimal>();
                hashMap.put(string, linkedHashSet);
            }
            linkedHashSet.add(fixedDecimal);
        }
        if (n > 0) {
            for (String string3 : set) {
                if (!hashMap2.containsKey(string3)) {
                    hashMap2.put(string3, Collections.emptyList());
                }
                if (hashMap.containsKey(string3)) continue;
                hashMap.put(string3, Collections.emptySet());
            }
        }
        for (Map.Entry entry : hashMap2.entrySet()) {
            hashMap2.put(entry.getKey(), Collections.unmodifiableList((List)entry.getValue()));
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            hashMap.put((String)entry.getKey(), Collections.unmodifiableSet((Set)entry.getValue()));
        }
        this._keySamplesMap = hashMap2;
        this._keyFractionSamplesMap = hashMap;
        this._fractionSamples = Collections.unmodifiableSet(treeSet);
    }

    private int addSimpleSamples(PluralRules pluralRules, int n, Map<String, List<Double>> map, int n2, double d) {
        String string = pluralRules.select(d);
        boolean bl = this._keyLimitedMap.get(string);
        List<Double> list = map.get(string);
        if (list == null) {
            list = new ArrayList<Double>(n);
            map.put(string, list);
        } else if (!bl && list.size() == n) {
            return n2;
        }
        list.add(d);
        if (!bl && list.size() == n) {
            --n2;
        }
        return n2;
    }

    private void addRelation(Map<String, Set<PluralRules.FixedDecimal>> map, String string, PluralRules.FixedDecimal fixedDecimal) {
        Set<PluralRules.FixedDecimal> set = map.get(string);
        if (set == null) {
            set = new HashSet<PluralRules.FixedDecimal>();
            map.put(string, set);
        }
        set.add(fixedDecimal);
    }

    private boolean addIfNotPresent(double d, Set<PluralRules.FixedDecimal> set, Map<String, Set<PluralRules.FixedDecimal>> map) {
        PluralRules.FixedDecimal fixedDecimal = new PluralRules.FixedDecimal(d);
        String string = this.pluralRules.select(fixedDecimal);
        if (!map.containsKey(string) || string.equals("other")) {
            this.addRelation(map, string, fixedDecimal);
            set.add(fixedDecimal);
            if (string.equals("other") && map.get("other").size() > 1) {
                return false;
            }
        }
        return true;
    }

    private Set<PluralRules.FixedDecimal> fractions(Set<PluralRules.FixedDecimal> set) {
        HashSet<PluralRules.FixedDecimal> hashSet = new HashSet<PluralRules.FixedDecimal>();
        HashSet<Integer> hashSet2 = new HashSet<Integer>();
        for (PluralRules.FixedDecimal serializable2 : set) {
            hashSet2.add((int)serializable2.integerValue);
        }
        ArrayList arrayList = new ArrayList(hashSet2);
        HashSet<String> hashSet3 = new HashSet<String>();
        for (int i = 0; i < arrayList.size(); ++i) {
            Integer n = (Integer)arrayList.get(i);
            String string = this.pluralRules.select(n.intValue());
            if (hashSet3.contains(string)) continue;
            hashSet3.add(string);
            hashSet.add(new PluralRules.FixedDecimal(n.intValue(), 1));
            hashSet.add(new PluralRules.FixedDecimal(n.intValue(), 2));
            Integer n2 = this.getDifferentCategory(arrayList, string);
            if (n2 >= TENS[2]) {
                hashSet.add(new PluralRules.FixedDecimal(n + "." + n2));
                continue;
            }
            for (int j = 1; j < 3; ++j) {
                for (int k = 1; k <= j; ++k) {
                    if (n2 >= TENS[k]) continue;
                    hashSet.add(new PluralRules.FixedDecimal((double)n.intValue() + (double)n2.intValue() / (double)TENS[k], j));
                }
            }
        }
        return hashSet;
    }

    private Integer getDifferentCategory(List<Integer> list, String string) {
        for (int i = list.size() - 1; i >= 0; --i) {
            Integer n = list.get(i);
            String string2 = this.pluralRules.select(n.intValue());
            if (string2.equals(string)) continue;
            return n;
        }
        return 37;
    }

    @Deprecated
    public PluralRules.KeywordStatus getStatus(String string, int n, Set<Double> set, Output<Double> output) {
        if (output != null) {
            output.value = null;
        }
        if (!this.pluralRules.getKeywords().contains(string)) {
            return PluralRules.KeywordStatus.INVALID;
        }
        Collection<Double> collection = this.pluralRules.getAllKeywordValues(string);
        if (collection == null) {
            return PluralRules.KeywordStatus.UNBOUNDED;
        }
        int n2 = collection.size();
        if (set == null) {
            set = Collections.emptySet();
        }
        if (n2 > set.size()) {
            if (n2 == 1) {
                if (output != null) {
                    output.value = collection.iterator().next();
                }
                return PluralRules.KeywordStatus.UNIQUE;
            }
            return PluralRules.KeywordStatus.BOUNDED;
        }
        HashSet<Double> hashSet = new HashSet<Double>(collection);
        for (Double d : set) {
            hashSet.remove(d - (double)n);
        }
        if (hashSet.size() == 0) {
            return PluralRules.KeywordStatus.SUPPRESSED;
        }
        if (output != null && hashSet.size() == 1) {
            output.value = hashSet.iterator().next();
        }
        return n2 == 1 ? PluralRules.KeywordStatus.UNIQUE : PluralRules.KeywordStatus.BOUNDED;
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

    Collection<Double> getAllKeywordValues(String string) {
        if (!this.pluralRules.getKeywords().contains(string)) {
            return Collections.emptyList();
        }
        Collection collection = this.getKeySamplesMap().get(string);
        if (collection.size() > 2 && !this._keyLimitedMap.get(string).booleanValue()) {
            return null;
        }
        return collection;
    }
}

