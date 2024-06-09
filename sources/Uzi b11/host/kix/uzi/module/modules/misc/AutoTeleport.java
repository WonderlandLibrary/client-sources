package host.kix.uzi.module.modules.misc;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.Uzi;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.file.CustomFile;
import host.kix.uzi.module.Module;
import host.kix.uzi.utilities.minecraft.Stopwatch;
import host.kix.uzi.utilities.value.Value;

import java.io.*;

/**
 * Created by Kix on 5/31/2017.
 * Made for the eclipse project.
 */
public class AutoTeleport extends Module {

    private Value<String> username = new Value<String>("Username", "");
    private Stopwatch stopwatch = new Stopwatch();

    /**
     * I automated boosting money on minetime :/
     */
    public AutoTeleport() {
        super("AutoTeleport", 0, Category.MISC);
        add(username);
        Uzi.getInstance().getFileManager().addContent(new CustomFile("autoteleport") {
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
        if (stopwatch.hasCompleted(2000)) {
            mc.thePlayer.sendChatMessage("/tpa " + username.getValue());
            stopwatch.reset();
        }
        if (stopwatch.hasCompleted(16000)){
            mc.thePlayer.sendChatMessage("a");
            stopwatch.reset();
        }
    }

}
