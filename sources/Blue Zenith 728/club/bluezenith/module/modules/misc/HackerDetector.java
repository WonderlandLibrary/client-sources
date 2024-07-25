package club.bluezenith.module.modules.misc;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.misc.hackerdetector.PacketHandler;
import club.bluezenith.module.modules.misc.hackerdetector.PlayerWatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S14PacketEntity;

public class HackerDetector extends Module {
    private final PlayerWatcher playerWatcher = new PlayerWatcher(this);
    private final PacketHandler packetHandler = new PacketHandler(this);

    public HackerDetector() {
        super("HackerDetector", ModuleCategory.MISC);
    }

    @Listener
    public void onPacket(PacketEvent event) {
        if(event.direction == EnumPacketDirection.CLIENTBOUND) return;
        final Packet<?> packet = event.packet;

        switch (packet.getPacketID()) {
            case 0xF14:
                getPacketHandler().onMovement((S14PacketEntity) packet);
            break;
        }
    }

    @Listener
    public void onTick(UpdatePlayerEvent event) {
        for (EntityPlayer playerEntity : mc.theWorld.playerEntities) {
            playerWatcher.upsertPlayer(playerEntity);
        }
        if(event.isPost())
        playerWatcher.evictNonExistentPlayers();
    }

    public PlayerWatcher getPlayerWatcher() {
        return this.playerWatcher;
    }

    public PacketHandler getPacketHandler() {
        return this.packetHandler;
    }
}
