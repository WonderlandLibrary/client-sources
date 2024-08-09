package mathax.client.systems.hud.modules;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.packets.PacketEvent;
import mathax.client.systems.hud.DoubleTextHudElement;
import mathax.client.systems.hud.HUD;


public class LastPacket extends DoubleTextHudElement {
    long timeLastPacket;

    public LastPacket(HUD hud) {
        super(hud,"LastPacket" , "Time since last packet", false);
    }

    @Override
    protected String getLeft() {
        return "Time since last packet: ";
    }

    @Override
    protected String getRight() {
        if (isInEditor()){
            visible = true;
            return "50ms";
        }
        visible = System.currentTimeMillis() - timeLastPacket >= 50;
        return System.currentTimeMillis() - timeLastPacket + "ms";
    }

    @EventHandler
    private void packet(PacketEvent.Receive event) {
            timeLastPacket = System.currentTimeMillis();
    }
}
