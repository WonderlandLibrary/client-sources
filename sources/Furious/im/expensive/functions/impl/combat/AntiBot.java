package im.expensive.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import com.mojang.authlib.GameProfile;
import im.expensive.events.EventPacket;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPlayerListItemPacket;

import java.util.*;

@FunctionRegister(name = "AntiBot", type = Category.Combat)
public class AntiBot extends Function {

    private final Set<UUID> susPlayers = new ConcurrentSet<>();
    private static final Map<UUID, Boolean> botsMap = new HashMap<>();

    @Subscribe
    private void onUpdate(EventUpdate e) {
        for (UUID susPlayer : susPlayers) {
            PlayerEntity entity = mc.world.getPlayerByUuid(susPlayer);

            if (entity != null) {
                //LoggerUtils.print("da: " + entity.getGameProfile().getName());

                Iterator<ItemStack> armor = entity.getArmorInventoryList().iterator();

                int count = 0;

                while (armor.hasNext()) {
                    ItemStack current = armor.next();

                    if (!current.isEmpty()) {
                        count++;
                    }
                }

                boolean isFullArmor = count == 4;

                count = 0;

                for (NetworkPlayerInfo networkPlayerInfo : mc.player.connection.getPlayerInfoMap()) {
                    GameProfile profile = networkPlayerInfo.getGameProfile();

                    if (entity.getGameProfile().getName().equals(profile.getName())) {
                        count++;
                    }
                }

                boolean isBot = isFullArmor || !entity.getUniqueID().equals(PlayerEntity.getOfflineUUID(entity.getGameProfile().getName()));

                botsMap.put(susPlayer, isBot);
            }

            susPlayers.remove(susPlayer);
        }


        if (mc.player.ticksExisted % 100 == 0) {
            botsMap.keySet().removeIf(uuid -> mc.world.getPlayerByUuid(uuid) == null);
        }
    }

    @Subscribe
    private void onPacket(EventPacket e) {
        if (e.getPacket() instanceof SPlayerListItemPacket p) {
            if (p.getAction() == SPlayerListItemPacket.Action.ADD_PLAYER) {
                for (SPlayerListItemPacket.AddPlayerData entry : p.getEntries()) {
                    GameProfile profile = entry.getProfile();

                    if (botsMap.containsKey(profile.getId()) || susPlayers.contains(profile.getId())) {
                        continue;
                    }

                    boolean isInvalid = profile.getProperties().isEmpty() && entry.getPing() != 0;

                    if (isInvalid) {
                        susPlayers.add(profile.getId());
                    }
                }
            }
        }
    }

    public static boolean isBot(Entity entity) {
        return entity instanceof PlayerEntity && botsMap.getOrDefault(entity.getUniqueID(), false);
    }

    public static boolean isBotU(Entity entity) {
        if (!entity.getUniqueID().equals(PlayerEntity.getOfflineUUID(entity.getName().getString()))) {
            return entity.isInvisible();
        }
        return false;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        botsMap.clear();
    }
}
