package net.minecraft.world.storage.loot.functions;

import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class SetCount extends LootFunction {
	private final RandomValueRange countRange;

	public SetCount(LootCondition[] conditionsIn, RandomValueRange countRangeIn) {
		super(conditionsIn);
		this.countRange = countRangeIn;
	}

	@Override
	public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
		stack.stackSize = this.countRange.generateInt(rand);
		return stack;
	}

	public static class Serializer extends LootFunction.Serializer<SetCount> {
		protected Serializer() {
			super(new ResourceLocation("set_count"), SetCount.class);
		}

		@Override
		public void serialize(JsonObject object, SetCount functionClazz, JsonSerializationContext serializationContext) {
			object.add("count", serializationContext.serialize(functionClazz.countRange));
		}

		@Override
		public SetCount deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
			return new SetCount(conditionsIn, JsonUtils.deserializeClass(object, "count", deserializationContext, RandomValueRange.class));
		}
	}
}
