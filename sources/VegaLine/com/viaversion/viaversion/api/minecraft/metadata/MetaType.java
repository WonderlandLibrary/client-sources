/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.metadata;

import com.viaversion.viaversion.api.type.Type;

public interface MetaType {
    public Type type();

    public int typeId();

    public static MetaType create(int typeId, Type<?> type2) {
        return new MetaTypeImpl(typeId, type2);
    }

    public static final class MetaTypeImpl
    implements MetaType {
        private final int typeId;
        private final Type<?> type;

        MetaTypeImpl(int typeId, Type<?> type2) {
            this.typeId = typeId;
            this.type = type2;
        }

        @Override
        public int typeId() {
            return this.typeId;
        }

        @Override
        public Type<?> type() {
            return this.type;
        }

        public String toString() {
            return "MetaType{typeId=" + this.typeId + ", type=" + this.type + '}';
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            MetaTypeImpl metaType = (MetaTypeImpl)o;
            if (this.typeId != metaType.typeId) {
                return false;
            }
            return this.type.equals(metaType.type);
        }

        public int hashCode() {
            int result = this.typeId;
            result = 31 * result + this.type.hashCode();
            return result;
        }
    }
}

