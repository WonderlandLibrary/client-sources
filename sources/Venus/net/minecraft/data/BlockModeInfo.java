/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.function.Function;

public class BlockModeInfo<T> {
    private final String property;
    private final Function<T, JsonElement> value;

    public BlockModeInfo(String string, Function<T, JsonElement> function) {
        this.property = string;
        this.value = function;
    }

    public Field getFieldInfo(T t) {
        return new Field(this, t);
    }

    public String toString() {
        return this.property;
    }

    public class Field {
        private final T element;
        final BlockModeInfo this$0;

        public Field(BlockModeInfo blockModeInfo, T t) {
            this.this$0 = blockModeInfo;
            this.element = t;
        }

        public void serialize(JsonObject jsonObject) {
            jsonObject.add(this.this$0.property, this.this$0.value.apply(this.element));
        }

        public String toString() {
            return this.this$0.property + "=" + this.element;
        }
    }
}

