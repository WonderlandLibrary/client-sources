/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.util.ResourceLocation;

public interface ICriterionTrigger<T extends ICriterionInstance> {
    public ResourceLocation func_192163_a();

    public void func_192165_a(PlayerAdvancements var1, Listener<T> var2);

    public void func_192164_b(PlayerAdvancements var1, Listener<T> var2);

    public void func_192167_a(PlayerAdvancements var1);

    public T func_192166_a(JsonObject var1, JsonDeserializationContext var2);

    public static class Listener<T extends ICriterionInstance> {
        private final T field_192160_a;
        private final Advancement field_192161_b;
        private final String field_192162_c;

        public Listener(T p_i47405_1_, Advancement p_i47405_2_, String p_i47405_3_) {
            this.field_192160_a = p_i47405_1_;
            this.field_192161_b = p_i47405_2_;
            this.field_192162_c = p_i47405_3_;
        }

        public T func_192158_a() {
            return this.field_192160_a;
        }

        public void func_192159_a(PlayerAdvancements p_192159_1_) {
            p_192159_1_.func_192750_a(this.field_192161_b, this.field_192162_c);
        }

        public boolean equals(Object p_equals_1_) {
            if (this == p_equals_1_) {
                return true;
            }
            if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
                Listener listener = (Listener)p_equals_1_;
                if (!this.field_192160_a.equals(listener.field_192160_a)) {
                    return false;
                }
                return !this.field_192161_b.equals(listener.field_192161_b) ? false : this.field_192162_c.equals(listener.field_192162_c);
            }
            return false;
        }

        public int hashCode() {
            int i = this.field_192160_a.hashCode();
            i = 31 * i + this.field_192161_b.hashCode();
            i = 31 * i + this.field_192162_c.hashCode();
            return i;
        }
    }
}

