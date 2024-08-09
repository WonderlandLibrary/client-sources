package xyz.mathax.mathaxclient.utils.network.capes;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.systems.config.Config;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.json.JSONObject;
import xyz.mathax.mathaxclient.utils.network.Executor;
import xyz.mathax.mathaxclient.utils.network.api.Api;

import java.util.*;

public class Capes {
    private static final Map<String, Cape> CAPES = new HashMap<>();

    private static final List<CapeOwner> OWNERS = new ArrayList<>();

    public static final List<Cape> TO_REGISTER = new ArrayList<>();
    public static final List<Cape> TO_RETRY = new ArrayList<>();

    private static final List<Cape> TO_REMOVE = new ArrayList<>();

    private static int timer = 0;

    public static void update(boolean enable) {
        clear();

        if (enable) {
            refresh();
        }
    }

    public static void update() {
        update(Config.get().capesSetting.get());
    }

    private static void refresh() {
        timer = 0;

        Executor.execute(() -> {
            JSONObject json = Api.getCapes();
            if (json == null) {
                return;
            }

            if (!json.has("capes") || !JSONUtils.isValidJSONArray(json, "capes") || !json.has("players") || !JSONUtils.isValidJSONArray(json, "players")) {
                return;
            }

            for (Object object : json.getJSONArray("capes")) {
                if (!(object instanceof JSONObject capeJson)) {
                    continue;
                }

                if (capeJson.has("name") && capeJson.has("url")) {
                    String name = capeJson.getString("name");
                    if (!CAPES.containsKey(name)) {
                        CAPES.put(name, new Cape(name, capeJson.getString("url")));
                    }
                }
            }

            for (Object object : json.getJSONArray("players")) {
                if (!(object instanceof JSONObject capeOwnersJson)) {
                    continue;
                }

                if (capeOwnersJson.has("cape") && capeOwnersJson.has("uuids") && JSONUtils.isValidJSONArray(capeOwnersJson, "uuids")) {
                    String capeName = capeOwnersJson.getString("cape");
                    if (!CAPES.containsKey(capeName)) {
                        continue;
                    }

                    for (Object object2 : capeOwnersJson.getJSONArray("uuids")) {
                        if (!(object2 instanceof String stringUuid)) {
                            continue;
                        }

                        OWNERS.add(new CapeOwner(UUID.fromString(stringUuid), CAPES.get(capeName)));
                    }
                }
            }
        });

        MatHax.EVENT_BUS.subscribe(Capes.class);
    }

    private static void clear() {
        CAPES.clear();
        OWNERS.clear();
        TO_REGISTER.clear();
        TO_RETRY.clear();
        TO_REMOVE.clear();
    }

    @EventHandler
    private static void onTick(TickEvent.Post event) {
        synchronized (TO_REGISTER) {
            for (Cape cape : TO_REGISTER) {
                cape.register();
            }

            TO_REGISTER.clear();
        }

        synchronized (TO_RETRY) {
            TO_RETRY.removeIf(Cape::tick);
        }

        synchronized (TO_REMOVE) {
            for (Cape cape : TO_REMOVE) {
                CAPES.remove(cape.name, cape);
                TO_REGISTER.remove(cape);
                TO_RETRY.remove(cape);
            }

            TO_REMOVE.clear();
        }
    }

    @EventHandler
    private static void onTick(TickEvent.Pre event) {
        if (Config.get().capesAutoReloadDelaySetting.get() == -1) {
            return;
        }

        if (timer >= Config.get().capesAutoReloadDelaySetting.get()) {
            timer = 0;

            Capes.update();
        }

        timer++;
    }

    public static Cape getCape(String name) {
        return CAPES.get(name);
    }

    public static Identifier get(PlayerEntity player) {
        Cape cape = null;
        for (CapeOwner capeOwner : OWNERS) {
            if (!capeOwner.uuid.equals(player.getGameProfile().getId())) {
                continue;
            }

            cape = capeOwner.cape;
        }

        if (cape == null || !CAPES.containsKey(cape.name)) {
            return null;
        }

        if (cape.isDownloaded()) {
            return cape;
        }

        cape.download();

        return null;
    }

    public static boolean selectPlayerCape(UUID uuid, Cape cape) {
        for (CapeOwner capeOwner : OWNERS) {
            if (!capeOwner.uuid.equals(uuid)) {
                continue;
            }

            capeOwner.cape = cape;

            return true;
        }

        return false;
    }

    public static Cape getPlayerCape(UUID uuid) {
        for (CapeOwner capeOwner : OWNERS) {
            if (!capeOwner.uuid.equals(uuid)) {
                continue;
            }

            return capeOwner.cape;
        }

        return null;
    }

    public static Cape getPlayerCape(PlayerEntity player) {
        return getPlayerCape(player.getGameProfile().getId());
    }
}
