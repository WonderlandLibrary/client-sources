package dev.nexus.modules.impl.movement;

import dev.nexus.Nexus;
import dev.nexus.events.bus.Listener;
import dev.nexus.events.bus.annotations.EventLink;
import dev.nexus.events.impl.EventLiving;
import dev.nexus.modules.Module;
import dev.nexus.modules.ModuleCategory;
import dev.nexus.modules.impl.player.Scaffold;
import net.minecraft.client.settings.KeyBinding;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", 0, ModuleCategory.MOVEMENT);
    }

    @EventLink
    public final Listener<EventLiving> eventLivingListener = event -> {
        if(Nexus.INSTANCE.getModuleManager().getModule(Scaffold.class).isEnabled()){
            return;
        }
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
    };
}
