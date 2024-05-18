package host.kix.uzi.module.modules.movement;

import com.darkmagician6.eventapi.SubscribeEvent;


import com.darkmagician6.eventapi.types.EventType;
import host.kix.uzi.Uzi;
import host.kix.uzi.events.RecievePacketEvent;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.file.CustomFile;
import host.kix.uzi.module.Module;
import host.kix.uzi.utilities.value.Value;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

import java.io.*;

/**
 * Created by myche on 2/5/2017.
 */
public class Flight extends Module {

    private Value<Mode> mode = new Value<Mode>("Mode", Mode.CREATIVE);

    public Flight() {
        super("Flight", 0, Category.MOVEMENT);
        add(mode);
        Uzi.getInstance().getFileManager().addContent(new CustomFile("flight") {
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
                                    if (arguments[1].equalsIgnoreCase("creative")) {
                                        value.setValue(Mode.CREATIVE);
                                    } else if (arguments[1].equalsIgnoreCase("packet")) {
                                        value.setValue(Mode.PACKET);
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
    public void onUpdate(UpdateEvent e) {
        switch (mode.getValue()) {
            case CREATIVE:
                if (isEnabled()) {
                    mc.thePlayer.capabilities.isFlying = true;
                }
                break;
            case PACKET:
                if (e.type == EventType.PRE) {
                    mc.thePlayer.motionY = 0;
                    if (mc.thePlayer.moveStrafing != 0 || mc.thePlayer.moveForward != 0) {
                        mc.thePlayer.setSpeed(0.26);
                    }
                    mc.getNetHandler().addToSendQueue(
                            new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + mc.thePlayer.motionX,
                                    mc.thePlayer.posY + (mc.gameSettings.keyBindJump.getIsKeyPressed() ? .0625
                                            : mc.gameSettings.keyBindSneak.getIsKeyPressed() ? -0.0625 : 0),
                                    mc.thePlayer.posZ + mc.thePlayer.motionZ, false));

                    mc.getNetHandler().addToSendQueue(
                            new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + mc.thePlayer.motionX,
                                    mc.theWorld.getHeight(), mc.thePlayer.posZ + mc.thePlayer.motionZ, true));
                }
                break;
        }
    }

    @SubscribeEvent
    private void onPacketReceive(final RecievePacketEvent event) {
        if (this.mode.getValue() == Mode.PACKET) {
            if (event.getPacket() instanceof S08PacketPlayerPosLook) {
                final S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.getPacket();
                packet.field_148936_d = mc.thePlayer.rotationYaw;
                packet.field_148937_e = mc.thePlayer.rotationPitch;
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.thePlayer.capabilities.isFlying = false;
    }

    private enum Mode {
        CREATIVE, PACKET
    }

}
