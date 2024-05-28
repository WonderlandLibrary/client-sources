package arsenic.module.impl.player;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventTick;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.impl.EnumProperty;
import arsenic.utils.minecraft.PlayerUtils;
import net.minecraft.client.settings.KeyBinding;


@ModuleInfo(name = "SafeWalk", category = ModuleCategory.Player)
public class SafeWalk extends Module {
    public final EnumProperty<sMode> mode = new EnumProperty<>("Mode: ", sMode.S_SHIFT);
    @EventLink
    public final Listener<EventTick> onTick = event -> {
        if(mode.getValue() == sMode.S_SHIFT)
            setShift(PlayerUtils.playerOverAir() && mc.thePlayer.onGround);
    };
    @Override
    public void onDisable() {
        setShift(false);
    }
    private void setShift(boolean sh) {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), sh);
    }

    public enum sMode {
        S_SHIFT
    }
}
