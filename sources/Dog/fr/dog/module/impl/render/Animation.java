package fr.dog.module.impl.render;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerNetworkTickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.ModeProperty;
import fr.dog.property.impl.NumberProperty;

public class Animation extends Module {
    public static final ModeProperty mode = ModeProperty.newInstance("Block Mode", new String[]{"1.8","1.7", "Smooth","Exhi","Spin","Spin2"}, "1.8");
    public static final NumberProperty animationSpeed = NumberProperty.newInstance("Animation Speed",0.1f, 1f, 2f, 0.1f );
    public Animation() {
        super("Animations", ModuleCategory.RENDER);
        this.registerProperties(mode,animationSpeed);
    }
    @SubscribeEvent
    private void onPlayerNetworkTickEvent(PlayerNetworkTickEvent event) {
        this.setSuffix(mode.getValue());
    }
}
