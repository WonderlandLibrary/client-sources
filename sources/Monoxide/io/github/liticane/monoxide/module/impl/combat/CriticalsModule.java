package io.github.liticane.monoxide.module.impl.combat;

import io.github.liticane.monoxide.listener.radbus.Listen;
import net.minecraft.network.play.client.C03PacketPlayer;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.listener.event.minecraft.player.combat.AttackEntityEvent;
import io.github.liticane.monoxide.util.interfaces.Methods;

@ModuleData(name = "Criticals", description = "Always gets you critical hits", category = ModuleCategory.COMBAT)
public class CriticalsModule extends Module {

    public ModeValue mode = new ModeValue("Mode", this, new String[]{"Packet", "Polar", "AAC v5.0.4", "AAC v5", "NCP Latest", "Vulcan"});

    @Override
    public String getSuffix() {
    	return mode.getValue();
    }
    
    private int attacked = 0;

    //We love skidded offsets
    @Listen
    public final void onAttack(AttackEntityEvent attackEntityEvent) {
        attacked++;
        switch (mode.getValue()) {
            case "Vulcan":
                if (attacked > 7) {
                    sendPositionPacket(0.16477328182606651, false);
                    sendPositionPacket(0.08307781780646721, false);
                    sendPositionPacket(0.0030162615090425808, false);
                    attacked = 0;
                }
                break;
            case "NCP Latest":
                if (attacked >= 5) {
                    sendPositionPacket(0.00001058293536, false);
                    sendPositionPacket(0.00000916580235, false);
                    sendPositionPacket(0.00000010371854, false);
                    attacked = 0;
                }
                break;
            case "AAC v5":
                sendPositionPacket(0.0625, false);
                sendPositionPacket(0.0433, false);
                sendPositionPacket(0.2088, false);
                sendPositionPacket(0.9963, false);
                break;
            case "AAC v5.0.4":
                sendPositionPacket(0.00133545, false);
                sendPositionPacket(-0.000000433, false);
                break;
            case "Packet":
                sendPositionPacket(0.0625, true);
                sendPositionPacket(false);
                sendPositionPacket(1.1E-5, false);
                sendPositionPacket(false);
                break;
        }
    }

    public void sendPositionPacket(boolean ground) {
        this.sendPositionPacket(0, 0, 0, ground);
    }

    public void sendPositionPacket(double yOffset, boolean ground) {
        this.sendPositionPacket(0, yOffset, 0, ground);
    }

    public void sendPositionPacket(double xOffset, double yOffset, double zOffset, boolean ground) {
        double x = Methods.mc.thePlayer.posX + xOffset;
        double y = Methods.mc.thePlayer.posY + yOffset;
        double z = Methods.mc.thePlayer.posZ + zOffset;
        Methods.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, ground));
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

}
