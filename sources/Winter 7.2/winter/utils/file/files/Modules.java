/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.file.files;

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
import org.lwjgl.input.Keyboard;
import winter.Client;
import winter.module.Module;
import winter.utils.file.FileManager;

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
            String base = line.substring(i2 + 1).trim();
            Module m2 = Client.getManager().getMod(module);
            int i22 = base.indexOf(":");
            if (i22 < 0) continue;
            String enabled = base.substring(0, i22).trim();
            String base2 = base.substring(i22 + 1).trim();
            if (m2 == null) continue;
            m2.toggleBoolean(Boolean.valueOf(enabled));
            int i3 = base2.indexOf(":");
            if (i3 < 0) continue;
            String key = base2.substring(0, i3).trim();
            String vis = base2.substring(i3 + 1).trim();
            m2.setBind(Keyboard.getKeyIndex(key.toUpperCase()));
            m2.visible(Boolean.valueOf(vis));
        }
        variable9.close();
        System.out.println("Loaded " + this.getName() + " File!");
    }

    @Override
    public void saveFile() throws IOException {
        PrintWriter variable9 = new PrintWriter(new FileWriter(this.getFile()));
        for (Module m2 : Client.getManager().getMods()) {
            String modstuff = String.valueOf(String.valueOf(m2.getName())) + ":" + m2.isEnabled() + ":" + Keyboard.getKeyName(m2.getBind()) + ":" + m2.visible();
            variable9.println(modstuff);
        }
        variable9.close();
    }
}

