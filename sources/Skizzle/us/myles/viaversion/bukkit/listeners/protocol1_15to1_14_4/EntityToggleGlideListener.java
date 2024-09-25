/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.entity.EntityToggleGlideEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffectType
 */
package us.myles.ViaVersion.bukkit.listeners.protocol1_15to1_14_4;

import java.util.Arrays;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import us.myles.ViaVersion.ViaVersionPlugin;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_14;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.version.Types1_14;
import us.myles.ViaVersion.bukkit.listeners.ViaBukkitListener;
import us.myles.ViaVersion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;

public class EntityToggleGlideListener
extends ViaBukkitListener {
    private boolean swimmingMethodExists;

    public EntityToggleGlideListener(ViaVersionPlugin plugin) {
        super((Plugin)plugin, Protocol1_15To1_14_4.class);
        try {
            Player.class.getMethod("isSwimming", new Class[0]);
            this.swimmingMethodExists = true;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            // empty catch block
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void entityToggleGlide(EntityToggleGlideEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player)event.getEntity();
        if (!this.isOnPipe(player)) {
            return;
        }
        if (event.isGliding() && event.isCancelled()) {
            PacketWrapper packet = new PacketWrapper(68, null, this.getUserConnection(player));
            try {
                packet.write(Type.VAR_INT, player.getEntityId());
                byte bitmask = 0;
                if (player.getFireTicks() > 0) {
                    bitmask = (byte)(bitmask | true ? 1 : 0);
                }
                if (player.isSneaking()) {
                    bitmask = (byte)(bitmask | 2);
                }
                if (player.isSprinting()) {
                    bitmask = (byte)(bitmask | 8);
                }
                if (this.swimmingMethodExists && player.isSwimming()) {
                    bitmask = (byte)(bitmask | 0x10);
                }
                if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    bitmask = (byte)(bitmask | 0x20);
                }
                if (player.isGlowing()) {
                    bitmask = (byte)(bitmask | 0x40);
                }
                packet.write(Types1_14.METADATA_LIST, Arrays.asList(new Metadata(0, MetaType1_14.Byte, bitmask)));
                packet.send(Protocol1_15To1_14_4.class);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

