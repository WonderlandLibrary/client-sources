/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ApplyBonus
extends LootFunction {
    private static final Map<ResourceLocation, IFormulaDeserializer> field_215875_a = Maps.newHashMap();
    private final Enchantment enchantment;
    private final IFormula field_215877_d;

    private ApplyBonus(ILootCondition[] iLootConditionArray, Enchantment enchantment, IFormula iFormula) {
        super(iLootConditionArray);
        this.enchantment = enchantment;
        this.field_215877_d = iFormula;
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.APPLY_BONUS;
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(LootParameters.TOOL);
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        ItemStack itemStack2 = lootContext.get(LootParameters.TOOL);
        if (itemStack2 != null) {
            int n = EnchantmentHelper.getEnchantmentLevel(this.enchantment, itemStack2);
            int n2 = this.field_215877_d.func_216204_a(lootContext.getRandom(), itemStack.getCount(), n);
            itemStack.setCount(n2);
        }
        return itemStack;
    }

    public static LootFunction.Builder<?> binomialWithBonusCount(Enchantment enchantment, float f, int n) {
        return ApplyBonus.builder(arg_0 -> ApplyBonus.lambda$binomialWithBonusCount$0(enchantment, n, f, arg_0));
    }

    public static LootFunction.Builder<?> oreDrops(Enchantment enchantment) {
        return ApplyBonus.builder(arg_0 -> ApplyBonus.lambda$oreDrops$1(enchantment, arg_0));
    }

    public static LootFunction.Builder<?> uniformBonusCount(Enchantment enchantment) {
        return ApplyBonus.builder(arg_0 -> ApplyBonus.lambda$uniformBonusCount$2(enchantment, arg_0));
    }

    public static LootFunction.Builder<?> uniformBonusCount(Enchantment enchantment, int n) {
        return ApplyBonus.builder(arg_0 -> ApplyBonus.lambda$uniformBonusCount$3(enchantment, n, arg_0));
    }

    private static ILootFunction lambda$uniformBonusCount$3(Enchantment enchantment, int n, ILootCondition[] iLootConditionArray) {
        return new ApplyBonus(iLootConditionArray, enchantment, new UniformBonusCountFormula(n));
    }

    private static ILootFunction lambda$uniformBonusCount$2(Enchantment enchantment, ILootCondition[] iLootConditionArray) {
        return new ApplyBonus(iLootConditionArray, enchantment, new UniformBonusCountFormula(1));
    }

    private static ILootFunction lambda$oreDrops$1(Enchantment enchantment, ILootCondition[] iLootConditionArray) {
        return new ApplyBonus(iLootConditionArray, enchantment, new OreDropsFormula());
    }

    private static ILootFunction lambda$binomialWithBonusCount$0(Enchantment enchantment, int n, float f, ILootCondition[] iLootConditionArray) {
        return new ApplyBonus(iLootConditionArray, enchantment, new BinomialWithBonusCountFormula(n, f));
    }

    static {
        field_215875_a.put(BinomialWithBonusCountFormula.field_216211_a, BinomialWithBonusCountFormula::func_216210_a);
        field_215875_a.put(OreDropsFormula.field_216206_a, OreDropsFormula::func_216205_a);
        field_215875_a.put(UniformBonusCountFormula.field_216208_a, UniformBonusCountFormula::func_216207_a);
    }

    static interface IFormula {
        public int func_216204_a(Random var1, int var2, int var3);

        public void func_216202_a(JsonObject var1, JsonSerializationContext var2);

        public ResourceLocation func_216203_a();
    }

    static final class UniformBonusCountFormula
    implements IFormula {
        public static final ResourceLocation field_216208_a = new ResourceLocation("uniform_bonus_count");
        private final int bonusMultiplier;

        public UniformBonusCountFormula(int n) {
            this.bonusMultiplier = n;
        }

        @Override
        public int func_216204_a(Random random2, int n, int n2) {
            return n + random2.nextInt(this.bonusMultiplier * n2 + 1);
        }

        @Override
        public void func_216202_a(JsonObject jsonObject, JsonSerializationContext jsonSerializationContext) {
            jsonObject.addProperty("bonusMultiplier", this.bonusMultiplier);
        }

        public static IFormula func_216207_a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            int n = JSONUtils.getInt(jsonObject, "bonusMultiplier");
            return new UniformBonusCountFormula(n);
        }

        @Override
        public ResourceLocation func_216203_a() {
            return field_216208_a;
        }
    }

    static final class OreDropsFormula
    implements IFormula {
        public static final ResourceLocation field_216206_a = new ResourceLocation("ore_drops");

        private OreDropsFormula() {
        }

        @Override
        public int func_216204_a(Random random2, int n, int n2) {
            if (n2 > 0) {
                int n3 = random2.nextInt(n2 + 2) - 1;
                if (n3 < 0) {
                    n3 = 0;
                }
                return n * (n3 + 1);
            }
            return n;
        }

        @Override
        public void func_216202_a(JsonObject jsonObject, JsonSerializationContext jsonSerializationContext) {
        }

        public static IFormula func_216205_a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return new OreDropsFormula();
        }

        @Override
        public ResourceLocation func_216203_a() {
            return field_216206_a;
        }
    }

    static final class BinomialWithBonusCountFormula
    implements IFormula {
        public static final ResourceLocation field_216211_a = new ResourceLocation("binomial_with_bonus_count");
        private final int extra;
        private final float probability;

        public BinomialWithBonusCountFormula(int n, float f) {
            this.extra = n;
            this.probability = f;
        }

        @Override
        public int func_216204_a(Random random2, int n, int n2) {
            for (int i = 0; i < n2 + this.extra; ++i) {
                if (!(random2.nextFloat() < this.probability)) continue;
                ++n;
            }
            return n;
        }

        @Override
        public void func_216202_a(JsonObject jsonObject, JsonSerializationContext jsonSerializationContext) {
            jsonObject.addProperty("extra", this.extra);
            jsonObject.addProperty("probability", Float.valueOf(this.probability));
        }

        public static IFormula func_216210_a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            int n = JSONUtils.getInt(jsonObject, "extra");
            float f = JSONUtils.getFloat(jsonObject, "probability");
            return new BinomialWithBonusCountFormula(n, f);
        }

        @Override
        public ResourceLocation func_216203_a() {
            return field_216211_a;
        }
    }

    static interface IFormulaDeserializer {
        public IFormula deserialize(JsonObject var1, JsonDeserializationContext var2);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends LootFunction.Serializer<ApplyBonus> {
        @Override
        public void serialize(JsonObject jsonObject, ApplyBonus applyBonus, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, applyBonus, jsonSerializationContext);
            jsonObject.addProperty("enchantment", Registry.ENCHANTMENT.getKey(applyBonus.enchantment).toString());
            jsonObject.addProperty("formula", applyBonus.field_215877_d.func_216203_a().toString());
            JsonObject jsonObject2 = new JsonObject();
            applyBonus.field_215877_d.func_216202_a(jsonObject2, jsonSerializationContext);
            if (jsonObject2.size() > 0) {
                jsonObject.add("parameters", jsonObject2);
            }
        }

        @Override
        public ApplyBonus deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "enchantment"));
            Enchantment enchantment = Registry.ENCHANTMENT.getOptional(resourceLocation).orElseThrow(() -> Serializer.lambda$deserialize$0(resourceLocation));
            ResourceLocation resourceLocation2 = new ResourceLocation(JSONUtils.getString(jsonObject, "formula"));
            IFormulaDeserializer iFormulaDeserializer = field_215875_a.get(resourceLocation2);
            if (iFormulaDeserializer == null) {
                throw new JsonParseException("Invalid formula id: " + resourceLocation2);
            }
            IFormula iFormula = jsonObject.has("parameters") ? iFormulaDeserializer.deserialize(JSONUtils.getJsonObject(jsonObject, "parameters"), jsonDeserializationContext) : iFormulaDeserializer.deserialize(new JsonObject(), jsonDeserializationContext);
            return new ApplyBonus(iLootConditionArray, enchantment, iFormula);
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void serialize(JsonObject jsonObject, LootFunction lootFunction, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (ApplyBonus)lootFunction, jsonSerializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (ApplyBonus)object, jsonSerializationContext);
        }

        private static JsonParseException lambda$deserialize$0(ResourceLocation resourceLocation) {
            return new JsonParseException("Invalid enchantment id: " + resourceLocation);
        }
    }
}

