/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.metadata;

import com.viaversion.viaversion.api.type.Type;

public interface MetaType {
    public Type type();

    public int typeId();

    public static MetaType create(int n, Type<?> type) {
        return new MetaTypeImpl(n, type);
    }

    public static final class MetaTypeImpl
    implements MetaType {
        private final int typeId;
        private final Type<?> type;

        MetaTypeImpl(int n, Type<?> type) {
            this.typeId = n;
            this.type = type;
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

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            MetaTypeImpl metaTypeImpl = (MetaTypeImpl)object;
            if (this.typeId != metaTypeImpl.typeId) {
                return true;
            }
            return this.type.equals(metaTypeImpl.type);
        }

        public int hashCode() {
            int n = this.typeId;
            n = 31 * n + this.type.hashCode();
            return n;
        }
    }
}

