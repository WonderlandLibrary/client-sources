package mathax.client.systems.modules.render;

import mathax.client.MatHax;
import mathax.client.settings.ColorSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.render.color.SettingColor;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;

public class Confetti extends Module {
    private final SettingGroup sgGeneral = settings.createGroup("Colors");

    // Colors

    private final Setting<SettingColor> colorOne = sgGeneral.add(new ColorSetting.Builder()
        .name("first")
        .description("The first confetti color to change.")
        .defaultValue(new SettingColor(MatHax.INSTANCE.MATHAX_COLOR.r, MatHax.INSTANCE.MATHAX_COLOR.g, MatHax.INSTANCE.MATHAX_COLOR.b))
        .build()
    );

    private final Setting<SettingColor> colorTwo = sgGeneral.add(new ColorSetting.Builder()
        .name("second")
        .description("The second confetti color to change.")
        .defaultValue(new SettingColor(MatHax.INSTANCE.MATHAX_BACKGROUND_COLOR.r, MatHax.INSTANCE.MATHAX_BACKGROUND_COLOR.g, MatHax.INSTANCE.MATHAX_BACKGROUND_COLOR.b))
        .build()
    );

    public Confetti() {
        super(Categories.Render, Items.TOTEM_OF_UNDYING, "Confetti", "Changes the color of the totem pop particles.");
    }
    //does this work
    public Vec3d getColorOne() {
        return getDoubleVectorColor(colorOne);
    }

    public Vec3d getColorTwo() {
        return getDoubleVectorColor(colorTwo);
    }

    public Vec3d getDoubleVectorColor(Setting<SettingColor> colorSetting) {
        return new Vec3d(colorSetting.get().r / 255.0, colorSetting.get().g / 255.0, colorSetting.get().b / 255.0);
    }
}
