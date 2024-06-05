package me.rich.helpers.command.impl;

import me.rich.Main;
import me.rich.helpers.command.Command;

public class FeatureToggle extends Command {

    public FeatureToggle() {
        super("FeatureToggle", new String[]{"t", "toggle"});
    }

    @Override
    public void onCommand(String[] args) {

        if(args.length == 2 && args[0].equalsIgnoreCase("t") || args.length == 2 && args[0].equalsIgnoreCase("toggle")) {
            try{
                Main.moduleManager.getModuleByName(args[1]).toggle();
                Main.msg(args[1] + " is toggled.", true);
            } catch(Exception e){
                Main.msg("invalid message.", true);
            }
        } else {
            Main.msg("invalid message, use .t/.toggle modulename.", true);
        }
    }
}