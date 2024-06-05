package me.superskidder.lune.guis.clickguis.tomorrow.values;

import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.utils.render.RenderUtil;
import me.superskidder.lune.values.Value;
import me.superskidder.lune.values.type.Mode;
import org.lwjgl.input.Mouse;
import java.awt.*;

import static me.superskidder.lune.guis.clickguis.tomorrow.MainWindow.mainColor;

public class ModeRender extends ValueRender {
    public ModeRender(Value v, float x, float y) {
        super(v, x, y);
    }

    @Override
    public void onRender(float valueX, float valueY) {
        float valueY1 = valueY;
        for (Enum m : ((Mode<?>) this.value).getModes()) {
            RenderUtil.circle(x + 8, valueY1 + 2, 4, mainColor);
            RenderUtil.circle(x + 8, valueY1 + 2, 4, mainColor);
            RenderUtil.circle(x + 8, valueY1 + 2, 4, mainColor);

            if (((Mode<?>) this.value).getValue().toString() == (m.name())) {
                RenderUtil.circle(x + 8f, valueY1 + 2f, 2.8f, new Color(255, 255, 255));
                RenderUtil.circle(x + 8f, valueY1 + 2f, 2.8f, new Color(255, 255, 255));
                RenderUtil.circle(x + 8f, valueY1 + 2f, 2.8f, new Color(255, 255, 255));
            }
//                    RenderUtil.drawRect(x + 6, valueY + 2, x + 11, valueY + 7, new Color(255, 255, 255));
            FontLoaders.F18.drawString(m.name(), x + 16, valueY1, new Color(115, 115, 115).getRGB());
            if (isHovered(x + 5, valueY1, x + 95, valueY1 + FontLoaders.F18.getHeight(), mouseX, mouseY) && Mouse.isButtonDown(0)) {
                ((Mode<?>) this.value).setMode(m.name());
            }
            valueY1 += 20;
        }
    }

    @Override
    public void onClicked(float x, float y, int clickType) {

    }

    @Override
    public void onMouseMove(float x, float y, int clickType) {
        this.mouseX = x;
        this.mouseY = y;

    }
}
