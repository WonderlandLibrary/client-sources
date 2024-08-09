/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.io.IOException;

interface BinaryTagScope
extends AutoCloseable {
    @Override
    public void close() throws IOException;

    public static final class NoOp
    implements BinaryTagScope {
        static final NoOp INSTANCE = new NoOp();

        private NoOp() {
        }

        @Override
        public void close() {
        }
    }
}

