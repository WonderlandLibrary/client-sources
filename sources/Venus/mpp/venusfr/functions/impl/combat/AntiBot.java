/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import com.mojang.authlib.GameProfile;
import io.netty.util.internal.ConcurrentSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SPlayerListItemPacket;

@FunctionRegister(name="AntiBot", type=Category.Combat)
public class AntiBot
extends Function {
    private final Set<UUID> susPlayers = new ConcurrentSet<UUID>();
    private static final Map<UUID, Boolean> botsMap = new HashMap<UUID, Boolean>();

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        for (UUID uUID : this.susPlayers) {
            PlayerEntity playerEntity = AntiBot.mc.world.getPlayerByUuid(uUID);
            if (playerEntity != null) {
                Iterator<ItemStack> iterator2 = playerEntity.getArmorInventoryList().iterator();
                int n = 0;
                while (iterator2.hasNext()) {
                    ItemStack itemStack = iterator2.next();
                    if (itemStack.isEmpty()) continue;
                    ++n;
                }
                boolean bl = n == 4;
                n = 0;
                for (NetworkPlayerInfo networkPlayerInfo : AntiBot.mc.player.connection.getPlayerInfoMap()) {
                    GameProfile gameProfile = networkPlayerInfo.getGameProfile();
                    if (!playerEntity.getGameProfile().getName().equals(gameProfile.getName())) continue;
                    ++n;
                }
                boolean bl2 = bl || !playerEntity.getUniqueID().equals(PlayerEntity.getOfflineUUID(playerEntity.getGameProfile().getName()));
                botsMap.put(uUID, bl2);
            }
            this.susPlayers.remove(uUID);
        }
        if (AntiBot.mc.player.ticksExisted % 100 == 0) {
            botsMap.keySet().removeIf(AntiBot::lambda$onUpdate$0);
        }
    }

    @Subscribe
    private void onPacket(EventPacket eventPacket) {
        SPlayerListItemPacket sPlayerListItemPacket;
        IPacket<?> iPacket = eventPacket.getPacket();
        if (iPacket instanceof SPlayerListItemPacket && (sPlayerListItemPacket = (SPlayerListItemPacket)iPacket).getAction() == SPlayerListItemPacket.Action.ADD_PLAYER) {
            for (SPlayerListItemPacket.AddPlayerData addPlayerData : sPlayerListItemPacket.getEntries()) {
                boolean bl;
                GameProfile gameProfile = addPlayerData.getProfile();
                if (botsMap.containsKey(gameProfile.getId()) || this.susPlayers.contains(gameProfile.getId()) || !(bl = gameProfile.getProperties().isEmpty() && addPlayerData.getPing() != 0)) continue;
                this.susPlayers.add(gameProfile.getId());
            }
        }
    }

    public static boolean isBot(Entity entity2) {
        return entity2 instanceof PlayerEntity && botsMap.getOrDefault(entity2.getUniqueID(), false) != false;
    }

    public static boolean isBotU(Entity entity2) {
        if (!entity2.getUniqueID().equals(PlayerEntity.getOfflineUUID(entity2.getName().getString()))) {
            return entity2.isInvisible();
        }
        return true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        botsMap.clear();
    }

    private static boolean lambda$onUpdate$0(UUID uUID) {
        return AntiBot.mc.world.getPlayerByUuid(uUID) == null;
    }
}

