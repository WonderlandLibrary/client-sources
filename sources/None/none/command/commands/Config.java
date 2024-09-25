package none.command.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import none.Client;
import none.command.Command;
import none.module.Checker;
import none.module.Module;
import none.module.modules.combat.AutoAwakeNgineXE;
import none.notifications.Notification;
import none.notifications.NotificationManager;
import none.notifications.NotificationType;
import none.utils.ChatUtil;
import none.utils.FileUtils;

public class Config extends Command{

	@Override
	public String getAlias() {
		return "config";
	}

	@Override
	public String getDescription() {
		return "Load Settings or Save Settings with out KeyBind settings";
	}

	@Override
	public String getSyntax() {
		return ".config <Load,Save, List>";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if (args == null || args.length > 2) {
            evc(getSyntax());
            return;
        }else if(args.length == 1){
         	if(args[0].equalsIgnoreCase("load")){
         		evc("Config : .config load <ConfigName>");
         		return;
         	}else if(args[0].equalsIgnoreCase("save")){
         		evc("Config : .config save <ConfigName>");
         		return;
         	}else if(args[0].equalsIgnoreCase("list")){
         		ArrayList<String> list = Client.instance.fileManager.getConfigList();
         		int lenght = list.size();
         		String send = "Config list : ";
         		for(int i = 0; i < lenght; i++){
         			if(i == lenght-1)
     					send = send + "\2477" + list.get(i) + ".";
     				else
     					send = send + "\2477" + list.get(i) + ", ";
         		}
         		evc(send);
         		return;
         	}
        }else if(args.length == 2){
        	if(args[0].equalsIgnoreCase("load")){
        		String config = args[1];
        		Client.instance.fileManager.loadConfig(config);
                return;
        	}else
        	if(args[0].equalsIgnoreCase("save")){
        		String config = args[1];
        		Client.instance.fileManager.SaveConfig(config);
                return;
        	}
        }
        evc(getSyntax());
	}
}
