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
import winter.Client;
import winter.module.Module;
import winter.utils.file.FileManager;
import winter.utils.value.Value;
import winter.utils.value.types.BooleanValue;
import winter.utils.value.types.NumberValue;

public class Options
extends FileManager.CustomFile {
    public Options(String name, boolean Module2, boolean loadOnStart) {
        super(name, Module2, loadOnStart);
    }

    @Override
    public void loadFile() throws IOException {
        String line;
        BufferedReader variable9 = new BufferedReader(new FileReader(this.getFile()));
        while ((line = variable9.readLine()) != null) {
            String[] stuff = line.split(":");
            Module module = Client.getManager().getMod(stuff[0]);
            Value val = module.getValue(stuff[1]);
            if (val instanceof BooleanValue) {
                BooleanValue value = (BooleanValue)val;
                value.set(Boolean.valueOf(stuff[2]));
                System.out.println("Loaded: " + line);
                continue;
            }
            if (!(val instanceof NumberValue)) continue;
            NumberValue value = (NumberValue)val;
            value.setVal(Double.valueOf(stuff[2]));
            System.out.println("Loaded: " + line);
        }
        variable9.close();
        System.out.println("Loaded " + this.getName() + " File!");
    }

    @Override
    public void saveFile() throws IOException {
        PrintWriter variable9 = new PrintWriter(new FileWriter(this.getFile()));
        for (Module m2 : Client.getManager().getMods()) {
            for (Value val : m2.getValues()) {
                BooleanValue value;
                if (val instanceof BooleanValue) {
                    value = (BooleanValue)val;
                    variable9.println(String.valueOf(m2.getName()) + ":" + value.getName() + ":" + value.isEnabled());
                    continue;
                }
                else if ((val instanceof NumberValue))
                {
                  NumberValue value1 = (NumberValue)val;
                  variable9.println(m2.getName() + ":" + value1.getName() + ":" + value1.getValue());
                }
            }
        }
        variable9.close();
    }
}

