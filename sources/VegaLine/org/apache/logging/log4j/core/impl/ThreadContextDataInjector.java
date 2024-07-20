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
    public static void copyProperties(List<Property> properties, StringMap result) {
        if (properties != null) {
            for (int i = 0; i < properties.size(); ++i) {
                Property prop = properties.get(i);
                result.putValue(prop.getName(), prop.getValue());
            }
        }
    }

    public static class ForCopyOnWriteThreadContextMap
    implements ContextDataInjector {
        @Override
        public StringMap injectContextData(List<Property> props, StringMap ignore) {
            StringMap immutableCopy = ThreadContext.getThreadContextMap().getReadOnlyContextData();
            if (props == null || props.isEmpty()) {
                return immutableCopy;
            }
            StringMap result = ContextDataFactory.createContextData(props.size() + immutableCopy.size());
            ThreadContextDataInjector.copyProperties(props, result);
            result.putAll(immutableCopy);
            return result;
        }

        @Override
        public ReadOnlyStringMap rawContextData() {
            return ThreadContext.getThreadContextMap().getReadOnlyContextData();
        }
    }

    public static class ForGarbageFreeThreadContextMap
    implements ContextDataInjector {
        @Override
        public StringMap injectContextData(List<Property> props, StringMap reusable) {
            ThreadContextDataInjector.copyProperties(props, reusable);
            StringMap immutableCopy = ThreadContext.getThreadContextMap().getReadOnlyContextData();
            reusable.putAll(immutableCopy);
            return reusable;
        }

        @Override
        public ReadOnlyStringMap rawContextData() {
            return ThreadContext.getThreadContextMap().getReadOnlyContextData();
        }
    }

    public static class ForDefaultThreadContextMap
    implements ContextDataInjector {
        @Override
        public StringMap injectContextData(List<Property> props, StringMap ignore) {
            Map<String, String> copy = ThreadContext.getImmutableContext();
            if (props == null || props.isEmpty()) {
                return copy.isEmpty() ? ContextDataFactory.emptyFrozenContextData() : ForDefaultThreadContextMap.frozenStringMap(copy);
            }
            JdkMapAdapterStringMap result = new JdkMapAdapterStringMap(new HashMap<String, String>(copy));
            for (int i = 0; i < props.size(); ++i) {
                Property prop = props.get(i);
                if (copy.containsKey(prop.getName())) continue;
                result.putValue(prop.getName(), prop.getValue());
            }
            result.freeze();
            return result;
        }

        private static JdkMapAdapterStringMap frozenStringMap(Map<String, String> copy) {
            JdkMapAdapterStringMap result = new JdkMapAdapterStringMap(copy);
            result.freeze();
            return result;
        }

        @Override
        public ReadOnlyStringMap rawContextData() {
            ReadOnlyThreadContextMap map = ThreadContext.getThreadContextMap();
            if (map instanceof ReadOnlyStringMap) {
                return (ReadOnlyStringMap)((Object)map);
            }
            Map<String, String> copy = ThreadContext.getImmutableContext();
            return copy.isEmpty() ? ContextDataFactory.emptyFrozenContextData() : new JdkMapAdapterStringMap(copy);
        }
    }
}

