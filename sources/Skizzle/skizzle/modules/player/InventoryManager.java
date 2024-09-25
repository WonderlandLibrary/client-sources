/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.player;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemAnvilBlock;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;
import skizzle.modules.combat.AutoArmour;
import skizzle.settings.BooleanSetting;
import skizzle.settings.ModeSetting;
import skizzle.settings.NumberSetting;
import skizzle.ui.notifications.Notification;
import skizzle.util.Timer;

public class InventoryManager
extends Module {
    public Timer notifTimer;
    public ModeSetting mode;
    public BooleanSetting manage;
    public boolean hasFood = false;
    public int cleanBlocks = 0;
    public NumberSetting delay;
    public Timer blockTimer;
    public Timer clickTimer;
    public int cleanI = 0;
    public BooleanSetting blocks;
    public boolean hasAxe = false;
    public int moveBlock = 0;
    public BooleanSetting clean;
    public boolean hasRod = false;
    public Timer timer;
    public NumberSetting blocksAmount;
    public boolean hasPick = false;
    public Minecraft mc = Minecraft.getMinecraft();

    public boolean checkMoveItemToMain(ItemStack Nigga) {
        String Nigga2 = Nigga.getUnlocalizedName().toLowerCase();
        return Nigga2.contains(Qprot0.0("\u7eaa\u71d9\u45cd\u4c39\u7b19")) || Nigga2.contains(Qprot0.0("\u7ea3\u71ca\u45cb\u4c35\u7b06\ufb14\u8c3b")) || Nigga2.contains(Qprot0.0("\u7eb8\u71c3\u45d0\u4c20\u7b0b\ufb1d")) || Nigga2.contains(Qprot0.0("\u7eaa\u71d3\u45da"));
    }

    @Override
    public void onEvent(Event Nigga) {
        InventoryManager Nigga2;
        if (!Client.ghostMode && Nigga2.mc.thePlayer != null && Nigga.isPre()) {
            if (Nigga2.clean.isEnabled()) {
                Nigga2.cleanOther();
            }
            if (Nigga2.manage.isEnabled()) {
                for (int Nigga3 = 0; Nigga3 < 9; ++Nigga3) {
                    ItemStack Nigga4 = Nigga2.getStackInSlot(Nigga3);
                    if (Nigga4 == null || !Nigga2.checkMoveItemToMain(Nigga4)) continue;
                    Nigga2.windowClick(Nigga2.mc.thePlayer.inventoryContainer.windowId, Nigga3 += 36, 0, 1, Nigga2.mc.thePlayer);
                }
                Nigga2.moveItemsToHotbar();
            }
        }
    }

    public void windowClick(int Nigga, int Nigga2, int Nigga3, int Nigga4, EntityPlayer Nigga5) {
        int Nigga6 = Nigga2;
        if (Nigga6 > 36) {
            Nigga6 -= 36;
        }
        try {
            InventoryManager Nigga7;
            short Nigga8 = Nigga5.openContainer.getNextTransactionID(Nigga5.inventory);
            ItemStack Nigga9 = Nigga5.openContainer.slotClick(Nigga2, Nigga3, Nigga4, Nigga5);
            Nigga7.mc.getNetHandler().addToSendQueue(new C0EPacketClickWindow(Nigga, Nigga2, Nigga3, Nigga4, Nigga9, Nigga8));
        }
        catch (Exception Nigga10) {
            Nigga10.printStackTrace();
        }
    }

    public InventoryManager() {
        super(Qprot0.0("\u7e82\u71c5\u45c9\ua7e1\u5076\ufb05\u8c20\u12f4\u571b\ufb1f\u713d\uaf02\ufc3c\u724a\u2444\ue2bc"), 0, Module.Category.PLAYER);
        InventoryManager Nigga;
        Nigga.timer = new Timer();
        Nigga.mode = new ModeSetting(Qprot0.0("\u7e86\u71c4\u45db\ua7e1"), Qprot0.0("\u7e85\u71c4\u45cd\ua7e9\u5079\ufb1d"), Qprot0.0("\u7e85\u71c4\u45cd\ua7e9\u5079\ufb1d"), Qprot0.0("\u7e8e\u71d3\u45cb\ua7f6\u5079"));
        Nigga.clean = new BooleanSetting(Qprot0.0("\u7e88\u71c7\u45da\ua7e5\u5076\ufb14\u8c3d"), true);
        Nigga.manage = new BooleanSetting(Qprot0.0("\u7e86\u71ca\u45d1\ua7e5\u507f\ufb14\u8c6f\u12ce\u570d\ufb26\u713e\uaf0d\ufc2f"), true);
        Nigga.blocks = new BooleanSetting(Qprot0.0("\u7e88\u71c7\u45da\ua7e5\u5076\ufb51\u8c0d\u12ea\u570d\ufb31\u7137\uaf1f"), true);
        Nigga.blocksAmount = new NumberSetting(Qprot0.0("\u7e89\u71c7\u45d0\ua7e7\u5073\ufb02"), 128.0, 32.0, 640.0, 16.0);
        Nigga.delay = new NumberSetting(Qprot0.0("\u7e8f\u71ce\u45d3\ua7e5\u5061"), 0.0, 0.0, 1000.0, 1.0);
        Nigga.clickTimer = new Timer();
        Nigga.blockTimer = new Timer();
        Nigga.notifTimer = new Timer();
        Nigga.addSettings(Nigga.mode, Nigga.clean, Nigga.manage, Nigga.blocks, Nigga.blocksAmount, Nigga.delay);
    }

    public ItemStack getStackInSlot(int Nigga) {
        try {
            InventoryManager Nigga2;
            return Nigga2.mc.thePlayer.inventory.getStackInSlot(Nigga);
        }
        catch (Exception Nigga3) {
            Nigga3.printStackTrace();
            return null;
        }
    }

    public void cleanInv(int Nigga) {
        InventoryManager Nigga2;
        ItemStack Nigga3 = Nigga2.getStackInSlot(Nigga);
        if (Nigga3 != null && Nigga2.checkGoodItem(Nigga3)) {
            if (Nigga < 9) {
                Nigga += 36;
            }
            Nigga2.windowClick(Nigga2.getInvId(), Nigga + Nigga2.getInvSize(), 0, 4, Nigga2.mc.thePlayer);
        }
        if (Nigga3 != null && Nigga3.getItem() instanceof ItemArmor) {
            ItemArmor Nigga4 = (ItemArmor)Nigga2.getStackInSlot(Nigga).getItem();
            int Nigga5 = 0;
            int Nigga6 = 0;
            int Nigga7 = 0;
            if (Nigga2.getStackInSlot(39 - Nigga4.armorType) != null) {
                ItemArmor Nigga8 = (ItemArmor)Nigga2.getStackInSlot(39 - Nigga4.armorType).getItem();
                ItemStack Nigga9 = Nigga2.getStackInSlot(39 - Nigga4.armorType);
                Nigga5 = Nigga8.getArmorMaterial().getDamageReductionAmount(Nigga4.armorType);
                Nigga5 = AutoArmour.checkProtection(Nigga2.mc.thePlayer.inventory.getStackInSlot(39 - Nigga4.armorType)) + Nigga5;
                Nigga6 = Nigga9.getItemDamage();
                Nigga7 = Nigga4.getArmorMaterial().getDamageReductionAmount(Nigga4.armorType);
                Nigga7 = AutoArmour.checkProtection(Nigga2.mc.thePlayer.inventory.getStackInSlot(Nigga)) + Nigga7;
            }
            if (Nigga2.mc.thePlayer.inventory.getStackInSlot(39 - Nigga4.armorType) != null) {
                Nigga2.mc.thePlayer.inventory.getStackInSlot(Nigga);
                if (Nigga7 < Nigga5 || Nigga7 == Nigga5 && Nigga3.getItemDamage() > Nigga6 || Nigga7 == Nigga5 && Nigga3.getItemDamage() > Nigga6 || Nigga7 == Nigga5 && Nigga3.getItemDamage() == Nigga6 && Nigga3 != Nigga2.mc.thePlayer.inventory.getStackInSlot(39 - Nigga4.armorType) && Nigga2.blockTimer.hasTimeElapsed((long)Nigga2.delay.getValue(), true)) {
                    if (Nigga < 9) {
                        Nigga += 36;
                    }
                    Nigga2.windowClick(Nigga2.getInvId(), Nigga, 0, 4, Nigga2.mc.thePlayer);
                }
            }
        }
        for (int Nigga10 = 0; Nigga10 < 40; ++Nigga10) {
            ItemStack Nigga11 = Nigga2.getStackInSlot(Nigga10);
            if (Nigga3 == null || Nigga11 == null || !(Nigga3.getItem() instanceof ItemSword) || !(Nigga11.getItem() instanceof ItemSword) || Nigga11 == Nigga3) continue;
            ItemSword Nigga12 = (ItemSword)Nigga3.getItem();
            ItemSword Nigga13 = (ItemSword)Nigga11.getItem();
            float Nigga14 = ModuleManager.autoSword.getDamageAmount(Nigga11) + Nigga13.damageAmount;
            float Nigga15 = ModuleManager.autoSword.getDamageAmount(Nigga3) + Nigga12.damageAmount;
            if (!(Nigga15 < Nigga14) && (Nigga15 != Nigga14 || Nigga3.getItemDamage() <= Nigga11.getItemDamage() && Nigga3.getItemDamage() != Nigga11.getItemDamage() || !Nigga2.blockTimer.hasTimeElapsed((long)Nigga2.delay.getValue(), true))) continue;
            if (Nigga < 9) {
                Nigga += 36;
            }
            Nigga2.windowClick(Nigga2.getInvId(), Nigga, 0, 4, Nigga2.mc.thePlayer);
        }
    }

    public void cleanOther() {
        InventoryManager Nigga;
        if (Nigga.cleanI >= 40) {
            Nigga.cleanI = 0;
            Nigga.hasFood = false;
            Nigga.hasPick = false;
            Nigga.hasAxe = false;
            Nigga.hasRod = false;
            Nigga.moveBlock = 0;
            Nigga.cleanBlocks = 0;
        }
        ItemStack Nigga2 = Nigga.getStackInSlot(Nigga.cleanI);
        if (Nigga.clickTimer.hasTimeElapsed((long)-1162289660 ^ 0xFFFFFFFFBAB8DE04L, true)) {
            if (Nigga2 != null) {
                Nigga.cleanInv(Nigga.cleanI);
                Nigga.moveToHotbar(Nigga.cleanI);
                if (Nigga.cleanI < 9) {
                    int Nigga3 = Nigga.cleanI + 36;
                    if (Nigga2.getItem() instanceof ItemBlock && Block.getBlockFromItem(Nigga2.getItem()).isFullBlock()) {
                        if (Nigga.moveBlock < 2) {
                            Nigga.moveBlock += Nigga2.stackSize;
                        } else {
                            Nigga.windowClick(Nigga.mc.thePlayer.inventoryContainer.windowId, Nigga3, 0, 1, Nigga.mc.thePlayer);
                        }
                    }
                }
                if (Nigga.hasAxe && Nigga2.getItem() instanceof ItemAxe) {
                    if (Nigga.cleanI < 9) {
                        Nigga.cleanI += 36;
                    }
                    Nigga.windowClick(Nigga.mc.thePlayer.inventoryContainer.windowId, Nigga.cleanI, 1, 4, Nigga.mc.thePlayer);
                }
                if (Nigga.hasPick && Nigga2.getItem() instanceof ItemPickaxe) {
                    if (Nigga.cleanI < 9) {
                        Nigga.cleanI += 36;
                    }
                    Nigga.windowClick(Nigga.mc.thePlayer.inventoryContainer.windowId, Nigga.cleanI, 1, 4, Nigga.mc.thePlayer);
                }
                if (Nigga.hasFood && Nigga2.getItem() instanceof ItemFood && !(Nigga2.getItem() instanceof ItemAppleGold)) {
                    if (Nigga.cleanI < 9) {
                        Nigga.cleanI += 36;
                    }
                    Nigga.windowClick(Nigga.mc.thePlayer.inventoryContainer.windowId, Nigga.cleanI, 1, 4, Nigga.mc.thePlayer);
                }
                if (Nigga.hasRod && Nigga2.getItem() instanceof ItemFishingRod) {
                    if (Nigga.cleanI < 9) {
                        Nigga.cleanI += 36;
                    }
                    Nigga.windowClick(Nigga.mc.thePlayer.inventoryContainer.windowId, Nigga.cleanI, 1, 4, Nigga.mc.thePlayer);
                }
                if (Nigga2.getItem() instanceof ItemAxe) {
                    Nigga.hasAxe = true;
                }
                if (Nigga2.getItem() instanceof ItemPickaxe) {
                    Nigga.hasPick = true;
                }
                if (Nigga2.getItem() instanceof ItemFood && !(Nigga2.getItem() instanceof ItemAppleGold)) {
                    Nigga.hasFood = true;
                }
                if (Nigga2.getItem() instanceof ItemFishingRod) {
                    if (Nigga.mode.getMode().equals(Qprot0.0("\u7e8e\u71d3\u45cb\ufbc3\uf4a6"))) {
                        if (Nigga.cleanI < 9) {
                            Nigga.cleanI += 36;
                        }
                        Nigga.windowClick(Nigga.mc.thePlayer.inventoryContainer.windowId, Nigga.cleanI, 1, 4, Nigga.mc.thePlayer);
                    } else {
                        Nigga.hasRod = true;
                    }
                }
                if (Nigga.mode.getMode().equals(Qprot0.0("\u7e8e\u71d3\u45cb\ufbc3\uf4a6")) && (Nigga2.getItem() instanceof ItemSnowball || Nigga2.getItem() instanceof ItemEgg || Nigga2.getItem() instanceof ItemBow || Nigga2.getItem().getUnlocalizedName().equals(Qprot0.0("\u7ea2\u71df\u45da\ufbdc\uf4e9\ufb10\u8c3d\u12f4\u0b38\u5ffa")) || Nigga2.getItem() instanceof ItemExpBottle || Nigga2.getItem() instanceof ItemAnvilBlock || Nigga2.getItem() instanceof ItemBucket)) {
                    if (Nigga.cleanI < 9) {
                        Nigga.cleanI += 36;
                    }
                    Nigga.windowClick(Nigga.mc.thePlayer.inventoryContainer.windowId, Nigga.cleanI, 1, 4, Nigga.mc.thePlayer);
                }
                if (Nigga2.getItem() instanceof ItemBlock && !ModuleManager.scaffold.noobBlocks.contains(Block.getBlockFromItem(Nigga2.getItem())) && Nigga.blocks.isEnabled()) {
                    Nigga.cleanBlocks += Nigga2.stackSize;
                }
                if ((double)Nigga.cleanBlocks > Nigga.blocksAmount.getValue() && Nigga2.getItem() instanceof ItemBlock) {
                    if (Nigga.cleanI < 9) {
                        Nigga.cleanI += 36;
                    }
                    Nigga.windowClick(Nigga.mc.thePlayer.inventoryContainer.windowId, Nigga.cleanI, 1, 4, Nigga.mc.thePlayer);
                }
            }
            ++Nigga.cleanI;
        }
    }

    public boolean checkGoodItem(ItemStack Nigga) {
        String Nigga2 = Nigga.getUnlocalizedName().toLowerCase();
        return Nigga2.contains(Qprot0.0("\u7eb8\u71ce\u45da\u2f0a\ud805")) || Nigga2.contains(Qprot0.0("\u7ead\u71c7\u45d6\u2f00\ud802")) || Nigga2.contains(Qprot0.0("\u7eb8\u71df\u45cd\u2f07\ud818\ufb16")) || Nigga2.contains(Qprot0.0("\u7ebf\u71c5\u45cb")) || Nigga2.contains(Qprot0.0("\u7eb8\u71ca\u45cf\u2f02\ud81f\ufb1f\u8c28")) || Nigga2.contains(Qprot0.0("\u7eb8\u71c3\u45da\u2f0b\ud804\ufb02")) || Nigga2.contains(Qprot0.0("\u7ea8\u71ca\u45cd\u2f1e\ud813\ufb05")) || Nigga2.contains(Qprot0.0("\u7eaf\u71c4\u45d0\u2f1c")) || Nigga2.contains(Qprot0.0("\u7ebb\u71ca\u45d1\u2f0b")) || Nigga2.contains(Qprot0.0("\u7ea3\u71c4\u45da")) || Nigga2.contains(Qprot0.0("\u7ea8\u71c7\u45d0\u2f19\ud818\ufb17\u8c26\u12f5\udfe0")) || Nigga2.contains(Qprot0.0("\u7ebb\u71c4\u45cb\u2f0f\ud802\ufb1e\u8c3f\u12e9\udfe1\u734f\u7133\uaf02\ufc32\ufab2\uac3c")) || Nigga2.contains(Qprot0.0("\u7eb8\u71db\u45d6\u2f0a\ud813\ufb03\u8c2a\u12ff\udfed")) || Nigga2.contains(Qprot0.0("\u7eb9\u71c4\u45cb\u2f1a\ud813\ufb1f\u8c29\u12ea\udfed\u734f\u7134")) || Nigga2.contains(Qprot0.0("\u7eaf\u71c2\u45cc\u2f0d"));
    }

    public int getInvSize() {
        InventoryManager Nigga;
        if (Nigga.mc.thePlayer.openContainer != null && Nigga.mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest Nigga2 = (ContainerChest)Nigga.mc.thePlayer.openContainer;
            return Nigga2.getLowerChestInventory().getSizeInventory() - 9;
        }
        return 0;
    }

    public boolean isValid(ItemStack Nigga) {
        return Nigga.getItem() instanceof ItemSword || Nigga.getItem() instanceof ItemAppleGold || Nigga.getItem() instanceof ItemPotion;
    }

    public void moveToHotbar(int Nigga) {
        InventoryManager Nigga2;
        ItemStack Nigga3 = Nigga2.mc.thePlayer.inventory.getStackInSlot(Nigga);
        if (Nigga3 != null && Nigga2.checkGoodItem(Nigga3)) {
            if (Nigga < 9) {
                Nigga += 36;
            }
            Nigga2.windowClick(Nigga2.getInvId(), Nigga + Nigga2.getInvSize(), 0, 4, Nigga2.mc.thePlayer);
        }
    }

    public void moveItemsToHotbar() {
        Object Nigga;
        InventoryManager Nigga2;
        int Nigga3;
        boolean Nigga4 = true;
        boolean Nigga5 = true;
        boolean Nigga6 = true;
        for (Nigga3 = 0; Nigga3 < 9; ++Nigga3) {
            String Nigga7;
            String Nigga8;
            if (Nigga2.getStackInSlot(Nigga3) == null) continue;
            if (Nigga2.getStackInSlot(Nigga3).getItem() instanceof ItemSword) {
                Nigga4 = false;
            }
            if (Nigga2.getStackInSlot(Nigga3).getItem() instanceof ItemAppleGold) {
                Nigga5 = false;
            }
            if (!(Nigga2.getStackInSlot(Nigga3).getItem() instanceof ItemPotion) || !(Nigga8 = (Nigga7 = "" + ((ItemPotion)(Nigga = (ItemPotion)Nigga2.mc.thePlayer.inventory.getStackInSlot(Nigga3).getItem())).getEffects(Nigga2.mc.thePlayer.inventory.getStackInSlot(Nigga3)).get(0)).split(Qprot0.0("\u7ee7\u718b"))[0]).equals(Qprot0.0("\u7ebb\u71c4\u45cb\uf30f\uec11\ufb1f\u8c61\u12ee\u03e5\u4755\u7130"))) continue;
            Nigga6 = false;
            break;
        }
        if (Nigga4 || Nigga5 || Nigga6) {
            if (InventoryManager.getEmptyHotbarSlot() == -1 && Nigga2.timer.hasTimeElapsed((long)-562670879 ^ 0xFFFFFFFFDE765B25L, true)) {
                Client.notifications.notifs.add(new Notification(Qprot0.0("\u7e86\u71ca\u45d1\uf307\uec19\ufb14\u8c3d"), Qprot0.0("\u7e85\u71c4\u459f\uf335\uec0e\ufb10\u8c2c\u12e3\u03a0\u475d\u7132\uaf4c\ufc15\u26a0\u9833\ue2ac\u42e8\uc8d6"), Float.intBitsToFloat(1.06209011E9f ^ 0x7F4E3597), Float.intBitsToFloat(2.12724813E9f ^ 0x7ECB3AF1), Notification.notificationType.WARNING));
            }
            if (InventoryManager.getEmptyHotbarSlot() != -1) {
                for (Nigga3 = 0; Nigga3 < Nigga2.mc.thePlayer.inventory.mainInventory.length; ++Nigga3) {
                    if (Nigga3 <= 8 || (Nigga = Nigga2.mc.thePlayer.inventory.mainInventory[Nigga3]) == null || !Nigga2.isValid((ItemStack)Nigga)) continue;
                    Nigga2.windowClick(Nigga2.mc.thePlayer.inventoryContainer.windowId, Nigga3, InventoryManager.getEmptyHotbarSlot(), 2, Nigga2.mc.thePlayer);
                    break;
                }
            }
        }
    }

    public static {
        throw throwable;
    }

    public static int getEmptyHotbarSlot() {
        for (int Nigga = 0; Nigga < 9; ++Nigga) {
            if (Minecraft.getMinecraft().thePlayer.inventory.mainInventory[Nigga] != null) continue;
            return Nigga;
        }
        return -1;
    }

    public boolean checkHotbarItem(ItemStack Nigga) {
        String Nigga2 = Nigga.getDisplayName();
        return Nigga2.contains(Qprot0.0("\u7e98\u71db\u45d3\u0996\u06fa\ufb19\u8c6f\u12d6\uf97e\uadb7\u7135\uaf03\ufc33\udc7e\u72df\ue2a8\u42a9\uc8f6\ub976\u19af\u8768\u01c4\u6ef3\u460d\uc14f\uc3b8\u2f17\uab82\uec2c"));
    }

    public int getInvId() {
        InventoryManager Nigga;
        if (Nigga.mc.thePlayer.openContainer != null && Nigga.mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest Nigga2 = (ContainerChest)Nigga.mc.thePlayer.openContainer;
            return Nigga2.windowId;
        }
        return Nigga.mc.thePlayer.inventoryContainer.windowId;
    }
}

