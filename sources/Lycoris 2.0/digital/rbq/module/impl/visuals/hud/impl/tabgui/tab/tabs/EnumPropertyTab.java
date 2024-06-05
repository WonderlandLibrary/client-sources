/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.tabs;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.TabHandler;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.block.TabBlock;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.AbstractTab;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.Tab;
import digital.rbq.module.option.impl.EnumOption;

public class EnumPropertyTab
extends AbstractTab<EnumOption<Enum>> {
    private boolean selected;

    public EnumPropertyTab(TabHandler handler, EnumOption<Enum> stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        super(handler, stateObject, parent, children, container);
    }

    @Override
    public void doInvocation() {
        Enum[] values = ((EnumOption)this.stateObject).getValues();
        this.selected = true;
        this.handler.disableDefaultKeyListening();
        this.handler.subscribeActionToKey(this, 203, (key, tab) -> {
            for (int i = 0; i < values.length; ++i) {
                if (values[i] != ((EnumOption)this.stateObject).getValue()) continue;
                ((EnumOption)this.stateObject).setValue(i == 0 ? values[values.length - 1] : values[i - 1]);
                break;
            }
        });
        this.handler.subscribeActionToKey(this, 205, (key, tab) -> {
            for (int i = 0; i < values.length; ++i) {
                if (values[i] != ((EnumOption)this.stateObject).getValue()) continue;
                ((EnumOption)this.stateObject).setValue(i == values.length - 1 ? values[0] : values[i + 1]);
                break;
            }
        });
        this.handler.subscribeActionToKey(this, 28, (key, tab) -> {
            this.selected = false;
            this.handler.unsubscribeSpecificActions(this);
            this.handler.enableDefaultKeyListening();
        });
    }

    @Override
    public void renderTabFront() {
        int valueColor;
        FontRenderer fr = this.mc.fontRenderer;
        if (this.selected) {
            Gui.drawRect((float)this.getPosX() + 1.0f, this.getPosY(), (float)(this.getPosX() + this.getWidth()) - 1.0f, this.getPosY() + this.getHeight(), -16448251);
            valueColor = -1;
        } else {
            valueColor = -5723992;
        }
        fr.drawStringWithShadow(((EnumOption)this.stateObject).getLabel() + ": ", (float)this.getPosX() + 2.5f, (float)this.getPosY() + 2.5f, -3355444);
        String enumValue = ((Enum)((EnumOption)this.stateObject).getValue()).toString();
        fr.drawStringWithShadow(enumValue, this.getPosX() + this.getWidth() - fr.getStringWidth(enumValue) - 3, (float)this.getPosY() + 2.5f, valueColor);
    }

    @Override
    public int getTabWidth() {
        FontRenderer fr = this.mc.fontRenderer;
        return fr.getStringWidth(((EnumOption)this.stateObject).getLabel() + ": ") + this.getWidestEnumName(fr, ((EnumOption)this.stateObject).getValues()) + 4;
    }

    private int getWidestEnumName(FontRenderer fr, Enum<?>[] enums) {
        int widestName = 0;
        for (Enum<?> e : enums) {
            int width = fr.getStringWidth(e.name());
            if (width <= widestName) continue;
            widestName = width;
        }
        return widestName;
    }
}

