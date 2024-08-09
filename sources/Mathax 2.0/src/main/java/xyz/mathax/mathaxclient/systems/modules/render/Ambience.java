package xyz.mathax.mathaxclient.systems.modules.render;

import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.ColorSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.math.Vec3d;

public class Ambience extends Module {
    private final SettingGroup skySettings = settings.createGroup("Sky");
    private final SettingGroup worldSettings = settings.createGroup("World");

    // Sky

    public final Setting<Boolean> endSkySetting = skySettings.add(new BoolSetting.Builder()
        .name("End sky")
        .description("Makes the sky like the end.")
        .defaultValue(false)
        .build()
    );

    public final Setting<Boolean> customSkyColorSetting = skySettings.add(new BoolSetting.Builder()
        .name("Custom sky color")
        .description("Whether the sky color should be changed.")
        .defaultValue(false)
        .build()
    );

    public final Setting<SettingColor> overworldSkyColorSetting = skySettings.add(new ColorSetting.Builder()
        .name("Overworld sky color")
        .description("The color of the overworld sky.")
        .defaultValue(new SettingColor(0, 125, 255))
        .visible(customSkyColorSetting::get)
        .build()
    );

    public final Setting<SettingColor> netherSkyColorSetting = skySettings.add(new ColorSetting.Builder()
        .name("Nether sky color")
        .description("The color of the nether sky.")
        .defaultValue(new SettingColor(100, 0, 0))
        .visible(customSkyColorSetting::get)
        .build()
    );

    public final Setting<SettingColor> endSkyColorSetting = skySettings.add(new ColorSetting.Builder()
        .name("End sky color")
        .description("The color of the end sky.")
        .defaultValue(new SettingColor(65, 30, 90))
        .visible(customSkyColorSetting::get)
        .build()
    );

    public final Setting<Boolean> customCloudColorSetting = skySettings.add(new BoolSetting.Builder()
        .name("Custom cloud color")
        .description("Whether the clouds color should be changed.")
        .defaultValue(false)
        .build()
    );

    public final Setting<SettingColor> cloudColorSetting = skySettings.add(new ColorSetting.Builder()
        .name("Cloud color")
        .description("The color of the clouds.")
        .defaultValue(new SettingColor(100, 0, 0))
        .visible(customCloudColorSetting::get)
        .build()
    );

    public final Setting<Boolean> changeLightningColorSetting = skySettings.add(new BoolSetting.Builder()
        .name("Custom lightning color")
        .description("Whether the lightning color should be changed.")
        .defaultValue(false)
        .build()
    );

    public final Setting<SettingColor> lightningColorSetting = skySettings.add(new ColorSetting.Builder()
        .name("Lightning color")
        .description("The color of the lightning.")
        .defaultValue(new SettingColor(100, 0, 0))
        .visible(changeLightningColorSetting::get)
        .build()
    );

    // World

    public final Setting<Boolean> customGrassColorSetting = worldSettings.add(new BoolSetting.Builder()
        .name("Custom grass color")
        .description("Whether the grass color should be changed.")
        .defaultValue(false)
        .onChanged(val -> reload())
        .build()
    );

    public final Setting<SettingColor> grassColorSetting = worldSettings.add(new ColorSetting.Builder()
        .name("Grass color")
        .description("The color of the grass.")
        .defaultValue(new SettingColor(100, 0, 0))
        .visible(customGrassColorSetting::get)
        .onChanged(val -> reload())
        .build()
    );

    public final Setting<Boolean> customFoliageColorSetting = worldSettings.add(new BoolSetting.Builder()
        .name("Custom foliage color")
        .description("Whether the foliage color should be changed.")
        .defaultValue(false)
        .onChanged(val -> reload())
        .build()
    );

    public final Setting<SettingColor> foliageColorSetting = worldSettings.add(new ColorSetting.Builder()
        .name("Foliage color")
        .description("The color of the foliage.")
        .defaultValue(new SettingColor(100, 0, 0))
        .visible(customFoliageColorSetting::get)
        .onChanged(val -> reload())
        .build()
    );

    public final Setting<Boolean> customWaterColorSetting = worldSettings.add(new BoolSetting.Builder()
        .name("Custom water color")
        .description("Whether the water color should be changed.")
        .defaultValue(false)
        .onChanged(val -> reload())
        .build()
    );

    public final Setting<SettingColor> waterColorSetting = worldSettings.add(new ColorSetting.Builder()
        .name("Water color")
        .description("The color of the water.")
        .defaultValue(new SettingColor(100, 0, 0))
        .visible(customWaterColorSetting::get)
        .onChanged(val -> reload())
        .build()
    );

    public final Setting<Boolean> customLavaColorSetting = worldSettings.add(new BoolSetting.Builder()
        .name("Custom lava color")
        .description("Whether the lava color should be changed.")
        .defaultValue(false)
        .onChanged(val -> reload())
        .build()
    );

    public final Setting<SettingColor> lavaColorSetting = worldSettings.add(new ColorSetting.Builder()
        .name("Lava color")
        .description("The color of the lava.")
        .defaultValue(new SettingColor(100, 0, 0))
        .visible(customLavaColorSetting::get)
        .onChanged(val -> reload())
        .build()
    );

    public Ambience(Category category) {
        super(category, "Ambience", "Change the color of various pieces of the environment.");
    }

    @Override
    public void onEnable() {
        reload();
    }

    @Override
    public void onDisable() {
        reload();
    }

    private void reload() {
        if (mc.worldRenderer != null && isEnabled()) {
            mc.worldRenderer.reload();
        }
    }

    public static class Custom extends DimensionEffects {
        public Custom() {
            super(Float.NaN, true, DimensionEffects.SkyType.END, true, false);
        }

        @Override
        public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
            return color.multiply(0.15000000596046448D);
        }

        @Override
        public boolean useThickFog(int camX, int camY) {
            return false;
        }

        @Override
        public float[] getFogColorOverride(float skyAngle, float tickDelta) {
            return null;
        }
    }

    public SettingColor skyColor() {
        switch (PlayerUtils.getDimension()) {
            case Overworld -> {
                return overworldSkyColorSetting.get();
            }
            case Nether -> {
                return netherSkyColorSetting.get();
            }
            case End -> {
                return endSkyColorSetting.get();
            }
        }

        return null;
    }
}
