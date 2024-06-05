package net.shoreline.client.impl.imixin;

import net.shoreline.client.util.network.InteractType;
import net.minecraft.entity.Entity;

/**
 *
 *
 */
public interface IPlayerInteractEntityC2SPacket
{
    /**
     *
     * @return
     */
    Entity getEntity();

    /**
     *
     * @return
     */
    InteractType getType();
}
