package club.strifeclient.module.implementations.combat;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.event.implementations.player.MotionEvent;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.implementations.BooleanSetting;
import club.strifeclient.setting.implementations.DoubleSetting;
import club.strifeclient.util.math.MathUtil;
import club.strifeclient.util.player.InventoryUtil;
import club.strifeclient.util.system.Stopwatch;
import org.lwjglx.input.Mouse;

@ModuleInfo(name = "AutoClicker", description = "Automatically click.", category = Category.COMBAT)
public final class AutoClicker extends Module {

    private final DoubleSetting minClicksSetting = new DoubleSetting("Minimum CPS", 12, 1, 20, 1);
    private final DoubleSetting maximumClicksSetting = new DoubleSetting("Maximum CPS", 12, 1, 20, 1);

    private final BooleanSetting rightMouseSetting = new BooleanSetting("Right Mouse", false);
    private final BooleanSetting onlySwordSetting = new BooleanSetting("Only Sword", false);

    private final Stopwatch stopwatch = new Stopwatch();

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = e -> {
        if ((onlySwordSetting.getValue() && !InventoryUtil.isHoldingSword())) return;
        if (mc.currentScreen == null) {
            if (Mouse.isButtonDown(0)) {
                if (stopwatch.hasElapsed(1000 / MathUtil.randomInt(minClicksSetting.getInt(), maximumClicksSetting.getInt()))) {
                    mc.leftClickCounter = 0;
                    mc.leftClickMouse();
                    stopwatch.reset();
                }
            }
            if (Mouse.isButtonDown(2) && rightMouseSetting.getValue()) {
                if (stopwatch.hasElapsed(1000 / MathUtil.randomInt(minClicksSetting.getInt(), maximumClicksSetting.getInt()))) {
                    mc.rightClickMouse();
                    stopwatch.reset();
                }
            }
        }
    };

}
