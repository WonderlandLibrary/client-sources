package xyz.mathax.mathaxclient.gui.widgets;

import xyz.mathax.mathaxclient.utils.render.color.Color;

public abstract class WQuad extends WWidget {
    public Color color;

    public WQuad(Color color) {
        this.color = color;
    }

    @Override
    protected void onCalculateSize() {
        double scale = theme.scale(32);

        width = scale;
        height = scale;
    }
}
