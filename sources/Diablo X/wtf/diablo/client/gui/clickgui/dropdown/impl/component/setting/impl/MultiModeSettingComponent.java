package wtf.diablo.client.gui.clickgui.dropdown.impl.component.setting.impl;

import wtf.diablo.client.font.FontHandler;
import wtf.diablo.client.font.TTFFontRenderer;
import wtf.diablo.client.gui.clickgui.dropdown.api.IGuiComponent;
import wtf.diablo.client.gui.clickgui.dropdown.impl.component.ModulePanelComponent;
import wtf.diablo.client.gui.clickgui.dropdown.impl.component.setting.api.ISettingComponent;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.MultiModeSetting;
import wtf.diablo.client.util.render.ColorUtil;
import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;

public final class MultiModeSettingComponent implements ISettingComponent {

    //TODO: Make these constant colors in the color module and make MultiModeSettingComponent use them too
    private final static Color BACKDROP_COLOR = new Color(32, 32, 32, 255);
    private final static Color BORDER_COLOR = new Color(0, 0, 0, 255);

    private final ModulePanelComponent modulePanelComponent;
    private final MultiModeSetting<? extends IMode> setting;
    private final int height;
    private boolean collapsed;

    public MultiModeSettingComponent(final MultiModeSetting<? extends IMode> setting, final ModulePanelComponent modulePanelComponent) {
        this.modulePanelComponent = modulePanelComponent;
        this.setting = setting;
        this.height = 21;
        this.collapsed = true;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        final int x = modulePanelComponent.getParent().getX();
        final int y = modulePanelComponent.getParent().getY() + modulePanelComponent.getParent().getPanelY();
        final int actualHeight = this.height - 4;
        final int actualWidth = modulePanelComponent.getParent().getWidth() - 8;

        RenderUtil.drawRoundedRectangle(x, y, modulePanelComponent.getParent().getWidth(), height, 0, ColorUtil.SETTING_BACKGROUND_COLOR.getValue().darker().getRGB());
        RenderUtil.drawRoundedRectangle(x + 2, y, modulePanelComponent.getParent().getWidth() - 4, height + 2, 0, ColorUtil.SETTING_BACKGROUND_COLOR.getValue().getRGB());
        RenderUtil.drawRoundedRectangle(x + 4, y + 2, actualWidth, actualHeight, 4, BACKDROP_COLOR.darker().getRGB());

        final String name = setting.getName();

        final TTFFontRenderer font = FontHandler.fetch("outfitregular 17");

        font.drawString(name, x + actualWidth / 2 - IGuiComponent.mcFontRenderer.getStringWidth(name) / 2, y + 4, ColorUtil.SETTING_TEXT_COLOR.getValue().getRGB());

        if (collapsed) {
            return;
        }

        final IMode[] modes = setting.getEnumValues();

        final int backdropStartY = y + height;
        RenderUtil.drawRoundedRectangle(x, backdropStartY, modulePanelComponent.getParent().getWidth(), (actualHeight * modes.length), 0, ColorUtil.SETTING_BACKGROUND_COLOR.getValue().darker().getRGB());
        RenderUtil.drawRoundedRectangle(x + 4, backdropStartY - 4, actualWidth, (actualHeight * modes.length) + 2, 0, 0, 4, 4, BACKDROP_COLOR.darker().getRGB());


        for (int i = 0; i < modes.length; ++i) {
            final TTFFontRenderer checkFont = FontHandler.fetch("check 14");
            final IMode iMode = modes[i];
            final String modeName = iMode.getName();
            final int startY = y + actualHeight + (actualHeight * i);
            final int textY = startY + 5;

            Color textColor = ColorUtil.SETTING_TEXT_COLOR.getValue();
            if(!setting.getValue().contains(iMode)){
                textColor = ColorUtil.SETTING_TEXT_INACTIVE_COLOR.getValue();
            } else {
                checkFont.drawString("A", x + actualWidth - 10, textY + 2, new Color(255, 255, 255, 255).getRGB());
            }

            font.drawString(modeName, x + 6, textY, textColor.getRGB());
            modulePanelComponent.getParent().setPanelY(modulePanelComponent.getParent().getPanelY() + actualHeight);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        final int x = modulePanelComponent.getParent().getX();
        final int y = modulePanelComponent.getParent().getY() + modulePanelComponent.getPanelY();
        final int actualHeight = this.height - 4;
        final int actualWidth = modulePanelComponent.getParent().getWidth() - 8;

        if (RenderUtil.isHovered(mouseX, mouseY, x + 4, y + 2, x + actualWidth, y + actualHeight)) {
            if (mouseButton == 1) {
                collapsed = !collapsed;
            }
        }

        if(collapsed) {
            return;
        }

        final IMode[] modes = setting.getEnumValues();

        for(int i = 0; i < modes.length; i++){
            final IMode iMode = modes[i];
            final int startY = y + actualHeight + (actualHeight * i);

            if (RenderUtil.isHovered(mouseX, mouseY, x + 4, startY, x + actualWidth, startY + actualHeight)) {
                if(setting.containsValue(iMode))
                    setting.removeValue(iMode);
                else
                    setting.addValue(iMode);
            }

            this.modulePanelComponent.setPanelY(this.modulePanelComponent.getPanelY() + height);
        }

        this.modulePanelComponent.setPanelY(this.modulePanelComponent.getPanelY() - 16);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public int getHeight() {
        return height;
    }
}
