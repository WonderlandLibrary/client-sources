package best.actinium.module.impl.movement;

import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.TickEvent;
import best.actinium.event.impl.move.EntityActionEvent;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.event.impl.move.MoveEvent;
import best.actinium.event.impl.network.PacketEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.ModeProperty;
import best.actinium.property.impl.NumberProperty;
import best.actinium.util.IAccess;
import best.actinium.util.io.PacketUtil;
import best.actinium.util.math.RandomUtil;
import best.actinium.util.player.MoveUtil;
import best.actinium.util.player.RotationsUtils;
import lombok.Getter;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import org.lwjglx.input.Keyboard;

import java.util.Random;


@ModuleInfo(
        name = "Speed",
        description = "Allows you to move faster.",
        category = ModuleCategory.MOVEMENT
)
//todo: make each speed in a dif class
public class SpeedModule extends Module {
    public ModeProperty mode = new ModeProperty("Mode", this, new String[] {"Watchdog","NCP","Vulcan Ground","Semi Strafe","Vulcan","Verus","Intave","Custom"}, "Watchdog");
    public NumberProperty offGroundSpeed = new NumberProperty("Off Ground Speed", this, 0, 2, 10, 0.01)
            .setHidden(() -> !mode.is("Custom"));
    public NumberProperty onGroundSpeed = new NumberProperty("On Ground Speed", this, 0, 2, 10, 0.01)
            .setHidden(() -> !mode.is("Custom"));
    public NumberProperty motionYS = new NumberProperty("Motion Y", this, 0, 0.42, 2, 0.01)
            .setHidden(() -> !mode.is("Custom"));
    public int onGroundTicks, offGroundTicks;
    private int offGroundMotionTicks;
    private boolean spoof = false;

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        spoof = false;
        IAccess.mc.gameSettings.keyBindJump.pressed = Keyboard.isKeyDown(IAccess.mc.gameSettings.keyBindJump.getKeyCode());
        super.onDisable();
    }

    @Callback
    public void onTick(TickEvent event) {
        this.onGroundTicks = IAccess.mc.thePlayer.onGround ? ++this.onGroundTicks : 0;
        this.offGroundTicks = IAccess.mc.thePlayer.onGround ? 0 : ++this.offGroundTicks;
    }

    @Callback
    public void onMove(MoveEvent event) {
        switch (mode.getMode()) {
            case "NCP":
                if(IAccess.mc.thePlayer.onGround) {
                    MoveUtil.strafe(RandomUtil.getShitRandom(0.40f,0.41f));
                    IAccess.mc.thePlayer.jump();
                } else {
                    MoveUtil.strafe(MoveUtil.getSpeed());
                }

                switch (offGroundMotionTicks) {
                    case 3:
                        IAccess.mc.thePlayer.motionY = IAccess.mc.thePlayer.motionY - 0.05f;
                        break;
                    case 4:
                        IAccess.mc.thePlayer.motionY = IAccess.mc.thePlayer.motionY - 0.09f;
                        break;
                }
                break;
            case "Semi Strafe":
                if(offGroundTicks > 6 && MoveUtil.isMoving()) {
                    //0.30 / 0.40 / 0.28
                    //0.35 / 40 / 0.30 / 0.40
                    //RandomUtil.getShitRandom(0.38f, 0.41f)
                    MoveUtil.strafe(MoveUtil.getPerfectValue(1, 1, RandomUtil.getShitRandom(0.40f, 0.45f)));
                    spoof = true;
                } else {
                    spoof = false;
                }
                break;
        }
    }

    @Callback
    public void onMotion(MotionEvent event) {
        this.setSuffix(mode.getMode());
        if(IAccess.mc.thePlayer.onGround) {
            offGroundMotionTicks = 0;
        } else {
            offGroundMotionTicks++;
        }

        if(!mode.is("Custom") && !mode.is("Verus") && !mode.is("Vulcan Ground")) {
            IAccess.mc.gameSettings.keyBindJump.pressed = true;
        }

        switch (mode.getMode()) {
            case "Watchdog":
                if(IAccess.mc.thePlayer.onGround) {
                    MoveUtil.strafe();
                }
                break;
            case "NCP":
                IAccess.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0, 0.5f, 0));
                break;
            case "Semi Strafe":
                if(mc.thePlayer.onGround) {
              //      MoveUtil.getPerfectValue((float) MoveUtil.baseSpeed(), (float) MoveUtil.baseSpeed(), (float) MoveUtil.baseSpeed() * RandomUtil.getShitRandom(1.3f, 1.5f));
                    MoveUtil.strafe(MoveUtil.baseSpeed() * MathHelper.randomFloatClamp(new Random(), 1.3f, 1.5f));
                } else if(spoof) {
                    event.setOnGround(true);
                }
                break;
            case "Verus":
                if(event.getType() == EventType.MID) {
                    PacketUtil.sendSilent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0, 0.5f, 0));
                }

                if (MoveUtil.isMoving()) {
                    // On fly don't check for moving
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.motionY = 0.00001f;
                    }
                } else {
                    mc.thePlayer.motionX = 0;
                    mc.thePlayer.motionZ = 0;
                }

                MoveUtil.strafe(MoveUtil.getVerusLimit(true));

                if (mc.thePlayer.fallDistance > 0.2) {
                    mc.thePlayer.motionY = -0.1f;
                }
                break;
            case "Intave":
                if (offGroundTicks >= 10 && offGroundTicks % 5 == 0) {
                    MoveUtil.strafe(
                            MoveUtil.getSpeed()
                    );
                }
                break;
            case "Custom":
                if(IAccess.mc.thePlayer.onGround) {
                    IAccess.mc.thePlayer.motionY = motionYS.getValue();
                }

                MoveUtil.strafe(IAccess.mc.thePlayer.onGround ? onGroundSpeed.getValue() : offGroundSpeed.getValue());
                break;
            case "Vulcan Ground":
                if (MoveUtil.isMoving() && event.getType() == EventType.PRE) {
                    mc.thePlayer.setSprinting(true);
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jumpTicks = 2;
                        mc.thePlayer.motionY = 0.00988; // suicide reference :pray:
                        event.setY(event.getY() + 0.00988);
                        MoveUtil.strafe(MoveUtil.baseSpeed() * (mc.thePlayer.isPotionActive(1) ? 1.46 : 1.712));
                    } else if (offGroundMotionTicks == 1) {
                        MoveUtil.strafe(MoveUtil.baseSpeed() * (mc.thePlayer.isPotionActive(1) ? 1.11 : 1.0407));
                    }
                }
                break;
        }
    }

    @Callback
    public void onPacket(PacketEvent event) {
        if(mode.is("Strafe") && event.getPacket() instanceof S08PacketPlayerPosLook) {
            this.setEnabled(false);
        }
    }
}
