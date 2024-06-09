package me.protocol_client.files.allfiles;

import java.io.File;
import java.io.IOException;

import me.protocol_client.Protocol;

public abstract class GuiFile {
	    protected final String name, extension;
	    protected final File file;

	    public GuiFile(String name, String extension) {
	        this.name = name;
	        this.extension = extension;
	        file = new File(Protocol.protocolDir + File.separator + name + "." + extension);


	        try {
	            if ((!file.exists() || file.isDirectory()) && file.createNewFile()) {
	                save();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        Protocol.getOtherFileManager().getContents().add(this);
	        
}
	    public String getName() {
	        return name;
	    }

	    public String getExtension() {
	        return extension;
	    }

	    public File getFile() {
	        return file;
	    }

	    public abstract void load() throws IOException;

	    public abstract void save() throws IOException;
}