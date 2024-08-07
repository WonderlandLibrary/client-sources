package net.minecraft.world.storage.loot;

import java.util.Collection;
import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class LootEntryEmpty extends LootEntry {
	public LootEntryEmpty(int weightIn, int qualityIn, LootCondition[] conditionsIn) {
		super(weightIn, qualityIn, conditionsIn);
	}

	@Override
	public void addLoot(Collection<ItemStack> stacks, Random rand, LootContext context) {
	}

	@Override
	protected void serialize(JsonObject json, JsonSerializationContext context) {
	}

	public static LootEntryEmpty deserialize(JsonObject object, JsonDeserializationContext deserializationContext, int weightIn, int qualityIn, LootCondition[] conditionsIn) {
		return new LootEntryEmpty(weightIn, qualityIn, conditionsIn);
	}
}
