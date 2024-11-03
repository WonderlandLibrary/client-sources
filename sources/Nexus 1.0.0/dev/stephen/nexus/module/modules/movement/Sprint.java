package dev.stephen.nexus.module.modules.movement;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.mixin.accesors.GameOptionsAccessor;
import dev.stephen.nexus.mixin.accesors.KeyBindingAccessor;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.modules.combat.Criticals;
import dev.stephen.nexus.module.modules.player.Scaffold;
import dev.stephen.nexus.module.setting.impl.BooleanSetting;
import net.minecraft.client.option.KeyBinding;

public class Sprint extends Module {
    public static BooleanSetting rage = new BooleanSetting("Rage", false);

    public Sprint() {
        super("Sprint", "Sprints for you", 0, ModuleCategory.MOVEMENT);
        this.addSetting(rage);
    }

    public static boolean shouldSprintDiagonally() {
        if (Client.INSTANCE.getModuleManager().getModule(Scaffold.class).isEnabled())
            return false;
        return rage.getValue();
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        this.setSuffix(rage.getValue() ? "Rage" : "Legit");
        if (Client.INSTANCE.getModuleManager().getModule(Scaffold.class).isEnabled())
            return;

        ((GameOptionsAccessor) mc.options).getSprintToggled().setValue(false);
        KeyBinding.setKeyPressed(((KeyBindingAccessor) mc.options.sprintKey).getBoundKey(), true);
    };
}
