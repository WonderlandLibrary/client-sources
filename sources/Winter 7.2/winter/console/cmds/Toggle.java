/*
 * Decompiled with CFR 0_122.
 */
package winter.console.cmds;

import winter.Client;
import winter.console.cmds.Command;
import winter.module.Module;

public class Toggle
extends Command {
    public Toggle() {
        super("toggle");
        this.desc("Toggled specified module.");
        this.use("t [module]");
    }

    @Override
    public void run(String cmd2) {
        String[] args = cmd2.split(" ");
        if (args[0].equalsIgnoreCase("t") || args[0].equalsIgnoreCase("toggle")) {
            if (args.length > 1) {
                if (Client.getManager().getMod(args[1]) != null) {
                    Module mod = Client.getManager().getMod(args[1]);
                    mod.toggle();
                    this.printChat(String.valueOf(mod.getName()) + " has been toggled.");
                } else {
                    this.printChat("Unknown module.");
                }
            } else {
                this.printChat("No module specified.");
            }
        }
    }
}

