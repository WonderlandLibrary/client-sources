package arsenic.module.impl.combat;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventAttack;
import arsenic.event.impl.EventTick;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.impl.BooleanProperty;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "AutoCombo", category = ModuleCategory.Combat)
public class AutoCombo extends Module {
    boolean attacked;
    public final BooleanProperty wtapmodule = new BooleanProperty("W-Tap", false);
    @EventLink
    public final Listener<EventAttack> eventAttackListener = eventAttack -> {
        attacked = true;
    };

    @EventLink
    public final Listener<EventTick> onTick = event -> {
        if (wtapmodule.getValue()) {
            if (mc.thePlayer.isSprinting() && Keyboard.isKeyDown(Keyboard.KEY_W)) {
                if (attacked) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), Keyboard.isKeyDown(Keyboard.KEY_W));
                    attacked = false;
                }
            }
        }
    };

    @Override
    protected void onEnable() {
        attacked = false;
        super.onEnable();
    }
}
