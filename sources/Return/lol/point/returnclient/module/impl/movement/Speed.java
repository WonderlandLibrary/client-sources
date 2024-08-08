package lol.point.returnclient.module.impl.movement;

import lol.point.returnclient.events.impl.packet.EventPacket;
import lol.point.returnclient.events.impl.update.EventUpdate;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.StringSetting;
import lol.point.returnclient.util.minecraft.ChatUtil;
import lol.point.returnclient.util.minecraft.MoveUtil;
import lol.point.returnclient.util.system.TimerUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.block.BlockSlab;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;

@ModuleInfo(
        name = "Speed",
        description = "Makes you go faster",
        category = Category.MOVEMENT
)
public class Speed extends Module {

    private int airTicks;
    private int groundTicks;
    private int mmcTicks;
    private boolean boosting;

    public TimerUtil timerUtil = new TimerUtil();

    private final StringSetting mode = new StringSetting("Mode", new String[]{"Hypixel", "Verus", "Vulcan", "Old NCP", "Updated NCP", "MinemenClub", "Intave", "Old Grim", "Matrix", "Karhu", "Incognito", "Spartan"});
    private final StringSetting verusMode = new StringSetting("Verus mode", new String[]{"Low-hop", "Y-Port", "Packet", "Ground"}).hideSetting(() -> !mode.is("Verus"));
    private final StringSetting vulcanMode = new StringSetting("Vulcan mode", new String[]{"Lowhop", "Super-Lowhop"}).hideSetting(() -> !mode.is("Vulcan"));

    public Speed() {
        addSettings(mode, verusMode, vulcanMode);
    }

    public String getSuffix() {
        return mode.value;
    }

    @Override
    public void onDisable() {
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        if (mode.is("Updated NCP")) {
            MoveUtil.stop();
        }

        mc.timer.timerSpeed = 1.0f;
        mc.thePlayer.speedInAir = 0.02f;
        mc.gameSettings.keyBindJump.pressed = false;

        airTicks = 0;
        groundTicks = 0;
        mmcTicks = 0;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
        super.onDisable();
    }

