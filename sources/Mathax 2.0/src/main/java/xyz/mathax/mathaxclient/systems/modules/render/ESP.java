package xyz.mathax.mathaxclient.systems.modules.render;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.Render2DEvent;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.renderer.Renderer2D;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.enemies.Enemies;
import xyz.mathax.mathaxclient.systems.friends.Friends;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.entity.EntityUtils;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;
import xyz.mathax.mathaxclient.utils.render.NametagUtils;
import xyz.mathax.mathaxclient.utils.render.WireframeEntityRenderer;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3d;

public class ESP extends Module {
    private final Color lineColor = new Color();
    private final Color sideColor = new Color();
    private final Color baseColor = new Color();

    private final Vector3d pos1 = new Vector3d();
    private final Vector3d pos2 = new Vector3d();
    private final Vector3d pos = new Vector3d();

    private int count;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup colorSettings = settings.createGroup("Colors");

    // General

    public final Setting<Mode> modeSetting = generalSettings.add(new EnumSetting.Builder<Mode>()
            .name("Mode")
            .description("Rendering mode.")
            .defaultValue(Mode.Shader)
            .build()
    );

    private final Setting<Object2BooleanMap<EntityType<?>>> entitiesSetting = generalSettings.add(new EntityTypeListSetting.Builder()
            .name("Entities")
            .description("Select specific entities.")
            .defaultValue(EntityType.PLAYER)
            .build()
    );

    public final Setting<Integer> outlineWidthSetting = generalSettings.add(new IntSetting.Builder()
            .name("Outline width")
            .description("The width of the shader outline.")
            .visible(() -> modeSetting.get() == Mode.Shader)
            .defaultValue(2)
            .range(1, 10)
            .sliderRange(1, 5)
            .build()
    );

