// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.misc;

import net.minecraft.client.renderer.GlStateManager;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.ScoreboardEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Scoreboard", description = "Changes the position of the scoreboard", cat = Category.MISC)
public class Scoreboard extends Module
{
    private final DoubleProperty scoreboardY;
    private final Property<Boolean> remove;
    @EventLink
    private final Listener<ScoreboardEvent> scoreboardEventListener;
    
    public Scoreboard() {
        this.scoreboardY = new DoubleProperty("Scoreboard Y", 0.0, -400.0, 400.0, 1.0);
        this.remove = new Property<Boolean>("Remove", false);
        this.scoreboardEventListener = (e -> {
            if (e.isPre()) {
                if (this.remove.getValue()) {
                    GlStateManager.translate(0.0f, 1000.0f, 1.0f);
                }
                else {
                    GlStateManager.translate(0.0, this.scoreboardY.getValue(), 0.0);
                }
            }
            else if (this.remove.getValue()) {
                GlStateManager.translate(0.0f, -1000.0f, 1.0f);
            }
            else {
                GlStateManager.translate(0.0, -this.scoreboardY.getValue(), 0.0);
            }
        });
    }
}
