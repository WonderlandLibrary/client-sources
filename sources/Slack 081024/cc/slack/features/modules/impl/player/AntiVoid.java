// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.player;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.*;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.features.modules.impl.player.antivoids.IAntiVoid;
import cc.slack.features.modules.impl.player.antivoids.impl.PolarAntiVoid;
import cc.slack.features.modules.impl.player.antivoids.impl.SelfTPAntiVoid;
import cc.slack.features.modules.impl.player.antivoids.impl.UniversalAntiVoid;
import cc.slack.features.modules.impl.player.antivoids.impl.VulcanAntiVoid;
import cc.slack.utils.player.BlinkUtil;
import cc.slack.utils.network.PacketUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.Vec3;


@ModuleInfo(
        name = "Antivoid",
        category = Category.PLAYER
)
public class AntiVoid extends Module {


    private final ModeValue<IAntiVoid> mode = new ModeValue<>(new IAntiVoid[] {

            // Normal

            new UniversalAntiVoid(),
            new SelfTPAntiVoid(),
            new PolarAntiVoid(),

            // Vulcan
            new VulcanAntiVoid()


    });


    // Vulcan Antivoid
    public final NumberValue<Float> vulcandistance = new NumberValue<>("Vulcan Distance", 2.6F, 0F, 15F, 0.1F);
    // Display
    private final ModeValue<String> displayMode = new ModeValue<>("Display", new String[]{"Simple", "Off"});



    public AntiVoid() {
        super();
        addSettings(mode, vulcandistance, displayMode);
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
    public void onStrafe(StrafeEvent event) {
        mode.getValue().onStrafe(event);
    }

    @Listen
    public void onWorld(WorldEvent event) {
        mode.getValue().onWorld(event);
    }

    @Listen
    public void onJump(JumpEvent event) {
        mode.getValue().onJump(event);
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
