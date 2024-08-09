/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.authlib.GameProfile;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.JSONUtils;

public class FillPlayerHead
extends LootFunction {
    private final LootContext.EntityTarget field_215902_a;

    public FillPlayerHead(ILootCondition[] iLootConditionArray, LootContext.EntityTarget entityTarget) {
        super(iLootConditionArray);
        this.field_215902_a = entityTarget;
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.FILL_PLAYER_HEAD;
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(this.field_215902_a.getParameter());
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        Entity entity2;
        if (itemStack.getItem() == Items.PLAYER_HEAD && (entity2 = lootContext.get(this.field_215902_a.getParameter())) instanceof PlayerEntity) {
            GameProfile gameProfile = ((PlayerEntity)entity2).getGameProfile();
            itemStack.getOrCreateTag().put("SkullOwner", NBTUtil.writeGameProfile(new CompoundNBT(), gameProfile));
        }
        return itemStack;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends LootFunction.Serializer<FillPlayerHead> {
        @Override
        public void serialize(JsonObject jsonObject, FillPlayerHead fillPlayerHead, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, fillPlayerHead, jsonSerializationContext);
            jsonObject.add("entity", jsonSerializationContext.serialize((Object)fillPlayerHead.field_215902_a));
        }

        @Override
        public FillPlayerHead deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            LootContext.EntityTarget entityTarget = JSONUtils.deserializeClass(jsonObject, "entity", jsonDeserializationContext, LootContext.EntityTarget.class);
            return new FillPlayerHead(iLootConditionArray, entityTarget);
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void serialize(JsonObject jsonObject, LootFunction lootFunction, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (FillPlayerHead)lootFunction, jsonSerializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (FillPlayerHead)object, jsonSerializationContext);
        }
    }
}

