/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import java.io.File;
import java.io.FileNotFoundException;

public class ResourcePackFileNotFoundException
extends FileNotFoundException {
    public ResourcePackFileNotFoundException(File file, String string) {
        super(String.format("'%s' in ResourcePack '%s'", string, file));
    }
}

