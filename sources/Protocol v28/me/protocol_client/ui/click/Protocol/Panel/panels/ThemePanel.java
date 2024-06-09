package me.protocol_client.ui.click.Protocol.Panel.panels;

import java.util.ArrayList;

import me.protocol_client.Protocol;
import me.protocol_client.ui.click.Protocol.Elements.moreElements.ThemeStuff;
import me.protocol_client.ui.click.Protocol.Panel.Panel;
import me.protocol_client.ui.click.Protocol.theme.ClickTheme;

public class ThemePanel extends Panel {
    public ThemePanel(float x, float y) {
        super("Themes", new ArrayList<>(), x, y, 20, 10);

        float elementY = 4;
        for (ClickTheme theme : Protocol.getGuiClick().getThemes()) {
            getElements().add(new ThemeStuff(theme, x + 2, elementY + 2, Protocol.getGuiClick().getTheme().getElementWidth(), Protocol.getGuiClick().getTheme().getElementHeight()));
            elementY += Protocol.getGuiClick().getTheme().getElementHeight() + 1;
        }
    }
}
