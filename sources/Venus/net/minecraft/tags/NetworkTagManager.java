/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tags;

import com.google.common.collect.Multimap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.tags.TagCollectionReader;
import net.minecraft.tags.TagRegistryManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class NetworkTagManager
implements IFutureReloadListener {
    private final TagCollectionReader<Block> blocks = new TagCollectionReader(Registry.BLOCK::getOptional, "tags/blocks", "block");
    private final TagCollectionReader<Item> items = new TagCollectionReader(Registry.ITEM::getOptional, "tags/items", "item");
    private final TagCollectionReader<Fluid> fluids = new TagCollectionReader(Registry.FLUID::getOptional, "tags/fluids", "fluid");
    private final TagCollectionReader<EntityType<?>> entityTypes = new TagCollectionReader(Registry.ENTITY_TYPE::getOptional, "tags/entity_types", "entity_type");
    private ITagCollectionSupplier tagCollectionSupplier = ITagCollectionSupplier.TAG_COLLECTION_SUPPLIER;

    public ITagCollectionSupplier getTagCollectionSupplier() {
        return this.tagCollectionSupplier;
    }

    @Override
    public CompletableFuture<Void> reload(IFutureReloadListener.IStage iStage, IResourceManager iResourceManager, IProfiler iProfiler, IProfiler iProfiler2, Executor executor, Executor executor2) {
        CompletableFuture<Map<ResourceLocation, ITag.Builder>> completableFuture = this.blocks.readTagsFromManager(iResourceManager, executor);
        CompletableFuture<Map<ResourceLocation, ITag.Builder>> completableFuture2 = this.items.readTagsFromManager(iResourceManager, executor);
        CompletableFuture<Map<ResourceLocation, ITag.Builder>> completableFuture3 = this.fluids.readTagsFromManager(iResourceManager, executor);
        CompletableFuture<Map<ResourceLocation, ITag.Builder>> completableFuture4 = this.entityTypes.readTagsFromManager(iResourceManager, executor);
        return ((CompletableFuture)CompletableFuture.allOf(completableFuture, completableFuture2, completableFuture3, completableFuture4).thenCompose(iStage::markCompleteAwaitingOthers)).thenAcceptAsync(arg_0 -> this.lambda$reload$1(completableFuture, completableFuture2, completableFuture3, completableFuture4, arg_0), executor2);
    }

    private void lambda$reload$1(CompletableFuture completableFuture, CompletableFuture completableFuture2, CompletableFuture completableFuture3, CompletableFuture completableFuture4, Void void_) {
        ITagCollection<EntityType<?>> iTagCollection;
        ITagCollection<Fluid> iTagCollection2;
        ITagCollection<Item> iTagCollection3;
        ITagCollection<Block> iTagCollection4 = this.blocks.buildTagCollectionFromMap((Map)completableFuture.join());
        ITagCollectionSupplier iTagCollectionSupplier = ITagCollectionSupplier.getTagCollectionSupplier(iTagCollection4, iTagCollection3 = this.items.buildTagCollectionFromMap((Map)completableFuture2.join()), iTagCollection2 = this.fluids.buildTagCollectionFromMap((Map)completableFuture3.join()), iTagCollection = this.entityTypes.buildTagCollectionFromMap((Map)completableFuture4.join()));
        Multimap<ResourceLocation, ResourceLocation> multimap = TagRegistryManager.validateTags(iTagCollectionSupplier);
        if (!multimap.isEmpty()) {
            throw new IllegalStateException("Missing required tags: " + multimap.entries().stream().map(NetworkTagManager::lambda$reload$0).sorted().collect(Collectors.joining(",")));
        }
        TagCollectionManager.setManager(iTagCollectionSupplier);
        this.tagCollectionSupplier = iTagCollectionSupplier;
    }

    private static String lambda$reload$0(Map.Entry entry) {
        return entry.getKey() + ":" + entry.getValue();
    }
}

