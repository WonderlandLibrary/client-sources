// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.combat;

import cc.slack.start.Slack;
import cc.slack.events.impl.game.TickEvent;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.CollideEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.MoveEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.features.modules.impl.combat.velocitys.IVelocity;
import cc.slack.features.modules.impl.combat.velocitys.impl.*;
import cc.slack.features.modules.impl.movement.Flight;
import io.github.nevalackin.radbus.Listen;

@ModuleInfo(
        name = "Velocity",
        category = Category.COMBAT
)

public class Velocity extends Module {

    private final ModeValue<IVelocity> mode = new ModeValue<>(new IVelocity[]{

            // Vanilla
            new CancelVelocity(),
            new MotionVelocity(),

            // Hypixel
            new HypixelAirVelocity(),
            new HypixelVelocity(),
            new HypixelStrafeVelocity(),

            // Vulcan
            new VulcanVelocity(),

            // Special
            new ReverseVelocity(),
            new ConditionalVelocity(),
            new TickVelocity()
    });

    public final NumberValue<Integer> vertical = new NumberValue<>("Vertical", 100, 0, 100, 1);
    public final NumberValue<Integer> horizontal = new NumberValue<>("Horizontal", 0, 0, 100, 1);
    public final NumberValue<Integer> velocityTick = new NumberValue<>("Velocity Tick", 5, 0, 20, 1);
    private final BooleanValue onlyground = new BooleanValue("Only Ground", false);
    private final BooleanValue noFire = new BooleanValue("No Fire", false);

    // Display
    private final ModeValue<String> displayMode = new ModeValue<>("Display", new String[]{"Simple", "Off"});


    public Velocity() {
        super();
        addSettings(mode, vertical, horizontal, velocityTick, onlyground, noFire, displayMode);
    }

    @Override
    public void onEnable() {
        mode.getValue().onEnable();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1F;
        mode.getValue().onDisable();
    }

    @Listen
    public void onMove(MoveEvent event) {
        mode.getValue().onMove(event);
    }

    @Listen
    public void onCollide(CollideEvent event) {
        mode.getValue().onCollide(event);
    }

    @Listen
    public void onMotion(MotionEvent event) {
        mode.getValue().onMotion(event);
    }

    @Listen
    public void onPacket(PacketEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null) return;
        if (noFire.getValue() && mc.thePlayer.isBurning()) return;

        if (onlyground.getValue() && !mc.thePlayer.onGround) {
            return;
        }

        if (Slack.getInstance().getModuleManager().getInstance(Flight.class).isToggle() && Slack.getInstance().getModuleManager().getInstance(Flight.class).mode.getValue().toString() == "Fireball Flight") {
            return;
        }

        mode.getValue().onPacket(event);

    }

    @Listen
    public void onTick (TickEvent event) {
        mode.getValue().onTick(event);
    }

    @Listen
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer.isInWater() || mc.thePlayer.isInLava() || mc.thePlayer.isInWeb) {
            return;
        }

        mode.getValue().onUpdate(event);
    }

    @Override
    public String getMode() {
        switch (displayMode.getValue()) {
            case "Simple":
                return mode.getValue().toString();
        }
        return null;
    }

}
