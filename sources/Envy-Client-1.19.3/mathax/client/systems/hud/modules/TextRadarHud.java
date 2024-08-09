package mathax.client.systems.hud.modules;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import mathax.client.eventbus.EventHandler;
import mathax.client.events.game.GameJoinedEvent;
import mathax.client.events.packets.PacketEvent;
import mathax.client.renderer.GL;
import mathax.client.renderer.Renderer2D;
import mathax.client.settings.*;
import mathax.client.systems.friends.Friends;
import mathax.client.utils.Utils;
import mathax.client.utils.entity.EntityUtils;
import mathax.client.utils.player.PlayerUtils;
import mathax.client.utils.render.color.Color;
import mathax.client.utils.render.color.SettingColor;
import mathax.client.systems.hud.HUD;
import mathax.client.systems.hud.HudElement;
import mathax.client.systems.hud.HudRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class TextRadarHud extends HudElement {
    private static final Identifier MATHAX_LOGO = new Identifier("mathax", "textures/icons/icon.png");
    private Color TEXTURE_COLOR = new Color(255, 255, 255, 255);

    private final Object2IntMap<UUID> totemPopMap = new Object2IntOpenHashMap<>();

    private final List<AbstractClientPlayerEntity> players = new ArrayList<>();

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // General

    public final Setting<SettingColor> playerNameColor = sgGeneral.add(new ColorSetting.Builder()
        .name("player-name-color")
        .description("Color of player names.")
        .defaultValue(new SettingColor(230, 25, 60))
        .build()
    );

    private final Setting<Integer> limit = sgGeneral.add(new IntSetting.Builder()
        .name("limit")
        .description("The max number of players to show.")
        .defaultValue(10)
        .min(1)
        .sliderRange(1, 20)
        .build()
    );

    private final Setting<Boolean> health = sgGeneral.add(new BoolSetting.Builder()
        .name("health")
        .description("Shows the health of player next to their name.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> ping = sgGeneral.add(new BoolSetting.Builder()
        .name("ping")
        .description("Shows the ping of player next to their name.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> distance = sgGeneral.add(new BoolSetting.Builder()
        .name("distance")
        .description("Shows the distance to the player next to their name.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> totemPops = sgGeneral.add(new BoolSetting.Builder()
        .name("totem-pops")
        .description("Show the players pops.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> ignoreFriends = sgGeneral.add(new BoolSetting.Builder()
        .name("ignore-friends")
        .description("Stops showing friends.")
        .defaultValue(false)
        .build()
    );

    public TextRadarHud(HUD hud) {
        super(hud, "text-radar", "Displays names and stats on players in your render distance.", true);
    }

    @EventHandler
    private void onGameJoin(GameJoinedEvent event) {
        totemPopMap.clear();
    }

    @EventHandler
    private void onReceivePacket(PacketEvent.Receive event) {
        if (!totemPops.get()) return;
        if (!(event.packet instanceof EntityStatusS2CPacket)) return;

        EntityStatusS2CPacket p = (EntityStatusS2CPacket) event.packet;
        if (p.getStatus() != 35) return;

        Entity entity = p.getEntity(mc.world);

        if (!(entity instanceof PlayerEntity)) return;

        synchronized (totemPopMap) {
            int pops = totemPopMap.getOrDefault(entity.getUuid(), 0);
            totemPopMap.put(entity.getUuid(), ++pops);
        }
    }

    @Override
    public void update(HudRenderer renderer) {
        double width = renderer.textWidth("Players:");
        double height = renderer.textHeight();

        if (mc.world == null) {
            box.setSize(width, height);
            return;
        }

        for (PlayerEntity entity : getPlayers()) {
            if (entity.equals(mc.player)) continue;
            if (ignoreFriends.get() && Friends.get().isFriend(entity)) continue;

            String text = "";
            if (Utils.isDeveloper(entity.getUuidAsString())) text += "    " + entity.getEntityName();
            else text += entity.getEntityName();

            if (health.get() || ping.get() || distance.get()) text += " -";
            if (health.get()) text += String.format(" %s", Math.round(entity.getHealth() + entity.getAbsorptionAmount()));
            if (ping.get()) text += String.format(" [%sms]", Math.round(EntityUtils.getPing(entity)));
            if (distance.get()) text += String.format(" (%sm)", Math.round(mc.getCameraEntity().distanceTo(entity)));
            if (totemPops.get()) text += String.format(" [%s]", getPops(entity));

            width = Math.max(width, renderer.textWidth(text));
            height += renderer.textHeight() + 2;
        }

        box.setSize(width, height);
    }

    private final Color RED = new Color(255, 25, 25);
    private final Color AMBER = new Color(255, 105, 25);
    private final Color GREEN = new Color(25, 252, 25);
    private final Color BLUE = new Color(20, 170, 170);

    @Override
    public void render(HudRenderer renderer) {
        double x = box.getX();
        double y = box.getY();

        renderer.text("Players:", x, y, hud.primaryColor.get());

        if (mc.world == null) return;

        for (PlayerEntity entity : getPlayers()) {
            if (entity.equals(mc.player)) continue;
            if (ignoreFriends.get() && Friends.get().isFriend(entity)) continue;

            x = box.getX();
            y += renderer.textHeight() + 2;

            String text = "";

            boolean showDev = Utils.isDeveloper(entity.getUuidAsString());

            if (showDev) {
                text += "    " + entity.getEntityName();

                GL.bindTexture(MATHAX_LOGO);
                Renderer2D.TEXTURE.begin();
                Renderer2D.TEXTURE.texQuad(x - renderer.textWidth(text) + 2, y, 16, 16, TEXTURE_COLOR);
                Renderer2D.TEXTURE.render(null);
            } else text += entity.getEntityName();

            Color color = PlayerUtils.getPlayerColor(entity, playerNameColor.get());

            renderer.text(text, x, y, color);

            if (health.get() || ping.get() || distance.get()) {
                x += renderer.textWidth(text + " ");

                text = "-";
                color = hud.secondaryColor.get();

                renderer.text(text, x, y, color);
            }

            if (health.get()) {
                x += renderer.textWidth(text + " ");

                text = String.format("%s", Math.round(entity.getHealth() + entity.getAbsorptionAmount()));
                double healthPercentage = Math.round(entity.getHealth() + entity.getAbsorptionAmount()) / (entity.getMaxHealth() + entity.getAbsorptionAmount());
                if (healthPercentage <= 0.333) color = RED;
                else if (healthPercentage <= 0.666) color = AMBER;
                else color = GREEN;

                renderer.text(text, x, y, color);
            }

            if (ping.get()) {
                x += renderer.textWidth(text + " ");

                text = String.format("[%sms]", Math.round(EntityUtils.getPing(entity)));
                color = BLUE;

                renderer.text(text, x, y, color);
            }

            if (distance.get()) {
                x += renderer.textWidth(text + " ");

                text = String.format("(%sm)", Math.round(mc.getCameraEntity().distanceTo(entity)));
                color = hud.secondaryColor.get();

                renderer.text(text, x, y, color);
            }

            if (totemPops.get()) {
                x += renderer.textWidth(text + " ");

                text = String.format("[%s]", getPops(entity));
                color = AMBER;

                renderer.text(text, x, y, color);
            }
        }
    }

    public int getPops(PlayerEntity p) {
        if (!totemPopMap.containsKey(p.getUuid())) return 0;
        return totemPopMap.getOrDefault(p.getUuid(), 0);
    }

    private List<AbstractClientPlayerEntity> getPlayers() {
        players.clear();
        players.addAll(mc.world.getPlayers());
        if (players.size() > limit.get()) players.subList(limit.get() - 1, players.size() - 1).clear();
        players.sort(Comparator.comparingDouble(e -> e.distanceTo(mc.getCameraEntity())));

        return players;
    }
}
