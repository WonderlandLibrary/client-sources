/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootPredicateManager;
import net.minecraft.loot.LootSerializers;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConditionArrayParser {
    private static final Logger field_234045_a_ = LogManager.getLogger();
    private final ResourceLocation field_234046_b_;
    private final LootPredicateManager field_234047_c_;
    private final Gson field_234048_d_ = LootSerializers.func_237386_a_().create();

    public ConditionArrayParser(ResourceLocation resourceLocation, LootPredicateManager lootPredicateManager) {
        this.field_234046_b_ = resourceLocation;
        this.field_234047_c_ = lootPredicateManager;
    }

    public final ILootCondition[] func_234050_a_(JsonArray jsonArray, String string, LootParameterSet lootParameterSet) {
        ILootCondition[] iLootConditionArray = this.field_234048_d_.fromJson((JsonElement)jsonArray, ILootCondition[].class);
        ValidationTracker validationTracker = new ValidationTracker(lootParameterSet, this.field_234047_c_::func_227517_a_, ConditionArrayParser::lambda$func_234050_a_$0);
        for (ILootCondition iLootCondition : iLootConditionArray) {
            iLootCondition.func_225580_a_(validationTracker);
            validationTracker.getProblems().forEach((arg_0, arg_1) -> ConditionArrayParser.lambda$func_234050_a_$1(string, arg_0, arg_1));
        }
        return iLootConditionArray;
    }

    public ResourceLocation func_234049_a_() {
        return this.field_234046_b_;
    }

    private static void lambda$func_234050_a_$1(String string, String string2, String string3) {
        field_234045_a_.warn("Found validation problem in advancement trigger {}/{}: {}", (Object)string, (Object)string2, (Object)string3);
    }

    private static LootTable lambda$func_234050_a_$0(ResourceLocation resourceLocation) {
        return null;
    }
}

