package host.kix.uzi.module.modules.combat;

import host.kix.uzi.Uzi;
import host.kix.uzi.file.CustomFile;
import host.kix.uzi.module.Module;
import host.kix.uzi.utilities.value.Value;

import java.io.*;

/**
 * Created by Kix on 6/2/2017.
 * Made for the eclipse project.
 */
public class Hitboxes extends Module {

    public static Value<Float> size = new Value<Float>("Size", 0.2F, 0.1F, 5.0F);

    public Hitboxes() {
        super("Hitboxes", 0, Category.COMBAT);
        add(size);
        Uzi.getInstance().getFileManager().addContent(new CustomFile("hitboxes") {
            @Override
            public void loadFile() {
                try {
                    final BufferedReader reader = new BufferedReader(new FileReader(getFile()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        final String[] arguments = line.split(":");
                        if (arguments.length == 2) {
                            final Value value = findGivenValue(arguments[0]);
                            if (value != null) {
                                if (value.getValue() instanceof Boolean) {
                                    value.setValue(Boolean.parseBoolean(arguments[1]));
                                } else if (value.getValue() instanceof Integer) {
                                    value.setValue(Integer.parseInt(arguments[1]));
                                } else if (value.getValue() instanceof Double) {
                                    value.setValue(Double.parseDouble(arguments[1]));
                                } else if (value.getValue() instanceof Float) {
                                    value.setValue(Float.parseFloat(arguments[1]));
                                } else if (value.getValue() instanceof Long) {
                                    value.setValue(Long.parseLong(arguments[1]));
                                } else if (value.getValue() instanceof String) {
                                    value.setValue(arguments[1]);
                                }
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
                    final BufferedWriter writer = new BufferedWriter(new FileWriter(getFile()));
                    for (final Value val : getValues()) {
                        writer.write(val.getName().toLowerCase() + ":" + val.getValue());
                        writer.newLine();
                    }
                    writer.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
