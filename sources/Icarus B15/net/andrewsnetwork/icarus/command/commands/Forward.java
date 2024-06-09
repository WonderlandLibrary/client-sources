// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.command.commands;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.command.Command;

public class Forward extends Command
{
    public Forward() {
        super("forward", "none");
    }
    
    @Override
    public void run(final String message) {
        OutputStreamWriter request = new OutputStreamWriter(System.out);
        Label_0033: {
            try {
                request.flush();
            }
            catch (IOException ex) {
                break Label_0033;
            }
            finally {
                request = null;
            }
            request = null;
        }
        final double[] forward = { 0.8, 1.2, 1.8, 2.6, 3.2, 4.1, 4.9, 5.6, 6.2, 7.0 };
        for (int i = 0; i < forward.length; ++i) {
            float dir = Forward.mc.thePlayer.rotationYaw;
            if (Forward.mc.thePlayer.moveForward < 0.0f) {
                dir += 180.0f;
            }
            if (Forward.mc.thePlayer.moveStrafing > 0.0f) {
                dir -= 90.0f * ((Forward.mc.thePlayer.moveForward < 0.0f) ? -0.5f : ((Forward.mc.thePlayer.moveForward > 0.0f) ? 0.5f : 1.0f));
            }
            if (Forward.mc.thePlayer.moveStrafing < 0.0f) {
                dir += 90.0f * ((Forward.mc.thePlayer.moveForward < 0.0f) ? -0.5f : ((Forward.mc.thePlayer.moveForward > 0.0f) ? 0.5f : 1.0f));
            }
            final double hOff = forward[i];
            final float xD = (float)(Math.cos(dir + 1.5707963267948966) * hOff);
            final float zD = (float)(Math.sin(dir + 1.5707963267948966) * hOff);
            Forward.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Forward.mc.thePlayer.posX, Forward.mc.thePlayer.posY - 0.02, Forward.mc.thePlayer.posZ, true));
            Forward.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Forward.mc.thePlayer.posX + xD * 10.0f, Forward.mc.thePlayer.posY, Forward.mc.thePlayer.posZ + zD * 10.0f, true));
        }
    }
}
