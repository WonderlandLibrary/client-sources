package club.bluezenith.module.modules.movement;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.CollisionEvent;
import club.bluezenith.events.impl.MoveEvent;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.combat.Aura;
import club.bluezenith.module.modules.combat.TargetStrafe;
import club.bluezenith.module.modules.movement.flight.*;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.module.value.types.IntegerValue;
import club.bluezenith.module.value.types.ModeValue;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

import java.util.concurrent.TimeUnit;

import static club.bluezenith.module.value.builders.AbstractBuilder.createBoolean;
import static club.bluezenith.module.value.builders.AbstractBuilder.createFloat;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class Flight extends Module {

    final IFlight vanillaMode = new Vanilla();
    public final IFlight oldVerusMode = new OldVerus();
    final IFlight collision = new Collision();

    IFlight flightMode;
    public final ModeValue mode = new ModeValue("Mode", "Vanilla", "Vanilla", "OldVerus", "Collision", "Watchdog").setIndex(1)
            .setValueChangeListener((old, new_) -> {
                switch (new_) {
                    case "Vanilla":
                        flightMode = vanillaMode;
                    break;

                    case "OldVerus":
                        flightMode = oldVerusMode;
                    break;

                    case "Collision":
                        flightMode = collision;
                    break;

                    case "Watchdog":
                        flightMode = new Watchdog();
                    break;

                    default:
                        flightMode = null;
                    break;
                }
                return new_;
            });

    public final IntegerValue viewBobbing = new IntegerValue("View Bobbing", 30, 0, 100, 5).showIf(() -> mode.is("Vanilla")).setIndex(2);
    public final BooleanValue fakeDamage = new BooleanValue("Fake Damage", false).showIf(() -> !mode.is("OldVerus")).setIndex(3);
    public final FloatValue speed = new FloatValue("Speed", 2f, 0f, 7f, 0.1f)
            .showIf(() -> (mode.is("Vanilla") || mode.is("Collision"))).setIndex(4); //example of a visibility modifier
    public final BooleanValue sameY = new BooleanValue("Keep Y", false).setIndex(5).showIf(() -> mode.is("Collision"));
    public final BooleanValue kickByepase = new BooleanValue("Float", false).setIndex(5).showIf(() -> mode.is("Vanilla"));
    public final FloatValue motionYOnStart = new FloatValue("Jump height", 0, 0, 3, 0.02F).setIndex(6).showIf(() -> mode.is("Collision"));
    public final FloatValue wdDelay = createFloat("Delay").min(0.8F).max(3F).showIf(() -> mode.is("Watchdog"))
            .increment(0.05F).index(7).defaultOf(1.2F).build();
    public final FloatValue wdDistance = createFloat("Distance").min(0.5F).max(8F).showIf(() -> mode.is("Watchdog"))
            .increment(0.05F).index(8).defaultOf(8F).build();
    public final BooleanValue wdSmart = createBoolean("Smart").index(8).defaultOf(true).showIf(() -> mode.is("Watchdog"))
            .build();
    public final BooleanValue wdRequireKeypress = createBoolean("Require keypress").showIf(() -> mode.is("Watchdog"))
            .defaultOf(false).index(9).build();
    public final BooleanValue lagbackCheck = createBoolean("Lagback Check")
            .defaultOf(false).index(10).build();
    public final BooleanValue sigmaFly = createBoolean("Sigma fast fly").index(10).build();

    public Flight() {
        super("Flight", ModuleCategory.MOVEMENT);
    }



    float prevFOV = -1;
    @Listener
    public void onUpdate(UpdatePlayerEvent e) {
        if(e.isPost())
            mc.thePlayer.cameraYaw = viewBobbing.get()/1000f;
        if(check()) return;
        flightMode.onPlayerUpdate(e, this);

    }
    @Listener
    public void onMove(MoveEvent e) {
        if(check()) return;
        flightMode.onMoveEvent(e, this);
    }

    @Listener
    public void onBlockBB(CollisionEvent e) {
        if(check()) return;
        flightMode.onCollision(e, this);
    }

    @Override
    public void onEnable() {
        if(check()) return;
        if(sigmaFly.get()) {
            prevFOV = mc.gameSettings.fovSetting;
            mc.gameSettings.fovSetting = 145;
        }
        if(fakeDamage.get()) {
            if(mc.thePlayer.getHealth() <= 1) {
                mc.thePlayer.setPlayerSPHealth(mc.thePlayer.getHealth() + 1);
            }
            mc.thePlayer.hurtTime = mc.thePlayer.maxHurtTime = 10;
            mc.thePlayer.hurtResistantTime = 20;
            mc.thePlayer.damageEntity(DamageSource.causeThornsDamage(mc.thePlayer), 1);
            mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("game.player.hurt")));
            BlueZenith.scheduledExecutorService.schedule(() -> { if(mc.thePlayer != null) mc.thePlayer.setPlayerSPHealth(mc.thePlayer.getHealth() + 1); }, 1500, TimeUnit.MILLISECONDS);
        }
        flightMode.onEnable(this);
    }
    @Override
    public void onDisable() {
        if(check()) return;
        if(prevFOV != -1) {
            mc.gameSettings.fovSetting = prevFOV;
            prevFOV = -1;
        }
        flightMode.onDisable(this);
    }

    @Listener
    public void onPacket(PacketEvent event) {
        if(check()) return;
        if(event.packet instanceof S08PacketPlayerPosLook) {
            if(!BlueZenith.expectedLagback && lagbackCheck.get()) {
                this.setState(false);
                mc.thePlayer.setPosition(((S08PacketPlayerPosLook) event.packet).getX(), ((S08PacketPlayerPosLook) event.packet).getY(), ((S08PacketPlayerPosLook) event.packet).getZ());
                BlueZenith.getBlueZenith().getNotificationPublisher().postWarning("Flight", "Disabled due to a lagback", 2000);
            }
        }
        flightMode.onPacket(event, this);
    }

    @Override
    public String getTag(){
        return this.mode.get();
    }

    boolean check() {
        if(flightMode == null || mc.thePlayer == null) {
            if(mc.thePlayer != null) {
                BlueZenith.getBlueZenith().getNotificationPublisher().postError("Flight", "Something went wrong. \n Select a different fly mode", 2000);
            }
            setState(false);
        }
        return flightMode == null;
    }

    public boolean canFlyUp() {
        final TargetStrafe strafe = (TargetStrafe) BlueZenith.getBlueZenith().getModuleManager().getModule(TargetStrafe.class);
        final Aura aura = BlueZenith.getBlueZenith().getModuleManager().getAndCast(Aura.class);
        return aura.getTarget() == null || !strafe.getState() || !strafe.supportOptions.getOptionState("Flight") || !strafe.jumpOnly.get() || player.posY < aura.getTarget().posY;
    }
}
