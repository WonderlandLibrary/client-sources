/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import org.lwjgl.system.Configuration;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Platform;

final class LibSTB {
    private LibSTB() {
    }

    static void initialize() {
    }

    private static native void setupMalloc(long var0, long var2, long var4, long var6, long var8, long var10);

    static {
        String string = Platform.mapLibraryNameBundled("lwjgl_stb");
        Library.loadSystem(System::load, System::loadLibrary, LibSTB.class, string);
        MemoryUtil.MemoryAllocator memoryAllocator = MemoryUtil.getAllocator(Configuration.DEBUG_MEMORY_ALLOCATOR_INTERNAL.get(true));
        LibSTB.setupMalloc(memoryAllocator.getMalloc(), memoryAllocator.getCalloc(), memoryAllocator.getRealloc(), memoryAllocator.getFree(), memoryAllocator.getAlignedAlloc(), memoryAllocator.getAlignedFree());
    }
}

