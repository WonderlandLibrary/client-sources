/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import javax.annotation.Nullable;
import org.lwjgl.system.FunctionProvider;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.Pointer;
import org.lwjgl.system.dyncall.DynLoad;

public interface SharedLibrary
extends FunctionProvider,
NativeResource,
Pointer {
    public String getName();

    @Nullable
    public String getPath();

    public static abstract class Delegate
    implements SharedLibrary {
        protected final SharedLibrary library;

        protected Delegate(SharedLibrary sharedLibrary) {
            this.library = sharedLibrary;
        }

        @Override
        public String getName() {
            return this.library.getName();
        }

        @Override
        @Nullable
        public String getPath() {
            return this.library.getPath();
        }

        @Override
        public long address() {
            return this.library.address();
        }

        @Override
        public void free() {
            this.library.free();
        }
    }

    public static abstract class Default
    extends Pointer.Default
    implements SharedLibrary {
        private final String name;

        protected Default(String string, long l) {
            super(l);
            this.name = string;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        @Nullable
        public String getPath() {
            String string = DynLoad.dlGetLibraryPath(this.address(), 256);
            return string.isEmpty() ? null : string;
        }
    }
}

