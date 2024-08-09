package xyz.mathax.mathaxclient.systems.modules.render;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import xyz.mathax.mathaxclient.systems.enemies.Enemies;
import xyz.mathax.mathaxclient.systems.friends.Friends;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.misc.MatHaxIdentifier;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import xyz.mathax.mathaxclient.utils.render.postprocess.PostProcessShaders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import xyz.mathax.mathaxclient.settings.*;

public class Chams extends Module {
    public static final Identifier BLANK = new MatHaxIdentifier("textures/blank.png");

    private final SettingGroup throughWallsSettings = settings.createGroup("Through Walls");
    private final SettingGroup playersSettings = settings.createGroup("Players");
    private final SettingGroup crystalsSettings = settings.createGroup("Crystals");
    private final SettingGroup handSettings = settings.createGroup("Hand");

    // Through walls

    public final Setting<Object2BooleanMap<EntityType<?>>> entitiesSetting = throughWallsSettings.add(new EntityTypeListSetting.Builder()
            .name("Entities")
            .description("Select entities to show through walls.")
            .build()
    );

    public final Setting<Shader> shaderSetting = throughWallsSettings.add(new EnumSetting.Builder<Shader>()
            .name("Shader")
            .description("Render a shader over of the entities.")
            .defaultValue(Shader.Image)
            .onModuleEnabled(setting -> updateShader(setting.get()))
            .onChanged(this::updateShader)
            .build()
    );

    public final Setting<SettingColor> shaderColorSetting = throughWallsSettings.add(new ColorSetting.Builder()
            .name("Color")
            .description("The color that the shader is drawn with.")
            .defaultValue(new SettingColor(255, 255, 255, 150))
            .visible(() -> shaderSetting.get() != Shader.None)
            .build()
    );

