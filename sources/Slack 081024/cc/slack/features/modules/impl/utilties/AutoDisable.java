package cc.slack.features.modules.impl.utilties;

import cc.slack.start.Slack;
import cc.slack.events.impl.player.WorldEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.impl.combat.KillAura;
import cc.slack.features.modules.impl.movement.Flight;
import cc.slack.features.modules.impl.player.TimerModule;
import cc.slack.features.modules.impl.render.Interface;
import cc.slack.features.modules.impl.world.InvManager;
import cc.slack.features.modules.impl.world.Scaffold;
import cc.slack.features.modules.impl.world.Stealer;
import io.github.nevalackin.radbus.Listen;


@ModuleInfo(
        name = "AutoDisable",
        category = Category.UTILITIES
)

public class AutoDisable extends Module {
    private final BooleanValue disableKA = new BooleanValue("Disable KillAura", false);
    private final BooleanValue disableInvManager = new BooleanValue("Disable InvManager",false);
    private final BooleanValue disableStealer = new BooleanValue("Disable Stealer",false);
    private final BooleanValue disableFlight = new BooleanValue("Disable Flight",false);
    private final BooleanValue disableScaffold = new BooleanValue("Disable Scaffold",false);
    private final BooleanValue disableTimer = new BooleanValue("Disable Timer",false);


    public AutoDisable() {
        addSettings(disableKA, disableInvManager, disableStealer, disableFlight, disableScaffold, disableTimer);
    }

    @SuppressWarnings("unused")
    @Listen
    public void onWorldChange (WorldEvent event) {
        Slack.getInstance().getModuleManager().getInstance(Interface.class).addNotification("AutoDisable:  Disabled modules on world change", "", 1500L, Slack.NotificationStyle.WARN);
        if (disableKA.getValue() && Slack.getInstance().getModuleManager().getInstance(KillAura.class).isToggle()) Slack.getInstance().getModuleManager().getInstance(KillAura.class).toggle();
        if (disableInvManager.getValue() && Slack.getInstance().getModuleManager().getInstance(InvManager.class).isToggle()) Slack.getInstance().getModuleManager().getInstance(InvManager.class).toggle();
        if (disableStealer.getValue() && Slack.getInstance().getModuleManager().getInstance(Stealer.class).isToggle()) Slack.getInstance().getModuleManager().getInstance(Stealer.class).toggle();
        if (disableFlight.getValue() && Slack.getInstance().getModuleManager().getInstance(Flight.class).isToggle()) Slack.getInstance().getModuleManager().getInstance(Flight.class).toggle();
        if (disableScaffold.getValue() && Slack.getInstance().getModuleManager().getInstance(Scaffold.class).isToggle()) Slack.getInstance().getModuleManager().getInstance(Scaffold.class).toggle();
        if (disableTimer.getValue() && Slack.getInstance().getModuleManager().getInstance(TimerModule.class).isToggle()) Slack.getInstance().getModuleManager().getInstance(TimerModule.class).toggle();
    }
}
