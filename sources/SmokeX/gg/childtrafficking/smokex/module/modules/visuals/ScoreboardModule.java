// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.visuals;

import gg.childtrafficking.smokex.gui.element.HAlignment;
import gg.childtrafficking.smokex.gui.element.Element;
import gg.childtrafficking.smokex.gui.element.elements.RectElement;
import gg.childtrafficking.smokex.module.ModuleManager;
import gg.childtrafficking.smokex.event.events.render.EventRender2D;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.property.properties.BooleanProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "Scoreboard", renderName = "Scoreboard", description = "Better Scoreboard", category = ModuleCategory.VISUALS)
public class ScoreboardModule extends Module
{
    private BooleanProperty customFontProperty;
    public EventListener<EventRender2D> eventRender2DEventListener;
    
    public ScoreboardModule() {
        this.customFontProperty = new BooleanProperty("Custom Font", true);
        this.eventRender2DEventListener = (event -> {
            final HUDModule hud = ModuleManager.getInstance(HUDModule.class);
            final RectElement background = (RectElement)this.getElement("background");
        });
    }
    
    @Override
    public void init() {
        this.addElement(new RectElement("background", 5.0f, 200.0f, 100.0f, 200.0f, 1426063360)).setHAlignment(HAlignment.RIGHT);
        this.toggle();
        super.init();
    }
}
