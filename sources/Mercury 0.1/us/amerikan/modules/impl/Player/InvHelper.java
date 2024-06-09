/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Player;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import us.amerikan.amerikan;
import us.amerikan.events.EventUpdate;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;
import us.amerikan.utils.TimeHelper;

public class InvHelper
extends Module {
    private static Random random = new Random();
    private ArrayList<Integer> uselessItems = new ArrayList();
    private ArrayList<Item> swords = new ArrayList();
    private ArrayList<Item> items = new ArrayList();
    private long lastClick = 0L;
    private int delay = 200;
    private boolean dropWholeStack = true;
    private boolean instant = false;
    private boolean dubOnly = true;
    private boolean invOnly = true;
    int bestSwordDamage = -1;
    TimeHelper time = new TimeHelper();
    private int i;

    public InvHelper() {
        super("InvHelper", "", 0, Category.MISC);
        amerikan.setmgr.rSetting(new Setting("Help Delay", this, 50.0, 0.0, 200.0, true));
        amerikan.setmgr.rSetting(new Setting("DropWholeStack", this, true));
        amerikan.setmgr.rSetting(new Setting("Instant ", this, false));
        amerikan.setmgr.rSetting(new Setting("Inv Only", this, true));
        amerikan.setmgr.rSetting(new Setting("Dublicate Only", this, false));
        amerikan.setmgr.rSetting(new Setting("Ignore Blocks", this, true));
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        super.onEnable();
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        this.delay = (int)amerikan.setmgr.getSettingByName("Help Delay").getValDouble() * 3;
        this.dropWholeStack = amerikan.setmgr.getSettingByName("DropWholeStack").getValBoolean();
        this.instant = amerikan.setmgr.getSettingByName("Instant").getValBoolean();
        this.invOnly = amerikan.setmgr.getSettingByName("Inv Only").getValBoolean();
        this.dubOnly = amerikan.setmgr.getSettingByName("Dublicate Only").getValBoolean();
        if (!(InvHelper.mc.currentScreen instanceof GuiInventory) && amerikan.setmgr.getSettingByName("Inv Only").getValBoolean()) {
            return;
        }
        if (this.time.hasReached1(this.instant ? 0.0 : amerikan.setmgr.getSettingByName("Help Delay").getValDouble())) {
            this.onAutoArmor();
            TimeHelper.reset();
        }
        this.uselessItems.clear();
        this.bestSwordDamage = -1;
        this.findBestSword();
        this.findUselessItems();
        if (!this.uselessItems.isEmpty()) {
            if (this.instant) {
                for (int i2 : this.uselessItems) {
                    InvHelper.mc.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, i2, 0, 4, Minecraft.thePlayer);
                }
            } else if (this.lastClick + (long)this.delay < System.currentTimeMillis()) {
                this.lastClick = System.currentTimeMillis();
                InvHelper.mc.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, this.uselessItems.get(random.nextInt(this.uselessItems.size())), this.dropWholeStack ? 1 : 0, 4, Minecraft.thePlayer);
            }
        }
    }

    private void findUselessItems() {
        if (this.dubOnly) {
            this.items.clear();
        }
        this.swords.clear();
        for (int i2 = 0; i2 < 41; ++i2) {
            if (i2 >= 0 && i2 <= 8 || i2 >= 36 && i2 <= 39 || i2 == 40) continue;
            ItemStack itemStack = Minecraft.thePlayer.inventory.getStackInSlot(i2);
            if (itemStack == null || itemStack.getItem() == null) continue;
            if (itemStack.getItem() instanceof ItemSword) {
                if (itemStack.getItem().getMaxDamage() == this.bestSwordDamage) {
                    if (this.swords.contains(itemStack.getItem())) {
                        this.uselessItems.add(i2);
                    }
                    this.swords.add(itemStack.getItem());
                    continue;
                }
                this.uselessItems.add(i2);
                continue;
            }
            if (itemStack == null || this.stackIsUseful(itemStack)) continue;
            if (this.dubOnly) {
                if (this.items.contains(itemStack.getItem())) {
                    this.uselessItems.add(i2);
                }
                this.items.add(itemStack.getItem());
                continue;
            }
            this.uselessItems.add(i2);
        }
    }

    private boolean stackIsUseful(ItemStack itemStack) {
        if (Item.getIdFromItem(itemStack.getItem()) == 0) {
            return true;
        }
        if (Item.getIdFromItem(itemStack.getItem()) == 30) {
            return true;
        }
        if (Item.getIdFromItem(itemStack.getItem()) == 258) {
            return true;
        }
        if (Item.getIdFromItem(itemStack.getItem()) == 259) {
            return true;
        }
        if (Item.getIdFromItem(itemStack.getItem()) == 260) {
            return true;
        }
        if (Item.getIdFromItem(itemStack.getItem()) == 261) {
            return true;
        }
        if (Item.getIdFromItem(itemStack.getItem()) == 262) {
            return true;
        }
        if (Item.getIdFromItem(itemStack.getItem()) == 264) {
            return true;
        }
        if (Item.getIdFromItem(itemStack.getItem()) == 265) {
            return true;
        }
        if (Item.getIdFromItem(itemStack.getItem()) == 279) {
            return true;
        }
        if (Item.getIdFromItem(itemStack.getItem()) == 282) {
            return true;
        }
        if (Item.getIdFromItem(itemStack.getItem()) == 297) {
            return true;
        }
        if (Item.getIdFromItem(itemStack.getItem()) == 320) {
            return true;
        }
        if (Item.getIdFromItem(itemStack.getItem()) == 322) {
            return true;
        }
        if (Item.getIdFromItem(itemStack.getItem()) == 346) {
            return true;
        }
        if (InvHelper.getArmorType(itemStack) == armortypes.helmet) {
            if (InvHelper.getQuality(Minecraft.thePlayer.inventoryContainer.getSlot(5).getStack()) < InvHelper.getQuality(itemStack)) {
                return true;
            }
        }
        if (InvHelper.getArmorType(itemStack) == armortypes.chestplate) {
            if (InvHelper.getQuality(Minecraft.thePlayer.inventoryContainer.getSlot(6).getStack()) < InvHelper.getQuality(itemStack)) {
                return true;
            }
        }
        if (InvHelper.getArmorType(itemStack) == armortypes.leggings) {
            if (InvHelper.getQuality(Minecraft.thePlayer.inventoryContainer.getSlot(7).getStack()) < InvHelper.getQuality(itemStack)) {
                return true;
            }
        }
        if (InvHelper.getArmorType(itemStack) == armortypes.boots) {
            if (InvHelper.getQuality(Minecraft.thePlayer.inventoryContainer.getSlot(8).getStack()) < InvHelper.getQuality(itemStack)) {
                return true;
            }
        }
        if (itemStack.getItem() instanceof ItemPotion) {
            return true;
        }
        if (itemStack.getItem() instanceof ItemFlintAndSteel) {
            return true;
        }
        if (itemStack.getItem() instanceof ItemEnderPearl) {
            return true;
        }
        if (itemStack.getItem() instanceof ItemFood) {
            return true;
        }
        return itemStack.getItem() instanceof ItemBlock && amerikan.setmgr.getSettingByName("Ignore Blocks").getValBoolean();
    }

    private void findBestSword() {
        for (int i2 = 0; i2 < 41; ++i2) {
            ItemSword sword;
            if (i2 >= 0 && i2 <= 8 || i2 >= 36 && i2 <= 39 || i2 == 40) continue;
            ItemStack itemStack = Minecraft.thePlayer.inventory.getStackInSlot(i2);
            if (itemStack == null || itemStack.getItem() == null || !(itemStack.getItem() instanceof ItemSword) || this.bestSwordDamage >= (sword = (ItemSword)itemStack.getItem()).getMaxDamage()) continue;
            this.bestSwordDamage = sword.getMaxDamage();
        }
    }

    private void onAutoArmor() {
        ItemStack itemHelmet = Minecraft.thePlayer.inventoryContainer.getSlot(5).getStack();
        ItemStack itemChestplate = Minecraft.thePlayer.inventoryContainer.getSlot(6).getStack();
        ItemStack itemLeggings = Minecraft.thePlayer.inventoryContainer.getSlot(7).getStack();
        ItemStack itemBoots = Minecraft.thePlayer.inventoryContainer.getSlot(8).getStack();
        int itemHelmetBest = InvHelper.getBestArmor(armortypes.helmet);
        int itemChestplateBest = InvHelper.getBestArmor(armortypes.chestplate);
        int itemLeggingsBest = InvHelper.getBestArmor(armortypes.leggings);
        int itemBootsBest = InvHelper.getBestArmor(armortypes.boots);
        if (itemHelmetBest >= 0) {
            if (InvHelper.getQuality(Minecraft.thePlayer.inventoryContainer.getSlot(itemHelmetBest).getStack()) > InvHelper.getQuality(itemHelmet)) {
                if (this.i == 0) {
                    this.drop(5);
                    ++this.i;
                } else {
                    this.switchSlot(itemHelmetBest, 1, 1);
                    this.i = 0;
                }
                return;
            }
        }
        if (itemChestplateBest >= 0) {
            if (InvHelper.getQuality(Minecraft.thePlayer.inventoryContainer.getSlot(itemChestplateBest).getStack()) > InvHelper.getQuality(itemChestplate)) {
                if (this.i == 0) {
                    this.drop(6);
                    ++this.i;
                } else {
                    this.switchSlot(itemChestplateBest, 1, 1);
                    this.i = 0;
                }
                return;
            }
        }
        if (itemLeggingsBest >= 0) {
            if (InvHelper.getQuality(Minecraft.thePlayer.inventoryContainer.getSlot(itemLeggingsBest).getStack()) > InvHelper.getQuality(itemLeggings)) {
                if (this.i == 0) {
                    this.drop(7);
                    ++this.i;
                } else {
                    this.switchSlot(itemLeggingsBest, 1, 1);
                    this.i = 0;
                }
                return;
            }
        }
        if (itemBootsBest >= 0) {
            if (InvHelper.getQuality(Minecraft.thePlayer.inventoryContainer.getSlot(itemBootsBest).getStack()) > InvHelper.getQuality(itemBoots)) {
                if (this.i == 0) {
                    this.drop(8);
                    ++this.i;
                } else {
                    this.switchSlot(itemBootsBest, 1, 1);
                    this.i = 0;
                }
                return;
            }
        }
    }

    private void switchSlot(int i2, int slot, int mouse) {
        InvHelper.mc.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, i2, slot, mouse, Minecraft.thePlayer);
    }

    private void drop(int slot) {
        Minecraft.thePlayer.inventoryContainer.slotClick(slot, 0, 4, Minecraft.thePlayer);
        InvHelper.mc.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, slot, 1, 4, Minecraft.thePlayer);
    }

    public static int getQuality(ItemStack item) {
        if (item != null && item.getItem() != null && item.getItem() instanceof ItemArmor) {
            ItemArmor itemArmor = (ItemArmor)item.getItem();
            int quality = -1;
            if (itemArmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                quality = 0;
            }
            if (itemArmor.getArmorMaterial() == ItemArmor.ArmorMaterial.GOLD) {
                quality = 1;
            }
            if (itemArmor.getArmorMaterial() == ItemArmor.ArmorMaterial.CHAIN) {
                quality = 2;
            }
            if (itemArmor.getArmorMaterial() == ItemArmor.ArmorMaterial.IRON) {
                quality = 3;
            }
            if (itemArmor.getArmorMaterial() == ItemArmor.ArmorMaterial.DIAMOND) {
                quality = 4;
            }
            if (item != null && item.hasTagCompound()) {
                quality += (int)item.getEnchantmentTagList().getCompoundTagAt(0).getDouble("lvl");
                quality += (int)item.getEnchantmentTagList().getCompoundTagAt(1).getDouble("lvl");
                quality += (int)item.getEnchantmentTagList().getCompoundTagAt(2).getDouble("lvl");
                quality += (int)item.getEnchantmentTagList().getCompoundTagAt(3).getDouble("lvl");
                quality += (int)item.getEnchantmentTagList().getCompoundTagAt(4).getDouble("lvl");
                quality += (int)item.getEnchantmentTagList().getCompoundTagAt(5).getDouble("lvl");
                quality += (int)item.getEnchantmentTagList().getCompoundTagAt(6).getDouble("lvl");
                quality += (int)item.getEnchantmentTagList().getCompoundTagAt(7).getDouble("lvl");
                quality += (int)item.getEnchantmentTagList().getCompoundTagAt(8).getDouble("lvl");
                quality += (int)item.getEnchantmentTagList().getCompoundTagAt(9).getDouble("lvl");
                quality += (int)item.getEnchantmentTagList().getCompoundTagAt(34).getDouble("lvl");
            }
            return quality;
        }
        return -1;
    }

    public static armortypes getArmorType(ItemStack item) {
        if (item != null && item.getItem() != null) {
            if (Item.getIdFromItem(item.getItem()) == 298 || Item.getIdFromItem(item.getItem()) == 302 || Item.getIdFromItem(item.getItem()) == 306 || Item.getIdFromItem(item.getItem()) == 310 || Item.getIdFromItem(item.getItem()) == 314) {
                return armortypes.helmet;
            }
            if (Item.getIdFromItem(item.getItem()) == 299 || Item.getIdFromItem(item.getItem()) == 303 || Item.getIdFromItem(item.getItem()) == 307 || Item.getIdFromItem(item.getItem()) == 311 || Item.getIdFromItem(item.getItem()) == 315) {
                return armortypes.chestplate;
            }
            if (Item.getIdFromItem(item.getItem()) == 300 || Item.getIdFromItem(item.getItem()) == 304 || Item.getIdFromItem(item.getItem()) == 308 || Item.getIdFromItem(item.getItem()) == 312 || Item.getIdFromItem(item.getItem()) == 316) {
                return armortypes.leggings;
            }
            if (Item.getIdFromItem(item.getItem()) == 301 || Item.getIdFromItem(item.getItem()) == 305 || Item.getIdFromItem(item.getItem()) == 309 || Item.getIdFromItem(item.getItem()) == 313 || Item.getIdFromItem(item.getItem()) == 317) {
                return armortypes.boots;
            }
        }
        return null;
    }

    public static int getBestArmor(armortypes armorType) {
        int slot = -1;
        int quality = -1;
        for (int i2 = 8; i2 < 45; ++i2) {
            int quality2;
            ItemStack item = Minecraft.thePlayer.inventoryContainer.getSlot(i2).getStack();
            if (InvHelper.getArmorType(item) != armorType || quality >= (quality2 = InvHelper.getQuality(item))) continue;
            quality = quality2;
            slot = i2;
        }
        return slot;
    }

    public static enum armortypes {
        helmet,
        chestplate,
        leggings,
        boots;
        
    }

}

