package io.github.liticane.monoxide.module.impl.combat;

import io.github.liticane.monoxide.component.ComponentManager;
import io.github.liticane.monoxide.listener.event.minecraft.network.PacketEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S41PacketServerDifficulty;
import net.minecraft.world.WorldSettings;
import io.github.liticane.monoxide.component.impl.EntityComponent;
import io.github.liticane.monoxide.component.impl.entity.IgnoreList;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateEvent;
import io.github.liticane.monoxide.util.interfaces.Methods;

import java.util.ArrayList;
import java.util.List;

@ModuleData(name = "AntiBot", description = "Blocks anticheat bots", category = ModuleCategory.COMBAT)
public class AntiBotModule extends Module implements IgnoreList {

    private final ArrayList<Entity> bots = new ArrayList<>();

    public final ModeValue mode = new ModeValue("Mode", this, new String[]{ "Watchdog", "Matrix", "Twerion"});

    private boolean wasAdded = false;
    private String name;

    public AntiBotModule() {
        ComponentManager.getInstance().getByClass(EntityComponent.class).addIgnoreList(this);
    }
    
    @Override
    public String getSuffix() {
    	return mode.getValue();
    }

    @Listen
    public final void onPacket(PacketEvent packetEvent) {
        if(Methods.mc.thePlayer == null || Methods.mc.theWorld == null) {
            return;
        }
        switch (mode.getValue()) {
            case "Matrix":
                Packet<?> packet = packetEvent.getPacket();

                if (packet instanceof S41PacketServerDifficulty) {
                    wasAdded = false;
                }

                if (packet instanceof S38PacketPlayerListItem) {
                    S38PacketPlayerListItem packetListItem = (S38PacketPlayerListItem) packet;
                    S38PacketPlayerListItem.AddPlayerData data = packetListItem.getPlayers().get(0);

                    if (data.getProfile() != null && data.getProfile().getName() != null) {
                        name = data.getProfile().getName();

                        if (!wasAdded) {
                            wasAdded = name.equals(Methods.mc.thePlayer.getCommandSenderName());
                        } else if (!Methods.mc.thePlayer.isSpectator() && !Methods.mc.thePlayer.capabilities.allowFlying &&
                                (data.getPing() != 0) &&
                                (data.getGameMode() != WorldSettings.GameType.NOT_SET)) {
                            packetEvent.setCancelled(true);
                        }
                    }
                }
                break;
        }
    }


    @Listen
    public final void onUpdate(UpdateEvent updateEvent) {
        switch (mode.getValue()) {
            case "Twerion":
                List<Entity> list = new ArrayList<>();
                Methods.mc.theWorld.loadedEntityList.forEach(entity -> {
                    if(entity instanceof EntityZombie) {
                        list.add(entity);
                    }
                });
                for(Entity entity : list)
                    Methods.mc.theWorld.removeEntity(entity);
                break;
            case "Watchdog":
                Methods.mc.theWorld.playerEntities.forEach(player -> {
                    final NetworkPlayerInfo info = Methods.mc.getNetHandler().getPlayerInfo(player.getUniqueID());
                    if (info == null) {
                        this.bots.add(player);
                    } else {
                        this.bots.remove(player);
                    }
                });
                break;
        }
    }

    @Override
    public boolean shouldSkipEntity(Entity entity) {
        if(!this.isEnabled())
            return false;
        return this.bots.contains(entity);
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {
        if(Methods.mc.thePlayer == null || Methods.mc.theWorld == null) {
            return;
        }
        this.bots.clear();
    }

}
