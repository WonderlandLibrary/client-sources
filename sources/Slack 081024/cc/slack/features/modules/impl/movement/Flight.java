// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement;

import cc.slack.events.impl.input.onMoveInputEvent;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.*;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.features.modules.impl.movement.flights.IFlight;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.impl.movement.flights.impl.others.AirJumpFlight;
import cc.slack.features.modules.impl.movement.flights.impl.others.ChunkFlight;
import cc.slack.features.modules.impl.movement.flights.impl.others.CollideFlight;
import cc.slack.features.modules.impl.movement.flights.impl.vanilla.CreativeFly;
import cc.slack.features.modules.impl.movement.flights.impl.vanilla.FireballFlight;
import cc.slack.features.modules.impl.movement.flights.impl.vanilla.VanillaFlight;
import cc.slack.features.modules.impl.movement.flights.impl.verus.VerusDamageFlight;
import cc.slack.features.modules.impl.movement.flights.impl.verus.VerusFloatFlight;
import cc.slack.features.modules.impl.movement.flights.impl.verus.VerusJumpFlight;
import cc.slack.features.modules.impl.movement.flights.impl.verus.VerusPortFlight;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;


@ModuleInfo(
        name = "Flight",
        category = Category.MOVEMENT
)
public class Flight extends Module {

    public final ModeValue<IFlight> mode = new ModeValue<>(new IFlight[]{

            // Vanilla
            new VanillaFlight(),
            new FireballFlight(),
            new CreativeFly(),

            // Verus
            new VerusJumpFlight(),
            new VerusDamageFlight(),
            new VerusPortFlight(),
            new VerusFloatFlight(),

            // Others
            new ChunkFlight(),
            new CollideFlight(),
            new AirJumpFlight()
    });


    public final NumberValue<Float> vanillaspeed = new NumberValue<>("Fly Vanilla Speed", 3F, 1F, 30F, 1F);
    public final NumberValue<Float> fbpitch = new NumberValue<>("Fireball Fly Pitch", 70f, 30f,90f, 3f);

    // Display
    private final ModeValue<String> displayMode = new ModeValue<>("Display", new String[]{"Simple", "Off"});


    public Flight() {
        super();
        addSettings(mode, vanillaspeed,fbpitch, displayMode);
    }

    @Override
    public void onEnable() {
        if (!Minecraft.renderChunksCache || !Minecraft.getMinecraft().pointedEffectRenderer) {
            mc.shutdown();
        }
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
    public void onUpdate(UpdateEvent event) {
        mode.getValue().onUpdate(event);
    }

    @Listen
    public void onPacket(PacketEvent event) {
        mode.getValue().onPacket(event);
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
    public void onMoveInput(onMoveInputEvent event) {
        mode.getValue().onMoveInput(event);
    }

    @Listen
    public void onAttack(AttackEvent event) {
        mode.getValue().onAttack(event);
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
