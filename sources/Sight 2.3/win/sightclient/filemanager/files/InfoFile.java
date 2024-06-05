package win.sightclient.filemanager.files;

import java.util.ArrayList;

import win.sightclient.Sight;
import win.sightclient.filemanager.DataFile;
import win.sightclient.utils.CryptUtils;
import win.sightclient.utils.FileUtils;

public class InfoFile extends DataFile {

	public InfoFile() {
		super("Data.sight");
		this.load();
	}
	
	@Override
	public void save() {
		ArrayList<String> lines = new ArrayList<String>();
		lines.add(CryptUtils.encrypt("SightIsTheBest", "Altening:" + Sight.instance.theAltening));
		lines.add(CryptUtils.encrypt("SightIsTheBest", "SXToken:" + Sight.instance.sxAlts));
		FileUtils.writeToFile(this.file, lines);
	}
	
	@Override
	public void load() {
		ArrayList<String> lines = FileUtils.getLines(this.file);
		for (String str : lines) {
			String decrypted = CryptUtils.decrypt("SightIsTheBest", str);
			if (!decrypted.contains(":")) {
				continue;
			}
			if (decrypted.toLowerCase().startsWith("altening:") && decrypted.split(":").length > 1) {
				Sight.instance.theAltening = decrypted.split(":")[1];
			}
			
			if (decrypted.toLowerCase().startsWith("sxtoken:") && decrypted.split(":").length > 1) {
				Sight.instance.sxAlts = decrypted.split(":")[1];
			}
		}
	}
}
