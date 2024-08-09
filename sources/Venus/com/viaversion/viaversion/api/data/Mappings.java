/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import java.util.Arrays;

public interface Mappings {
    public int getNewId(int var1);

    default public int getNewIdOrDefault(int n, int n2) {
        int n3 = this.getNewId(n);
        return n3 != -1 ? n3 : n2;
    }

    default public boolean contains(int n) {
        return this.getNewId(n) != -1;
    }

    public void setNewId(int var1, int var2);

    public int size();

    public int mappedSize();

    public Mappings inverse();

    public static <T extends Mappings> Builder<T> builder(MappingsSupplier<T> mappingsSupplier) {
        return new Builder<T>(mappingsSupplier);
    }

    @Deprecated
    public static class Builder<T extends Mappings> {
        protected final MappingsSupplier<T> supplier;
        protected JsonElement unmapped;
        protected JsonElement mapped;
        protected JsonObject diffMappings;
        protected int mappedSize = -1;
        protected int size = -1;
        protected boolean warnOnMissing = true;

        protected Builder(MappingsSupplier<T> mappingsSupplier) {
            this.supplier = mappingsSupplier;
        }

        public Builder<T> customEntrySize(int n) {
            this.size = n;
            return this;
        }

        public Builder<T> customMappedSize(int n) {
            this.mappedSize = n;
            return this;
        }

        public Builder<T> warnOnMissing(boolean bl) {
            this.warnOnMissing = bl;
            return this;
        }

        public Builder<T> unmapped(JsonArray jsonArray) {
            this.unmapped = jsonArray;
            return this;
        }

        public Builder<T> unmapped(JsonObject jsonObject) {
            this.unmapped = jsonObject;
            return this;
        }

        public Builder<T> mapped(JsonArray jsonArray) {
            this.mapped = jsonArray;
            return this;
        }

        public Builder<T> mapped(JsonObject jsonObject) {
            this.mapped = jsonObject;
            return this;
        }

        public Builder<T> diffMappings(JsonObject jsonObject) {
            this.diffMappings = jsonObject;
            return this;
        }

        public T build() {
            int n = this.size != -1 ? this.size : this.size(this.unmapped);
            int n2 = this.mappedSize != -1 ? this.mappedSize : this.size(this.mapped);
            int[] nArray = new int[n];
            Arrays.fill(nArray, -1);
            if (this.unmapped.isJsonArray()) {
                if (this.mapped.isJsonObject()) {
                    MappingDataLoader.mapIdentifiers(nArray, this.toJsonObject(this.unmapped.getAsJsonArray()), this.mapped.getAsJsonObject(), this.diffMappings, this.warnOnMissing);
                } else {
                    MappingDataLoader.mapIdentifiers(nArray, this.unmapped.getAsJsonArray(), this.mapped.getAsJsonArray(), this.diffMappings, this.warnOnMissing);
                }
            } else if (this.mapped.isJsonArray()) {
                MappingDataLoader.mapIdentifiers(nArray, this.unmapped.getAsJsonObject(), this.toJsonObject(this.mapped.getAsJsonArray()), this.diffMappings, this.warnOnMissing);
            } else {
                MappingDataLoader.mapIdentifiers(nArray, this.unmapped.getAsJsonObject(), this.mapped.getAsJsonObject(), this.diffMappings, this.warnOnMissing);
            }
            return this.supplier.supply(nArray, n2);
        }

        protected int size(JsonElement jsonElement) {
            return jsonElement.isJsonObject() ? jsonElement.getAsJsonObject().size() : jsonElement.getAsJsonArray().size();
        }

        protected JsonObject toJsonObject(JsonArray jsonArray) {
            JsonObject jsonObject = new JsonObject();
            for (int i = 0; i < jsonArray.size(); ++i) {
                JsonElement jsonElement = jsonArray.get(i);
                jsonObject.add(Integer.toString(i), jsonElement);
            }
            return jsonObject;
        }
    }

    @FunctionalInterface
    public static interface MappingsSupplier<T extends Mappings> {
        public T supply(int[] var1, int var2);
    }
}

