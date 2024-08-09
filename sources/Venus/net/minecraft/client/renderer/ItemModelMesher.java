/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;

public class ItemModelMesher {
    public final Int2ObjectMap<ModelResourceLocation> modelLocations = new Int2ObjectOpenHashMap<ModelResourceLocation>(256);
    private final Int2ObjectMap<IBakedModel> itemModels = new Int2ObjectOpenHashMap<IBakedModel>(256);
    private final ModelManager modelManager;

    public ItemModelMesher(ModelManager modelManager) {
        this.modelManager = modelManager;
    }

    public TextureAtlasSprite getParticleIcon(IItemProvider iItemProvider) {
        return this.getParticleIcon(new ItemStack(iItemProvider));
    }

    public TextureAtlasSprite getParticleIcon(ItemStack itemStack) {
        IBakedModel iBakedModel = this.getItemModel(itemStack);
        return iBakedModel == this.modelManager.getMissingModel() && itemStack.getItem() instanceof BlockItem ? this.modelManager.getBlockModelShapes().getTexture(((BlockItem)itemStack.getItem()).getBlock().getDefaultState()) : iBakedModel.getParticleTexture();
    }

    public IBakedModel getItemModel(ItemStack itemStack) {
        IBakedModel iBakedModel = this.getItemModel(itemStack.getItem());
        return iBakedModel == null ? this.modelManager.getMissingModel() : iBakedModel;
    }

    @Nullable
    public IBakedModel getItemModel(Item item) {
        return (IBakedModel)this.itemModels.get(ItemModelMesher.getIndex(item));
    }

    private static int getIndex(Item item) {
        return Item.getIdFromItem(item);
    }

    public void register(Item item, ModelResourceLocation modelResourceLocation) {
        this.modelLocations.put(ItemModelMesher.getIndex(item), modelResourceLocation);
    }

    public ModelManager getModelManager() {
        return this.modelManager;
    }

    public void rebuildCache() {
        this.itemModels.clear();
        for (Map.Entry entry : this.modelLocations.entrySet()) {
            this.itemModels.put((Integer)entry.getKey(), this.modelManager.getModel((ModelResourceLocation)entry.getValue()));
        }
    }
}

