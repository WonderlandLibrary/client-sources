package host.kix.uzi.file.files;

import host.kix.uzi.Uzi;
import host.kix.uzi.file.CustomFile;
import host.kix.uzi.module.Module;
import org.lwjgl.input.Keyboard;

import java.io.*;

/**
 * Created by Kix on 6/4/2017.
 * Made for the eclipse project.
 */
public class ModInfo extends CustomFile {

    public ModInfo() {
        super("modinfo");
    }

    @Override
    public void loadFile() {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(
                    getFile()));
            String line;
            while ((line = reader.readLine()) != null) {
                final String[] arguments = line.split(":");
                if (arguments.length == 3) {
                    final Module mod = Uzi.getInstance().getModuleManager().find(arguments[0]);
                    if (mod != null) {
                        if (Boolean.parseBoolean(arguments[1])) {
                            mod.setEnabled(true);
                        }
                        mod.setBind(Keyboard.getKeyIndex(arguments[2]
                                .toUpperCase()));
                    }
                }
            }
            reader.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveFile() {
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(
                    getFile()));
            for (final Module mod : Uzi.getInstance().getModuleManager().getContents()) {
                writer.write(mod.getName().toLowerCase() + ":"
                        + mod.isEnabled() + ":"
                        + Keyboard.getKeyName(mod.getBind()));
                writer.newLine();
            }
            writer.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
