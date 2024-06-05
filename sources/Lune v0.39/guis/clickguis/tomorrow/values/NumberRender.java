package me.superskidder.lune.guis.clickguis.tomorrow.values;

import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.utils.render.RenderUtil;
import me.superskidder.lune.values.Value;
import me.superskidder.lune.values.type.Num;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.math.BigDecimal;

import static me.superskidder.lune.guis.clickguis.tomorrow.MainWindow.mainColor;

public class NumberRender extends ValueRender {
    public NumberRender(Value v, float x, float y) {
        super(v, x, y);
    }

    @Override
    public void onRender(float valueX, float valueY) {
        this.valueX = valueX;
        this.valueY = valueY;
        float present = 90
                * (((Number) value.getValue()).floatValue() - ((Num) value).getMin().floatValue())
                / (((Num) value).getMax().floatValue() - ((Num) value).getMin().floatValue());
//                    RenderUtil.drawRect(x + 5, valueY, x + 12, valueY + 7, new Color(171, 71, 188));
//                    RenderUtil.drawRect(x + 6, valueY + 2, x + 11, valueY + 7, new Color(255, 255, 255));
        FontLoaders.F16.drawString(value.getName(), x + 5, valueY - 5, new Color(115,115,115).getRGB());
        FontLoaders.F16.drawString(value.getValue().toString(), x + 95 - FontLoaders.F16.getStringWidth(value.getValue().toString()), valueY - 5, new Color(115,115,115).getRGB());
        value.animX1 = present;

        if (value.animX != value.animX1) {
            value.animX += (value.animX1 - value.animX) / 30;
        }

        RenderUtil.drawRect(x + 5, valueY + 3, x + 95, valueY + 4.5f, new Color(222, 222, 222));
        RenderUtil.drawRect(x + 5, valueY + 3, x + 5 + value.animX, valueY + 4.5f, mainColor);

        if ((isHovered(x + 5, valueY + 0.5f, x + 95, valueY + 6.5f, mouseX, mouseY) || value.drag) && Mouse.isButtonDown(0)) {
            value.drag = true;
            float render2 = ((Num) value).getMin().floatValue();
            double max = ((Num) value).getMax().doubleValue();
//            float inc = (((Num<?>) value).getIncrement()).floatValue();
            float inc = 0.1f;
            double valAbs = (double) mouseX - x - 5;
            double perc = valAbs / (((x + 95) - (x + 5)));
            perc = Math.min(Math.max(0.0D, perc), 1.0D);
            double valRel = (max - render2) * perc;
            float val = (float) (render2 + valRel);
            val = (float) (Math.round(val * (1.0D / inc)) / (1.0D / inc));
            BigDecimal b = new BigDecimal(val);
            double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            ((Num) value).setValue(f1);

            RenderUtil.circle(x + 5 + value.animX, valueY + 3.5f, 4, mainColor);
            RenderUtil.circle(x + 5 + value.animX, valueY + 3.5f, 4, mainColor);
            RenderUtil.circle(x + 5 + value.animX, valueY + 3.5f, 4, mainColor);
            RenderUtil.circle(x + 5 + value.animX, valueY + 3.5f, 3, -1);
            RenderUtil.circle(x + 5 + value.animX, valueY + 3.5f, 3, -1);
            RenderUtil.circle(x + 5 + value.animX, valueY + 3.5f, 3, -1);
        } else {

            RenderUtil.circle(x + 5 + value.animX, valueY + 3.5f, 4, new Color(200, 200, 200).getRGB());
            RenderUtil.circle(x + 5 + value.animX, valueY + 3.5f, 4, new Color(200, 200, 200).getRGB());
            RenderUtil.circle(x + 5 + value.animX, valueY + 3.5f, 4, new Color(200, 200, 200).getRGB());
            RenderUtil.circle(x + 5 + value.animX, valueY + 3.5f, 3, -1);
            RenderUtil.circle(x + 5 + value.animX, valueY + 3.5f, 3, -1);
            RenderUtil.circle(x + 5 + value.animX, valueY + 3.5f, 3, -1);
        }

    }

    @Override
    public void onClicked(float x, float y, int clickType) {

    }

    @Override
    public void onMouseMove(float x, float y, int clickType) {
        mouseX = x;
        mouseY = y;
        if (value.drag && !Mouse.isButtonDown(0)) {
            value.drag = false;
        }

    }
}
