/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.util.INameable;
import net.minecraft.util.JSONUtils;

public class CopyName
extends LootFunction {
    private final Source source;

    private CopyName(ILootCondition[] iLootConditionArray, Source source) {
        super(iLootConditionArray);
        this.source = source;
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.COPY_NAME;
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(this.source.parameter);
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        INameable iNameable;
        Object obj = lootContext.get(this.source.parameter);
        if (obj instanceof INameable && (iNameable = (INameable)obj).hasCustomName()) {
            itemStack.setDisplayName(iNameable.getDisplayName());
        }
        return itemStack;
    }

    public static LootFunction.Builder<?> builder(Source source) {
        return CopyName.builder(arg_0 -> CopyName.lambda$builder$0(source, arg_0));
    }

    private static ILootFunction lambda$builder$0(Source source, ILootCondition[] iLootConditionArray) {
        return new CopyName(iLootConditionArray, source);
    }

    public static enum Source {
        THIS("this", LootParameters.THIS_ENTITY),
        KILLER("killer", LootParameters.KILLER_ENTITY),
        KILLER_PLAYER("killer_player", LootParameters.LAST_DAMAGE_PLAYER),
        BLOCK_ENTITY("block_entity", LootParameters.BLOCK_ENTITY);

        public final String name;
        public final LootParameter<?> parameter;

        private Source(String string2, LootParameter<?> lootParameter) {
            this.name = string2;
            this.parameter = lootParameter;
        }

        public static Source byName(String string) {
            for (Source source : Source.values()) {
                if (!source.name.equals(string)) continue;
                return source;
            }
            throw new IllegalArgumentException("Invalid name source " + string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends LootFunction.Serializer<CopyName> {
        @Override
        public void serialize(JsonObject jsonObject, CopyName copyName, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, copyName, jsonSerializationContext);
            jsonObject.addProperty("source", copyName.source.name);
        }

        @Override
        public CopyName deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            Source source = Source.byName(JSONUtils.getString(jsonObject, "source"));
            return new CopyName(iLootConditionArray, source);
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void serialize(JsonObject jsonObject, LootFunction lootFunction, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (CopyName)lootFunction, jsonSerializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (CopyName)object, jsonSerializationContext);
        }
    }
}

