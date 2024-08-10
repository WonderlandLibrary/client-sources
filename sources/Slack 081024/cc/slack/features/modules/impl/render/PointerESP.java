package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.render.RenderUtil;
import io.github.nevalackin.radbus.Listen;

import java.awt.*;


@ModuleInfo(
        name = "PointerESP",
        category = Category.RENDER
)
public class PointerESP extends Module {

    public final ModeValue<String> colormodes = new ModeValue<>("Color", new String[] { "Client Theme", "Rainbow", "Custom" });
    public final NumberValue<Integer> redValue = new NumberValue<>("Red", 116, 0, 255, 1);
    public final NumberValue<Integer> greenValue = new NumberValue<>("Green", 202, 0, 255, 1);
    public final NumberValue<Integer> blueValue = new NumberValue<>("Blue", 255, 0, 255, 1);
    public final NumberValue<Integer> alphaValue = new NumberValue<>("Alpha", 100, 0, 255, 1);

    public PointerESP() {
        addSettings(colormodes,redValue, greenValue, blueValue, alphaValue);
    }

    @Listen
    public void onRender (RenderEvent event) {
        if (event.getState() == RenderEvent.State.RENDER_2D) {
            RenderUtil.RenderPointerESP();
        }
    }

}
