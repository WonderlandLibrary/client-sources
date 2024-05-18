// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.cmd;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;

public class Forward extends Cmd
{
    private int[] offsetX;
    private int[] offsetZ;
    
    public Forward() {
        super("forward", "Teleports you forward.", "forward <Blocks>");
        this.offsetX = new int[] { 0, -1, 0, 1 };
        this.offsetZ = new int[] { 1, 0, -1, 0 };
    }
    
    @Override
    public void runCmd(final String msg, final String[] args) {
        try {
            final int blocks = Integer.parseInt(args[1]);
            if (blocks > 55) {
                this.addMessage("Cannot teleport more than §c55§f blocks forward.");
                return;
            }
            double x = 0.0;
            double z = 0.0;
            double offset = 0.0;
            final int dirFace = MathHelper.floor_double(this.mc.thePlayer.rotationYaw * 4.0 / 360.0 + 0.5) & 0x3;
            int i = 0;
            while (offset < blocks) {
                if ((i & 0x1) == 0x0) {
                    offset += 0.29;
                }
                else {
                    offset += 0.57;
                }
                x = this.offsetX[dirFace] * offset;
                z = this.offsetZ[dirFace] * offset;
                this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX + x, this.mc.thePlayer.posY, this.mc.thePlayer.posZ + z, true));
                ++i;
            }
            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX + x, this.mc.thePlayer.posY, this.mc.thePlayer.posZ + z);
            this.mc.thePlayer.playSound("mob.endermen.portal", 100.0f, 1.0f);
            this.addMessage("Teleported §c" + blocks + "§f blocks forward.");
        }
        catch (Exception e) {
            this.runHelp();
        }
    }
}
