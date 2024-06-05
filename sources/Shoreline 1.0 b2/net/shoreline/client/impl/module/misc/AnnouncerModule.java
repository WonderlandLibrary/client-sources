package net.shoreline.client.impl.module.misc;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.TickEvent;
import net.shoreline.client.impl.event.entity.ConsumeItemEvent;
import net.shoreline.client.impl.event.entity.player.PlayerJumpEvent;
import net.shoreline.client.impl.event.network.BreakBlockEvent;
import net.shoreline.client.impl.event.network.PacketEvent;
import net.shoreline.client.impl.imixin.IPlayerInteractEntityC2SPacket;
import net.shoreline.client.util.chat.ChatUtil;
import net.shoreline.client.util.math.timer.CacheTimer;
import net.shoreline.client.util.math.timer.Timer;
import net.shoreline.client.util.network.InteractType;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRemoveS2CPacket;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class AnnouncerModule extends ToggleModule
{
    //
    Config<Boolean> joinConfig = new BooleanConfig("Join", "Announces " +
            "when a player joins in the chat", true);
    Config<Boolean> leaveConfig = new BooleanConfig("Leave", "Announces when " +
            "a player leaves in the chat", true);
    Config<Boolean> walkConfig = new BooleanConfig("Walk", "Announces when " +
            "you walk in the chat", false);
    Config<Boolean> eatConfig = new BooleanConfig("Eat", "Announces when you " +
            "eat food in the chat", false);
    Config<Boolean> placeConfig = new BooleanConfig("Place", "Announces when " +
            "you place blocks in the chat", false);
    Config<Boolean> breakConfig = new BooleanConfig("Break", "Announces when " +
            "you break blocks in the chat", false);
    Config<Boolean> attackConfig = new BooleanConfig("Attack", "Announces " +
            "when you attack players in the chat", false);
    Config<Boolean> jumpConfig = new BooleanConfig("Jump", "Announces when " +
            "you jump in the chat", false);
    Config<Boolean> worldTimeConfig = new BooleanConfig("WorldTime",
            "Announces the world time in chat", false);
    Config<Boolean> clientSideConfig = new BooleanConfig("ClientSideOnly",
            "Only announces messages in the clientside chat", false);
    //
    private final Random rand = new Random();
    //
    private final Timer eatAnnounceTimer = new CacheTimer();
    private final Timer placeAnnounceTimer = new CacheTimer();
    private final Timer breakAnnounceTimer = new CacheTimer();
    private final Timer attackAnnounceTimer = new CacheTimer();
    private final Timer jumpAnnounceTimer = new CacheTimer();
    //
    private double lastPositionX;
    private double lastPositionY;
    private double lastPositionZ;
    private final Timer lastPositionUpdate = new CacheTimer();
    //
    private int blocksPlaced;
    private int blocksBroken;
    private int foodEaten;
    //
    private static final String[] JOIN_ANNOUNCE = new String[]
            {
                    "Good to see you, ",
                    "Greetings, ",
                    "Hello, ",
                    "Howdy, ",
                    "Hey, ",
                    "Good evening, ",
                    "Welcome to {server}, "
            };
    private static final String[] LEAVE_ANNOUNCE = new String[]
            {
                    "See you later, ",
                    "Catch ya later, ",
                    "See you next time, ",
                    "Farewell, ",
                    "Bye, ",
                    "Good bye, ",
                    "Later, "
            };
    private static final String[] SUNRISE_ANNOUNCE = new String[]
            {
                    "Good bye, zombies!",
                    "Monsters are now burning!",
                    "Burn baby, burn!"
            };
    private static final String[] MORNING_ANNOUNCE = new String[]
            {
                    "Good morning!",
                    "Top of the morning to you!",
                    "Good day!",
                    "You survived another night!",
                    "Good morning everyone!",
                    "The sun is rising in the east, hurrah, hurrah!",
            };
    private static final String[] AFTERNOON_ANNOUNCE = new String[]
            {
                    "Let's go tanning!",
                    "Let's go to the beach!",
                    "Grab your sunglasses!",
                    "Enjoy the sun outside! It is currently very bright!",
                    "It's the brightest time of the day!",
            };
    private static final String[] SUNSET_ANNOUNCE = new String[]
            {
                    "Happy hour!",
                    "Let's get crunk!",
                    "Enjoy the sunset everyone!"
            };
    private static final String[] EVENING_ANNOUNCE = new String[]
            {
                    "It's so dark outside...",
                    "It's the opposite of noon!"
            };
    private static final String[] NIGHT_ANNOUNCE = new String[]
            {
                    "Let's get comfy!",
                    "You survived another day!",
                    "Time to go to bed kids!"
            };

    /**
     *
     */
    public AnnouncerModule()
    {
        super("Announcer", "Announces player actions in the chat",
                ModuleCategory.MISCELLANEOUS);
    }

    /**
     *
     */
    @Override
    public void onEnable()
    {
        blocksPlaced = 0;
        blocksBroken = 0;
        foodEaten = 0;
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
        if (walkConfig.getValue() && lastPositionUpdate.passed(5000))
        {
            double x = lastPositionX - mc.player.prevX;
            double y = lastPositionY - mc.player.prevY;
            double z = lastPositionZ - mc.player.prevZ;
            double dist = Math.sqrt(x * x + y * y + z * z);
            if (dist > 1.0 && dist <= 5000.0)
            {
                String walk = new DecimalFormat("0.00").format(dist);
                sendAnnouncement("I just walked " + walk + " blocks!");
                lastPositionX = mc.player.prevX;
                lastPositionY = mc.player.prevY;
                lastPositionZ = mc.player.prevZ;
                lastPositionUpdate.reset();
            }
        }
        if (worldTimeConfig.getValue())
        {
            long time = mc.world.getTimeOfDay();
            if (time == 0) // Start day
            {
                sendAnnouncement(MORNING_ANNOUNCE[rand.nextInt(MORNING_ANNOUNCE.length)]);
            }
            else if (time == 6000) // Mid day
            {
                sendAnnouncement(AFTERNOON_ANNOUNCE[rand.nextInt(AFTERNOON_ANNOUNCE.length)]);
            }
            else if (time == 11616) // Sunset
            {
                sendAnnouncement(SUNSET_ANNOUNCE[rand.nextInt(SUNSET_ANNOUNCE.length)]);
            }
            else if (time == 12000) // Evening
            {
                sendAnnouncement(EVENING_ANNOUNCE[rand.nextInt(EVENING_ANNOUNCE.length)]);
            }
            else if (time == 18000) // Night
            {
                sendAnnouncement(NIGHT_ANNOUNCE[rand.nextInt(NIGHT_ANNOUNCE.length)]);
            }
            else if (time == 23448)
            {
                sendAnnouncement(SUNRISE_ANNOUNCE[rand.nextInt(SUNRISE_ANNOUNCE.length)]);
            }
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPacketOutbound(PacketEvent.Outbound event)
    {
        if (mc.player == null)
        {
            return;
        }
        if (event.getPacket() instanceof PlayerInteractBlockC2SPacket packet
                && mc.player.getStackInHand(packet.getHand()).getItem() instanceof BlockItem
                && placeConfig.getValue())
        {
            blocksPlaced++;
            if (placeAnnounceTimer.passed(150) && blocksPlaced > rand.nextInt(10))
            {
                sendAnnouncement("I just placed " + blocksPlaced + " blocks!");
                blocksPlaced = 0;
                placeAnnounceTimer.reset();
            }
        }
        else if (event.getPacket() instanceof IPlayerInteractEntityC2SPacket packet
                && packet.getType() == InteractType.ATTACK && attackConfig.getValue())
        {
            Entity attackEntity = packet.getEntity();
            if (attackEntity instanceof PlayerEntity && attackAnnounceTimer.passed(300))
            {
                sendAnnouncement("I just attacked " + attackEntity.getEntityName() + "!");
                attackAnnounceTimer.reset();
            }
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPacketInbound(PacketEvent.Inbound event)
    {
        if (mc.world == null)
        {
            return;
        }
        if (event.getPacket() instanceof PlayerListS2CPacket packet
                && joinConfig.getValue())
        {
            for (PlayerListS2CPacket.Entry entry : packet.getEntries())
            {
                GameProfile profile = entry.profile();
                if (profile.getName() == null || profile.getName().isEmpty()
                        || profile.getId() == null)
                {
                    continue;
                }
                PlayerEntity player = mc.world.getPlayerByUuid(profile.getId());
                if (player != null)
                {
                    if (packet.getActions().contains(PlayerListS2CPacket.Action.ADD_PLAYER))
                    {
                        String serverIp = mc.getServer().getServerIp();
                        sendAnnouncement(JOIN_ANNOUNCE[rand.nextInt(JOIN_ANNOUNCE.length)]
                                .replace("{server}", serverIp) + player.getEntityName());
                    }
                }
            }
        }
        else if (event.getPacket() instanceof PlayerRemoveS2CPacket packet
                && leaveConfig.getValue())
        {
            for (UUID id : packet.profileIds())
            {
                PlayerEntity player = mc.world.getPlayerByUuid(id);
                if (player != null)
                {
                    sendAnnouncement(LEAVE_ANNOUNCE[rand.nextInt(LEAVE_ANNOUNCE.length)]
                            + player.getEntityName());
                }
            }
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onBreakBlock(BreakBlockEvent event)
    {
        blocksBroken++;
        if (breakConfig.getValue() && breakAnnounceTimer.passed(300)
                && blocksBroken > rand.nextInt(10))
        {
            sendAnnouncement("I just mined " + blocksBroken + " blocks!");
            blocksBroken = 0;
            breakAnnounceTimer.reset();
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPlayerJump(PlayerJumpEvent event)
    {
        if (jumpConfig.getValue() && jumpAnnounceTimer.passed(300))
        {
            sendAnnouncement("I just jumped in the air!");
            jumpAnnounceTimer.reset();
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onConsumeItem(ConsumeItemEvent event)
    {
        if (!event.getItem().isFood())
        {
            return;
        }
        foodEaten++;
        if (eatConfig.getValue() && eatAnnounceTimer.passed(300)
                && foodEaten > rand.nextInt(10))
        {
            sendAnnouncement("I just ate " + foodEaten + " " + event.getStack().getName()
                    + (foodEaten > 1 ? "'s" : "") + "!");
            foodEaten = 0;
            eatAnnounceTimer.reset();
        }
    }

    /**
     *
     * @param announcement
     */
    private void sendAnnouncement(String announcement)
    {
        if (clientSideConfig.getValue())
        {
            ChatUtil.clientSendMessageRaw(announcement);
        }
        else
        {
            ChatUtil.serverSendMessage(announcement);
        }
    }
}
