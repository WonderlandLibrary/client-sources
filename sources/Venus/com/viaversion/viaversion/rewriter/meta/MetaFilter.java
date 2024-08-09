/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.rewriter.meta;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.rewriter.meta.MetaHandler;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MetaFilter {
    private final MetaHandler handler;
    private final EntityType type;
    private final int index;
    private final boolean filterFamily;

    public MetaFilter(@Nullable EntityType entityType, boolean bl, int n, MetaHandler metaHandler) {
        Preconditions.checkNotNull(metaHandler, "MetaHandler cannot be null");
        this.type = entityType;
        this.filterFamily = bl;
        this.index = n;
        this.handler = metaHandler;
    }

    public int index() {
        return this.index;
    }

    public @Nullable EntityType type() {
        return this.type;
    }

    public MetaHandler handler() {
        return this.handler;
    }

    public boolean filterFamily() {
        return this.filterFamily;
    }

    public boolean isFiltered(@Nullable EntityType entityType, Metadata metadata) {
        return !(this.index != -1 && metadata.id() != this.index || this.type != null && !this.matchesType(entityType));
    }

    private boolean matchesType(@Nullable EntityType entityType) {
        return entityType != null && (this.filterFamily ? entityType.isOrHasParent(this.type) : this.type == entityType);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        MetaFilter metaFilter = (MetaFilter)object;
        if (this.index != metaFilter.index) {
            return true;
        }
        if (this.filterFamily != metaFilter.filterFamily) {
            return true;
        }
        if (!this.handler.equals(metaFilter.handler)) {
            return true;
        }
        return Objects.equals(this.type, metaFilter.type);
    }

    public int hashCode() {
        int n = this.handler.hashCode();
        n = 31 * n + (this.type != null ? this.type.hashCode() : 0);
        n = 31 * n + this.index;
        n = 31 * n + (this.filterFamily ? 1 : 0);
        return n;
    }

    public String toString() {
        return "MetaFilter{type=" + this.type + ", filterFamily=" + this.filterFamily + ", index=" + this.index + ", handler=" + this.handler + '}';
    }

    public static final class Builder {
        private final EntityRewriter rewriter;
        private EntityType type;
        private int index = -1;
        private boolean filterFamily;
        private MetaHandler handler;

        public Builder(EntityRewriter entityRewriter) {
            this.rewriter = entityRewriter;
        }

        public Builder type(EntityType entityType) {
            Preconditions.checkArgument(this.type == null);
            this.type = entityType;
            return this;
        }

        public Builder index(int n) {
            Preconditions.checkArgument(this.index == -1);
            this.index = n;
            return this;
        }

        public Builder filterFamily(EntityType entityType) {
            Preconditions.checkArgument(this.type == null);
            this.type = entityType;
            this.filterFamily = true;
            return this;
        }

        public Builder handlerNoRegister(MetaHandler metaHandler) {
            Preconditions.checkArgument(this.handler == null);
            this.handler = metaHandler;
            return this;
        }

        public void handler(MetaHandler metaHandler) {
            Preconditions.checkArgument(this.handler == null);
            this.handler = metaHandler;
            this.register();
        }

        public void cancel(int n) {
            this.index = n;
            this.handler(Builder::lambda$cancel$0);
        }

        public void toIndex(int n) {
            Preconditions.checkArgument(this.index != -1);
            this.handler((arg_0, arg_1) -> Builder.lambda$toIndex$1(n, arg_0, arg_1));
        }

        public void addIndex(int n) {
            Preconditions.checkArgument(this.index == -1);
            this.handler((arg_0, arg_1) -> Builder.lambda$addIndex$2(n, arg_0, arg_1));
        }

        public void removeIndex(int n) {
            Preconditions.checkArgument(this.index == -1);
            this.handler((arg_0, arg_1) -> Builder.lambda$removeIndex$3(n, arg_0, arg_1));
        }

        public void register() {
            this.rewriter.registerFilter(this.build());
        }

        public MetaFilter build() {
            return new MetaFilter(this.type, this.filterFamily, this.index, this.handler);
        }

        private static void lambda$removeIndex$3(int n, MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
            int n2 = metaHandlerEvent.index();
            if (n2 == n) {
                metaHandlerEvent.cancel();
            } else if (n2 > n) {
                metaHandlerEvent.setIndex(n2 - 1);
            }
        }

        private static void lambda$addIndex$2(int n, MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
            if (metaHandlerEvent.index() >= n) {
                metaHandlerEvent.setIndex(metaHandlerEvent.index() + 1);
            }
        }

        private static void lambda$toIndex$1(int n, MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
            metaHandlerEvent.setIndex(n);
        }

        private static void lambda$cancel$0(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
            metaHandlerEvent.cancel();
        }
    }
}

