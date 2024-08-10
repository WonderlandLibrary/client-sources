package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;

import cc.slack.start.Slack;
import cc.slack.utils.render.RenderUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;


@ModuleInfo(
        name = "Radar",
        category = Category.RENDER
)
public class Radar extends Module {

    private final NumberValue<Float> xValue = new NumberValue<>("Pos X", 8.0F, 1.0F, 900.0F, 1F);
    private final NumberValue<Float> yValue = new NumberValue<>("Pos Y", 178F, 1.0F, 900.0F, 1F);
    private final NumberValue<Float> scaleValue = new NumberValue<>("Scale", 11.5F, 1.0F, 30.0F, 0.1F);
    private final BooleanValue roundedValue = new BooleanValue("Rounded", false);
    public final BooleanValue resetPos = new BooleanValue("Reset Position", false);


    private boolean dragging = false;
    private double dragX = 0, dragY = 0;

    public Radar() {
        addSettings(xValue, yValue, scaleValue, roundedValue, resetPos);
    }

    @Listen
    public void onRender(RenderEvent event) {
        if (resetPos.getValue()) {
            xValue.setValue(8F);
            yValue.setValue(170F);
            Slack.getInstance().getModuleManager().getInstance(Radar.class).resetPos.setValue(false);
        }

        float x = xValue.getValue();
        float y = yValue.getValue();
        float scale = scaleValue.getValue();
        boolean rounded = roundedValue.getValue();


        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int mouseX = Mouse.getX() * sr.getScaledWidth() / mc.displayWidth;
        int mouseY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / mc.displayHeight - 1;

        if (dragging) {
            xValue.setValue((float) (mouseX - dragX));
            yValue.setValue((float) (mouseY - dragY));
        }

        RenderUtil.drawRadar(x, y, scale, rounded);

        handleMouseInput(mouseX, mouseY, x, y, scale * 10, scale * 10);
    }

    private void handleMouseInput(int mouseX, int mouseY, float rectX, float rectY, float rectWidth, float rectHeight) {
        if (Mouse.isButtonDown(0)) {
            if (!dragging) {
                if (mouseX >= rectX && mouseX <= rectX + rectWidth &&
                        mouseY >= rectY && mouseY <= rectY + rectHeight) {
                    dragging = true;
                    dragX = mouseX - rectX;
                    dragY = mouseY - rectY;
                }
            }
        } else {
            dragging = false;
        }
    }
}
