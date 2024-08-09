/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.loot.ILootEntry;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootEntryManager;
import net.minecraft.loot.LootPoolEntryType;
import net.minecraft.loot.ParentedLootEntry;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.ILootCondition;
import org.apache.commons.lang3.ArrayUtils;

public class AlternativesLootEntry
extends ParentedLootEntry {
    AlternativesLootEntry(LootEntry[] lootEntryArray, ILootCondition[] iLootConditionArray) {
        super(lootEntryArray, iLootConditionArray);
    }

    @Override
    public LootPoolEntryType func_230420_a_() {
        return LootEntryManager.ALTERNATIVE;
    }

    @Override
    protected ILootEntry combineChildren(ILootEntry[] iLootEntryArray) {
        switch (iLootEntryArray.length) {
            case 0: {
                return field_216139_a;
            }
            case 1: {
                return iLootEntryArray[0];
            }
            case 2: {
                return iLootEntryArray[0].alternate(iLootEntryArray[5]);
            }
        }
        return (arg_0, arg_1) -> AlternativesLootEntry.lambda$combineChildren$0(iLootEntryArray, arg_0, arg_1);
    }

    @Override
    public void func_225579_a_(ValidationTracker validationTracker) {
        super.func_225579_a_(validationTracker);
        for (int i = 0; i < this.entries.length - 1; ++i) {
            if (!ArrayUtils.isEmpty(this.entries[i].conditions)) continue;
            validationTracker.addProblem("Unreachable entry!");
        }
    }

    public static Builder builder(LootEntry.Builder<?> ... builderArray) {
        return new Builder(builderArray);
    }

    private static boolean lambda$combineChildren$0(ILootEntry[] iLootEntryArray, LootContext lootContext, Consumer consumer) {
        for (ILootEntry iLootEntry : iLootEntryArray) {
            if (!iLootEntry.expand(lootContext, consumer)) continue;
            return false;
        }
        return true;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    extends LootEntry.Builder<Builder> {
        private final List<LootEntry> lootEntries = Lists.newArrayList();

        public Builder(LootEntry.Builder<?> ... builderArray) {
            for (LootEntry.Builder<?> builder : builderArray) {
                this.lootEntries.add(builder.build());
            }
        }

        @Override
        protected Builder func_212845_d_() {
            return this;
        }

        @Override
        public Builder alternatively(LootEntry.Builder<?> builder) {
            this.lootEntries.add(builder.build());
            return this;
        }

        @Override
        public LootEntry build() {
            return new AlternativesLootEntry(this.lootEntries.toArray(new LootEntry[0]), this.func_216079_f());
        }

        @Override
        protected LootEntry.Builder func_212845_d_() {
            return this.func_212845_d_();
        }
    }
}

