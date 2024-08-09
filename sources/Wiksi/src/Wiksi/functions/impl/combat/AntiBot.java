package src.Wiksi.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import com.mojang.authlib.GameProfile;
import src.Wiksi.events.EventPacket;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import io.netty.util.internal.ConcurrentSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SPlayerListItemPacket;
import net.minecraft.network.play.server.SPlayerListItemPacket.Action;

@FunctionRegister(name = "AntiBot", type = Category.Combat)
public class AntiBot extends Function {
    private final Set<UUID> susPlayers = new ConcurrentSet();
    private static final Map<UUID, Boolean> botsMap = new HashMap();
    private static boolean isActive = false;

    public AntiBot() {
    }

    @Subscribe
    private void onUpdate(EventUpdate var1) {
        if (isActive) {
            Iterator player = this.susPlayers.iterator();

            while(player.hasNext()) {
                UUID var3 = (UUID)player.next();
                PlayerEntity var4 = mc.world.getPlayerByUuid(var3);
                if (var4 != null) {
                    Iterator var5 = var4.getArmorInventoryList().iterator();
                    int var6 = 0;

                    while(var5.hasNext()) {
                        ItemStack var7 = (ItemStack)var5.next();
                        if (!var7.isEmpty()) {
                            ++var6;
                        }
                    }

                    boolean var11 = var6 == 4;
                    var6 = 0;
                    Iterator var8 = mc.player.connection.getPlayerInfoMap().iterator();

                    while(var8.hasNext()) {
                        NetworkPlayerInfo var9 = (NetworkPlayerInfo)var8.next();
                        GameProfile var10 = var9.getGameProfile();
                        if (var4.getGameProfile().getName().equals(var10.getName())) {
                            ++var6;
                        }
                    }

                    boolean var12 = var11 || !var4.getUniqueID().equals(PlayerEntity.getOfflineUUID(var4.getGameProfile().getName()));
                    botsMap.put(var3, var12);
                }

                this.susPlayers.remove(var3);
            }

            if (mc.player.ticksExisted % 100 == 0) {
                botsMap.keySet().removeIf((var0) -> {
                    return mc.world.getPlayerByUuid(var0) == null;
                });
            }

        }
    }

    @Subscribe
    private void onPacket(EventPacket var1) {
        if (isActive) {
            IPacket var3 = var1.getPacket();
            if (var3 instanceof SPlayerListItemPacket) {
                SPlayerListItemPacket var2 = (SPlayerListItemPacket)var3;
                if (var2.getAction() == Action.ADD_PLAYER) {
                    Iterator var7 = var2.getEntries().iterator();

                    while(true) {
                        SPlayerListItemPacket.AddPlayerData var4;
                        GameProfile var5;
                        do {
                            do {
                                if (!var7.hasNext()) {
                                    return;
                                }

                                var4 = (SPlayerListItemPacket.AddPlayerData)var7.next();
                                var5 = var4.getProfile();
                            } while(botsMap.containsKey(var5.getId()));
                        } while(this.susPlayers.contains(var5.getId()));

                        boolean var6 = var5.getProperties().isEmpty() && var4.getPing() != 0;
                        if (var6) {
                            this.susPlayers.add(var5.getId());
                        }
                    }
                }
            }

        }
    }

    public static boolean isBot(Entity var0) {
        return isActive && var0 instanceof PlayerEntity && checkBotConditions((PlayerEntity)var0);
    }

    public static boolean isBotU(Entity var0) {
        return isActive && !var0.getUniqueID().equals(PlayerEntity.getOfflineUUID(var0.getName().getString())) && var0.isInvisible();
    }

    private static boolean checkBotConditions(PlayerEntity var0) {
        String var1 = var0.getName().getString();
        boolean var2 = Pattern.compile("[а-яА-Я]").matcher(var1).find();
        boolean var3 = var1.length() < 3;
        return (Boolean)botsMap.getOrDefault(var0.getUniqueID(), false) || var1.contains("-") || var1.contains("anarchy") || var1.contains("grief") || var1.contains("[") || var1.contains("battlepass") || var1.contains("effecter") || var1.contains("+") || var2 || var3;
    }

    public void onEnable() {
        super.onEnable();
        isActive = true;
    }

    public void onDisable() {
        super.onDisable();
        isActive = false;
        botsMap.clear();
    }
}
