/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SkullBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.TridentRenderer;
import net.minecraft.client.renderer.entity.model.BookModel;
import net.minecraft.client.renderer.entity.model.TridentModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.EnchantmentTableTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.entity.model.CustomEntityModelParser;
import net.optifine.entity.model.CustomEntityRenderer;
import net.optifine.entity.model.CustomModelRegistry;
import net.optifine.entity.model.CustomModelRenderer;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.anim.ModelResolver;
import net.optifine.entity.model.anim.ModelUpdater;
import net.optifine.reflect.Reflector;
import net.optifine.util.Either;

public class CustomEntityModels {
    private static boolean active = false;
    private static Map<EntityType, EntityRenderer> originalEntityRenderMap = null;
    private static Map<TileEntityType, TileEntityRenderer> originalTileEntityRenderMap = null;
    private static Map<SkullBlock.ISkullType, Model> originalSkullModelMap = null;
    private static List<TileEntityType> customTileEntityTypes = new ArrayList<TileEntityType>();

    public static void update() {
        Map<EntityType, EntityRenderer> map = CustomEntityModels.getEntityRenderMap();
        Map<TileEntityType, TileEntityRenderer> map2 = CustomEntityModels.getTileEntityRenderMap();
        Map<SkullBlock.ISkullType, Model> map3 = CustomEntityModels.getSkullModelMap();
        if (map == null) {
            Config.warn("Entity render map not found, custom entity models are DISABLED.");
        } else if (map2 == null) {
            Config.warn("Tile entity render map not found, custom entity models are DISABLED.");
        } else {
            active = false;
            map.clear();
            map2.clear();
            map3.clear();
            customTileEntityTypes.clear();
            map.putAll(originalEntityRenderMap);
            map2.putAll(originalTileEntityRenderMap);
            map3.putAll(originalSkullModelMap);
            ItemStackTileEntityRenderer.instance.trident = new TridentModel();
            CustomEntityModels.setEnchantmentScreenBookModel(new BookModel());
            if (Config.isCustomEntityModels()) {
                ResourceLocation[] resourceLocationArray = CustomEntityModels.getModelLocations();
                for (int i = 0; i < resourceLocationArray.length; ++i) {
                    ResourceLocation resourceLocation = resourceLocationArray[i];
                    Config.dbg("CustomEntityModel: " + resourceLocation.getPath());
                    IEntityRenderer iEntityRenderer = CustomEntityModels.parseEntityRender(resourceLocation);
                    if (iEntityRenderer == null) continue;
                    Either<EntityType, TileEntityType> either = iEntityRenderer.getType();
                    if (iEntityRenderer instanceof EntityRenderer) {
                        map.put(either.getLeft().get(), (EntityRenderer)iEntityRenderer);
                        if (iEntityRenderer instanceof TridentRenderer && (var9_9 = (TridentModel)Reflector.getFieldValue(var8_8 = (TridentRenderer)iEntityRenderer, Reflector.RenderTrident_modelTrident)) != null) {
                            ItemStackTileEntityRenderer.instance.trident = var9_9;
                        }
                    } else if (iEntityRenderer instanceof TileEntityRenderer) {
                        map2.put(either.getRight().get(), (TileEntityRenderer)iEntityRenderer);
                        if (iEntityRenderer instanceof EnchantmentTableTileEntityRenderer) {
                            var8_8 = (EnchantmentTableTileEntityRenderer)iEntityRenderer;
                            var9_9 = (BookModel)Reflector.getFieldValue(var8_8, Reflector.TileEntityEnchantmentTableRenderer_modelBook);
                            CustomEntityModels.setEnchantmentScreenBookModel((BookModel)var9_9);
                        }
                        customTileEntityTypes.add(either.getRight().get());
                    } else {
                        Config.warn("Unknown renderer type: " + iEntityRenderer.getClass().getName());
                    }
                    active = true;
                }
            }
        }
    }

