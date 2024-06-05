package net.shoreline.client.impl.event;

import net.shoreline.client.api.event.Event;
import net.shoreline.client.mixin.MixinMinecraftClient;

/**
 * The main game loop event, this "tick" runs while the
 * {@link net.minecraft.client.MinecraftClient#running} var is <tt>true</tt>.
 *
 * @author linus
 * @since 1.0
 *
 * @see MixinMinecraftClient
 */
public class RunTickEvent extends Event
{

}
