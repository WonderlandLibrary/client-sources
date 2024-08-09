package xyz.mathax.mathaxclient.systems.hud;

import xyz.mathax.mathaxclient.gui.renderer.OverlayRenderer;
import xyz.mathax.mathaxclient.settings.Settings;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import net.minecraft.client.MinecraftClient;
import org.json.JSONObject;

import java.io.File;

public abstract class HudElement implements ISerializable<HudElement> {
    protected final Hud hud;

    protected final MinecraftClient mc;

    public final BoundingBox box = new BoundingBox();

    public final Settings settings = new Settings();

    public final String name;
    public final String description;

    public final boolean defaultEnabled;
    public boolean enabled;

    public HudElement(Hud hud, String name, String description, boolean defaultEnabled) {
        this.hud = hud;
        this.name = name;
        this.description = description;
        this.defaultEnabled = defaultEnabled;
        this.mc = MinecraftClient.getInstance();
    }

    public HudElement(Hud hud, String name, String description) {
        this.hud = hud;
        this.name = name;
        this.description = description;
        this.defaultEnabled = false;
        this.mc = MinecraftClient.getInstance();
    }

    public void onEnable() {}

    public void onDisable() {}

    public void toggle() {
        enabled = !enabled;
    }

    public void forceToggle(boolean toggle) {
        enabled = toggle;
    }

    public abstract void update(OverlayRenderer renderer);

    public abstract void render(OverlayRenderer renderer);

    protected boolean isInEditor() {
        return Hud.isEditorScreen() || !Utils.canUpdate();
    }

    public void save(File folder) {
        JSONObject json = toJson();
        if (json == null) {
            return;
        }

        File file = new File(folder, name + ".json");
        JSONUtils.saveJSON(json, file);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("enabled", enabled);
        json.put("settings", settings.toJson());
        json.put("box", box.toJson());
        return json;
    }

    public void load(File folder) {
        File file = new File(folder, name + ".json");
        JSONObject json = JSONUtils.loadJSON(file);
        if (json == null) {
            return;
        }

        fromJson(json);
    }

    @Override
    public HudElement fromJson(JSONObject json) {
        enabled = json.has("enabled") ? json.getBoolean("enabled") : defaultEnabled;

        if (json.has("settings")) {
            settings.fromJson(json.getJSONObject("settings"));
        }

        if (json.has("box")) {
            box.fromJson(json.getJSONObject("box"));
        }

        return this;
    }
}