package me.valk.command.commands;

import java.util.List;

import me.valk.Vital;
import me.valk.command.Command;
import me.valk.module.Module;

/**
 * Created by Zeb on 5/7/2016.
 */
public class ToggleCommand extends Command {

    public ToggleCommand(){
        super("Toggle", new String[]{"t"}, "Toggle modules.");
    }

    @Override
    public void onCommand(List<String> args){
        if(args.size() != 1){
            error("Invalid args! Usage : 'Toggle [module]'");
            return;
        }

        Module module = Vital.getManagers().getModuleManager().getModuleFromName(args.get(0));

        if(module == null){
            error("Module '" + args.get(0) + "' not found.");
            return;
        }

        module.toggle();
        addChat("Toggled §8" + module.getName());
       
       
        
    }
}
