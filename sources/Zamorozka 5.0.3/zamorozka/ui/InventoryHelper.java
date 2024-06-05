package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

import java.util.ArrayList;

public class InventoryHelper {
    private Minecraft mc = Minecraft.getMinecraft();

    public ItemStack getBestWeapon() {
        ItemStack best = null;
        for (int i = 0; i < this.mc.player.inventoryContainer.getInventory().size(); ++i) {
            ItemStack itemStack = (ItemStack)this.mc.player.inventoryContainer.getInventory().get(i);
            if (itemStack == null || !(itemStack.getItem() instanceof ItemTool) && !(itemStack.getItem() instanceof ItemSword) || best != null && !(itemStack.getItemDamage() > best.getItemDamage())) continue;
            best = itemStack;
        }
        return best;
    }

    public ItemStack getBestItemByDurability(String className) {
        ItemStack best = null;
        for (int i = 0; i < this.mc.player.inventoryContainer.getInventory().size(); ++i) {
            boolean check = false;
            ItemStack itemStack = (ItemStack)this.mc.player.inventoryContainer.getInventory().get(i);
            if (itemStack == null) continue;
            Class clazz = null;
            try {
                clazz = Class.forName((String)("net.minecraft.item." + className));
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
            if (itemStack.getItem().getClass() != clazz) continue;
            boolean bl = best == null ? true : (check = itemStack.getMaxDamage() > best.getMaxDamage());
            if (!check) continue;
            best = itemStack;
        }
        return best;
    }

    public ItemStack getBestArmorPiece(String type) {
        ItemStack best = null;
        for (int i = 0; i < this.mc.player.inventoryContainer.getInventory().size(); ++i) {
            boolean checkProt = false;
            ItemStack itemStack = (ItemStack)this.mc.player.inventoryContainer.getInventory().get(i);
            if (itemStack == null || !itemStack.getUnlocalizedName().toLowerCase().contains((CharSequence)type.toLowerCase())) continue;
            int protLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(0), (ItemStack)itemStack);
            int protLvlBest = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(0), best);
            boolean bl = best != null ? (protLvl > 0 ? itemStack.getMaxDamage() >= best.getMaxDamage() && protLvl > protLvlBest || itemStack.getMaxDamage() > best.getMaxDamage() : itemStack.getMaxDamage() > best.getMaxDamage()) : (checkProt = false);
            if (best != null && !checkProt) continue;
            best = itemStack;
        }
        return best;
    }

    public ArrayList<ItemStack> getBlockStacks(int maxBlockStacks) {
        ArrayList arrayList = new ArrayList();
        int cnt = 0;
        for (int i = this.mc.player.inventoryContainer.getInventory().size() - 1; i > 0; --i) {
            ItemBlock item;
            if (cnt - 1 == maxBlockStacks - 1) break;
            ItemStack itemStack = (ItemStack)this.mc.player.inventoryContainer.getInventory().get(i);
            if (itemStack == null || !(itemStack.getItem() instanceof ItemBlock) || (item = (ItemBlock)itemStack.getItem()).getBlock() == Blocks.WEB || item.getBlock() == Blocks.FLOWER_POT) continue;
            arrayList.add((Object)itemStack);
            ++cnt;
        }
        return arrayList;
    }
}
