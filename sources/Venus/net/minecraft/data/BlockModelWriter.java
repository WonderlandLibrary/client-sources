/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.function.Supplier;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BlockModelWriter
implements Supplier<JsonElement> {
    private final ResourceLocation field_240218_a_;

    public BlockModelWriter(ResourceLocation resourceLocation) {
        this.field_240218_a_ = resourceLocation;
    }

    @Override
    public JsonElement get() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("parent", this.field_240218_a_.toString());
        return jsonObject;
    }

    @Override
    public Object get() {
        return this.get();
    }
}

