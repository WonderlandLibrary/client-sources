/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class SetLootTable
extends LootFunction {
    private final ResourceLocation field_215928_a;
    private final long field_215929_c;

    private SetLootTable(ILootCondition[] iLootConditionArray, ResourceLocation resourceLocation, long l) {
        super(iLootConditionArray);
        this.field_215928_a = resourceLocation;
        this.field_215929_c = l;
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.SET_LOOT_TABLE;
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        if (itemStack.isEmpty()) {
            return itemStack;
        }
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("LootTable", this.field_215928_a.toString());
        if (this.field_215929_c != 0L) {
            compoundNBT.putLong("LootTableSeed", this.field_215929_c);
        }
        itemStack.getOrCreateTag().put("BlockEntityTag", compoundNBT);
        return itemStack;
    }

    @Override
    public void func_225580_a_(ValidationTracker validationTracker) {
        if (validationTracker.func_227532_a_(this.field_215928_a)) {
            validationTracker.addProblem("Table " + this.field_215928_a + " is recursively called");
        } else {
            super.func_225580_a_(validationTracker);
            LootTable lootTable = validationTracker.func_227539_c_(this.field_215928_a);
            if (lootTable == null) {
                validationTracker.addProblem("Unknown loot table called " + this.field_215928_a);
            } else {
                lootTable.validate(validationTracker.func_227531_a_("->{" + this.field_215928_a + "}", this.field_215928_a));
            }
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends LootFunction.Serializer<SetLootTable> {
        @Override
        public void serialize(JsonObject jsonObject, SetLootTable setLootTable, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, setLootTable, jsonSerializationContext);
            jsonObject.addProperty("name", setLootTable.field_215928_a.toString());
            if (setLootTable.field_215929_c != 0L) {
                jsonObject.addProperty("seed", setLootTable.field_215929_c);
            }
        }

        @Override
        public SetLootTable deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "name"));
            long l = JSONUtils.getLong(jsonObject, "seed", 0L);
            return new SetLootTable(iLootConditionArray, resourceLocation, l);
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void serialize(JsonObject jsonObject, LootFunction lootFunction, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (SetLootTable)lootFunction, jsonSerializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (SetLootTable)object, jsonSerializationContext);
        }
    }
}

