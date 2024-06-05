package me.mees.remix.modules.player;

import me.satisfactory.base.utils.timer.*;
import me.satisfactory.base.module.*;
import me.satisfactory.base.setting.*;
import net.minecraft.nbt.*;
import me.satisfactory.base.events.*;
import net.minecraft.client.gui.*;
import me.satisfactory.base.*;
import pw.stamina.causam.scan.method.model.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.*;

public class AutoArmor extends Module
{
    TimerUtil timer;
    
    public AutoArmor() {
        super("AutoArmor", 0, Category.PLAYER);
        this.timer = new TimerUtil();
        this.addSetting(new Setting("Inventory", this, false));
        this.addSetting(new Setting("Delay", this, 300.0, 1.0, 1000.0, true, 10.0));
    }
    
    public static int getEnchantmentLevel(final int p_77506_0_, final ItemStack p_77506_1_) {
        if (p_77506_1_ == null) {
            return 0;
        }
        final NBTTagList var2 = p_77506_1_.getEnchantmentTagList();
        if (var2 == null) {
            return 0;
        }
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final short var4 = var2.getCompoundTagAt(var3).getShort("id");
            final short var5 = var2.getCompoundTagAt(var3).getShort("lvl");
            if (var4 == p_77506_0_) {
                return var5;
            }
        }
        return 0;
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate tick) {
        if (!this.timer.hasTimeElapsed(this.getSettingByModule(this, "Delay").doubleValue()) || AutoArmor.mc.thePlayer.capabilities.isCreativeMode || (AutoArmor.mc.currentScreen == null && this.getSettingByModule(this, "Inventory").booleanValue()) || AutoArmor.mc.currentScreen instanceof GuiChat || Base.INSTANCE.getModuleManager().getModByName("InvCleaner").isEnabled()) {
            return;
        }
        for (int b10 = 5; b10 <= 8; ++b10) {
            if (this.equipArmor(b10)) {
                this.timer.reset();
                break;
            }
        }
    }
    
    private boolean equipArmor(final int b10) {
        int currentProtection = -1;
        int slot = -1;
        ItemArmor current = null;
        if (AutoArmor.mc.thePlayer.inventoryContainer.getSlot(b10).getStack() != null && AutoArmor.mc.thePlayer.inventoryContainer.getSlot(b10).getStack().getItem() instanceof ItemArmor) {
            current = (ItemArmor)AutoArmor.mc.thePlayer.inventoryContainer.getSlot(b10).getStack().getItem();
            currentProtection = current.damageReduceAmount + getEnchantmentLevel(Enchantment.field_180310_c.effectId, AutoArmor.mc.thePlayer.inventoryContainer.getSlot(b10).getStack());
        }
        for (int i2 = 9; i2 <= 44; i2 = (byte)(i2 + 1)) {
            final ItemStack stack = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i2).getStack();
            if (stack != null && stack.getItem() instanceof ItemArmor) {
                final ItemArmor armor = (ItemArmor)stack.getItem();
                final int armorProtection = armor.damageReduceAmount + getEnchantmentLevel(Enchantment.field_180310_c.effectId, stack);
                if (this.checkArmor(armor, b10) && (current == null || currentProtection < armorProtection)) {
                    currentProtection = armorProtection;
                    current = armor;
                    slot = i2;
                }
            }
        }
        if (slot == -1) {
            return false;
        }
        final boolean isNull = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(b10).getStack() == null;
        if (!isNull) {
            this.dropSlot(b10);
            return true;
        }
        try {
            this.clickSlot(slot, 0, true);
        }
        catch (Exception ex) {}
        return true;
    }
    
    private boolean checkArmor(final ItemArmor item, final int b10) {
        return (b10 == 5 && item.getUnlocalizedName().startsWith("item.helmet")) || (b10 == 6 && item.getUnlocalizedName().startsWith("item.chestplate")) || (b10 == 7 && item.getUnlocalizedName().startsWith("item.leggings")) || (b10 == 8 && item.getUnlocalizedName().startsWith("item.boots"));
    }
    
    private void clickSlot(final int slot, final int mouseButton, final boolean shiftClick) {
        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.thePlayer.inventoryContainer.windowId, slot, mouseButton, shiftClick ? 1 : 0, AutoArmor.mc.thePlayer);
    }
    
    private void dropSlot(final int slot) {
        AutoArmor.mc.playerController.windowClick(0, slot, 1, 4, AutoArmor.mc.thePlayer);
    }
}
