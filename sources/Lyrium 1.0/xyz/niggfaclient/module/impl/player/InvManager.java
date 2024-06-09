// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.player;

import java.util.Iterator;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPotion;
import xyz.niggfaclient.utils.player.BlockUtils;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.Item;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemStack;
import xyz.niggfaclient.utils.player.InventoryUtils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import xyz.niggfaclient.property.impl.Representation;
import xyz.niggfaclient.events.impl.UpdateEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.utils.other.TimerUtil;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "InvManager", description = "Throws garbage items automatically for you", cat = Category.PLAYER)
public class InvManager extends Module
{
    private final int weaponSlot = 36;
    private final int pickaxeSlot = 37;
    private final int axeSlot = 38;
    private final int shovelSlot = 39;
    private final DoubleProperty armorDelay;
    private final Property<Boolean> openInventory;
    private final TimerUtil delayTimer;
    private final TimerUtil timer;
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    @EventLink
    private final Listener<UpdateEvent> updateEventListener;
    
    public InvManager() {
        this.armorDelay = new DoubleProperty("Delay", 100.0, 0.0, 500.0, 10.0, Representation.MILLISECONDS);
        this.openInventory = new Property<Boolean>("Open Inventory", false);
        this.delayTimer = new TimerUtil();
        this.timer = new TimerUtil();
        this.packetEventListener = (e -> {
            if (e.getState() == PacketEvent.State.SEND && (e.getPacket() instanceof C0DPacketCloseWindow || e.getPacket() instanceof C07PacketPlayerDigging || e.getPacket() instanceof C08PacketPlayerBlockPlacement)) {
                this.delayTimer.resetWithOffset(150L);
            }
            return;
        });
        int i;
        ItemStack is;
        this.updateEventListener = (e -> {
            if ((!this.openInventory.getValue() || this.mc.currentScreen instanceof GuiContainer) && this.mc.thePlayer.isAllowEdit()) {
                if (this.timer.hasElapsed(this.armorDelay.getValue().longValue())) {
                    if (!this.mc.thePlayer.inventoryContainer.getSlot(36).getHasStack()) {
                        this.getBestWeapon(36);
                    }
                    else if (!this.isBestWeapon(this.mc.thePlayer.inventoryContainer.getSlot(36).getStack())) {
                        this.getBestWeapon(36);
                    }
                    this.getBestPickaxe();
                    this.getBestShovel();
                    this.getBestAxe();
                    for (i = 9; i < 45; ++i) {
                        if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                            is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                            if (this.shouldDrop(is, i)) {
                                InventoryUtils.drop(i);
                                this.timer.reset();
                                if (this.armorDelay.getValue() > 0.0) {
                                    break;
                                }
                            }
                        }
                    }
                    this.timer.reset();
                }
                this.delayTimer.reset();
            }
        });
    }
    
    public boolean isBestWeapon(final ItemStack stack) {
        final float damage = this.getDamage(stack);
        for (int i = 9; i < 45; ++i) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (this.getDamage(is) > damage && is.getItem() instanceof ItemSword) {
                    return false;
                }
            }
        }
        return stack.getItem() instanceof ItemSword;
    }
    
    public void getBestWeapon(final int slot) {
        for (int i = 9; i < 45; ++i) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (this.isBestWeapon(is) && this.getDamage(is) > 0.0f && is.getItem() instanceof ItemSword) {
                    InventoryUtils.swap(i, slot - 36);
                    this.timer.reset();
                    break;
                }
            }
        }
    }
    
    private float getDamage(final ItemStack stack) {
        float damage = 0.0f;
        final Item item = stack.getItem();
        if (item instanceof ItemTool) {
            final ItemTool tool = (ItemTool)item;
            damage += tool.getAttackDamage();
        }
        if (item instanceof ItemSword) {
            final ItemSword sword = (ItemSword)item;
            damage += sword.getAttackDamage();
        }
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25f + EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 2.0f;
        if (stack.getItemDamage() * 3 > stack.getMaxItemUseDuration()) {
            damage -= 3.0f;
        }
        return damage;
    }
    
    public boolean shouldDrop(final ItemStack stack, final int slot) {
        if (stack.getItem() instanceof ItemFishingRod) {
            return true;
        }
        if ((slot == 36 && this.isBestWeapon(this.mc.thePlayer.inventoryContainer.getSlot(36).getStack())) || (slot == 37 && this.isBestPickaxe(this.mc.thePlayer.inventoryContainer.getSlot(37).getStack())) || (slot == 38 && this.isBestAxe(this.mc.thePlayer.inventoryContainer.getSlot(38).getStack())) || (slot == 39 && this.isBestShovel(this.mc.thePlayer.inventoryContainer.getSlot(39).getStack()))) {
            return false;
        }
        if (stack.getItem() instanceof ItemArmor) {
            for (int type = 1; type < 5; ++type) {
                if (this.mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()) {
                    final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
                    if (InventoryUtils.isBestArmor(is, type)) {
                        continue;
                    }
                }
                if (InventoryUtils.isBestArmor(stack, type)) {
                    return false;
                }
            }
        }
        return (stack.getItem() instanceof ItemBlock && BlockUtils.BLACKLISTED_BLOCKS.contains(((ItemBlock)stack.getItem()).getBlock())) || (stack.getItem() instanceof ItemPotion && this.isBadPotion(stack)) || (stack.getItem() instanceof ItemHoe || stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemArmor) || stack.getItem().getUnlocalizedName().contains("tnt") || stack.getItem().getUnlocalizedName().contains("stick") || stack.getItem().getUnlocalizedName().contains("string") || stack.getItem().getUnlocalizedName().contains("cake") || stack.getItem().getUnlocalizedName().contains("mushroom") || stack.getItem().getUnlocalizedName().contains("flint") || stack.getItem().getUnlocalizedName().contains("feather") || stack.getItem().getUnlocalizedName().contains("shears") || stack.getItem().getUnlocalizedName().contains("torch") || stack.getItem().getUnlocalizedName().contains("seeds") || stack.getItem().getUnlocalizedName().contains("reeds") || stack.getItem().getUnlocalizedName().contains("record") || stack.getItem() instanceof ItemGlassBottle || stack.getItem().getUnlocalizedName().contains("piston");
    }
    
    private void getBestPickaxe() {
        for (int i = 9; i < 45; ++i) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (this.isBestPickaxe(is) && 37 != i && !this.isBestWeapon(is)) {
                    if (!this.mc.thePlayer.inventoryContainer.getSlot(37).getHasStack()) {
                        InventoryUtils.swap(i, 1);
                        this.timer.reset();
                        if (this.armorDelay.getValue() > 0.0) {
                            return;
                        }
                    }
                    else if (!this.isBestPickaxe(this.mc.thePlayer.inventoryContainer.getSlot(37).getStack())) {
                        InventoryUtils.swap(i, 1);
                        this.timer.reset();
                        return;
                    }
                }
            }
        }
    }
    
    private void getBestShovel() {
        for (int i = 9; i < 45; ++i) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (this.isBestShovel(is) && 39 != i && !this.isBestWeapon(is)) {
                    if (!this.mc.thePlayer.inventoryContainer.getSlot(39).getHasStack()) {
                        InventoryUtils.swap(i, 3);
                        this.timer.reset();
                        if (this.armorDelay.getValue() > 0.0) {
                            return;
                        }
                    }
                    else if (!this.isBestShovel(this.mc.thePlayer.inventoryContainer.getSlot(39).getStack())) {
                        InventoryUtils.swap(i, 3);
                        this.timer.reset();
                        return;
                    }
                }
            }
        }
    }
    
    private void getBestAxe() {
        for (int i = 9; i < 45; ++i) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (this.isBestAxe(is) && 38 != i && !this.isBestWeapon(is)) {
                    if (!this.mc.thePlayer.inventoryContainer.getSlot(38).getHasStack()) {
                        InventoryUtils.swap(i, 2);
                        this.timer.reset();
                        if (this.armorDelay.getValue() > 0.0) {
                            return;
                        }
                    }
                    else if (!this.isBestAxe(this.mc.thePlayer.inventoryContainer.getSlot(38).getStack())) {
                        InventoryUtils.swap(i, 2);
                        this.timer.reset();
                        return;
                    }
                }
            }
        }
    }
    
    private boolean isBestPickaxe(final ItemStack stack) {
        final Item item = stack.getItem();
        if (!(item instanceof ItemPickaxe)) {
            return false;
        }
        final float value = this.getToolEffect(stack);
        for (int i = 9; i < 45; ++i) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (this.getToolEffect(is) > value && is.getItem() instanceof ItemPickaxe) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean isBestShovel(final ItemStack stack) {
        final Item item = stack.getItem();
        if (!(item instanceof ItemSpade)) {
            return false;
        }
        final float value = this.getToolEffect(stack);
        for (int i = 9; i < 45; ++i) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (this.getToolEffect(is) > value && is.getItem() instanceof ItemSpade) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean isBestAxe(final ItemStack stack) {
        final Item item = stack.getItem();
        if (!(item instanceof ItemAxe)) {
            return false;
        }
        final float value = this.getToolEffect(stack);
        for (int i = 9; i < 45; ++i) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (this.getToolEffect(is) > value && is.getItem() instanceof ItemAxe && !this.isBestWeapon(stack)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private float getToolEffect(final ItemStack stack) {
        final Item item = stack.getItem();
        if (!(item instanceof ItemTool)) {
            return 0.0f;
        }
        final String itemName = item.getUnlocalizedName();
        final ItemTool tool = (ItemTool)item;
        float value;
        if (item instanceof ItemPickaxe) {
            value = tool.getStrVsBlock(stack, Blocks.stone);
            if (itemName.toLowerCase().contains("gold")) {
                value -= 5.0f;
            }
        }
        else if (item instanceof ItemSpade) {
            value = tool.getStrVsBlock(stack, Blocks.dirt);
            if (itemName.toLowerCase().contains("gold")) {
                value -= 5.0f;
            }
        }
        else {
            if (!(item instanceof ItemAxe)) {
                return 1.0f;
            }
            value = tool.getStrVsBlock(stack, Blocks.log);
            if (itemName.toLowerCase().contains("gold")) {
                value -= 5.0f;
            }
        }
        value += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack) * 0.0075);
        value += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 100.0);
        return value;
    }
    
    private boolean isBadPotion(final ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion)stack.getItem();
            if (potion.getEffects(stack) == null) {
                return true;
            }
            for (final PotionEffect o : potion.getEffects(stack)) {
                if (o.getPotionID() == Potion.poison.getId() || o.getPotionID() == Potion.harm.getId() || o.getPotionID() == Potion.moveSlowdown.getId() || o.getPotionID() == Potion.weakness.getId()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean invContainsType(final int type) {
        for (int i = 9; i < 45; ++i) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                final Item item = is.getItem();
                if (item instanceof ItemArmor) {
                    final ItemArmor armor = (ItemArmor)item;
                    if (type == armor.armorType) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private void getBestArmor() {
        for (int type = 1; type < 5; ++type) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()) {
                final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
                if (InventoryUtils.isBestArmor(is, type)) {
                    continue;
                }
                InventoryUtils.drop(4 + type);
            }
            for (int i = 9; i < 45; ++i) {
                if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    final ItemStack is2 = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                    if (InventoryUtils.isBestArmor(is2, type) && InventoryUtils.getProtection(is2) > 0.0f) {
                        InventoryUtils.shiftClick(i);
                        this.timer.reset();
                        if (this.armorDelay.getValue() > 0.0) {
                            return;
                        }
                    }
                }
            }
        }
    }
}
