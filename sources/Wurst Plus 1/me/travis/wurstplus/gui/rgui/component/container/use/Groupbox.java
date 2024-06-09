package me.travis.wurstplus.gui.rgui.component.container.use;

import me.travis.wurstplus.gui.rgui.component.container.AbstractContainer;
import me.travis.wurstplus.gui.rgui.render.theme.Theme;

/**
 * Created by 086 on 26/06/2017.
 */
public class Groupbox extends AbstractContainer {

    String name;

    public Groupbox(Theme theme, String name) {
        super(theme);
        this.name = name;
    }

    public Groupbox(Theme theme, String name, int x, int y) {
        this(theme, name);
        setX(x);
        setY(y);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
