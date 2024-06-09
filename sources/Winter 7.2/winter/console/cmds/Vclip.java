/*
 * Decompiled with CFR 0_122.
 */
package winter.console.cmds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import winter.console.cmds.Command;

public class Vclip
extends Command {
    public Vclip() {
        super("Vclip");
        this.desc("Teleports on Y axis.");
        this.use("vclip [blocks]");
    }

    @Override
    public void run(String cmd2) {
        String[] args = cmd2.split(" ");
        if (args[0].equalsIgnoreCase("vclip") && args.length > 1) {
            int y2 = Integer.valueOf(args[1]);
            Minecraft mc2 = Minecraft.getMinecraft();
            mc2.thePlayer.setPosition(mc2.thePlayer.posX, mc2.thePlayer.posY + (double)y2, mc2.thePlayer.posZ);
        }
    }
}

