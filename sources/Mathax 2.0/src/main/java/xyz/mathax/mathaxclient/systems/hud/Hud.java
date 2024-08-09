package xyz.mathax.mathaxclient.systems.hud;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.Render2DEvent;
import xyz.mathax.mathaxclient.gui.renderer.OverlayRenderer;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.System;
import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.gui.screens.modules.hud.HudEditorScreen;
import xyz.mathax.mathaxclient.gui.screens.modules.hud.HudElementScreen;
import xyz.mathax.mathaxclient.utils.input.KeyBind;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.render.Alignment;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import xyz.mathax.mathaxclient.systems.hud.elements.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class Hud extends System<Hud> implements Iterable<HudElement> {
    public static final File HUD_FOLDER = new File(MatHax.VERSION_FOLDER, "HUD");

    private final OverlayRenderer RENDERER = new OverlayRenderer();

    public final Settings settings = new Settings();

    public final List<HudElement> elements = new ArrayList<>();
    private final HudElementLayer layer1, layer2, layer3, layer4, layer5, layer6, layer7, layer8, layer9, layer10, layer11;

    public boolean enabled;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup editorSettings = settings.createGroup("Editor");
    private final SettingGroup keybindSettings = settings.createGroup("KeyBindings");

    // General

    public final Setting<Double> scaleSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Scale")
            .description("Scale of the HUD.")
            .defaultValue(1)
            .min(0.75)
            .sliderRange(0.75, 4)
            .build()
    );

    public final Setting<SettingColor> primaryColorSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Primary color")
            .description("Primary color of HUD text.")
            .defaultValue(new SettingColor(Color.MATHAX))
            .build()
    );

    public final Setting<SettingColor> secondaryColorSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Secondary color")
            .description("Secondary color of HUD text.")
            .defaultValue(new SettingColor(Color.LIGHT_GRAY))
            .build()
    );

    // Editor

    public final Setting<Integer> snappingRangeSetting = editorSettings.add(new IntSetting.Builder()
            .name("Snapping range")
            .description("Snapping range in editor.")
            .defaultValue(6)
            .build()
    );

    // KeyBindings

    private final Setting<KeyBind> keyBindSetting = keybindSettings.add(new KeyBindSetting.Builder()
            .name("KeyBind")
            .defaultValue(KeyBind.none())
            .action(() -> enabled = !enabled)
            .build()
    );


    public final Runnable reset = () -> {
        settings.reset();

        align();
        elements.forEach(element -> {
            element.enabled = element.defaultEnabled;
            element.settings.forEach(group -> group.forEach(Setting::reset));
        });
    };

    public Hud() {
        super("HUD", null);

        settings.registerColorSettings(null);

        layer1 = new HudElementLayer(RENDERER, elements, Alignment.X.Left, Alignment.Y.Top, 2, 2);
        layer1.add(new WatermarkHudElement(this));
        layer1.add(new FpsHudElement(this));
        layer1.add(new PingHudElement(this));
        layer1.add(new TpsHudElement(this));
        layer1.add(new SpeedHudElement(this));
        layer1.add(new ServerHudElement(this));
        layer1.add(new ServerBrandHudElement(this));
        layer1.add(new DurabilityHudElement(this));
        layer1.add(new BiomeHudElement(this));
        //layer1.add(new PlayerModelHudElement(this));

        layer2 = new HudElementLayer(RENDERER, elements, Alignment.X.Left, Alignment.Y.Top, 100, 200);
        layer2.add(new ModuleInfoHudElement(this));

        layer3 = new HudElementLayer(RENDERER, elements, Alignment.X.Center, Alignment.Y.Center, 0, 215);
        layer3.add(new LookingAtHudElement(this));
        layer3.add(new BreakingBlockHudElement(this));

        layer4 = new HudElementLayer(RENDERER, elements, Alignment.X.Left, Alignment.Y.Bottom, 2, 2);
        layer4.add(new PositionHudElement(this));
        layer4.add(new RotationHudElement(this));

        layer5 = new HudElementLayer(RENDERER, elements, Alignment.X.Center, Alignment.Y.Top, 0, 2);
        layer5.add(new WelcomeHudElement(this));
        layer5.add(new LagNotifierHudElement(this));

        layer6 = new HudElementLayer(RENDERER, elements, Alignment.X.Right, Alignment.Y.Bottom, 2, 2);
        layer6.add(new EnabledModulesHudElement(this));

        layer7 = new HudElementLayer(RENDERER, elements, Alignment.X.Right, Alignment.Y.Top, 2, 2);
        layer7.add(new InventoryViewerHudElement(this));
        layer7.add(new ContainerViewerHudElement(this));
        layer7.add(new PotionTimersHudElement(this));

        layer8 = new HudElementLayer(RENDERER, elements, Alignment.X.Left, Alignment.Y.Center, 2, 100);
        //layer8.add(new CombatInfoHudElement(this));
        layer8.add(new TextRadarHudElement(this));

        layer9 = new HudElementLayer(RENDERER, elements, Alignment.X.Center, Alignment.Y.Center, -150, -50);
        layer9.add(new TotemHudElement(this));
        layer9.add(new ArmorHudElement(this));

        layer10 = new HudElementLayer(RENDERER, elements, Alignment.X.Center, Alignment.Y.Center, 0, 0);
        layer10.add(new HoleHudElement(this));

        layer11 = new HudElementLayer(RENDERER, elements, Alignment.X.Center, Alignment.Y.Center, 0, 0);
        layer11.add(new CompassHudElement(this));

        align();
    }

    public static Hud get() {
        return Systems.get(Hud.class);
    }

    public void toggle() {
        enabled = !enabled;
    }

    public void forceToggle(boolean enabled) {
        this.enabled = enabled;
    }

    private void align() {
        RENDERER.begin(scaleSetting.get(), 0, true);

        layer1.align();
        layer2.align();
        layer3.align();
        layer4.align();
        layer5.align();
        layer6.align();
        layer7.align();
        layer8.align();
        layer9.align();
        layer10.align();
        layer11.align();

        RENDERER.end();
    }

    @EventHandler
    public void onRender(Render2DEvent event) {
        if (isEditorScreen()) {
            render(event.tickDelta, element -> true);
        } else if (enabled && !mc.options.hudHidden && !mc.options.debugEnabled) {
            render(event.tickDelta, element -> element.enabled);
        }
    }

    public void render(float delta, Predicate<HudElement> shouldRender) {
        RENDERER.begin(scaleSetting.get(), delta, false);

        for (HudElement element : elements) {
            if (shouldRender.test(element)) {
                element.update(RENDERER);
                element.render(RENDERER);
            }
        }

        RENDERER.end();
    }

    public static boolean isEditorScreen() {
        return mc.currentScreen instanceof HudEditorScreen || mc.currentScreen instanceof HudElementScreen;
    }

    @NotNull
    @Override
    public Iterator<HudElement> iterator() {
        return elements.iterator();
    }

    @Override
    public void save(File folder) {
        if (folder == null) {
            folder = HUD_FOLDER;
        } else {
            folder = new File(folder, "HUD");
        }

        JSONObject json = toJson();
        if (json == null) {
            return;
        }

        File file = new File(folder, "HUD.json");
        JSONUtils.saveJSON(json, file);

        for (HudElement element : elements) {
            element.save(new File(folder, "Elements"));
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("enabled", enabled);
        json.put("settings", settings.toJson());
        return json;
    }

    @Override
    public void load(File folder) {
        if (folder == null) {
            folder = HUD_FOLDER;
        } else {
            folder = new File(folder, "HUD");
        }

        File file = new File(folder, "HUD.json");
        JSONObject json = JSONUtils.loadJSON(file);
        if (json == null) {
            return;
        }

        fromJson(json);

        for (HudElement element : elements) {
            element.load(new File(folder, "Elements"));
        }
    }

    @Override
    public Hud fromJson(JSONObject json) {
        settings.reset();

        if (json.has("enabled")) {
            enabled = json.getBoolean("enabled");
        }

        if (json.has("settings")) {
            settings.fromJson(json.getJSONObject("settings"));
        }

        return this;
    }
}
