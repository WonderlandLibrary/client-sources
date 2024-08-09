/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.chars.Char2BooleanFunction;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Char2BooleanMap
extends Char2BooleanFunction,
Map<Character, Boolean> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(boolean var1);

    @Override
    public boolean defaultReturnValue();

    public ObjectSet<Entry> char2BooleanEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Character, Boolean>> entrySet() {
        return this.char2BooleanEntrySet();
    }

    @Override
    @Deprecated
    default public Boolean put(Character c, Boolean bl) {
        return Char2BooleanFunction.super.put(c, bl);
    }

    @Override
    @Deprecated
    default public Boolean get(Object object) {
        return Char2BooleanFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Boolean remove(Object object) {
        return Char2BooleanFunction.super.remove(object);
    }

    public CharSet keySet();

    public BooleanCollection values();

    @Override
    public boolean containsKey(char var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Char2BooleanFunction.super.containsKey(object);
    }

    public boolean containsValue(boolean var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Boolean)object);
    }

    default public boolean getOrDefault(char c, boolean bl) {
        boolean bl2 = this.get(c);
        return bl2 != this.defaultReturnValue() || this.containsKey(c) ? bl2 : bl;
    }

    @Override
    default public boolean putIfAbsent(char c, boolean bl) {
        boolean bl2;
        boolean bl3 = this.get(c);
        if (bl3 != (bl2 = this.defaultReturnValue()) || this.containsKey(c)) {
            return bl3;
        }
        this.put(c, bl);
        return bl2;
    }

    default public boolean remove(char c, boolean bl) {
        boolean bl2 = this.get(c);
        if (bl2 != bl || bl2 == this.defaultReturnValue() && !this.containsKey(c)) {
            return true;
        }
        this.remove(c);
        return false;
    }

    @Override
    default public boolean replace(char c, boolean bl, boolean bl2) {
        boolean bl3 = this.get(c);
        if (bl3 != bl || bl3 == this.defaultReturnValue() && !this.containsKey(c)) {
            return true;
        }
        this.put(c, bl2);
        return false;
    }

    @Override
    default public boolean replace(char c, boolean bl) {
        return this.containsKey(c) ? this.put(c, bl) : this.defaultReturnValue();
    }

    default public boolean computeIfAbsent(char c, IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        boolean bl = this.get(c);
        if (bl != this.defaultReturnValue() || this.containsKey(c)) {
            return bl;
        }
        boolean bl2 = intPredicate.test(c);
        this.put(c, bl2);
        return bl2;
    }

    default public boolean computeIfAbsentNullable(char c, IntFunction<? extends Boolean> intFunction) {
        Objects.requireNonNull(intFunction);
        boolean bl = this.get(c);
        boolean bl2 = this.defaultReturnValue();
        if (bl != bl2 || this.containsKey(c)) {
            return bl;
        }
        Boolean bl3 = intFunction.apply(c);
        if (bl3 == null) {
            return bl2;
        }
        boolean bl4 = bl3;
        this.put(c, bl4);
        return bl4;
    }

    default public boolean computeIfAbsentPartial(char c, Char2BooleanFunction char2BooleanFunction) {
        Objects.requireNonNull(char2BooleanFunction);
        boolean bl = this.get(c);
        boolean bl2 = this.defaultReturnValue();
        if (bl != bl2 || this.containsKey(c)) {
            return bl;
        }
        if (!char2BooleanFunction.containsKey(c)) {
            return bl2;
        }
        boolean bl3 = char2BooleanFunction.get(c);
        this.put(c, bl3);
        return bl3;
    }

    @Override
    default public boolean computeIfPresent(char c, BiFunction<? super Character, ? super Boolean, ? extends Boolean> biFunction) {
        Objects.requireNonNull(biFunction);
        boolean bl = this.get(c);
        boolean bl2 = this.defaultReturnValue();
        if (bl == bl2 && !this.containsKey(c)) {
            return bl2;
        }
        Boolean bl3 = biFunction.apply(Character.valueOf(c), (Boolean)bl);
        if (bl3 == null) {
            this.remove(c);
            return bl2;
        }
        boolean bl4 = bl3;
        this.put(c, bl4);
        return bl4;
    }

    @Override
    default public boolean compute(char c, BiFunction<? super Character, ? super Boolean, ? extends Boolean> biFunction) {
        Objects.requireNonNull(biFunction);
        boolean bl = this.get(c);
        boolean bl2 = this.defaultReturnValue();
        boolean bl3 = bl != bl2 || this.containsKey(c);
        Boolean bl4 = biFunction.apply(Character.valueOf(c), bl3 ? Boolean.valueOf(bl) : null);
        if (bl4 == null) {
            if (bl3) {
                this.remove(c);
            }
            return bl2;
        }
        boolean bl5 = bl4;
        this.put(c, bl5);
        return bl5;
    }

    @Override
    default public boolean merge(char c, boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
        boolean bl2;
        Objects.requireNonNull(biFunction);
        boolean bl3 = this.get(c);
        boolean bl4 = this.defaultReturnValue();
        if (bl3 != bl4 || this.containsKey(c)) {
            Boolean bl5 = biFunction.apply((Boolean)bl3, (Boolean)bl);
            if (bl5 == null) {
                this.remove(c);
                return bl4;
            }
            bl2 = bl5;
        } else {
            bl2 = bl;
        }
        this.put(c, bl2);
        return bl2;
    }

    @Override
    @Deprecated
    default public Boolean getOrDefault(Object object, Boolean bl) {
        return Map.super.getOrDefault(object, bl);
    }

    @Override
    @Deprecated
    default public Boolean putIfAbsent(Character c, Boolean bl) {
        return Map.super.putIfAbsent(c, bl);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Character c, Boolean bl, Boolean bl2) {
        return Map.super.replace(c, bl, bl2);
    }

    @Override
    @Deprecated
    default public Boolean replace(Character c, Boolean bl) {
        return Map.super.replace(c, bl);
    }

    @Override
    @Deprecated
    default public Boolean computeIfAbsent(Character c, Function<? super Character, ? extends Boolean> function) {
        return Map.super.computeIfAbsent(c, function);
    }

    @Override
    @Deprecated
    default public Boolean computeIfPresent(Character c, BiFunction<? super Character, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.computeIfPresent(c, biFunction);
    }

    @Override
    @Deprecated
    default public Boolean compute(Character c, BiFunction<? super Character, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.compute(c, biFunction);
    }

    @Override
    @Deprecated
    default public Boolean merge(Character c, Boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.merge(c, bl, biFunction);
    }

    @Override
    @Deprecated
    default public Object remove(Object object) {
        return this.remove(object);
    }

    @Override
    @Deprecated
    default public Object get(Object object) {
        return this.get(object);
    }

    @Override
    @Deprecated
    default public Object put(Object object, Object object2) {
        return this.put((Character)object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Character)object, (Boolean)object2, (BiFunction<? super Boolean, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Character)object, (BiFunction<? super Character, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Boolean>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Character)object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Character)object, (Boolean)object2, (Boolean)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Character)object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public Set entrySet() {
        return this.entrySet();
    }

    @Override
    default public Collection values() {
        return this.values();
    }

    @Override
    default public Set keySet() {
        return this.keySet();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static interface Entry
    extends Map.Entry<Character, Boolean> {
        public char getCharKey();

        @Override
        @Deprecated
        default public Character getKey() {
            return Character.valueOf(this.getCharKey());
        }

        public boolean getBooleanValue();

        @Override
        public boolean setValue(boolean var1);

        @Override
        @Deprecated
        default public Boolean getValue() {
            return this.getBooleanValue();
        }

        @Override
        @Deprecated
        default public Boolean setValue(Boolean bl) {
            return this.setValue((boolean)bl);
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Boolean)object);
        }

        @Override
        @Deprecated
        default public Object getValue() {
            return this.getValue();
        }

        @Override
        @Deprecated
        default public Object getKey() {
            return this.getKey();
        }
    }

    public static interface FastEntrySet
    extends ObjectSet<Entry> {
        public ObjectIterator<Entry> fastIterator();

        default public void fastForEach(Consumer<? super Entry> consumer) {
            this.forEach(consumer);
        }
    }
}

