package io.github.nevalackin.radium.module.impl.combat;

import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.event.impl.render.Render3DEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.module.ModuleManager;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.utils.Wrapper;
import io.github.nevalackin.radium.utils.render.Colors;
import io.github.nevalackin.radium.utils.render.RenderingUtils;
import me.zane.basicbus.api.annotations.Listener;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(label = "Target Strafe", category = ModuleCategory.COMBAT)
public final class TargetStrafe extends Module {

    public final DoubleProperty radiusProperty = new DoubleProperty("Radius", 2.0D, 0.1D, 4.0D, 0.1D);
    public final Property<Boolean> holdSpaceProperty = new Property<>("Hold Space", true);
    private final Property<Boolean> renderProperty = new Property<>("Render", true);
    private final Property<Integer> renderColorProperty = new Property<>("Line Color", Colors.RED, renderProperty::getValue);
    private final DoubleProperty pointsProperty = new DoubleProperty("Points", 12, renderProperty::getValue, 4, 90, 1);
    private final DoubleProperty lineWidthProperty = new DoubleProperty("Line Width", 1, renderProperty::getValue, 0.5, 10, 0.5);

    private final List<Vector3f> points = new ArrayList<>();

    public byte direction;

    public static TargetStrafe getInstance() {
        return ModuleManager.getInstance(TargetStrafe.class);
    }

    @Listener
    public void onUpdatePositionEvent(UpdatePositionEvent event) {
        if (event.isPre()) {
            if (Wrapper.getPlayer().isCollidedHorizontally) {
                direction = (byte) -direction;
                return;
            }

            if (Wrapper.getGameSettings().keyBindLeft.isKeyDown()) {
                direction = 1;
                return;
            }

            if (Wrapper.getGameSettings().keyBindRight.isKeyDown())
                direction = -1;
        }
    }

    @Listener
    public void onRender3DEvent(Render3DEvent event) {
        KillAura killAura = ModuleManager.getInstance(KillAura.class);
        if (renderProperty.getValue() && killAura.getTarget() != null) {
            RenderingUtils.drawLinesAroundPlayer(killAura.getTarget(),
                    radiusProperty.getValue(),
                    event.getPartialTicks(),
                    pointsProperty.getValue().intValue(),
                    lineWidthProperty.getValue().floatValue(),
                    renderColorProperty.getValue());
        }
    }

    public enum TargetStrafeMode {

    }
}
