package win.sightclient.filemanager.files;

import java.util.ArrayList;

import win.sightclient.filemanager.DataFile;
import win.sightclient.ui.altmanager.Alt;
import win.sightclient.ui.altmanager.AltManager;
import win.sightclient.utils.FileUtils;

public class AltsFile extends DataFile {

	public AltsFile() {
		super("Alts.sight");
		this.load();
	}
	
	@Override
	public void save() {
		ArrayList<String> toWrite = new ArrayList<String>();
		for (int k = 0; k < AltManager.getAlts().size(); k++) {
			toWrite.add(AltManager.getAlts().get(k).getUser() + ":" + AltManager.getAlts().get(k).getPass());
		}
		
		if (!toWrite.isEmpty()) {
			FileUtils.writeToFile(this.file, toWrite);
		}
	}
	
	@Override
	public void load() {
		ArrayList<String> lines = FileUtils.getLines(this.file);
		for (int k = 0; k < lines.size(); k++) {
			if (lines.get(k).contains(":")) {
				String username = "";
				if (lines.get(k).split(":").length > 0) {
					username = lines.get(k).split(":")[0];
				}
				String pass = "";
				if (lines.get(k).split(":").length > 1) {
					pass = lines.get(k).split(":")[1];
				}
				AltManager.getAlts().add(new Alt(username == null ? "" : username, pass == null ? "" : pass));
			}
		}
	}
}
