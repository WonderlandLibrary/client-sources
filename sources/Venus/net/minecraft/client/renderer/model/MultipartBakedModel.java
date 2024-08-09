/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import org.apache.commons.lang3.tuple.Pair;

public class MultipartBakedModel
implements IBakedModel {
    private final List<Pair<Predicate<BlockState>, IBakedModel>> selectors;
    protected final boolean ambientOcclusion;
    protected final boolean gui3D;
    protected final boolean isSideLit;
    protected final TextureAtlasSprite particleTexture;
    protected final ItemCameraTransforms cameraTransforms;
    protected final ItemOverrideList overrides;
    private final Map<BlockState, BitSet> bitSetCache = new Object2ObjectOpenCustomHashMap<BlockState, BitSet>(Util.identityHashStrategy());

    public MultipartBakedModel(List<Pair<Predicate<BlockState>, IBakedModel>> list) {
        this.selectors = list;
        IBakedModel iBakedModel = list.iterator().next().getRight();
        this.ambientOcclusion = iBakedModel.isAmbientOcclusion();
        this.gui3D = iBakedModel.isGui3d();
        this.isSideLit = iBakedModel.isSideLit();
        this.particleTexture = iBakedModel.getParticleTexture();
        this.cameraTransforms = iBakedModel.getItemCameraTransforms();
        this.overrides = iBakedModel.getOverrides();
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState blockState, @Nullable Direction direction, Random random2) {
        if (blockState == null) {
            return Collections.emptyList();
        }
        BitSet bitSet = this.bitSetCache.get(blockState);
        if (bitSet == null) {
            bitSet = new BitSet();
            for (int i = 0; i < this.selectors.size(); ++i) {
                Pair<Predicate<BlockState>, IBakedModel> pair = this.selectors.get(i);
                if (!pair.getLeft().test(blockState)) continue;
                bitSet.set(i);
            }
            this.bitSetCache.put(blockState, bitSet);
        }
        ArrayList<BakedQuad> arrayList = Lists.newArrayList();
        long l = random2.nextLong();
        for (int i = 0; i < bitSet.length(); ++i) {
            if (!bitSet.get(i)) continue;
            arrayList.addAll(this.selectors.get(i).getRight().getQuads(blockState, direction, new Random(l)));
        }
        return arrayList;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return this.ambientOcclusion;
    }

    @Override
    public boolean isGui3d() {
        return this.gui3D;
    }

    @Override
    public boolean isSideLit() {
        return this.isSideLit;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return this.particleTexture;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return this.cameraTransforms;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return this.overrides;
    }

    public static class Builder {
        private final List<Pair<Predicate<BlockState>, IBakedModel>> selectors = Lists.newArrayList();

        public void putModel(Predicate<BlockState> predicate, IBakedModel iBakedModel) {
            this.selectors.add(Pair.of(predicate, iBakedModel));
        }

        public IBakedModel build() {
            return new MultipartBakedModel(this.selectors);
        }
    }
}

