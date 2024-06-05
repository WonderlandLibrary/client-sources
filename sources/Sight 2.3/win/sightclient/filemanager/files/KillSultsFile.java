package win.sightclient.filemanager.files;

import java.io.File;
import java.util.ArrayList;

import win.sightclient.Sight;
import win.sightclient.filemanager.DataFile;
import win.sightclient.filemanager.FileManager;
import win.sightclient.module.combat.KillSults;
import win.sightclient.utils.FileUtils;

public class KillSultsFile extends DataFile {

	static boolean existed;
	
	static {
		existed = new File(FileManager.dir, "KillSults.sight").exists();
	}
	
	public KillSultsFile() {
		super("KillSults.sight");
		killsults = (KillSults) Sight.instance.mm.getModuleByName("Killsults");
		if (!existed) {
			this.saveForFirstTime();
		} else {
			this.load();
		}
	}

	private KillSults killsults;
	
	public void saveForFirstTime() {
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("Go Download sight %name%");
		lines.add("%name%, you just got killed by sight");
		lines.add("Sight is the best client, %name% you should go download.");
		lines.add("bad client");
		lines.add("U got slayed by sight.");
		lines.add("Sight is best");
		killsults.insults = lines;
		this.save();
	}
	
	@Override
	public void save() {
		FileUtils.writeToFile(this.file, killsults.insults);
	}
	
	@Override
	public void load() {
		killsults.insults = FileUtils.getLines(this.file);
	}
}
