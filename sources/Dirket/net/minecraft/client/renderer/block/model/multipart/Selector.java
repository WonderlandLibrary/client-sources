package net.minecraft.client.renderer.block.model.multipart;

import java.lang.reflect.Type;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.gson.*;

import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.VariantList;
import net.minecraft.util.JsonUtils;

public class Selector {
	private final ICondition condition;
	private final VariantList variantList;

	public Selector(ICondition conditionIn, VariantList variantListIn) {
		if (conditionIn == null) {
			throw new IllegalArgumentException("Missing condition for selector");
		} else if (variantListIn == null) {
			throw new IllegalArgumentException("Missing variant for selector");
		} else {
			this.condition = conditionIn;
			this.variantList = variantListIn;
		}
	}

	public VariantList getVariantList() {
		return this.variantList;
	}

	public Predicate<IBlockState> getPredicate(BlockStateContainer state) {
		return this.condition.getPredicate(state);
	}

	@Override
	public boolean equals(Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		} else {
			if (p_equals_1_ instanceof Selector) {
				Selector selector = (Selector) p_equals_1_;

				if (this.condition.equals(selector.condition)) { return this.variantList.equals(selector.variantList); }
			}

			return false;
		}
	}

	@Override
	public int hashCode() {
		return (31 * this.condition.hashCode()) + this.variantList.hashCode();
	}

	public static class Deserializer implements JsonDeserializer<Selector> {
		private static final Function<JsonElement, ICondition> FUNCTION_OR_AND = new Function<JsonElement, ICondition>() {
			@Override
			@Nullable
			public ICondition apply(@Nullable JsonElement p_apply_1_) {
				return p_apply_1_ == null ? null : Selector.Deserializer.getOrAndCondition(p_apply_1_.getAsJsonObject());
			}
		};
		private static final Function<Entry<String, JsonElement>, ICondition> FUNCTION_PROPERTY_VALUE = new Function<Entry<String, JsonElement>, ICondition>() {
			@Override
			@Nullable
			public ICondition apply(@Nullable Entry<String, JsonElement> p_apply_1_) {
				return p_apply_1_ == null ? null : Selector.Deserializer.makePropertyValue(p_apply_1_);
			}
		};

		@Override
		public Selector deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
			JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
			return new Selector(this.getWhenCondition(jsonobject), (VariantList) p_deserialize_3_.deserialize(jsonobject.get("apply"), VariantList.class));
		}

		private ICondition getWhenCondition(JsonObject json) {
			return json.has("when") ? getOrAndCondition(JsonUtils.getJsonObject(json, "when")) : ICondition.TRUE;
		}

		@VisibleForTesting
		static ICondition getOrAndCondition(JsonObject json) {
			Set<Entry<String, JsonElement>> set = json.entrySet();

			if (set.isEmpty()) {
				throw new JsonParseException("No elements found in selector");
			} else {
				return set.size() == 1
						? (json.has("OR") ? new ConditionOr(Iterables.transform(JsonUtils.getJsonArray(json, "OR"), FUNCTION_OR_AND))
								: (json.has("AND") ? new ConditionAnd(Iterables.transform(JsonUtils.getJsonArray(json, "AND"), FUNCTION_OR_AND)) : makePropertyValue(set.iterator().next())))
						: new ConditionAnd(Iterables.transform(set, FUNCTION_PROPERTY_VALUE));
			}
		}

		private static ConditionPropertyValue makePropertyValue(Entry<String, JsonElement> entry) {
			return new ConditionPropertyValue(entry.getKey(), entry.getValue().getAsString());
		}
	}
}
