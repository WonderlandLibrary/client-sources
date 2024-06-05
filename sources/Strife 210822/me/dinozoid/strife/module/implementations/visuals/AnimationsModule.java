package me.dinozoid.strife.module.implementations.visuals;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.module.implementations.combat.KillAuraModule;
import me.dinozoid.strife.property.Property;
import me.dinozoid.strife.property.implementations.DoubleProperty;
import me.dinozoid.strife.property.implementations.EnumProperty;

@ModuleInfo(name = "Animations", renderName = "Animations", description = "Sword/hit animations.", category = Category.VISUALS)
public class AnimationsModule extends Module {

    private final EnumProperty<AnimationsMode> animationsModeProperty = new EnumProperty("Mode", AnimationsMode.EXHIBITION);
    private final EnumProperty<HitMode> hitModeProperty = new EnumProperty("Hit Mode", HitMode.NORMAL);
    private final DoubleProperty scaleProperty = new DoubleProperty("Scale", 0.85, 0.5, 1, 0.05);

    @Override
    public void init() {
        super.init();
        addValueChangeListener(animationsModeProperty);
    }

    public static AnimationsModule instance() {
        return StrifeClient.INSTANCE.moduleRepository().moduleBy(AnimationsModule.class);
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