    @Override
    public void onEnable() {
        this.timerUtil.reset();
        this.boosting = false;
        super.onEnable();
    }

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(eventUpdate -> {
        if (mc.thePlayer.onGround) {
            airTicks = 0;
        } else {
            airTicks++;
        }

        if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer).down()).getBlock() instanceof BlockSlab) {
            return;
        }

        switch (mode.value) {
            case "Hypixel" -> {
                mc.thePlayer.jumpTicks = -1;

                if (mc.thePlayer.onGround && MoveUtil.isMoving()) {
                    mc.thePlayer.jump();
                    MoveUtil.setSpeed(0.482);
                }
            }

            case "Verus" -> {
                switch (verusMode.value) {
                    case "Low-hop" -> {
                        mc.thePlayer.jumpTicks = -1;
                        mc.thePlayer.setSprinting(true);

                        if (!mc.thePlayer.onGround) {
                            MoveUtil.setSpeed(0.34);
                            airTicks++;
                        }

                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.motionY = 0.5f;
                            MoveUtil.setSpeed(0.39);
                            airTicks = 0;
                        }

                        if (!mc.thePlayer.onGround && airTicks == 2) {
                            mc.thePlayer.motionY = -0.0784000015258789;
                        }
                    }

                    case "Y-Port" -> {
                        if (!mc.gameSettings.keyBindBack.isKeyDown()) {
                            mc.thePlayer.setSprinting(true);
                            if (!mc.thePlayer.onGround) {
                                airTicks++;
                                groundTicks = 0;
                                MoveUtil.setSpeed(0.376);
                            }

                            if (mc.thePlayer.onGround) {
                                groundTicks++;
                                airTicks = 0;
                                MoveUtil.setSpeed(0.55);
                                mc.thePlayer.jumpTicks = -1;

                                if (groundTicks == 10) {
                                    mc.thePlayer.motionY = 0.42F;
                                }
                            }
                        }
                    }

                    case "Packet" -> {
                        mc.thePlayer.jumpTicks = -1;
                        mc.thePlayer.setSprinting(true);
                        if (!mc.thePlayer.onGround) {
                            MoveUtil.setSpeed(0.425);
                            airTicks++;
                        }

                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.motionY = 0.00000000001f;
                            mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getPosition().down(), 1, new ItemStack(Items.water_bucket), 0.5F, 0.5F, 0.5F));
                            MoveUtil.setSpeed(0.55);
                            airTicks = 0;
                        }
                    }
                    case "Ground" -> {
                        mc.thePlayer.setSprinting(true);
                        if (mc.thePlayer.onGround && mc.gameSettings.keyBindForward.isKeyDown()) {
                            MoveUtil.setSpeed(0.23);
                            if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                                MoveUtil.setSpeed(0.54);
                            }
                        }
                        if(!mc.thePlayer.onGround && mc.gameSettings.keyBindForward.isKeyDown()){
                            MoveUtil.setSpeed(0.3768);
                        }
                        if (mc.thePlayer.onGround && mc.gameSettings.keyBindBack.isKeyDown()) {
                            MoveUtil.setSpeed(0.1845);
                        }
                    }
                }
            }

            case "Vulcan" -> {
                switch (vulcanMode.value) {
                    case "Lowhop" -> {
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.jump();
                            MoveUtil.setSpeed(0.45);
                        }
                        if (!mc.thePlayer.onGround) {
                            mc.thePlayer.jumpTicks = -1;
                        }
                        if (airTicks == 2 && !mc.thePlayer.onGround) {
                            mc.thePlayer.motionY = -0.16;
                        }
                    }
                    case "Super-Lowhop" -> {
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.jump();
                            MoveUtil.setSpeed(0.4);
                        }
                        if (!mc.thePlayer.onGround) {
                            mc.thePlayer.jumpTicks = -1;
                        }
                        if (airTicks == 1) {
                            mc.thePlayer.motionY = -0.1;
                        }
                    }
                }
            }

            case "Old NCP" -> {
                if (MoveUtil.isMoving()) {
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                        mc.timer.timerSpeed = 0.6f;
                    } else {
                        mc.timer.timerSpeed = 1.18f;
                    }
                }
            }

            case "Updated NCP" -> {
                if (!MoveUtil.isMoving() || (mc.thePlayer.isInLava() && mc.thePlayer.isInWater())) {
                    return;
                }

                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);

                if (mc.thePlayer.onGround) {
                    MoveUtil.strafe(MoveUtil.getBaseMoveSpeed());
                    mc.thePlayer.jump();

                    if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                        MoveUtil.strafe(MoveUtil.getSpeed() * 1.2F);
                    }
                }

                mc.timer.timerSpeed = (float) (1.075F - (Math.random() - 0.5) / 100.0F);
                MoveUtil.strafe(MoveUtil.getSpeed() - (float) (Math.random() - 0.5F) / 100.0F);
            }

            case "MinemenClub" -> {
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                }

                if (mc.thePlayer.onGround) {
                    mc.thePlayer.motionY = 0.35;
                }

                if (Math.abs(mc.thePlayer.motionY) < 0.005 && mmcTicks <= 3) {
                    mc.thePlayer.motionY = (mc.thePlayer.motionY - 0.08) * 0.98f;
                    mmcTicks++;
                }

                if (mc.thePlayer.onGround) {
                    mmcTicks = 0;
                }

                MoveUtil.strafe(MoveUtil.getBaseMoveSpeed());
            }

            case "Intave" -> {
                mc.timer.timerSpeed = 1.0F;
                if (MoveUtil.isMoving()) {
                    if (mc.thePlayer.fallDistance < 0.1) {
                        mc.timer.timerSpeed = 0.9F;
                    } else if (mc.thePlayer.fallDistance > 0.7) {
                        mc.timer.timerSpeed = 2.0F;
                    }

                }
            }

            case "Old Grim" -> {
                if (timerUtil.elapsed(1150L)) {
                    boosting = true;
                }

                if (timerUtil.elapsed(7000L)) {
                    boosting = true;
                    timerUtil.reset();
                }

                if (boosting) {
                    if (mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.pressed) {
                        mc.thePlayer.jump();
                    }

                    mc.timer.timerSpeed = mc.thePlayer.ticksExisted % 4 == 0 ? 1.5F : 1.2F;
                } else {
                    mc.timer.timerSpeed = 0.07F;
                }
            }

            case "Matrix" -> {
                if (!MoveUtil.isMoving()) {
                    mc.gameSettings.keyBindJump.pressed = false;
                    return;
                }

                mc.gameSettings.keyBindJump.pressed = true;

                mc.thePlayer.speedInAir = 0.0203F;

                if (mc.thePlayer.motionY > 0.4) {
                    mc.thePlayer.motionX *= 1.003F;
                    mc.thePlayer.motionZ *= 1.003F;
                }

                if (mc.thePlayer.onGround) {
                    mc.timer.timerSpeed = (float) (1.1 + Math.random() / 50 - Math.random() / 50);
                    mc.thePlayer.motionX *= 1.0045F;
                    mc.thePlayer.motionZ *= 1.0045F;
                } else {
                    mc.timer.timerSpeed = (float) (1 - Math.random() / 500);
                }
            }

            case "Karhu" -> {
                if (mc.gameSettings.keyBindJump.pressed || !MoveUtil.isMoving()) return;

                mc.thePlayer.setSprinting(true);

                if (mc.thePlayer.onGround) {
                    mc.timer.timerSpeed = 1;
                    mc.thePlayer.jump();
                    mc.thePlayer.motionY *= 0.55;
                } else {
                    mc.timer.timerSpeed = (float) (1 + (Math.random() - 0.5) / 100);
                }
            }

            case "Incognito" -> {
                mc.gameSettings.keyBindJump.pressed = MoveUtil.isMoving();

                if (mc.thePlayer.onGround) {
                    MoveUtil.strafe((float) (0.36 + Math.random() / 70 + MoveUtil.getSpeedBoost(1)));
                } else {
                    MoveUtil.strafe((float) (MoveUtil.getSpeed() - (float) (Math.random() - 0.5F) / 70F));
                }

                if (MoveUtil.getSpeed() < 0.25F) {
                    MoveUtil.strafe((float) (MoveUtil.getSpeed() + 0.02));
                }
            }

            case "Spartan" -> {
                mc.gameSettings.keyBindJump.pressed = MoveUtil.isMoving();

                MoveUtil.strafe((float) MoveUtil.getSpeed());

                mc.timer.timerSpeed = (float) (1.07 + Math.random() / 25);

                if (0 > mc.thePlayer.moveForward && mc.thePlayer.moveStrafing == 0) {
                    mc.thePlayer.speedInAir = (float) (0.04 - Math.random() / 100);
                } else if (mc.thePlayer.moveStrafing != 0) {
                    mc.thePlayer.speedInAir = (float) (0.036 - Math.random() / 100);
                } else {
                    mc.thePlayer.speedInAir = (float) (0.0215F - Math.random() / 1000);
                }

                if (mc.thePlayer.onGround) {
                    MoveUtil.strafe((float) (MoveUtil.getSpeed() + 0.002));
                }
            }
        }
    });

    @Subscribe
    private final Listener<EventPacket> onPacket = new Listener<>(eventPacket -> {
        if (mode.is("Updated NCP")) {
            if (eventPacket.packet instanceof C0BPacketEntityAction) {
                eventPacket.setCancelled(true);
            }
        }
    });

}
