/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mpp.venusfr.utils.client.IMinecraft;

public class MacroManager
implements IMinecraft {
    public List<Macro> macroList = new ArrayList<Macro>();
    public File macroFile;

    public MacroManager() {
        this.macroFile = new File(MacroManager.mc.gameDir, "\\venusfr\\files\\macro.cfg");
    }

    public void init() throws IOException {
        if (!this.macroFile.exists()) {
            this.macroFile.createNewFile();
        } else {
            this.readFile();
        }
    }

    public boolean isEmpty() {
        return this.macroList.isEmpty();
    }

    public void addMacro(String string, String string2, int n) {
        this.macroList.add(new Macro(string, string2, n));
        this.writeFile();
    }

    public boolean hasMacro(String string) {
        for (Macro macro : this.macroList) {
            if (!macro.getName().equalsIgnoreCase(string)) continue;
            return false;
        }
        return true;
    }

    public void deleteMacro(String string) {
        if (this.macroList.stream().anyMatch(arg_0 -> MacroManager.lambda$deleteMacro$0(string, arg_0))) {
            this.macroList.removeIf(arg_0 -> MacroManager.lambda$deleteMacro$1(string, arg_0));
            this.writeFile();
        }
    }

    public void clearList() {
        if (!this.macroList.isEmpty()) {
            this.macroList.clear();
        }
        this.writeFile();
    }

    public void onKeyPressed(int n) {
        if (MacroManager.mc.player == null) {
            return;
        }
        this.macroList.stream().filter(arg_0 -> MacroManager.lambda$onKeyPressed$2(n, arg_0)).findFirst().ifPresent(this::lambda$onKeyPressed$3);
    }

    public void writeFile() {
        StringBuilder stringBuilder = new StringBuilder();
        this.macroList.forEach(arg_0 -> MacroManager.lambda$writeFile$4(stringBuilder, arg_0));
        Files.write(this.macroFile.toPath(), stringBuilder.toString().getBytes(), new OpenOption[0]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void readFile() {
        FileInputStream fileInputStream = new FileInputStream(this.macroFile.getAbsolutePath());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(fileInputStream)));
        try {
            String string;
            while ((string = bufferedReader.readLine()) != null) {
                String string2 = string.trim();
                String string3 = string2.split(":")[0];
                String string4 = string2.split(":")[1];
                String string5 = string2.split(":")[2];
                this.macroList.add(new Macro(string3, string4, Integer.parseInt(string5)));
            }
        } finally {
            if (Collections.singletonList(bufferedReader).get(0) != null) {
                bufferedReader.close();
            }
        }
    }

    private static void lambda$writeFile$4(StringBuilder stringBuilder, Macro macro) {
        stringBuilder.append(macro.getName()).append(":").append(macro.getMessage()).append(":").append(String.valueOf(macro.getKey()).toUpperCase()).append("\n");
    }

    private void lambda$onKeyPressed$3(Macro macro) {
        try {
            MacroManager.mc.player.sendChatMessage(macro.getMessage());
        } catch (Exception exception) {
            this.print("\u041e\u0448\u0438\u0431\u043a\u0430 \u043f\u0440\u0438 \u043e\u0442\u043f\u0440\u0430\u0432\u043a\u0438 \u043a\u043e\u043c\u0430\u043d\u0434\u044b " + exception);
        }
    }

    private static boolean lambda$onKeyPressed$2(int n, Macro macro) {
        return macro.getKey() == n;
    }

    private static boolean lambda$deleteMacro$1(String string, Macro macro) {
        return macro.getName().equalsIgnoreCase(string);
    }

    private static boolean lambda$deleteMacro$0(String string, Macro macro) {
        return macro.getName().equals(string);
    }

    public static final class Macro {
        private final String name;
        private final String message;
        private final int key;

        public Macro(String string, String string2, int n) {
            this.name = string;
            this.message = string2;
            this.key = n;
        }

        public String getName() {
            return this.name;
        }

        public String getMessage() {
            return this.message;
        }

        public int getKey() {
            return this.key;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (!(object instanceof Macro)) {
                return true;
            }
            Macro macro = (Macro)object;
            if (this.getKey() != macro.getKey()) {
                return true;
            }
            String string = this.getName();
            String string2 = macro.getName();
            if (string == null ? string2 != null : !string.equals(string2)) {
                return true;
            }
            String string3 = this.getMessage();
            String string4 = macro.getMessage();
            return string3 == null ? string4 != null : !string3.equals(string4);
        }

        public int hashCode() {
            int n = 59;
            int n2 = 1;
            n2 = n2 * 59 + this.getKey();
            String string = this.getName();
            n2 = n2 * 59 + (string == null ? 43 : string.hashCode());
            String string2 = this.getMessage();
            n2 = n2 * 59 + (string2 == null ? 43 : string2.hashCode());
            return n2;
        }

        public String toString() {
            return "MacroManager.Macro(name=" + this.getName() + ", message=" + this.getMessage() + ", key=" + this.getKey() + ")";
        }
    }
}

