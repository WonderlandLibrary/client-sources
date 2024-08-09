package dev.excellent.client.screen.mainmenu.button;

import dev.excellent.Excellent;
import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.api.interfaces.client.IMouse;
import dev.excellent.api.interfaces.screen.IScreen;
import dev.excellent.client.screen.account.AltManagerScreen;
import dev.excellent.client.screen.mainmenu.MainMenu;
import dev.excellent.impl.client.theme.Themes;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.gui.screen.*;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.joml.Vector2d;

@Getter
@RequiredArgsConstructor
public class MainMenuButton implements IAccess, IMouse, IScreen {
    private final String name;
    private final Vector2d position;
    private final Vector2d size;
    private final MainMenu parent;
    private final int id;
    private final Animation alphaAnimation = new Animation(Easing.LINEAR, 500);

    @Override
    public void init() {
        alphaAnimation.setValue(1);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Font font = Fonts.INTER_BOLD.get(14);

        boolean isHovered = isHover(mouseX, mouseY, position.x, position.y, size.x, size.y);

        alphaAnimation.setDuration(parent.isExit() ? 100 : 300);
        alphaAnimation.run(parent.isExit() ? 1 : (isHovered ? 255 : 100));

        if (alphaAnimation.getValue() <= 10) return;

        float btnDarkness = 0.15F;
        Themes theme = Excellent.getInst().getThemeManager().getTheme();
        int color1 = ColorUtil.multDark(theme.getClientColor(0, (float) (alphaAnimation.getValue() / 255F)), btnDarkness);
        int color2 = ColorUtil.multDark(theme.getClientColor(90, (float) (alphaAnimation.getValue() / 255F)), btnDarkness);
        int color3 = ColorUtil.multDark(theme.getClientColor(180, (float) (alphaAnimation.getValue() / 255F)), btnDarkness);
        int color4 = ColorUtil.multDark(theme.getClientColor(270, (float) (alphaAnimation.getValue() / 255F)), btnDarkness);

        RectUtil.drawRoundedRectShadowed(matrixStack, (float) position.x, (float) position.y, (float) (position.x + size.x), (float) (position.y + size.y), 3, 5, color1, color2, color3, color4, true, true, true, true);
        RectUtil.drawRoundedRectShadowed(matrixStack, (float) position.x, (float) position.y, (float) (position.x + size.x), (float) (position.y + size.y), 3, 5, color1, color2, color3, color4, true, true, false, true);

        font.drawCenter(matrixStack, getName(), position.x + (size.x / 2F), position.y + (size.y / 2F) - (font.getHeight() / 2F), ColorUtil.getColor(255, 255, 255, (int) alphaAnimation.getValue()));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        if (isHover(mouseX, mouseY, position.x, position.y, size.x, size.y) && isLClick(button)) {
//            onClick.run();
            parent.setExit(true);
            Screen singleplayer = new WorldSelectionScreen(parent);
            Screen multiplayer = this.mc.gameSettings.skipMultiplayerWarning ? new MultiplayerScreen(parent) : new MultiplayerWarningScreen(parent);
            Screen altmanager = new AltManagerScreen();
            Screen options = new OptionsScreen(parent, this.mc.gameSettings);
            if (id == 0) {
                parent.setToExecute(() ->
                        mc.displayScreen(singleplayer)
                );
            } else if (id == 1) {
                parent.setToExecute(() ->
                        mc.displayScreen(multiplayer)
                );
            } else if (id == 2) {
                parent.setToExecute(() ->
                        mc.displayScreen(altmanager)
                );
            } else if (id == 3) {
                parent.setToExecute(() ->
                        mc.displayScreen(options)
                );
            } else if (id == 4) {
                parent.setToExecute(mc::shutdown);
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return false;
    }

    @Override
    public void onClose() {

    }
}
