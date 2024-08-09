/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Const;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import net.minecraft.util.ResourceLocation;

public class NamespacedSchema
extends Schema {
    public static final PrimitiveCodec<String> field_233455_a_ = new PrimitiveCodec<String>(){

        @Override
        public <T> DataResult<String> read(DynamicOps<T> dynamicOps, T t) {
            return dynamicOps.getStringValue(t).map(NamespacedSchema::ensureNamespaced);
        }

        @Override
        public <T> T write(DynamicOps<T> dynamicOps, String string) {
            return dynamicOps.createString(string);
        }

        public String toString() {
            return "NamespacedString";
        }

        @Override
        public Object write(DynamicOps dynamicOps, Object object) {
            return this.write(dynamicOps, (String)object);
        }
    };
    private static final Type<String> field_233456_b_ = new Const.PrimitiveType<String>(field_233455_a_);

    public NamespacedSchema(int n, Schema schema) {
        super(n, schema);
    }

    public static String ensureNamespaced(String string) {
        ResourceLocation resourceLocation = ResourceLocation.tryCreate(string);
        return resourceLocation != null ? resourceLocation.toString() : string;
    }

    public static Type<String> func_233457_a_() {
        return field_233456_b_;
    }

    @Override
    public Type<?> getChoiceType(DSL.TypeReference typeReference, String string) {
        return super.getChoiceType(typeReference, NamespacedSchema.ensureNamespaced(string));
    }
}

