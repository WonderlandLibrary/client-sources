package xyz.mathax.mathaxclient.systems.modules.player;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.eventbus.EventPriority;
import xyz.mathax.mathaxclient.events.entity.player.StartBreakingBlockEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.world.InfinityMiner;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import xyz.mathax.mathaxclient.utils.settings.ListMode;
import xyz.mathax.mathaxclient.utils.world.BlockUtils;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.BambooSaplingBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.*;
import xyz.mathax.mathaxclient.settings.*;

import java.util.List;
import java.util.function.Predicate;

public class AutoTool extends Module {
    private boolean wasPressed;
    private boolean shouldSwitch;

    private int ticks;
    private int bestSlot;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup listSettings = settings.createGroup("List");

    // General

    private final Setting<EnchantPreference> preferSetting = generalSettings.add(new EnumSetting.Builder<EnchantPreference>()
            .name("Prefer")
            .description("Either to prefer Silk Touch, Fortune, or none.")
            .defaultValue(EnchantPreference.Fortune)
            .build()
    );

    private final Setting<Boolean> silkTouchForEnderChestSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Silk touch for ender chest")
            .description("Mine Ender Chests only with the Silk Touch enchantment.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> antiBreakSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Anti break")
            .description("Stop you from breaking your tool.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Integer> breakDurabilitySetting = generalSettings.add(new IntSetting.Builder()
            .name("Anti break percentage")
            .description("The durability percentage to stop using a tool.")
            .defaultValue(10)
            .range(1, 100)
            .sliderRange(1, 100)
            .visible(antiBreakSetting::get)
            .build()
    );

    private final Setting<Boolean> switchBackSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Switch back")
            .description("Switche your hand to whatever was selected when releasing your attack key.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Integer> switchDelaySetting = generalSettings.add((new IntSetting.Builder()
            .name("Switch delay")
            .description("Delay in ticks before switching tools.")
            .defaultValue(0)
            .build()
    ));

    // List

    private final Setting<ListMode> listModeSetting = listSettings.add(new EnumSetting.Builder<ListMode>()
            .name("List mode")
            .description("Selection mode.")
            .defaultValue(ListMode.Blacklist)
            .build()
    );

    private final Setting<List<Item>> whitelistSetting = listSettings.add(new ItemListSetting.Builder()
            .name("Whitelist")
            .description("The tools you want to use.")
            .visible(() -> listModeSetting.get() == ListMode.Whitelist)
            .filter(AutoTool::isTool)
            .build()
    );

    private final Setting<List<Item>> blacklistSetting = listSettings.add(new ItemListSetting.Builder()
            .name("Blacklist")
            .description("The tools you don't want to use.")
            .visible(() -> listModeSetting.get() == ListMode.Blacklist)
            .filter(AutoTool::isTool)
            .build()
    );

    public AutoTool(Category category) {
        super(category, "Auto Tool", "Automatically switches to the most effective tool when performing an action.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (Modules.get().isEnabled(InfinityMiner.class)) {
            return;
        }

        if (switchBackSetting.get() && !mc.options.attackKey.isPressed() && wasPressed && InvUtils.previousSlot != -1) {
            InvUtils.swapBack();
            wasPressed = false;
            return;
        }

        if (ticks <= 0 && shouldSwitch && bestSlot != -1) {
            InvUtils.swap(bestSlot, switchBackSetting.get());
            shouldSwitch = false;
        } else {
            ticks--;
        }

        wasPressed = mc.options.attackKey.isPressed();
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onStartBreakingBlock(StartBreakingBlockEvent event) {
        if (Modules.get().isEnabled(InfinityMiner.class)) {
            return;
        }

        BlockState blockState = mc.world.getBlockState(event.blockPos);
        if (!BlockUtils.canBreak(event.blockPos, blockState)) {
            return;
        }

        ItemStack currentStack = mc.player.getMainHandStack();

        double bestScore = -1;
        bestSlot = -1;

        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = mc.player.getInventory().getStack(i);
            if (listModeSetting.get() == ListMode.Whitelist && !whitelistSetting.get().contains(itemStack.getItem())) {
                continue;
            }

            if (listModeSetting.get() == ListMode.Blacklist && blacklistSetting.get().contains(itemStack.getItem())) {
                continue;
            }

            double score = getScore(itemStack, blockState, silkTouchForEnderChestSetting.get(), preferSetting.get(), itemStack2 -> !shouldStopUsing(itemStack2));
            if (score < 0) {
                continue;
            }

            if (score > bestScore) {
                bestScore = score;
                bestSlot = i;
            }
        }

        if ((bestSlot != -1 && (bestScore > getScore(currentStack, blockState, silkTouchForEnderChestSetting.get(), preferSetting.get(), itemStack -> !shouldStopUsing(itemStack))) || shouldStopUsing(currentStack) || !isTool(currentStack))) {
            ticks = switchDelaySetting.get();
            if (ticks == 0) {
                InvUtils.swap(bestSlot, true);
            } else {
                shouldSwitch = true;
            }
        }

        currentStack = mc.player.getMainHandStack();

        if (shouldStopUsing(currentStack) && isTool(currentStack)) {
            mc.options.attackKey.setPressed(false);
            event.setCancelled(true);
        }
    }

    private boolean shouldStopUsing(ItemStack itemStack) {
        return antiBreakSetting.get() && (itemStack.getMaxDamage() - itemStack.getDamage()) < (itemStack.getMaxDamage() * breakDurabilitySetting.get() / 100);
    }

    public static double getScore(ItemStack itemStack, BlockState state, boolean silkTouchEnderChest, EnchantPreference enchantPreference, Predicate<ItemStack> good) {
        if (!good.test(itemStack) || !isTool(itemStack)) {
            return -1;
        }

        if (silkTouchEnderChest && state.getBlock() == Blocks.ENDER_CHEST && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, itemStack) == 0) {
            return -1;
        }

        double score = 0;
        score += itemStack.getMiningSpeedMultiplier(state) * 1000;
        score += EnchantmentHelper.getLevel(Enchantments.UNBREAKING, itemStack);
        score += EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, itemStack);
        score += EnchantmentHelper.getLevel(Enchantments.MENDING, itemStack);

        if (enchantPreference == EnchantPreference.Fortune) {
            score += EnchantmentHelper.getLevel(Enchantments.FORTUNE, itemStack);
        }

        if (enchantPreference == EnchantPreference.Silk_Touch) {
            score += EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, itemStack);
        }

        if (itemStack.getItem() instanceof SwordItem item && (state.getBlock() instanceof BambooBlock || state.getBlock() instanceof BambooSaplingBlock)) {
            score += 9000 + (item.getMaterial().getMiningLevel() * 1000);
        }

        return score;
    }

    public static boolean isTool(Item item) {
        return item instanceof ToolItem || item instanceof ShearsItem;
    }

    public static boolean isTool(ItemStack itemStack) {
        return isTool(itemStack.getItem());
    }

    public enum EnchantPreference {
        None("None"),
        Fortune("Fortune"),
        Silk_Touch("Silk Touch");

        private final String name;

        EnchantPreference(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}