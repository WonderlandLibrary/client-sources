/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.tabs;

import java.awt.Color;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.TabHandler;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.block.TabBlock;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.AbstractTab;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.Tab;
import digital.rbq.module.option.impl.ColorOption;
import digital.rbq.utils.render.RenderUtils;

public class ColorPropertyTab
extends AbstractTab<ColorOption> {
    private boolean selected;

    public ColorPropertyTab(TabHandler handler, ColorOption stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        super(handler, stateObject, parent, children, container);
    }

    @Override
    public void doInvocation() {
        this.selected = true;
        this.handler.disableDefaultKeyListening();
        this.handler.subscribeActionToKey(this, 203, (key, tab) -> {
            float hue = MathHelper.clamp_float(this.getHue() - 5.0f, 0.0f, 360.0f) / 360.0f;
            ((ColorOption)this.stateObject).setValue(Color.getHSBColor(hue, 1.0f, 1.0f));
        });
        this.handler.subscribeActionToKey(this, 205, (key, tab) -> {
            float hue = MathHelper.clamp_float(this.getHue() + 5.0f, 0.0f, 360.0f) / 360.0f;
            ((ColorOption)this.stateObject).setValue(Color.getHSBColor(hue, 1.0f, 1.0f));
        });
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
        double renderIncrement = (double)(width - 6) / 360.0;
        RenderUtils.drawHsvScale(x + 3, y + 9, x + width - 3, y + 10);
        Gui.drawRect((double)(x + 3) + renderIncrement * (double)this.getHue() - 0.5, y + 8, (double)(x + 3) + renderIncrement * (double)this.getHue() + 0.5, y + 11, lineRenderColor);
        fr.drawStringWithShadow(((ColorOption)this.stateObject).getLabel(), x + 3, y + 4, this.selected ? -1 : -2236963);
    }

    private float getHue() {
        int r = ((Color)((ColorOption)this.stateObject).getValue()).getRed();
        int b = ((Color)((ColorOption)this.stateObject).getValue()).getBlue();
        int g = ((Color)((ColorOption)this.stateObject).getValue()).getGreen();
        return Color.RGBtoHSB(r, g, b, null)[0] * 360.0f;
    }

    @Override
    public int getTabWidth() {
        return 1;
    }
}

