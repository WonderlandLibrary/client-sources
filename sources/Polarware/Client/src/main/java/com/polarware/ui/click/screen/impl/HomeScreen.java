package com.polarware.ui.click.screen.impl;

import com.polarware.ui.click.RiseClickGUI;
import com.polarware.ui.click.screen.Screen;
import com.polarware.util.interfaces.InstanceAccess;
import com.polarware.util.vector.Vector2f;

import java.awt.*;

public final class HomeScreen extends Screen implements InstanceAccess {

    @Override
    public void onRender(final int mouseX, final int mouseY, final float partialTicks) {
        final RiseClickGUI clickGUI = getStandardClickGUI();

        final Vector2f positionOfSearch = new Vector2f(clickGUI.position.x + clickGUI.scale.x / 2, clickGUI.position.y + clickGUI.scale.y - 14);
        final String text = "Start typing to search...";
        this.nunitoNormal.drawString(text, positionOfSearch.x - this.nunitoNormal.width(text) / 2f, positionOfSearch.y, new Color(clickGUI.fontColor.getRed(), clickGUI.fontColor.getBlue(), clickGUI.fontColor.getGreen(), 70).hashCode());
    }

    @Override
    public void onKey(final char typedChar, final int keyCode) {
    }

    @Override
    public void onClick(final int mouseX, final int mouseY, final int mouseButton) {

    }

    @Override
    public void onMouseRelease() {

    }

    @Override
    public void onBloom() {

    }

    @Override
    public void onInit() {

    }
}
