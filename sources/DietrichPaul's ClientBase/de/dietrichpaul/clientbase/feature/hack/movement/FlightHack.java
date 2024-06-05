/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature.hack.movement;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.network.ReceivePacketListener;
import de.dietrichpaul.clientbase.event.network.SendPacketListener;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.feature.hack.HackCategory;
import de.dietrichpaul.clientbase.injection.mixin.hack.PlayerMoveC2SPacketMixin;
import de.dietrichpaul.clientbase.property.PropertyGroup;
import de.dietrichpaul.clientbase.property.impl.BooleanProperty;
import de.dietrichpaul.clientbase.property.impl.EnumProperty;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdatePlayerAbilitiesC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;

public class FlightHack extends Hack implements SendPacketListener, ReceivePacketListener {
    private final BooleanProperty onGroundSpoof = new BooleanProperty("OnGround", true);
    private final EnumProperty<Mode> mode = new EnumProperty<>("Mode", Mode.CREATIVE, Mode.values(), Mode.class);
    private boolean realFlyingState = false;

    public FlightHack() {
        super("Flight", HackCategory.MOVEMENT);
        this.addProperty(this.mode);
        final PropertyGroup vanillaGroup = this.addPropertyGroup("Vanilla");
        vanillaGroup.addProperty(this.onGroundSpoof);
    }

    @Override
    protected void onEnable() {
        ClientBase.INSTANCE.getEventDispatcher().subscribe(SendPacketListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().subscribe(ReceivePacketListener.class, this);

        if (mc.player == null) return;
        this.realFlyingState = mc.player.getAbilities().allowFlying;
        mc.player.getAbilities().allowFlying = true;
    }

    @Override
    protected void onDisable() {
        ClientBase.INSTANCE.getEventDispatcher().unsubscribeInternal(ReceivePacketListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().unsubscribeInternal(SendPacketListener.class, this);

        if (mc.player == null) return;
        mc.player.getAbilities().allowFlying = this.realFlyingState;
    }

    @Override
    public void onSendPacket(final SendPacketEvent event) {
        if (mode.getValue() != Mode.CREATIVE) return;
        if (mc.player == null) return;
        if (!onGroundSpoof.getState()) return;
        if (!(event.getPacket() instanceof PlayerMoveC2SPacket packet)) return;
        ((PlayerMoveC2SPacketMixin.IAccessor) packet).setOnGround(true);
        mc.player.fallDistance = 0.0F;
    }

    @Override
    public void onReceivePacket(final ReceivePacketEvent event) {
        if (mode.getValue() != Mode.CREATIVE) return;
        if (!(event.getPacket() instanceof PlayerAbilitiesS2CPacket ||
                event.getPacket() instanceof UpdatePlayerAbilitiesC2SPacket)) return;
        this.realFlyingState =
                (event.getPacket() instanceof PlayerAbilitiesS2CPacket packet) ? packet.isFlying() :
                        (event.getPacket() instanceof UpdatePlayerAbilitiesC2SPacket packet1) ? packet1.isFlying()
                                : realFlyingState;

    }

    public enum Mode {
        CREATIVE
    }
}
