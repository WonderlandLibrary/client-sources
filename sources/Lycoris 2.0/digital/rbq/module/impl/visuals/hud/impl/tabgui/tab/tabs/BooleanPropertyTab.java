/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.tabs;

import net.minecraft.client.gui.FontRenderer;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.TabHandler;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.block.TabBlock;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.AbstractTab;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.Tab;
import digital.rbq.module.option.impl.BoolOption;

public class BooleanPropertyTab
extends AbstractTab<BoolOption> {
    public BooleanPropertyTab(TabHandler handler, BoolOption stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        super(handler, stateObject, parent, children, container);
    }

    @Override
    public void doInvocation() {
        ((BoolOption)this.stateObject).setValue(((BoolOption)this.stateObject).getValue() == false);
    }

    @Override
    public void renderTabFront() {
        FontRenderer font = this.mc.fontRenderer;
        font.drawStringWithShadow(((BoolOption)this.stateObject).getLabel(), this.getPosX() + 3, (float)this.getPosY() + 2.5f, -855638017);
        font.drawStringWithShadow(": ", this.getPosX() + font.getStringWidth(((BoolOption)this.stateObject).getLabel()) + 4, (float)this.getPosY() + 2.5f, -5723992);
        String booleanValue = String.valueOf(((BoolOption)this.stateObject).getValue());
        booleanValue = booleanValue.substring(0, 1).toUpperCase() + booleanValue.substring(1).toLowerCase();
        font.drawStringWithShadow(booleanValue, this.getPosX() + this.getWidth() - font.getStringWidth(booleanValue) - 3, (float)this.getPosY() + 2.5f, ((BoolOption)this.stateObject).getValue() != false ? -2236963 : -5723992);
    }

    @Override
    public int getTabWidth() {
        FontRenderer font = this.mc.fontRenderer;
        return font.getStringWidth(((BoolOption)this.stateObject).getLabel() + ": ") + Math.max(font.getStringWidth("False"), font.getStringWidth("True")) + 4;
    }
}

