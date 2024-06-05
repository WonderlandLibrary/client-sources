package koks.command.impl;

import koks.Koks;
import koks.command.Command;
import koks.modules.Module;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:12
 */
public class Toggle extends Command {

    public Toggle() {
        super("toggle", "t");
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 1) {
            Module module = Koks.getKoks().moduleManager.getModule(args[0]);
            if(module != null) {
                module.toggle();
                Koks.getKoks().shutdownClient();
                String color = module.isToggled() ? "§a" : "§c";
                sendmsg(color + "Toggled " + module.getModuleName(), true);
            }else{
                sendError(args[0], "doesn't exist!", true);
            }
        }
    }
}
