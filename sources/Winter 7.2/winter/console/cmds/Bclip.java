/*
 * Decompiled with CFR 0_122.
 */
package winter.console.cmds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.AxisAlignedBB;
import winter.console.cmds.Command;

public class Bclip
extends Command {
    public Bclip() {
        super("bclip");
        this.desc("Bounding box vclip.");
        this.use("bclip [blocks]");
    }

    @Override
    public void run(String cmd2) {
        String[] args = cmd2.split(" ");
        if (args[0].equalsIgnoreCase("bclip") && args.length > 1) {
            int y2 = Integer.valueOf(args[1]);
            Minecraft mc2 = Minecraft.getMinecraft();
            mc2.thePlayer.boundingBox.offsetAndUpdate(0.0, y2, 0.0);
        }
    }
}

