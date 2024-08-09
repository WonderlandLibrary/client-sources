package xyz.mathax.mathaxclient.systems.modules.world;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.Settings;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.process.ICustomGoalProcess;
import baritone.api.process.IMineProcess;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.player.FindItemResult;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.function.Predicate;

public class InfinityMiner extends Module {
    private final IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
    private final Settings baritoneSettings = BaritoneAPI.getSettings();

    private final BlockPos.Mutable homePos = new BlockPos.Mutable();

    private boolean prevMineScanDroppedItems;
    private boolean repairing;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup whenFullSettings = settings.createGroup("When Full");

    // General

    public final Setting<List<Block>> targetBlocksSetting = generalSettings.add(new BlockListSetting.Builder()
            .name("Target blocks")
            .description("The target blocks to mine.")
            .defaultValue(Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE)
            .filter(this::filterBlocks)
            .build()
    );

    public final Setting<List<Block>> repairBlocksSetting = generalSettings.add(new BlockListSetting.Builder()
            .name("Repair blocks")
            .description("The repair blocks to mine.")
            .defaultValue(Blocks.COAL_ORE, Blocks.REDSTONE_ORE, Blocks.NETHER_QUARTZ_ORE)
            .filter(this::filterBlocks)
            .build()
    );

    public final Setting<Double> startRepairingSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Repair threshold")
            .description("The durability percentage at which to start repairing.")
            .defaultValue(20)
            .range(1, 99)
            .sliderRange(1, 99)
            .build()
    );

    public final Setting<Double> startMiningSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Mine threshold")
            .description("The durability percentage at which to start mining.")
            .defaultValue(70)
            .range(1, 99)
            .sliderRange(1, 99)
            .build()
    );

    // When Full

    public final Setting<Boolean> walkHomeSetting = whenFullSettings.add(new BoolSetting.Builder()
            .name("Walk home")
            .description("Will walk 'home' when your inventory is full.")
            .defaultValue(false)
            .build()
    );

    public final Setting<Boolean> logOutSetting = whenFullSettings.add(new BoolSetting.Builder()
            .name("Log out")
            .description("Log out when your inventory is full. Will walk home FIRST if walk home is enabled.")
            .defaultValue(false)
            .build()
    );

    public InfinityMiner(Category category) {
        super(category, "Infinity Miner", "Allows you to essentially mine forever by mining repair blocks when the durability gets low. Needs a mending pickaxe.");
    }

    @Override
    public void onEnable() {
        prevMineScanDroppedItems = baritoneSettings.mineScanDroppedItems.value;
        baritoneSettings.mineScanDroppedItems.value = true;
        homePos.set(mc.player.getBlockPos());
        repairing = false;
    }

    @Override
    public void onDisable() {
        baritone.getPathingBehavior().cancelEverything();
        baritoneSettings.mineScanDroppedItems.value = prevMineScanDroppedItems;
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player.getInventory().getEmptySlot() == -1) {
            if (walkHomeSetting.get()) {
                if (isBaritoneNotWalking()) {
                    info("Walking home.");
                    baritone.getCustomGoalProcess().setGoalAndPath(new GoalBlock(homePos));
                } else if (mc.player.getBlockPos().equals(homePos) && logOutSetting.get()) {
                    logOut();
                }
            } else if (logOutSetting.get()) {
                logOut();
            } else {
                forceToggle(false);
            }

            return;
        }

        if (!findPickaxe()) {
            error("Could not find a usable mending pickaxe, disabling...");
            forceToggle(false);
            return;
        }

        if (!checkThresholds()) {
            error("Start mining value can't be lower than start repairing value, disabling...");
            forceToggle(false);
            return;
        }

        if (repairing) {
            if (!needsRepair()) {
                warning("Finished repairing, going back to mining.");
                repairing = false;
                mineTargetBlocks();
                return;
            }

            if (isBaritoneNotMining()) {
                mineRepairBlocks();
            }
        } else {
            if (needsRepair()) {
                warning("Pickaxe needs repair, beginning repair process");
                repairing = true;
                mineRepairBlocks();
                return;
            }

            if (isBaritoneNotMining()) {
                mineTargetBlocks();
            }
        }
    }

    private boolean needsRepair() {
        ItemStack itemStack = mc.player.getMainHandStack();
        double toolPercentage = ((itemStack.getMaxDamage() - itemStack.getDamage()) * 100f) / (float) itemStack.getMaxDamage();
        return !(toolPercentage > startMiningSetting.get() || (toolPercentage > startRepairingSetting.get() && !repairing));
    }

    private boolean findPickaxe() {
        Predicate<ItemStack> pickaxePredicate = (stack -> stack.getItem() instanceof PickaxeItem && Utils.hasEnchantments(stack, Enchantments.MENDING) && !Utils.hasEnchantments(stack, Enchantments.SILK_TOUCH));
        FindItemResult bestPick = InvUtils.findInHotbar(pickaxePredicate);

        if (bestPick.isOffhand()) {
            InvUtils.quickMove().fromOffhand().toHotbar(mc.player.getInventory().selectedSlot);
        } else if (bestPick.isHotbar()) {
            InvUtils.swap(bestPick.slot(), false);
        }

        return InvUtils.findInHotbar(pickaxePredicate).isMainHand();
    }

    private boolean checkThresholds() {
        return startRepairingSetting.get() < startMiningSetting.get();
    }

    private void mineTargetBlocks() {
        Block[] array = new Block[targetBlocksSetting.get().size()];

        baritone.getPathingBehavior().cancelEverything();
        baritone.getMineProcess().mine(targetBlocksSetting.get().toArray(array));
    }

    private void mineRepairBlocks() {
        Block[] array = new Block[repairBlocksSetting.get().size()];

        baritone.getPathingBehavior().cancelEverything();
        baritone.getMineProcess().mine(repairBlocksSetting.get().toArray(array));
    }

    private void logOut() {
        forceToggle(false);
        mc.player.networkHandler.sendPacket(new DisconnectS2CPacket(Text.literal("[Infinity Miner] Inventory is full.")));
    }

    private boolean isBaritoneNotMining() {
        return !(baritone.getPathingControlManager().mostRecentInControl().orElse(null) instanceof IMineProcess);
    }

    private boolean isBaritoneNotWalking() {
        return !(baritone.getPathingControlManager().mostRecentInControl().orElse(null) instanceof ICustomGoalProcess);
    }

    private boolean filterBlocks(Block block) {
        return block != Blocks.AIR && block.getDefaultState().getHardness(mc.world, null) != -1 && !(block instanceof FluidBlock);
    }
}