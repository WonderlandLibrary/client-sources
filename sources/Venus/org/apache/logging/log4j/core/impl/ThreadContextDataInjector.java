/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.ContextDataInjector;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.impl.ContextDataFactory;
import org.apache.logging.log4j.core.impl.JdkMapAdapterStringMap;
import org.apache.logging.log4j.spi.ReadOnlyThreadContextMap;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.StringMap;

public class ThreadContextDataInjector {
    public static void copyProperties(List<Property> list, StringMap stringMap) {
        if (list != null) {
            for (int i = 0; i < list.size(); ++i) {
                Property property = list.get(i);
                stringMap.putValue(property.getName(), property.getValue());
            }
        }
    }

    public static class ForCopyOnWriteThreadContextMap
    implements ContextDataInjector {
        @Override
        public StringMap injectContextData(List<Property> list, StringMap stringMap) {
            StringMap stringMap2 = ThreadContext.getThreadContextMap().getReadOnlyContextData();
            if (list == null || list.isEmpty()) {
                return stringMap2;
            }
            StringMap stringMap3 = ContextDataFactory.createContextData(list.size() + stringMap2.size());
            ThreadContextDataInjector.copyProperties(list, stringMap3);
            stringMap3.putAll(stringMap2);
            return stringMap3;
        }

        @Override
        public ReadOnlyStringMap rawContextData() {
            return ThreadContext.getThreadContextMap().getReadOnlyContextData();
        }
    }

    public static class ForGarbageFreeThreadContextMap
    implements ContextDataInjector {
        @Override
        public StringMap injectContextData(List<Property> list, StringMap stringMap) {
            ThreadContextDataInjector.copyProperties(list, stringMap);
            StringMap stringMap2 = ThreadContext.getThreadContextMap().getReadOnlyContextData();
            stringMap.putAll(stringMap2);
            return stringMap;
        }

        @Override
        public ReadOnlyStringMap rawContextData() {
            return ThreadContext.getThreadContextMap().getReadOnlyContextData();
        }
    }

    public static class ForDefaultThreadContextMap
    implements ContextDataInjector {
        @Override
        public StringMap injectContextData(List<Property> list, StringMap stringMap) {
            Map<String, String> map = ThreadContext.getImmutableContext();
            if (list == null || list.isEmpty()) {
                return map.isEmpty() ? ContextDataFactory.emptyFrozenContextData() : ForDefaultThreadContextMap.frozenStringMap(map);
            }
            JdkMapAdapterStringMap jdkMapAdapterStringMap = new JdkMapAdapterStringMap(new HashMap<String, String>(map));
            for (int i = 0; i < list.size(); ++i) {
                Property property = list.get(i);
                if (map.containsKey(property.getName())) continue;
                jdkMapAdapterStringMap.putValue(property.getName(), property.getValue());
            }
            jdkMapAdapterStringMap.freeze();
            return jdkMapAdapterStringMap;
        }

        private static JdkMapAdapterStringMap frozenStringMap(Map<String, String> map) {
            JdkMapAdapterStringMap jdkMapAdapterStringMap = new JdkMapAdapterStringMap(map);
            jdkMapAdapterStringMap.freeze();
            return jdkMapAdapterStringMap;
        }

        @Override
        public ReadOnlyStringMap rawContextData() {
            ReadOnlyThreadContextMap readOnlyThreadContextMap = ThreadContext.getThreadContextMap();
            if (readOnlyThreadContextMap instanceof ReadOnlyStringMap) {
                return (ReadOnlyStringMap)((Object)readOnlyThreadContextMap);
            }
            Map<String, String> map = ThreadContext.getImmutableContext();
            return map.isEmpty() ? ContextDataFactory.emptyFrozenContextData() : new JdkMapAdapterStringMap(map);
        }
    }
}

