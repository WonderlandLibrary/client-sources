package wtf.diablo.client.gui.clickgui.dropdown.impl.component;

import org.lwjgl.opengl.GL11;
import wtf.diablo.client.font.FontHandler;
import wtf.diablo.client.font.TTFFontRenderer;
import wtf.diablo.client.gui.clickgui.dropdown.api.IGuiComponent;
import wtf.diablo.client.gui.clickgui.dropdown.impl.component.setting.api.ISettingComponent;
import wtf.diablo.client.gui.clickgui.dropdown.impl.component.setting.impl.*;
import wtf.diablo.client.module.api.management.IModule;
import wtf.diablo.client.setting.api.AbstractSetting;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.*;
import wtf.diablo.client.util.render.ColorUtil;
import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class ModulePanelComponent implements IGuiComponent {
    private final List<ISettingComponent> settingComponentList;
    private final CategoryPanelComponent parent;
    private final IModule module;
    private boolean extended;
    private final int height;
    private int panelY;

    ModulePanelComponent(final CategoryPanelComponent parent, final IModule module) {
        this.settingComponentList = new ArrayList<>();
        this.parent = parent;
        this.module = module;
        this.height = 15;

        for (final AbstractSetting<?> setting : module.getSettingList()) {
            if (setting instanceof BooleanSetting)
                this.settingComponentList.add(new BooleanSettingComponent((BooleanSetting) setting, this));
            else if (setting instanceof NumberSetting)
                this.settingComponentList.add(new NumberSettingComponent((NumberSetting<?>) setting, this));
            else if (setting instanceof ModeSetting)
                this.settingComponentList.add(new ModeSettingComponent((ModeSetting<?>) setting, this));
            else if (setting instanceof MultiModeSetting)
                this.settingComponentList.add(new MultiModeSettingComponent((MultiModeSetting<? extends IMode>) setting, this));
            else if (setting instanceof ColorSetting)
                this.settingComponentList.add(new ColorSettingComponent((ColorSetting) setting, this));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        final int modulePanelX = parent.getX();
        final int modulePanelY = parent.getY() + parent.getPanelY();
        final int modulePanelWidth = parent.getWidth();
        final int modulePanelHeight = height;

        RenderUtil.drawRoundedRectangle(modulePanelX, modulePanelY, modulePanelWidth, modulePanelHeight + 2, 0, 0, 2, 2, new Color(0xff282828).getRGB());

        Color color = ColorUtil.CATEGORY_MODULE_TEXT_COLOR_INACTIVE.getValue();

        if (module.isEnabled())
            color = ColorUtil.AMBIENT_COLOR.getValue();
        if (RenderUtil.isHovered(mouseX, mouseY, modulePanelX, modulePanelY, modulePanelX + modulePanelWidth, modulePanelY + modulePanelHeight))
            color = ColorUtil.CATEGORY_MODULE_TEXT_COLOR_HOVER.getValue();

        final TTFFontRenderer font = FontHandler.fetch("outfitregular 19");

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        font.drawStringWithShadow(module.getName(), modulePanelX + 3, modulePanelY + 2, color.getRGB());
        GL11.glDisable(GL11.GL_BLEND);

        if (module.getSettingList().size() > 0)
            RenderUtil.drawArrow(modulePanelX + 100, modulePanelY + (this.extended ? 7 : 6), 2, this.extended ? -4 : 4);

        this.panelY = parent.getPanelY();

        //update the panel y position (allows for multiple modules to be drawn in order along with the extended settings)

        parent.setPanelY(parent.getPanelY() + modulePanelHeight);

        if (extended)
            this.settingComponentList.forEach(settingComponent -> {
                settingComponent.drawScreen(mouseX, mouseY, partialTicks);
                parent.setPanelY(parent.getPanelY() + settingComponent.getHeight());
            });
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (RenderUtil.isHovered(mouseX, mouseY, parent.getX(), parent.getY() + this.panelY, parent.getX() + parent.getWidth(), parent.getY() + this.panelY + height)) {
            if (mouseButton == 0)
                module.toggle();
            if (mouseButton == 1)
                extended = !extended;
        }

        if (extended) {
            this.panelY += height;
            this.settingComponentList.forEach(settingComponent -> {
                settingComponent.mouseClicked(mouseX, mouseY, mouseButton);
                this.panelY += settingComponent.getHeight();
            });
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (extended)
            this.settingComponentList.forEach(settingComponent -> settingComponent.mouseReleased(mouseX, mouseY, state));
    }

    public CategoryPanelComponent getParent() {
        return parent;
    }

    public int getPanelY() {
        return panelY;
    }

    public void setPanelY(final int panelY) {
        this.panelY = panelY;
    }

    public IModule getModule() {
        return module;
    }
}
