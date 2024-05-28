package arsenic.module.impl.movement;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventTick;
import arsenic.main.Nexus;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.impl.EnumProperty;
import net.minecraft.client.settings.KeyBinding;

@ModuleInfo(name = "Sprint", category = ModuleCategory.Movement)
//KEY_V more like KV // kv pls stop i beg
public class Sprint extends Module {
    public final EnumProperty<sMode> sprintMode = new EnumProperty<>("Mode: ", sMode.Legit);

    @EventLink
    public final Listener<EventTick> onTick = event -> {
        if (scaffoldDisabler()) {
            return;
        }
        sprintMode.getValue().setSprinting();
    };

    @Override
    protected void onDisable() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
        mc.thePlayer.setSprinting(false);
    }

    private boolean scaffoldDisabler() {
        Scaffold scaffold = Nexus.getNexus().getModuleManager().getModuleByClass(Scaffold.class);
        if (scaffold.isEnabled()) {
            return true;
        }
        return false;
    }

    public enum sMode {
        Omni(() -> mc.thePlayer.setSprinting(true)),
        Legit(() -> KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true));

        private final Runnable v;

        sMode(Runnable v) {
            this.v = v;
        }

        public void setSprinting() {
            v.run();
        }
    }
}
