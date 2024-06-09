/*
 * Decompiled with CFR 0_122.
 */
package winter.console.cmds;

import winter.Client;
import winter.console.cmds.Command;
import winter.module.Module;
import winter.notifications.Notification;
import winter.utils.render.overlays.Hud;

public class Visible
extends Command {
    public Visible() {
        super("visible");
        this.desc("Toggled visibility on specified module.");
        this.use("vis [module]");
    }

    @Override
    public void run(String cmd2) {
        String[] args = cmd2.split(" ");
        if (args[0].equalsIgnoreCase("vis")) {
            if (args.length > 1) {
                if (Client.getManager().getMod(args[1]) != null) {
                	Module mod = Client.getManager().getMod(args[1]);
                    mod.visible(!(mod = Client.getManager().getMod(args[1])).visible());
                    this.printChat(String.valueOf(mod.getName()) + " visibility has been toggled.");
                    Hud.cur = new Notification("Modules", "Visiblity toggled.", 2);
                } else {
                    this.printChat("Unknown module.");
                }
            } else {
                this.printChat("No module specified.");
            }
        }
    }
}

