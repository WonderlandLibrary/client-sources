package me.napoleon.napoline.guis.clickguis.tomorrow.values;

import org.lwjgl.input.Mouse;

import me.napoleon.napoline.font.FontLoaders;
import me.napoleon.napoline.junk.values.Value;
import me.napoleon.napoline.utils.render.RenderUtil;

import static me.napoleon.napoline.guis.clickguis.tomorrow.MainWindow.mainColor;

import java.awt.*;

public class OptionRender extends ValueRender {

    public OptionRender(Value v, float x, float y) {
        super(v, x, y);
    }


    @Override
    public void onRender(float valueX, float valueY) {
        this.valueX = valueX;
        this.valueY = valueY;
        if (((Boolean) value.getValue())) {
            RenderUtil.drawRect(valueX + 5, valueY, valueX + 12, valueY + 7, mainColor);

            RenderUtil.drawRect(valueX + 6, valueY + 1, valueX + 11, valueY + 6, new Color(255, 255, 255).getRGB());

            FontLoaders.F18.drawString(value.getName(), valueX + 16, valueY + 1, new Color(115,115,115).getRGB());
        } else {
            RenderUtil.drawRect(valueX + 5, valueY, valueX + 12, valueY + 7, mainColor);
//                    RenderUtil.drawRect(x + 6, valueY + 2, x + 11, valueY + 7, new Color(255, 255, 255));
            FontLoaders.F18.drawString(value.getName(), valueX + 16, valueY + 1, new Color(115,115,115).getRGB());
        }
    }

    @Override
    public void onClicked(float x, float y, int clickType) {

    }

    @Override
    public void onMouseMove(float x1, float y1, int clickType) {
        mouseX = x1;
        mouseY = y1;
        if (isHovered(x + 5, valueY, x + 95, valueY + 7, mouseX, mouseY) && Mouse.isButtonDown(0) && timerUtil.delay(200)) {
            value.setValue(!(boolean) value.getValue());
            timerUtil.reset();
        }
    }
}
