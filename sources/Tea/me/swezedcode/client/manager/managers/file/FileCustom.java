package me.swezedcode.client.manager.managers.file;

import java.io.File;

import me.swezedcode.client.manager.managers.FileManager;

public abstract class FileCustom {
	private static File file;
	private static String fileName;

	public static File getFile() {
		return file;
	}

	public static String getName() {
		return fileName;
	}

	public FileCustom(String fileName) {
		FileCustom.fileName = fileName;
		file = new File(FileManager.getDirectory().getAbsolutePath(), fileName + ".txt");
		if (!file.exists()) {
			System.out.println(fileName);
			FileManager.createFile(fileName + ".txt");
		}
	}
}
