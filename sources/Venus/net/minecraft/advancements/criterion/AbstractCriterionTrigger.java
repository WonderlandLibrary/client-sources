/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.LootContext;

public abstract class AbstractCriterionTrigger<T extends CriterionInstance>
implements ICriterionTrigger<T> {
    private final Map<PlayerAdvancements, Set<ICriterionTrigger.Listener<T>>> triggerListeners = Maps.newIdentityHashMap();

    @Override
    public final void addListener(PlayerAdvancements playerAdvancements, ICriterionTrigger.Listener<T> listener) {
        this.triggerListeners.computeIfAbsent(playerAdvancements, AbstractCriterionTrigger::lambda$addListener$0).add(listener);
    }

    @Override
    public final void removeListener(PlayerAdvancements playerAdvancements, ICriterionTrigger.Listener<T> listener) {
        Set<ICriterionTrigger.Listener<T>> set = this.triggerListeners.get(playerAdvancements);
        if (set != null) {
            set.remove(listener);
            if (set.isEmpty()) {
                this.triggerListeners.remove(playerAdvancements);
            }
        }
    }

    @Override
    public final void removeAllListeners(PlayerAdvancements playerAdvancements) {
        this.triggerListeners.remove(playerAdvancements);
    }

    protected abstract T deserializeTrigger(JsonObject var1, EntityPredicate.AndPredicate var2, ConditionArrayParser var3);

    @Override
    public final T deserialize(JsonObject jsonObject, ConditionArrayParser conditionArrayParser) {
        EntityPredicate.AndPredicate andPredicate = EntityPredicate.AndPredicate.deserializeJSONObject(jsonObject, "player", conditionArrayParser);
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    protected void triggerListeners(ServerPlayerEntity serverPlayerEntity, Predicate<T> predicate) {
        PlayerAdvancements playerAdvancements = serverPlayerEntity.getAdvancements();
        Set<ICriterionTrigger.Listener<T>> set = this.triggerListeners.get(playerAdvancements);
        if (set != null && !set.isEmpty()) {
            LootContext lootContext = EntityPredicate.getLootContext(serverPlayerEntity, serverPlayerEntity);
            ArrayList<ICriterionTrigger.Listener<T>> arrayList = null;
            for (ICriterionTrigger.Listener<T> listener : set) {
                CriterionInstance criterionInstance = (CriterionInstance)listener.getCriterionInstance();
                if (!criterionInstance.getPlayerCondition().testContext(lootContext) || !predicate.test(criterionInstance)) continue;
                if (arrayList == null) {
                    arrayList = Lists.newArrayList();
                }
                arrayList.add(listener);
            }
            if (arrayList != null) {
                for (ICriterionTrigger.Listener<Object> listener : arrayList) {
                    listener.grantCriterion(playerAdvancements);
                }
            }
        }
    }

    @Override
    public ICriterionInstance deserialize(JsonObject jsonObject, ConditionArrayParser conditionArrayParser) {
        return this.deserialize(jsonObject, conditionArrayParser);
    }

    private static Set lambda$addListener$0(PlayerAdvancements playerAdvancements) {
        return Sets.newHashSet();
    }
}

