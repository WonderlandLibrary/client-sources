/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemModelMesher {
    private final Map<Integer, ModelResourceLocation> simpleShapes = Maps.newHashMap();
    private final Map<Integer, IBakedModel> simpleShapesCache = Maps.newHashMap();
    private final Map<Item, ItemMeshDefinition> shapers = Maps.newHashMap();
    private final ModelManager modelManager;

    public ItemModelMesher(ModelManager modelManager) {
        this.modelManager = modelManager;
    }

    public TextureAtlasSprite getParticleIcon(Item item) {
        return this.getParticleIcon(item, 0);
    }

    public TextureAtlasSprite getParticleIcon(Item item, int meta) {
        return this.getItemModel(new ItemStack(item, 1, meta)).getParticleTexture();
    }

    public IBakedModel getItemModel(ItemStack stack) {
        ItemMeshDefinition itemmeshdefinition;
        Item item = stack.getItem();
        IBakedModel ibakedmodel = this.getItemModel(item, this.getMetadata(stack));
        if (ibakedmodel == null && (itemmeshdefinition = this.shapers.get(item)) != null) {
            ibakedmodel = this.modelManager.getModel(itemmeshdefinition.getModelLocation(stack));
        }
        if (ibakedmodel == null) {
            ibakedmodel = this.modelManager.getMissingModel();
        }
        return ibakedmodel;
    }

    protected int getMetadata(ItemStack stack) {
        return stack.getMaxDamage() > 0 ? 0 : stack.getMetadata();
    }

    @Nullable
    protected IBakedModel getItemModel(Item item, int meta) {
        return this.simpleShapesCache.get(this.getIndex(item, meta));
    }

    private int getIndex(Item item, int meta) {
        return Item.getIdFromItem(item) << 16 | meta;
    }

    public void register(Item item, int meta, ModelResourceLocation location) {
        this.simpleShapes.put(this.getIndex(item, meta), location);
        this.simpleShapesCache.put(this.getIndex(item, meta), this.modelManager.getModel(location));
    }

    public void register(Item item, ItemMeshDefinition definition) {
        this.shapers.put(item, definition);
    }

    public ModelManager getModelManager() {
        return this.modelManager;
    }

    public void rebuildCache() {
        this.simpleShapesCache.clear();
        for (Map.Entry<Integer, ModelResourceLocation> entry : this.simpleShapes.entrySet()) {
            this.simpleShapesCache.put(entry.getKey(), this.modelManager.getModel(entry.getValue()));
        }
    }
}

