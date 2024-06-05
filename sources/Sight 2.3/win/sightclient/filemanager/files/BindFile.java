package win.sightclient.filemanager.files;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import win.sightclient.Sight;
import win.sightclient.filemanager.DataFile;
import win.sightclient.module.Module;
import win.sightclient.utils.FileUtils;

public class BindFile extends DataFile {

	public BindFile() {
		super("Binds.sight");
		this.load();
	}
	
	@Override
	public void save() {
		ArrayList<String> toWrite = new ArrayList<String>();
		for (Module m : Sight.instance.mm.getModules()) {
			if (m.getKey() != Keyboard.KEY_NONE) {
				toWrite.add(m.getName() + ":" + m.getKey());
			}
		}
		FileUtils.writeToFile(this.file, toWrite);
	}
	
	@Override
	public void load() {
		ArrayList<String> lines = FileUtils.getLines(this.file);
		for (int k = 0; k < lines.size(); k++) {
			if (lines.get(k).contains(":")) {
				String[] args = lines.get(k).split(":");
				Module m = Sight.instance.mm.getModuleByName(args[0]);
				if (m != null && args.length > 1) {
					try {
						m.setKey(Integer.parseInt(args[1]));
					} catch (Exception e) {e.printStackTrace();}
				}
			}
		}
	}
}
