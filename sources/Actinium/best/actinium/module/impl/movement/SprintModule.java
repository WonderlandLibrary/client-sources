package best.actinium.module.impl.movement;

import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.UpdateEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.BooleanProperty;
import best.actinium.util.IAccess;
import best.actinium.util.player.MoveUtil;
import best.actinium.Actinium;
import best.actinium.module.impl.movement.scaffold.ScaffoldWalkModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjglx.input.Keyboard;

@ModuleInfo(
        name = "Sprint",
        description = "Sprints automatically for you.",
        category = ModuleCategory.MOVEMENT,
        autoEnabled = true
)
public class SprintModule extends Module {

    public BooleanProperty legit = new BooleanProperty("Legit", this, true);

    @Callback
    public void onUpdate(UpdateEvent event) {
        if(Actinium.INSTANCE.getModuleManager().get(ScaffoldWalkModule.class).isEnabled()) {
            KeyBinding.setKeyBindState(IAccess.mc.gameSettings.keyBindSprint.getKeyCode(), false);
            return;
        }

        if (legit.isEnabled())
            KeyBinding.setKeyBindState(IAccess.mc.gameSettings.keyBindSprint.getKeyCode(), true);
        else
            Minecraft.getMinecraft().thePlayer.setSprinting(MoveUtil.isMoving());
    }

    @Override
    public void onDisable() {
        super.onDisable();

        KeyBinding.setKeyBindState(
                IAccess.mc.gameSettings.keyBindSprint.getKeyCode(),
                Keyboard.isKeyDown(IAccess.mc.gameSettings.keyBindSprint.getKeyCode())
        );
    }
}