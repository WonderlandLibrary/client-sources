// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement;

import cc.slack.events.impl.input.onMoveInputEvent;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.*;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.features.modules.impl.movement.speeds.hypixel.HypixelBasicSpeed;
import cc.slack.features.modules.impl.movement.speeds.hypixel.HypixelHopSpeed;
import cc.slack.features.modules.impl.movement.speeds.ncp.NCPHopSpeed;
import cc.slack.features.modules.impl.movement.speeds.ncp.OldNCPSpeed;
import cc.slack.features.modules.impl.movement.speeds.vanilla.*;
import cc.slack.features.modules.impl.movement.speeds.verus.VerusGroundSpeed;
import cc.slack.features.modules.impl.movement.speeds.verus.VerusHopSpeed;
import cc.slack.features.modules.impl.movement.speeds.verus.VerusLowHopSpeed;
import cc.slack.features.modules.impl.movement.speeds.vulcan.VulcanHopSpeed;
import cc.slack.features.modules.impl.movement.speeds.vulcan.VulcanLowSpeed;
import cc.slack.features.modules.impl.movement.speeds.vulcan.VulcanPortSpeed;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.item.ItemFood;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "Speed",
        category = Category.MOVEMENT
)
public class Speed extends Module {

    private final ModeValue<ISpeed> mode = new ModeValue<>(new ISpeed[]{

            // Vanilla
            new VanillaSpeed(),
            new StrafeSpeed(),
            new GroundStrafeSpeed(),
            new LegitSpeed(),

            // Verus
            new VerusHopSpeed(),
            new VerusLowHopSpeed(),
            new VerusGroundSpeed(),

            // Hypixel
            new HypixelHopSpeed(),
            new HypixelBasicSpeed(),

            // NCP
            new NCPHopSpeed(),
            new OldNCPSpeed(),

            // Vulcan
            new VulcanLowSpeed(),
            new VulcanPortSpeed(),
//            new Vulcan2PortSpeed(),
            new VulcanHopSpeed(),

            new FakeStrafeSpeed()
    });

    public final NumberValue<Float> vanillaspeed = new NumberValue<>("Vanilla Speed", 1.0F, 0.0F, 3.0F, 0.01F);
    public final BooleanValue vanillaGround = new BooleanValue("Vanilla Only Ground", false);
    public final BooleanValue hypixelSemiStrafe = new BooleanValue("Hypixel Semi Strafe", false);
    public final BooleanValue hypixelTest = new BooleanValue("Hypixel Test", false);
    public final BooleanValue hypixelGlide = new BooleanValue("Hypixel Glide", true);


    public final BooleanValue nosloweat = new BooleanValue("NoSlow when Speed", true);
    public final BooleanValue jumpFix = new BooleanValue("Jump Fix", true);

    // Display
    private final ModeValue<String> displayMode = new ModeValue<>("Display", new String[]{"Simple", "Off"});

    public Speed() {
        super();
        addSettings(mode, vanillaspeed, vanillaGround, hypixelSemiStrafe, hypixelTest, hypixelGlide, nosloweat, jumpFix, displayMode);
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
        mc.timer.timerSpeed = 1f;
        if (jumpFix.getValue()) { mc.gameSettings.keyBindJump.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindJump); }
        mode.getValue().onDisable();
    }

    @Listen
    public void onMove(MoveEvent event) {
        if (!nosloweat.getValue() && mc.thePlayer.isUsingItem() && (mc.thePlayer.getHeldItem().item instanceof ItemFood)) { return; }
        mode.getValue().onMove(event);
    }

    @Listen
    public void onUpdate(UpdateEvent event) {
        if (!nosloweat.getValue() && mc.thePlayer.isUsingItem() && (mc.thePlayer.getHeldItem().item instanceof ItemFood)) { return; }
        if (jumpFix.getValue()) { mc.gameSettings.keyBindJump.pressed = false; }
        mode.getValue().onUpdate(event);
    }

    @Listen
    public void onMotion(MotionEvent event) {
        if (!nosloweat.getValue() && mc.thePlayer.isUsingItem() && (mc.thePlayer.getHeldItem().item instanceof ItemFood)) { return; }
        mode.getValue().onMotion(event);
    }

    @Listen
    public void onPacket(PacketEvent event) {

        mode.getValue().onPacket(event);
    }

    @Listen
    public void onMoveInput(onMoveInputEvent event) {
        mode.getValue().onMoveInput(event);
    }

    @Listen
    public void onStrafe(StrafeEvent event) {
        mode.getValue().onStrafe(event);
    }

    @Listen
    public void onJump(JumpEvent event) {
        mode.getValue().onJump(event);
    }

    @Override
    public String getMode() {
        return mode.getValue().toString();
    }
}
