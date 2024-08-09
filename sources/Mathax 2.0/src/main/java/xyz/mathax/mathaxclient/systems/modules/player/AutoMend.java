package xyz.mathax.mathaxclient.systems.modules.player;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import xyz.mathax.mathaxclient.utils.settings.ListMode;

import java.util.List;

public class AutoMend extends Module {
    private boolean didMove;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup listSettings = settings.createGroup("List");

    // General

    private final Setting<Boolean> forceSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Force")
            .description("Replace item in offhand even if there is some other non-repairable item.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> autoDisableSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Auto disable")
            .description("Automatically disables when there are no more items to repair.")
            .defaultValue(true)
            .build()
    );

    // List

    private final Setting<ListMode> listModeSetting = listSettings.add(new EnumSetting.Builder<ListMode>()
            .name("List mode")
            .description("Selection mode.")
            .defaultValue(ListMode.Whitelist)
            .build()
    );

    private final Setting<List<Block>> whitelistSetting = listSettings.add(new BlockListSetting.Builder()
            .name("Whitelist")
            .description("The blocks you want to mine.")
            .visible(() -> listModeSetting.get() == ListMode.Whitelist)
            .build()
    );

    private final Setting<List<Block>> blacklistSetting = listSettings.add(new BlockListSetting.Builder()
            .name("Blacklist")
            .description("The blocks you don't want to mine.")
            .visible(() -> listModeSetting.get() == ListMode.Blacklist)
            .build()
    );

    public AutoMend(Category category) {
        super(category, "Auto Mend", "Automatically replaces items in your offhand with mending when fully repaired.");
    }

    @Override
    public void onEnable() {
        didMove = false;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (shouldWait()) {
            return;
        }

        int slot = getSlot();
        if (slot == -1) {
            if (autoDisableSetting.get()) {
                info("Repaired all items, disabling");

                if (didMove) {
                    int emptySlot = getEmptySlot();
                    InvUtils.move().fromOffhand().to(emptySlot);
                }

                toggle();
            }
        }
        else {
            InvUtils.move().from(slot).toOffhand();
            didMove = true;
        }
    }

    private boolean shouldWait() {
        ItemStack itemStack = mc.player.getOffHandStack();
        if (itemStack.isEmpty()) {
            return false;
        }

        if (EnchantmentHelper.getLevel(Enchantments.MENDING, itemStack) > 0) {
            return itemStack.getDamage() != 0;
        }

        return !forceSetting.get();
    }

    private int getSlot() {
        for (int i = 0; i < mc.player.getInventory().main.size(); i++) {
            ItemStack itemStack = mc.player.getInventory().getStack(i);
            if (listModeSetting.get() == ListMode.Whitelist && !whitelistSetting.get().contains(itemStack.getItem())) {
                continue;
            }

            if (listModeSetting.get() == ListMode.Blacklist && blacklistSetting.get().contains(itemStack.getItem())) {
                continue;
            }

            if (EnchantmentHelper.getLevel(Enchantments.MENDING, itemStack) > 0 && itemStack.getDamage() > 0) {
                return i;
            }
        }

        return -1;
    }

    private int getEmptySlot() {
        for (int i = 0; i < mc.player.getInventory().main.size(); i++) {
            if (mc.player.getInventory().getStack(i).isEmpty()) return i;
        }

        return -1;
    }
}
