package fr.dog.module.impl.player;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.world.TickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.NumberProperty;

public class FastBreak extends Module {

    private final NumberProperty multiplier = NumberProperty.newInstance("Multiplier", 1.0f, 1.45f, 3.0f, 0.05f);

    public FastBreak() {
        super("FastBreak", ModuleCategory.PLAYER);

        this.registerProperty(multiplier);
    }

    @SubscribeEvent
    private void onTick(TickEvent event) {
        this.setSuffix(String.valueOf(multiplier.getValue()));
        final double multiplier = this.multiplier.getValue();
        final float damage = mc.playerController.curBlockDamageMP;

        if (multiplier > 1.0) {
            final double endPoint = 1.0 / multiplier;

            if (damage < 1.0f && damage >= endPoint) {
                mc.playerController.curBlockDamageMP = 1;
            }
        }
    }
}
