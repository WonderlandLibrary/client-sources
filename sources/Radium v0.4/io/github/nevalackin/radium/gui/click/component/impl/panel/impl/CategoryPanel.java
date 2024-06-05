package io.github.nevalackin.radium.gui.click.component.impl.panel.impl;

import io.github.nevalackin.radium.RadiumClient;
import io.github.nevalackin.radium.gui.click.ClickGui;
import io.github.nevalackin.radium.gui.click.component.Component;
import io.github.nevalackin.radium.gui.click.component.impl.ExpandableComponent;
import io.github.nevalackin.radium.gui.click.component.impl.component.module.ModuleComponent;
import io.github.nevalackin.radium.gui.click.component.impl.panel.DraggablePanel;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.utils.StringUtils;
import io.github.nevalackin.radium.utils.Wrapper;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.util.Collections;
import java.util.List;

public final class CategoryPanel extends DraggablePanel {

    public static final int HEADER_WIDTH = 120;
    public static final int X_ITEM_OFFSET = 1;
    public static final int ITEM_HEIGHT = 15;
    public static final int HEADER_HEIGHT = 17;

    private final List<Module> modules;

    public CategoryPanel(ModuleCategory category,
                         int x,
                         int y) {
        super(null, StringUtils.upperSnakeCaseToPascal(category.name()), x, y, HEADER_WIDTH, HEADER_HEIGHT);
        int moduleY = HEADER_HEIGHT;

        this.modules = Collections.unmodifiableList(RadiumClient.getInstance().getModuleManager().getModulesForCategory(category));

        for (Module module : modules) {
            this.children.add(new ModuleComponent(this, module, X_ITEM_OFFSET, moduleY, HEADER_WIDTH - (X_ITEM_OFFSET * 2), ITEM_HEIGHT));
            moduleY += ITEM_HEIGHT;
        }
    }

    @Override
    public void drawComponent(ScaledResolution scaledResolution,
                              int mouseX,
                              int mouseY) {
        super.drawComponent(scaledResolution, mouseX, mouseY);

        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();
        int panelHeaderCol = ClickGui.getInstance().getPalette().getPanelHeaderColor().getRGB();
        int headerHeight = height;
        int heightWithExpand = getHeightWithExpand();
        switch (ClickGui.getInstance().getPalette()) {
            case DEFAULT:
                headerHeight = (isExpanded() ? heightWithExpand + 1 : height);
                break;
        }
        Gui.drawRect(x, y, x + width, y + headerHeight, panelHeaderCol);
        Wrapper.getFontRenderer().drawStringWithShadow(getName(),
                x + width / 2.0F - Wrapper.getFontRenderer().getWidth(getName()) / 2.0F - 1,
                y + HEADER_HEIGHT / 2.0f - 4,
                ClickGui.getInstance().getPalette().getPanelHeaderTextColor().getRGB());

        if (isExpanded()) {
            int moduleY = height;
            for (Component child : children) {
                child.setY(moduleY);
                child.drawComponent(scaledResolution, mouseX, mouseY);
                int cHeight = child.getHeight();
                if (child instanceof ExpandableComponent) {
                    ExpandableComponent expandableComponent = (ExpandableComponent) child;
                    if (expandableComponent.isExpanded())
                        cHeight = expandableComponent.getHeightWithExpand();
                }
                moduleY += cHeight;
            }
        }
    }

    @Override
    public boolean canExpand() {
        return !modules.isEmpty();
    }

    @Override
    public int getHeightWithExpand() {
        int height = getHeight();

        if (isExpanded()) {
            for (Component child : children) {
                int cHeight = child.getHeight();
                if (child instanceof ExpandableComponent) {
                    ExpandableComponent expandableComponent = (ExpandableComponent) child;
                    if (expandableComponent.isExpanded())
                        cHeight = expandableComponent.getHeightWithExpand();
                }
                height += cHeight;
            }
        }

        return height;
    }
}
