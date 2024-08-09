/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;

public class ItemInput
implements Predicate<ItemStack> {
    private static final Dynamic2CommandExceptionType STACK_TOO_LARGE = new Dynamic2CommandExceptionType(ItemInput::lambda$static$0);
    private final Item item;
    @Nullable
    private final CompoundNBT tag;

    public ItemInput(Item item, @Nullable CompoundNBT compoundNBT) {
        this.item = item;
        this.tag = compoundNBT;
    }

    public Item getItem() {
        return this.item;
    }

    @Override
    public boolean test(ItemStack itemStack) {
        return itemStack.getItem() == this.item && NBTUtil.areNBTEquals(this.tag, itemStack.getTag(), true);
    }

    public ItemStack createStack(int n, boolean bl) throws CommandSyntaxException {
        ItemStack itemStack = new ItemStack(this.item, n);
        if (this.tag != null) {
            itemStack.setTag(this.tag);
        }
        if (bl && n > itemStack.getMaxStackSize()) {
            throw STACK_TOO_LARGE.create(Registry.ITEM.getKey(this.item), itemStack.getMaxStackSize());
        }
        return itemStack;
    }

    public String serialize() {
        StringBuilder stringBuilder = new StringBuilder(Registry.ITEM.getId(this.item));
        if (this.tag != null) {
            stringBuilder.append(this.tag);
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean test(Object object) {
        return this.test((ItemStack)object);
    }

    private static Message lambda$static$0(Object object, Object object2) {
        return new TranslationTextComponent("arguments.item.overstacked", object, object2);
    }
}

