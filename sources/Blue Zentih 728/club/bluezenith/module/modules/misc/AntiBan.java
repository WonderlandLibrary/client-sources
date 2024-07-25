package club.bluezenith.module.modules.misc;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.MoveEvent;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.BooleanValue;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;

public class AntiBan extends Module {
    private final BooleanValue combatDisabler = new BooleanValue("Combat Disabler", false).setIndex(3);
    private final BooleanValue confirm = new BooleanValue("Cancel C0F", false).setIndex(1);
    private final BooleanValue keepalive = new BooleanValue("Cancel C00", false).setIndex(2);
    private final BooleanValue move = new BooleanValue("Movement Disabler", false).setIndex(4);

    public AntiBan() {
        super("AntiBan", ModuleCategory.MISC, "antiban");
    }

    @Listener
    public void onPacket(PacketEvent e) {
        if (confirm.get()) {
            if (e.packet instanceof C0FPacketConfirmTransaction) {
                e.cancel();
            }
        }
        if (keepalive.get()) {
            if (e.packet instanceof C00PacketKeepAlive) {
                e.cancel();
            }
        }
        if(combatDisabler.get()) {
           if(e.packet instanceof C02PacketUseEntity && ((C02PacketUseEntity)e.packet).getAction().equals(C02PacketUseEntity.Action.ATTACK))
               e.cancel();
        }
    }
    
    public void onEnable() {
        BlueZenith.getBlueZenith().getNotificationPublisher().postWarning(displayName, "Do not use AntiBan on Hypixel!", 3000);
    }

    @Listener
    public void bypass(MoveEvent event) {
        if(move.get())
        event.cancel();
    }
}


