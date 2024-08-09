/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootSerializers;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootPredicateManager
extends JsonReloadListener {
    private static final Logger field_227510_a_ = LogManager.getLogger();
    private static final Gson field_227511_b_ = LootSerializers.func_237386_a_().create();
    private Map<ResourceLocation, ILootCondition> field_227512_c_ = ImmutableMap.of();

    public LootPredicateManager() {
        super(field_227511_b_, "predicates");
    }

    @Nullable
    public ILootCondition func_227517_a_(ResourceLocation resourceLocation) {
        return this.field_227512_c_.get(resourceLocation);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, IResourceManager iResourceManager, IProfiler iProfiler) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        map.forEach((arg_0, arg_1) -> LootPredicateManager.lambda$apply$0(builder, arg_0, arg_1));
        ImmutableMap<ResourceLocation, ILootCondition> immutableMap = builder.build();
        ValidationTracker validationTracker = new ValidationTracker(LootParameterSets.GENERIC, immutableMap::get, LootPredicateManager::lambda$apply$1);
        immutableMap.forEach((arg_0, arg_1) -> LootPredicateManager.lambda$apply$2(validationTracker, arg_0, arg_1));
        validationTracker.getProblems().forEach(LootPredicateManager::lambda$apply$3);
        this.field_227512_c_ = immutableMap;
    }

    public Set<ResourceLocation> func_227513_a_() {
        return Collections.unmodifiableSet(this.field_227512_c_.keySet());
    }

    @Override
    protected void apply(Object object, IResourceManager iResourceManager, IProfiler iProfiler) {
        this.apply((Map)object, iResourceManager, iProfiler);
    }

    private static void lambda$apply$3(String string, String string2) {
        field_227510_a_.warn("Found validation problem in " + string + ": " + string2);
    }

    private static void lambda$apply$2(ValidationTracker validationTracker, ResourceLocation resourceLocation, ILootCondition iLootCondition) {
        iLootCondition.func_225580_a_(validationTracker.func_227535_b_("{" + resourceLocation + "}", resourceLocation));
    }

    private static LootTable lambda$apply$1(ResourceLocation resourceLocation) {
        return null;
    }

    private static void lambda$apply$0(ImmutableMap.Builder builder, ResourceLocation resourceLocation, JsonElement jsonElement) {
        try {
            if (jsonElement.isJsonArray()) {
                ILootCondition[] iLootConditionArray = field_227511_b_.fromJson(jsonElement, ILootCondition[].class);
                builder.put(resourceLocation, new AndCombiner(iLootConditionArray));
            } else {
                ILootCondition iLootCondition = field_227511_b_.fromJson(jsonElement, ILootCondition.class);
                builder.put(resourceLocation, iLootCondition);
            }
        } catch (Exception exception) {
            field_227510_a_.error("Couldn't parse loot table {}", (Object)resourceLocation, (Object)exception);
        }
    }

    static class AndCombiner
    implements ILootCondition {
        private final ILootCondition[] field_237405_a_;
        private final Predicate<LootContext> field_237406_b_;

        private AndCombiner(ILootCondition[] iLootConditionArray) {
            this.field_237405_a_ = iLootConditionArray;
            this.field_237406_b_ = LootConditionManager.and(iLootConditionArray);
        }

        @Override
        public final boolean test(LootContext lootContext) {
            return this.field_237406_b_.test(lootContext);
        }

        @Override
        public void func_225580_a_(ValidationTracker validationTracker) {
            ILootCondition.super.func_225580_a_(validationTracker);
            for (int i = 0; i < this.field_237405_a_.length; ++i) {
                this.field_237405_a_[i].func_225580_a_(validationTracker.func_227534_b_(".term[" + i + "]"));
            }
        }

        @Override
        public LootConditionType func_230419_b_() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean test(Object object) {
            return this.test((LootContext)object);
        }
    }
}

