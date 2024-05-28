package arsenic.module.impl.movement;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.*;
import arsenic.injection.accessor.IMixinMinecraft;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.PropertyInfo;
import arsenic.module.property.impl.BooleanProperty;
import arsenic.module.property.impl.EnumProperty;
import arsenic.module.property.impl.doubleproperty.DoubleProperty;
import arsenic.module.property.impl.doubleproperty.DoubleValue;
import arsenic.utils.minecraft.*;
import arsenic.utils.timer.MillisTimer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

@ModuleInfo(name = "Flight", category = ModuleCategory.Movement)

public class Flight extends Module {
    boolean one;
    int ticks = 0;
    public final EnumProperty<fMode> mode = new EnumProperty<>("Mode:", fMode.Vanilla);
    @PropertyInfo(reliesOn = "Mode:", value = "Vanilla")
    public final DoubleProperty speed = new DoubleProperty("Horizontal speed", new DoubleValue(0, 10, 0.5, 0.1));
    @PropertyInfo(reliesOn = "Mode:", value = "Damage")
    public final DoubleProperty speed2 = new DoubleProperty("Horizontal speed", new DoubleValue(0, 10, 0.5, 0.1));
    @PropertyInfo(reliesOn = "Mode:", value = "BlocksMC")
    public final BooleanProperty lowtimer = new BooleanProperty("Timer", false);
    @PropertyInfo(reliesOn = "Mode:", value = "BlocksMC")
    public final BooleanProperty blinkbmc = new BooleanProperty("Blink", false);
    @PropertyInfo(reliesOn = "Mode:", value = "Fireball")
    public final BooleanProperty hypixelfb = new BooleanProperty("Hypixel", false);
    @PropertyInfo(reliesOn = "Mode:", value = "Bow")
    public final BooleanProperty hypixel = new BooleanProperty("Hypixel", false);
    @PropertyInfo(reliesOn = "Mode:", value = "Bow")
    public final BooleanProperty verus = new BooleanProperty("Verus", false);
    private boolean canBoost = false;
    MillisTimer boosttimer = new MillisTimer();
    @EventLink
    public final Listener<EventTick> onTick = event -> {
        if (mode.getValue() == fMode.Vanilla) {
            MoveUtil.strafe(speed.getValue().getInput() / 2);
            mc.thePlayer.motionY = 0.0D + (mc.gameSettings.keyBindJump.isKeyDown() ? speed.getValue().getInput() : 0.0D) - (mc.gameSettings.keyBindSneak.isKeyDown() ? speed.getValue().getInput() : 0.0D);
        }
        if (mode.getValue() == fMode.Damage) {
            MoveUtil.strafe(speed2.getValue().getInput() / 2);
            mc.thePlayer.motionY = 0.0D + (mc.gameSettings.keyBindJump.isKeyDown() ? speed2.getValue().getInput() : 0.0D) - (mc.gameSettings.keyBindSneak.isKeyDown() ? speed2.getValue().getInput() : 0.0D);
        }
        if (mode.getValue() == fMode.Bow && verus.getValue()) {
            if (mc.thePlayer.hurtTime > 5) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
                MoveUtil.strafe(6.0);
                mc.thePlayer.motionY = 0;
            } else {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
            }
        }
        if (mode.getValue() == fMode.BWP) {
            mc.thePlayer.motionY = 0;
        }
    };
    @EventLink
    public final Listener<EventSilentRotation> eventSilentRotationListener = e -> {
        if (mode.getValue() == fMode.Fireball) {
            e.setSpeed(180F);
            e.setYaw(MathHelper.wrapAngleTo180_float(e.getYaw() + 180f));
            e.setPitch(69F);
            e.setJumpFix(true);
            e.setDoMovementFix(true);
        }
        if (mode.getValue() == fMode.Bow) {
            e.setSpeed(180F);
            e.setPitch(-90);
            e.setJumpFix(false);
            e.setDoMovementFix(false);
        }
    };
    @EventLink
    public final Listener<EventTick> eventTickListener = event -> {
        if (mode.getValue() == fMode.Fireball) {
            ticks++;
            for (int slot = 0; slot <= 8; slot++) {
                ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
                if (itemInSlot != null && itemInSlot.getItem().getRegistryName().contains("charge")) {
                    if (mc.thePlayer.inventory.currentItem != slot) {
                        mc.thePlayer.inventory.currentItem = slot;
                    }
                }
            }
            if (mc.thePlayer.onGround) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                mc.thePlayer.jump();
            } else {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
            }
            if (ticks == 5) {
                toggle();
            }
        }
        if (mode.getValue() == fMode.Bow) {
            if (mc.thePlayer.hurtTime == 1) {
                toggle();
            }
        }
    };
    @EventLink
    public final Listener<EventMovementInput> eventMovementInputListener = event -> {
        if (mode.getValue() == fMode.Fireball) {
            if (mc.thePlayer.hurtTime == 10 && hypixelfb.getValue()) {
                event.setSpeed(2);
            }
        }
    };
    @EventLink
    public final Listener<EventMove> moveListener = event -> {
        if (mode.getValue() == fMode.Bow) {
            ticks++;
            for (int i = 8; i >= 0; i--) {
                ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);

                if (stack != null && stack.getItem() instanceof ItemBow) {
                    mc.thePlayer.inventory.currentItem = i;
                    break;
                }
            }
            if (ticks >= 1 && ticks <= 3) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
            } else {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
            }
        }
    };
    boolean finished = false;
    private int jumps;
    @EventLink
    public final Listener<EventUpdate.Pre> preListener = event -> {
        if (mode.getValue() == fMode.BlocksMC) {
            if (lowtimer.getValue()) {
                ((IMixinMinecraft) mc).getTimer().timerSpeed = 0.2f;
            } else {
                ((IMixinMinecraft) mc).getTimer().timerSpeed = 0.7f;
            }
            final AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox().offset(0, 1, 0);
            if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty()) {
                canBoost = true;
            } else {
                if (one == true) {
                    one = false;
                    PlayerUtils.addWaterMarkedMessageToChat("Funied ncp");
                    PacketUtil.send(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
                    PacketUtil.send(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY - 0.1, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
                    PacketUtil.send(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
                }
            }
            if (mc.thePlayer.onGround && MoveUtil.isMoving() && canBoost == true) {
                mc.thePlayer.jump();
            }

            if (!mc.thePlayer.onGround) {
                if (canBoost) {
                    mc.thePlayer.motionY += 0.025;
                    MoveUtil.strafe(8.0);
                    finished = true;
                }
            }
            if (canBoost) {

                canBoost = false;
            }
            if (mc.thePlayer.onGround && finished) {
                finished = false;
                toggle();
            }
        }
        if (mode.getValue() == fMode.Vulcan) {
            if (mc.thePlayer.fallDistance >= 3) {
                mc.thePlayer.motionY = -0.07;
                event.setOnGround(true);
                mc.thePlayer.posY = mc.thePlayer.posY + 0.07;
                mc.thePlayer.fallDistance = 0;
            }
            if (mc.thePlayer.onGround) {
                mc.thePlayer.jump();
            } else if (mc.thePlayer.fallDistance > 0.25) {
                if (jumps < 3) {
                    mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + (10), mc.thePlayer.posZ);
                    jumps += 1;
                } else {
                    if (mc.thePlayer.ticksExisted % 2 == 0) {
                        mc.thePlayer.motionY = -0.0975;
                    }
                }
            }
        }
    };

    @EventLink
    public final Listener<EventPacket.OutGoing> onPacket = event -> {
        if (PlayerUtils.isPlayerInGame()) {
            if (mode.getValue() == fMode.BlocksMC) {
                if (blinkbmc.getValue()) {
                    BlinkUtils.CancelAll(event);
                }
            }
        }
    };

    @Override
    protected void onDisable() {
        if (mode.getValue() == fMode.Vanilla && mode.getValue() == fMode.Damage) {
            MoveUtil.stop();
        }
        if (mode.getValue() == fMode.BlocksMC) {
            if (blinkbmc.getValue()) {
                BlinkUtils.resetAll();
            }
            MoveUtil.stop();
        }
        ((IMixinMinecraft) mc).getTimer().timerSpeed = 1f;
        super.onDisable();
    }

    @Override
    protected void onEnable() {
        jumps = 0;
        if (mode.getValue() == fMode.BlocksMC) {
            one = true;
            boosttimer.reset();
        }
        finished = false;
        canBoost = false;
        ticks = 0;
        if (mode.getValue() == fMode.Damage) {
            DamageUtil.damagePlayer(DamageUtil.DamageType.POSITION, 3.42F, 1, false, false);
        }
        super.onEnable();
    }

    public enum fMode {
        Vanilla,
        Damage,
        Fireball,
        BlocksMC,
        Bow,
        Vulcan,
        BWP
    }
}
