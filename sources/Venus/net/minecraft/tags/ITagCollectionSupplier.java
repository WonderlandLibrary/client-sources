/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tags;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.TagRegistryManager;
import net.minecraft.util.registry.Registry;

public interface ITagCollectionSupplier {
    public static final ITagCollectionSupplier TAG_COLLECTION_SUPPLIER = ITagCollectionSupplier.getTagCollectionSupplier(ITagCollection.getEmptyTagCollection(), ITagCollection.getEmptyTagCollection(), ITagCollection.getEmptyTagCollection(), ITagCollection.getEmptyTagCollection());

    public ITagCollection<Block> getBlockTags();

    public ITagCollection<Item> getItemTags();

    public ITagCollection<Fluid> getFluidTags();

    public ITagCollection<EntityType<?>> getEntityTypeTags();

    default public void updateTags() {
        TagRegistryManager.fetchTags(this);
        Blocks.cacheBlockStates();
    }

    default public void writeTagCollectionSupplierToBuffer(PacketBuffer packetBuffer) {
        this.getBlockTags().writeTagCollectionToBuffer(packetBuffer, Registry.BLOCK);
        this.getItemTags().writeTagCollectionToBuffer(packetBuffer, Registry.ITEM);
        this.getFluidTags().writeTagCollectionToBuffer(packetBuffer, Registry.FLUID);
        this.getEntityTypeTags().writeTagCollectionToBuffer(packetBuffer, Registry.ENTITY_TYPE);
    }

    public static ITagCollectionSupplier readTagCollectionSupplierFromBuffer(PacketBuffer packetBuffer) {
        ITagCollection<Block> iTagCollection = ITagCollection.readTagCollectionFromBuffer(packetBuffer, Registry.BLOCK);
        ITagCollection<Item> iTagCollection2 = ITagCollection.readTagCollectionFromBuffer(packetBuffer, Registry.ITEM);
        ITagCollection<Fluid> iTagCollection3 = ITagCollection.readTagCollectionFromBuffer(packetBuffer, Registry.FLUID);
        ITagCollection<EntityType<?>> iTagCollection4 = ITagCollection.readTagCollectionFromBuffer(packetBuffer, Registry.ENTITY_TYPE);
        return ITagCollectionSupplier.getTagCollectionSupplier(iTagCollection, iTagCollection2, iTagCollection3, iTagCollection4);
    }

    public static ITagCollectionSupplier getTagCollectionSupplier(ITagCollection<Block> iTagCollection, ITagCollection<Item> iTagCollection2, ITagCollection<Fluid> iTagCollection3, ITagCollection<EntityType<?>> iTagCollection4) {
        return new ITagCollectionSupplier(){
            final ITagCollection val$blockTags;
            final ITagCollection val$itemTags;
            final ITagCollection val$fluidTags;
            final ITagCollection val$entityTypeTags;
            {
                this.val$blockTags = iTagCollection;
                this.val$itemTags = iTagCollection2;
                this.val$fluidTags = iTagCollection3;
                this.val$entityTypeTags = iTagCollection4;
            }

            @Override
            public ITagCollection<Block> getBlockTags() {
                return this.val$blockTags;
            }

            @Override
            public ITagCollection<Item> getItemTags() {
                return this.val$itemTags;
            }

            @Override
            public ITagCollection<Fluid> getFluidTags() {
                return this.val$fluidTags;
            }

            @Override
            public ITagCollection<EntityType<?>> getEntityTypeTags() {
                return this.val$entityTypeTags;
            }
        };
    }
}

