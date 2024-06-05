package win.sightclient.filemanager;

import java.io.File;
import java.io.IOException;

public class DataFile {

	protected File file;
	protected String name = "";
	
	public DataFile(String fileName) {
		name = fileName;
		file = new File(FileManager.dir, fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public DataFile(File file) {
		name = file.getName();
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.file = file;
	}
	
	public String getName() {
		return this.name;
	}
	
	public File getFile() {
		return this.file;
	}
	
	public void save() {}
	
	public void load() {}
}
