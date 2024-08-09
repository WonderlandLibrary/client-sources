package dev.excellent.client.module.impl.player;

import dev.excellent.api.event.impl.input.KeyboardPressEvent;
import dev.excellent.api.event.impl.input.KeyboardReleaseEvent;
import dev.excellent.api.event.impl.player.StrafeEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.module.impl.combat.KillAura;
import dev.excellent.client.rotation.FreeLookHandler;
import dev.excellent.client.rotation.Rotation;
import dev.excellent.client.rotation.RotationHandler;
import dev.excellent.impl.util.keyboard.Keyboard;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.KeyValue;
import net.minecraft.client.settings.PointOfView;
import org.joml.Vector2f;

@ModuleInfo(name = "Free Look", description = "Позволяет смотреть что происходит вокруг вас", category = Category.PLAYER)
public class FreeLook extends Module {
    private final KeyValue key = new KeyValue("Кнопка", this, -1);
    private final BooleanValue autoThirdPerson = new BooleanValue("Авто 3-е лицо", this, true);
    private final Vector2f rotation = new Vector2f();
    private PointOfView prevPointOfView = PointOfView.FIRST_PERSON;

    @Override
    protected void onEnable() {
        super.onEnable();
        if (KillAura.singleton.get().isEnabled() && KillAura.singleton.get().getTarget() != null) return;
        RotationHandler.update(new Rotation(mc.player.rotationYaw, mc.player.rotationPitch), 360, 0, 1);
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        if (KillAura.singleton.get().isEnabled() && KillAura.singleton.get().getTarget() != null) return;
        RotationHandler.update(new Rotation(mc.player.rotationYaw, mc.player.rotationPitch), 360, 0, 1);
    }

    private final Listener<StrafeEvent> onStrafe = event -> {
        if (KillAura.singleton.get().isEnabled() && KillAura.singleton.get().getTarget() != null || FreeCam.singleton.get().isEnabled())
            return;
        if (Keyboard.isKeyDown(key.getValue())) {
            RotationHandler.update(new Rotation(rotation.x, rotation.y), 360, 1, 1);
        }
    };

    private final Listener<KeyboardPressEvent> onKeyPress = event -> {
        if (KillAura.singleton.get().isEnabled() && KillAura.singleton.get().getTarget() != null) return;
        if (event.getScreen() == null && event.getKeyCode() == key.getValue()) {
            if (autoThirdPerson.getValue()) {
                prevPointOfView = mc.gameSettings.getPointOfView();
                mc.gameSettings.setPointOfView(PointOfView.THIRD_PERSON_BACK);
            }
            setRotation();
        }
    };
    private final Listener<KeyboardReleaseEvent> onKeyRelease = event -> {
        if (KillAura.singleton.get().isEnabled() && KillAura.singleton.get().getTarget() != null) return;
        if (event.getScreen() == null && event.getKeyCode() == key.getValue()) {
            if (autoThirdPerson.getValue()) {
                mc.gameSettings.setPointOfView(prevPointOfView);
            }

            if (FreeLookHandler.isActive()) {
                FreeLookHandler.setActive(false);
                mc.player.rotationYaw = rotation.x;
                mc.player.rotationPitch = rotation.y;
            }
        }
    };

    private void setRotation() {
        rotation.set(mc.player.rotationYaw, mc.player.rotationPitch);
    }
}
