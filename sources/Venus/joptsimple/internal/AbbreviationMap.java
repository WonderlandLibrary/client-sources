/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple.internal;

import java.util.Map;
import java.util.TreeMap;
import joptsimple.internal.OptionNameMap;

public class AbbreviationMap<V>
implements OptionNameMap<V> {
    private final Map<Character, AbbreviationMap<V>> children = new TreeMap<Character, AbbreviationMap<V>>();
    private String key;
    private V value;
    private int keysBeyond;

    @Override
    public boolean contains(String string) {
        return this.get(string) != null;
    }

    @Override
    public V get(String string) {
        char[] cArray = AbbreviationMap.charsOf(string);
        AbbreviationMap<V> abbreviationMap = this;
        for (char c : cArray) {
            abbreviationMap = abbreviationMap.children.get(Character.valueOf(c));
            if (abbreviationMap != null) continue;
            return null;
        }
        return abbreviationMap.value;
    }

    @Override
    public void put(String string, V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        if (string.length() == 0) {
            throw new IllegalArgumentException();
        }
        char[] cArray = AbbreviationMap.charsOf(string);
        this.add(cArray, v, 0, cArray.length);
    }

    @Override
    public void putAll(Iterable<String> iterable, V v) {
        for (String string : iterable) {
            this.put(string, v);
        }
    }

    private boolean add(char[] cArray, V v, int n, int n2) {
        boolean bl;
        if (n == n2) {
            this.value = v;
            boolean bl2 = this.key != null;
            this.key = new String(cArray);
            return !bl2;
        }
        char c = cArray[n];
        AbbreviationMap<V> abbreviationMap = this.children.get(Character.valueOf(c));
        if (abbreviationMap == null) {
            abbreviationMap = new AbbreviationMap<V>();
            this.children.put(Character.valueOf(c), abbreviationMap);
        }
        if (bl = super.add(cArray, v, n + 1, n2)) {
            ++this.keysBeyond;
        }
        if (this.key == null) {
            this.value = this.keysBeyond > 1 ? null : v;
        }
        return bl;
    }

    @Override
    public void remove(String string) {
        if (string.length() == 0) {
            throw new IllegalArgumentException();
        }
        char[] cArray = AbbreviationMap.charsOf(string);
        this.remove(cArray, 0, cArray.length);
    }

    private boolean remove(char[] cArray, int n, int n2) {
        if (n == n2) {
            return this.removeAtEndOfKey();
        }
        char c = cArray[n];
        AbbreviationMap<V> abbreviationMap = this.children.get(Character.valueOf(c));
        if (abbreviationMap == null || !super.remove(cArray, n + 1, n2)) {
            return true;
        }
        --this.keysBeyond;
        if (abbreviationMap.keysBeyond == 0) {
            this.children.remove(Character.valueOf(c));
        }
        if (this.keysBeyond == 1 && this.key == null) {
            this.setValueToThatOfOnlyChild();
        }
        return false;
    }

    private void setValueToThatOfOnlyChild() {
        Map.Entry<Character, AbbreviationMap<V>> entry = this.children.entrySet().iterator().next();
        AbbreviationMap<V> abbreviationMap = entry.getValue();
        this.value = abbreviationMap.value;
    }

    private boolean removeAtEndOfKey() {
        if (this.key == null) {
            return true;
        }
        this.key = null;
        if (this.keysBeyond == 1) {
            this.setValueToThatOfOnlyChild();
        } else {
            this.value = null;
        }
        return false;
    }

    @Override
    public Map<String, V> toJavaUtilMap() {
        TreeMap treeMap = new TreeMap();
        this.addToMappings(treeMap);
        return treeMap;
    }

    private void addToMappings(Map<String, V> map) {
        if (this.key != null) {
            map.put(this.key, this.value);
        }
        for (AbbreviationMap<V> abbreviationMap : this.children.values()) {
            super.addToMappings(map);
        }
    }

    private static char[] charsOf(String string) {
        char[] cArray = new char[string.length()];
        string.getChars(0, string.length(), cArray, 0);
        return cArray;
    }
}

