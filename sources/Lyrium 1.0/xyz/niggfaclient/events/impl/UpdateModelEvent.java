// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.events.impl;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;
import xyz.niggfaclient.events.Event;

public class UpdateModelEvent implements Event
{
    public EntityPlayer player;
    public ModelPlayer model;
    
    public UpdateModelEvent(final EntityPlayer player, final ModelPlayer model) {
        this.player = player;
        this.model = model;
    }
    
    public EntityPlayer getPlayer() {
        return this.player;
    }
    
    public ModelPlayer getModel() {
        return this.model;
    }
    
    public void setModel(final ModelPlayer model) {
        this.model = model;
    }
}
