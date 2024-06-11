package Hydro.module.modules.combat;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;

import java.util.ArrayList;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.event.Event;
import Hydro.event.events.EventMotion;
import Hydro.event.events.EventPacket;
import Hydro.module.Category;
import Hydro.module.Module;
import Hydro.util.MoveUtils;
import Hydro.util.Timer;

public class Criticals extends Module {

    public String mode;
    private final double[] watchdogOffsets = {0.056f, 0.016f, 0.003f};
    private final Timer timer = new Timer();
    private int groundTicks;

    public Criticals() {
        super("Criticals", 0, true, Category.COMBAT, "Makes you always hit criticals");
        ArrayList<String> options = new ArrayList<>();
        options.add("Redesky");
        Client.instance.settingsManager.rSetting(new Setting("CriticalsMode", "Mode", this, "Redesky", options));
    }

    @Override
    public void onEvent(Event e) {
        if(e instanceof EventMotion){
            if(e.isPre()){
                groundTicks = MoveUtils.isOnGround() ? groundTicks + 1 : 0;
            }
        }

        if(e instanceof EventPacket){
            if (((EventPacket) e).getPacket() instanceof C0APacketAnimation) {
                if (groundTicks > 1 && timer.hasReached(800L)) {
                    for (double offset : watchdogOffsets) {
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                                mc.thePlayer.posX, mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
                    }
                    timer.reset();
                }
            }
        }

    }

}
