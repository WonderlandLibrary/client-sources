package best.actinium.module.impl.movement.scaffold;

import best.actinium.component.componets.RotationComponent;
import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.TickEvent;
import best.actinium.event.impl.game.UpdateEvent;
import best.actinium.event.impl.input.ClickEvent;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.event.impl.network.PacketEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.BooleanProperty;
import best.actinium.property.impl.ModeProperty;
import best.actinium.property.impl.NumberProperty;
import best.actinium.util.io.PacketUtil;
import best.actinium.util.io.TimerUtil;
import best.actinium.util.player.*;
import best.actinium.util.render.ChatUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import org.lwjglx.input.Keyboard;
import org.lwjglx.util.vector.Vector2f;
import java.util.Random;
//todo: maybe use look vector fix slot thingys ghost placing btw return when u dont have blocks and use like -1
//todo: use that placedata(pol) so it can clutch better
/**
 * @author Nyghtfull
 * @since 10/25/2023
 * note: some old p bad scaffold of mine that i recoded on 2/10/2024
 */
@ModuleInfo(
        name = "Scaffold Walk",
        description = "Places blocks i think",
        category = ModuleCategory.MOVEMENT
)
public class ScaffoldWalkModule extends Module {
    private ScafUtil.BlockData blockdata, lastblockdata;
    public ModeProperty scaffoldMode = new ModeProperty("Scaffold Mode", this, new String[] {"Normal", "Extend"}, "Normal");
    public ModeProperty placeMode = new ModeProperty("Place Mode", this, new String[] {"Update", "Legit"}, "Update");
    public BooleanProperty startSneak = new BooleanProperty("Start Sneak", this, false);
    public NumberProperty extenda = new NumberProperty("Extend Amount", this, 1, 0, 6, 1)
            .setHidden(() -> !scaffoldMode.is("Extend"));
    public BooleanProperty sprint = new BooleanProperty("Sprint asd",this,false);
    public ModeProperty sprintMode = new ModeProperty("Sprint Mode", this, new String[] {"Normal", "Watchdog Slow", "Watchdog Fast"}, "Watchdog Slow")
            .setHidden(() -> !sprint.isEnabled());
    public BooleanProperty autoJump = new BooleanProperty("Auto Jump",this,false);
    public BooleanProperty tower = new BooleanProperty("Tower",this,false);
    public ModeProperty towerMode = new ModeProperty("Tower Mode", this, new String[] {"Watchdog", "Watchdog 2"}, "Watchdog")
            .setHidden(() -> !tower.isEnabled());
    public BooleanProperty sprintTower = new BooleanProperty("Sprint Tower",this,false)
            .setHidden(() -> !tower.isEnabled());
    public BooleanProperty strafeTower = new BooleanProperty("Strafe Tower",this,false)
            .setHidden(() -> !tower.isEnabled());
    public BooleanProperty sneak = new BooleanProperty("Sneak",this,false);
    public NumberProperty startMin = new NumberProperty("Start Min", this, 1, 50, 400, 1)
            .setHidden(() -> !sneak.isEnabled());
    public NumberProperty startMax = new NumberProperty("Start Max", this, 1, 50, 400, 1)
            .setHidden(() -> !sneak.isEnabled());
    public NumberProperty endMin = new NumberProperty("Min Min", this, 1, 50, 400, 1)
            .setHidden(() -> !sneak.isEnabled());
    public NumberProperty endMax = new NumberProperty("Min Max", this, 1, 50, 400, 1)
            .setHidden(() -> !sneak.isEnabled());
    public BooleanProperty rotations = new BooleanProperty("Rotations",this,false);
    public ModeProperty rotationCalculateEvent = new ModeProperty("Calculate Event", this, new String[] {"Update", "Tick(most legit)"}, "Update")
            .setHidden(() -> !rotations.isEnabled());
    public ModeProperty rotationsMode = new ModeProperty("Rotation Mode", this, new String[] {"Normal", "Full Brute Force","Semi Brute Force"}, "Normal")
            .setHidden(() -> !rotations.isEnabled());
    public ModeProperty raytraceMode = new ModeProperty("Raytrace Mode", this, new String[] {"Off", "Normal","Advanced"}, "Off")
            .setHidden(() -> !rotations.isEnabled());
    public NumberProperty bruteForce = new NumberProperty("Brute Force Amount", this, 0, 0.5, 3, 0.1)
            .setHidden(() -> !rotations.isEnabled() || !rotationsMode.is("Normal"));
    public NumberProperty yawValue = new NumberProperty("Yaw Value", this, 0, 180, 360, 1)
            .setHidden(() -> !rotations.isEnabled());
    public BooleanProperty swing = new BooleanProperty("Swing",this, false);
    public BooleanProperty spoofItem = new BooleanProperty("Spoof Item",this,false);
    public double Ylevel;
    private float yaw, pitch;
    private boolean towering = false, wdSpoof = false, sneaking;
    private int wdTick = 0, towerTick = 0, gticks = 0, slot,  lastSlot;
    private final TimerUtil timerend = new TimerUtil(), timestart = new TimerUtil(), sprintT = new TimerUtil();
    private float targetYaw = 0, targetPitch = 0;

