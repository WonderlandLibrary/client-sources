// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.manager;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import me.perry.mcdonalds.features.Feature;

public class PositionManager extends Feature
{
    private double x;
    private double y;
    private double z;
    private boolean onground;
    
    public void updatePosition() {
        this.x = PositionManager.mc.player.posX;
        this.y = PositionManager.mc.player.posY;
        this.z = PositionManager.mc.player.posZ;
        this.onground = PositionManager.mc.player.onGround;
    }
    
    public void restorePosition() {
        PositionManager.mc.player.posX = this.x;
        PositionManager.mc.player.posY = this.y;
        PositionManager.mc.player.posZ = this.z;
        PositionManager.mc.player.onGround = this.onground;
    }
    
    public void setPlayerPosition(final double x, final double y, final double z) {
        PositionManager.mc.player.posX = x;
        PositionManager.mc.player.posY = y;
        PositionManager.mc.player.posZ = z;
    }
    
    public void setPlayerPosition(final double x, final double y, final double z, final boolean onground) {
        PositionManager.mc.player.posX = x;
        PositionManager.mc.player.posY = y;
        PositionManager.mc.player.posZ = z;
        PositionManager.mc.player.onGround = onground;
    }
    
    public void setPositionPacket(final double x, final double y, final double z, final boolean onGround, final boolean setPos, final boolean noLagBack) {
        PositionManager.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, onGround));
        if (setPos) {
            PositionManager.mc.player.setPosition(x, y, z);
            if (noLagBack) {
                this.updatePosition();
            }
        }
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
}
