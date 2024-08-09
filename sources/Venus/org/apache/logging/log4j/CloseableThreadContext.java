/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.ThreadContext;

public class CloseableThreadContext {
    private CloseableThreadContext() {
    }

    public static Instance push(String string) {
        return new Instance(null).push(string);
    }

    public static Instance push(String string, Object ... objectArray) {
        return new Instance(null).push(string, objectArray);
    }

    public static Instance put(String string, String string2) {
        return new Instance(null).put(string, string2);
    }

    public static Instance pushAll(List<String> list) {
        return new Instance(null).pushAll(list);
    }

    public static Instance putAll(Map<String, String> map) {
        return new Instance(null).putAll(map);
    }

    static class 1 {
    }

    public static class Instance
    implements AutoCloseable {
        private int pushCount = 0;
        private final Map<String, String> originalValues = new HashMap<String, String>();

        private Instance() {
        }

        public Instance push(String string) {
            ThreadContext.push(string);
            ++this.pushCount;
            return this;
        }

        public Instance push(String string, Object[] objectArray) {
            ThreadContext.push(string, objectArray);
            ++this.pushCount;
            return this;
        }

        public Instance put(String string, String string2) {
            if (!this.originalValues.containsKey(string)) {
                this.originalValues.put(string, ThreadContext.get(string));
            }
            ThreadContext.put(string, string2);
            return this;
        }

        public Instance putAll(Map<String, String> map) {
            Map<String, String> map2 = ThreadContext.getContext();
            ThreadContext.putAll(map);
            for (String string : map.keySet()) {
                if (this.originalValues.containsKey(string)) continue;
                this.originalValues.put(string, map2.get(string));
            }
            return this;
        }

        public Instance pushAll(List<String> list) {
            for (String string : list) {
                this.push(string);
            }
            return this;
        }

        @Override
        public void close() {
            this.closeStack();
            this.closeMap();
        }

        private void closeMap() {
            Iterator<Map.Entry<String, String>> iterator2 = this.originalValues.entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry<String, String> entry = iterator2.next();
                String string = entry.getKey();
                String string2 = entry.getValue();
                if (null == string2) {
                    ThreadContext.remove(string);
                } else {
                    ThreadContext.put(string, string2);
                }
                iterator2.remove();
            }
        }

        private void closeStack() {
            for (int i = 0; i < this.pushCount; ++i) {
                ThreadContext.pop();
            }
            this.pushCount = 0;
        }

        Instance(1 var1_1) {
            this();
        }
    }
}

