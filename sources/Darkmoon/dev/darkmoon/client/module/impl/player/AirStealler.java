package dev.darkmoon.client.module.impl.player;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.impl.combat.AutoTotem;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.utility.misc.TimerHelper;
import net.minecraft.block.BlockNote;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ModuleAnnotation(name = "AirStealler", category = Category.UTIL)
public class AirStealler extends Module {
    private NumberSetting delay = new NumberSetting("Delay", 1, 1, 1000, 1, () -> Boolean.TRUE);
    TimerHelper timer = new TimerHelper();
    @EventTarget
    public void unUpdate(EventUpdate event) {
        if (mc.currentScreen == null) {
            return;
        }
        if (mc.currentScreen instanceof GuiChest chest) {
            for (int i = 0; i < chest.lowerChestInventory.getSizeInventory(); i++) {
                ItemStack stack = chest.lowerChestInventory.getStackInSlot(i);
                if (!WhiteList(stack))
                    continue;
                if (!stack.isEmpty() && timer.hasReached(delay.get())) {
                    mc.playerController.windowClick(mc.player.openContainer.windowId, i, 0, ClickType.QUICK_MOVE, mc.player);
                    timer.reset();
                } else {
                    this.timer.reset();
                }
            }
        }
    }


    public boolean WhiteList(ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemArmor || itemStack.getItem() == Items.TOTEM_OF_UNDYING ||
                itemStack.getItem() == Items.ELYTRA || (itemStack.getItem() == Item.getItemFromBlock(Blocks.GOLD_BLOCK)
                || (itemStack.getItem() == Item.getItemFromBlock(Blocks.TRIPWIRE_HOOK) || itemStack.getItem() instanceof ItemEnchantedBook ||
                itemStack.getItem() instanceof ItemEgg));
    }
}
