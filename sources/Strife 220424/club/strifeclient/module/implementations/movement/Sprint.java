package club.strifeclient.module.implementations.movement;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.event.implementations.player.MoveEvent;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.implementations.BooleanSetting;
import club.strifeclient.util.player.MovementUtil;
import org.lwjglx.input.Keyboard;

import java.util.function.Supplier;

@ModuleInfo(name = "Sprint", description = "Automatically sprints for you.", keybind = Keyboard.KEY_F, category = Category.MOVEMENT)
public final class Sprint extends Module {

    private final BooleanSetting omniSetting = new BooleanSetting("Omni", false);

    @Override
    public Supplier<Object> getSuffix() {
        return () -> omniSetting.getValue() ? omniSetting.getName() : null;
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        mc.thePlayer.setSprinting(mc.gameSettings.keyBindSprint.isKeyDown());
    }

    @EventHandler
    private final Listener<MoveEvent> moveEventListener = e -> mc.thePlayer.setSprinting(MovementUtil.canSprint(omniSetting.getValue()));
}
