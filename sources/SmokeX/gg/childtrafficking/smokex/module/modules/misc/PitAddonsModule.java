// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.misc;

import gg.childtrafficking.smokex.gui.element.VAlignment;
import gg.childtrafficking.smokex.gui.element.HAlignment;
import gg.childtrafficking.smokex.utils.system.StringUtils;
import gg.childtrafficking.smokex.gui.element.elements.TextElement;
import gg.childtrafficking.smokex.utils.player.PitUtils;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.events.render.EventRender2D;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "PitAddons", renderName = "Pit Addons", description = "Useful tools for pit", category = ModuleCategory.MISC)
public final class PitAddonsModule extends Module
{
    private final EventListener<EventRender2D> eventRender2D;
    private final EventListener<EventUpdate> eventUpdate;
    
    public PitAddonsModule() {
        this.eventRender2D = (event -> {});
        this.eventUpdate = (event -> {
            if (event.isPre()) {
                if (PitUtils.isInPit()) {
                    this.renderElements = true;
                    ((TextElement)this.getElement("gold")).setText("Gold: §6" + StringUtils.formatNumber(PitUtils.getGold()) + "g");
                    this.getElement("gold").setY(25.0f);
                }
                else {
                    this.renderElements = false;
                }
            }
        });
    }
    
    @Override
    public void init() {
        this.addElement(new TextElement("gold", 10.0f, 15.0f, "Gold: §6Loading...", -1).setHAlignment(HAlignment.RIGHT).setVAlignment(VAlignment.BOTTOM));
    }
}
