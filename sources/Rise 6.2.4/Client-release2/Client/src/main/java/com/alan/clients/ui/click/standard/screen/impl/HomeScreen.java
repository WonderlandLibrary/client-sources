package com.alan.clients.ui.click.standard.screen.impl;

import com.alan.clients.ui.click.standard.RiseClickGUI;
import com.alan.clients.ui.click.standard.screen.Screen;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.vector.Vector2f;

public final class HomeScreen implements Screen, Accessor {

    @Override
    public void onRender(final int mouseX, final int mouseY, final float partialTicks) {
        final RiseClickGUI clickGUI = getClickGUI();

        /* Saves the position of the Rise logo */
        final Vector2f positionOfLogo = new Vector2f(clickGUI.position.x + 20, clickGUI.position.y + 20);

        /* Draws logo */
//        nunitoLarge.drawString(Rise.NAME, positionOfLogo.x, positionOfLogo.y, clickGUI.logoColor.hashCode());

        /* Draws version number */
//        Fonts.SF_ROUNDED.get(18, Weight.REGULAR).drawString(Rise.VERSION,
//                positionOfLogo.x + nunitoLarge.width(Rise.NAME), positionOfLogo.y + 11,
//                clickGUI.fontDarkerColor.hashCode());

        /* Draws session information */
//        Fonts.SF_ROUNDED.get(16, Weight.REGULAR).drawString("Username: " + PlayerUtil.name(),
//                positionOfLogo.x + 1.5f, positionOfLogo.y + 30, clickGUI.fontColor.hashCode());
//        Fonts.SF_ROUNDED.get(16, Weight.REGULAR).drawString("UID: 1",
//                positionOfLogo.x + 1.5f, positionOfLogo.y + 43, clickGUI.fontColor.hashCode());

        final Vector2f positionOfSearch = new Vector2f(clickGUI.position.x + clickGUI.scale.x / 2, clickGUI.position.y + clickGUI.scale.y - 14);
        final String text = "Start typing to search...";
//        Fonts.SF_ROUNDED.get(20, Weight.REGULAR).drawString(text, positionOfSearch.x - Fonts.SF_ROUNDED.get(20, Weight.REGULAR).width(text) / 2f, positionOfSearch.y, new Color(clickGUI.fontColor.getRed(), clickGUI.fontColor.getBlue(), clickGUI.fontColor.getGreen(), 70).hashCode());
    }
}
