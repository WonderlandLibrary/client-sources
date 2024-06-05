package koks.modules.impl.movement;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.AnimationEvent;
import koks.event.impl.EventUpdate;
import koks.event.impl.PacketEvent;
import koks.modules.Module;
import koks.modules.impl.movement.modes.HypixelFly;
import koks.modules.impl.visuals.Animations;
import koks.utilities.MovementUtil;
import koks.utilities.RandomUtil;
import koks.utilities.TimeUtil;
import koks.utilities.value.values.ModeValue;
import koks.utilities.value.values.NumberValue;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

import java.util.ArrayList;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 18:14
 */
public class Fly extends Module {

    private final ArrayList<Packet> packets = new ArrayList<>();
    private int stage;
    private final HypixelFly hypixelFly;
    private final TimeUtil timeUtil = new TimeUtil();
    private final MovementUtil movementUtil = new MovementUtil();
    public final ModeValue<String> modeValue = new ModeValue<>("Mode", "Hypixel", new String[]{"Hypixel", "AAC3.2.2", "Faithful", "MCCentral", "MCCentral 2"}, this);
    public final NumberValue<Integer> aac322boost = new NumberValue<Integer>("AAC3.2.2-Boost", 9, 10, 5, this);

    public Fly() {
        super("Fly", "You are very fliegen", Category.MOVEMENT);
        addValue(aac322boost);
        Koks.getKoks().valueManager.addValue(modeValue);
        hypixelFly = new HypixelFly();
    }

    @Override
    public void onEvent(Event event) {
        switch (modeValue.getSelectedMode()) {
            case "Hypixel":
                hypixelFly.onEvent(event);
                break;
        }

        if (event instanceof EventUpdate) {
            setModuleInfo(modeValue.getSelectedMode());
            switch (modeValue.getSelectedMode()) {
                case "AAC3.2.2":
                    aac322();
                    break;
                case "Faithful":
                    faithful();
                    break;
                case "MCCentral":
                    mccentral();
                    break;
                case "MCCentral 2":
                    mccentral2();
                    break;
            }
        }

        if (event instanceof PacketEvent && modeValue.getSelectedMode().equals("MCCentral 2")) {
            if (((PacketEvent) event).getType() == PacketEvent.Type.SEND && ((PacketEvent) event).getPacket() instanceof C03PacketPlayer) {
                packets.add(((PacketEvent) event).getPacket());
                //event.setCanceled(true);
            }
        }

        if (event instanceof AnimationEvent && Koks.getKoks().moduleManager.getModule(Animations.class).isToggled()) {
            AnimationEvent animationEvent = (AnimationEvent) event;
            animationEvent.setRightLeg(1.5F, 0F, 0F);
            animationEvent.setLeftLeg(1.5F, 0F, 0F);
            animationEvent.setBody(1.5F, 0F, 0F);
            animationEvent.setBodyPos(0, 0.7F, -0.5F);
            animationEvent.setHead(0.6F, 0F, 0F);
            animationEvent.setLeftArm(-1.5F, 0F, 0F);
            animationEvent.setRightArm(-1.5F, 0F, 0F);
            animationEvent.setRightArmPos(0, 0.7F, -0.5F);
            animationEvent.setLeftArmPos(0, 0.7F, -0.5F);
            animationEvent.setHeadPos(0, 0.7F, -0.5F);
        }
    }

    public void faithful() {
        if (timeUtil.hasReached(50)) {
            timeUtil.reset();
        }

        for (int i = 0; i < 5; i++)
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
        mc.thePlayer.motionY = 0;
        mc.timer.timerSpeed = 0.02;
        BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
        mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, null, pos, EnumFacing.UP, new Vec3((pos.getX() + 0.4) * 0.4, (pos.getY() + 0.4) * 0.4, (pos.getZ() + 0.4) * 0.4));
        mc.thePlayer.capabilities.setFlySpeed(2.0F);
        mc.thePlayer.capabilities.isFlying = true;
        if (mc.thePlayer.moveForward != 0) {
            movementUtil.setSpeed(5.0);
        }
    }

    public void mccentral() {
        System.out.println(mc.thePlayer.fallDistance);
            mc.thePlayer.motionY = 0;

            MovementUtil movementUtil = new MovementUtil();
            if(mc.gameSettings.keyBindBack.pressed || mc.gameSettings.keyBindForward.pressed) {
                movementUtil.setSpeed(1.5F);
            }
            if(mc.gameSettings.keyBindJump.pressed) {
                mc.thePlayer.motionY += 0.1;
            }
            if(mc.gameSettings.keyBindSneak.pressed) {
                mc.thePlayer.motionY -= 0.1;

        }
    }

    public void mccentral2() {
        if (mc.gameSettings.keyBindJump.isKeyDown())
            mc.thePlayer.motionY = 0.5;
        else if (mc.gameSettings.keyBindSneak.isKeyDown())
            mc.thePlayer.motionY = -0.5;
        else
            mc.thePlayer.motionY = 0;

        mc.thePlayer.setSneaking(false);
        movementUtil.setSpeed(0.4);
        if (mc.gameSettings.keyBindSprint.isKeyDown())
            mc.thePlayer.speedInAir = 0.6F;
        else
            mc.thePlayer.speedInAir = 0.2F;

        double addY = new RandomUtil().randomDouble(-0.05, 0.10);

        mc.thePlayer.motionY += addY;
    }

    public void aac322() {
        if (mc.thePlayer.posY <= -70) {
            mc.thePlayer.motionY = aac322boost.getDefaultValue();
        }
        if (mc.gameSettings.keyBindSprint.pressed && !mc.thePlayer.onGround) {
            mc.timer.timerSpeed = 0.1F;
            mc.rightClickDelayTimer = 0;
        } else {
            mc.timer.timerSpeed = 1F;
            mc.rightClickDelayTimer = 6;
        }
    }

    @Override
    public void onEnable() {
        stage = 0;
        timeUtil.reset();
        switch (modeValue.getSelectedMode()) {
            case "Hypixel":
                hypixelFly.onEnable();
                break;
        }
    }

    @Override
    public void onDisable() {
        switch (modeValue.getSelectedMode()) {
            case "MCCentral":
                mc.thePlayer.motionX = 0;
                mc.thePlayer.motionZ = 0;
            case "Hypixel":
                hypixelFly.onDisable();
                break;
            case "AAC3.2.2":
                mc.timer.timerSpeed = 1F;
                mc.rightClickDelayTimer = 6;
                break;
            case "Faithful":
                mc.timer.timerSpeed = 1.0;
                mc.thePlayer.capabilities.setFlySpeed(0.1F);
                mc.thePlayer.capabilities.isFlying = false;
                mc.thePlayer.motionX = 0;
                mc.thePlayer.motionY = 0.1;
                mc.thePlayer.motionZ = 0;
                for (int i = 0; i < 50; i++)
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
/*                packets.forEach(mc.thePlayer.sendQueue.getNetworkManager()::sendPacket);
                packets.clear();*/
                break;
            case "MCCentral 2":
                mc.thePlayer.speedInAir = 0.02F;
                mc.timer.timerSpeed = 1.0;
                mc.thePlayer.motionX = 0;
                mc.thePlayer.motionZ = 0;
                break;
        }
    }


}
