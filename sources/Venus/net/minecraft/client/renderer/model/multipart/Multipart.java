/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model.multipart;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BlockModelDefinition;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.MultipartBakedModel;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.model.VariantList;
import net.minecraft.client.renderer.model.multipart.Selector;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;

public class Multipart
implements IUnbakedModel {
    private final StateContainer<Block, BlockState> stateContainer;
    private final List<Selector> selectors;

    public Multipart(StateContainer<Block, BlockState> stateContainer, List<Selector> list) {
        this.stateContainer = stateContainer;
        this.selectors = list;
    }

    public List<Selector> getSelectors() {
        return this.selectors;
    }

    public Set<VariantList> getVariants() {
        HashSet<VariantList> hashSet = Sets.newHashSet();
        for (Selector selector : this.selectors) {
            hashSet.add(selector.getVariantList());
        }
        return hashSet;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Multipart)) {
            return true;
        }
        Multipart multipart = (Multipart)object;
        return Objects.equals(this.stateContainer, multipart.stateContainer) && Objects.equals(this.selectors, multipart.selectors);
    }

    public int hashCode() {
        return Objects.hash(this.stateContainer, this.selectors);
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return this.getSelectors().stream().flatMap(Multipart::lambda$getDependencies$0).collect(Collectors.toSet());
    }

    @Override
    public Collection<RenderMaterial> getTextures(Function<ResourceLocation, IUnbakedModel> function, Set<Pair<String, String>> set) {
        return this.getSelectors().stream().flatMap(arg_0 -> Multipart.lambda$getTextures$1(function, set, arg_0)).collect(Collectors.toSet());
    }

    @Override
    @Nullable
    public IBakedModel bakeModel(ModelBakery modelBakery, Function<RenderMaterial, TextureAtlasSprite> function, IModelTransform iModelTransform, ResourceLocation resourceLocation) {
        MultipartBakedModel.Builder builder = new MultipartBakedModel.Builder();
        for (Selector selector : this.getSelectors()) {
            IBakedModel iBakedModel = selector.getVariantList().bakeModel(modelBakery, function, iModelTransform, resourceLocation);
            if (iBakedModel == null) continue;
            builder.putModel(selector.getPredicate(this.stateContainer), iBakedModel);
        }
        return builder.build();
    }

    private static Stream lambda$getTextures$1(Function function, Set set, Selector selector) {
        return selector.getVariantList().getTextures(function, set).stream();
    }

    private static Stream lambda$getDependencies$0(Selector selector) {
        return selector.getVariantList().getDependencies().stream();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Deserializer
    implements JsonDeserializer<Multipart> {
        private final BlockModelDefinition.ContainerHolder containerHolder;

        public Deserializer(BlockModelDefinition.ContainerHolder containerHolder) {
            this.containerHolder = containerHolder;
        }

        @Override
        public Multipart deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new Multipart(this.containerHolder.getStateContainer(), this.getSelectors(jsonDeserializationContext, jsonElement.getAsJsonArray()));
        }

        private List<Selector> getSelectors(JsonDeserializationContext jsonDeserializationContext, JsonArray jsonArray) {
            ArrayList<Selector> arrayList = Lists.newArrayList();
            for (JsonElement jsonElement : jsonArray) {
                arrayList.add((Selector)jsonDeserializationContext.deserialize(jsonElement, (Type)((Object)Selector.class)));
            }
            return arrayList;
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

