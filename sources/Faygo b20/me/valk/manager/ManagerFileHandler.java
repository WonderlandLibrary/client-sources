package me.valk.manager;

import java.io.File;
import java.io.IOException;

public interface ManagerFileHandler {

	void save(File file) throws IOException;
	
	void load(File file) throws IOException;
	
}