    private static void setEnchantmentScreenBookModel(BookModel bookModel) {
        BookModel bookModel2 = (BookModel)Reflector.GuiEnchantment_bookModel.getValue();
        if (bookModel2 != null && bookModel != null) {
            if (!Reflector.ModelBook_ModelRenderers.exists()) {
                return;
            }
            if (!Reflector.ModelBook_bookParts.exists()) {
                return;
            }
            int n = Reflector.ModelBook_ModelRenderers.getFieldCount();
            for (int i = 0; i < n; ++i) {
                ModelRenderer modelRenderer = (ModelRenderer)Reflector.ModelBook_ModelRenderers.getValue(bookModel, i);
                Reflector.ModelBook_ModelRenderers.setValue(bookModel2, i, modelRenderer);
            }
            List list = (List)Reflector.ModelBook_bookParts.getValue(bookModel);
            Reflector.ModelBook_bookParts.setValue(bookModel2, list);
            bookModel2.textureWidth = bookModel.textureWidth;
            bookModel2.textureHeight = bookModel.textureHeight;
        }
    }

    private static Map<EntityType, EntityRenderer> getEntityRenderMap() {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        Map<EntityType, EntityRenderer> map = entityRendererManager.getEntityRenderMap();
        if (map == null) {
            return null;
        }
        if (originalEntityRenderMap == null) {
            originalEntityRenderMap = new HashMap<EntityType, EntityRenderer>(map);
        }
        return map;
    }

    private static Map<TileEntityType, TileEntityRenderer> getTileEntityRenderMap() {
        Map<TileEntityType, TileEntityRenderer> map = TileEntityRendererDispatcher.instance.renderers;
        if (originalTileEntityRenderMap == null) {
            originalTileEntityRenderMap = new HashMap<TileEntityType, TileEntityRenderer>(map);
        }
        return map;
    }

    private static Map<SkullBlock.ISkullType, Model> getSkullModelMap() {
        HashMap hashMap = (HashMap)Reflector.TileEntitySkullRenderer_MODELS.getValue();
        if (hashMap == null) {
            Config.warn("Field not found: TileEntitySkullRenderer.MODELS");
            hashMap = new HashMap();
        }
        if (originalSkullModelMap == null) {
            originalSkullModelMap = new HashMap<SkullBlock.ISkullType, Model>(hashMap);
        }
        return hashMap;
    }

    private static ResourceLocation[] getModelLocations() {
        String string = "optifine/cem/";
        String string2 = ".jem";
        ArrayList<ResourceLocation> arrayList = new ArrayList<ResourceLocation>();
        String[] stringArray = CustomModelRegistry.getModelNames();
        for (int i = 0; i < stringArray.length; ++i) {
            String string3 = stringArray[i];
            String string4 = string + string3 + string2;
            ResourceLocation resourceLocation = new ResourceLocation(string4);
            if (!Config.hasResource(resourceLocation)) continue;
            arrayList.add(resourceLocation);
        }
        return arrayList.toArray(new ResourceLocation[arrayList.size()]);
    }

