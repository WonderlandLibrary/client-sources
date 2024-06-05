
package me.rich.helpers.command.impl;

import me.rich.Main;
import me.rich.helpers.command.Command;

/**
 * Created by 1 on 11.04.2021.
 */
public class ModuleToggle extends Command {

    public ModuleToggle() {
        super("ModuleToggle", new String[]{"t", "toggle"});
    }

    @Override
    public void onCommand(String[] args) {

        if(args.length == 2 && args[0].equalsIgnoreCase("t") || args.length == 2 && args[0].equalsIgnoreCase("toggle")) {
            try{
                Main.moduleManager.getModuleByName(args[1]).toggle();
            } catch(Exception e){
                Main.msg("Wrong usage!!!", true);
            }
        } else {
            Main.msg("Wrong usage!!!", true);
        }
    }
}