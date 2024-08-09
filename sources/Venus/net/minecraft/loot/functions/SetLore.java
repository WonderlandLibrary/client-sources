/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.loot.functions.SetName;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.text.ITextComponent;

public class SetLore
extends LootFunction {
    private final boolean replace;
    private final List<ITextComponent> lore;
    @Nullable
    private final LootContext.EntityTarget field_215947_d;

    public SetLore(ILootCondition[] iLootConditionArray, boolean bl, List<ITextComponent> list, @Nullable LootContext.EntityTarget entityTarget) {
        super(iLootConditionArray);
        this.replace = bl;
        this.lore = ImmutableList.copyOf(list);
        this.field_215947_d = entityTarget;
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.SET_LORE;
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return this.field_215947_d != null ? ImmutableSet.of(this.field_215947_d.getParameter()) : ImmutableSet.of();
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        ListNBT listNBT = this.func_215942_a(itemStack, !this.lore.isEmpty());
        if (listNBT != null) {
            if (this.replace) {
                listNBT.clear();
            }
            UnaryOperator<ITextComponent> unaryOperator = SetName.func_215936_a(lootContext, this.field_215947_d);
            this.lore.stream().map(unaryOperator).map(ITextComponent.Serializer::toJson).map(StringNBT::valueOf).forEach(listNBT::add);
        }
        return itemStack;
    }

    @Nullable
    private ListNBT func_215942_a(ItemStack itemStack, boolean bl) {
        CompoundNBT compoundNBT;
        CompoundNBT compoundNBT2;
        if (itemStack.hasTag()) {
            compoundNBT2 = itemStack.getTag();
        } else {
            if (!bl) {
                return null;
            }
            compoundNBT2 = new CompoundNBT();
            itemStack.setTag(compoundNBT2);
        }
        if (compoundNBT2.contains("display", 1)) {
            compoundNBT = compoundNBT2.getCompound("display");
        } else {
            if (!bl) {
                return null;
            }
            compoundNBT = new CompoundNBT();
            compoundNBT2.put("display", compoundNBT);
        }
        if (compoundNBT.contains("Lore", 0)) {
            return compoundNBT.getList("Lore", 8);
        }
        if (bl) {
            ListNBT listNBT = new ListNBT();
            compoundNBT.put("Lore", listNBT);
            return listNBT;
        }
        return null;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends LootFunction.Serializer<SetLore> {
        @Override
        public void serialize(JsonObject jsonObject, SetLore setLore, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, setLore, jsonSerializationContext);
            jsonObject.addProperty("replace", setLore.replace);
            JsonArray jsonArray = new JsonArray();
            for (ITextComponent iTextComponent : setLore.lore) {
                jsonArray.add(ITextComponent.Serializer.toJsonTree(iTextComponent));
            }
            jsonObject.add("lore", jsonArray);
            if (setLore.field_215947_d != null) {
                jsonObject.add("entity", jsonSerializationContext.serialize((Object)setLore.field_215947_d));
            }
        }

        @Override
        public SetLore deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            boolean bl = JSONUtils.getBoolean(jsonObject, "replace", false);
            List list = Streams.stream(JSONUtils.getJsonArray(jsonObject, "lore")).map(ITextComponent.Serializer::getComponentFromJson).collect(ImmutableList.toImmutableList());
            LootContext.EntityTarget entityTarget = JSONUtils.deserializeClass(jsonObject, "entity", null, jsonDeserializationContext, LootContext.EntityTarget.class);
            return new SetLore(iLootConditionArray, bl, list, entityTarget);
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void serialize(JsonObject jsonObject, LootFunction lootFunction, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (SetLore)lootFunction, jsonSerializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (SetLore)object, jsonSerializationContext);
        }
    }
}

