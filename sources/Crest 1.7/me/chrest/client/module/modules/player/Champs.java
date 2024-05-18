// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.player;

import org.lwjgl.opengl.GL11;
import me.chrest.event.EventTarget;
import me.chrest.event.events.EventPostRenderEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import me.chrest.event.EventManager;
import net.minecraft.client.Minecraft;
import me.chrest.client.module.Module;

@Mod(displayName = "Champs")
public class Champs extends Module
{
    Minecraft mc;
    
    public Champs() {
        this.mc = Minecraft.getMinecraft();
    }
    
    public String getHelp() {
        return "Draw players through walls.";
    }
    
    public void onEnable() {
        EventManager.register(this);
    }
    
    public void onDisable() {
        EventManager.unregister(this);
    }
    
    private boolean isValid(final Entity ent) {
        return ent instanceof EntityPlayer && ent != this.mc.thePlayer && !ent.isInvisible();
    }
    
    @EventTarget
    public void beforeRenderPlayer(final EventPostRenderEntity event) {
        if (this.isValid(event.getEntity())) {
            this.offsetPolygon();
        }
    }
    
    @EventTarget
    public void afterRenderPlayer(final EventPostRenderEntity event) {
        if (this.isValid(event.getEntity())) {
            this.resetPolygon();
        }
    }
    
    private void offsetPolygon() {
        GL11.glEnable(32823);
        GL11.glPolygonOffset(1.0f, -1.0E7f);
    }
    
    private void resetPolygon() {
        GL11.glPolygonOffset(1.0f, 1000000.0f);
        GL11.glDisable(32823);
    }
}
