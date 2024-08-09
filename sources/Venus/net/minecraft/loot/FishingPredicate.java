/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.util.JSONUtils;

public class FishingPredicate {
    public static final FishingPredicate field_234635_a_ = new FishingPredicate(false);
    private boolean field_234636_b_;

    private FishingPredicate(boolean bl) {
        this.field_234636_b_ = bl;
    }

    public static FishingPredicate func_234640_a_(boolean bl) {
        return new FishingPredicate(bl);
    }

    public static FishingPredicate func_234639_a_(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "fishing_hook");
            JsonElement jsonElement2 = jsonObject.get("in_open_water");
            return jsonElement2 != null ? new FishingPredicate(JSONUtils.getBoolean(jsonElement2, "in_open_water")) : field_234635_a_;
        }
        return field_234635_a_;
    }

    public JsonElement func_234637_a_() {
        if (this == field_234635_a_) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("in_open_water", new JsonPrimitive(this.field_234636_b_));
        return jsonObject;
    }

    public boolean func_234638_a_(Entity entity2) {
        if (this == field_234635_a_) {
            return false;
        }
        if (!(entity2 instanceof FishingBobberEntity)) {
            return true;
        }
        FishingBobberEntity fishingBobberEntity = (FishingBobberEntity)entity2;
        return this.field_234636_b_ == fishingBobberEntity.func_234605_g_();
    }
}

