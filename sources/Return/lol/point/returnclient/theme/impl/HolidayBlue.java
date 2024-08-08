package lol.point.returnclient.theme.impl;

import lol.point.returnclient.theme.Theme;
import lol.point.returnclient.theme.ThemeInfo;

import java.awt.*;

@ThemeInfo(
        name = "Holiday blue"
)
public class HolidayBlue extends Theme {
    public HolidayBlue() {
        super(new Color(51, 191, 218), new Color(255, 255, 255));
    }
}
