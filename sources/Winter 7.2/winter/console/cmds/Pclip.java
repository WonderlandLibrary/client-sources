/*
 * Decompiled with CFR 0_122.
 */
package winter.console.cmds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import winter.console.cmds.Command;

public class Pclip
extends Command {
    public Pclip() {
        super("pclip");
        this.desc("Packet vclip.");
        this.use("pclip [blocks]");
    }

    @Override
    public void run(String cmd2) {
        String[] args = cmd2.split(" ");
        if (args[0].equalsIgnoreCase("pclip") && args.length > 1) {
            int y2 = Integer.valueOf(args[1]);
            Minecraft mc2 = Minecraft.getMinecraft();
            double[] pos = new double[]{mc2.thePlayer.posX, mc2.thePlayer.posY + (double)y2, mc2.thePlayer.posZ};
            int i2 = 0;
            while (i2 < 5) {
                mc2.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pos[0], pos[1], pos[2], false));
                ++i2;
            }
            mc2.thePlayer.setPositionAndUpdate(pos[0], pos[1], pos[2]);
        }
    }
}

