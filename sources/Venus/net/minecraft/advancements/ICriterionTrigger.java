/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.ResourceLocation;

public interface ICriterionTrigger<T extends ICriterionInstance> {
    public ResourceLocation getId();

    public void addListener(PlayerAdvancements var1, Listener<T> var2);

    public void removeListener(PlayerAdvancements var1, Listener<T> var2);

    public void removeAllListeners(PlayerAdvancements var1);

    public T deserialize(JsonObject var1, ConditionArrayParser var2);

    public static class Listener<T extends ICriterionInstance> {
        private final T criterionInstance;
        private final Advancement advancement;
        private final String criterionName;

        public Listener(T t, Advancement advancement, String string) {
            this.criterionInstance = t;
            this.advancement = advancement;
            this.criterionName = string;
        }

        public T getCriterionInstance() {
            return this.criterionInstance;
        }

        public void grantCriterion(PlayerAdvancements playerAdvancements) {
            playerAdvancements.grantCriterion(this.advancement, this.criterionName);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object != null && this.getClass() == object.getClass()) {
                Listener listener = (Listener)object;
                if (!this.criterionInstance.equals(listener.criterionInstance)) {
                    return true;
                }
                return !this.advancement.equals(listener.advancement) ? false : this.criterionName.equals(listener.criterionName);
            }
            return true;
        }

        public int hashCode() {
            int n = this.criterionInstance.hashCode();
            n = 31 * n + this.advancement.hashCode();
            return 31 * n + this.criterionName.hashCode();
        }
    }
}

