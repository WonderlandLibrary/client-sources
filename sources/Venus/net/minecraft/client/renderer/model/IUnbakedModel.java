/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public interface IUnbakedModel {
    public Collection<ResourceLocation> getDependencies();

    public Collection<RenderMaterial> getTextures(Function<ResourceLocation, IUnbakedModel> var1, Set<Pair<String, String>> var2);

    @Nullable
    public IBakedModel bakeModel(ModelBakery var1, Function<RenderMaterial, TextureAtlasSprite> var2, IModelTransform var3, ResourceLocation var4);
}

