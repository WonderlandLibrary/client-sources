package xyz.mathax.mathaxclient.systems.hud.elements;

import xyz.mathax.mathaxclient.gui.renderer.OverlayRenderer;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.IntSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.enemies.Enemies;
import xyz.mathax.mathaxclient.systems.friends.Friends;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import xyz.mathax.mathaxclient.systems.hud.HudElement;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TextRadarHudElement extends HudElement {
    private final List<AbstractClientPlayerEntity> players = new ArrayList<>();

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup filterSettings = settings.createGroup("Filter");

    // General

    private final Setting<Integer> limitSetting = generalSettings.add(new IntSetting.Builder()
            .name("Limit")
            .description("Max number of players to show.")
            .defaultValue(10)
            .min(1)
            .sliderRange(1, 20)
            .build()
    );

    private final Setting<Boolean> distanceSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Distance")
            .description("Show the distance to the player next to their name.")
            .defaultValue(false)
            .build()
    );

    // Filter

    private final Setting<Boolean> friendsSetting = filterSettings.add(new BoolSetting.Builder()
            .name("Display friends")
            .description("Whether to show friends or not.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> enemiesSetting = filterSettings.add(new BoolSetting.Builder()
            .name("Display enemies")
            .description("Whether to show enemies or not.")
            .defaultValue(true)
            .build()
    );

    public TextRadarHudElement(Hud hud) {
        super(hud, "Text Radar", "Displays players in your visual range.");
    }

    @Override
    public void update(OverlayRenderer renderer) {
        double width = renderer.textWidth(name + ":");
        double height = renderer.textHeight();

        if (mc.world == null) {
            box.setSize(width, height);
            return;
        }

        for (PlayerEntity entity : getPlayers()) {
            if (entity.equals(mc.player)) {
                continue;
            }

            if (!friendsSetting.get() && Friends.get().contains(entity)) {
                continue;
            }

            if (!enemiesSetting.get() && Enemies.get().contains(entity)) {
                continue;
            }

            String text = entity.getEntityName();
            if (distanceSetting.get()) {
                text += String.format("(%sm)", Math.round(mc.getCameraEntity().distanceTo(entity)));
            }

            width = Math.max(width, renderer.textWidth(text));
            height += renderer.textHeight() + 2;
        }

        box.setSize(width, height);
    }

    @Override
    public void render(OverlayRenderer renderer) {
        double x = box.getX();
        double y = box.getY();

        renderer.text(name + ":", x, y, hud.primaryColorSetting.get());

        if (mc.world == null) {
            return;
        }

        for (PlayerEntity entity : getPlayers()) {
            if (entity.equals(mc.player)) {
                continue;
            }

            if (!friendsSetting.get() && Friends.get().contains(entity)) {
                continue;
            }

            if (!enemiesSetting.get() && Enemies.get().contains(entity)) {
                continue;
            }

            x = box.getX();
            y += renderer.textHeight() + 2;

            String text = entity.getEntityName();
            Color color = PlayerUtils.getPlayerColor(entity, hud.secondaryColorSetting.get());

            renderer.text(text, x, y, color);

            if (distanceSetting.get()) {
                x += renderer.textWidth(text + " ");

                text = String.format("(%sm)", Math.round(mc.getCameraEntity().distanceTo(entity)));
                color = hud.secondaryColorSetting.get();

                renderer.text(text, x, y, color);
            }
        }
    }

    private List<AbstractClientPlayerEntity> getPlayers() {
        players.clear();

        players.addAll(mc.world.getPlayers());
        if (players.size() > limitSetting.get()) {
            players.subList(limitSetting.get() - 1, players.size() - 1).clear();
        }

        players.sort(Comparator.comparingDouble(playerEntity -> playerEntity.distanceTo(mc.getCameraEntity())));

        return players;
    }
}
