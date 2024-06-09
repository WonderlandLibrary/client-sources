// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.gui.click;

import xyz.niggfaclient.gui.click.component.impl.ExpandableComponent;
import java.io.IOException;
import java.util.Iterator;
import xyz.niggfaclient.utils.Utils;
import xyz.niggfaclient.gui.click.component.impl.panel.impl.CategoryPanel;
import xyz.niggfaclient.module.Category;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.KeyEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.gui.click.component.Component;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public final class ClickGui extends GuiScreen
{
    public static boolean escapeKeyInUse;
    private static ClickGui instance;
    private final List<Component> components;
    private Component selectedPanel;
    @EventLink
    public final Listener<KeyEvent> keyEventListener;
    
    public ClickGui() {
        this.components = new ArrayList<Component>();
        this.keyEventListener = (e -> {
            if (e.getKey() == 54) {
                Minecraft.getMinecraft().displayGuiScreen(this);
            }
            return;
        });
        ClickGui.instance = this;
        int panelX = 2;
        for (final Category category : Category.values()) {
            final CategoryPanel panel = new CategoryPanel(category, panelX, 2);
            this.components.add(panel);
            panelX += panel.getWidth() + 2;
            this.selectedPanel = panel;
        }
    }
    
    public static ClickGui getInstance() {
        return ClickGui.instance;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        for (final Component component : this.components) {
            component.drawComponent(Utils.sr, mouseX, mouseY);
        }
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        this.selectedPanel.onKeyPress(keyCode);
        if (!ClickGui.escapeKeyInUse) {
            super.keyTyped(typedChar, keyCode);
        }
        ClickGui.escapeKeyInUse = false;
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        for (int i = this.components.size() - 1; i >= 0; --i) {
            final Component component = this.components.get(i);
            final int x = component.getX();
            final int y = component.getY();
            int cHeight = component.getHeight();
            if (component instanceof ExpandableComponent) {
                final ExpandableComponent expandableComponent = (ExpandableComponent)component;
                if (expandableComponent.isExpanded()) {
                    cHeight = expandableComponent.getHeightWithExpand();
                }
            }
            if (mouseX > x && mouseY > y && mouseX < x + component.getWidth() && mouseY < y + cHeight) {
                (this.selectedPanel = component).onMouseClick(mouseX, mouseY, mouseButton);
                break;
            }
        }
    }
    
    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        this.selectedPanel.onMouseRelease(state);
    }
}
