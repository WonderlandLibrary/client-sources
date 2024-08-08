package lol.point.returnclient.ui;

import lol.point.Return;
import lol.point.returnclient.module.impl.client.Theme;
import lol.point.returnclient.ui.components.UIButton;
import lol.point.returnclient.ui.utils.security.LoginUtil;
import lol.point.returnclient.util.render.FastFontRenderer;
import lol.point.returnclient.util.render.shaders.ShaderUtil;
import net.minecraft.client.gui.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends GuiScreen {

    private List<UIButton> buttons = new ArrayList<>();

    private final FastFontRenderer big = Return.INSTANCE.fontManager.getFont("Nano-Regular 200");
    private final FastFontRenderer small = Return.INSTANCE.fontManager.getFont("Nano-Regular 20");
    private final FastFontRenderer copyRight = Return.INSTANCE.fontManager.getFont("ProductSans-Bold 18");
    private final FastFontRenderer icons = Return.INSTANCE.fontManager.getFont("ReturnNew-Icons 18");
    private final FastFontRenderer text = Return.INSTANCE.fontManager.getFont("ProductSans-Regular 18");

    public void initGui() {
        buttons.clear();
        buttons.add(new UIButton("Singleplayer", "a", 200, 20));
        buttons.add(new UIButton("Multiplayer", "b", 200, 20));
        buttons.add(new UIButton("Alt Manager", "Y", 200, 20));
        buttons.add(new UIButton("Options", "l", 200, 20));
        buttons.add(new UIButton("Quit", "e", 200, 20));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        RenderUtil.rectangle(0, 0, width, height, new Color(22, 22, 22));

        Color gradient1, gradient2;
        gradient1 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient1;
        gradient2 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient2;

        float yOffset = 0;
        for (UIButton button : buttons) {
            float iconX = ((float) sr.getScaledWidth() / 2f) - (200 / 2f), iconY = ((float) sr.getScaledHeight() / 2f) - (20 / 2f) + yOffset;
            switch (button.buttonText) {
                case "Singleplayer" -> {
                    iconX += 4;
                    iconY += 6;
                }
                case "Multiplayer" -> {
                    iconX += 3;
                    iconY += 6;
                }
                case "Alt Manager" -> {
                    iconX += 4;
                    iconY += 6.5f;
                }
                case "Options", "Quit" -> {
                    iconX += 5;
                    iconY += 6.5f;
                }
            }
            button.drawButton(mouseX, mouseY, ((float) sr.getScaledWidth() / 2f) - (200 / 2f), ((float) sr.getScaledHeight() / 2f) - (20 / 2f) + yOffset, iconX, iconY);
            yOffset += 21;
        }
        String welcometext = "§fWelcome §b" + LoginUtil.username + "§b§f! §8[UID-" + LoginUtil.uid + "]";
        text.drawStringWithShadow(welcometext, 5, 5, -1);
        big.drawStringWithShadow("R", (sr.getScaledWidth() / 2f) - (59f / 2f), (sr.getScaledHeight() / 2f) - yOffset, gradient1);
        small.drawStringWithShadow("eturn", (sr.getScaledWidth() / 2f) - (59f / 2f) + 14, (sr.getScaledHeight() / 2f) - yOffset + 67, -1);
        copyRight.drawString("  2024-2025 Return Client. All Rights Reserved", ((float) sr.getScaledWidth() / 2f) - (copyRight.getWidth("  2024-2025 Return Client. All Rights Reserved") / 2), sr.getScaledHeight() - 12, new Color(60, 60, 60));
        icons.drawString("o", ((float) sr.getScaledWidth() / 2f) - (copyRight.getWidth("@@ 2024-2025 Return Client. All Rights Reserved") / 2) + 2, sr.getScaledHeight() - 11, new Color(60, 60, 60));
        float finalYOffset = yOffset;
        ShaderUtil.renderGlow(() -> {
            text.drawStringWithShadow(welcometext, 5, 5, -1);
            big.drawStringWithShadow("R", (sr.getScaledWidth() / 2f) - (59f / 2f), (sr.getScaledHeight() / 2f) - finalYOffset, gradient1);
            small.drawStringWithShadow("eturn", (sr.getScaledWidth() / 2f) - (59f / 2f) + 14, (sr.getScaledHeight() / 2f) - finalYOffset + 67, -1);
        }, 2, 2, 0.86f);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution sr = new ScaledResolution(mc);

        float yOffset = 0;
        for (UIButton button : buttons) {
            if (button.mouseClicked(mouseButton, mouseX, mouseY, ((float) sr.getScaledWidth() / 2f) - (200 / 2f), ((float) sr.getScaledHeight() / 2f) - (20 / 2f) + yOffset)) {
                switch (button.buttonText) {
                    case "Singleplayer" -> mc.displayGuiScreen(new GuiSelectWorld(this));
                    case "Multiplayer" -> mc.displayGuiScreen(new GuiMultiplayer(this));
                    case "Alt Manager" -> mc.displayGuiScreen(new AltMenu(this));
                    case "Options" -> mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                    case "Quit" -> mc.shutdown();
                }
            }
            yOffset += 21;
        }
    }
}
