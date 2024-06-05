/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.tabs;

import java.awt.Color;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import digital.rbq.core.Autumn;
import digital.rbq.module.Module;
import digital.rbq.module.impl.visuals.hud.HUDMod;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.TabHandler;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.block.TabBlock;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.AbstractTab;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.Tab;
import digital.rbq.utils.render.Palette;

public class ModuleTab
extends AbstractTab<Module> {
    private float hoverPosition;

    public ModuleTab(TabHandler handler, Module stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        super(handler, stateObject, parent, children, container);
    }

    @Override
    public void doInvocation() {
        ((Module)this.stateObject).toggle();
    }

    @Override
    public void renderTabFront() {
        FontRenderer fr = this.mc.fontRenderer;
        Color c = (Color)Autumn.MANAGER_REGISTRY.moduleManager.getModuleOrNull(HUDMod.class).color.getValue();
        int color = Palette.fade(c).getRGB();
        if (this.handler.isInDirectTree(this)) {
            if (this.hoverPosition < 7.0f) {
                this.hoverPosition = Math.min(7.0f, this.hoverPosition + 0.5f);
            }
        } else if (this.handler.getCurrentTab() != this && this.hoverPosition != 0.0f) {
            this.hoverPosition = Math.max(0.0f, this.hoverPosition - 0.5f);
        }
        if (((Module)this.stateObject).isEnabled()) {
            Gui.drawRect((float)this.getPosX() - 0.5f, this.getPosY(), (float)this.getPosX() + 1.5f, this.getPosY() + this.getHeight(), color);
            Gui.drawRect((float)this.getPosX() + 1.5f, this.getPosY(), (float)(this.getPosX() + this.getWidth()) - 1.5f, this.getPosY() + this.getHeight(), 436536581);
        }
        fr.drawStringWithShadow(((Module)this.stateObject).getLabel(), (float)(this.getPosX() + 3) + this.hoverPosition, (float)this.getPosY() + 2.5f, ((Module)this.stateObject).isEnabled() ? -1 : -5723992);
        if (this.findChildren().get().sizeOf() > 0) {
            fr.drawStringWithShadow("+", this.getPosX() + this.getWidth() - fr.getStringWidth("+") - 3, this.getPosY() + 2, ((Module)this.stateObject).isEnabled() ? -3355444 : -5723992);
        }
    }

    @Override
    public int getTabWidth() {
        return this.mc.fontRenderer.getStringWidth(((Module)this.stateObject).getLabel() + (this.findChildren().get().sizeOf() > 0 ? " +" : "")) + 7 + 4;
    }
}

