package me.mees.remix.modules.player;

import me.satisfactory.base.utils.timer.*;
import me.satisfactory.base.module.*;
import me.satisfactory.base.setting.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.enchantment.*;
import me.satisfactory.base.events.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import pw.stamina.causam.scan.method.model.*;

public class InvCleaner extends Module
{
    TimerUtil timer;
    private int slots;
    private double numberIdkWillfigureout;
    private boolean someboolean;
    
    public InvCleaner() {
        super("InvCleaner", 0, Category.PLAYER);
        this.timer = new TimerUtil();
        this.addSetting(new Setting("CleanSpeed", this, 50.0, 0.0, 500.0, false, 10.0));
        this.addSetting(new Setting("Open Inventory", this, false));
    }
    
    public static boolean isItemBad(final ItemStack item) {
        return item != null && (item.getItem().getUnlocalizedName().contains("TNT") || item.getItem().getUnlocalizedName().contains("stick") || item.getItem().getUnlocalizedName().contains("egg") || item.getItem().getUnlocalizedName().contains("string") || item.getItem().getUnlocalizedName().contains("flint") || item.getItem().getUnlocalizedName().contains("compass") || item.getItem().getUnlocalizedName().contains("feather") || item.getItem().getUnlocalizedName().contains("map") || item.getItem().getUnlocalizedName().contains("bucket") || item.getItem().getUnlocalizedName().contains("chest") || item.getItem().getUnlocalizedName().contains("snowball") || item.getItem().getUnlocalizedName().contains("dye") || item.getItem().getUnlocalizedName().contains("web") || item.getItem().getUnlocalizedName().contains("gold_ingot") || item.getItem().getUnlocalizedName().contains("arrow") || item.getItem().getUnlocalizedName().contains("leather") || item.getItem().getUnlocalizedName().contains("wheat") || item.getItem().getUnlocalizedName().contains("fish") || item.getItem().getUnlocalizedName().contains("enchant") || item.getItem().getUnlocalizedName().contains("exp") || item.getItem() instanceof ItemPickaxe || item.getItem() instanceof ItemTool || item.getItem() instanceof ItemArmor || item.getItem() instanceof ItemSword || item.getItem() instanceof ItemBow || (item.getItem().getUnlocalizedName().contains("potion") && isBadPotion(item)));
    }
    
    public static boolean isBadPotion(final ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        if (!(itemStack.getItem() instanceof ItemPotion)) {
            return false;
        }
        final ItemPotion itemPotion = (ItemPotion)itemStack.getItem();
        for (final Object pObj : itemPotion.getEffects(itemStack)) {
            final PotionEffect potionEffect = (PotionEffect)pObj;
            if (potionEffect.getPotionID() == Potion.poison.getId()) {
                return true;
            }
            if (potionEffect.getPotionID() == Potion.moveSlowdown.getId()) {
                return true;
            }
            if (potionEffect.getEffectName() == null) {
                return true;
            }
            if (potionEffect.getPotionID() == Potion.harm.getId()) {
                return true;
            }
        }
        return false;
    }
    
    private static double getEnchantmentOnSword(final ItemStack itemStack) {
        if (itemStack == null) {
            return 0.0;
        }
        if (!(itemStack.getItem() instanceof ItemSword)) {
            return 0.0;
        }
        final ItemSword itemSword = (ItemSword)itemStack.getItem();
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180314_l.effectId, itemStack) + itemSword.func_150931_i();
    }
    
    @Override
    public void onEnable() {
        this.slots = 9;
        this.numberIdkWillfigureout = getEnchantmentOnSword(InvCleaner.mc.thePlayer.getHeldItem());
        super.onEnable();
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate tick) {
        if (this.getSettingByModule(this, "Open Inventory").booleanValue() && InvCleaner.mc.currentScreen == null) {
            return;
        }
        if (this.slots >= 45 && !this.someboolean) {
            this.slots = 9;
            return;
        }
        if (this.someboolean) {
            if (this.timer.hasTimeElapsed((double)(long)this.getSettingByModule(this, "CleanSpeed").doubleValue(), true) || InvCleaner.mc.thePlayer.inventoryContainer.getSlot(this.slots).getStack() == null) {
                InvCleaner.mc.playerController.windowClick(0, -999, 0, 0, InvCleaner.mc.thePlayer);
                InvCleaner.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(InvCleaner.mc.thePlayer.inventory.currentItem));
                InvCleaner.mc.playerController.syncCurrentPlayItem();
                this.someboolean = false;
            }
            return;
        }
        this.numberIdkWillfigureout = getEnchantmentOnSword(InvCleaner.mc.thePlayer.getHeldItem());
        final ItemStack stack = InvCleaner.mc.thePlayer.inventoryContainer.getSlot(this.slots).getStack();
        if (isItemBad(stack) && getEnchantmentOnSword(stack) <= this.numberIdkWillfigureout && stack != InvCleaner.mc.thePlayer.getHeldItem()) {
            InvCleaner.mc.playerController.windowClick(0, this.slots, 0, 0, InvCleaner.mc.thePlayer);
            this.someboolean = true;
        }
        ++this.slots;
    }
}
