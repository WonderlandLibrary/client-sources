package xyz.mathax.mathaxclient.systems.modules.world;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import xyz.mathax.mathaxclient.utils.world.BlockUtils;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

public class AirPlace extends Module {
    private HitResult hitResult;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup renderSettings = settings.createGroup("Render");

    // General

    private final Setting<Boolean> customRangeSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Custom range")
            .description("Use custom range for air place.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Double> rangeSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Range")
            .description("Custom range to place at.")
            .visible(customRangeSetting::get)
            .defaultValue(5)
            .min(0)
            .sliderRange(0, 6)
            .build()
    );

    // Render

    private final Setting<Boolean> renderSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Render a block overlay where the obsidian will be placed.")
            .defaultValue(true)
            .build()
    );

    private final Setting<ShapeMode> shapeModeSetting = renderSettings.add(new EnumSetting.Builder<ShapeMode>()
            .name("Shape mode")
            .description("How the shapes are rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );

    private final Setting<SettingColor> sideColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Side color")
            .description("The color of the sides of the blocks being rendered.")
            .defaultValue(new SettingColor(205, 0, 0, 75))
            .build()
    );

    private final Setting<SettingColor> lineColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Line color")
            .description("The color of the lines of the blocks being rendered.")
            .defaultValue(new SettingColor(205, 0, 0, 255))
            .build()
    );

    public AirPlace(Category category) {
        super(category, "Air Place", "Places a block where your crosshair is pointing at.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        double range = customRangeSetting.get() ? rangeSetting.get() : mc.interactionManager.getReachDistance();
        hitResult = mc.getCameraEntity().raycast(range, 0, false);
        if (!(hitResult instanceof BlockHitResult) || !(mc.player.getMainHandStack().getItem() instanceof BlockItem)) {
            return;
        }

        if (mc.options.useKey.isPressed()) {
            BlockUtils.place(((BlockHitResult) hitResult).getBlockPos(), Hand.MAIN_HAND, mc.player.getInventory().selectedSlot, false, 0, true, true, false);
        }
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        if (!(hitResult instanceof BlockHitResult) || !mc.world.getBlockState(((BlockHitResult) hitResult).getBlockPos()).getMaterial().isReplaceable() || !(mc.player.getMainHandStack().getItem() instanceof BlockItem) || !renderSetting.get()) {
            return;
        }

        event.renderer.box(((BlockHitResult) hitResult).getBlockPos(), sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get(), 0);
    }
}