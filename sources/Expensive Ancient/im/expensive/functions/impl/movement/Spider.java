package im.expensive.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import im.expensive.Expensive;
import im.expensive.events.EventMotion;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.ModeSetting;
import im.expensive.functions.settings.impl.SliderSetting;
import im.expensive.utils.math.StopWatch;
import im.expensive.utils.player.MouseUtil;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;

@FunctionRegister(name = "Spider", type = Category.Movement)
public class Spider extends Function {
    public ModeSetting mode = new ModeSetting("Mode", "Grim", "Grim", "Matrix");
    private final SliderSetting spiderSpeed = new SliderSetting(
            "Speed",
            2.0f,
            1.0f,
            10.0f,
            0.05f
    ).setVisible(() -> !mode.is("Grim"));

    StopWatch stopWatch = new StopWatch();


    public Spider() {
        addSettings(spiderSpeed, mode);
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
                    print("Блоки не найдены!");
                    toggle();
                    return;
                }
                if (!mc.player.collidedHorizontally) {
                    Expensive.getInstance().getFunctionRegistry().getKillAura().canAttackWithSpider = true;
                    return;
                }

                Expensive.getInstance().getFunctionRegistry().getKillAura().nextAttackDelay = 10;
                Expensive.getInstance().getFunctionRegistry().getKillAura().canAttackWithSpider = false;
                Expensive.getInstance().getFunctionRegistry().getKillAura().lastPosY = 0;

                if (mc.player.isOnGround()) {
                    motion.setOnGround(true);
                    mc.player.setOnGround(true);
                    mc.player.jump();
                }
                if (mc.player.fallDistance > 0 && mc.player.fallDistance < 2) {

                    int last = mc.player.inventory.currentItem;
                    mc.player.inventory.currentItem = slotInHotBar;

                    motion.setPitch(80);
                    motion.setYaw(mc.player.getHorizontalFacing().getHorizontalAngle());
                    mc.player.rotationPitchHead = 80;
                    mc.player.rotationYawHead = mc.player.getHorizontalFacing().getHorizontalAngle();
                    mc.player.renderYawOffset = mc.player.getHorizontalFacing().getHorizontalAngle();

                    RayTraceResult result = MouseUtil.rayTrace(4, motion.getYaw(), motion.getPitch(), mc.player);
                    if (result instanceof BlockRayTraceResult blockRayTraceResult) {
                        mc.playerController.processRightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, blockRayTraceResult);
                        mc.player.swingArm(Hand.MAIN_HAND);
                    }

                    mc.player.inventory.currentItem = last;
                    mc.player.fallDistance = 0;
                }
            }
        }
    }

    private void placeBlocks(EventMotion motion, int block) {
        int last = mc.player.inventory.currentItem;
        mc.player.inventory.currentItem = block;
        motion.setPitch(80);
        motion.setYaw(mc.player.getHorizontalFacing().getHorizontalAngle());
        mc.player.rotationPitchHead = 80;
        mc.player.rotationYawHead = mc.player.getHorizontalFacing().getHorizontalAngle();
        mc.player.renderYawOffset = mc.player.getHorizontalFacing().getHorizontalAngle();
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

    @Override
    public void onDisable() {
        Expensive.getInstance().getFunctionRegistry().getKillAura().nextAttackDelay = 0;
        Expensive.getInstance().getFunctionRegistry().getKillAura().lastPosY = 0;
        Expensive.getInstance().getFunctionRegistry().getKillAura().canAttackWithSpider = true;
        super.onDisable();
    }
}
