/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemOverride;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.ItemOverrideCache;

public class ItemOverrideList {
    public static final ItemOverrideList EMPTY = new ItemOverrideList();
    private final List<ItemOverride> overrides = Lists.newArrayList();
    private final List<IBakedModel> overrideBakedModels;
    private ItemOverrideCache itemOverrideCache;
    public static ResourceLocation lastModelLocation = null;

    private ItemOverrideList() {
        this.overrideBakedModels = Collections.emptyList();
    }

    public ItemOverrideList(ModelBakery modelBakery, BlockModel blockModel, Function<ResourceLocation, IUnbakedModel> function, List<ItemOverride> list) {
        this(modelBakery, blockModel, function, modelBakery.getSpriteMap()::getSprite, list);
    }

    public ItemOverrideList(ModelBakery modelBakery, IUnbakedModel iUnbakedModel, Function<ResourceLocation, IUnbakedModel> function, Function<RenderMaterial, TextureAtlasSprite> function2, List<ItemOverride> list) {
        this.overrideBakedModels = list.stream().map(arg_0 -> ItemOverrideList.lambda$new$0(function, iUnbakedModel, modelBakery, function2, arg_0)).collect(Collectors.toList());
        Collections.reverse(this.overrideBakedModels);
        for (int i = list.size() - 1; i >= 0; --i) {
            this.overrides.add(list.get(i));
        }
        if (this.overrides.size() > 65) {
            this.itemOverrideCache = ItemOverrideCache.make(this.overrides);
        }
    }

    @Nullable
    public IBakedModel getOverrideModel(IBakedModel iBakedModel, ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity) {
        boolean bl = Config.isCustomItems();
        if (bl) {
            lastModelLocation = null;
        }
        if (!this.overrides.isEmpty()) {
            Integer n;
            if (this.itemOverrideCache != null && (n = this.itemOverrideCache.getModelIndex(itemStack, clientWorld, livingEntity)) != null) {
                int n2 = n;
                if (n2 >= 0 && n2 < this.overrideBakedModels.size()) {
                    IBakedModel iBakedModel2;
                    if (bl) {
                        lastModelLocation = this.overrides.get(n2).getLocation();
                    }
                    if ((iBakedModel2 = this.overrideBakedModels.get(n2)) != null) {
                        return iBakedModel2;
                    }
                }
                return iBakedModel;
            }
            for (int i = 0; i < this.overrides.size(); ++i) {
                ItemOverride itemOverride = this.overrides.get(i);
                if (!itemOverride.matchesOverride(itemStack, clientWorld, livingEntity)) continue;
                IBakedModel iBakedModel3 = this.overrideBakedModels.get(i);
                if (bl) {
                    lastModelLocation = itemOverride.getLocation();
                }
                if (this.itemOverrideCache != null) {
                    this.itemOverrideCache.putModelIndex(itemStack, clientWorld, livingEntity, i);
                }
                if (iBakedModel3 == null) {
                    return iBakedModel;
                }
                return iBakedModel3;
            }
            if (this.itemOverrideCache != null) {
                this.itemOverrideCache.putModelIndex(itemStack, clientWorld, livingEntity, ItemOverrideCache.INDEX_NONE);
            }
        }
        return iBakedModel;
    }

    public ImmutableList<ItemOverride> getOverrides() {
        return ImmutableList.copyOf(this.overrides);
    }

    private static IBakedModel lambda$new$0(Function function, IUnbakedModel iUnbakedModel, ModelBakery modelBakery, Function function2, ItemOverride itemOverride) {
        IUnbakedModel iUnbakedModel2 = (IUnbakedModel)function.apply(itemOverride.getLocation());
        return Objects.equals(iUnbakedModel2, iUnbakedModel) ? null : modelBakery.getBakedModel(itemOverride.getLocation(), ModelRotation.X0_Y0, function2);
    }
}

