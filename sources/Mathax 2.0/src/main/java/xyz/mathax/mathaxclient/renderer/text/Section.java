package xyz.mathax.mathaxclient.renderer.text;

import xyz.mathax.mathaxclient.utils.render.color.Color;

public class Section {
    public final String text;

    public final Color color;

    public final SectionShadow shadow;

    public Section(String text, Color color, SectionShadow shadow) {
        this.text = text;
        this.color = color;
        this.shadow = shadow;
    }

    public Section(String text, Color color) {
        this.text = text;
        this.color = color;
        this.shadow = SectionShadow.Undefined;
    }
}
