package wtf.resolute.ui.styled;

import wtf.resolute.utiled.render.ColorUtils;


public class Style {
    public String name;
    public int[] colors;

    public Style(String name, int... colors) {
        this.name = name;
        this.colors = colors;
    }


    public int getColor(int index) {

        return ColorUtils.gradient2(5,
                index, colors);
    }

}