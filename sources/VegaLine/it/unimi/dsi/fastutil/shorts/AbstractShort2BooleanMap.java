/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2BooleanFunction;
import it.unimi.dsi.fastutil.shorts.AbstractShortIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractShort2BooleanMap
extends AbstractShort2BooleanFunction
implements Short2BooleanMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractShort2BooleanMap() {
    }

    @Override
    public boolean containsValue(Object ov) {
        if (ov == null) {
            return false;
        }
        return this.containsValue((Boolean)ov);
    }

    @Override
    public boolean containsValue(boolean v) {
        return this.values().contains(v);
    }

    @Override
    public boolean containsKey(short k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends Short, ? extends Boolean> m) {
        int n = m.size();
        Iterator<Map.Entry<? extends Short, ? extends Boolean>> i = m.entrySet().iterator();
        if (m instanceof Short2BooleanMap) {
            while (n-- != 0) {
                Short2BooleanMap.Entry e = (Short2BooleanMap.Entry)i.next();
                this.put(e.getShortKey(), e.getBooleanValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<? extends Short, ? extends Boolean> e = i.next();
                this.put(e.getKey(), e.getValue());
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public ShortSet keySet() {
        return new AbstractShortSet(){

            @Override
            public boolean contains(short k) {
                return AbstractShort2BooleanMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractShort2BooleanMap.this.size();
            }

            @Override
            public void clear() {
                AbstractShort2BooleanMap.this.clear();
            }

            @Override
            public ShortIterator iterator() {
                return new AbstractShortIterator(){
                    final ObjectIterator<Map.Entry<Short, Boolean>> i;
                    {
                        this.i = AbstractShort2BooleanMap.this.entrySet().iterator();
                    }

                    @Override
                    public short nextShort() {
                        return ((Short2BooleanMap.Entry)this.i.next()).getShortKey();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }

                    @Override
                    public void remove() {
                        this.i.remove();
                    }
                };
            }
        };
    }

    @Override
    public BooleanCollection values() {
        return new AbstractBooleanCollection(){

            @Override
            public boolean contains(boolean k) {
                return AbstractShort2BooleanMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractShort2BooleanMap.this.size();
            }

            @Override
            public void clear() {
                AbstractShort2BooleanMap.this.clear();
            }

            @Override
            public BooleanIterator iterator() {
                return new AbstractBooleanIterator(){
                    final ObjectIterator<Map.Entry<Short, Boolean>> i;
                    {
                        this.i = AbstractShort2BooleanMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public boolean nextBoolean() {
                        return ((Short2BooleanMap.Entry)this.i.next()).getBooleanValue();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }
                };
            }
        };
    }

    @Override
    public ObjectSet<Map.Entry<Short, Boolean>> entrySet() {
        return this.short2BooleanEntrySet();
    }

    @Override
    public int hashCode() {
        int h = 0;
        int n = this.size();
        ObjectIterator i = this.entrySet().iterator();
        while (n-- != 0) {
            h += ((Map.Entry)i.next()).hashCode();
        }
        return h;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Map)) {
            return false;
        }
        Map m = (Map)o;
        if (m.size() != this.size()) {
            return false;
        }
        return this.entrySet().containsAll(m.entrySet());
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        ObjectIterator i = this.entrySet().iterator();
        int n = this.size();
        boolean first = true;
        s.append("{");
        while (n-- != 0) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            Short2BooleanMap.Entry e = (Short2BooleanMap.Entry)i.next();
            s.append(String.valueOf(e.getShortKey()));
            s.append("=>");
            s.append(String.valueOf(e.getBooleanValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry
    implements Short2BooleanMap.Entry {
        protected short key;
        protected boolean value;

        public BasicEntry(Short key, Boolean value) {
            this.key = key;
            this.value = value;
        }

        public BasicEntry(short key, boolean value) {
            this.key = key;
            this.value = value;
        }

        @Override
        @Deprecated
        public Short getKey() {
            return this.key;
        }

        @Override
        public short getShortKey() {
            return this.key;
        }

        @Override
        @Deprecated
        public Boolean getValue() {
            return this.value;
        }

        @Override
        public boolean getBooleanValue() {
            return this.value;
        }

        @Override
        public boolean setValue(boolean value) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean setValue(Boolean value) {
            return this.setValue((boolean)value);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Short)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Boolean)) {
                return false;
            }
            return this.key == (Short)e.getKey() && this.value == (Boolean)e.getValue();
        }

        @Override
        public int hashCode() {
            return this.key ^ (this.value ? 1231 : 1237);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

