package gq.vapu.czfclient.Module.Modules.Misc;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPacketReceive;
import gq.vapu.czfclient.API.Events.World.EventPacketSend;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;

import java.util.ArrayList;
import java.util.List;

public class TimerDisabler extends Module {

    private final List<C0FPacketConfirmTransaction> c0fs = new ArrayList<>();
    private final List<C00PacketKeepAlive> c00s = new ArrayList<>();

    public TimerDisabler() {
        super("Disabler", new String[]{}, ModuleType.Player);
    }

//    @EventHandler
//    public void onPacket(EventPacketSend e){
//        if(Speed.mode.getValue().equals(Speed.SpeedMode.Test) && mc.thePlayer.isMoving() && ModuleManager.getModuleByClass(Speed.class).isEnabled()){
////            Packet packet = e.getPacket();
////            if(e.getPacket() instanceof C03PacketPlayer){
////                C03PacketPlayer c03 = (C03PacketPlayer) packet;
////                //c03.y = c03.y + 0.001332;
////                c03.x = c03.x + 0.000334;
//            Random iq = new Random(100);
//            int idk = 12;
////                c03.z = c03.z + 0.000102;
////            }
//            if(e.getPacket() instanceof C03PacketPlayer && iq.nextBoolean()){
//                e.setCancelled(false);
//            }else
//                e.setCancelled(true);
//        }
//    }

    @Override
    public void onEnable() {
        if (mc.thePlayer == null)
            return;
        c0fs.clear();
        c00s.clear();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (mc.thePlayer == null)
            return;
        c0fs.clear();
        c00s.clear();
        super.onDisable();
    }

    @EventHandler
    public void onUpdate(EventPreUpdate e) {
        this.setSuffix("WatchDog");
    }

    @EventHandler
    private void onS00Rcv(EventPacketReceive event) {

    }

    @EventHandler
    private void onC00Send(EventPacketSend event) {
        Packet packet = EventPacketSend.getPacket();
        if (EventPacketSend.getPacket() instanceof C00PacketKeepAlive) {
            C00PacketKeepAlive c00 = (C00PacketKeepAlive) packet;
            event.setCancelled(true);
            c00s.add(c00);
            if (c00s.size() >= 5) {
                for (C00PacketKeepAlive pack : c00s) {
                    //this.mc.getConnection().sendPacketNoEvent(pack);
//                    Helper.sendMessage("¡ì4[Debug] ¡ìfSync?");
                    mc.thePlayer.sendQueue.addToSendQueue1(pack);
                }
                c00s.clear();
            }
        }
    }

    @EventHandler
    private void onC0FSend(EventPacketSend event) {
        Packet packet = EventPacketSend.getPacket();
        if (packet instanceof C0FPacketConfirmTransaction) {
            C0FPacketConfirmTransaction c0f = (C0FPacketConfirmTransaction) packet;
            if (c0f.getWindowId() == 0 && c0f.getUid() < 0) {
                event.setCancelled(true);
                c0fs.add(c0f);
            }
            if (c0fs.size() >= 6) {
                for (C0FPacketConfirmTransaction pack : c0fs) {
                    //this.mc.getConnection().sendPacketNoEvent(pack);
//                    Helper.sendMessage("¡ì4[Debug] ¡ìfHello?");
                    mc.thePlayer.sendQueue.addToSendQueue1(pack);
                }
                c0fs.clear();
            }
        }
    }

}
