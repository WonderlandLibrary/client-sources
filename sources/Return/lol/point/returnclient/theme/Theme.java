package lol.point.returnclient.theme;

import java.awt.*;

public class Theme {

    public final String name;
    public Color gradient1, gradient2;

    public Theme(Color gradient1, Color gradient2) {
        this.name = getClass().getAnnotation(ThemeInfo.class).name();
        this.gradient1 = gradient1;
        this.gradient2 = gradient2;
    }

}
