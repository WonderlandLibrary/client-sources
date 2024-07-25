package club.bluezenith.module.modules.misc.hackerdetector;

import club.bluezenith.module.modules.misc.HackerDetector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.minecraft.client.Minecraft.getMinecraft;

public class PlayerWatcher {
    private final HackerDetector hackerDetector;
    private final Map<UUID, PlayerInfo> playerRegistry = new HashMap<>();

    public PlayerWatcher(HackerDetector hackerDetector) {
        this.hackerDetector = hackerDetector;
    }

    public PlayerInfo getPlayerOrNull(UUID uuid) {
        return playerRegistry.get(uuid);
    }

    public PlayerInfo getPlayerByIdOrNull(int entityId) {
        final Entity entity = getMinecraft().theWorld.getEntityByID(entityId);
        if(!(entity instanceof EntityPlayer)) return null;

        return playerRegistry.get(entity.getUniqueID());
    }

    public void upsertPlayer(EntityPlayer player) {
        if(player == getMinecraft().thePlayer) return;
        if(!playerRegistry.containsKey(player.getUniqueID()))
        playerRegistry.put(player.getUniqueID(), new PlayerInfo(player));
    }

    public void update() {
        for (PlayerInfo value : playerRegistry.values()) {
            value.updatePlayerEntity();
        }
    }

    public void evictNonExistentPlayers() {
        playerRegistry.keySet().removeIf(uuid -> getMinecraft().theWorld.getPlayerEntityByUUID(uuid) == null);
    }

    public void clearRegistry() {
        playerRegistry.clear();
    }
}
