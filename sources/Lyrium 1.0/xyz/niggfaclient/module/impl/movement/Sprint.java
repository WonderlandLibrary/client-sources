// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.movement;

import xyz.niggfaclient.utils.player.MoveUtils;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.UpdateEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Sprint", description = "Sprints for you", cat = Category.MOVEMENT)
public class Sprint extends Module
{
    private final Property<Boolean> omnisprint;
    @EventLink
    private final Listener<UpdateEvent> updateEventListener;
    
    public Sprint() {
        this.omnisprint = new Property<Boolean>("Omni-Sprint", false);
        this.updateEventListener = (e -> {
            this.mc.gameSettings.keyBindSprint.setPressed(true);
            this.mc.thePlayer.allowOmniSprint = (this.omnisprint.getValue() && !this.mc.thePlayer.isSneaking() && MoveUtils.isMoving() && !this.mc.thePlayer.isCollidedHorizontally);
        });
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.thePlayer.setSprinting(this.mc.gameSettings.keyBindSprint.isKeyDown());
        this.mc.thePlayer.allowOmniSprint = false;
    }
}
