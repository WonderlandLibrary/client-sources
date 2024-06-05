package koks.modules.impl.utilities;

import koks.event.Event;
import koks.event.impl.PacketEvent;
import koks.modules.Module;
import koks.utilities.value.values.ModeValue;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import org.omg.CORBA.INTERNAL;

import java.util.Random;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 16:39
 */
public class Disabler extends Module {

    public ModeValue<String> mode = new ModeValue<String>("Mode", "Hypixel", new String[]{"Hypixel"}, this);

    public Disabler() {
        super("Disabler", "You disable the anticheat", Category.UTILITIES);
        addValue(mode);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof PacketEvent) {
            if (mode.getSelectedMode().equalsIgnoreCase("Hypixel")) {
                if (((PacketEvent) event).getType() == PacketEvent.Type.RECIVE) {
                    if (Minecraft.getMinecraft().getCurrentServerData().serverIP.contains("hypixel") && Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().thePlayer != null) {

                        if (((PacketEvent) event).getPacket() instanceof S32PacketConfirmTransaction) {
                            final S32PacketConfirmTransaction s32PacketConfirmTransaction = (S32PacketConfirmTransaction) ((PacketEvent) event).getPacket();
                            if (s32PacketConfirmTransaction.getActionNumber() < 0) {
                                event.setCanceled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
