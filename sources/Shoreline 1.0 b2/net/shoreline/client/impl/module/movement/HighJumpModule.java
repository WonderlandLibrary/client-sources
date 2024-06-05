package net.shoreline.client.impl.module.movement;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.entity.player.PlayerMoveEvent;
import net.shoreline.client.init.Managers;
import net.shoreline.client.util.Globals;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class HighJumpModule extends ToggleModule
{
    //
    Config<Float> heightConfig = new NumberConfig<>("Height", "The height to " +
            "jump on the ground", 0.1f, 0.42f, 1.0f);
    Config<Boolean> airJumpConfig = new BooleanConfig("InAir", "Allows jumps " +
            "in the air (i.e. double jumps)", false);

    /**
     *
     */
    public HighJumpModule()
    {
        super("HighJump", "Allows player to jump higher", ModuleCategory.MOVEMENT);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPlayerMove(PlayerMoveEvent event)
    {
        if (mc.options.jumpKey.isPressed() && (mc.player.isOnGround()
                || airJumpConfig.getValue()))
        {
            Managers.MOVEMENT.setMotionY(heightConfig.getValue());
            event.cancel();
            event.setY(mc.player.getVelocity().y);
        }
    }
}
