/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources.data;

import net.minecraft.resources.data.PackMetadataSectionSerializer;
import net.minecraft.util.text.ITextComponent;

public class PackMetadataSection {
    public static final PackMetadataSectionSerializer SERIALIZER = new PackMetadataSectionSerializer();
    private final ITextComponent description;
    private final int packFormat;

    public PackMetadataSection(ITextComponent iTextComponent, int n) {
        this.description = iTextComponent;
        this.packFormat = n;
    }

    public ITextComponent getDescription() {
        return this.description;
    }

    public int getPackFormat() {
        return this.packFormat;
    }
}

