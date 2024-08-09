/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.resources.ResourceIndex;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.VanillaPack;
import net.minecraft.util.ResourceLocation;

public class VirtualAssetsPack
extends VanillaPack {
    private final ResourceIndex field_195785_b;

    public VirtualAssetsPack(ResourceIndex resourceIndex) {
        super("minecraft", "realms");
        this.field_195785_b = resourceIndex;
    }

    @Override
    @Nullable
    protected InputStream getInputStreamVanilla(ResourcePackType resourcePackType, ResourceLocation resourceLocation) {
        File file;
        if (resourcePackType == ResourcePackType.CLIENT_RESOURCES && (file = this.field_195785_b.getFile(resourceLocation)) != null && file.exists()) {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException fileNotFoundException) {
                // empty catch block
            }
        }
        return super.getInputStreamVanilla(resourcePackType, resourceLocation);
    }

    @Override
    public boolean resourceExists(ResourcePackType resourcePackType, ResourceLocation resourceLocation) {
        File file;
        if (resourcePackType == ResourcePackType.CLIENT_RESOURCES && (file = this.field_195785_b.getFile(resourceLocation)) != null && file.exists()) {
            return false;
        }
        return super.resourceExists(resourcePackType, resourceLocation);
    }

    @Override
    @Nullable
    protected InputStream getInputStreamVanilla(String string) {
        File file = this.field_195785_b.getFile(string);
        if (file != null && file.exists()) {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException fileNotFoundException) {
                // empty catch block
            }
        }
        return super.getInputStreamVanilla(string);
    }

    @Override
    public Collection<ResourceLocation> getAllResourceLocations(ResourcePackType resourcePackType, String string, String string2, int n, Predicate<String> predicate) {
        Collection<ResourceLocation> collection = super.getAllResourceLocations(resourcePackType, string, string2, n, predicate);
        collection.addAll(this.field_195785_b.getFiles(string2, string, n, predicate));
        return collection;
    }
}

