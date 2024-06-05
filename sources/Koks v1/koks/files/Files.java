package koks.files;

import koks.Koks;
import net.minecraft.client.Minecraft;

import java.io.*;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 16:30
 */
public abstract class Files {

    public File file;

    public Minecraft mc = Minecraft.getMinecraft();

    public Files(String name) {
        this.file = new File(mc.mcDataDir + "/" + Koks.getKoks().CLIENT_NAME, name + "." + Koks.getKoks().CLIENT_NAME.toLowerCase());
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public abstract void writeToFile(FileWriter fileWriter) throws Exception;
    public abstract void readFromFile(BufferedReader fileReader) throws Exception;

    public void createFile() {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean existFile(){
        return file.exists();
    }
}
