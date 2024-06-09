// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.command.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.command.Command;

public class Damage extends Command
{
    public Damage() {
        super("damage", "none");
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
        final double x = Damage.mc.thePlayer.posX;
        final double y = Damage.mc.thePlayer.posY;
        final double z = Damage.mc.thePlayer.posZ;
        if (Damage.mc.thePlayer != null) {
            for (int i = 0; i < 64; ++i) {
                Damage.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Damage.mc.thePlayer.posX, Damage.mc.thePlayer.posY - 0.05, Damage.mc.thePlayer.posZ, false));
                Damage.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Damage.mc.thePlayer.posX, Damage.mc.thePlayer.posY + 0.05, Damage.mc.thePlayer.posZ, false));
            }
            Damage.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer(true));
        }
        else if (Damage.mc.thePlayer != null && message.split(" ")[1].equalsIgnoreCase("old")) {
            for (int i = 0; i < 4; ++i) {
                Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 1.01, z, false));
                Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
            }
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.8, z, false));
        }
    }
}
