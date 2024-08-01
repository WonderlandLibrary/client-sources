package wtf.diablo.client.gui.clickgui.dropdown.impl.component.setting.impl;


import net.minecraft.util.EnumChatFormatting;
import wtf.diablo.client.font.FontHandler;
import wtf.diablo.client.font.TTFFontRenderer;
import wtf.diablo.client.gui.clickgui.dropdown.api.IGuiComponent;
import wtf.diablo.client.gui.clickgui.dropdown.impl.component.ModulePanelComponent;
import wtf.diablo.client.gui.clickgui.dropdown.impl.component.setting.api.ISettingComponent;
import wtf.diablo.client.gui.clickgui.dropdown.impl.component.subcomponent.impl.SliderSubComponent;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.math.MathUtil;
import wtf.diablo.client.util.render.ColorUtil;
import wtf.diablo.client.util.render.RenderUtil;

public final class NumberSettingComponent implements ISettingComponent {
    private final SliderSubComponent sliderSubComponent;
    private final ModulePanelComponent modulePanelComponent;
    private final NumberSetting<?> setting;
    private final int height;

    public NumberSettingComponent(final NumberSetting<?> setting, final ModulePanelComponent modulePanelComponent) {
        this.setting = setting;
        this.modulePanelComponent = modulePanelComponent;
        this.height = 24;
        this.sliderSubComponent = SliderSubComponent.builder().setting(this).x(4).y(6 + IGuiComponent.mcFontRenderer.FONT_HEIGHT).width(modulePanelComponent.getParent().getWidth() - 8).height(4).build();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        final int x = modulePanelComponent.getParent().getX();
        final int y = modulePanelComponent.getParent().getY() + modulePanelComponent.getParent().getPanelY();

        RenderUtil.drawRoundedRectangle(x,y, modulePanelComponent.getParent().getWidth(), height, 0, ColorUtil.SETTING_BACKGROUND_COLOR.getValue().darker().getRGB());
        RenderUtil.drawRoundedRectangle(x + 2, y, modulePanelComponent.getParent().getWidth() - 4, height + 2, 0, ColorUtil.SETTING_BACKGROUND_COLOR.getValue().getRGB());

        final TTFFontRenderer font = FontHandler.fetch("outfitregular 17");

        font.drawString(setting.getName() + ": " + EnumChatFormatting.GRAY + MathUtil.roundToPlace(setting.getValue().doubleValue(), 2), x + 4, y + 2, ColorUtil.SETTING_TEXT_COLOR.getValue().getRGB());

        sliderSubComponent.drawScreen(mouseX, mouseY, partialTicks);

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        sliderSubComponent.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        sliderSubComponent.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public int getHeight() {
        return height;
    }

    public NumberSetting<?> getSetting() {
        return setting;
    }

    public ModulePanelComponent getModulePanelComponent() {
        return modulePanelComponent;
    }
}
