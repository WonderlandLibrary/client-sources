/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.macosx;

import org.lwjgl.system.Library;
import org.lwjgl.system.SharedLibrary;

public final class LibSystem {
    private static final SharedLibrary SYSTEM = Library.loadNative(LibSystem.class, "System");

    private LibSystem() {
        throw new UnsupportedOperationException();
    }

    public static SharedLibrary getLibrary() {
        return SYSTEM;
    }
}

