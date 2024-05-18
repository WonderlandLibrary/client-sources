/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.util.management;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import pw.vertexcode.nemphis.Nemphis;
import pw.vertexcode.nemphis.module.ModuleManager;
import pw.vertexcode.util.ClientHelper;
import pw.vertexcode.util.module.types.ToggleableModule;

public class KeybindManager {
    public File keybind;

    public KeybindManager() {
        this.keybind = new File(Nemphis.instance.directory, "keybind.ini");
    }

    public void bindModule(ToggleableModule mod, int key) {
        this.writeFile(key);
    }

    public void unbindModule(ToggleableModule mod) {
    }

    public void initFile() {
        if (!this.keybind.exists()) {
            try {
                this.keybind.createNewFile();
            }
            catch (IOException e) {
                ClientHelper.crash("The File 'keybind.ini' could not be created.");
            }
        }
    }

    public void writeFile(int bind) {
        for (ToggleableModule mod : Nemphis.instance.modulemanager.getMods()) {
            try {
                FileWriter writer = new FileWriter(this.keybind);
                writer.write(String.valueOf(mod.getName()) + ":" + bind + "\n");
                continue;
            }
            catch (IOException e) {
                ClientHelper.crash("Something went worng while writing the 'keybind.ini' file.");
            }
        }
    }
}

