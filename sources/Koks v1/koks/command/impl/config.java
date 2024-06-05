package koks.command.impl;

import koks.Koks;
import koks.command.Command;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 22:14
 */
public class config extends Command {

    public config() {
        super("config", "configs");
    }


    @Override
    public void execute(String[] args) {
        if(args.length >= 2) {
            if(args[0].equalsIgnoreCase("save")) {
                StringBuilder name = new StringBuilder();
                for(int i = 1; i < args.length; i++) name.append(args[i]).append(" ");

                String configName = name.substring(0, name.length() -1);
                Koks.getKoks().configManager.createConfig(configName);
                sendmsg("§acreated config §e" + configName,true);
            }else if(args[0].equalsIgnoreCase("load")) {
                StringBuilder name = new StringBuilder();
                for(int i = 1; i < args.length; i++) {
                    name.append(args[i]).append(" ");
                }

                String configName = name.substring(0, name.length() - 1);
                if(Koks.getKoks().configManager.configExist(configName)) {
                    Koks.getKoks().configManager.loadConfig(configName);
                    sendmsg("§aloaded config §e" + configName, true);
                }else{
                    sendError("NOT FOUND", "§7" + configName + " not exist!",true);
                }
            }
        }else{
            sendError("USAGE" ,"§7.config load/save [Name]",true);
        }
    }
}