    private static IEntityRenderer parseEntityRender(ResourceLocation resourceLocation) {
        try {
            JsonObject jsonObject = CustomEntityModelParser.loadJson(resourceLocation);
            return CustomEntityModels.parseEntityRender(jsonObject, resourceLocation.getPath());
        } catch (IOException iOException) {
            Config.error(iOException.getClass().getName() + ": " + iOException.getMessage());
            return null;
        } catch (JsonParseException jsonParseException) {
            Config.error(jsonParseException.getClass().getName() + ": " + jsonParseException.getMessage());
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private static IEntityRenderer parseEntityRender(JsonObject jsonObject, String string) {
        CustomEntityRenderer customEntityRenderer = CustomEntityModelParser.parseEntityRender(jsonObject, string);
        String string2 = customEntityRenderer.getName();
        ModelAdapter modelAdapter = CustomModelRegistry.getModelAdapter(string2);
        CustomEntityModels.checkNull(modelAdapter, "Entity not found: " + string2);
        Either<EntityType, TileEntityType> either = modelAdapter.getType();
        CustomEntityModels.checkNull(either, "Entity type not found: " + string2);
        IEntityRenderer iEntityRenderer = CustomEntityModels.makeEntityRender(modelAdapter, customEntityRenderer);
        if (iEntityRenderer == null) {
            return null;
        }
        iEntityRenderer.setType(either);
        return iEntityRenderer;
    }

    private static IEntityRenderer makeEntityRender(ModelAdapter modelAdapter, CustomEntityRenderer customEntityRenderer) {
        Model model;
        ResourceLocation resourceLocation = customEntityRenderer.getTextureLocation();
        CustomModelRenderer[] customModelRendererArray = customEntityRenderer.getCustomModelRenderers();
        float f = customEntityRenderer.getShadowSize();
        if (f < 0.0f) {
            f = modelAdapter.getShadowSize();
        }
        if ((model = modelAdapter.makeModel()) == null) {
            return null;
        }
        ModelResolver modelResolver = new ModelResolver(modelAdapter, model, customModelRendererArray);
        if (!CustomEntityModels.modifyModel(modelAdapter, model, customModelRendererArray, modelResolver)) {
            return null;
        }
        IEntityRenderer iEntityRenderer = modelAdapter.makeEntityRender(model, f);
        if (iEntityRenderer == null) {
            throw new JsonParseException("Entity renderer is null, model: " + modelAdapter.getName() + ", adapter: " + modelAdapter.getClass().getName());
        }
        if (resourceLocation != null) {
            CustomEntityModels.setTextureLocation(modelAdapter, model, iEntityRenderer, resourceLocation);
        }
        return iEntityRenderer;
    }

    private static void setTextureLocation(ModelAdapter modelAdapter, Model model, IEntityRenderer iEntityRenderer, ResourceLocation resourceLocation) {
        if (iEntityRenderer instanceof LivingRenderer) {
            iEntityRenderer.setLocationTextureCustom(resourceLocation);
        } else {
            String[] stringArray = modelAdapter.getModelRendererNames();
            for (int i = 0; i < stringArray.length; ++i) {
                String string = stringArray[i];
                ModelRenderer modelRenderer = modelAdapter.getModelRenderer(model, string);
                if (modelRenderer == null || modelRenderer.getTextureLocation() != null) continue;
                modelRenderer.setTextureLocation(resourceLocation);
            }
        }
    }

    private static boolean modifyModel(ModelAdapter modelAdapter, Model model, CustomModelRenderer[] customModelRendererArray, ModelResolver modelResolver) {
        for (int i = 0; i < customModelRendererArray.length; ++i) {
            CustomModelRenderer customModelRenderer = customModelRendererArray[i];
            if (CustomEntityModels.modifyModel(modelAdapter, model, customModelRenderer, modelResolver)) continue;
            return true;
        }
        return false;
    }

    private static boolean modifyModel(ModelAdapter modelAdapter, Model model, CustomModelRenderer customModelRenderer, ModelResolver modelResolver) {
        ModelRenderer[] modelRendererArray;
        String string = customModelRenderer.getModelPart();
        ModelRenderer modelRenderer = modelAdapter.getModelRenderer(model, string);
        if (modelRenderer == null) {
            Config.warn("Model part not found: " + string + ", model: " + model);
            return true;
        }
        if (!customModelRenderer.isAttach()) {
            if (modelRenderer.cubeList != null) {
                modelRenderer.cubeList.clear();
            }
            if (modelRenderer.spriteList != null) {
                modelRenderer.spriteList.clear();
            }
            if (modelRenderer.childModels != null) {
                modelRendererArray = modelAdapter.getModelRenderers(model);
                Set set = Collections.newSetFromMap(new IdentityHashMap());
                set.addAll(Arrays.asList(modelRendererArray));
                ObjectList<ModelRenderer> objectList = modelRenderer.childModels;
                Iterator iterator2 = objectList.iterator();
                while (iterator2.hasNext()) {
                    ModelRenderer modelRenderer2 = (ModelRenderer)iterator2.next();
                    if (set.contains(modelRenderer2)) continue;
                    iterator2.remove();
                }
            }
        }
        modelRenderer.addChild(customModelRenderer.getModelRenderer());
        modelRendererArray = customModelRenderer.getModelUpdater();
        if (modelRendererArray != null) {
            modelResolver.setThisModelRenderer(customModelRenderer.getModelRenderer());
            modelResolver.setPartModelRenderer(modelRenderer);
            if (!modelRendererArray.initialize(modelResolver)) {
                return true;
            }
            customModelRenderer.getModelRenderer().setModelUpdater((ModelUpdater)modelRendererArray);
        }
        return false;
    }

    private static void checkNull(Object object, String string) {
        if (object == null) {
            throw new JsonParseException(string);
        }
    }

    public static boolean isActive() {
        return active;
    }

    public static boolean isCustomModel(BlockState blockState) {
        Block block = blockState.getBlock();
        for (int i = 0; i < customTileEntityTypes.size(); ++i) {
            TileEntityType tileEntityType = customTileEntityTypes.get(i);
            if (!tileEntityType.isValidBlock(block)) continue;
            return false;
        }
        return true;
    }
}

