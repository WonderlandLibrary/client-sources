/*
 * Decompiled with CFR 0_115.
 */
package cow.milkgod.cheese.files.files.modulefiles;

import cow.milkgod.cheese.Cheese;
import cow.milkgod.cheese.files.FileManager;
import cow.milkgod.cheese.module.Module;
import cow.milkgod.cheese.module.ModuleManager;
import cow.milkgod.cheese.module.modules.ESP;
import cow.milkgod.cheese.module.modules.Search;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;

public class Modules
extends FileManager.CustomFile {
    public Modules(String name, boolean Module2, boolean loadOnStart) {
        super(name, Module2, loadOnStart);
    }

    @Override
    public void loadFile() throws IOException {
        String line;
        BufferedReader variable9 = new BufferedReader(new FileReader(this.getFile()));
        while ((line = variable9.readLine()) != null) {
            int i2 = line.indexOf(":");
            if (i2 < 0) continue;
            String module = line.substring(0, i2).trim();
            String Nigger = line.substring(i2 + 1).trim();
            String enabled = line.substring(i2 + 1, i2 + Nigger.length() - 4).replaceAll(":", "");
            String visible = line.substring(i2 + (enabled.length() + 2)).trim();
            Cheese.getInstance();
            Module m2 = Cheese.moduleManager.getModbyName(module);
            if (Boolean.valueOf(enabled).booleanValue() && !m2.getState()) {
                Cheese.getInstance();
                if (m2 != Cheese.moduleManager.getModule(ESP.class)) {
                    Cheese.getInstance();
                    if (m2 != Cheese.moduleManager.getModule(Search.class)) {
                        m2.toggleModule();
                    }
                }
            }
            m2.setVisible(Boolean.valueOf(visible));
        }
        variable9.close();
        System.out.println("Loaded " + this.getName() + " File!");
    }

    @Override
    public void saveFile() throws IOException {
        PrintWriter variable9 = new PrintWriter(new FileWriter(this.getFile()));
        for (Module m2 : ModuleManager.activeModules) {
            variable9.println(String.valueOf(String.valueOf(m2.getName())) + ":" + m2.getState() + ":" + m2.isVisible());
        }
        variable9.close();
    }
}

