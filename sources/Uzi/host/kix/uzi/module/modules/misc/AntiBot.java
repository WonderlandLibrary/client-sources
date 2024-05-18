package host.kix.uzi.module.modules.misc;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.Uzi;
import host.kix.uzi.events.RecievePacketEvent;
import host.kix.uzi.file.CustomFile;
import host.kix.uzi.utilities.value.Value;
import host.kix.uzi.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;

import java.io.*;

/**
 * Created by myche on 2/5/2017.
 */
public class AntiBot extends Module {

    public Value<Type> type = new Value<>("Type", Type.WATCHDOGS);

    public AntiBot() {
        super("AntiBot", 0, Category.MISC);
        add(type);
        Uzi.getInstance().getFileManager().addContent(new CustomFile("antibot") {
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
                                } else if (value.getValue() instanceof Type) {
                                    if (arguments[1].equalsIgnoreCase("watchdogs")) {
                                        value.setValue(Type.WATCHDOGS);
                                    } else if (arguments[1].equalsIgnoreCase("gwen")) {
                                        value.setValue(Type.GWEN);
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
    public void onPacket(RecievePacketEvent e) {
        switch (type.getValue()) {
            case WATCHDOGS:
                for (Object entity1 : mc.theWorld.loadedEntityList) {
                    if (((Entity) entity1).isInvisible() && entity1 != mc.thePlayer) {
                        mc.theWorld.loadedEntityList.remove((Entity) entity1);
                    }
                }
                break;
            case GWEN:
                if (e.getPacket() instanceof S0CPacketSpawnPlayer) {
                    S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer) e.getPacket();
                    double posX = (double) packet.func_148942_f() / 32.0D;
                    double posY = (double) packet.func_148949_g() / 32.0D;
                    double posZ = (double) packet.func_148946_h() / 32.0D;

                    double difX = mc.thePlayer.posX - posX;
                    double difY = mc.thePlayer.posY - posY;
                    double difZ = mc.thePlayer.posZ - posZ;

                    double dist = Math.sqrt(difX * difX + difY * difY + difZ * difZ);

                    if (dist <= 17 && posX != mc.thePlayer.posX && posY != mc.thePlayer.posY && posZ != mc.thePlayer.posZ) {
                        e.setCancelled(true);
                    }
                }
                break;
        }
    }

    private enum Type {
        WATCHDOGS, GWEN
    }

}
