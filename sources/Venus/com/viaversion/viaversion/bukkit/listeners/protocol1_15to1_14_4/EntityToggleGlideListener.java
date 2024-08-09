/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.entity.EntityToggleGlideEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffectType
 */
package com.viaversion.viaversion.bukkit.listeners.protocol1_15to1_14_4;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import java.util.Arrays;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

public class EntityToggleGlideListener
extends ViaBukkitListener {
    private boolean swimmingMethodExists;

    public EntityToggleGlideListener(ViaVersionPlugin viaVersionPlugin) {
        super((Plugin)viaVersionPlugin, Protocol1_15To1_14_4.class);
        try {
            Player.class.getMethod("isSwimming", new Class[0]);
            this.swimmingMethodExists = true;
        } catch (NoSuchMethodException noSuchMethodException) {
            // empty catch block
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void entityToggleGlide(EntityToggleGlideEvent entityToggleGlideEvent) {
        if (!(entityToggleGlideEvent.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player)entityToggleGlideEvent.getEntity();
        if (!this.isOnPipe(player)) {
            return;
        }
        if (entityToggleGlideEvent.isGliding() && entityToggleGlideEvent.isCancelled()) {
            PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_15.ENTITY_METADATA, null, this.getUserConnection(player));
            try {
                packetWrapper.write(Type.VAR_INT, player.getEntityId());
                byte by = 0;
                if (player.getFireTicks() > 0) {
                    by = (byte)(by | 1);
                }
                if (player.isSneaking()) {
                    by = (byte)(by | 2);
                }
                if (player.isSprinting()) {
                    by = (byte)(by | 8);
                }
                if (this.swimmingMethodExists && player.isSwimming()) {
                    by = (byte)(by | 0x10);
                }
                if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    by = (byte)(by | 0x20);
                }
                if (player.isGlowing()) {
                    by = (byte)(by | 0x40);
                }
                packetWrapper.write(Types1_14.METADATA_LIST, Arrays.asList(new Metadata(0, Types1_14.META_TYPES.byteType, by)));
                packetWrapper.scheduleSend(Protocol1_15To1_14_4.class);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}

