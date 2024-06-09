package host.kix.uzi.module.modules.combat;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.Uzi;
import host.kix.uzi.events.AttackEvent;
import host.kix.uzi.file.CustomFile;
import host.kix.uzi.module.Module;
import host.kix.uzi.utilities.value.Value;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.io.*;

/**
 * Created by myche on 2/5/2017.
 */
public class Criticals extends Module {

    private Value<Mode> mode = new Value<Mode>("Mode", Mode.QUAD);

    public Criticals() {
        super("Criticals", 0, Category.COMBAT);
        add(mode);
        Uzi.getInstance().getFileManager().addContent(new CustomFile("criticals") {
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
                                    if (arguments[1].equalsIgnoreCase("quad")) {
                                        value.setValue(Mode.QUAD);
                                    } else if (arguments[1].equalsIgnoreCase("jump")) {
                                        value.setValue(Mode.JUMP);
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

    private void crit() {
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.05D, this.mc.thePlayer.posZ, false));
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.012511D, this.mc.thePlayer.posZ, false));
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
    }

    @SubscribeEvent
    public void onAttack(AttackEvent e) {
        switch (mode.getValue()) {
            case QUAD:
                crit();
                break;
            case JUMP:
                mc.thePlayer.boundingBox.offsetAndUpdate(0, .234, 0);
                break;
        }
    }

    private enum Mode {
        JUMP, QUAD
    }

}
