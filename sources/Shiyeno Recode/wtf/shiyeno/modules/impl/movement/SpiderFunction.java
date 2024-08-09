package wtf.shiyeno.modules.impl.movement;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventMotion;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.imp.ModeSetting;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.ClientUtil;
import wtf.shiyeno.util.math.RayTraceUtil;
import wtf.shiyeno.util.misc.TimerUtil;

@FunctionAnnotation(name = "Spider", type = Type.Movement)
public class SpiderFunction extends Function {
    TimerUtil timerUtil = new TimerUtil();
    public ModeSetting mode = new ModeSetting("Мод", "Grim", "Grim", "Matrix");

    private final SliderSetting spiderSpeed = new SliderSetting(
            "Скорость",
            2.0f,
            1.0f,
            10.0f,
            0.05f
    ).setVisible(() -> !mode.is("Grim"));


    public SpiderFunction() {
        addSettings(spiderSpeed, mode);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventMotion e) {
            handleEventMotion(e);
        }
    }

    private void handleEventMotion(EventMotion motion) {
        if (!mc.player.collidedHorizontally) {
            return;
        }

        switch (mode.get()) {
            case "Matrix" -> {
                long speed = MathHelper.clamp(500 - (spiderSpeed.getValue().longValue() / 2 * 100), 0, 500);
                if (timerUtil.hasTimeElapsed(speed)) {
                    motion.setOnGround(true);
                    mc.player.setOnGround(true);
                    mc.player.collidedVertically = true;
                    mc.player.collidedHorizontally = true;
                    mc.player.isAirBorne = true;
                    mc.player.jump();
                    timerUtil.reset();
                }
            }
            case "Grim" -> {
                if (mc.player.isOnGround()) {
                    motion.setOnGround(true);
                    mc.player.setOnGround(true);
                    mc.player.jump();
                }
                if (mc.player.fallDistance > 0 && mc.player.fallDistance < 2) {
                    int block = -1;
                    for (int i = 0; i < 9; i++) {
                        ItemStack s = mc.player.inventory.getStackInSlot(i);
                        if (s.getItem() instanceof BlockItem) {
                            block = i;
                            break;
                        }
                    }

                    if (block == -1) {
                        ClientUtil.sendMesage("Для использования этого спайдера у вас должны блоки в хотбаре!");
                        toggle();
                        return;
                     }

                    int last = mc.player.inventory.currentItem;
                    mc.player.inventory.currentItem = block;
                    motion.setPitch(80);
                    motion.setYaw(mc.player.getHorizontalFacing().getHorizontalAngle());
                    BlockRayTraceResult r = (BlockRayTraceResult) RayTraceUtil.rayTrace(4, motion.getYaw(), motion.getPitch(), mc.player);
                    mc.player.swingArm(Hand.MAIN_HAND);
                    mc.playerController.processRightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, r);
                    mc.player.inventory.currentItem = last;
                    mc.player.fallDistance = 0;
                }
            }
        }
    }
}