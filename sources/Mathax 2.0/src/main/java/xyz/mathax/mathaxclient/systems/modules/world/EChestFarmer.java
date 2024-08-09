package xyz.mathax.mathaxclient.systems.modules.world;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.player.PacketMine;
import xyz.mathax.mathaxclient.utils.player.FindItemResult;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import xyz.mathax.mathaxclient.utils.world.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;

public class EChestFarmer extends Module {
    private final VoxelShape SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);

    private BlockPos target;

    private int startCount;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup renderSettings = settings.createGroup("Render");

    private final Setting<Boolean> selfToggleSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Self toggle")
            .description("Disables when you reach the desired amount of obsidian.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> ignoreExistingSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Ignore existing")
            .description("Ignores existing obsidian in your inventory and mines the total target amount.")
            .defaultValue(true)
            .visible(selfToggleSetting::get)
            .build()
    );

    private final Setting<Integer> amountSetting = generalSettings.add(new IntSetting.Builder()
            .name("Amount")
            .description("The amount of obsidian to farm.")
            .defaultValue(64)
            .range(8, 512)
            .sliderRange(8, 128)
            .visible(selfToggleSetting::get)
            .build()
    );

    // Render

    private final Setting<Boolean> swingHandSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Swing hand")
            .description("Swing hand client side.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> renderSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Render a block overlay where the obsidian will be placed.")
            .defaultValue(true)
            .build()
    );

    private final Setting<ShapeMode> shapeModeSetting = renderSettings.add(new EnumSetting.Builder<ShapeMode>()
            .name("Shape mode")
            .description("How the shapes are rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );

    private final Setting<SettingColor> sideColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Side color")
            .description("The color of the sides of the blocks being rendered.")
            .defaultValue(new SettingColor(205, 0, 0, 50))
            .build()
    );

    private final Setting<SettingColor> lineColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Line color")
            .description("The color of the lines of the blocks being rendered.")
            .defaultValue(new SettingColor(205, 0, 0))
            .build()
    );

    public EChestFarmer(Category category) {
        super(category, "E-Chest Farmer", "Places and breaks EChests to farm obsidian.");
    }

    @Override
    public void onEnable() {
        target = null;

        startCount = InvUtils.find(Items.OBSIDIAN).count();
    }

    @Override
    public void onDisable() {
        InvUtils.swapBack();
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (target == null) {
            if (mc.crosshairTarget == null || mc.crosshairTarget.getType() != HitResult.Type.BLOCK) {
                return;
            }

            BlockPos pos = ((BlockHitResult) mc.crosshairTarget).getBlockPos().up();
            BlockState state = mc.world.getBlockState(pos);
            if (state.getMaterial().isReplaceable() || state.getBlock() == Blocks.ENDER_CHEST) {
                target = ((BlockHitResult) mc.crosshairTarget).getBlockPos().up();
            } else {
                return;
            }
        }

        if (PlayerUtils.distanceTo(target) > mc.interactionManager.getReachDistance()) {
            error("Target block pos out of reach.");
            target = null;
            return;
        }

        if (selfToggleSetting.get() && InvUtils.find(Items.OBSIDIAN).count() - (ignoreExistingSetting.get() ? startCount : 0) >= amountSetting.get()) {
            InvUtils.swapBack();
            forceToggle(false);
            return;
        }

        if (mc.world.getBlockState(target).getBlock() == Blocks.ENDER_CHEST) {
            double bestScore = -1;
            int bestSlot = -1;
            for (int i = 0; i < 9; i++) {
                ItemStack itemStack = mc.player.getInventory().getStack(i);
                if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, itemStack) > 0) {
                    continue;
                }

                double score = itemStack.getMiningSpeedMultiplier(Blocks.ENDER_CHEST.getDefaultState());
                if (score > bestScore) {
                    bestScore = score;
                    bestSlot = i;
                }
            }

            if (bestSlot == -1) {
                return;
            }

            InvUtils.swap(bestSlot, true);
            BlockUtils.breakBlock(target, swingHandSetting.get());
        }

        if (mc.world.getBlockState(target).getMaterial().isReplaceable()) {
            FindItemResult echest = InvUtils.findInHotbar(Items.ENDER_CHEST);
            if (!echest.found()) {
                error("No ender chests found in hotbar, disabling...");
                forceToggle(false);
                return;
            }

            BlockUtils.place(target, echest, true, 0, true);
        }
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        if (target == null || !renderSetting.get() || Modules.get().get(PacketMine.class).isMiningBlock(target)) {
            return;
        }

        Box box = SHAPE.getBoundingBoxes().get(0);
        event.renderer.box(target.getX() + box.minX, target.getY() + box.minY, target.getZ() + box.minZ, target.getX() + box.maxX, target.getY() + box.maxY, target.getZ() + box.maxZ, sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get(), 0);
    }
}