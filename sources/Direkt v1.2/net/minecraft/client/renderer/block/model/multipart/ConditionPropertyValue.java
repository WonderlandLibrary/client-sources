package net.minecraft.client.renderer.block.model.multipart;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.*;
import com.google.common.collect.Iterables;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class ConditionPropertyValue implements ICondition {
	private static final Splitter SPLITTER = Splitter.on('|').omitEmptyStrings();
	private final String key;
	private final String value;

	public ConditionPropertyValue(String keyIn, String valueIn) {
		this.key = keyIn;
		this.value = valueIn;
	}

	@Override
	public Predicate<IBlockState> getPredicate(BlockStateContainer blockState) {
		final IProperty<?> iproperty = blockState.getProperty(this.key);

		if (iproperty == null) {
			throw new RuntimeException(this.toString() + ": Definition: " + blockState + " has no property: " + this.key);
		} else {
			String s = this.value;
			boolean flag = !s.isEmpty() && (s.charAt(0) == 33);

			if (flag) {
				s = s.substring(1);
			}

			List<String> list = SPLITTER.splitToList(s);

			if (list.isEmpty()) {
				throw new RuntimeException(this.toString() + ": has an empty value: " + this.value);
			} else {
				Predicate<IBlockState> predicate;

				if (list.size() == 1) {
					predicate = this.makePredicate(iproperty, s);
				} else {
					predicate = Predicates.or(Iterables.transform(list, new Function<String, Predicate<IBlockState>>() {
						@Override
						@Nullable
						public Predicate<IBlockState> apply(@Nullable String p_apply_1_) {
							return ConditionPropertyValue.this.makePredicate(iproperty, p_apply_1_);
						}
					}));
				}

				return flag ? Predicates.not(predicate) : predicate;
			}
		}
	}

	private Predicate<IBlockState> makePredicate(final IProperty<?> property, String valueIn) {
		final Optional<?> optional = property.parseValue(valueIn);

		if (!optional.isPresent()) {
			throw new RuntimeException(this.toString() + ": has an unknown value: " + this.value);
		} else {
			return new Predicate<IBlockState>() {
				@Override
				public boolean apply(@Nullable IBlockState p_apply_1_) {
					return (p_apply_1_ != null) && p_apply_1_.getValue(property).equals(optional.get());
				}
			};
		}
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("key", this.key).add("value", this.value).toString();
	}
}
