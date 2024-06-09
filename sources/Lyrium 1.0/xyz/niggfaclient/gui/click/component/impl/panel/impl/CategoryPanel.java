// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.gui.click.component.impl.panel.impl;

import xyz.niggfaclient.gui.click.component.impl.ExpandableComponent;
import xyz.niggfaclient.font.Fonts;
import xyz.niggfaclient.utils.render.RenderUtils;
import xyz.niggfaclient.module.impl.render.HUD;
import net.minecraft.client.gui.ScaledResolution;
import java.util.Iterator;
import xyz.niggfaclient.gui.click.component.impl.component.module.ModuleComponent;
import xyz.niggfaclient.Client;
import xyz.niggfaclient.gui.click.component.Component;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.Module;
import java.util.List;
import xyz.niggfaclient.gui.click.component.impl.panel.DraggablePanel;

public final class CategoryPanel extends DraggablePanel
{
    public static final int HEADER_WIDTH = 120;
    public static final int X_ITEM_OFFSET = 1;
    public static final int ITEM_HEIGHT = 15;
    public static final int HEADER_HEIGHT = 17;
    private final List<Module> modules;
    
    public CategoryPanel(final Category category, final int x, final int y) {
        super(null, category.name, x, y, 120, 17);
        int moduleY = 17;
        this.modules = Client.getInstance().getModuleManager().getModulesForCategory(category);
        for (final Module module : this.modules) {
            this.children.add(new ModuleComponent(this, module, 1, moduleY, 118, 15));
            moduleY += 15;
        }
    }
    
    @Override
    public void drawComponent(final ScaledResolution scaledResolution, final int mouseX, final int mouseY) {
        super.drawComponent(scaledResolution, mouseX, mouseY);
        final int x = this.getX();
        final int y = this.getY();
        final int width = this.getWidth();
        final int height = this.getHeight();
        final int heightWithExpand = this.getHeightWithExpand();
        final int headerHeight = this.isExpanded() ? (heightWithExpand + 1) : height;
        RenderUtils.drawCustomRounded((float)(x + 1), (float)y, (float)(x + width - 1), (float)(y + height + (this.isExpanded() ? 2 : 0)), 5.0f, 5.0f, (float)(this.isExpanded() ? 0 : 3), (float)(this.isExpanded() ? 0 : 3), HUD.hudColor.getValue());
        RenderUtils.drawOutlinedRoundedRect2(x + 1, y, x + width - 1, y + headerHeight + (this.isExpanded() ? 2 : 0), 5.0, 3.0f, HUD.hudColor.getValue());
        int categoryX = 0;
        if (this.getName().equalsIgnoreCase("combat")) {
            categoryX = 98;
            Fonts.icon26.drawCenteredString("a", (float)(x + width - 10), y + 8.5f - 3.0f, -1);
        }
        if (this.getName().equalsIgnoreCase("player")) {
            categoryX = 103;
            Fonts.icon26.drawCenteredString("c", (float)(x + width - 10), y + 8.5f - 3.0f, -1);
        }
        if (this.getName().equalsIgnoreCase("movement")) {
            categoryX = 92;
            Fonts.icon26.drawCenteredString("b", (float)(x + width - 10), y + 8.5f - 3.0f, -1);
        }
        if (this.getName().equalsIgnoreCase("render")) {
            categoryX = 101;
            Fonts.icon26.drawCenteredString("g", (float)(x + width - 10), y + 8.5f - 3.0f, -1);
        }
        if (this.getName().equalsIgnoreCase("exploit")) {
            categoryX = 102;
            Fonts.icon26.drawCenteredString("d", (float)(x + width - 10), y + 8.5f - 3.0f, -1);
        }
        if (this.getName().equalsIgnoreCase("misc")) {
            categoryX = 106;
            Fonts.icon26.drawCenteredString("e", (float)(x + width - 10), y + 8.5f - 3.0f, -1);
        }
        if (this.getName().equalsIgnoreCase("ghost")) {
            categoryX = 103;
            Fonts.icon26.drawCenteredString("f", (float)(x + width - 10), y + 8.5f - 3.0f, -1);
        }
        Fonts.sf21.drawCenteredString(this.getName(), (float)(x + width - categoryX), y + 8.5f - 4.0f, -1);
        if (this.isExpanded()) {
            int moduleY = height;
            for (final Component child : this.children) {
                child.setY(moduleY);
                child.drawComponent(scaledResolution, mouseX, mouseY);
                int cHeight = child.getHeight();
                if (child instanceof ExpandableComponent) {
                    final ExpandableComponent expandableComponent = (ExpandableComponent)child;
                    if (expandableComponent.isExpanded()) {
                        cHeight = expandableComponent.getHeightWithExpand();
                    }
                }
                moduleY += cHeight;
            }
        }
    }
    
    @Override
    public boolean canExpand() {
        return !this.modules.isEmpty();
    }
    
    @Override
    public int getHeightWithExpand() {
        int height = this.getHeight();
        if (this.isExpanded()) {
            for (final Component child : this.children) {
                int cHeight = child.getHeight();
                if (child instanceof ExpandableComponent) {
                    final ExpandableComponent expandableComponent = (ExpandableComponent)child;
                    if (expandableComponent.isExpanded()) {
                        cHeight = expandableComponent.getHeightWithExpand();
                    }
                }
                height += cHeight;
            }
        }
        return height;
    }
}
