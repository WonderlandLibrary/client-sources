package wtf.shiyeno.ui.midnight;

import wtf.shiyeno.util.render.ColorUtil;

public class Style {
    public String name;
    public int[] colors;

    public Style(String name, int... colors) {
        this.name = name;
        this.colors = colors;
    }

    public int getColor(int index) {
        if (name.equals("Цветной")) {
            return ColorUtil.astolfo(10, index, 0.5F, 1.0F, 1.0F);
        }
        if (name.equals("Радужный")) {
            return ColorUtil.rainbow(10, index, 0.5F, 1.0F, 1.0F);
        }
        return ColorUtil.gradient(5,
                index, colors);
    }
}