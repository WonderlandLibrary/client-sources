package Focus.Beta.IMPL.Configs;

import Focus.Beta.Client;
import Focus.Beta.IMPL.managers.FileManager;

import java.io.File;
import java.io.IOException;

public class Configs {
    private File dir;
    private File dataFile;
    public void save(final String name) {
        this.dir = new File(String.valueOf(Client.instance.dir));
        this.dataFile = new File(this.dir, name);
        if (!this.dataFile.exists()) {
            try {
                this.dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(final String name) {
        this.dir = new File(String.valueOf(Client.instance.dir));
        if (!this.dir.exists()) {
            this.dir.mkdir();
        }
        this.dataFile = new File(this.dir, name + ".focus");
        try {
            this.dataFile.delete();
        }
        catch (Exception ex) {}
    }

}
