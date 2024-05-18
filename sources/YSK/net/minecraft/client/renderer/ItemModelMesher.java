package net.minecraft.client.renderer;

import net.minecraft.client.resources.model.*;
import com.google.common.collect.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.texture.*;
import java.util.*;

public class ItemModelMesher
{
    private final Map<Item, ItemMeshDefinition> shapers;
    private final Map<Integer, ModelResourceLocation> simpleShapes;
    private final ModelManager modelManager;
    private final Map<Integer, IBakedModel> simpleShapesCache;
    
    private int getIndex(final Item item, final int n) {
        return Item.getIdFromItem(item) << (0x6A ^ 0x7A) | n;
    }
    
    public void register(final Item item, final int n, final ModelResourceLocation modelResourceLocation) {
        this.simpleShapes.put(this.getIndex(item, n), modelResourceLocation);
        this.simpleShapesCache.put(this.getIndex(item, n), this.modelManager.getModel(modelResourceLocation));
    }
    
    public ItemModelMesher(final ModelManager modelManager) {
        this.simpleShapes = (Map<Integer, ModelResourceLocation>)Maps.newHashMap();
        this.simpleShapesCache = (Map<Integer, IBakedModel>)Maps.newHashMap();
        this.shapers = (Map<Item, ItemMeshDefinition>)Maps.newHashMap();
        this.modelManager = modelManager;
    }
    
    public IBakedModel getItemModel(final ItemStack itemStack) {
        final Item item = itemStack.getItem();
        IBakedModel bakedModel = this.getItemModel(item, this.getMetadata(itemStack));
        if (bakedModel == null) {
            final ItemMeshDefinition itemMeshDefinition = this.shapers.get(item);
            if (itemMeshDefinition != null) {
                bakedModel = this.modelManager.getModel(itemMeshDefinition.getModelLocation(itemStack));
            }
        }
        if (bakedModel == null) {
            bakedModel = this.modelManager.getMissingModel();
        }
        return bakedModel;
    }
    
    protected IBakedModel getItemModel(final Item item, final int n) {
        return this.simpleShapesCache.get(this.getIndex(item, n));
    }
    
    public TextureAtlasSprite getParticleIcon(final Item item) {
        return this.getParticleIcon(item, "".length());
    }
    
    protected int getMetadata(final ItemStack itemStack) {
        int n;
        if (itemStack.isItemStackDamageable()) {
            n = "".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            n = itemStack.getMetadata();
        }
        return n;
    }
    
    public void rebuildCache() {
        this.simpleShapesCache.clear();
        final Iterator<Map.Entry<Integer, ModelResourceLocation>> iterator = this.simpleShapes.entrySet().iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<Integer, ModelResourceLocation> entry = iterator.next();
            this.simpleShapesCache.put(entry.getKey(), this.modelManager.getModel(entry.getValue()));
        }
    }
    
    public void register(final Item item, final ItemMeshDefinition itemMeshDefinition) {
        this.shapers.put(item, itemMeshDefinition);
    }
    
    public TextureAtlasSprite getParticleIcon(final Item item, final int n) {
        return this.getItemModel(new ItemStack(item, " ".length(), n)).getParticleTexture();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ModelManager getModelManager() {
        return this.modelManager;
    }
}
