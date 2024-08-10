package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.render.RenderUtil;
import io.github.nevalackin.radbus.Listen;

@ModuleInfo(
        name= "Zoom",
        category = Category.RENDER
)
public class Zoom extends Module {

    private final BooleanValue slowsensitivity = new BooleanValue("Slow Sensitivity", false);
    private final NumberValue<Float> smoothspeed = new NumberValue<>("Smooth Speed", 0.1F, 0.1F, 5.0F, 0.1F);

    float fov = 0;

    public Zoom() {
        addSettings(slowsensitivity, smoothspeed);
    }

    @Override
    public void onEnable() {
        fov = mc.gameSettings.fovSetting;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.fovSetting = fov;
    }

    @Listen
    public void onRender (RenderEvent event) {
        if (event.getState() == RenderEvent.State.RENDER_2D) {
            RenderUtil.renderZoom(fov, smoothspeed.getValue(), slowsensitivity.getValue());
        }
    }
}
