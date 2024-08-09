/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.ULocale;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalePriorityList
implements Iterable<ULocale> {
    private static final Double D1 = 1.0;
    private static final Pattern languageSplitter = Pattern.compile("\\s*,\\s*");
    private static final Pattern weightSplitter = Pattern.compile("\\s*(\\S*)\\s*;\\s*q\\s*=\\s*(\\S*)");
    private final Map<ULocale, Double> languagesAndWeights;
    private static Comparator<Double> myDescendingDouble = new Comparator<Double>(){

        @Override
        public int compare(Double d, Double d2) {
            int n = d.compareTo(d2);
            return n > 0 ? -1 : (n < 0 ? 1 : 0);
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((Double)object, (Double)object2);
        }
    };

    public static Builder add(ULocale ... uLocaleArray) {
        return new Builder(null).add(uLocaleArray);
    }

    public static Builder add(ULocale uLocale, double d) {
        return new Builder(null).add(uLocale, d);
    }

    public static Builder add(LocalePriorityList localePriorityList) {
        return new Builder(localePriorityList, null);
    }

    public static Builder add(String string) {
        return new Builder(null).add(string);
    }

    public Double getWeight(ULocale uLocale) {
        return this.languagesAndWeights.get(uLocale);
    }

    public Set<ULocale> getULocales() {
        return this.languagesAndWeights.keySet();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<ULocale, Double> entry : this.languagesAndWeights.entrySet()) {
            ULocale uLocale = entry.getKey();
            double d = entry.getValue();
            if (stringBuilder.length() != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(uLocale);
            if (d == 1.0) continue;
            stringBuilder.append(";q=").append(d);
        }
        return stringBuilder.toString();
    }

    @Override
    public Iterator<ULocale> iterator() {
        return this.languagesAndWeights.keySet().iterator();
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (this == object) {
            return false;
        }
        try {
            LocalePriorityList localePriorityList = (LocalePriorityList)object;
            return this.languagesAndWeights.equals(localePriorityList.languagesAndWeights);
        } catch (RuntimeException runtimeException) {
            return true;
        }
    }

    public int hashCode() {
        return this.languagesAndWeights.hashCode();
    }

    private LocalePriorityList(Map<ULocale, Double> map) {
        this.languagesAndWeights = map;
    }

    static Map access$200(LocalePriorityList localePriorityList) {
        return localePriorityList.languagesAndWeights;
    }

    static Comparator access$300() {
        return myDescendingDouble;
    }

    static Double access$400() {
        return D1;
    }

    LocalePriorityList(Map map, 1 var2_2) {
        this(map);
    }

    static Pattern access$600() {
        return languageSplitter;
    }

    static Pattern access$700() {
        return weightSplitter;
    }

    public static class Builder {
        private Map<ULocale, Double> languageToWeight;
        private LocalePriorityList built;
        private boolean hasWeights = false;
        static final boolean $assertionsDisabled = !LocalePriorityList.class.desiredAssertionStatus();

        private Builder() {
            this.languageToWeight = new LinkedHashMap<ULocale, Double>();
        }

        private Builder(LocalePriorityList localePriorityList) {
            this.built = localePriorityList;
            for (Double d : LocalePriorityList.access$200(localePriorityList).values()) {
                double d2 = d;
                if (!($assertionsDisabled || 0.0 < d2 && d2 <= 1.0)) {
                    throw new AssertionError();
                }
                if (d2 == 1.0) continue;
                this.hasWeights = true;
                break;
            }
        }

        public LocalePriorityList build() {
            return this.build(true);
        }

        public LocalePriorityList build(boolean bl) {
            Map<ULocale, Double> map;
            if (this.built != null) {
                return this.built;
            }
            if (this.hasWeights) {
                Comparable<ULocale> comparable;
                TreeMap<Double, LinkedList<ULocale>> treeMap = new TreeMap<Double, LinkedList<ULocale>>(LocalePriorityList.access$300());
                for (Map.Entry<ULocale, Double> entry : this.languageToWeight.entrySet()) {
                    comparable = entry.getKey();
                    Double d = entry.getValue();
                    Serializable serializable = (LinkedList<ULocale>)treeMap.get(d);
                    if (serializable == null) {
                        serializable = new LinkedList<ULocale>();
                        treeMap.put(d, (LinkedList<ULocale>)serializable);
                    }
                    serializable.add(comparable);
                }
                if (treeMap.size() <= 1) {
                    map = this.languageToWeight;
                    if (treeMap.isEmpty() || (Double)treeMap.firstKey() == 1.0) {
                        this.hasWeights = false;
                    }
                } else {
                    map = new LinkedHashMap<ULocale, Double>();
                    for (Map.Entry<ULocale, Double> entry : treeMap.entrySet()) {
                        comparable = bl ? (Double)((Object)entry.getKey()) : LocalePriorityList.access$400();
                        for (Serializable serializable : (List)((Object)entry.getValue())) {
                            map.put((ULocale)serializable, (Double)comparable);
                        }
                    }
                }
            } else {
                map = this.languageToWeight;
            }
            this.languageToWeight = null;
            this.built = new LocalePriorityList(Collections.unmodifiableMap(map), null);
            return this.built;
        }

        public Builder add(LocalePriorityList localePriorityList) {
            for (Map.Entry entry : LocalePriorityList.access$200(localePriorityList).entrySet()) {
                this.add((ULocale)entry.getKey(), (Double)entry.getValue());
            }
            return this;
        }

        public Builder add(ULocale uLocale) {
            return this.add(uLocale, 1.0);
        }

        public Builder add(ULocale ... uLocaleArray) {
            for (ULocale uLocale : uLocaleArray) {
                this.add(uLocale, 1.0);
            }
            return this;
        }

        public Builder add(ULocale uLocale, double d) {
            Double d2;
            if (this.languageToWeight == null) {
                this.languageToWeight = new LinkedHashMap<ULocale, Double>(LocalePriorityList.access$200(this.built));
                this.built = null;
            }
            if (this.languageToWeight.containsKey(uLocale)) {
                this.languageToWeight.remove(uLocale);
            }
            if (d <= 0.0) {
                return this;
            }
            if (d >= 1.0) {
                d2 = LocalePriorityList.access$400();
            } else {
                d2 = d;
                this.hasWeights = true;
            }
            this.languageToWeight.put(uLocale, d2);
            return this;
        }

        public Builder add(String string) {
            String[] stringArray = LocalePriorityList.access$600().split(string.trim());
            Matcher matcher = LocalePriorityList.access$700().matcher("");
            for (String string2 : stringArray) {
                if (matcher.reset(string2).matches()) {
                    ULocale uLocale = new ULocale(matcher.group(1));
                    double d = Double.parseDouble(matcher.group(2));
                    if (!(0.0 <= d) || !(d <= 1.0)) {
                        throw new IllegalArgumentException("Illegal weight, must be 0..1: " + d);
                    }
                    this.add(uLocale, d);
                    continue;
                }
                if (string2.length() == 0) continue;
                this.add(new ULocale(string2));
            }
            return this;
        }

        Builder(1 var1_1) {
            this();
        }

        Builder(LocalePriorityList localePriorityList, 1 var2_2) {
            this(localePriorityList);
        }
    }
}

