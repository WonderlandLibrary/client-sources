package dev.zprestige.prestige.client.module.impl.visuals;

import dev.zprestige.prestige.client.event.EventListener;
import dev.zprestige.prestige.client.event.impl.Render2DEvent;
import dev.zprestige.prestige.client.module.Category;
import dev.zprestige.prestige.client.module.Module;
import dev.zprestige.prestige.client.setting.impl.BooleanSetting;
import dev.zprestige.prestige.client.setting.impl.FloatSetting;
import net.minecraft.client.network.ClientPlayerEntity;

public class ItemSpinner extends Module {

    public BooleanSetting enabled;
    public FloatSetting rotationSpeed;
    private float rotationAngle;

    public ItemSpinner() {
        super("ItemSpinner", Category.Visual, "Spins the item in ur hand.");
        enabled = setting("Enabled", true);
        rotationSpeed = setting("Rotation Speed", 5.0f, 1.0f, 20.0f);
        rotationAngle = 0;
    }

    @EventListener
    public void onRender(Render2DEvent event) {
        if (!enabled.getObject() || getMc().currentScreen != null || !getMc().isWindowFocused()) {
            return;
        }

        ClientPlayerEntity player = getMc().player;
        if (player != null && player.getMainHandStack().getItem() != null) {
            rotationAngle += rotationSpeed.getObject();
            if (rotationAngle >= 360) {
                rotationAngle -= 360;
            }
        }
    }
}