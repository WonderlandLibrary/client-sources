package client.metaware.impl.module.render;

import client.metaware.Metaware;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.api.properties.property.impl.EnumProperty;

@ModuleInfo(name = "Animations", renderName = "Animations", description = "Sword/hit animations.", category = Category.VISUALS)
public class Animations extends Module {

    private final EnumProperty<AnimationsMode> animationsModeProperty = new EnumProperty("Mode", AnimationsMode.EXHIBITION);
    private final EnumProperty<HitMode> hitModeProperty = new EnumProperty("Hit Mode", HitMode.NORMAL);
    private final DoubleProperty scaleProperty = new DoubleProperty("Scale", 0.85, 0.5, 2, 0.05);

    @Override
    public void init() {
        super.init();
        addValueChangeListener(animationsModeProperty);
    }

    public static Animations instance() {
        return Metaware.INSTANCE.getModuleManager().getModuleByClass(Animations.class);
    }

    public enum AnimationsMode {
        VANILLA, OLD, SWANK, EXHIBITION, STELLA, KEKCLIENT, ETHEREAL, DORTWARE, LUNAR, AVATAR
    }

    public enum HitMode {
        NORMAL, SMOOTH
    }

    public EnumProperty<AnimationsMode> animationsModeProperty() {
        return animationsModeProperty;
    }
    public EnumProperty<HitMode> hitModeProperty() {
        return hitModeProperty;
    }

    public DoubleProperty scaleProperty() {
        return scaleProperty;
    }
}