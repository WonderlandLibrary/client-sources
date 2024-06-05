package net.shoreline.client.impl.module.misc;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.TickEvent;
import net.shoreline.client.init.Managers;
import net.shoreline.client.util.math.timer.CacheTimer;
import net.shoreline.client.util.math.timer.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.passive.DonkeyEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class AutoMountModule extends ToggleModule
{
    //
    Config<Float> delayConfig = new NumberConfig<>("Delay", "The delay to " +
            "wait between mounts", 0.0f, 0.5f, 10.0f);
    Config<Boolean> donkeyConfig = new BooleanConfig("Donkey", "Automatically" +
            " mounts nearby donkeys", true);
    Config<Boolean> horseConfig = new BooleanConfig("Horse", "Automatically " +
            "mounts nearby horses", true);
    Config<Boolean> skeletonHorseConfig = new BooleanConfig("SkeletonHorse",
            "Automatically mounts nearby skeleton horses", false);
    Config<Boolean> llamaConfig = new BooleanConfig("Llama", "Automatically " +
            "mounts nearby llamas", false);
    Config<Boolean> pigConfig = new BooleanConfig("Pig", "Automatically " +
            "mounts nearby pigs", false);
    //
    private final Timer mountTimer = new CacheTimer();

    /**
     *
     */
    public AutoMountModule()
    {
        super("AutoMount", "Automatically mounts nearby entities",
                ModuleCategory.MISCELLANEOUS);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onTick(TickEvent event)
    {
        if (event.getStage() != EventStage.PRE)
        {
            return;
        }
        if (mc.player.getVehicle() != null || !mountTimer.passed(delayConfig.getValue() * 1000.0f))
        {
            return;
        }
        for (Entity entity : mc.world.getEntities())
        {
            double dist = mc.player.distanceTo(entity);
            if (dist > 4.0)
            {
                continue;
            }
            if (checkMount(entity))
            {
                boolean sprint = Managers.POSITION.isSprinting();
                boolean sneak = Managers.POSITION.isSneaking();
                if (sprint)
                {
                    Managers.NETWORK.sendPacket(new ClientCommandC2SPacket(mc.player,
                            ClientCommandC2SPacket.Mode.STOP_SPRINTING));
                }
                if (sneak)
                {
                    Managers.NETWORK.sendPacket(new ClientCommandC2SPacket(mc.player,
                            ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
                }
                Managers.NETWORK.sendPacket(PlayerInteractEntityC2SPacket.interact(entity, false, Hand.MAIN_HAND));
                if (sprint)
                {
                    Managers.NETWORK.sendPacket(new ClientCommandC2SPacket(mc.player,
                            ClientCommandC2SPacket.Mode.START_SPRINTING));
                }
                if (sneak)
                {
                    Managers.NETWORK.sendPacket(new ClientCommandC2SPacket(mc.player,
                            ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));
                }
                mountTimer.reset();
                break;
            }
        }
    }

    private boolean checkMount(Entity entity)
    {
        return donkeyConfig.getValue() && entity instanceof DonkeyEntity
                || horseConfig.getValue() && entity instanceof HorseEntity
                || skeletonHorseConfig.getValue() && entity instanceof SkeletonHorseEntity
                || llamaConfig.getValue() && entity instanceof LlamaEntity
                || pigConfig.getValue() && entity instanceof PigEntity;
     }
}
