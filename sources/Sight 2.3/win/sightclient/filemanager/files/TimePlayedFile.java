package win.sightclient.filemanager.files;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import win.sightclient.Sight;
import win.sightclient.filemanager.DataFile;
import win.sightclient.utils.CryptUtils;
import win.sightclient.utils.FileUtils;

public class TimePlayedFile extends DataFile {

	public TimePlayedFile() {
		super("TimePlayed.sight");
		this.load();
	}
	
	@Override
	public void save() {
		try {
			ArrayList<String> lines = FileUtils.getLines(this.file);
			double hours = 0;
			if (!lines.isEmpty() && lines.size() > 0 && !lines.get(0).isEmpty()) {
				hours = Double.parseDouble(CryptUtils.decrypt("SebIsTheBest",lines.get(0)));
			}
			long time = System.currentTimeMillis() - Sight.instance.getStartTime();
			double minute = TimeUnit.MILLISECONDS.toMinutes(time);
			double start = minute / 60;
			if (start > 0) {
				hours += start;
				lines.clear();
				String toAdd = CryptUtils.encrypt("SebIsTheBest", hours + "");
				lines.add(toAdd);
				System.out.println("Hours Played: " + hours);
				FileUtils.writeToFile(this.file, lines);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void load() {
		ArrayList<String> lines = FileUtils.getLines(this.file);
		if (!lines.isEmpty() && lines.size() > 0 && !lines.get(0).isEmpty()) {
			String encryptedTime = lines.get(0);
			Sight.instance.hoursPlayed = Double.parseDouble(CryptUtils.decrypt("SebIsTheBest", encryptedTime));
			
		}
	}
}
