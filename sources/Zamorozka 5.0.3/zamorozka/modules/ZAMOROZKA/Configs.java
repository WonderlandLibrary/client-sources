package zamorozka.modules.ZAMOROZKA;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Keyboard;

import de.Hero.clickgui.Panel;
import de.Hero.settings.Setting;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.notification.Notification;
import zamorozka.notification.NotificationPublisher;
import zamorozka.notification.NotificationType;
import zamorozka.ui.ChatUtils;

public class Configs extends Module{
	

	public Configs(){
		super("Configs", 0, Category.Zamorozka);
	}

    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        Zamorozka.settingsManager.rSetting(new Setting("Configs", this, "", options));
		 File folder = new File((mc).mcDataDir + File.separator + "Zamorozka/cfgs");
		 File[] listOfFiles = folder.listFiles();
		 if(folder.exists())
			{
			 for (File file : listOfFiles) {
			     if (file.isFile()) {
			         options.add(file.getName());
			     }
			 }
			}
    }
	@Override
	public void onEnable() {
		try {
			 File folder = new File((mc).mcDataDir + File.separator + "Zamorozka/cfgs");
			 File[] listOfFiles = folder.listFiles();
			 String mode = Zamorozka.instance.settingsManager.getSettingByName("Configs").getValString();
			 for (File file : listOfFiles) {
			     if (file.isFile()) {
				        	 if(mode.equalsIgnoreCase(file.getName())) {
				        	 if(file.getName()!=null) {
				        		File file1 = new File((mc).mcDataDir + File.separator + "Zamorozka/cfgs", file.getName());
				        		if(file1!=null) {
				     				FileInputStream fstream = null;
									try {
										fstream = new FileInputStream(file1.getAbsolutePath());
									} catch (FileNotFoundException e) {}
				     				DataInputStream in = new DataInputStream(fstream);
				     				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				     				String line;
				     				try {
										while((line = br.readLine()) != null)
										{
											String readString = line.trim();
										   String[] split = readString.split(":");
										   for(Setting s : Zamorozka.settingsManager.getSettings()){
										      if(s.getName().equals(split[0])) {
										    	s.setValString(split[1]);
										        s.setValBoolean(Boolean.valueOf(split[2]).booleanValue());
										        s.setValDouble(Float.valueOf(split[3]));
										      }
										   }
										}
									} catch (NumberFormatException e) {} catch (IOException e) {}
				     				try {
										br.close();
										NotificationPublisher.queue("Config", file.getName() + " Was loaded!", NotificationType.SUCCESS);
										ModuleManager.getModule(Configs.class).setState(false);
									} catch (IOException e) {}	
				        		}else {
				        			NotificationPublisher.queue("Config", "Not found!", NotificationType.ERROR);
				        		} 
				         }
			    	 }
			     }
			 }
		}catch(NumberFormatException e){
			ChatUtils.printChatprefix("Неизвестная ошибка.");
		}
		super.onEnable();
	}
}
