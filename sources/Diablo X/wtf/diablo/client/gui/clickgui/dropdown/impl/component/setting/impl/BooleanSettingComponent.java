package wtf.diablo.client.gui.clickgui.dropdown.impl.component.setting.impl;

import wtf.diablo.client.font.FontHandler;
import wtf.diablo.client.font.TTFFontRenderer;
import wtf.diablo.client.gui.clickgui.dropdown.impl.component.ModulePanelComponent;
import wtf.diablo.client.gui.clickgui.dropdown.impl.component.setting.api.ISettingComponent;
import wtf.diablo.client.gui.clickgui.dropdown.impl.component.subcomponent.impl.CheckBoxSubComponent;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.util.render.ColorUtil;
import wtf.diablo.client.util.render.RenderUtil;

public final class BooleanSettingComponent implements ISettingComponent {
    private final CheckBoxSubComponent checkBoxSubComponent;
    private final ModulePanelComponent modulePanelComponent;
    private final BooleanSetting setting;
    private final int height;

    public BooleanSettingComponent(final BooleanSetting setting, final ModulePanelComponent modulePanelComponent) {
        this.setting = setting;
        this.modulePanelComponent = modulePanelComponent;
        this.height = 14;
        this.checkBoxSubComponent = CheckBoxSubComponent.builder().setting(this).x(5).y(2).width(10).height(10).toggleGap(2).build();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        final int x = modulePanelComponent.getParent().getX();
        final int y = modulePanelComponent.getParent().getY() + modulePanelComponent.getParent().getPanelY();

        RenderUtil.drawRoundedRectangle(x, y, modulePanelComponent.getParent().getWidth(), height + 5, 0, 0, 0, 0, ColorUtil.SETTING_BACKGROUND_COLOR.getValue().darker().getRGB());
        RenderUtil.drawRoundedRectangle(x + 2, y, modulePanelComponent.getParent().getWidth() - 4, height + 2, 0, ColorUtil.SETTING_BACKGROUND_COLOR.getValue().getRGB());

        final TTFFontRenderer font = FontHandler.fetch("outfitregular 17");

        font.drawString(setting.getName(), x + 2, y + 1, ColorUtil.SETTING_TEXT_COLOR.getValue().getRGB());
        checkBoxSubComponent.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        checkBoxSubComponent.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        checkBoxSubComponent.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public int getHeight() {
        return height;
    }

    public BooleanSetting getSetting() {
        return setting;
    }

    public ModulePanelComponent getModulePanelComponent() {
        return modulePanelComponent;
    }

}
