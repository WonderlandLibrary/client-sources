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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

@ModuleInfo(
        name = "Tracers",
        category = Category.RENDER
)
public class Tracers extends Module {

    public final ModeValue<String> colormodes = new ModeValue<>("Color", new String[] { "Client Theme", "Rainbow", "Custom" });
    private final NumberValue<Integer> redValue = new NumberValue<>("Red", 0, 0, 255, 1);
    private final NumberValue<Integer> greenValue = new NumberValue<>("Green", 255, 0, 255, 1);
    private final NumberValue<Integer> blueValue = new NumberValue<>("Blue", 255, 0, 255, 1);
    private final NumberValue<Integer> alphaValue = new NumberValue<>("Alpha", 200, 0, 255, 1);

    public Tracers() {
        addSettings(colormodes, redValue, greenValue, blueValue, alphaValue);
    }

    @Listen
    public void onRender (RenderEvent event) {
        Color c = ColorUtil.getColor();
        if (event.getState() != RenderEvent.State.RENDER_3D) return;

        for(Entity entity : mc.theWorld.getLoadedEntityList()) {
            if(entity instanceof EntityPlayer && entity != mc.thePlayer) {
                RenderUtil.drawTracer(entity, redValue.getValue(), greenValue.getValue(), blueValue.getValue(), alphaValue.getValue());
            }
        }
    }

}
