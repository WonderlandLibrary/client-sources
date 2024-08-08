package lol.point.returnclient.theme.impl;

import lol.point.returnclient.theme.Theme;
import lol.point.returnclient.theme.ThemeInfo;

import java.awt.*;

@ThemeInfo(
        name = "Crimson red"
)
public class CrimsonRed extends Theme {
    public CrimsonRed() {
        super(new Color(255, 3, 3), new Color(255, 70, 70));
    }
}
