/*
 * Decompiled with CFR 0_122.
 */
package winter.console.cmds;

import winter.console.cmds.Command;
import winter.notifications.Notification;
import winter.utils.render.overlays.Hud;

public class Watermark
extends Command {
    public Watermark() {
        super("watermark");
        this.desc("Changes watermark.");
        this.use("wm [name]");
    }

    @Override
    public void run(String cmd2) {
        String[] args = cmd2.split(" ");
        if (args[0].equalsIgnoreCase("wm") || args[0].equalsIgnoreCase("watermark")) {
            if (args.length > 1) {
                Hud.watermark = args[1];
                Hud.cur = new Notification("Client", "Watermark changed.", 2);
                this.printChat("Watermark changed.");
            } else {
                this.printChat("Please specify a new name.");
            }
        }
    }
}

