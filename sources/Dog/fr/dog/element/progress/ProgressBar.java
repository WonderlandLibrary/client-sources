package fr.dog.element.progress;

import fr.dog.element.Element;
import fr.dog.element.Placement;
import fr.dog.util.render.RenderUtil;

import java.awt.*;

public class ProgressBar extends Element {
    private float percentage;

    public ProgressBar(long time) {
        super(Placement.BOTTOM, time, 100, 10);
    }

    @Override
    public void draw() {

        RenderUtil.drawRoundedRect(scaledResolution().getScaledWidth() / 2f - width / 2f, scaledResolution().getScaledHeight() - height, width, height, 5, new Color(-1));
        mc.fontRendererObj.drawString(String.valueOf(percentage), scaledResolution().getScaledWidth() / 2f - width / 2f + 5, scaledResolution().getScaledHeight() - height + 5, -1);
    }
}
