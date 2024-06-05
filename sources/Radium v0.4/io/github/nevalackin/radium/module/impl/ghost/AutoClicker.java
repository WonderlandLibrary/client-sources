package io.github.nevalackin.radium.module.impl.ghost;

import io.github.nevalackin.radium.event.impl.world.TickEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.utils.RandomUtils;
import io.github.nevalackin.radium.utils.TimerUtil;
import io.github.nevalackin.radium.utils.Wrapper;
import me.zane.basicbus.api.annotations.Listener;

@ModuleInfo(label = "Auto Clicker", category = ModuleCategory.GHOST)
public class AutoClicker extends Module {

    private final DoubleProperty minApsProperty = new DoubleProperty("Min APS", 9.0, 1.0,
            20.0, 0.1);
    private final DoubleProperty maxApsProperty = new DoubleProperty("Max APS", 12.0, 1.0,
            20.0, 0.1);
    private final Property<Boolean> rightClickProperty = new Property<>("Right Click", false);

    private final TimerUtil cpsTimer = new TimerUtil();

    @Listener
    public void TickEvent() {
        if (rightClickProperty.getValue() && Wrapper.getGameSettings().keyBindUseItem.isKeyDown()) {
            Wrapper.getMinecraft().rightClickMouse();
        } else if (Wrapper.getGameSettings().keyBindAttack.isKeyDown()) {
            long cps = (long) RandomUtils.getRandomInRange(minApsProperty.getValue(), maxApsProperty.getValue());
            if (cpsTimer.hasElapsed(1000 / cps)) {
                Wrapper.getMinecraft().clickMouse();
                cpsTimer.reset();
            }
        }
    }
}
