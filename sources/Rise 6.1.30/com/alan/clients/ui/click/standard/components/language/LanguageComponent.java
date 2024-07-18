package com.alan.clients.ui.click.standard.components.language;

import com.alan.clients.Client;
import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.ui.click.standard.screen.Colors;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.gui.GUIUtil;
import com.alan.clients.util.localization.Locale;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.util.vector.Vector2f;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.util.ResourceLocation;

@Getter
@RequiredArgsConstructor
public class LanguageComponent implements Accessor {
    private final Locale locale;
    private final String localName, englishName;

    private double lastY;

    public void draw(double y) {
        final Vector2f position = getClickGUI().getPosition();
        final Vector2f scale = getClickGUI().getScale();
        final double sidebar = getClickGUI().getSidebar().sidebarWidth;

        RenderUtil.roundedRectangle(position.getX() + sidebar + 8, position.getY() + y, 285,
                38, 6, Colors.OVERLAY.get());

        // Draw locale english name
        Fonts.MAIN.get(20, Weight.REGULAR).draw(this.englishName, position.getX() + sidebar + 18, position.getY() + y + 9,
                Client.INSTANCE.getLocale().equals(this.locale) ? getTheme().getAccentColor(new Vector2d(0, position.y / 5)).getRGB() :
                        Colors.TEXT.getRGB());

        // Draw locale native name
        Fonts.MAIN.get(17, Weight.REGULAR).draw(this.localName, position.getX() + sidebar + 18,
                position.getY() + y + 24, Colors.TEXT.getRGBWithAlpha(100));

        // Draw flag
        RenderUtil.image(new ResourceLocation("rise/icons/language/" + locale.getFile() + ".png"),
                position.getX() + sidebar + Fonts.MAIN.get(20, Weight.REGULAR).width(this.englishName) + 25, position.getY() + y + 5, 15, 15);

        this.lastY = y;
    }

    public void click(double mouseX, double mouseY) {
        final Vector2f position = getClickGUI().getPosition();
        final double sidebar = getClickGUI().getSidebar().sidebarWidth;

        if (GUIUtil.mouseOver(position.getX() + sidebar + 8, position.getY() + lastY,
                285, 38, mouseX, mouseY)) {
            Client.INSTANCE.setLocale(this.locale);
            Client.INSTANCE.terminate();
            Client.INSTANCE.init();
        }
    }
}