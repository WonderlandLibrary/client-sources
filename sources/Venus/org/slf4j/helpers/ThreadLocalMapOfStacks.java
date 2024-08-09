/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.helpers;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class ThreadLocalMapOfStacks {
    final ThreadLocal<Map<String, Deque<String>>> tlMapOfStacks = new ThreadLocal();

    public void pushByKey(String string, String string2) {
        Deque<String> deque;
        if (string == null) {
            return;
        }
        Map<String, Deque<String>> map = this.tlMapOfStacks.get();
        if (map == null) {
            map = new HashMap<String, Deque<String>>();
            this.tlMapOfStacks.set(map);
        }
        if ((deque = map.get(string)) == null) {
            deque = new ArrayDeque<String>();
        }
        deque.push(string2);
        map.put(string, deque);
    }

    public String popByKey(String string) {
        if (string == null) {
            return null;
        }
        Map<String, Deque<String>> map = this.tlMapOfStacks.get();
        if (map == null) {
            return null;
        }
        Deque<String> deque = map.get(string);
        if (deque == null) {
            return null;
        }
        return deque.pop();
    }

    public Deque<String> getCopyOfDequeByKey(String string) {
        if (string == null) {
            return null;
        }
        Map<String, Deque<String>> map = this.tlMapOfStacks.get();
        if (map == null) {
            return null;
        }
        Deque<String> deque = map.get(string);
        if (deque == null) {
            return null;
        }
        return new ArrayDeque<String>(deque);
    }

    public void clearDequeByKey(String string) {
        if (string == null) {
            return;
        }
        Map<String, Deque<String>> map = this.tlMapOfStacks.get();
        if (map == null) {
            return;
        }
        Deque<String> deque = map.get(string);
        if (deque == null) {
            return;
        }
        deque.clear();
    }
}

