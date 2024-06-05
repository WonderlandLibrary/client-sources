package win.sightclient.filemanager;

import java.io.File;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import win.sightclient.filemanager.files.AltsFile;
import win.sightclient.filemanager.files.BindFile;
import win.sightclient.filemanager.files.ConfigFile;
import win.sightclient.filemanager.files.InfoFile;
import win.sightclient.filemanager.files.KillSultsFile;
import win.sightclient.filemanager.files.TimePlayedFile;


public class FileManager {

	private ArrayList<DataFile> files = new ArrayList<DataFile>();
	public static File dir;
	public static File configDir;
	public static File hitsDir;
	
	public FileManager() {
		dir = new File(Minecraft.getMinecraft().mcDataDir, "sight");
		if (!dir.exists()) {
			dir.mkdir();
		}
		configDir = new File(dir, "configs");
		if (!configDir.exists()) {
			configDir.mkdir();
		}
		
		ConfigFile defaultConfig = new ConfigFile("DefaultConfig.cfg");
		defaultConfig.load();
		files.add(defaultConfig);
		files.add(new TimePlayedFile());
		files.add(new InfoFile());
		files.add(new AltsFile());
		files.add(new BindFile());
		this.getBindsFile().load();
		files.add(new KillSultsFile());
	}
	
	public void saveDefaultConfig() {
		files.get(0).save();
	}
	
	public void saveTimePlayedFile() {
		files.get(1).save();
	}
	
	public void saveDataFile() {
		files.get(2).save();
	}
	
	public void saveAltsFile() {
		files.get(3).save();
	}
	
	public DataFile getBindsFile() {
		return files.get(4);
	}
	
	public DataFile getKillsultsFile() {
		return files.get(5);
	}
	
	public DataFile getDataFile(String fileName) {
		for (int k = 0; k < files.size(); k++) {
			DataFile df = files.get(k);
			if (df.getName() != null && df.getName().equalsIgnoreCase(fileName) || (df.getName().contains(".") && df.getName().split(".")[0].equalsIgnoreCase(fileName))) {
				return df;
			}
		}
		return null;
	}
	
	public ArrayList<DataFile> files() {
		return this.files;
	}
	
	public ArrayList<File> getConfigs() {
		ArrayList<File> c = new ArrayList<File>();
		for (File f : configDir.listFiles()) {
			if (f.getName().toLowerCase().endsWith(".txt")) {
				c.add(f);
			}
		}
		return c;
	}
}
