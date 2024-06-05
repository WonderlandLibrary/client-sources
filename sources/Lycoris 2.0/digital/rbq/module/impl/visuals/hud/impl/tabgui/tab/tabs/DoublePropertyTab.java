/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.tabs;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.TabHandler;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.block.TabBlock;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.AbstractTab;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.Tab;
import digital.rbq.module.option.impl.DoubleOption;

public class DoublePropertyTab
extends AbstractTab<DoubleOption> {
    private boolean selected;

    public DoublePropertyTab(TabHandler handler, DoubleOption stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        super(handler, stateObject, parent, children, container);
    }

    @Override
    public void doInvocation() {
        this.selected = true;
        this.handler.disableDefaultKeyListening();
        this.handler.subscribeActionToKey(this, 203, (key, tab) -> ((DoubleOption)this.stateObject).setValue(MathHelper.clamp_double((Double)((DoubleOption)this.stateObject).getValue() - ((DoubleOption)this.stateObject).getIncrement(), ((DoubleOption)this.stateObject).getMinValue(), ((DoubleOption)this.stateObject).getMaxValue())));
        this.handler.subscribeActionToKey(this, 205, (key, tab) -> ((DoubleOption)this.stateObject).setValue(MathHelper.clamp_double((Double)((DoubleOption)this.stateObject).getValue() + ((DoubleOption)this.stateObject).getIncrement(), ((DoubleOption)this.stateObject).getMinValue(), ((DoubleOption)this.stateObject).getMaxValue())));
        this.handler.subscribeActionToKey(this, 28, (key, tab) -> {
            this.selected = false;
            this.handler.unsubscribeSpecificActions(this);
            this.handler.enableDefaultKeyListening();
        });
    }

    @Override
    public void renderTabFront() {
        int lineRenderColor;
        FontRenderer fr = this.mc.fontRendererSmall;
        int x = this.getPosX();
        int y = this.getPosY();
        int width = this.getWidth();
        int height = this.getHeight();
        if (this.selected) {
            Gui.drawRect((float)x + 1.0f, y, (float)(x + width) - 1.0f, y + height, -16448251);
            lineRenderColor = -1;
        } else {
            lineRenderColor = -5723992;
        }
        DoubleOption stateObject = (DoubleOption)this.stateObject;
        double renderIncrement = (double)(width - 6) / (stateObject.getMaxValue() - stateObject.getMinValue());
        Gui.drawRect(x + 3, y + 9, x + width - 3, y + 10, -1436524448);
        Gui.drawRect(x + 3, y + 9, (double)(x + 3) + renderIncrement * (double)((float)((Double)stateObject.getValue()).doubleValue()) - renderIncrement * ((DoubleOption)this.stateObject).getMinValue(), y + 10, lineRenderColor);
        String valueString = String.valueOf((float)Math.round((Double)stateObject.getValue() * 10.0) / 10.0f);
        fr.drawStringWithShadow(stateObject.getLabel(), x + 3, y + 4, this.selected ? -1 : -2236963);
        fr.drawStringWithShadow(valueString, x + width - fr.getStringWidth(valueString) - 4, y + 4, this.selected ? -1 : -2236963);
    }

    @Override
    public int getTabWidth() {
        return this.mc.fontRendererSmall.getStringWidth(((DoubleOption)this.stateObject).getLabel() + " " + String.format("%.1f", ((DoubleOption)this.stateObject).getMaxValue())) + 7;
    }
}

