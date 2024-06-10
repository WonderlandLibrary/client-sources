package me.kaimson.melonclient.utils;

import me.kaimson.melonclient.*;
import me.kaimson.melonclient.gui.utils.*;

public class WatermarkRenderer
{
    public static void render(final int x, final int y) {
        bfl.E();
        bfl.l();
        bfl.f();
        bfl.c();
        bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
        ave.A().P().a(Client.LOGO);
        GuiUtils.a(x - 158 - 6, y - 24 - 1, 0.0f, 0.0f, 77, 24, 163.0f, 24.0f);
        GuiUtils.setGlColor(Client.getMainColor(255));
        GuiUtils.a(x - 79 - 5, y - 24 - 2, 76.0f, 24.0f, 82, 24, 158.0f, 24.0f);
        bfl.F();
    }
}
