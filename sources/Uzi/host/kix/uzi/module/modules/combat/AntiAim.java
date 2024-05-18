package host.kix.uzi.module.modules.combat;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.Uzi;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.file.CustomFile;
import host.kix.uzi.module.Module;
import host.kix.uzi.utilities.value.Value;

import java.io.*;

/**
 * Created by Kix on 5/30/2017.
 * Made for the eclipse project.
 */
public class AntiAim extends Module {

    private Value<Mode> mode = new Value<Mode>("Mode", Mode.FAKEDOWN);
    private float spin;

    public AntiAim() {
        super("AntiAim", 0, Category.COMBAT);
        add(mode);
        Uzi.getInstance().getFileManager().addContent(new CustomFile("antiaim") {
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
                                } else if (value.getValue() instanceof Mode) {
                                    if (arguments[1].equalsIgnoreCase("fakedown")) {
                                        value.setValue(Mode.FAKEDOWN);
                                    } else if (arguments[1].equalsIgnoreCase("jitter")) {
                                        value.setValue(Mode.JITTER);
                                    }
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
                    final BufferedWriter writer = new BufferedWriter(new FileWriter(
                            getFile()));
                    for (final Value val : getValues()) {
                        writer.write(val.getName().toLowerCase() + ":"
                                + val.getValue());
                        writer.newLine();
                    }
                    writer.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @SubscribeEvent
    public void updateGame(UpdateEvent event) {
        switch (mode.getValue()) {
            case FAKEDOWN:
                if (!mc.thePlayer.isSwingInProgress) {
                    event.setPitch(90);
                }
                break;
            case JITTER:
                if (!mc.thePlayer.isSwingInProgress) {
                    spin += 20;
                    if (spin > 180) {
                        spin = -180;
                    } else if (spin < -180) {
                        spin = 180;
                    }
                    event.setYaw(spin);
                }
                break;
        }
    }

    private enum Mode {
        FAKEDOWN, JITTER
    }

}
