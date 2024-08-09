package fun.ellant.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventMotion;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.functions.settings.impl.SliderSetting;
import fun.ellant.utils.math.StopWatch;
import fun.ellant.utils.player.MouseUtil;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;

@FunctionRegister(name = "Spider", type = Category.MOVEMENT, desc = "Человек паук")
public class Spider extends Function {
    public ModeSetting mode = new ModeSetting("Mode", "Grim", "Grim", "Matrix", "WaterSpider");
    private final SliderSetting spiderSpeed = new SliderSetting(
            "Speed",
            2.0f,
            1.0f,
            10.0f,
            0.05f
    ).setVisible(() -> !mode.is("Grim"));

    StopWatch stopWatch = new StopWatch();
    StopWatch waterPlaceTimer = new StopWatch(); // Timer for placing water

    public Spider() {
        addSettings(spiderSpeed, mode);
    }

    private void placeWater(EventMotion motion, int waterBucketSlot) {
        if (!waterPlaceTimer.isReached(50)) { // Ensure a delay of 500 ms between placements
            return;
        }

        int lastSlot = mc.player.inventory.currentItem;
        mc.player.inventory.currentItem = waterBucketSlot;
        motion.setPitch(80);
        motion.setYaw(mc.player.getHorizontalFacing().getHorizontalAngle());
        BlockRayTraceResult result = (BlockRayTraceResult) MouseUtil.rayTrace(4, motion.getYaw(), motion.getPitch(), mc.player);

        // Place water
        mc.player.swingArm(Hand.MAIN_HAND);
        mc.playerController.processRightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, result);

        // Reset to the original item
        mc.player.inventory.currentItem = lastSlot;
        mc.player.fallDistance = 0;

        waterPlaceTimer.reset(); // Reset the timer after placing water
    }

    @Subscribe
    private void onMotion(EventMotion motion) {
        switch (mode.get()) {
            case "Matrix" -> {
                if (!mc.player.collidedHorizontally) {
                    return;
                }
                long speed = MathHelper.clamp(500 - (spiderSpeed.get().longValue() / 2 * 100), 0, 500);
                if (stopWatch.isReached(speed)) {
                    motion.setOnGround(true);
                    mc.player.setOnGround(true);
                    mc.player.collidedVertically = true;
                    mc.player.collidedHorizontally = true;
                    mc.player.isAirBorne = true;
                    mc.player.jump();
                    stopWatch.reset();
                }
            }
            case "Grim" -> {
                int slotInHotBar = getSlotInInventoryOrHotbar(true);

                if (slotInHotBar == -1) {
                    print("Блоки не найдены, возьмите в хотбар блоки, чтобы спайдер заработал.");
                    toggle();
                    return;
                }
                if (!mc.player.collidedHorizontally) {
                    return;
                }
                if (mc.player.isOnGround()) {
                    motion.setOnGround(true);
                    mc.player.setOnGround(true);
                    mc.player.jump();
                }
                if (mc.player.fallDistance > 0 && mc.player.fallDistance < 2) {
                    placeBlocks(motion, slotInHotBar);
                }
            }
            case "WaterSpider" -> {
                if (mc.player.isOnGround()) {
                    motion.setOnGround(true);
                    mc.player.setOnGround(true);
                    mc.player.jump();
                } else if (mc.player.isInWater() || mc.player.isInLava()) {
                    int waterBucketSlot = getWaterBucketSlot();
                    if (waterBucketSlot != -1) {
                        placeWater(motion, waterBucketSlot);
                    }
                }
            }
        }
    }

    public int getWaterBucketSlot() {
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == Items.WATER_BUCKET) {
                return i;
            }
        }
        return -1;
    }

    private void placeBlocks(EventMotion motion, int block) {
        int last = mc.player.inventory.currentItem;
        mc.player.inventory.currentItem = block;
        motion.setPitch(80);
        motion.setYaw(mc.player.getHorizontalFacing().getHorizontalAngle());
        BlockRayTraceResult r = (BlockRayTraceResult) MouseUtil.rayTrace(4, motion.getYaw(), motion.getPitch(), mc.player);
        mc.player.swingArm(Hand.MAIN_HAND);
        mc.playerController.processRightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, r);
        mc.player.inventory.currentItem = last;
        mc.player.fallDistance = 0;
    }

    public int getSlotInInventoryOrHotbar(boolean inHotBar) {
        int firstSlot = inHotBar ? 0 : 9;
        int lastSlot = inHotBar ? 9 : 36;
        int finalSlot = -1;
        for (int i = firstSlot; i < lastSlot; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.TORCH) {
                continue;
            }

            if (mc.player.inventory.getStackInSlot(i).getItem() instanceof BlockItem
                    || mc.player.inventory.getStackInSlot(i).getItem() == Items.WATER_BUCKET) {
                finalSlot = i;
            }
        }

        return finalSlot;
    }
}
