package net.shoreline.client.impl.module.render;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRemoveS2CPacket;
import net.minecraft.util.math.Box;
import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.api.render.RenderManager;
import net.shoreline.client.api.waypoint.Waypoint;
import net.shoreline.client.impl.event.network.PacketEvent;
import net.shoreline.client.impl.event.render.RenderWorldEvent;
import net.shoreline.client.impl.event.world.RemoveEntityEvent;
import net.shoreline.client.init.Managers;
import net.shoreline.client.init.Modules;

import java.util.UUID;

/**
 * @author linus
 * @since 1.0
 */
public class WaypointsModule extends ToggleModule {

    Config<Boolean> logoutsConfig = new BooleanConfig("LogoutPoints", "Marks the position of player logouts", false);
    Config<Boolean> deathsConfig = new BooleanConfig("DeathPoints", "Marks the position of player deaths", false);

    public WaypointsModule() {
        super("Waypoints", "Renders a waypoint at marked locations",
                ModuleCategory.RENDER);
    }

    @EventListener
    public void onPacketInbound(PacketEvent.Inbound event) {
        if (mc.world == null) {
            return;
        }
        if (event.getPacket() instanceof PlayerListS2CPacket packet
                && logoutsConfig.getValue()) {
            for (PlayerListS2CPacket.Entry entry : packet.getEntries()) {
                GameProfile profile = entry.profile();
                if (profile.getName() == null || profile.getName().isEmpty()
                        || profile.getId() == null) {
                    continue;
                }
                PlayerEntity player = mc.world.getPlayerByUuid(profile.getId());
                if (player != null) {
                    if (packet.getActions().contains(PlayerListS2CPacket.Action.ADD_PLAYER)) {
                        Managers.WAYPOINT.remove(String.format("%s's Logout", player.getName().getString()));
                    }
                }
            }
        } else if (event.getPacket() instanceof PlayerRemoveS2CPacket packet
                && logoutsConfig.getValue() && mc.player.getServer() != null) {
            String ip = mc.player.getServer().getServerIp();
            String serverIp = mc.isInSingleplayer() ? "Singleplayer" : ip;
            for (UUID id : packet.profileIds()) {
                PlayerEntity player = mc.world.getPlayerByUuid(id);
                if (player != null) {
                    Managers.WAYPOINT.register(new Waypoint(String.format("%s's Logout",
                            player.getName().getString()), serverIp, player.prevX, player.prevY, player.prevZ));
                }
            }
        }
    }

    @EventListener
    public void onRemoveEntity(RemoveEntityEvent event) {
        if (event.getRemovalReason() == Entity.RemovalReason.KILLED &&
                event.getEntity() == mc.player && deathsConfig.getValue()) {
            String serverIp = mc.isInSingleplayer() ? "Singleplayer" : mc.world.getServer().getServerIp();
            Managers.WAYPOINT.remove("Last Death");
            Managers.WAYPOINT.register(new Waypoint("Last Death", serverIp,
                    mc.player.lastX, mc.player.lastBaseY, mc.player.lastZ));
        }
    }

    @EventListener
    public void onRenderWorld(RenderWorldEvent event) {
        if (mc.player == null) {
            return;
        }
        for (Waypoint waypoint : Managers.WAYPOINT.getWaypoints()) {
            Box waypointBox = PlayerEntity.STANDING_DIMENSIONS.getBoxAt(waypoint.getPos());
            RenderManager.renderBoundingBox(event.getMatrices(), waypointBox,
                    2.5f, Modules.COLORS.getRGB(145));
            RenderManager.renderSign(event.getMatrices(), waypoint.getName(),
                    waypointBox.getCenter().add(0.0, 1.0, 0.0));
        }
    }
}
