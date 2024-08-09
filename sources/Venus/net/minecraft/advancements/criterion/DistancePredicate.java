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
import net.minecraft.util.math.MathHelper;

public class DistancePredicate {
    public static final DistancePredicate ANY = new DistancePredicate(MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED);
    private final MinMaxBounds.FloatBound x;
    private final MinMaxBounds.FloatBound y;
    private final MinMaxBounds.FloatBound z;
    private final MinMaxBounds.FloatBound horizontal;
    private final MinMaxBounds.FloatBound absolute;

    public DistancePredicate(MinMaxBounds.FloatBound floatBound, MinMaxBounds.FloatBound floatBound2, MinMaxBounds.FloatBound floatBound3, MinMaxBounds.FloatBound floatBound4, MinMaxBounds.FloatBound floatBound5) {
        this.x = floatBound;
        this.y = floatBound2;
        this.z = floatBound3;
        this.horizontal = floatBound4;
        this.absolute = floatBound5;
    }

    public static DistancePredicate forHorizontal(MinMaxBounds.FloatBound floatBound) {
        return new DistancePredicate(MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, floatBound, MinMaxBounds.FloatBound.UNBOUNDED);
    }

    public static DistancePredicate forVertical(MinMaxBounds.FloatBound floatBound) {
        return new DistancePredicate(MinMaxBounds.FloatBound.UNBOUNDED, floatBound, MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED);
    }

    public boolean test(double d, double d2, double d3, double d4, double d5, double d6) {
        float f = (float)(d - d4);
        float f2 = (float)(d2 - d5);
        float f3 = (float)(d3 - d6);
        if (this.x.test(MathHelper.abs(f)) && this.y.test(MathHelper.abs(f2)) && this.z.test(MathHelper.abs(f3))) {
            if (!this.horizontal.testSquared(f * f + f3 * f3)) {
                return true;
            }
            return this.absolute.testSquared(f * f + f2 * f2 + f3 * f3);
        }
        return true;
    }

    public static DistancePredicate deserialize(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "distance");
            MinMaxBounds.FloatBound floatBound = MinMaxBounds.FloatBound.fromJson(jsonObject.get("x"));
            MinMaxBounds.FloatBound floatBound2 = MinMaxBounds.FloatBound.fromJson(jsonObject.get("y"));
            MinMaxBounds.FloatBound floatBound3 = MinMaxBounds.FloatBound.fromJson(jsonObject.get("z"));
            MinMaxBounds.FloatBound floatBound4 = MinMaxBounds.FloatBound.fromJson(jsonObject.get("horizontal"));
            MinMaxBounds.FloatBound floatBound5 = MinMaxBounds.FloatBound.fromJson(jsonObject.get("absolute"));
            return new DistancePredicate(floatBound, floatBound2, floatBound3, floatBound4, floatBound5);
        }
        return ANY;
    }

    public JsonElement serialize() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("x", this.x.serialize());
        jsonObject.add("y", this.y.serialize());
        jsonObject.add("z", this.z.serialize());
        jsonObject.add("horizontal", this.horizontal.serialize());
        jsonObject.add("absolute", this.absolute.serialize());
        return jsonObject;
    }
}

