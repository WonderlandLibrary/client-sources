package dev.excellent.client.module.impl.player;

import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.event.impl.player.MoveInputEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.player.PlayerUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.NumberValue;
import net.minecraft.block.AirBlock;

@ModuleInfo(name = "Eagle", description = "Автоматически нажимает на шифт у края блока", category = Category.PLAYER)
public class Eagle extends Module {
    private final NumberValue sneakSpeed = new NumberValue("Скорость приседа", this, 0.3, 0.1, 1, 0.1);
    private final BooleanValue groundOnly = new BooleanValue("Только на земле", this, false);
    private boolean sneaked;
    private int ticksOverEdge;

    @Override
    protected void onDisable() {
        super.onDisable();
        if (sneaked) {
            sneaked = false;
        }
    }

    public final Listener<MotionEvent> onMotion = event -> {
        if ((mc.player.isOnGround() || !groundOnly.getValue()) &&
                (PlayerUtil.blockRelativeToPlayer(0, -1, 0) instanceof AirBlock)) {
            if (!sneaked) {
                sneaked = true;
            }
        } else if (sneaked) {
            sneaked = false;
        }

        if (sneaked) {
            mc.gameSettings.keyBindSprint.setPressed(false);
        }

        if (sneaked) {
            ticksOverEdge++;
        } else {
            ticksOverEdge = 0;
        }
    };


    public final Listener<MoveInputEvent> onMove = event -> {
        event.setSneaking(sneaked);

        if (sneaked && ticksOverEdge <= 2) {
            event.setSneakSlowDownMultiplier(sneakSpeed.getValue().doubleValue());
        }
    };
}
