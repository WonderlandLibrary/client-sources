/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Configs;

import drunkclient.beta.Client;
import java.io.File;
import java.io.IOException;

public class Configs {
    private File dir;
    private File dataFile;

    public void save(String name) {
        this.dir = new File(String.valueOf(Client.instance.dir));
        this.dataFile = new File(this.dir, name);
        if (this.dataFile.exists()) return;
        try {
            this.dataFile.createNewFile();
            return;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(String name) {
        this.dir = new File(String.valueOf(Client.instance.dir));
        if (!this.dir.exists()) {
            this.dir.mkdir();
        }
        this.dataFile = new File(this.dir, name + ".drunkclient");
        try {
            this.dataFile.delete();
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

