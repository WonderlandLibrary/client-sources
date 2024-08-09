package mathax.client.systems.modules.combat;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.entity.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;

/*/--------------------------------------------------------------------------------------------------------------/*/
/*/ Based on Meteor Rejects                                                                                     /*/
/*/ https://github.com/AntiCope/meteor-rejects/blob/master/src/main/java/anticope/rejects/modules/AntiBot.java    /*/
/*/--------------------------------------------------------------------------------------------------------------/*/

public class BidenHatesBots extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgFilters = settings.createGroup("Filters");

    private final Setting<Boolean> removeInvisible = sgGeneral.add(new BoolSetting.Builder()
        .name("remove-invisible")
        .description("Removes bot only if they are invisible.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> gameMode = sgFilters.add(new BoolSetting.Builder()
        .name("null-gamemode")
        .description("Removes players without a gamemode")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> api = sgFilters.add(new BoolSetting.Builder()
        .name("null-entry")
        .description("Removes players without a player entry")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> profile = sgFilters.add(new BoolSetting.Builder()
        .name("null-profile")
        .description("Removes players without a game profile")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> latency = sgFilters.add(new BoolSetting.Builder()
        .name("ping-check")
        .description("Removes players using ping check")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> nullException = sgFilters.add(new BoolSetting.Builder()
        .name("null-exception")
        .description("Removes players if a NullPointerException occurred")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> notInTabList = sgFilters.add(new BoolSetting.Builder()
        .name("not-in-tab-list")
        .description("Removes players not listed in the tab list")
        .defaultValue(false)
        .build()
    );

    public BidenHatesBots() {
        super(Categories.Player, Items.BARRIER, "biden-hates-bots", "An Antibot, please use this in combination with the BidenAura antibot setting to bypass all KillAura bots.");
    }

    @EventHandler
    public void onTick(TickEvent.Post tickEvent) {
        for (Entity entity : mc.world.getEntities()) {
            if (entity == null || !(entity instanceof PlayerEntity playerEntity)) continue;
            if (removeInvisible.get() && !entity.isInvisible()) continue;

            if (isBot(playerEntity)) entity.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    private boolean isBot(PlayerEntity entity) {
        try {
            if (gameMode.get() && EntityUtils.getGameMode(entity) == null) return true;
            if (api.get() &&
                mc.getNetworkHandler().getPlayerListEntry(entity.getUuid()) == null) return true;
            if (profile.get() &&
                mc.getNetworkHandler().getPlayerListEntry(entity.getUuid()).getProfile() == null) return true;
            if (latency.get() &&
                mc.getNetworkHandler().getPlayerListEntry(entity.getUuid()).getLatency() > 1) return true;
            if (notInTabList.get() && mc.getNetworkHandler().getPlayerListEntry(entity.getUuid()) == null) return true;
        } catch (NullPointerException e) {
            if (nullException.get()) return true;
        }

        return false;
    }
}
