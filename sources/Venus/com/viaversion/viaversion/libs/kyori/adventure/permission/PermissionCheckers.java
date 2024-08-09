/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.permission;

import com.viaversion.viaversion.libs.kyori.adventure.permission.PermissionChecker;
import com.viaversion.viaversion.libs.kyori.adventure.util.TriState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class PermissionCheckers {
    static final PermissionChecker NOT_SET = new Always(TriState.NOT_SET, null);
    static final PermissionChecker FALSE = new Always(TriState.FALSE, null);
    static final PermissionChecker TRUE = new Always(TriState.TRUE, null);

    private PermissionCheckers() {
    }

    private static final class Always
    implements PermissionChecker {
        private final TriState value;

        private Always(TriState triState) {
            this.value = triState;
        }

        @Override
        @NotNull
        public TriState value(String string) {
            return this.value;
        }

        public String toString() {
            return PermissionChecker.class.getSimpleName() + ".always(" + (Object)((Object)this.value) + ")";
        }

        public boolean equals(@Nullable Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            Always always = (Always)object;
            return this.value == always.value;
        }

        public int hashCode() {
            return this.value.hashCode();
        }

        Always(TriState triState, 1 var2_2) {
            this(triState);
        }
    }
}

