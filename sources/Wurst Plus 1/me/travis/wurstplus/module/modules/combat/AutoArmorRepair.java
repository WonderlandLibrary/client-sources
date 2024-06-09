package me.travis.wurstplus.module.modules.combat;

import java.util.HashMap;
import java.util.Map;

import me.travis.wurstplus.command.Command;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.module.ModuleManager;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.util.WorldUtils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

// t r a v i s i s h o t a n d o r e p i c

@Module.Info(name = "Auto Armor Repair", category = Module.Category.COMBAT)
public class AutoArmorRepair extends Module
{

    private Setting<Integer> delay = this.register(Settings.integerBuilder("Delay").withMinimum(12).withValue(16).withMaximum(24).build());
    private Setting<Integer> damage = this.register(Settings.integerBuilder("Heal Damage %").withMinimum(10).withValue(60).withMaximum(90).build());

    private int mostDamagedSlot; 
    private int mostDamage;
    private int lastSlot;
    private int counter;
    private int armorCount;
    private int wait;

    private int[] slots;

    private boolean shouldThrow;
    private boolean shouldArmor;
    private boolean falg;
    
    @Override
    protected void onEnable() {
        this.falg = false;
        this.mostDamage = -1;
        this.mostDamagedSlot = -1;
        this.shouldArmor = false;
        this.armorCount = 0;
        this.slots = new int[3];
        this.wait = 0;
        takeOffArmor();
        if (ModuleManager.getModuleByName("Tickplace").isEnabled()) {
            this.falg = true;
            ModuleManager.getModuleByName("Tickplace").disable();
        }
    }

    @Override
    protected void onDisable() {
        if (this.falg) {
            ModuleManager.getModuleByName("Tickplace").enable();
        }
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || this.isDisabled() || mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.shouldThrow) {
            WorldUtils.lookAtBlock(new BlockPos((Vec3i)this.mc.player.getPosition().add(0, -1, 0)));
            mc.player.inventory.currentItem = findXP();
            mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
            if (isRepaired() || this.counter > 40) {
                this.shouldThrow = false;
                this.shouldArmor = true;
                mc.player.inventory.currentItem = this.lastSlot;
                Command.sendChatMessage("done");
            } 
            this.counter++;
        }
        if (this.shouldArmor) {
            if (this.wait >= this.delay.getValue()) {
                this.wait = 0;
                mc.playerController.windowClick(0, this.slots[this.armorCount], 0, ClickType.QUICK_MOVE, (EntityPlayer)mc.player);
                mc.playerController.updateController();
                this.armorCount++;
                if (this.armorCount > 2) {
                    this.armorCount = 0;
                    this.shouldArmor = false;
                    this.disable();
                    return;
                }
            }
            this.wait++;
        }
    }

    public int getMostDamagedSlot() {
        for (final Map.Entry<Integer, ItemStack> armorSlot : getArmor().entrySet()) {
            final ItemStack stack = armorSlot.getValue();
            if (stack.getItemDamage() > this.mostDamage) {
                this.mostDamage = stack.getItemDamage();
                this.mostDamagedSlot = armorSlot.getKey();
            }
        }
        return this.mostDamagedSlot;
    }

    public boolean isRepaired() {
        for (final Map.Entry<Integer, ItemStack> armorSlot : getArmor().entrySet()) {
            final ItemStack stack = armorSlot.getValue();
            if (armorSlot.getKey() == this.mostDamagedSlot) {
                float percent = ( (float) this.damage.getValue() / (float) 100);
                int dam = Math.round(stack.getMaxDamage() * percent);
                int goods = stack.getMaxDamage() - stack.getItemDamage();
                if (dam <= goods) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public int findXP() {
        this.lastSlot = mc.player.inventory.currentItem;
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemExpBottle)) continue;
            slot = i;
            break;
        }
        if (slot == -1) {
            Command.sendChatMessage("no xp");
            this.disable();
            return 1;
        }
        return slot;
    }

    public boolean isSpace() {
        int spareSlots = 0;
        for (final Map.Entry<Integer, ItemStack> invSlot : getInventory().entrySet()) {
            final ItemStack stack = invSlot.getValue();
            if (stack.getItem() == Items.AIR) {
                this.slots[spareSlots] = invSlot.getKey();
                spareSlots++;
                if (spareSlots > 2) {
                    return true;
                }
            }
        }
        return false;
    }

    public void takeOffArmor() {
        if (isSpace()) {
            getMostDamagedSlot();
            if (this.mostDamagedSlot != -1) {
                for (final Map.Entry<Integer, ItemStack> armorSlot : getArmor().entrySet()) { 
                    if (armorSlot.getKey() != this.mostDamagedSlot) {
                        mc.playerController.windowClick(0, armorSlot.getKey(), 0, ClickType.QUICK_MOVE, (EntityPlayer)mc.player);
                    }                        
                }
                this.counter = 0;
                this.shouldThrow = true;
                return;
            }
        }
        Command.sendChatMessage("Please ensure there is atleast 3 inv slots open!");
        this.disable();
        return;
    }

    private static Map<Integer, ItemStack> getInventory() {
        return getInventorySlots(9, 44);
    }

    private static Map<Integer, ItemStack> getArmor() {
        return getInventorySlots(5, 8);
    }

    private static Map<Integer, ItemStack> getInventorySlots(int current, final int last) {
        final Map<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            fullInventorySlots.put(current, (ItemStack)mc.player.inventoryContainer.getInventory().get(current));
            ++current;
        }
        return fullInventorySlots;
    }

}
