package me.superskidder.lune.modules.combat;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventPacketReceive;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.values.type.Mode;
import me.superskidder.lune.values.type.Num;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

/**
 * @description: 反击退
 * @author: Qian_Xia
 * @create: 2020-08-23 19:23
 **/
public class AntiKnockBack extends Mod {
    private Mode<Enum> mode = new Mode<>("Mode", AntiMode.values(), AntiMode.Cancel);
    private Num<Double> posX = new Num<Double>("X", 0.0, 0.0, 100.0);
    private Num<Double> posY = new Num<Double>("Y", 0.0, 0.0, 100.0);
    private Num<Double> posZ = new Num<Double>("Z", 0.0, 0.0, 100.0);

    public AntiKnockBack() {
        super("AntiKnockBack", ModCategory.Combat, "Change your velocity");
        this.addValues(mode, posX, posY, posZ);
        this.setDescription("Change your velocity");
    }

    @EventTarget
    public void onPacketReceive(EventPacketReceive event) {
        this.setDisplayName(this.mode.getModeAsString());

        Packet packet = event.getPacket();

        switch (this.mode.getModeAsString()) {
            case "Cancel":
                if (packet instanceof S12PacketEntityVelocity || packet instanceof S27PacketExplosion) {
                    event.setCancelled(true);
                }
                break;
            case "Custom":
                if (packet instanceof S12PacketEntityVelocity) {
                    S12PacketEntityVelocity p = (S12PacketEntityVelocity) packet;
                        ((S12PacketEntityVelocity) packet).motionX *= (int) (this.posX.getValue() / 100.0);
                        ((S12PacketEntityVelocity) packet).motionY *= (int) (this.posY.getValue() / 100.0);
                        ((S12PacketEntityVelocity) packet).motionZ *= (int) (this.posZ.getValue() / 100.0);

//                    ((S12PacketEntityVelocity) packet).motionX
                }
                break;
            default:
                break;
        }
    }

    enum AntiMode {
        // 直接不处理数据包
        Cancel,
        // 自定义
        Custom
    }
}
