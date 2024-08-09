/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;

public interface IMultiPartPredicateBuilder
extends Supplier<JsonElement> {
    public void func_230523_a_(StateContainer<?, ?> var1);

    public static Properties func_240089_a_() {
        return new Properties();
    }

    public static IMultiPartPredicateBuilder func_240090_b_(IMultiPartPredicateBuilder ... iMultiPartPredicateBuilderArray) {
        return new Serializer(Operator.OR, Arrays.asList(iMultiPartPredicateBuilderArray));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Properties
    implements IMultiPartPredicateBuilder {
        private final Map<Property<?>, String> field_240096_a_ = Maps.newHashMap();

        private static <T extends Comparable<T>> String func_240101_a_(Property<T> property, Stream<T> stream) {
            return stream.map(property::getName).collect(Collectors.joining("|"));
        }

        private static <T extends Comparable<T>> String func_240103_c_(Property<T> property, T t, T[] TArray) {
            return Properties.func_240101_a_(property, Stream.concat(Stream.of(t), Stream.of(TArray)));
        }

        private <T extends Comparable<T>> void func_240100_a_(Property<T> property, String string) {
            String string2 = this.field_240096_a_.put(property, string);
            if (string2 != null) {
                throw new IllegalStateException("Tried to replace " + property + " value from " + string2 + " to " + string);
            }
        }

        public final <T extends Comparable<T>> Properties func_240098_a_(Property<T> property, T t) {
            this.func_240100_a_(property, property.getName(t));
            return this;
        }

        @SafeVarargs
        public final <T extends Comparable<T>> Properties func_240099_a_(Property<T> property, T t, T ... TArray) {
            this.func_240100_a_(property, Properties.func_240103_c_(property, t, TArray));
            return this;
        }

        @Override
        public JsonElement get() {
            JsonObject jsonObject = new JsonObject();
            this.field_240096_a_.forEach((arg_0, arg_1) -> Properties.lambda$get$0(jsonObject, arg_0, arg_1));
            return jsonObject;
        }

        @Override
        public void func_230523_a_(StateContainer<?, ?> stateContainer) {
            List list = this.field_240096_a_.keySet().stream().filter(arg_0 -> Properties.lambda$func_230523_a_$1(stateContainer, arg_0)).collect(Collectors.toList());
            if (!list.isEmpty()) {
                throw new IllegalStateException("Properties " + list + " are missing from " + stateContainer);
            }
        }

        @Override
        public Object get() {
            return this.get();
        }

        private static boolean lambda$func_230523_a_$1(StateContainer stateContainer, Property property) {
            return stateContainer.getProperty(property.getName()) != property;
        }

        private static void lambda$get$0(JsonObject jsonObject, Property property, String string) {
            jsonObject.addProperty(property.getName(), string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements IMultiPartPredicateBuilder {
        private final Operator field_240091_a_;
        private final List<IMultiPartPredicateBuilder> field_240092_b_;

        private Serializer(Operator operator, List<IMultiPartPredicateBuilder> list) {
            this.field_240091_a_ = operator;
            this.field_240092_b_ = list;
        }

        @Override
        public void func_230523_a_(StateContainer<?, ?> stateContainer) {
            this.field_240092_b_.forEach(arg_0 -> Serializer.lambda$func_230523_a_$0(stateContainer, arg_0));
        }

        @Override
        public JsonElement get() {
            JsonArray jsonArray = new JsonArray();
            this.field_240092_b_.stream().map(Supplier::get).forEach(jsonArray::add);
            JsonObject jsonObject = new JsonObject();
            jsonObject.add(this.field_240091_a_.field_240094_c_, jsonArray);
            return jsonObject;
        }

        @Override
        public Object get() {
            return this.get();
        }

        private static void lambda$func_230523_a_$0(StateContainer stateContainer, IMultiPartPredicateBuilder iMultiPartPredicateBuilder) {
            iMultiPartPredicateBuilder.func_230523_a_(stateContainer);
        }
    }

    public static enum Operator {
        AND("AND"),
        OR("OR");

        private final String field_240094_c_;

        private Operator(String string2) {
            this.field_240094_c_ = string2;
        }
    }
}

