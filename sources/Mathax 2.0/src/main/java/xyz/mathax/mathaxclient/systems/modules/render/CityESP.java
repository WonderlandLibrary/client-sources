package xyz.mathax.mathaxclient.systems.modules.render;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.settings.ColorSetting;
import xyz.mathax.mathaxclient.settings.EnumSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.entity.EntityUtils;
import xyz.mathax.mathaxclient.utils.entity.SortPriority;
import xyz.mathax.mathaxclient.utils.entity.TargetUtils;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class CityESP extends Module {
    private BlockPos target;

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<ShapeMode> shapeModeSetting = generalSettings.add(new EnumSetting.Builder<ShapeMode>()
            .name("Shape mode")
            .description("Determines how the shapes are rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );

    private final Setting<SettingColor> sideColorSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Side color")
            .description("The side color of the rendering.")
            .defaultValue(new SettingColor(Color.MATHAX, 75))
            .build()
    );

    private final Setting<SettingColor> lineColorSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Line color")
            .description("The line color of the rendering.")
            .defaultValue(new SettingColor(Color.MATHAX))
            .build()
    );

    public CityESP(Category category) {
        super(category, "City ESP", "Displays blocks that can be broken in order to city another player.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        PlayerEntity targetEntity = TargetUtils.getPlayerTarget(mc.interactionManager.getReachDistance() + 2, SortPriority.Lowest_Distance);
        if (TargetUtils.isBadTarget(targetEntity, mc.interactionManager.getReachDistance() + 2)) {
            target = null;
        } else {
            target = EntityUtils.getCityBlock(targetEntity);
        }
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (target == null) {
            return;
        }

        event.renderer.box(target, sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get(), 0);
    }
}