    public final Setting<Double> glowMultiplierSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Glow multiplier")
            .description("Multiplier for the glow effect.")
            .visible(() -> modeSetting.get() == Mode.Shader)
            .decimalPlaces(3)
            .defaultValue(3.5)
            .min(0)
            .sliderRange(0, 10)
            .build()
    );

    public final Setting<Boolean> ignoreSelfSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Ignore self")
            .description("Ignores yourself drawing the shader.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Boolean> ignoreFriendsSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Ignore friends")
            .description("Ignores friends drawing the shader.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Boolean> ignoreEnemiesSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Ignore enemies")
            .description("Ignores enemies drawing the shader.")
            .defaultValue(true)
            .build()
    );

    public final Setting<ShapeMode> shapeModeSetting = generalSettings.add(new EnumSetting.Builder<ShapeMode>()
            .name("Shape mode")
            .description("How the shapes are rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );

    public final Setting<Double> fillOpacitySetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Fill opacity")
            .description("The opacity of the shape fill.")
            .visible(() -> shapeModeSetting.get() != ShapeMode.Lines)
            .defaultValue(0.8)
            .range(0, 1)
            .sliderRange(0, 1)
            .build()
    );

    private final Setting<Double> fadeDistanceSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("fade-distance")
            .description("The distance from an entity where the color begins to fade.")
            .defaultValue(3)
            .min(0)
            .sliderRange(0, 12)
            .build()
    );

    // Colors

    public final Setting<Boolean> distanceSetting = colorSettings.add(new BoolSetting.Builder()
            .name("Distance colors")
            .description("Change the color of tracers depending on distance.")
            .defaultValue(false)
            .build()
    );

    public final Setting<Boolean> friendOverride = colorSettings.add(new BoolSetting.Builder()
            .name("Show friend colors")
            .description("Whether or not to override the distance color of friends with the friend color.")
            .defaultValue(true)
            .visible(distanceSetting::get)
            .build()
    );

    private final Setting<SettingColor> playersColorSetting = colorSettings.add(new ColorSetting.Builder()
            .name("Players color")
            .description("The other player's color.")
            .defaultValue(new SettingColor(255, 255, 255))
            .visible(() -> !distanceSetting.get())
            .build()
    );

    private final Setting<SettingColor> animalsColorSetting = colorSettings.add(new ColorSetting.Builder()
            .name("Animals color")
            .description("The animal's color.")
            .defaultValue(new SettingColor(25, 255, 25))
            .visible(() -> !distanceSetting.get())
            .build()
    );

    private final Setting<SettingColor> waterAnimalsColorSetting = colorSettings.add(new ColorSetting.Builder()
            .name("Water animals color")
            .description("The water animal's color.")
            .defaultValue(new SettingColor(25, 25, 255))
            .visible(() -> !distanceSetting.get())
            .build()
    );

    private final Setting<SettingColor> monstersColorSetting = colorSettings.add(new ColorSetting.Builder()
            .name("Monsters color")
            .description("The monster's color.")
            .defaultValue(new SettingColor(255, 25, 25))
            .visible(() -> !distanceSetting.get())
            .build()
    );

    private final Setting<SettingColor> ambientColorSetting = colorSettings.add(new ColorSetting.Builder()
            .name("Ambient color")
            .description("The ambient's color.")
            .defaultValue(new SettingColor(25, 25, 25))
            .visible(() -> !distanceSetting.get())
            .build()
    );

    private final Setting<SettingColor> miscColorSetting = colorSettings.add(new ColorSetting.Builder()
            .name("Misc color")
            .description("The misc color.")
            .defaultValue(new SettingColor(175, 175, 175, 255))
            .visible(() -> !distanceSetting.get())
            .build()
    );

    public ESP(Category category) {
        super(category, "ESP", "Renders entities through walls.");
    }

    // Box

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (modeSetting.get() == Mode._2D) {
            return;
        }

        count = 0;

        for (Entity entity : mc.world.getEntities()) {
            if (shouldSkip(entity)) {
                continue;
            }

            if (modeSetting.get() == Mode.Box || modeSetting.get() == Mode.Wireframe) {
                drawBoundingBox(event, entity);
            }

            count++;
        }
    }

    private void drawBoundingBox(Render3DEvent event, Entity entity) {
        Color color = getColor(entity);
        lineColor.set(color);
        sideColor.set(color).a((int) (sideColor.a * fillOpacitySetting.get()));

        if (modeSetting.get() == Mode.Box) {
            double x = MathHelper.lerp(event.tickDelta, entity.lastRenderX, entity.getX()) - entity.getX();
            double y = MathHelper.lerp(event.tickDelta, entity.lastRenderY, entity.getY()) - entity.getY();
            double z = MathHelper.lerp(event.tickDelta, entity.lastRenderZ, entity.getZ()) - entity.getZ();

            net.minecraft.util.math.Box box = entity.getBoundingBox();
            event.renderer.box(x + box.minX, y + box.minY, z + box.minZ, x + box.maxX, y + box.maxY, z + box.maxZ, sideColor, lineColor, shapeModeSetting.get(), 0);
        } else {
            WireframeEntityRenderer.render(event, entity, 1, sideColor, lineColor, shapeModeSetting.get());
        }
    }

    // 2D

    @EventHandler
    private void onRender2D(Render2DEvent event) {
        if (modeSetting.get() != Mode._2D) {
            return;
        }

        Renderer2D.COLOR.begin();

        count = 0;

        for (Entity entity : mc.world.getEntities()) {
            if (shouldSkip(entity)) {
                continue;
            }

            Box box = entity.getBoundingBox();

            double x = MathHelper.lerp(event.tickDelta, entity.lastRenderX, entity.getX()) - entity.getX();
            double y = MathHelper.lerp(event.tickDelta, entity.lastRenderY, entity.getY()) - entity.getY();
            double z = MathHelper.lerp(event.tickDelta, entity.lastRenderZ, entity.getZ()) - entity.getZ();

            // Check corners
            pos1.set(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
            pos2.set(0, 0, 0);

            // Bottom
            if (checkCorner(box.minX + x, box.minY + y, box.minZ + z, pos1, pos2)) {
                continue;
            }

            if (checkCorner(box.maxX + x, box.minY + y, box.minZ + z, pos1, pos2)) {
                continue;
            }

            if (checkCorner(box.minX + x, box.minY + y, box.maxZ + z, pos1, pos2)) {
                continue;
            }

            if (checkCorner(box.maxX + x, box.minY + y, box.maxZ + z, pos1, pos2)) {
                continue;
            }

            // Top
            if (checkCorner(box.minX + x, box.maxY + y, box.minZ + z, pos1, pos2)) {
                continue;
            }

            if (checkCorner(box.maxX + x, box.maxY + y, box.minZ + z, pos1, pos2)) {
                continue;
            }

            if (checkCorner(box.minX + x, box.maxY + y, box.maxZ + z, pos1, pos2)) {
                continue;
            }

            if (checkCorner(box.maxX + x, box.maxY + y, box.maxZ + z, pos1, pos2)) {
                continue;
            }

            // Setup color
            Color color = getColor(entity);
            lineColor.set(color);
            sideColor.set(color).a((int) (sideColor.a * fillOpacitySetting.get()));

            // Render
            if (shapeModeSetting.get() != ShapeMode.Lines && sideColor.a > 0) {
                Renderer2D.COLOR.quad(pos1.x, pos1.y, pos2.x - pos1.x, pos2.y - pos1.y, sideColor);
            }

            if (shapeModeSetting.get() != ShapeMode.Sides) {
                Renderer2D.COLOR.line(pos1.x, pos1.y, pos1.x, pos2.y, lineColor);
                Renderer2D.COLOR.line(pos2.x, pos1.y, pos2.x, pos2.y, lineColor);
                Renderer2D.COLOR.line(pos1.x, pos1.y, pos2.x, pos1.y, lineColor);
                Renderer2D.COLOR.line(pos1.x, pos2.y, pos2.x, pos2.y, lineColor);
            }

            count++;
        }

        Renderer2D.COLOR.render(null);
    }

    private boolean checkCorner(double x, double y, double z, Vector3d min, Vector3d max) {
        pos.set(x, y, z);

        if (!NametagUtils.to2D(pos, 1)) {
            return true;
        }

        // Check Min
        if (pos.x < min.x) {
            min.x = pos.x;
        }

        if (pos.y < min.y) {
            min.y = pos.y;
        }

        if (pos.z < min.z) {
            min.z = pos.z;
        }

        // Check Max
        if (pos.x > max.x) {
            max.x = pos.x;
        }

        if (pos.y > max.y) {
            max.y = pos.y;
        }

        if (pos.z > max.z) {
            max.z = pos.z;
        }

        return false;
    }

    // Utils

    public boolean shouldSkip(Entity entity) {
        if (!entitiesSetting.get().getBoolean(entity.getType())) {
            return true;
        }

        if (entity instanceof PlayerEntity player) {
            if (player == mc.player && ignoreSelfSetting.get()) {
                return true;
            } else if (Friends.get().contains(player) && ignoreFriendsSetting.get()) {
                return true;
            } else if (Enemies.get().contains(player) && ignoreEnemiesSetting.get()) {
                return true;
            }
        }

        if (entity == mc.cameraEntity && mc.options.getPerspective().isFirstPerson()) {
            return true;
        }

        return !EntityUtils.isInRenderDistance(entity) || getFadeAlpha(entity) == 0;
    }

    public Color getColor(Entity entity) {
        if (!entitiesSetting.get().getBoolean(entity.getType())) {
            return null;
        }

        double alpha = getFadeAlpha(entity);
        if (alpha == 0) {
            return null;
        }

        Color color = getEntityTypeColor(entity);
        return baseColor.set(color.r, color.g, color.b, (int) (color.a * alpha));
    }

    private double getFadeAlpha(Entity entity) {
        double dist = PlayerUtils.distanceToCamera(entity.getX() + entity.getWidth() / 2, entity.getY() + entity.getEyeHeight(entity.getPose()), entity.getZ() + entity.getWidth() / 2);
        double fadeDist = Math.pow(fadeDistanceSetting.get(), 2);
        double alpha = 1;
        if (dist <= fadeDist) {
            alpha = (float) (dist / fadeDist);
        }

        if (alpha <= 0.075) {
            alpha = 0;
        }

        return alpha;
    }

    public Color getEntityTypeColor(Entity entity) {
        if (distanceSetting.get()) {
            if (friendOverride.get() && entity instanceof PlayerEntity && Friends.get().contains((PlayerEntity) entity)) {
                return Friends.get().colorSetting.get();
            } else {
                return EntityUtils.getColorFromDistance(entity);
            }
        } else if (entity instanceof PlayerEntity) {
            return PlayerUtils.getPlayerColor(((PlayerEntity) entity), playersColorSetting.get());
        } else {
            return switch (entity.getType().getSpawnGroup()) {
                case CREATURE -> animalsColorSetting.get();
                case WATER_AMBIENT, WATER_CREATURE, UNDERGROUND_WATER_CREATURE, AXOLOTLS -> waterAnimalsColorSetting.get();
                case MONSTER -> monstersColorSetting.get();
                case AMBIENT -> ambientColorSetting.get();
                default -> miscColorSetting.get();
            };
        }
    }

    public boolean isShader() {
        return isEnabled() && modeSetting.get() == Mode.Shader;
    }

    @Override
    public String getInfoString() {
        return Integer.toString(count);
    }

    public enum Mode {
        Box("Box"),
        Wireframe("Wireframe"),
        _2D("2D"),
        Shader("Shader");

        private final String name;

        Mode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}