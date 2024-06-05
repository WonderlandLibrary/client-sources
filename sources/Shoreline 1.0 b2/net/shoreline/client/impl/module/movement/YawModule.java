package net.shoreline.client.impl.module.movement;

import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.TickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.LlamaEntity;
import net.shoreline.client.util.Globals;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class YawModule extends ToggleModule
{
    /**
     *
     */
    public YawModule()
    {
        super("Yaw", "Locks player yaw to a cardinal axis",
                ModuleCategory.MOVEMENT);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onTick(TickEvent event)
    {
        if (event.getStage() == EventStage.PRE)
        {
            float yaw = Math.round(mc.player.getYaw() / 90.0f) * 90.0f;
            Entity vehicle = mc.player.getVehicle();
            if (vehicle != null)
            {
                vehicle.setYaw(yaw);
                if (vehicle instanceof LlamaEntity llama)
                {
                    llama.setHeadYaw(yaw);
                }
                return;
            }
            mc.player.setYaw(yaw);
            mc.player.setHeadYaw(yaw);
        }
    }
}
