package com.alan.clients.ui.click.standard.screen.impl;

import com.alan.clients.Client;
import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.ui.click.standard.screen.Screen;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.render.ScissorUtil;
import com.alan.clients.util.vector.Vector2f;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class InfoScreen implements Screen, Accessor {

    @Override
    public void onRender(int mouseX, int mouseY, float partialTicks) {

        final Vector2f position = getClickGUI().getPosition();
        final Vector2f scale = getClickGUI().getScale();
        final double sidebar = getClickGUI().getSidebar().sidebarWidth;

        // Draw left column (client name and version)
        Fonts.MAIN.get(32, Weight.REGULAR).draw(Client.NAME, position.getX() + sidebar + 20,
                position.getY() + 20, Color.WHITE.getRGB());
        Fonts.MAIN.get(16, Weight.REGULAR).draw(Client.VERSION,
                position.getX() + sidebar + 20 + Fonts.MAIN.get(32, Weight.REGULAR).width(Client.NAME),
                position.getY() + 18, new Color(255, 255, 255, 100).getRGB());
        Fonts.MAIN.get(17, Weight.REGULAR).draw(Client.VERSION_FULL, position.getX() + sidebar + 20,
                position.getY() + 50, new Color(255, 255, 255, 164).getRGB());

        // Draw right column (user info)

        // Draw credits
        final int seconds = 45;
        final double offset = ((System.currentTimeMillis() / 1000D) % seconds) * 11;

        ScissorUtil.enable();
        ScissorUtil.scissor(new ScaledResolution(mc), position.getX() + sidebar, position.getY() + 90, scale.getX(), scale.getY() - 175);

        // Draw text
        Fonts.MAIN.get(17, Weight.REGULAR).draw(getCredits1(), position.getX() + sidebar + 20,
                position.getY() + 100 - offset + (scale.getY() - 175), new Color(164, 164, 164, 64).getRGB());
        Fonts.MAIN.get(17, Weight.REGULAR).draw(getCredits2(), position.getX() + sidebar + 155,
                position.getY() + 100 - offset + (scale.getY() - 175), new Color(164, 164, 164, 64).getRGB());

        // Draw gradients to make the fade in/out look on the scrolling text
//        RenderUtil.verticalGradient(position.getX() + sidebar, position.getY() + 89, scale.getX() - sidebar,
//                35, getClickGUI().getBackgroundColor(), ColorUtil.withAlpha(getClickGUI().getBackgroundColor(), 0));
//        RenderUtil.verticalGradient(position.getX() + sidebar, position.getY() + scale.getY() - 120,
//                scale.getX() - sidebar, 35, ColorUtil.withAlpha(getClickGUI().getBackgroundColor(), 0),
//                getClickGUI().getBackgroundColor());

        ScissorUtil.disable();
    }

    // Returns the first column of the credits.
    // Method so I can hotswap the string
    private static String getCredits1() {
        return "Rise " + Client.VERSION + " (riseclient.com)\n" +
                "\n" +
                "Designed and built by Alan and Hazsi.\n" +
                "\n" +
                "Additional Development\n" +
                "  -> Nicklas, Tecnio, Patrick, Strikeless\n" +
                "\n" +
                "UI/UX Design\n" +
                "  -> Hazsi, Alan\n" +
                "\n" +
                "Rendering/OpenGL\n" +
                "  -> Patrick, Strikeless\n" +
                "\n" +
                "Protection and Obfuscation\n" +
                "  -> Alan, NyanCatForEver\n" +
                "\n" +
                "Scripting Documentation\n" +
                "  -> Alice\n" +
                "\n" +
                "Localization\n" +
                "-> a cat (Romanian)\n" +
                "-> Alice (Russian)\n" +
                "-> Bx2 (Arabic)\n" +
                "-> El Gatito Grande (Portuguese)\n" +
                "-> days (Hebrew)\n" +
                "-> Duits/Jess (Indonesian)\n" +
                "-> fan87 (Chinese Traditional)\n" +
                "-> Grekgamer13 (Greek)\n" +
                "-> Jb (Austrian)\n" +
                "-> kinja (Polish)\n" +
                "-> I_only_die_twice (Swedish)\n" +
                "\n" +
                "Special Thanks\n" +
                "  -> Auth and Error as contributing to development\n" +
                "  -> Config and script makers\n" +
                "  -> You, as a user of Rise. Thank you, on behalf of all of us!\n";
    }

    // Returns the second column of the credits
    private static String getCredits2() {
        return "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                "-> im ray (Latvian)\n" +
                "-> Novus (Spanish)\n" +
                "-> Mongrall (Hungarian)\n" +
                "-> MOON (Italian)\n" +
                "-> Stimular (Czech)\n" +
                "-> Tapludeforfair (French)\n" +
                "-> toastedwaffles (Vietnamese)\n" +
                "-> trollo (German)\n" +
                "-> Velcola (Norwegian)\n" +
                "-> whoistinywifi (Thai)\n" +
                "-> YK_FCZ (Chinese Simplified)\n";
    }
}
