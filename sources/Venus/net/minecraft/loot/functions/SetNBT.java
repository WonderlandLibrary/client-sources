/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.JSONUtils;

public class SetNBT
extends LootFunction {
    private final CompoundNBT tag;

    private SetNBT(ILootCondition[] iLootConditionArray, CompoundNBT compoundNBT) {
        super(iLootConditionArray);
        this.tag = compoundNBT;
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.SET_NBT;
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        itemStack.getOrCreateTag().merge(this.tag);
        return itemStack;
    }

    public static LootFunction.Builder<?> builder(CompoundNBT compoundNBT) {
        return SetNBT.builder(arg_0 -> SetNBT.lambda$builder$0(compoundNBT, arg_0));
    }

    private static ILootFunction lambda$builder$0(CompoundNBT compoundNBT, ILootCondition[] iLootConditionArray) {
        return new SetNBT(iLootConditionArray, compoundNBT);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends LootFunction.Serializer<SetNBT> {
        @Override
        public void serialize(JsonObject jsonObject, SetNBT setNBT, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, setNBT, jsonSerializationContext);
            jsonObject.addProperty("tag", setNBT.tag.toString());
        }

        @Override
        public SetNBT deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            try {
                CompoundNBT compoundNBT = JsonToNBT.getTagFromJson(JSONUtils.getString(jsonObject, "tag"));
                return new SetNBT(iLootConditionArray, compoundNBT);
            } catch (CommandSyntaxException commandSyntaxException) {
                throw new JsonSyntaxException(commandSyntaxException.getMessage());
            }
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void serialize(JsonObject jsonObject, LootFunction lootFunction, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (SetNBT)lootFunction, jsonSerializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (SetNBT)object, jsonSerializationContext);
        }
    }
}

