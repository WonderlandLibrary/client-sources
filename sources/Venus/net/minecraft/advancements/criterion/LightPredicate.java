/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class LightPredicate {
    public static final LightPredicate ANY = new LightPredicate(MinMaxBounds.IntBound.UNBOUNDED);
    private final MinMaxBounds.IntBound bounds;

    private LightPredicate(MinMaxBounds.IntBound intBound) {
        this.bounds = intBound;
    }

    public boolean test(ServerWorld serverWorld, BlockPos blockPos) {
        if (this == ANY) {
            return false;
        }
        if (!serverWorld.isBlockPresent(blockPos)) {
            return true;
        }
        return this.bounds.test(serverWorld.getLight(blockPos));
    }

    public JsonElement serialize() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("light", this.bounds.serialize());
        return jsonObject;
    }

    public static LightPredicate deserialize(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "light");
            MinMaxBounds.IntBound intBound = MinMaxBounds.IntBound.fromJson(jsonObject.get("light"));
            return new LightPredicate(intBound);
        }
        return ANY;
    }
}

