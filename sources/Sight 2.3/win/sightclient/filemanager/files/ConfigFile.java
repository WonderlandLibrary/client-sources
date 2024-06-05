package win.sightclient.filemanager.files;

import java.io.File;
import java.util.ArrayList;

import win.sightclient.Sight;
import win.sightclient.filemanager.DataFile;
import win.sightclient.module.Module;
import win.sightclient.module.settings.BooleanSetting;
import win.sightclient.module.settings.ModeSetting;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.module.settings.Setting;
import win.sightclient.utils.FileUtils;

public class ConfigFile extends DataFile {

	public ConfigFile(String configName) {
		super(configName);
	}
	
	public ConfigFile(File file) {
		super(file);
	}
	
	@Override
	public void save() {
		try {
			ArrayList<String> toWrite = new ArrayList<String>();
			
			for (Module m : Sight.instance.mm.getModules()) {
				toWrite.add("MOD:" + m.getName() + ":" + m.isToggled() + ":" + m.isHidden());
			}
			
			for (Setting s : Sight.instance.sm.getSettings()) {
				if (s.getValue() != null) {
					String toAdd = s.getModule().getName() + ":" + s.getName() + ":";
					toAdd = toAdd + s.getValue();
					toWrite.add("SET:" + toAdd);
				}
			}
			
			FileUtils.writeToFile(this.file, toWrite);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void load() {
		try {
			ArrayList<String> lines = FileUtils.getLines(this.file);
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
