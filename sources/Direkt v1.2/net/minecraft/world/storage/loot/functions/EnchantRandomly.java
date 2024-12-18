package net.minecraft.world.storage.loot.functions;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.gson.*;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class EnchantRandomly extends LootFunction {
	private static final Logger LOGGER = LogManager.getLogger();
	@Nullable
	private final List<Enchantment> enchantments;

	public EnchantRandomly(LootCondition[] conditionsIn, @Nullable List<Enchantment> enchantmentsIn) {
		super(conditionsIn);
		this.enchantments = enchantmentsIn;
	}

	@Override
	public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
		Enchantment enchantment;

		if ((this.enchantments != null) && !this.enchantments.isEmpty()) {
			enchantment = this.enchantments.get(rand.nextInt(this.enchantments.size()));
		} else {
			List<Enchantment> list = Lists.<Enchantment> newArrayList();

			for (Enchantment enchantment1 : Enchantment.REGISTRY) {
				if ((stack.getItem() == Items.BOOK) || enchantment1.canApply(stack)) {
					list.add(enchantment1);
				}
			}

			if (list.isEmpty()) {
				LOGGER.warn("Couldn\'t find a compatible enchantment for {}", new Object[] { stack });
				return stack;
			}

			enchantment = list.get(rand.nextInt(list.size()));
		}

		int i = MathHelper.getRandomIntegerInRange(rand, enchantment.getMinLevel(), enchantment.getMaxLevel());

		if (stack.getItem() == Items.BOOK) {
			stack.setItem(Items.ENCHANTED_BOOK);
			Items.ENCHANTED_BOOK.addEnchantment(stack, new EnchantmentData(enchantment, i));
		} else {
			stack.addEnchantment(enchantment, i);
		}

		return stack;
	}

	public static class Serializer extends LootFunction.Serializer<EnchantRandomly> {
		public Serializer() {
			super(new ResourceLocation("enchant_randomly"), EnchantRandomly.class);
		}

		@Override
		public void serialize(JsonObject object, EnchantRandomly functionClazz, JsonSerializationContext serializationContext) {
			if ((functionClazz.enchantments != null) && !functionClazz.enchantments.isEmpty()) {
				JsonArray jsonarray = new JsonArray();

				for (Enchantment enchantment : functionClazz.enchantments) {
					ResourceLocation resourcelocation = Enchantment.REGISTRY.getNameForObject(enchantment);

					if (resourcelocation == null) { throw new IllegalArgumentException("Don\'t know how to serialize enchantment " + enchantment); }

					jsonarray.add(new JsonPrimitive(resourcelocation.toString()));
				}

				object.add("enchantments", jsonarray);
			}
		}

		@Override
		public EnchantRandomly deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
			List<Enchantment> list = null;

			if (object.has("enchantments")) {
				list = Lists.<Enchantment> newArrayList();

				for (JsonElement jsonelement : JsonUtils.getJsonArray(object, "enchantments")) {
					String s = JsonUtils.getString(jsonelement, "enchantment");
					Enchantment enchantment = Enchantment.REGISTRY.getObject(new ResourceLocation(s));

					if (enchantment == null) { throw new JsonSyntaxException("Unknown enchantment \'" + s + "\'"); }

					list.add(enchantment);
				}
			}

			return new EnchantRandomly(conditionsIn, list);
		}
	}
}
