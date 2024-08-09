package xyz.mathax.mathaxclient.systems.waypoints;

import xyz.mathax.mathaxclient.renderer.GL;
import xyz.mathax.mathaxclient.renderer.Renderer2D;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import xyz.mathax.mathaxclient.utils.world.Dimension;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.util.math.BlockPos;
import org.json.JSONObject;
import xyz.mathax.mathaxclient.settings.*;

import java.util.Map;
import java.util.Objects;

public class Waypoint implements ISerializable<Waypoint> {
    public final Settings settings = new Settings();

    private final SettingGroup visualSettings = settings.createGroup("Visual");
    private final SettingGroup positionSettings = settings.createGroup("Position");

    // Visual

    public Setting<String> nameSetting = visualSettings.add(new StringSetting.Builder()
            .name("Name")
            .description("The name of the waypoint.")
            .defaultValue("Home")
            .build()
    );

    public Setting<String> iconSetting = visualSettings.add(new ProvidedStringSetting.Builder()
            .name("Icon")
            .description("The icon of the waypoint.")
            .defaultValue("Square")
            .supplier(() -> Waypoints.BUILTIN_ICONS)
            .onChanged(v -> validateIcon())
            .build()
    );

    public Setting<SettingColor> colorSetting = visualSettings.add(new ColorSetting.Builder()
            .name("Color")
            .description("The color of the waypoint.")
            .defaultValue(Color.MATHAX.toSetting())
            .build()
    );

    public Setting<Boolean> visibleSetting = visualSettings.add(new BoolSetting.Builder()
            .name("Visible")
            .description("Whether to show the waypoint.")
            .defaultValue(true)
            .build()
    );

    public Setting<Integer> maxVisibleSetting = visualSettings.add(new IntSetting.Builder()
            .name("Max visible distance")
            .description("How far away to render the waypoint.")
            .defaultValue(5000)
            .build()
    );

    public Setting<Double> scaleSetting = visualSettings.add(new DoubleSetting.Builder()
            .name("Scale")
            .description("The scale of the waypoint.")
            .defaultValue(1)
            .build()
    );

    // Position

    public Setting<BlockPos> posSetting = positionSettings.add(new BlockPosSetting.Builder()
            .name("Location")
            .description("The location of the waypoint.")
            .defaultValue(BlockPos.ORIGIN)
            .build()
    );

    public Setting<Dimension> dimensionSetting = positionSettings.add(new EnumSetting.Builder<Dimension>()
            .name("Dimension")
            .description("Which dimension the waypoint is in.")
            .defaultValue(Dimension.Overworld)
            .build()
    );

    public Setting<Boolean> oppositeSetting = positionSettings.add(new BoolSetting.Builder()
            .name("Opposite dimension")
            .description("Whether to show the waypoint in the opposite dimension.")
            .defaultValue(true)
            .visible(() -> dimensionSetting.get() != Dimension.End)
            .build()
    );

    private Waypoint() {}

    public Waypoint(JSONObject json) {
        fromJson(json);
    }

    public void renderIcon(double x, double y, double a, double size) {
        AbstractTexture texture = Waypoints.get().icons.get(iconSetting.get());
        if (texture == null) {
            return;
        }

        int preA = colorSetting.get().a;
        colorSetting.get().a *= a;

        GL.bindTexture(texture.getGlId());
        Renderer2D.TEXTURE.begin();
        Renderer2D.TEXTURE.texturedQuad(x, y, size, size, colorSetting.get());
        Renderer2D.TEXTURE.render(null);

        colorSetting.get().a = preA;
    }

    public BlockPos getPos() {
        Dimension dim = dimensionSetting.get();
        BlockPos pos = posSetting.get();

        Dimension currentDim = PlayerUtils.getDimension();
        if (dim == currentDim || dim.equals(Dimension.End)) {
            return posSetting.get();
        }

        return switch (dim) {
            case Overworld -> new BlockPos(pos.getX() / 8, pos.getY(), pos.getZ() / 8);
            case Nether -> new BlockPos(pos.getX() * 8, pos.getY(), pos.getZ() * 8);
            default -> null;
        };
    }

    private void validateIcon() {
        Map<String, AbstractTexture> icons = Waypoints.get().icons;
        AbstractTexture texture = icons.get(iconSetting.get());
        if (texture == null && !icons.isEmpty()) {
            iconSetting.set(icons.keySet().iterator().next());
        }
    }

    public static class Builder {
        private String name = "", icon = "";

        private BlockPos pos = BlockPos.ORIGIN;

        private Dimension dimension = Dimension.Overworld;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder icon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder pos(BlockPos pos) {
            this.pos = pos;
            return this;
        }

        public Builder dimension(Dimension dimension) {
            this.dimension = dimension;
            return this;
        }

        public Waypoint build() {
            Waypoint waypoint = new Waypoint();
            if (!name.equals(waypoint.nameSetting.getDefaultValue())) {
                waypoint.nameSetting.set(name);
            }

            if (!icon.equals(waypoint.iconSetting.getDefaultValue())) {
                waypoint.iconSetting.set(icon);
            }

            if (!pos.equals(waypoint.posSetting.getDefaultValue())) {
                waypoint.posSetting.set(pos);
            }

            if (!dimension.equals(waypoint.dimensionSetting.getDefaultValue())) {
                waypoint.dimensionSetting.set(dimension);
            }

            return waypoint;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("settings", settings.toJson());
        return json;
    }

    @Override
    public Waypoint fromJson(JSONObject json) {
        if (json.has("settings")) {
            settings.fromJson(json.getJSONObject("settings"));
        }

        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Waypoint waypoint = (Waypoint) object;
        return Objects.equals(waypoint.nameSetting.get(), this.nameSetting.get());
    }
}
