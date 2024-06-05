package gq.vapu.czfclient.Module.Modules.Misc;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;


public class AntiFire extends Module {

    public AntiFire() {
        super("AntiFire", new String[]{"antifire"}, ModuleType.Player);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventHandler
    public void onTick() {
        if (!mc.thePlayer.capabilities.isCreativeMode && mc.thePlayer.onGround && mc.thePlayer.isBurning()) {
            for (int i = 0; i < 100; i++) {
//                sendPacket(new C03PacketPlayer(true));
//                addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(1.7e+301,-999.0,0.0,true);
                Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
            }
        }
    }

}
