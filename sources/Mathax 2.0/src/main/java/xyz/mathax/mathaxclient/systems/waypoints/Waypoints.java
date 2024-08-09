package xyz.mathax.mathaxclient.systems.waypoints;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.eventbus.EventPriority;
import xyz.mathax.mathaxclient.events.game.GameJoinedEvent;
import xyz.mathax.mathaxclient.events.game.GameLeftEvent;
import xyz.mathax.mathaxclient.events.render.Render2DEvent;
import xyz.mathax.mathaxclient.renderer.text.TextRenderer;
import xyz.mathax.mathaxclient.systems.System;
import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.WaypointsModule;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.files.StreamUtils;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.misc.MatHaxIdentifier;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;
import xyz.mathax.mathaxclient.utils.render.NametagUtils;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.world.Dimension;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3d;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Waypoints extends System<Waypoints> implements Iterable<Waypoint> {
    public static final String[] BUILTIN_ICONS = {
            "square",
            "circle",
            "triangle",
            "star",
            "diamond",
            "skull"
    };

    private static final Color TEXT = Color.WHITE;

    public final Map<String, AbstractTexture> icons = new ConcurrentHashMap<>();

    public Map<String, Waypoint> waypoints = new ConcurrentHashMap<>();

    public Waypoints() {
        super("Waypoints", null);
    }

    public static Waypoints get() {
        return Systems.get(Waypoints.class);
    }

    @Override
    public void init() {
        File iconsFolder = new File(MatHax.FOLDER, "Waypoint Icons");
        iconsFolder.mkdirs();

        for (String builtinIcon : BUILTIN_ICONS) {
            File iconFile = new File(iconsFolder, builtinIcon + ".png");
            if (!iconFile.exists()) {
                copyIcon(iconFile);
            }
        }

        File[] files = iconsFolder.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.getName().endsWith(".png")) {
                try {
                    String name = file.getName().replace(".png", "");
                    AbstractTexture texture = new NativeImageBackedTexture(NativeImage.read(new FileInputStream(file)));
                    icons.put(name, texture);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public boolean add(Waypoint waypoint) {
        Waypoint added = waypoints.put(waypoint.nameSetting.get().toLowerCase(Locale.ROOT), waypoint);
        if (added != null) {
            save();
        }

        return added != null;
    }

    public boolean remove(Waypoint waypoint) {
        Waypoint removed = waypoints.remove(waypoint.nameSetting.get().toLowerCase(Locale.ROOT));
        if (removed != null) {
            save();
        }

        return removed != null;
    }

    public Waypoint get(String name) {
        return waypoints.get(name.toLowerCase(Locale.ROOT));
    }

    @EventHandler
    private void onGameJoined(GameJoinedEvent event) {
        load();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onGameDisconnected(GameLeftEvent event) {
        waypoints.clear();
    }

    public static boolean checkDimension(Waypoint waypoint) {
        Dimension playerDim = PlayerUtils.getDimension();
        Dimension waypointDim = waypoint.dimensionSetting.get();
        if (playerDim == waypointDim) {
            return true;
        }

        if (!waypoint.oppositeSetting.get()) {
            return false;
        }

        boolean playerOpp = playerDim == Dimension.Overworld || playerDim == Dimension.Nether;
        boolean waypointOpp = waypointDim == Dimension.Overworld || waypointDim == Dimension.Nether;
        return playerOpp && waypointOpp;
    }

    @EventHandler
    private void onRender2D(Render2DEvent event) {
        WaypointsModule module = Modules.get().get(WaypointsModule.class);
        if (!module.isEnabled()) {
            return;
        }

        TextRenderer text = TextRenderer.get();
        Vector3d center = new Vector3d(MatHax.mc.getWindow().getFramebufferWidth() / 2.0, MatHax.mc.getWindow().getFramebufferHeight() / 2.0, 0);
        int textRenderDistance = module.textRenderDistanceSetting.get();

        for (Waypoint waypoint : this) {
            if (!waypoint.visibleSetting.get() || !checkDimension(waypoint)) {
                continue;
            }

            BlockPos blockPos = waypoint.getPos();
            Vector3d pos = new Vector3d(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
            double dist = PlayerUtils.distanceToCamera(pos.x, pos.y, pos.z);
            if (dist > waypoint.maxVisibleSetting.get()) {
                continue;
            }

            if (!NametagUtils.to2D(pos, 1)) {
                continue;
            }

            double distToCenter = pos.distance(center);
            double a = 1;
            if (dist < 20) {
                a = (dist - 10) / 10;
                if (a < 0.01) {
                    continue;
                }
            }

            NametagUtils.scale = waypoint.scaleSetting.get() - 0.2;
            NametagUtils.begin(pos);

            waypoint.renderIcon(-16, -16, a, 32);

            if (distToCenter <= textRenderDistance) {
                int preTextA = TEXT.a;
                TEXT.a *= a;
                text.begin();

                text.render(waypoint.nameSetting.get(), -text.getWidth(waypoint.nameSetting.get()) / 2, -16 - text.getHeight(), TEXT, true);

                String distText = String.format("%d blocks", (int) Math.round(dist));
                text.render(distText, -text.getWidth(distText) / 2, 16, TEXT, true);

                text.end();

                TEXT.a = preTextA;
            }

            NametagUtils.end();
        }
    }

    @Override
    public File getFile() {
        if (!Utils.canUpdate()) {
            return null;
        }

        return new File(MatHax.VERSION_FOLDER, "Waypoints/" + Utils.getFileWorldName() + ".json");
    }

    public boolean isEmpty() {
        return waypoints.isEmpty();
    }

    @Override
    public Iterator<Waypoint> iterator() {
        return waypoints.values().iterator();
    }

    public ListIterator<Waypoint> iteratorReverse() {
        return new ArrayList<>(waypoints.values()).listIterator(waypoints.size());
    }

    private void copyIcon(File file) {
        try {
            StreamUtils.copy(MatHax.mc.getResourceManager().getResource(new MatHaxIdentifier("textures/icons/waypoints/" + file.getName())).get().getInputStream(), file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("waypoints", new JSONArray());

        waypoints.forEach((name, waypoint) -> {
            JSONObject waypointJson = new JSONObject();
            waypointJson.put("name", name);
            waypointJson.put("value", waypoint.toJson());
            json.append("waypoints", waypointJson);
        });

        return json;
    }

    @Override
    public Waypoints fromJson(JSONObject json) {
        waypoints = new ConcurrentHashMap<>();

        if (json.has("waypoints") && JSONUtils.isValidJSONArray(json, "waypoints")) {
            for (Object object : json.getJSONArray("waypoints")) {
                if (object instanceof JSONObject waypointJson) {
                    if (waypointJson.has("name") && waypointJson.has("value")) {
                        waypoints.put(waypointJson.getString("name"), new Waypoint(waypointJson.getJSONObject("value")));
                    }
                }
            }
        }

        return this;
    }
}
