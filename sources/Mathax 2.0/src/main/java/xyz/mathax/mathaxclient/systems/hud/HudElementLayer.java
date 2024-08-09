package xyz.mathax.mathaxclient.systems.hud;

import xyz.mathax.mathaxclient.gui.renderer.OverlayRenderer;
import xyz.mathax.mathaxclient.utils.render.Alignment;

import java.util.ArrayList;
import java.util.List;

public class HudElementLayer {
    private final OverlayRenderer renderer;

    private final List<HudElement> allElements, elements;

    private final Alignment.X xAlign;
    private final Alignment.Y yAlign;

    private final int xOffset, yOffset;

    public HudElementLayer(OverlayRenderer renderer, List<HudElement> allElements, Alignment.X xAlign, Alignment.Y yAlign, int xOffset, int yOffset) {
        this.renderer = renderer;
        this.allElements = allElements;
        this.elements = new ArrayList<>();
        this.xAlign = xAlign;
        this.yAlign = yAlign;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void add(HudElement element) {
        allElements.add(element);
        elements.add(element);

        element.settings.registerColorSettings(null);
    }

    public void align() {
        double x = xOffset * (xAlign == Alignment.X.Right ? -1 : 1);
        double y = yOffset * (yAlign == Alignment.Y.Bottom ? -1 : 1);
        for (HudElement element : elements) {
            element.update(renderer);

            element.box.x = xAlign;
            element.box.y = yAlign;
            element.box.xOffset = (int) Math.round(x);
            element.box.yOffset = (int) Math.round(y);

            if (yAlign == Alignment.Y.Bottom) {
                y -= element.box.height;
            } else {
                y += element.box.height;
            }
        }
    }
}