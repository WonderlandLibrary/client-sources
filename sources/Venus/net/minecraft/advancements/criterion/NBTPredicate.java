/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.JSONUtils;

public class NBTPredicate {
    public static final NBTPredicate ANY = new NBTPredicate(null);
    @Nullable
    private final CompoundNBT tag;

    public NBTPredicate(@Nullable CompoundNBT compoundNBT) {
        this.tag = compoundNBT;
    }

    public boolean test(ItemStack itemStack) {
        return this == ANY ? true : this.test(itemStack.getTag());
    }

    public boolean test(Entity entity2) {
        return this == ANY ? true : this.test(NBTPredicate.writeToNBTWithSelectedItem(entity2));
    }

    public boolean test(@Nullable INBT iNBT) {
        if (iNBT == null) {
            return this == ANY;
        }
        return this.tag == null || NBTUtil.areNBTEquals(this.tag, iNBT, true);
    }

    public JsonElement serialize() {
        return this != ANY && this.tag != null ? new JsonPrimitive(this.tag.toString()) : JsonNull.INSTANCE;
    }

    public static NBTPredicate deserialize(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            CompoundNBT compoundNBT;
            try {
                compoundNBT = JsonToNBT.getTagFromJson(JSONUtils.getString(jsonElement, "nbt"));
            } catch (CommandSyntaxException commandSyntaxException) {
                throw new JsonSyntaxException("Invalid nbt tag: " + commandSyntaxException.getMessage());
            }
            return new NBTPredicate(compoundNBT);
        }
        return ANY;
    }

    public static CompoundNBT writeToNBTWithSelectedItem(Entity entity2) {
        ItemStack itemStack;
        CompoundNBT compoundNBT = entity2.writeWithoutTypeId(new CompoundNBT());
        if (entity2 instanceof PlayerEntity && !(itemStack = ((PlayerEntity)entity2).inventory.getCurrentItem()).isEmpty()) {
            compoundNBT.put("SelectedItem", itemStack.write(new CompoundNBT()));
        }
        return compoundNBT;
    }
}

