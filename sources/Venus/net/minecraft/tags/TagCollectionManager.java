/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tags;

import java.util.stream.Collectors;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.tags.ItemTags;

public class TagCollectionManager {
    private static volatile ITagCollectionSupplier manager = ITagCollectionSupplier.getTagCollectionSupplier(ITagCollection.getTagCollectionFromMap(BlockTags.getAllTags().stream().collect(Collectors.toMap(ITag.INamedTag::getName, TagCollectionManager::lambda$static$0))), ITagCollection.getTagCollectionFromMap(ItemTags.getAllTags().stream().collect(Collectors.toMap(ITag.INamedTag::getName, TagCollectionManager::lambda$static$1))), ITagCollection.getTagCollectionFromMap(FluidTags.getAllTags().stream().collect(Collectors.toMap(ITag.INamedTag::getName, TagCollectionManager::lambda$static$2))), ITagCollection.getTagCollectionFromMap(EntityTypeTags.getAllTags().stream().collect(Collectors.toMap(ITag.INamedTag::getName, TagCollectionManager::lambda$static$3))));

    public static ITagCollectionSupplier getManager() {
        return manager;
    }

    public static void setManager(ITagCollectionSupplier iTagCollectionSupplier) {
        manager = iTagCollectionSupplier;
    }

    private static ITag lambda$static$3(ITag.INamedTag iNamedTag) {
        return iNamedTag;
    }

    private static ITag lambda$static$2(ITag.INamedTag iNamedTag) {
        return iNamedTag;
    }

    private static ITag lambda$static$1(ITag.INamedTag iNamedTag) {
        return iNamedTag;
    }

    private static ITag lambda$static$0(ITag.INamedTag iNamedTag) {
        return iNamedTag;
    }
}

