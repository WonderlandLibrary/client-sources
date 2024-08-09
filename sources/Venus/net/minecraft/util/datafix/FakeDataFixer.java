/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.SharedConstants;

public class FakeDataFixer
implements DataFixer {
    private static FakeSchema FAKE_SCHEMA = new FakeSchema();

    @Override
    public <T> Dynamic<T> update(DSL.TypeReference typeReference, Dynamic<T> dynamic, int n, int n2) {
        return dynamic;
    }

    @Override
    public Schema getSchema(int n) {
        return FAKE_SCHEMA;
    }

    private static class FakeSchema
    extends Schema {
        public FakeSchema() {
            super(SharedConstants.getVersion().getWorldVersion(), null);
        }

        @Override
        public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
            return ImmutableMap.of();
        }

        @Override
        public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
            return ImmutableMap.of();
        }

        @Override
        public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map, Map<String, Supplier<TypeTemplate>> map2) {
        }

        @Override
        protected Map<String, Type<?>> buildTypes() {
            return ImmutableMap.of();
        }

        @Override
        public Type<?> getType(DSL.TypeReference typeReference) {
            return null;
        }

        @Override
        public Type<?> getChoiceType(DSL.TypeReference typeReference, String string) {
            return null;
        }

        @Override
        public Type<?> getTypeRaw(DSL.TypeReference typeReference) {
            return null;
        }

        @Override
        public TaggedChoice.TaggedChoiceType<?> findChoiceType(DSL.TypeReference typeReference) {
            return null;
        }
    }
}

