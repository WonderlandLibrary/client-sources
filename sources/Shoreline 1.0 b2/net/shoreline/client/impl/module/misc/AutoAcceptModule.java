package net.shoreline.client.impl.module.misc;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.network.PacketEvent;
import net.shoreline.client.init.Managers;
import net.shoreline.client.util.chat.ChatUtil;
import net.shoreline.client.util.math.timer.CacheTimer;
import net.shoreline.client.util.math.timer.Timer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class AutoAcceptModule extends ToggleModule
{
    //
    Config<Float> delayConfig = new NumberConfig<>("Delay", "The delay before" +
            " accepting teleport requests", 0.0f, 3.0f, 10.0f);
    //
    private final Timer acceptTimer = new CacheTimer();

    /**
     *
     */
    public AutoAcceptModule()
    {
        super("AutoAccept", "Automatically accepts teleport requests",
                ModuleCategory.MISCELLANEOUS);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPacketInbound(PacketEvent.Inbound event)
    {
        if (event.getPacket() instanceof ChatMessageS2CPacket packet)
        {
            String text = packet.body().content();
            if ((text.contains("has requested to teleport to you.")
                    || text.contains("has requested you teleport to them."))
                    && acceptTimer.passed(delayConfig.getValue() * 1000))
            {
                for (PlayerEntity friend :
                        Managers.SOCIAL.getFriendEntities())
                {
                    if (text.contains(friend.getEntityName()))
                    {
                        ChatUtil.serverSendMessage("/tpaccept");
                        break;
                    }
                }
            }
        }
    }
}
