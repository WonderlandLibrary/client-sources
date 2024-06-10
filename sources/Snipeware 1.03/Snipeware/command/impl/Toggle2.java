package Snipeware.command.impl;

import Snipeware.Client;
import Snipeware.command.Command;
import Snipeware.module.Module;
import Snipeware.util.other.Logger;

public class Toggle2 extends Command {

    public Toggle2() {
        super("t");
    }

    @Override
    public String usage() {
        return "t <module>";
    }

    @Override
    public void executeCommand(String[] commandArguments) {
        if(commandArguments.length != 1) {
            this.printUsage();
        }
        if(commandArguments.length == 1) {
          String moduleName = commandArguments[0];

              if (Client.INSTANCE.getModuleManager().getModule(moduleName) != null) {
                  Module module = Client.INSTANCE.getModuleManager().getModule(moduleName);
                  Logger.print("Toggled " + module.getName());
                  module.toggle();
              } 
              else {
            	  Logger.print("Could not find a module with that name!");
              }
          }
     }
}
