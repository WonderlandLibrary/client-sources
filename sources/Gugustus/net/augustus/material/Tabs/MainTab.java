package net.augustus.material.Tabs;

import net.augustus.Augustus;
import net.augustus.font.UnicodeFontRenderer;
import net.augustus.material.Main;
import net.augustus.material.Tab;

import java.awt.*;

public class MainTab extends Tab {
    private static UnicodeFontRenderer arial24;
    private static UnicodeFontRenderer arial18;
    static {
        try {
            arial24 = new UnicodeFontRenderer(Font.createFont(0, MainTab.class.getResourceAsStream("/ressources/arial.ttf")).deriveFont(24F));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            arial18 = new UnicodeFontRenderer(Font.createFont(0, MainTab.class.getResourceAsStream("/ressources/arial.ttf")).deriveFont(18F));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public MainTab() {
        name = "Main Menu";
    }

    @Override
    public void render(float mouseX, float mouseY) {
        super.render(mouseX, mouseY);
        arial24.drawString("Welcome!", Main.windowX + Main.animListX + 50, Main.windowY + 100, new Color(50, 50, 50).getRGB());
        //float width = arial24.getStringWidth("Welcome!");
        //arial18.drawString("用户:" + Augustus.getInstance().uid, Main.windowX + Main.animListX + 60 + width, Main.windowY + 110, new Color(100, 200, 50).getRGB());
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY) {
        super.mouseClicked(mouseX, mouseY);
    }
}
