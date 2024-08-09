/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.loot.IParameterized;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.ValidationTracker;

public class LootParameterSet {
    private final Set<LootParameter<?>> required;
    private final Set<LootParameter<?>> all;

    private LootParameterSet(Set<LootParameter<?>> set, Set<LootParameter<?>> set2) {
        this.required = ImmutableSet.copyOf(set);
        this.all = ImmutableSet.copyOf(Sets.union(set, set2));
    }

    public Set<LootParameter<?>> getRequiredParameters() {
        return this.required;
    }

    public Set<LootParameter<?>> getAllParameters() {
        return this.all;
    }

    public String toString() {
        return "[" + Joiner.on(", ").join(this.all.stream().map(this::lambda$toString$0).iterator()) + "]";
    }

    public void func_227556_a_(ValidationTracker validationTracker, IParameterized iParameterized) {
        Set<LootParameter<?>> set = iParameterized.getRequiredParameters();
        Sets.SetView<LootParameter<?>> setView = Sets.difference(set, this.all);
        if (!setView.isEmpty()) {
            validationTracker.addProblem("Parameters " + setView + " are not provided in this context");
        }
    }

    private String lambda$toString$0(LootParameter lootParameter) {
        return (this.required.contains(lootParameter) ? "!" : "") + lootParameter.getId();
    }

    public static class Builder {
        private final Set<LootParameter<?>> required = Sets.newIdentityHashSet();
        private final Set<LootParameter<?>> optional = Sets.newIdentityHashSet();

        public Builder required(LootParameter<?> lootParameter) {
            if (this.optional.contains(lootParameter)) {
                throw new IllegalArgumentException("Parameter " + lootParameter.getId() + " is already optional");
            }
            this.required.add(lootParameter);
            return this;
        }

        public Builder optional(LootParameter<?> lootParameter) {
            if (this.required.contains(lootParameter)) {
                throw new IllegalArgumentException("Parameter " + lootParameter.getId() + " is already required");
            }
            this.optional.add(lootParameter);
            return this;
        }

        public LootParameterSet build() {
            return new LootParameterSet(this.required, this.optional);
        }
    }
}

