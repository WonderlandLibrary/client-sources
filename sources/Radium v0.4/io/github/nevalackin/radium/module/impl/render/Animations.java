package io.github.nevalackin.radium.module.impl.render;

import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.module.ModuleManager;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.property.impl.EnumProperty;

@ModuleInfo(label = "Animations", category = ModuleCategory.RENDER)
public final class Animations extends Module {

    public final EnumProperty<AnimationMode> animationModeProperty = new EnumProperty<>("Mode", AnimationMode.OLD);
    public final Property<Boolean> equipProgressProperty = new Property<>("Equip Progress", true);
    public final DoubleProperty equipProgMultProperty = new DoubleProperty("Equip Prog Multiplier", 2, 0.5, 3.0, 0.1);
    public final DoubleProperty itemScale = new DoubleProperty("Item Scale", 0.7, 0.0, 1.0, 0.05);
    public final DoubleProperty swingSpeed = new DoubleProperty("Swing Duration", 1.0, 0.1, 2.0, 0.1);
    public final DoubleProperty xPosProperty = new DoubleProperty("X", 0.0, -1, 1, 0.05);
    public final DoubleProperty yPosProperty = new DoubleProperty("Y", 0.0, -1, 1, 0.05);
    public final DoubleProperty zPosProperty = new DoubleProperty("Z", 0.0, -1, 1, 0.05);


    public static Animations getInstance() {
        return ModuleManager.getInstance(Animations.class);
    }

    public Animations() {
        toggle();
        setHidden(true);
    }

    public enum AnimationMode {
        SPINNY, OLD, EXHIBITION, ASTRO, REMIX, EXHIBOBO
    }

}