    @Override
    public void onEnable() {
        slot = ScafUtil.getBlockSlot();

        if(spoofItem.isEnabled()) {
            PacketUtil.sendSilent(new C09PacketHeldItemChange(slot));
        } else {
            mc.thePlayer.inventory.currentItem = slot;
        }

        mc.gameSettings.keyBindSneak.pressed = startSneak.isEnabled();

        lastSlot = mc.thePlayer.inventory.currentItem;
        gticks = 0;
        Ylevel = mc.thePlayer.getPositionVector().yCoord;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if(autoJump.isEnabled()) {
            mc.gameSettings.keyBindJump.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode());
        }

        if(lastSlot != -1) {
            if (spoofItem.isEnabled()) {
                PacketUtil.sendSilent(new C09PacketHeldItemChange(lastSlot));
            } else {
                mc.thePlayer.inventory.currentItem = lastSlot;
            }
        }

        if(startSneak.isEnabled()) {
            mc.gameSettings.keyBindSneak.pressed = false;
        }

        super.onDisable();
    }

    @Callback
    public void onUpdate(UpdateEvent e) {
        slot = ScafUtil.getBlockSlot();

        if(rotations.isEnabled()) {
            RotationComponent.setRotations(new Vector2f(yaw, pitch));
        }

        if(placeMode.is("Update")) {
            place();
        }
    }

    @Callback
    public void onTickEvent(TickEvent event) {
        if(lastblockdata != null && rotationCalculateEvent.is("Tick(most legit)")) {
            calculateRotations();
        }
    }

    @Callback
    public void onClick(ClickEvent e) {
        if(placeMode.is("Legit")) {
            place();
        }
    }

    @Callback
    public void onMotion(UpdateEvent e) {
        if(!tower.isEnabled() || !mc.gameSettings.keyBindJump.isKeyDown() || !MoveUtil.isMoving()) {
            towering = false;
            return;
        }

        switch (towerMode.getMode()) {
            case "Watchdog":
            if (tower.isEnabled() && mc.gameSettings.keyBindJump.isKeyDown() && MoveUtil.isMoving()) {
                if (!mc.thePlayer.onGround && strafeTower.isEnabled()) {
                    MoveUtil.strafe(MoveUtil.baseSpeed() - 0.01);
                }

                towering = true;
                if (wdTick != 0) {
                    towerTick = 0;
                    return;
                }

                if (towerTick > 0) {
                    ++towerTick;
                    if (towerTick > 6) {
                        mc.thePlayer.motionX *= 0.9f;
                        mc.thePlayer.motionZ *= 0.9f;
                    }
                    if (towerTick > 16) {
                        towerTick = 0;
                    }
                }

                if (mc.thePlayer.onGround) {
                    if (towerTick == 0 || towerTick == 5) {
                        mc.thePlayer.motionY = 0.42;
                        towerTick = 1;
                    }
                } else if (mc.thePlayer.motionY > -0.0784000015258789) {
                    int n = (int) Math.round(mc.thePlayer.posY % 1.0 * 100.0);
                    switch (n) {
                        case 42:
                            //0.33
                            mc.thePlayer.motionY = 0.33;
                            break;

                        case 75:
                            mc.thePlayer.motionY = 1.0 - mc.thePlayer.posY % 1.0;
                            wdSpoof = true;
                            break;

                        case 0:
                            //mc.thePlayer.motionY = -0.0784000015258789;
                            mc.thePlayer.motionY = -0.184000015258789;
                            break;
                    }
                }
            }
            break;
            case "Watchdog 2":
                mc.thePlayer.setSprinting(false);
                towering = true;
                gticks++;
                if (mc.thePlayer.onGround) {
                    gticks = 0;
                }

                mc.thePlayer.motionY = 0.41965; //0.41982
                if (gticks == 1) {
                    mc.thePlayer.motionY = 0.33;
                    MoveUtil.strafe(0.2397);
                } else if (gticks == 2) {
                    mc.thePlayer.motionY = 1 - mc.thePlayer.posY % 1l;
                } else if (gticks == 3) {
                    gticks = 0;
                }

                if (gticks >= 3) {
                    gticks = 0;
                }
                break;
        }
    }

    @Callback
    public void onMotion(MotionEvent e) {
        if(e.getType() == EventType.POST) {
            return;
        }

        this.setSuffix(scaffoldMode.getMode());

        if(spoofItem.isEnabled()) {
            PacketUtil.sendSilent(new C09PacketHeldItemChange(slot));
        } else {
            mc.thePlayer.inventory.currentItem = slot;
        }

        sneak();

        if(tower.isEnabled() && towerMode.is("Watchdog")) {
            if (wdTick > 0) {
                wdTick--;
            }

            if(!towering && !mc.thePlayer.onGround && mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                mc.thePlayer.motionX = 0;
                mc.thePlayer.motionZ = 0;
            }
        }

        if(sprint.isEnabled() && !towering) {
            switch (sprintMode.getMode()) {
                case "Normal":
                    mc.thePlayer.setSprinting(MoveUtil.isMoving());
                    break;
                case "Watchdog Slow":
                    mc.thePlayer.setSprinting(MoveUtil.isMoving());

                    if (!mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                        if (sprintT.hasTimeElapsed(40)) {
                            MoveUtil.strafe(0.07f);
                        } else {
                            MoveUtil.strafe(0.15f);
                        }

                    } else {
                        //works without speed too but unstable
                        if (sprintT.hasTimeElapsed(70)) {
                            MoveUtil.strafe(0.06f);
                        } else {
                            MoveUtil.strafe(0.14f);
                        }

                    }

                    if (sprintT.hasTimeElapsed(100)) {
                        sprintT.reset();
                    }
                    break;

                case "Watchdog Fast":
                    if (mc.thePlayer.onGround && MoveUtil.isMoving() && !Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                        mc.thePlayer.setSprinting(true);
                        MoveUtil.strafe(MoveUtil.baseSpeed() * 0.512);
                        e.setY(e.getY() + 0.00000000000001);
                    }
                    break;
            }
        }
    }

    private void calculateRotations() {
        float[] rotations = new float[0];

        if(lastblockdata != null) {
            switch (rotationsMode.getMode()) {
                case "Semi Brute Force":
                case "Full Brute Force":
                    boolean found = false;
                    float possibleYaw = mc.thePlayer.rotationYaw - yawValue.getValue().floatValue();

                    for (float possiblePitch = 90; possiblePitch > 30 && !found; possiblePitch -= possiblePitch > (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 60 : 80) ? 1 : 5) {
                        if (RayTraceUtil.getOver(lastblockdata.getFacing(), lastblockdata.getPosition(), true, 5, possibleYaw, possiblePitch)) {
                            targetYaw = possibleYaw;
                            targetPitch = possiblePitch;
                            found = true;
                        }
                    }

                    for (possibleYaw = mc.thePlayer.rotationYaw - yawValue.getValue().floatValue(); possibleYaw <= mc.thePlayer.rotationYaw && !found; possibleYaw += 45) {
                        for (float possiblePitch = 90; possiblePitch > 30 && !found; possiblePitch -= possiblePitch > (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 60 : 80) ? 1 : 10) {
                            if (RayTraceUtil.getOver(lastblockdata.getFacing(), lastblockdata.getPosition(), true, 5, possibleYaw, possiblePitch)) {
                                targetYaw = possibleYaw;
                                targetPitch = possiblePitch;
                                found = true;
                            }
                        }
                    }

                    rotations = new float[]{rotationsMode.is("Full Brute Force") ? targetYaw : mc.thePlayer.rotationYaw - yawValue.getValue().floatValue(), targetPitch};
                    break;
                case "Normal":
                    rotations = RotationsUtils.getSmoothRotations(lastblockdata.getPosition(), lastblockdata.getFacing(), false,
                            bruteForce.getValue().floatValue(), yawValue.getValue().floatValue());
                    break;
            }
         } else  {
            rotations = new float[] {mc.thePlayer.rotationYaw - yawValue.getValue().floatValue(), 80.34f};
        }

        yaw = rotations[0];
        pitch = rotations[1];
    }

    @Callback
    public void onPacket(PacketEvent event) {
        if(event.getType() == EventType.INCOMING && towerMode.is("Watchdog")) {
            if(tower.isEnabled() && mc.gameSettings.keyBindJump.isKeyDown()) {
                if (event.getPacket() instanceof C03PacketPlayer) {
                    if (wdSpoof) {
                        ((C03PacketPlayer) event.getPacket()).onGround = true;
                        wdSpoof = false;
                    }
                }
            }
        }
    }

    /* functions */
    private void sneak() {
        if(!sneak.isEnabled()) {
            return;
        }

        if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ)).getBlock() instanceof BlockAir && mc.thePlayer.onGround) {
            if(timestart.hasTimeElapsed(MathHelper.randomFloatClamp(new Random(), startMin.getValue().floatValue(), startMax.getValue().floatValue()))) {
                sneaking = true;
                timestart.reset();
                mc.gameSettings.keyBindSneak.pressed = true;
            }
        } else {
            if (sneaking) {
                if (timestart.hasTimeElapsed(MathHelper.randomFloatClamp(new Random(), endMin.getValue().floatValue(), endMax.getValue().floatValue()))) {
                    mc.gameSettings.keyBindSneak.pressed = false;
                    timerend.reset();
                    sneaking = false;
                }
            }
        }
    }

    private void unSneak() {
        //||  lastblockdata == null || !RayTraceUtil.getOver(lastblockdata.getFacing(), lastblockdata.getPosition(), false, 5, yaw, pitch)
        if(!startSneak.isEnabled() || sneak.isEnabled()) {
            return;
        }

        mc.gameSettings.keyBindSneak.pressed = false;
    }

    private void place() {
        if(!sprint.isEnabled() && mc.thePlayer.isSprinting() && (!sprintTower.isEnabled() || !towering)) {
          mc.thePlayer.setSprinting(false);
        }

        if(sprintTower.isEnabled() && towering) {
            mc.thePlayer.setSprinting(true);
        }

        blockdata = ScafUtil.placeData();
        if (blockdata != null) {
            lastblockdata = ScafUtil.placeData();
        } else {
            return;
        }

        if(rotationCalculateEvent.is("Update")) {
            calculateRotations();
        }

        int slot = ScafUtil.getBlockSlot();

        if(lastblockdata != null) {
            if((RayTraceUtil.getOver(lastblockdata.getFacing(), lastblockdata.getPosition(), raytraceMode.is("Advanced"), 5, yaw, pitch)) || raytraceMode.is("Off")) {
                mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getStackInSlot(slot), lastblockdata.position, lastblockdata.facing, VectorUtil.getNewVector(lastblockdata));
            }

            unSneak();
        }

        if(swing.isEnabled()){
            mc.thePlayer.swingItem();
        } else {
            mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
        }

        mc.sendClickBlockToController(mc.currentScreen == null && mc.gameSettings.keyBindAttack.isKeyDown() && mc.inGameHasFocus);

        if(autoJump.isEnabled()) {
            mc.gameSettings.keyBindJump.pressed = true;
        }

        blockdata = null;
    }
}
