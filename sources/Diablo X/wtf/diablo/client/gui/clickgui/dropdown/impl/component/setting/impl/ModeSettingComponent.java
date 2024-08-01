package wtf.diablo.client.gui.clickgui.dropdown.impl.component.setting.impl;

import net.minecraft.util.EnumChatFormatting;
import wtf.diablo.client.font.FontHandler;
import wtf.diablo.client.font.TTFFontRenderer;
import wtf.diablo.client.gui.clickgui.dropdown.impl.component.ModulePanelComponent;
import wtf.diablo.client.gui.clickgui.dropdown.impl.component.setting.api.ISettingComponent;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.util.render.ColorUtil;
import wtf.diablo.client.util.render.RenderUtil;

public final class ModeSettingComponent implements ISettingComponent {
    private final ModulePanelComponent modulePanelComponent;
    private final ModeSetting<?> setting;
    private final int height;

    public ModeSettingComponent(final ModeSetting<?> setting, final ModulePanelComponent modulePanelComponent) {
        this.modulePanelComponent = modulePanelComponent;
        this.setting = setting;
        this.height = 15;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        final int x = modulePanelComponent.getParent().getX();
        final int y = modulePanelComponent.getParent().getY() + modulePanelComponent.getParent().getPanelY();
        final int actualHeight = this.height - 4;
        final int actualWidth = modulePanelComponent.getParent().getWidth() - 8;

        RenderUtil.drawRoundedRectangle(x, y, modulePanelComponent.getParent().getWidth(), height, 0, ColorUtil.SETTING_BACKGROUND_COLOR.getValue().darker().getRGB());
        RenderUtil.drawRoundedRectangle(x + 2, y, modulePanelComponent.getParent().getWidth() - 4, height + 2, 0, ColorUtil.SETTING_BACKGROUND_COLOR.getValue().getRGB());

        final String name = setting.getValue().getName();

        final TTFFontRenderer font = FontHandler.fetch("outfitregular 17");

        font.drawString(setting.getName() + ": " + EnumChatFormatting.GRAY + name, x + 2, y + 1, ColorUtil.SETTING_TEXT_COLOR.getValue().getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        final int x = modulePanelComponent.getParent().getX();
        final int y = modulePanelComponent.getParent().getY() + modulePanelComponent.getPanelY();
        final int actualHeight = this.height - 4;
        final int actualWidth = modulePanelComponent.getParent().getWidth() - 8;

        if (RenderUtil.isHovered(mouseX, mouseY, x + 4, y + 2, x + actualWidth, y + actualHeight)) {
            if (mouseButton == 0) {
                setting.cycle(false);
            } else if (mouseButton == 1) {
                setting.cycle(true);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public int getHeight() {
        return height;
    }
}
