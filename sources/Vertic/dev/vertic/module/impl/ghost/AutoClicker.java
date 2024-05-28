package dev.vertic.module.impl.ghost;

import dev.vertic.event.api.EventLink;
import dev.vertic.event.impl.other.TickEvent;
import dev.vertic.event.impl.render.Render2DEvent;
import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.setting.impl.NumberSetting;
import dev.vertic.util.math.MathUtil;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

public class AutoClicker extends Module {

    public final NumberSetting minCPS = new NumberSetting("Minimum CPS", 8, 1, 60, 1);
    public final NumberSetting maxCPS = new NumberSetting("Maximum CPS", 12, 1, 60, 1);
    private int currentCPS, tick;

    public AutoClicker() {
        super("AutoClicker", "Automatically clicks for you.", Category.GHOST);
        this.addSettings(minCPS, maxCPS);
    }

    @Override
    public void update() {
        if (minCPS.getValue() > maxCPS.getValue()) {
            minCPS.setValue(maxCPS.getValue());
        }
    }

    @EventLink
    public void onTick(TickEvent event) {
        if (tick <= 20) {
            tick++;
        } else {
            tick = 0;
            currentCPS = 0;
        }
    }

    @EventLink
    public void on2DRender(Render2DEvent event) {
        if (mc.currentScreen == null && Mouse.isButtonDown(0) && mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            mc.gameSettings.keyBindAttack.setPressed(true);
            return;
        }

        if (mc.currentScreen == null && !mc.thePlayer.isBlocking()) {
            Mouse.poll();

            if (Mouse.isButtonDown(0) && Math.random() * 50 <= minCPS.getValue() + (MathUtil.RANDOM.nextDouble() * (maxCPS.getValue() - minCPS.getValue()))) {
                sendClick(0, true);
                sendClick(0, false);
                currentCPS++;
            }
        }
    }

    private void sendClick(final int button, final boolean state) {
        final int keyBind = button == 0 ? mc.gameSettings.keyBindAttack.getKeyCode() : mc.gameSettings.keyBindUseItem.getKeyCode();
        KeyBinding.setKeyBindState(button == 0 ? mc.gameSettings.keyBindAttack.getKeyCode() : mc.gameSettings.keyBindUseItem.getKeyCode(), state);

        if (state) {
            KeyBinding.onTick(keyBind);
        }
    }

    @Override
    public String getSuffix() {
        return minCPS.getInt() + "-" + maxCPS.getInt();
    }
}
