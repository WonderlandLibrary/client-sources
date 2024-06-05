package net.shoreline.client.impl.module.combat;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.EnumConfig;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.TickEvent;
import net.shoreline.client.impl.event.network.PacketEvent;
import net.shoreline.client.init.Managers;
import net.shoreline.client.init.Modules;
import net.shoreline.client.impl.imixin.IPlayerInteractEntityC2SPacket;
import net.shoreline.client.util.math.timer.CacheTimer;
import net.shoreline.client.util.math.timer.Timer;
import net.shoreline.client.util.network.InteractType;
import net.shoreline.client.util.player.InventoryUtil;
import net.shoreline.client.util.string.EnumFormatter;
import net.shoreline.client.util.world.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;

import java.util.Random;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class CriticalsModule extends ToggleModule
{
    //
    Config<CritMode> modeConfig = new EnumConfig<>("Mode", "Mode for critical" +
            " attack modifier", CritMode.PACKET, CritMode.values());
    Config<Boolean> packetSyncConfig = new BooleanConfig("Packet-Sync",
            "Syncs the cached packet interaction to the MC tick", false);
    //
    private final Timer attackTimer = new CacheTimer();
    // The cached attack packets that will be resent after manipulating
    // player position packets
    private PlayerInteractEntityC2SPacket attackPacket;
    private HandSwingC2SPacket swingPacket;
    // RANDOM
    private final Random random = new Random();

    /**
     *
     *
     */
    public CriticalsModule()
    {
        super("Criticals", "Modifies attacks to guarentee critical hits",
                ModuleCategory.COMBAT);
    }

    /**
     *
     *
     * @return
     */
    @Override
    public String getModuleData()
    {
        return EnumFormatter.formatEnum(modeConfig.getValue());
    }

    /**
     *
     *
     * @param event
     */
    @EventListener
    public void onTick(TickEvent event)
    {
        if (packetSyncConfig.getValue() && attackPacket != null
                && swingPacket != null)
        {
            Managers.NETWORK.sendPacket(attackPacket);
            Managers.NETWORK.sendPacket(swingPacket);
            attackPacket = null;
            swingPacket = null;
        }
    }

    /**
     *
     *
     * @param event
     */
    @EventListener
    public void onPacketOutbound(PacketEvent.Outbound event)
    {
        // Custom aura crit handling
        if (Modules.AURA.isEnabled())
        {
            return;
        }
        if (event.getPacket() instanceof IPlayerInteractEntityC2SPacket packet
                && packet.getType() == InteractType.ATTACK)
        {
            if (!Managers.POSITION.isOnGround()
                    || mc.player.isRiding()
                    || mc.player.isFallFlying()
                    || mc.player.isTouchingWater()
                    || mc.player.isInLava()
                    || mc.player.isHoldingOntoLadder()
                    || mc.player.hasStatusEffect(StatusEffects.BLINDNESS)
                    || mc.player.input.jumping
                    || InventoryUtil.isHolding32k())
            {
                return;
            }
            // Attacked entity
            final Entity e = packet.getEntity();
            if (e == null || !e.isAlive() || e instanceof EndCrystalEntity)
            {
                return;
            }
            if (attackTimer.passed(500))
            {
                event.cancel();
                if (EntityUtil.isVehicle(e))
                {
                    for (int i = 0; i < 5; ++i)
                    {
                        Managers.NETWORK.sendPacket(PlayerInteractEntityC2SPacket.attack(e,
                                Managers.POSITION.isSneaking()));
                        Managers.NETWORK.sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
                    }
                }
                else
                {
                    attackPacket = (PlayerInteractEntityC2SPacket) packet;
                    preAttackPacket();
                    if (!packetSyncConfig.getValue())
                    {
                        Managers.NETWORK.sendPacket(PlayerInteractEntityC2SPacket.attack(
                                e, Managers.POSITION.isSneaking()));
                    }
                    mc.player.addCritParticles(e);
                }
                attackTimer.reset();
            }
        }
        else if (event.getPacket() instanceof HandSwingC2SPacket packet)
        {
            if (packetSyncConfig.getValue() && attackPacket != null)
            {
                event.cancel();
                swingPacket = packet;
            }
        }
    }

    /**
     * Callback method for pre attack stage, must be called before the attack
     * packet or else the movements will not be registered
     *
     * @see AuraModule#postAttackTarget()
     */
    public void preAttackPacket()
    {
        double x = Managers.POSITION.getX();
        double y = Managers.POSITION.getY();
        double z = Managers.POSITION.getZ();
        switch (modeConfig.getValue())
        {
            case PACKET ->
            {
                double d = 1.0e-7 + 1.0e-7 * (1.0 + random.nextInt(random.nextBoolean() ? 34 : 43));
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y + 0.1016f + d * 3.0f, z, false));
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y + 0.0202f + d * 2.0f, z, false));
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y + 3.239e-4 + d, z, false));
            }
            case PACKET_STRICT ->
            {
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y + 0.11f, z, false));
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y + 0.1100013579f, z, false));
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y + 0.0000013579f, z, false));
            }
            case VANILLA ->
            {
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y + 0.0626024016927725f, z, false));
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y + 0.0726023996066094f, z, false));
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y, z, false));
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y, z, false));
            }
            case LOW_HOP ->
            {
                // mc.player.jump();
                Managers.MOVEMENT.setMotionY(0.3425);
            }
        }
    }

    public enum CritMode
    {
        PACKET,
        PACKET_STRICT,
        VANILLA,
        LOW_HOP
    }
}
