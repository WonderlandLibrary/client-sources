/*
 * Decompiled with CFR 0_122.
 */
package winter.console.cmds;

import groovy.lang.GroovyShell;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import winter.console.cmds.Command;
import winter.module.modules.NoteBot;

public class Loadsong
extends Command {
    public Loadsong() {
        super("load");
        this.desc("Loads song.");
        this.use("load [name]");
    }

    @Override
    public void run(String cmd2) {
        String[] args = cmd2.split(" ");
        if (args[0].equalsIgnoreCase("load") && args.length > 0) {
            String name = args[1];
            try {
                File file = null;
                file = Util.getOSType() == Util.EnumOS.LINUX ? new File(Minecraft.getMinecraft().mcDataDir + "/Winter/songs/" + name + ".groovy") : new File(Minecraft.getMinecraft().mcDataDir + "\\Winter\\songs\\" + name + ".groovy");
                NoteBot.note = 0;
                NoteBot.notes.clear();
                NoteBot.groovyShell.evaluate(new FileReader(file));
                this.printChat("The song " + file.getName() + " was loaded successfully.");
            }
            catch (Exception e2) {
                this.printChat("Failed to load: " + name + ", are you sure you got the correct name?");
                e2.printStackTrace();
            }
        }
    }
}

