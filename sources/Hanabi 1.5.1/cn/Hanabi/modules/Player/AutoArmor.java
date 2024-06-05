package cn.Hanabi.modules.Player;

import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.gui.inventory.*;
import com.darkmagician6.eventapi.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import ClassSub.*;
import java.util.*;
import net.minecraft.enchantment.*;

public class AutoArmor extends Mod
{
    private Class205 timeHelper;
    public Class205 timer;
    public Class205 droptimer;
    private Value<Boolean> openInv;
    private Value<Double> delay;
    private Value inventoryKeepTimeVal;
    private Value insant;
    private ArrayList openList;
    private ArrayList closeList;
    public static boolean complete;
    private static boolean openedInventory;
    private int[] itemHelmet;
    private int[] itemChestplate;
    private int[] itemLeggings;
    private int[] itemBoots;
    private HashMap<ItemStack, Long> armorContains;
    
    
    public AutoArmor() {
        super("AutoArmor", Category.PLAYER);
        this.timeHelper = new Class205();
        this.timer = new Class205();
        this.droptimer = new Class205();
        this.openInv = new Value<Boolean>("AutoArmor_SortInInv", true);
        this.delay = new Value<Double>("AutoArmor_Delay", 60.0, 0.0, 1000.0, 10.0);
        this.inventoryKeepTimeVal = new Value("AutoArmor_TimeInInv", (T)1000.0, (T)0.0, (T)10000.0, 100.0);
        this.insant = new Value("AutoArmor_Insant", (T)false);
        this.openList = new ArrayList();
        this.closeList = new ArrayList();
        this.itemHelmet = new int[] { 298, 302, 306, 310, 314 };
        this.itemChestplate = new int[] { 299, 303, 307, 311, 315 };
        this.itemLeggings = new int[] { 300, 304, 308, 312, 316 };
        this.armorContains = new HashMap<ItemStack, Long>();
        this.itemBoots = new int[] { 301, 305, 309, 313, 317 };
    }
    
