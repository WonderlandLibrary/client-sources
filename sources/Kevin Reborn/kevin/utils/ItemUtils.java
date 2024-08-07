/*
 * This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package kevin.utils;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;
import java.util.regex.Pattern;

public final class ItemUtils extends MinecraftInstance {

    public static ItemStack createItem(String itemArguments) {
        try {
            itemArguments = itemArguments.replace('&', '§');
            Item item = new Item();
            Item itemInstance = item;
            String[] args = null;
            int i = 1;
            int j = 0;

            for (int mode = 0; mode <= Math.min(12, itemArguments.length() - 2); ++mode) {
                args = itemArguments.substring(mode).split(Pattern.quote(" "));
                ResourceLocation resourcelocation = new ResourceLocation(args[0]);
                item = Item.itemRegistry.getObject(resourcelocation);

                if (item != null)
                    break;
            }

            if (item == null)
                return null;

            if (Objects.requireNonNull(args).length >= 2 && args[1].matches("\\d+"))
                i = Integer.parseInt(args[1]);
            if (args.length >= 3 && args[2].matches("\\d+"))
                j = Integer.parseInt(args[2]);

            ItemStack itemstack = new ItemStack(item, i, j);

            if (args.length >= 4) {
                StringBuilder NBT = new StringBuilder();
                for (int nbtcount = 3; nbtcount < args.length; ++nbtcount)
                    NBT.append(" ").append(args[nbtcount]);
                itemstack.setTagCompound(JsonToNBT.getTagFromJson(NBT.toString()));
            }

            return itemstack;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static int getEnchantment(ItemStack itemStack, Enchantment enchantment) {
        if (itemStack == null || itemStack.getEnchantmentTagList() == null || itemStack.getEnchantmentTagList().hasNoTags())
            return 0;

        for (int i = 0; i < itemStack.getEnchantmentTagList().tagCount(); i++) {
            final NBTTagCompound tagCompound = itemStack.getEnchantmentTagList().getCompoundTagAt(i);

            if ((tagCompound.hasKey("ench") && tagCompound.getShort("ench") == enchantment.effectId) || (tagCompound.hasKey("id") && tagCompound.getShort("id") == enchantment.effectId))
                return tagCompound.getShort("lvl");
        }

        return 0;
    }

    public static int getEnchantmentCount(ItemStack itemStack) {
        if (itemStack == null || itemStack.getEnchantmentTagList() == null || itemStack.getEnchantmentTagList().hasNoTags())
            return 0;

        int c = 0;

        for (int i = 0; i < itemStack.getEnchantmentTagList().tagCount(); i++) {
            NBTTagCompound tagCompound = itemStack.getEnchantmentTagList().getCompoundTagAt(i);

            if ((tagCompound.hasKey("ench") || tagCompound.hasKey("id")))
                c++;
        }

        return c;
    }

    public static boolean isStackEmpty(ItemStack stack) {
        return stack == null;
    }
}
