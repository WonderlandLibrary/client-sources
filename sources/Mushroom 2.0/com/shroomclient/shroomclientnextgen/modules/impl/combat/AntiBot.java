package com.shroomclient.shroomclientnextgen.modules.impl.combat;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.ClientTickEvent;
import com.shroomclient.shroomclientnextgen.events.impl.WorldLoadEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import java.util.HashMap;
import lombok.Getter;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

@RegisterModule(
    name = "Anti Bot",
    uniqueId = "antibot",
    description = "Don't Attack Bots",
    category = ModuleCategory.Combat,
    enabledByDefault = true
)
public class AntiBot extends Module {

    @ConfigOption(
        name = "Tablist",
        description = "Checks If On Tablist",
        order = 1
    )
    public static Boolean tablist = true;

    @ConfigOption(
        name = "Invisible",
        description = "Checks If Invisible",
        order = 2
    )
    public static Boolean invisible = true;

    @ConfigOption(
        name = "NPC",
        description = "Checks  If They Are An NPC",
        order = 3
    )
    public static Boolean npc = true;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    public static final HashMap<Integer, EntityData> entityData =
        new HashMap<>();

    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        if (C.w() != null) {
            for (AbstractClientPlayerEntity player : C.w().getPlayers()) {
                EntityData data = entityData.get(player.getId());

                if (data == null) {
                    entityData.put(player.getId(), new EntityData(player));
                } else {
                    entityData.get(player.getId()).update();
                }
            }
        }
    }

    public static boolean isNPC(final Entity entity) {
        if (!(entity instanceof OtherClientPlayerEntity)) return false;
        if (entity.getDisplayName() == null) return true;
        if (entity.getDisplayName().getString().contains("[NPC]")) return true;

        return false;
    }

    @SubscribeEvent
    public void onWorldJoin(WorldLoadEvent event) {
        entityData.clear();
    }

    public static class EntityData {

        @Getter
        private int ticksInvisible;

        @Getter
        private int tabTicks;

        private final Entity entity;

        public EntityData(final Entity entity) {
            this.entity = entity;
            this.update();
        }

        public int getTicksExisted() {
            return this.entity.age;
        }

        public void update() {
            if (
                this.entity instanceof PlayerEntity &&
                C.mc.getNetworkHandler() != null &&
                C.mc
                        .getNetworkHandler()
                        .getPlayerListEntry(this.entity.getUuid()) !=
                    null
            ) {
                this.tabTicks++;
            }
            if (this.entity.isInvisible()) {
                this.ticksInvisible++;
            }
        }
    }
}