    @EventTarget
    public void onEvent(final EventUpdate eventUpdate) {
        if (AutoArmor.mc.thePlayer == null) {
            AutoArmor.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0DPacketCloseWindow(AutoArmor.mc.thePlayer.inventoryContainer.windowId));
            return;
        }
        if (AutoArmor.mc.currentScreen != null && !(AutoArmor.mc.currentScreen instanceof GuiInventory)) {
            if (AutoArmor.openedInventory) {
                AutoArmor.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0DPacketCloseWindow(AutoArmor.mc.thePlayer.inventoryContainer.windowId));
                AutoArmor.openedInventory = false;
            }
            return;
        }
        final boolean b = (this.openInv.getValueState() && AutoArmor.mc.currentScreen != null && AutoArmor.mc.currentScreen instanceof GuiInventory) || !this.openInv.getValueState();
        boolean b2 = true;
        for (final String s : this.getArmors()) {
            b2 = false;
            final int slotByName = this.getSlotByName(s);
            int bestInInventory = this.getBestInInventory(s);
            boolean b3 = true;
            if (bestInInventory != -1) {
                b3 = (this.getValence(AutoArmor.mc.thePlayer.inventoryContainer.getSlot(bestInInventory).getStack()) > this.getValence(AutoArmor.mc.thePlayer.inventoryContainer.getSlot(slotByName).getStack()));
            }
            if (b3 && bestInInventory != -1 && !this.armorContains.containsKey(AutoArmor.mc.thePlayer.inventoryContainer.getSlot(bestInInventory).getStack())) {
                this.armorContains.put(AutoArmor.mc.thePlayer.inventoryContainer.getSlot(bestInInventory).getStack(), System.currentTimeMillis());
            }
            if (b) {
                if (bestInInventory != -1 && AutoArmor.mc.thePlayer.inventoryContainer.getSlot(slotByName).getHasStack() && this.getValence(AutoArmor.mc.thePlayer.inventoryContainer.getSlot(bestInInventory).getStack()) < this.getValence(AutoArmor.mc.thePlayer.inventoryContainer.getSlot(slotByName).getStack())) {
                    bestInInventory = -1;
                }
                if (this.timer.isDelayComplete((long)(Object)this.delay.getValueState()) && bestInInventory != -1 && this.armorContains.containsKey(AutoArmor.mc.thePlayer.inventoryContainer.getSlot(bestInInventory).getStack()) && System.currentTimeMillis() - this.armorContains.get(AutoArmor.mc.thePlayer.inventoryContainer.getSlot(bestInInventory).getStack()) >= 200L) {
                    this.putOnItem(slotByName, bestInInventory);
                    this.armorContains.remove(AutoArmor.mc.thePlayer.inventoryContainer.getSlot(bestInInventory).getStack());
                    this.timer.reset();
                }
                for (final int intValue : this.findArmor(s)) {
                    int n = 0;
                    if (slotByName != -1) {
                        n = ((this.getValence(AutoArmor.mc.thePlayer.inventoryContainer.getSlot(slotByName).getStack()) >= this.getValence(AutoArmor.mc.thePlayer.inventoryContainer.getSlot(intValue).getStack())) ? 1 : 0);
                    }
                    if (n != 0) {
                        b2 = false;
                        if (!this.droptimer.isDelayComplete(300L)) {
                            continue;
                        }
                        this.droptimer.reset();
                    }
                }
            }
        }
        if (b2) {
            AutoArmor.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0DPacketCloseWindow(AutoArmor.mc.thePlayer.inventoryContainer.windowId));
            AutoArmor.openedInventory = false;
        }
    }
    
    private void changeArmor() {
        final String[] array = { "boots", "leggings", "chestplate", "helmet" };
        for (int i = 0; i < 4; ++i) {
            if (this.insant.getValueState() || this.timeHelper.isDelayComplete((long)(Object)this.delay.getValueState())) {
                final int bestArmor = this.getBestArmor(array[i]);
                if (bestArmor != -1) {
                    AutoArmor.complete = false;
                    if (AutoArmor.mc.thePlayer.inventory.armorInventory[i] == null) {
                        AutoArmor.mc.playerController.windowClick(0, bestArmor, 0, 1, (EntityPlayer)AutoArmor.mc.thePlayer);
                        this.timeHelper.reset();
                    }
                    else if (isBetter(this.getInventoryItem(bestArmor), AutoArmor.mc.thePlayer.inventory.armorInventory[i].getItem())) {
                        AutoArmor.mc.playerController.windowClick(0, 8 - i, 0, 1, (EntityPlayer)AutoArmor.mc.thePlayer);
                        this.timeHelper.reset();
                    }
                }
                else {
                    AutoArmor.complete = true;
                }
            }
        }
    }
    
    private int getBestArmor(final String s) {
        return this.getBestInInventory(s);
    }
    
    public static boolean isBetter(final Item item, final Item item2) {
        return item instanceof ItemArmor && item2 instanceof ItemArmor && ((ItemArmor)item).damageReduceAmount > ((ItemArmor)item2).damageReduceAmount;
    }
    
    private Item getInventoryItem(final int n) {
        return (AutoArmor.mc.thePlayer.inventoryContainer.getSlot(n).getStack() == null) ? null : AutoArmor.mc.thePlayer.inventoryContainer.getSlot(n).getStack().getItem();
    }
    
    private void clearLists() {
        for (final ItemStack itemStack : this.closeList) {
            ItemStack itemStack2 = null;
            final InventoryPlayer inventory = AutoArmor.mc.thePlayer.inventory;
            for (int i = 0; i < 45; ++i) {
                final ItemStack getStack = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getStack != null && itemStack == getStack) {
                    itemStack2 = itemStack;
                }
            }
            if (itemStack2 == null) {
                this.closeList.remove(itemStack);
            }
        }
    }
    
    private void putOnItem(final int n, final int n2) {
        if (Class346.abuses < 0) {
            this.dropOldArmor(n2);
            return;
        }
        if (n != -1 && AutoArmor.mc.thePlayer.inventoryContainer.getSlot(n).getStack() != null) {
            this.dropOldArmor(n);
        }
        this.inventoryAction(n2);
    }
    
    private void dropOldArmor(final int n) {
        AutoArmor.mc.thePlayer.inventoryContainer.slotClick(n, 0, 4, (EntityPlayer)AutoArmor.mc.thePlayer);
        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.thePlayer.inventoryContainer.windowId, n, 1, 4, (EntityPlayer)AutoArmor.mc.thePlayer);
    }
    
    private void inventoryAction(final int n) {
        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.thePlayer.inventoryContainer.windowId, n, 1, 1, (EntityPlayer)AutoArmor.mc.thePlayer);
    }
    
    private List<String> getArmors() {
        return Arrays.asList("helmet", "leggings", "chestplate", "boots");
    }
    
    private int[] getIdsByName(final String s) {
        switch (s) {
            case "helmet": {
                return this.itemHelmet;
            }
            case "boots": {
                return this.itemBoots;
            }
            case "chestplate": {
                return this.itemChestplate;
            }
            case "leggings": {
                return this.itemLeggings;
            }
            default: {
                return new int[0];
            }
        }
    }
    
    private List<Integer> findArmor(final String s) {
        final int[] array = this.getIdsByName(s);
        final ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 9; i < AutoArmor.mc.thePlayer.inventoryContainer.getInventory().size(); ++i) {
            final ItemStack getStack = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (getStack != null) {
                final int getIdFromItem = Item.getIdFromItem(getStack.getItem());
                int[] array2;
                for (int length = (array2 = array).length, j = 0; j < length; ++j) {
                    if (getIdFromItem == array2[j]) {
                        list.add(i);
                    }
                }
            }
        }
        return list;
    }
    
    private int getBestInInventory(final String s) {
        int n = -1;
        for (final int intValue : this.findArmor(s)) {
            if (n == -1) {
                n = intValue;
            }
            if (AutoArmor.mc.thePlayer.inventoryContainer.getSlot(intValue) != null) {
                if (!(AutoArmor.mc.thePlayer.inventoryContainer.getSlot(intValue).getStack().getItem() instanceof ItemArmor)) {
                    continue;
                }
                if (this.getValence(AutoArmor.mc.thePlayer.inventoryContainer.getSlot(intValue).getStack()) <= this.getValence(AutoArmor.mc.thePlayer.inventoryContainer.getSlot(n).getStack())) {
                    continue;
                }
                n = intValue;
            }
        }
        return n;
    }
    
    private int getSlotByName(final String s) {
        int n = -1;
        switch (s) {
            case "helmet": {
                n = 5;
                break;
            }
            case "boots": {
                n = 8;
                break;
            }
            case "chestplate": {
                n = 6;
                break;
            }
            case "leggings": {
                n = 7;
                break;
            }
        }
        return n;
    }
    
    private double getProtectionValue(final ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof ItemArmor)) {
            return 0.0;
        }
        return ((ItemArmor)itemStack.getItem()).damageReduceAmount + (100 - ((ItemArmor)itemStack.getItem()).damageReduceAmount * 4) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, itemStack) * 4 * 0.0075 + EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, itemStack) + EnchantmentHelper.getEnchantmentLevel(Enchantment.featherFalling.effectId, itemStack) + EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, itemStack) + EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, itemStack) + EnchantmentHelper.getEnchantmentLevel(Enchantment.respiration.effectId, itemStack) + EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, itemStack);
    }
    
    private int getValence(final ItemStack itemStack) {
        int n = 0;
        if (itemStack == null) {
            return 0;
        }
        if (itemStack.getItem() instanceof ItemArmor) {
            n += ((ItemArmor)itemStack.getItem()).damageReduceAmount;
        }
        if (itemStack != null && itemStack.hasTagCompound()) {
            n = n + (int)itemStack.getEnchantmentTagList().getCompoundTagAt(0).getDouble("lvl") + (int)itemStack.getEnchantmentTagList().getCompoundTagAt(1).getDouble("lvl") + (int)itemStack.getEnchantmentTagList().getCompoundTagAt(2).getDouble("lvl") + (int)itemStack.getEnchantmentTagList().getCompoundTagAt(3).getDouble("lvl") + (int)itemStack.getEnchantmentTagList().getCompoundTagAt(4).getDouble("lvl") + (int)itemStack.getEnchantmentTagList().getCompoundTagAt(5).getDouble("lvl") + (int)itemStack.getEnchantmentTagList().getCompoundTagAt(6).getDouble("lvl") + (int)itemStack.getEnchantmentTagList().getCompoundTagAt(7).getDouble("lvl") + (int)itemStack.getEnchantmentTagList().getCompoundTagAt(8).getDouble("lvl") + (int)itemStack.getEnchantmentTagList().getCompoundTagAt(9).getDouble("lvl") + (int)itemStack.getEnchantmentTagList().getCompoundTagAt(34).getDouble("lvl");
        }
        return n + (int)this.getProtectionValue(itemStack) + (itemStack.getMaxDamage() - itemStack.getItemDamage());
    }
}
