package me.valk.command.commands;

import java.util.List;

import org.lwjgl.input.Keyboard;

import me.valk.Vital;
import me.valk.command.Command;
import me.valk.module.Module;
import me.valk.utils.bind.KeyBind;

public class BindCommand extends Command {

	public BindCommand(){
		super("Bind", new String[]{"b"}, "Bind modules .");
	}

	@Override
	public void onCommand(List<String> args) {
		if(args.size() >= 2){
			if(args.get(0).equalsIgnoreCase("add")){
				if(args.size() == 3){
					String moduleName = args.get(1);
					String key = args.get(2);

					if(Vital.getManagers().getModuleManager().getModuleFromName(moduleName) == null){
						error("Module '" + moduleName + "' not found.");
						return;
					}

					if(Keyboard.getKeyIndex(key.toUpperCase()) == 0){
						error("Invalid key!");
						return;
					}

					Module module = Vital.getManagers().getModuleManager().getModuleFromName(moduleName);
					
					module.getData().setKeybind(new KeyBind(module.getName(), Keyboard.getKeyIndex(key.toUpperCase())));

					addChat("Set key for '" + module.getName() +"' to '" + key + "'");		
					
					Vital.getManagers().getModDataManager().save();
				}else{
					error("Invalid args! Usage : 'Bind add [module] [key]' or 'Bind remove [module]'");
				}
			}else if(args.get(0).equalsIgnoreCase("remove")){
				if(args.size() == 2){
					String moduleName = args.get(1);

					if(Vital.getManagers().getModuleManager().getModuleFromName(moduleName) == null){
						error("Module '" + moduleName + "' not found.");
						return;
					}

					Module module = Vital.getManagers().getModuleManager().getModuleFromName(moduleName);

					module.getData().setKeybind(new KeyBind(module.getName(), 0));

					addChat("Removed bind for '" + module.getName() + "'");
					
					Vital.getManagers().getModDataManager().save();
				}else{
					error("Invalid args! Usage : 'Bind add [module] [key]' or 'Bind remove [module]'");
				}
			}
		}else{
			error("Invalid args! Usage : 'Bind add [module] [key]' or 'Bind remove [module]'");
		}
	}

}