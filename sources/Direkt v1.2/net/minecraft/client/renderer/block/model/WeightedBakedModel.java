package net.minecraft.client.renderer.block.model;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandom;

public class WeightedBakedModel implements IBakedModel {
	private final int totalWeight;
	private final List<WeightedBakedModel.WeightedModel> models;
	private final IBakedModel baseModel;

	public WeightedBakedModel(List<WeightedBakedModel.WeightedModel> modelsIn) {
		this.models = modelsIn;
		this.totalWeight = WeightedRandom.getTotalWeight(modelsIn);
		this.baseModel = modelsIn.get(0).model;
	}

	private IBakedModel getRandomModel(long p_188627_1_) {
		return WeightedRandom.getRandomItem(this.models, Math.abs((int) p_188627_1_ >> 16) % this.totalWeight).model;
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		return this.getRandomModel(rand).getQuads(state, side, rand);
	}

	@Override
	public boolean isAmbientOcclusion() {
		return this.baseModel.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return this.baseModel.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return this.baseModel.isBuiltInRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return this.baseModel.getParticleTexture();
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return this.baseModel.getItemCameraTransforms();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return this.baseModel.getOverrides();
	}

	public static class Builder {
		private final List<WeightedBakedModel.WeightedModel> listItems = Lists.<WeightedBakedModel.WeightedModel> newArrayList();

		public WeightedBakedModel.Builder add(IBakedModel p_177677_1_, int p_177677_2_) {
			this.listItems.add(new WeightedBakedModel.WeightedModel(p_177677_1_, p_177677_2_));
			return this;
		}

		public WeightedBakedModel build() {
			Collections.sort(this.listItems);
			return new WeightedBakedModel(this.listItems);
		}

		public IBakedModel first() {
			return this.listItems.get(0).model;
		}
	}

	static class WeightedModel extends WeightedRandom.Item implements Comparable<WeightedBakedModel.WeightedModel> {
		protected final IBakedModel model;

		public WeightedModel(IBakedModel modelIn, int itemWeightIn) {
			super(itemWeightIn);
			this.model = modelIn;
		}

		@Override
		public int compareTo(WeightedBakedModel.WeightedModel p_compareTo_1_) {
			return ComparisonChain.start().compare(p_compareTo_1_.itemWeight, this.itemWeight).result();
		}

		@Override
		public String toString() {
			return "MyWeighedRandomItem{weight=" + this.itemWeight + ", model=" + this.model + '}';
		}
	}
}
