/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package cc.diablo.manager.file.files;

import cc.diablo.manager.file.FileManager;
import cc.diablo.manager.module.ModuleManager;
import cc.diablo.module.Module;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.lwjgl.input.Keyboard;

public class KeyBindsFile
extends FileManager.CustomFile {
    public KeyBindsFile(String name, boolean Module2, boolean loadOnStart) {
        super(name, Module2, loadOnStart);
    }

    @Override
    public void loadFile() throws IOException {
        String line;
        BufferedReader variable9 = new BufferedReader(new FileReader(this.getFile()));
        while ((line = variable9.readLine()) != null) {
            int i = line.indexOf(":");
            if (i < 0) continue;
            String module = line.substring(0, i).trim();
            String key = line.substring(i + 1).trim();
            Module m = ModuleManager.getModuleByName(module);
            if (key.isEmpty() || m == null) continue;
            m.setKey(Keyboard.getKeyIndex((String)key.toUpperCase()));
        }
        variable9.close();
        System.out.println("Loaded " + this.getName() + " File");
    }

    @Override
    public void saveFile() throws IOException {
        PrintWriter variable9 = new PrintWriter(new FileWriter(this.getFile()));
        for (Module m : ModuleManager.modules) {
            variable9.println(String.valueOf(String.valueOf(m.getName())) + ":" + Keyboard.getKeyName((int)m.getKey()));
        }
        variable9.close();
    }
}

