package me.napoleon.napoline.modules.player;

import me.napoleon.napoline.events.EventPacketReceive;
import me.napoleon.napoline.manager.event.EventTarget;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;
import me.napoleon.napoline.utils.player.PlayerUtil;
import net.minecraft.network.play.server.S29PacketSoundEffect;

public class LightningCheck extends Mod {
    public LightningCheck() {
        super("LightningCheck", ModCategory.Player, "Check the Lightning");
    }

    @EventTarget
    public void onPacket(EventPacketReceive e) {
        if ((e.getPacket() instanceof S29PacketSoundEffect) && "ambient.weather.thunder".equals(((S29PacketSoundEffect) e.getPacket()).getSoundName())) {
            PlayerUtil.sendMessage("LightningCheck on:" + ((S29PacketSoundEffect) e.getPacket()).getX() + " " + ((S29PacketSoundEffect) e.getPacket()).getY() + " " + ((S29PacketSoundEffect) e.getPacket()).getZ());
        }
    }
}