    public final Setting<Boolean> ignoreSelfDepthSetting = throughWallsSettings.add(new BoolSetting.Builder()
            .name("Ignore self")
            .description("Ignore yourself drawing the player.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Boolean> ignoreFriendsDepthSetting = throughWallsSettings.add(new BoolSetting.Builder()
            .name("Ignore friends")
            .description("Ignore friends drawing the player.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Boolean> ignoreEnemiesDepthSetting = throughWallsSettings.add(new BoolSetting.Builder()
            .name("Ignore enemies")
            .description("Ignore enemies drawing the player.")
            .defaultValue(true)
            .build()
    );

    // Players

    public final Setting<Boolean> playersSetting = playersSettings.add(new BoolSetting.Builder()
            .name("Players")
            .description("Enable model tweaks for players.")
            .defaultValue(false)
            .build()
    );

    public final Setting<Boolean> ignoreSelfSetting = playersSettings.add(new BoolSetting.Builder()
            .name("Ignore self")
            .description("Ignore yourself when tweaking player models.")
            .defaultValue(false)
            .visible(playersSetting::get)
            .build()
    );

    public final Setting<Boolean> ignoreFriendsSetting = playersSettings.add(new BoolSetting.Builder()
            .name("Ignore friends")
            .description("Ignore friends when tweaking player models.")
            .defaultValue(false)
            .visible(playersSetting::get)
            .build()
    );

    public final Setting<Boolean> ignoreEnemiesSetting = playersSettings.add(new BoolSetting.Builder()
            .name("Ignore enemies")
            .description("Ignore enemies when tweaking player models.")
            .defaultValue(false)
            .visible(playersSetting::get)
            .build()
    );

    public final Setting<Boolean> playersTextureSetting = playersSettings.add(new BoolSetting.Builder()
            .name("Texture")
            .description("Enable player model textures.")
            .defaultValue(false)
            .visible(playersSetting::get)
            .build()
    );

    public final Setting<SettingColor> playersColorSetting = playersSettings.add(new ColorSetting.Builder()
            .name("Color")
            .description("The color of player models.")
            .defaultValue(new SettingColor(200, 135, 255, 150))
            .visible(playersSetting::get)
            .build()
    );

    public final Setting<Double> playersScaleSetting = playersSettings.add(new DoubleSetting.Builder()
            .name("Scale")
            .description("Player scale.")
            .defaultValue(1.0)
            .min(0.0)
            .visible(playersSetting::get)
            .build()
    );

    // Crystals

    public final Setting<Boolean> crystalsSetting = crystalsSettings.add(new BoolSetting.Builder()
            .name("Crystals")
            .description("Enable model tweaks for end crystals.")
            .defaultValue(false)
            .build()
    );

    public final Setting<Double> crystalsScaleSetting = crystalsSettings.add(new DoubleSetting.Builder()
            .name("Scale")
            .description("Crystal scale.")
            .defaultValue(0.6)
            .min(0)
            .visible(crystalsSetting::get)
            .build()
    );

    public final Setting<Double> crystalsBounceSetting = crystalsSettings.add(new DoubleSetting.Builder()
            .name("Bounce")
            .description("How high crystals bounce.")
            .defaultValue(0.6)
            .min(0.0)
            .visible(crystalsSetting::get)
            .build()
    );

    public final Setting<Double> crystalsRotationSpeedSetting = crystalsSettings.add(new DoubleSetting.Builder()
            .name("Rotation speed")
            .description("Multiply the rotation speed of the crystal.")
            .defaultValue(0.3)
            .min(0)
            .visible(crystalsSetting::get)
            .build()
    );

    public final Setting<Boolean> crystalsTextureSetting = crystalsSettings.add(new BoolSetting.Builder()
            .name("Texture")
            .description("Whether to render crystal model textures.")
            .defaultValue(true)
            .visible(crystalsSetting::get)
            .build()
    );

    public final Setting<Boolean> renderCoreSetting = crystalsSettings.add(new BoolSetting.Builder()
            .name("Render core")
            .description("Enable rendering of the core of the crystal.")
            .defaultValue(false)
            .visible(crystalsSetting::get)
            .build()
    );

    public final Setting<SettingColor> crystalsCoreColorSetting = crystalsSettings.add(new ColorSetting.Builder()
            .name("Core color")
            .description("The color of the core of the crystal.")
            .defaultValue(new SettingColor(200, 135, 255))
            .visible(() -> crystalsSetting.get() && renderCoreSetting.get())
            .build()
    );

    public final Setting<Boolean> renderFrame1Setting = crystalsSettings.add(new BoolSetting.Builder()
            .name("Render inner frame")
            .description("Enable rendering of the inner frame of the crystal.")
            .defaultValue(true)
            .visible(crystalsSetting::get)
            .build()
    );

    public final Setting<SettingColor> crystalsFrame1ColorSetting = crystalsSettings.add(new ColorSetting.Builder()
            .name("Inner frame color")
            .description("The color of the inner frame of the crystal.")
            .defaultValue(new SettingColor(200, 135, 255))
            .visible(() -> crystalsSetting.get() && renderFrame1Setting.get())
            .build()
    );

    public final Setting<Boolean> renderFrame2Setting = crystalsSettings.add(new BoolSetting.Builder()
            .name("Render outer frame")
            .description("Enable rendering of the outer frame of the crystal.")
            .defaultValue(true)
            .visible(crystalsSetting::get)
            .build()
    );

    public final Setting<SettingColor> crystalsFrame2ColorSetting = crystalsSettings.add(new ColorSetting.Builder()
            .name("Outer frame color")
            .description("The color of the outer frame of the crystal.")
            .defaultValue(new SettingColor(200, 135, 255))
            .visible(() -> crystalsSetting.get() && renderFrame2Setting.get())
            .build()
    );

    // Hand

    public final Setting<Boolean> handSetting = handSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Enable tweaks of hand rendering.")
            .defaultValue(false)
            .build()
    );

    public final Setting<Boolean> handTextureSetting = handSettings.add(new BoolSetting.Builder()
            .name("Texture")
            .description("Whether to render hand textures.")
            .defaultValue(false)
            .visible(handSetting::get)
            .build()
    );

    public final Setting<SettingColor> handColorSetting = handSettings.add(new ColorSetting.Builder()
            .name("Hand color")
            .description("The color of your hand.")
            .defaultValue(new SettingColor(200, 135, 255, 150))
            .visible(handSetting::get)
            .build()
    );

    public Chams(Category category) {
        super(category, "Chams", "Tweaks rendering of entities.");
    }

    public boolean shouldRender(Entity entity) {
        if (entity instanceof PlayerEntity player) {
            if (player == mc.player && ignoreSelfDepthSetting.get()) {
                return false;
            } else if (Friends.get().contains(player) && ignoreFriendsDepthSetting.get()) {
                return false;
            } else if (Enemies.get().contains(player) && ignoreEnemiesDepthSetting.get()) {
                return false;
            }
        }

        return isEnabled() && !isShader() && entitiesSetting.get().getBoolean(entity.getType());
    }

    public boolean isShader() {
        return isEnabled() && shaderSetting.get() != Shader.None;
    }

    public void updateShader(Shader value) {
        if (value == Shader.None) {
            return;
        }

        PostProcessShaders.CHAMS.init(Utils.nameToCommand(value.name()));
    }

    public enum Shader {
        Image("Image"),
        None("None");

        private final String name;

        Shader(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}