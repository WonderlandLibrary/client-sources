package me.superskidder.lune.modules.player;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventPacketReceive;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.utils.player.PlayerUtil;
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
