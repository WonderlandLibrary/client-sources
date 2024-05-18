package host.kix.uzi.ui.alt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import host.kix.uzi.Uzi;
import host.kix.uzi.file.CustomFile;
import host.kix.uzi.utilities.value.Value;

public class SavedAlts extends CustomFile {

    public SavedAlts() {
        super("alts");
    }

    @Override
    public void loadFile() {
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String split[] = line.split(":");
                if (split.length == 2) {
                    Uzi.getInstance().getAltManager().getAlts().add(new Alt(split[0], split[1]));
                } else if (split.length == 3) {
                    Uzi.getInstance().getAltManager().getAlts().add(new Alt(split[0], split[1], split[2]));
                }
            }
            reader.close();
        } catch (IOException e) {

        }
    }

    @Override
    public void saveFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            StringBuilder builder = new StringBuilder();
            for (Alt alt : Uzi.getInstance().getAltManager().getAlts()) {
                if (!alt.getDisplay().equals(alt.getUsername())) {
                    writer.write(alt.getDisplay() + ":" + alt.getUsername() + ":" + alt.getPassword());
                } else {
                    writer.write(alt.getUsername() + ":" + alt.getPassword());
                }
                writer.newLine();
            }
            writer.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
