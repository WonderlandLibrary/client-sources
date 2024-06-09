/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.other.alts.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import winter.utils.other.alts.Alt;
import winter.utils.other.alts.AltManager;
import winter.utils.other.alts.files.CustomFile;

public class Alts
extends CustomFile {
    public Alts() {
        super("Alts");
    }

    @Override
    public void loadFile() {
        try {
            String line;
            BufferedReader variable9 = new BufferedReader(new FileReader(this.getFile()));
            while ((line = variable9.readLine()) != null) {
                String[] arguments = line.split(":");
                int i2 = 0;
                while (i2 < 2) {
                    arguments[i2].replace(" ", "");
                    ++i2;
                }
                if (arguments.length > 2) {
                    AltManager.registry.add(new Alt(arguments[0], arguments[1], arguments[2]));
                    continue;
                }
                AltManager.registry.add(new Alt(arguments[0], arguments[1]));
            }
            variable9.close();
            System.out.println("Loaded " + this.getName() + " File!");
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override
    public void saveFile() {
        try {
            PrintWriter alts = new PrintWriter(new FileWriter(this.getFile()));
            for (Alt alt2 : AltManager.registry) {
                if (alt2.getMask().equals("")) {
                    alts.println(String.valueOf(String.valueOf(String.valueOf(alt2.getUsername()))) + ":" + alt2.getPassword());
                    continue;
                }
                alts.println(String.valueOf(String.valueOf(String.valueOf(alt2.getUsername()))) + ":" + alt2.getPassword() + ":" + alt2.getMask());
            }
            alts.close();
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}

