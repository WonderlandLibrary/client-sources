package Snipeware.command.impl;

import org.lwjgl.input.Keyboard;

import Snipeware.Client;
import Snipeware.command.Command;
import Snipeware.gui.click.ClickGui;
import Snipeware.module.Module;
import Snipeware.util.other.Logger;

public class Unbind extends Command {

    public Unbind() {
        super("unbind");
    }

    @Override
    public String usage() {
        return "unbind <module/all>";
    }

    @Override
    public void executeCommand(String[] commandArguments) {
        if(commandArguments.length > 1 || commandArguments.length < 1) {
            printUsage();
        }
        else {
            String arg1 = commandArguments[0];
            if(arg1.toLowerCase().contains("all")) {
	            for(Module module : Client.INSTANCE.getModuleManager().getModules()) {
	            	module.setKeyBind(0);
	            }
	            Module clickgui = Client.INSTANCE.getModuleManager().getModule("clickgui");
	            clickgui.setKeyBind(54);
	            Logger.print("Unbinded all modules.");
	            return;
            }
            if(Client.INSTANCE.getModuleManager().getModule(arg1.replaceAll("_", " ")) != null) {
	            Module module = Client.INSTANCE.getModuleManager().getModule(arg1.replaceAll("_", " "));
	            if(arg1.replaceAll("_", " ").toLowerCase().contains("clickgui")) {
	            	Logger.print("Why would you want to do that");
	            	return;
	            }
	            else {
	            module.setKeyBind(0);
	            }
	            Logger.print("Unbinded "+module.getName());
            } 
            else {
            	Logger.print("Could not find a module with that name!");
            }
        }
    }
}
