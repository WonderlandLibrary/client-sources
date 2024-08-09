/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.StateHolder;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.JSONUtils;

public class StatePropertiesPredicate {
    public static final StatePropertiesPredicate EMPTY = new StatePropertiesPredicate(ImmutableList.of());
    private final List<Matcher> matchers;

    private static Matcher deserializeProperty(String string, JsonElement jsonElement) {
        if (jsonElement.isJsonPrimitive()) {
            String string2 = jsonElement.getAsString();
            return new ExactMatcher(string, string2);
        }
        JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "value");
        String string3 = jsonObject.has("min") ? StatePropertiesPredicate.getNullableString(jsonObject.get("min")) : null;
        String string4 = jsonObject.has("max") ? StatePropertiesPredicate.getNullableString(jsonObject.get("max")) : null;
        return string3 != null && string3.equals(string4) ? new ExactMatcher(string, string3) : new RangedMacher(string, string3, string4);
    }

    @Nullable
    private static String getNullableString(JsonElement jsonElement) {
        return jsonElement.isJsonNull() ? null : jsonElement.getAsString();
    }

    private StatePropertiesPredicate(List<Matcher> list) {
        this.matchers = ImmutableList.copyOf(list);
    }

    public <S extends StateHolder<?, S>> boolean matchesAll(StateContainer<?, S> stateContainer, S s) {
        for (Matcher matcher : this.matchers) {
            if (matcher.test(stateContainer, s)) continue;
            return true;
        }
        return false;
    }

    public boolean matches(BlockState blockState) {
        return this.matchesAll(blockState.getBlock().getStateContainer(), blockState);
    }

    public boolean matches(FluidState fluidState) {
        return this.matchesAll(fluidState.getFluid().getStateContainer(), fluidState);
    }

    public void forEachNotPresent(StateContainer<?, ?> stateContainer, Consumer<String> consumer) {
        this.matchers.forEach(arg_0 -> StatePropertiesPredicate.lambda$forEachNotPresent$0(stateContainer, consumer, arg_0));
    }

    public static StatePropertiesPredicate deserializeProperties(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "properties");
            ArrayList<Matcher> arrayList = Lists.newArrayList();
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                arrayList.add(StatePropertiesPredicate.deserializeProperty(entry.getKey(), entry.getValue()));
            }
            return new StatePropertiesPredicate(arrayList);
        }
        return EMPTY;
    }

    public JsonElement toJsonElement() {
        if (this == EMPTY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        if (!this.matchers.isEmpty()) {
            this.matchers.forEach(arg_0 -> StatePropertiesPredicate.lambda$toJsonElement$1(jsonObject, arg_0));
        }
        return jsonObject;
    }

    private static void lambda$toJsonElement$1(JsonObject jsonObject, Matcher matcher) {
        jsonObject.add(matcher.getPropertyName(), matcher.toJsonElement());
    }

    private static void lambda$forEachNotPresent$0(StateContainer stateContainer, Consumer consumer, Matcher matcher) {
        matcher.runIfNotPresent(stateContainer, consumer);
    }

    static class ExactMatcher
    extends Matcher {
        private final String valueToMatch;

        public ExactMatcher(String string, String string2) {
            super(string);
            this.valueToMatch = string2;
        }

        @Override
        protected <T extends Comparable<T>> boolean matchesExact(StateHolder<?, ?> stateHolder, Property<T> property) {
            Comparable comparable = stateHolder.get(property);
            Optional<T> optional = property.parseValue(this.valueToMatch);
            return optional.isPresent() && comparable.compareTo((Comparable)((Comparable)optional.get())) == 0;
        }

        @Override
        public JsonElement toJsonElement() {
            return new JsonPrimitive(this.valueToMatch);
        }
    }

    static class RangedMacher
    extends Matcher {
        @Nullable
        private final String minimum;
        @Nullable
        private final String maximum;

        public RangedMacher(String string, @Nullable String string2, @Nullable String string3) {
            super(string);
            this.minimum = string2;
            this.maximum = string3;
        }

        @Override
        protected <T extends Comparable<T>> boolean matchesExact(StateHolder<?, ?> stateHolder, Property<T> property) {
            Optional<T> optional;
            Comparable comparable = stateHolder.get(property);
            if (!(this.minimum == null || (optional = property.parseValue(this.minimum)).isPresent() && comparable.compareTo((Comparable)((Comparable)optional.get())) >= 0)) {
                return true;
            }
            return this.maximum != null && (!(optional = property.parseValue(this.maximum)).isPresent() || comparable.compareTo((Comparable)((Comparable)optional.get())) > 0);
        }

        @Override
        public JsonElement toJsonElement() {
            JsonObject jsonObject = new JsonObject();
            if (this.minimum != null) {
                jsonObject.addProperty("min", this.minimum);
            }
            if (this.maximum != null) {
                jsonObject.addProperty("max", this.maximum);
            }
            return jsonObject;
        }
    }

    static abstract class Matcher {
        private final String propertyName;

        public Matcher(String string) {
            this.propertyName = string;
        }

        public <S extends StateHolder<?, S>> boolean test(StateContainer<?, S> stateContainer, S s) {
            Property<?> property = stateContainer.getProperty(this.propertyName);
            return property == null ? false : this.matchesExact(s, property);
        }

        protected abstract <T extends Comparable<T>> boolean matchesExact(StateHolder<?, ?> var1, Property<T> var2);

        public abstract JsonElement toJsonElement();

        public String getPropertyName() {
            return this.propertyName;
        }

        public void runIfNotPresent(StateContainer<?, ?> stateContainer, Consumer<String> consumer) {
            Property<?> property = stateContainer.getProperty(this.propertyName);
            if (property == null) {
                consumer.accept(this.propertyName);
            }
        }
    }

    public static class Builder {
        private final List<Matcher> matchers = Lists.newArrayList();

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withStringProp(Property<?> property, String string) {
            this.matchers.add(new ExactMatcher(property.getName(), string));
            return this;
        }

        public Builder withIntProp(Property<Integer> property, int n) {
            return this.withStringProp(property, Integer.toString(n));
        }

        public Builder withBoolProp(Property<Boolean> property, boolean bl) {
            return this.withStringProp(property, Boolean.toString(bl));
        }

        public <T extends Comparable<T> & IStringSerializable> Builder withProp(Property<T> property, T t) {
            return this.withStringProp(property, ((IStringSerializable)t).getString());
        }

        public StatePropertiesPredicate build() {
            return new StatePropertiesPredicate(this.matchers);
        }
    }
}

