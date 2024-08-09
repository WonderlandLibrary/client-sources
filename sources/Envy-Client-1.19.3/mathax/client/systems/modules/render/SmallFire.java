package mathax.client.systems.modules.render;

import mathax.client.settings.DoubleSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;
import org.joml.Vector3f;

public class SmallFire extends Module{
    private final SettingGroup sgScale = settings.createGroup("Scale");
    private final SettingGroup sgPosition = settings.createGroup("Position");

    // Scale

    private final Setting<Double> scaleX = sgScale.add(new DoubleSetting.Builder()
        .name("scale-x")
        .description("Zoom for fire on screen.")
        .defaultValue(0.0)
        .sliderRange(0.05, 1.0)
        .build()
    );

    private final Setting<Double> scaleY = sgScale.add(new DoubleSetting.Builder()
        .name("scale-y")
        .description("Zoom for fire on screen.")
        .defaultValue(0.2)
        .sliderRange(0.05, 1.0)
        .build()
    );

    private final Setting<Double> scaleZ = sgScale.add(new DoubleSetting.Builder()
        .name("Scale Z")
        .description("Zoom for fire on screen.")
        .defaultValue(0.0)
        .sliderRange(0.05, 1.0)
        .build()
    );

    // Position

    private final Setting<Double> positionX = sgPosition.add(new DoubleSetting.Builder()
        .name("position-x")
        .description("Offset for fire on screen.")
        .defaultValue(0.0)
        .sliderRange(-10.0, 10.0)
        .build()
    );

    private final Setting<Double> positionY = sgPosition.add(new DoubleSetting.Builder()
        .name("position-y")
        .description("Offset for fire on screen.")
        .defaultValue(-1.0)
        .sliderRange(-10.0, 10.0)
        .build()
    );

    private final Setting<Double> positionZ = sgPosition.add(new DoubleSetting.Builder()
        .name("Position Z")
        .description("Offset for fire on screen.")
        .defaultValue(0.0)
        .sliderRange(-10.0, 10.0)
        .build()
    );

    public SmallFire() {
        super(Categories.Render, Items.SPYGLASS, "small-fire", "Smalls fire on screen.");
    }

    public Vector3f getFireScale() { //your fired
        return new Vector3f(scaleX.get().floatValue(), scaleY.get().floatValue(), scaleZ.get().floatValue());
    }

    public Vector3f getFirePosition() {
        return new Vector3f(scaleX.get().floatValue(), scaleY.get().floatValue(), scaleZ.get().floatValue());
    }
}
