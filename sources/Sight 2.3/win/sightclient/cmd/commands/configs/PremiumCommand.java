package win.sightclient.cmd.commands.configs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import win.sightclient.Sight;
import win.sightclient.cmd.Command;
import win.sightclient.module.Module;
import win.sightclient.module.settings.BooleanSetting;
import win.sightclient.module.settings.ModeSetting;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.module.settings.Setting;
import win.sightclient.utils.minecraft.ChatUtils;

public class PremiumCommand extends Command {

	public PremiumCommand() {
		super(new String[] {"premium"});
	}

	public void onCommand(String message) {
		//.if (Security3.isPremium()) {
			ChatUtils.sendMessage("Downloading premium config...");
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
				    ArrayList<String> list = new ArrayList<String>();
					try {
						URL url = new URL("https://sighthost.netlify.app/configs/premium");
				        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {

				            String line;
				            while ((line = br.readLine()) != null) {
				            	list.add(line);
				            }
				        } catch (IOException e) {
							e.printStackTrace();
						}
					} catch (Exception e) {
						System.out.println("ERROR: " + e.getMessage());
					}
					load(list);
					ChatUtils.sendMessage("Loaded the premium config.");
				}
			});
			t.start();
		//} else {
			//ChatUtils.sendMessage("Sorry u need to be premium to download this config.");
		//}
	}
	
	public void load(ArrayList<String> lines) {
		try {
			for (String str : lines) {
				String[] args = str.split(":");
				try {
					if (str.toLowerCase().startsWith("mod:")) {
						Module m = Sight.instance.mm.getModuleByName(args[1]);
						if (m != null) {
							m.setToggledNoSave(Boolean.parseBoolean(args[2]));
							m.setHidden(Boolean.parseBoolean(args[3]));
						}
					} else if (str.toLowerCase().startsWith("set:") && args.length > 3) {
						Module m = Sight.instance.mm.getModuleByName(args[1]);
						if (m != null) {
							Setting s = Sight.instance.sm.getSettingByName(m, args[2]);
							if (s != null && args[3] != null) {
								if (s instanceof BooleanSetting) {
									BooleanSetting bs = (BooleanSetting)s;
									bs.setValue(Boolean.parseBoolean(args[3]), false);
								}
								if (s instanceof ModeSetting) {
									ModeSetting ms = (ModeSetting)s;
									ms.setValue(args[3], false);
								}
								if (s instanceof NumberSetting) {
									NumberSetting ns = (NumberSetting)s;
									ns.setValue(Double.parseDouble(args[3]), false);
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
