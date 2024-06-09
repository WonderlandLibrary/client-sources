/*
 * Decompiled with CFR 0_122.
 */
package winter.console.cmds;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;
import winter.Client;
import winter.console.cmds.Command;
import winter.module.Module;
import winter.notifications.Notification;
import winter.utils.render.overlays.Hud;

public class Bind
extends Command {
    public Bind() {
        super("bind");
        this.desc("Binds module to specified key.");
        this.use("bind [module] [key]");
    }

    @Override
    public void run(String cmd2) {
        String[] args = cmd2.split(" ");
        if (args[0].equalsIgnoreCase("bind")) {
            if (args.length > 1) {
                if (Client.getManager().getMod(args[1]) != null) {
                    Module mod = Client.getManager().getMod(args[1]);
                    if (args.length > 2) {
                        String bind = StringUtils.capitalize(args[2]);
                        mod.setBind(Keyboard.getKeyIndex(bind));
                        this.printChat(String.valueOf(mod.getName()) + " has been bound to: " + Keyboard.getKeyName(Keyboard.getKeyIndex(bind)));
                        Hud.cur = new Notification("Modules", String.valueOf(mod.getName()) + " bound to: " + Keyboard.getKeyName(Keyboard.getKeyIndex(bind)), 2);
                    } else {
                        this.printChat(String.valueOf(mod.getName()) + " is currently bound to: " + Keyboard.getKeyName(mod.getBind()));
                    }
                } else {
                    this.printChat("Unknown module.");
                }
            } else {
                this.printChat("No module specified.");
            }
        }
    }
}

