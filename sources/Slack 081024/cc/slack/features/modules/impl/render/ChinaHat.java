package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.render.ColorUtil;
import cc.slack.utils.render.RenderUtil;
import io.github.nevalackin.radbus.Listen;


import java.awt.*;

@ModuleInfo(
        name = "ChinaHat",
        category = Category.RENDER
)
public class ChinaHat extends Module {

    public final ModeValue<String> colormodes = new ModeValue<>("Color", new String[] { "Client Theme", "Rainbow", "Custom" });
    private final NumberValue<Integer> redValue = new NumberValue<>("Red", 0, 0, 255, 1);
    private final NumberValue<Integer> greenValue = new NumberValue<>("Green", 255, 0, 255, 1);
    private final NumberValue<Integer> blueValue = new NumberValue<>("Blue", 255, 0, 255, 1);
    private final NumberValue<Integer> alphaValue = new NumberValue<>("Alpha", 100, 0, 255, 1);



    public ChinaHat() {
        addSettings(colormodes,redValue, greenValue, blueValue, alphaValue);
    }

    @Listen
    public void onRender (RenderEvent event) {
        Color c = ColorUtil.getColor();
        if (event.getState() != RenderEvent.State.RENDER_3D) return;

        if (mc.gameSettings.thirdPersonView != 0) {
            for (int i = 0; i < 400; ++i) {
                if (colormodes.getValue().equals("Client Theme")) {
                    RenderUtil.drawHat(mc.thePlayer, 0.009 + i * 0.0014, mc.timer.elapsedPartialTicks, 12, 2.0f, 2.2f - i * 7.85E-4f - 0.03f, c.getRGB());
                } else {
                    RenderUtil.drawHat(mc.thePlayer, 0.009 + i * 0.0014, mc.timer.elapsedPartialTicks, 12, 2.0f, 2.2f - i * 7.85E-4f - 0.03f, (!colormodes.getValue().equals("Rainbow")) ? new Color(redValue.getValue(), greenValue.getValue(), blueValue.getValue(), alphaValue.getValue()).getRGB() : ColorUtil.rainbow(-100, 1.0f, 0.47f).getRGB());
                }
            }
        }
    }

}
