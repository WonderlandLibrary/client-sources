package im.expensive.ui.midnight.component.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.Expensive;
import im.expensive.config.Config;
import im.expensive.ui.midnight.ClickGui;
import im.expensive.utils.font.Fonts;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;


import java.awt.*;

public class ConfigComponent extends Component {

    public Config config;
    public boolean selected;
    public float animation;
    public float animation1;

    public ConfigComponent(Config config) {
        this.config = config;
    }


    @Override
    public void drawComponent(MatrixStack matrixStack, int mouseX, int mouseY) {
        updateAnimation(mouseX, mouseY);
        drawBackground();
        drawFileName(matrixStack);
        drawButtons(matrixStack);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isSaveLoadButtonClicked(mouseX, mouseY)) {
            handleSaveLoadButtonClick();
        } else if (isDeleteButtonClicked(mouseX, mouseY)) {
            handleDeleteButtonClick();
        }
    }

    private void updateAnimation(int mouseX, int mouseY) {
        boolean isInRegion = MathUtil.isInRegion(mouseX, mouseY, x + width - 35 - 2, y + 2, 35 - 2, 32 / 2f);
        animation = MathUtil.lerp(animation, (selected | isInRegion) ? 1 : 0, 10);

        isInRegion = MathUtil.isInRegion(mouseX, mouseY, x + width - 35 - 35 - 2, y + 2, 35 - 2, 32 / 2f);
        animation1 = MathUtil.lerp(animation1, (selected | isInRegion) ? 1 : 0, 10);

    }

    private void drawBackground() {
        DisplayUtils.drawShadow(x + 3, y, width - 6, height, 12, new Color(32, 36, 42).darker().darker().getRGB());
        DisplayUtils.drawRoundedRect(x + 3, y, width - 5, height, 4, new Color(32, 36, 42).getRGB());
    }

    private void drawFileName(MatrixStack matrixStack) {
        Fonts.gilroy[16].drawString(matrixStack, config.getFile().getName(), x + 8, y + 7, -1);
    }

    private void drawButtons(MatrixStack matrixStack) {
        DisplayUtils.drawRoundedRect(x + width - 35 - 35 - 2, y + 2, 35 - 2, 32 / 2f, 4, ColorUtils.interpolateColor(ColorUtils.IntColor.rgba(74, 166, 218, 255), ColorUtils.IntColor.rgba(22, 24, 28, 255), 1 - animation1));
        DisplayUtils.drawRoundedRect(x + width - 35 - 2, y + 2, 35 - 2, 32 / 2f, 4, ColorUtils.interpolateColor(ColorUtils.IntColor.rgba(74, 166, 218, 255), ColorUtils.IntColor.rgba(22, 24, 28, 255), 1 - animation));
        Fonts.gilroy[14].drawCenteredString(matrixStack, selected ? "Save" : "Load", x + width - 35 - 1.5F + 32F / 2F, y + 8, -1);
        Fonts.gilroy[14].drawCenteredString(matrixStack, "Delete", x + width - 35 - 35 - 1.5F + 32F / 2F, y + 8, -1);
    }

    private boolean isSaveLoadButtonClicked(int mouseX, int mouseY) {
        return MathUtil.isInRegion(mouseX, mouseY, x + width - 35 - 2, y + 2, 35 - 2, 32 / 2f);
    }

    private void handleSaveLoadButtonClick() {
        if (!selected) {
            Expensive.getInstance().getConfigStorage().loadConfiguration(config.getFile().getName().replace(".cfg", ""));
            ClickGui.confign = config.getFile().getName();
        } else {
            Expensive.getInstance().getConfigStorage().saveConfiguration(config.getFile().getName().replace(".cfg", ""));
        }
    }

    private boolean isDeleteButtonClicked(int mouseX, int mouseY) {
        return MathUtil.isInRegion(mouseX, mouseY, x + width - 35 - 35 - 2, y + 2, 35 - 2, 32 / 2f);
    }

    private void handleDeleteButtonClick() {
        Expensive.getInstance().getConfigStorage().deleteConfig(config.getFile().getName().replace(".cfg", ""));
        parent.cfg.clear();
        for (String config : Expensive.getInstance().getConfigStorage().getConfigsByName()) {
            parent.cfg.add(new ConfigComponent(Expensive.getInstance().getConfigStorage().findConfig(config)));
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void keyTyped(int keyCode, int scanCode, int modifiers) {

    }

    @Override
    public void charTyped(char codePoint, int modifiers) {

    }
}
