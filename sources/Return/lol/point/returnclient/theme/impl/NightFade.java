package lol.point.returnclient.theme.impl;

import lol.point.returnclient.theme.Theme;
import lol.point.returnclient.theme.ThemeInfo;

import java.awt.*;

@ThemeInfo(
        name = "Night fade"
)
public class NightFade extends Theme {
    public NightFade() {
        super(new Color(0xFFF8BFEA), new Color(0xFFA38DD5));
    }
}
