/*
 * Decompiled with CFR 0_122.
 */
package winter.console.cmds;

import winter.console.cmds.Command;
import winter.utils.render.overlays.Hud;

public class Rainbow
extends Command {
    public Rainbow() {
        super("rainbow");
        this.desc("Change arraylist speed.");
        this.use("rainbow [speed]");
    }

    @Override
    public void run(String cmd2) {
        String[] args = cmd2.split(" ");
        if (args[0].equalsIgnoreCase("rainbow") && args.length > 1) {
            double speed;
            Hud.rn = speed = Double.valueOf(args[1]).doubleValue();
        }
    }
}

