/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.function.IntUnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.MathHelper;

public class IntClamper
implements IntUnaryOperator {
    private final Integer field_215852_a;
    private final Integer field_215853_b;
    private final IntUnaryOperator field_215854_c;

    private IntClamper(@Nullable Integer n, @Nullable Integer n2) {
        this.field_215852_a = n;
        this.field_215853_b = n2;
        if (n == null) {
            if (n2 == null) {
                this.field_215854_c = IntClamper::lambda$new$0;
            } else {
                int n3 = n2;
                this.field_215854_c = arg_0 -> IntClamper.lambda$new$1(n3, arg_0);
            }
        } else {
            int n4 = n;
            if (n2 == null) {
                this.field_215854_c = arg_0 -> IntClamper.lambda$new$2(n4, arg_0);
            } else {
                int n5 = n2;
                this.field_215854_c = arg_0 -> IntClamper.lambda$new$3(n4, n5, arg_0);
            }
        }
    }

    public static IntClamper func_215843_a(int n, int n2) {
        return new IntClamper(n, n2);
    }

    public static IntClamper func_215848_a(int n) {
        return new IntClamper(n, null);
    }

    public static IntClamper func_215851_b(int n) {
        return new IntClamper(null, n);
    }

    @Override
    public int applyAsInt(int n) {
        return this.field_215854_c.applyAsInt(n);
    }

    private static int lambda$new$3(int n, int n2, int n3) {
        return MathHelper.clamp(n3, n, n2);
    }

    private static int lambda$new$2(int n, int n2) {
        return Math.max(n, n2);
    }

    private static int lambda$new$1(int n, int n2) {
        return Math.min(n, n2);
    }

    private static int lambda$new$0(int n) {
        return n;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements JsonDeserializer<IntClamper>,
    JsonSerializer<IntClamper> {
        @Override
        public IntClamper deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "value");
            Integer n = jsonObject.has("min") ? Integer.valueOf(JSONUtils.getInt(jsonObject, "min")) : null;
            Integer n2 = jsonObject.has("max") ? Integer.valueOf(JSONUtils.getInt(jsonObject, "max")) : null;
            return new IntClamper(n, n2);
        }

        @Override
        public JsonElement serialize(IntClamper intClamper, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject = new JsonObject();
            if (intClamper.field_215853_b != null) {
                jsonObject.addProperty("max", intClamper.field_215853_b);
            }
            if (intClamper.field_215852_a != null) {
                jsonObject.addProperty("min", intClamper.field_215852_a);
            }
            return jsonObject;
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }

        @Override
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((IntClamper)object, type, jsonSerializationContext);
        }
    }
}

