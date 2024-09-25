/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.spongepowered.api.data.type.HandTypes
 *  org.spongepowered.api.entity.living.player.Player
 *  org.spongepowered.api.event.Listener
 *  org.spongepowered.api.event.action.InteractEvent
 *  org.spongepowered.api.event.entity.MoveEntityEvent$Teleport
 *  org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent
 *  org.spongepowered.api.event.filter.cause.Root
 *  org.spongepowered.api.event.item.inventory.ClickInventoryEvent
 *  org.spongepowered.api.event.network.ClientConnectionEvent$Join
 *  org.spongepowered.api.item.inventory.ItemStack
 *  org.spongepowered.api.item.inventory.ItemStackSnapshot
 *  org.spongepowered.api.item.inventory.transaction.SlotTransaction
 *  org.spongepowered.api.world.World
 */
package us.myles.ViaVersion.sponge.listeners.protocol1_9to1_8.sponge5;

import java.util.Optional;
import java.util.UUID;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.action.InteractEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.transaction.SlotTransaction;
import org.spongepowered.api.world.World;
import us.myles.ViaVersion.SpongePlugin;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.ArmorType;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import us.myles.ViaVersion.sponge.listeners.ViaSpongeListener;

public class Sponge5ArmorListener
extends ViaSpongeListener {
    private static final UUID ARMOR_ATTRIBUTE = UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150");

    public Sponge5ArmorListener(SpongePlugin plugin) {
        super(plugin, Protocol1_9To1_8.class);
    }

    public void sendArmorUpdate(Player player) {
        if (!this.isOnPipe(player.getUniqueId())) {
            return;
        }
        int armor = 0;
        armor += this.calculate(player.getHelmet());
        armor += this.calculate(player.getChestplate());
        armor += this.calculate(player.getLeggings());
        armor += this.calculate(player.getBoots());
        PacketWrapper wrapper = new PacketWrapper(75, null, this.getUserConnection(player.getUniqueId()));
        try {
            wrapper.write(Type.VAR_INT, this.getEntityId(player));
            wrapper.write(Type.INT, 1);
            wrapper.write(Type.STRING, "generic.armor");
            wrapper.write(Type.DOUBLE, 0.0);
            wrapper.write(Type.VAR_INT, 1);
            wrapper.write(Type.UUID, ARMOR_ATTRIBUTE);
            wrapper.write(Type.DOUBLE, Double.valueOf(armor));
            wrapper.write(Type.BYTE, (byte)0);
            wrapper.send(Protocol1_9To1_8.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int calculate(Optional<ItemStack> itemStack) {
        if (itemStack.isPresent()) {
            return ArmorType.findByType(itemStack.get().getItem().getType().getId()).getArmorPoints();
        }
        return 0;
    }

    @Listener
    public void onInventoryClick(ClickInventoryEvent e, @Root Player player) {
        for (SlotTransaction transaction : e.getTransactions()) {
            if (!ArmorType.isArmor(((ItemStackSnapshot)transaction.getFinal()).getType().getId()) && !ArmorType.isArmor(((ItemStackSnapshot)e.getCursorTransaction().getFinal()).getType().getId())) continue;
            this.sendDelayedArmorUpdate(player);
            break;
        }
    }

    @Listener
    public void onInteract(InteractEvent event, @Root Player player) {
        if (player.getItemInHand(HandTypes.MAIN_HAND).isPresent() && ArmorType.isArmor(((ItemStack)player.getItemInHand(HandTypes.MAIN_HAND).get()).getItem().getId())) {
            this.sendDelayedArmorUpdate(player);
        }
    }

    @Listener
    public void onJoin(ClientConnectionEvent.Join e) {
        this.sendArmorUpdate(e.getTargetEntity());
    }

    @Listener
    public void onRespawn(RespawnPlayerEvent e) {
        this.sendDelayedArmorUpdate(e.getTargetEntity());
    }

    @Listener
    public void onWorldChange(MoveEntityEvent.Teleport e) {
        if (!(e.getTargetEntity() instanceof Player)) {
            return;
        }
        if (!((World)e.getFromTransform().getExtent()).getUniqueId().equals(((World)e.getToTransform().getExtent()).getUniqueId())) {
            this.sendArmorUpdate((Player)e.getTargetEntity());
        }
    }

    public void sendDelayedArmorUpdate(final Player player) {
        if (!this.isOnPipe(player.getUniqueId())) {
            return;
        }
        Via.getPlatform().runSync(new Runnable(){

            @Override
            public void run() {
                Sponge5ArmorListener.this.sendArmorUpdate(player);
            }
        });
    }
}

