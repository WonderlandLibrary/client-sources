package net.shoreline.client.impl.module.misc;

import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.network.DisconnectEvent;
import net.shoreline.client.util.world.FakePlayerEntity;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see FakePlayerEntity
 */
public class FakePlayerModule extends ToggleModule
{
    //
    private FakePlayerEntity fakePlayer;

    /**
     *
     */
    public FakePlayerModule()
    {
        super("FakePlayer", "Spawns an indestructible client-side player",
                ModuleCategory.MISCELLANEOUS);
    }

    /**
     *
     *
     */
    @Override
    public void onEnable()
    {
        if (mc.player != null && mc.world != null)
        {
            fakePlayer = new FakePlayerEntity(mc.player, "FakePlayer");
            fakePlayer.spawnPlayer();
        }
    }

    /**
     *
     *
     */
    @Override
    public void onDisable()
    {
        if (fakePlayer != null)
        {
            fakePlayer.despawnPlayer();
            fakePlayer = null;
        }
    }

    /**
     *
     *
     * @param event
     */
    @EventListener
    public void onDisconnect(DisconnectEvent event)
    {
        fakePlayer = null;
        disable();
    }
}
